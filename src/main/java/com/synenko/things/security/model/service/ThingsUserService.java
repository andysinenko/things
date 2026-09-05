package com.synenko.things.security.model.service;

import com.synenko.things.common.exception.UserExistsException;
import com.synenko.things.security.model.dto.AuthorityDto;
import com.synenko.things.security.model.dto.UserDto;
import com.synenko.things.security.model.repository.ThingsUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThingsUserService {
    private static final Logger logger = LoggerFactory.getLogger(ThingsUserService.class);

    private final ThingsUserRepository thingsUserRepository;

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
}
