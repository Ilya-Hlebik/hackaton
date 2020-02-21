package com.web.hackaton.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.hackaton.dto.AbstractRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillRequestDto extends AbstractRequestDTO {
    private String skillName;
    @JsonIgnore
    private WorkerRequestDTO workerRequestDTO;
}
