package pl.edu.zut.app.parking.auth.security.utils;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import pl.edu.zut.app.parking.auth.entities.JWTUserPrincipal;

/**
 * Utility class for handling security-related operations.
 */
public class SecurityUtils {

    /**
     * Extracts the JWTUserPrincipal object from the current security context
     * @return JWTUserPrincipal object
     * @see JWTUserPrincipal
     * @throws AccessDeniedException if the user is not authenticated or the principal is not an instance of JWTUserPrincipal or the authentication is not an instance of PreAuthenticatedAuthenticationToken
     */
    public static JWTUserPrincipal extractJwtUserPrincipals() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof PreAuthenticatedAuthenticationToken && authentication.getPrincipal() instanceof JWTUserPrincipal jwtUserPrincipal) {
            return jwtUserPrincipal;
        }
        else throw new AccessDeniedException("Access denied: User is not authenticated or has invalid principal.");
    }
}
