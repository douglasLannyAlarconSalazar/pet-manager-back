package com.gestionCliente.notificaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PromocionPersonalizadaRequest {
    
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long idCliente;
    
    @NotNull(message = "El ID de la promoción es obligatorio")
    private Long idPromocion;
    
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long idUsuario;
    
    @NotBlank(message = "El canal de envío es obligatorio")
    private String canalEnvio; // EMAIL, SMS, WHATSAPP, PUSH
    
    private String mensajePersonalizado;
    
    // Constructores
    public PromocionPersonalizadaRequest() {}
    
    public PromocionPersonalizadaRequest(Long idCliente, Long idPromocion, Long idUsuario, 
                                       String canalEnvio, String mensajePersonalizado) {
        this.idCliente = idCliente;
        this.idPromocion = idPromocion;
        this.idUsuario = idUsuario;
        this.canalEnvio = canalEnvio;
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
    
    public String getCanalEnvio() {
        return canalEnvio;
    }
    
    public void setCanalEnvio(String canalEnvio) {
        this.canalEnvio = canalEnvio;
    }
    
    public String getMensajePersonalizado() {
        return mensajePersonalizado;
    }
    
    public void setMensajePersonalizado(String mensajePersonalizado) {
        this.mensajePersonalizado = mensajePersonalizado;
    }
}
