package com.gestionCliente.notificaciones.service;

import com.gestionCliente.notificaciones.entity.Notificacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

@Service
public class EmailService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private org.springframework.core.env.Environment environment;
    
    /**
     * Envía un email de promoción
     */
    public boolean enviarEmail(Notificacion notificacion) {
        try {
            // Obtener configuración SMTP
            String mailHost = environment.getProperty("spring.mail.host", "no configurado");
            String mailPort = environment.getProperty("spring.mail.port", "no configurado");
            String fromEmail = environment.getProperty("spring.mail.from");
            
            // Validar que el remitente esté configurado
            if (fromEmail == null || fromEmail.isEmpty()) {
                logger.error("❌ MAIL_FROM no está configurado. Por favor, configura la variable de entorno MAIL_FROM con el email verificado en SendGrid");
                return false;
            }
            
            // Log de configuración para diagnóstico
            logger.info("📧 Enviando email - SMTP: {}:{}, From: {}", mailHost, mailPort, fromEmail);
            
            SimpleMailMessage message = new SimpleMailMessage();
            // Configurar remitente (debe ser un email verificado en SendGrid)
            message.setFrom(fromEmail);
            message.setTo(notificacion.getCliente().getEmail());
            message.setSubject("🎉 " + notificacion.getPromocion().getNombre());
            message.setText(construirMensajeEmail(notificacion));
            
            mailSender.send(message);
            
            logger.info("Email enviado exitosamente a: {}", notificacion.getCliente().getEmail());
            return true;
            
        } catch (MailException e) {
            // Verificar si es un timeout de conexión (esperado en algunos entornos)
            return manejarErrorMail(e, notificacion.getCliente().getEmail());
            
        } catch (Exception e) {
            // Capturar cualquier otra excepción, incluyendo las que puedan envolver MailException
            // Verificar si la causa es una MailException
            Throwable cause = e.getCause();
            if (cause instanceof MailException) {
                return manejarErrorMail((MailException) cause, notificacion.getCliente().getEmail());
            }
            
            // Verificar si el mensaje indica un error de mail
            String errorMessage = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
            if (errorMessage.contains("mail") || errorMessage.contains("smtp") || 
                errorMessage.contains("timeout") || errorMessage.contains("connect")) {
                return manejarErrorMailGenerico(e, notificacion.getCliente().getEmail());
            }
            
            logger.error("Error inesperado al enviar email a {}: {}", 
                        notificacion.getCliente().getEmail(), e.getMessage());
            return false;
        }
    }
    
    /**
     * Maneja errores de MailException
     */
    private boolean manejarErrorMail(MailException e, String email) {
        Throwable cause = e.getCause();
        String errorMessage = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
        String fullMessage = e.getMessage() != null ? e.getMessage() : "Error desconocido";
        
        boolean isTimeout = cause instanceof SocketTimeoutException || 
                           cause instanceof ConnectException ||
                           errorMessage.contains("timeout") ||
                           errorMessage.contains("connect timed out") ||
                           errorMessage.contains("couldn't connect to host") ||
                           errorMessage.contains("mail server connection failed");
        
        if (isTimeout) {
            // Timeouts son errores reales en producción
            logger.error("❌ ERROR: Timeout de conexión SMTP al enviar email a {}. Verifica: 1) Variables de entorno (MAIL_HOST, MAIL_PORT), 2) Conectividad de red, 3) Configuración del servidor SMTP. Detalle: {}", 
                       email, fullMessage);
        } else if (errorMessage.contains("authentication failed") || errorMessage.contains("authentication")) {
            logger.error("❌ ERROR: Fallo de autenticación SMTP al enviar email a {}. Verifica: 1) MAIL_USERNAME y MAIL_PASSWORD correctos, 2) API Key válida en SendGrid. Detalle: {}", 
                       email, fullMessage);
        } else if (errorMessage.contains("sender identity") || errorMessage.contains("verified sender")) {
            logger.error("❌ ERROR: El remitente no está verificado en SendGrid. Email: {}. Verifica que MAIL_FROM coincida exactamente con un Sender Identity verificado en SendGrid. Detalle: {}", 
                       email, fullMessage);
        } else {
            // Otros errores de mail se loguean como ERROR
            logger.error("❌ ERROR: Error de conexión SMTP al enviar email a {}. Detalle: {}", email, fullMessage);
        }
        return false;
    }
    
    /**
     * Maneja errores genéricos que parecen ser de mail
     */
    private boolean manejarErrorMailGenerico(Exception e, String email) {
        String errorMessage = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
        String fullMessage = e.getMessage() != null ? e.getMessage() : "Error desconocido";
        
        boolean isTimeout = errorMessage.contains("timeout") ||
                           errorMessage.contains("connect timed out") ||
                           errorMessage.contains("couldn't connect to host");
        
        if (isTimeout) {
            logger.error("❌ ERROR: Timeout de conexión SMTP al enviar email a {}. Verifica la configuración SMTP y conectividad de red. Detalle: {}", 
                       email, fullMessage);
        } else {
            logger.error("❌ ERROR: Error de conexión SMTP al enviar email a {}. Detalle: {}", email, fullMessage);
        }
        return false;
    }
    
    /**
     * Construye el mensaje del email
     */
    private String construirMensajeEmail(Notificacion notificacion) {
        StringBuilder mensaje = new StringBuilder();
        
        mensaje.append("¡Hola ").append(notificacion.getCliente().getNombre()).append("! 🐾\n\n");
        mensaje.append("Tenemos una promoción especial para ti:\n\n");
        mensaje.append("📢 ").append(notificacion.getPromocion().getNombre()).append("\n");
        
        if (notificacion.getPromocion().getDescripcion() != null && !notificacion.getPromocion().getDescripcion().isEmpty()) {
            mensaje.append("\n").append(notificacion.getPromocion().getDescripcion()).append("\n");
        }
        
        mensaje.append("\n💰 Descuento: ");
        if ("PORCENTAJE".equals(notificacion.getPromocion().getTipoDescuento())) {
            mensaje.append(notificacion.getPromocion().getValorDescuento()).append("% de descuento");
        } else {
            mensaje.append("$").append(notificacion.getPromocion().getValorDescuento()).append(" de descuento");
        }
        mensaje.append("\n");
        
        if (notificacion.getPromocion().getCategoriaAplicable() != null && !notificacion.getPromocion().getCategoriaAplicable().isEmpty()) {
            mensaje.append("\n🐕 Categoría: ").append(notificacion.getPromocion().getCategoriaAplicable()).append("\n");
        }
        
        if (notificacion.getMensaje() != null && !notificacion.getMensaje().isEmpty()) {
            mensaje.append("\n").append(notificacion.getMensaje()).append("\n");
        }
        
        mensaje.append("\n¡No te pierdas esta oportunidad!\n");
        mensaje.append("Visita nuestra tienda y aprovecha esta oferta.\n\n");
        mensaje.append("Saludos,\n");
        mensaje.append("Equipo de Mascotas Felices 🐾");
        
        return mensaje.toString();
    }
    
    /**
     * Envía email masivo
     */
    public int enviarEmailMasivo(String[] emails, String asunto, String mensaje) {
        int enviados = 0;
        
        String fromEmail = environment.getProperty("spring.mail.from");
        
        if (fromEmail == null || fromEmail.isEmpty()) {
            logger.error("❌ MAIL_FROM no está configurado. No se pueden enviar emails masivos.");
            return 0;
        }
        
        for (String email : emails) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromEmail);
                message.setTo(email);
                message.setSubject(asunto);
                message.setText(mensaje);
                
                mailSender.send(message);
                enviados++;
                logger.info("Email masivo enviado a: {}", email);
                
            } catch (MailException e) {
                // Reutilizar el método de manejo de errores
                String errorMessage = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
                String fullMessage = e.getMessage() != null ? e.getMessage() : "Error desconocido";
                
                boolean isTimeout = errorMessage.contains("timeout") ||
                                   errorMessage.contains("connect timed out") ||
                                   errorMessage.contains("couldn't connect to host");
                
                if (isTimeout) {
                    logger.error("❌ ERROR: Timeout de conexión SMTP al enviar email masivo a {}. Detalle: {}", email, fullMessage);
                } else {
                    logger.error("❌ ERROR: Error al enviar email masivo a {}. Detalle: {}", email, fullMessage);
                }
            } catch (Exception e) {
                logger.error("Error inesperado al enviar email masivo a {}: {}", email, e.getMessage());
            }
        }
        
        return enviados;
    }
}
