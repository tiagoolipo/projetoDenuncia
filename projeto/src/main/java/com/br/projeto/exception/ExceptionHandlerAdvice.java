package com.br.projeto.exception;

import com.br.projeto.exception.contract.MessageException;
import com.br.projeto.exception.dto.error.ApiErrorResponseDTO;
import com.br.projeto.exception.dto.error.ErrorDTO;
import com.br.projeto.exception.dto.error.ErrorModel;
import com.br.projeto.exception.dto.error.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    private static final String UNKNOWN_ERROR_KEY = "unknown.error";

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
    private final MessageSource messageSource;

    public ExceptionHandlerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponseDTO> handlerAuthenticationException(AuthenticationException exception, HttpServletRequest request) {
        Set<ErrorDTO> errors = new HashSet<>();
        errors.add(new ErrorDTO("UNAUTHORIZED", exception.getMessage()));

        var authError = new ApiErrorResponseDTO(new Date(), HttpServletResponse.SC_UNAUTHORIZED, HttpStatus.UNAUTHORIZED.toString(), request.getRequestURI(), errors);

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(authError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handlerMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpServletRequest request) {
        List<ErrorModel> errorMessages = exception.getBindingResult().getFieldErrors().stream()
                .map(err -> new ErrorModel(err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                .distinct()
                .collect(Collectors.toList());

        ErrorResponseDTO errorResponseDTO = baseErrorBuilder(HttpStatus.BAD_REQUEST, errorMessages, request);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponseDTO);
    }

    @ExceptionHandler(BaseRuntimeException.class)
    public ResponseEntity<ApiErrorResponseDTO> handlerBaseException(Throwable exception, HttpServletRequest request) {
        logger.error("Exception {}", exception.getClass().getName());
        MessageException messageException = (MessageException) exception;
        ErrorDTO error = buildError(messageException.getExceptionKey(), bindExceptionKeywords(messageException.getMapDetails(), messageException.getExceptionKey()));
        Set<ErrorDTO> errors = Set.of(error);
        ApiErrorResponseDTO apiErrorDTO = baseErrorBuilder(getResponseStatus(exception), errors, request);

        return ResponseEntity
                .status(getResponseStatus(exception))
                .body(apiErrorDTO);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiErrorResponseDTO> handlerMethodThrowable(Throwable exception, HttpServletRequest request) {
        logger.error("Exception {}, Message: {}", exception.getClass().getName(), exception.getMessage());
        Set<ErrorDTO> errors = Set.of(buildError(UNKNOWN_ERROR_KEY, exception.getMessage()));

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(baseErrorBuilder(HttpStatus.INTERNAL_SERVER_ERROR, errors, request));
    }

    private ErrorDTO buildError(String code, String message) {
        return new ErrorDTO(code, message);
    }

    private ApiErrorResponseDTO baseErrorBuilder(HttpStatus httpStatus, Set<ErrorDTO> errorList, HttpServletRequest request) {
        return new ApiErrorResponseDTO(new Date(), httpStatus.value(), httpStatus.name(), request.getRequestURI(), errorList);
    }

    private ErrorResponseDTO baseErrorBuilder(HttpStatus httpStatus, List<ErrorModel> errorList, HttpServletRequest request) {
        return new ErrorResponseDTO(new Date(), httpStatus.value(), httpStatus.name(), request.getRequestURI(), errorList);
    }

    private String bindExceptionKeywords(Map<String, Object> keywords, String exceptionKey) {
        String message = messageSource.getMessage(exceptionKey, null, LocaleContextHolder.getLocale());
        return Objects.nonNull(keywords) ? new StrSubstitutor(keywords).replace(message) : message;
    }

    private HttpStatus getResponseStatus(Throwable exception) {
        ResponseStatus responseStatus = exception.getClass().getAnnotation(ResponseStatus.class);
        if (exception.getClass().getAnnotation(ResponseStatus.class) == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return responseStatus.value();
    }
}
