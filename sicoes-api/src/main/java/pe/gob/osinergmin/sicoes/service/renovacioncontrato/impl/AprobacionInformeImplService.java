package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.osinergmin.sicoes.model.Notificacion;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.*;

import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.HistorialEstadoAprobacionCampo;
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

import pe.gob.osinergmin.sicoes.service.renovacioncontrato.HistorialAprobacionRenovacionService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper.AprobacionCreateMapper;

import pe.gob.osinergmin.sicoes.service.renovacioncontrato.AprobacionInformeService;

import pe.gob.osinergmin.sicoes.service.renovacioncontrato.ValidaAprobacionInformeService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.NotificacionAprobacionInformeService;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.service.UsuarioService;
import pe.gob.osinergmin.sicoes.model.Usuario;

/**
 * Implementación del servicio de aprobación de informes de renovación de contrato.
 */
@Service
public class AprobacionInformeImplService implements AprobacionInformeService {

    private final Logger logger = LogManager.getLogger(AprobacionInformeImplService.class);
    @Autowired
    private InformeRenovacionContratoDao informeRenovacionContratoDao;

    @Autowired
    private SolicitudPerfecionamientoContratoDao solicitudPerfecionamientoContratoDao;

    @Autowired
    private RequerimientoAprobacionDao requerimientoAprobacionDao;

    @Autowired
    private RequerimientoRenovacionDao requerimientoRenovacionDao;

    @Autowired
    private ListadoDetalleService listadoDetalleService;

    @Autowired
    private NotificacionAprobacionInformeService notificacionAprobacionInformeService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ValidaAprobacionInformeService validaAprobacionService;

    @Autowired
    private ValidaAprobacionInformeService datosService;

    @Autowired
    private AprobacionCreateMapper aprobacionCreateMapper;

    @Autowired
    private HistorialAprobacionRenovacionService historialAprobacionRenovacionService;

    @Override
    @Transactional
    public AprobacionInformeRenovacionCreateResponseDTO aprobarInformeRenovacion(
            AprobacionInformeRenovacionCreateRequestDTO requestDTO,
            Contexto contexto) throws DataNotFoundException {
        AprobacionInformeRenovacionCreateResponseDTO responseDTO = new AprobacionInformeRenovacionCreateResponseDTO();
        validaAprobacionService.validarInformeRenovacion(requestDTO, contexto);
        List<AprobacionCreateResponseDTO> aprobaciones = new ArrayList<>();
        for (Long idReqApr : requestDTO.getIdRequerimientosAprobacion()) {
            try {
                // 1) Obtener requerimiento
                RequerimientoAprobacion entity = requerimientoAprobacionDao.obtenerPorId(idReqApr);
                // Guardamos el historico previo a la aprobacion
                HistorialEstadoAprobacionCampo historial = historialAprobacionRenovacionService.registrarHistorialPreAprobacion(entity, contexto);
                ListadoDetalle grupoAprobadorLd = listadoDetalleService.obtener(
                        entity.getIdGrupoAprobadorLd(),contexto);
                String codigoGrupo = grupoAprobadorLd.getCodigo();
                // 4) Enrutar según grupo aprobador
                // 3) Construir request para aprobación específica
                AprobacionInformeCreateRequestDTO reqAprob = new AprobacionInformeCreateRequestDTO();
                reqAprob.setIdUsuario(contexto.getUsuario().getIdUsuario());
                reqAprob.setObservacion(requestDTO.getObservacion());
                // Agregar informe a la lista
                InformeAprobacionCreateRequestDTO informeDTO = new InformeAprobacionCreateRequestDTO();
                informeDTO.setIdInformeRenovacion(entity.getInformeRenovacionContrato().getIdInformeRenovacion());

                List<InformeAprobacionCreateRequestDTO> informes = new ArrayList<>();
                informes.add(informeDTO);
                reqAprob.setInformes(informes);
                
                switch (codigoGrupo) {
                    case "JEFE_UNIDAD":
                       aprobarInformeRenovacionG1(reqAprob, contexto);
                        break;
                    case "GERENTE":
                        aprobarInformeRenovacionG2(reqAprob, contexto);
                        break;
                    case "GPPM":
                        aprobarInformeRenovacionGppmG3(reqAprob, contexto);
                        break;
                    case "GSE":
                        aprobarInformeRenovacionGseG3(reqAprob, contexto);
                        break;
                    default:
                        logger.error("Código de grupo aprobador no soportado: {}", codigoGrupo);
                        throw new DataNotFoundException(
                                "Código de grupo aprobador no soportado: " + codigoGrupo);
                }

                RequerimientoAprobacion entityBefore = requerimientoAprobacionDao.obtenerPorId(idReqApr);
                historialAprobacionRenovacionService.registrarHistorialPostAprobacion(historial, entityBefore, contexto);
                // Mapear requerimiento a DTO usando el mapper
                AprobacionCreateResponseDTO aprobacionDTO = aprobacionCreateMapper.toDto(entityBefore);
                aprobaciones.add(aprobacionDTO);

            } catch (Exception e) {
                // Loggear error y continuar con siguiente id
                logger.error("Error procesando RequerimientoAprobacion id={}", idReqApr, e);
                throw new DataNotFoundException("Error procesando requerimiento: " + e.getMessage());
            }
        }
        responseDTO.setAprobaciones(aprobaciones);
        return responseDTO;
    }


    /**
     * Aprueba el informe de renovación G1.
     * @param requestDTO DTO con datos de la aprobación
     * @param contexto contexto de la petición
     * @return DTO con el resultado de la aprobación
     */
    @Override
    public AprobacionInformeCreateResponseDTO aprobarInformeRenovacionG1(AprobacionInformeCreateRequestDTO requestDTO, Contexto contexto) {



        // 3.5 Iniciar iteración de lógica de negocio de Aprobación
        for (InformeAprobacionCreateRequestDTO informeRequest : requestDTO.getInformes()) {
            
            // 3.5.1 Obtiene datos SolicitudPerfecionamientoContrato
            Optional<InformeRenovacionContrato> informeOpt = informeRenovacionContratoDao.findById(informeRequest.getIdInformeRenovacion());

            InformeRenovacionContrato informeRenovacionContrato = informeOpt.get();

            SolicitudPerfecionamientoContrato solicitudPerfecionamientoContrato = obtenerSolicitudPerfecionamiento(informeRenovacionContrato);
            
            // 3.5.2 Actualiza aprobación el campo "Estado Aprobación Jefe División" = Aprobado

            
            // Buscar el requerimiento de aprobación G1 existente
            List<RequerimientoAprobacion> requerimientosG1 = requerimientoAprobacionDao.findByIdInformeRenovacionAndIdGrupoAprobadorLd(
                informeRequest.getIdInformeRenovacion(),
                    datosService.obtenerIdLd("GRUPO_APROBACION", "JEFE_UNIDAD")
            );
            
            if (requerimientosG1.isEmpty()) {
                throw new DataNotFoundException("No se encontró requerimiento de aprobación G1 para el informe: " + informeRequest.getIdInformeRenovacion());
            }
            
            RequerimientoAprobacion requerimientoAprobacionG1 = requerimientosG1.get(0);
            asignarAuditoriaActualizacion(requerimientoAprobacionG1, contexto);
            requerimientoAprobacionG1.setEstado(
                    listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,
                            Constantes.LISTADO.ESTADO_APROBACION.APROBADO)
            );
            requerimientoAprobacionG1.getUsuario().setIdUsuario(solicitudPerfecionamientoContrato.getIdAprobadorG1());
            requerimientoAprobacionG1.setDeObservacion(requestDTO.getObservacion());

            
            // 3.5.3 Registra el campo "Estado Aprobación Gerente División" = Asignado
            RequerimientoAprobacion requerimientoAprobacionG2 = new RequerimientoAprobacion();
            asignarAuditoriaCreacion(requerimientoAprobacionG2, contexto);

            requerimientoAprobacionG2.setIdInformeRenovacion(informeRequest.getIdInformeRenovacion());
            
            // NO asignar idRequerimiento - la FK espera SICOES_TC_REQUERIMIENTO que no existe en el modelo actual
            // requerimientoAprobacionG2.setIdRequerimiento(requerimientoAprobacionG1.getIdRequerimiento());

            requerimientoAprobacionG2.setIdTipoLd(
                datosService.obtenerIdLd("TIPO_APROBACION", "APROBAR")
            );

            requerimientoAprobacionG2.setGrupo(
                    listadoDetalleService.obtenerListadoDetalle(
                            Constantes.LISTADO.GRUPOS.CODIGO,
                            Constantes.LISTADO.GRUPOS.G2
                    )
            );
            
            requerimientoAprobacionG2.getUsuario().setIdUsuario(solicitudPerfecionamientoContrato.getIdAprobadorG2());
            
            requerimientoAprobacionG2.setEstado(
                    listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,
                            Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO)
            );

            requerimientoAprobacionG2.setTipoAprobador(
                    listadoDetalleService.obtenerListadoDetalle(
                            Constantes.LISTADO.TIPO_EVALUADOR.CODIGO,
                            Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO
                    )
            );
            
            requerimientoAprobacionG2.setIdGrupoAprobadorLd(
                datosService.obtenerIdLd("GRUPO_APROBACION", "GERENTE")
            );
            
            // IMPORTANTE: Asignar el valor 962 para ID_FIRMADO_LD cuando ID_GRUPO_LD es 543 (G2)
//            if (requerimientoAprobacionG2.getIdGrupoLd() != null && requerimientoAprobacionG2.getIdGrupoLd().equals(543L)) {
//                requerimientoAprobacionG2.setIdFirmadoLd(962L);
//            }

            ListadoDetalle g2 = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.GRUPOS.CODIGO,
                    Constantes.LISTADO.GRUPOS.G2
            );

            if (requerimientoAprobacionG2.getGrupo() != null && requerimientoAprobacionG2.getGrupo().equals(g2)) {
                ListadoDetalle estadoFirmado = listadoDetalleService.obtenerListadoDetalle(
                        Constantes.LISTADO.ESTADO_FIRMADO.CODIGO,
                        Constantes.LISTADO.ESTADO_FIRMADO.FIRMADO
                );
                requerimientoAprobacionG2.setIdFirmadoLd(estadoFirmado.getIdListadoDetalle());
            }

            // 3.5.4 Primero crear la notificación antes de guardar el requerimiento G2
            Usuario usuarioG2 = usuarioService.obtener(solicitudPerfecionamientoContrato.getIdAprobadorG2());
            String numExpediente = informeRenovacionContrato.getRequerimiento().getNuExpediente();
            Notificacion notificacion = notificacionAprobacionInformeService.notificacionInformePorAprobaryFirmar(usuarioG2, numExpediente, contexto);
            
            // Asignar el ID de notificación al requerimiento G2 antes de guardarlo
            if (notificacion != null && notificacion.getIdNotificacion() != null) {
                requerimientoAprobacionG2.setIdNotificacion(notificacion.getIdNotificacion());
                logger.info("Asignando ID de notificación {} al nuevo requerimiento G2", notificacion.getIdNotificacion());
            }
            
            RequerimientoAprobacion requerimientoAprobacionResult=requerimientoAprobacionDao.save(requerimientoAprobacionG2);
            historialAprobacionRenovacionService.registrarHistorialAprobacionRenovacion(requerimientoAprobacionResult, contexto);
            
            // Asignar el ID de notificación al requerimiento G1
            if (notificacion != null && notificacion.getIdNotificacion() != null) {
                requerimientoAprobacionG1.setIdNotificacion(notificacion.getIdNotificacion());
                logger.info("Asignando ID de notificación {} al requerimiento G1", notificacion.getIdNotificacion());
            }
            // Guardar cambios
            requerimientoAprobacionDao.save(requerimientoAprobacionG1);
        }

        return new AprobacionInformeCreateResponseDTO();
    }

    /**
     * Aprueba el informe de renovación G2.
     * @param requestDTO DTO con datos de la aprobación
     * @param contexto contexto de la petición
     * @return DTO con el resultado de la aprobación
     */
    @Override
    public AprobacionInformeCreateResponseDTO aprobarInformeRenovacionG2(AprobacionInformeCreateRequestDTO requestDTO, Contexto contexto) {

        // 3.5 Iniciar iteración de lógica de negocio de Aprobación
        for (InformeAprobacionCreateRequestDTO informeRequest : requestDTO.getInformes()) {

            // 3.5.1 Obtiene datos SolicitudPerfecionamientoContrato
            Optional<InformeRenovacionContrato> informeOpt = informeRenovacionContratoDao.findById(informeRequest.getIdInformeRenovacion());

            InformeRenovacionContrato informeRenovacionContrato = informeOpt.get();


            // 3.5.2 Actualiza aprobación el campo "Estado Aprobación Gerente División" = Aprobado

            // Buscar el requerimiento de aprobación G2 existente
            List<RequerimientoAprobacion> requerimientosG2 = requerimientoAprobacionDao.findByIdInformeRenovacionAndIdGrupoAprobadorLd(
                informeRequest.getIdInformeRenovacion(),
                    datosService.obtenerIdLd("GRUPO_APROBACION", "GERENTE")
            );
            
            if (requerimientosG2.isEmpty()) {
                throw new DataNotFoundException("No se encontró requerimiento de aprobación G2 para el informe: " + informeRequest.getIdInformeRenovacion());
            }
            
            RequerimientoAprobacion requerimientoAprobacionG2 = requerimientosG2.get(0);
            asignarAuditoriaActualizacion(requerimientoAprobacionG2, contexto);

            requerimientoAprobacionG2.setEstado(
                    listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,
                            Constantes.LISTADO.ESTADO_APROBACION.APROBADO)
            );
            requerimientoAprobacionG2.getUsuario().setIdUsuario(contexto.getUsuario().getIdUsuario());
            requerimientoAprobacionG2.setDeObservacion(requestDTO.getObservacion());
            
            // IMPORTANTE: Asignar el valor 962 para ID_FIRMADO_LD cuando ID_GRUPO_LD es 543 (G2)
//            if (requerimientoAprobacionG2.getIdGrupoLd() != null && requerimientoAprobacionG2.getIdGrupoLd().equals(543L)) {
//                requerimientoAprobacionG2.setIdFirmadoLd(962L);
//            }

            ListadoDetalle g2 = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.GRUPOS.CODIGO,
                    Constantes.LISTADO.GRUPOS.G2
            );

            if (requerimientoAprobacionG2.getGrupo() != null && requerimientoAprobacionG2.getGrupo().equals(g2)) {
                ListadoDetalle estadoFirmado = listadoDetalleService.obtenerListadoDetalle(
                        Constantes.LISTADO.ESTADO_FIRMADO.CODIGO,
                        Constantes.LISTADO.ESTADO_FIRMADO.FIRMADO
                );
                requerimientoAprobacionG2.setIdFirmadoLd(estadoFirmado.getIdListadoDetalle());
            }

            // 3.5.3 Actualiza el campo "Estado Aprobación Informe" = Concluido
            ListadoDetalle concluidoEstadoAprobacionInforme = listadoDetalleService.obtenerListadoDetalle(
                "ESTADO_REQUERIMIENTO", 
                "CONCLUIDO"
            );
            informeRenovacionContrato.setEstadoAprobacionInforme(concluidoEstadoAprobacionInforme);
            asignarAuditoriaActualizacionInforme(informeRenovacionContrato, contexto);

            informeRenovacionContratoDao.save(informeRenovacionContrato);
            
            // 3.5.4 Envía notificación por correo al rol Evaluador de Contratos para la Evaluación de la Invitación
            Usuario evaluadorContratosUsuario = usuarioService.obtener(informeRenovacionContrato.getUsuario().getIdUsuario());
            String numExpediente = informeRenovacionContrato.getRequerimiento().getNuExpediente();

            Notificacion notificacion =  notificacionAprobacionInformeService.notificacionInformePorRevisar(evaluadorContratosUsuario, numExpediente, contexto);
            
            // Asignar el ID de notificación al requerimiento G2
            if (notificacion != null && notificacion.getIdNotificacion() != null) {
                requerimientoAprobacionG2.setIdNotificacion(notificacion.getIdNotificacion());
                logger.info("Asignando ID de notificación {} al requerimiento G2", notificacion.getIdNotificacion());
            }

            // Guardar cambios
            requerimientoAprobacionDao.save(requerimientoAprobacionG2);
        }

        return new AprobacionInformeCreateResponseDTO();
    }

    /**
     * Aprueba el informe de renovación GPPM G3.
     * @param requestDTO DTO con datos de la aprobación
     * @param contexto contexto de la petición
     * @return DTO con el resultado de la aprobación
     */
    @Override
    public AprobacionInformeCreateResponseDTO aprobarInformeRenovacionGppmG3(AprobacionInformeCreateRequestDTO requestDTO, Contexto contexto) {



        // 3.5 Iniciar iteración de lógica de negocio de Aprobación
        for (InformeAprobacionCreateRequestDTO informeRequest : requestDTO.getInformes()) {
            
            // 3.5.1 Obtiene datos SolicitudPerfecionamientoContrato
            Optional<InformeRenovacionContrato> informeOpt = informeRenovacionContratoDao.findById(informeRequest.getIdInformeRenovacion());

            InformeRenovacionContrato informeRenovacionContrato = informeOpt.get();
            SolicitudPerfecionamientoContrato solicitudPerfecionamientoContrato = obtenerSolicitudPerfecionamiento(informeRenovacionContrato);
            
            // 3.5.2 Actualiza aprobación el campo "Estado Aprobación GPPM G3" = Aprobado
            Long idGrupoAprobadorGppmG3 = datosService.obtenerIdLd("GRUPO_APROBACION", "GPPM");
            
            // Buscar el requerimiento de aprobación GPPM G3 existente
            List<RequerimientoAprobacion> requerimientosGppmG3 = requerimientoAprobacionDao.findByIdInformeRenovacionAndIdGrupoAprobadorLd(
                informeRequest.getIdInformeRenovacion(), 
                idGrupoAprobadorGppmG3
            );
            
            if (requerimientosGppmG3.isEmpty()) {
                throw new DataNotFoundException("No se encontró requerimiento de aprobación GPPM G3 para el informe: " + informeRequest.getIdInformeRenovacion());
            }
            
            RequerimientoAprobacion requerimientoAprobacionGppmG3 = requerimientosGppmG3.get(0);
            asignarAuditoriaActualizacion(requerimientoAprobacionGppmG3, contexto);

            requerimientoAprobacionGppmG3.setEstado(
                    listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,
                            Constantes.LISTADO.ESTADO_APROBACION.APROBADO)
            );
            requerimientoAprobacionGppmG3.getUsuario().setIdUsuario(contexto.getUsuario().getIdUsuario());
            requerimientoAprobacionGppmG3.setDeObservacion(requestDTO.getObservacion());
            
            // 3.5.3 Registra el campo "Estado Aprobación GSE G3" = Asignado
            RequerimientoAprobacion requerimientoAprobacionGseG3 = new RequerimientoAprobacion();
            asignarAuditoriaCreacion(requerimientoAprobacionGseG3, contexto);

            requerimientoAprobacionGseG3.setIdInformeRenovacion(informeRequest.getIdInformeRenovacion());
            
            // NO asignar idRequerimiento - la FK espera SICOES_TC_REQUERIMIENTO que no existe en el modelo actual
            // requerimientoAprobacionGseG3.setIdRequerimiento(requerimientoAprobacionGppmG3.getIdRequerimiento());
            
            requerimientoAprobacionGseG3.setIdTipoLd(
                datosService.obtenerIdLd("TIPO_APROBACION", "APROBAR")
            );

            requerimientoAprobacionGseG3.setGrupo(
                    listadoDetalleService.obtenerListadoDetalle(
                            Constantes.LISTADO.GRUPOS.CODIGO,
                            Constantes.LISTADO.GRUPOS.G3
                    )
            );
            
            requerimientoAprobacionGseG3.getUsuario().setIdUsuario(solicitudPerfecionamientoContrato.getIdAprobadorG3());
            
            requerimientoAprobacionGseG3.setEstado(
                    listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,
                            Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO)
            );

            requerimientoAprobacionGseG3.setTipoAprobador(
                    listadoDetalleService.obtenerListadoDetalle(
                            Constantes.LISTADO.TIPO_EVALUADOR.CODIGO,
                            Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO
                    )
            );
            
            requerimientoAprobacionGseG3.setIdGrupoAprobadorLd(
                datosService.obtenerIdLd("GRUPO_APROBACION", "GSE")
            );

            // 3.5.4 Primero crear la notificación antes de guardar el requerimiento GSE G3
            Usuario gseG3Usuario = usuarioService.obtener(solicitudPerfecionamientoContrato.getIdAprobadorG3());
            String numExpediente = informeRenovacionContrato.getRequerimiento().getNuExpediente();
            Notificacion notificacion = notificacionAprobacionInformeService.notificacionInformePorEvaluar(gseG3Usuario, numExpediente, contexto);
            
            // Asignar el ID de notificación al nuevo requerimiento GSE G3 antes de guardarlo
            if (notificacion != null && notificacion.getIdNotificacion() != null) {
                requerimientoAprobacionGseG3.setIdNotificacion(notificacion.getIdNotificacion());
                logger.info("Asignando ID de notificación {} al nuevo requerimiento GSE G3", notificacion.getIdNotificacion());
            }
            
            RequerimientoAprobacion requerimientoAprobacionResult=requerimientoAprobacionDao.save(requerimientoAprobacionGseG3);
            historialAprobacionRenovacionService.registrarHistorialAprobacionRenovacion(requerimientoAprobacionResult, contexto);
            
            // Asignar el ID de notificación al requerimiento GPPM G3
            if (notificacion != null && notificacion.getIdNotificacion() != null) {
                requerimientoAprobacionGppmG3.setIdNotificacion(notificacion.getIdNotificacion());
                logger.info("Asignando ID de notificación {} al requerimiento GPPM G3", notificacion.getIdNotificacion());
            }
            // Guardar cambios
            requerimientoAprobacionDao.save(requerimientoAprobacionGppmG3);
        }

        return new AprobacionInformeCreateResponseDTO();
    }
    
    /**
     * Aprueba el informe de renovación GSE G3 - Nivel final de aprobación.
     * @param requestDTO DTO con datos de la aprobación
     * @param contexto contexto de la petición
     * @return DTO con el resultado de la aprobación
     */
    @Override
    public AprobacionInformeCreateResponseDTO aprobarInformeRenovacionGseG3(AprobacionInformeCreateRequestDTO requestDTO, Contexto contexto) {


        // 3.5 Iniciar iteración de lógica de negocio de Aprobación
        for (InformeAprobacionCreateRequestDTO informeRequest : requestDTO.getInformes()) {
            
            // 3.5.1 Obtiene datos SolicitudPerfecionamientoContrato
            Optional<InformeRenovacionContrato> informeOpt = informeRenovacionContratoDao.findById(informeRequest.getIdInformeRenovacion());
            InformeRenovacionContrato informeRenovacionContrato = informeOpt.get();
            // 3.5.2 Actualiza aprobación el campo "Estado Aprobación GSE G3" = Aprobado

            
            // Buscar el requerimiento de aprobación GSE G3 existente
            List<RequerimientoAprobacion> requerimientosGseG3 = requerimientoAprobacionDao.findByIdInformeRenovacionAndIdGrupoAprobadorLd(
                informeRequest.getIdInformeRenovacion(),
                    datosService.obtenerIdLd("GRUPO_APROBACION", "GSE")
            );
            
            if (requerimientosGseG3.isEmpty()) {
                throw new DataNotFoundException("No se encontró requerimiento de aprobación GSE G3 para el informe: " + informeRequest.getIdInformeRenovacion());
            }
            
            RequerimientoAprobacion requerimientoAprobacionGseG3 = requerimientosGseG3.get(0);
            asignarAuditoriaActualizacion(requerimientoAprobacionGseG3, contexto);

            requerimientoAprobacionGseG3.setEstado(
                    listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,
                            Constantes.LISTADO.ESTADO_APROBACION.APROBADO)
            );
            requerimientoAprobacionGseG3.getUsuario().setIdUsuario(contexto.getUsuario().getIdUsuario());
            requerimientoAprobacionGseG3.setDeObservacion(requestDTO.getObservacion());
            
            // 3.5.3 Actualiza el campo estado requerimiento renovacion = 'CONCLUIDO'
            RequerimientoRenovacion requerimientoRenovacion = requerimientoRenovacionDao.obtenerPorId(
                informeRenovacionContrato.getRequerimiento().getIdReqRenovacion());

            asignarAuditoriaActualizacionRequerimiento(requerimientoRenovacion, contexto);

            requerimientoRenovacion.setEstadoReqRenovacion(
                datosService.obtenerLd("ESTADO_REQUERIMIENTO", "CONCLUIDO")
            );
            requerimientoRenovacionDao.save(requerimientoRenovacion);

            // 3.5.4 Envía notificación por correo al evaluador de contratos para solicitud de contratos
            Usuario evaluadorContratosUsuario = usuarioService.obtener(informeRenovacionContrato.getUsuario().getIdUsuario());
            String numExpediente = informeRenovacionContrato.getRequerimiento().getNuExpediente();

            Notificacion notificacion =  notificacionAprobacionInformeService.notificacionSolicitudDeContratos(evaluadorContratosUsuario, numExpediente, contexto);
            
            // Asignar el ID de notificación al requerimiento GSE G3
            if (notificacion != null && notificacion.getIdNotificacion() != null) {
                requerimientoAprobacionGseG3.setIdNotificacion(notificacion.getIdNotificacion());
                logger.info("Asignando ID de notificación {} al requerimiento GSE G3", notificacion.getIdNotificacion());
            }
            // Guardar cambios
            requerimientoAprobacionDao.save(requerimientoAprobacionGseG3);
        }

        return new AprobacionInformeCreateResponseDTO();
    }

    /**
     * Asigna los valores de auditoría de actualización a un requerimiento
     * @param requerimiento El requerimiento a actualizar
     * @param contexto El contexto con la información del usuario
     */
    private void asignarAuditoriaActualizacion(RequerimientoAprobacion requerimiento, Contexto contexto) {
        requerimiento.setUsuActualizacion(contexto.getUsuario().getIdUsuario().toString());
        requerimiento.setIpActualizacion(contexto.getIp());
        requerimiento.setFecActualizacion(new Date());
        requerimiento.setFeAprobacion(new Date());
    }

    /**
     * Asigna los valores de auditoría de creación a un requerimiento
     * @param requerimiento El requerimiento a crear
     * @param contexto El contexto con la información del usuario
     */
    private void asignarAuditoriaCreacion(RequerimientoAprobacion requerimiento, Contexto contexto) {
        requerimiento.setIpCreacion(contexto.getIp());
        requerimiento.setFeAsignacion(new Date());
        requerimiento.setFecCreacion(new Date());
        requerimiento.setUsuCreacion(contexto.getUsuario().getIdUsuario().toString());
    }

    /**
     * Asigna los valores de auditoría de actualización a un informe de renovación
     * @param informe El informe a actualizar
     * @param contexto El contexto con la información del usuario
     */
    private void asignarAuditoriaActualizacionInforme(InformeRenovacionContrato informe, Contexto contexto) {
        informe.setIpActualizacion(contexto.getIp());
        informe.setUsuActualizacion(contexto.getUsuario().getIdUsuario().toString());
        informe.setFecActualizacion(new Date());
    }

    /**
     * Asigna los valores de auditoría de actualización a un requerimiento de renovación
     * @param requerimiento El requerimiento a actualizar
     * @param contexto El contexto con la información del usuario
     */
    private void asignarAuditoriaActualizacionRequerimiento(RequerimientoRenovacion requerimiento, Contexto contexto) {
        requerimiento.setIpActualizacion(contexto.getIp());
        requerimiento.setUsuActualizacion(contexto.getUsuario().getIdUsuario().toString());
        requerimiento.setFecActualizacion(new Date());
    }


    /**
     * Obtiene el contrato de perfeccionamiento asociado a un informe de renovación
     * @param informeRenovacionContrato El informe de renovación
     * @return El contrato de perfeccionamiento asociado
     * @throws DataNotFoundException Si no se encuentra el contrato
     */
    private SolicitudPerfecionamientoContrato obtenerSolicitudPerfecionamiento(InformeRenovacionContrato informeRenovacionContrato) {
        Long idSolicitud = informeRenovacionContrato.getRequerimiento().getSolicitudPerfil().getIdSolicitud();
        List<SolicitudPerfecionamientoContrato> listaPerfilesAprobadoresBySolicitud =
            solicitudPerfecionamientoContratoDao.getPerfilAprobadorByIdPerfilListadoDetalle(idSolicitud);
        
        if (listaPerfilesAprobadoresBySolicitud.isEmpty()) {
            throw new DataNotFoundException("No se encontró contrato de perfeccionamiento para la solicitud: " + idSolicitud);
        }
        
        return listaPerfilesAprobadoresBySolicitud.get(0);
    }

}
