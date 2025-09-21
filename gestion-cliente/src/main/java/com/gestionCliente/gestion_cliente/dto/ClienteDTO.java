package com.gestionCliente.gestion_cliente.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "DTO para la gestión de clientes")
public class ClienteDTO {
    
    @Schema(description = "ID único del cliente", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idCliente;
    
    @Schema(description = "Nombre del cliente", example = "Juan", required = true)
    private String nombre;
    
    @Schema(description = "Apellido del cliente", example = "Pérez", required = true)
    private String apellido;
    
    @Schema(description = "Email único del cliente", example = "juan.perez@email.com", required = true)
    private String email;
    
    @Schema(description = "Número de teléfono del cliente", example = "+57 300 123 4567")
    private String telefono;
    
    @Schema(description = "Dirección del cliente", example = "Calle 123 #45-67, Bogotá")
    private String direccion;
    
    @Schema(description = "Fecha de registro del cliente", example = "2024-01-15", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate fechaRegistro;
    
    @Schema(description = "Estado del cliente (activo/inactivo)", example = "true", required = true)
    private Boolean estado;
    
    @Schema(description = "Puntos de fidelidad acumulados", example = "150", minimum = "0")
    private Integer puntosFidelidad;
    
    // Constructores
    public ClienteDTO() {
    }
    
    public ClienteDTO(Long idCliente, String nombre, String apellido, String email, 
                      String telefono, String direccion, LocalDate fechaRegistro, 
                      Boolean estado, Integer puntosFidelidad) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.puntosFidelidad = puntosFidelidad;
    }
    
    // Getters y Setters
    public Long getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public Boolean getEstado() {
        return estado;
    }
    
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    
    public Integer getPuntosFidelidad() {
        return puntosFidelidad;
    }
    
    public void setPuntosFidelidad(Integer puntosFidelidad) {
        this.puntosFidelidad = puntosFidelidad;
    }
    
    @Override
    public String toString() {
        return "ClienteDTO{" +
                "idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", estado=" + estado +
                ", puntosFidelidad=" + puntosFidelidad +
                '}';
    }
}
