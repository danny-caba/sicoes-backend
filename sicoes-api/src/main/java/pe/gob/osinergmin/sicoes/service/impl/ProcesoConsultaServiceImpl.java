package pe.gob.osinergmin.sicoes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.repository.ProcesoConsultaDao;
import pe.gob.osinergmin.sicoes.repository.ProcesoDao;
import pe.gob.osinergmin.sicoes.repository.ProcesoEtapaDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.ProcesoConsultaService;
import pe.gob.osinergmin.sicoes.service.SupervisoraService;
import pe.gob.osinergmin.sicoes.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.*;

@Service
public class ProcesoConsultaServiceImpl implements ProcesoConsultaService {

    Logger logger = LogManager.getLogger(ProcesoConsultaServiceImpl.class);

    @Autowired
    private ListadoDetalleService listadoDetalleService;

    @Autowired
    private SupervisoraService supervisoraService;

    @Autowired
    private ProcesoEtapaDao procesoEtapaDao;

    @Autowired
    private ProcesoConsultaDao procesoConsultaDao;

    @Autowired
    private ProcesoDao procesoDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProcesoConsulta guardar(ProcesoConsulta consulta, Contexto contexto) {
        ProcesoConsulta consultaDB;

        Proceso proceso = procesoDao.obtener(consulta.getProceso().getIdProceso());

        ListadoDetalle etapaFormulacion = listadoDetalleService.obtenerListadoDetalleOrden(Constantes.LISTADO.ETAPA_PROCESO.CODIGO,
                Constantes.LISTADO.ETAPA_PROCESO.ETAPA_FORMULACION_ORDEN);

        List<ProcesoEtapa> lstEtapas = procesoEtapaDao.buscarExiste(proceso.getProcesoUuid(), etapaFormulacion.getIdListadoDetalle());

        if (lstEtapas.isEmpty()) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.NO_EXISTE_ETAPA);
        }

        boolean estaEnFecha =  validarFechaConsulta(lstEtapas.get(0).getFechaInicio(), lstEtapas.get(0).getFechaFin());

        if (!estaEnFecha) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.FUERA_FECHA_CONSULTA);
        }

        validarCampo(consulta.getSeccion(), Constantes.CODIGO_MENSAJE.SECCION_NO_ENVIADA);
        validarCampo(consulta.getDeNumeral(), Constantes.CODIGO_MENSAJE.NUMERAL_NO_ENVIADA);
        validarCampo(consulta.getDeLiteral(), Constantes.CODIGO_MENSAJE.LITERAL_NO_ENVIADA);
        validarCampo(consulta.getDePagina(), Constantes.CODIGO_MENSAJE.PAGINA_NO_ENVIADA);
        validarCampo(consulta.getDeConsulta(), Constantes.CODIGO_MENSAJE.CONSULTA_NO_ENVIADA);

        if (consulta.getProcesoConsultaUuid() == null) {
            consultaDB = consulta;
            ListadoDetalle pendiente = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO, Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
            Supervisora supervisora = supervisoraService.obtenerSupervisoraXRUCNoProfesional(contexto.getUsuario().getCodigoRuc());
            consultaDB.setEstado(pendiente);
            consultaDB.setSupervisora(supervisora);
            consultaDB.setProcesoConsultaUuid(UUID.randomUUID().toString());

            AuditoriaUtil.setAuditoriaRegistro(consultaDB, contexto);
            consultaDB = procesoConsultaDao.save(consultaDB);
        } else {
            consultaDB = procesoConsultaDao.obtener(consulta.getProcesoConsultaUuid());

            if (!consulta.getEstado().getCodigo().equals(consultaDB.getEstado().getCodigo())) {
                consultaDB.setEstado(consulta.getEstado());
            } else {
                consultaDB.setSeccion(consulta.getSeccion());
                consultaDB.setDeNumeral(consulta.getDeNumeral());
                consultaDB.setDeLiteral(consulta.getDeLiteral());
                consultaDB.setDePagina(consulta.getDePagina());
                consultaDB.setDeConsulta(consulta.getDeConsulta());
                consultaDB.setDeArticuloNorma(consulta.getDeArticuloNorma());
            }

            AuditoriaUtil.setAuditoriaRegistro(consultaDB, contexto);
            consultaDB = procesoConsultaDao.save(consultaDB);
        }

        return consultaDB;
    }

    @Override
    public ProcesoConsulta obtener(Long aLong, Contexto contexto) {
        return null;
    }

    @Override
    public void eliminar(Long idConsulta, Contexto contexto) {
        procesoConsultaDao.deleteById(idConsulta);
    }

    private void validarCampo(Object campo, String error) {
        if (campo == null) throw new ValidacionException(error);
    }

    @Override
    public Page<ProcesoConsulta> consultasPorUsuario(Long idProceso, Pageable pageable, Contexto contexto) {
        logger.info("consultasPorUsuario");
        String idUsuario = contexto.getUsuario().getIdUsuario().toString();
        return procesoConsultaDao.buscarConsultasPorUsuario(idProceso, idUsuario, pageable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void eliminar(String uuid, Contexto contexto) {
        procesoConsultaDao.eliminarConsulta(uuid);
    }

    @Override
    public InputStream generarExport(Long idProceso) throws Exception {
        String estado = Constantes.LISTADO.ESTADO_NOTIFICACIONES.ENVIADO;
        List<Object[]> result = procesoConsultaDao.listarConsultasEnviado(idProceso, estado);
        Proceso proceso = procesoDao.obtener(idProceso);

        List<Object[]> resultWithRowNum = new ArrayList<>();
        int rownum = 1;

        for (Object[] row : result) {
            Object[] newRow = new Object[row.length + 1];
            newRow[0] = rownum++;
            System.arraycopy(row, 0, newRow, 1, row.length);
            resultWithRowNum.add(newRow);
        }

        List<DatosExportacion> listDatos = new ArrayList<>();
        int year = Year.now().getValue();

        DatosExportacion datosExportacion = new DatosExportacion();
        datosExportacion.setTitulo("Concurso de Empresas Supervisoras Nro. " + proceso.getNumeroProceso() + "-" + year + "-" + "Osinergmin-" + proceso.getNombreArea());
        datosExportacion.setSubtitulo(proceso.getNombreProceso());
        datosExportacion.setNombreHoja("Consultas Formuladas");
        datosExportacion.setFecha(new Date());
        datosExportacion.setDescuentoRegistros(0L);
        datosExportacion.setFiltros(
                new String[][]{
                });
        datosExportacion.setNombreTitulos(
                new String[] {
                        "Nro. Orden",
                        "RUC",
                        "Nombre o Razón Social",
                        "Sección",
                        "Numeral",
                        "Literal",
                        "Página",
                        "Consulta",
                        "Artículo o norma que se vulnera(en el caso de observaciones)",
                        "Fecha y Hora de Envío"});
        datosExportacion.setListado(resultWithRowNum);
        listDatos.add(datosExportacion);

        return ExcelUtils.generarArchivoConsulta(listDatos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean actualizarEstadoEnvio(Long idProceso, Contexto contexto) {

        Proceso proceso = procesoDao.obtener(idProceso);

        ListadoDetalle etapaFormulacion = listadoDetalleService.obtenerListadoDetalleOrden(Constantes.LISTADO.ETAPA_PROCESO.CODIGO,
                Constantes.LISTADO.ETAPA_PROCESO.ETAPA_FORMULACION_ORDEN);
        List<ProcesoEtapa> lstEtapas = procesoEtapaDao.buscarExiste(proceso.getProcesoUuid(), etapaFormulacion.getIdListadoDetalle());

        if (lstEtapas.isEmpty()) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.NO_EXISTE_ETAPA);
        }

        boolean estaEnFecha =  validarFechaConsulta(lstEtapas.get(0).getFechaInicio(), lstEtapas.get(0).getFechaFin());

        if (!estaEnFecha) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.FUERA_FECHA_CONSULTA);
        }

        ListadoDetalle estado = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO, Constantes.LISTADO.ESTADO_NOTIFICACIONES.ENVIADO);
            List<ProcesoConsulta> lstProcesoConsulta = procesoConsultaDao.listarConsultasNoEnviado(idProceso);

        for (ProcesoConsulta consulta : lstProcesoConsulta) {
            consulta.setEstado(estado);
            AuditoriaUtil.setAuditoriaActualizacion(consulta, contexto);
            procesoConsultaDao.save(consulta);
        }
        return true;
    }

    private boolean validarFechaConsulta(Date fechaInicio, Date fechaFin) {
        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date.compareTo(fechaInicio) >= 0 && date.compareTo(fechaFin) <= 0;
    }

}
