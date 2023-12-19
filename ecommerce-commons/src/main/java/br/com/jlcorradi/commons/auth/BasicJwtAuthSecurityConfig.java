package br.com.jlcorradi.commons.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
@Import(JwtAuthFilter.class)
@EnableWebSecurity
@EnableMethodSecurity
public class BasicJwtAuthSecurityConfig
{
  private final JwtAuthFilter jwtAuthFilter;

  @Bean
  public SecurityFilterChain config(HttpSecurity http) throws Exception
  {
    log.info("Using Basic Static Jwt based security");
    return http
        .httpBasic(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(cfg -> cfg.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize ->
        {
          authorize.requestMatchers("/actuator", "/actuator/**").permitAll();
          authorize.anyRequest().authenticated();
        })
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }
}
