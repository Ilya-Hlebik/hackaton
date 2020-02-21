package com.web.hackaton.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.hackaton.dbo.User;
import com.web.hackaton.dto.AbstractRequestDTO;
import com.web.hackaton.enumerated.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkerRequestDTO extends AbstractRequestDTO {
    private String name;
    private String sureName;
    private List<PositionRequestDto> position;
    private List<TechnologyRequestDto> technologies;
    private List<SkillRequestDto> skills;
    private Status status;
    private String photo;
    @JsonIgnore
    private User user;
}
