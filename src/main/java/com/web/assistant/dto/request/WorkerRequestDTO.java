package com.web.assistant.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.assistant.dbo.User;
import com.web.assistant.dto.AbstractRequestDTO;
import com.web.assistant.enumerated.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerRequestDTO extends AbstractRequestDTO {
    private String name;
    private String sureName;
    private String position;
    private Status status;
    @JsonIgnore
    private User user;
}
