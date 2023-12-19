package br.com.jlcorradi.commons.auth;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.StringUtils;

@Getter
@EqualsAndHashCode(callSuper = false)
public class BasicJwtAuthenticationToken extends AbstractAuthenticationToken
{
  private final String userId;
  private final String token;

  private String username;

  private BasicJwtAuthenticationToken(String userId, String token, String commaSeparatedAuthorities)
  {
    super(AuthorityUtils.createAuthorityList(commaSeparatedAuthorities.split(",")));
    this.userId = userId;
    this.token = token;
    this.setAuthenticated(!StringUtils.hasText(token));
  }

  public static BasicJwtAuthenticationToken authenticated(String userId, String username, String commaSeparatedAuthorities)
  {
    BasicJwtAuthenticationToken auth = new BasicJwtAuthenticationToken(userId, null, commaSeparatedAuthorities);
    auth.setDetails(username);
    return auth;
  }

  @Override
  public Object getCredentials()
  {
    return this.username;
  }

  @Override
  public Object getPrincipal()
  {
    return this.userId;
  }

  @Override
  public boolean isAuthenticated()
  {
    return true;
  }
}
