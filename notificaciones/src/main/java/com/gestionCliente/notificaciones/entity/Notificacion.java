package com.gestionCliente.notificaciones.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "notificaciones")
public class Notificacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private Long idNotificacion;
    
    @NotNull(message = "El ID del cliente es obligatorio")
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;
    
    @NotNull(message = "El ID de la promoción es obligatorio")
    @Column(name = "id_promocion", nullable = false)
    private Long idPromocion;
    
    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;
    
    @NotBlank(message = "El tipo de notificación es obligatorio")
    @Column(name = "tipo_notificacion", nullable = false)
    private String tipoNotificacion; // EMAIL, SMS, WHATSAPP, PUSH
    
    @Column(name = "mensaje", columnDefinition = "TEXT")
    private String mensaje;
    
    @Column(name = "fecha_envio")
    private LocalDate fechaEnvio;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_entrega", nullable = false)
    private EstadoEntrega estadoEntrega = EstadoEntrega.PENDIENTE;
    
    @NotBlank(message = "El canal de envío es obligatorio")
    @Column(name = "canal_envio", nullable = false)
    private String canalEnvio; // EMAIL, SMS, WHATSAPP, PUSH
    
    @Column(name = "fecha_lectura")
    private LocalDate fechaLectura;
    
    // Relaciones
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", insertable = false, updatable = false)
    private Cliente cliente;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_promocion", insertable = false, updatable = false)
    private Promocion promocion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;
    
    // Constructores
    public Notificacion() {}
    
    public Notificacion(Long idCliente, Long idPromocion, Long idUsuario, 
                       String tipoNotificacion, String mensaje, String canalEnvio) {
        this.idCliente = idCliente;
        this.idPromocion = idPromocion;
        this.idUsuario = idUsuario;
        this.tipoNotificacion = tipoNotificacion;
        this.mensaje = mensaje;
        this.canalEnvio = canalEnvio;
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
    
    public EstadoEntrega getEstadoEntrega() {
        return estadoEntrega;
    }
    
    public void setEstadoEntrega(EstadoEntrega estadoEntrega) {
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
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Promocion getPromocion() {
        return promocion;
    }
    
    public void setPromocion(Promocion promocion) {
        this.promocion = promocion;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    // Enum para el estado de entrega
    public enum EstadoEntrega {
        PENDIENTE,
        ENVIADA,
        ENTREGADA,
        LEIDA,
        FALLIDA
    }
}
