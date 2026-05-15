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
import java.security.Key;
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
        logger.info("! JWT_KEY in JwtTokenService {}", jwtKey);
        logger.info("");
        Key secretKey = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
        return Jwts
                .builder()
                .claims(new HashMap<>())
                .issuer(JWT_ISSUER)
                .subject(thingsUser.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(ThingsUser thingsUser) {
        Key secretKey = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
        return  Jwts
                .builder()
                .claims(new HashMap<>())
                .issuer(JWT_ISSUER)
                .subject(thingsUser.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(secretKey)
                .compact();
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

    public String getSubjectFromToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

    }

    public boolean isTokenValid(String userName, String token) {
        var subject =getSubjectFromToken(token);
        return (userName.equals(subject)) && !isTokenExpired(token);
    }

    public Jws<Claims> getClaims(String jwtToken) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwtToken);
    }
}
