package com.petmanager.preferencias.backend.service;

import com.petmanager.preferencias.backend.model.Preferencia;
import com.petmanager.preferencias.backend.repository.PreferenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PreferenciaService {

    private final PreferenciaRepository preferenciaRepository;

    /**
     * Obtiene todas las preferencias.
     */
    public List<Preferencia> findAll() {
        return preferenciaRepository.findAll();
    }

    /**
     * Busca una preferencia por su ID.
     */
    public Optional<Preferencia> findById(Long id) {
        return preferenciaRepository.findById(id);
    }

    /**
     * Crea o actualiza una preferencia.
     */
    public Preferencia save(Preferencia preferencia) {
        return preferenciaRepository.save(preferencia);
    }

    /**
     * Elimina una preferencia por su ID.
     */
    public boolean deleteById(Long id) {
        if (!preferenciaRepository.existsById(id)) {
            return false;
        }
        preferenciaRepository.deleteById(id);
        return true;
    }

    /**
     * Obtiene todas las preferencias asociadas a un cliente específico.
     */
    public List<Preferencia> findByClienteId(Long idCliente) {
        return preferenciaRepository.findByClienteIdCliente(idCliente);
    }
}
