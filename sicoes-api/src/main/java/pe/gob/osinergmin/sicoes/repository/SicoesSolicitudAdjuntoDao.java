package pe.gob.osinergmin.sicoes.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.SicoesSolicitudAdjunto;

@Repository
public interface SicoesSolicitudAdjuntoDao extends JpaRepository<SicoesSolicitudAdjunto, Long> {

	@Transactional 
	@Modifying
	@Query("update SicoesSolicitudAdjunto adj set adj.estado = :estado, adj.ipActualizacion = :ip, adj.usuActualizacion = :usuario, adj.fecActualizacion = :fecha where adj.idAdjuntoSolicitud = :id")
	int setEstadoxSolicitudAdjunto(@Param("estado") String estado, @Param("id") Long id, @Param("ip") String ip, @Param("usuario") String usuario, @Param("fecha") Date fecha);
	@Query(value ="Select ID_ADJUNTO_SOLI,NO_ARCHIVO,ES_ADJUNTO_SOLI,ID_DET_SOLICITUD,NO_ALFRESCO,US_CREACION,IP_CREACION,FE_CREACION,US_ACTUALIZACION,IP_ACTUALIZACION,FE_ACTUALIZACION  from ES_SICOES.SICOES_TD_ADJUNTO_SOLI  s where   es_adjunto_soli =1 and   id_det_solicitud  = :id", nativeQuery = true)
	List<Map<String,Object>> getSicoesSolicitudAdjunto(@Param("id") int id  );

}
