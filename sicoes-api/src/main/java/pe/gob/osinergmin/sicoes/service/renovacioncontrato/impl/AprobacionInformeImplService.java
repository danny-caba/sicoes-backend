package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeAprobacionCreateRequestDTO;
import pe.gob.osinergmin.sicoes.util.Constantes;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.SolicitudPerfecionamientoContratoDao;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.SolicitudPerfecionamientoContrato;
import java.util.Optional;
import java.util.List;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.AprobacionInformeCreateRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.AprobacionInformeCreateResponseDTO;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.AprobacionInformeService;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;

/**
 * Implementación del servicio de aprobación de informes de renovación de contrato.
 */
@Service
public class AprobacionInformeImplService implements AprobacionInformeService {

    @Autowired
    private InformeRenovacionContratoDao informeRenovacionContratoDao;

    @Autowired
    private SolicitudPerfecionamientoContratoDao solicitudPerfecionamientoContratoDao;
    /**
     * Aprueba el informe de renovación G1.
     * @param requestDTO DTO con datos de la aprobación
     * @param contexto contexto de la petición
     * @return DTO con el resultado de la aprobación
     */
    @Override
    public AprobacionInformeCreateResponseDTO aprobarInformeRenovacionG1(AprobacionInformeCreateRequestDTO requestDTO, Contexto contexto) {
        // Validación 3.0: idUsuario obligatorio
        if (requestDTO.getIdUsuario() == null) {
            throw new DataNotFoundException("El campo id Usuario es obligatorio");
        }
        // Validación 3.1: observacion obligatorio
        if (requestDTO.getObservacion() == null || requestDTO.getObservacion().trim().isEmpty()) {
            throw new DataNotFoundException("El campo observacion es obligatorio");
        }
        // Validación 3.2: observacion longitud máxima
        if (requestDTO.getObservacion().length() > 500) {
            throw new DataNotFoundException("La observación no debe superar los 500 caracteres");
        }
        // Validación 3.3: todos los informes deben tener idInformeRenovacion
        if (requestDTO.getInformes() == null || requestDTO.getInformes().isEmpty()) {
            throw new DataNotFoundException("Debe ingresar al menos un informe de renovación");
        }
        // Unificar validaciones en un solo bucle
        for (InformeAprobacionCreateRequestDTO informeDTO : requestDTO.getInformes()) {
            if (informeDTO.getIdInformeRenovacion() == null) {
                throw new DataNotFoundException("Debe ingresar idInformeRenovacion en todos los informes");
            }
            Optional<InformeRenovacionContrato> informeOpt = informeRenovacionContratoDao.findById(informeDTO.getIdInformeRenovacion());
            if (!informeOpt.isPresent()) {
                throw new DataNotFoundException(Constantes.CODIGO_MENSAJE.INFORME_PRESUPUESTO_RENOVACION_CONTRATO_NO_ENCONTRADO);
            }
            InformeRenovacionContrato informeRenovacionContrato = informeOpt.get();
            Long idSolicitud = informeRenovacionContrato.getRequerimiento().getSolicitudPerfil().getIdSolicitud();

            List<SolicitudPerfecionamientoContrato> listaPerfilesAprobadoresBySolicitud = solicitudPerfecionamientoContratoDao.getPerfilAprobadorByIdPerfilListadoDetalle(idSolicitud);
            if (listaPerfilesAprobadoresBySolicitud == null || listaPerfilesAprobadoresBySolicitud.isEmpty()) {
                throw new DataNotFoundException(Constantes.CODIGO_MENSAJE.PERFIL_APROBADOR_RENOVACION_CONTRATO_NO_ENCONTRADO);
            }
            SolicitudPerfecionamientoContrato solicitudPerfecionamientoContrato = listaPerfilesAprobadoresBySolicitud.get(0);
            if (!solicitudPerfecionamientoContrato.getIdAprobadorG1().equals(requestDTO.getIdUsuario())) {
                throw new DataNotFoundException("El usuario no coincide con el perfil aprobador G1");
            }
        }


        return new AprobacionInformeCreateResponseDTO();
    }
}
