package com.web.assistant.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AbstractResponseDTO {
    @ApiModelProperty
    private Long id;
}
