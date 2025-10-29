package com.gestionCliente.notificaciones.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class EmailMasivoRequest {
    
    @NotEmpty(message = "La lista de IDs de clientes no puede estar vacía")
    private List<Long> idClientes;
    
    @NotNull(message = "El ID de la promoción es obligatorio")
    private Long idPromocion;
    
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long idUsuario;
    
    private String mensajePersonalizado;
    
    // Constructores
    public EmailMasivoRequest() {}
    
    public EmailMasivoRequest(List<Long> idClientes, Long idPromocion, Long idUsuario, String mensajePersonalizado) {
        this.idClientes = idClientes;
        this.idPromocion = idPromocion;
        this.idUsuario = idUsuario;
        this.mensajePersonalizado = mensajePersonalizado;
    }
    
    // Getters y Setters
    public List<Long> getIdClientes() {
        return idClientes;
    }
    
    public void setIdClientes(List<Long> idClientes) {
        this.idClientes = idClientes;
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
