package de.throsenheim.unlimited.stockeasilyapi.controller;

import de.throsenheim.unlimited.stockeasilyapi.repository.UserProfileRepository;
import de.throsenheim.unlimited.stockeasilyapi.service.article.AuthService;
import de.throsenheim.unlimited.stockeasilyapi.user.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
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



}
