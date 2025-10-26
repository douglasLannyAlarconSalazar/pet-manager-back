package com.petmanager.preferencias.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "preferencias", indexes = {
    @Index(name = "idx_preferencia_cliente", columnList = "id_cliente")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "cliente")
public class Preferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_preferencia")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(name = "tipo_mascota", length = 50)
    private String tipoMascota;

    @Column(name = "marca_preferida", length = 100)
    private String marcaPreferida;

    @Column(name = "categoria_interes", length = 100)
    private String categoriaInteres;

    @Column(name = "frecuencia_compra", length = 50)
    private String frecuenciaCompra;

    @Column(name = "presupuesto_promedio", precision = 10, scale = 2)
    private BigDecimal presupuestoPromedio;

    @Column(name = "fecha_actualizacion")
    private LocalDate fechaActualizacion;

    @PrePersist
    @PreUpdate
    public void actualizarFecha() {
        this.fechaActualizacion = LocalDate.now();
    }
}
