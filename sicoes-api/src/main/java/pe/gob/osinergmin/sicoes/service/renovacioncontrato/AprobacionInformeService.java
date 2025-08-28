package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.AprobacionInformeCreateRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.AprobacionInformeCreateResponseDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

/**
 * Interfaz para el servicio de aprobación de informes de renovación de contrato.
 */
public interface AprobacionInformeService {
    /**
     * Aprueba el informe de renovación G1.
     * @param requestDTO DTO con datos de la aprobación
     * @param contexto contexto de la petición
     * @return DTO con el resultado de la aprobación
     */
    AprobacionInformeCreateResponseDTO aprobarInformeRenovacionG1(AprobacionInformeCreateRequestDTO requestDTO, Contexto contexto);

    /**
     * Aprueba el informe de renovación G2.
     * @param requestDTO DTO con datos de la aprobación
     * @param contexto contexto de la petición
     * @return DTO con el resultado de la aprobación
     */
    AprobacionInformeCreateResponseDTO aprobarInformeRenovacionG2(AprobacionInformeCreateRequestDTO requestDTO, Contexto contexto);

    /**
     * Aprueba el informe de renovación GPPM G3.
     * @param requestDTO DTO con datos de la aprobación
     * @param contexto contexto de la petición
     * @return DTO con el resultado de la aprobación
     */
    AprobacionInformeCreateResponseDTO aprobarInformeRenovacionGppmG3(AprobacionInformeCreateRequestDTO requestDTO, Contexto contexto);
}
