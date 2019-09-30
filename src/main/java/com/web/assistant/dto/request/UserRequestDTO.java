package com.web.assistant.dto.request;


import com.web.assistant.dto.AbstractRequestDTO;
import com.web.assistant.enumerated.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequestDTO extends AbstractRequestDTO {

    @ApiModelProperty(position = 3)
    private List<Role> roles;
    @ApiModelProperty
    private String username;
    @ApiModelProperty(position = 1)
    private String email;
    @ApiModelProperty(position = 2)
    private String password;
}
