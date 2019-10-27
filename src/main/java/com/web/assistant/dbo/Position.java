package com.web.assistant.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.assistant.enumerated.PositionName;
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
    @Enumerated(EnumType.STRING)
    @Column(name = "POSITION_NAME")
    private PositionName positionName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "WORKER_POSITION",
            joinColumns = {@JoinColumn(name = "POSITION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "WORKER_ID")})
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Worker> workers;
}
