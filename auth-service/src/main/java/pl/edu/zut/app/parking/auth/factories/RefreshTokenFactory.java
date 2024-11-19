/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: RefreshTokenFactory.java
 *
 */

package pl.edu.zut.app.parking.auth.factories;


import lombok.Setter;
import org.springframework.security.core.Authentication;
import pl.edu.zut.app.parking.auth.dto.common.Token;
import pl.edu.zut.app.parking.auth.entities.UserPrincipal;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.UUID;

@Setter
public class RefreshTokenFactory implements AuthenticationTokenFactory {

    private Duration tokenTtl = Duration.ofDays(1);


    private String issuer = "streaming-service";

    /**
     * Generates a new refresh token based on the authentication object. The new token will have the same user id, subject, issuer.
     * The authorities will be mapped to add {@code "GRAND::"} prefix and add {@code "REFRESH::REFRESH_TOKEN"} role.
     *
     * @param authentication the authentication object to be used to generate the refresh token
     * @return a new refresh token
     */
    @Override
    public Token apply(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            LinkedList<String> authorities = authentication.getAuthorities().stream()
                    .map(Object::toString).map("GRAND::"::concat).collect(LinkedList::new, LinkedList::add, LinkedList::addAll);
            authorities.add("REFRESH::REFRESH_TOKEN");
            Instant now = Instant.now();

            return new Token(
                    UUID.randomUUID(),
                    userPrincipal.getId(),
                    userPrincipal.getUsername(),
                    issuer,
                    authorities,
                    now,
                    now.plus(this.tokenTtl)
            );
        }
        return null;
    }

    public RefreshTokenFactory issuer(String issuer) {
        this.issuer = issuer;
        return null;
    }

    public RefreshTokenFactory tokenTtl(Duration tokenTtl) {
        this.tokenTtl = tokenTtl;
        return null;
    }
}
