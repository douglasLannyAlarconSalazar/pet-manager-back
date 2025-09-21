package com.gestionCliente.gestion_cliente.mapper;

import com.gestionCliente.gestion_cliente.dto.ClienteDTO;
import com.gestionCliente.gestion_cliente.entity.Cliente;

public interface ClienteMapper {
    
    /**
     * Convierte un objeto Cliente (entidad) a ClienteDTO
     * @param cliente La entidad Cliente
     * @return ClienteDTO correspondiente
     */
    ClienteDTO toDTO(Cliente cliente);
    
    /**
     * Convierte un objeto ClienteDTO a Cliente (entidad)
     * @param clienteDTO El DTO ClienteDTO
     * @return Cliente (entidad) correspondiente
     */
    Cliente toEntity(ClienteDTO clienteDTO);
}
