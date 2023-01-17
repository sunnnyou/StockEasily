package de.throsenheim.unlimited.stockeasilyapi.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import de.throsenheim.unlimited.stockeasilyapi.user.UserProfile;

public class Auth0Client {
    private String clientId;
    private String clientSecret;
    private String domain;


    public Auth0Client(String clientId, String clientSecret, String domain) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.domain = domain;
    }

    public UserProfile authenticate (String token) {
        Algorithm algorithm = Algorithm.HMAC256(clientSecret);
        JWTVerifier verifier = com.auth0.jwt.JWT.require(algorithm)
                .withIssuer("https://" + domain + "/")
                .withAudience(clientId)
                .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);
        return new UserProfile(jwt);
    }

    public DecodedJWT decodeToken(String token) {
        return JWT.decode(token);
    }

        public void createUser(String email, String password) {
                UserProfile user = new UserProfile();
                user.setEmail(email);
                user.setPassword(password);

        }

    }

