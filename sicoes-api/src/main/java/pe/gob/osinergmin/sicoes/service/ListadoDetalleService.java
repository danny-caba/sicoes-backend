package pe.gob.osinergmin.sicoes.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetallePerfilDTO;
import pe.gob.osinergmin.sicoes.model.dto.PerfilDetalleDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface ListadoDetalleService extends BaseService<ListadoDetalle, Long>{

	public ListadoDetalle obtener(Long idListadoDetalle,Contexto contexto);
	
	
	public Page<ListadoDetalle> buscar(Pageable pageable,Contexto contexto);
	
	public List<ListadoDetalle> buscar(String codigo,Contexto contexto);
	public ListadoDetalle obtenerListadoDetalle(String codigoListado, String codigo);


	public List<ListadoDetalle> buscar(String codigo, Long idSuperior, Contexto contexto);


	public ListadoDetalle obtenerListadoDetalleValor(String codigo, String abreviaturaTitulo);

	public ListadoDetalle obtenerListadoDetalleOrden(String codigo, Long orden);

	List<ListadoDetallePerfilDTO> buscarPerfiles(Long idSubSector);
	
	public List<ListadoDetallePerfilDTO> listarPerfilesDistintosPorIdProfesionIdDivision(Long idProfesion, Long idDivision, Contexto contexto);
	
	public List<ListadoDetalle> listarListadoDetallePorCoodigo(String codigo, Contexto contexto);

	List<PerfilDetalleDTO> buscarPerfilesDetalle(Contexto contexto);

	List<ListadoDetalle> buscarPerfilesDetallePorDivision(Long idDivision, Contexto contexto);

	
}
