package br.com.jlcorradi.commons.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants
{
  public static final String BEARER_HEADER = "Bearer ";
  public static final String USER_ID_JWT_CLAIM = "userId";
  public static final String AUTHORITIES_JWT_CLAIM = "commaSeparatedAuthorities";
}
