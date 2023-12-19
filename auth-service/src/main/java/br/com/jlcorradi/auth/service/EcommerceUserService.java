package br.com.jlcorradi.auth.service;

import br.com.jlcorradi.auth.dto.EcommerceUserDto;
import br.com.jlcorradi.auth.dto.LoginRequest;
import br.com.jlcorradi.auth.dto.LoginResponse;
import br.com.jlcorradi.auth.repository.EcommerceUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class EcommerceUserService
{

  private final EcommerceUserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public Optional<LoginResponse> login(LoginRequest request)
  {
    return repository.findByUsername(request.username())
        .filter(ecommerceUser -> passwordEncoder.matches(request.password(), ecommerceUser.getEncodedPassword()))
        .map(jwtService::retrieveOrCreateTokenForUser)
        .map(LoginResponse::new);
  }

  public EcommerceUserDto resolveUserFromToken(String accessToken)
  {
    return jwtService.validateToken(accessToken);
  }
}
