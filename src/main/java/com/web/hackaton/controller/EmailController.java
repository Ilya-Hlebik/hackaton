package com.web.hackaton.controller;

import com.web.hackaton.dto.request.EmailConfirmationDto;
import com.web.hackaton.dto.response.PositionResponseDto;
import com.web.hackaton.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
@RequestMapping("/mail")
@Api(tags = "Forgot password")
@AllArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/forgot_password")
    @ApiOperation(value = "${EmailController.sendVerifyCodeByEmail}", response = PositionResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The user with this email doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public ResponseEntity sendVerifyCodeByEmail(@RequestParam @Valid @Email(message = "Enter a valid email address.") final String email) {
        return emailService.sendVerifyCodeByEmail(email);
    }

    @PostMapping("/verify_code")
    @ApiOperation(value = "${EmailController.verifyCode}", response = PositionResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The user doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public boolean verifyCode(@RequestBody @Valid final EmailConfirmationDto emailConfirmationDto) {
        return emailService.verifyCode(emailConfirmationDto);
    }
}
