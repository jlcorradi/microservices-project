package br.com.jlcorradi.commons.auth;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class BasicJwtAuthenticationProvider implements AuthenticationProvider {
  private final JwtValidator jwtValidator;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    try {
      Claims claims = jwtValidator.validateJwtToken(String.valueOf(authentication.getCredentials()));
      return BasicJwtAuthenticationToken.authenticated(
          claims.get(Constants.USER_ID_JWT_CLAIM, String.class),
          claims.getSubject(),
          claims.get(Constants.AUTHORITIES_JWT_CLAIM, String.class));
    } catch (Exception e) {
      throw new BadCredentialsException("Invalid Token");
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.isAssignableFrom(BasicJwtAuthenticationToken.class);
  }
}
