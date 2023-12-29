package br.com.jlcorradi.commons.auth;

import io.jsonwebtoken.Claims;

public interface JwtValidator
{
  Claims validateJwtToken(String accessToken);
}
