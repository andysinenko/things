package ua.com.sinenko.things.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.com.sinenko.things.security.model.repository.ThingsUserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ApplicationConfig {
    @Autowired
    private ThingsUserRepository thingsUserRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return (username) -> {
            var thingsUser = thingsUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            List<GrantedAuthority> authorities = thingsUser.getAuthorities().stream().map(e -> new SimpleGrantedAuthority(e.getName()))
                    .collect(Collectors.toUnmodifiableList());
            return new User(thingsUser.getUsername(), thingsUser.getPassword(), authorities);
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
