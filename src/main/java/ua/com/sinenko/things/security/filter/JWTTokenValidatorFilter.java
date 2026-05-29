package ua.com.sinenko.things.security.filter;

import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.com.sinenko.things.security.model.service.JwtTokenService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static ua.com.sinenko.things.security.filter.Constants.JWT_KEY;

@Component
@RequiredArgsConstructor
public class JWTTokenValidatorFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JWTTokenValidatorFilter.class);

    private final UserDetailsService userDetailsService;
    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = authHeader.substring(7);
            String keyEnv = getEnvironment().getProperty(JWT_KEY);
            var key = Keys.hmacShaKeyFor(keyEnv.getBytes(StandardCharsets.UTF_8));

            var claims = jwtTokenService.getClaims(jwt);
            String username = claims.getSubject();

            String authorities = (String) claims.get("authorities");

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var authToken = new UsernamePasswordAuthenticationToken(
                        username, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities)
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
            logger.error("JWT validation failed: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/api/v1/auth/authenticate")
                || path.equals("/api/v1/auth/register");
    }
}
