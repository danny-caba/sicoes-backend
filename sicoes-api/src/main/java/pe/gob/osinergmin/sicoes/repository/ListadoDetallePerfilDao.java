package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import pe.gob.osinergmin.sicoes.model.dto.ListadoDetallePerfilDTO;

public interface ListadoDetallePerfilDao {
	
	List<ListadoDetallePerfilDTO> buscarListadoDetallePerfiles(Long idSubSector);
	
	List<ListadoDetallePerfilDTO> listarPerfilesDistintosPorIdProfesionIdDivision(Long idProfesion, Long idDivision, Long idUsuario);
	
}
