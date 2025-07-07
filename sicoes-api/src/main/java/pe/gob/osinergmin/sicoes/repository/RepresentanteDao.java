package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Representante;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RepresentanteDao extends JpaRepository<Representante, Long> {
	
	@Query("select r from Representante r "
			+ "where r.idRepresentante=:idRepresentante")
	Representante obtener(Long idRepresentante);

	@Query(value="select r from Representante r ",
			countQuery = "select count(r) from Representante r ")			
			Page<Representante> buscar(Long idRepresentante,Pageable pageable);

	@Query(value="select r from Representante r " +
			"where r.idSolicitud = :idSolicitud " +
			"and r.estado.codigo = :estado " +
			"order by r.idRepresentante desc ")
	List<Representante> obtenerRepresentantesSolicitud(Long idSolicitud, String estado);

	@Modifying
	@Transactional
	@Query(value = "update SICOES_TR_REPRESENTANTE set ID_ESTADO_LD = :estado, " +
			"ID_SOLICITUD = :idSolicitud, " +
			"US_ACTUALIZACION = :usuActualizacion, " +
			"FE_ACTUALIZACION = sysdate, " +
			"IP_ACTUALIZACION = :ipActualizacion " +
			"where ID_REPRESENTANTE = (select ID_REPRESENTANTE from SICOES_TR_SOLICITUD where ID_SOLICITUD = :idSolicitud)"
			, nativeQuery = true)
	void updateEstadoRepresentanteSolicitud(@Param("idSolicitud") Long idSolicitud,
						@Param("estado") Long estado,
						@Param("usuActualizacion") String usuActualizacion,
						@Param("ipActualizacion") String ipActualizacion);
}
