package com.cine.entradas.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Manejo de "No encontrado" (404)
    // Se dispara cuando lanzas EntityNotFoundException en los Servicios
    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFound(EntityNotFoundException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problem.setTitle("Recurso no encontrado");
        problem.setType(URI.create("https://cine.com/errors/not-found"));
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    // 2. Manejo de Validaciones (@Valid fallido) (400)
    // Se dispara si falta un campo obligatorio o el email está mal formado
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationErrors(MethodArgumentNotValidException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Error de validación en los datos enviados");
        problem.setTitle("Datos inválidos");

        // Recopilamos los errores de campo (ej: "email": "debe ser válido")
        Map<String, String> errores = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );

        problem.setProperty("errors", errores);
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    // 3. Reglas de Negocio / Concurrencia (409 Conflict)
    // Se dispara cuando intentan comprar un asiento ocupado
    @ExceptionHandler(IllegalStateException.class)
    public ProblemDetail handleBusinessRule(IllegalStateException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problem.setTitle("Conflicto de Negocio");
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }
}