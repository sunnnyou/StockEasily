package de.throsenheim.unlimited.stockeasilyapi.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import de.throsenheim.unlimited.stockeasilyapi.auth.Auth0Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Auth0Config {

    @Value("${auth0.domain}")
    private String domain;

    @Value("${auth0.clientId}")
    private String clientId;

    @Value("${auth0.clientSecret}")
    private String clientSecret;

    @Value("${auth0.audience}")
    private String audience;

    public Auth0Client createAuth0Client() {
        return new Auth0Client(domain, clientId, clientSecret);
    }

    public String getAudience() {
        return audience;
    }

}
