package ua.com.sinenko.things.security.model.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtTokenService {
    @Value("${application.security.jwt-key}")
    private String jwtKey;

    @Value("${application.security.jwt-header}")
    private String header;

    public String generateToken(String username) {
        return Jwts.builder()
                .setIssuer("Sinenko.com.ua")
                .setSubject("JWT Token")
                .claim("username", username)
                .claim("authorities", "ROLE_USER")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 30000000))
                .signWith(Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }
}
