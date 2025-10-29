package com.petmanager.preferencias.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "reporte")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "usuario")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reporte")
    private Long idReporte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "tipo_reporte", length = 50, nullable = false)
    private String tipoReporte;

    @Column(name = "nombre_reporte", length = 100, nullable = false)
    private String nombreReporte;

    @Lob
    @Column(name = "parametros", columnDefinition = "TEXT")
    private String parametros;

    @Column(name = "fecha_generacion", nullable = false)
    private LocalDate fechaGeneracion;

    @Column(name = "ruta_archivo")
    private String rutaArchivo;

    @Column(name = "estado", length = 20)
    private String estado;

    @Column(name = "fecha_expiracion")
    private LocalDate fechaExpiracion;

    @PrePersist
    public void prePersist() {
        this.fechaGeneracion = LocalDate.now();
        if (this.estado == null) {
            this.estado = "GENERADO";
        }
    }
}
