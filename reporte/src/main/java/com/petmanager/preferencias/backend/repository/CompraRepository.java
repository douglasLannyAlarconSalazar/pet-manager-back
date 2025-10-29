package com.petmanager.preferencias.backend.repository;


import com.petmanager.preferencias.backend.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    // Resumen por cliente (cantidad de compras y monto total)
    @Query("SELECT c.cliente.idCliente, COUNT(c), SUM(c.montoTotal) " +
       "FROM Compra c " +
       "WHERE c.estadoCompra = 'COMPLETADA' " +
       "GROUP BY c.cliente.idCliente")
    List<Object[]> obtenerResumenCompras();

}
