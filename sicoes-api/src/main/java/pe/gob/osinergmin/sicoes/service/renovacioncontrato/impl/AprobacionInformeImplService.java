package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;
import pe.gob.osinergmin.sicoes.model.Notificacion;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeAprobacionCreateRequestDTO;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.SolicitudPerfecionamientoContratoDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoRenovacionDao;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.SolicitudPerfecionamientoContrato;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.ListadoDetalleRenovacionContrato;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;

import java.util.Date;
import java.util.Optional;
import java.util.List;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.AprobacionInformeCreateRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.AprobacionInformeCreateResponseDTO;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.AprobacionInformeService;
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
    /**
     * Aprueba el informe de renovación G1.
     * @param requestDTO DTO con datos de la aprobación
     * @param contexto contexto de la petición
     * @return DTO con el resultado de la aprobación
     */
    @Override
    public AprobacionInformeCreateResponseDTO aprobarInformeRenovacionG1(AprobacionInformeCreateRequestDTO requestDTO, Contexto contexto) {
        // Validación 3.0: idUsuario obligatorio
        /*if (requestDTO.getIdUsuario() == null) {
            throw new DataNotFoundException("El campo id Usuario es obligatorio");
        }*/
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
        // 3.4  validaciones en   validaciones en un solo bucle
        for (InformeAprobacionCreateRequestDTO informeDTO : requestDTO.getInformes()) {
            if (informeDTO.getIdInformeRenovacion() == null) {
                throw new DataNotFoundException("Debe ingresar idInformeRenovacion en todos los informes");
            }
            Optional<InformeRenovacionContrato> informeOpt = informeRenovacionContratoDao.findById(informeDTO.getIdInformeRenovacion());
            if (!informeOpt.isPresent()) {
                throw new DataNotFoundException("Código informe renovación no encontrado "+informeDTO.getIdInformeRenovacion().toString());
            }
            InformeRenovacionContrato informeRenovacionContrato = informeOpt.get();
            Long idSolicitud = informeRenovacionContrato.getRequerimiento().getSolicitudPerfil().getIdSolicitud();

            List<SolicitudPerfecionamientoContrato> listaPerfilesAprobadoresBySolicitud = solicitudPerfecionamientoContratoDao.getPerfilAprobadorByIdPerfilListadoDetalle(idSolicitud);
            if (listaPerfilesAprobadoresBySolicitud == null || listaPerfilesAprobadoresBySolicitud.isEmpty()) {
                throw new DataNotFoundException(Constantes.CODIGO_MENSAJE.PERFIL_APROBADOR_RENOVACION_CONTRATO_NO_ENCONTRADO);
            }
            /*SolicitudPerfecionamientoContrato solicitudPerfecionamientoContrato = listaPerfilesAprobadoresBySolicitud.get(0);
            if (!solicitudPerfecionamientoContrato.getIdAprobadorG1().equals(requestDTO.getIdUsuario())) {
                throw new DataNotFoundException("El usuario no coincide con el perfil aprobador G1");
            }*/
        }

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
            ListadoDetalle jefeUnidadG1GrupoAprobadorLD = listadoDetalleService.obtenerListadoDetalle(
                "GRUPO_APROBACION", 
                "JEFE_UNIDAD"
            );
            
            // Buscar el requerimiento de aprobación G1 existente
            List<RequerimientoAprobacion> requerimientosG1 = requerimientoAprobacionDao.findByIdInformeRenovacionAndIdGrupoAprobadorLd(
                informeRequest.getIdInformeRenovacion(), 
                jefeUnidadG1GrupoAprobadorLD.getIdListadoDetalle()
            );
            
            if (requerimientosG1.isEmpty()) {
                throw new DataNotFoundException("No se encontró requerimiento de aprobación G1 para el informe: " + informeRequest.getIdInformeRenovacion());
            }
            
            RequerimientoAprobacion requerimientoAprobacionG1 = requerimientosG1.get(0);
            
            ListadoDetalle aprobadoEstadoLD = listadoDetalleService.obtenerListadoDetalle(
                "ESTADO_APROBACION", 
                "APROBADO"
            );
            requerimientoAprobacionG1.setUsuActualizacion(solicitudPerfecionamientoContrato.getIdAprobadorG1().toString());
            requerimientoAprobacionG1.setFeAprobacion(new Date());
            requerimientoAprobacionG1.setFecActualizacion(new Date());
            requerimientoAprobacionG1.setIdEstadoLd(aprobadoEstadoLD.getIdListadoDetalle());
            requerimientoAprobacionG1.setIdUsuario(solicitudPerfecionamientoContrato.getIdAprobadorG1());
            requerimientoAprobacionG1.setDeObservacion(requestDTO.getObservacion());

            
            // 3.5.3 Registra el campo "Estado Aprobación Gerente División" = Asignado
            RequerimientoAprobacion requerimientoAprobacionG2 = new RequerimientoAprobacion();
            requerimientoAprobacionG2.setFeAsignacion(new Date());
            requerimientoAprobacionG2.setFecCreacion(new Date());
            requerimientoAprobacionG2.setUsuCreacion(solicitudPerfecionamientoContrato.getIdAprobadorG1().toString());

            requerimientoAprobacionG2.setIdInformeRenovacion(informeRequest.getIdInformeRenovacion());
            requerimientoAprobacionG2.setIdTipoLd(null); // revisar si aplica
            
            ListadoDetalle g2GrupoLD = listadoDetalleService.obtenerListadoDetalle(
                "GRUPOS", 
                "G2"
            );
            requerimientoAprobacionG2.setIdGrupoLd(g2GrupoLD.getIdListadoDetalle());
            
            requerimientoAprobacionG2.setIdUsuario(solicitudPerfecionamientoContrato.getIdAprobadorG2());
            
            ListadoDetalle asignadoEstadoLD = listadoDetalleService.obtenerListadoDetalle(
                "ESTADO_APROBACION", 
                "ASIGNADO"
            );
            requerimientoAprobacionG2.setIdEstadoLd(asignadoEstadoLD.getIdListadoDetalle());
            
            ListadoDetalle tecnicoTipoEvaluadorLD = listadoDetalleService.obtenerListadoDetalle(
                "TIPO_EVALUADOR", 
                "TECNICO"
            );
            requerimientoAprobacionG2.setIdTipoAprobadorLd(tecnicoTipoEvaluadorLD.getIdListadoDetalle());
            
            ListadoDetalle grupoAprobadorLD = listadoDetalleService.obtenerListadoDetalle(
                "GRUPO_APROBACION", 
                "GERENTE"
            );
            requerimientoAprobacionG2.setIdGrupoAprobadorLd(grupoAprobadorLD.getIdListadoDetalle());
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

        // 3.4 validaciones en un solo bucle - Valida id Usuario de List<InformeAprobacionCreateRequestDTO> informes
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
            if (!solicitudPerfecionamientoContrato.getIdAprobadorG2().equals(requestDTO.getIdUsuario())) {
                throw new DataNotFoundException("El usuario no coincide con el perfil aprobador G2");
            }
        }

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
            ListadoDetalle gerenteUnidadG2GrupoAprobadorLD = listadoDetalleService.obtenerListadoDetalle(
                "GRUPO_APROBACION", 
                "GERENTE"
            );
            
            // Buscar el requerimiento de aprobación G2 existente
            List<RequerimientoAprobacion> requerimientosG2 = requerimientoAprobacionDao.findByIdInformeRenovacionAndIdGrupoAprobadorLd(
                informeRequest.getIdInformeRenovacion(), 
                gerenteUnidadG2GrupoAprobadorLD.getIdListadoDetalle()
            );
            
            if (requerimientosG2.isEmpty()) {
                throw new DataNotFoundException("No se encontró requerimiento de aprobación G2 para el informe: " + informeRequest.getIdInformeRenovacion());
            }
            
            RequerimientoAprobacion requerimientoAprobacionG2 = requerimientosG2.get(0);
            
            ListadoDetalle aprobadoEstadoLD = listadoDetalleService.obtenerListadoDetalle(
                "ESTADO_APROBACION", 
                "APROBADO"
            );

            requerimientoAprobacionG2.setIdEstadoLd(aprobadoEstadoLD.getIdListadoDetalle());
            requerimientoAprobacionG2.setIdUsuario(solicitudPerfecionamientoContrato.getIdAprobadorG2());
            requerimientoAprobacionG2.setDeObservacion(requestDTO.getObservacion());
            
            // 3.5.3 Actualiza el campo "Estado Aprobación Informe" = Concluido
            ListadoDetalle concluidoEstadoAprobacionInforme = listadoDetalleService.obtenerListadoDetalle(
                "ESTADO_REQUERIMIENTO", 
                "CONCLUIDO"
            );
            
            // Crear la instancia de ListadoDetalleRenovacionContrato basada en ListadoDetalle
            ListadoDetalleRenovacionContrato estadoAprobacionInforme = new ListadoDetalleRenovacionContrato();
            estadoAprobacionInforme.setIdListadoDetalle(concluidoEstadoAprobacionInforme.getIdListadoDetalle());
            estadoAprobacionInforme.setIdListado(concluidoEstadoAprobacionInforme.getIdListado());
            estadoAprobacionInforme.setCodigo(concluidoEstadoAprobacionInforme.getCodigo());
            estadoAprobacionInforme.setNombre(concluidoEstadoAprobacionInforme.getNombre());
            estadoAprobacionInforme.setDescripcion(concluidoEstadoAprobacionInforme.getDescripcion());
            estadoAprobacionInforme.setValor(concluidoEstadoAprobacionInforme.getValor());
            
            informeRenovacionContrato.setEstadoAprobacionInforme(estadoAprobacionInforme);
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

        // 3.4 validaciones en un solo bucle - Valida id Usuario de List<InformeAprobacionCreateRequestDTO> informes
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
        }

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
            ListadoDetalle gppmG3GrupoAprobadorLD = listadoDetalleService.obtenerListadoDetalle(
                "GRUPO_APROBACION", 
                "GPPM"
            );
            
            // Buscar el requerimiento de aprobación GPPM G3 existente
            List<RequerimientoAprobacion> requerimientosGppmG3 = requerimientoAprobacionDao.findByIdInformeRenovacionAndIdGrupoAprobadorLd(
                informeRequest.getIdInformeRenovacion(), 
                gppmG3GrupoAprobadorLD.getIdListadoDetalle()
            );
            
            if (requerimientosGppmG3.isEmpty()) {
                throw new DataNotFoundException("No se encontró requerimiento de aprobación GPPM G3 para el informe: " + informeRequest.getIdInformeRenovacion());
            }
            
            RequerimientoAprobacion requerimientoAprobacionGppmG3 = requerimientosGppmG3.get(0);
            
            ListadoDetalle aprobadoEstadoLD = listadoDetalleService.obtenerListadoDetalle(
                "ESTADO_APROBACION", 
                "APROBADO"
            );
            
            requerimientoAprobacionGppmG3.setIdEstadoLd(aprobadoEstadoLD.getIdListadoDetalle());
            requerimientoAprobacionGppmG3.setIdUsuario(solicitudPerfecionamientoContrato.getIdAprobadorG3());
            requerimientoAprobacionGppmG3.setDeObservacion(requestDTO.getObservacion());
            
            // 3.5.3 Registra el campo "Estado Aprobación GSE G3" = Asignado
            RequerimientoAprobacion requerimientoAprobacionGseG3 = new RequerimientoAprobacion();
            
            requerimientoAprobacionGseG3.setIdInformeRenovacion(informeRequest.getIdInformeRenovacion());
            requerimientoAprobacionGseG3.setIdTipoLd(null); // revisar si aplica
            
            ListadoDetalle g3GrupoLD = listadoDetalleService.obtenerListadoDetalle(
                "GRUPOS", 
                "G3"
            );
            requerimientoAprobacionGseG3.setIdGrupoLd(g3GrupoLD.getIdListadoDetalle());
            
            requerimientoAprobacionGseG3.setIdUsuario(solicitudPerfecionamientoContrato.getIdAprobadorG3());
            
            ListadoDetalle asignadoEstadoLD = listadoDetalleService.obtenerListadoDetalle(
                "ESTADO_APROBACION", 
                "ASIGNADO"
            );
            requerimientoAprobacionGseG3.setIdEstadoLd(asignadoEstadoLD.getIdListadoDetalle());
            
            ListadoDetalle tecnicoTipoEvaluadorLD = listadoDetalleService.obtenerListadoDetalle(
                "TIPO_EVALUADOR", 
                "TECNICO"
            );
            requerimientoAprobacionGseG3.setIdTipoAprobadorLd(tecnicoTipoEvaluadorLD.getIdListadoDetalle());
            
            ListadoDetalle grupoAprobadorLD = listadoDetalleService.obtenerListadoDetalle(
                "GRUPO_APROBACION", 
                "GSE"
            );
            requerimientoAprobacionGseG3.setIdGrupoAprobadorLd(grupoAprobadorLD.getIdListadoDetalle());
            
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

        // 3.4 validaciones en un solo bucle - Valida id Usuario de List<InformeAprobacionCreateRequestDTO> informes
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
            if (!solicitudPerfecionamientoContrato.getIdAprobadorG3().equals(requestDTO.getIdUsuario())) {
                throw new DataNotFoundException("El usuario no coincide con el perfil aprobador G3");
            }
        }

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
            ListadoDetalle gseG3GrupoAprobadorLD = listadoDetalleService.obtenerListadoDetalle(
                "GRUPO_APROBACION", 
                "GSE"
            );
            
            // Buscar el requerimiento de aprobación GSE G3 existente
            List<RequerimientoAprobacion> requerimientosGseG3 = requerimientoAprobacionDao.findByIdInformeRenovacionAndIdGrupoAprobadorLd(
                informeRequest.getIdInformeRenovacion(), 
                gseG3GrupoAprobadorLD.getIdListadoDetalle()
            );
            
            if (requerimientosGseG3.isEmpty()) {
                throw new DataNotFoundException("No se encontró requerimiento de aprobación GSE G3 para el informe: " + informeRequest.getIdInformeRenovacion());
            }
            
            RequerimientoAprobacion requerimientoAprobacionGseG3 = requerimientosGseG3.get(0);
            
            ListadoDetalle aprobadoEstadoLD = listadoDetalleService.obtenerListadoDetalle(
                "ESTADO_APROBACION", 
                "APROBADO"
            );
            
            requerimientoAprobacionGseG3.setIdEstadoLd(aprobadoEstadoLD.getIdListadoDetalle());
            requerimientoAprobacionGseG3.setIdUsuario(requestDTO.getIdUsuario());
            requerimientoAprobacionGseG3.setDeObservacion(requestDTO.getObservacion());
            
            // 3.5.3 Actualiza el campo estado requerimiento renovacion = 'CONCLUIDO'
            RequerimientoRenovacion requerimientoRenovacion = requerimientoRenovacionDao.obtenerPorId(
                informeRenovacionContrato.getRequerimiento().getIdReqRenovacion());
               
            ListadoDetalle concluidoEstadoReqRenovacion = listadoDetalleService.obtenerListadoDetalle(
                "ESTADO_REQUERIMIENTO", 
                "CONCLUIDO"
            );
            
            requerimientoRenovacion.setEstadoReqRenovacion(concluidoEstadoReqRenovacion);
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
