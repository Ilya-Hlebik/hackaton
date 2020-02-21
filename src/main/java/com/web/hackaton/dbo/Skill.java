package com.web.hackaton.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "SKILL")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Skill extends AbstractEntity {
    @NotNull
    @Column(name = "SKILL_NAME", length = 500)
    private String skillName;

    @ManyToOne
    @JoinColumn(name = "WORKER_ID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Worker worker;

    public Skill(@NotNull final String skillName, final Worker worker) {
        this.skillName = skillName;
        this.worker = worker;
    }
}
