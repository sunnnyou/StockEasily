package de.throsenheim.unlimited.stockeasilyapi.user;

import com.auth0.jwt.interfaces.DecodedJWT;

public class UserProfile {
    private String name;
    private String email;
    private boolean authenticated;
    private String password;
    private DecodedJWT jwt;

    public UserProfile() {
    }

    public UserProfile(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.authenticated = false;
    }

    public UserProfile(DecodedJWT jwt) {
        this.name = jwt.getClaim("name").asString();
        this.email = jwt.getClaim("email").asString();
        this.authenticated = true;
        this.jwt = jwt;
    }

    public DecodedJWT getJwt() {
        return jwt;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public boolean authenticate(String attemptedPassword) {
        if(attemptedPassword.equals(password)) {
            authenticated = true;
            return true;
        } else {
            authenticated = false;
            return false;
        }
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
