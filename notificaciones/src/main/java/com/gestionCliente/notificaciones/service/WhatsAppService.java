package com.gestionCliente.notificaciones.service;

import com.gestionCliente.notificaciones.config.WhatsAppConfig;
import com.gestionCliente.notificaciones.entity.Notificacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class WhatsAppService {
    
    private static final Logger logger = LoggerFactory.getLogger(WhatsAppService.class);
    
    @Autowired
    private WhatsAppConfig whatsAppConfig;
    
    private WebClient webClient;
    
    /**
     * Inicializa el WebClient para WhatsApp API
     */
    private void initWebClient() {
        if (webClient == null && whatsAppConfig.getAccessToken() != null) {
            webClient = WebClient.builder()
                .baseUrl(whatsAppConfig.getApiUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + whatsAppConfig.getAccessToken())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        }
    }
    
    /**
     * Envía un mensaje de WhatsApp de promoción usando WhatsApp Business API
     */
    public boolean enviarWhatsApp(Notificacion notificacion) {
        try {
            String mensaje = construirMensajeWhatsApp(notificacion);
            String telefono = notificacion.getCliente().getTelefono();
            
            // Verificar si las credenciales están configuradas
            if (whatsAppConfig.getAccessToken() == null || whatsAppConfig.getPhoneNumberId() == null) {
                logger.warn("Credenciales de WhatsApp no configuradas, enviando mensaje simulado");
                return enviarWhatsAppSimulado(notificacion, mensaje);
            }
            
            // Validar formato del teléfono
            if (!esTelefonoValido(telefono)) {
                logger.error("Número de teléfono inválido: {}", telefono);
                return false;
            }
            
            // Inicializar WebClient si es necesario
            initWebClient();
            
            // Preparar el número de teléfono para WhatsApp (formato internacional)
            String telefonoFormateado = formatearTelefonoParaWhatsApp(telefono);
            
            // Enviar mensaje real con WhatsApp Business API
            return enviarMensajeWhatsAppReal(telefonoFormateado, mensaje);
            
        } catch (Exception e) {
            logger.error("Error al enviar WhatsApp a {}: {}", 
                        notificacion.getCliente().getTelefono(), e.getMessage());
            
            // Fallback a modo simulado en caso de error
            logger.info("Intentando envío simulado como fallback");
            return enviarWhatsAppSimulado(notificacion, construirMensajeWhatsApp(notificacion));
        }
    }
    
    /**
     * Envía mensaje real usando WhatsApp Business API
     */
    private boolean enviarMensajeWhatsAppReal(String telefono, String mensaje) {
        try {
            // Construir el payload para la API de WhatsApp
            Map<String, Object> payload = new HashMap<>();
            payload.put("messaging_product", "whatsapp");
            payload.put("to", telefono);
            payload.put("type", "text");
            
            Map<String, Object> text = new HashMap<>();
            text.put("body", mensaje);
            payload.put("text", text);
            
            // Hacer la llamada a la API
            String url = whatsAppConfig.getApiUrl() + "/" + whatsAppConfig.getPhoneNumberId() + "/messages";
            
            String response = webClient.post()
                .uri(url)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .block();
            
            logger.info("WhatsApp enviado exitosamente a {}: {}", telefono, response);
            return true;
            
        } catch (Exception e) {
            logger.error("Error al enviar mensaje real de WhatsApp: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Envía WhatsApp simulado (fallback)
     */
    private boolean enviarWhatsAppSimulado(Notificacion notificacion, String mensaje) {
        try {
            logger.info("WhatsApp simulado enviado a {}: {}", 
                       notificacion.getCliente().getTelefono(), mensaje);
            
            // Simular delay de red
            Thread.sleep(1500);
            
            return true;
            
        } catch (Exception e) {
            logger.error("Error en WhatsApp simulado: {}", e.getMessage());
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
     * Formatea el número de teléfono para WhatsApp (formato internacional)
     */
    private String formatearTelefonoParaWhatsApp(String telefono) {
        // Remover espacios y caracteres especiales
        String telefonoLimpio = telefono.replaceAll("[^0-9+]", "");
        
        // Si no empieza con +, agregarlo
        if (!telefonoLimpio.startsWith("+")) {
            // Asumir que es un número colombiano si no tiene código de país
            if (telefonoLimpio.startsWith("57")) {
                telefonoLimpio = "+" + telefonoLimpio;
            } else {
                telefonoLimpio = "+57" + telefonoLimpio;
            }
        }
        
        return telefonoLimpio;
    }
    
    /**
     * Construye el mensaje de WhatsApp
     */
    private String construirMensajeWhatsApp(Notificacion notificacion) {
        StringBuilder mensaje = new StringBuilder();
        
        mensaje.append("🐾 *Mascotas Felices* 🐾\n\n");
        mensaje.append("¡Hola ").append(notificacion.getCliente().getNombre()).append("! Tenemos una promoción especial para ti:\n\n");
        mensaje.append("📢 *").append(notificacion.getPromocion().getNombre()).append("*\n");
        
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
     * Envía WhatsApp masivo
     */
    public int enviarWhatsAppMasivo(String[] telefonos, String mensaje) {
        int enviados = 0;
        
        for (String telefono : telefonos) {
            try {
                // Simulación de envío de WhatsApp
                logger.info("WhatsApp masivo simulado enviado a {}: {}", telefono, mensaje);
                enviados++;
                
                // Simular delay de red
                Thread.sleep(800);
                
            } catch (Exception e) {
                logger.error("Error al enviar WhatsApp masivo a {}: {}", telefono, e.getMessage());
            }
        }
        
        return enviados;
    }
}
