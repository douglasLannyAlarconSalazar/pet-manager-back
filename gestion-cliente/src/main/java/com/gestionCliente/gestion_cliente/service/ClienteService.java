package com.gestionCliente.gestion_cliente.service;

import com.gestionCliente.gestion_cliente.dto.ClienteDTO;
import com.gestionCliente.gestion_cliente.entity.Cliente;
import com.gestionCliente.gestion_cliente.mapper.ClienteMapper;
import com.gestionCliente.gestion_cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ClienteMapper clienteMapper;
    
    /**
     * Obtiene todos los clientes
     * @return Lista de ClienteDTO
     */
    public List<ClienteDTO> findAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca un cliente por ID
     * @param id El ID del cliente
     * @return ClienteDTO si existe, null si no
     */
    public ClienteDTO findById(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.map(clienteMapper::toDTO).orElse(null);
    }
    
    /**
     * Crea un nuevo cliente
     * @param clienteDTO El DTO del cliente a crear (sin ID)
     * @return ClienteDTO creado con ID generado
     */
    public ClienteDTO create(ClienteDTO clienteDTO) {
        // Asegurar que el ID sea null para crear un nuevo cliente
        clienteDTO.setIdCliente(null);
        
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        cliente.setFechaRegistro(java.time.LocalDate.now());
        Cliente clienteGuardado = clienteRepository.save(cliente);
        return clienteMapper.toDTO(clienteGuardado);
    }
    
    /**
     * Guarda un cliente (crear o actualizar)
     * @param clienteDTO El DTO del cliente a guardar
     * @return ClienteDTO guardado
     */
    public ClienteDTO save(ClienteDTO clienteDTO) {
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        Cliente clienteGuardado = clienteRepository.save(cliente);
        return clienteMapper.toDTO(clienteGuardado);
    }
    
    /**
     * Actualiza un cliente existente
     * @param id El ID del cliente a actualizar
     * @param clienteDTO El DTO con los nuevos datos
     * @return ClienteDTO actualizado o null si no existe
     */
    public ClienteDTO update(Long id, ClienteDTO clienteDTO) {
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);
        if (clienteExistente.isEmpty()) {
            return null;
        }
        
        // Preservar la fecha de registro original
        LocalDate fechaRegistroOriginal = clienteExistente.get().getFechaRegistro();
        
        clienteDTO.setIdCliente(id);
        clienteDTO.setFechaRegistro(fechaRegistroOriginal);
        
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        Cliente clienteActualizado = clienteRepository.save(cliente);
        return clienteMapper.toDTO(clienteActualizado);
    }
    
    /**
     * Elimina un cliente por ID
     * @param id El ID del cliente a eliminar
     * @return true si se eliminó, false si no existía
     */
    public boolean deleteById(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Busca un cliente por email
     * @param email El email del cliente
     * @return ClienteDTO si existe, null si no
     */
    public ClienteDTO findByEmail(String email) {
        Cliente cliente = clienteRepository.findByEmail(email);
        return cliente != null ? clienteMapper.toDTO(cliente) : null;
    }
    
    /**
     * Verifica si existe un cliente con el email dado
     * @param email El email a verificar
     * @return true si existe, false si no
     */
    public boolean existsByEmail(String email) {
        return clienteRepository.existsByEmail(email);
    }
}
