package com.br.projeto.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequestDTO implements Serializable {

    @NotEmpty(message = "{required.first.name}")
    private String firstName;

    @NotEmpty(message = "{required.last.name}")
    private String lastName;

    @NotEmpty(message = "{required.email}")
    @Email(message = "{required.type.email}")
    private String email;

    @NotEmpty(message = "{required.password}")
    private String password;

    @NotNull(message = "{required.roleId}")
    private Long roleId;
}
