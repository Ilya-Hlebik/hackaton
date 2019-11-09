package com.web.assistant.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.assistant.dto.AbstractResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TechnologyResponseDto extends AbstractResponseDTO {
    private String technologyName;
    @JsonIgnore
    private List<WorkerResponseDTO> workers;
}
