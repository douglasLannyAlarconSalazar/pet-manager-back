package com.petmanager.preferencias.backend.repository;

import com.petmanager.preferencias.backend.model.Preferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferenciaRepository extends JpaRepository<Preferencia, Long> {
    
    List<Preferencia> findByClienteIdCliente(Long idCliente);
}