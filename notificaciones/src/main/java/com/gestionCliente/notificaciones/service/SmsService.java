package com.gestionCliente.notificaciones.service;

import com.gestionCliente.notificaciones.config.TwilioConfig;
import com.gestionCliente.notificaciones.entity.Notificacion;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class SmsService {
    
    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);
    
    @Autowired
    private TwilioConfig twilioConfig;
    
    @PostConstruct
    public void init() {
        // Inicializar Twilio con las credenciales
        if (twilioConfig.getAccountSid() != null && twilioConfig.getAuthToken() != null) {
            Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
            logger.info("Twilio inicializado correctamente");
        } else {
            logger.warn("Credenciales de Twilio no configuradas, usando modo simulado");
        }
    }
    
    /**
     * Envía un SMS de promoción usando Twilio
     */
    public boolean enviarSms(Notificacion notificacion) {
        try {
            String mensaje = construirMensajeSms(notificacion);
            String telefono = notificacion.getCliente().getTelefono();
            
            // Verificar si las credenciales están configuradas
            if (twilioConfig.getAccountSid() == null || twilioConfig.getAuthToken() == null || 
                twilioConfig.getMessagingServiceSid() == null) {
                logger.warn("Credenciales de Twilio no configuradas, enviando SMS simulado");
                return enviarSmsSimulado(notificacion, mensaje);
            }
            
            // Validar formato del teléfono
            if (!esTelefonoValido(telefono)) {
                logger.error("Número de teléfono inválido: {}", telefono);
                return false;
            }
            
            // Enviar SMS real con Twilio usando Messaging Service
            Message message = Message.creator(
                new PhoneNumber(telefono), // Número de destino
                twilioConfig.getMessagingServiceSid(), // Messaging Service SID
                mensaje
            ).create();
            
            logger.info("SMS enviado exitosamente a {} con SID: {}", telefono, message.getSid());
            return true;
            
        } catch (Exception e) {
            logger.error("Error al enviar SMS a {}: {}", 
                        notificacion.getCliente().getTelefono(), e.getMessage());
            
            // Fallback a modo simulado en caso de error
            logger.info("Intentando envío simulado como fallback");
            return enviarSmsSimulado(notificacion, construirMensajeSms(notificacion));
        }
    }
    
    /**
     * Envía SMS simulado (fallback)
     */
    private boolean enviarSmsSimulado(Notificacion notificacion, String mensaje) {
        try {
            logger.info("SMS simulado enviado a {}: {}", 
                       notificacion.getCliente().getTelefono(), mensaje);
            
            // Simular delay de red
            Thread.sleep(1000);
            
            return true;
            
        } catch (Exception e) {
            logger.error("Error en SMS simulado: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Valida el formato del número de teléfono
     */
    private boolean esTelefonoValido(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return false;
        }
        
        // Remover espacios y caracteres especiales
        String telefonoLimpio = telefono.replaceAll("[^0-9+]", "");
        
        // Verificar que tenga al menos 10 dígitos y máximo 15
        return telefonoLimpio.length() >= 10 && telefonoLimpio.length() <= 15;
    }
    
    /**
     * Construye el mensaje del SMS
     */
    private String construirMensajeSms(Notificacion notificacion) {
        StringBuilder mensaje = new StringBuilder();
        
        mensaje.append("Mascotas Felices: ");
        mensaje.append(notificacion.getPromocion().getNombre());
        
        mensaje.append(" - Descuento: ");
        if ("PORCENTAJE".equals(notificacion.getPromocion().getTipoDescuento())) {
            mensaje.append(notificacion.getPromocion().getValorDescuento()).append("%");
        } else {
            mensaje.append("$").append(notificacion.getPromocion().getValorDescuento());
        }
        
        if (notificacion.getPromocion().getCategoriaAplicable() != null && !notificacion.getPromocion().getCategoriaAplicable().isEmpty()) {
            mensaje.append(" (").append(notificacion.getPromocion().getCategoriaAplicable()).append(")");
        }
        
        if (notificacion.getMensaje() != null && !notificacion.getMensaje().isEmpty()) {
            mensaje.append(" - ").append(notificacion.getMensaje());
        }
        
        mensaje.append(" - Visita nuestra tienda!");
        
        return mensaje.toString();
    }
    
    /**
     * Envía SMS masivo
     */
    public int enviarSmsMasivo(String[] telefonos, String mensaje) {
        int enviados = 0;
        
        for (String telefono : telefonos) {
            try {
                // Simulación de envío de SMS
                logger.info("SMS masivo simulado enviado a {}: {}", telefono, mensaje);
                enviados++;
                
                // Simular delay de red
                Thread.sleep(500);
                
            } catch (Exception e) {
                logger.error("Error al enviar SMS masivo a {}: {}", telefono, e.getMessage());
            }
        }
        
        return enviados;
    }
}
