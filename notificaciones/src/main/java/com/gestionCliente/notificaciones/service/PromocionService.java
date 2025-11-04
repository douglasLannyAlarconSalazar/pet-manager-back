package com.gestionCliente.notificaciones.service;

import com.gestionCliente.notificaciones.dto.ApiResponse;
import com.gestionCliente.notificaciones.entity.Promocion;
import com.gestionCliente.notificaciones.repository.PromocionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PromocionService {
    
    private static final Logger logger = LoggerFactory.getLogger(PromocionService.class);
    
    @Autowired
    private PromocionRepository promocionRepository;
    
    public ApiResponse<Promocion> crear(Promocion promocion) {
        try {
            String errorValidacion = validar(promocion);
            if (errorValidacion != null) {
                return ApiResponse.error(errorValidacion, 400);
            }
            Promocion guardada = promocionRepository.save(promocion);
            return ApiResponse.success("Promoción creada exitosamente", guardada);
        } catch (Exception e) {
            logger.error("Error al crear promoción: {}", e.getMessage());
            return ApiResponse.error("Error al crear promoción: " + e.getMessage(), 500);
        }
    }
    
    public ApiResponse<Promocion> actualizar(Long idPromocion, Promocion cambios) {
        try {
            Optional<Promocion> existenteOpt = promocionRepository.findById(idPromocion);
            if (existenteOpt.isEmpty()) {
                return ApiResponse.error("Promoción no encontrada", 404);
            }
            Promocion existente = existenteOpt.get();
            
            // Aplicar cambios permitidos
            if (cambios.getNombre() != null) existente.setNombre(cambios.getNombre());
            if (cambios.getDescripcion() != null) existente.setDescripcion(cambios.getDescripcion());
            if (cambios.getTipoDescuento() != null) existente.setTipoDescuento(cambios.getTipoDescuento());
            if (cambios.getValorDescuento() != null) existente.setValorDescuento(cambios.getValorDescuento());
            if (cambios.getFechaInicio() != null) existente.setFechaInicio(cambios.getFechaInicio());
            if (cambios.getFechaFin() != null) existente.setFechaFin(cambios.getFechaFin());
            if (cambios.getCategoriaAplicable() != null) existente.setCategoriaAplicable(cambios.getCategoriaAplicable());
            if (cambios.getEstado() != null) existente.setEstado(cambios.getEstado());
            if (cambios.getCriterioSegmentacion() != null) existente.setCriterioSegmentacion(cambios.getCriterioSegmentacion());
            
            String errorValidacion = validar(existente);
            if (errorValidacion != null) {
                return ApiResponse.error(errorValidacion, 400);
            }
            
            Promocion actualizada = promocionRepository.save(existente);
            return ApiResponse.success("Promoción actualizada exitosamente", actualizada);
        } catch (Exception e) {
            logger.error("Error al actualizar promoción: {}", e.getMessage());
            return ApiResponse.error("Error al actualizar promoción: " + e.getMessage(), 500);
        }
    }
    
    public ApiResponse<String> eliminar(Long idPromocion) {
        try {
            Optional<Promocion> existenteOpt = promocionRepository.findById(idPromocion);
            if (existenteOpt.isEmpty()) {
                return ApiResponse.error("Promoción no encontrada", 404);
            }
            promocionRepository.deleteById(idPromocion);
            return ApiResponse.success("Promoción eliminada exitosamente");
        } catch (Exception e) {
            logger.error("Error al eliminar promoción: {}", e.getMessage());
            return ApiResponse.error("Error al eliminar promoción: " + e.getMessage(), 500);
        }
    }
    
    public ApiResponse<List<Promocion>> obtenerTodas() {
        try {
            List<Promocion> promociones = promocionRepository.findAll();
            return ApiResponse.success("Promociones obtenidas exitosamente", promociones);
        } catch (Exception e) {
            logger.error("Error al obtener promociones: {}", e.getMessage());
            return ApiResponse.error("Error al obtener promociones: " + e.getMessage(), 500);
        }
    }
    
    public ApiResponse<Promocion> obtenerPorId(Long idPromocion) {
        try {
            Optional<Promocion> promocionOpt = promocionRepository.findById(idPromocion);
            if (promocionOpt.isEmpty()) {
                return ApiResponse.error("Promoción no encontrada", 404);
            }
            return ApiResponse.success("Promoción obtenida exitosamente", promocionOpt.get());
        } catch (Exception e) {
            logger.error("Error al obtener promoción: {}", e.getMessage());
            return ApiResponse.error("Error al obtener promoción: " + e.getMessage(), 500);
        }
    }
    
    private String validar(Promocion promocion) {
        if (promocion.getFechaInicio() != null && promocion.getFechaFin() != null) {
            if (promocion.getFechaFin().isBefore(promocion.getFechaInicio())) {
                return "La fecha fin no puede ser anterior a la fecha inicio";
            }
        }
        return null;
    }
}


