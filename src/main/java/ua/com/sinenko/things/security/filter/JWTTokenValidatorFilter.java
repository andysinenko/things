package ua.com.sinenko.things.security.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.com.sinenko.things.security.model.repository.JwtTokenRepository;
import ua.com.sinenko.things.security.model.service.JwtTokenService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
public class JWTTokenValidatorFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtTokenService jwtTokenService;
    private final JwtTokenRepository jwtTokenRepository;

    public JWTTokenValidatorFilter(UserDetailsService userDetailsService, JwtTokenService jwtTokenService, JwtTokenRepository jwtTokenRepository) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenService = jwtTokenService;
        this.jwtTokenRepository = jwtTokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        /*if (request.getServletPath().contains("/api/v1/auth") ) {
            filterChain.doFilter(request, response);
            return;
        }*/

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }


        var key = Keys.hmacShaKeyFor(Constants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

        String jwt = request.getHeader("Authorization");
        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
        }

        logger.info("JWT_KEY: " + Constants.JWT_KEY);
        logger.info("JWT: " + jwt);

        var claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
        var username = String.valueOf(claims.get("sub"));
        var username2 = String.valueOf(claims.get("username"));
        var authorities = String.valueOf(claims.get("authorities"));

        logger.info("claims.get(\"sub\"): " + username);
        logger.info("claims.get(\"username2\"): " + username2);
        logger.info("claims.get(\"authorities\"): " + authorities);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            var tokenRecord = jwtTokenRepository.findByToken(jwt);
            var isTokenValid = jwtTokenService.isTokenValid(username, tokenRecord.token);

            if (isTokenValid) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","))));
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/api/v1/auth/authenticate") ||
                request.getServletPath().equals("/api/v1/auth/register");
    }

}
