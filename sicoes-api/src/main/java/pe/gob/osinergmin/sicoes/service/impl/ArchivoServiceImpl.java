package pe.gob.osinergmin.sicoes.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowagie.text.pdf.PdfReader;

import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.Documento;
import pe.gob.osinergmin.sicoes.model.OtroRequisito;
import pe.gob.osinergmin.sicoes.model.ProcesoItem;
import pe.gob.osinergmin.sicoes.model.Propuesta;
import pe.gob.osinergmin.sicoes.model.SolicitudNotificacion;
import pe.gob.osinergmin.sicoes.repository.ArchivoDao;
import pe.gob.osinergmin.sicoes.repository.SolicitudDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.PropuestaService;
import pe.gob.osinergmin.sicoes.service.SolicitudService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@Service
public class ArchivoServiceImpl implements ArchivoService {

	Logger logger = LogManager.getLogger(ArchivoServiceImpl.class);

	@Autowired
	private ArchivoDao archivoDao;
	
	@Autowired
	private SolicitudDao solicitudDao;

	@Autowired
	private SigedOldConsumer sigedOldConsumer;

	@Value("${path.temporal}")
	private String path;
	
	@Autowired
	private ListadoDetalleService listadoDetalleService;
	
	@Autowired
	private SolicitudService solicitudService;
	
	@Autowired
	private PropuestaService propuestaService;
	
	@Value("${peso.acreditacion}")
	private Long pesoAcreditacion;
	
	@Value("${peso.proceso}")
	private Long pesoProceso;

	@Override
	public Archivo obtener(Long idArchivo, Contexto contexto) {
		return archivoDao.obtener(idArchivo);
	}

	@Override
	public Archivo obtener(String codigo, Contexto contexto) {
		Archivo archivo = archivoDao.obtener(codigo);
		archivo.setContenido(sigedOldConsumer.descargarArchivosAlfresco(archivo));
		return archivo;
	}
	
	@Override
	public Page<Archivo> buscar(Pageable pageable, Contexto contexto) {
		return archivoDao.buscar(pageable);
	}

	private Archivo registrar(Archivo archivo, Contexto contexto) {
		Archivo archivoBD = null;
//		if(archivo.getIdSolicitud()==null) {
//			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_SOLICITUD_NO_ENVIADO);
//		}
		if(archivo.getIdPropuesta() != null && archivo.getIdPropuestaEconomica() != null) {
			 List<Archivo> archivos = archivoDao.listarXEconomica(archivo.getIdPropuestaEconomica());
			 if(archivos.size() >= 10) {
				 throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLO_10_ARCHIVOS);
			 }
		}
		
		if(archivo.getIdPropuesta() != null && archivo.getIdPropuestaTecnica() != null) {
			 List<Archivo> archivos = archivoDao.listarXTecnica(archivo.getIdPropuestaTecnica());
			 if(archivos.size() >= 10) {
				 throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLO_10_ARCHIVOS);
			 }
		}
		Integer tamanioByte=null;
		Double tamanioMB=null;
		try {
			if(archivo.getFile()!=null) {
				tamanioByte=archivo.getFile().getBytes().length;	
			}else {
				tamanioByte=archivo.getContenido().length;
			}
			tamanioMB=tamanioByte/(1024.0*1024.0);
			logger.info("tamanio bytes : "+tamanioByte);
			logger.info("tamanio : "+tamanioMB);			
		} catch (IOException e) {
			logger.info(e.getMessage(),e);
		}
		if(archivo.getIdPropuesta() != null) {			
			if(tamanioMB>pesoProceso) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_TAMANIO,pesoProceso);
			}	
		}
		
		if(archivo.getIdSolicitud()!=null) {			
			if(tamanioMB>pesoAcreditacion) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_TAMANIO,pesoAcreditacion);
			}	
		}
		
		AuditoriaUtil.setAuditoriaRegistro(archivo, contexto);
		if (archivo.getCodigo() == null) {
			archivo.setCodigo(UUID.randomUUID().toString());
		}
		if (archivo.getFile() != null) {
			archivo.setNombreReal(reemplazarCaracteres(archivo.getFile().getOriginalFilename()));
			archivo.setTipo(archivo.getFile().getContentType());
			if(archivo.getIdPropuesta() != null&&(archivo.getIdPropuestaEconomica()!=null||archivo.getIdPropuestaTecnica()!=null)) {
				SimpleDateFormat sdf2=new SimpleDateFormat("hhmmss");
				String hora = sdf2.format(new Date());
				String nombre = reemplazarCaracteres(archivo.getNombreReal());
				nombre=nombre.replace(".pdf", "");
				archivo.setNombreReal(nombre+"-"+hora+".pdf");
			}
			PdfReader reader;
			try {
				reader = new PdfReader(archivo.getFile().getBytes());
				int count = reader.getNumberOfPages();
				archivo.setNroFolio(count * 1L);
				archivo.setPeso(archivo.getFile().getSize());
			} catch (Exception e) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_NO_SE_PUEDE_LEER);
			}

		}
		if(archivo.getDescripcion()==null||"".equals(archivo.getDescripcion())) {
			archivo.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_ARCHIVO.CODIGO, Constantes.LISTADO.ESTADO_ARCHIVO.CARGADO));
		}else {
			archivo.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_ARCHIVO.CODIGO, Constantes.LISTADO.ESTADO_ARCHIVO.ASOCIADO));			
		}
		
		archivoBD = archivoDao.save(archivo);
		if (archivo.getFile() != null || archivo.getContenido() != null) {
			String nombre = sigedOldConsumer.subirArchivosAlfresco(archivoBD.getIdSolicitud(),archivoBD.getIdPropuesta(), archivo);
			archivo.setNombreAlFresco(nombre);
			archivoBD = archivoDao.save(archivo);
		}
		return archivoBD;
	}

	private Archivo modificar(Archivo archivo, Contexto contexto) {
		Archivo archivoBD = null;
		archivoBD = archivoDao.obtener(archivo.getIdArchivo());
		AuditoriaUtil.setAuditoriaRegistro(archivoBD, contexto);
		archivoBD.setDescripcion(archivo.getDescripcion());
		archivoBD.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_ARCHIVO.CODIGO, Constantes.LISTADO.ESTADO_ARCHIVO.ASOCIADO));			
		if (archivo.getFile() != null) {
			archivo.setNombreReal(reemplazarCaracteres(archivo.getFile().getOriginalFilename()));
			archivoBD.setNombreReal(archivo.getNombreReal());
			archivoBD.setTipo(archivo.getFile().getContentType());
			if(archivo.getIdPropuesta() != null&&(archivo.getIdPropuestaEconomica()!=null||archivo.getIdPropuestaTecnica()!=null)) {
				SimpleDateFormat sdf2=new SimpleDateFormat("hhmmss");
				String hora = sdf2.format(new Date());
				String nombre = reemplazarCaracteres(archivo.getNombreReal());
				nombre=nombre.replace(".pdf", "");
				archivoBD.setNombreReal(nombre+"-"+hora+".pdf");
			}
			PdfReader reader;
			try {
				reader = new PdfReader(archivo.getFile().getBytes());
				int count = reader.getNumberOfPages();
				archivoBD.setNroFolio(count * 1L);
				archivoBD.setPeso(archivo.getFile().getSize());
				if(archivo.getIdPropuesta() != null&&(archivo.getIdPropuestaEconomica()!=null||archivo.getIdPropuestaTecnica()!=null)) {
					SimpleDateFormat sdf2=new SimpleDateFormat("hhmmss");
					String hora = sdf2.format(new Date());
					String nombre = reemplazarCaracteres(archivo.getNombreReal());
					nombre=nombre.replace(".pdf", "");
					archivo.setNombreReal(nombre+"-"+hora+".pdf");
				}
				String nombre = sigedOldConsumer.subirArchivosAlfresco(archivoBD.getIdSolicitud(),archivoBD.getIdPropuesta(), archivo);
				archivoBD.setNombreAlFresco(nombre);
			} catch (Exception e) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_NO_SE_PUEDE_LEER);
			}

		}
		archivoDao.save(archivoBD);
		return archivoBD;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Archivo guardar(Archivo archivo, Contexto contexto) {
		boolean nuevo = archivo.getIdArchivo() == null;
		if(archivo.getSolicitudUuid() != null) {
			archivo.setIdSolicitud(solicitudService.obtenerId(archivo.getSolicitudUuid()));
		}
		if(archivo.getPropuestaUuid() != null) {
			archivo.setIdPropuesta(propuestaService.obtener(archivo.getPropuestaUuid(),contexto).getIdPropuesta());
		}
		if (nuevo) {
			return registrar(archivo, contexto);
		} else {
			return modificar(archivo, contexto);
		}
	}

	private String reemplazarCaracteres(String originalFilename) {
		if (originalFilename == null) {
			throw new NullPointerException("originalFilename es nulo");
		}
		return originalFilename.replaceAll("[^\\p{ASCII}]", "");
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		archivoDao.deleteById(id);

	}

	@Override
	public void asociarArchivos(Long idSolicitud, Long idEstudio, List<Archivo> archivos, Contexto contexto) {
		if (archivos == null) {
			return;
		}
		List<Archivo> archivosBD = archivoDao.buscarXEstudio(idEstudio);
		List<Archivo> eliminarArchivos = new ArrayList<>();
		for (Archivo archivoBD : archivosBD) {
			boolean existe = false;
			for (Archivo archivo : archivos) {
				if (archivoBD.getIdArchivo().equals(archivo.getIdArchivo())) {
					existe = true;
				}
			}
			if (!existe) {
				eliminarArchivos.add(archivoBD);
			}
		}
		Iterator<Archivo> it = eliminarArchivos.iterator();
		while (it.hasNext()) {
			Archivo archivo = it.next();
			archivoDao.delete(archivo);
		}
		for (Archivo archivo : archivos) {
			Archivo archivoBD = archivoDao.obtener(archivo.getIdArchivo());
			if (archivoBD == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_NO_ENCONTRADO);
			}
			archivoBD.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_ARCHIVO.CODIGO, Constantes.LISTADO.ESTADO_ARCHIVO.ASOCIADO));	
			archivoBD.setIdEstudio(idEstudio);
			archivoBD.setIdSolicitud(idSolicitud);
			AuditoriaUtil.setAuditoriaRegistro(archivoBD, contexto);
			archivoDao.save(archivoBD);
		}

	}

	@Override
	public List<Archivo> buscar(Long idEstudio, Long idDocumento, Long idOtroRequisito, Contexto contexto) {
		if (idEstudio != null) {
			return archivoDao.buscarXEstudio(idEstudio);
		} else if (idDocumento != null) {
			return archivoDao.buscarXDocumento(idDocumento);
		} else if (idOtroRequisito != null) {
			return archivoDao.buscarXOtroRequisito(idOtroRequisito);
		}else 
		return new ArrayList<>();
	}
	
	@Override
	public List<Archivo> buscarPropuesta(Long idPropuestaTecnica,Contexto contexto) {
		if (idPropuestaTecnica != null) {
			return archivoDao.buscarXPropuestaTecnica(idPropuestaTecnica);
		}
		return new ArrayList<>();
	}

	@Override
	public void asociarArchivo(SolicitudNotificacion solicitudNotificacion, Archivo archivo, Contexto contexto) {
		List<Archivo> archivosBD = archivoDao
				.buscarXSolicitudNotificacion(solicitudNotificacion.getIdSolNotificacion());
		Archivo archivoBD = archivoDao.obtener(archivo.getIdArchivo());
		if (archivoBD == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_NO_ENCONTRADO);
		}
		Iterator<Archivo> it = archivosBD.iterator();
		while (it.hasNext()) {
			Archivo archivoAux = it.next();
			if (!archivoAux.getIdArchivo().equals(archivoBD.getIdArchivo())) {
				archivoDao.delete(archivoAux);
			}
		}
		archivoBD.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_ARCHIVO.CODIGO, Constantes.LISTADO.ESTADO_ARCHIVO.ASOCIADO));	
		archivoBD.setIdSolicitud(solicitudNotificacion.getIdSolicitud());
		archivoBD.setIdNotificacionSolicitud(solicitudNotificacion.getIdSolNotificacion());
		AuditoriaUtil.setAuditoriaRegistro(archivoBD, contexto);
		archivoDao.save(archivoBD);
	}

	@Override
	public void asociarArchivo(Documento documento, Archivo archivo, Contexto contexto) {
		List<Archivo> archivosBD = archivoDao.buscarXDocumento(documento.getIdDocumento());
		Archivo archivoBD = archivoDao.obtener(archivo.getIdArchivo());
		if (archivoBD == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_NO_ENCONTRADO);
		}
		Iterator<Archivo> it = archivosBD.iterator();
		while (it.hasNext()) {
			Archivo archivoAux = it.next();
			if (!archivoAux.getIdArchivo().equals(archivoBD.getIdArchivo())) {
				archivoDao.delete(archivoAux);
			}
		}
		archivoBD.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_ARCHIVO.CODIGO, Constantes.LISTADO.ESTADO_ARCHIVO.ASOCIADO));	
		archivoBD.setIdSolicitud(solicitudDao.obtenerId(documento.getSolicitud().getSolicitudUuid()));
		archivoBD.setIdDocumento(documento.getIdDocumento());
		AuditoriaUtil.setAuditoriaRegistro(archivoBD, contexto);
		archivoDao.save(archivoBD);
	}
	
	@Override
	public void asociarArchivos(Long idPropuestaTecnica, List<Archivo> archivos, Contexto contexto) {
		if (archivos == null) {
			return;
		}
		List<Archivo> archivosBD = archivoDao.buscarXPropuestaTecnica(idPropuestaTecnica);
		List<Archivo> eliminarArchivos = new ArrayList<>();
		for (Archivo archivoBD : archivosBD) {
			boolean existe = false;
			for (Archivo archivo : archivos) {
				if (archivoBD.getIdArchivo().equals(archivo.getIdArchivo())) {
					existe = true;
				}
			}
			if (!existe) {
				eliminarArchivos.add(archivoBD);
			}
		}
		Iterator<Archivo> it = eliminarArchivos.iterator();
		while (it.hasNext()) {
			Archivo archivo = it.next();
			archivoDao.delete(archivo);
		}
		for (Archivo archivo : archivos) {
			Archivo archivoBD = archivoDao.obtener(archivo.getIdArchivo());
			if (archivoBD == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_NO_ENCONTRADO);
			}
			archivoBD.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_ARCHIVO.CODIGO, Constantes.LISTADO.ESTADO_ARCHIVO.ASOCIADO));	
			archivoBD.setIdPropuestaTecnica(idPropuestaTecnica);
			AuditoriaUtil.setAuditoriaRegistro(archivoBD, contexto);
			archivoDao.save(archivoBD);
		}
	}

	@Override
	public void asociarArchivo(OtroRequisito otroRequisito, Archivo archivo, Contexto contexto) {
		List<Archivo> archivosBD = archivoDao.buscarXOtroRequisito(otroRequisito.getIdOtroRequisito());
		Archivo archivoBD = archivoDao.obtener(archivo.getIdArchivo());
		if (archivoBD == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_NO_ENCONTRADO);
		}
		Iterator<Archivo> it = archivosBD.iterator();
		while (it.hasNext()) {
			Archivo archivoAux = it.next();
			if (!archivoAux.getIdArchivo().equals(archivoBD.getIdArchivo())) {
				archivoDao.delete(archivoAux);
			}
		}
		archivoBD.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_ARCHIVO.CODIGO, Constantes.LISTADO.ESTADO_ARCHIVO.ASOCIADO));	
		archivoBD.setIdSolicitud(solicitudDao.obtenerId(otroRequisito.getSolicitud().getSolicitudUuid()));
		archivoBD.setIdOtroRequisito(otroRequisito.getIdOtroRequisito());
		AuditoriaUtil.setAuditoriaRegistro(archivoBD, contexto);
		archivoDao.save(archivoBD);

	}

	@Override
	public List<Archivo> buscar(Long idSolicitud, Contexto contexto) {
		return archivoDao.buscarXSolicitud(idSolicitud,Constantes.LISTADO.ESTADO_ARCHIVO.ASOCIADO);
	}
	
	@Override
	public List<Archivo> buscarPresentacion(Long idSolicitud,String tipoArchivo, Contexto contexto) {
		List<Archivo> archivo1 = archivoDao.obtenerArchivoDocumentoPendiente(idSolicitud);
		List<Archivo> archivo2 = archivoDao.obtenerArchivoEstudioPendiente(idSolicitud);
		List<Archivo> archivo3 = archivoDao.obtenerArchivoOtroRequisitoPendiente(idSolicitud);
		List<Archivo> archivo4 = archivoDao.obtenerArchivoOtrosPendiente(idSolicitud, tipoArchivo);
		List<Archivo> archivos = new ArrayList<>();
		if (!archivo1.isEmpty()) {
			archivos.addAll(archivo1);
		}
		if (!archivo2.isEmpty()) {
			archivos.addAll(archivo2);
		}
		if (!archivo3.isEmpty()) {
			archivos.addAll(archivo3);
		}
		if (!archivo4.isEmpty()) {
			archivos.addAll(archivo4);
		}
		return archivos;
	}


	@Override
	public List<File> obtenerArchivoContenido(Long idSolicitud, String tipoArchivo, Contexto contexto) {
		List<Archivo> archivos = buscarPresentacion( idSolicitud, tipoArchivo,  contexto);

		List<File> files = new ArrayList<>();
		File dir = new File(path + idSolicitud);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		for (Archivo archivo : archivos) {
			byte[] contenido = sigedOldConsumer.descargarArchivosAlfresco(archivo);
			try {
				String ruta=path + File.separator +"temporales"+ File.separator + idSolicitud + File.separator + archivo.getNombre();
				logger.info(ruta);
				File file = new File(ruta);
				FileUtils.writeByteArrayToFile(file, contenido);
				files.add(file);
				logger.info(file.getName());
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_PROBLEMA_DESCARGAR_ALFRESCO, e);
			}
		}
		return files;
	}

	public Page<Archivo> buscarArchivo(String codigo, String solicitudUuid, Pageable pageable, Contexto contexto) {
		Long idSolicitud = solicitudService.obtenerId(solicitudUuid);
		return archivoDao.buscarArchivo(codigo, idSolicitud, pageable);

	}

	@Override
	public List<Archivo> buscarArchivo(SolicitudNotificacion solicitudNotificacion, Contexto contexto) {
		return archivoDao.buscarXSolicitudNotificacion(solicitudNotificacion.getIdSolNotificacion());
	}

	@Override
	public Archivo obtenerTipoArchivo(Long idSolicitud, String formato) {
		return archivoDao.obtenerTipoArchivo(idSolicitud, formato);
	}

	@Override
	public void eliminarIdEstudio(Long idEstudio, Contexto contexto) {
		archivoDao.eliminarIdEstudio(idEstudio);

	}

	@Override
	public void eliminarIdOtroRequisito(Long idOtroRequisito, Contexto contexto) {
		archivoDao.eliminarIdOtroRequisito(idOtroRequisito);
	}

	@Override
	public void eliminarIdDocumento(Long idDocumento, Contexto contexto) {
		archivoDao.eliminarIdDocumento(idDocumento);

	}

	@Override
	public List<Archivo> listarEvidencias(Long idSolicitud) {
		return archivoDao.buscarArchivo(Constantes.LISTADO.TIPO_ARCHIVO.EVIDENCIA, idSolicitud);
	}

	@Override
	public void eliminarXIDSolicitud(Long idSolicitud) {
		archivoDao.eliminarXIDSolicitud(idSolicitud);

	}

	@Override
	public List<File> obtenerArchivosContenido(Long idNotificacion) {
		List<Archivo> archivos = archivoDao.obtenerArchivoIdNotificacion(idNotificacion);		
		List<File> files = new ArrayList<>();
		for (Archivo archivo : archivos) {		
			File dir = new File(path + archivo.getIdSolicitud());
			if (!dir.exists()) {
				dir.mkdirs();
			}
			byte[] contenido = sigedOldConsumer.descargarArchivosAlfresco(archivo);
			try {
				String ruta=path+File.separator+"temporales"+File.separator + archivo.getIdSolicitud() + File.separator + archivo.getNombre();
				logger.info(ruta);
				File file = new File(ruta);
				FileUtils.writeByteArrayToFile(file, contenido);
				files.add(file);
				logger.info(file.getName());
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_PROBLEMA_DESCARGAR_ALFRESCO, e);
			}
		}
		return files;
	}

	@Override
	public List<Object[]> buscarPropuesta(String procesoItemUuid, Contexto contexto) {
		List<Object[]> archivosEco = archivoDao.buscarXPropuestaEconomica(procesoItemUuid);
		List<Object[]> archivosTec = archivoDao.buscarXPropuestaTecnica(procesoItemUuid); 
		
		List<Object[]> union =  new ArrayList<Object[]>();
		union.addAll(archivosTec);
		union.addAll(archivosEco);
		
		return  union;
	}

	@Override
	public Page<Archivo> buscarArchivoPropuestaEconomica(String codigo, String propuestaUuid, Pageable pageable,
			Contexto contexto) {
		
		Long idPropuestaEconomica = propuestaService.obtenerIdPropuestaEconomica(propuestaUuid);
		
		return archivoDao.buscarArchivoPropuesta(codigo, idPropuestaEconomica, null, pageable);
	}

	@Override
	public Page<Archivo> buscarArchivoPropuestaTecnica(String codigo, String propuestaUuid, Pageable pageable,
			Contexto contexto) {
		Long idPropuestaTecnica = propuestaService.obtenerIdPropuestaTecnica(propuestaUuid);
		return archivoDao.buscarArchivoPropuesta(codigo, null, idPropuestaTecnica, pageable);
	}
	
	@Override
	public List<Archivo> listarXEconomica(String propuestaUuid, Contexto contexto) {
		Long idPropuestaEconomica = propuestaService.obtenerIdPropuestaEconomica(propuestaUuid);
		return archivoDao.listarXEconomica(idPropuestaEconomica);
	}

	@Override
	public List<Archivo> listarXTecnica(String propuestaUuid, Contexto contexto) {
		Long idPropuestaTecnica = propuestaService.obtenerIdPropuestaTecnica(propuestaUuid);
		return archivoDao.listarXTecnica(idPropuestaTecnica);
	}

	@Override
	public String generarArchivoContenido(Propuesta propuesta, Contexto contexto) {
		String pathSalida=path + "propuesta"+File.separator+propuesta.getIdPropuesta();
		generarArchivoContenidoAuxiliar(propuesta,pathSalida);
		return pathSalida;
	}
	
	public void guardar(String pathSalida,List<Archivo> archivos) {
		for (Archivo archivo : archivos) {
			String nombre="";
			if(archivo.getNombre()!=null) {
				nombre=archivo.getNombre();
			}else {
				nombre=archivo.getNombreReal()+".pdf";
			}
			
			File dir = new File(pathSalida );
			if (!dir.exists()) {
				dir.mkdirs();
			}
			byte[] contenido = sigedOldConsumer.descargarArchivosAlfresco(archivo);
			try {
				String ruta=pathSalida + File.separator +nombre;
				logger.info(ruta);
				File file = new File(ruta);
				FileUtils.writeByteArrayToFile(file, contenido);
				logger.info(file.getName());
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_PROBLEMA_DESCARGAR_ALFRESCO, e);
			}
		}
	}
	
	public String generarArchivoContenidoAuxiliar(Propuesta propuesta,String pathSalida) {
		List<Archivo> archivosTecnicos = archivoDao.obtenerArchivoPropuestaTecnica(propuesta.getPropuestaTecnica().getIdPropuestaTecnica());	
		List<Archivo> archivosEconomicos = archivoDao.obtenerArchivoPropuestaEconomica(propuesta.getPropuestaEconomica().getIdPropuestaEconomica());
		guardar(pathSalida+File.separator+"Economicos",archivosEconomicos);
		guardar(pathSalida+File.separator+"Tecnicos",archivosTecnicos);
		return pathSalida;
	}
	
	@Override
	public String generarArchivoContenido(ProcesoItem procesoItem, Contexto contexto) {
		String pathSalida = path + "procesoItem"+File.separator+procesoItem.getIdProcesoItem();
		List<Propuesta> listPropuesta=propuestaService.obtenerTodasPropuestaXProcesoItem(procesoItem.getIdProcesoItem(), contexto);
		for(Propuesta propuesta:listPropuesta) {
			generarArchivoContenidoAuxiliar(propuesta,pathSalida+File.separator+propuesta.getSupervisora().getNumeroDocumento());	
		}
		return pathSalida;
	}

	@Override
	public Archivo obtenerTipoArchivo(Long idSolicitud, String codigoFormato, Long idAsignacion) {
		return archivoDao.obtenerTipoArchivo(idSolicitud, codigoFormato,idAsignacion);
	}

	@Override
	public List<Archivo> obtenerDocumentoTecnicosPendientes(Contexto contexto) {
		return archivoDao.obtenerDocumentoTecnicosPendientes();
	}

	@Override
	public void actualizarEstado(Archivo archivo,Long estado, Contexto contexto) {
		 archivoDao.actualizarEstado(archivo.getIdArchivo(),estado);
	}

	@Override
	public Archivo obtenerContenido(Long idArhivo, Contexto contexto) {
		Archivo archivo = archivoDao.obtener(idArhivo);
		archivo.setContenido(sigedOldConsumer.descargarArchivosAlfresco(archivo));
		return archivo;
	}

}
