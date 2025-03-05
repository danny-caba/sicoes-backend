package pe.gob.osinergmin.sicoes.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.ConfiguracionBandeja;
import pe.gob.osinergmin.sicoes.model.Rol;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.UsuarioRol;

@Repository
public interface UsuarioDao extends PagingAndSortingRepository<Usuario, Long> {
	
	@Query("select u from Usuario u "
			+ "left join fetch u.tipoPersona tip "
			+ "left join fetch u.tipoDocumento tid "
			+ "left join fetch u.pais p "
			+ "where u.idUsuario=:idUsuario")
	public Usuario obtener(Long idUsuario);
	
	@Query("select u from Usuario u "
			+ "left join fetch u.tipoPersona tip "
			+ "left join fetch u.tipoDocumento tid "
			+ "left join fetch u.pais p "
			+ " where lower(correo) =lower(:correo)")
	Usuario obtenerUsuarioCorreo(String correo);
	
	@Query(value="select u from Usuario u "
			+ "left join fetch u.tipoPersona tip "
			+ "left join fetch u.tipoDocumento tid "
			+ "left join fetch u.pais p ",
			countQuery = "select count(u) from Usuario u ")			
	Page<Usuario> buscar(Long idUsuario,Pageable pageable);
	
	@Query("select u from Usuario u "
			+ "left join fetch u.tipoPersona tip "
			+ "left join fetch u.tipoDocumento tid "
			+ "left join fetch u.pais p "
			+ "where UPPER(u.usuario)=UPPER(:usuario)")
	public Usuario obtener(String usuario);
	
	@Query("select u from Usuario u "
			+ "left join fetch u.tipoPersona tip "
			+ "left join fetch u.tipoDocumento tid "
			+ "left join fetch u.pais p "
			+ "where u.codigoRuc = :ruc")
	public Usuario obtenerRUC( String ruc);
	
	@Query("select u from Usuario u "
			+ "left join fetch u.tipoPersona tip "
			+ "left join fetch u.tipoDocumento tid "
			+ "left join fetch u.pais p "
			+ "where tid.idListadoDetalle=:idTipoDocumento "
			+ "and u.codigoRuc=:ruc ")
	public Usuario obtenerxDocumento(Long idTipoDocumento,String ruc);

	@Query(value="select ur.usuario from UsuarioRol ur "
			+ "left join ur.usuario u "
			+ "left join fetch u.tipoPersona tip "
			+ "left join fetch u.tipoDocumento tid "
			+ "left join fetch u.pais p "
			+ "where ur.rol.codigo=:codigoRol",
			countQuery ="select count(1) from UsuarioRol ur "
					+ "where ur.rol.codigo=:codigoRol")
	public Page<Usuario> listarUsuarioPerfil(String codigoRol, Pageable pageable);

	@Query("select distinct p.idListadoDetalle from OtroRequisito ot  left join ot.solicitud s left join ot.perfil p "
			+ "where s.solicitudUuid=:uuidSolicitud and p.idListadoDetalle is not null ")
	public Long[] obtenerCodigoPeril(String uuidSolicitud);

	@Query("select distinct ss.idListadoDetalle from OtroRequisito ot  left join ot.solicitud s left join ot.perfil p left join ot.subsector ss "
			+ "where s.solicitudUuid=:uuidSolicitud and p.idListadoDetalle is null and ss.idListadoDetalle is not null "
			+ "AND ( (ot.fecCreacion > :fechaLimite AND ot.usuario IS NOT NULL) " 
		    + "      OR ot.fecCreacion <= :fechaLimite )")
	public Long[] obtenerCodigoSector(String uuidSolicitud, Date fechaLimite);

	@Query(value="select ur.usuario from UsuarioRol ur "
			+ "left join ur.usuario u "
			+ "left join fetch u.tipoPersona tip "
			+ "left join fetch u.tipoDocumento tid "
			+ "left join fetch u.pais p "
			+ "where ur.rol.codigo=:codigoRol and u.idUsuario "
			+ "in("
				+ "select  cu.idUsuario from ConfiguracionBandeja c "
				+ "left join c.usuario cu  "
				+ "left join c.perfil p "
				+ "left join c.subsector ss "
				+ "where c.estadoConfiguracion = '1' and ((p.idListadoDetalle in(:codigoPerfil)) "
				+ "or ( ss.idListadoDetalle in(:codigoSubSector) and p.idListadoDetalle is null)) "
				+ ")",
			countQuery ="select count(1) from UsuarioRol ur "
					+ "left join ur.usuario u "
					+ "where ur.rol.codigo=:codigoRol and u.idUsuario "
					+ "in("
						+ "select  cu.idUsuario from ConfiguracionBandeja c "
						+ "left join c.usuario cu  "
						+ "left join c.perfil p "
						+ "left join c.subsector ss "
						+ "where c.estadoConfiguracion = '1' and ((p.idListadoDetalle in(:codigoPerfil)) "
						+ "or ( ss.idListadoDetalle in(:codigoSubSector) and p.idListadoDetalle is null)) "
						+ ")")
	public Page<Usuario> listarUsuarioPerfil(String codigoRol,List codigoPerfil, List codigoSubSector,
			Pageable pageable);

	@Query(value="select u "
			+ "from Usuario u "
			+ "left join fetch u.tipoPersona tip "
			+ "left join fetch u.tipoDocumento tid "
			+ "left join fetch u.pais p "
			+ "where (:nombreUsuario is null or u.usuario like '%'|| :nombreUsuario ||'%') or "
			+ "(:nombreUsuario is null or u.nombreUsuario like '%'|| :nombreUsuario ||'%') "
			+ "order by u.idUsuario asc",
			countQuery = "select count(1) from Usuario u ")
	public Page<Usuario> buscar2(String nombreUsuario, Pageable pageable);
	
	
	@Query(value="select ur from UsuarioRol ur "	
			+ "left join fetch ur.usuario u "
			+ "left join fetch ur.rol r "
			+ "left join fetch r.estado re "
			+ "left join fetch u.tipoDocumento tid "
			+ "where u.idUsuario=:idUsuario and ur.estadoUsuarioRol='1' ",
			countQuery = "select count(ur) from UsuarioRol ur ")
	public Page<UsuarioRol> listarRolUsuario(Long idUsuario, Pageable pageable);
	
	@Modifying
	@Transactional
	@Query(value="update Usuario set estadoUsuario = :estadoUsuario, usuActualizacion = :usuActualizacion, fecActualizacion = sysdate, ipActualizacion = :ipActualizacion where idUsuario=:idUsuario")
	public void actualizarEstadoUsuario(Long idUsuario, String estadoUsuario, String usuActualizacion, String ipActualizacion);

}
