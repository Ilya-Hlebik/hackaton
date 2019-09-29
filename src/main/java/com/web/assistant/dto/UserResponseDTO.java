package com.web.assistant.dto;

import com.web.assistant.dbo.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserResponseDTO {

    @ApiModelProperty(position = 3)
    private List<Role> roles;
    @ApiModelProperty
    private Integer id;
    @ApiModelProperty(position = 1)
    private String username;
    @ApiModelProperty(position = 2)
    private String email;

}
