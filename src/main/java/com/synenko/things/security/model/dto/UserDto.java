package com.synenko.things.security.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 479480883093048690L;

    private Long id;

    private String username;

    private String password;

    private String email;

    private List<AuthorityDto> authorities = new ArrayList<>();

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;
}
