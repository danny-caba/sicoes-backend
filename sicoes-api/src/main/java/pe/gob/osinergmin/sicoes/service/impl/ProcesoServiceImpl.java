package pe.gob.osinergmin.sicoes.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Paces;
import pe.gob.osinergmin.sicoes.model.Proceso;
import pe.gob.osinergmin.sicoes.model.ProcesoDocumento;
import pe.gob.osinergmin.sicoes.model.ProcesoEtapa;
import pe.gob.osinergmin.sicoes.model.ProcesoItem;
import pe.gob.osinergmin.sicoes.model.ProcesoItemPerfil;
import pe.gob.osinergmin.sicoes.model.ProcesoMiembro;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.SupervisoraPerfil;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.repository.PacesDao;
import pe.gob.osinergmin.sicoes.repository.ProcesoDao;
import pe.gob.osinergmin.sicoes.repository.ProcesoItemDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.ProcesoDocumentoService;
import pe.gob.osinergmin.sicoes.service.ProcesoEtapaService;
import pe.gob.osinergmin.sicoes.service.ProcesoItemPerfilService;
import pe.gob.osinergmin.sicoes.service.ProcesoItemService;
import pe.gob.osinergmin.sicoes.service.ProcesoMiembroService;
import pe.gob.osinergmin.sicoes.service.ProcesoService;
import pe.gob.osinergmin.sicoes.service.SupervisoraPerfilService;
import pe.gob.osinergmin.sicoes.service.SupervisoraService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@Service
public class ProcesoServiceImpl implements ProcesoService {

	Logger logger = LogManager.getLogger(ProcesoServiceImpl.class);

	@Autowired
	private ProcesoDao procesoDao;
	
	@Autowired
	private ProcesoItemDao procesoItemDao;

	@Autowired
	private ListadoDetalleService listadoDetalleService;

	@Autowired
	private ProcesoItemService procesoItemService;
	
	@Autowired
	private ProcesoDocumentoService procesoDocumentoService;

	@Autowired
	private ProcesoMiembroService procesoMiembroService;

	@Autowired
	private ProcesoEtapaService procesoEtapaService;

	@Autowired
	private SupervisoraPerfilService supervisoraPerfilService;

	@Autowired
	private SupervisoraService supervisoraService;

	@Autowired
	private ProcesoItemPerfilService procesoItemPerfilService;

	@Autowired
	private Environment env;
	
	@Autowired
	MessageSource messageSource;

	@Autowired
	private PacesDao pacesDao;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Proceso guardar(Proceso proceso, Contexto contexto) {
		Proceso procesoBD = null;

		if (proceso.getProcesoUuid() == null) {
			if (proceso.getNumeroProceso() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.NUMERO_PROCESO_NO_ENVIADO);
			}
			if (proceso.getNombreProceso() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.NOMBRE_PROCESO_NO_ENVIADO);
			}
			if (proceso.getNumeroExpediente() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.NUMERO_EXPEDIENTE_NO_ENVIADO);
			}
			if (proceso.getCodigoArea() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.AREA_NO_ENVIADA);
			}
			if (proceso.getNombreArea() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.AREA_NO_ENVIADA);
			}
			if (proceso.getSector() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.P_SECTOR_NO_ENVIADO);
			}
			if (proceso.getSubsector() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.P_SUBSECTOR_NO_ENVIADO);
			}
			List<Proceso> procesos = procesoDao.obtenerProcesosNombre(proceso.getNombreProceso().trim());
			if (!procesos.isEmpty()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.NOMBRE_PROCESO_EXISTE);
			}
			procesos = procesoDao.obtenerProcesosNumero(proceso.getNumeroProceso().trim());
			if (!procesos.isEmpty()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.NUMERO_PROCESO_EXISTE);
			}
			
//			if (proceso.getTipoFacturacion() == null) {
//				throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_FACTURACION_NO_ENVIADO);
//			}
			
			procesoBD = proceso;
			ListadoDetalle estadoElaboracion = listadoDetalleService.obtenerListadoDetalle(
					Constantes.LISTADO.ESTADO_PROCESO.CODIGO, Constantes.LISTADO.ESTADO_PROCESO.EN_ELABORACION);
			procesoBD.setEstado(estadoElaboracion);
			if (procesoBD.getProcesoUuid() == null) {
				procesoBD.setProcesoUuid(UUID.randomUUID().toString());
			}
			procesoBD.setUsuario(contexto.getUsuario());
			procesoBD.setUsuarioCreador(contexto.getUsuario());
					
			AuditoriaUtil.setAuditoriaRegistro(procesoBD,contexto);
			procesoBD.setIdProceso( procesoDao.save(procesoBD).getIdProceso());
			
			if(pacesDao.findById(proceso.getIdPace()).isPresent())				
			{
				Paces opcionBD=pacesDao.findById(proceso.getIdPace()).get();				
				opcionBD.setIdProceso(procesoBD.getIdProceso());												
				AuditoriaUtil.setAuditoriaActualizacion(opcionBD, contexto);
				pacesDao.save(opcionBD);											
			}
			
		} else {
			procesoBD = procesoDao.obtener(proceso.getProcesoUuid());
			ListadoDetalle estadoProceso = listadoDetalleService.obtener(proceso.getEstado().getIdListadoDetalle(),
					contexto);
			if (!(Constantes.LISTADO.ESTADO_PROCESO.EN_ELABORACION.equals(procesoBD.getEstado().getCodigo()))) {
				if (!estadoProceso.getCodigo().equals(procesoBD.getEstado().getCodigo())) {
					if (!estadoProceso.getCodigo().equals(Constantes.LISTADO.ESTADO_PROCESO.DESIGNACION)
							&& !estadoProceso.getCodigo().equals(Constantes.LISTADO.ESTADO_PROCESO.CERRADO)) {
						throw new ValidacionException(Constantes.CODIGO_MENSAJE.NO_CAMBIAR_ESTADO);
					}
				}
			}
			if (Constantes.LISTADO.ESTADO_PROCESO.EN_ELABORACION.equals(procesoBD.getEstado().getCodigo())) {
				procesoBD.setNumeroProceso(proceso.getNumeroProceso());
				procesoBD.setNombreProceso(proceso.getNombreProceso());
				procesoBD.setNumeroExpediente(proceso.getNumeroExpediente());
				procesoBD.setSector(proceso.getSector());
				procesoBD.setSubsector(proceso.getSubsector());
				procesoBD.setCodigoArea(proceso.getCodigoArea());
				procesoBD.setTipoFacturacion(proceso.getTipoFacturacion());
				procesoBD.setNombreArea(proceso.getNombreArea());
				procesoBD.setFechaPublicacion(proceso.getFechaPublicacion());
			}
			if(!proceso.getEstado().getCodigo().equals(procesoBD.getEstado().getCodigo())) {
				if (Constantes.LISTADO.ESTADO_PROCESO.DESIGNACION.equals(proceso.getEstado().getCodigo())
						|| Constantes.LISTADO.ESTADO_PROCESO.CERRADO.equals(proceso.getEstado().getCodigo())) {
					procesoBD.setEstado(estadoProceso);
				}
			}
			AuditoriaUtil.setAuditoriaRegistro(procesoBD,contexto);
			procesoBD.setIdProceso( procesoDao.save(procesoBD).getIdProceso());
		}
		/*AuditoriaUtil.setAuditoriaRegistro(procesoBD,contexto);
		procesoBD.setIdProceso( procesoDao.save(procesoBD).getIdProceso());*/
												
		return procesoBD;
	}

	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Proceso publicar(String procesoUuid, Proceso proceso, Contexto contexto) {

		Proceso procesoBD = procesoDao.obtener(procesoUuid);
		ProcesoMiembro presidente = procesoMiembroService.obtenerXtipo(procesoUuid,
				Constantes.LISTADO.CARGO_MIEMBRO.C_PRESIDENTE, contexto);
		ProcesoMiembro primerMiembro = procesoMiembroService.obtenerXtipo(procesoUuid,
				Constantes.LISTADO.CARGO_MIEMBRO.C_PRIMER, contexto);
		ProcesoMiembro miembro3 = procesoMiembroService.obtenerXtipo(procesoUuid,
				Constantes.LISTADO.CARGO_MIEMBRO.C_MIEMBRO, contexto);
		
		List<ProcesoEtapa> etapas = procesoEtapaService.listar(procesoUuid, contexto);

		ProcesoEtapa presentacion = null;
		Boolean existeEtapa = false;
		for (ProcesoEtapa etapa : etapas) {
			if (Constantes.LISTADO.ETAPA_PROCESO.ETAPA_PRESENTADO.equals(etapa.getEtapa().getCodigo())) {
				existeEtapa = true;
				presentacion=etapa;
			}
		}

		if (!existeEtapa) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.E_PRESENTACION_NO_AGREGADA);
		}
		if (DateUtil.esMayorIgual(new Date(),presentacion.getFechaFin())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.FECHA_ETAPA_FIN);
		}
		if (DateUtil.esMayorIgual(new Date(),presentacion.getFechaInicio())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.FECHA_ETAPA_INICIO);
		}

		if (presidente == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.REGISTRAR_MIEMBROS);
		}
		if (primerMiembro == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.REGISTRAR_MIEMBROS);
		}
		if (miembro3 == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.REGISTRAR_MIEMBROS);
		}

		List<ProcesoItem> items = procesoItemService.listar(procesoUuid, contexto);

		if (items.isEmpty()) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.MINIMO_ITEM_PROCESO);
		}
		Boolean existeItemMal = false;

		List<ProcesoItemPerfil> listPerfiles = procesoItemPerfilService
				.buscar(procesoBD.getIdProceso());

		for (ProcesoItemPerfil perf : listPerfiles) {
			if (!procesoBD.getSubsector().getCodigo().equals(perf.getSubsector().getCodigo())) {
				existeItemMal = true;
			}
		}

		if (existeItemMal) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ITEM_DIFERENTE_SUBSECTOR);
		}

		Usuario usuario = contexto.getUsuario();
		// FIXME: EL PRESIDENTE Y PRIMER MIEMBRO TIENE QUE SER CONFIGURABLES

		if (!((presidente.getCodigoUsuario().equals(usuario.getCodigoUsuarioInterno()))
				|| (primerMiembro.getCodigoUsuario().equals(usuario.getCodigoUsuarioInterno()))
				|| (miembro3.getCodigoUsuario().equals(usuario.getCodigoUsuarioInterno())))) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.NO_ERES_M_PRESIDENTE);
		}

		ListadoDetalle convocatoriaItem = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.ESTADO_ITEM.CODIGO, Constantes.LISTADO.ESTADO_ITEM.CONVOCATORIA);

		for (ProcesoItem item : items) {
			item.setEstado(convocatoriaItem);
		}

		ListadoDetalle estadoConvocatoria = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.ESTADO_PROCESO.CODIGO, Constantes.LISTADO.ESTADO_PROCESO.CONVOCATORIA);
		procesoBD.setEstado(estadoConvocatoria);
		procesoBD.setFechaPublicacion(new Date());
		procesoBD.setUsuario(null);
		AuditoriaUtil.setAuditoriaRegistro(procesoBD,contexto);
		procesoDao.save(procesoBD);
		return procesoBD;
	}

	@Override
	public Proceso obtener(String uuid, Contexto contexto) {
		Pageable pageable = PageRequest.of(0, Integer.parseInt(env.getProperty("maximo.paginas")));
	    Proceso proceso = procesoDao.obtener(uuid);
	    List<ProcesoEtapa> procesoEtapas = procesoEtapaService.obtenerProcesosEtapa(proceso.getIdProceso(), contexto);
		//ProcesoEtapa procesoEtapa = procesoEtapaService.obtener(proceso.getIdProceso(), contexto);
	    Page<ProcesoItem> listProcesoItem = procesoItemService.listarItems(proceso.getProcesoUuid(), pageable, contexto);
	    Page<ProcesoDocumento> listProcesoDocumento = procesoDocumentoService.listarDocumentos(proceso.getIdProceso(), pageable, contexto);
	    
		ProcesoMiembro presidente = procesoMiembroService.obtenerXtipo(uuid,
				Constantes.LISTADO.CARGO_MIEMBRO.C_PRESIDENTE, contexto);
		ProcesoMiembro primerMiembro = procesoMiembroService.obtenerXtipo(uuid,
				Constantes.LISTADO.CARGO_MIEMBRO.C_PRIMER, contexto);
		ProcesoMiembro miembro3 = procesoMiembroService.obtenerXtipo(uuid, Constantes.LISTADO.CARGO_MIEMBRO.C_MIEMBRO,
				contexto);
		if ((presidente != null) && (primerMiembro != null) && (miembro3 != null)) {
	        proceso.setMiembros(true);
	    } else {
	        proceso.setMiembros(false);
	    }

	    proceso.setDatosGenerales(true);
	    proceso.setEtapa(procesoEtapas != null && !procesoEtapas.isEmpty());
	    //proceso.setEtapa(procesoEtapa != null);
	    proceso.setItems(listProcesoItem != null && !listProcesoItem.isEmpty());
	    proceso.setInformacion(listProcesoDocumento != null && !listProcesoDocumento.isEmpty());
	    return proceso;
	}
	

	@Override
	public void eliminar(Long idProceso, Contexto contexto) {
		procesoDao.deleteById(idProceso);
	}

	@Override
	public Page<Proceso> listarProcesos(String sFechaDesde, String sFechaHasta, Long idEstado, Long idSector,
			Long idSubsector, String nroProceso, String nombreProceso, String nroExpediente, Pageable pageable,
			Contexto contexto) {
		Date fechaDesde = DateUtil.getEndDay(sFechaDesde);
		Date fechaHasta = DateUtil.getEndDay(sFechaHasta);
		Long codigoUsuario = contexto.getUsuario().getCodigoUsuarioInterno();
		Page<Proceso> page = procesoDao.buscar(fechaDesde, fechaHasta, idEstado, idSector, idSubsector, nroProceso,
				nombreProceso, nroExpediente, codigoUsuario, pageable);
		for (Proceso proceso : page.getContent()) {
			ProcesoMiembro procesoMiembro = procesoMiembroService.obtenerXUsuario(proceso.getProcesoUuid(),
					codigoUsuario, contexto);
			// FIXME: EL PRESIDENTE Y PRIMER MIEMBRO TIENE QUE SER CONFIGURABLES
			if (procesoMiembro != null && (Constantes.LISTADO.CARGO_MIEMBRO.C_PRESIDENTE
					.equals(procesoMiembro.getCargo().getCodigo())
					|| Constantes.LISTADO.CARGO_MIEMBRO.C_PRIMER.equals(procesoMiembro.getCargo().getCodigo())
					|| Constantes.LISTADO.CARGO_MIEMBRO.C_MIEMBRO.equals(procesoMiembro.getCargo().getCodigo()))) {
				proceso.setEditar(true);
			} else {
				proceso.setEditar(false);
			}

			if (Constantes.LISTADO.ESTADO_PROCESO.EN_ELABORACION.equals(proceso.getEstado().getCodigo())) {
				if (proceso.getUsuario() != null
						&& proceso.getUsuario().getIdUsuario() == contexto.getUsuario().getIdUsuario()) {
					proceso.setEditar(true);
				}
			}

			if (!(Constantes.LISTADO.ESTADO_PROCESO.EN_ELABORACION.equals(proceso.getEstado().getCodigo())
					|| Constantes.LISTADO.ESTADO_PROCESO.CONVOCATORIA.equals(proceso.getEstado().getCodigo())
					|| Constantes.LISTADO.ESTADO_PROCESO.PRESENTACION.equals(proceso.getEstado().getCodigo()))) {
				proceso.setVerPostulante(true);
			} else {
				proceso.setVerPostulante(false);
			}

		}
		return page;
	}

	@Override
	public Proceso obtener(Long id, Contexto contexto) {
		return procesoDao.obtener(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public void actualizarProcesoPresentacion(Contexto contexto) {
		logger.info("actualizarProcesoPresentacion Inicio");
		List<Proceso> procesos = procesoDao.obtenerProcesoPresentacion();
		ListadoDetalle estadoPresentacion = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.ESTADO_PROCESO.CODIGO, Constantes.LISTADO.ESTADO_PROCESO.PRESENTACION);
		for (Proceso proceso : procesos) {
			Proceso procesoBD = procesoDao.obtener(proceso.getIdProceso());
			procesoBD.setEstado(estadoPresentacion);
			AuditoriaUtil.setAuditoriaRegistro(proceso, contexto);
			procesoBD = procesoDao.save(procesoBD);
			procesoItemService.actualizarProcesoPresentacion(procesoBD, contexto);
		}
		logger.info("actualizarProcesoPresentacion Fin");
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void actualizarProcesoAdmision(Contexto contexto) {
		logger.info("actualizarProcesoAdmision Inicio");
		List<Proceso> procesos = procesoDao.obtenerProcesoAdmision();
		ListadoDetalle estadoAdmision = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.ESTADO_PROCESO.CODIGO, Constantes.LISTADO.ESTADO_PROCESO.ADMISION_CALIFICACION);
		for (Proceso proceso : procesos) {
			Proceso procesoBD = procesoDao.obtener(proceso.getIdProceso());
			procesoBD.setEstado(estadoAdmision);
			AuditoriaUtil.setAuditoriaRegistro(proceso, contexto);
			procesoBD = procesoDao.save(procesoBD);
			procesoItemService.actualizarProcesoAdmision(procesoBD, contexto);
		}
		logger.info("actualizarProcesoAdmision Fin");
	}

	@Override
	public void validacionVerProfesionales(String procesoUuid, Contexto contexto) {
		if(procesoUuid == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROCESO_UUID_NO_ENVIADO);
		}
		Proceso procesoBD = procesoDao.obtener(procesoUuid);
		if(procesoBD == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROCESO_UUID_NO_ENVIADO);
		}
		
		Supervisora supervisora = supervisoraService
				.obtenerSupervisoraXRUCNoProfesional(contexto.getUsuario().getCodigoRuc());
		if (supervisora == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_NO_ES_SUPERVISORA);
		}
		List<SupervisoraPerfil> listSupervisoraPerfil = supervisoraPerfilService.buscar(supervisora.getIdSupervisora(),
				procesoBD.getSector().getIdListadoDetalle(), procesoBD.getSubsector().getIdListadoDetalle());
		if (listSupervisoraPerfil == null || listSupervisoraPerfil.isEmpty()||!(Constantes.LISTADO.TIPO_PERSONA.PN_POSTOR.equals(supervisora.getTipoPersona().getCodigo())||
				Constantes.LISTADO.TIPO_PERSONA.JURIDICA.equals(supervisora.getTipoPersona().getCodigo()))) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_NO_ES_SUPERVISORA);
		}

	}

	@Override
	public List<String> validacionXUsuario(String procesoUuid, String procesoItemUuid, Contexto contexto) {
		if(procesoUuid == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROCESO_UUID_NO_ENVIADO);
		}
		List<String> validaciones = new ArrayList<>();
		ProcesoItem procesoItemDB = procesoItemDao.obtener(procesoItemUuid);
		Proceso procesoDB = procesoDao.obtener(procesoUuid);
		if(procesoDB == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROCESO_UUID_NO_ENVIADO);
		}
		
		if (!Constantes.LISTADO.ESTADO_PROCESO.PRESENTACION.equals(procesoItemDB.getEstado().getCodigo()) && 
				!Constantes.LISTADO.ESTADO_PROCESO.CONVOCATORIA.equals(procesoItemDB.getEstado().getCodigo())) {
			validaciones.add(messageSource.getMessage(Constantes.CODIGO_MENSAJE.PROCESO_ITEM_PRESENTACION, new Object[0], Constantes.BITACORA.COD_MENSAJE.MENSAJE_DEFECTO, Locale.getDefault()));
		}
		
		Supervisora supervisora = supervisoraService
				.obtenerSupervisoraXRUCNoProfesional(contexto.getUsuario().getCodigoRuc());
		List<SupervisoraPerfil> listSupervisoraPerfil = null;
		if(supervisora != null ) {
			listSupervisoraPerfil = supervisoraPerfilService.buscar(supervisora.getIdSupervisora(),
					procesoDB.getSector().getIdListadoDetalle(), procesoDB.getSubsector().getIdListadoDetalle());
		}
		if (supervisora == null || listSupervisoraPerfil == null || listSupervisoraPerfil.isEmpty()||!(Constantes.LISTADO.TIPO_PERSONA.PN_POSTOR.equals(supervisora.getTipoPersona().getCodigo())||
				Constantes.LISTADO.TIPO_PERSONA.JURIDICA.equals(supervisora.getTipoPersona().getCodigo()))) {
			validaciones.add(messageSource.getMessage(Constantes.CODIGO_MENSAJE.USUARIO_NO_ES_SUPERVISORA, new Object[0], Constantes.BITACORA.COD_MENSAJE.MENSAJE_DEFECTO, Locale.getDefault()));
		}
			
		return validaciones;
	}


	@Override
	public Page<Proceso> listarProcesosSeleccion(Long idEstado, String nombreArea, String nombreProceso,
			Pageable pageable, Contexto contexto) {
		return this.procesoDao.buscar(idEstado, nombreArea, nombreProceso, pageable);
	}


	@Override
	public Proceso obtenerPublico(String uuid, Contexto contexto) {
		Pageable pageable = PageRequest.of(0, Integer.parseInt(env.getProperty("maximo.paginas")));
	    Proceso proceso = procesoDao.obtener(uuid);
	    List<ProcesoEtapa> procesoEtapas = procesoEtapaService.obtenerProcesosEtapa(proceso.getIdProceso(), contexto);
	    Page<ProcesoItem> listProcesoItem = procesoItemService.listarItems(proceso.getProcesoUuid(), pageable, contexto);


//		ProcesoMiembro presidente = procesoMiembroService.obtenerXtipo(uuid,
//				Constantes.LISTADO.CARGO_MIEMBRO.C_PRESIDENTE, contexto);
//		ProcesoMiembro primerMiembro = procesoMiembroService.obtenerXtipo(uuid,
//				Constantes.LISTADO.CARGO_MIEMBRO.C_PRIMER, contexto);
//		ProcesoMiembro miembro3 = procesoMiembroService.obtenerXtipo(uuid, Constantes.LISTADO.CARGO_MIEMBRO.C_MIEMBRO,
//				contexto);
//		if ((presidente != null) && (primerMiembro != null) && (miembro3 != null)) {
//	        proceso.setMiembros(true);
//	    } else {
	        proceso.setMiembros(false);
//	    }

	    proceso.setDatosGenerales(true);
	    proceso.setEtapa(procesoEtapas != null && !procesoEtapas.isEmpty());
	    proceso.setItems(listProcesoItem != null && !listProcesoItem.isEmpty());
	    return proceso;
	}



}
