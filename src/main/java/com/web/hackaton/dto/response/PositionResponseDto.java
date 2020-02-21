package com.web.hackaton.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.hackaton.dto.AbstractResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PositionResponseDto extends AbstractResponseDTO {
    private String positionName;
    @JsonIgnore
    private List<WorkerResponseDTO> workers;
}
