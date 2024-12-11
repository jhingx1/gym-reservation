package com.mitocode.reservation.adapter.in.rest.common;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class ClientErrorHandler {

    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<ErrorEntity> handleClassNotFoundException(ClientErrorException ex) {
        return ex.getResponse();
    }

    //Excepciones de seguridad
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorEntity> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorEntity errorEntity = new ErrorEntity(HttpStatus.FORBIDDEN.value(),
                "You do not have permission to access the requested resource");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorEntity);
    }

}