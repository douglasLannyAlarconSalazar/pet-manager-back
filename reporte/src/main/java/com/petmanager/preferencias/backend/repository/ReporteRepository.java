package com.petmanager.preferencias.backend.repository;

import com.petmanager.preferencias.backend.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {

    // Buscar todos los reportes de un usuario
    @Query("SELECT r FROM Reporte r WHERE r.usuario.id = :idUsuario")
    List<Reporte> findByIdUsuario(@Param("idUsuario") Long idUsuario);

    // Buscar por tipo de reporte
    List<Reporte> findByTipoReporte(String tipoReporte);

    // Buscar reportes generados entre dos fechas
    List<Reporte> findByFechaGeneracionBetween(LocalDate inicio, LocalDate fin);

    // Buscar reportes activos (no expirados y con estado GENERADO)
    @Query("SELECT r FROM Reporte r WHERE (r.fechaExpiracion IS NULL OR r.fechaExpiracion > :hoy) AND r.estado = 'GENERADO'")
    List<Reporte> findReportesActivos(@Param("hoy") LocalDate hoy);

    // Buscar reportes por tipo y usuario
    @Query("SELECT r FROM Reporte r WHERE r.usuario.id = :idUsuario AND r.tipoReporte = :tipoReporte")
    List<Reporte> findByIdUsuarioAndTipoReporte(@Param("idUsuario") Long idUsuario, @Param("tipoReporte") String tipoReporte);
}
