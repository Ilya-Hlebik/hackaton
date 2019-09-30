package com.web.assistant.dto;

import com.web.assistant.dbo.Worker;
import com.web.assistant.enumerated.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserResponseDTO extends AbstractDto {

    @ApiModelProperty(position = 3)
    private List<Role> roles;
    @ApiModelProperty(position = 1)
    private String username;
    @ApiModelProperty(position = 2)
    private String email;
    private Worker worker;
}
