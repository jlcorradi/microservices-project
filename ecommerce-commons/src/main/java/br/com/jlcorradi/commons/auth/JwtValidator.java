package br.com.jlcorradi.commons.auth;

import br.com.jlcorradi.commons.exception.UnauthorizedTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class JwtValidator
{

  private final String jwtTokenSecret;

  public JwtValidator(@Value("${authService.jwtTokenSecret}") String jwtTokenSecret)
  {
    this.jwtTokenSecret = jwtTokenSecret;
  }

  public Claims validateJwtToken(String accessToken)
  {
    JwtParser parser = Jwts.parser()
        .verifyWith(Keys.hmacShaKeyFor(jwtTokenSecret.getBytes(StandardCharsets.UTF_8)))
        .build();

    try {
      return parser.parseSignedClaims(accessToken).getPayload();
    } catch (Exception e) {
      throw new UnauthorizedTokenException(e);
    }
  }
}
