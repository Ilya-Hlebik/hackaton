package com.web.assistant.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.assistant.dto.AbstractRequestDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TechnologyRequestDto extends AbstractRequestDTO {

    private String technologyName;
    @JsonIgnore
    private List<WorkerRequestDTO> skills;
}
