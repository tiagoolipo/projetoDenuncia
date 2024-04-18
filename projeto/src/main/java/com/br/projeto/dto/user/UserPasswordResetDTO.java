package com.br.projeto.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordResetDTO {

    @NotEmpty(message = "{required.email}")
    @Email(message = "{required.type.email}")
    private String email;
}
