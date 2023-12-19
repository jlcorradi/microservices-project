package br.com.jlcorradi.auth.api;

import br.com.jlcorradi.auth.dto.EcommerceUserDto;
import br.com.jlcorradi.auth.dto.LoginRequest;
import br.com.jlcorradi.auth.dto.LoginResponse;
import br.com.jlcorradi.auth.service.EcommerceUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthApi
{

  private final EcommerceUserService userService;

  @PostMapping
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request)
  {
    return userService.login(request)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
  }

  @GetMapping
  public ResponseEntity<EcommerceUserDto> validateToken(
      @RequestParam("accessToken") String accessToken,
      @RequestParam("uri") String uri
  )
  {
    EcommerceUserDto ecommerceUserDto = userService.resolveUserFromToken(accessToken);
    log.debug("Verifying access to URI: {}", uri);
    return ResponseEntity.ok(ecommerceUserDto);
  }
}
