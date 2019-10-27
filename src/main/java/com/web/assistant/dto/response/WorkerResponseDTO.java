package com.web.assistant.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.assistant.dbo.User;
import com.web.assistant.dto.AbstractResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkerResponseDTO extends AbstractResponseDTO {
    private String name;
    private String sureName;
    private List<String> positions;
    private String status;
    private String photo;
    @JsonIgnore
    private User user;
}
