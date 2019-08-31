package api.v1.handlers;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MyAuthToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = 1L;

    public MyAuthToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public MyAuthToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

}
