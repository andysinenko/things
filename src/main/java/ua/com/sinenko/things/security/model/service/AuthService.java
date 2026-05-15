package ua.com.sinenko.things.security.model.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ua.com.sinenko.things.common.exception.UserExistsException;
import ua.com.sinenko.things.security.model.dto.AuthenticationRequest;
import ua.com.sinenko.things.security.model.dto.AuthenticationResponse;
import ua.com.sinenko.things.security.model.dto.AuthorityDto;
import ua.com.sinenko.things.security.model.dto.UserDto;
import ua.com.sinenko.things.security.model.entity.Authority;
import ua.com.sinenko.things.security.model.entity.JwtTokenEntity;
import ua.com.sinenko.things.security.model.entity.ThingsUser;
import ua.com.sinenko.things.security.model.repository.AuthorityRepository;
import ua.com.sinenko.things.security.model.repository.JwtTokenRepository;
import ua.com.sinenko.things.security.model.repository.ThingsUserRepository;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final JwtTokenService jwtTokenService;
    private final ThingsUserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenRepository jwtTokenRepository;

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
            logger.warn("auth header is null ");
            return;
        }

        refreshToken = authHeader.substring(7);

        userName = Jwts.parser()
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload()
                .get("username").toString();

        if (userName != null) {
            var thingsUser = userRepository
                    .findByUsername(userName)
                    .orElseThrow(() -> new UserExistsException("User doesn't exists"));
            if (jwtTokenService.isTokenValid(userName, refreshToken)) {
                var accessToken = jwtTokenService.generateToken(thingsUser);
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
                .map(AuthorityDto::getName)
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
        var jwtToken = jwtTokenService.generateToken(savedUser);
        var refreshToken = jwtTokenService.generateRefreshToken(user);
        saveToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }



    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var thingsUser = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtTokenService.generateToken(thingsUser);
        var refreshToken = jwtTokenService.generateRefreshToken(thingsUser);
        revokeAllUserTokens(thingsUser);
        saveToken(thingsUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public JwtTokenEntity findByToken(String jwt) {
        return jwtTokenRepository.findByToken(jwt);
    }
}

