package com.gestionCliente.notificaciones.exception;

public class NotificacionNotFoundException extends RuntimeException {
    
    public NotificacionNotFoundException(String message) {
        super(message);
    }
    
    public NotificacionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
