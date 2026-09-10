package com.synenko.things.security.web;

import com.synenko.things.security.model.dto.AuthorityDto;
import com.synenko.things.security.model.service.AuthoritiesService;
import com.synenko.things.security.model.service.ThingsUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.synenko.things.security.model.dto.AuthenticationRequest;
import com.synenko.things.security.model.dto.AuthenticationResponse;
import com.synenko.things.security.model.dto.UserDto;
import com.synenko.things.security.model.service.AuthService;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final ThingsUserService thingsUserService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final AuthoritiesService authoritiesService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserDto request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/user")
    public UserDto getUserDetailsAfterLogin(Authentication authentication) {
        return thingsUserService.getUserDetailsAfterLogin(authentication);
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserDto>> getAllUsers(Authentication authentication) {
        return ResponseEntity.ok(thingsUserService.getAllUsers(authentication));
    }

    @GetMapping("user/{id}")
    public UserDto  getUserDetailsAfterLogout(Long id) {
        return thingsUserService.findById(id);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }


    @GetMapping("/authorities")
    public ResponseEntity<List<AuthorityDto>> getAuthorities() {
        return ResponseEntity.ok(authoritiesService.getAuthorities());
    }
}
