package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.AprobacionInformeCreateRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.AprobacionInformeCreateResponseDTO;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.AprobacionInformeService;
import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.util.Contexto;

/**
 * Controlador REST para la gestión de aprobaciones de informes de renovación de contrato.
 * 
 * Este controlador maneja los diferentes niveles de aprobación del flujo de renovación de contrato:
 * - G1: Aprobación de Jefe de Unidad
 * - G2: Aprobación de Gerente de División  
 * - GPPM G3: Aprobación de Gerente de Procesos de Proyectos y Mantenimiento
 * - GSE G3: Aprobación final de Gerencia de Supervisión de Electricidad
 * 
 * Cada endpoint valida permisos, procesa la lógica de negocio y envía notificaciones correspondientes.
 * 
 * @author Sistema SICOES
 * @version 1.0
 */
@RestController
@RequestMapping("/renovacion-contrato/aprobacion-informe")
public class AprobacionInformeRestController extends BaseRestController {

    /**
     * Servicio para la lógica de aprobación de informes.
     */
    @Autowired
    private AprobacionInformeService aprobacionInformeService;

    /**
     * Endpoint para aprobar informe de renovación G1.
     * @param requestDTO DTO con datos de la aprobación
     * @return DTO con el resultado de la aprobación
     */
    /**
     * Endpoint para aprobar informe de renovación G1.
     * @param requestDTO DTO con datos de la aprobación
     * @return DTO con el resultado de la aprobación
     */
    @PostMapping("/aprobar-informe-renovacion-g1")
    public AprobacionInformeCreateResponseDTO aprobarInformeRenovacionG1(
            @RequestBody AprobacionInformeCreateRequestDTO requestDTO) {
        // Obtener contexto autenticado
        Contexto contexto = getContexto();
        return aprobacionInformeService.aprobarInformeRenovacionG1(requestDTO, contexto);
    }

    /**
     * Endpoint para aprobar informe de renovación G2.
     * @param requestDTO DTO con datos de la aprobación
     * @return DTO con el resultado de la aprobación
     */
    @PostMapping("/aprobar-informe-renovacion-g2")
    public AprobacionInformeCreateResponseDTO aprobarInformeRenovacionG2(
            @RequestBody AprobacionInformeCreateRequestDTO requestDTO) {
        // Obtener contexto autenticado
        Contexto contexto = getContexto();
        return aprobacionInformeService.aprobarInformeRenovacionG2(requestDTO, contexto);
    }

    /**
     * Endpoint para aprobar informe de renovación GPPM G3.
     * @param requestDTO DTO con datos de la aprobación
     * @return DTO con el resultado de la aprobación
     */
    @PostMapping("/aprobar-informe-renovacion-gppm-g3")
    public AprobacionInformeCreateResponseDTO aprobarInformeRenovacionGppmG3(
            @RequestBody AprobacionInformeCreateRequestDTO requestDTO) {
        // Obtener contexto (puede ser de sesión, request, etc.)
        Contexto contexto = getContexto();
        return aprobacionInformeService.aprobarInformeRenovacionGppmG3(requestDTO, contexto);
    }

    /**
     * Endpoint para aprobar informe de renovación GSE G3.
     * @param requestDTO DTO con datos de la aprobación
     * @return DTO con el resultado de la aprobación
     */
    @PostMapping("/aprobar-informe-renovacion-gse-g3")
    public AprobacionInformeCreateResponseDTO aprobarInformeRenovacionGseG3(
            @RequestBody AprobacionInformeCreateRequestDTO requestDTO) {
        // Obtener contexto (puede ser sesión, request, etc.)
        Contexto contexto = getContexto();
        return aprobacionInformeService.aprobarInformeRenovacionGseG3(requestDTO, contexto);
    }
}
