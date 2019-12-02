package com.web.assistant.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EmailConfirmationDto {
    @Email(message = "Enter a valid email address.")
    @NotNull
    private String email;
    @Range(min = 1111, max = 9999)
    private int code;
}
