package pe.gob.osinergmin.sicoes.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetallePerfilDTO;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;

@RestController
@RequestMapping("/api")
public class ListadoRestController extends BaseRestController{

    @Autowired
    private ListadoDetalleService listadoDetalleService;
    
    @GetMapping("/listado/{codigo}")
    public List<ListadoDetalle> listar(@PathVariable String codigo, HttpServletRequest request) {
    	return listadoDetalleService.buscar(codigo, getContexto());
    }
    
    @GetMapping("/listado/{codigo}/detalles/{idSuperior}")
    public List<ListadoDetalle> listar(@PathVariable String codigo,@PathVariable Long idSuperior, HttpServletRequest request) {
    	return listadoDetalleService.buscar(codigo,idSuperior, getContexto());
    }
    
    @GetMapping("/listado-publico/{codigo}/detalles/{idSuperior}")
    public List<ListadoDetalle> listarPublico(@PathVariable String codigo,@PathVariable Long idSuperior, HttpServletRequest request) {
    	return listadoDetalleService.buscar(codigo,idSuperior, null);
    }
    
    @GetMapping("/listado/perfiles")
    public List<ListadoDetallePerfilDTO> listar(@RequestParam(required=false) Long idSubSector) {
    	return listadoDetalleService.buscarPerfiles(idSubSector);
    }
    
    @GetMapping("/listado-publico/{codigo}")
    public List<ListadoDetalle> listarPublico(@PathVariable String codigo, HttpServletRequest request) {
    	return listadoDetalleService.buscar(codigo, null);
    }
    
    @GetMapping("/listado/perfiles/{idProfesion}/{idDivision}")
    public List<ListadoDetallePerfilDTO> listarPerfilesPorIdProfesionIdDivisioon(@PathVariable Long idProfesion, @PathVariable Long idDivision, HttpServletRequest request) {
    	return listadoDetalleService.listarPerfilesDistintosPorIdProfesionIdDivision(idProfesion, idDivision, getContexto());
    }
    
    @GetMapping("/listado-detalle/{codigo}")
    public List<ListadoDetalle> listarListadoDetallePorCoodigo(@PathVariable String codigo, HttpServletRequest request) {
    	return listadoDetalleService.listarListadoDetallePorCoodigo(codigo, getContexto());
    }
}
