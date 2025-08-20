package pe.gob.osinergmin.sicoes.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import gob.osinergmin.sne.util.StringUtil;
import pe.gob.osinergmin.sicoes.model.SicoesArchivo;
import pe.gob.osinergmin.sicoes.service.SicoesArchivoService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Raml;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@RestController
@RequestMapping("/sicoes/archivo")
public class SicoesArchivoRestController  extends BaseRestController {
	
	private Logger logger = LogManager.getLogger(SicoesArchivoRestController.class);
	
	
	@Autowired
	private SicoesArchivoService sicoesArchivoService;
	
	@GetMapping(value = "/test")
	public String getTest() {
		return "hola";
	}
	
	
	@PostMapping(consumes = { "multipart/form-data" }, produces = MediaType.APPLICATION_JSON_VALUE)
//	@Raml("adjunto.obtener.properties")
	public SicoesArchivo registrar(@RequestParam(value = "file",required = true) MultipartFile file,
							 @RequestParam(value = "idArchivo",required = false) Long idArchivo,
							 //@RequestParam(value = "idPropuesta",required = false) Long idPropuesta,
							 //@RequestParam(value = "idProceso",required = false) Long idProceso,
							 @RequestParam(value = "idSolicitud",required = false) Long idSolicitud) {
		
		System.out.println("-----");
		SicoesArchivo archivo = new SicoesArchivo();
		
		if(idArchivo == null) {
			if(file == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_SUBIR_ARCHIVO);
			}
			/*if(StringUtil.isEmpty(codigo)) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_CODIGO_REQUERIDO);
			}*/
			
		}
		if(file != null) {
			archivo.setFile(file);
		}
		
		archivo.setIdArchivo(idArchivo);
		//archivo.setDescripcion(descripcion);
		archivo.setIdSolicitud(idSolicitud);
		//archivo.setIdPropuesta(idPropuesta);
		//archivo.setIdProceso(idProceso);
		
		SicoesArchivo value= sicoesArchivoService.guardarEnAlfresco(archivo, null); //getContexto() usuarioDummy?
		value.setFile(null);
		
    	return value;
    }
	
	@DeleteMapping("/{id}")
	public void eliminar(@PathVariable Long  id){
		logger.debug("eliminar {} ",id);
		sicoesArchivoService.eliminarIdSicoesArchivo(id,getContexto());
	}

}
