package com.web.hackaton.dto.response;

import com.web.hackaton.dbo.Worker;
import com.web.hackaton.dto.AbstractResponseDTO;
import com.web.hackaton.enumerated.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserResponseDTO extends AbstractResponseDTO {

    @ApiModelProperty(position = 3)
    private List<Role> roles;
    @ApiModelProperty(position = 1)
    private String username;
    @ApiModelProperty(position = 2)
    private String email;
    private boolean active;
    private Worker worker;
}
