package pe.gob.osinergmin.sicoes.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitudSeccion;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.service.*;
import pe.gob.osinergmin.sicoes.util.Raml;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@RestController
@RequestMapping("/api")
public class SicoesSolicitudesRestController extends BaseRestController {

	private Logger logger = LogManager.getLogger(SicoesSolicitudesRestController.class);

	@Autowired
	private SicoesSolicitudService sicoesSolicitudService;

	@Autowired
	private SicoesSolicitudSeccionService sicoesSolicitudSeccionService;

	@Autowired
	private ListadoDetalleService listadoDetalleService;

	@Autowired
	private EmpresasSancionadaService empresasSancionadaService;

	@Autowired
	private SupervisoraService supervisoraService;

	@GetMapping("/solicitudes/{idSolicitud}/proceso")
	@Raml("sicoesSolicitud.obtener.properties")
	public SicoesSolicitud obtenerSolicitudPorId(@PathVariable Long idSolicitud) {
		logger.info("obtenerSolicitud");
		return sicoesSolicitudService.obtener(idSolicitud, getContexto());
	}

	@GetMapping("/solicitudes/presentacion")
	@Raml("sicoesSolicitud.listar.properties")
	public Page<SicoesSolicitud> listarSolicitudesPresentacion(
			@RequestParam(required = false) String nroConcurso,
			@RequestParam(required = false) Long item,
			@RequestParam(required = false) String convocatoria,
			@RequestParam(required = false) String estado,
			@RequestParam(required = false) String tipoSolicitud,
			Pageable pageable
	) {
		logger.info("listarItemsExterno");
		return sicoesSolicitudService.listarSolicitudesPresentacion(estado, nroConcurso, item, convocatoria, tipoSolicitud, pageable, getContexto());
	}

	@GetMapping("/solicitudes/proceso")
	@Raml("sicoesSolicitud.listar.properties")
	public Page<SicoesSolicitud> listarSolicitudesProceso(
			@RequestParam(required = false) String nroConcurso,
			@RequestParam(required = false) Long item,
			@RequestParam(required = false) String convocatoria,
			@RequestParam(required = false) String estado,
			@RequestParam(required = false) String tipoSolicitud,
			Pageable pageable
	) {
		logger.info("listarItemsInterno");
		return sicoesSolicitudService.listarSolicitudesProceso(estado, nroConcurso, item, convocatoria, tipoSolicitud, pageable, getContexto());
	}

	@GetMapping("/solicitud/{idSolicitud}/validar-fecha-presentacion")
	public boolean validarFechaPresentacionSubsanacion(@PathVariable Long idSolicitud) {
		logger.info("validar-fecha-presentacion");
		return sicoesSolicitudService.validarFechaPresentacionSubsanacion(idSolicitud, getContexto());
	}

	@PutMapping("/solicitudes/{idSolicitud}/{tipoContratoSeleccionado}/registrar")
//	@Raml("sicoesSolicitud.obtener.properties")
	public String registrarPerfeccionamiento(@RequestBody List<SicoesSolicitudSeccion> listaSolicitudSeccion, @PathVariable Long idSolicitud, @PathVariable Long tipoContratoSeleccionado) {
		logger.info("registrarPerfeccionamiento {} {}", idSolicitud);

		boolean estaEnFecha = sicoesSolicitudService.validarFechaPresentacionSubsanacion(idSolicitud, getContexto());

		if (!estaEnFecha) {
			throw new ValidacionException("No se puede registrar la solicitud, la fecha de presentación/subsanación ha vencido.");
		}

		try {

			SicoesSolicitud solicitud = sicoesSolicitudService.obtener(idSolicitud, getContexto());
			ListadoDetalle tipoContratacion = listadoDetalleService.obtener(tipoContratoSeleccionado, getContexto());

			if (solicitud == null) {
				throw new ValidacionException("No se encontró la solicitud con id " + idSolicitud);
			}

			if (tipoContratacion == null) {
				throw new ValidacionException("No se encontró el tipo de contrato con id " + tipoContratoSeleccionado);
			}

			solicitud.setTipoContratacion(tipoContratacion);
			return sicoesSolicitudService.actualizarSolicitud(listaSolicitudSeccion, solicitud, getContexto());

		} catch (ValidacionException e) {
			throw e;
		}
	}

	@PutMapping("/solicitudes/{idSolicitud}/finalizar")
	@Raml("sicoesSolicitud.obtener.properties")
	public SicoesSolicitud finalizarEvaluacion(@PathVariable Long idSolicitud, @RequestBody List<SicoesSolicitudSeccion> listaSolicitudSeccion) {
		logger.info("finalizarEvaluacion {}", idSolicitud);

		SicoesSolicitud solicitud = sicoesSolicitudService.obtener(idSolicitud, getContexto());

		if (solicitud == null) {
			throw new ValidacionException("No se encontró la solicitud con id " + idSolicitud);
		}

		return sicoesSolicitudService.finalizarSolicitud(solicitud, listaSolicitudSeccion, getContexto());
	}

	@PostMapping("/solicitudes/{idSolicitud}/enviar-correo-sancion")
	public boolean enviarCorreoSancion(
		@PathVariable Long idSolicitud,
		@RequestBody Map<String, Object> datos
	) {
		logger.info("enviarCorreoSancion {}", idSolicitud);

		SicoesSolicitud solicitud = sicoesSolicitudService.obtener(idSolicitud, getContexto());

		if (solicitud == null) {
			throw new ValidacionException("No se encontró la solicitud con id " + idSolicitud);
		}

		sicoesSolicitudService.enviarCorreoSancion(solicitud, datos.get("periodoInhabilitacion").toString(), datos.get("fechaInicio").toString(), datos.get("fechaFin").toString(), getContexto());

		return true;
	}

	@PostMapping("/solicitudes/enviar-correo-sancion-pn/{ruc}")
	public boolean enviarCorreoSancionPN(
			@PathVariable String ruc,
			@RequestBody Map<String, Object> datos) {
		logger.info("enviarCorreoSancionPN {}");

		Supervisora supervisora = supervisoraService.obtenerSupervisoraXRUC(ruc);

		if (supervisora == null) {
			throw new ValidacionException("No se encontró la supervisora");
		}

		sicoesSolicitudService.	enviarCorreoSancionPN(datos, supervisora, getContexto());

		return true;
	}

	@GetMapping("/solicitudes/sancion-vigente/V2")
	public Map<String,String> ValidadSancionV2(@RequestParam(required=false) String ruc){
		Map<String,String> valor=new HashMap<>();
		String documento = getContexto().getUsuario().getCodigoRuc();
		documento = documento.trim();
		if (documento.startsWith("20") && ruc == null) {
//			valor.put("respuesta", empresasSancionadaService.validadSancionv2(documento));
			List<String[]> resultados = empresasSancionadaService.validadSancionV2(documento);
			resultados.forEach((resultado) -> {
				if (resultado != null) {
					String key = resultado[0].split(":")[1];
					String value = resultado[1];
					valor.put(key, value);
				}
			});
			valor.put("respuestaPN", "2");
			valor.put("respuestaFec", "2");
		}else {
			String rpt = null;

			if (ruc == null) {
				rpt =  empresasSancionadaService.validadSancionPersonNatural(documento);
			}else {
				rpt =  empresasSancionadaService.validadSancionPersonNatural(ruc);
			}
			if (rpt != null) {
				if(!rpt.equalsIgnoreCase("2")) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate fechaCese = LocalDate.parse(rpt, formatter);
					LocalDate fechaLimite = LocalDate.now().minusDays(1);
					if (fechaCese.isAfter(fechaLimite)) {
						valor.put("respuestaFec","1");
						valor.put("respuestaPN","2");
					}else {
						valor.put("respuestaFec","2");
						valor.put("respuestaPN","2");
					}
				}else {
					valor.put("respuestaFec","2");
					valor.put("respuestaPN","2");
				}
			}else {
				// Si fechaCese es null
				valor.put("respuestaFec","2");
				valor.put("respuestaPN", "1");
			}
			valor.put("respuesta", "2");
		}
		return valor;
	}

	@GetMapping("/solicitud/validar-remype/{numeroDocumento}")
	public boolean validarRemype(@PathVariable String numeroDocumento) {
		return sicoesSolicitudService.validarRemype(numeroDocumento, getContexto());
	}

}
