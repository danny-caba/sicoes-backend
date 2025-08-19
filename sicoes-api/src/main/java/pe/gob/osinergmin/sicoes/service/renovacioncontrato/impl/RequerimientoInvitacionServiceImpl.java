package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoInvitacionDao;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.RequerimientoInvitacionService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

import java.util.Date;


@Service
public class RequerimientoInvitacionServiceImpl implements RequerimientoInvitacionService {

    @Autowired
    private RequerimientoInvitacionDao requerimientoInvitacionDao;
    @Autowired
    private ListadoDetalleDao listadoDetalleDao;

    Logger logger = LogManager.getLogger(RequerimientoInvitacionServiceImpl.class);

    @Override
    public RequerimientoInvitacion aceptar(RequerimientoInvitacion requerimientoInvitacionIn, Contexto contexto) {
        RequerimientoInvitacion requerimientoInvitacion = requerimientoInvitacionDao.findById(requerimientoInvitacionIn.getIdReqInvitacion()).orElse(null);
        ListadoDetalle estadoActual = listadoDetalleDao.findById(requerimientoInvitacion.getIdEstadoLd()).orElseThrow(
                () -> new ValidacionException(Constantes.CODIGO_MENSAJE.ESTADO_TIPO_INCORRECTO));
        if (!Constantes.LISTADO.ESTADO_INVITACION.INVITADO.equals(estadoActual.getCodigo())) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ESTADO_INVITACION_EDITAR);
        }
        requerimientoInvitacion.setIdEstadoLd(listadoDetalleDao.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_INVITACION.CODIGO, Constantes.LISTADO.ESTADO_INVITACION.ACEPTADO
        ).getIdListadoDetalle());
        requerimientoInvitacion.setFeAceptacion(new Date());

        //TODO: set estado GPPM a Asignado
        

        requerimientoInvitacionDao.save(requerimientoInvitacion);
        return requerimientoInvitacion;
    }


}
