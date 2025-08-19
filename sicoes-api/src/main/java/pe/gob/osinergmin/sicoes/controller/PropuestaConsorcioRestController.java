package pe.gob.osinergmin.sicoes.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.model.PropuestaConsorcio;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.service.BitacoraService;
import pe.gob.osinergmin.sicoes.service.PropuestaConsorcioService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api")
public class PropuestaConsorcioRestController extends BaseRestController{
	
	private Logger logger = LogManager.getLogger(PropuestaTecnicaRestController.class);
	
	@Autowired
	private BitacoraService bitacoraService;
	
	@Autowired
	private PropuestaConsorcioService propuestaConsorcioService;
	
	@GetMapping("/propuestaConsorcio/empresasSupervisoraSector/{idSector}")
	public List<Supervisora> obtenerEmpresasSupervisoraSector(@PathVariable Long idSector) {
		
		logger.info("obtenerEmpresasSupervisoraSector ");
		return propuestaConsorcioService.obtenerEmpresasSupervisoraSector(idSector);
	}
		
	@PutMapping("/propuestaConsorcio")
	@Raml("propuestaConsorcio.obtener.properties")
	public PropuestaConsorcio registrar(@RequestBody PropuestaConsorcio propuestaConsorcio) {
		
		logger.info("registrarPropuestaConsorcio");
		
		try {
			bitacoraService.registrarEmpresaConsorcio(propuestaConsorcio, getContexto());
			PropuestaConsorcio propuestaConsorcioBD = propuestaConsorcioService.guardar(propuestaConsorcio, getContexto());
			return propuestaConsorcioBD;
		}
		catch (Exception e) {
			bitacoraService.registrarEmpresaConsorcioError(propuestaConsorcio, getContexto());
			throw e;
		}  
	}

	@GetMapping("/propuestaConsorcio/obtenerEmpresas/{idPropuestaTecnica}/{idSector}")
	public List<PropuestaConsorcio> obtenerEmpresasConsorcio(@PathVariable Long idPropuestaTecnica, @PathVariable Long idSector) {
		
		logger.info("obtenerEmpresasConsorcio {} {} ", idPropuestaTecnica, idSector);
		List<PropuestaConsorcio> listaEmpresas = new ArrayList<PropuestaConsorcio>();
		listaEmpresas = propuestaConsorcioService.obtenerEmpresasConsorcio(idPropuestaTecnica, idSector);
		listaEmpresas.sort(Comparator.comparing(PropuestaConsorcio::getFacturacion, Comparator.reverseOrder()));
		
		return listaEmpresas;
	}
	
	@PutMapping("/propuestaConsorcio/registrarParticipacion")
	public Boolean registrarParticiapcion(@RequestBody List<PropuestaConsorcio> propuestaConsorcio) {
		
		logger.info("registrarParticiapcion");
		Boolean checkRegistro = false;
		
		try {
			bitacoraService.registrarParticipacionEmpresaConsorcio(propuestaConsorcio, getContexto());
			for (PropuestaConsorcio empresa : propuestaConsorcio) {
				propuestaConsorcioService.guardar(empresa, getContexto());
			}
			checkRegistro = true;
		}
		catch (Exception e) {
			bitacoraService.registrarParticipacionEmpresaConsorcioError(propuestaConsorcio, getContexto());
			throw e;
		}
		
		return checkRegistro;
	}
	
	
	@DeleteMapping("/propuestaConsorcio/{idProConsorcio}")
	public void eliminar(@PathVariable Long idProConsorcio) {
	    logger.info("Eliminar empresa del consorcio con ID: {}", idProConsorcio);
	    propuestaConsorcioService.eliminar(idProConsorcio,getContexto());
	}

}
