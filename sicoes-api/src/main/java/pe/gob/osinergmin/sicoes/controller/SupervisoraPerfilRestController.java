package pe.gob.osinergmin.sicoes.controller;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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

import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.SupervisoraPerfil;
import pe.gob.osinergmin.sicoes.service.SupervisoraPerfilService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api/supervisora-perfiles")
public class SupervisoraPerfilRestController extends BaseRestController {

	@Autowired
	SupervisoraPerfilService supervisoraPerfilService;
	
	private Logger logger = LogManager.getLogger(SupervisoraPerfilRestController.class);
	
	@GetMapping
	@Raml("supervisora.perfil.listar.properties")
	public Page<SupervisoraPerfil> buscar(
				@RequestParam(required=false) String[] codigoTipoPersona,
				@RequestParam(required=false) String codigoTipoDocumento,
				@RequestParam(required=false)String ruc,
				@RequestParam(required=false)String nombres,
				@RequestParam(required=false)String perfil,
				@RequestParam(required=false)String fechaInicio,
				@RequestParam(required=false)String fechaFin,Pageable pageable){
		Page<SupervisoraPerfil> supervisoras= supervisoraPerfilService.buscar(codigoTipoPersona,codigoTipoDocumento,ruc,nombres,perfil,fechaInicio,fechaFin,pageable,getContextoAnonimo());
		return supervisoras;
	}
	
	
	@PostMapping
	@Raml("supervisora.perfil.obtener.properties")
	public SupervisoraPerfil registrar(@RequestBody SupervisoraPerfil supervisora) {
		logger.info("registrar {} ",supervisora);
		SupervisoraPerfil supervisoraBD=  supervisoraPerfilService.guardar(supervisora,getContexto());
		return supervisoraBD;
	}
	
	@PutMapping("/{id}")
	@Raml("supervisora.perfil.obtener.properties")
	public SupervisoraPerfil modificar(@PathVariable Long  id,@RequestBody SupervisoraPerfil supervisora) {
		logger.info("modificar {} {}",id,supervisora);
		supervisora.setIdSupervisoraPerfil(id);
		return supervisoraPerfilService.guardar(supervisora,getContexto());
		
	}
	
	@DeleteMapping("/{id}")
	public void eliminar(@PathVariable Long  id){
		logger.info("eliminar {} ",id);
		supervisoraPerfilService.eliminar(id,getContexto());
	}
	
	@GetMapping("/{id}")
	@Raml("supervisora.perfil.obtener.properties")
	public SupervisoraPerfil obtener(@PathVariable Long  id){
		logger.info("obtener {} ",id);
		return supervisoraPerfilService.obtener(id,getContexto());
	}
	
	@GetMapping("/export")
	public void exportar(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(required=false) String numeroExpediente,
			@RequestParam(required=false) Long idTipoEmpresa,
			@RequestParam(required=false) String nombreRazonSocial,
			@RequestParam(required=false) String numeroDocumento)throws Exception {
		logger.info("export {} {} {} {} {} {} ",numeroExpediente,idTipoEmpresa,nombreRazonSocial,numeroDocumento);
		InputStream is=supervisoraPerfilService.generarExport(numeroExpediente,idTipoEmpresa,nombreRazonSocial,numeroDocumento,getContexto());
		response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition", "attachment; filename=Empresas_Supervisoras.xlsx");
	    IOUtils.copy(is, response.getOutputStream());
	}
	
	
	@GetMapping("/profesionales")
	@Raml("supervisora.listar.properties")
	public Page<Supervisora> liberacionPersonal(
				@RequestParam(required=false) String codigoRuc,
				@RequestParam(required=false) Long idEstado,
				@RequestParam(required=true)Long idSector,
				@RequestParam(required=true)Long idSubsector,
				@RequestParam(required=false)String proceso,
				@RequestParam(required=false)String item,Pageable pageable){
		Page<Supervisora> supervisoras= supervisoraPerfilService.liberacionPersonal(codigoRuc,idEstado,idSector,idSubsector,proceso,item,pageable,getContexto());
		return supervisoras;
	}

	
}
