package pl.edu.zut.app.parking.auth.security.providers;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.edu.zut.app.parking.auth.entities.UserPrincipal;

/**
 * Implementation of {@link AuthenticationProvider} that validates username and password credentials.
 */
public class CredentialsAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a CredentialsAuthenticationProvider with the provided {@link UserDetailsService} and {@link PasswordEncoder}.
     *
     * @param userDetailsService the service to load user details.
     * @param passwordEncoder the encoder to validate passwords.
     */
    public CredentialsAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Attempts to authenticate the provided {@link Authentication} object.
     *
     * @param authentication the authentication request object.
     * @return a fully authenticated object including credentials.
     * @throws AuthenticationException if authentication fails.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserPrincipal userDetails = (UserPrincipal) userDetailsService.loadUserByUsername(username);
        if (userDetails != null && passwordEncoder.matches(password, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    userDetails, password, userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
