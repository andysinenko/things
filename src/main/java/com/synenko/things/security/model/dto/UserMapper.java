package com.synenko.things.security.model.dto;

import com.synenko.things.security.model.entity.ThingsUser;

import java.util.stream.Collectors;

public class UserMapper {
    public static UserDto mapToDto(ThingsUser user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password("********")
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
