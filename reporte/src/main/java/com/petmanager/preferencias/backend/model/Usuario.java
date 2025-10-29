package com.petmanager.preferencias.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(
    name = "usuario",
    uniqueConstraints = {
        @UniqueConstraint(name = "usuario_email_key", columnNames = "email")
    },
    indexes = {
        @Index(name = "usuario_email_idx", columnList = "email", unique = true),
        @Index(name = "usuario_pkey", columnList = "id_usuario", unique = true)
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre_usuario", length = 100)
    private String nombreUsuario;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "password_hash", length = 255)
    private String passwordHash;

    @Column(name = "id_rol")
    private Integer idRol;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(name = "ultimo_acceso")
    private LocalDate ultimoAcceso;
}

