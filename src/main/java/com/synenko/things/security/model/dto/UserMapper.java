package com.synenko.things.security.model.dto;

import com.synenko.things.security.model.entity.ThingsUser;

import java.util.stream.Collectors;

public class UserMapper {
    public static UserResponse mapToDto(ThingsUser user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .enabled(user.isEnabled())
                .authorities(user.getAuthorities().stream()
                        .map(a -> AuthorityDto.builder().id(a.getId()).name(a.getName()).build())
                        .collect(Collectors.toList()))
                .build();
    }
}
