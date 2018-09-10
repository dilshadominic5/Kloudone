package com.xedflix.video.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

public class UserNamePasswordAuthenticationTokenExtended extends UsernamePasswordAuthenticationToken implements Authentication {

    Map<String, Object> claims;

    public UserNamePasswordAuthenticationTokenExtended(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public UserNamePasswordAuthenticationTokenExtended(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public UserNamePasswordAuthenticationTokenExtended(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, Map<String, Object> claims) {
        super(principal, credentials, authorities);
        this.claims = claims;
    }
}
