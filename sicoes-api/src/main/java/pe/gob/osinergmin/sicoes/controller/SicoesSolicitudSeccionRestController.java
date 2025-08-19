package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitudSeccion;
import pe.gob.osinergmin.sicoes.model.SicoesTdSoliPersProp;
import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.model.dto.SicoesSolicitudSeccionDTO;
import pe.gob.osinergmin.sicoes.service.SicoesSolicitudSeccionService;
import pe.gob.osinergmin.sicoes.service.SicoesSolicitudService;
import pe.gob.osinergmin.sicoes.util.Raml;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

import java.util.List;

@RestController
@RequestMapping("/sicoes/solicitud")
public class SicoesSolicitudSeccionRestController extends BaseRestController {

private Logger logger = LogManager.getLogger(SicoesSolicitudSeccionRestController.class);
	
	
	@Autowired
	private SicoesSolicitudSeccionService sicoesSolicitudSeccionService;

	@Autowired
	private SicoesSolicitudService solicitudService;
	
	@PutMapping("/seccion")
	//@Raml("proceso.obtener.properties")
	public SicoesSolicitudSeccion registrar(@RequestBody SicoesSolicitudSeccion solicitudSeccion) {
		logger.info("registrar avance parcial {} ", solicitudSeccion);

		return sicoesSolicitudSeccionService.guardar(solicitudSeccion, getContexto());
	}

	@PutMapping("/evaluacion/requisito")
	@Raml("requisitoSeccion.obtener.properties")
	public SicoesSolicitudSeccion putRequisito(@RequestBody SicoesSolicitudSeccion sicoesSolicitud) {
		logger.info("Put soliicitudes {} ", sicoesSolicitud);
		return sicoesSolicitudSeccionService.putRequisito(sicoesSolicitud , getContexto());
	}

	@PutMapping("/evaluacion/requisito/personal")
	@Raml("requisitoSeccion.obtener.properties")
	public SicoesSolicitudSeccion actualizarRequisitoPersonal(@RequestBody SicoesTdSoliPersProp personalPropuesto) {
		logger.info("actualizarRequisitoPersonal {} ", personalPropuesto);
		return sicoesSolicitudSeccionService.actualizarRequisitoPersonal(personalPropuesto , getContexto());
	}

	@GetMapping("/secciones/requisitos/{idSeccion}/{tipoContrato}/{evaluador}/{propuesta}")
	@Raml("sicoesSolicitudSeccion.listar.properties")
	public Page<SicoesSolicitudSeccion> obtener(@PathVariable Long idSeccion, @PathVariable Long tipoContrato, @PathVariable boolean evaluador, @PathVariable Long propuesta, Pageable pageable) {
		logger.info("obtener requisitos x seccion {} ", idSeccion);

//		Long tipoContratoLong = Long.parseLong(tipoContrato);

		return sicoesSolicitudSeccionService.obtenerRequisitosPorSeccion(idSeccion, tipoContrato, evaluador, propuesta, pageable, getContexto());
	}

	@GetMapping("/personas/requisitos/{idSoliPersProp}/{tipoContrato}")
	@Raml("sicoesSolicitudSeccion.listar.properties")
	public Page<SicoesSolicitudSeccion> obtenerPorPersonal(@PathVariable Long idSoliPersProp, @PathVariable Long tipoContrato, Pageable pageable) {
		logger.info("obtener requisitos x personal {} ", idSoliPersProp);

		return sicoesSolicitudSeccionService.obtenerRequisitosPorPersonal(idSoliPersProp, tipoContrato, pageable, getContexto());
	}

	@PutMapping("/seccion/estado")
//	@Raml("sicoesSolicitudSeccion.listar.properties")
	public boolean modificarFlagRequisito(@RequestBody List<SicoesSolicitudSeccion> lstRequisitos) {
		logger.info("modificar estado requisito {}");

		return sicoesSolicitudSeccionService.modificarFlagRequisito(lstRequisitos, getContexto());
	}

	@PutMapping("/seccion/estado/personal")
//	@Raml("sicoesSolicitudSeccion.listar.properties")
	public boolean descartarFlagRequisitoPersonal(@RequestBody SicoesTdSoliPersProp persProp) {
		logger.info("descartarFlagRequisitoPersonal estado requisito {}");

		return sicoesSolicitudSeccionService.descartarFlagRequisitoPersonal(persProp, getContexto());
	}

	@GetMapping("/personas/requisitos")
	@Raml("persona.listar.properties")
	public Page<SicoesTdSoliPersProp> obtenerRequisitosConFlagActivo(
		@RequestParam(required = false) Long idSeccion,
		@RequestParam(required = false) Long tipoContrato,
		Pageable pageable
	) {
		logger.info("obtener requisitos x personal {} ");

		return sicoesSolicitudSeccionService.obtenerRequisitosConFlagActivo(idSeccion, tipoContrato, pageable, getContexto());
	}

}
