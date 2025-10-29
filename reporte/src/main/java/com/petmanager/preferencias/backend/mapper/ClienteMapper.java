package com.petmanager.preferencias.backend.mapper;

import com.petmanager.preferencias.backend.dto.ClienteDTO;
import com.petmanager.preferencias.backend.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        ClienteDTO dto = new ClienteDTO();
        dto.setId_cliente(cliente.getIdCliente());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        dto.setDireccion(cliente.getDireccion());
        dto.setFecha_registro(cliente.getFechaRegistro());
        dto.setEstado(cliente.getEstado());
        dto.setPuntos_fidelidad(cliente.getPuntosFidelidad());

        return dto;
    }

    public Cliente toEntity(ClienteDTO dto) {
        if (dto == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        cliente.setIdCliente(dto.getId_cliente());
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());
        cliente.setFechaRegistro(dto.getFecha_registro());
        cliente.setEstado(dto.getEstado());
        cliente.setPuntosFidelidad(dto.getPuntos_fidelidad());

        return cliente;
    }
}
