package com.web.hackaton.dto.request;

import com.web.hackaton.dto.AbstractRequestDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequestDTO extends AbstractRequestDTO {
    @ApiModelProperty(position = 1)
    private String username;
    @ApiModelProperty(position = 2)
    private String password;
}
