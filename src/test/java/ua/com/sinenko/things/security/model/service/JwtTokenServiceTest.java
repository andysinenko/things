package ua.com.sinenko.things.security.model.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.com.sinenko.things.security.model.entity.ThingsUser;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenServiceTest {
    JwtTokenService jwtTokenService;

    @BeforeEach
    void setDataBeforeTests() {
        jwtTokenService = new JwtTokenService("secretkeyforjwttokenyforjwttoken", "Authorization", 86400000L, 604800000L);
    }

    @Test
    @DisplayName("Token generation")
    void generateToken() {
        ThingsUser thingsUser = new ThingsUser();
        thingsUser.setUsername("test");
        Collections.synchronizedCollection(new ArrayList<>());
        String token = jwtTokenService.generateToken(thingsUser);
        String[] parts = token.split("\\.");

        assertAll("Assertion for generateToken()",
                () -> assertEquals("{\"alg\":\"HS256\"}", new String(Base64.getDecoder().decode(parts[0]))),
                () -> assertTrue(new String(Base64.getDecoder().decode(parts[1])).contains("test"))
        );
    }

    @Test
    @DisplayName("Refresh token generation")
    void generateRefreshToken() {
        ThingsUser thingsUser = new ThingsUser();
        thingsUser.setUsername("test");

        String refreshToken = jwtTokenService.generateRefreshToken(thingsUser);
        String[] parts = refreshToken.split("\\.");

        assertAll("Assertion for generateRefreshToken()",
            () -> assertEquals("{\"alg\":\"HS256\"}", new String(Base64.getDecoder().decode(parts[0]))),
            () -> assertTrue(new String(Base64.getDecoder().decode(parts[1])).contains("test"))
        );
    }

    @Test
    @DisplayName("Token expiration")
    void isTokenExpired() {

        ThingsUser thingsUser = new ThingsUser();
        thingsUser.setUsername("test");

        String token = jwtTokenService.generateToken(thingsUser);

        assertFalse(jwtTokenService.isTokenExpired(token));
    }

    @Test
    @DisplayName("Token validation")
    void isTokenValid() {
        ThingsUser thingsUser = new ThingsUser();
        thingsUser.setUsername("test");

        String token = jwtTokenService.generateToken(thingsUser);

        assertTrue(jwtTokenService.isTokenValid("test", token));

    }
}