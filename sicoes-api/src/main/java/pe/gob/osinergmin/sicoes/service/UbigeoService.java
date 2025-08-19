package pe.gob.osinergmin.sicoes.service;

import java.util.List;
import java.util.Optional;

import pe.gob.osinergmin.sicoes.model.Ubigeo;


public interface UbigeoService {
	List<Ubigeo> findAll();	
	Optional<Ubigeo> findById(Long id);	
	Ubigeo save(Ubigeo maestroUbigeo);	
	void delete(Long id);	
	Ubigeo update(Ubigeo maestroUbigeo);
	List<Ubigeo> listarProvincia(String codigoDepartamento);
	List<Ubigeo> listarDepartamento();
	List<Ubigeo> listarDistrito(String codigoDepartamento, String codigoProvincia);
}
