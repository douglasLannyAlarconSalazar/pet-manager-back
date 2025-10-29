package com.gestionCliente.notificaciones.repository;

import com.gestionCliente.notificaciones.entity.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    
    /**
     * Buscar promociones activas
     */
    @Query("SELECT p FROM Promocion p WHERE p.estado = true AND p.fechaInicio <= :fecha AND p.fechaFin >= :fecha")
    List<Promocion> findPromocionesActivas(@Param("fecha") LocalDate fecha);
    
    /**
     * Buscar promociones por categoría aplicable
     */
    List<Promocion> findByCategoriaAplicable(String categoriaAplicable);
    
    /**
     * Buscar promociones por tipo de descuento
     */
    List<Promocion> findByTipoDescuento(String tipoDescuento);
    
    /**
     * Buscar promociones por rango de fechas
     */
    @Query("SELECT p FROM Promocion p WHERE p.fechaInicio BETWEEN :fechaInicio AND :fechaFin OR p.fechaFin BETWEEN :fechaInicio AND :fechaFin")
    List<Promocion> findByRangoFechas(@Param("fechaInicio") LocalDate fechaInicio, 
                                     @Param("fechaFin") LocalDate fechaFin);
    
    /**
     * Buscar promociones que están por vencer
     */
    @Query("SELECT p FROM Promocion p WHERE p.estado = true AND p.fechaFin BETWEEN :fechaInicio AND :fechaFin")
    List<Promocion> findPromocionesPorVencer(@Param("fechaInicio") LocalDate fechaInicio, 
                                            @Param("fechaFin") LocalDate fechaFin);
    
    /**
     * Contar promociones activas
     */
    @Query("SELECT COUNT(p) FROM Promocion p WHERE p.estado = true AND p.fechaInicio <= :fecha AND p.fechaFin >= :fecha")
    long countPromocionesActivas(@Param("fecha") LocalDate fecha);
}
