package pe.gob.osinergmin.sicoes.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.model.Division;
import pe.gob.osinergmin.sicoes.service.DivisionService;

@RestController
@RequestMapping("/api/divisiones")
public class DivisionRestController extends BaseRestController {
	
	@Autowired
    private DivisionService divisionService;
	
	@GetMapping("/{idProfesion}")
    public List<Division> listarDivisionesPorIdProfesion(@PathVariable Long idProfesion, HttpServletRequest request) {
    	return divisionService.listarDivisionesPorIdProfesion(idProfesion);
    }

}
