package com.petmanager.preferencias.backend.repository;

import com.petmanager.preferencias.backend.model.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Long> {

    @Query("SELECT d.idProducto, SUM(d.cantidad) " +
           "FROM DetalleCompra d " +
           "JOIN d.compra c " +
           "WHERE c.cliente.idCliente = :idCliente AND c.estadoCompra = 'COMPLETADA' " +
           "GROUP BY d.idProducto " +
           "ORDER BY SUM(d.cantidad) DESC")
    List<Object[]> obtenerProductosFavoritos(@Param("idCliente") Long idCliente);
}

