package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.Listado;
import pe.gob.osinergmin.sicoes.repository.ListadoDao;
import pe.gob.osinergmin.sicoes.service.ListadoService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Contexto;


@Service
public class ListadoServiceImpl implements ListadoService{
	
	Logger logger = LogManager.getLogger(ListadoServiceImpl.class);

	@Autowired
	private ListadoDao listadoDao;
	
	@Override
	public Listado obtener(Long idListado, Contexto contexto) {
		Listado listado= listadoDao.obtener(idListado);
		return listado;
	}
	
	@Override
	public Page<Listado> buscar(Pageable pageable,Contexto contexto) {
		Page<Listado> listados =listadoDao.buscar(pageable);
		return listados;
	}

	@Override
	public Listado guardar(Listado listado, Contexto contexto) {
		AuditoriaUtil.setAuditoriaRegistro(listado,contexto);
		Listado listadoBD=listadoDao.save(listado);
		return listadoBD;
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		listadoDao.deleteById(id);
		
	}

	@Override
	public Listado obtenerPorCodigo(String codigo, Contexto contexto) {
		return listadoDao.obtenerPorCodigo(codigo);
	}
	
}
