package br.com.jlcorradi.commons.auth;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class BasicJwtAuthenticationToken extends AbstractAuthenticationToken {

    @Getter
    private final String username;
    @Getter
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
