package de.throsenheim.unlimited.stockeasilyapi.controller;

import de.throsenheim.unlimited.stockeasilyapi.service.user.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"User"}) // Set correct heading
@RestController
@RequestMapping(path = "/api/v1/user", consumes = "application/json", produces = "application/json")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") UserService userService) {
        this.userService = userService;
    }


}
