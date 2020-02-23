package com.web.hackathon.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ForgotPasswordRequestDto {
    @ApiModelProperty(position = 1)
    @Email(message = "Enter a valid email address.")
    private String email;
    @ApiModelProperty(position = 2)
    @NotNull(message = "password cannot be null.")
    @Size(min = 8, message = "Minimum password length: 8 characters")
    private String newPassword;
}
