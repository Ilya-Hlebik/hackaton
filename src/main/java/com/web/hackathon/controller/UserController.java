package com.web.hackathon.controller;

import com.web.hackathon.dbo.User;
import com.web.hackathon.dto.ForgotPasswordRequestDto;
import com.web.hackathon.dto.PasswordUpdateRequestDto;
import com.web.hackathon.dto.SignInRequestDTO;
import com.web.hackathon.service.UserService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Api(tags = "users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/signin")
    @ApiOperation(value = "${UserController.signin}", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 203, message = "You should change password"),
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 422, message = "Invalid username/password supplied")})
    public User login(@ApiParam("UserName and Password") @RequestBody final SignInRequestDTO signInRequestDTO, final HttpServletResponse res) {
        return userService.signIn(signInRequestDTO, res);
    }

    @PostMapping("/signup")
    @ApiOperation(value = "${UserController.signup}", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public User signUp(@ApiParam("Signup User") @Valid @RequestBody final User user) {
        return userService.signUp(user, true);
    }

    @PostMapping("/create")
    @ApiOperation(value = "${UserController.create}", response = User.class)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public User create(@ApiParam("Create User manually by admin") @RequestBody final User user) {
        return userService.signUp(user, false);
    }

    @PutMapping("/update_pass")
    @ApiOperation(value = "${UserController.update_pass}", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public User updatePassword(@ApiParam("Password") @Valid @RequestBody final PasswordUpdateRequestDto user) {
        return userService.updatePassword(user);
    }

    @DeleteMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${UserController.delete}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The user doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public String delete(@ApiParam("Username") @PathVariable final String username) {
        userService.delete(username);
        return username;
    }

    @GetMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${UserController.search}", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The user doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public User search(@ApiParam("Username") @PathVariable final String username) {
        return userService.search(username);
    }

    @GetMapping(value = "/me")
    @ApiOperation(value = "${UserController.me}", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public User findMe(final HttpServletRequest req) {
        return userService.findMe(req);
    }

    @GetMapping(value = "/logout")
    @ApiOperation(value = "${UserController.logout}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public void logOut(final HttpServletRequest req) {
        userService.logOut(req);
    }

    @PutMapping(value = "/update_pass/without_previous")
    @ApiOperation(value = "${UserController.update_pass_without_previous}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "The user with this email doesn't exist oor you didn't activate account"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public ResponseEntity<User> updatePassword(@ApiParam("Update Password") @Valid @RequestBody final ForgotPasswordRequestDto forgotPasswordRequestDto) {
        return userService.updatePassword(forgotPasswordRequestDto);
    }
}
