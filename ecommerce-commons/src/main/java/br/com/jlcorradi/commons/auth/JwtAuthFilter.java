package br.com.jlcorradi.commons.auth;

import br.com.jlcorradi.commons.exception.UnauthorizedTokenException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class JwtAuthFilter extends OncePerRequestFilter
{

  private final AuthenticationManager authenticationManager;

  public JwtAuthFilter(AuthenticationManager authenticationManager)
  {
    this.authenticationManager = authenticationManager;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  @Nonnull HttpServletResponse response,
                                  @Nonnull FilterChain filterChain) throws ServletException, IOException
  {
    // Check
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (null == authorizationHeader)
    {
      filterChain.doFilter(request, response);
      return;
    }

    BasicJwtAuthenticationToken authRequest = Optional.of(authorizationHeader)
        .map(authHeader -> authHeader.substring(Constants.BEARER_HEADER.length()))
        .map(BasicJwtAuthenticationToken::unauthenticated)
        .orElseThrow(UnauthorizedTokenException::new);

    // Authenticate
    Authentication auth = authenticationManager.authenticate(authRequest);
    SecurityContext newContext = SecurityContextHolder.createEmptyContext();
    newContext.setAuthentication(auth);
    SecurityContextHolder.setContext(newContext);

    MDC.put("userId", auth.getPrincipal().toString());

    // Next
    filterChain.doFilter(request, response);
  }

}
