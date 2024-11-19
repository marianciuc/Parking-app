package pl.edu.zut.app.parking.auth.services.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.zut.app.parking.auth.dto.common.Token;
import pl.edu.zut.app.parking.auth.entities.JWTUserPrincipal;

import java.time.Instant;

@Service
public class TokenAuthenticationUserDetailsService implements AuthenticationUserDetailsService<Authentication> {

    @Override
    public UserDetails loadUserDetails(Authentication authentication) throws UsernameNotFoundException {
            if (authentication.getPrincipal() instanceof Token token) {
            return new JWTUserPrincipal(
                    token.subject(),
                    "",
                    token.expiresAt().isBefore(Instant.now()),
                    true,
                    true,
                    true,
                    token.roles().stream().map(SimpleGrantedAuthority::new).toList(),
                    token,
                    token.userId()
            );
        }
        return null;
    }
}
