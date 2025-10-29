package com.gestionCliente.notificaciones.service;

import com.gestionCliente.notificaciones.dto.*;
import com.gestionCliente.notificaciones.entity.*;
import com.gestionCliente.notificaciones.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificacionService {
    
    private static final Logger logger = LoggerFactory.getLogger(NotificacionService.class);
    
    @Autowired
    private NotificacionRepository notificacionRepository;
    
    @Autowired
    private PromocionRepository promocionRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private SmsService smsService;
    
    @Autowired
    private WhatsAppService whatsAppService;
    
    @Autowired
    private PushNotificationService pushNotificationService;
    
    /**
     * Envía notificación por email
     */
    public ApiResponse<NotificacionResponse> enviarEmail(EmailRequest request) {
        try {
            // Validar que la promoción existe
            Optional<Promocion> promocionOpt = promocionRepository.findById(request.getIdPromocion());
            if (promocionOpt.isEmpty()) {
                return ApiResponse.error("Promoción no encontrada", 404);
            }
            
            // Validar que el cliente existe
            Optional<Cliente> clienteOpt = clienteRepository.findById(request.getIdCliente());
            if (clienteOpt.isEmpty()) {
                return ApiResponse.error("Cliente no encontrado", 404);
            }
            
            // Validar que el usuario existe
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(request.getIdUsuario());
            if (usuarioOpt.isEmpty()) {
                return ApiResponse.error("Usuario no encontrado", 404);
            }
            
            Promocion promocion = promocionOpt.get();
            Cliente cliente = clienteOpt.get();
            Usuario usuario = usuarioOpt.get();
            
            // Crear notificación
            Notificacion notificacion = new Notificacion();
            notificacion.setIdCliente(request.getIdCliente());
            notificacion.setIdPromocion(request.getIdPromocion());
            notificacion.setIdUsuario(request.getIdUsuario());
            notificacion.setTipoNotificacion("EMAIL");
            notificacion.setCanalEnvio("EMAIL");
            notificacion.setMensaje(request.getMensajePersonalizado());
            notificacion.setCliente(cliente);
            notificacion.setPromocion(promocion);
            notificacion.setUsuario(usuario);
            
            // Guardar notificación
            notificacion = notificacionRepository.save(notificacion);
            
            // Enviar email
            boolean enviado = emailService.enviarEmail(notificacion);
            
            if (enviado) {
                notificacion.setEstadoEntrega(Notificacion.EstadoEntrega.ENVIADA);
                notificacion.setFechaEnvio(LocalDate.now());
            } else {
                notificacion.setEstadoEntrega(Notificacion.EstadoEntrega.FALLIDA);
            }
            
            notificacion = notificacionRepository.save(notificacion);
            
            return ApiResponse.success("Email enviado exitosamente", new NotificacionResponse(notificacion));
            
        } catch (Exception e) {
            logger.error("Error al enviar email: {}", e.getMessage());
            return ApiResponse.error("Error al enviar email: " + e.getMessage(), 500);
        }
    }
    
    /**
     * Envía notificación por SMS
     */
    public ApiResponse<NotificacionResponse> enviarSms(SmsRequest request) {
        try {
            // Validar que la promoción existe
            Optional<Promocion> promocionOpt = promocionRepository.findById(request.getIdPromocion());
            if (promocionOpt.isEmpty()) {
                return ApiResponse.error("Promoción no encontrada", 404);
            }
            
            // Validar que el cliente existe
            Optional<Cliente> clienteOpt = clienteRepository.findById(request.getIdCliente());
            if (clienteOpt.isEmpty()) {
                return ApiResponse.error("Cliente no encontrado", 404);
            }
            
            // Validar que el usuario existe
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(request.getIdUsuario());
            if (usuarioOpt.isEmpty()) {
                return ApiResponse.error("Usuario no encontrado", 404);
            }
            
            Promocion promocion = promocionOpt.get();
            Cliente cliente = clienteOpt.get();
            Usuario usuario = usuarioOpt.get();
            
            // Crear notificación
            Notificacion notificacion = new Notificacion();
            notificacion.setIdCliente(request.getIdCliente());
            notificacion.setIdPromocion(request.getIdPromocion());
            notificacion.setIdUsuario(request.getIdUsuario());
            notificacion.setTipoNotificacion("SMS");
            notificacion.setCanalEnvio("SMS");
            notificacion.setMensaje(request.getMensajePersonalizado());
            notificacion.setCliente(cliente);
            notificacion.setPromocion(promocion);
            notificacion.setUsuario(usuario);
            
            // Guardar notificación
            notificacion = notificacionRepository.save(notificacion);
            
            // Enviar SMS
            boolean enviado = smsService.enviarSms(notificacion);
            
            if (enviado) {
                notificacion.setEstadoEntrega(Notificacion.EstadoEntrega.ENVIADA);
                notificacion.setFechaEnvio(LocalDate.now());
            } else {
                notificacion.setEstadoEntrega(Notificacion.EstadoEntrega.FALLIDA);
            }
            
            notificacion = notificacionRepository.save(notificacion);
            
            return ApiResponse.success("SMS enviado exitosamente", new NotificacionResponse(notificacion));
            
        } catch (Exception e) {
            logger.error("Error al enviar SMS: {}", e.getMessage());
            return ApiResponse.error("Error al enviar SMS: " + e.getMessage(), 500);
        }
    }
    
    /**
     * Envía notificaciones masivas por email
     */
    public ApiResponse<String> enviarEmailMasivo(EmailMasivoRequest request) {
        try {
            // Validar que la promoción existe
            Optional<Promocion> promocionOpt = promocionRepository.findById(request.getIdPromocion());
            if (promocionOpt.isEmpty()) {
                return ApiResponse.error("Promoción no encontrada", 404);
            }
            
            // Validar que el usuario existe
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(request.getIdUsuario());
            if (usuarioOpt.isEmpty()) {
                return ApiResponse.error("Usuario no encontrado", 404);
            }
            
            Promocion promocion = promocionOpt.get();
            Usuario usuario = usuarioOpt.get();
            
            List<Notificacion> notificaciones = new ArrayList<>();
            int enviados = 0;
            int fallidos = 0;
            
            for (Long idCliente : request.getIdClientes()) {
                // Validar que el cliente existe
                Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
                if (clienteOpt.isEmpty()) {
                    logger.warn("Cliente con ID {} no encontrado, omitiendo", idCliente);
                    fallidos++;
                    continue;
                }
                
                Cliente cliente = clienteOpt.get();
                
                // Crear notificación
                Notificacion notificacion = new Notificacion();
                notificacion.setIdCliente(idCliente);
                notificacion.setIdPromocion(request.getIdPromocion());
                notificacion.setIdUsuario(request.getIdUsuario());
                notificacion.setTipoNotificacion("EMAIL");
                notificacion.setCanalEnvio("EMAIL");
                notificacion.setMensaje(request.getMensajePersonalizado());
                notificacion.setCliente(cliente);
                notificacion.setPromocion(promocion);
                notificacion.setUsuario(usuario);
                
                notificaciones.add(notificacion);
            }
            
            // Guardar notificaciones
            notificaciones = notificacionRepository.saveAll(notificaciones);
            
            // Enviar emails
            for (Notificacion notificacion : notificaciones) {
                boolean enviado = emailService.enviarEmail(notificacion);
                
                if (enviado) {
                    notificacion.setEstadoEntrega(Notificacion.EstadoEntrega.ENVIADA);
                    notificacion.setFechaEnvio(LocalDate.now());
                    enviados++;
                } else {
                    notificacion.setEstadoEntrega(Notificacion.EstadoEntrega.FALLIDA);
                    fallidos++;
                }
            }
            
            notificacionRepository.saveAll(notificaciones);
            
            String mensaje = String.format("Envío masivo completado. Enviados: %d, Fallidos: %d", enviados, fallidos);
            return ApiResponse.success(mensaje);
            
        } catch (Exception e) {
            logger.error("Error al enviar email masivo: {}", e.getMessage());
            return ApiResponse.error("Error al enviar email masivo: " + e.getMessage(), 500);
        }
    }
    
    /**
     * Envía promoción personalizada
     */
    public ApiResponse<NotificacionResponse> enviarPromocionPersonalizada(PromocionPersonalizadaRequest request) {
        try {
            // Validar que la promoción existe
            Optional<Promocion> promocionOpt = promocionRepository.findById(request.getIdPromocion());
            if (promocionOpt.isEmpty()) {
                return ApiResponse.error("Promoción no encontrada", 404);
            }
            
            // Validar que el cliente existe
            Optional<Cliente> clienteOpt = clienteRepository.findById(request.getIdCliente());
            if (clienteOpt.isEmpty()) {
                return ApiResponse.error("Cliente no encontrado", 404);
            }
            
            // Validar que el usuario existe
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(request.getIdUsuario());
            if (usuarioOpt.isEmpty()) {
                return ApiResponse.error("Usuario no encontrado", 404);
            }
            
            Promocion promocion = promocionOpt.get();
            Cliente cliente = clienteOpt.get();
            Usuario usuario = usuarioOpt.get();
            
            // Crear notificación
            Notificacion notificacion = new Notificacion();
            notificacion.setIdCliente(request.getIdCliente());
            notificacion.setIdPromocion(request.getIdPromocion());
            notificacion.setIdUsuario(request.getIdUsuario());
            notificacion.setTipoNotificacion(request.getCanalEnvio());
            notificacion.setCanalEnvio(request.getCanalEnvio());
            notificacion.setMensaje(request.getMensajePersonalizado());
            notificacion.setCliente(cliente);
            notificacion.setPromocion(promocion);
            notificacion.setUsuario(usuario);
            
            // Guardar notificación
            notificacion = notificacionRepository.save(notificacion);
            
            // Enviar según el canal
            boolean enviado = false;
            switch (request.getCanalEnvio().toUpperCase()) {
                case "EMAIL":
                    enviado = emailService.enviarEmail(notificacion);
                    break;
                case "SMS":
                    enviado = smsService.enviarSms(notificacion);
                    break;
                case "WHATSAPP":
                    enviado = whatsAppService.enviarWhatsApp(notificacion);
                    break;
                case "PUSH":
                    enviado = pushNotificationService.enviarPushNotification(notificacion);
                    break;
                default:
                    enviado = emailService.enviarEmail(notificacion);
            }
            
            if (enviado) {
                notificacion.setEstadoEntrega(Notificacion.EstadoEntrega.ENVIADA);
                notificacion.setFechaEnvio(LocalDate.now());
            } else {
                notificacion.setEstadoEntrega(Notificacion.EstadoEntrega.FALLIDA);
            }
            
            notificacion = notificacionRepository.save(notificacion);
            
            return ApiResponse.success("Promoción personalizada enviada exitosamente", 
                                    new NotificacionResponse(notificacion));
            
        } catch (Exception e) {
            logger.error("Error al enviar promoción personalizada: {}", e.getMessage());
            return ApiResponse.error("Error al enviar promoción personalizada: " + e.getMessage(), 500);
        }
    }
    
    /**
     * Obtiene todas las notificaciones
     */
    public ApiResponse<List<NotificacionResponse>> obtenerTodasLasNotificaciones() {
        try {
            List<Notificacion> notificaciones = notificacionRepository.findAll();
            List<NotificacionResponse> responses = notificaciones.stream()
                    .map(NotificacionResponse::new)
                    .collect(Collectors.toList());
            
            return ApiResponse.success("Notificaciones obtenidas exitosamente", responses);
            
        } catch (Exception e) {
            logger.error("Error al obtener notificaciones: {}", e.getMessage());
            return ApiResponse.error("Error al obtener notificaciones: " + e.getMessage(), 500);
        }
    }
    
    /**
     * Obtiene notificaciones por cliente
     */
    public ApiResponse<List<NotificacionResponse>> obtenerNotificacionesPorCliente(Long idCliente) {
        try {
            List<Notificacion> notificaciones = notificacionRepository.findByIdCliente(idCliente);
            List<NotificacionResponse> responses = notificaciones.stream()
                    .map(NotificacionResponse::new)
                    .collect(Collectors.toList());
            
            return ApiResponse.success("Notificaciones del cliente obtenidas exitosamente", responses);
            
        } catch (Exception e) {
            logger.error("Error al obtener notificaciones del cliente: {}", e.getMessage());
            return ApiResponse.error("Error al obtener notificaciones del cliente: " + e.getMessage(), 500);
        }
    }
    
    /**
     * Obtiene notificaciones por promoción
     */
    public ApiResponse<List<NotificacionResponse>> obtenerNotificacionesPorPromocion(Long idPromocion) {
        try {
            List<Notificacion> notificaciones = notificacionRepository.findByIdPromocion(idPromocion);
            List<NotificacionResponse> responses = notificaciones.stream()
                    .map(NotificacionResponse::new)
                    .collect(Collectors.toList());
            
            return ApiResponse.success("Notificaciones de la promoción obtenidas exitosamente", responses);
            
        } catch (Exception e) {
            logger.error("Error al obtener notificaciones de la promoción: {}", e.getMessage());
            return ApiResponse.error("Error al obtener notificaciones de la promoción: " + e.getMessage(), 500);
        }
    }
    
    /**
     * Marca una notificación como leída
     */
    public ApiResponse<NotificacionResponse> marcarComoLeida(Long idNotificacion) {
        try {
            Optional<Notificacion> notificacionOpt = notificacionRepository.findById(idNotificacion);
            if (notificacionOpt.isEmpty()) {
                return ApiResponse.error("Notificación no encontrada", 404);
            }
            
            Notificacion notificacion = notificacionOpt.get();
            notificacion.setFechaLectura(LocalDate.now());
            notificacion.setEstadoEntrega(Notificacion.EstadoEntrega.LEIDA);
            
            notificacion = notificacionRepository.save(notificacion);
            
            return ApiResponse.success("Notificación marcada como leída", new NotificacionResponse(notificacion));
            
        } catch (Exception e) {
            logger.error("Error al marcar notificación como leída: {}", e.getMessage());
            return ApiResponse.error("Error al marcar notificación como leída: " + e.getMessage(), 500);
        }
    }
}
