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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.model.Documento;
import pe.gob.osinergmin.sicoes.service.DocumentoService;
import pe.gob.osinergmin.sicoes.service.SolicitudService;
import pe.gob.osinergmin.sicoes.util.Raml;
@RestController
@RequestMapping("/api/requisitos/documentos")
public class RequisitoRestController extends BaseRestController{

	private Logger logger = LogManager.getLogger(RequisitoRestController.class);
	
	@Autowired
	DocumentoService documentoService;
	
	@Autowired
	SolicitudService solicitudService; 

	@PostMapping
	@Raml("documento.obtener.properties")
	public Documento registrar(@RequestBody Documento documento) {
		logger.info("registrar {} ",documento);
		 return documentoService.guardar(documento,getContexto());
	}
	
	@PutMapping("/{id}")
	@Raml("documento.obtener.properties")
	public Documento modificar(@PathVariable Long  id,@RequestBody Documento documento) {
		logger.info("modificar {} {}",id,documento);
		documento.setIdDocumento(id);
		return documentoService.guardar(documento,getContexto());
		
	}
	
	@DeleteMapping("/{id}")
	public void eliminar(@PathVariable Long  id){
		logger.info("eliminar {} ",id);
		documentoService.eliminar(id,getContexto());
	}
	
	@GetMapping("/{id}")
	@Raml("documento.obtener.properties")
	public Documento obtener(@PathVariable Long  id){
		logger.info("obtener {} ",id);
		return documentoService.obtener(id,getContexto());
	}
	
	@GetMapping
	@Raml("documento.listar.properties")
	public Page<Documento> buscar(@RequestParam String solicitudUuid, Pageable pageable) {
		logger.info("buscar {} {}");			
		Long idSolicitud = solicitudService.obtenerId(solicitudUuid);
		Page<Documento> page= documentoService.buscar(idSolicitud,pageable,getContexto());
		return page;
	}

}
