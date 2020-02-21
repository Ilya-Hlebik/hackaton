package com.web.hackaton.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.hackaton.dbo.User;
import com.web.hackaton.dto.AbstractResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkerResponseDTO extends AbstractResponseDTO {
    private String name;
    private String sureName;
    private List<PositionResponseDto> positions;
    private List<TechnologyResponseDto> technologies;
    private List<SkillResponseDto> skills;
    private String status;
    private String photo;
    @JsonIgnore
    private User user;
}
