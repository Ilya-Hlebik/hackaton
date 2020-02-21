package com.web.hackaton.dto.request;

import com.web.hackaton.dto.AbstractRequestDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PasswordUpdateRequestDto extends AbstractRequestDTO {
    @ApiModelProperty(position = 1)
    @NotNull(message = "password cannot be null.")
    private String username;
    @ApiModelProperty(position = 2)
    @NotNull(message = "password cannot be null.")
    private String oldPassword;
    @ApiModelProperty(position = 3)
    @NotNull(message = "password cannot be null.")
    @Size(min = 8, message = "Minimum password length: 8 characters")
    private String newPasswordFirstEntry;
    @ApiModelProperty(position = 4)
    @NotNull(message = "password cannot be null.")
    @Size(min = 8, message = "Minimum password length: 8 characters")
    private String newPasswordSecondEntry;
}
