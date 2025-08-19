package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pe.gob.osinergmin.sicoes.model.ProcesoDocumento;
import pe.gob.osinergmin.sicoes.service.ProcesoDocumentoService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api")
public class ProcesoDocumentoRestController extends BaseRestController{
	
	private Logger logger = LogManager.getLogger(ProcesoDocumentoRestController.class);
	
	@Autowired
	private ProcesoDocumentoService procesoDocumentoService;
	
	@PostMapping(value = "/proceso-documento",consumes = { "multipart/form-data" })
	@Raml("procesoDocumento.obtener.properties")
	public ProcesoDocumento registrar(@RequestParam(value = "file",required = false) MultipartFile file,
			@RequestParam(value = "idEtapa",required = false) Long idEtapa,
			@RequestParam(value = "idProceso",required = false) Long idProceso,
			@RequestParam(value = "documentName",required = false) String documentName){
		return procesoDocumentoService.registrar(file, idEtapa, idProceso, documentName, getContexto());
	}
	
	@GetMapping("/proceso-documento/listar")
	@Raml("procesoDocumento.listar.properties")
	public Page<ProcesoDocumento> buscar(@RequestParam(required=true) Long idProceso,Pageable pageable) {
		logger.info("listarproceso-documentos");			
		return procesoDocumentoService.listarDocumentos(idProceso, pageable, getContexto());
	}
	
	@DeleteMapping("/proceso-documento/{id}")
	public void eliminar(@PathVariable Long  id){
		logger.info("eliminar {} ",id);
		procesoDocumentoService.eliminar(id,getContexto());
	}

}
