package org.ungur.clouddatastore.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class GcpAuthentication extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -1949976839306453197L;
    private SpringGoogleUser authenticatedUser;

    public GcpAuthentication(Collection<? extends GrantedAuthority> authorities, SpringGoogleUser authenticatedUser) {
        super(authorities);
        this.authenticatedUser = authenticatedUser;
    }

    @Override
    public Object getCredentials() {
        return authenticatedUser.getPassword();
    }

    @Override
    public Object getPrincipal() {
        return authenticatedUser;
    }
}
