package pe.gob.osinergmin.sicoes.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import gob.osinergmin.sne.util.StringUtil;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.ProcesoItem;
import pe.gob.osinergmin.sicoes.model.Propuesta;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.ProcesoItemService;
import pe.gob.osinergmin.sicoes.service.PropuestaService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Raml;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@RestController
@RequestMapping("/api")
public class ArchivoRestController extends BaseRestController {

	private Logger logger = LogManager.getLogger(ArchivoRestController.class);

	@Autowired
	private ArchivoService archivoService;

	@Autowired
	private ListadoDetalleService listadoDetalleService;

	@Autowired
	private PropuestaService propuestaService;

	@Autowired
	private ProcesoItemService procesoItemService;

	@Autowired
	private SigedOldConsumer sigedOldConsumer;

	@Value("${path.formato}")
	String PATH_FORMATO;

	@GetMapping("/archivos/{id}")
	@Raml("adjunto.obtener.properties")
	public Archivo obtener(@PathVariable Long id) {
		return archivoService.obtener(id, getContexto());
	}

	@GetMapping("/archivos")
	public Page<Archivo> buscar(@RequestParam(value = "codigo", required = false) String codigo,
			@RequestParam(value = "solicitudUuid", required = false) String solicitudUuid, Pageable pageable) {
		return archivoService.buscarArchivo(codigo, solicitudUuid, pageable, getContexto());
	}

	@GetMapping("/archivos/propuestaTecnica")
	public Page<Archivo> buscarPropuestaTecnica(@RequestParam(value = "codigo", required = false) String codigo,
			@RequestParam(value = "propuestaUuid", required = false) String propuestaUuid, Pageable pageable) {
		return archivoService.buscarArchivoPropuestaTecnica(codigo, propuestaUuid, pageable, getContexto());
	}

	@GetMapping("/archivos/propuestaEconomica")
	public Page<Archivo> buscarPropuestaEconomica(@RequestParam(value = "codigo", required = false) String codigo,
			@RequestParam(value = "propuestaUuid", required = false) String propuestaUuid, Pageable pageable) {
		return archivoService.buscarArchivoPropuestaEconomica(codigo, propuestaUuid, pageable, getContexto());
	}

	@GetMapping("/archivos/proceso")
	public Page<Archivo> buscarProceso(@RequestParam(value = "codigo", required = false) String codigo,
			@RequestParam(value = "idProceso", required = false) Long idProceso, Pageable pageable) {
		return archivoService.buscarArchivoProceso(codigo, idProceso, pageable, getContexto());
	}

	@PostMapping(value = "/archivos", consumes = { "multipart/form-data" })
	@Raml("adjunto.obtener.properties")
	public Archivo registrar(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "codigo", required = false) String codigo,
			@RequestParam(value = "descripcion", required = false) String descripcion,
			@RequestParam(value = "solicitudUuid", required = false) String solicitudUuid,
			@RequestParam(value = "propuestaUuid", required = false) String propuestaUuid,
			@RequestParam(value = "idPropuestaEconomica", required = false) Long idPropuestaEconomica,
			@RequestParam(value = "idPropuestaTecnica", required = false) Long idPropuestaTecnica,
			@RequestParam(value = "idArchivo", required = false) Long idArchivo,
			@RequestParam(value = "idProceso", required = false) Long idProceso,
			@RequestParam(value = "idSolicitudSeccion", required = false) Long idSolicitudSeccion) {

		Archivo archivo = new Archivo();

		if (idArchivo == null) {
			if (file == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_SUBIR_ARCHIVO);
			}
			if (StringUtil.isEmpty(codigo)) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_CODIGO_REQUERIDO);
			}

		}
		if (file != null) {
			archivo.setFile(file);
		}
		if (!"".equals(codigo) && codigo != null) {
			ListadoDetalle tipoArchivo;
			if (idSolicitudSeccion == null) {
				tipoArchivo = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
						codigo);
			} else {
				tipoArchivo = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
						Constantes.LISTADO.TIPO_ARCHIVO.PERFECCIONAMIENTO_CONTRATO);
			}
			archivo.setTipoArchivo(tipoArchivo);
		}
		archivo.setIdArchivo(idArchivo);
		archivo.setDescripcion(descripcion);
		archivo.setSolicitudUuid(solicitudUuid);
		archivo.setPropuestaUuid(propuestaUuid);
		archivo.setIdPropuestaEconomica(idPropuestaEconomica);
		archivo.setIdPropuestaTecnica(idPropuestaTecnica);
		archivo.setIdProceso(idProceso);
		archivo.setIdSeccionRequisito(idSolicitudSeccion);
		Archivo value = archivoService.guardar(archivo, getContexto());
		value.setFile(null);

		return value;
	}

	@PutMapping(value = "/archivos/{id}", consumes = { "multipart/form-data" })
	@Raml("adjunto.obtener.properties")
	public Archivo modificar(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "codigo", required = false) String codigo,
			@RequestParam(value = "descripcion", required = false) String descripcion,
			@RequestParam(value = "solicitudUuid", required = false) String solicitudUuid,
			@RequestParam(value = "idPropuestaEconomica", required = false) Long idPropuestaEconomica,
			@RequestParam(value = "idPropuestaTecnica", required = false) Long idPropuestaTecnica,
			@RequestParam(value = "idProceso", required = false) Long idProceso, @PathVariable Long id) {

		Archivo archivo = new Archivo();
		archivo.setIdArchivo(id);
		archivo.setFile(file);
		archivo.setDescripcion(descripcion);
		archivo.setSolicitudUuid(solicitudUuid);
		archivo.setIdPropuestaEconomica(idPropuestaEconomica);
		archivo.setIdPropuestaTecnica(idPropuestaTecnica);
		archivo.setIdProceso(idProceso);
		Archivo value = archivoService.guardar(archivo, getContexto());
		value.setFile(null);

		return value;
	}

	@GetMapping("/archivos/{uuid}/descarga")
	public ResponseEntity<Object> download(@PathVariable String uuid, HttpServletResponse response) {
		Archivo archivo = archivoService.obtener(uuid, getContexto());
		download(archivo, response);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/formato/{nombre}/descarga")
	public ResponseEntity<Object> descargarFormato(@PathVariable String nombre, HttpServletResponse response) {
		try {
			File file = new File(PATH_FORMATO + nombre);
			if (nombre.endsWith(".doc")) {
				response.setContentType("application/msword");
			} else if (nombre.endsWith(".docx")) {
				response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			} else if (nombre.endsWith(".pdf")) {
				response.setContentType("application/pdf");
			}
			response.setHeader("Content-Disposition", "attachment; filename=\"" + nombre + "\"");
			IOUtils.copy(new ByteArrayInputStream(FileUtils.readFileToByteArray(file)), response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/formato-publico/{nombre}/descarga")
	public ResponseEntity<Object> descargarFormatoPublico(@PathVariable String nombre, HttpServletResponse response) {
		try {
			File file = new File(PATH_FORMATO + nombre);
			if (nombre.endsWith(".doc")) {
				response.setContentType("application/msword");
			} else if (nombre.endsWith(".docx")) {
				response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			} else if (nombre.endsWith(".pdf")) {
				response.setContentType("application/pdf");
			}
			response.setHeader("Content-Disposition", "attachment; filename=\"" + nombre + "\"");
			IOUtils.copy(new ByteArrayInputStream(FileUtils.readFileToByteArray(file)), response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	public void download(Archivo archivo, HttpServletResponse response) {
		try {
			response.setContentType(archivo.getTipo());
			response.setHeader("Content-Disposition", "attachment; filename=\"" + archivo.getNombre() + "\"");
			IOUtils.copy(new ByteArrayInputStream(archivo.getContenido()), response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@GetMapping("/archivos/propuesta-zip")
	public ResponseEntity<Object> buscarPropuesta(
			@RequestParam(value = "propuestaUuid", required = false) String propuestaUuid,
			HttpServletResponse response) {
		try {
			Propuesta propuesta = propuestaService.obtener(propuestaUuid, getContexto());
			File file = new File(propuesta.getRutaDescarga());
			response.setContentType("application/zip");
			response.setHeader("Content-Disposition", "attachment; filename=propuesta.zip");
			IOUtils.copy(new ByteArrayInputStream(FileUtils.readFileToByteArray(file)), response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/archivos/procesoItem-zip")
	public ResponseEntity<Object> buscarItem(
			@RequestParam(value = "procesoItemUuid", required = false) String procesoItemUuid,
			HttpServletResponse response) {
		try {
			ProcesoItem procesoItem = procesoItemService.obtener(procesoItemUuid, getContexto());
			File file = new File(procesoItem.getRutaDescarga());
			response.setContentType("application/zip");
			response.setHeader("Content-Disposition", "attachment; filename=procesoItem.zip");
			IOUtils.copy(new ByteArrayInputStream(FileUtils.readFileToByteArray(file)), response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/archivos/{id}")
	public void eliminar(@PathVariable Long id) {
		logger.info("eliminar {} ", id);
		archivoService.eliminar(id, getContexto());
	}

	@GetMapping("/archivos/procesos/{idProceso}")
	public Archivo obtenerArchivoXlsPorProceso(@PathVariable Long idProceso) {
		return archivoService.obtenerArchivoXlsPorProceso(idProceso);
	}

	@DeleteMapping("/archivos/codigo/{codigo}")
	public void eliminarPorCodigo(@PathVariable String codigo) {
		logger.info("eliminar {} ", codigo);
		archivoService.eliminarArchivoCodigo(codigo, getContexto());
	}

	
	@PostMapping(value = "/contratos/{idContrato}/archivos", consumes = { "multipart/form-data" })
	public ResponseEntity<?> subirArchivoContrato(@PathVariable("idContrato") Long idContrato,
			@RequestParam("file") MultipartFile file, @RequestParam("tipoRequisito") String tipoRequisito) {
		try {
			if (file == null || file.isEmpty()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_NO_ENVIADO);
			}
			if (StringUtil.isEmpty(tipoRequisito)) {
				throw new ValidacionException("El tipo de requisito es obligatorio.");
			}

			Archivo archivoGuardado = archivoService.guardarArchivoContrato(idContrato, tipoRequisito, file,
					getContexto());
			return new ResponseEntity<>(archivoGuardado, HttpStatus.CREATED);
		} catch (ValidacionException e) {
			logger.error("Error de validación al subir archivo para el contrato {}: {}", idContrato, e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Error interno al subir archivo para el contrato {}: {}", idContrato, e.getMessage(), e);
			return new ResponseEntity<>("Error interno del servidor al subir el archivo.",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/contratos/{idContrato}/archivos")
	public ResponseEntity<?> listarArchivosPorContrato(@PathVariable("idContrato") Long idContrato) {
		try {
			List<Archivo> archivos = archivoService.obtenerArchivosPorContrato(idContrato);
			return new ResponseEntity<>(archivos, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error interno al listar archivos para el contrato {}: {}", idContrato, e.getMessage(), e);
			return new ResponseEntity<>("Error interno del servidor al listar los archivos.",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/contratos/{idContrato}/archivos/{idArchivo}")
	public ResponseEntity<?> eliminarArchivoContrato(@PathVariable("idContrato") Long idContrato, 
			@PathVariable("idArchivo") Long idArchivo) {
		try {
			archivoService.eliminarArchivo(idArchivo); 
			return new ResponseEntity<>("Archivo eliminado exitosamente.", HttpStatus.OK); 
		} catch (ValidacionException e) {
			logger.error("Error de validación al eliminar archivo con ID {} del contrato {}: {}", idArchivo, idContrato,
					e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error("Error interno al eliminar archivo con ID {} del contrato {}: {}", idArchivo, idContrato,
					e.getMessage(), e);
			return new ResponseEntity<>("Error interno del servidor al eliminar el archivo.",
					HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}

	@GetMapping("/contratos/{idContrato}/archivos/{idArchivo}/descargar")
	public ResponseEntity<Object> descargarArchivoContrato(@PathVariable("idContrato") Long idContrato,
			@PathVariable("idArchivo") Long idArchivo, HttpServletResponse response) {
		try {

			Archivo archivo = archivoService.obtener(idArchivo, getContexto());

			if (archivo == null) {
				return new ResponseEntity<>("Archivo no encontrado.", HttpStatus.NOT_FOUND);
			}

			if (archivo.getContenido() == null) {

				return new ResponseEntity<>("El contenido del archivo no está disponible.",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}

			response.setContentType(archivo.getTipo());
			response.setHeader("Content-Disposition", "attachment; filename=\"" + archivo.getNombreReal() + "\"");
			IOUtils.copy(new ByteArrayInputStream(archivo.getContenido()), response.getOutputStream());
			response.flushBuffer();
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ValidacionException e) {
			logger.error("Error de validación al descargar archivo con ID {} del contrato {}: {}", idArchivo,
					idContrato, e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Error al descargar archivo con ID {} del contrato {}: {}", idArchivo, idContrato,
					e.getMessage(), e);
			return new ResponseEntity<>("Error interno del servidor al descargar el archivo.",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/perfcontratos/{idSoliPerfCont}/archivos", consumes = { "multipart/form-data" })
	public ResponseEntity<?> subirArchivoPerfContrato(@PathVariable("idSoliPerfCont") Long idSoliPerfCont,
			@RequestParam("file") MultipartFile file, @RequestParam("tipoRequisito") String tipoRequisito) {
		try {
			if (file == null || file.isEmpty()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_NO_ENVIADO);
			}
			if (StringUtil.isEmpty(tipoRequisito)) {
				throw new ValidacionException("El tipo de requisito es obligatorio.");
			}

			Archivo archivoGuardado = archivoService.guardarArchivoPerfContrato(idSoliPerfCont, tipoRequisito, file,
					getContexto());
			return new ResponseEntity<>(archivoGuardado, HttpStatus.CREATED); // 201 Created
		} catch (ValidacionException e) {
			logger.error("Error de validación al subir archivo para el contrato {}: {}", idSoliPerfCont, e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 Bad Request
		} catch (Exception e) {
			logger.error("Error interno al subir archivo para el contrato {}: {}", idSoliPerfCont, e.getMessage(), e);
			return new ResponseEntity<>("Error interno del servidor al subir el archivo.",
					HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
		}
	}

	@GetMapping("/perfcontratos/{idSoliPerfCont}/archivos")
	public ResponseEntity<?> listarArchivosPorPerfContrato(@PathVariable("idSoliPerfCont") Long idSoliPerfCont) {
		try {
			List<Archivo> archivos = archivoService.obtenerArchivosPorPerfContrato(idSoliPerfCont);
			return new ResponseEntity<>(archivos, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error interno al listar archivos para el contrato {}: {}", idSoliPerfCont, e.getMessage(), e);
			return new ResponseEntity<>("Error interno del servidor al listar los archivos.",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/perfcontratos/{idSoliPerfCont}/archivos/{idArchivo}")
	public ResponseEntity<?> eliminarArchivoPerfContrato(@PathVariable("idSoliPerfCont") Long idSoliPerfCont, 
			@PathVariable("idArchivo") Long idArchivo) {
		try {
			archivoService.eliminarArchivo(idArchivo); 
			return new ResponseEntity<>("Archivo eliminado exitosamente.", HttpStatus.OK); 
		} catch (ValidacionException e) {
			logger.error("Error de validación al eliminar archivo con ID {} del contrato {}: {}", idArchivo, idSoliPerfCont,
					e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error("Error interno al eliminar archivo con ID {} del contrato {}: {}", idArchivo, idSoliPerfCont,
					e.getMessage(), e);
			return new ResponseEntity<>("Error interno del servidor al eliminar el archivo.",
					HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}

	@GetMapping("/perfcontratos/{idSoliPerfCont}/archivos/{idArchivo}/descargar")
	public ResponseEntity<Object> descargarArchivoPerfContrato(@PathVariable("idSoliPerfCont") Long idSoliPerfCont,
			@PathVariable("idArchivo") Long idArchivo, HttpServletResponse response) {
		try {

			Archivo archivo = archivoService.obtener(idArchivo, getContexto());

			if (archivo == null) {
				return new ResponseEntity<>("Archivo no encontrado.", HttpStatus.NOT_FOUND);
			}

			if (archivo.getContenido() == null) {

				return new ResponseEntity<>("El contenido del archivo no está disponible.",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}

			response.setContentType(archivo.getTipo());
			response.setHeader("Content-Disposition", "attachment; filename=\"" + archivo.getNombreReal() + "\"");
			IOUtils.copy(new ByteArrayInputStream(archivo.getContenido()), response.getOutputStream());
			response.flushBuffer();
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ValidacionException e) {
			logger.error("Error de validación al descargar archivo con ID {} del contrato {}: {}", idArchivo,
					idSoliPerfCont, e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Error al descargar archivo con ID {} del contrato {}: {}", idArchivo, idSoliPerfCont,
					e.getMessage(), e);
			return new ResponseEntity<>("Error interno del servidor al descargar el archivo.",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/archivos/documento-detalle/{requerimientoDocumentoDetalleUuid}")
	@Raml("adjunto.obtener.properties")
	public Archivo obtenerArchivoPorReqDocumentoDetalle(@PathVariable String requerimientoDocumentoDetalleUuid) {
		return archivoService.obtenerArchivoPorReqDocumentoDetalle(requerimientoDocumentoDetalleUuid, getContexto());
	}


	@GetMapping("/archivos/{uuid}/visualizar")
	public ResponseEntity<Object> visualizarArchivo(@PathVariable String uuid, HttpServletResponse response) {
		try {
			Archivo archivo = archivoService.obtener(uuid, getContexto());
			byte[] data = sigedOldConsumer.descargarArchivosAlfresco(archivo);
			response.setContentType(archivo.getTipo());
			response.setHeader("Content-Disposition", "inline; filename=\"" + archivo.getNombreReal() + "\"");
			IOUtils.copy(new ByteArrayInputStream(data), response.getOutputStream());
			response.flushBuffer();

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error al visualizar archivo con UUID {}: {}", uuid, e.getMessage(), e);
			return new ResponseEntity<>("Error al visualizar el archivo.",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
