package com.web.assistant.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.assistant.dto.AbstractRequestDTO;
import com.web.assistant.enumerated.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class UserRequestDTO extends AbstractRequestDTO {

    @ApiModelProperty(position = 3)
    @NotNull
    private List<Role> roles;
    @ApiModelProperty
    @NotNull
    private String username;
    @ApiModelProperty(position = 1)
    @Email(message = "Enter a valid email address.")
    @NotNull
    private String email;
    @ApiModelProperty(position = 2)
    @NotNull
    @Size(min = 8, message = "Minimum password length: 8 characters")
    private String password;
    @JsonIgnore
    private boolean active;
}
