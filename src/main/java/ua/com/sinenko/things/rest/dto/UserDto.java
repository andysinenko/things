package ua.com.sinenko.things.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto implements Serializable {
    private static final long serialVersionUID = 479480883093048690L;

    private Long id;

    private String username;

    private String password;

    private String email;

    private List<String> authorities;
}
