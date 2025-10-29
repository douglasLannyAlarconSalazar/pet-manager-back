package com.gestionCliente.notificaciones.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "promocion")
public class Promocion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_promocion")
    private Long idPromocion;
    
    @NotBlank(message = "El nombre de la promoción es obligatorio")
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @NotBlank(message = "El tipo de descuento es obligatorio")
    @Column(name = "tipo_descuento", nullable = false)
    private String tipoDescuento; // PORCENTAJE, FIJO
    
    @NotNull(message = "El valor del descuento es obligatorio")
    @Column(name = "valor_descuento", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorDescuento;
    
    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;
    
    @NotNull(message = "La fecha de fin es obligatoria")
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;
    
    @Column(name = "categoria_aplicable")
    private String categoriaAplicable;
    
    @Column(name = "estado", nullable = false)
    private Boolean estado = true;
    
    @Column(name = "criterio_segmentacion", columnDefinition = "TEXT")
    private String criterioSegmentacion;
    
    // Constructores
    public Promocion() {}
    
    public Promocion(String nombre, String descripcion, String tipoDescuento, 
                    BigDecimal valorDescuento, LocalDate fechaInicio, LocalDate fechaFin) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoDescuento = tipoDescuento;
        this.valorDescuento = valorDescuento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    
    // Getters y Setters
    public Long getIdPromocion() {
        return idPromocion;
    }
    
    public void setIdPromocion(Long idPromocion) {
        this.idPromocion = idPromocion;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getTipoDescuento() {
        return tipoDescuento;
    }
    
    public void setTipoDescuento(String tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }
    
    public BigDecimal getValorDescuento() {
        return valorDescuento;
    }
    
    public void setValorDescuento(BigDecimal valorDescuento) {
        this.valorDescuento = valorDescuento;
    }
    
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public String getCategoriaAplicable() {
        return categoriaAplicable;
    }
    
    public void setCategoriaAplicable(String categoriaAplicable) {
        this.categoriaAplicable = categoriaAplicable;
    }
    
    public Boolean getEstado() {
        return estado;
    }
    
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    
    public String getCriterioSegmentacion() {
        return criterioSegmentacion;
    }
    
    public void setCriterioSegmentacion(String criterioSegmentacion) {
        this.criterioSegmentacion = criterioSegmentacion;
    }
}
