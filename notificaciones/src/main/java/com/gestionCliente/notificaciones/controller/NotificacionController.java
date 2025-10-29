package com.gestionCliente.notificaciones.controller;

import com.gestionCliente.notificaciones.dto.*;
import com.gestionCliente.notificaciones.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@Tag(name = "Notificaciones", description = "API para gestión de notificaciones de promociones")
public class NotificacionController {
    
    @Autowired
    private NotificacionService notificacionService;
    
    @PostMapping("/email")
    @Operation(summary = "Enviar promoción por email", 
               description = "Envía una notificación de promoción por correo electrónico")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Email enviado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ApiResponse<NotificacionResponse>> enviarEmail(
            @Valid @RequestBody EmailRequest request) {
        
        ApiResponse<NotificacionResponse> response = notificacionService.enviarEmail(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    @PostMapping("/sms")
    @Operation(summary = "Enviar promoción por SMS", 
               description = "Envía una notificación de promoción por mensaje de texto")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "SMS enviado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ApiResponse<NotificacionResponse>> enviarSms(
            @Valid @RequestBody SmsRequest request) {
        
        ApiResponse<NotificacionResponse> response = notificacionService.enviarSms(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    @PostMapping("/email-masivo")
    @Operation(summary = "Envío masivo por email", 
               description = "Envía notificaciones de promoción por email a múltiples clientes")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Emails masivos enviados exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ApiResponse<String>> enviarEmailMasivo(
            @Valid @RequestBody EmailMasivoRequest request) {
        
        ApiResponse<String> response = notificacionService.enviarEmailMasivo(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    @PostMapping("/personalizada")
    @Operation(summary = "Promoción personalizada", 
               description = "Envía una promoción personalizada basada en el historial de compras del cliente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Promoción personalizada enviada exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ApiResponse<NotificacionResponse>> enviarPromocionPersonalizada(
            @Valid @RequestBody PromocionPersonalizadaRequest request) {
        
        ApiResponse<NotificacionResponse> response = notificacionService.enviarPromocionPersonalizada(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    @GetMapping
    @Operation(summary = "Obtener todas las notificaciones", 
               description = "Retorna todas las notificaciones del sistema")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Notificaciones obtenidas exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ApiResponse<List<NotificacionResponse>>> obtenerTodasLasNotificaciones() {
        
        ApiResponse<List<NotificacionResponse>> response = notificacionService.obtenerTodasLasNotificaciones();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    @GetMapping("/cliente/{idCliente}")
    @Operation(summary = "Obtener notificaciones por cliente", 
               description = "Retorna todas las notificaciones de un cliente específico")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Notificaciones del cliente obtenidas exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ApiResponse<List<NotificacionResponse>>> obtenerNotificacionesPorCliente(
            @Parameter(description = "ID del cliente") @PathVariable Long idCliente) {
        
        ApiResponse<List<NotificacionResponse>> response = notificacionService.obtenerNotificacionesPorCliente(idCliente);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    @GetMapping("/promocion/{idPromocion}")
    @Operation(summary = "Obtener notificaciones por promoción", 
               description = "Retorna todas las notificaciones de una promoción específica")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Notificaciones de la promoción obtenidas exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ApiResponse<List<NotificacionResponse>>> obtenerNotificacionesPorPromocion(
            @Parameter(description = "ID de la promoción") @PathVariable Long idPromocion) {
        
        ApiResponse<List<NotificacionResponse>> response = notificacionService.obtenerNotificacionesPorPromocion(idPromocion);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    @PutMapping("/{idNotificacion}/leida")
    @Operation(summary = "Marcar notificación como leída", 
               description = "Marca una notificación específica como leída")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Notificación marcada como leída exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Notificación no encontrada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ApiResponse<NotificacionResponse>> marcarComoLeida(
            @Parameter(description = "ID de la notificación") @PathVariable Long idNotificacion) {
        
        ApiResponse<NotificacionResponse> response = notificacionService.marcarComoLeida(idNotificacion);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    @GetMapping("/health")
    @Operation(summary = "Health check", 
               description = "Verifica el estado del microservicio")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(ApiResponse.success("Microservicio de notificaciones funcionando correctamente"));
    }
}
