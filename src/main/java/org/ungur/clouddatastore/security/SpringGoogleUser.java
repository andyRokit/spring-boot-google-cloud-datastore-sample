package org.ungur.clouddatastore.security;

import com.google.appengine.api.users.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SpringGoogleUser implements UserDetails {
    private User googleUser;
    private Collection<? extends GrantedAuthority> authorities;

    public SpringGoogleUser(User rawUser, Collection<? extends GrantedAuthority> authorities) {
        this.googleUser = rawUser;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "N/a";
    }

    @Override
    public String getUsername() {
        return googleUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getUserId() {
        return googleUser.getUserId();
    }
}
