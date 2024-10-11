package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.UsuarioRolConfiguracion;

@Repository
public interface UsuarioRolConfiguracionDao extends PagingAndSortingRepository<UsuarioRolConfiguracion, Long>{

	
	@Modifying
	@Transactional
	@Query(value="update UsuarioRolConfiguracion set estadoUsuarioRolConfig = :estado, usuActualizacion = :usuActualizacion, fecActualizacion = sysdate, ipActualizacion = :ipActualizacion where idUsuarioRolConfig=:id")
	public void actualizarEstadoUsuarioRolConf(Long id, String estado, String usuActualizacion, String ipActualizacion);
}
