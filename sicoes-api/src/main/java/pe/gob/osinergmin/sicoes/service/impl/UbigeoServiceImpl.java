package pe.gob.osinergmin.sicoes.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.Ubigeo;
import pe.gob.osinergmin.sicoes.repository.UbigeoDao;
import pe.gob.osinergmin.sicoes.service.UbigeoService;
import pe.gob.osinergmin.sicoes.util.Constantes;
@Service
public class UbigeoServiceImpl  implements UbigeoService{

	@Autowired
	UbigeoDao ubigeoDao;

	@Override
	public List<Ubigeo> findAll() {
		return (List<Ubigeo>) ubigeoDao.findAll();
	}

	@Override
	public Optional<Ubigeo> findById(Long id) {
		return ubigeoDao.findById(id);
	}

	@Override
	public Ubigeo save(Ubigeo maestro_ubigeo) {
		return ubigeoDao.save(maestro_ubigeo);
	}

	@Override
	public void delete(Long id) {
		ubigeoDao.deleteById(id);
	}

	@Override
	public Ubigeo update(Ubigeo maestro_ubigeo) {
		return ubigeoDao.save(maestro_ubigeo);
	}

	@Override
	public List<Ubigeo> listarDistrito(String codigoDepartamento, String codigoProvincia){
		return (List<Ubigeo>) ubigeoDao.findByCodigoDepartamentoAndCodigoProvinciaOrderByNombreDistrito(codigoDepartamento, codigoProvincia);
	}

	@Override
	public List<Ubigeo> listarProvincia(String codigoDepartamento) {
		return (List<Ubigeo>) ubigeoDao.findByCodigoDepartamentoAndCodigoDistritoOrderByNombreProvincia(codigoDepartamento, Constantes.UBIGEO.DISTRITO1);
	}

	@Override
	public List<Ubigeo> listarDepartamento() {
		return (List<Ubigeo>) ubigeoDao.findByCodigoProvinciaAndCodigoDistritoOrderByNombreDepartamento(Constantes.UBIGEO.PROVINCIA1, Constantes.UBIGEO.DISTRITO1);
	}
	
	
}