package com.gestionCliente.gestion_cliente.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "\"Cliente\"")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"id_cliente\"")
    private Long idCliente;
    
    @Column(name = "\"nombre\"", nullable = false)
    private String nombre;
    
    @Column(name = "\"apellido\"", nullable = false)
    private String apellido;
    
    @Column(name = "\"email\"", unique = true, nullable = false)
    private String email;
    
    @Column(name = "\"telefono\"")
    private String telefono;
    
    @Column(name = "\"direccion\"")
    private String direccion;
    
    @Column(name = "\"fecha_registro\"", nullable = false)
    private LocalDate fechaRegistro;
    
    @Column(name = "\"estado\"", nullable = false)
    private Boolean estado;
    
    @Column(name = "\"puntos_fidelidad\"")
    private Integer puntosFidelidad;
    
    // Constructores
    public Cliente() {
    }
    
    public Cliente(String nombre, String apellido, String email, String telefono, 
                   String direccion, LocalDate fechaRegistro, Boolean estado, Integer puntosFidelidad) {
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
        return "Cliente{" +
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
