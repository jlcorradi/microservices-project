package br.com.jlcorradi.commons.auth;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

@Getter
@EqualsAndHashCode(callSuper = false)
public class BasicJwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String username;
    private final String userId;

    public BasicJwtAuthenticationToken(String username, String userId, String commaSeparatedAuthorities) {
        super(AuthorityUtils.createAuthorityList(commaSeparatedAuthorities.split(",")));
        this.username = username;
        this.userId = userId;
    }

    @Override
    public Object getCredentials() {
        return this.username;
    }

    @Override
    public Object getPrincipal() {
        return this.userId;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }
}
