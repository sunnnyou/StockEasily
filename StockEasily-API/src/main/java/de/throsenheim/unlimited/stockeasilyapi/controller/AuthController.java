package de.throsenheim.unlimited.stockeasilyapi.controller;

import de.throsenheim.unlimited.stockeasilyapi.auth.Auth0Client;
import de.throsenheim.unlimited.stockeasilyapi.repository.UserProfileRepository;
import de.throsenheim.unlimited.stockeasilyapi.service.article.AuthService;
import de.throsenheim.unlimited.stockeasilyapi.user.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserProfileRepository userProfileRepository;

    @RolesAllowed({"read:data"})
    @GetMapping("/data")
    public ResponseEntity<UserProfile> getData(@RequestHeader("Authorization") String token){
        if(!authService.authenticate(token)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UserProfile userProfile = userProfileRepository.getUserProfile();
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    @Value("${auth0.domain}")
    private String domain;

    @Value("${auth0.clientId}")
    private String clientId;

    @Value("${auth0.clientSecret}")
    private String clientSecret;

    @PostMapping("/register")
    public void register(@RequestBody UserProfile user) {
        Auth0Client auth0Client = new Auth0Client(domain, clientId, clientSecret);
        auth0Client.createUser(user.getEmail(), user.getPassword());
    }



}
