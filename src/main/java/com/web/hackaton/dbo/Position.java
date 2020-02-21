package com.web.hackaton.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "POSITION")
@Data
@EqualsAndHashCode(callSuper = true)
public class Position extends AbstractEntity {

    @NotNull
    @Column(name = "POSITION_NAME")
    private String positionName;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "WORKER_POSITION",
            joinColumns = {@JoinColumn(name = "POSITION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "WORKER_ID")})
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Worker> workers;
}
