package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.DocumentoInformePresupuestoRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.DocumentoInformePresupuestoResponseDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

@RestController
@RequestMapping("/api/informe/renovacion/presupuesto")
public class BorrarRestController extends BaseRestController {

	private Logger logger = LogManager.getLogger(BorrarRestController.class);
	
	@Autowired
	private BorrarService borrarService;

	@PostMapping("/documento/agregar")
	public DocumentoInformePresupuestoResponseDTO agregarDocumento(@RequestBody DocumentoInformePresupuestoRequestDTO documentoInformePresupuesto, Contexto contexto) throws Exception {
		logger.info("agregarDocumento");
		return borrarService.agregarDocumento(documentoInformePresupuesto, getContexto());
	}

	@PostMapping("/documento/anular")
	public DocumentoInformePresupuestoResponseDTO anularDocumento(@RequestBody DocumentoInformePresupuestoRequestDTO documentoInformePresupuesto, Contexto contexto) throws Exception {
		logger.info("anularDocumento");
		return borrarService.anularDocumento(documentoInformePresupuesto, getContexto());
	}
}
