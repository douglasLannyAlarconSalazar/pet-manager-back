package com.gestionCliente.notificaciones.service;

import com.gestionCliente.notificaciones.entity.Notificacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
    
    /**
     * Envía una notificación push de promoción (simulada)
     */
    public boolean enviarPushNotification(Notificacion notificacion) {
        try {
            // Simulación de envío de notificación push
            String titulo = notificacion.getPromocion().getNombre();
            String mensaje = construirMensajePush(notificacion);
            
            // Aquí se integraría con Firebase Cloud Messaging (FCM) o similar
            logger.info("Push notification simulado enviado para cliente {}: {} - {}", 
                       notificacion.getIdCliente(), titulo, mensaje);
            
            // Simular delay de red
            Thread.sleep(1200);
            
            return true;
            
        } catch (Exception e) {
            logger.error("Error al enviar push notification para cliente {}: {}", 
                        notificacion.getIdCliente(), e.getMessage());
            return false;
        }
    }
    
    /**
     * Construye el mensaje de la notificación push
     */
    private String construirMensajePush(Notificacion notificacion) {
        StringBuilder mensaje = new StringBuilder();
        
        mensaje.append("🐾 ");
        
        mensaje.append("Descuento: ");
        if ("PORCENTAJE".equals(notificacion.getPromocion().getTipoDescuento())) {
            mensaje.append(notificacion.getPromocion().getValorDescuento()).append("% - ");
        } else {
            mensaje.append("$").append(notificacion.getPromocion().getValorDescuento()).append(" - ");
        }
        
        mensaje.append(notificacion.getPromocion().getNombre());
        
        if (notificacion.getPromocion().getCategoriaAplicable() != null && !notificacion.getPromocion().getCategoriaAplicable().isEmpty()) {
            mensaje.append(" (").append(notificacion.getPromocion().getCategoriaAplicable()).append(")");
        }
        
        return mensaje.toString();
    }
    
    /**
     * Envía notificaciones push masivas
     */
    public int enviarPushMasivo(Long[] idClientes, String titulo, String mensaje) {
        int enviados = 0;
        
        for (Long idCliente : idClientes) {
            try {
                // Simulación de envío de notificación push
                logger.info("Push notification masivo simulado enviado para cliente {}: {} - {}", 
                           idCliente, titulo, mensaje);
                enviados++;
                
                // Simular delay de red
                Thread.sleep(300);
                
            } catch (Exception e) {
                logger.error("Error al enviar push notification masivo para cliente {}: {}", 
                           idCliente, e.getMessage());
            }
        }
        
        return enviados;
    }
}
