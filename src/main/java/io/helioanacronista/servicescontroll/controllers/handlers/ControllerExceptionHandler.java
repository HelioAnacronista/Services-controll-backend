package io.helioanacronista.servicescontroll.controllers.handlers;

import io.helioanacronista.servicescontroll.DTO.CustomError;
import io.helioanacronista.servicescontroll.DTO.ValidationError;
import io.helioanacronista.servicescontroll.services.exceptions.DataBaseNotFoundException;
import io.helioanacronista.servicescontroll.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DataBaseNotFoundException.class)
    public ResponseEntity<CustomError> dataBase(DataBaseNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    //Customizando a resposta da validação
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        ValidationError err = new ValidationError(Instant.now(), status.value(), "Dados inválidos", request.getRequestURI());

        for(FieldError f : e.getBindingResult().getFieldErrors()){
            err.addError(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(err);
    }
}
