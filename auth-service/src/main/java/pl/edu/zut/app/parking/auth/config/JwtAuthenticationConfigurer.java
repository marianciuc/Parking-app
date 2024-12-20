package pl.edu.zut.app.parking.auth.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.edu.zut.app.parking.auth.factories.AuthenticationTokenFactory;
import pl.edu.zut.app.parking.auth.factories.TokenFactory;
import pl.edu.zut.app.parking.auth.filters.JWTAuthenticationFilter;
import pl.edu.zut.app.parking.auth.filters.RefreshAccessJWTFilter;
import pl.edu.zut.app.parking.auth.filters.RequestPublicKeyFilter;
import pl.edu.zut.app.parking.auth.filters.UsernamePasswordAuthenticationFilter;
import pl.edu.zut.app.parking.auth.security.converters.JWTAuthenticationConverter;
import pl.edu.zut.app.parking.auth.security.converters.UsernamePasswordAuthenticationConverter;
import pl.edu.zut.app.parking.auth.security.providers.CredentialsAuthenticationProvider;
import pl.edu.zut.app.parking.auth.security.providers.JWTAuthenticationProvider;
import pl.edu.zut.app.parking.auth.security.utils.ProviderManager;
import pl.edu.zut.app.parking.auth.serializers.TokenDeserializer;
import pl.edu.zut.app.parking.auth.serializers.TokenSerializer;

import java.util.Arrays;


/**
 * Configurer to set up JWT and Username/Password authentication in the security filter chain.
 */
public class JwtAuthenticationConfigurer implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> {

    private static final String API_TOKENS_PATH = "/api/v1/tokens";
    private static final String POST_METHOD = "POST";


    private TokenSerializer refreshTokenSerializer = Object::toString;

    private TokenSerializer accessTokenSerializer = Object::toString;

    private TokenDeserializer accessTokenStringDeserializer;
    private TokenDeserializer refreshTokenStringDeserializer;
    private AuthenticationTokenFactory refreshTokenFactory;

    private TokenFactory accessTokenFactory;
    private UserDetailsService credentialsUserDetailsService;
    private AuthenticationUserDetailsService<Authentication> jwtUserDetailsService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationProvider credentialsAuthenticationProvider;

    private AuthenticationProvider jwtAuthenticationProvider;
    private AuthenticationConverter jwtAuthenticationConverter;
    private AuthenticationConverter usernamePasswordAuthenticationConverter;

    private String publicKey;

    @Override
    public void init(HttpSecurity builder) {
        var csrfConfigurer = builder.getConfigurer(CsrfConfigurer.class);
        if (csrfConfigurer != null) {
            csrfConfigurer.ignoringRequestMatchers(new AntPathRequestMatcher("/register/*", POST_METHOD));
        }
    }

    @Override
    public void configure(HttpSecurity builder) {
        initializeProvidersAndConverters();

        AuthenticationManager authManager = new ProviderManager(Arrays.asList(this.credentialsAuthenticationProvider, this.jwtAuthenticationProvider));

        var usernamePasswordAuthenticationFilter = this.createUsernamePasswordAuthenticationFilter(authManager);
        var jwtAuthenticationFilter = new JWTAuthenticationFilter(this.jwtAuthenticationConverter, authManager);
        var refreshTokenFilter = new RefreshAccessJWTFilter();
        var publicKeysFilter = new RequestPublicKeyFilter(publicKey);

        SecurityContextRepository securityContextRepository = builder.getSharedObject(SecurityContextRepository.class);
        refreshTokenFilter.setSecurityContextRepository(securityContextRepository);
        refreshTokenFilter.accessTokenFactory(this.accessTokenFactory);
        refreshTokenFilter.refreshTokenFactory(this.refreshTokenFactory);
        refreshTokenFilter.accessTokenSerializer(this.accessTokenSerializer);
        refreshTokenFilter.refreshTokenSerializer(this.refreshTokenSerializer);
        refreshTokenFilter.includeRefreshToken(true);


        builder
                .addFilterBefore(usernamePasswordAuthenticationFilter, CsrfFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, usernamePasswordAuthenticationFilter.getClass())
                .addFilterBefore(publicKeysFilter, usernamePasswordAuthenticationFilter.getClass())
                .addFilterAfter(refreshTokenFilter, jwtAuthenticationFilter.getClass());
    }

    private void initializeProvidersAndConverters() {
        this.credentialsAuthenticationProvider = new CredentialsAuthenticationProvider(this.credentialsUserDetailsService, this.passwordEncoder);
        this.jwtAuthenticationProvider = new JWTAuthenticationProvider(this.jwtUserDetailsService);
        this.jwtAuthenticationConverter = new JWTAuthenticationConverter(this.accessTokenStringDeserializer, this.refreshTokenStringDeserializer);
        this.usernamePasswordAuthenticationConverter = new UsernamePasswordAuthenticationConverter();
    }

    private UsernamePasswordAuthenticationFilter createUsernamePasswordAuthenticationFilter(AuthenticationManager authManager) {
        return new UsernamePasswordAuthenticationFilter(
                this.usernamePasswordAuthenticationConverter,
                this.accessTokenFactory,
                this.refreshTokenFactory,
                this.accessTokenSerializer,
                this.refreshTokenSerializer,
                authManager
        );
    }

    public JwtAuthenticationConfigurer passwordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }

    public JwtAuthenticationConfigurer jwtUserDetailsService(AuthenticationUserDetailsService<Authentication> authenticationUserDetailsService) {
        this.jwtUserDetailsService = authenticationUserDetailsService;
        return this;
    }

    public JwtAuthenticationConfigurer credentialsUserDetailsService(UserDetailsService credentialsUserDetailsService) {
        this.credentialsUserDetailsService = credentialsUserDetailsService;
        return this;
    }

    public JwtAuthenticationConfigurer accessTokenFactory(TokenFactory accessTokenFactory) {
        this.accessTokenFactory = accessTokenFactory;
        return this;
    }

    public JwtAuthenticationConfigurer refreshTokenFactory(AuthenticationTokenFactory refreshTokenFactory) {
        this.refreshTokenFactory = refreshTokenFactory;
        return this;
    }

    public JwtAuthenticationConfigurer accessTokenStringDeserializer(TokenDeserializer accessTokenStringDeserializer) {
        this.accessTokenStringDeserializer = accessTokenStringDeserializer;
        return this;
    }

    public JwtAuthenticationConfigurer refreshTokenStringDeserializer(TokenDeserializer refreshTokenStringDeserializer) {
        this.refreshTokenStringDeserializer = refreshTokenStringDeserializer;
        return this;
    }

    public JwtAuthenticationConfigurer accessTokenSerializer(TokenSerializer accessTokenSerializer) {
        this.accessTokenSerializer = accessTokenSerializer;
        return this;
    }

    public JwtAuthenticationConfigurer refreshTokenSerializer(TokenSerializer refreshTokenSerializer) {
        this.refreshTokenSerializer = refreshTokenSerializer;
        return this;
    }

    public JwtAuthenticationConfigurer publicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }
}
