package com.petmanager.preferencias.backend.dto;

import com.petmanager.preferencias.backend.model.Reporte;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReporteDTO {
    private Long idReporte;
    private String nombreReporte;
    private String tipoReporte;
    private String estado;
    private LocalDate fechaGeneracion;
    private LocalDate fechaExpiracion;
    private String parametros;
    private Long idUsuario;

    public static ReporteDTO fromEntity(Reporte reporte) {
        return ReporteDTO.builder()
                .idReporte(reporte.getIdReporte())
                .nombreReporte(reporte.getNombreReporte())
                .tipoReporte(reporte.getTipoReporte())
                .estado(reporte.getEstado())
                .fechaGeneracion(reporte.getFechaGeneracion())
                .fechaExpiracion(reporte.getFechaExpiracion())
                .parametros(reporte.getParametros())
                .idUsuario(reporte.getUsuario() != null ? reporte.getUsuario().getIdUsuario() : null)
                .build();
    }
}
