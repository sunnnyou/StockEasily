package de.throsenheim.unlimited.stockeasilyapi.controller;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.UserRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.ApiErrorDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.UserResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.exception.InvalidBodyException;
import de.throsenheim.unlimited.stockeasilyapi.model.User;
import de.throsenheim.unlimited.stockeasilyapi.service.user.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"User"}) // Set correct heading
@RestController
@RequestMapping(path = "/api/v1/user", consumes = "application/json", produces = "application/json")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Edit user", response = UserResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User edited", response = UserResponseDto.class),
            @ApiResponse(code = 400, message = "Parameter validation error", response = ApiErrorDto.class),
            @ApiResponse(code = 500, message = "Entity serialization error", response = ApiErrorDto.class)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "/{userId}")
    public ResponseEntity<UserResponseDto> editUser(
            @PathVariable("userId") long id,
            @ApiParam(name = "request") @Valid @RequestBody UserRequestDto request,
            BindingResult bindingResult) {
        User user = userService.find(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            if (bindingResult.hasErrors()) {
                throw new InvalidBodyException(bindingResult);
            }
            final User result = this.userService.edit(request, id);
            // INTERNAL SERVER ERROR should NOT occur
            final HttpStatus httpStatus = result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK;
            return new ResponseEntity<>(new UserResponseDto(result), httpStatus);
        }
    }

}
