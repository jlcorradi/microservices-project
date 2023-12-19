package br.com.jlcorradi.commons.auth;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class BasicJwtAuthenticationToken extends AbstractAuthenticationToken
{
  private final String token;

  @Getter
  @Setter
  private String username;
  @Getter
  private final String userId;

  private BasicJwtAuthenticationToken(String userId, String token, String commaSeparatedAuthorities)
  {
    super(StringUtils.hasText(commaSeparatedAuthorities) ?
        AuthorityUtils.createAuthorityList(commaSeparatedAuthorities.split(",")) :
        List.of()
    );
    this.userId = userId;
    this.token = token;
    this.setAuthenticated(!StringUtils.hasText(token));
  }

  public static BasicJwtAuthenticationToken authenticated(String userId, String username, String commaSeparatedAuthorities)
  {
    BasicJwtAuthenticationToken auth = new BasicJwtAuthenticationToken(userId, null, commaSeparatedAuthorities);
    auth.setDetails(userId);
    auth.setUsername(username);
    return auth;
  }

  public static BasicJwtAuthenticationToken unauthenticated(String token)
  {
    return new BasicJwtAuthenticationToken(null, token, "");
  }

  @Override
  public Object getCredentials()
  {
    return this.token;
  }

  @Override
  public Object getPrincipal()
  {
    return this.userId;
  }
}
