package pe.gob.osinergmin.sicoes.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import pe.gob.osinergmin.sicoes.model.PerfilAprobador;
import pe.gob.osinergmin.sicoes.service.DocumentoService;
import pe.gob.osinergmin.sicoes.service.SolicitudService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoRestController extends BaseRestController {

	@Autowired
	DocumentoService documentoService;
	
	@Autowired
	SolicitudService solicitudService;
	
	private Logger logger = LogManager.getLogger(DocumentoRestController.class);
	
	@GetMapping
	@Raml("documento.listar.properties")
	public Page<Documento> buscar(@RequestParam String solicitudUuid, Pageable pageable) {
	    Long idSolicitud = solicitudService.obtenerId(solicitudUuid);
	    
	    Page<Documento> documentos = documentoService.buscar(idSolicitud, pageable, getContexto());
	    
	    List<PerfilAprobador> aprobadores = solicitudService.buscarAprobadoresPorSolicitud(idSolicitud);

	    for (Documento doc : documentos.getContent()) {
	        List<PerfilAprobador> relacionados = aprobadores.stream()
	            .filter(p -> p.getPerfil() != null && doc.getActividadArea() != null &&
	                         p.getPerfil().getIdListadoDetalle().equals(doc.getActividadArea().getIdListadoDetalle()))
	            .collect(Collectors.toList());

	        doc.setPerfilesAprobadores(relacionados);
	    }

	    return documentos;
	}
	
	@PostMapping
	@Raml("documento.obtener.properties")
	public Documento registrar(@RequestBody Documento documento) {
		logger.info("registrar {} ",documento);
		Documento documentoBD=  documentoService.guardar(documento,getContexto());
		return documentoBD;
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
	
	@PutMapping("/{id}/evaluar")
	@Raml("documento.obtener.properties")
	public Documento evaluar(@PathVariable Long  id,@RequestBody Documento documento) {
		logger.info("modificar {} {}",id,documento);
		documento.setIdDocumento(id);
		return documentoService.evalular(documento,getContexto());
		
	}
	
	@PutMapping("/{id}/modificar")
	@Raml("documento.obtener.properties")
	public Documento modificarDocumento(@PathVariable Long  id,@RequestBody Documento documento) {
		logger.info("modificarDocumento {} {}",id,documento);
		documento.setIdDocumento(id);
		return documentoService.modificarDocumento(documento,getContexto());

	}
}
