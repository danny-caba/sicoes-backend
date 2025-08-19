package pe.gob.osinergmin.sicoes.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.osinergmin.sicoes.model.ItemEstado;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.ProcesoItem;
import pe.gob.osinergmin.sicoes.model.Propuesta;
import pe.gob.osinergmin.sicoes.model.PropuestaProfesional;
import pe.gob.osinergmin.sicoes.model.SupervisoraMovimiento;
import pe.gob.osinergmin.sicoes.model.SupervisoraPerfil;
import pe.gob.osinergmin.sicoes.repository.ItemEstadoDao;
import pe.gob.osinergmin.sicoes.service.*;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@Service
public class ItemEstadoServiceImpl implements ItemEstadoService {

	Logger logger = LogManager.getLogger(ItemEstadoServiceImpl.class);

	@Autowired
	private ItemEstadoDao itemEstadoDao;
	
	@Autowired
	private ProcesoItemService procesoItemService;
	
	@Autowired
	private ListadoDetalleService listadoDetalleService;
	
	@Autowired
	private PropuestaProfesionalService propuestaProfesionalService;
	
	@Autowired
	private PropuestaService propuestaService;
	
	@Autowired
	private SupervisoraPerfilService supervisoraPerfilService;

	@Autowired
	private SupervisoraMovimientoService supervisoraMovimientoService;

	@Autowired
	private SicoesSolicitudService sicoesSolicitudService;
	

	@Override
	public ItemEstado guardar(ItemEstado itemEstado, Contexto contexto) {
		ProcesoItem procesoItem = procesoItemService.obtener(itemEstado.getProcesoItem().getProcesoItemUuid(), contexto);
		ListadoDetalle estadoItem = listadoDetalleService.obtener(itemEstado.getEstado().getIdListadoDetalle(), contexto);
		
		
		if(Constantes.LISTADO.ESTADO_ITEM.DESIERTO.equals(estadoItem.getCodigo())||
			Constantes.LISTADO.ESTADO_ITEM.NULO.equals(estadoItem.getCodigo())||
			Constantes.LISTADO.ESTADO_ITEM.SUSPENDIDO.equals(estadoItem.getCodigo())||
			Constantes.LISTADO.ESTADO_ITEM.CANCELADO.equals(estadoItem.getCodigo())) {
			
			List<PropuestaProfesional> profesionalesTotal = propuestaProfesionalService.listarAceptadosXItem(procesoItem.getProcesoItemUuid(),contexto);
			ListadoDetalle motivoProcesoCancelado=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.MOTIVO_BLOQUEO_DESBLOQUEO.CODIGO, Constantes.LISTADO.MOTIVO_BLOQUEO_DESBLOQUEO.PROCESO_CANCELADO);
			desbloquear(profesionalesTotal,motivoProcesoCancelado,contexto);
		}
		
		if(Constantes.LISTADO.ESTADO_ITEM.CONSENTIDO.equals(estadoItem.getCodigo())) {
			
			Propuesta ganadora = propuestaService.obtenerPropuestaGanadora(procesoItem.getProcesoItemUuid(), contexto);
			
			if(ganadora == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.GANADOR_NO_SELECCIONADO);
			}
			
			List<PropuestaProfesional> profesionales = propuestaProfesionalService.listarAceptados(ganadora.getPropuestaUuid(), contexto);
			for(PropuestaProfesional profesional:profesionales) {
				
				List<SupervisoraPerfil> perfilesBloqueados = supervisoraPerfilService.buscarBloqueados(profesional.getSupervisora().getIdSupervisora(), profesional.getSubsector().getIdListadoDetalle(),contexto);

				for(SupervisoraPerfil perfil:perfilesBloqueados) {
					perfil.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUP_PERFIL.CODIGO, Constantes.LISTADO.ESTADO_SUP_PERFIL.BLOQUEADO));
					supervisoraPerfilService.guardar(perfil, contexto);
				}
				
				SupervisoraMovimiento movimientoBloqueado = new SupervisoraMovimiento();
				
				movimientoBloqueado.setSector(profesional.getSector());
				movimientoBloqueado.setSubsector(profesional.getSubsector());
				movimientoBloqueado.setSupervisora(profesional.getSupervisora());
				movimientoBloqueado.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUP_PERFIL.CODIGO, Constantes.LISTADO.ESTADO_SUP_PERFIL.BLOQUEADO));
				movimientoBloqueado.setTipoMotivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_MOTIVO_BLOQUEO.CODIGO, Constantes.LISTADO.TIPO_MOTIVO_BLOQUEO.AUTOMATICO));
				movimientoBloqueado.setMotivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.MOTIVO_BLOQUEO_DESBLOQUEO.CODIGO, Constantes.LISTADO.MOTIVO_BLOQUEO_DESBLOQUEO.POSTOR_GANADOR));
				movimientoBloqueado.setAccion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ACCION_BLOQUEO_DESBLOQUEO.CODIGO, Constantes.LISTADO.ACCION_BLOQUEO_DESBLOQUEO.BLOQUEO));
				movimientoBloqueado.setPropuestaProfesional(profesional);
				movimientoBloqueado.setFechaRegistro(new Date());
				
				supervisoraMovimientoService.guardar(movimientoBloqueado, contexto);
			}
			List<PropuestaProfesional> profesionalesNoGanadores = propuestaProfesionalService.listarNoGanadoresXItem(procesoItem.getProcesoItemUuid(),contexto);
			ListadoDetalle motivoPostorNoGanador = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.MOTIVO_BLOQUEO_DESBLOQUEO.CODIGO, Constantes.LISTADO.MOTIVO_BLOQUEO_DESBLOQUEO.POSTOR_NO_GANADOR);
			desbloquear(profesionalesNoGanadores,motivoPostorNoGanador,contexto);
			try {
				sicoesSolicitudService.guardarContratoConsentido(ganadora, contexto);
			} catch (Exception e) {
				logger.error("Error al guardar solicitud de contrato consentido",e);
			}
		}
		
		itemEstado.setUsuario(contexto.getUsuario());
		itemEstado.setEstadoAnterior(procesoItem.getEstado());
		procesoItem.setEstado(estadoItem);
		procesoItemService.guardar(procesoItem, contexto);
		itemEstado.setFechaRegistro(new Date());
		AuditoriaUtil.setAuditoriaRegistro(itemEstado,contexto);
		return itemEstadoDao.save(itemEstado);
	}
	
	private void desbloquear (List<PropuestaProfesional> profesionales,ListadoDetalle motivo ,Contexto contexto) {
		
		for(PropuestaProfesional profesional:profesionales) {
			
			List<SupervisoraPerfil> perfilesBloqueados = supervisoraPerfilService.buscarBloqueados(profesional.getSupervisora().getIdSupervisora(), profesional.getSubsector().getIdListadoDetalle(),contexto);

			for(SupervisoraPerfil perfil:perfilesBloqueados) {
				perfil.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUP_PERFIL.CODIGO, Constantes.LISTADO.ESTADO_SUP_PERFIL.ACTIVO));
				supervisoraPerfilService.guardar(perfil, contexto);
			}
			
			SupervisoraMovimiento movimientoBloqueado = new SupervisoraMovimiento();
			
			movimientoBloqueado.setSector(profesional.getSector());
			movimientoBloqueado.setSubsector(profesional.getSubsector());
			movimientoBloqueado.setSupervisora(profesional.getSupervisora());
			movimientoBloqueado.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUP_PERFIL.CODIGO, Constantes.LISTADO.ESTADO_SUP_PERFIL.ACTIVO));
			movimientoBloqueado.setTipoMotivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_MOTIVO_BLOQUEO.CODIGO, Constantes.LISTADO.TIPO_MOTIVO_BLOQUEO.AUTOMATICO));
			movimientoBloqueado.setMotivo(motivo);
			movimientoBloqueado.setAccion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ACCION_BLOQUEO_DESBLOQUEO.CODIGO, Constantes.LISTADO.ACCION_BLOQUEO_DESBLOQUEO.DESBLOQUEO));
			movimientoBloqueado.setPropuestaProfesional(profesional);
			movimientoBloqueado.setFechaRegistro(new Date());
			
			supervisoraMovimientoService.guardar(movimientoBloqueado, contexto);
		}	
	}

	@Override
	public ItemEstado obtener(Long idItemEstado, Contexto contexto) {
		return itemEstadoDao.obtener(idItemEstado);
	}
	
	@Override
	public ItemEstado obtener(Long idItemEstado,String procesoItemUuid, Contexto contexto) {
		return itemEstadoDao.obtener(idItemEstado,procesoItemUuid);
	}

	@Override
	public void eliminar(Long idItemEstado, Contexto contexto) {
		itemEstadoDao.deleteById(idItemEstado);
	}
	
	
	@Override
	public Page<ItemEstado> listarItemsEstado(String procesoItemUuid,Pageable pageable, Contexto contexto) {
		Page<ItemEstado> page=itemEstadoDao.buscar(procesoItemUuid,pageable);
		return page;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void eliminar(Long id, String procesoItemUuid, Contexto contexto) {
		ItemEstado itemEstado = itemEstadoDao.obtener(id, procesoItemUuid);
		itemEstadoDao.delete(itemEstado);
	}  
}
