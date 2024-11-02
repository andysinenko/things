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
    @Value("${things.jwt.secret-key}")
    private String jwtKey;

    @Value("${things.jwt.header}")
    private String header;

    @Value("${things.jwt.expiration}")
    private long expiration;

    @Value("${things.jwt.refresh-token.expiration}")
    private long refreshExpiration;


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

    public boolean isTokenExpired(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration().before(new Date());
    }

    public boolean isTokenValid(String userName, String token, ThingsUserRepository userRepository) {
        final var user = userRepository.findByUsername(userName);
        return (userName.equals(user.get().getUsername())) && !isTokenExpired(token);
    }
}
