package pe.gob.osinergmin.sicoes.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.SupervisoraDictamen;
import pe.gob.osinergmin.sicoes.model.dto.SupervisoraDTO;
import pe.gob.osinergmin.sicoes.model.dto.SupervisoraRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.SupervisoraResponseDTO;
import pe.gob.osinergmin.sicoes.service.SupervisoraDictamenService;
import pe.gob.osinergmin.sicoes.service.SupervisoraService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api/supervisoras")
public class SupervisoraRestController extends BaseRestController {

	@Autowired
	SupervisoraService supervisoraService;
	
	@Autowired
	SupervisoraDictamenService supervisoraDictamenService;
	
	private Logger logger = LogManager.getLogger(SupervisoraRestController.class);
	@GetMapping
	@Raml("supervisora.listar.properties")
	public Page<Supervisora> buscar(
				@RequestParam(required=false) String nroExpediente,
				@RequestParam(required=false) Long idTipoDocumento,
				@RequestParam(required=false) Long idTipoPersona,
				@RequestParam(required=false)String ruc,
				@RequestParam(required=false)String nombres,
				@RequestParam(required=false)String perfil,
				@RequestParam(required=false)String fechaInicio,
				@RequestParam(required=false)String fechaFin,Pageable pageable){
		if(getContexto().getUsuario().isRol(Constantes.ROLES.USUARIO_EXTERNO)) {
			return null;
		}
		return supervisoraService.buscar(nroExpediente,idTipoPersona,idTipoDocumento,ruc,nombres,perfil,fechaInicio,fechaFin,pageable,getContexto());
	}
	
	@GetMapping("/monto")
//	@Raml("supervisora.listar.properties")
	public Page<SupervisoraDTO> buscarXMonto(
		@RequestParam(required=false) String nroExpediente,
		@RequestParam(required=false) Long idTipoDocumento,
		@RequestParam(required=false) Long idTipoPersona,
		@RequestParam(required=false) String ruc,
		@RequestParam(required=false) String nombres,
		@RequestParam(required=false) String perfil,
		@RequestParam(required=false) String fechaInicio,
		@RequestParam(required=false) String fechaFin,
		Pageable pageable
	){
		if(getContexto().getUsuario().isRol(Constantes.ROLES.USUARIO_EXTERNO)) {
			return null;
		}
		return supervisoraDictamenService.buscarXMonto(
			nroExpediente, idTipoPersona,
			idTipoDocumento, ruc,
			nombres, perfil,
			fechaInicio, fechaFin,
			pageable, getContexto()
		);
	}
	
	@GetMapping("suspendidas-cancelado")
	@Raml("supervisora.listar.properties")
	public Page<Supervisora> buscarSupendidaCancelada(			
				@RequestParam(required=false) String nroExpediente,
				@RequestParam(required=false) Long idTipoDocumento,
				@RequestParam(required=false) Long idTipoPersona,
				@RequestParam(required=false)String ruc,
				@RequestParam(required=false)String nombres,
				@RequestParam(required=false)String perfil,
				@RequestParam(required=false)String fechaInicio,
				@RequestParam(required=false)String fechaFin,Pageable pageable){
		if(getContexto().getUsuario().isRol(Constantes.ROLES.USUARIO_EXTERNO)) {
			return null;
		}
		return supervisoraService.buscarSuspendidasCanceladas(nroExpediente,idTipoPersona,idTipoDocumento,ruc,nombres,perfil,fechaInicio,fechaFin,pageable,getContexto());
	}
	
	
	@PostMapping
	@Raml("supervisora.obtener.properties")
	public Supervisora registrar(@RequestBody Supervisora supervisora) {
		logger.info("registrar {} ",supervisora);
		return supervisoraService.guardar(supervisora,getContexto());
	}
	
	@PutMapping("/{id}")
	@Raml("supervisora.obtener.properties")
	public Supervisora modificar(@PathVariable Long  id,@RequestBody Supervisora supervisora) {
		logger.info("modificar {} {}",id,supervisora);
		supervisora.setIdSupervisora(id);
		if(getContexto().getUsuario().isRol(Constantes.ROLES.USUARIO_EXTERNO)) {
			return null;
		}
		return supervisoraService.guardar(supervisora,getContexto());
		
	}
	
	@DeleteMapping("/{id}")
	public void eliminar(@PathVariable Long  id){
		logger.info("eliminar {} ",id);
		supervisoraService.eliminar(id,getContexto());
	}
	
	@GetMapping("/{id}")
	@Raml("supervisora.obtener.properties")
	public Supervisora obtener(@PathVariable Long  id){
		logger.info("obtener {} ",id);
		if(getContexto().getUsuario().isRol(Constantes.ROLES.USUARIO_EXTERNO)) {
			return null;
		}
		return supervisoraService.obtener(id,getContexto());
	}
	
	@GetMapping("/reporte")
	public ResponseEntity<?> generarPDF(
			@RequestParam(required=false) String numeroExpediente,
			@RequestParam(required=false) Long idTipoEmpresa,
			@RequestParam(required=false) String nombreRazonSocial,
			@RequestParam(required=false) String numeroDocumento,
			HttpServletResponse response){
		try {
			Archivo archivo=supervisoraService.generarReporte(numeroExpediente,idTipoEmpresa,nombreRazonSocial,numeroDocumento,getContexto());
			response.setContentType(archivo.getTipo());
			response.setHeader("Content-Disposition", "attachment; filename=\""+archivo.getNombre()+"\""); 
		    IOUtils.copy(new ByteArrayInputStream(archivo.getContenido()), response.getOutputStream());
		    response.flushBuffer();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	
	@GetMapping("/profesionales")
	public List<Supervisora> listarSupervisoras(
			@RequestParam(required=false) Long idPerfil){
		logger.info("listarSupervisoras ");
		return supervisoraService.listarSupervisoras(idPerfil,getContexto());
	}
	
	/**
	 * Endpoint para buscar supervisoras con funcionalidad de autocomplete
	 * Requerimiento 350 - Renovación de Contratos
	 * Permite buscar supervisoras por nombre, razón social o apellidos
	 * @param nombreSupervisora Término de búsqueda opcional para filtrar supervisoras
	 * @return Lista de SupervisoraResponseDTO con datos formateados para autocomplete (máximo 10 resultados)
	 */
	@GetMapping("/autocomplete")
	public List<SupervisoraResponseDTO> buscarSupervisorasParaAutocomplete(
			@RequestParam(required=false) String nombreSupervisora){
		logger.info("buscarSupervisorasParaAutocomplete con nombre: " + nombreSupervisora);
		SupervisoraRequestDTO request = new SupervisoraRequestDTO();
		request.setNombreSupervisora(nombreSupervisora);
		return supervisoraService.buscarSupervisorasParaAutocomplete(request, getContexto());
	}
	
}
