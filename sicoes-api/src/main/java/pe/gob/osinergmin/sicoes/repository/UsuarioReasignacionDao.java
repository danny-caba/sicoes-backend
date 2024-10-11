package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.UsuarioReasignacion;

@Repository
public interface UsuarioReasignacionDao extends PagingAndSortingRepository<UsuarioReasignacion, Long>{

	@Modifying
	@Transactional
	@Query(value="update UsuarioReasignacion set estadoUsuarioReasignacion = '2', usuActualizacion = :usuActualizacion, fecActualizacion = sysdate, ipActualizacion = :ipActualizacion where idUsuario=:idUsuario and idConfiguracionBandeja=:idConfiguracionBandeja ")
	public void finalizarReasignacion(Long idUsuario, Long idConfiguracionBandeja, String usuActualizacion, String ipActualizacion);
	
	
	@Query("select u from UsuarioReasignacion u "
			+ " where u.estadoUsuarioReasignacion = '1' and u.idConfiguracionBandeja=:idConfiguracionBandeja")
	public UsuarioReasignacion obtenerReasignacionPorIdConf(Long idConfiguracionBandeja);
}
