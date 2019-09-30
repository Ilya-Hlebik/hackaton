package com.web.assistant.dto;

import com.web.assistant.dbo.User;
import com.web.assistant.enumerated.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerDTO extends AbstractDto {
    private String name;
    private String sureName;
    private String position;
    private Status status;
    private User user;
}
