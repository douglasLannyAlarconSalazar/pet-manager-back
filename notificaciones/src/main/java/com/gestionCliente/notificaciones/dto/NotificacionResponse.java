package com.gestionCliente.notificaciones.dto;

import com.gestionCliente.notificaciones.entity.Notificacion;
import java.time.LocalDate;

public class NotificacionResponse {
    
    private Long idNotificacion;
    private Long idCliente;
    private Long idPromocion;
    private Long idUsuario;
    private String tipoNotificacion;
    private String mensaje;
    private LocalDate fechaEnvio;
    private String estadoEntrega;
    private String canalEnvio;
    private LocalDate fechaLectura;
    
    // Información adicional de las relaciones
    private String nombreCliente;
    private String emailCliente;
    private String telefonoCliente;
    private String nombrePromocion;
    private String descripcionPromocion;
    private String tipoDescuento;
    private String valorDescuento;
    private String nombreUsuario;
    
    // Constructores
    public NotificacionResponse() {}
    
    public NotificacionResponse(Notificacion notificacion) {
        this.idNotificacion = notificacion.getIdNotificacion();
        this.idCliente = notificacion.getIdCliente();
        this.idPromocion = notificacion.getIdPromocion();
        this.idUsuario = notificacion.getIdUsuario();
        this.tipoNotificacion = notificacion.getTipoNotificacion();
        this.mensaje = notificacion.getMensaje();
        this.fechaEnvio = notificacion.getFechaEnvio();
        this.estadoEntrega = notificacion.getEstadoEntrega().toString();
        this.canalEnvio = notificacion.getCanalEnvio();
        this.fechaLectura = notificacion.getFechaLectura();
        
        // Información adicional si las relaciones están cargadas
        if (notificacion.getCliente() != null) {
            this.nombreCliente = notificacion.getCliente().getNombreCompleto();
            this.emailCliente = notificacion.getCliente().getEmail();
            this.telefonoCliente = notificacion.getCliente().getTelefono();
        }
        
        if (notificacion.getPromocion() != null) {
            this.nombrePromocion = notificacion.getPromocion().getNombre();
            this.descripcionPromocion = notificacion.getPromocion().getDescripcion();
            this.tipoDescuento = notificacion.getPromocion().getTipoDescuento();
            this.valorDescuento = notificacion.getPromocion().getValorDescuento().toString();
        }
        
        if (notificacion.getUsuario() != null) {
            this.nombreUsuario = notificacion.getUsuario().getNombreUsuario();
        }
    }
    
    // Getters y Setters
    public Long getIdNotificacion() {
        return idNotificacion;
    }
    
    public void setIdNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }
    
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
    
    public String getTipoNotificacion() {
        return tipoNotificacion;
    }
    
    public void setTipoNotificacion(String tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public LocalDate getFechaEnvio() {
        return fechaEnvio;
    }
    
    public void setFechaEnvio(LocalDate fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
    
    public String getEstadoEntrega() {
        return estadoEntrega;
    }
    
    public void setEstadoEntrega(String estadoEntrega) {
        this.estadoEntrega = estadoEntrega;
    }
    
    public String getCanalEnvio() {
        return canalEnvio;
    }
    
    public void setCanalEnvio(String canalEnvio) {
        this.canalEnvio = canalEnvio;
    }
    
    public LocalDate getFechaLectura() {
        return fechaLectura;
    }
    
    public void setFechaLectura(LocalDate fechaLectura) {
        this.fechaLectura = fechaLectura;
    }
    
    public String getNombreCliente() {
        return nombreCliente;
    }
    
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
    public String getEmailCliente() {
        return emailCliente;
    }
    
    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }
    
    public String getTelefonoCliente() {
        return telefonoCliente;
    }
    
    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }
    
    public String getNombrePromocion() {
        return nombrePromocion;
    }
    
    public void setNombrePromocion(String nombrePromocion) {
        this.nombrePromocion = nombrePromocion;
    }
    
    public String getDescripcionPromocion() {
        return descripcionPromocion;
    }
    
    public void setDescripcionPromocion(String descripcionPromocion) {
        this.descripcionPromocion = descripcionPromocion;
    }
    
    public String getTipoDescuento() {
        return tipoDescuento;
    }
    
    public void setTipoDescuento(String tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }
    
    public String getValorDescuento() {
        return valorDescuento;
    }
    
    public void setValorDescuento(String valorDescuento) {
        this.valorDescuento = valorDescuento;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
