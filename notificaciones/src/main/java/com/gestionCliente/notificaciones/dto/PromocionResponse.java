package com.gestionCliente.notificaciones.dto;

import com.gestionCliente.notificaciones.entity.Promocion;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PromocionResponse {
    private Long idPromocion;
    private String nombre;
    private String descripcion;
    private String tipoDescuento;
    private BigDecimal valorDescuento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String categoriaAplicable;
    private Boolean estado;
    private String criterioSegmentacion;
    
    public PromocionResponse() {}
    
    public PromocionResponse(Promocion p) {
        this.idPromocion = p.getIdPromocion();
        this.nombre = p.getNombre();
        this.descripcion = p.getDescripcion();
        this.tipoDescuento = p.getTipoDescuento();
        this.valorDescuento = p.getValorDescuento();
        this.fechaInicio = p.getFechaInicio();
        this.fechaFin = p.getFechaFin();
        this.categoriaAplicable = p.getCategoriaAplicable();
        this.estado = p.getEstado();
        this.criterioSegmentacion = p.getCriterioSegmentacion();
    }
    
    // Getters y Setters
    public Long getIdPromocion() { return idPromocion; }
    public void setIdPromocion(Long idPromocion) { this.idPromocion = idPromocion; }
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


