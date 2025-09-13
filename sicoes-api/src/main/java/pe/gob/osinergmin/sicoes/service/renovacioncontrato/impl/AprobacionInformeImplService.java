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

import pe.gob.osinergmin.sicoes.service.renovacioncontrato.ValidaAprobacionInformeService;
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



    @Override
    public AprobacionInformeRenovacionCreateResponseDTO aprobarInformeRenovacion(
            AprobacionInformeRenovacionCreateRequestDTO requestDTO,
            Contexto contexto) throws DataNotFoundException {

        validaAprobacionService.validarInformeRenovacion(requestDTO, contexto);

        for (Long idReqApr : requestDTO.getIdRequerimientosAprobacion()) {
            try {
                // 1) Obtener requerimiento
                RequerimientoAprobacion entity = requerimientoAprobacionDao.obtenerPorId(idReqApr);
                ListadoDetalle grupoAprobadorLd = listadoDetalleService.obtener(
                        entity.getIdGrupoAprobadorLd(),contexto);
                String codigoGrupo = grupoAprobadorLd.getCodigo();
                // 4) Enrutar según grupo aprobador
                AprobacionInformeCreateResponseDTO resultado;
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
                        resultado = aprobarInformeRenovacionG1(reqAprob, contexto);
                        break;
                    case "GERENTE":
                        resultado = aprobarInformeRenovacionG2(reqAprob, contexto);
                        break;
                    case "GPPM":
                        resultado = aprobarInformeRenovacionGppmG3(reqAprob, contexto);
                        break;
                    case "GSE":
                        resultado = aprobarInformeRenovacionGseG3(reqAprob, contexto);
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

        return null;
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
            Long idSolicitud = informeRenovacionContrato.getRequerimiento().getSolicitudPerfil().getIdSolicitud();

            List<SolicitudPerfecionamientoContrato> listaPerfilesAprobadoresBySolicitud =
                solicitudPerfecionamientoContratoDao.getPerfilAprobadorByIdPerfilListadoDetalle(idSolicitud);

            SolicitudPerfecionamientoContrato solicitudPerfecionamientoContrato = listaPerfilesAprobadoresBySolicitud.get(0);
            
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
            
            requerimientoAprobacionG1.setUsuActualizacion(contexto.getUsuario().getIdUsuario().toString());
            requerimientoAprobacionG1.setIpActualizacion(contexto.getIp());
            requerimientoAprobacionG1.setFecActualizacion(new Date());
            requerimientoAprobacionG1.setFeAprobacion(new Date());
            requerimientoAprobacionG1.setIdEstadoLd(
                datosService.obtenerIdLd("ESTADO_APROBACION", "APROBADO")
            );
            requerimientoAprobacionG1.setIdUsuario(solicitudPerfecionamientoContrato.getIdAprobadorG1());
            requerimientoAprobacionG1.setDeObservacion(requestDTO.getObservacion());

            
            // 3.5.3 Registra el campo "Estado Aprobación Gerente División" = Asignado
            RequerimientoAprobacion requerimientoAprobacionG2 = new RequerimientoAprobacion();
            requerimientoAprobacionG2.setIpCreacion(contexto.getIp());
            requerimientoAprobacionG2.setFeAsignacion(new Date());
            requerimientoAprobacionG2.setFecCreacion(new Date());
            requerimientoAprobacionG2.setUsuCreacion(contexto.getUsuario().getIdUsuario().toString());

            requerimientoAprobacionG2.setIdInformeRenovacion(informeRequest.getIdInformeRenovacion());

            requerimientoAprobacionG2.setIdTipoLd(
                datosService.obtenerIdLd("TIPO_APROBACION", "FIRMAR")
            );

            requerimientoAprobacionG2.setIdGrupoLd(
                datosService.obtenerIdLd("GRUPOS", "G2")
            );
            
            requerimientoAprobacionG2.setIdUsuario(solicitudPerfecionamientoContrato.getIdAprobadorG2());
            
            requerimientoAprobacionG2.setIdEstadoLd(
                datosService.obtenerIdLd("ESTADO_APROBACION", "ASIGNADO")
            );
            
            requerimientoAprobacionG2.setIdTipoAprobadorLd(
                datosService.obtenerIdLd("TIPO_EVALUADOR", "APROBADOR_TECNICO")
            );
            
            requerimientoAprobacionG2.setIdGrupoAprobadorLd(
                datosService.obtenerIdLd("GRUPO_APROBACION", "GERENTE")
            );
            requerimientoAprobacionDao.save(requerimientoAprobacionG2);
            // 3.5.4 Realiza notificación
            Usuario usuarioG2 = usuarioService.obtener(solicitudPerfecionamientoContrato.getIdAprobadorG2());
            String numExpediente = informeRenovacionContrato.getRequerimiento().getNuExpediente();
            Notificacion notificacion = notificacionAprobacionInformeService.notificacionInformePorAprobaryFirmar(usuarioG2, numExpediente, contexto);
            requerimientoAprobacionG1.setNotificacion(notificacion);
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


            Long idSolicitud = informeRenovacionContrato.getRequerimiento().getSolicitudPerfil().getIdSolicitud();
            
            List<SolicitudPerfecionamientoContrato> listaPerfilesAprobadoresBySolicitud =
                solicitudPerfecionamientoContratoDao.getPerfilAprobadorByIdPerfilListadoDetalle(idSolicitud);

            SolicitudPerfecionamientoContrato solicitudPerfecionamientoContrato = listaPerfilesAprobadoresBySolicitud.get(0);
            
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
            requerimientoAprobacionG2.setUsuActualizacion(contexto.getUsuario().getIdUsuario().toString());
            requerimientoAprobacionG2.setIpActualizacion(contexto.getIp());
            requerimientoAprobacionG2.setFecActualizacion(new Date());
            requerimientoAprobacionG2.setFeAprobacion(new Date());

            requerimientoAprobacionG2.setIdEstadoLd(
                datosService.obtenerIdLd("ESTADO_APROBACION", "APROBADO")
            );
            requerimientoAprobacionG2.setIdUsuario(solicitudPerfecionamientoContrato.getIdAprobadorG2());
            requerimientoAprobacionG2.setDeObservacion(requestDTO.getObservacion());
            
            // 3.5.3 Actualiza el campo "Estado Aprobación Informe" = Concluido
            ListadoDetalle concluidoEstadoAprobacionInforme = listadoDetalleService.obtenerListadoDetalle(
                "ESTADO_REQUERIMIENTO", 
                "CONCLUIDO"
            );
            informeRenovacionContrato.setEstadoAprobacionInforme(concluidoEstadoAprobacionInforme);

            informeRenovacionContrato.setUsuActualizacion(solicitudPerfecionamientoContrato.getIdAprobadorG1().toString());
            informeRenovacionContrato.setFecActualizacion(new Date());


            informeRenovacionContratoDao.save(informeRenovacionContrato);
            
            // 3.5.4 Envía notificación por correo al rol Evaluador de Contratos para la Evaluación de la Invitación
            Usuario evaluadorContratosUsuario = usuarioService.obtener(informeRenovacionContrato.getUsuario().getIdUsuario());
            String numExpediente = informeRenovacionContrato.getRequerimiento().getNuExpediente();

            Notificacion notificacion =  notificacionAprobacionInformeService.notificacionInformePorRevisar(evaluadorContratosUsuario, numExpediente, contexto);
            requerimientoAprobacionG2.setNotificacion(notificacion);

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
            Long idSolicitud = informeRenovacionContrato.getRequerimiento().getSolicitudPerfil().getIdSolicitud();
            
            List<SolicitudPerfecionamientoContrato> listaPerfilesAprobadoresBySolicitud = 
                solicitudPerfecionamientoContratoDao.getPerfilAprobadorByIdPerfilListadoDetalle(idSolicitud);

            SolicitudPerfecionamientoContrato solicitudPerfecionamientoContrato = listaPerfilesAprobadoresBySolicitud.get(0);
            
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
            requerimientoAprobacionGppmG3.setUsuActualizacion(contexto.getUsuario().getIdUsuario().toString());
            requerimientoAprobacionGppmG3.setIpActualizacion(contexto.getIp());
            requerimientoAprobacionGppmG3.setFecActualizacion(new Date());
            requerimientoAprobacionGppmG3.setFeAprobacion(new Date());

            requerimientoAprobacionGppmG3.setIdEstadoLd(
                datosService.obtenerIdLd("ESTADO_APROBACION", "APROBADO")
            );
            requerimientoAprobacionGppmG3.setIdUsuario(solicitudPerfecionamientoContrato.getIdAprobadorG3());
            requerimientoAprobacionGppmG3.setDeObservacion(requestDTO.getObservacion());
            
            // 3.5.3 Registra el campo "Estado Aprobación GSE G3" = Asignado
            RequerimientoAprobacion requerimientoAprobacionGseG3 = new RequerimientoAprobacion();
            requerimientoAprobacionGseG3.setIpCreacion(contexto.getIp());
            requerimientoAprobacionGseG3.setFeAsignacion(new Date());
            requerimientoAprobacionGseG3.setFecCreacion(new Date());
            requerimientoAprobacionGseG3.setUsuCreacion(contexto.getUsuario().getIdUsuario().toString());

            requerimientoAprobacionGseG3.setIdInformeRenovacion(informeRequest.getIdInformeRenovacion());
            
            requerimientoAprobacionGseG3.setIdTipoLd(
                datosService.obtenerIdLd("TIPO_APROBACION", "APROBAR")
            );
            
            requerimientoAprobacionGseG3.setIdGrupoLd(
                datosService.obtenerIdLd("GRUPOS", "G3")
            );
            
            requerimientoAprobacionGseG3.setIdUsuario(solicitudPerfecionamientoContrato.getIdAprobadorG3());
            
            requerimientoAprobacionGseG3.setIdEstadoLd(
                datosService.obtenerIdLd("ESTADO_APROBACION", "ASIGNADO")
            );
            
            requerimientoAprobacionGseG3.setIdTipoAprobadorLd(
                datosService.obtenerIdLd("TIPO_EVALUADOR", "APROBADOR_TECNICO")
            );
            
            requerimientoAprobacionGseG3.setIdGrupoAprobadorLd(
                datosService.obtenerIdLd("GRUPO_APROBACION", "GSE")
            );
            
            requerimientoAprobacionDao.save(requerimientoAprobacionGseG3);
            
            // 3.5.4 Envía notificación por correo al rol GSE G3 para la evaluación
            Usuario gseG3Usuario = usuarioService.obtener(solicitudPerfecionamientoContrato.getIdAprobadorG3());
            String numExpediente = informeRenovacionContrato.getRequerimiento().getNuExpediente();
            Notificacion notificacion = notificacionAprobacionInformeService.notificacionInformePorEvaluar(gseG3Usuario, numExpediente, contexto);
            requerimientoAprobacionGppmG3.setNotificacion(notificacion);
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
            Long idSolicitud = informeRenovacionContrato.getRequerimiento().getSolicitudPerfil().getIdSolicitud();
            
            List<SolicitudPerfecionamientoContrato> listaPerfilesAprobadoresBySolicitud = 
                solicitudPerfecionamientoContratoDao.getPerfilAprobadorByIdPerfilListadoDetalle(idSolicitud);

            SolicitudPerfecionamientoContrato solicitudPerfecionamientoContrato = listaPerfilesAprobadoresBySolicitud.get(0);
            
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
            requerimientoAprobacionGseG3.setUsuActualizacion(contexto.getUsuario().getIdUsuario().toString());
            requerimientoAprobacionGseG3.setIpActualizacion(contexto.getIp());
            requerimientoAprobacionGseG3.setFecActualizacion(new Date());
            requerimientoAprobacionGseG3.setFeAprobacion(new Date());

            requerimientoAprobacionGseG3.setIdEstadoLd(
                datosService.obtenerIdLd("ESTADO_APROBACION", "APROBADO")
            );
            requerimientoAprobacionGseG3.setIdUsuario(requestDTO.getIdUsuario());
            requerimientoAprobacionGseG3.setDeObservacion(requestDTO.getObservacion());
            
            // 3.5.3 Actualiza el campo estado requerimiento renovacion = 'CONCLUIDO'
            RequerimientoRenovacion requerimientoRenovacion = requerimientoRenovacionDao.obtenerPorId(
                informeRenovacionContrato.getRequerimiento().getIdReqRenovacion());
               
            requerimientoRenovacion.setEstadoReqRenovacion(
                datosService.obtenerLd("ESTADO_REQUERIMIENTO", "CONCLUIDO")
            );
            requerimientoRenovacionDao.save(requerimientoRenovacion);
            
            // 3.5.4 Envía notificación por correo al evaluador de contratos para solicitud de contratos
            Usuario evaluadorContratosUsuario = usuarioService.obtener(informeRenovacionContrato.getUsuario().getIdUsuario());
            String numExpediente = informeRenovacionContrato.getRequerimiento().getNuExpediente();

            Notificacion notificacion =  notificacionAprobacionInformeService.notificacionSolicitudDeContratos(evaluadorContratosUsuario, numExpediente, contexto);
            requerimientoAprobacionGseG3.setNotificacion(notificacion);
            // Guardar cambios
            requerimientoAprobacionDao.save(requerimientoAprobacionGseG3);
        }

        return new AprobacionInformeCreateResponseDTO();
    }


}
