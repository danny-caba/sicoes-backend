package pe.gob.osinergmin.sicoes.service.impl;

import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ARCHIVO_SUBIR_ARCHIVO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_FIN_ANTES_INICIO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_INICIO_ANTES_HOY;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.FECHA_SUSCRIPCION_NO_ENCONTRADO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.NUMERO_CONTRATO_NO_ENCONTRADO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.REQUERIMIENTO_CONTRATO_NO_ENCONTRADO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.REQUERIMIENTO_EN_PROCESO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.REQUERIMIENTO_NO_ENCONTRADO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Requerimiento;
import pe.gob.osinergmin.sicoes.model.RequerimientoContrato;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.dto.ArchivoDTO;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoContratoDTO;
import pe.gob.osinergmin.sicoes.model.dto.RequerimientoContratoDTO;
import pe.gob.osinergmin.sicoes.repository.ArchivoDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoContratoDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.RequerimientoContratoService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RequerimientoContratoImpl implements RequerimientoContratoService {

    private static final Logger logger = LogManager.getLogger(RequerimientoContratoImpl.class);

    @Autowired
    private ListadoDetalleService listadoDetalleService;
    @Autowired
    private RequerimientoContratoDao requerimientoContratoDao;
    @Autowired
    private RequerimientoDao requerimientoDao;
    @Autowired
    private ArchivoDao archivoDao;
    @Autowired
    private NotificacionService notificacionService;

    @Override
    @Transactional
    public RequerimientoContrato guardar(RequerimientoContrato contrato, Contexto contexto) {
        if(Objects.isNull(contrato.getNumeroContrato()) || contrato.getNumeroContrato().length() != 10) {
            throw new ValidacionException(NUMERO_CONTRATO_NO_ENCONTRADO);
        }
        Requerimiento requerimiento = requerimientoDao.findById(contrato.getRequerimiento().getIdRequerimiento())
                .orElseThrow(() -> new ValidacionException(REQUERIMIENTO_NO_ENCONTRADO));

        ListadoDetalle estado = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQ_CONTRATO.CODIGO,
                Constantes.LISTADO.ESTADO_REQ_CONTRATO.EN_PROCESO
        );
        ListadoDetalle tipo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_REQ_CONTRATO.CODIGO,
                Constantes.LISTADO.TIPO_REQ_CONTRATO.PRIMIGENIO
        );
        contrato.setRequerimiento(requerimiento);
        contrato.setRequerimientoContratoUuid(UUID.randomUUID().toString());
        contrato.setEstado(estado);
        contrato.setTipo(tipo);
        AuditoriaUtil.setAuditoriaRegistro(contrato, contexto);
        return requerimientoContratoDao.save(contrato);
    }

    @Override
    public Page<RequerimientoContrato> listar(FiltroRequerimientoContratoDTO filtros, Pageable pageable, Contexto contextos) {
        return requerimientoContratoDao.listarRequerimientoContrato(filtros.getDivision(),
                filtros.getPerfil(), filtros.getSupervisora(), filtros.getNumeroContrato(), pageable);
    }

    @Override
    @Transactional
    public RequerimientoContrato editar(String uuid, RequerimientoContratoDTO contrato, Contexto contexto) {
        RequerimientoContrato contratoBD = requerimientoContratoDao.obtenerPorUuid(uuid)
                .orElseThrow(() -> new ValidacionException(REQUERIMIENTO_CONTRATO_NO_ENCONTRADO));
        Requerimiento requerimiento = contratoBD.getRequerimiento();
//        if(!requerimiento.getEstado().getCodigo().equalsIgnoreCase(Constantes.LISTADO.ESTADO_REQUERIMIENTO.EN_PROCESO)) {
//            throw new ValidacionException(REQUERIMIENTO_EN_PROCESO);
//        }
        if(!contratoBD.getEstado().getCodigo().equalsIgnoreCase(Constantes.LISTADO.ESTADO_REQ_CONTRATO.EN_PROCESO)) {
            throw new ValidacionException(REQUERIMIENTO_EN_PROCESO);
        }
        if(Objects.isNull(contrato.getArchivos()) || contrato.getArchivos().isEmpty()) {
            throw new ValidacionException(ARCHIVO_SUBIR_ARCHIVO);
        }
        //Asociar Archivos
        ListadoDetalle estadoArchivo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_ARCHIVO.CODIGO, Constantes.LISTADO.ESTADO_ARCHIVO.ASOCIADO);
        for(ArchivoDTO archivo: contrato.getArchivos()) {
            Archivo archivoBD = archivoDao.obtener(archivo.getIdArchivo());
            archivoBD.setEstado(estadoArchivo);
            archivoBD.setIdRequerimientoContrato(contratoBD.getIdRequerimientoContrato());
            AuditoriaUtil.setAuditoriaRegistro(archivoBD, contexto);
            archivoDao.save(archivoBD);
        }

        //Actualizar estado Requerimiento
        ListadoDetalle estadoRequerimiento = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQUERIMIENTO.CODIGO,
                Constantes.LISTADO.ESTADO_REQUERIMIENTO.CONCLUIDO
        );
        requerimiento.setEstado(estadoRequerimiento);
        AuditoriaUtil.setAuditoriaRegistro(requerimiento, contexto);
        requerimiento = requerimientoDao.save(requerimiento);
        contratoBD.setRequerimiento(requerimiento);

        //Actualizar Fechas y estado Req. Contrato
        Date fechaSuscripcion = contrato.getFechaSuscripcion();//obligatorio nomas
        Date fechaInicio = contrato.getFechaInicio();
        Date fechaFin = contrato.getFechaFin();
        if (fechaSuscripcion == null) {
            throw new ValidacionException(FECHA_SUSCRIPCION_NO_ENCONTRADO);
        }
        if (fechaInicio != null && fechaInicio.after(new Date())) {
            throw new ValidacionException(ERROR_FECHA_INICIO_ANTES_HOY);
        }
        if (fechaInicio != null && fechaFin != null && fechaFin.before(fechaInicio)) {
            throw new ValidacionException(ERROR_FECHA_FIN_ANTES_INICIO);
        }
        contratoBD.setFechaSuscripcion(contrato.getFechaSuscripcion());
        contratoBD.setFechaInicio(contrato.getFechaInicio());
        contratoBD.setFechaFin(contrato.getFechaFin());
        ListadoDetalle estado = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQ_CONTRATO.CODIGO,
                Constantes.LISTADO.ESTADO_REQ_CONTRATO.CONCLUIDO
        );
        contratoBD.setEstado(estado);
        AuditoriaUtil.setAuditoriaRegistro(contratoBD, contexto);
        contratoBD = requerimientoContratoDao.save(contratoBD);

        //Enviar Notificación Fin Contratacion
        notificacionService.enviarMensajeFinalizacionContratacion(requerimiento.getSupervisora(), contexto);
        return contratoBD;
    }

    @Override
    public RequerimientoContrato obtener(Long aLong, Contexto contexto) {
        return null;
    }

    @Override
    public void eliminar(Long aLong, Contexto contexto) {

    }

}
