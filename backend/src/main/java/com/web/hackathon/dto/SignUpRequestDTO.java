package com.web.hackathon.dto;


import com.web.hackathon.enumerated.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignUpRequestDTO {
    private List<Role> roles;
    private String username;
    private String email;
    private String password;
    private boolean active;
}
