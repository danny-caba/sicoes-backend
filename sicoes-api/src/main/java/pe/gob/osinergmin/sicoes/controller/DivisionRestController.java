package pe.gob.osinergmin.sicoes.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.model.Division;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.PerfilDivision;
import pe.gob.osinergmin.sicoes.model.dto.DivisionDTO;
import pe.gob.osinergmin.sicoes.service.DivisionService;

@RestController
@RequestMapping("/api/divisiones")
public class DivisionRestController extends BaseRestController {

    private Logger logger = LogManager.getLogger(DivisionRestController.class);
	
	@Autowired
    private DivisionService divisionService;

    @GetMapping
    public List<DivisionDTO> listarDivisiones(HttpServletRequest request) {
    	return divisionService.listarDivisiones();
    }
	
	@GetMapping("/{idProfesion}")
    public List<Division> listarDivisionesPorIdProfesion(@PathVariable Long idProfesion, HttpServletRequest request) {
    	return divisionService.listarDivisionesPorIdProfesion(idProfesion);
    }

    @GetMapping("/perfiles/{idUsuario}")
    public List<Division> listarDivisionesPorUsuario(@PathVariable Long idUsuario, HttpServletRequest request) {
        logger.info("listarDivisionesPorUsuario {} ", idUsuario);
    	return divisionService.listarDivisionesPorUsuario(idUsuario);
    }

    @GetMapping("/coordinador")
    public List<DivisionDTO> listarDivisionesCoordinador(HttpServletRequest request) {
        return divisionService.listarDivisionesCoordinador(getContexto());
    }

}
