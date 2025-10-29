package com.petmanager.preferencias.backend.service;

import com.petmanager.preferencias.backend.dto.ClienteDTO;
import com.petmanager.preferencias.backend.mapper.ClienteMapper;
import com.petmanager.preferencias.backend.model.Cliente;
import com.petmanager.preferencias.backend.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    /**
     * Obtiene todos los clientes
     */
    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toDTO)
                .toList();
    }

    /**
     * Busca un cliente por ID
     */
    public Optional<ClienteDTO> findById(Long id) {
        return clienteRepository.findById(id)
                .map(clienteMapper::toDTO);
    }

    /**
     * Crea un nuevo cliente
     */
    public ClienteDTO create(ClienteDTO clienteDTO) {
        clienteDTO.setId_cliente(null);
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        cliente.setFechaRegistro(LocalDate.now());
        if (cliente.getEstado() == null) {
            cliente.setEstado(true);
        }
        Cliente guardado = clienteRepository.save(cliente);
        return clienteMapper.toDTO(guardado);
    }

    /**
     * Actualiza un cliente existente
     */
    public Optional<ClienteDTO> update(Long id, ClienteDTO clienteDTO) {
        return clienteRepository.findById(id).map(existing -> {
            clienteDTO.setId_cliente(id);
            clienteDTO.setFecha_registro(existing.getFechaRegistro());
            Cliente actualizado = clienteRepository.save(clienteMapper.toEntity(clienteDTO));
            return clienteMapper.toDTO(actualizado);
        });
    }

    /**
     * Elimina un cliente por ID
     */
    public boolean deleteById(Long id) {
        if (!clienteRepository.existsById(id)) {
            return false;
        }
        clienteRepository.deleteById(id);
        return true;
    }

    /**
     * Busca un cliente por email
     */
    public Optional<ClienteDTO> findByEmail(String email) {
        return Optional.ofNullable(clienteRepository.findByEmail(email))
                .map(clienteMapper::toDTO);
    }

    /**
     * Verifica si existe un cliente con el email dado
     */
    public boolean existsByEmail(String email) {
        return clienteRepository.existsByEmail(email);
    }
}
