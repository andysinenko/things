package ua.com.sinenko.things.security.model.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.com.sinenko.things.security.model.dto.AuthorityDto;
import ua.com.sinenko.things.security.model.dto.UserDto;
import ua.com.sinenko.things.security.model.entity.Authority;
import ua.com.sinenko.things.security.model.entity.ThingsUser;
import ua.com.sinenko.things.security.model.repository.AuthorityRepository;
import ua.com.sinenko.things.security.model.repository.JwtTokenRepository;
import ua.com.sinenko.things.security.model.repository.ThingsUserRepository;
import ua.com.sinenko.things.security.model.dto.AuthenticationRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith({MockitoExtension.class})
class JwtTokenServiceTest {
    @InjectMocks
    private JwtTokenService jwtTokenService;

    private final String secretKey = "secretkeyforjwttokenyforjwttoken";
    private final String header = "Authorization";
    private final Long expiration = 86400000L;
    private final Long refreshExpiration = 604800000L;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    ThingsUserRepository userRepository;

    @Mock
    JwtTokenRepository jwtTokenRepository;

    @Mock
    AuthorityRepository authorityRepository;

    @BeforeEach
    void setDataBeforeTests() {
        jwtTokenService.setJwtKey(secretKey);
        jwtTokenService.setHeader(header);
        jwtTokenService.setExpiration(expiration);
        jwtTokenService.setRefreshExpiration(refreshExpiration);
    }

    @Test
    void generateToken() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(getUser()));

        var token = jwtTokenService.generateToken(userRepository.findByUsername("test").get());
        var parts = getTokenParts(token);

        assertEquals("{\"alg\":\"HS256\"}", parts.get("header"));
        assertTrue( parts.get("body").contains("test"));

    }

    @Test
    void generateRefreshToken() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(getUser()));
        var token = jwtTokenService.generateRefreshToken(userRepository.findByUsername("test").get());
        var parts = getTokenParts(token);

        assertEquals("{\"alg\":\"HS256\"}", parts.get("header"));
        assertTrue( parts.get("body").contains("test"));
    }

    @Test
    void refreshToken() {
    }

    @Test
    void register() {
        UserDto userDto = UserDto.builder()
                .username("test")
                .password("test")
                .email("test")
                .firstName("test")
                .lastName("test")
                .phoneNumber("test")
                .authorities(List.of(AuthorityDto.builder().id(2L).name("ROLE_USER").build()))
                .build();

        when(userRepository.save(any(ThingsUser.class))).thenReturn(getUser());

        var registredUser = jwtTokenService.register(userDto);

        var token = registredUser.getAccessToken();
        var parts = getTokenParts(token);

        assertTrue(token != null);
        assertEquals("{\"alg\":\"HS256\"}", parts.get("header"));
        assertTrue( parts.get("body").contains("test"));
    }

    @Test
    void authenticate() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(getUser()));
        var response = jwtTokenService.authenticate(new AuthenticationRequest("test", "test"));
        assertTrue(jwtTokenService.isTokenValid(getUser().getUsername(), response.getAccessToken()));
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getRefreshToken());
    }

    private Map<String, String> getTokenParts(String token) {
        var parts = token.split("\\.");

        var header = new String(Base64.getDecoder().decode(parts[0]));
        var body = new String(Base64.getDecoder().decode(parts[1]));

        return Map.of("header", header, "body", body);
    }

    private ThingsUser getUser() {
        Authority authority = Authority.builder().name("ROLE_USER").build();

        return ThingsUser.builder()
                .id(1L)
                .email("test@email.com")
                .authorities(List.of(authority))
                .username("test")
                .firstName("test")
                .lastName("test")
                .password(passwordEncoder.encode("test"))
                .isCredentialsNonExpired(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isEnabled(true)
                .createDate(new Date())
                .build();
    }
}