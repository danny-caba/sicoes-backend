package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.controller.RequerimientoAprobacionRestController;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.Rol;
import pe.gob.osinergmin.sicoes.model.dto.DivisionDTO;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDTO;
import pe.gob.osinergmin.sicoes.model.dto.RequerimientoAprobacionResponseDTO;
import pe.gob.osinergmin.sicoes.model.dto.SupervisoraDTO;
import pe.gob.osinergmin.sicoes.repository.ArchivoDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.RequerimientoAprobacionService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RequerimientoAprobacionServiceImpl implements RequerimientoAprobacionService {

    private static final Logger logger = LogManager.getLogger(RequerimientoAprobacionRestController.class);

    @Autowired
    private ListadoDetalleService listadoDetalleService;
    @Autowired
    private RequerimientoAprobacionDao requerimientoAprobacionDao;
    @Autowired
    private ArchivoDao archivoDao;

    @Override
    public Page<RequerimientoAprobacion> obtenerHistorial(String uuid, Contexto contexto, Pageable pageable) {
        return requerimientoAprobacionDao.obtenerAprobaciones(uuid, pageable);
    }

    @Override
    public RequerimientoAprobacion guardar(RequerimientoAprobacion model, Contexto contexto) {
        return null;
    }

    @Override
    public RequerimientoAprobacion obtener(Long aLong, Contexto contexto) {
        return null;
    }

    @Override
    public void eliminar(Long aLong, Contexto contexto) {

    }

    @Override
    public Page<RequerimientoAprobacionResponseDTO> buscar(FiltroRequerimientoDTO filtro, Pageable pageable, Contexto contexto) {

        // Validar rol aprobador
        validarRolAprobador(contexto);

        // Obtener registros de aprobaciones
        Page<RequerimientoAprobacion> aprobacionesPage = requerimientoAprobacionDao.buscar(
                filtro.getExpediente(),
                filtro.getDivision(),
                filtro.getPerfil(),
                filtro.getSupervisora(),
                filtro.getEstadoAprobacion(),
                contexto.getUsuario().getIdUsuario(),
                pageable
        );

        // Mapear Aprobaciones
        Page<RequerimientoAprobacion> aprobacionesPageMapeado =  mapearAprobacionesPorRequerimiento(aprobacionesPage);

        // Mapear DTO
        return aprobacionesPageMapeado.map(this::convertirResponseDTO);
    }

    private RequerimientoAprobacionResponseDTO convertirResponseDTO(RequerimientoAprobacion requerimientoAprobacion) {
        RequerimientoAprobacionResponseDTO responseDTO = new RequerimientoAprobacionResponseDTO();

        logger.info("RequerimientoAprobacion obtenido: {}", requerimientoAprobacion);

        // Mapear RequerimientoAprobacion
        responseDTO.setIdRequerimientoAprobacion(requerimientoAprobacion.getIdRequerimientoAprobacion());
        responseDTO.setTipo(requerimientoAprobacion.getTipo());
        responseDTO.setFechaAsignacion(requerimientoAprobacion.getFechaAsignacion());
        responseDTO.setArchivoInforme(requerimientoAprobacion.getArchivoInforme());
        responseDTO.setEstadoFirmaJefeUnidad(requerimientoAprobacion.getEstadoFirmaJefeUnidad());
        responseDTO.setEstadoFirmaGerente(requerimientoAprobacion.getEstadoFirmaGerente());
        responseDTO.setEstadoAprobacionGPPM(requerimientoAprobacion.getEstadoAprobacionGPPM());
        responseDTO.setEstadoAprobacionGSE(requerimientoAprobacion.getEstadoAprobacionGSE());
        responseDTO.setAccionAprobar(requerimientoAprobacion.getAccionAprobar());
        responseDTO.setResponsableSIAF(requerimientoAprobacion.isResponsableSIAF());

        // Mapear Requerimiento
        RequerimientoAprobacionResponseDTO.RequerimientoDTO requerimientoDTO = new RequerimientoAprobacionResponseDTO.RequerimientoDTO();
        requerimientoDTO.setNuExpediente(requerimientoAprobacion.getRequerimiento().getNuExpediente());
        requerimientoDTO.setEstado(requerimientoAprobacion.getRequerimiento().getEstado());
        requerimientoDTO.setRequerimientoUuid(requerimientoAprobacion.getRequerimiento().getRequerimientoUuid());
        requerimientoDTO.setEstadoAprobacion(requerimientoAprobacion.getRequerimiento().getEstadoRevision());

        // Mapear Supervisora
        RequerimientoAprobacionResponseDTO.SupervisoraDTO supervisoraDTO = new RequerimientoAprobacionResponseDTO.SupervisoraDTO();
        if (requerimientoAprobacion.getRequerimiento().getSupervisora() != null) {
            supervisoraDTO.setTipoDocumento(requerimientoAprobacion.getRequerimiento().getSupervisora().getTipoDocumento().getCodigo());
            supervisoraDTO.setNombreRazonSocial(requerimientoAprobacion.getRequerimiento().getSupervisora().getNombreRazonSocial());
            supervisoraDTO.setNombres(requerimientoAprobacion.getRequerimiento().getSupervisora().getNombres());
            supervisoraDTO.setApellidoMaterno(requerimientoAprobacion.getRequerimiento().getSupervisora().getApellidoMaterno());
            supervisoraDTO.setApellidoPaterno(requerimientoAprobacion.getRequerimiento().getSupervisora().getApellidoPaterno());
        }
        requerimientoDTO.setSupervisora(supervisoraDTO);

        // Mapear DivisionDTO
        DivisionDTO divisionDTO = new DivisionDTO();
        divisionDTO.setIdDivision(requerimientoAprobacion.getRequerimiento().getDivision().getIdDivision());
        divisionDTO.setDeDivision(requerimientoAprobacion.getRequerimiento().getDivision().getDeDivision());
        requerimientoDTO.setDivision(divisionDTO);
        responseDTO.setRequerimiento(requerimientoDTO);

        return responseDTO;
    }

    private Page<RequerimientoAprobacion> mapearAprobacionesPorRequerimiento(
            Page<RequerimientoAprobacion> aprobacionesPage) {

        if (aprobacionesPage.getContent().isEmpty()) {
            return aprobacionesPage;
        }

        // Seteamos la accion aprobar y resp siaf si corresponde
        Page<RequerimientoAprobacion> aprobacionesConAccionPage = aprobacionesPage
                .map(this::setearAccionAprobar);

        // Obtenemos ids de los requerimientos
        Set<Long> idsRequerimientos = aprobacionesPage.getContent().stream()
                .map(aprob -> aprob.getRequerimiento().getIdRequerimiento())
                .collect(Collectors.toSet());

        // Cargar los archivos de tipo informe
        Map<Long, Archivo> archivosInforme = cargarArchivosInforme(idsRequerimientos);

        // Cargar estados de aprobaciones
        Map<Long, Map<String, String>> estadosAprobaciones = cargarEstadosAprobaciones(idsRequerimientos);

        // Setear datos por requerimiento
        return aprobacionesConAccionPage.map(reqAprob -> setearDatosPorRequerimiento(
                reqAprob, archivosInforme, estadosAprobaciones));

    }

    private RequerimientoAprobacion setearAccionAprobar(
            RequerimientoAprobacion requerimientoAprobacion) {

        boolean esAsignado = requerimientoAprobacion.getEstado().getCodigo().equals(
                Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO);

        boolean esEncargadoSIAF = requerimientoAprobacion.getGrupoAprobador().getCodigo().equals(
                Constantes.LISTADO.GRUPO_APROBACION.GPPM);

        requerimientoAprobacion.setAccionAprobar(esAsignado);
        requerimientoAprobacion.setResponsableSIAF(esEncargadoSIAF);


        return requerimientoAprobacion;
    }

    private RequerimientoAprobacion setearDatosPorRequerimiento(
            RequerimientoAprobacion requerimientoAprobacion,
            Map<Long, Archivo> archivosInforme,
            Map<Long, Map<String, String>> estadosAprobaciones) {

        Long idRequerimiento = requerimientoAprobacion.getRequerimiento().getIdRequerimiento();

        // Asignar archivo de informe si no existe
        if (requerimientoAprobacion.getArchivoInforme() == null) {
            requerimientoAprobacion.setArchivoInforme(archivosInforme.get(idRequerimiento));
        }

        // Asignar estado de de firma y aprobacion
        Map<String, String> estadoAprobacion = estadosAprobaciones.getOrDefault(idRequerimiento, new HashMap<>());
        requerimientoAprobacion.setEstadoFirmaJefeUnidad(estadoAprobacion.get(Constantes.LISTADO.GRUPO_APROBACION.JEFE_UNIDAD));
        requerimientoAprobacion.setEstadoFirmaGerente(estadoAprobacion.get(Constantes.LISTADO.GRUPO_APROBACION.GERENTE));
        requerimientoAprobacion.setEstadoAprobacionGPPM(estadoAprobacion.get(Constantes.LISTADO.GRUPO_APROBACION.GPPM));
        requerimientoAprobacion.setEstadoAprobacionGSE(estadoAprobacion.get(Constantes.LISTADO.GRUPO_APROBACION.GSE));

        return requerimientoAprobacion;
    }


    private void validarRolAprobador(Contexto contexto) {

        boolean isAprobador = contexto.getUsuario().getRoles()
                .stream()
                .anyMatch(rol ->
                        Objects.equals(rol.getCodigo(), Constantes.ROLES.APROBADOR_TECNICO) ||
                        Objects.equals(rol.getCodigo(), Constantes.ROLES.APROBADOR_ADMINISTRATIVO));

        if (!isAprobador)
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ACCESO_NO_AUTORIZADO);
    }

    private Map<Long, Archivo> cargarArchivosInforme(Set<Long> idsRequerimientos) {
        List<Archivo> archivos = archivoDao.obtenerTipoArchivoRequerimiento(
                idsRequerimientos, Constantes.LISTADO.TIPO_ARCHIVO.INFORME_REQUERIMIENTO);

        return archivos.stream().collect(Collectors.toMap(
                Archivo::getIdRequerimiento,
                archivo -> archivo
        ));
    }

    private Map<Long, Map<String, String>> cargarEstadosAprobaciones(Set<Long> idsRequerimientos) {
        Map<Long, Map<String, String>> estadosPorRequerimiento = new HashMap<>();

        List<RequerimientoAprobacion> listAprobaciones = requerimientoAprobacionDao.obtenerAprobacionesPorRequerimiento(idsRequerimientos);

        // Agrupar por requerimiento y mapear estados por grupo
        for (RequerimientoAprobacion aprobacion : listAprobaciones) {
            Long idRequerimiento = aprobacion.getRequerimiento().getIdRequerimiento();
            String codigoGrupoAprobador = aprobacion.getGrupoAprobador().getCodigo();
            String nombreEstado = aprobacion.getEstado().getNombre();

            estadosPorRequerimiento
                    .computeIfAbsent(idRequerimiento, k -> new HashMap<>())
                    .put(codigoGrupoAprobador, nombreEstado);
        }

        return estadosPorRequerimiento;
    }

}
