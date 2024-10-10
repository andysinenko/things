package ua.com.sinenko.things.security.model.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.sinenko.things.security.model.entity.Role;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthorityDto {
    private Long id;

    private String name;
}
