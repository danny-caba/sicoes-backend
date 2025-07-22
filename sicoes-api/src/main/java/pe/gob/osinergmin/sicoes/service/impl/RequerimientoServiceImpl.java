package pe.gob.osinergmin.sicoes.service.impl;

import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ACCESO_NO_AUTORIZADO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.APROBACION_NO_ENCONTRADA;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_FIN_ANTES_INICIO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_INICIO_ANTES_HOY;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ESTADO_APROBACION_NO_ENVIADO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.REQUERIMIENTO_NO_ENCONTRADO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.SIAF_NO_ENVIADO;
import static pe.gob.osinergmin.sicoes.util.Constantes.ROLES.APROBADOR_GPPM;
import static pe.gob.osinergmin.sicoes.util.Constantes.ROLES.APROBADOR_GSE;
import static pe.gob.osinergmin.sicoes.util.Constantes.ROLES.APROBADOR_TECNICO;
import static pe.gob.osinergmin.sicoes.util.Constantes.ROLES.EVALUADOR_CONTRATOS;
import static pe.gob.osinergmin.sicoes.util.Constantes.ROLES.RESPONSABLE_TECNICO;

import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DireccionxClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.ExpedienteOutRO;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.PerfilAprobador;
import pe.gob.osinergmin.sicoes.model.Rol;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.UsuarioRol;
import pe.gob.osinergmin.sicoes.model.dto.DivisionDTO;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Requerimiento;
import pe.gob.osinergmin.sicoes.model.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDTO;
import pe.gob.osinergmin.sicoes.model.dto.RequerimientoAprobacionDTO;
import pe.gob.osinergmin.sicoes.repository.PerfilAprobadorDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.DivisionService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.RequerimientoService;
import pe.gob.osinergmin.sicoes.service.RolService;
import pe.gob.osinergmin.sicoes.service.UsuarioRolService;
import pe.gob.osinergmin.sicoes.service.UsuarioService;
import pe.gob.osinergmin.sicoes.util.ArchivoUtil;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class RequerimientoServiceImpl implements RequerimientoService {

    private static final Logger logger = LogManager.getLogger(RequerimientoServiceImpl.class);

    @Autowired
    private RequerimientoDao requerimientoDao;

    @Autowired
    private RequerimientoAprobacionDao aprobacionDao;

    @Autowired
    private ListadoDetalleService listadoDetalleService;

    @Autowired
    private Environment env;

    @Autowired
    private ArchivoService archivoService;

    @Autowired
    private SigedApiConsumer sigedApiConsumer;

    @Value("${path.temporal}")
    private String pathTemporal;

    @Value("${path.jasper}")
    private String pathJasper;

    @Autowired
    private ArchivoUtil archivoUtil;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRolService usuarioRolService;

    @Autowired
    private RolService rolService;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private PerfilAprobadorDao perfilAprobadorDao;

    @Autowired
    private RequerimientoAprobacionDao requerimientoAprobacionDao;

    @Value("${solicitud.requerimiento.contrato.pn}")
    private String SOLICITUD_REQUERIMIENTO_CONTRATO_PN;

    @Value("${siged.old.proyecto}")
    private String SIGLA_PROYECTO;

    @Value("${siged.ws.cliente.osinergmin.numero.documento}")
    private String OSI_DOCUMENTO;

    @Override
    @Transactional
    public Requerimiento guardar(Requerimiento requerimiento, Contexto contexto) {
        if(contexto.getUsuario().getRoles().stream().noneMatch(rol -> rol.getCodigo().equals(RESPONSABLE_TECNICO))) {
            throw new ValidacionException(ACCESO_NO_AUTORIZADO);
        }
        ListadoDetalle perfil = listadoDetalleService.obtener(requerimiento.getPerfil().getIdListadoDetalle(), contexto);
        requerimiento.setPerfil(perfil);
        ListadoDetalle estadoPreliminar = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQUERIMIENTO.CODIGO,
                Constantes.LISTADO.ESTADO_REQUERIMIENTO.PRELIMINAR);
        requerimiento.setEstado(estadoPreliminar);
        requerimiento.setFeRegistro(new Date());
        requerimiento.setRequerimientoUuid(UUID.randomUUID().toString());
        AuditoriaUtil.setAuditoriaRegistro(requerimiento, contexto);
        Requerimiento requerimientoDB = requerimientoDao.save(requerimiento);

        ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
                Constantes.LISTADO.TIPO_ARCHIVO.ARCHIVO_REQUERIMIENTO);

        String nuExpediente = generarArchivoSiged(requerimientoDB, tipoArchivo, contexto);
        requerimientoDB.setNuExpediente(nuExpediente);
        return requerimientoDao.save(requerimientoDB);
    }

    @Override
    public Requerimiento obtener(Long id, Contexto contexto) {
        return requerimientoDao.obtener(id).orElse(null);
    }

    @Override
    public void eliminar(Long aLong, Contexto contexto) { }

    @Override
    public Page<Requerimiento> listar(FiltroRequerimientoDTO filtro, Pageable pageable, Contexto contexto) {

        List<Long> divisionIds;
        List<DivisionDTO> divisiones;
        Date fechaInicio = null;
        Date fechaFin = null;

        validarFechas(filtro);

        // Coordinador de Gestion -> Responsable Tecnico
        boolean isCoordinador = contexto.getUsuario()
                .getRoles()
                .stream()
                .anyMatch(rol -> Objects.equals(rol.getCodigo(), RESPONSABLE_TECNICO));

        divisiones = isCoordinador
                ? divisionService.listarDivisionesCoordinador(contexto)
                : divisionService.listarDivisiones();

        if (divisiones == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ERROR_LISTA_DIVISIONES);
        }

        divisionIds = divisiones
                .stream()
                .map(DivisionDTO::getIdDivision)
                .collect(Collectors.toList());

        if (filtro.getFechaInicio() != null)
            fechaInicio = DateUtil.getInitDay(filtro.getFechaInicio());

        if (filtro.getFechaFin() != null)
            fechaFin = DateUtil.getEndDay(filtro.getFechaFin());

        return requerimientoDao.listarRequerimientos(
                filtro.getDivision(), filtro.getPerfil(), fechaInicio, fechaFin,
                filtro.getSupervisora(), filtro.getEstadoAprobacion(), divisionIds, pageable);

    }

    @Override
    @Transactional
    public Requerimiento archivar(Requerimiento requerimiento, Contexto contexto) {
        if(contexto.getUsuario().getRoles().stream().noneMatch(rol -> rol.getCodigo().equals(RESPONSABLE_TECNICO))) {
            throw new ValidacionException(ACCESO_NO_AUTORIZADO);
        }
        if (!Objects.equals(Constantes.LISTADO.ESTADO_REQUERIMIENTO.EN_PROCESO,
                requerimiento.getEstado().getCodigo())) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVAR_ESTADO_EN_PROCESO);
        }
        ListadoDetalle estadoAprobacion = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQUERIMIENTO.CODIGO,
                Constantes.LISTADO.ESTADO_REQUERIMIENTO.EN_APROBACION);

        ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
                Constantes.LISTADO.TIPO_ARCHIVO.ARCHIVO_ARCHIVAR_REQUERIMIENTO);

        ListadoDetalle revisionEnAprobacion = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REVISION.CODIGO,
                Constantes.LISTADO.ESTADO_REVISION.EN_APROBACION);

        requerimiento.setEstadoRevision(revisionEnAprobacion);
        requerimiento.setEstado(estadoAprobacion);
        RequerimientoAprobacion aprobacionG2 = asignarAprobadorG2(requerimiento, contexto);
        Usuario aprobador = usuarioService.obtener(aprobacionG2.getUsuario().getIdUsuario());
        notificacionService.enviarMensajeSolicitudFirmaArchivamientoRequerimiento(aprobador, requerimiento, contexto);
        AuditoriaUtil.setAuditoriaRegistro(requerimiento, contexto);
        Requerimiento requerimientoDB = requerimientoDao.save(requerimiento);
        generarArchivoSiged(requerimiento, tipoArchivo, contexto);

        return requerimientoDB;
    }

    @Override
    public Long obtenerId(String requerimientoUuid) {
        if (requerimientoUuid == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_REQUERIMIENTO_NO_ENVIADO);
        }
        return requerimientoDao.obtenerId(requerimientoUuid);
    }

    @Override
    public Optional<Requerimiento> obtenerPorUuid(String requerimientoUuid) {
        return requerimientoDao.findByRequerimientoUuid(requerimientoUuid);
    }

    public Requerimiento actualizar(Requerimiento requerimiento, Contexto contexto) {
        AuditoriaUtil.setAuditoriaActualizacion(requerimiento, contexto);
        return requerimientoDao.save(requerimiento);
    }

    private RequerimientoAprobacion asignarAprobadorG2(Requerimiento requerimiento, Contexto contexto) {
        RequerimientoAprobacion requerimientoAprobacion = new RequerimientoAprobacion();
        PerfilAprobador perfilAprobador = perfilAprobadorDao
                .findFirstByPerfilIdListadoDetalle(requerimiento.getPerfil().getIdListadoDetalle())
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.PERFIL_APROBADOR_NO_ENCONTRADO));
        Usuario aprobadorG2 = perfilAprobador.getAprobadorG2();
        if (aprobadorG2 == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.PERFIL_APROBADOR_G2_NO_ENCONTRADO);
        }
        requerimientoAprobacion.setUsuario(aprobadorG2);
        // TODO: EL ESTADO APROBACION SE DEBE REUTILIZAR
        ListadoDetalle asignado = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_APROBACION.CODIGO,
                Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO
        );
        ListadoDetalle firmaPendiente = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_FIRMADO.CODIGO,
                Constantes.LISTADO.ESTADO_FIRMADO.PENDIENTE);
        ListadoDetalle grupo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.GRUPOS.CODIGO,
                Constantes.LISTADO.GRUPOS.G2);
        ListadoDetalle grupoAprobador = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.GRUPO_APROBACION.CODIGO,
                Constantes.LISTADO.GRUPO_APROBACION.GERENTE);
        ListadoDetalle tipo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_APROBACION.CODIGO,
                Constantes.LISTADO.TIPO_APROBACION.APROBAR);
        ListadoDetalle tipoAprobador = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_EVALUADOR.CODIGO,
                Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO);
        requerimientoAprobacion.setEstado(asignado);
        requerimientoAprobacion.setRequerimiento(requerimiento);
        requerimientoAprobacion.setFirmado(firmaPendiente);
        requerimientoAprobacion.setGrupo(grupo);
        requerimientoAprobacion.setGrupoAprobador(grupoAprobador);
        requerimientoAprobacion.setTipo(tipo);
        requerimientoAprobacion.setTipoAprobador(tipoAprobador);
        AuditoriaUtil.setAuditoriaRegistro(requerimientoAprobacion, contexto);
        return requerimientoAprobacionDao.save(requerimientoAprobacion);
    }

    @Transactional
    public Requerimiento aprobar(String uuid, RequerimientoAprobacionDTO aprobacion, Contexto contexto) {
        Requerimiento requerimientoBD = requerimientoDao.obtenerPorUuid(uuid)
                .orElseThrow(() -> new ValidacionException(REQUERIMIENTO_NO_ENCONTRADO));

        if(isAprobacionArchivamiento(requerimientoBD)) {
            return aprobarArchivamiento(requerimientoBD, contexto);
        }

        boolean esGppm = contexto.getUsuario().getRoles().stream().anyMatch(rol -> rol.getCodigo().equals(APROBADOR_GPPM));
        boolean esGse = contexto.getUsuario().getRoles().stream().anyMatch(rol -> rol.getCodigo().equals(APROBADOR_GSE));
        RequerimientoAprobacion reqAprobacion;
        //validar si es GPPM o GSE
        if(esGppm) {
            //Buscar Aprobacion GPPM
            ListadoDetalle grupoGppm = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.GRUPO_APROBACION.CODIGO, Constantes.LISTADO.GRUPO_APROBACION.GPPM);
            reqAprobacion = aprobacionDao.obtenerPorRequerimientoYGrupo(requerimientoBD.getIdRequerimiento(),
                            grupoGppm.getIdListadoDetalle(), Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO)
                    .orElseThrow(() -> new ValidacionException(APROBACION_NO_ENCONTRADA));
            //Aprobado o Rechazado
            if(aprobacion.getEstado().getCodigo()
                    .equalsIgnoreCase(Constantes.LISTADO.ESTADO_APROBACION.APROBADO)) {
                //Registrar Asignado GSE
                ListadoDetalle tipoAprobacion = listadoDetalleService.obtenerListadoDetalle(
                        Constantes.LISTADO.TIPO_APROBACION.CODIGO, Constantes.LISTADO.TIPO_APROBACION.APROBAR);
                ListadoDetalle grupoAprobador = listadoDetalleService.obtenerListadoDetalle(
                        Constantes.LISTADO.GRUPO_APROBACION.CODIGO, Constantes.LISTADO.GRUPO_APROBACION.GSE);
                ListadoDetalle estadoAprobacion = listadoDetalleService.obtenerListadoDetalle(
                        Constantes.LISTADO.ESTADO_APROBACION.CODIGO, Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO);
                ListadoDetalle tipoAprobador = listadoDetalleService.obtenerListadoDetalle(
                        Constantes.LISTADO.TIPO_APROBACION.CODIGO, Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO);
                RequerimientoAprobacion aprobacionGse = new RequerimientoAprobacion();
                aprobacionGse.setRequerimiento(requerimientoBD);
                aprobacionGse.setTipo(tipoAprobacion);
                aprobacionGse.setGrupoAprobador(grupoAprobador);
                aprobacionGse.setUsuario(contexto.getUsuario());
                aprobacionGse.setTipoAprobador(tipoAprobador);
                aprobacionGse.setEstado(estadoAprobacion);
                AuditoriaUtil.setAuditoriaRegistro(aprobacionGse, contexto);
                aprobacionDao.save(aprobacionGse);

                //Actualizar NuSiaf Requerimiento
                if(Objects.isNull(aprobacion.getNuSiaf()) || aprobacion.getNuSiaf().isEmpty()) {
                    throw new ValidacionException(SIAF_NO_ENVIADO);
                }
                requerimientoBD.setNuSiaf(aprobacion.getNuSiaf());

                //Actualizar Fecha de Aprobacion
                reqAprobacion.setFechaAprobacion(new Date());

                //Enviar por aprobar a GSE
                Rol rol = rolService.obtenerCodigo(Constantes.ROLES.APROBADOR_GSE);
                List<UsuarioRol> usuario = usuarioRolService.obtenerUsuarioRolPorRol(rol);
                if(usuario.isEmpty()) {
                    throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_ROL_GSE_NO_ENCONTRADO);
                }
                notificacionService.enviarMensajeRequerimientoPorAprobar(requerimientoBD, usuario.get(0).getUsuario(), contexto);
            } else if(aprobacion.getEstado().getCodigo()
                    .equalsIgnoreCase(Constantes.LISTADO.ESTADO_APROBACION.DESAPROBADO)) {
                //Actualizar Estado Requerimiento
                ListadoDetalle estadoReqDesaprobado = listadoDetalleService.obtenerListadoDetalle(
                        Constantes.LISTADO.ESTADO_REQUERIMIENTO.CODIGO, Constantes.LISTADO.ESTADO_REQUERIMIENTO.DESAPROBADO);
                requerimientoBD.setEstado(estadoReqDesaprobado);

                //Actualizar Fecha de Rechazo
                reqAprobacion.setFechaRechazo(new Date());

                //Enviar que GPPM rechazo
                Rol rol = rolService.obtenerCodigo(Constantes.ROLES.APROBADOR_GPPM);
                Usuario usuarioCoordinador = requerimientoBD.getDivision().getUsuario();
                notificacionService.enviarMensajeRechazoRequerimiento(requerimientoBD, usuarioCoordinador, rol.getNombre(), contexto);
            } else {
                throw new ValidacionException(ESTADO_APROBACION_NO_ENVIADO);
            }
        } else if(esGse) {
            //Buscar Aprobacion GSE
            ListadoDetalle grupoGse = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.GRUPO_APROBACION.CODIGO, Constantes.LISTADO.GRUPO_APROBACION.GSE);
            reqAprobacion = aprobacionDao.obtenerPorRequerimientoYGrupo(requerimientoBD.getIdRequerimiento(),
                            grupoGse.getIdListadoDetalle(), Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO)
                    .orElseThrow(() -> new ValidacionException(APROBACION_NO_ENCONTRADA));
            //Aprobado o Rechazado
            if(aprobacion.getEstado().getCodigo()
                    .equalsIgnoreCase(Constantes.LISTADO.ESTADO_APROBACION.APROBADO)) {
                //Actualizar Fecha de Aprobacion
                reqAprobacion.setFechaAprobacion(new Date());

                //Enviar para cargar docs
                notificacionService.enviarMensajeCargarDocumentosRequerimiento(requerimientoBD, contexto);
            } else if(aprobacion.getEstado().getCodigo()
                .equalsIgnoreCase(Constantes.LISTADO.ESTADO_APROBACION.DESAPROBADO)) {
                //Actualizar Fecha de Rechazo
                reqAprobacion.setFechaRechazo(new Date());

                //Actualizar Requerimiento
                ListadoDetalle estadoReqDesaprobado = listadoDetalleService.obtenerListadoDetalle(
                        Constantes.LISTADO.ESTADO_REQUERIMIENTO.CODIGO, Constantes.LISTADO.ESTADO_REQUERIMIENTO.DESAPROBADO);
                requerimientoBD.setEstado(estadoReqDesaprobado);

                //Enviar que GSE rechazo
                Rol rol = rolService.obtenerCodigo(Constantes.ROLES.APROBADOR_GSE);
                Usuario usuarioCoordinador = requerimientoBD.getDivision().getUsuario();
                notificacionService.enviarMensajeRechazoRequerimiento(requerimientoBD, usuarioCoordinador, rol.getNombre(), contexto);
            } else {
                throw new ValidacionException(ESTADO_APROBACION_NO_ENVIADO);
            }
        } else {
            throw new ValidacionException(ACCESO_NO_AUTORIZADO);
        }

        //Actualizar Aprobacion a Aprobado o Desaprobado
        ListadoDetalle estadoAprobacionRequest = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_APROBACION.CODIGO, aprobacion.getEstado().getCodigo());
        reqAprobacion.setEstado(estadoAprobacionRequest);
        AuditoriaUtil.setAuditoriaRegistro(reqAprobacion, contexto);
        aprobacionDao.save(reqAprobacion);

        //Actualizar Requerimiento
        requerimientoBD.setDeObservacion(aprobacion.getDeObservacion());
        AuditoriaUtil.setAuditoriaRegistro(requerimientoBD, contexto);
        return requerimientoDao.save(requerimientoBD);
    }

    @Override
    public Page<Requerimiento> listarPorAprobar(FiltroRequerimientoDTO filtroRequerimientoDTO, Pageable pageable, Contexto contexto) {
//        return this.listar(filtroRequerimientoDTO, pageable, contexto)
//                .map(req -> {
//                    req.setArchivos(new ArrayList<Archivo>());
//                    Archivo informe = archivoDao.obtenerTipoArchivoRequerimiento(req.getIdRequerimiento(),
//                            Constantes.LISTADO.TIPO_ARCHIVO.ARCHIVO_REQUERIMIENTO);
//                    req.getArchivos().add(informe);
//                    req.getReqAprobaciones()
//                            .forEach(aprob -> {
//                                if(aprob.getEstado().getCodigo().equalsIgnoreCase(Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO)) {
//                                    req.setTipoAprobacion(aprob.getTipo().getNombre());
//                                }
//                                if(aprob.getGrupo().getCodigo().equalsIgnoreCase(Constantes.LISTADO.GRUPO_APROBACION.JEFE_UNIDAD)) {
//                                    req.setEstadoFirmaJefeUnidad(aprob.getEstado().getNombre());
//                                } else if(aprob.getGrupo().getCodigo().equalsIgnoreCase(Constantes.LISTADO.GRUPO_APROBACION.GERENTE)) {
//                                    req.setEstadoFirmaGerente(aprob.getEstado().getNombre());
//                                }else if(aprob.getGrupo().getCodigo().equalsIgnoreCase(Constantes.LISTADO.GRUPO_APROBACION.GPPM)) {
//                                    req.setEstadoAprobacionGPPM(aprob.getEstado().getNombre());
//                                }else if(aprob.getGrupo().getCodigo().equalsIgnoreCase(Constantes.LISTADO.GRUPO_APROBACION.GSE)) {
//                                    req.setEstadoAprobacionGSE(aprob.getEstado().getNombre());
//                                }
//                            });
//                    return req;
//                });
        return null;
    }

    private void validarFechas(FiltroRequerimientoDTO filtro) {
        if (filtro.getFechaInicio() != null &&
                filtro.getFechaInicio().after(new Date())) {

            throw new ValidacionException(ERROR_FECHA_INICIO_ANTES_HOY);
        }
        if (filtro.getFechaInicio() != null &&
                filtro.getFechaFin() != null &&
                filtro.getFechaFin().before(filtro.getFechaInicio())) {

            throw new ValidacionException(ERROR_FECHA_FIN_ANTES_INICIO);
        }
    }

    private String generarArchivoSiged(Requerimiento requerimiento, ListadoDetalle tipoArchivo, Contexto contexto) {
        String nombreArchivo = ArchivoUtil.obtenerNombreArchivo(tipoArchivo);
        String nombreJasper = ArchivoUtil.obtenerNombreJasper(tipoArchivo);
        Archivo archivo = generarReporte(requerimiento, nombreArchivo, nombreJasper);
        archivo.setIdRequerimiento(requerimiento.getIdRequerimiento());
        archivo.setTipoArchivo(tipoArchivo);
        Archivo archivoDB = archivoService.guardarPorRequerimiento(archivo, contexto);

        return Objects.equals(tipoArchivo.getCodigo(), Constantes.LISTADO.TIPO_ARCHIVO.ARCHIVO_REQUERIMIENTO)
                ? registrarExpedienteSiged(archivoDB, requerimiento)
                : adjuntarDocumentoSiged(archivoDB, requerimiento);
    }

    private ExpedienteInRO crearExpediente(Requerimiento requerimiento, Integer codigoTipoDocumento) {
        ExpedienteInRO expediente = new ExpedienteInRO();
        DocumentoInRO documento = new DocumentoInRO();
        ClienteListInRO clientes = new ClienteListInRO();
        ClienteInRO cs = new ClienteInRO();
        List<ClienteInRO> cliente = new ArrayList<>();
        DireccionxClienteListInRO direcciones = new DireccionxClienteListInRO();
        DireccionxClienteInRO d = new DireccionxClienteInRO();
        List<DireccionxClienteInRO> direccion = new ArrayList<>();
        expediente.setProceso(Integer.parseInt(env.getProperty("crear.expediente.parametros.proceso")));
        expediente.setDocumento(documento);
        if (requerimiento.getNuExpediente() != null) {
            expediente.setNroExpediente(requerimiento.getNuExpediente());
        }
        documento.setAsunto(SOLICITUD_REQUERIMIENTO_CONTRATO_PN);
        documento.setAppNameInvokes(SIGLA_PROYECTO);
        documento.setCodTipoDocumento(codigoTipoDocumento);
        documento.setNroFolios(Integer.parseInt(env.getProperty("crear.expediente.parametros.crea.folio")));
        documento.setUsuarioCreador(Integer.parseInt(env.getProperty("siged.bus.server.id.usuario")));
        if (Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.informe.respuesta.solicitud.pn")) == codigoTipoDocumento) {
            documento.setFirmante(Integer.parseInt(env.getProperty("siged.firmante.informe.respuesta.id.usuario")));
        }
        cs.setCodigoTipoIdentificacion(Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.cliente")));
        cs.setNombre("OSINERGMIN");
        cs.setApellidoPaterno("-");
        cs.setApellidoMaterno("-");
        cs.setRazonSocial("OSINERGMIN");
        cs.setNroIdentificacion(OSI_DOCUMENTO);
        cs.setTipoCliente(Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.cliente")));
        cliente.add(cs);
        d.setDireccion("-");
        d.setDireccionPrincipal(true);
        d.setEstado(env.getProperty("crear.expediente.parametros.direccion.estado").charAt(0));
        d.setTelefono("-");
        d.setUbigeo(Integer.parseInt(env.getProperty("siged.ws.cliente.osinergmin.ubigeo")));
        direccion.add(d);
        direcciones.setDireccion(direccion);
        cs.setDirecciones(direcciones);
        clientes.setCliente(cliente);
        documento.setClientes(clientes);
        documento.setEnumerado(env.getProperty("crear.expediente.parametros.enumerado").charAt(0));
        documento.setEstaEnFlujo(env.getProperty("crear.expediente.parametros.esta.en.flujo").charAt(0));
        documento.setFirmado(env.getProperty("crear.expediente.parametros.firmado").charAt(0));
        documento.setCreaExpediente(env.getProperty("crear.expediente.parametros.crea.expediente").charAt(0));
        documento.setPublico(env.getProperty("crear.expediente.parametros.crea.publico").charAt(0));
        return expediente;
    }

    private Archivo generarReporte(Requerimiento requerimiento, String nombreArchivo, String nombreJasper) {
        requerimiento.setUsuarioCreador(usuarioService.obtener(Long.valueOf(requerimiento.getUsuCreacion())));
        Archivo archivo = new Archivo();
        archivo.setNombre(nombreArchivo);
        archivo.setNombreReal(nombreArchivo);
        archivo.setTipo("application/pdf");
        ByteArrayOutputStream output;
        JasperPrint print;
        InputStream appLogo = null;
        InputStream osinermingLogo = null;
        try {
            File jrxml = new File(pathJasper + nombreJasper);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("SUBREPORT_DIR", pathJasper);
            appLogo = Files.newInputStream(Paths.get(pathJasper + "logo-sicoes.png"));
            osinermingLogo = Files.newInputStream(Paths.get(pathJasper + "logo-osinerming.png"));
            parameters.put("P_LOGO_APP", appLogo);
            parameters.put("P_LOGO_OSINERGMIN", osinermingLogo);
            List<Requerimiento> requerimientos = new ArrayList<>();
            requerimientos.add(requerimiento);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(requerimientos);
            JasperReport jasperReport = archivoUtil.getJasperCompilado(jrxml);
            print = JasperFillManager.fillReport(jasperReport, parameters, ds);
            output = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, output);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.REQUERIMIENTO_GUARDAR_FORMATO_04, e);
        } finally {
            archivoUtil.close(appLogo);
            archivoUtil.close(osinermingLogo);
        }
        byte[] bytesSalida = output.toByteArray();
        archivo.setPeso((long) bytesSalida.length);
        archivo.setNroFolio(1L);
        archivo.setContenido(bytesSalida);
        return archivo;
    }

    private File fileRequerimiento(Archivo archivo, Long idRequerimiento) {
        try {
            String dirPath = pathTemporal + File.separator + "temporales" + File.separator + idRequerimiento;
            File dir = new File(dirPath);
            if (!dir.exists()) {
                boolean creado = dir.mkdirs();
                if (!creado) {
                    logger.warn("No se pudo crear el directorio temporal: {}", dirPath);
                }
            }
            File file = new File(dirPath + File.separator + archivo.getNombre());
            FileUtils.writeByteArrayToFile(file, archivo.getContenido());
            archivo.setContenido(Files.readAllBytes(file.toPath()));
            return file;
        } catch (Exception e) {
            logger.error("Error al escribir archivo temporal", e);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO);
        }
    }

    private String registrarExpedienteSiged(Archivo archivo, Requerimiento requerimiento) {
        List<File> archivosAlfresco = new ArrayList<>();
        ExpedienteInRO expedienteInRO = crearExpediente(
                requerimiento,
                Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.crear"))
        );
        File file = fileRequerimiento(archivo, requerimiento.getIdRequerimiento());
        archivosAlfresco.add(file);
        ExpedienteOutRO expedienteOutRO = null;
        try {
            expedienteOutRO = sigedApiConsumer.crearExpediente(expedienteInRO, archivosAlfresco);
            logger.info("SIGED RESULT: {}", expedienteOutRO.getMessage());
            if (expedienteOutRO.getResultCode() != 1) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO, expedienteOutRO.getMessage());
            } else {
                return expedienteOutRO.getCodigoExpediente();
            }
        } catch (Exception e) {
            logger.error("Error al agregar documento en SIGED", e);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO, expedienteOutRO.getMessage());
        }
    }

    private String adjuntarDocumentoSiged(Archivo archivo, Requerimiento requerimiento) {
        List<File> archivosAlfresco = new ArrayList<>();
        ExpedienteInRO expedienteInRO = crearExpediente(
                requerimiento,
                Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.crear"))
        );
        File file = fileRequerimiento(archivo, requerimiento.getIdRequerimiento());
        archivosAlfresco.add(file);
        try {
            DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);
            logger.info("SIGED RESULT: {}", documentoOutRO.getMessage());
            if (documentoOutRO.getResultCode() != 1) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO, documentoOutRO.getMessage());
            }
        } catch (Exception e) {
            logger.error("Error al agregar documento en SIGED", e);
        }
        return null;
    }

    private boolean isAprobacionArchivamiento(Requerimiento requerimiento) {
        ListadoDetalle enAprobacion = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQUERIMIENTO.CODIGO,
                Constantes.LISTADO.ESTADO_REQUERIMIENTO.EN_APROBACION);

        return StringUtils.isEmpty(requerimiento.getDeObservacion().trim())
                && Objects.equals(requerimiento.getEstado().getIdListadoDetalle(), enAprobacion.getIdListadoDetalle());
    }

    private Requerimiento aprobarArchivamiento(Requerimiento requerimiento, Contexto contexto) {
        boolean isAprobTecnico = contexto.getUsuario().getRoles()
                .stream()
                .anyMatch(rol -> rol.getCodigo().equals(APROBADOR_TECNICO));

        if (!isAprobTecnico)
            throw new ValidacionException(ACCESO_NO_AUTORIZADO);

        // Obtenemos grupo de aprobacion (G2)
        ListadoDetalle g2 = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.GRUPOS.CODIGO,
                Constantes.LISTADO.GRUPOS.G2);

        // Obtenemos la aprobacion de archivamiento
        RequerimientoAprobacion aprobArchivamiento = requerimientoAprobacionDao.obtenerPorRequerimientoYGrupo(
                        requerimiento.getIdRequerimiento(), g2.getIdListadoDetalle(), Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO)
                .orElseThrow(() -> new ValidacionException(APROBACION_NO_ENCONTRADA));

        // Obtenemos estado Archivado
        ListadoDetalle estadoArchivado = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQUERIMIENTO.CODIGO,
                Constantes.LISTADO.ESTADO_REQUERIMIENTO.ARCHIVADO);

        // Obtenemos revisison Aprobado
        ListadoDetalle revisionAprobado = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_APROBACION.CODIGO,
                Constantes.LISTADO.ESTADO_APROBACION.APROBADO);

        requerimiento.setEstadoRevision(revisionAprobado);
        requerimiento.setEstado(estadoArchivado);

        // Obtenemos estado Aprobado
        ListadoDetalle estadoAprobado = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.RESULTADO_APROBACION.CODIGO,
                Constantes.LISTADO.RESULTADO_APROBACION.APROBADO);
        aprobArchivamiento.setEstado(estadoAprobado);

        AuditoriaUtil.setAuditoriaRegistro(aprobArchivamiento, contexto);
        requerimientoAprobacionDao.save(aprobArchivamiento);

        AuditoriaUtil.setAuditoriaRegistro(requerimiento, contexto);
        return requerimientoDao.save(requerimiento);
    }

}
