package ua.com.sinenko.things.security.model.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ua.com.sinenko.things.common.exception.UserExistsException;
import ua.com.sinenko.things.security.model.dto.UserDto;
import ua.com.sinenko.things.security.model.entity.Authority;
import ua.com.sinenko.things.security.model.entity.JwtTokenEntity;
import ua.com.sinenko.things.security.model.entity.ThingsUser;
import ua.com.sinenko.things.security.model.repository.AuthorityRepository;
import ua.com.sinenko.things.security.model.repository.JwtTokenRepository;
import ua.com.sinenko.things.security.model.repository.ThingsUserRepository;
import ua.com.sinenko.things.security.model.dto.AuthenticationRequest;
import ua.com.sinenko.things.security.model.dto.AuthenticationResponse;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Setter
public class JwtTokenService {
    @Value("${things.jwt.key}")
    private String jwtKey;

    @Value("${things.jwt.header}")
    private String header;

    @Value("${things.jwt.expiration}")
    private long expiration;

    @Value("${things.jwt.refresh-token-expiration}")
    private long refreshExpiration;

    private ThingsUserRepository userRepository;

    private JwtTokenRepository jwtTokenRepository;

    private AuthorityRepository authorityRepository;

    private AuthenticationManager authenticationManager;

    public JwtTokenService(ThingsUserRepository thingsUserRepository, JwtTokenRepository jwtTokenRepository, AuthorityRepository authorityRepository, AuthenticationManager authenticationManager) {
        this.userRepository = thingsUserRepository;
        this.jwtTokenRepository = jwtTokenRepository;
        this.authorityRepository = authorityRepository;
        this.authenticationManager = authenticationManager;
    }


    private String buildToken(Map<String, Object> additionalClaims, ThingsUser user, long expiration) {
        Key secretKey = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
        return Jwts
                .builder()
                .claims(additionalClaims)
                .issuer("Sinenko.com.ua")
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(ThingsUser thingsUser) {
        return buildToken(new HashMap<>(), thingsUser, expiration);
    }

    public String generateRefreshToken(ThingsUser thingsUser) {
        return buildToken(new HashMap<>(), thingsUser, refreshExpiration);
    }

    private void revokeAllUserTokens(ThingsUser user) {
        var validUserTokens = jwtTokenRepository.findAllValidTokenByUserAndRevokedFalseAndExpiredFalse(user);

        if (validUserTokens != null && !validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setExpired(true);
                token.setRevoked(true);
            });
            jwtTokenRepository.saveAll(validUserTokens);
        }
    }

    private void saveToken(ThingsUser user, String jwtToken) {
        JwtTokenEntity jwtTokenEntity = JwtTokenEntity.builder()
                .user(user)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        jwtTokenRepository.save(jwtTokenEntity);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userName;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);

        userName = Jwts.parser()
                .build()
                .parseClaimsJws(refreshToken)
                .getPayload()
                .get("username").toString();

        if (userName != null) {
            var thingsUser = userRepository
                    .findByUsername(userName)
                    .orElseThrow();
            if (isTokenValid(userName, refreshToken)) {
                var accessToken = generateToken(thingsUser);
                revokeAllUserTokens(thingsUser);
                saveToken(thingsUser, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    @Transactional
    public AuthenticationResponse register(UserDto userDto) {
        var userCheck = userRepository.findByUsername(userDto.getUsername());

        if (userCheck.isPresent()) {
            throw new UserExistsException(userCheck.get().getUsername());
        }

        List<String> authList = userDto.getAuthorities()
                .stream()
                .map(e -> e.getName())
                .collect(Collectors.toList());
        List<Authority> authorities = authorityRepository.findAllByNameIn(authList);

        var user = ThingsUser.builder()
                .firstName(userDto.getUsername())
                .username(userDto.getUsername())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .authorities(authorities)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .phoneNumber(userDto.getPhoneNumber())
                .isEnabled(true)
                .createDate(new Date())
                .build();

        var savedUser = userRepository.save(user);
        var jwtToken = generateToken(savedUser);
        var refreshToken = generateRefreshToken(user);
        saveToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean isTokenValid(String userName, String token) {
        final var user = userRepository.findByUsername(userName);
        return (userName.equals(user.get().getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration().before(new Date());
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );
        var thingsUser = userRepository.findByUsername(request.getUserName())
                .orElseThrow();
        var jwtToken = generateToken(thingsUser);
        var refreshToken = generateRefreshToken(thingsUser);
        revokeAllUserTokens(thingsUser);
        saveToken(thingsUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

}
