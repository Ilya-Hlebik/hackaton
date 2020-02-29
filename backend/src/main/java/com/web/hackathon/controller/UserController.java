package com.web.hackathon.controller;

import com.web.hackathon.dbo.User;
import com.web.hackathon.dto.ForgotPasswordRequestDto;
import com.web.hackathon.dto.PasswordUpdateRequestDto;
import com.web.hackathon.dto.SignInRequestDTO;
import com.web.hackathon.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Api(tags = "users")
@AllArgsConstructor
public class UserController {

    public static final String ACCESS_DENIED_RESPONSE_MESSAGE = "Access denied";
    public static final String USERNAME_IS_ALREADY_IN_USE_RESPONSE_MESSAGE = "Username is already in use";
    public static final String EXPIRED_OR_INVALID_JWT_TOKEN_MESSAGE = "Expired or invalid JWT token";
    public static final String SOMETHING_WENT_WRONG_RESPONSE_MESSAGE = "Something went wrong";
    public static final String YOU_SHOULD_CHANGE_PASSWORD_RESPONSE_MESSAGE = "You should change password";
    public static final String INVALID_USERNAME_PASSWORD_SUPPLIED_RESPONSE_MESSAGE =
        "Invalid username/password supplied";
    private final UserService userService;

    @PostMapping("/signin")
    @ApiOperation(value = "${UserController.signin}", response = User.class)
    @ApiResponses(value = {
        @ApiResponse(code = 203, message = YOU_SHOULD_CHANGE_PASSWORD_RESPONSE_MESSAGE),
        @ApiResponse(code = 400, message = SOMETHING_WENT_WRONG_RESPONSE_MESSAGE),
        @ApiResponse(code = 422, message = INVALID_USERNAME_PASSWORD_SUPPLIED_RESPONSE_MESSAGE)})
    public User login(@ApiParam("UserName and Password") @RequestBody final SignInRequestDTO signInRequestDTO,
                      final HttpServletResponse res) {
        return userService.signIn(signInRequestDTO, res);
    }

    @PostMapping("/signup")
    @ApiOperation(value = "${UserController.signup}", response = User.class)
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = SOMETHING_WENT_WRONG_RESPONSE_MESSAGE),
        @ApiResponse(code = 403, message = ACCESS_DENIED_RESPONSE_MESSAGE),
        @ApiResponse(code = 422, message = USERNAME_IS_ALREADY_IN_USE_RESPONSE_MESSAGE),
        @ApiResponse(code = 500, message = EXPIRED_OR_INVALID_JWT_TOKEN_MESSAGE)})
    public User signUp(@ApiParam("Signup User") @Valid @RequestBody final User user) {
        return userService.signUp(user, true);
    }

    @PostMapping("/create")
    @ApiOperation(value = "${UserController.create}", response = User.class)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = SOMETHING_WENT_WRONG_RESPONSE_MESSAGE),
        @ApiResponse(code = 403, message = ACCESS_DENIED_RESPONSE_MESSAGE),
        @ApiResponse(code = 422, message = USERNAME_IS_ALREADY_IN_USE_RESPONSE_MESSAGE),
        @ApiResponse(code = 500, message = EXPIRED_OR_INVALID_JWT_TOKEN_MESSAGE)})
    public User create(@ApiParam("Create User manually by admin") @RequestBody final User user) {
        return userService.signUp(user, false);
    }

    @PutMapping("/update_pass")
    @ApiOperation(value = "${UserController.update_pass}", response = User.class)
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = SOMETHING_WENT_WRONG_RESPONSE_MESSAGE),
        @ApiResponse(code = 403, message = ACCESS_DENIED_RESPONSE_MESSAGE),
        @ApiResponse(code = 422, message = USERNAME_IS_ALREADY_IN_USE_RESPONSE_MESSAGE),
        @ApiResponse(code = 500, message = EXPIRED_OR_INVALID_JWT_TOKEN_MESSAGE)})
    public User updatePassword(@ApiParam("Password") @Valid @RequestBody final PasswordUpdateRequestDto user) {
        return userService.updatePassword(user);
    }

    @DeleteMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${UserController.delete}")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = SOMETHING_WENT_WRONG_RESPONSE_MESSAGE),
        @ApiResponse(code = 403, message = ACCESS_DENIED_RESPONSE_MESSAGE),
        @ApiResponse(code = 404, message = "The user doesn't exist"),
        @ApiResponse(code = 500, message = EXPIRED_OR_INVALID_JWT_TOKEN_MESSAGE)})
    public String delete(@ApiParam("Username") @PathVariable final String username) {
        userService.delete(username);
        return username;
    }

    @GetMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${UserController.search}", response = User.class)
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = SOMETHING_WENT_WRONG_RESPONSE_MESSAGE),
        @ApiResponse(code = 403, message = ACCESS_DENIED_RESPONSE_MESSAGE),
        @ApiResponse(code = 404, message = "The user doesn't exist"),
        @ApiResponse(code = 500, message = EXPIRED_OR_INVALID_JWT_TOKEN_MESSAGE)})
    public User search(@ApiParam("Username") @PathVariable final String username) {
        return userService.search(username);
    }

    @GetMapping(value = "/me")
    @ApiOperation(value = "${UserController.me}", response = User.class)
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = SOMETHING_WENT_WRONG_RESPONSE_MESSAGE),
        @ApiResponse(code = 403, message = ACCESS_DENIED_RESPONSE_MESSAGE),
        @ApiResponse(code = 500, message = EXPIRED_OR_INVALID_JWT_TOKEN_MESSAGE)})
    public User findMe(final HttpServletRequest req) {
        return userService.findMe(req);
    }

    @GetMapping(value = "/logout")
    @ApiOperation(value = "${UserController.logout}")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = SOMETHING_WENT_WRONG_RESPONSE_MESSAGE),
        @ApiResponse(code = 403, message = ACCESS_DENIED_RESPONSE_MESSAGE),
        @ApiResponse(code = 500, message = EXPIRED_OR_INVALID_JWT_TOKEN_MESSAGE)})
    public void logOut(final HttpServletRequest req) {
        userService.logOut(req);
    }

    @PutMapping(value = "/update_pass/without_previous")
    @ApiOperation(value = "${UserController.update_pass_without_previous}")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "The user with this email doesn't exist oor you didn't activate account"),
        @ApiResponse(code = 403, message = ACCESS_DENIED_RESPONSE_MESSAGE),
        @ApiResponse(code = 500, message = EXPIRED_OR_INVALID_JWT_TOKEN_MESSAGE)})
    public ResponseEntity<User> updatePassword(@ApiParam(
        "Update Password") @Valid @RequestBody final ForgotPasswordRequestDto forgotPasswordRequestDto) {
        return userService.updatePassword(forgotPasswordRequestDto);
    }
}
