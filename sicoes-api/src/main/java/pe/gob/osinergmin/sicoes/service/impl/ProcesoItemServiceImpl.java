package pe.gob.osinergmin.sicoes.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.ItemEstado;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Proceso;
import pe.gob.osinergmin.sicoes.model.ProcesoEtapa;
import pe.gob.osinergmin.sicoes.model.ProcesoItem;
import pe.gob.osinergmin.sicoes.model.ProcesoItemPerfil;
import pe.gob.osinergmin.sicoes.model.ProcesoMiembro;
import pe.gob.osinergmin.sicoes.model.Propuesta;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.repository.ItemEstadoDao;
import pe.gob.osinergmin.sicoes.repository.ProcesoDao;
import pe.gob.osinergmin.sicoes.repository.ProcesoItemDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.ProcesoEtapaService;
import pe.gob.osinergmin.sicoes.service.ProcesoItemPerfilService;
import pe.gob.osinergmin.sicoes.service.ProcesoItemService;
import pe.gob.osinergmin.sicoes.service.ProcesoMiembroService;
import pe.gob.osinergmin.sicoes.service.PropuestaService;
import pe.gob.osinergmin.sicoes.service.SupervisoraPerfilService;
import pe.gob.osinergmin.sicoes.service.SupervisoraService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.ZipUtil;

@Service
public class ProcesoItemServiceImpl implements ProcesoItemService {

	Logger logger = LogManager.getLogger(ProcesoItemServiceImpl.class);

	@Autowired
	private ProcesoItemDao procesoItemDao;

	@Autowired
	private ListadoDetalleService listadoDetalleService;

	@Autowired
	private ProcesoEtapaService procesoEtapaService;

	@Autowired
	PropuestaService propuestaService;

	@Autowired
	ProcesoDao procesoDao;

	@Autowired
	ProcesoItemPerfilService procesoItemPerfilService;

	@Autowired
	ArchivoService archivoService;
	
	@Autowired
	SupervisoraService supervisoraService;
	
	@Autowired
	SupervisoraPerfilService supervisoraPerfilService;
    @Autowired
    private ProcesoMiembroService procesoMiembroService;
    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;
    @Autowired
    private NotificacionService notificacionService;
    @Autowired
    private ItemEstadoDao itemEstadoDao;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void eliminar(String uuid, Contexto contexto) {
		ProcesoItem procesoItemBD = procesoItemDao.obtener(uuid);
		Proceso procesoBD = procesoDao.obtener(procesoItemBD.getProceso().getProcesoUuid());
		ListadoDetalle estadoElaboracion = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.ESTADO_PROCESO.CODIGO, Constantes.LISTADO.ESTADO_PROCESO.EN_ELABORACION);
		if (!estadoElaboracion.getCodigo().equals(procesoBD.getEstado().getCodigo())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.EDITAR_ESTADO_ELABORACION);
		}
		List<ProcesoItemPerfil> perfiles = procesoItemPerfilService.listar(uuid, contexto);
		for (ProcesoItemPerfil perfil : perfiles) {
			procesoItemPerfilService.eliminar(perfil.getIdProcesoItemPerfil(), contexto);
		}
		List<ProcesoItemPerfil> perf = procesoItemPerfilService.listar(uuid, contexto);
		if (perf.size() == 0) {
			procesoItemDao.eliminarItem(uuid);
		}
	}

	@Override
	public Page<ProcesoItem> listarItems(String uuidProceso, Pageable pageable, Contexto contexto) {
		Page<ProcesoItem> page = procesoItemDao.buscar(uuidProceso, pageable);
		for (ProcesoItem item : page.getContent()) {
			long nroSuma = 0;
			List<ProcesoItemPerfil> perfiles = procesoItemPerfilService.listar(item.getProcesoItemUuid(), contexto);
			for (ProcesoItemPerfil perfil : perfiles) {
				nroSuma = nroSuma + perfil.getNroProfesionales();
			}
			item.setNroProfesionales(nroSuma);
		}

		return page;
	}

	@Override
	public Page<ProcesoItem> listarProcesos(String sFechaDesde, String sFechaHasta, Long idEstadoItem, Long idSector,
			Long idSubSector, String nroProceso, String nombreProceso, String descripcionItem, String nroExpediente,
			Long idEstadoProceso, Pageable pageable, Contexto contexto) {
		Date fechaDesde = DateUtil.getInitDay(sFechaDesde);
		Date fechaHasta = DateUtil.getEndDay(sFechaHasta);
		Page<ProcesoItem> page = procesoItemDao.buscarProcesos(fechaDesde, fechaHasta, idEstadoItem, idSector,
				idSubSector, nroProceso, nombreProceso, descripcionItem, nroExpediente, idEstadoProceso, pageable);

		for (ProcesoItem item : page.getContent()) {
			Propuesta propuesta = propuestaService.obtenerPropuestaXProcesoItem(item.getIdProcesoItem(), contexto);
			item.setPropuesta(propuesta);
		}
		return page;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ProcesoItem guardar(ProcesoItem item, Contexto contexto) {
		ProcesoItem itemBD = null;
		if (item.getDescripcionItem() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.DESCRIPCION_NO_ENVIADO);
		}
		if (item.getNumeroItem() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.NUMERO_ITEM_NO_ENVIADO);
		}
		if (item.getDivisa() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.DIVISA_NO_ENVIADO);
		}
		if (item.getMontoReferencial() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.MONTO_NO_ENVIADO);
		}
		ListadoDetalle dolares = listadoDetalleService.obtener(item.getDivisa().getIdListadoDetalle(), contexto);
		if (dolares.getCodigo().equals(Constantes.LISTADO.TIPO_CAMBIO.DOLARES)) {
			if (item.getMontoTipoCambio() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.MONTO_TIPO_CAMBIO_NO_ENVIADO);
			}
		}

		if (item.getProcesoItemUuid() == null) {
			if (item.getListProcesoItemPerfil().isEmpty()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.AGREGAR_PERFIL);
			}
			List<ProcesoItem> listProcesoItem = procesoItemDao.obtenerProcesoNumero(item.getNumeroItem(),
					item.getProceso().getIdProceso(), item.getIdProcesoItem());
			if (!listProcesoItem.isEmpty()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.NRO_PROCESO_ITEM_EXISTE);
			}
			itemBD = item;
			ListadoDetalle elaboracion = listadoDetalleService.obtenerListadoDetalle(
					Constantes.LISTADO.ESTADO_ITEM.CODIGO, Constantes.LISTADO.ESTADO_ITEM.EN_ELABORACION);
			itemBD.setEstado(elaboracion);
			if (itemBD.getProcesoItemUuid() == null) {
				itemBD.setProcesoItemUuid(UUID.randomUUID().toString());
			}

			AuditoriaUtil.setAuditoriaRegistro(itemBD,contexto);
			itemBD = procesoItemDao.save(itemBD);
			HashMap<String, ProcesoItemPerfil> perfilesMap = new HashMap<String, ProcesoItemPerfil>();
			for (ProcesoItemPerfil procesoItemPerfil : item.getListProcesoItemPerfil()) {
				perfilesMap.put(procesoItemPerfil.getPerfil().getCodigo(), procesoItemPerfil);
			}

			Collection<ProcesoItemPerfil> values = perfilesMap.values();
			ArrayList<ProcesoItemPerfil> listOfValues = new ArrayList<>(values);

			for (ProcesoItemPerfil perfil : listOfValues) {
				perfil.setProcesoItem(itemBD);
				procesoItemPerfilService.guardar(perfil, contexto);
			}

		} else {
			itemBD = procesoItemDao.obtener(item.getProcesoItemUuid());
			if (!Constantes.LISTADO.ESTADO_PROCESO.EN_ELABORACION.equals(itemBD.getProceso().getEstado().getCodigo())) {
				itemBD.setEstado(item.getEstado());
			}
			if (Constantes.LISTADO.ESTADO_PROCESO.EN_ELABORACION.equals(itemBD.getProceso().getEstado().getCodigo())) {
				itemBD.setDivisa(item.getDivisa());
				itemBD.setFacturacionMinima(item.getFacturacionMinima());
				itemBD.setDescripcionItem(item.getDescripcionItem());
				itemBD.setNumeroItem(item.getNumeroItem());
				itemBD.setMontoTipoCambio(item.getMontoTipoCambio());
				itemBD.setMontoReferencial(item.getMontoReferencial());
				itemBD.setMontoReferencialSoles(item.getMontoReferencialSoles());
				HashMap<String, ProcesoItemPerfil> perfilesMap = new HashMap<String, ProcesoItemPerfil>();
				for (ProcesoItemPerfil procesoItemPerfil : item.getListProcesoItemPerfil()) {
					perfilesMap.put(procesoItemPerfil.getPerfil().getCodigo(), procesoItemPerfil);
				}
				Collection<ProcesoItemPerfil> values = perfilesMap.values();
				ArrayList<ProcesoItemPerfil> listOfValues = new ArrayList<>(values);
				List<ProcesoItemPerfil> perfilesExistentes = procesoItemPerfilService.listar(item.getProcesoItemUuid(),
						contexto);
				List<ProcesoItemPerfil> perfiles = listOfValues;
				List<ProcesoItemPerfil> listRevisar = new ArrayList<>();
				for (ProcesoItemPerfil perfil : perfiles) {
					if (perfil.getIdProcesoItemPerfil() == null) {
						perfil.setProcesoItem(itemBD);
						procesoItemPerfilService.guardar(perfil, contexto);
					} else {
						perfil.setProcesoItem(itemBD);
						listRevisar.add(perfil);
					}
				}

				List<String> soloIDRevisar = new ArrayList<>();
				List<String> soloIDExistente = new ArrayList<>();

				for (ProcesoItemPerfil per : listRevisar) {
					soloIDRevisar.add(per.getIdProcesoItemPerfil().toString());
				}
				for (ProcesoItemPerfil per : perfilesExistentes) {
					soloIDExistente.add(per.getIdProcesoItemPerfil().toString());
				}

				for (String e : soloIDExistente) {
					if (!(soloIDRevisar.contains(e))) {
						procesoItemPerfilService.eliminarPerfil(Long.parseLong(e), item.getProcesoItemUuid(), contexto);
					}
				}
			}

			AuditoriaUtil.setAuditoriaRegistro(itemBD,contexto);
			itemBD = procesoItemDao.save(itemBD);
		}

		return itemBD;
	}

	@Override
	public ProcesoItem obtener(String procesoItemUuid, Contexto contexto) {
		ProcesoItem procesoItemBD = procesoItemDao.obtener(procesoItemUuid);
		ProcesoEtapa etapa = procesoEtapaService.obtener(procesoItemBD.getProceso().getIdProceso(), contexto);
		procesoItemBD.setEtapa(etapa);

		Propuesta propuesta = propuestaService.obtenerPropuestaXProcesoItem(procesoItemBD.getIdProcesoItem(), contexto);
		List<ProcesoItemPerfil> perfiles = procesoItemPerfilService.listar(procesoItemUuid, contexto);
		if (propuesta != null) {
			procesoItemBD.setPropuesta(propuesta);
		}
		procesoItemBD.setListProcesoItemPerfil(perfiles);
		return procesoItemBD;
	}

	@Override
	public ProcesoItem obtener(Long id, Contexto contexto) {
		return procesoItemDao.obtener(id);
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ProcesoItem> listar(String procesoUuid, Contexto contexto) {
		return procesoItemDao.buscar(procesoUuid);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void actualizarProcesoPresentacion(Proceso procesoBD, Contexto contexto) {
		List<ProcesoItem> list = listar(procesoBD.getProcesoUuid(), contexto);
		ListadoDetalle estadoPresentacion = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.ESTADO_ITEM.CODIGO, Constantes.LISTADO.ESTADO_ITEM.PRESENTACION);
		for (ProcesoItem procesoItem : list) {
			ProcesoItem procesoItemBD = procesoItemDao.obtener(procesoItem.getIdProcesoItem());
			procesoItemBD.setEstado(estadoPresentacion);
			AuditoriaUtil.setAuditoriaRegistro(procesoItemBD, contexto);
			procesoItemBD = procesoItemDao.save(procesoItemBD);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void actualizarProcesoAdmision(Proceso procesoBD, Contexto contexto) {
		List<ProcesoItem> list = listar(procesoBD.getProcesoUuid(), contexto);
		ListadoDetalle estadoPresentacion = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.ESTADO_ITEM.CODIGO, Constantes.LISTADO.ESTADO_ITEM.ADMISION_CALIFICACION);
		for (ProcesoItem procesoItem : list) {
			ProcesoItem procesoItemBD = procesoItemDao.obtener(procesoItem.getIdProcesoItem());
			procesoItemBD.setEstado(estadoPresentacion);
			AuditoriaUtil.setAuditoriaRegistro(procesoItemBD, contexto);
			procesoItemBD = procesoItemDao.save(procesoItemBD);
		}

	}

	@Override
	public void generarArchivoDescarga(ProcesoItem procesoItem, Contexto contexto) {
		try {
			ProcesoItem procesoItemBD = procesoItemDao.obtener(procesoItem.getProcesoItemUuid());
			procesoItemBD.setDecripcionDescarga("Descargando Archivos");
			procesoItemDao.save(procesoItemBD);
			String path = archivoService.generarArchivoContenido(procesoItemBD,contexto);
			logger.info("path zip "+path);
			String pathZip = path + ".zip";
			procesoItemBD.setDecripcionDescarga("Generando Zip");
			procesoItemDao.save(procesoItemBD);
			ZipUtil.zipearCarpeta(path, pathZip);
			procesoItemBD.setRutaDescarga(pathZip);
			procesoItemBD.setDecripcionDescarga("Descargar");
			procesoItemDao.save(procesoItemBD);
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
			throw new ValidacionException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void actualizarProcesoItemDesignacion(Proceso procesoBD, Contexto contexto) {
		List<ProcesoItem> items = listar(procesoBD.getProcesoUuid(), contexto);

		boolean itemsConMultiplesPropuestas = false;

		for (ProcesoItem procesoItem : items) {
			logger.info("Inicio proceso item designacion: " + procesoItem.getIdProcesoItem());
			List<Propuesta> propuestasPresentadas = propuestaService.buscarPropuestasXItem(procesoItem.getProcesoItemUuid(), contexto)
					.stream()
					.filter(propuesta -> propuesta.getEstado() != null &&
							propuesta.getEstado().getCodigo().equals(Constantes.LISTADO.ESTADO_PRESENTACION.PRESENTADO))
					.collect(Collectors.toList());
			logger.info("Cantidad de propuestas presentadas: " + propuestasPresentadas.size());
			if (propuestasPresentadas.isEmpty()) {
				logger.info("Item " + procesoItem.getIdProcesoItem() + " marcado como DESIERTO - No se presentaron propuestas");
				ListadoDetalle estadoDesierto = listadoDetalleService.obtenerListadoDetalle(
						Constantes.LISTADO.ESTADO_ITEM.CODIGO, Constantes.LISTADO.ESTADO_ITEM.DESIERTO);
				String descripcion = "No se presentaron propuestas";
				guardarEstadoItem(procesoItem, estadoDesierto, descripcion, null, contexto);
				procesoItem.setEstado(estadoDesierto);
				AuditoriaUtil.setAuditoriaRegistro(procesoItem, contexto);
				procesoItemDao.save(procesoItem);
			} else if (propuestasPresentadas.size() > 1) {
				logger.info("Item " + procesoItem.getIdProcesoItem() + " con múltiples propuestas - requiere notificación");
				if (!itemsConMultiplesPropuestas)
					itemsConMultiplesPropuestas = true;

				if (!procesoItem.getEstado().getCodigo().equals(Constantes.LISTADO.ESTADO_ITEM.NULO)) {
					actualizarProceso(procesoItem, contexto);
				}
			} else {
				logger.info("Item " + procesoItem.getIdProcesoItem() + " con una propuesta - seleccionando ganador automáticamente");
				actualizarProceso(procesoItem, contexto);
				propuestaService.seleccionar(propuestasPresentadas.get(0).getPropuestaUuid(), propuestasPresentadas.get(0), contexto);
			}
			logger.info("Fin proceso item designacion: " + procesoItem.getIdProcesoItem());
		}

		if (itemsConMultiplesPropuestas)
			enviarNotificacionMultiplesPropuestas(items, procesoBD, contexto);

	}

	@Override
	public void actualizarProcesoItemConsentido(Proceso procesoBD, Contexto contexto, String postulantes) {
		logger.info("Iniciando actualización de items a estado consentido para proceso: " + procesoBD.getIdProceso() + ", tipo postulantes: " + postulantes);
		List<ProcesoItem> items = listar(procesoBD.getProcesoUuid(), contexto);
		logger.info("Total de items a procesar: " + items.size());
		
		ListadoDetalle estadoConsentido = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.ESTADO_ITEM.CODIGO, Constantes.LISTADO.ESTADO_ITEM.CONSENTIDO);
		
		int itemsConsentidos = 0;
		for (ProcesoItem procesoItem : items) {
			logger.info("Procesando item de consentimiento: " + procesoItem.getIdProcesoItem() + ", estado actual: " + procesoItem.getEstado().getCodigo());
			
			if (!Constantes.LISTADO.ESTADO_ITEM.NULO.equals(procesoItem.getEstado().getCodigo())
				&& Constantes.LISTADO.ESTADO_ITEM.DESIGNACION.equals(procesoItem.getEstado().getCodigo())) {
				
				List<Propuesta> propuestas = propuestaService.buscarPropuestasXItem(procesoItem.getProcesoItemUuid(), contexto)
						.stream()
						.filter(propuesta -> propuesta.getEstado() != null
								&& propuesta.getEstado().getCodigo().equals(Constantes.LISTADO.ESTADO_PRESENTACION.PRESENTADO))
						.collect(Collectors.toList());

				List<Propuesta> propuestaGanadora = propuestaService.buscarPropuestasXItem(procesoItem.getProcesoItemUuid(), contexto)
						.stream()
						.filter(propuesta -> propuesta.getGanador() != null
								&& propuesta.getGanador().getCodigo().equals(Constantes.LISTADO.SI_NO.SI))
						.collect(Collectors.toList());

				logger.info("Item " + procesoItem.getIdProcesoItem() + " - Propuestas presentadas: " + propuestas.size() + ", Propuestas ganadoras: " + propuestaGanadora.size());

				if (propuestas.size() == 1 && propuestaGanadora.size() == 1 && postulantes.equals("U")) {
					logger.info("Consentimiento automático para item " + procesoItem.getIdProcesoItem() + " (un postulante)");
					procesoItem.setEstado(estadoConsentido);
					String descripcion = "Se consiente ganador";
					guardarEstadoItem(procesoItem, estadoConsentido, descripcion, null, contexto);
					AuditoriaUtil.setAuditoriaRegistro(procesoItem, contexto);
					procesoItemDao.save(procesoItem);
					itemsConsentidos++;
				}

				if (propuestas.size() > 1 && propuestaGanadora.size() == 1 && postulantes.equals("M")) {
					logger.info("Consentimiento para item " + procesoItem.getIdProcesoItem() + " (múltiples postulantes)");
					procesoItem.setEstado(estadoConsentido);
					String descripcion = "Se consiente ganador";
					guardarEstadoItem(procesoItem, estadoConsentido, descripcion, null, contexto);
					AuditoriaUtil.setAuditoriaRegistro(procesoItem, contexto);
					procesoItemDao.save(procesoItem);
					itemsConsentidos++;
				}
			} else {
				logger.info("Item " + procesoItem.getIdProcesoItem() + " no cumple condiciones para consentimiento");
			}
		}
		logger.info("Actualización de consentimiento completada para proceso " + procesoBD.getIdProceso() + ": " + itemsConsentidos + " items consentidos de " + items.size() + " totales");
	}

	private void actualizarProceso(ProcesoItem procesoItem, Contexto contexto) {
		ListadoDetalle estadoDesignacion = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.ESTADO_ITEM.CODIGO, Constantes.LISTADO.ESTADO_ITEM.DESIGNACION);
		String descripcion = "Se designa ganador en concurso de selección";
		guardarEstadoItem(procesoItem, estadoDesignacion, descripcion, null, contexto);
		procesoItem.setEstado(estadoDesignacion);
		AuditoriaUtil.setAuditoriaRegistro(procesoItem, contexto);
		procesoItemDao.save(procesoItem);
	}

	private void enviarNotificacionMultiplesPropuestas(List<ProcesoItem> items, Proceso procesoBD, Contexto contexto) {
		ProcesoEtapa etapaPresentacionDB = procesoEtapaService.obtenerEtapaPorOrden(procesoBD.getProcesoUuid(), Constantes.LISTADO.ETAPA_PROCESO.ETAPA_PRESENTACION_ORDEN);
		Date hoy = new Date();
		Date dia4_postPresentacion = DateUtil.sumarDia(etapaPresentacionDB.getFechaFin(), 4L);
		if (dia4_postPresentacion.before(hoy)) {
			ProcesoMiembro presidente = procesoMiembroService.obtenerXtipo(procesoBD.getProcesoUuid(),
					Constantes.LISTADO.CARGO_MIEMBRO.C_PRESIDENTE, contexto);
			ProcesoMiembro primerMiembro = procesoMiembroService.obtenerXtipo(procesoBD.getProcesoUuid(),
					Constantes.LISTADO.CARGO_MIEMBRO.C_PRIMER, contexto);
			ProcesoMiembro miembro3 = procesoMiembroService.obtenerXtipo(procesoBD.getProcesoUuid(),
					Constantes.LISTADO.CARGO_MIEMBRO.C_MIEMBRO, contexto);
			Usuario presidenteUsuario = usuarioServiceImpl.obtenerUsuarioPorCodigoInterno(presidente.getCodigoUsuario());
			Usuario primerMiembroUsuario = usuarioServiceImpl.obtenerUsuarioPorCodigoInterno(primerMiembro.getCodigoUsuario());
			Usuario miembro3Usuario = usuarioServiceImpl.obtenerUsuarioPorCodigoInterno(miembro3.getCodigoUsuario());

			String correos = presidenteUsuario.getCorreo() + ";" +
					primerMiembroUsuario.getCorreo() + ";" +
					miembro3Usuario.getCorreo();

			notificacionService.enviarMensajeEleccionGanador(correos, items, contexto);
		}
	}

	private void guardarEstadoItem(ProcesoItem procesoItem, ListadoDetalle estado, String descripcion, Usuario usuario, Contexto contexto) {
		ItemEstado itemEstado = new ItemEstado();
		itemEstado.setProcesoItem(procesoItem);
		itemEstado.setDescripcion(descripcion);
		itemEstado.setEstado(estado);
		itemEstado.setEstadoAnterior(procesoItem.getEstado());
		itemEstado.setUsuario(usuario);
		itemEstado.setFechaRegistro(new Date());
		AuditoriaUtil.setAuditoriaRegistro(itemEstado, contexto);
		itemEstadoDao.save(itemEstado);

	}

}
