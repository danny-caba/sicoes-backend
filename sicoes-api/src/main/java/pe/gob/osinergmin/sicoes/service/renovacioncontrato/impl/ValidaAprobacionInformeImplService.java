package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import pe.gob.osinergmin.sicoes.model.Notificacion;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.*;

import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.SolicitudPerfecionamientoContratoDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoRenovacionDao;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.SolicitudPerfecionamientoContrato;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.List;

import pe.gob.osinergmin.sicoes.service.renovacioncontrato.AprobacionInformeService;

import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.NotificacionAprobacionInformeService;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.service.UsuarioService;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.ValidaAprobacionInformeService;

@Service
public class ValidaAprobacionInformeImplService implements ValidaAprobacionInformeService {


    private final Logger logger = LogManager.getLogger(ValidaAprobacionInformeImplService.class);

    @Autowired
    private ListadoDetalleService listadoDetalleService;
    

    


    @Autowired
    private RequerimientoAprobacionDao requerimientoAprobacionDao;

    @Autowired
    private SolicitudPerfecionamientoContratoDao solicitudPerfecionamientoContratoDao;



    @Override
    public AprobacionInformeRenovacionCreateResponseDTO validarInformeRenovacion(
            AprobacionInformeRenovacionCreateRequestDTO requestDTO,
            Contexto contexto) throws DataNotFoundException {

        // Validaciones básicas
        if (requestDTO == null) {
            logger.error("El request no puede ser nulo.");
            throw new DataNotFoundException("El request no puede ser nulo.");
        }
        if (contexto == null || contexto.getUsuario() == null || contexto.getUsuario().getIdUsuario() == null) {
            logger.error("El contexto/usuario es inválido.");
            throw new DataNotFoundException("El contexto/usuario es inválido.");
        }
        if (requestDTO.getIdRequerimientosAprobacion() == null || requestDTO.getIdRequerimientosAprobacion().isEmpty()) {
            logger.error("Debe indicar al menos un id de RequerimientoAprobacion.");
            throw new DataNotFoundException("Debe indicar al menos un id de RequerimientoAprobacion.");
        }
        if (!(requestDTO.getObservacion() == null || requestDTO.getObservacion().isEmpty())) {
            if (requestDTO.getObservacion().length() > 500) {
                throw new DataNotFoundException("La observación no debe superar los 500 caracteres");
            }
        }

        // Inicializar respuesta agregada
        AprobacionInformeRenovacionCreateResponseDTO respuestaAgregada = new AprobacionInformeRenovacionCreateResponseDTO();
        List<AprobacionInformeCreateResponseDTO> resultados = new ArrayList<>();

        // Procesar cada id de requerimiento
        for (Long idReqApr : requestDTO.getIdRequerimientosAprobacion()) {
            try {
                // 1) Obtener requerimiento
                RequerimientoAprobacion entity = requerimientoAprobacionDao.obtenerPorId(idReqApr);
                if (entity == null) {
                    logger.error("No existe RequerimientoAprobacion con id={}", idReqApr);
                    throw new DataNotFoundException("No existe RequerimientoAprobacion con id=" + idReqApr);
                }

                // Validar informe de renovación
                if (entity.getInformeRenovacionContrato() == null ||
                    entity.getInformeRenovacionContrato().getIdInformeRenovacion() == null) {
                    logger.error("El RequerimientoAprobacion {} no tiene InformeRenovacion asociado.", idReqApr);
                    throw new DataNotFoundException(
                        "El RequerimientoAprobacion " + idReqApr + " no tiene InformeRenovacion asociado.");
                }

                Long idSolicitud = entity.getInformeRenovacionContrato().getRequerimiento().getIdSoliPerfCont();
                List<SolicitudPerfecionamientoContrato> listaPerfilesAprobadoresBySolicitud = 
                    solicitudPerfecionamientoContratoDao.getPerfilAprobadorByIdPerfilListadoDetalle(idSolicitud);
                if (listaPerfilesAprobadoresBySolicitud == null || listaPerfilesAprobadoresBySolicitud.isEmpty()) {
                    throw new DataNotFoundException("No tiene perfil aprobador asignado la solicitud "+idSolicitud.toString());
                }

                // 2) Obtener grupo aprobador
                ListadoDetalle grupoAprobadorLd = listadoDetalleService.obtener(
                    entity.getIdGrupoAprobadorLd(),contexto);
                if (grupoAprobadorLd == null || grupoAprobadorLd.getCodigo() == null) {
                    logger.error("No se pudo resolver el grupo aprobador para RequerimientoAprobacion {}", idReqApr);
                    throw new DataNotFoundException(
                        "No se pudo resolver el grupo aprobador para RequerimientoAprobacion " + idReqApr);
                }
                String codigoGrupo = grupoAprobadorLd.getCodigo();

                SolicitudPerfecionamientoContrato solicitudPerfecionamientoContrato = listaPerfilesAprobadoresBySolicitud.get(0);
                switch (codigoGrupo) {
                    case "JEFE_UNIDAD":
                        if (!solicitudPerfecionamientoContrato.getIdAprobadorG1().equals(contexto.getUsuario().getIdUsuario())) {
                            throw new DataNotFoundException("El usuario no coincide con el perfil aprobador G1");
                        }
                        break;
                    case "GERENTE":
                        if (!solicitudPerfecionamientoContrato.getIdAprobadorG2().equals(contexto.getUsuario().getIdUsuario())) {
                            throw new DataNotFoundException("El usuario no coincide con el perfil aprobador G2");
                        }
                        break;
                    case "GPPM":
                        if (!solicitudPerfecionamientoContrato.getIdAprobadorG3().equals(contexto.getUsuario().getIdUsuario())) {
                            throw new DataNotFoundException("El usuario no coincide con el perfil aprobador G3 (GPPM)");
                        }
                        break;
                    case "GSE":
                        if (!solicitudPerfecionamientoContrato.getIdAprobadorG3().equals(contexto.getUsuario().getIdUsuario())) {
                            throw new DataNotFoundException("El usuario no coincide con el perfil aprobador G3 (GSE)");
                        }
                        break;
                    default:
                        logger.error("Código de grupo aprobador no soportado: {}", codigoGrupo);
                        throw new DataNotFoundException(
                                "Código de grupo aprobador no soportado: " + codigoGrupo);
                }

            } catch (Exception e) {
                // Loggear error y continuar con siguiente id
                logger.error("Error procesando RequerimientoAprobacion id={}", idReqApr, e);
                throw new DataNotFoundException("Error procesando requerimiento: " + e.getMessage());
            }
        }

        return respuestaAgregada;
    }

    @Override
    public Long obtenerIdLd(String codigoListado, String codigo) {
        ListadoDetalle listadoDetalle = listadoDetalleService.obtenerListadoDetalle(
                codigoListado,
                codigo
        );
        return listadoDetalle.getIdListadoDetalle();
    }
    @Override
    public ListadoDetalle obtenerLd(String codigoListado, String codigo) {
        ListadoDetalle listadoDetalle = listadoDetalleService.obtenerListadoDetalle(
                codigoListado,
                codigo
        );
        return listadoDetalle;
    }

}
