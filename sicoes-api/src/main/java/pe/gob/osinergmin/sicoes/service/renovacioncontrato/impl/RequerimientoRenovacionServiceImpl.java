package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DireccionxClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.ExpedienteOutRO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.RequerimientoRenovacionListDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.HistorialEstadoAprobacionCampo;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.HistorialEstadoAprobacionCampoDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoRenovacionDao;
import pe.gob.osinergmin.sicoes.repository.SicoesSolicitudDao;
import pe.gob.osinergmin.sicoes.service.*;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.RequerimientoRenovacionService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper.RequerimientoRenovacionMapper;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class RequerimientoRenovacionServiceImpl implements RequerimientoRenovacionService {

	@Autowired
	private RequerimientoRenovacionDao requerimientoRenovacionDao;
	@Autowired
	private SicoesSolicitudService sicoesSolicitudService;
	@Autowired
	private SupervisoraRepresentanteService supervisoraRepresentanteService;
	@Autowired
	private SicoesSolicitudSeccionService sicoesSolicitudSeccionService;
	@Autowired
	private ListadoDetalleService listadoDetalleService;
	@Autowired
	private ArchivoService archivoService;
	@Autowired
	private SicoesTdSolPerConSecService seccionesService;
	@Autowired
	private SigedApiConsumer sigedApiConsumer;
	@Autowired
	private SicoesSolicitudDao sicoesSolicitudDao;
	@Autowired
	private Environment env;

	@Value("${path.jasper}")
	private String pathJasper;
	@Value("${path.temporal}")
	private String pathTemporal;
	@Value("${siged.titulo.incripcion.registro}")
	private String TITULO_REGISTRO;
	@Value("${siged.old.proyecto}")
	private String SIGLA_PROYECTO;

	Logger logger = LogManager.getLogger(RequerimientoRenovacionServiceImpl.class);
	@Autowired
	private RequerimientoRenovacionMapper requerimientoRenovacionMapper;
	@Autowired
	private HistorialEstadoAprobacionCampoDao historialEstadoAprobacionCampoDao;
	@Autowired
	private InformeRenovacionDao informeRenovacionDao;
	@Autowired
	private ListadoDetalleDao listadoDetalleDao;

	@Override
	public Page<RequerimientoRenovacionListDTO> buscar(String idSolicitud, String numeroExpediente, String sector, String subSector, Pageable pageable, Contexto contexto) {
		Page<RequerimientoRenovacion> lista = requerimientoRenovacionDao.findByNuExpedienteContains(idSolicitud==null?null:Long.parseLong(idSolicitud),numeroExpediente, sector, subSector, pageable);
		return lista.map((r)->{
			RequerimientoRenovacionListDTO requerimiento = new RequerimientoRenovacionListDTO();
			requerimiento.setNuExpediente(r.getNuExpediente());
			requerimiento.setTiSector(r.getTiSector());
			requerimiento.setTiSubSector(r.getTiSubSector());
			requerimiento.setNoItem(r.getNoItem());
			requerimiento.setFeRegistro(r.getFeRegistro());
			requerimiento.setFeRegistro(r.getFeRegistro());
			requerimiento.setEstadoReqRenovacion(  r.getEstadoReqRenovacion().getCodigo());
			//requerimiento.setEstadoAprobacionInforme();
			List<InformeRenovacion> listaInforme = informeRenovacionDao.listarPorRequerimiento(requerimiento.getIdReqRenovacion());
			if(!listaInforme.isEmpty()) {
				requerimiento.setEstadoAprobacionInforme(listaInforme.get(listaInforme.size() - 1).getEstadoAprobacionInforme().getNombre());
			}
			ListadoDetalle ldGPPM = listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.GRUPO_APROBACION.CODIGO,Constantes.LISTADO.GRUPO_APROBACION.GPPM);
			List<HistorialEstadoAprobacionCampo> listaGPPM = historialEstadoAprobacionCampoDao.listarPorRequerimientoAprobacion(requerimiento.getIdReqRenovacion(),ldGPPM.getIdListadoDetalle());
			if(!listaGPPM.isEmpty()) {
				ListadoDetalle estado = listadoDetalleDao.findById(listaGPPM.get(listaGPPM.size() - 1).getDeEstadoNuevoLd()).orElse(null);
				if(estado!=null) {
					requerimiento.setEstadoAprobacionGPPM(estado.getNombre());
				}
			}
			ListadoDetalle ldGSE = listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.GRUPO_APROBACION.CODIGO,Constantes.LISTADO.GRUPO_APROBACION.GSE);
			List<HistorialEstadoAprobacionCampo> listaGSE = historialEstadoAprobacionCampoDao.listarPorRequerimientoAprobacion(requerimiento.getIdReqRenovacion(),ldGSE.getIdListadoDetalle());
			if(!listaGSE.isEmpty()) {
				ListadoDetalle estado = listadoDetalleDao.findById(listaGSE.get(listaGSE.size() - 1).getDeEstadoNuevoLd()).orElse(null);
				if(estado!=null) {
					requerimiento.setEstadoAprobacionGSE(estado.getNombre());
				}
			}
			return requerimiento;
		});
	}

	public RequerimientoRenovacion guardar(RequerimientoRenovacion requerimientoRenovacion, Contexto contexto) throws Exception {
		if (requerimientoRenovacion.getIdSoliPerfCont()==null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_SOLICITUD_NO_ENVIADO);
		}
		ListadoDetalle concluido = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.ESTADO_REQ_RENOVACION.CODIGO, Constantes.LISTADO.ESTADO_REQ_RENOVACION.CONCLUIDO);
		ListadoDetalle archivado = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.ESTADO_REQ_RENOVACION.CODIGO, Constantes.LISTADO.ESTADO_REQ_RENOVACION.ARCHIVADO);
		// Crear una lista con los IDs a excluir
		List<Long> estadosExcluidos = new ArrayList<>();
		if (concluido != null) {
			estadosExcluidos.add(concluido.getIdListadoDetalle());
		}
		if (archivado != null) {
			estadosExcluidos.add(archivado.getIdListadoDetalle());
		}
		List<RequerimientoRenovacion> requerimientosActivos = requerimientoRenovacionDao
				.listarNoConcluidos(
						requerimientoRenovacion.getIdSoliPerfCont(),
						estadosExcluidos
				);

		if (!requerimientosActivos.isEmpty()) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.REQ_RENOVACION_ACTIVOS);
		}
		SicoesSolicitud sicoesSolicitud = sicoesSolicitudService.obtener(requerimientoRenovacion.getIdSoliPerfCont(), contexto);
		if(sicoesSolicitud==null ){
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_SOLICITUD_NO_ENVIADO);
		}
		if(!Constantes.ESTADO_PROCESO_PERF_CONTRATO.CONCLUIDO.equals(sicoesSolicitud.getEstadoProcesoSolicitud())){
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_CONCLUIDA);
		}
		if(!(Constantes.TIPO_SOLICITUD_PERF_CONTRATO.INSCRIPCION.equals(sicoesSolicitud.getTipoSolicitud()) ||
		Constantes.TIPO_SOLICITUD_PERF_CONTRATO.SUBSANACION.equals(sicoesSolicitud.getTipoSolicitud()))){
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_SOLICITUD_NO_PERMITIDO);
		}
		//return null;
		requerimientoRenovacion.setFeRegistro(new Date());
		ListadoDetalle estadoPreliminar = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.ESTADO_REQ_RENOVACION.CODIGO, Constantes.LISTADO.ESTADO_REQ_RENOVACION.PRELIMINAR);
		requerimientoRenovacion.setEstadoReqRenovacion(estadoPreliminar);

		List<Archivo> archivosRegistrados = obtenerArchivosRegistrados(new ArrayList<>(), sicoesSolicitud, contexto);
		List<File> archivosAlfresco = null;
		archivosAlfresco = archivoService.obtenerArchivoContenidoPerfCont(archivosRegistrados, sicoesSolicitud, contexto);
		String expediente = enviarArchivos(archivosAlfresco, sicoesSolicitud, contexto);
		requerimientoRenovacion.setNuExpediente(expediente);
		Proceso proceso= sicoesSolicitud.getPropuesta().getProcesoItem().getProceso();
		requerimientoRenovacion.setTiSector(proceso.getSector().getCodigo());
		requerimientoRenovacion.setTiSubSector(proceso.getSubsector().getCodigo());
		requerimientoRenovacion.setNoItem("Item"+sicoesSolicitud.getPropuesta().getProcesoItem().getNumeroItem());
		requerimientoRenovacion.setEsRegistro(Constantes.ESTADO.ACTIVO);
		requerimientoRenovacion.setSolicitudPerfil(sicoesSolicitud);
		AuditoriaUtil.setAuditoriaRegistro(requerimientoRenovacion,contexto);
		return requerimientoRenovacionDao.save(requerimientoRenovacion);
	}


	private String enviarArchivos(List<File> archivosAlfresco, SicoesSolicitud solicitud, Contexto contexto) throws Exception {
		ExpedienteInRO expedienteInRO = null;
		String codExpediente = null;
		if (solicitud.getIdSolicitudPadre() != null) {
			SicoesSolicitud solicitudPadre = sicoesSolicitudDao.findById(solicitud.getIdSolicitudPadre()).orElse(null);
			codExpediente = solicitudPadre.getNumeroExpediente();
		}
		expedienteInRO = crearExpedientePresentacion(solicitud, codExpediente, contexto);
		ExpedienteOutRO expedienteOutRO = null;
		DocumentoOutRO documentoSubsanacionOutRO = null;
		if (codExpediente != null) {
			documentoSubsanacionOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);
		} else {
			expedienteOutRO = sigedApiConsumer.crearExpediente(expedienteInRO, archivosAlfresco);
		}

		if (codExpediente != null) {
			if (1 != documentoSubsanacionOutRO.getResultCode()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_NOMBRE_DUPLICADO);
			}
		} else {
			if (1 != expedienteOutRO.getResultCode()) {
				throw new ValidacionException(expedienteOutRO.getMessage());
			}
		}

		codExpediente = codExpediente != null ? codExpediente : expedienteOutRO.getCodigoExpediente();

		if (false) {
			throw new ValidacionException("Error al guardar archivos en SIGED");
		} else {
			expedienteInRO = crearExpedientePresentacion(solicitud, codExpediente, contexto);
			Archivo formato_22 = null;

			formato_22 = generarReportePresentacion(solicitud, codExpediente, contexto);

			archivosAlfresco = new ArrayList<>();
			File file = null;

			File dir = new File(pathTemporal + File.separator+"temporales" + File.separator + solicitud.getIdSolicitud());
			if (!dir.exists()) {
				dir.mkdirs();
			}
			file = new File(
					pathTemporal + File.separator + "temporales" + File.separator + solicitud.getIdSolicitud() + File.separator + formato_22.getNombre());
			FileUtils.writeByteArrayToFile(file, formato_22.getContenido());
			formato_22.setContenido(Files.readAllBytes(file.toPath()));

			archivosAlfresco.add(file);
			DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);
			if (documentoOutRO.getResultCode() != 1) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_AGREGAR_DOCUMENTOS,
						codExpediente);
			}
		}
		return codExpediente;
	}


	private List<Archivo> obtenerArchivosRegistrados(List<SicoesSolicitudSeccion> listaSolicitudSeccion, SicoesSolicitud solicitud, Contexto contexto) {
		List<Archivo> archivosRegistrados = new ArrayList<>();
		List<SicoesSolicitudSeccion> listaSolicitudSeccionFinal = new ArrayList<>();

		if (solicitud.getIdSolicitudPadre() != null) {
			listaSolicitudSeccionFinal = listaSolicitudSeccion.stream().filter(s -> !s.getProcRevision().equals("1")).collect(Collectors.toList());
		} else {
			listaSolicitudSeccionFinal.addAll(listaSolicitudSeccion);
		}

		for (SicoesSolicitudSeccion requisito: listaSolicitudSeccionFinal) {
			if (requisito.getArchivo() == null) {
				continue;
			}
			if (solicitud.getIdSolicitudPadre() != null) {
				requisito.getArchivo().setNombreReal("subsanacion_"+requisito.getArchivo().getNombreReal());
			}
			archivosRegistrados.add(requisito.getArchivo());
		}

		List<SicoesTdSolPerConSec> secciones = seccionesService.obtenerSeccionesXSolicitud(solicitud.getIdSolicitud());

		for (SicoesTdSolPerConSec seccion: secciones) {
			if (!"1".equals(seccion.getFlConPersonal())) {
				continue;
			}

			List<Long> seccionesIds = new ArrayList<>();
			seccionesIds.add(seccion.getIdSolPerConSec());

			List<SicoesSolicitudSeccion> requisitosPersonal = sicoesSolicitudSeccionService.obtenerRequisitosPorSeccionFinalizacion(seccionesIds);
			List<SicoesSolicitudSeccion> requisitosPersonalFinal = new ArrayList<>();

			if (solicitud.getIdSolicitudPadre() != null) {
				requisitosPersonalFinal = requisitosPersonal.stream().filter(s -> !s.getProcRevision().equals("1")).collect(Collectors.toList());
			} else {
				requisitosPersonalFinal.addAll(requisitosPersonal);
			}

			List<Long> requisitosIds = new ArrayList<>();
			requisitosPersonalFinal.forEach(requisito -> requisitosIds.add(requisito.getIdSolicitudSeccion()));
			List<Archivo> archivosPersonal = archivoService.obtenerArchivosPorRequisitos(requisitosIds, contexto);

			for (Archivo archivo: archivosPersonal) {
//				if (archivo.getArchivo() == null) {
//					continue;
//				}
				if (solicitud.getIdSolicitudPadre() != null) {
					archivo.setNombreReal("subsanacion_"+archivo.getNombreReal());
				}
				archivosRegistrados.add(archivo);
			}
		}

		return archivosRegistrados;
	}

	private ExpedienteInRO crearExpedientePresentacion(SicoesSolicitud solicitud, String codExpediente, Contexto contexto) {
		return crearExpediente(solicitud,
				Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.crear")), codExpediente, contexto);
	}

	private ExpedienteInRO crearExpediente(SicoesSolicitud solicitud, Integer codigoTipoDocumento, String codExpediente, Contexto contexto) {
		ExpedienteInRO expediente = new ExpedienteInRO();
		DocumentoInRO documento = new DocumentoInRO();
		ClienteListInRO clientes = new ClienteListInRO();
		ClienteInRO cs = new ClienteInRO();
		List<ClienteInRO> cliente = new ArrayList<>();
		DireccionxClienteListInRO direcciones = new DireccionxClienteListInRO();
		DireccionxClienteInRO d = new DireccionxClienteInRO();
		List<DireccionxClienteInRO> direccion = new ArrayList<>();
		expediente.setProceso(Integer.parseInt(env.getProperty("crear.expediente.parametros.proceso")));
		expediente.setDocumento(documento);
		if (solicitud.getNumeroExpediente() != null || codExpediente != null) {
			if (codExpediente != null) {
				expediente.setNroExpediente(codExpediente);
			} else {
				expediente.setNroExpediente(solicitud.getNumeroExpediente());
			}
		}
//		if (solicitud.getPersona() != null) {
		documento.setAsunto(String.format("Solicitud de Renovación Requerimiento"));
//		}

		documento.setAppNameInvokes(SIGLA_PROYECTO);
//		ListadoDetalle tipoDocumento = listadoDetalleService
//				.obtener(solicitud.getPersona().getTipoDocumento().getIdListadoDetalle(), contexto);
		cs.setCodigoTipoIdentificacion(1);
		if (cs.getCodigoTipoIdentificacion() == 1) {
			cs.setNombre(solicitud.getSupervisora().getNombreRazonSocial());
			cs.setApellidoPaterno("-");
			cs.setApellidoMaterno("-");
		}

//		if (solicitud.getRepresentante() != null) {
//			cs.setRepresentanteLegal(
//					solicitud.getRepresentante().getNombres() + " " + solicitud.getRepresentante().getApellidoPaterno()
//							+ " " + solicitud.getRepresentante().getApellidoMaterno());
//		}
		cs.setRazonSocial(solicitud.getSupervisora().getNombreRazonSocial());
		cs.setNroIdentificacion(solicitud.getSupervisora().getNumeroDocumento());
		cs.setTipoCliente(Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.cliente")));
		cliente.add(cs);
		d.setDireccion(solicitud.getSupervisora().getDireccion());
		d.setDireccionPrincipal(true);
		d.setEstado(env.getProperty("crear.expediente.parametros.direccion.estado").charAt(0));
		d.setTelefono(solicitud.getSupervisora().getTelefono1());
		if (solicitud.getSupervisora().getCodigoDistrito() != null) {
			d.setUbigeo(Integer.parseInt(solicitud.getSupervisora().getCodigoDistrito()));
		}
		direccion.add(d);
		direcciones.setDireccion(direccion);
		cs.setDirecciones(direcciones);
		clientes.setCliente(cliente);
		documento.setClientes(clientes);
		documento.setCodTipoDocumento(codigoTipoDocumento);
		documento.setEnumerado(env.getProperty("crear.expediente.parametros.enumerado").charAt(0));
		documento.setEstaEnFlujo(env.getProperty("crear.expediente.parametros.esta.en.flujo").charAt(0));
		documento.setFirmado(env.getProperty("crear.expediente.parametros.firmado").charAt(0));
		documento.setCreaExpediente(env.getProperty("crear.expediente.parametros.crea.expediente").charAt(0));
		documento.setPublico(env.getProperty("crear.expediente.parametros.crea.publico").charAt(0));
		documento.setNroFolios(Integer.parseInt(env.getProperty("crear.expediente.parametros.crea.folio")));
		/*if(Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.informe.tecnico"))==codigoTipoDocumento) {
			if(codigoUsuario!=null) {
				documento.setUsuarioCreador(codigoUsuario);
				documento.setFirmante(codigoUsuario);
			}
		}else{
			documento.setUsuarioCreador(Integer.parseInt(env.getProperty("siged.bus.server.id.usuario")));
		}*/
//		String stecnico=env.getProperty("crear.expediente.parametros.tipo.documento.informe.tecnico");
//		int vtenico= Integer.parseInt(stecnico);
//
//		if(vtenico==codigoTipoDocumento) {
//			if(codigoUsuario!=null) {
//				documento.setUsuarioCreador(codigoUsuario);
//				documento.setFirmante(codigoUsuario);
//			}
//		}
//		if(Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.informe.respuesta.solicitud.pn"))==codigoTipoDocumento) {
//
//			documento.setUsuarioCreador(Integer.parseInt(env.getProperty("siged.bus.server.id.usuario")));
//			documento.setFirmante(Integer.parseInt(env.getProperty("siged.firmante.informe.respuesta.id.usuario")));
//
//		}else{
		documento.setUsuarioCreador(Integer.parseInt(env.getProperty("siged.bus.server.id.usuario")));
//		}
		logger.info("DOC_EXPEDIENTE--- :"+documento);
		logger.info("EXPEDIENTE_REGISTRO_PERF :"+expediente);
		return expediente;
	}

	private Archivo generarReportePresentacion(SicoesSolicitud solicitud, String codExpediente, Contexto contexto) {
		Archivo archivo = new Archivo();
		if (solicitud.getIdSolicitudPadre() != null) {
			archivo.setNombre("Subsanacion_Perfeccionamiento_" + solicitud.getSupervisora().getNumeroDocumento() + ".pdf");
			archivo.setNombreReal("Subsanacion_Perfeccionamiento_" + solicitud.getSupervisora().getNumeroDocumento() + ".pdf");
		} else {
			archivo.setNombre("Solicitud_Perfeccionamiento_" + solicitud.getSupervisora().getNumeroDocumento() + ".pdf");
			archivo.setNombreReal("Solicitud_Perfeccionamiento_" + solicitud.getSupervisora().getNumeroDocumento() + ".pdf");
		}
		archivo.setTipo("application/pdf");
		archivo.setTipoArchivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,Constantes.LISTADO.TIPO_ARCHIVO.SOLICITUD_PERFECCIONAMIENTO));
		//si se desea guardar en bd descomentar y asignar a un nuevo campo
		// archivo.setIdNuevoCampo(solicitud.getIdSolicitud());

		List<Long> seccionesIds = new ArrayList<>();
		List<Long> requisitosIds = new ArrayList<>();
		SicoesSolicitud solicitudDB = sicoesSolicitudDao.findById(solicitud.getIdSolicitud()).orElse(null);
		SicoesSolicitud solicitudJasper = new SicoesSolicitud();

		solicitudJasper.setIdSolicitud(solicitudDB.getIdSolicitud());
		solicitudJasper.setNumeroExpediente(solicitudDB.getNumeroExpediente() == null ? codExpediente : solicitudDB.getNumeroExpediente());
		solicitudJasper.setFechaHoraPresentacion(solicitudDB.getFechaHoraPresentacion());

		// Cargar supervisora
		Supervisora supervisora = initializeAndUnproxy(solicitudDB.getSupervisora());
		if (supervisora.getNombreRazonSocial() == null) {
			supervisora.setNombreRazonSocial(supervisora.getNombres()+ " " + supervisora.getApellidoPaterno() + " " + supervisora.getApellidoMaterno());
		}
		ListadoDetalle pais = initializeAndUnproxy(supervisora.getPais());
		ListadoDetalle tipoDocumento = initializeAndUnproxy(supervisora.getTipoDocumento());
		supervisora.setPais(pais);
		supervisora.setTipoDocumento(tipoDocumento);
		SupervisoraRepresentante representante = supervisoraRepresentanteService.obtenerXIdSupervisora(solicitudDB.getSupervisora().getIdSupervisora(), contexto);
		ListadoDetalle tipoDocumentoRepresentante = initializeAndUnproxy(representante.getTipoDocumento());
		representante.setTipoDocumento(tipoDocumentoRepresentante);
		supervisora.setSupervisoraRepresentante(representante);

		solicitudJasper.setSupervisora(supervisora);

		// Cargar propuesta
		Propuesta propuesta = initializeAndUnproxy(solicitudDB.getPropuesta());
		ProcesoItem procesoItem = initializeAndUnproxy(propuesta.getProcesoItem());
		Proceso proceso = initializeAndUnproxy(procesoItem.getProceso());
		ListadoDetalle sector = initializeAndUnproxy(proceso.getSector());
		ListadoDetalle subsector = initializeAndUnproxy(proceso.getSubsector());
		proceso.setSector(sector);
		proceso.setSector(subsector);
		procesoItem.setProceso(proceso);
		propuesta.setProcesoItem(procesoItem);
		solicitudJasper.setPropuesta(propuesta);

		List<SicoesTdSolPerConSec> secciones = seccionesService.obtenerSeccionesXSolicitud(solicitud.getIdSolicitud());
		secciones.forEach(seccion -> seccionesIds.add(seccion.getIdSolPerConSec()));

		List<SicoesSolicitudSeccion> requisitos = sicoesSolicitudSeccionService.obtenerRequisitosPorSecciones(seccionesIds);
		requisitos.forEach(requisito -> requisitosIds.add(requisito.getIdSolicitudSeccion()));

		List<Archivo> archivos = archivoService.obtenerArchivosPorRequisitos(requisitosIds, contexto);
		requisitos.forEach(requisito -> {
			archivos.forEach(archivoRequisito -> {
				if (requisito.getIdSolicitudSeccion().equals(archivoRequisito.getIdSeccionRequisito())) {
					requisito.setNombreArchivo(archivoRequisito.getNombreReal());
					requisito.setPeso((long) (archivoRequisito.getPeso() / 8.0 / 1024.0));
					requisito.setNroFolio(archivoRequisito.getNroFolio());
				}
			});
		});

		// Filtrar requisitos sin archivos asociados
		requisitos.removeIf(requisito ->
				archivos.stream()
						.noneMatch(archivoRequisito ->
								requisito.getIdSolicitudSeccion().equals(archivoRequisito.getIdSeccionRequisito())
						)
		);

		if (solicitud.getIdSolicitudPadre() != null) {
			requisitos.removeIf(requisito -> requisito.getProcRevision().equals("1"));
		}

		solicitudJasper.setRequisitos(requisitos);

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		JasperPrint print = null;
		InputStream appLogo = null;
		InputStream osinermingLogo = null;

		try {
			File jrxmlFile = new File(pathJasper + "Formato_22.jrxml");
			File jrxmlFile2 = new File(pathJasper + "Formato_22_Presentados.jrxml");
			JasperReport jasperReport2 = getJasperCompilado(jrxmlFile2);

			Map<String, Object> params = new HashMap<>();
			logger.info("Subreport_DIR: {}", pathJasper);
			params.put("SUBREPORT_DIR", pathJasper);

			appLogo = new FileInputStream(pathJasper + "logo-sicoes.png");
			osinermingLogo = new FileInputStream(pathJasper + "logo-osinerming.png");
			params.put("P_LOGO_APP", appLogo);
			params.put("P_LOGO_OSINERGMIN", osinermingLogo);
			List<SicoesSolicitud> solicitudes = new ArrayList<>();
			solicitudes.add(solicitudJasper);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(solicitudes);
			JasperReport jasperReport = getJasperCompilado(jrxmlFile);
			print = JasperFillManager.fillReport(jasperReport, params, ds);
			output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(print, output);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw new ValidacionException("Error al generar reporte de presentación", e);
		} finally {
			close(appLogo);
			close(osinermingLogo);
		}

		byte[] outBytes = output.toByteArray();
		archivo.setPeso(outBytes.length * 1L);
		archivo.setNroFolio(1L);
		archivo.setContenido(outBytes);
		return archivo;

	}

	public static <T> T initializeAndUnproxy(T entity) {
		if (entity == null) {
			throw new
					NullPointerException("Entity passed for initialization is null");
		}

		Hibernate.initialize(entity);
		if (entity instanceof HibernateProxy) {
			entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer()
					.getImplementation();
		}
		return entity;
	}

	public JasperReport getJasperCompilado(File path) throws JRException, FileNotFoundException {
		FileInputStream employeeReportStream = new FileInputStream(path);
		return JasperCompileManager.compileReport(employeeReportStream);
	}

	private void close(InputStream file) {
		try {
			if (file != null)
				file.close();
		} catch (Exception e) {
			logger.error("Error al cerrar el stream del logo", e);
		}
	}
}
