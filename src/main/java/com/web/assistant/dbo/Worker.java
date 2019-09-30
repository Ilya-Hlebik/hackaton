package com.web.assistant.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.assistant.enumerated.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "WORKER")
@Data
@EqualsAndHashCode(callSuper = true)
public class Worker extends AbstractEntity {

    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String sureName;

    @Column(nullable = false)
    private String position;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private User user;
}
