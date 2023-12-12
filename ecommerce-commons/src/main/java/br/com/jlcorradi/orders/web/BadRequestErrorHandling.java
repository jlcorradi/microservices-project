package br.com.jlcorradi.orders.web;

import br.com.jlcorradi.commons.exception.UnauthorizedTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class BadRequestErrorHandling {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> result = ex.getBindingResult().getAllErrors().stream()
                .collect(Collectors.toMap(
                        err -> err instanceof FieldError ? ((FieldError) err).getField() : err.getObjectName(),
                        objectError -> Optional.ofNullable(objectError.getDefaultMessage())
                                .orElse("No message provided"))
                );

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedTokenException.class)
    public void handleUnauthorizedException(UnauthorizedTokenException ex) {
        log.debug("Unauthorized: {}", ex);
    }

}
