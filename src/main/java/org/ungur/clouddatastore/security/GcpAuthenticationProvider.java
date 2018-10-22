package org.ungur.clouddatastore.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class GcpAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        return authentication;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        if(aClass.equals(PreAuthenticatedAuthenticationToken.class)) {
            return true;
        } else {
            return false;
        }
    }
}
