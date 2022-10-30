package com.project.pixchallenge.web.exception;

import com.project.pixchallenge.core.exception.KeyValidatorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleIdempotencyException(MethodArgumentNotValidException e) {
        var fieldErrors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldErrorDTO::from)
                .collect(toList());

        var response = ErrorDTO.from(UNPROCESSABLE_ENTITY, "Invalid Arguments", fieldErrors);

        log.error("error_handleMethodArgumentNotValidException");

        return ResponseEntity
                .status(UNPROCESSABLE_ENTITY)
                .body(response);
    }

    @ExceptionHandler({KeyValidatorException.class})
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public ErrorDTO handleKeyValidatorException(final KeyValidatorException e) {
        var errorDTO = ErrorDTO.from(UNPROCESSABLE_ENTITY, e.getMessage());

        log.error("error_handleKeyValidatorException");

        log.error(e.getMessage());

        return errorDTO;
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public ErrorDTO httpMessageNotReadableException(final HttpMessageNotReadableException e) {
        var errorDTO = ErrorDTO.from(UNPROCESSABLE_ENTITY, e.getMostSpecificCause().getMessage());

        log.error("error_httpMessageNotReadableException");

        log.error(e.getMessage());

        return errorDTO;
    }

}
