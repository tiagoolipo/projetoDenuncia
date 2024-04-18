package com.br.projeto.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordUpdateRequestDTO {

    @NotEmpty(message = "{required.password}")
    private String oldPassword;

    @NotEmpty(message = "{required.password}")
    private String newPassword;
}
