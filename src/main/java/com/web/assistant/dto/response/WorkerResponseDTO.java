package com.web.assistant.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.assistant.dbo.User;
import com.web.assistant.dto.AbstractResponseDTO;
import com.web.assistant.enumerated.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerResponseDTO extends AbstractResponseDTO {
    private String name;
    private String sureName;
    private String position;
    private Status status;
    private String photo;
    @JsonIgnore
    private User user;
}
