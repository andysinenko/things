package com.synenko.things.security.web;


import com.synenko.things.security.model.dto.AuthorityDto;
import com.synenko.things.security.model.dto.UserDto;
import com.synenko.things.security.model.service.AuthoritiesService;
import com.synenko.things.security.model.service.ThingsUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public UserDto getUserDetailsAfterLogout(Long id) {
        return thingsUserService.findById(id);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(Authentication authentication) {
        return ResponseEntity.ok(thingsUserService.getAllUsers(authentication));
    }

    @GetMapping("/authorities")
    public ResponseEntity<List<AuthorityDto>> getAuthorities() {
        return ResponseEntity.ok(authoritiesService.getAuthorities());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        var response = thingsUserService.updateUser(id, userDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
