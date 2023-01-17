package de.throsenheim.unlimited.stockeasilyapi.service.article;

import com.auth0.jwt.JWT;
import de.throsenheim.unlimited.stockeasilyapi.auth.Auth0Client;
import de.throsenheim.unlimited.stockeasilyapi.config.Auth0Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private Auth0Config auth0Config;

    public boolean authenticate(String token){
        if (token == null || !token.startsWith("Bearer ")) {
            return false;
        }
        token = token.substring(7);
        Auth0Client auth0Client = auth0Config.createAuth0Client();
        JWT jwt;
        jwt = (JWT) auth0Client.decodeToken(token);
        return jwt.equals(auth0Config.getAudience());
    }


}
