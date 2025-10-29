package com.petmanager.preferencias.backend.controller;

import com.petmanager.preferencias.backend.dto.ReporteDTO;
import com.petmanager.preferencias.backend.service.ReportePDFService;
import com.petmanager.preferencias.backend.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReporteController {

    private final ReporteService reporteService;
    private final ReportePDFService reportePDFService;

    @GetMapping("/clientes-frecuentes")
    public ResponseEntity<?> obtenerReporteClientesFrecuentes() {
        try {
            return ResponseEntity.ok(reporteService.generarReporteClientesFrecuentes());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al generar el reporte de clientes frecuentes.");
        }
    }

    @GetMapping("/clientes-frecuentes/pdf")
    public ResponseEntity<byte[]> descargarReportePDF() {
        try {
            ByteArrayInputStream pdfStream = reportePDFService.generarPDFClientesFrecuentes();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=clientes_frecuentes.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfStream.readAllBytes());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // Listar todos los reportes guardados
    @GetMapping
    public ResponseEntity<List<ReporteDTO>> listarReportes() {
        return ResponseEntity.ok(reporteService.obtenerTodosLosReportes());
    }

    // Listar reportes activos (no expirados)
    @GetMapping("/activos")
    public ResponseEntity<List<ReporteDTO>> listarReportesActivos() {
        List<ReporteDTO> reportesActivos = reporteService.findReportesActivos(LocalDate.now());
        return ResponseEntity.ok(reportesActivos);
    }

    // Buscar un reporte por ID 
    @GetMapping("/{id}")
    public ResponseEntity<ReporteDTO> obtenerReportePorId(@PathVariable Long id) {
        return reporteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}