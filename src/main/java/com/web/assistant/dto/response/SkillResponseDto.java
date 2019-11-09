package com.web.assistant.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.assistant.dto.AbstractResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillResponseDto extends AbstractResponseDTO {
    private String skillName;
    @JsonIgnore
    private WorkerResponseDTO workerRequestDTO;
}
