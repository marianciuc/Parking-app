/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: RefreshAccessJWTFilter.java
 *
 */

package pl.edu.zut.app.parking.auth.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.edu.zut.app.parking.auth.dto.common.Token;
import pl.edu.zut.app.parking.auth.dto.common.TokenPair;
import pl.edu.zut.app.parking.auth.entities.JWTUserPrincipal;
import pl.edu.zut.app.parking.auth.factories.AccessTokenFactory;
import pl.edu.zut.app.parking.auth.factories.AuthenticationTokenFactory;
import pl.edu.zut.app.parking.auth.factories.RefreshTokenFactory;
import pl.edu.zut.app.parking.auth.factories.TokenFactory;
import pl.edu.zut.app.parking.auth.serializers.TokenSerializer;

import java.io.IOException;

@Slf4j
public class RefreshAccessJWTFilter extends OncePerRequestFilter {

    private final RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/v1/users/jwt/refresh",
            HttpMethod.POST.name());

    @Setter
    private SecurityContextRepository securityContextRepository;

    private TokenFactory accessTokenFactory = new AccessTokenFactory();
    private AuthenticationTokenFactory refreshTokenFactory = new RefreshTokenFactory();
    private TokenSerializer accessTokenSerializer = Object::toString;
    private TokenSerializer refreshTokenSerializer = Object::toString;
    private Boolean includeRefreshToken = false;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("RefreshAccessJWTFilter");
        if (requestMatcher.matches(request)) {
            log.info("Refresh token request");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication instanceof PreAuthenticatedAuthenticationToken && authentication.getPrincipal() instanceof JWTUserPrincipal userPrincipal) {

                log.info("Refresh token request for user {}", userPrincipal.getUsername());
                log.info("Token: {}", userPrincipal.getToken());

                if (!userPrincipal.getToken().roles().contains("REFRESH::REFRESH_TOKEN")) {
                    log.error("Invalid token, missing required role");
                    throw new AccessDeniedException("Invalid token");
                }

                Token accessToken = accessTokenFactory.apply(userPrincipal.getToken());

                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                if (includeRefreshToken) {
                    var refreshToken = refreshTokenFactory.apply(authentication);
                    this.objectMapper.writeValue(response.getWriter(),
                            new TokenPair(accessTokenSerializer.apply(accessToken), accessToken.expiresAt().toString(), refreshTokenSerializer.apply(refreshToken), refreshToken.expiresAt().toString()));
                } else {
                    this.objectMapper.writeValue(response.getWriter(),
                            new TokenPair(accessTokenSerializer.apply(accessToken), accessToken.expiresAt().toString(), null, null));
                }
                return;
            }
            throw new AccessDeniedException("Invalid token");
        }
        filterChain.doFilter(request, response);
    }

    public RefreshAccessJWTFilter accessTokenFactory(TokenFactory accessTokenFactory) {
        this.accessTokenFactory = accessTokenFactory;
        return this;
    }

    public RefreshAccessJWTFilter refreshTokenFactory(AuthenticationTokenFactory refreshTokenFactory) {
        this.refreshTokenFactory = refreshTokenFactory;
        return this;
    }

    public RefreshAccessJWTFilter accessTokenSerializer(TokenSerializer accessTokenSerializer) {
        this.accessTokenSerializer = accessTokenSerializer;
        return this;
    }

    public RefreshAccessJWTFilter refreshTokenSerializer(TokenSerializer refreshTokenSerializer) {
        this.refreshTokenSerializer = refreshTokenSerializer;
        return this;
    }

    public RefreshAccessJWTFilter includeRefreshToken(Boolean includeRefreshToken) {
        this.includeRefreshToken = includeRefreshToken;
        return this;
    }

}
