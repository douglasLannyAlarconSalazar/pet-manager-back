package com.gestionCliente.notificaciones.dto;

import jakarta.validation.constraints.NotNull;

public class EmailRequest {
    
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long idCliente;
    
    @NotNull(message = "El ID de la promoción es obligatorio")
    private Long idPromocion;
    
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long idUsuario;
    
    private String mensajePersonalizado;
    
    // Constructores
    public EmailRequest() {}
    
    public EmailRequest(Long idCliente, Long idPromocion, Long idUsuario, String mensajePersonalizado) {
        this.idCliente = idCliente;
        this.idPromocion = idPromocion;
        this.idUsuario = idUsuario;
        this.mensajePersonalizado = mensajePersonalizado;
    }
    
    // Getters y Setters
    public Long getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }
    
    public Long getIdPromocion() {
        return idPromocion;
    }
    
    public void setIdPromocion(Long idPromocion) {
        this.idPromocion = idPromocion;
    }
    
    public Long getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getMensajePersonalizado() {
        return mensajePersonalizado;
    }
    
    public void setMensajePersonalizado(String mensajePersonalizado) {
        this.mensajePersonalizado = mensajePersonalizado;
    }
}
