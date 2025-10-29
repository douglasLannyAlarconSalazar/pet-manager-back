package com.gestionCliente.notificaciones.repository;

import com.gestionCliente.notificaciones.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    
    /**
     * Buscar notificaciones por ID de cliente
     */
    List<Notificacion> findByIdCliente(Long idCliente);
    
    /**
     * Buscar notificaciones por ID de promoción
     */
    List<Notificacion> findByIdPromocion(Long idPromocion);
    
    /**
     * Buscar notificaciones por tipo
     */
    List<Notificacion> findByTipoNotificacion(String tipoNotificacion);
    
    /**
     * Buscar notificaciones por canal de envío
     */
    List<Notificacion> findByCanalEnvio(String canalEnvio);
    
    /**
     * Buscar notificaciones por estado de entrega
     */
    List<Notificacion> findByEstadoEntrega(Notificacion.EstadoEntrega estadoEntrega);
    
    /**
     * Buscar notificaciones pendientes
     */
    @Query("SELECT n FROM Notificacion n WHERE n.estadoEntrega = 'PENDIENTE' ORDER BY n.idNotificacion ASC")
    List<Notificacion> findNotificacionesPendientes();
    
    /**
     * Buscar notificaciones por rango de fechas de envío
     */
    @Query("SELECT n FROM Notificacion n WHERE n.fechaEnvio BETWEEN :fechaInicio AND :fechaFin ORDER BY n.fechaEnvio DESC")
    List<Notificacion> findByFechaEnvioBetween(@Param("fechaInicio") LocalDate fechaInicio, 
                                              @Param("fechaFin") LocalDate fechaFin);
    
    /**
     * Contar notificaciones por estado de entrega
     */
    long countByEstadoEntrega(Notificacion.EstadoEntrega estadoEntrega);
    
    /**
     * Buscar notificaciones de un cliente por promoción
     */
    @Query("SELECT n FROM Notificacion n WHERE n.idCliente = :idCliente AND n.idPromocion = :idPromocion")
    List<Notificacion> findByIdClienteAndIdPromocion(@Param("idCliente") Long idCliente, 
                                                    @Param("idPromocion") Long idPromocion);
    
    /**
     * Buscar notificaciones no leídas de un cliente
     */
    @Query("SELECT n FROM Notificacion n WHERE n.idCliente = :idCliente AND n.fechaLectura IS NULL")
    List<Notificacion> findNotificacionesNoLeidasByIdCliente(@Param("idCliente") Long idCliente);
    
    /**
     * Buscar notificaciones por usuario que las creó
     */
    List<Notificacion> findByIdUsuario(Long idUsuario);
}
