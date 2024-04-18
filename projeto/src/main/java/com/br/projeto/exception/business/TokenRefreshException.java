package com.br.projeto.exception.business;

import com.br.projeto.exception.BaseRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TokenRefreshException extends BaseRuntimeException {
    private static final String KEY = "token.expired";

    public TokenRefreshException(String message) {
        super(Map.of("message", message));
    }

    @Override
    public String getExceptionKey() {
        return KEY;
    }
}
