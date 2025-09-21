package com.gestionCliente.gestion_cliente.controller;

import com.gestionCliente.gestion_cliente.dto.ClienteDTO;
import com.gestionCliente.gestion_cliente.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
@Tag(name = "Clientes", description = "API para la gestión de clientes")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    @Operation(summary = "Obtener todos los clientes", description = "Retorna una lista con todos los clientes registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        List<ClienteDTO> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);
    }
    
    @Operation(summary = "Obtener cliente por ID", description = "Busca y retorna un cliente específico por su ID único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(
            @Parameter(description = "ID único del cliente", example = "1", required = true)
            @PathVariable Long id) {
        ClienteDTO cliente = clienteService.findById(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(summary = "Obtener cliente por email", description = "Busca y retorna un cliente específico por su dirección de email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteDTO> getClienteByEmail(
            @Parameter(description = "Email único del cliente", example = "juan.perez@email.com", required = true)
            @PathVariable String email) {
        ClienteDTO cliente = clienteService.findByEmail(email);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(summary = "Crear nuevo cliente", description = "Crea un nuevo cliente en el sistema. El ID y la fecha de registro se generan automáticamente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))),
            @ApiResponse(responseCode = "409", description = "Ya existe un cliente con ese email"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<ClienteDTO> createCliente(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del cliente a crear (sin ID ni fecha de registro)",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ClienteDTO.class))
            )
            @RequestBody ClienteDTO clienteDTO) {
        // Verificar si ya existe un cliente con ese email
        if (clienteService.existsByEmail(clienteDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        
        ClienteDTO clienteCreado = clienteService.create(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCreado);
    }
    
    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente existente por su ID. La fecha de registro se preserva automáticamente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> updateCliente(
            @Parameter(description = "ID único del cliente a actualizar", example = "1", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del cliente (la fecha de registro se preserva automáticamente)",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ClienteDTO.class))
            )
            @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO clienteActualizado = clienteService.update(id, clienteDTO);
        if (clienteActualizado != null) {
            return ResponseEntity.ok(clienteActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente del sistema por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(
            @Parameter(description = "ID único del cliente a eliminar", example = "1", required = true)
            @PathVariable Long id) {
        boolean eliminado = clienteService.deleteById(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
