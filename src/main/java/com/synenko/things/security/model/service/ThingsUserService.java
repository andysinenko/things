package com.synenko.things.security.model.service;

import com.synenko.things.common.exception.UserExistsException;
import com.synenko.things.security.model.dto.AuthorityDto;
import com.synenko.things.security.model.dto.UserRequest;
import com.synenko.things.security.model.dto.UserResponse;
import com.synenko.things.security.model.dto.UserMapper;
import com.synenko.things.security.model.entity.Authority;
import com.synenko.things.security.model.entity.ThingsUser;
import com.synenko.things.security.model.repository.AuthorityRepository;
import com.synenko.things.security.model.repository.ThingsUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThingsUserService {
    private static final Logger logger = LoggerFactory.getLogger(ThingsUserService.class);

    private final ThingsUserRepository thingsUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;


    public List<UserResponse> getAllUsers(Authentication authentication) {
        boolean isAdmin = authentication
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));
        if (!isAdmin) {
            throw new AccessDeniedException("Only ADMIN can list all users");
        }

        return thingsUserRepository.findAllByOrderByUsernameAsc()
                .stream()
                .map(UserMapper::mapToDto)
                .toList();
    }

    public UserResponse getUserDetailsAfterLogin(Authentication authentication) {
        return thingsUserRepository.findByUsername(authentication.getName())
                .map(UserMapper::mapToDto)
                .orElseThrow(() -> new UserExistsException(authentication.getName()));
    }

    public Optional<UserResponse> findById(Long id) {
        return Optional.of(thingsUserRepository.findById(id)
                .map(UserMapper::mapToDto
                ).orElseThrow(() -> new UserExistsException(id)));
    }

    @Transactional
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        ThingsUser user = thingsUserRepository.findById(id)
                .orElseThrow(() -> new UserExistsException("User not found with id: " + id));

        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setAccountNonExpired(userRequest.isAccountNonExpired());
        user.setAccountNonLocked(userRequest.isAccountNonLocked());
        user.setCredentialsNonExpired(userRequest.isCredentialsNonExpired());
        user.setEnabled(userRequest.isEnabled());

        if (userRequest.getPassword() != null && !userRequest.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        if (userRequest.getAuthorities() != null) {
            Set<Long> authorityIds = userRequest.getAuthorities().stream()
                    .map(AuthorityDto::getId)
                    .collect(Collectors.toSet());
            List<Authority> authorities = authorityRepository.findAllById(authorityIds);
            user.setAuthorities(authorities);
        }

        ThingsUser saved = thingsUserRepository.save(user);
        return UserMapper.mapToDto(saved);
    }

    public UserResponse findByUsername(String username) {
        var user = thingsUserRepository.findByUsername(username)
                .orElseThrow(() -> new UserExistsException(username));
        return UserMapper.mapToDto(user);
    }
}
