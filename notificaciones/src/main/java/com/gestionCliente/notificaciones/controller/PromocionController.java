package com.gestionCliente.notificaciones.controller;

import com.gestionCliente.notificaciones.dto.*;
import com.gestionCliente.notificaciones.entity.Promocion;
import com.gestionCliente.notificaciones.service.PromocionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/promociones")
@Tag(name = "Promociones", description = "API para gestión de promociones (CRUD)")
public class PromocionController {
    
    @Autowired
    private PromocionService promocionService;
    
    @PostMapping
    @Operation(summary = "Crear promoción")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Promoción creada exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ApiResponse<PromocionResponse>> crear(@Valid @RequestBody PromocionCreateRequest request) {
        Promocion nueva = mapToEntity(request);
        ApiResponse<Promocion> serviceResp = promocionService.crear(nueva);
        ApiResponse<PromocionResponse> resp = wrap(serviceResp);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }
    
    @PutMapping("/{idPromocion}")
    @Operation(summary = "Actualizar promoción")
    public ResponseEntity<ApiResponse<PromocionResponse>> actualizar(
            @PathVariable Long idPromocion,
            @Valid @RequestBody PromocionUpdateRequest request) {
        Promocion cambios = mapToEntity(request);
        ApiResponse<Promocion> serviceResp = promocionService.actualizar(idPromocion, cambios);
        ApiResponse<PromocionResponse> resp = wrap(serviceResp);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }
    
    @DeleteMapping("/{idPromocion}")
    @Operation(summary = "Eliminar promoción")
    public ResponseEntity<ApiResponse<String>> eliminar(@PathVariable Long idPromocion) {
        ApiResponse<String> resp = promocionService.eliminar(idPromocion);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }
    
    @GetMapping
    @Operation(summary = "Obtener todas las promociones")
    public ResponseEntity<ApiResponse<List<PromocionResponse>>> obtenerTodas() {
        ApiResponse<List<Promocion>> serviceResp = promocionService.obtenerTodas();
        ApiResponse<List<PromocionResponse>> resp = wrapList(serviceResp);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }
    
    @GetMapping("/{idPromocion}")
    @Operation(summary = "Obtener promoción por ID")
    public ResponseEntity<ApiResponse<PromocionResponse>> obtenerPorId(@PathVariable Long idPromocion) {
        ApiResponse<Promocion> serviceResp = promocionService.obtenerPorId(idPromocion);
        ApiResponse<PromocionResponse> resp = wrap(serviceResp);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }
    
    private Promocion mapToEntity(PromocionCreateRequest request) {
        Promocion p = new Promocion();
        p.setNombre(request.getNombre());
        p.setDescripcion(request.getDescripcion());
        p.setTipoDescuento(request.getTipoDescuento());
        p.setValorDescuento(request.getValorDescuento());
        p.setFechaInicio(request.getFechaInicio());
        p.setFechaFin(request.getFechaFin());
        p.setCategoriaAplicable(request.getCategoriaAplicable());
        p.setEstado(request.getEstado());
        p.setCriterioSegmentacion(request.getCriterioSegmentacion());
        return p;
    }
    
    private Promocion mapToEntity(PromocionUpdateRequest request) {
        Promocion p = new Promocion();
        p.setNombre(request.getNombre());
        p.setDescripcion(request.getDescripcion());
        p.setTipoDescuento(request.getTipoDescuento());
        p.setValorDescuento(request.getValorDescuento());
        p.setFechaInicio(request.getFechaInicio());
        p.setFechaFin(request.getFechaFin());
        p.setCategoriaAplicable(request.getCategoriaAplicable());
        p.setEstado(request.getEstado());
        p.setCriterioSegmentacion(request.getCriterioSegmentacion());
        return p;
    }
    
    private ApiResponse<PromocionResponse> wrap(ApiResponse<Promocion> serviceResp) {
        if (serviceResp.isSuccess() && serviceResp.getData() != null) {
            return ApiResponse.success(serviceResp.getMessage(), new PromocionResponse(serviceResp.getData()));
        }
        return ApiResponse.error(serviceResp.getMessage(), serviceResp.getStatusCode());
    }
    
    private ApiResponse<List<PromocionResponse>> wrapList(ApiResponse<List<Promocion>> serviceResp) {
        if (serviceResp.isSuccess() && serviceResp.getData() != null) {
            List<PromocionResponse> list = serviceResp.getData().stream()
                    .map(PromocionResponse::new)
                    .collect(Collectors.toList());
            return ApiResponse.success(serviceResp.getMessage(), list);
        }
        return ApiResponse.error(serviceResp.getMessage(), serviceResp.getStatusCode());
    }
}


