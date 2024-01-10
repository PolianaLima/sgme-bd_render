package com.br.sgme.exceptions;


import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler({LoginInvalidoException.class, JWTVerificationException.class})
    public ResponseEntity<ErrorDetails> loginInvalidoException(LoginInvalidoException exception){
        return ResponseEntity
                .status(403)
                .body(ErrorDetails.builder()
                .message(exception.getMessage())
                .time(LocalDateTime.now())
                .codigo(HttpStatus.FORBIDDEN.value())
                .build()
        );
    }

    @ExceptionHandler({RecursoNaoEncontradoException.class})
    public ResponseEntity<ErrorDetails> detailsHandleExceptions(RecursoNaoEncontradoException exception) {
        return ResponseEntity
                .status(404)
                .body(ErrorDetails.builder()
                        .message(exception.getMessage())
                        .time(LocalDateTime.now())
                        .codigo(HttpStatus.NOT_FOUND.value())
                        .build()
                );
    }
}
