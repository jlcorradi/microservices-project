package br.com.jlcorradi.commons.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@RequiredArgsConstructor
public class BasicJwtSecurityHttpConfigurer extends AbstractHttpConfigurer<BasicJwtSecurityHttpConfigurer, HttpSecurity>
{
  private final JwtValidator jwtValidator;

  @Override
  public void init(HttpSecurity http)
  {
    http.authenticationProvider(new BasicJwtAuthenticationProvider(jwtValidator));
  }

  @Override
  public void configure(HttpSecurity http)
  {
    AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
    http.addFilterBefore(new JwtAuthFilter(authenticationManager), AuthorizationFilter.class);
  }
}
