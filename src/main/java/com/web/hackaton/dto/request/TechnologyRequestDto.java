package com.web.hackaton.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.hackaton.dto.AbstractRequestDTO;
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
