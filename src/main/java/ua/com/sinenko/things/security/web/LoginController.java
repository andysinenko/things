package ua.com.sinenko.things.security.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.sinenko.things.security.model.dto.AuthenticationRequest;
import ua.com.sinenko.things.security.model.dto.AuthenticationResponse;
import ua.com.sinenko.things.security.model.dto.AuthorityDto;
import ua.com.sinenko.things.security.model.dto.UserDto;
import ua.com.sinenko.things.security.model.repository.ThingsUserRepository;
import ua.com.sinenko.things.security.model.service.AuthService;

import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

    @Autowired
    private ThingsUserRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserDto request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/user")
    public UserDto getUserDetailsAfterLogin(Authentication authentication) {
        var storedUser = customerRepository.findByUsername(authentication.getName());

        return storedUser.map(thingsUser -> UserDto.builder().id(thingsUser.getId())
                .username(thingsUser.getUsername())
                .authorities(thingsUser.getAuthorities().stream()
                        .map(e -> {
                            return AuthorityDto
                                    .builder()
                                    .name(e.getName())
                                    .id(e.getId())
                                    .build();
                                })
                        .collect(Collectors.toList()))
                .build()).orElseThrow();

    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
