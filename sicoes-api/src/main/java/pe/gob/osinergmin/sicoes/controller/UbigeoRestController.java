package pe.gob.osinergmin.sicoes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.model.Ubigeo;
import pe.gob.osinergmin.sicoes.service.UbigeoService;

@RestController
@RequestMapping("/api")
public class UbigeoRestController {
	
	@Autowired
	UbigeoService ubigeoService;
	
	@Autowired
	SigedOldConsumer sigedOldConsumer;
	
	@GetMapping("/departamentos")
	public List<Ubigeo> listarDepartamento(){		 
		 return sigedOldConsumer.departamentos();
	}
	
	@GetMapping("/departamentos/{codigoDepartamento}/provincias")
	public List<Ubigeo> listarProvincia(@PathVariable String  codigoDepartamento){
		return sigedOldConsumer.provincias(codigoDepartamento);
	}
	
	
	@GetMapping("/provincias/{codigoProvincia}/distritos")
	public List<Ubigeo> listarDistrito(@PathVariable String  codigoProvincia){
		return sigedOldConsumer.distritos(codigoProvincia);
	}
}
