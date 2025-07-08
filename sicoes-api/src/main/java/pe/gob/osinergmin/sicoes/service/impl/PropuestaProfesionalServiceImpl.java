package pe.gob.osinergmin.sicoes.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Proceso;
import pe.gob.osinergmin.sicoes.model.ProcesoEtapa;
import pe.gob.osinergmin.sicoes.model.ProcesoItem;
import pe.gob.osinergmin.sicoes.model.ProcesoItemPerfil;
import pe.gob.osinergmin.sicoes.model.Propuesta;
import pe.gob.osinergmin.sicoes.model.PropuestaProfesional;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.SupervisoraMovimiento;
import pe.gob.osinergmin.sicoes.model.SupervisoraPerfil;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.repository.PropuestaDao;
import pe.gob.osinergmin.sicoes.repository.PropuestaProfesionalDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.ProcesoEtapaService;
import pe.gob.osinergmin.sicoes.service.ProcesoItemPerfilService;
import pe.gob.osinergmin.sicoes.service.ProcesoItemService;
import pe.gob.osinergmin.sicoes.service.ProcesoService;
import pe.gob.osinergmin.sicoes.service.PropuestaProfesionalService;
import pe.gob.osinergmin.sicoes.service.PropuestaService;
import pe.gob.osinergmin.sicoes.service.SupervisoraMovimientoService;
import pe.gob.osinergmin.sicoes.service.SupervisoraPerfilService;
import pe.gob.osinergmin.sicoes.service.SupervisoraService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;
import pe.gob.osinergmin.sicoes.util.DatosExportacion;
import pe.gob.osinergmin.sicoes.util.ExcelUtils;
import pe.gob.osinergmin.sicoes.util.PageUtilImpl;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@Service
public class PropuestaProfesionalServiceImpl implements PropuestaProfesionalService {

	Logger logger = LogManager.getLogger(PropuestaProfesionalServiceImpl.class);

	@Autowired
	private PropuestaProfesionalDao propuestaProfesionalDao;
	
	@Autowired
	private ListadoDetalleService listadoDetalleService;
	
	@Autowired
	private PropuestaService propuestaService;
	
	@Autowired
	private PropuestaDao propuestaDao;
	
	@Autowired
	private SupervisoraService supervisoraService;
	
	@Autowired
	private ProcesoItemPerfilService procesoItemPerfilService;
	
	@Autowired
	private ProcesoItemService procesoItemService;
	
	@Autowired
	private ProcesoEtapaService procesoEtapaService;
	
	@Autowired
	private  SupervisoraPerfilService supervisoraPerfilService; 
	
	
	@Autowired
	private ProcesoService procesoService;
	
	@Autowired
	private SupervisoraMovimientoService supervisoraMovimientoService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PropuestaProfesional guardar(PropuestaProfesional propuestaProfesional, Contexto contexto) {
		if(propuestaProfesional.getPerfil() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.P_PERFIL_NO_ENVIADO);		
		}
		if(propuestaProfesional.getSector() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.P_SECTOR_NO_ENVIADO);
		}
		if(propuestaProfesional.getSubsector() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.P_SUBSECTOR_NO_ENVIADO);
		}
		if(propuestaProfesional.getSubsector() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROFESIONAL_NO_ENVIADO);
		}
		PropuestaProfesional propuestaProfesionalBD=null;
		Propuesta propuesta = propuestaService.obtener(propuestaProfesional.getPropuesta().getPropuestaUuid(), contexto);
		
		Supervisora supervisora = supervisoraService.obtener(propuestaProfesional.getSupervisora().getIdSupervisora(),contexto);
		Propuesta propuestaBD = propuestaDao.obtener(propuesta.getPropuestaUuid());
	    Long idProcesoItem = propuestaBD.getProcesoItem().getIdProcesoItem();  // Declarar e inicializar idProcesoItem
		List<SupervisoraMovimiento> movimientos = supervisoraMovimientoService.listarXProfesional(supervisora.getIdSupervisora(),propuestaProfesional.getSubsector().getIdListadoDetalle());
		List<SupervisoraMovimiento> movimientosItem = supervisoraMovimientoService.listarXProfesionalXItem(supervisora.getIdSupervisora(),propuestaProfesional.getSubsector().getIdListadoDetalle());

		//if(movimientos.size()>0 && Constantes.LISTADO.ESTADO_SUP_PERFIL.BLOQUEADO.equals(movimientos.get(0).getEstado().getCodigo()) ) {
			//throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROF_BLOQUEADO);
		//}

		if (movimientosItem.size() > 0 
			    && Constantes.LISTADO.ESTADO_SUP_PERFIL.BLOQUEADO.equals(movimientosItem.get(0).getEstado().getCodigo()) 
			    && !movimientosItem.get(0).getPropuestaProfesional().getPropuesta().getProcesoItem().getIdProcesoItem().equals(idProcesoItem)) {
				
			    throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROF_BLOQUEADO);
			}
		
		
//		List<SupervisoraPerfil> listPerfSuper = supervisoraPerfilService.buscar(supervisora.getIdSupervisora(), contexto);
//		for(SupervisoraPerfil perfil:listPerfSuper) {
//			if(propuestaProfesional.getSubsector().getCodigo().equals(perfil.getSubsector().getCodigo())) {
//				if(Constantes.LISTADO.ESTADO_SUP_PERFIL.BLOQUEADO.equals(perfil.getEstado().getCodigo())) {
//					throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROF_BLOQUEADO);
//				}
//			}
//		}
		
		if(propuestaProfesional.getIdPropuestaProfesional()==null) {
			List<PropuestaProfesional> invitaciones = propuestaProfesionalDao.listarXpropuesta(propuestaProfesional.getPropuesta().getPropuestaUuid());
			for(PropuestaProfesional invitacion:invitaciones) {
				if(invitacion.getSupervisora().getCodigoRuc().equals(propuestaProfesional.getSupervisora().getCodigoRuc())) {
					if(invitacion.getEstado().getCodigo().equals(Constantes.LISTADO.ESTADO_INVITACION.ACEPTADO)||
							invitacion.getEstado().getCodigo().equals(Constantes.LISTADO.ESTADO_INVITACION.INVITADO)) {
						throw new ValidacionException(Constantes.CODIGO_MENSAJE.EXISTE_INVITACION);
					}
					
				}	
			}
			propuestaProfesionalBD=propuestaProfesional;
			ListadoDetalle enviada = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_INVITACION.CODIGO, Constantes.LISTADO.ESTADO_INVITACION.INVITADO);
			propuestaProfesionalBD.setPropuesta(propuesta);
			propuestaProfesionalBD.setSupervisora(supervisora);
			propuestaProfesionalBD.setEstado(enviada);
			propuestaProfesionalBD.setFechaInvitacion(new Date());
		}else { 
			propuestaProfesionalBD = propuestaProfesionalDao.obtener(propuestaProfesional.getIdPropuestaProfesional(), propuestaProfesional.getPropuesta().getPropuestaUuid());
			propuestaProfesionalBD.setPerfil(propuestaProfesional.getPerfil());
		}
		
		AuditoriaUtil.setAuditoriaRegistro(propuestaProfesionalBD,contexto);
		propuestaProfesionalDao.save(propuestaProfesionalBD);
		
		return propuestaProfesionalBD;
	}

	@Override
	public PropuestaProfesional obtener(Long id,String propuestaUuid, Contexto contexto) {
		PropuestaProfesional propuestaProfesionalBD = propuestaProfesionalDao.obtener(id,propuestaUuid);
		if(propuestaProfesionalBD != null) {
			List<ProcesoEtapa> etapas = procesoEtapaService.listar(propuestaProfesionalBD.getPropuesta().getProcesoItem().getProceso().getProcesoUuid(), contexto);
			propuestaProfesionalBD.getPropuesta().getProcesoItem().getProceso().setEtapas(etapas);
		}
		return propuestaProfesionalBD;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void eliminar(Long idPropuestaProfesional, Contexto contexto) {
		propuestaProfesionalDao.deleteById(idPropuestaProfesional);		
	}

	@Override
	public Page<PropuestaProfesional> listarPropuestasProfesionales(String sFechaDesde, String sFechaHasta, Long idSector, Long idSubsector, String nroProceso, String nombreProceso, Long idEstadoItem, Long idEstadoInvitacion, String empresa,String item, Pageable pageable, Contexto contexto) {
		Date fechaDesde = DateUtil.getEndDay(sFechaDesde);
		Date fechaHasta = DateUtil.getEndDay(sFechaHasta);
		Usuario usuario = contexto.getUsuario();
		List<Supervisora> supervisoras = supervisoraService.obtenerSupervisoraPNProfesional(usuario.getCodigoRuc());
		
		List<PropuestaProfesional> list = new ArrayList<>();
		
		if(supervisoras != null) {
			
			for (Supervisora supervisora : supervisoras) {

				List<PropuestaProfesional> lista = new ArrayList<>();				
				lista = propuestaProfesionalDao.buscar( fechaDesde, fechaHasta,idSector, idSubsector, nroProceso, nombreProceso,
						idEstadoItem, idEstadoInvitacion,supervisora.getIdSupervisora(),empresa,item,pageable).toList();
				
				for  (PropuestaProfesional propuesta : lista)
					list.add(propuesta);
			}
			
			PageUtilImpl<PropuestaProfesional> page =new PageUtilImpl<PropuestaProfesional>(list);			
			return page;
		}
		else {
			PageUtilImpl<PropuestaProfesional> page =new PageUtilImpl<PropuestaProfesional>(list);
			return page;
		}
	}

	@Override
	public PropuestaProfesional obtener(Long id, Contexto contexto) {
			return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PropuestaProfesional aceptarInvitacion(Long id, PropuestaProfesional propuestaProfesional,String uuidPropuesta,
			Contexto contexto) {
		
		PropuestaProfesional propuestaProfesionalBD = propuestaProfesionalDao.obtener(id,uuidPropuesta);
		Propuesta propuestaBD = propuestaService.obtener(uuidPropuesta, contexto);
	
		if(propuestaProfesionalBD == null||propuestaBD == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROPUESTA_UUID_NO_ENVIADO);
		}
		/*
		if(!Constantes.LISTADO.ESTADO_ITEM.PRESENTACION.equals(propuestaBD.getProcesoItem().getEstado().getCodigo())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.INV_ESTADO_PRESENTACION);
		}*/
		
		Supervisora supervisora = supervisoraService.obtener(propuestaProfesionalBD.getSupervisora().getIdSupervisora(), contexto);
		
		if(Constantes.LISTADO.ESTADO_SUPERVISORA.SUSPENDIDO.equals(supervisora.getEstado().getCodigo())||
				Constantes.LISTADO.ESTADO_SUPERVISORA.CANCELADO.equals(supervisora.getEstado().getCodigo())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.SUP_CANCELADA_SUSPENDIDA);
		}
		
		if(Constantes.LISTADO.ESTADO_SUPERVISORA.BLOQUEADO.equals(supervisora.getEstado().getCodigo())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROF_BLOQUEADO);
		}
		if(!Constantes.LISTADO.ESTADO_INVITACION.INVITADO.equals(propuestaProfesionalBD.getEstado().getCodigo())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ESTADO_INVITACION_EDITAR);
		}
		
//		List<SupervisoraPerfil> perfilesBloqueados = supervisoraPerfilService.buscarBloqueados(supervisora.getIdSupervisora(), propuestaProfesionalBD.getSubsector().getIdListadoDetalle(),contexto);
//		
//		if(!perfilesBloqueados.isEmpty() ) {
//				throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROF_BLOQUEADO);
//		}
		
		List<SupervisoraMovimiento> movimientos = supervisoraMovimientoService.listarXProfesional(supervisora.getIdSupervisora(),propuestaProfesional.getSubsector().getIdListadoDetalle());

		//if(movimientos.size()>0 && Constantes.LISTADO.ESTADO_SUP_PERFIL.BLOQUEADO.equals(movimientos.get(0).getEstado().getCodigo()) ) {
			//throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROF_BLOQUEADO);
		//}
		List<SupervisoraPerfil> perfiles = supervisoraPerfilService.buscar(supervisora.getIdSupervisora(), propuestaProfesionalBD.getSector().getIdListadoDetalle(), propuestaProfesionalBD.getSubsector().getIdListadoDetalle());

		for(SupervisoraPerfil perfil:perfiles) {
			perfil.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUP_PERFIL.CODIGO, Constantes.LISTADO.ESTADO_SUP_PERFIL.BLOQUEADO));
			supervisoraPerfilService.guardar(perfil, contexto);
		}

		SupervisoraMovimiento movimientoBloqueado = new SupervisoraMovimiento();
		
		movimientoBloqueado.setSector(propuestaProfesionalBD.getSector());
		movimientoBloqueado.setSubsector(propuestaProfesionalBD.getSubsector());
		movimientoBloqueado.setSupervisora(supervisora);
		movimientoBloqueado.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUP_PERFIL.CODIGO, Constantes.LISTADO.ESTADO_SUP_PERFIL.BLOQUEADO));
		movimientoBloqueado.setTipoMotivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_MOTIVO_BLOQUEO.CODIGO, Constantes.LISTADO.TIPO_MOTIVO_BLOQUEO.AUTOMATICO));
		movimientoBloqueado.setMotivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.MOTIVO_BLOQUEO_DESBLOQUEO.CODIGO, Constantes.LISTADO.MOTIVO_BLOQUEO_DESBLOQUEO.INVITACION_ACEPTADA));
		movimientoBloqueado.setAccion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ACCION_BLOQUEO_DESBLOQUEO.CODIGO, Constantes.LISTADO.ACCION_BLOQUEO_DESBLOQUEO.BLOQUEO));
		movimientoBloqueado.setPropuestaProfesional(propuestaProfesionalBD);
		movimientoBloqueado.setFechaRegistro(new Date());
		
		supervisoraMovimientoService.guardar(movimientoBloqueado, contexto);
	
		supervisoraService.guardar(supervisora, contexto);
		ListadoDetalle estadoAceptado = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_INVITACION.CODIGO, Constantes.LISTADO.ESTADO_INVITACION.ACEPTADO);
		propuestaProfesionalBD.setEstado(estadoAceptado);
		AuditoriaUtil.setAuditoriaRegistro(propuestaProfesionalBD,contexto);
		propuestaProfesionalDao.save(propuestaProfesionalBD);	
		return propuestaProfesionalBD;
	}  
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public PropuestaProfesional rechazarInvitacion(Long id, PropuestaProfesional propuestaProfesional,String uuidPropuesta,
			Contexto contexto) {
		
		PropuestaProfesional propuestaProfesionalBD = propuestaProfesionalDao.obtener(id,uuidPropuesta);

		Supervisora supervisora = supervisoraService.obtener(propuestaProfesionalBD.getSupervisora().getIdSupervisora(), contexto);
		Propuesta propuestaBD = propuestaService.obtener(uuidPropuesta, contexto);
		
		/*
		if(!Constantes.LISTADO.ESTADO_ITEM.PRESENTACION.equals(propuestaBD.getProcesoItem().getEstado().getCodigo())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.INV_ESTADO_PRESENTACION);
		}
		*/
		if(Constantes.LISTADO.ESTADO_SUPERVISORA.SUSPENDIDO.equals(supervisora.getEstado().getCodigo())||
				Constantes.LISTADO.ESTADO_SUPERVISORA.CANCELADO.equals(supervisora.getEstado().getCodigo())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.SUP_CANCELADA_SUSPENDIDA);
		}
		List<SupervisoraMovimiento> movimientos = supervisoraMovimientoService.listarXProfesional(supervisora.getIdSupervisora(),propuestaProfesional.getSubsector().getIdListadoDetalle());

		if(movimientos.size()>0 && Constantes.LISTADO.ESTADO_SUP_PERFIL.BLOQUEADO.equals(movimientos.get(0).getEstado().getCodigo()) ) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROF_BLOQUEADO);
		}
		if(!Constantes.LISTADO.ESTADO_INVITACION.INVITADO.equals(propuestaProfesionalBD.getEstado().getCodigo())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ESTADO_INVITACION_EDITAR);
		}
		ListadoDetalle estadoRechazado = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_INVITACION.CODIGO, Constantes.LISTADO.ESTADO_INVITACION.RECHAZADO);
		propuestaProfesionalBD.setEstado(estadoRechazado);
		AuditoriaUtil.setAuditoriaRegistro(propuestaProfesionalBD,contexto);
		propuestaProfesionalDao.save(propuestaProfesionalBD);	
		return propuestaProfesionalBD;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public PropuestaProfesional cancelarInvitacion(Long id, PropuestaProfesional propuestaProfesional,String uuidPropuesta,
			Contexto contexto) {
		
		PropuestaProfesional propuestaProfesionalBD = propuestaProfesionalDao.obtener(id,uuidPropuesta);
		Supervisora supervisora = supervisoraService.obtener(propuestaProfesionalBD.getSupervisora().getIdSupervisora(), contexto);
		if(Constantes.LISTADO.ESTADO_INVITACION.ACEPTADO.equals(propuestaProfesionalBD.getEstado().getCodigo())) {

			List<SupervisoraPerfil> perfilesBloqueados = supervisoraPerfilService.buscarBloqueados(supervisora.getIdSupervisora(), propuestaProfesionalBD.getSubsector().getIdListadoDetalle(),contexto);

			for(SupervisoraPerfil perfil:perfilesBloqueados) {
				perfil.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUP_PERFIL.CODIGO, Constantes.LISTADO.ESTADO_SUP_PERFIL.ACTIVO));
				supervisoraPerfilService.guardar(perfil, contexto);
			}
			
			SupervisoraMovimiento movimientoBloqueado = new SupervisoraMovimiento();
			
			movimientoBloqueado.setSector(propuestaProfesionalBD.getSector());
			movimientoBloqueado.setSubsector(propuestaProfesionalBD.getSubsector());
			movimientoBloqueado.setSupervisora(supervisora);
			movimientoBloqueado.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUP_PERFIL.CODIGO, Constantes.LISTADO.ESTADO_SUP_PERFIL.ACTIVO));
			movimientoBloqueado.setTipoMotivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_MOTIVO_BLOQUEO.CODIGO, Constantes.LISTADO.TIPO_MOTIVO_BLOQUEO.AUTOMATICO));
			movimientoBloqueado.setMotivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.MOTIVO_BLOQUEO_DESBLOQUEO.CODIGO, Constantes.LISTADO.MOTIVO_BLOQUEO_DESBLOQUEO.INVITACION_CANCELADA));
			movimientoBloqueado.setAccion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ACCION_BLOQUEO_DESBLOQUEO.CODIGO, Constantes.LISTADO.ACCION_BLOQUEO_DESBLOQUEO.DESBLOQUEO));
			movimientoBloqueado.setPropuestaProfesional(propuestaProfesionalBD);
			movimientoBloqueado.setFechaRegistro(new Date());
			
			supervisoraMovimientoService.guardar(movimientoBloqueado, contexto);

		}
		ListadoDetalle estadoCancelado = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_INVITACION.CODIGO, Constantes.LISTADO.ESTADO_INVITACION.CANCELADO);
		propuestaProfesionalBD.setEstado(estadoCancelado);
		propuestaProfesionalDao.save(propuestaProfesionalBD);	
		return propuestaProfesionalBD;
	}  

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void eliminar(Long id, String propuestaUuid, Contexto contexto) {
		PropuestaProfesional propuestaProfesionaBD = propuestaProfesionalDao.obtener(id, propuestaUuid);
		propuestaProfesionalDao.delete(propuestaProfesionaBD);
		
	}

	@Override
	public List<PropuestaProfesional> listar(String propuestaUuid, Contexto contexto) {
		return propuestaProfesionalDao.listarXpropuesta(propuestaUuid);
	}

	@Override
	public List<PropuestaProfesional> listarPorId(Long id, Contexto contexto) {
		return propuestaProfesionalDao.findAllById(Collections.singleton(id));
	}

	@Override
	public List<PropuestaProfesional> listarAceptados(String propuestaUuid, Contexto contexto) {
		return propuestaProfesionalDao.listarAceptados(propuestaUuid);
	}
	
	@Override
	public List<PropuestaProfesional> listar() {
		return propuestaProfesionalDao.listar();
	}

	@Override
	public List<Object[]> listarProfesionalesXPerfil(String procesoItemUuid, Contexto contexto) {
		List<Object[]> profesionales = propuestaProfesionalDao.listarProfesionalesXPerfil(procesoItemUuid); 
		
		return profesionales;
	}

	@Override
	public Page<PropuestaProfesional> listarPropuestasProfesionalesXPropuesta(String propuestaUuid, Pageable pageable, Contexto contexto) {
		return propuestaProfesionalDao.buscarXPropuesta(propuestaUuid, pageable);
	}
	
	@Override
	public Page<PropuestaProfesional> listarProfesionalesAceptados(String propuestaUuid, Pageable pageable, Contexto contexto) {
		return propuestaProfesionalDao.buscarAceptados(propuestaUuid, pageable);
	}

	@Override
	public InputStream generarExport(String procesoItemUuid,Contexto contexto) {
		ProcesoItem procesoItem = procesoItemService.obtener(procesoItemUuid, contexto);
		Proceso proceso = procesoService.obtener(procesoItem.getProceso().getProcesoUuid(), contexto);
		Supervisora supervisora = supervisoraService.obtenerSupervisoraXRUCNoProfesional(contexto.getUsuario().getCodigoRuc());
		if(supervisora == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_NO_ES_SUPERVISORA);
		}		
		List<SupervisoraPerfil> listSupervisoraPerfil=supervisoraPerfilService.buscar(supervisora.getIdSupervisora(),proceso.getSector().getIdListadoDetalle(),proceso.getSubsector().getIdListadoDetalle());
		if(listSupervisoraPerfil==null||listSupervisoraPerfil.isEmpty()) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_NO_ES_SUPERVISORA);
		}
		List<Object[]> union = new ArrayList<>();
		List<ProcesoItemPerfil> itemsPerfil = procesoItemPerfilService.listar(procesoItemUuid, contexto);
		for(ProcesoItemPerfil itemPerfil:itemsPerfil) {
			List<Object[]> result =  supervisoraService.listarProfesionales(itemPerfil.getPerfil().getIdListadoDetalle());
			union.addAll(result);
		}
		
		List<DatosExportacion> listDatos = new ArrayList<DatosExportacion>();			
		
		DatosExportacion datosExportacion =new DatosExportacion();
		datosExportacion.setTitulo("Reporte de profesionales");
		datosExportacion.setNombreHoja("Reporte de profesionales");
		datosExportacion.setFecha(new Date());
		datosExportacion.setUsuario(contexto.getUsuario().getNombreUsuario());
		datosExportacion.setDescuentoRegistros(0L);
		datosExportacion.setFiltros(
				new String[][]{				
					});
		datosExportacion.setNombreTitulos(new String[] {"Sector","SubSector","Actividad","Unidad","Subcategoria","Perfil","RUC","Nombre","Teléfono 1","Teléfono 2","Teléfono 3","Correo Electrónico"});
		datosExportacion.setListado(union);
		listDatos.add(datosExportacion);
		
		return ExcelUtils.generarArchivo(listDatos);
	}

	@Override
	public List<PropuestaProfesional> listarAceptadosXItem(String procesoItemUuid, Contexto contexto) {
		return propuestaProfesionalDao.listarAceptadosXItem(procesoItemUuid);
	}

	@Override
	public List<PropuestaProfesional> listarNoGanadoresXItem(String procesoItemUuid, Contexto contexto) {
		return propuestaProfesionalDao.listarNoGanadoresXItem(procesoItemUuid);
	}

	@Override
	public List<PropuestaProfesional> listarNoAceptados(String propuestaUuid, Contexto contexto) {
		return propuestaProfesionalDao.listarInvitados(propuestaUuid);
	}  
	
	public List<Object[]> obtenerPersonalPropuesto(Long idSoliPerfCont) {
	    return propuestaProfesionalDao.findPersonalPropuesto(idSoliPerfCont);
	}

}
