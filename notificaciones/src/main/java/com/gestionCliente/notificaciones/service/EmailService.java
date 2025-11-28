package com.gestionCliente.notificaciones.service;

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

/**
 * Servicio de envío de emails usando SendGrid API REST
 * Usa API REST en lugar de SMTP para funcionar en plataformas cloud como Render
 * que bloquean conexiones SMTP salientes
 */
@Service
public class EmailService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    
    @Autowired
    private org.springframework.core.env.Environment environment;
    
    @Autowired
    private WebClient.Builder webClientBuilder;
    
    /**
     * Envía un email de promoción usando SendGrid API REST
     */
    public boolean enviarEmail(Notificacion notificacion) {
        try {
            // Obtener configuración de SendGrid API
            String apiKey = environment.getProperty("SENDGRID_API_KEY");
            String fromEmail = environment.getProperty("SENDGRID_FROM_EMAIL");
            
            // Validar configuración
            if (apiKey == null || apiKey.isEmpty()) {
                logger.error("❌ ERROR: SENDGRID_API_KEY no está configurado. Por favor, configura la variable de entorno SENDGRID_API_KEY");
                return false;
            }
            
            if (fromEmail == null || fromEmail.isEmpty()) {
                logger.error("❌ ERROR: SENDGRID_FROM_EMAIL no está configurado. Por favor, configura la variable de entorno SENDGRID_FROM_EMAIL con el email verificado en SendGrid");
                return false;
            }
            
            // Construir el mensaje del email
            String mensajeTexto = construirMensajeEmail(notificacion);
            String asunto = "🎉 " + notificacion.getPromocion().getNombre();
            String destinatario = notificacion.getCliente().getEmail();
            
            logger.info("📧 Enviando email vía SendGrid API - To: {}, From: {}", destinatario, fromEmail);
            
            // Construir el payload para SendGrid API
            Map<String, Object> payload = new HashMap<>();
            Map<String, String> fromMap = new HashMap<>();
            fromMap.put("email", fromEmail);
            payload.put("from", fromMap);
            
            Map<String, String> toMap = new HashMap<>();
            toMap.put("email", destinatario);
            payload.put("personalizations", new Object[]{
                Map.of("to", new Object[]{toMap})
            });
            
            payload.put("subject", asunto);
            
            Map<String, String> contentMap = new HashMap<>();
            contentMap.put("type", "text/plain");
            contentMap.put("value", mensajeTexto);
            payload.put("content", new Object[]{contentMap});
            
            // Enviar usando WebClient (más confiable que el SDK de SendGrid)
            WebClient webClient = webClientBuilder
                .baseUrl("https://api.sendgrid.com")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
            
            // Enviar email - SendGrid retorna 202 Accepted cuando es exitoso
            webClient.post()
                .uri("/v3/mail/send")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Bloquea porque necesitamos el resultado síncrono
            
            logger.info("✅ Email enviado exitosamente a: {}", destinatario);
            return true;
            
        } catch (org.springframework.web.reactive.function.client.WebClientResponseException e) {
            logger.error("❌ ERROR: SendGrid API retornó código de error {} al enviar email a {}. Respuesta: {}", 
                       e.getStatusCode().value(), notificacion.getCliente().getEmail(), e.getResponseBodyAsString());
            return false;
        } catch (Exception e) {
            logger.error("❌ ERROR: Error inesperado al enviar email a {}: {}", 
                        notificacion.getCliente().getEmail(), e.getMessage(), e);
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
     * Envía email masivo usando SendGrid API REST
     */
    public int enviarEmailMasivo(String[] emails, String asunto, String mensaje) {
        int enviados = 0;
        
        String apiKey = environment.getProperty("SENDGRID_API_KEY");
        String fromEmail = environment.getProperty("SENDGRID_FROM_EMAIL");
        
        if (apiKey == null || apiKey.isEmpty() || fromEmail == null || fromEmail.isEmpty()) {
            logger.error("❌ ERROR: SENDGRID_API_KEY o SENDGRID_FROM_EMAIL no están configurados. No se pueden enviar emails masivos.");
            return 0;
        }
        
        // Configurar WebClient para SendGrid API
        WebClient webClient = webClientBuilder
            .baseUrl("https://api.sendgrid.com")
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
        
        for (String email : emails) {
            try {
                // Construir payload para cada email
                Map<String, Object> payload = new HashMap<>();
                Map<String, String> fromMap = new HashMap<>();
                fromMap.put("email", fromEmail);
                payload.put("from", fromMap);
                
                Map<String, String> toMap = new HashMap<>();
                toMap.put("email", email);
                payload.put("personalizations", new Object[]{
                    Map.of("to", new Object[]{toMap})
                });
                
                payload.put("subject", asunto);
                
                Map<String, String> contentMap = new HashMap<>();
                contentMap.put("type", "text/plain");
                contentMap.put("value", mensaje);
                payload.put("content", new Object[]{contentMap});
                
                // Enviar email
                webClient.post()
                    .uri("/v3/mail/send")
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
                
                enviados++;
                logger.info("✅ Email masivo enviado a: {}", email);
                
            } catch (org.springframework.web.reactive.function.client.WebClientResponseException e) {
                logger.error("❌ ERROR: SendGrid API retornó código de error {} al enviar email masivo a {}. Respuesta: {}", 
                           e.getStatusCode().value(), email, e.getResponseBodyAsString());
            } catch (Exception e) {
                logger.error("❌ ERROR: Error inesperado al enviar email masivo a {}: {}", email, e.getMessage());
            }
        }
        
        return enviados;
    }
}
