package com.picpaysimplificado.infra;

import com.picpaysimplificado.dtos.ExceptionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity threatDuplicateEntry(DataIntegrityViolationException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Usuário já cadastrado","400");
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity threat404(EntityNotFoundException exception) {
            return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AuthenticationException.class)
    public  ResponseEntity threat401(AuthenticationException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Não autorizado", "401");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionDTO);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity threat403(AccessDeniedException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), "403");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity threatGeneralException(Exception exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), "500");
        return ResponseEntity.internalServerError().body(exceptionDTO);
    }
}
