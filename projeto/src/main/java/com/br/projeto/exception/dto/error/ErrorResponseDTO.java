package com.br.projeto.exception.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {

    private Date timestamp;
    private Integer status;
    private String code;
    private String path;
    private List<ErrorModel> errors;

}
