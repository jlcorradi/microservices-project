package br.com.jlcorradi.commons.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class BasicJwtAuthSecurityConfig {
  private final JwtValidator jwtValidator;

  @Bean
  public SecurityFilterChain config(HttpSecurity http) throws Exception {
    log.info("Using Basic Static Jwt based security");

    return http
        .httpBasic(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .sessionManagement(cfg -> cfg.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize ->
        {
          authorize.requestMatchers("/actuator", "/actuator/**").permitAll();
          authorize.requestMatchers("/api/**").hasAnyRole("user", "admin", "root");
          authorize.anyRequest().authenticated();
        })
        .exceptionHandling(ex -> ex
            .authenticationEntryPoint(new BasicJwtAuthenticationEntryPoint())
            .accessDeniedHandler(new BasicJwtAccessDeniedHandler())
        )
        .with(new BasicJwtSecurityHttpConfigurer(), jwt -> jwt.withJwtValidator(jwtValidator))
        .build();
  }
}
