package com.migs.mycontacts.exception;

import com.migs.mycontacts.dto.ExceptionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ExceptionDTO> handleConflict(RegraNegocioException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionDTO(
            HttpStatus.CONFLICT.value(),
            "Regra de Negócio",
            e.getMessage(),
            null,
            LocalDateTime.now().toString()
        ));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDTO(
            HttpStatus.NOT_FOUND.value(),
            "Recurso não Encontrado",
            e.getMessage(),
            null,
            LocalDateTime.now().toString()
        ));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionDTO> handleAuthentication(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionDTO(
            HttpStatus.UNAUTHORIZED.value(),
            "Acesso Negado",
            "Você não está logado.",
            null,
            LocalDateTime.now().toString()
        ));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDTO> handleForbidden(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionDTO(
            HttpStatus.FORBIDDEN.value(),
            "Acesso Negado",
            "Você não possui acesso para tal operação.",
            null,
            LocalDateTime.now().toString()
        ));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionDTO> handleBadCredentials(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionDTO(
            HttpStatus.UNAUTHORIZED.value(),
            "Dados Inválidos",
            "Usuário ou senha incorretos..",
            null,
            LocalDateTime.now().toString()
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO> handleMethodArgument(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDTO(
            HttpStatus.BAD_REQUEST.value(),
            "Dados Inválidos",
            "Verifique os campos preenchidos.",
            e.getBindingResult().getFieldErrors().stream().map(
                erro -> erro.getField() + ": " + erro.getDefaultMessage()
            ).toList(),
            LocalDateTime.now().toString()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionDTO(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Erro Interno",
            e.getMessage(),
            null,
            LocalDateTime.now().toString()
        ));
    }
}
