package com.br.projeto.exception.dto.error;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class ApiErrorResponseDTO {

    private Date timestamp;
    private Integer status;
    private String code;
    private String path;
    private Set<ErrorDTO> errors;

    public ApiErrorResponseDTO(){}

    public ApiErrorResponseDTO(Date timestamp, Integer status, String code, String path, Set<ErrorDTO> errors){
        this.timestamp = timestamp;
        this.status = status;
        this.code = code;
        this.path = path;
        this.errors = errors;
    }


}
