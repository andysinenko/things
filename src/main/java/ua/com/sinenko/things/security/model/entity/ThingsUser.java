package ua.com.sinenko.things.security.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "users")
@Builder
public class ThingsUser implements UserDetails {
    @Id
    @SequenceGenerator(
            name = "places_sequence",
            sequenceName = "places_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "places_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "username")
    private final String username;

    @Column(name = "password")
    private final String password;

    @Column(name = "granted_authorities")
    private final Set<? extends GrantedAuthority> grantedAuthorities;

    @Column(name = "is_account_non_expired")
    private final boolean isAccountNonExpired;

    @Column(name = "is_account_non_locked")
    private final boolean isAccountNonLocked;

    @Column(name = "is_credentials_non_expired")
    private final boolean isCredentialsNonExpired;

    @Column(name = "is_enabled")
    private final boolean isEnabled;

    public ThingsUser(Long id,
                      String username,
                      String password,
                      Set<? extends GrantedAuthority> grantedAuthorities,
                      boolean isAccountNonExpired,
                      boolean isAccountNonLocked,
                      boolean isCredentialsNonExpired,
                      boolean isEnabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
