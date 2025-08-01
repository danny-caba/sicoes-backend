package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.osinergmin.sicoes.model.AdendaReemplazo;
import pe.gob.osinergmin.sicoes.model.PersonalReemplazo;
import pe.gob.osinergmin.sicoes.repository.AdendaReemplazoDao;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.repository.PersonalReemplazoDao;
import pe.gob.osinergmin.sicoes.service.AdendaReemplazoService;
import pe.gob.osinergmin.sicoes.service.PersonalReemplazoService;
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
        personalReemplazo.setEstadoAprobacionAdenda(listadoDetalleDao.listarListadoDetallePorCoodigo(
                Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO ).get(0));

        personalReemplazoService.actualizar(personalReemplazo);
        adenda.setEstadoAprobacion(listadoDetalleDao.listarListadoDetallePorCoodigo(
                Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO).get(0));
        adenda.setEstadoAprLogistica(listadoDetalleDao.listarListadoDetallePorCoodigo(
                Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO).get(0));

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
