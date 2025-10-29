package com.gestionCliente.notificaciones.repository;

import com.gestionCliente.notificaciones.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    /**
     * Buscar cliente por email
     */
    Optional<Cliente> findByEmail(String email);
    
    /**
     * Buscar clientes activos
     */
    List<Cliente> findByEstadoTrue();
    
    /**
     * Buscar clientes por nombre o apellido
     */
    @Query("SELECT c FROM Cliente c WHERE c.nombre LIKE %:nombre% OR c.apellido LIKE %:nombre%")
    List<Cliente> findByNombreContaining(@Param("nombre") String nombre);
    
    /**
     * Buscar clientes por rango de fechas de registro
     */
    @Query("SELECT c FROM Cliente c WHERE c.fechaRegistro BETWEEN :fechaInicio AND :fechaFin ORDER BY c.fechaRegistro DESC")
    List<Cliente> findByFechaRegistroBetween(@Param("fechaInicio") LocalDate fechaInicio, 
                                           @Param("fechaFin") LocalDate fechaFin);
    
    /**
     * Buscar clientes con puntos de fidelidad mayores a un valor
     */
    @Query("SELECT c FROM Cliente c WHERE c.puntosFidelidad >= :puntosMinimos ORDER BY c.puntosFidelidad DESC")
    List<Cliente> findByPuntosFidelidadGreaterThanEqual(@Param("puntosMinimos") Integer puntosMinimos);
    
    /**
     * Contar clientes activos
     */
    long countByEstadoTrue();
    
    /**
     * Buscar clientes por teléfono
     */
    Optional<Cliente> findByTelefono(String telefono);
}
