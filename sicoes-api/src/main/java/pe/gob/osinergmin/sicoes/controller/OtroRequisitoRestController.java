package pe.gob.osinergmin.sicoes.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.OtroRequisito;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.OtroRequisitoService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.OtrosRequisitos;
import pe.gob.osinergmin.sicoes.util.Raml;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@RestController
@RequestMapping("/api/requisitos")
public class OtroRequisitoRestController extends BaseRestController {

	@Autowired
	OtroRequisitoService otroRequisitoService;
	
	@Autowired
	ListadoDetalleService listadoDetalleService;;
	
	private Logger logger = LogManager.getLogger(OtroRequisitoRestController.class);
	
	@GetMapping("/{tipo}")
	@Raml("otroRequisito.listar.properties")
	public Page<OtroRequisito> buscar(@PathVariable String  tipo,@RequestParam String  solicitudUuid,Pageable pageable){
		Page<OtroRequisito> otroRequisitos= otroRequisitoService.listarOtroRequisito(OtrosRequisitos.getCodigoByPath(tipo), solicitudUuid, pageable,getContexto());
		return otroRequisitos;
	}
	
	@PostMapping("/{tipo}")
	@Raml("otroRequisito.obtener.properties")
	public OtroRequisito registrar(@PathVariable String  tipo,@RequestBody OtroRequisito otroRequisito) {
		logger.info("registrar {} ",otroRequisito);
		otroRequisito.setTipo(new ListadoDetalle());
		otroRequisito.getTipo().setCodigo(OtrosRequisitos.getCodigoByPath(tipo));
		OtroRequisito solicitudBD=  otroRequisitoService.guardar(otroRequisito,getContexto());
		return solicitudBD;
	}
	
	@PutMapping("/{tipo}/{id}")
	@Raml("otroRequisito.obtener.properties")
	public OtroRequisito modificar(@PathVariable String  tipo,@PathVariable Long  id,@RequestBody OtroRequisito otroRequisito) {
		logger.info("modificar {} {}",id,otroRequisito);
		otroRequisito.setIdOtroRequisito(id);
		otroRequisito.setTipo(new ListadoDetalle());
		otroRequisito.getTipo().setCodigo(OtrosRequisitos.getCodigoByPath(tipo));
		return otroRequisitoService.guardar(otroRequisito,getContexto());
		
	}
	
	@PutMapping("/{tipo}/{id}/finalizar")
	@Raml("otroRequisito.obtener.properties")
	public OtroRequisito finalizar(@PathVariable String  tipo,@PathVariable Long  id,@RequestBody OtroRequisito otroRequisito) {
		logger.info("modificar {} {}",id,otroRequisito);
		otroRequisito.setIdOtroRequisito(id);
		otroRequisito.setTipo(new ListadoDetalle());
		otroRequisito.getTipo().setCodigo(OtrosRequisitos.getCodigoByPath(tipo));
		OtroRequisito otroRequisitoBD = otroRequisitoService.finalizar(otroRequisito,getContexto());
		return otroRequisitoBD;
		
	}
	
	@PutMapping("/{tipo}/{id}/evaluar")
	@Raml("otroRequisito.obtener.properties")
	public OtroRequisito evaluar(@PathVariable String  tipo,@PathVariable Long  id,@RequestBody OtroRequisito otroRequisito) {
		logger.info("modificar {} {}",id,otroRequisito);
		otroRequisito.setIdOtroRequisito(id);
		otroRequisito.setTipo(new ListadoDetalle());
		otroRequisito.getTipo().setCodigo(OtrosRequisitos.getCodigoByPath(tipo));
		OtroRequisito otroRequisitoBD = otroRequisitoService.evalular(otroRequisito,getContexto());

		return otroRequisitoBD;
		
	}
	
	@DeleteMapping("/{id}")
	public void eliminar(@PathVariable Long  id){
		logger.info("eliminar {} ",id);
		otroRequisitoService.eliminar(id,getContexto());
	}
	
	@GetMapping("/{tipo}/{id}")
	@Raml("otroRequisito.obtener.properties")
	public OtroRequisito obtener(@PathVariable String  tipo,@PathVariable Long  id){
		logger.info("obtener {} ",id);
		return otroRequisitoService.obtener(OtrosRequisitos.getCodigoByPath(tipo),id,getContexto());
	}
	
	@PutMapping("/asignarEvaluadorPerfil/{idOtroRequisito}")
	@Raml("otroRequisito.obtener.properties")
	public OtroRequisito asignarEvaluadorPerfil(@PathVariable Long idOtroRequisito, @RequestBody List<Asignacion> asignaciones) {
		// asignacion siempre recibe un solo valor para este metodo
		logger.info("asignarEvaluadorPerfil {} {}", idOtroRequisito, asignaciones.get(0).getUsuario().getIdUsuario());
		return otroRequisitoService.asignarEvaluadorPerfil(idOtroRequisito, asignaciones, getContexto());
	}
	
	@PutMapping("/{tipo}/{id}/revertir/solicita")
	@Raml("otroRequisito.obtener.properties")
	public OtroRequisito solicitarRevertirEvaluacion(@PathVariable String  tipo, @PathVariable Long id,@RequestBody OtroRequisito otroRequisito) {
		logger.info("solicitarRevertirEvaluacion {} {}", id, otroRequisito);
		otroRequisito.setIdOtroRequisito(id);
		otroRequisito.setTipo(new ListadoDetalle());
		otroRequisito.getTipo().setCodigo(OtrosRequisitos.getCodigoByPath(tipo));
		ListadoDetalle estadoReversion = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_REVERSION.ESTADO_REVERSION_EVALUACION, Constantes.LISTADO.ESTADO_REVERSION.REV_SOLICITADO);
		otroRequisito.setEstadoReversion(estadoReversion);
		OtroRequisito otroRequisitoBD = otroRequisitoService.actualizarEstadoSolicitudRevertirEvaluacion(otroRequisito,getContexto());
		return otroRequisitoBD;
	}
	
	@PutMapping("/{tipo}/{id}/revertir/aprobar")
	@Raml("otroRequisito.obtener.properties")
	public OtroRequisito aprobarRevertirEvaluacion(@PathVariable String  tipo, @PathVariable Long id, @RequestBody OtroRequisito otroRequisito) {
		logger.info("aprobarRevertirEvaluacion {} {}", id, otroRequisito);
		if (otroRequisito.getFinalizado() == null || otroRequisito.getFinalizado().getCodigo().equals(Constantes.LISTADO.SI_NO.NO)) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.EVALUACION_NO_FINALIZADO);
		}
		otroRequisito.setIdOtroRequisito(id);
		otroRequisito.setTipo(new ListadoDetalle());
		otroRequisito.getTipo().setCodigo(OtrosRequisitos.getCodigoByPath(tipo));
		ListadoDetalle estadoReversion = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_REVERSION.ESTADO_REVERSION_EVALUACION, Constantes.LISTADO.ESTADO_REVERSION.REV_APROBADO);
		otroRequisito.setEstadoReversion(estadoReversion);
		otroRequisito.setFinalizado(null);
		otroRequisito.setFechaFinalizador(null);
		otroRequisito.setObservacion(null);
		ListadoDetalle evaluacion = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_EVALUACION.CODIGO, Constantes.LISTADO.RESULTADO_EVALUACION.POR_EVALUAR);
		otroRequisito.setEvaluacion(evaluacion);
		OtroRequisito otroRequisitoBD = otroRequisitoService.actualizarEstadoSolicitudRevertirEvaluacion(otroRequisito,getContexto());
		return otroRequisitoBD;
	}
	
	@PutMapping("/{tipo}/{id}/revertir/rechazar")
	@Raml("otroRequisito.obtener.properties")
	public OtroRequisito rechazarRevertirEvaluacion(@PathVariable String  tipo, @PathVariable Long id, @RequestBody OtroRequisito otroRequisito) {
		logger.info("rechazarRevertirEvaluacion {} {}", id, otroRequisito);
		if (otroRequisito.getFinalizado() == null || otroRequisito.getFinalizado().getCodigo().equals(Constantes.LISTADO.SI_NO.NO)) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.EVALUACION_NO_FINALIZADO);
		}
		otroRequisito.setIdOtroRequisito(id);
		otroRequisito.setTipo(new ListadoDetalle());
		otroRequisito.getTipo().setCodigo(OtrosRequisitos.getCodigoByPath(tipo));
		ListadoDetalle estadoReversion = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_REVERSION.ESTADO_REVERSION_EVALUACION, Constantes.LISTADO.ESTADO_REVERSION.REV_RECHAZADO);
		otroRequisito.setEstadoReversion(estadoReversion);
		OtroRequisito otroRequisitoBD = otroRequisitoService.actualizarEstadoSolicitudRevertirEvaluacion(otroRequisito,getContexto());
		return otroRequisitoBD;
	}
	
	@PutMapping("/modificarEvaluadorPerfil/{idOtroRequisito}")
	@Raml("otroRequisito.obtener.properties")
	public OtroRequisito modificarEvaluadorPerfil(@PathVariable Long idOtroRequisito, @RequestBody List<Asignacion> asignaciones) {
		
		logger.info("modificarEvaluadorPerfil {} {}", idOtroRequisito, asignaciones.get(0).getUsuario().getIdUsuario());
		return otroRequisitoService.modificarEvaluadorPerfil(idOtroRequisito, asignaciones, getContexto());
	}	
}
