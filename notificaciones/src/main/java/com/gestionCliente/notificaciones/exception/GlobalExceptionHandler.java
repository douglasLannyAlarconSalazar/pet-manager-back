package com.gestionCliente.notificaciones.exception;

import com.gestionCliente.notificaciones.dto.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        logger.warn("Error de validación: {}", errors);
        
        ApiResponse<Map<String, String>> response = ApiResponse.error(
            "Datos de entrada inválidos", errors, HttpStatus.BAD_REQUEST.value());
        
        return ResponseEntity.badRequest().body(response);
    }
    
    @ExceptionHandler(NotificacionNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNotificacionNotFoundException(
            NotificacionNotFoundException ex) {
        
        logger.warn("Notificación no encontrada: {}", ex.getMessage());
        
        ApiResponse<String> response = ApiResponse.error(
            ex.getMessage(), HttpStatus.NOT_FOUND.value());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    @ExceptionHandler(EmailServiceException.class)
    public ResponseEntity<ApiResponse<String>> handleEmailServiceException(
            EmailServiceException ex) {
        
        logger.error("Error en el servicio de email: {}", ex.getMessage());
        
        ApiResponse<String> response = ApiResponse.error(
            "Error al enviar email: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
    @ExceptionHandler(SmsServiceException.class)
    public ResponseEntity<ApiResponse<String>> handleSmsServiceException(
            SmsServiceException ex) {
        
        logger.error("Error en el servicio de SMS: {}", ex.getMessage());
        
        ApiResponse<String> response = ApiResponse.error(
            "Error al enviar SMS: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(
            IllegalArgumentException ex) {
        
        logger.warn("Argumento inválido: {}", ex.getMessage());
        
        ApiResponse<String> response = ApiResponse.error(
            "Argumento inválido: " + ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        
        return ResponseEntity.badRequest().body(response);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGenericException(Exception ex) {
        
        logger.error("Error interno del servidor: {}", ex.getMessage(), ex);
        
        ApiResponse<String> response = ApiResponse.error(
            "Error interno del servidor: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
