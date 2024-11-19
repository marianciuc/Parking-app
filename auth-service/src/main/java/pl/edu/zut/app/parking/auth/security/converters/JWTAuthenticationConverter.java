package pl.edu.zut.app.parking.auth.security.converters;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import pl.edu.zut.app.parking.auth.dto.common.Token;
import pl.edu.zut.app.parking.auth.serializers.TokenDeserializer;

/**
 * Converter to extract and process JWT tokens from HTTP requests.
 */
public class JWTAuthenticationConverter implements AuthenticationConverter {

    private final TokenDeserializer accessTokenStringDeserializer;
    private final TokenDeserializer refreshTokenStringDeserializer;

    /**
     * Constructs a JWTAuthenticationConverter with the given deserializers.
     *
     * @param accessTokenStringDeserializer the deserializer for access tokens.
     * @param refreshTokenStringDeserializer the deserializer for refresh tokens.
     */
    public JWTAuthenticationConverter(TokenDeserializer accessTokenStringDeserializer, TokenDeserializer refreshTokenStringDeserializer) {
        this.accessTokenStringDeserializer = accessTokenStringDeserializer;
        this.refreshTokenStringDeserializer = refreshTokenStringDeserializer;
    }

    /**
     * Converts the received HTTP request to an {@link Authentication} object based on JWT token.
     *
     * @param request the HttpServletRequest object.
     * @return an Authentication object or null if conversion fails.
     */
    @Override
    public Authentication convert(HttpServletRequest request) {
        var authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            Token accessToken = this.accessTokenStringDeserializer.apply(token);
            if (accessToken != null) {
                return new PreAuthenticatedAuthenticationToken(accessToken, token);
            }

            Token refreshToken = this.refreshTokenStringDeserializer.apply(token);
            if (refreshToken != null) {
                System.out.println("refresh token found");
                return new PreAuthenticatedAuthenticationToken(refreshToken, token);
            }
        }
        return null;
    }
}
