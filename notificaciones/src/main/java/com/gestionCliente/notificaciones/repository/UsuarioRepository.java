package com.gestionCliente.notificaciones.repository;

import com.gestionCliente.notificaciones.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    /**
     * Buscar usuario por email
     */
    Optional<Usuario> findByEmail(String email);
    
    /**
     * Buscar usuario por nombre de usuario
     */
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    
    /**
     * Buscar usuarios activos
     */
    List<Usuario> findByEstadoTrue();
    
    /**
     * Buscar usuarios por rol
     */
    List<Usuario> findByIdRol(Long idRol);
    
    /**
     * Buscar usuarios activos por rol
     */
    @Query("SELECT u FROM Usuario u WHERE u.idRol = :idRol AND u.estado = true")
    List<Usuario> findUsuariosActivosByIdRol(@Param("idRol") Long idRol);
    
    /**
     * Buscar usuarios por rango de fechas de creación
     */
    @Query("SELECT u FROM Usuario u WHERE u.fechaCreacion BETWEEN :fechaInicio AND :fechaFin ORDER BY u.fechaCreacion DESC")
    List<Usuario> findByFechaCreacionBetween(@Param("fechaInicio") LocalDate fechaInicio, 
                                           @Param("fechaFin") LocalDate fechaFin);
    
    /**
     * Buscar usuarios que no han accedido en un período
     */
    @Query("SELECT u FROM Usuario u WHERE u.ultimoAcceso < :fechaLimite OR u.ultimoAcceso IS NULL")
    List<Usuario> findUsuariosInactivos(@Param("fechaLimite") LocalDate fechaLimite);
    
    /**
     * Contar usuarios activos
     */
    long countByEstadoTrue();
    
    /**
     * Contar usuarios por rol
     */
    long countByIdRol(Long idRol);
}
