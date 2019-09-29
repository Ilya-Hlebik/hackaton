package com.web.assistant.dbo;

import com.web.assistant.enumerated.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "USER")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity{

  @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
  @Column(unique = true, nullable = false)
  private String username;

  @Column(unique = true, nullable = false)
  private String email;

  @Size(min = 8, message = "Minimum password length: 8 characters")
  private String password;

  @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"))
  @Enumerated(EnumType.STRING)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  List<Role> roles;
}

