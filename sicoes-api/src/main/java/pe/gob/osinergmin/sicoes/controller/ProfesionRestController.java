package pe.gob.osinergmin.sicoes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.model.dto.ProfesionDTO;
import pe.gob.osinergmin.sicoes.service.ProfesionService;

@RestController
@RequestMapping("/api/profesiones")
public class ProfesionRestController extends BaseRestController {
	
	@Autowired
    private ProfesionService profesionService;
	
	@GetMapping("/todas")
    public List<ProfesionDTO> listarProfesionesTodas() {
    	return profesionService.listarProfesionesTodas();
    }

}
