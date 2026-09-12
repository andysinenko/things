package com.synenko.things.security.web;


import com.synenko.things.common.exception.UserExistsException;
import com.synenko.things.security.model.dto.AuthorityDto;
import com.synenko.things.security.model.dto.UserRequest;
import com.synenko.things.security.model.dto.UserResponse;
import com.synenko.things.security.model.service.AuthoritiesService;
import com.synenko.things.security.model.service.ThingsUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final ThingsUserService thingsUserService;
    private final AuthoritiesService authoritiesService;

    @GetMapping("/{id}")
    public UserResponse getUserDetailsAfterLogout(@PathVariable Long id) {
        return thingsUserService.findById(id).orElseThrow(() -> new UserExistsException(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(Authentication authentication) {
        return ResponseEntity.ok(thingsUserService.getAllUsers(authentication));
    }

    @GetMapping("/authorities")
    public ResponseEntity<List<AuthorityDto>> getAuthorities() {
        return ResponseEntity.ok(authoritiesService.getAuthorities());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest, Authentication authentication) {
        var response = thingsUserService.updateUser(id, userRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/me")
    public UserResponse getCurrentUser(Authentication authentication) {
        return thingsUserService.findByUsername(authentication.getName());
    }
}
