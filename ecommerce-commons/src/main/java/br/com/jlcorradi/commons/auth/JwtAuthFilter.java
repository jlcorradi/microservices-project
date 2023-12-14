package br.com.jlcorradi.commons.auth;

import br.com.jlcorradi.commons.exception.UnauthorizedTokenException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtValidator jwtValidator;

    public JwtAuthFilter(JwtValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!Collections.list(request.getHeaderNames()).contains(HttpHeaders.AUTHORIZATION)) {
            filterChain.doFilter(request, response);
            return;
        }

        BasicJwtAuthenticationToken auth = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .map(authHeader -> authHeader.substring(Constants.BEARER_HEADER.length()))
                .map(this::createAuthenticationObject)
                .orElseThrow(UnauthorizedTokenException::new);

        SecurityContext newContext = SecurityContextHolder.createEmptyContext();
        newContext.setAuthentication(auth);
        SecurityContextHolder.setContext(newContext);
        MDC.put("userId", auth.getUserId());

        filterChain.doFilter(request, response);
    }

    private BasicJwtAuthenticationToken createAuthenticationObject(String token) {
        Claims claims = jwtValidator.validateJwtToken(token);
        return new BasicJwtAuthenticationToken(claims.getSubject(),
                claims.get(Constants.USER_ID_JWT_CLAIM, String.class),
                claims.get(Constants.AUTHORITIES_JWT_CLAIM, String.class));
    }

}
