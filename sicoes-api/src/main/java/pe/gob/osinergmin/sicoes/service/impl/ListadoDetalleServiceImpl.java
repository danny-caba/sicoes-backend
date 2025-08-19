package pe.gob.osinergmin.sicoes.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.Listado;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetallePerfilDTO;
import pe.gob.osinergmin.sicoes.model.dto.PerfilDetalleDTO;
import pe.gob.osinergmin.sicoes.repository.ListadoDao;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.repository.ListadoDetallePerfilDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.ListadoService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;


@Service
public class ListadoDetalleServiceImpl implements ListadoDetalleService{

	@Autowired
	ListadoDetallePerfilDao listadoDetallePerfilDao;
	
	@Autowired
	ListadoDetalleDao listadoDetalleDao;
	
	@Autowired
	ListadoDao listadoDao;

	@Autowired
	ListadoService listadoService;

	@Override
	public ListadoDetalle guardar(ListadoDetalle listadoDetalle, Contexto contexto) {
		AuditoriaUtil.setAuditoriaRegistro(listadoDetalle,contexto);
		ListadoDetalle listadoDetalleBD=listadoDetalleDao.save(listadoDetalle);
		return listadoDetalleBD;
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		listadoDetalleDao.deleteById(id);
		
	}

	@Override
	public ListadoDetalle obtener(Long idListadoDetalle, Contexto contexto) {
		ListadoDetalle listado= listadoDetalleDao.obtener(idListadoDetalle);
		return listado;
	}

	@Override
	public Page<ListadoDetalle> buscar(Pageable pageable, Contexto contexto) {
		Page<ListadoDetalle> listados =listadoDetalleDao.buscar(pageable);
		return listados;
	}

	@Override
	public List<ListadoDetalle> buscar(String codigo, Contexto contexto) {
		return listadoDetalleDao.listarListadoDetalle(codigo);
	}
	
	@Override
	public ListadoDetalle obtenerListadoDetalle(String codigoListado, String codigoListadoDetalle) {
		return listadoDetalleDao.obtenerListadoDetalle(codigoListado,codigoListadoDetalle);
	}

	@Override
	public List<ListadoDetalle> buscar(String codigo, Long idSuperior, Contexto contexto) {
		return listadoDetalleDao.listarListadoDetalle(codigo,idSuperior);
	}

	@Override
	public ListadoDetalle obtenerListadoDetalleValor(String codigo, String abreviaturaTitulo) {
		return listadoDetalleDao.obtenerListadoDetalleValor(codigo,abreviaturaTitulo);
	}

	@Override
	public ListadoDetalle obtenerListadoDetalleOrden(String codigo, Long orden) {
		return listadoDetalleDao.obtenerListadoDetalleOrden(codigo, orden);
	}

	@Override
	public List<ListadoDetallePerfilDTO> buscarPerfiles(Long idSubSector) {
		return listadoDetallePerfilDao.buscarListadoDetallePerfiles(idSubSector);
	}
	
	@Override
	public List<ListadoDetallePerfilDTO> listarPerfilesDistintosPorIdProfesionIdDivision(Long idProfesion, Long idDivision, Contexto contexto) {
		Long idUsuario = -1L;
		
		if (contexto.getUsuario().isRol(Constantes.ROLES.USUARIO_EXTERNO)) {
			idUsuario = contexto.getUsuario().getIdUsuario();
		}
		
		return listadoDetallePerfilDao.listarPerfilesDistintosPorIdProfesionIdDivision(idProfesion, idDivision, idUsuario);
	}
	
	@Override
	public List<ListadoDetalle> listarListadoDetallePorCoodigo(String codigo, Contexto contexto) {
		return listadoDetalleDao.listarListadoDetallePorCoodigo(codigo);
	}

	@Override
	public List<PerfilDetalleDTO> buscarPerfilesDetalle(Contexto contexto) {

		Listado listadoPerfil = listadoService.obtenerPorCodigo(Constantes.LISTADO.PERFILES, contexto);

		List<Object[]> perfiles =  listadoDetalleDao.listarPerfilesDetalleV2(listadoPerfil.getIdListado());
		List<PerfilDetalleDTO> listaDto = new ArrayList<>();

		for (Object[] fila : perfiles) {
			PerfilDetalleDTO dto = new PerfilDetalleDTO(
					((Number) fila[0]).longValue(),
					((Number) fila[1]).longValue(),
					(String) fila[2],
					(String) fila[3]
			);
			listaDto.add(dto);
		}
		return listaDto;
	}

	@Override
	public List<ListadoDetalle> buscarPerfilesDetallePorDivision(Long idDivision, Contexto contexto) {
		return listadoDetalleDao.listarPerfilesDetallePorDivision(idDivision);
	}
	
}

