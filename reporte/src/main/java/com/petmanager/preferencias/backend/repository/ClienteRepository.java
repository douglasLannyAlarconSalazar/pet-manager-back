package com.petmanager.preferencias.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.petmanager.preferencias.backend.model.Cliente;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c WHERE c.puntosFidelidad > 100 ORDER BY c.puntosFidelidad DESC")
    List<Cliente> findClientesFrecuentes();
    
    Cliente findByEmail(String email);
    
    boolean existsByEmail(String email);
}
