package de.throsenheim.unlimited.stockeasilyapi.user;

import com.auth0.jwt.interfaces.DecodedJWT;

public class UserProfile {
    private String name;
    private String email;
    private boolean authenticated;
    private String password;
    private String connection;
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

    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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

    public void setConnection(String connection) {
        this.connection = connection;
    }
}
