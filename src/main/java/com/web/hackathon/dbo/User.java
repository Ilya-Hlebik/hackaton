package com.web.hackathon.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.hackathon.enumerated.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "USER")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity {

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"))
    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    List<Role> roles;
    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @NotNull
    @JsonIgnore
    private String password;
    @Column(nullable = false, name = "ACTIVE")
    private boolean active;
}

