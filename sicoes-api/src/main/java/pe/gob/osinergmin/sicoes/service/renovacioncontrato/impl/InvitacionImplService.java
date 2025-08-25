package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.PlazoConfirmacionDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoInvitacionDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoRenovacionDao;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;
import pe.gob.osinergmin.sicoes.util.Contexto;
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
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;
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

    public InvitacionCreateResponseDTO registrarInvitacion(InvitacionCreateRequestDTO requestDTO,List<String> log) {
        if (requestDTO.getIdReqRenovacion() == null) {
            throw new DataNotFoundException("El campo idReqRenovacion es obligatorio");
        }
        log.add("PASO 1");

        if (requestDTO.getUsuario() == null || requestDTO.getUsuario().trim().isEmpty()) {
            throw new DataNotFoundException("El campo usuario es obligatorio");
        }
        log.add("PASO 2");
        if (requestDTO.getIp() == null || requestDTO.getIp().trim().isEmpty()) {
            throw new DataNotFoundException("El campo ip es obligatorio");
        }
        log.add("PASO 3");
        List<PlazoConfirmacion> plazosConfirmacion = plazoConfirmacionDao.buscarPorEstado(EstadoRegistro.ACTIVO.getCodigo());
        if (plazosConfirmacion == null || plazosConfirmacion.isEmpty()) {
            throw new DataNotFoundException("No se encontró plazo de confirmación activo");
        }
        log.add("PASO 4");
        PlazoConfirmacion plazoConfirmacion = plazosConfirmacion.get(0);

        RequerimientoRenovacion requerimientoRenovacion = requerimientoRenovacionDao.obtenerPorId(requestDTO.getIdReqRenovacion());
        if (requerimientoRenovacion == null) {
            throw new DataNotFoundException("No se encontró el requerimiento de renovación con ID: " + requestDTO.getIdReqRenovacion());
        }
        log.add("PASO 5");
        SicoesSolicitud solicitud = requerimientoRenovacion.getSolicitudPerfil();
        if (solicitud == null) {
            throw new DataNotFoundException("No se encontró la solicitud asociada al requerimiento");
        }
        log.add("PASO 6");
        Supervisora supervisora = solicitud.getSupervisora();
        if (supervisora == null) {
            throw new DataNotFoundException("No se encontró la supervisora asociada a la solicitud");
        }
        log.add("PASO 7");
        Date fechaInvitacion = new Date();
        
        String tipoPlazo;
        if (TipoDia.CALENDARIO.getCodigo().equals(plazoConfirmacion.getInTipoDia().toString())) {
            tipoPlazo = "C";
        } else {
            tipoPlazo = Constantes.DIAS_HABILES;
        }
        log.add("PASO 8");
        Date fechaCaducidad = sigedApiConsumer.calcularFechaFin(fechaInvitacion, plazoConfirmacion.getNuDias().longValue(), tipoPlazo);
        log.add("PASO 9");
        ListadoDetalle estadoInvitacion = listadoDetalleDao.obtenerListadoDetalle(
            Constantes.LISTADO.ESTADO_INVITACION.CODIGO, 
            EstadoInvitacion.INVITACION.getCodigo()
        );
        log.add("PASO 10");
        RequerimientoInvitacion nuevaInvitacion = new RequerimientoInvitacion();
        nuevaInvitacion.setRequerimientoRenovacion(requerimientoRenovacion);
        nuevaInvitacion.setIdSupervisora(supervisora.getIdSupervisora());
        nuevaInvitacion.setPlazoConfirmacion(plazoConfirmacion);
        nuevaInvitacion.setFeInvitacion(fechaInvitacion);
        nuevaInvitacion.setFeCaducidad(fechaCaducidad);
        nuevaInvitacion.setEstadoInvitacion(estadoInvitacion);
        nuevaInvitacion.setFlActivo(EstadoRegistro.ACTIVO.getCodigo());
        nuevaInvitacion.setUsuCreacion(requestDTO.getUsuario());
        nuevaInvitacion.setFecCreacion(fechaInvitacion);
        nuevaInvitacion.setIpCreacion(requestDTO.getIp());

        RequerimientoInvitacion invitacionGuardada = requerimientoInvitacionDao.save(nuevaInvitacion);
        log.add("PASO 11");
        InvitacionCreateResponseDTO response = new InvitacionCreateResponseDTO();
        response.setIdReqInvitacion(invitacionGuardada.getIdReqInvitacion());
        response.setRequerimientoRenovacion(requerimientoRenovacion);
        response.setPlazoConfirmacion(invitacionGuardada.getPlazoConfirmacion());
        response.setEstadoInvitacion(invitacionGuardada.getEstadoInvitacion());
        response.setIdSupervisora(invitacionGuardada.getIdSupervisora());
        response.setFeInvitacion(invitacionGuardada.getFeInvitacion());
        response.setFeCaducidad(invitacionGuardada.getFeCaducidad());
        response.setFeAceptacion(invitacionGuardada.getFeAceptacion());
        response.setFeRechazo(invitacionGuardada.getFeRechazo());
        response.setFlActivo(invitacionGuardada.getFlActivo());
        response.setCoUuid(invitacionGuardada.getCoUuid());
        response.setFeCancelado(invitacionGuardada.getFeCancelado());
        log.add("PASO 12");
        return response;
    }
}
