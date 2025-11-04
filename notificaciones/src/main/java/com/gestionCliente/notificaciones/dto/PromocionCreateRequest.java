package com.gestionCliente.notificaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PromocionCreateRequest {
    
    @NotBlank(message = "El nombre de la promoción es obligatorio")
    private String nombre;
    
    private String descripcion;
    
    @NotBlank(message = "El tipo de descuento es obligatorio")
    private String tipoDescuento; // PORCENTAJE, FIJO
    
    @NotNull(message = "El valor del descuento es obligatorio")
    private BigDecimal valorDescuento;
    
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;
    
    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;
    
    private String categoriaAplicable;
    
    private Boolean estado = true;
    
    private String criterioSegmentacion;
    
    public PromocionCreateRequest() {}
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getTipoDescuento() { return tipoDescuento; }
    public void setTipoDescuento(String tipoDescuento) { this.tipoDescuento = tipoDescuento; }
    public BigDecimal getValorDescuento() { return valorDescuento; }
    public void setValorDescuento(BigDecimal valorDescuento) { this.valorDescuento = valorDescuento; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public String getCategoriaAplicable() { return categoriaAplicable; }
    public void setCategoriaAplicable(String categoriaAplicable) { this.categoriaAplicable = categoriaAplicable; }
    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }
    public String getCriterioSegmentacion() { return criterioSegmentacion; }
    public void setCriterioSegmentacion(String criterioSegmentacion) { this.criterioSegmentacion = criterioSegmentacion; }
}


