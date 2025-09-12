package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.AprobacionInformeCreateRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.AprobacionInformeCreateResponseDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.AprobacionInformeRenovacionCreateRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.AprobacionInformeRenovacionCreateResponseDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;

/**
 * Interfaz para el servicio de aprobación de informes de renovación de contrato.
 */
public interface AprobacionInformeService {

        /**
     * Procesa la aprobación de informes de renovación de contratos.
     * 
     * @param requestDTO Objeto que contiene los IDs de los requerimientos a aprobar y las observaciones
     * @param contexto Objeto que contiene información del contexto de la aplicación y usuario
     * @return DTO con la respuesta del proceso de aprobación
     * @throws DataNotFoundException si no se encuentran los requerimientos o estados necesarios
     */
    AprobacionInformeRenovacionCreateResponseDTO aprobarInformeRenovacion(
        AprobacionInformeRenovacionCreateRequestDTO requestDTO, 
        Contexto contexto) throws DataNotFoundException;
        
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

    /**
     * Aprueba el informe de renovación GSE G3 - Nivel final de aprobación.
     * @param requestDTO DTO con datos de la aprobación
     * @param contexto contexto de la petición
     * @return DTO con el resultado de la aprobación
     */
    AprobacionInformeCreateResponseDTO aprobarInformeRenovacionGseG3(AprobacionInformeCreateRequestDTO requestDTO, Contexto contexto);


}
