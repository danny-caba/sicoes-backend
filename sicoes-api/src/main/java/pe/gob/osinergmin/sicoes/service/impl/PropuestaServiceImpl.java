package pe.gob.osinergmin.sicoes.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Proceso;
import pe.gob.osinergmin.sicoes.model.ProcesoEtapa;
import pe.gob.osinergmin.sicoes.model.ProcesoItem;
import pe.gob.osinergmin.sicoes.model.ProcesoItemPerfil;
import pe.gob.osinergmin.sicoes.model.ProcesoMiembro;
import pe.gob.osinergmin.sicoes.model.Propuesta;
import pe.gob.osinergmin.sicoes.model.PropuestaConsorcio;
import pe.gob.osinergmin.sicoes.model.PropuestaEconomica;
import pe.gob.osinergmin.sicoes.model.PropuestaProfesional;
import pe.gob.osinergmin.sicoes.model.PropuestaTecnica;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.SupervisoraDictamen;
import pe.gob.osinergmin.sicoes.model.SupervisoraPerfil;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.repository.ProcesoDao;
import pe.gob.osinergmin.sicoes.repository.PropuestaConsorcioDao;
import pe.gob.osinergmin.sicoes.repository.PropuestaDao;
import pe.gob.osinergmin.sicoes.repository.SupervisoraDictamenDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.DocumentoService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.ProcesoEtapaService;
import pe.gob.osinergmin.sicoes.service.ProcesoItemPerfilService;
import pe.gob.osinergmin.sicoes.service.ProcesoItemService;
import pe.gob.osinergmin.sicoes.service.ProcesoMiembroService;
import pe.gob.osinergmin.sicoes.service.ProcesoService;
import pe.gob.osinergmin.sicoes.service.PropuestaEconomicaService;
import pe.gob.osinergmin.sicoes.service.PropuestaProfesionalService;
import pe.gob.osinergmin.sicoes.service.PropuestaService;
import pe.gob.osinergmin.sicoes.service.PropuestaTecnicaService;
import pe.gob.osinergmin.sicoes.service.SolicitudService;
import pe.gob.osinergmin.sicoes.service.SupervisoraDictamenService;
import pe.gob.osinergmin.sicoes.service.SupervisoraPerfilService;
import pe.gob.osinergmin.sicoes.service.SupervisoraService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DatosExportacion;
import pe.gob.osinergmin.sicoes.util.ExcelUtils;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.ZipUtil;

@Service
public class PropuestaServiceImpl implements PropuestaService {

	Logger logger = LogManager.getLogger(PropuestaServiceImpl.class);

	@Autowired
	private ProcesoDao procesoDao;
	
	@Autowired
	private PropuestaDao propuestaDao;
	
	@Autowired
	private PropuestaConsorcioDao propuestaConsorcioDao;
	
	@Autowired
	private SupervisoraDictamenDao supervisoraDictamenDao;
	
	@Autowired
	private PropuestaTecnicaService propuestaTecnicaService;
	
	@Autowired
	private PropuestaEconomicaService propuestaEconomicaService;
	
	@Autowired
	private ListadoDetalleService listadoDetalleService;
	
	@Autowired
	private ArchivoService archivoService;
	
	@Autowired
	private SupervisoraService supervisoraService;
	
	@Autowired
	private ProcesoItemService procesoItemService;
	
	@Autowired
	private PropuestaProfesionalService propuestaProfesionalService;
	
	@Autowired
	private ProcesoItemPerfilService procesoItemPerfilService;
	
	@Autowired
	private SupervisoraPerfilService supervisoraPerfilService;
	
	@Autowired
	private ProcesoEtapaService procesoEtapaService;
	
	@Autowired
	private DocumentoService documentoService;
	
	@Autowired
	private SolicitudService solicitudService;
	
	@Autowired
	private NotificacionService notificacionService;
	
	@Autowired
	private ProcesoMiembroService procesoMiembroService;
	
	@Autowired
	private ProcesoService procesoService;
	
	@Autowired
	private SupervisoraDictamenService supervisoraDictamenService;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Propuesta guardar(Propuesta propuesta, Contexto contexto) {
		Propuesta propuestaBD = null;
		if(propuesta.getIdPropuesta() == null) {
			ProcesoItem procesoItem = procesoItemService.obtener(propuesta.getProcesoItem().getProcesoItemUuid(), contexto);
			Supervisora supervisora	= supervisoraService.obtenerSupervisoraXRUCNoProfesional(contexto.getUsuario().getCodigoRuc());
			if(supervisora == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_NO_ES_SUPERVISORA);
			}
			
			Boolean tieneSubsector = false;
			List<SupervisoraPerfil> perfiles =supervisoraPerfilService.buscar(supervisora.getIdSupervisora(), contexto);
			List<ProcesoItemPerfil> perfilesProceso =procesoItemPerfilService.listar(procesoItem.getProcesoItemUuid(), contexto);
			for(ProcesoItemPerfil perfilProceso:perfilesProceso) {
				for(SupervisoraPerfil perfil:perfiles) {
					if(perfilProceso.getSubsector().getCodigo().equals(perfil.getSubsector().getCodigo())) {
						tieneSubsector = true;
					}
				}
			}
//			if(!tieneSubsector) {
//				throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_NO_ES_SUPERVISORA);
//			}
			if(Constantes.LISTADO.ESTADO_SUPERVISORA.BLOQUEADO.equals(supervisora.getEstado().getCodigo()) ) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_NO_ES_SUPERVISORA);
			}
			propuesta.setSupervisora(supervisora);	
			Propuesta propuestaExistente = propuestaDao.obtenerPropuestaXProcesoItem(procesoItem.getIdProcesoItem(),supervisora.getIdSupervisora());
			if(!(propuestaExistente==null)) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROPUESTA_YA_EXISTE);
			}
			propuestaBD=propuesta;
			ListadoDetalle estadoNoPresentado = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_PRESENTACION.CODIGO, Constantes.LISTADO.ESTADO_PRESENTACION.NO_PRESENTO);
			propuestaBD.setEstado(estadoNoPresentado);
			if (propuestaBD.getPropuestaUuid() == null) {
				propuestaBD.setPropuestaUuid(UUID.randomUUID().toString());
			}
			
			
			PropuestaTecnica propuestaTecnica = new PropuestaTecnica();
			AuditoriaUtil.setAuditoriaRegistro(propuestaTecnica,contexto);
			PropuestaTecnica propuestaTecnicaBD = propuestaTecnicaService.guardar(propuestaTecnica,contexto);		

			PropuestaEconomica propuestaEconomica=new PropuestaEconomica();
			AuditoriaUtil.setAuditoriaRegistro(propuestaEconomica,contexto);
			PropuestaEconomica propuestaEconomicaBD=propuestaEconomicaService.guardar(propuestaEconomica, contexto);
			
			propuestaBD.setPropuestaEconomica(propuestaEconomicaBD);
			propuestaBD.setPropuestaTecnica(propuestaTecnicaBD);
			AuditoriaUtil.setAuditoriaRegistro(propuestaBD,contexto);
			propuestaDao.save(propuestaBD);
		}
		
		
		return propuestaBD;
	}

	@Override
	public Propuesta obtener(String propuestaUuid, Contexto contexto) {
		
		Propuesta propuestaBD = propuestaDao.obtener(propuestaUuid);
		Long idPropuestaTecnica = propuestaBD.getIdPropuesta();
	    Long idSectorListadoDetalle = propuestaBD.getProcesoItem().getProceso().getSector().getIdListadoDetalle();

		List<PropuestaConsorcio> consorcio = propuestaConsorcioDao.obtenerConsorcios(idPropuestaTecnica);
	    propuestaBD.getPropuestaTecnica().setConsorcios(consorcio);
	    List<SupervisoraDictamen> facturacion = supervisoraDictamenDao.obtenerConsorciosConFacturado(idPropuestaTecnica, idSectorListadoDetalle);
	    // Crear un mapa para relacionar idSupervisora con montoFacturado
	    Map<Long, Double> facturacionMap = facturacion.stream()
	        .collect(Collectors.toMap(f -> f.getSupervisora().getIdSupervisora(), SupervisoraDictamen::getMontoFacturado));
	    for (PropuestaConsorcio c : consorcio) {
	        if (c.getSupervisora() != null && facturacionMap.containsKey(c.getSupervisora().getIdSupervisora())) {
	            c.setFacturacion(facturacionMap.get(c.getSupervisora().getIdSupervisora()));
	        } else {
	            c.setFacturacion(0.0); // Default value if no facturation is found
	        }
	    }
	    //propuestaBD.getSupervisora().setFacturacion(facturacion);
	    
	    
		ProcesoEtapa etapa = procesoEtapaService.obtener(propuestaBD.getProcesoItem().getProceso().getIdProceso(), contexto);
		List<ProcesoEtapa> etapas = procesoEtapaService.listar(propuestaBD.getProcesoItem().getProceso().getProcesoUuid(), contexto);
		List<ProcesoItemPerfil> perfiles =procesoItemPerfilService.listar(propuestaBD.getProcesoItem().getProcesoItemUuid(), contexto);
		propuestaBD.getProcesoItem().setEtapa(etapa);
		propuestaBD.getProcesoItem().getProceso().setEtapas(etapas);
		propuestaBD.getProcesoItem().setListProcesoItemPerfil(perfiles);
		
		propuestaBD.setDatoProceso(propuestaBD!=null);
		if(propuestaBD.getPropuestaTecnica()!=null) {
			propuestaBD.setProTecnica(propuestaBD.getPropuestaTecnica().getConsorcio()!=null);
		}else {
			propuestaBD.setProTecnica(false);
		}
		List<PropuestaProfesional> list=propuestaProfesionalService.listar(propuestaBD.getPropuestaUuid(), contexto);
		if(list!=null) {
			propuestaBD.setInvitarProfesionales(!list.isEmpty());
		}else {
			propuestaBD.setInvitarProfesionales(false);
		}
		if(propuestaBD.getPropuestaEconomica()!=null) {
			propuestaBD.setProEconomica(propuestaBD.getPropuestaEconomica().getImporte()!=null);
		}else {
			propuestaBD.setProEconomica(false);
		}

		propuestaBD.setPresentarPro(false);
		
		return propuestaBD;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void eliminar(Long idPropuesta, Contexto contexto) {
		propuestaDao.deleteById(idPropuesta);		
	}

	@Override
	public Page<Propuesta> listarPropuestas(Pageable pageable, Contexto contexto) {
		Page<Propuesta> page=propuestaDao.buscar(pageable);
		return page;
	}

	@Override
	public Propuesta obtener(Long id, Contexto contexto) {
		return propuestaDao.obtener(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Propuesta presentar(String propuestaUuid, Propuesta propuesta, Contexto contexto) {
		Propuesta propuestaBD = propuestaDao.obtener(propuestaUuid);
		ProcesoItem procesoItem = procesoItemService.obtener(propuestaBD.getProcesoItem().getProcesoItemUuid(), contexto);
		Supervisora supervisora = supervisoraService.obtenerSupervisoraXRUCNoProfesional(contexto.getUsuario().getCodigoRuc());
		if(supervisora == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_NO_ES_SUPERVISORA);
		}
		if(!Constantes.LISTADO.ESTADO_ITEM.PRESENTACION.equals(procesoItem.getProceso().getEstado().getCodigo())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROCESO_ITEM_PRESENTACION);
		}
		if(propuestaBD.getPropuestaEconomica().getImporte()==null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.MONTO_PROPUESTA_ECONOMICA);
		}
		if(propuestaBD.getPropuestaTecnica().getConsorcio()==null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.CONSORCIO_NO_ENVIADO);
		}
		
		if(Constantes.LISTADO.SI_NO.SI.equals(propuestaBD.getPropuestaTecnica().getConsorcio().getCodigo())) {
			//FIXME:Mejoras por agregar
		}
		
		Proceso proceso = procesoService.obtener(procesoItem.getProceso().getProcesoUuid(), contexto);
		if(Constantes.LISTADO.TIPO_FACTURACION.POR_SECTOR.equals(proceso.getTipoFacturacion().getCodigo())) {
			SupervisoraDictamen dictamen = supervisoraDictamenService.obtenerXSupervisora(supervisora.getIdSupervisora(), proceso.getSector().getIdListadoDetalle(), contexto);
			if(dictamen.getMontoFacturado() < procesoItem.getFacturacionMinima() && Constantes.LISTADO.SI_NO.NO.equals(propuestaBD.getPropuestaTecnica().getConsorcio().getCodigo())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.FACTURACION_MINIMA_NO_CUMPLE);
			}
		}else {
			List<SupervisoraDictamen> dictamenMontos = supervisoraDictamenService.listarXSupervisora(supervisora.getIdSupervisora(),contexto);
			
			Double suma = (double) 0 ;
			for(SupervisoraDictamen dictamen:dictamenMontos) {
				suma = suma + dictamen.getMontoFacturado();
			}
			if(suma < procesoItem.getFacturacionMinima()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.FACTURACION_MINIMA_NO_CUMPLE);
			}
		}
		List<PropuestaProfesional> invitaciones = propuestaProfesionalService.listarAceptados(propuestaUuid, contexto);
		List<ProcesoItemPerfil> perfiles = procesoItemPerfilService.listar(propuestaBD.getProcesoItem().getProcesoItemUuid(), contexto);
		for(ProcesoItemPerfil perfil:perfiles) {
			 List<PropuestaProfesional> invArray = new ArrayList<>();
			for(PropuestaProfesional invitacion:invitaciones) {
				if(perfil.getPerfil().getCodigo().equals(invitacion.getPerfil().getCodigo())) {
					invArray.add(invitacion);
				}
			}
			Integer cantidad = invArray.size();
			if(perfil.getNroProfesionales()<cantidad) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.CANTIDAD_PROFESIONALES);
			}
		}		
		
		List<Archivo> archivosTec = archivoService.listarXTecnica(propuestaUuid, contexto);
		List<Archivo> archivosEco = archivoService.listarXEconomica(propuestaUuid, contexto);
		
		ListadoDetalle caducada = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_INVITACION.CODIGO, Constantes.LISTADO.ESTADO_INVITACION.CADUCADO);
		List<PropuestaProfesional> invitacionesNoAceptadas = propuestaProfesionalService.listarNoAceptados(propuestaUuid, contexto);
		for(PropuestaProfesional invitacion:invitacionesNoAceptadas) {
			if(invitacion.getEstado().getCodigo().equals(Constantes.LISTADO.ESTADO_INVITACION.INVITADO)) {
				invitacion.setEstado(caducada);
			}
		}
		
		ListadoDetalle estadoPresentado = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_PRESENTACION.CODIGO, Constantes.LISTADO.ESTADO_PRESENTACION.PRESENTADO);
		propuestaBD.setEstado(estadoPresentado);
		propuestaBD.setFechaPresentacion(new Date());
		AuditoriaUtil.setAuditoriaRegistro(propuestaBD,contexto);
		propuestaDao.save(propuestaBD);	
		notificacionService.enviarMensajePresentacionPropuesta(propuestaBD,archivosTec.size(),archivosEco.size(), contexto);
		return propuestaBD;
	}

	@Override
	public Propuesta obtenerPropuestaXProcesoItem(Long idProcesoItem,Contexto contexto) {
		Supervisora supervisora= supervisoraService.obtenerSupervisoraXNroDocumento(contexto.getUsuario().getCodigoRuc());
		if(supervisora!=null) {
			return propuestaDao.obtenerPropuestaXProcesoItem(idProcesoItem,supervisora.getIdSupervisora());
		}else {
			return null;
		}
	}  
	
	@Override
	public List<Propuesta> obtenerTodasPropuestaXProcesoItem(Long idProcesoItem,Contexto contexto) {
		return propuestaDao.obtenerPropuestaXProcesoItem(idProcesoItem);		
	}  
	
	@Override
	public InputStream generarExport(String procesoItemUuid, Contexto contexto) {
		
		List<DatosExportacion> listDatos = new ArrayList<DatosExportacion>();
		
		List<Object[]> result=  archivoService.buscarPropuesta(procesoItemUuid,contexto);
		DatosExportacion datosExportacion1 =new DatosExportacion();
		datosExportacion1.setTitulo("Postores y Adjuntos");
		datosExportacion1.setNombreHoja("Postores y Adjuntos");
		datosExportacion1.setFecha(new Date());
		datosExportacion1.setUsuario(contexto.getUsuario().getNombreUsuario());
		datosExportacion1.setDescuentoRegistros(0L);
		datosExportacion1.setFiltros(
				new String[][]{				
					});
		datosExportacion1.setNombreTitulos(new String[] {"Fecha/Hora Presentación","Proceso","Item","Tipo de Persona","País","Postor","Consorcio","Tipo de Moneda","Monto de Propuesta","Tipo de Archivo","Nombre Archivo","Descripción"});
		datosExportacion1.setListado(result);
		listDatos.add(datosExportacion1);
		
		List<Object[]> result2=  propuestaProfesionalService.listarProfesionalesXPerfil(procesoItemUuid,contexto);
		DatosExportacion datosExportacion2 =new DatosExportacion();
		datosExportacion2.setTitulo("Profesionales");
		datosExportacion2.setNombreHoja("Profesionales");
		datosExportacion2.setFecha(new Date());
		datosExportacion2.setUsuario(contexto.getUsuario().getNombreUsuario());
		datosExportacion2.setDescuentoRegistros(0L);
		datosExportacion2.setFiltros(
				new String[][]{				
					});
		datosExportacion2.setNombreTitulos(new String[] {"Fecha/Hora Presentación","Proceso","Item","Tipo de Persona","País","Postor","# Profesionales Requeridos","Sector","Subsector","Perfil","RUC","Nombre"});
		datosExportacion2.setListado(result2);
		listDatos.add(datosExportacion2);
		
		List<Object[]> result3=  procesoItemPerfilService.listarProfesionalesXPerfil(procesoItemUuid,contexto);
	
		DatosExportacion datosExportacion3 =new DatosExportacion();
		datosExportacion3.setTitulo("Profesionales por Perfil");
		datosExportacion3.setNombreHoja("Profesionales por Perfil");
		datosExportacion3.setFecha(new Date());
		datosExportacion3.setUsuario(contexto.getUsuario().getNombreUsuario());
		datosExportacion3.setDescuentoRegistros(0L);
		datosExportacion3.setFiltros(
				new String[][]{				
					});
		datosExportacion3.setNombreTitulos(new String[] {"Proceso","Item","Tipo de Persona","País","Postor","Perfil","Requeridos","Presentados","Resultado"});
		datosExportacion3.setListado(result3);
		listDatos.add(datosExportacion3);
		
		return ExcelUtils.generarArchivo(listDatos);
	}

	@Override
	public Long obtenerIdPropuestaEconomica(String propuestaUuid) {
		if(propuestaUuid==null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROPUESTA_UUID_NO_ENVIADO);
		}
		return propuestaDao.obtenerIdEconomica(propuestaUuid);
	}
	
	@Override
	public Long obtenerIdPropuestaTecnica(String propuestaUuid) {
		if(propuestaUuid==null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROPUESTA_UUID_NO_ENVIADO);
		}
		return propuestaDao.obtenerIdTecnica(propuestaUuid);
	}

	@Override
	public List<String> validaciones(String propuestaUuid, Contexto contexto) {
		List<String> validaciones = new ArrayList<>();
		Propuesta propuestaBD = propuestaDao.obtener(propuestaUuid);
		List<PropuestaProfesional> invitaciones = propuestaProfesionalService.listar(propuestaUuid, contexto);
		List<ProcesoItemPerfil> perfiles = procesoItemPerfilService.listar(propuestaBD.getProcesoItem().getProcesoItemUuid(), contexto);
		for(ProcesoItemPerfil perfil:perfiles) {
			 List<PropuestaProfesional> invArray = new ArrayList<>();
			for(PropuestaProfesional invitacion:invitaciones) {
				if(perfil.getPerfil().getCodigo().equals(invitacion.getPerfil().getCodigo())) {
					invArray.add(invitacion);
				}
			}
			Integer cantidad = invArray.size();
			if(perfil.getNroProfesionales()>cantidad) {
				validaciones.add(messageSource.getMessage(Constantes.CODIGO_MENSAJE.CUMPLE_PROFESIONALES, new Object[0], Constantes.BITACORA.COD_MENSAJE.MENSAJE_DEFECTO, Locale.getDefault()));
			}
		}
		
		List<Archivo> archivosTec = archivoService.listarXTecnica(propuestaUuid, contexto);
		List<Archivo> archivosEco = archivoService.listarXEconomica(propuestaUuid, contexto);
		
		if(archivosTec.isEmpty()) {
			validaciones.add(messageSource.getMessage(Constantes.CODIGO_MENSAJE.AGREGAR_ARCHIVO_TECNICA, new Object[0], Constantes.BITACORA.COD_MENSAJE.MENSAJE_DEFECTO, Locale.getDefault()));
		}
		if(archivosEco.isEmpty()) {
			validaciones.add(messageSource.getMessage(Constantes.CODIGO_MENSAJE.AGREGAR_ARCHIVO_ECONOMICA, new Object[0], Constantes.BITACORA.COD_MENSAJE.MENSAJE_DEFECTO, Locale.getDefault()));
		}
		Supervisora supervisora	= supervisoraService.obtenerSupervisoraXRUCNoProfesional(contexto.getUsuario().getCodigoRuc());
		if(supervisora == null) {
			validaciones.add(messageSource.getMessage(Constantes.CODIGO_MENSAJE.USUARIO_NO_ES_SUPERVISORA, new Object[0], Constantes.BITACORA.COD_MENSAJE.MENSAJE_DEFECTO, Locale.getDefault()));
			return validaciones;
		}
		boolean tieneSubsector = false;
		List<SupervisoraPerfil> perfilesSupervisora =supervisoraPerfilService.buscar(supervisora.getIdSupervisora(), contexto);
		for(ProcesoItemPerfil perfilProceso:perfiles) {
			for(SupervisoraPerfil perfil:perfilesSupervisora) {
				if(perfilProceso.getSubsector().getCodigo().equals(perfil.getSubsector().getCodigo())) {
					tieneSubsector = true;
				}
			}
		}
		if(!tieneSubsector) {
			validaciones.add(messageSource.getMessage(Constantes.CODIGO_MENSAJE.USUARIO_NO_ES_SUPERVISORA, new Object[0], Constantes.BITACORA.COD_MENSAJE.MENSAJE_DEFECTO, Locale.getDefault()));
		}
			
	
		return validaciones;
	}
	
	
	@Override
	public void generarArchivoDescarga(Propuesta propuesta, Contexto contexto) {
		try {
			Propuesta propuestaBD=propuestaDao.obtener(propuesta.getPropuestaUuid());
			propuestaBD.setDecripcionDescarga("Descargando Archivos");
			propuestaDao.save(propuestaBD);
			String path=archivoService.generarArchivoContenido(propuestaBD,contexto);
			String pathZip=path+".zip";
			propuestaBD.setDecripcionDescarga("Generando Zip");
			propuestaDao.save(propuestaBD);
			ZipUtil.zipearCarpeta(path,pathZip);
			propuestaBD.setRutaDescarga(pathZip);
			propuestaBD.setDecripcionDescarga("Descargar");
			propuestaDao.save(propuestaBD);
		}catch (Exception e) {
			throw new ValidacionException(e);
		}
	}

	@Override
	public List<Propuesta> buscarPropuestasXItem(String procesoItemUuid, Contexto contexto) {
		return propuestaDao.listar(procesoItemUuid);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Propuesta seleccionar(String propuestaUuid, Propuesta propuesta, Contexto contexto) {
		Propuesta propuestaBD = propuestaDao.obtener(propuestaUuid);
		ProcesoItem procesoItemBD = procesoItemService.obtener(propuestaBD.getProcesoItem().getProcesoItemUuid(), contexto);
		
		if(propuestaBD.getGanador()==null) {
			if(!Constantes.LISTADO.ESTADO_ITEM.DESIGNACION.equals(procesoItemBD.getEstado().getCodigo())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.SELECCIONAR_GANADOR_DESIGNACION);
			}

		}else {
			if(!Constantes.LISTADO.ESTADO_ITEM.DESIGNACION.equals(procesoItemBD.getEstado().getCodigo())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.CAMBIAR_GANADOR_DESIGNACION);
			}
		}
		
		Proceso procesoBD = procesoService.obtener(procesoItemBD.getProceso().getProcesoUuid(),contexto);
		ProcesoMiembro presidente = procesoMiembroService.obtenerXtipo(procesoBD.getProcesoUuid(),
				Constantes.LISTADO.CARGO_MIEMBRO.C_PRESIDENTE, contexto);
		ProcesoMiembro primerMiembro = procesoMiembroService.obtenerXtipo(procesoBD.getProcesoUuid(),
				Constantes.LISTADO.CARGO_MIEMBRO.C_PRIMER, contexto);
		ProcesoMiembro miembro3 = procesoMiembroService.obtenerXtipo(procesoBD.getProcesoUuid(),
				Constantes.LISTADO.CARGO_MIEMBRO.C_MIEMBRO, contexto);
		
		Usuario usuario = contexto.getUsuario();
		if (!((presidente.getCodigoUsuario().equals(usuario.getCodigoUsuarioInterno()))
				|| (primerMiembro.getCodigoUsuario().equals(usuario.getCodigoUsuarioInterno()))
				|| (miembro3.getCodigoUsuario().equals(usuario.getCodigoUsuarioInterno())))) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.SELECCIONAR_GANADOR);
		}

		List<Propuesta> propuestas = propuestaDao.listar(propuestaBD.getProcesoItem().getProcesoItemUuid());
		if(propuestaBD.getGanador()==null) {
			for(Propuesta prop:propuestas) {
				if(prop.getIdPropuesta()==propuestaBD.getIdPropuesta()) {
					prop.setGanador(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.SI_NO.CODIGO, Constantes.LISTADO.SI_NO.SI));
				}else {
					prop.setGanador(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.SI_NO.CODIGO, Constantes.LISTADO.SI_NO.NO));
				}
			}
			propuestaBD.setGanador(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.SI_NO.CODIGO, Constantes.LISTADO.SI_NO.SI));
		}else if(Constantes.LISTADO.SI_NO.SI.equals(propuestaBD.getGanador().getCodigo())) {
			propuestaBD.setGanador(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.SI_NO.CODIGO, Constantes.LISTADO.SI_NO.NO));
		}else {
			for(Propuesta prop:propuestas) {
				if(prop.getIdPropuesta()==propuestaBD.getIdPropuesta()) {
					prop.setGanador(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.SI_NO.CODIGO, Constantes.LISTADO.SI_NO.SI));
				}else {
					prop.setGanador(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.SI_NO.CODIGO, Constantes.LISTADO.SI_NO.NO));
				}
			}
		}
		
		AuditoriaUtil.setAuditoriaRegistro(propuestaBD,contexto);
		propuestaDao.save(propuestaBD);			
		return propuestaBD;
	}

	@Override
	public Page<Propuesta> listarPropuestasXItem(String procesoItemUuid, Pageable pageable,
			Contexto contexto) {
		
		return propuestaDao.listarXItem(procesoItemUuid, pageable);
	}
	
	@Transactional
	public void limpiarDescarga() {
		propuestaDao.limpiarDescarga();		
	}

	@Override
	public Propuesta obtenerPropuestaGanadora(String procesoItemUuid, Contexto contexto) {
		return propuestaDao.obtenerPropuestaGanadora(procesoItemUuid);
	}
}
