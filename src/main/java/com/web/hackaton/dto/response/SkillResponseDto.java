package com.web.hackaton.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.hackaton.dto.AbstractResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillResponseDto extends AbstractResponseDTO {
    private String skillName;
    @JsonIgnore
    private WorkerResponseDTO workerRequestDTO;
}
