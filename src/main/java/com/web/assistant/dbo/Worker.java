package com.web.assistant.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.assistant.enumerated.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "WORKER_POSITION",
            joinColumns = {@JoinColumn(name = "WORKER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "POSITION_ID")})

    @ToString.Exclude
    private List<Position> positions;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column
    private String photo;

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "USER_ID", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private User user;
}
