package com.gestionCliente.gestion_cliente.repository;

import com.gestionCliente.gestion_cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    /**
     * Busca un cliente por email
     * @param email El email del cliente
     * @return Cliente encontrado o null si no existe
     */
    Cliente findByEmail(String email);
    
    /**
     * Verifica si existe un cliente con el email dado
     * @param email El email a verificar
     * @return true si existe, false si no
     */
    boolean existsByEmail(String email);
}
