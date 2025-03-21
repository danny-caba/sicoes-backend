package pe.gob.osinergmin.sicoes.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import gob.osinergmin.siged.remote.rest.ro.in.*;
import gob.osinergmin.siged.remote.rest.ro.in.list.ArchivoListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.query.ClienteConsultaOutRO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowagie.text.pdf.PdfReader;

import org.springframework.web.multipart.MultipartFile;
import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.repository.ArchivoDao;
import pe.gob.osinergmin.sicoes.repository.SolicitudDao;
import pe.gob.osinergmin.sicoes.service.*;
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

	@Autowired
	private ProcesoService procesoService;

	@Autowired
	private SigedApiConsumer sigedApiConsumer;

	@Autowired
	private Environment env;
	
	@Value("${peso.acreditacion}")
	private Long pesoAcreditacion;
	
	@Value("${peso.proceso}")
	private Long pesoProceso;

	@Value("${siged.titulo.absolucion}")
	private String TITULO_ABSOLUCION;

	@Value("${siged.old.proyecto}")
	private String SIGLA_PROYECTO;

	@Value("${siged.ws.cliente.osinergmin.tipo.documento}")
	private Integer tipoDocumentoOsinergmin;

	@Value("${siged.ws.cliente.osinergmin.numero.documento}")
	private String numeroDocumentoOsinergmin;

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
				if ("application/pdf".equals(archivo.getFile().getContentType())) {
					reader = new PdfReader(archivo.getFile().getBytes());
					int count = reader.getNumberOfPages();
					archivo.setNroFolio(count * 1L);
					archivo.setPeso(archivo.getFile().getSize());
				} else if ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(archivo.getFile().getContentType()) ||
						"application/vnd.ms-excel".equals(archivo.getFile().getContentType())) {
					archivo.setNroFolio(1L);
					archivo.setPeso(archivo.getFile().getSize());
				}
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
			String nombre = sigedOldConsumer.subirArchivosAlfresco(archivoBD.getIdSolicitud(),archivoBD.getIdPropuesta(),archivoBD.getIdProceso(), archivoBD.getIdSeccionRequisito(), archivo);
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
				String nombre = sigedOldConsumer.subirArchivosAlfresco(archivoBD.getIdSolicitud(),archivoBD.getIdPropuesta(),archivoBD.getIdProceso(),archivoBD.getIdSeccionRequisito(),archivo);
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
			//Validar Archivo duplicado Para Codigo TA08 (Documento Experiencia)
			if(archivo.getTipoArchivo().getCodigo().equals(Constantes.LISTADO.TIPO_ARCHIVO.EXPERIENCIA)) {
				List<Archivo> archivosExperiencia = this.buscarArchivo(Constantes.LISTADO.TIPO_ARCHIVO.EXPERIENCIA,
						archivo.getSolicitudUuid(), null, null).getContent();
				boolean existe = archivosExperiencia.stream()
						.anyMatch(arch -> {
							try {
								arch.setContenido(sigedOldConsumer.descargarArchivosAlfresco(arch));
								if(Optional.ofNullable(archivo.getFile()).isPresent()
										&& Optional.ofNullable(arch.getContenido()).isPresent()) {
									InputStream nuevoArch = archivo.getFile().getInputStream();
									InputStream oldArch = new ByteArrayInputStream(arch.getContenido());
									boolean existeArchivo = IOUtils.contentEquals(nuevoArch, oldArch);
									nuevoArch.close();
									oldArch.close();
									return existeArchivo;
								} else {
									return false;
								}
							} catch (Exception e) {
								return false;
							}
                        });
				if(existe) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_DUPLICADO);
				}
			}
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

	@Override
	public List<File> obtenerArchivoContenidoPerfCont(List<Archivo> archivosRegistrados, SicoesSolicitud solicitud, Contexto contexto) {
		List<File> files = new ArrayList<>();
		String pathPresentacion = path + solicitud.getIdSolicitud();
		String pathSubsanacion = path + "subsanacion/" + solicitud.getIdSolicitud();
		String pathFinal = solicitud.getIdSolicitudPadre() != null ? pathSubsanacion : pathPresentacion;
		File dir = new File(pathFinal);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		for (Archivo archivo : archivosRegistrados) {
			Archivo archivoDB = archivoDao.obtener(archivo.getIdArchivo());

			byte[] contenido = sigedOldConsumer.descargarArchivosAlfresco(archivoDB);
			String pathRutaPresentacion = path;
			String pathRutaSubsanacion = path + "subsanacion";
			String pathRutaFinal = solicitud.getIdSolicitudPadre() != null ? pathRutaSubsanacion : pathRutaPresentacion;
			try {
				String ruta = pathRutaFinal + File.separator +"temporales"+ File.separator + solicitud.getIdSolicitud() + File.separator + archivo.getNombreReal();
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

	@Override
	public Page<Archivo> buscarArchivoProceso(String codigo, Long idProceso, Pageable pageable, Contexto contexto) {
		return archivoDao.buscarArchivoProceso(codigo, idProceso, pageable);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Archivo guardarEnSiged(Long idProceso, Archivo archivo, Contexto contexto) {
		boolean nuevo = archivo.getIdArchivo() == null;
		if(archivo.getSolicitudUuid() != null) {
			archivo.setIdSolicitud(solicitudService.obtenerId(archivo.getSolicitudUuid()));
		}
		if(archivo.getPropuestaUuid() != null) {
			archivo.setIdPropuesta(propuestaService.obtener(archivo.getPropuestaUuid(),contexto).getIdPropuesta());
		}
		if (nuevo) {
			cargarAbsolucion(idProceso, archivo, contexto);
			return registrar(archivo, contexto);
		} else {
			return modificar(archivo, contexto);
		}
	}

	@Override
	public Archivo obtenerArchivoXlsPorProceso(Long idProceso) {

		Archivo archivo;

		try {
			ListadoDetalle ldArchivoFormulacionConsultas = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ARCHIVO.CODIGO, Constantes.LISTADO.TIPO_ARCHIVO.INFORMACION_FORMULACION_CONSULTAS);
			archivo = archivoDao.obtenerArchivoXlsPorProceso(idProceso, ldArchivoFormulacionConsultas.getIdListadoDetalle());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_NO_ENCONTRADO);
		}

		return archivo;
	}

	@Override
	public List<Archivo> buscarPorPerfContrato(Long idPerfContrato, Contexto contexto) {
		return archivoDao.buscarPorPerfContrato(idPerfContrato);
	}

	@Override
	public Archivo guardarArchivoSubsanacionContrato(Archivo archivo, Contexto contexto) {
		return archivoDao.save(archivo);
	}

	@Override
	public List<Archivo> obtenerArchivosPorRequisitos(List<Long> requisitosIds, Contexto contexto) {
		return archivoDao.obtenerArchivosPorRequisitos(requisitosIds);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void eliminarArchivoCodigo(String codigo, Contexto contexto) {

		Archivo archivo = archivoDao.obtener(codigo);
		if (archivo == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_NO_ENCONTRADO);
		}
		try {
			Long idUsuarioCreacion = Long.parseLong(archivo.getUsuCreacion());

			if (!contexto.getUsuario().getIdUsuario().equals(idUsuarioCreacion)) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_ELIMINAR_USUARIO);
			}
		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_ELIMINAR_USUARIO);
		}
		archivoDao.deleteById(archivo.getIdArchivo());

	}

	public void cargarAbsolucion(Long idProceso, Archivo archivo, Contexto contexto) {
		Proceso procesoDB = procesoService.obtener(idProceso, contexto);
		ExpedienteInRO expedienteInRO = crearExpedienteAgregarDocumentos(procesoDB);
		List<File> archivosAlfresco = new ArrayList<>();
		try {
			archivosAlfresco.add(convertirMultipartFileAFile(archivo));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO);
		}
		try {
			DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);
			if(documentoOutRO.getResultCode()!=1) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO,documentoOutRO.getMessage());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (e instanceof ValidacionException) {
				throw (ValidacionException) e;
			}
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO);
		}
	}

	public File convertirMultipartFileAFile(Archivo archivo) throws IOException {
		MultipartFile multipartFile = archivo.getFile();

		File file = new File(multipartFile.getOriginalFilename());

		if (!file.createNewFile()) {
			logger.info("El archivo ya existe, se sobreescribirá" + file.getName());
		}

		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(multipartFile.getBytes());
		}

		return file;
	}

	public ExpedienteInRO crearExpedienteAgregarDocumentos(Proceso proceso) {
		return crearExpediente(proceso,
				Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.crear.anexo")));
	}

	public ExpedienteInRO crearExpediente(Proceso proceso, Integer tipoDocumento) {
		ExpedienteInRO expediente = new ExpedienteInRO();
		DocumentoInRO documento = new DocumentoInRO();
		ClienteListInRO clientes = new ClienteListInRO();
		ClienteInRO cs = new ClienteInRO();
		List<ClienteInRO> cliente = new ArrayList<>();
		ClienteConsultaOutRO clienteOutRO = sigedApiConsumer.buscarCliente(tipoDocumentoOsinergmin, numeroDocumentoOsinergmin);
		DireccionxClienteListInRO direcciones = new DireccionxClienteListInRO();
		DireccionxClienteInRO d = new DireccionxClienteInRO();
		List<DireccionxClienteInRO> direccion = new ArrayList<>();
		ArchivoListInRO archivos = new ArchivoListInRO();
		List<ArchivoInRO> arch = new ArrayList<>();

		expediente.setDocumento(documento);
		if (proceso.getNumeroExpediente() != null) {
			expediente.setNroExpediente(proceso.getNumeroExpediente());
		}

		documento.setAsunto(TITULO_ABSOLUCION);
		documento.setAppNameInvokes(SIGLA_PROYECTO);
		documento.setNumeroDocumento(proceso.getNumeroProceso());

		ArchivoInRO ar = new ArchivoInRO();
		ar.setDescripcion(proceso.getNombreProceso());
		arch.add(ar);
		archivos.setArchivo(arch);
		documento.setArchivos(archivos);

		cs.setCodigoTipoIdentificacion(clienteOutRO.getTipoIdentificacion());
		cs.setRazonSocial(clienteOutRO.getNombre());
		cs.setNroIdentificacion(clienteOutRO.getNroIdentificacion());
		cs.setTipoCliente(Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.cliente")));
		cliente.add(cs);
		clientes.setCliente(cliente);

		d.setDireccion("-");
		d.setDireccionPrincipal(true);
		d.setEstado(env.getProperty("crear.expediente.parametros.direccion.estado").charAt(0));
		d.setTelefono(clienteOutRO.getTelefonoOtro());
		d.setUbigeo(Integer.parseInt(env.getProperty("siged.ws.cliente.osinergmin.ubigeo")));
		direccion.add(d);
		direcciones.setDireccion(direccion);
		cs.setDirecciones(direcciones);
		documento.setClientes(clientes);
		documento.setCodTipoDocumento(tipoDocumento);
		documento.setUsuarioCreador(Integer.parseInt(env.getProperty("siged.bus.server.id.usuario")));
		documento.setEnumerado(env.getProperty("crear.expediente.parametros.enumerado").charAt(0));
		documento.setEstaEnFlujo(env.getProperty("crear.expediente.parametros.esta.en.flujo").charAt(0));
		documento.setFirmado(env.getProperty("crear.expediente.parametros.firmado").charAt(0));
		documento.setCreaExpediente(env.getProperty("crear.expediente.parametros.crea.expediente.no").charAt(0));
		documento.setNroFolios(Integer.parseInt(env.getProperty("crear.expediente.parametros.crea.folio")));
		documento.setPublico(env.getProperty("crear.expediente.parametros.crea.publico").charAt(0));

		return expediente;
	}

	private List<Archivo> buscarSolicitud(Long idPerfCont, String tipoArchivo, Contexto contexto) {
		return null;
	}

}
