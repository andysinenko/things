package ua.com.sinenko.things.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.sinenko.things.rest.dto.UserDto;
import ua.com.sinenko.things.security.model.entity.ThingsUser;
import ua.com.sinenko.things.security.model.repository.UserRepository;

import java.time.LocalDate;
import java.util.Date;

@RestController
public class LoginController {

    @Autowired
    private UserRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        ThingsUser savedThingsUser = null;
        ThingsUser thingsUser = new ThingsUser();
        ResponseEntity response = null;
        try {
            thingsUser.setUsername(thingsUser.getUsername());
            String hashPwd = passwordEncoder.encode(thingsUser.getPassword());
            thingsUser.setPassword(hashPwd);
            thingsUser.setCreateDate(new Date());
            savedThingsUser = customerRepository.save(thingsUser);
            if (savedThingsUser.getId() > 0) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Given user details are successfully registered");
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }

    @RequestMapping("/user")
    public UserDto getUserDetailsAfterLogin(Authentication authentication) {
        var storedUser = customerRepository.findByEmail(authentication.getName());

        return storedUser.map(thingsUser -> UserDto.builder().id(thingsUser.getId())
                .username(thingsUser.getUsername())
                .authorities(thingsUser.getAuthorities().stream().map(e -> e.getAuthority().name()).toList())
                .build()).orElseGet(() -> {return null;}) ;

    }
}
