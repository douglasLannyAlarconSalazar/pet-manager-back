package com.petmanager.preferencias.backend.dto;

import com.petmanager.preferencias.backend.model.Cliente;
import com.petmanager.preferencias.backend.model.Preferencia;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteClientesFrecuentesDTO {

    private String nombre;
    private String email;
    private Integer puntosFidelidad;
    private List<String> preferencias;
    private Long numeroCompras;
    private BigDecimal totalGastado;
    private List<String> productosFavoritos;

    public ReporteClientesFrecuentesDTO(
            Cliente cliente,
            List<Preferencia> preferencias,
            Long numeroCompras,
            BigDecimal totalGastado,
            List<String> productosFavoritos
    ) {
        this.nombre = cliente.getNombre() + " " + cliente.getApellido();
        this.email = cliente.getEmail();
        this.puntosFidelidad = cliente.getPuntosFidelidad();
        this.numeroCompras = numeroCompras;
        this.totalGastado = totalGastado;
        this.productosFavoritos = productosFavoritos;

        this.preferencias = preferencias.stream()
                .map(p -> String.format("%s - %s", p.getTipoMascota(), p.getCategoriaInteres()))
                .collect(Collectors.toList());
    }
}
