package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InvitacionRequestDTO;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.PlazoConfirmacionDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoInvitacionDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoRenovacionDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.PlazoConfirmacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InvitacionCreateRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InvitacionCreateResponseDTO;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InvitacionService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.renovacioncontrato.EstadoInvitacion;
import pe.gob.osinergmin.sicoes.util.renovacioncontrato.EstadoRegistro;
import pe.gob.osinergmin.sicoes.util.renovacioncontrato.TipoDia;

@Service
@Transactional
public class InvitacionImplService implements InvitacionService {

    @Autowired
    private RequerimientoInvitacionDao requerimientoInvitacionDao;

    @Autowired
    private PlazoConfirmacionDao plazoConfirmacionDao;

    @Autowired
    private RequerimientoRenovacionDao requerimientoRenovacionDao;

    @Autowired
    private SigedApiConsumer sigedApiConsumer;

    @Autowired
    private ListadoDetalleDao listadoDetalleDao;

    @Autowired
    private NotificacionService notificacionService;

    @Transactional
    public RequerimientoInvitacion registrarInvitacion(InvitacionCreateRequestDTO requestDTO, Contexto contexto) {
        if (requestDTO.getIdReqRenovacion() == null)
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.REQUERIMIENTO_NO_ENVIADO);

        RequerimientoRenovacion requerimientoRenovacionDB = requerimientoRenovacionDao.obtenerPorId(requestDTO.getIdReqRenovacion());
        SicoesSolicitud solicitud = requerimientoRenovacionDB.getSolicitudPerfil();
        Supervisora supervisora = solicitud.getSupervisora();

        if (supervisora == null)
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.SUPERVISORA_NO_ENCONTRADA);

        List<PlazoConfirmacion> plazosConfirmacion = plazoConfirmacionDao.buscarPorEstado(EstadoRegistro.ACTIVO.getCodigo());
        if (plazosConfirmacion == null || plazosConfirmacion.isEmpty()) {
            throw new DataNotFoundException("No se encontró plazo de confirmación activo");
        }

        PlazoConfirmacion plazoConfirmacion = plazosConfirmacion.get(0);

        Date fechaInvitacion = new Date();

        String tipoPlazo;
        if (TipoDia.CALENDARIO.getCodigo().equals(plazoConfirmacion.getInTipoDia().toString())) {
            tipoPlazo = Constantes.DIAS_CALENDARIO;
        } else {
            tipoPlazo = Constantes.DIAS_HABILES;
        }

        Date fechaCaducidad = sigedApiConsumer.calcularFechaFin(fechaInvitacion, plazoConfirmacion.getNuDias().longValue(), tipoPlazo);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaCaducidad);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        fechaCaducidad = calendar.getTime();

        ListadoDetalle estadoInvitado = listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_INVITACION.CODIGO,
                Constantes.LISTADO.ESTADO_INVITACION.INVITADO);

        RequerimientoInvitacion invitacion = new RequerimientoInvitacion();
        invitacion.setRequerimientoRenovacion(requerimientoRenovacionDB);
        invitacion.setSupervisora(supervisora);
        invitacion.setFeInvitacion(new Date());
        invitacion.setFeCaducidad(fechaCaducidad);
        invitacion.setEstadoInvitacion(estadoInvitado);
        invitacion.setFlActivo(EstadoRegistro.ACTIVO.getCodigo());
        invitacion.setCoUuid(UUID.randomUUID().toString());
        invitacion.setPlazoConfirmacion(plazoConfirmacion);

        AuditoriaUtil.setAuditoriaRegistro(invitacion, contexto);
        notificacionService.enviarMensajeInvitacionRenovacion(invitacion, contexto);
        return requerimientoInvitacionDao.save(invitacion);
    }

    @Override
    public void eliminarInvitacion(InvitacionRequestDTO requestDTO, Contexto contexto) {
        RequerimientoInvitacion invitacionDB = requerimientoInvitacionDao.
                obtenerPorUuid(requestDTO.getUuid()).orElseThrow(() ->
                        new ValidacionException(Constantes.CODIGO_MENSAJE.INVITACION_NO_ENCONTRADA));

        ListadoDetalle estadoInvitacion = listadoDetalleDao.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_INVITACION.CODIGO,
                Constantes.LISTADO.ESTADO_INVITACION.ELIMINADO);
        invitacionDB.setEstadoInvitacion(estadoInvitacion);
        invitacionDB.setFeCancelado(new Date());
        invitacionDB.setFlActivo(EstadoRegistro.INACTIVO.getCodigo());
        AuditoriaUtil.setAuditoriaRegistro(invitacionDB, contexto);
        requerimientoInvitacionDao.save(invitacionDB);
    }
}
