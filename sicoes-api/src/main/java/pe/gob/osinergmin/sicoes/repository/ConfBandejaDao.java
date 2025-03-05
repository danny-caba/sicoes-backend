package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.ConfiguracionBandeja;
import pe.gob.osinergmin.sicoes.model.Usuario;

@Repository
public interface ConfBandejaDao extends JpaRepository<ConfiguracionBandeja, Long> {
	
	@Query("select c from ConfiguracionBandeja c "	
			+ "left join fetch c.sector s "
			+ "left join fetch c.subsector ss "
			+ "left join fetch c.actividad a "
			+ "left join fetch c.unidad u "
			+ "left join fetch c.subCategoria sc "
			+ "left join fetch c.perfil p "
			+ "left join fetch c.usuario u "
		+ "where c.idConfiguracionBandeja=:idConfBandeja")	
	public ConfiguracionBandeja obtener(Long idConfBandeja);
	
	
	@Query("select u from ConfiguracionBandeja c "	
			+ "left join c.sector s "
			+ "left join c.subsector ss "
			+ "left join c.actividad a "
			+ "left join c.unidad u "
			+ "left join c.subCategoria sc "
			+ "left join c.perfil p "
			+ "left join c.usuario u "
		+ "where p.idListadoDetalle in (:codigoperfil) "
		+ "and u.idUsuario in (select ur.usuario from UsuarioRol ur where ur.rol.codigo=:codigoRol)" 
		)
	public List<Usuario> listarConfiguraciones(Long codigoperfil[],String codigoRol);
	
	
	@Query("select c from ConfiguracionBandeja c "	
			+ "left join c.sector s "
			+ "left join c.subsector ss "
			+ "left join c.actividad a "
			+ "left join c.unidad u "
			+ "left join c.subCategoria sc "
			+ "left join c.perfil p "
			+ "left join c.usuario u "
		+ "where p.idListadoDetalle = :codigoperfil "
		+ "order by c.idConfiguracionBandeja desc "
		)
	public List<ConfiguracionBandeja> obtenerConfiguracionPorPerfil(Long codigoperfil);
	
	
	@Query(value = "select c from ConfiguracionBandeja c "	
			+ "left join fetch c.sector s "
			+ "left join fetch c.subsector ss "
			+ "left join fetch c.actividad a "
			+ "left join fetch c.unidad u "
			+ "left join fetch c.subCategoria sc "
			+ "left join fetch c.perfil p "
			+ "left join fetch c.tipoConfiguracion tc "
			+ "left join fetch c.usuario u "
			+ "left join fetch u.tipoDocumento td "
		+ "where u.idUsuario=:idUsuario ",
		countQuery = "select count(c) from ConfiguracionBandeja c ")
	public Page<ConfiguracionBandeja> listarConfiguracionesPorIdUsuario(Long idUsuario, Pageable pageable);
	
	
	@Modifying
	@Transactional
	@Query(value="update ConfiguracionBandeja set estadoConfiguracion = :estadoConfiguracion, usuActualizacion = :usuActualizacion, fecActualizacion = sysdate, ipActualizacion = :ipActualizacion where idConfiguracionBandeja=:idConfiguracionBandeja")
	public void actualizarEstadoConfigBandeja(Long idConfiguracionBandeja, String estadoConfiguracion, String usuActualizacion, String ipActualizacion);

	@Query("select count(c) from ConfiguracionBandeja c "
			+ "left join c.perfil p "
			+ "left join c.usuario u "
		+ "where p.idListadoDetalle = :idPerfil "
		+ "and u.idUsuario = :idUsuario "
		+ "and c.estadoConfiguracion = '1' "
		)
	int contarConfiguracionPorPerfilUsuario(Long idPerfil, Long idUsuario);
}
