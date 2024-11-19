package pl.edu.zut.app.parking.auth.dto.common;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Record representing a Token in the authentication system.
 *
 * @param tokenId   the unique identifier of the token
 * @param userId    the unique identifier of the user associated with the token
 * @param subject   the subject of the token
 * @param issuer    the issuer of the token
 * @param roles     the roles associated with the token
 * @param createdAt the timestamp when the token was created
 * @param expiresAt the timestamp when the token expires
 */
public record Token(
        UUID tokenId,
        UUID userId,
        String subject,
        String issuer,
        List<String> roles,
        Instant createdAt,
        Instant expiresAt
) {
}
