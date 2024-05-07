package com.br.projeto.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO implements Serializable {

    private String firstName;

    private String lastName;

    private String office;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
