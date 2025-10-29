package com.gestionCliente.notificaciones.service;

import com.gestionCliente.notificaciones.entity.Notificacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    
    @Autowired
    private JavaMailSender mailSender;
    
    /**
     * Envía un email de promoción
     */
    public boolean enviarEmail(Notificacion notificacion) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(notificacion.getCliente().getEmail());
            message.setSubject("🎉 " + notificacion.getPromocion().getNombre());
            message.setText(construirMensajeEmail(notificacion));
            
            mailSender.send(message);
            
            logger.info("Email enviado exitosamente a: {}", notificacion.getCliente().getEmail());
            return true;
            
        } catch (Exception e) {
            logger.error("Error al enviar email a {}: {}", 
                        notificacion.getCliente().getEmail(), e.getMessage());
            return false;
        }
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
        
        for (String email : emails) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(email);
                message.setSubject(asunto);
                message.setText(mensaje);
                
                mailSender.send(message);
                enviados++;
                logger.info("Email masivo enviado a: {}", email);
                
            } catch (Exception e) {
                logger.error("Error al enviar email masivo a {}: {}", email, e.getMessage());
            }
        }
        
        return enviados;
    }
}
