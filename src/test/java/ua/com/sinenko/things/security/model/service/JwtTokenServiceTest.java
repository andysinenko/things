package ua.com.sinenko.things.security.model.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.sinenko.things.security.model.entity.ThingsUser;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenServiceTest {
    JwtTokenService jwtTokenService = new JwtTokenService();

    @BeforeEach
    void setDataBeforeTests() {
        jwtTokenService.setJwtKey("secretkeyforjwttokenyforjwttoken");
        jwtTokenService.setHeader("Authorization");
        jwtTokenService.setExpiration(86400000L);
        jwtTokenService.setRefreshExpiration(604800000L);
    }

    @Test
    void generateToken() {
        ThingsUser thingsUser = new ThingsUser();
        thingsUser.setUsername("test");

        String token = jwtTokenService.generateToken(thingsUser);
        String[] parts = token.split("\\.");

        assertEquals("{\"alg\":\"HS256\"}", new String(Base64.getDecoder().decode(parts[0])));
        assertTrue(new String(Base64.getDecoder().decode(parts[1])).contains("test"));
    }

    @Test
    void generateRefreshToken() {
        ThingsUser thingsUser = new ThingsUser();
        thingsUser.setUsername("test");

        String refreshToken = jwtTokenService.generateRefreshToken(thingsUser);
        String[] parts = refreshToken.split("\\.");

        assertEquals("{\"alg\":\"HS256\"}", new String(Base64.getDecoder().decode(parts[0])));
        assertTrue(new String(Base64.getDecoder().decode(parts[1])).contains("test"));
    }

    @Test
    void isTokenExpired() {
        jwtTokenService.setJwtKey("secretkeyforjwttokenyforjwttoken");
        jwtTokenService.setExpiration(86400000L);

        ThingsUser thingsUser = new ThingsUser();
        thingsUser.setUsername("test");

        String token = jwtTokenService.generateToken(thingsUser);

        assertFalse(jwtTokenService.isTokenExpired(token));
    }

    @Test
    void isTokenValid() {
        ThingsUser thingsUser = new ThingsUser();
        thingsUser.setUsername("test");

        String token = jwtTokenService.generateToken(thingsUser);

        assertTrue(jwtTokenService.isTokenValid("test", token));

    }
}