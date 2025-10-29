package com.petmanager.preferencias.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petmanager.preferencias.backend.dto.ReporteClientesFrecuentesDTO;
import com.petmanager.preferencias.backend.dto.ReporteDTO;
import com.petmanager.preferencias.backend.model.*;
import com.petmanager.preferencias.backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReporteService {

    private final ClienteRepository clienteRepository;
    private final PreferenciaRepository preferenciaRepository;
    private final CompraRepository compraRepository;
    private final DetalleCompraRepository detalleCompraRepository;
    private final ReporteRepository reporteRepository;
    private final ObjectMapper objectMapper; 

    /* ============================================================
     *  MÉTODO PRINCIPAL: Genera el reporte de clientes frecuentes
     * ============================================================ */
    public List<ReporteClientesFrecuentesDTO> generarReporteClientesFrecuentes() {
        List<Cliente> clientes = clienteRepository.findClientesFrecuentes();
        if (clientes == null || clientes.isEmpty()) {
            log.warn("No se encontraron clientes frecuentes.");
            return Collections.emptyList();
        }

        // Resumen de compras por cliente: [idCliente, numCompras, totalGastado]
        Map<Long, Object[]> resumenCompras = compraRepository.obtenerResumenCompras().stream()
                .collect(Collectors.toMap(
                        r -> (Long) r[0],
                        r -> r,
                        (a, b) -> a
                ));

        List<ReporteClientesFrecuentesDTO> reporte = new ArrayList<>();

        for (Cliente cliente : clientes) {
            Long id = cliente.getIdCliente();

            List<Preferencia> preferencias = preferenciaRepository.findByClienteIdCliente(id);

            Object[] resumen = resumenCompras.getOrDefault(id, new Object[]{id, 0L, BigDecimal.ZERO});
            Long numCompras = (Long) resumen[1];
            BigDecimal totalGastado = (BigDecimal) resumen[2];

            List<Object[]> favoritos = detalleCompraRepository.obtenerProductosFavoritos(id);
            List<String> productosFavoritos = favoritos.stream()
                    .limit(3)
                    .map(p -> "Producto " + p[0] + " (x" + p[1] + ")")
                    .collect(Collectors.toList());

            ReporteClientesFrecuentesDTO dto = new ReporteClientesFrecuentesDTO(
                    cliente,
                    preferencias,
                    numCompras,
                    totalGastado,
                    productosFavoritos
            );

            reporte.add(dto);
        }

        guardarReporteEnBD(reporte);
        return reporte;
    }

    /* ============================================================
     *  Guarda el reporte generado en la base de datos
     * ============================================================ */
    private void guardarReporteEnBD(List<ReporteClientesFrecuentesDTO> reporteData) {
        try {
            String parametrosJson = objectMapper.writeValueAsString(reporteData);

            // Verifica que el usuario con id=1 exista en tu BD
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(1L);

            Reporte nuevoReporte = Reporte.builder()
                    .usuario(usuario)
                    .tipoReporte("CLIENTES_FRECUENTES")
                    .nombreReporte("Reporte de Clientes Frecuentes")
                    .parametros(parametrosJson)
                    .fechaGeneracion(LocalDate.now())
                    .estado("GENERADO")
                    .fechaExpiracion(LocalDate.now().plusDays(30))
                    .build();

            reporteRepository.save(nuevoReporte);
            log.info("Reporte guardado correctamente (id={}).", nuevoReporte.getIdReporte());

        } catch (Exception e) {
            log.error("Error guardando reporte en BD", e);
            throw new RuntimeException("Error al guardar el reporte en la base de datos", e);
        }
    }

    /* ============================================================
     *  Métodos de consulta para el controlador
     * ============================================================ */

    /**
     * Devuelve todos los reportes guardados (en formato DTO).
     */
    @Transactional(readOnly = true)
    public List<ReporteDTO> obtenerTodosLosReportes() {
        List<Reporte> reportes = reporteRepository.findAll();
        return reportes.stream()
                .map(ReporteDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Devuelve los reportes activos (no expirados) respecto a la fecha indicada.
     */
    @Transactional(readOnly = true)
    public List<ReporteDTO> findReportesActivos(LocalDate fecha) {
        return reporteRepository.findReportesActivos(fecha).stream()
                .map(ReporteDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca un reporte por id.
     */
    @Transactional(readOnly = true)
    public Optional<ReporteDTO> findById(Long id) {
        return reporteRepository.findById(id)
                .map(ReporteDTO::fromEntity);
    }
}
