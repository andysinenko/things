package ua.com.sinenko.things.security.model.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.com.sinenko.things.security.model.entity.ThingsUser;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenService {
    @Value("${things.jwt.secret-key}")
    private String jwtKey;

    @Value("${things.jwt.header}")
    private String header;

    @Value("${things.jwt.expiration}")
    private long expiration;

    @Value("${things.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String generateToken(ThingsUser thingsUser) {
        Key secretKey = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
        return Jwts
                .builder()
                .claims(new HashMap<>())
                .issuer("sinenko.homes")
                .subject(thingsUser.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(ThingsUser thingsUser) {
        return generateToken(thingsUser);
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

    public Jws<Claims> getCaims(String jwtToken) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwtToken);
    }
}
