package com.br.projeto.exception.business;

import com.br.projeto.exception.BaseRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ConstraintException extends BaseRuntimeException {
    private final static String KEY = "constraint.rule";

    public ConstraintException(String message) {
        super(Map.of("message", message));
    }

    @Override
    public String getExceptionKey() {
        return KEY;
    }
}
