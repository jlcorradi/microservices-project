package br.com.jlcorradi.orders.web;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class BadRequestErrorHandling {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> result = ex.getBindingResult().getAllErrors().stream()
                .collect(Collectors.toMap(
                        err -> err instanceof FieldError ? ((FieldError) err).getField() : err.getObjectName(),
                        DefaultMessageSourceResolvable::getDefaultMessage)
                );

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
