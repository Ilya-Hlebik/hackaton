package com.web.hackaton.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.hackaton.dto.AbstractRequestDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PositionRequestDto extends AbstractRequestDTO {
    private String positionName;
    @JsonIgnore
    private List<WorkerRequestDTO> workers;
}
