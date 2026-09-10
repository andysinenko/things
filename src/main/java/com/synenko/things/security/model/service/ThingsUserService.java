package com.synenko.things.security.model.service;

import com.synenko.things.common.exception.UserExistsException;
import com.synenko.things.security.model.dto.AuthorityDto;
import com.synenko.things.security.model.dto.UserDto;
import com.synenko.things.security.model.dto.UserMapper;
import com.synenko.things.security.model.entity.Authority;
import com.synenko.things.security.model.entity.ThingsUser;
import com.synenko.things.security.model.repository.AuthorityRepository;
import com.synenko.things.security.model.repository.ThingsUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThingsUserService {
    private static final Logger logger = LoggerFactory.getLogger(ThingsUserService.class);

    private final ThingsUserRepository thingsUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;


    public List<UserDto> getAllUsers(Authentication authentication) {
        //todo add check for access rights by authentication.getName()

        return thingsUserRepository.findAllByOrderByUsernameAsc()
                .stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .password("********")
                        .email(user.getEmail())
                        .phoneNumber(user.getPhoneNumber())
                        .accountNonExpired(user.isAccountNonExpired())
                        .accountNonLocked(user.isAccountNonLocked())
                        .credentialsNonExpired(user.isCredentialsNonExpired())
                        .enabled(user.isEnabled())
                        .authorities(user.getAuthorities().stream()
                                .map(a -> AuthorityDto.builder()
                                        .id(a.getId())
                                        .name(a.getName())
                                        .build())
                                .collect(Collectors.toList()))
                        .build()
                ).toList();
    }

    public UserDto getUserDetailsAfterLogin(Authentication authentication) {
        return thingsUserRepository.findByUsername(authentication.getName())
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .password(user.getPassword())
                        .email(user.getEmail())
                        .phoneNumber(user.getPhoneNumber())
                        .accountNonExpired(user.isAccountNonExpired())
                        .accountNonLocked(user.isAccountNonLocked())
                        .credentialsNonExpired(user.isCredentialsNonExpired())
                        .enabled(user.isEnabled())
                        .authorities(user.getAuthorities().stream()
                                .map(a -> AuthorityDto.builder()
                                        .id(a.getId())
                                        .name(a.getName())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .orElseThrow(() -> new UserExistsException(authentication.getName()));
    }

    public UserDto findById(Long id) {
        return thingsUserRepository.findById(id)
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .password(user.getPassword())
                        .email(user.getEmail())
                        .phoneNumber(user.getPhoneNumber())
                        .accountNonExpired(user.isAccountNonExpired())
                        .accountNonLocked(user.isAccountNonLocked())
                        .credentialsNonExpired(user.isCredentialsNonExpired())
                        .enabled(user.isEnabled())
                        .authorities(user.getAuthorities().stream()
                                .map(e -> {
                                    return AuthorityDto.builder()
                                            .id(e.getId())
                                            .name(e.getName())
                                            .build();
                                }).toList()
                        ).build()
                ).orElseThrow(() -> new UserExistsException(id));
    }
    @Transactional
    public UserDto updateUser(Long id, UserDto dto) {
        ThingsUser user = thingsUserRepository.findById(id)
                .orElseThrow(() -> new UserExistsException("User not found with id: " + id));

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAccountNonExpired(dto.isAccountNonExpired());
        user.setAccountNonLocked(dto.isAccountNonLocked());
        user.setCredentialsNonExpired(dto.isCredentialsNonExpired());
        user.setEnabled(dto.isEnabled());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getAuthorities() != null) {
            Set<Long> authorityIds = dto.getAuthorities().stream()
                    .map(AuthorityDto::getId)
                    .collect(Collectors.toSet());
            List<Authority> authorities = authorityRepository.findAllById(authorityIds);
            user.setAuthorities(authorities);
        }

        ThingsUser saved = thingsUserRepository.save(user);
        return UserMapper.mapToDto(saved);
    }

}
