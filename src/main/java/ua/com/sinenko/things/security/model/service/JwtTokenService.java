package ua.com.sinenko.things.security.model.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.com.sinenko.things.security.model.entity.ThingsUser;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;

import static ua.com.sinenko.things.security.filter.Constants.JWT_ISSUER;


@Service
public class JwtTokenService {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenService.class);

    private final long expiration;
    private final long refreshExpiration;
    private final String jwtKey;

    public JwtTokenService(
            @Value("${things.jwt.expiration}") long expiration,
            @Value("${things.jwt.refresh-token.expiration}") long refreshExpiration,
            @Value("${things.jwt.secret-key}") String jwtKey) {
        this.expiration = expiration;
        this.refreshExpiration = refreshExpiration;
        this.jwtKey = jwtKey;
    }

    public String generateToken(ThingsUser thingsUser) {
        return buildToken(thingsUser, expiration);
    }

    public String generateRefreshToken(ThingsUser thingsUser) {
        return buildToken(thingsUser, refreshExpiration);
    }

    private String buildToken(ThingsUser thingsUser, long ttlMs) {
        SecretKey secretKey = getSecretKey();
        return Jwts.builder()
                .claims(new HashMap<>())
                .issuer(JWT_ISSUER)
                .subject(thingsUser.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ttlMs))
                .signWith(secretKey)
                .compact();
    }

    public boolean isTokenValid(String username, String token) {
        String subject = getSubjectFromToken(token);
        return username.equals(subject) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String getSubjectFromToken(String token) {
        return getClaims(token).getPayload().getSubject();
    }

    public Jws<Claims> getClaims(String jwtToken) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(jwtToken);
    }

    private Date extractExpiration(String token) {
        return getClaims(token).getPayload().getExpiration();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(
                jwtKey.getBytes(StandardCharsets.UTF_8)
        );
    }
}
