package pe.gob.osinergmin.sicoes.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.service.EmpresasSancionadaService;

@RestController
@RequestMapping("/api")
public class EmpresasSancionadaRestController extends BaseRestController {

	@Autowired
	EmpresasSancionadaService empresasSancionadaService;
	
	private Logger logger = LogManager.getLogger(EmpresasSancionadaRestController.class);
	
	@GetMapping("/empresas-sancionadas")
	public Map<String,String> buscar(@RequestParam(required=false) String ruc){
		Map<String,String> valor=new HashMap<>();
		valor.put("respuesta", empresasSancionadaService.validarEmpresaSancionada(getContexto().getUsuario().getCodigoRuc()));
		return valor;
	}

	@GetMapping("/sancion-vigente")
	public Map<String,String> ValidadSancion(@RequestParam(required=false) String ruc){
		Map<String,String> valor=new HashMap<>();
		String documento = getContexto().getUsuario().getCodigoRuc();
		documento = documento.trim();
		if (documento.startsWith("20") && ruc == null) {
		valor.put("respuesta", empresasSancionadaService.validadSancion(documento));
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

	@GetMapping("/sancion-vigente-pn-contr")
	public Map<String,String> ValidadSancionPerfContr(@RequestParam(required=false) String ruc){
		Map<String,String> valor=new HashMap<>();
		String documento = getContexto().getUsuario().getCodigoRuc();
		documento = documento.trim();
		if (documento.startsWith("20") && ruc == null) {
		valor.put("respuesta", empresasSancionadaService.validadSancion(documento));
		valor.put("respuestaPN", "2");
		valor.put("respuestaFec", "2");
		}else {
			Map<String,String> resultados = null;

			if (ruc == null) {

				resultados =  empresasSancionadaService.validadSancionPersonNaturalV2(documento);
			}else {

				resultados =  empresasSancionadaService.validadSancionPersonNaturalV2(ruc);
			}

		 if (resultados != null) {

			 String rpt = resultados.get("fechaCeseStr");

			 if(!rpt.equalsIgnoreCase("2")) {

	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	            LocalDate fechaCese = LocalDate.parse(rpt, formatter);

	            LocalDate fechaLimite = LocalDate.now().minusDays(1);
		            if (fechaCese.isAfter(fechaLimite)) {
		            	valor.put("respuestaFec","1");
		            	valor.put("respuestaPN","2");
						valor.put("areaOperativa",resultados.get("areaOperativa"));
						valor.put("fechaIngreso",resultados.get("fechaIngreso"));
						valor.put("descripcionPuesto",resultados.get("descripcionPuesto"));
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

	@GetMapping("/vinculo-laboral")
	public Map<String,String> ValidarVinculoLaboral(@RequestParam String numeroDocumento){
		Map<String,String> valor=new HashMap<>();

		boolean esRuc = false;
		esRuc = Objects.equals(numeroDocumento.trim().length(), 10) ? true : false;
		numeroDocumento = numeroDocumento.trim();

		if (esRuc) {
			numeroDocumento = numeroDocumento.substring(2, 10);
		}

		String rpt = empresasSancionadaService.validarVinculoLaboral(numeroDocumento);

		if (rpt != null) {

			if(!rpt.equalsIgnoreCase("2")) {

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate fechaCese = LocalDate.parse(rpt, formatter);

				LocalDate fechaLimite = LocalDate.now().minusDays(1);
				if (fechaCese.isAfter(fechaLimite)) {
					valor.put("respuesta","1");
				}else {
					valor.put("respuesta","2");
				}
			}else {
				valor.put("respuesta","2");
			}
		}else {
			// Si fechaCese es null
			valor.put("respuesta","2");
		}

		return valor;
	}
}
