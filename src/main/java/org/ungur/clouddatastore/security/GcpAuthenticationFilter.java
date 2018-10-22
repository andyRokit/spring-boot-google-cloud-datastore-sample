package org.ungur.clouddatastore.security;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Based on https://github.com/spring-projects/spring-security/blob/master/samples/xml/gae/src/main/java/samples/gae/security/GaeAuthenticationFilter.java
 */
public class GcpAuthenticationFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> ads = new WebAuthenticationDetailsSource();

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        User googleUser = UserServiceFactory.getUserService().getCurrentUser();

        if (authentication != null
                && !loggedInUserMatchesGcpUser(authentication, googleUser)) {
            SecurityContextHolder.clearContext();
            authentication = null;
            request.getSession().invalidate();
        }

        if (authentication == null) {
            if (googleUser != null) {
                logger.debug("Currently logged on to Gcp as user " + googleUser);
                logger.debug("Authenticating to Spring Security");
                // User has returned after authenticating via Gcp. Need to authenticate
                // through Spring Security.
                PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(
                        googleUser, null);
                token.setDetails(ads.buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }

        chain.doFilter(request, response);
    }


    private boolean loggedInUserMatchesGcpUser(Authentication authentication,
                                               User googleUser) {
        assert authentication != null;

        if (googleUser == null) {
            // User has logged out of Gcp but is still logged into application
            return false;
        }

        return authentication.getName().equals(googleUser.getEmail());
    }
}