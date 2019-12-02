package com.web.assistant.controller;

import com.web.assistant.dbo.User;
import com.web.assistant.dto.request.ForgotPasswordRequestDto;
import com.web.assistant.dto.request.PasswordUpdateRequestDto;
import com.web.assistant.dto.request.SignInRequestDTO;
import com.web.assistant.dto.request.UserRequestDTO;
import com.web.assistant.dto.response.UserResponseDTO;
import com.web.assistant.service.UserService;
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
    @ApiOperation(value = "${UserController.signin}", response = UserResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 203, message = "You should change password"),
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 422, message = "Invalid username/password supplied")})
    public UserResponseDTO login(@ApiParam("UserName and Password") @RequestBody final SignInRequestDTO signInRequestDTO, final HttpServletResponse res) {
        return userService.signIn(signInRequestDTO, res);
    }

    @PostMapping("/signup")
    @ApiOperation(value = "${UserController.signup}", response = UserResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public UserResponseDTO signUp(@ApiParam("Signup User") @Valid @RequestBody final UserRequestDTO user) {
        return userService.signUp(modelMapper.map(user, User.class), true);
    }

    @PostMapping("/create")
    @ApiOperation(value = "${UserController.create}", response = UserResponseDTO.class)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public UserResponseDTO create(@ApiParam("Create User manually by admin") @RequestBody final UserRequestDTO user) {
        return userService.signUp(modelMapper.map(user, User.class), false);
    }

    @PutMapping("/update_pass")
    @ApiOperation(value = "${UserController.update_pass}", response = UserResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public UserResponseDTO updatePassword(@ApiParam("Password") @Valid @RequestBody final PasswordUpdateRequestDto user) {
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
    @ApiOperation(value = "${UserController.search}", response = UserResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The user doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public UserResponseDTO search(@ApiParam("Username") @PathVariable final String username) {
        return modelMapper.map(userService.search(username), UserResponseDTO.class);
    }

    @GetMapping(value = "/me")
    @ApiOperation(value = "${UserController.me}", response = UserResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public UserResponseDTO findMe(final HttpServletRequest req) {
        return modelMapper.map(userService.findMe(req), UserResponseDTO.class);
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
    public ResponseEntity<UserResponseDTO> updatePassword(@ApiParam("Update Password") @Valid @RequestBody final ForgotPasswordRequestDto forgotPasswordRequestDto) {
     return   userService.updatePassword(forgotPasswordRequestDto);
    }
}
