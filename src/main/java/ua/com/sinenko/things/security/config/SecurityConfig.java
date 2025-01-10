package ua.com.sinenko.things.security.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import ua.com.sinenko.things.security.filter.CsrfCookieFilter;
import ua.com.sinenko.things.security.filter.JWTTokenGeneratorFilter;
import ua.com.sinenko.things.security.filter.JWTTokenValidatorFilter;
import ua.com.sinenko.things.security.model.service.ThingsAccessDeniedHandler;
import ua.com.sinenko.things.security.model.service.ThingsAuthenticationEntryPoint;

import java.util.Collections;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@Profile("stage")
public class SecurityConfig {

    private final JWTTokenValidatorFilter jwtTokenValidatorFilter;
    private final JWTTokenGeneratorFilter jwtTokenGeneratorFilter;
    private final UserDetailsService userDetailsService;
    private final ThingsAuthenticationEntryPoint authenticationEntryPoint;
    private final ThingsAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(UserDetailsService userDetailsService, JWTTokenValidatorFilter jwtTokenValidatorFilter, JWTTokenGeneratorFilter jwtTokenGeneratorFilter,
                          ThingsAuthenticationEntryPoint authenticationEntryPoint, ThingsAccessDeniedHandler accessDeniedHandler) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.userDetailsService = userDetailsService;
        this.jwtTokenValidatorFilter = jwtTokenValidatorFilter;
        this.jwtTokenGeneratorFilter = jwtTokenGeneratorFilter;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http.securityContext((context) -> context.requireExplicitSave(false))
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);
                        return config;
                }}))
                .csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/api/v1/auth/authenticate", "/api/v1/auth/register")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests((requests)->requests
                        .requestMatchers("/api/v1/auth/register","/api/v1/auth/authenticate")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .addFilterAfter(jwtTokenGeneratorFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtTokenValidatorFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling((exception) -> exception.authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
