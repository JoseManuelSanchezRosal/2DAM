package com.dam2.Practica1.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Metodo para controlar los errores de validaciones en el CreateUpdate de las clases
     * @param exception para recorrer las excepciones de las validaciones
     * @return un mensaje personalizado configurado en los @Valid en las clases CreateUpdateDTO
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException exception){
        Map<String, String> errores = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error)-> {
            String nombreError = ((FieldError) error).getField();
            String mensajeError =  error.getDefaultMessage();
            errores.put(nombreError, mensajeError);
        });
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}