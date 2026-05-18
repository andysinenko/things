package ua.com.sinenko.things.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.com.sinenko.things.security.model.repository.ThingsUserRepository;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
public class ApplicationConfig {
    private ThingsUserRepository thingsUserRepository;

    public ApplicationConfig(ThingsUserRepository thingsUserRepository) {
        this.thingsUserRepository = thingsUserRepository;
    }

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

    @Bean
    public CaffeineCache authorsCache() {
        return new CaffeineCache("authors",
                Caffeine.newBuilder()
                        .maximumSize(500)
                        .expireAfterWrite(Duration.ofHours(1))
                        .refreshAfterWrite(Duration.ofMinutes(5))
                        .build(key -> /* loader */ null));
    }

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager();
        manager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(60, TimeUnit.SECONDS)
        );

        return manager;
    }
}
