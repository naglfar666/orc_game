package api.v1.handlers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String HEADER = "TOKEN";
    private static final String BEARER_TOKEN = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authenticationHeader = request.getHeader(HEADER);

        try {
            SecurityContext context = SecurityContextHolder.getContext();

            if(authenticationHeader != null && authenticationHeader.startsWith(BEARER_TOKEN)) {

                final String bearerTkn = authenticationHeader.replaceAll(BEARER_TOKEN, "");

                try {
                    Jws<Claims> claims = Jwts.parser().requireIssuer(Constants.JWT_ISSUER).setSigningKey(Constants.JWT_SECRET_KEY).parseClaimsJws(bearerTkn);

                    String roles = (String) claims.getBody().get("roles");
                    Integer id = (Integer) claims.getBody().get("userId");

                    List<GrantedAuthority> authority = new ArrayList<GrantedAuthority>();

                    for (String role: roles.split(","))
                        authority.add(new SimpleGrantedAuthority(role));

                    MyAuthToken authenticationTkn = new MyAuthToken(id, null, authority);

                    context.setAuthentication(authenticationTkn);
                } catch (Exception e) {
                    throw new ServletException("Invalid token.");
                }
            }

            filterChain.doFilter(request, response);
            context.setAuthentication(null);
        } catch(AuthenticationException ex) {
            throw new ServletException("Authentication exception.");
        }
    }

}