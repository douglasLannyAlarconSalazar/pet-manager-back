package com.gestionCliente.gestion_cliente.mapper;

import com.gestionCliente.gestion_cliente.dto.ClienteDTO;
import com.gestionCliente.gestion_cliente.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapperImpl implements ClienteMapper {
    
    @Override
    public ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setIdCliente(cliente.getIdCliente());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setApellido(cliente.getApellido());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setTelefono(cliente.getTelefono());
        clienteDTO.setDireccion(cliente.getDireccion());
        clienteDTO.setFechaRegistro(cliente.getFechaRegistro());
        clienteDTO.setEstado(cliente.getEstado());
        clienteDTO.setPuntosFidelidad(cliente.getPuntosFidelidad());
        
        return clienteDTO;
    }
    
    @Override
    public Cliente toEntity(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            return null;
        }
        
        Cliente cliente = new Cliente();
        // Solo establecer el ID si no es null (para actualizaciones)
        if (clienteDTO.getIdCliente() != null) {
            cliente.setIdCliente(clienteDTO.getIdCliente());
        }
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setDireccion(clienteDTO.getDireccion());
        // No establecer fechaRegistro desde DTO - se maneja automáticamente en el servicio
        if (clienteDTO.getIdCliente() != null) {
            // Solo para actualizaciones, mantener la fecha original
            cliente.setFechaRegistro(clienteDTO.getFechaRegistro());
        }
        cliente.setEstado(clienteDTO.getEstado());
        cliente.setPuntosFidelidad(clienteDTO.getPuntosFidelidad());
        
        return cliente;
    }
}
