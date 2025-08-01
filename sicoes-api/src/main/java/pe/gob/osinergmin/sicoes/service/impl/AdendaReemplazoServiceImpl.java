package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.osinergmin.sicoes.model.AdendaReemplazo;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.PersonalReemplazo;
import pe.gob.osinergmin.sicoes.repository.AdendaReemplazoDao;
import pe.gob.osinergmin.sicoes.repository.DocumentoReemDao;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.repository.PersonalReemplazoDao;
import pe.gob.osinergmin.sicoes.service.AdendaReemplazoService;
import pe.gob.osinergmin.sicoes.service.PersonalReemplazoService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@Service
public class AdendaReemplazoServiceImpl implements AdendaReemplazoService {

    Logger logger = LogManager.getLogger(AdendaReemplazoServiceImpl.class);

    @Autowired
    private AdendaReemplazoDao adendaReemplazoDao;

    @Autowired
    private PersonalReemplazoDao reemplazoDao;

    @Autowired
    private PersonalReemplazoService personalReemplazoService;

    @Autowired
    private ListadoDetalleDao listadoDetalleDao;

    @Autowired
    private DocumentoReemDao documentoReemDao;

    @Override
    @Transactional
    public AdendaReemplazo guardar(AdendaReemplazo adenda, Contexto contexto) {
        logger.info("guardar adenda");
        Long idReemplazoPersonal = adenda.getIdReemplazoPersonal();
        if (idReemplazoPersonal==null){
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONAL_REEMPLAZO_NO_ENVIADO);
        }
        PersonalReemplazo personalReemplazo = reemplazoDao.findById(idReemplazoPersonal)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REEMPLAZO_PERSONAL_NO_EXISTE));
        //Validar que documentacion 6.Cargar Adenda exista
        ListadoDetalle seccion = listadoDetalleDao.obtenerListadoDetalle(
                Constantes.LISTADO.SECCIONES_REEMPLAZO_PERSONAL,
                Constantes.LISTADO.SECCION_DOC_REEMPLAZO.CARGAR_ADENDA);
        logger.info("idReemplazo {}",personalReemplazo.getIdReemplazo());
        logger.info("seccion {}",seccion.getIdListadoDetalle());

        if (!documentoReemDao.existsByIdReemplazoPersonalAndSeccion_IdListadoDetalle(
                personalReemplazo.getIdReemplazo(),seccion.getIdListadoDetalle())) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.DOCUMENTO_REEMPLAZO_NO_EXISTE);
        }

        String listadoAprobacion = Constantes.LISTADO.ESTADO_APROBACION.CODIGO;
        String descAprobacion = Constantes.LISTADO.ESTADO_APROBACION.EN_APROBACION;
        String descAsignado = Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO;
        ListadoDetalle estadoApro = listadoDetalleDao.obtenerListadoDetalle(listadoAprobacion, descAprobacion);
        ListadoDetalle estadoAsignado = listadoDetalleDao.obtenerListadoDetalle(listadoAprobacion, descAsignado);

        personalReemplazo.setEstadoAprobacionAdenda(estadoApro);
        personalReemplazoService.actualizar(personalReemplazo);
        adenda.setEstadoAprobacion(estadoAsignado);
        adenda.setEstadoAprLogistica(estadoAsignado);
        AuditoriaUtil.setAuditoriaRegistro(adenda,AuditoriaUtil.getContextoJob());
        return adendaReemplazoDao.save(adenda);
    }

    @Override
    public AdendaReemplazo obtener(Long aLong, Contexto contexto) {
        return null;
    }

    @Override
    public void eliminar(Long aLong, Contexto contexto) {

    }
}
