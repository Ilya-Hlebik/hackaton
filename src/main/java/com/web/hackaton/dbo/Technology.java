package com.web.hackaton.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name = "TECHNOLOGY")
@EqualsAndHashCode(callSuper = true)
public class Technology extends AbstractEntity {

    @NotNull
    @Column(name = "TECHNOLOGY_NAME")
    private String technologyName;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "WORKER_TECHNOLOGY",
            joinColumns = {@JoinColumn(name = "TECHNOLOGY_ID")},
            inverseJoinColumns = {@JoinColumn(name = "WORKER_ID")})
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Worker> workers;
}
