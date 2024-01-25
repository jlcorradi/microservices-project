package br.com.jlcorradi.commons.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

public class BasicJwtSecurityHttpConfigurer extends AbstractHttpConfigurer<BasicJwtSecurityHttpConfigurer, HttpSecurity> {
  private JwtValidator jwtValidator;

  @Override
  public void init(HttpSecurity http) {
    http.authenticationProvider(new BasicJwtAuthenticationProvider(jwtValidator));
  }

  public BasicJwtSecurityHttpConfigurer withJwtValidator(JwtValidator jwtValidator) {
    this.jwtValidator = jwtValidator;
    return this;
  }

  @Override
  public void configure(HttpSecurity http) {
    AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
    http.addFilterBefore(new JwtAuthFilter(authenticationManager), AuthorizationFilter.class);
  }
}
