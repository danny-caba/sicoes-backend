package pe.gob.osinergmin.sicoes.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.DictamenEvaluacion;
import pe.gob.osinergmin.sicoes.model.PropuestaConsorcio;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.SupervisoraDictamen;
import pe.gob.osinergmin.sicoes.util.Constantes;


@Repository
public interface SupervisoraDictamenDao extends JpaRepository<SupervisoraDictamen, Long> {
	
	@Query("select d from SupervisoraDictamen d "	
			+ "left join fetch d.supervisora s "
			+ "left join fetch d.sector sc "
			+ "where d.idSupervisoraDictamen=:idSupervisoraDictamen ")	
	public SupervisoraDictamen obtener(Long idSupervisoraDictamen);

	@Query("select d from SupervisoraDictamen d "	
			+ "left join fetch d.supervisora s "
			+ "left join fetch d.sector sc "
			+ "where s.idSupervisora=:idSupervisora and sc.idListadoDetalle=:idSector ")
	public SupervisoraDictamen obtenerXSupervisora(Long idSupervisora, Long idSector);
	
	@Query(value="select d from SupervisoraDictamen d "	
			+ "left join fetch d.supervisora s "
			+ "left join fetch d.sector sc "
			+ "where s.idSupervisora =:idSupervisora ")	
	public List<SupervisoraDictamen> listarXSupervisora(Long idSupervisora);
	
	@Query(value="select sd from SupervisoraDictamen sd "
			+ "left join fetch sd.sector sc"
			+ "left join fetch sd.supervisora s "
			+ "left join fetch s.tipoDocumento td "
			+ "left join fetch s.pais p "
			+ "left join fetch s.tipoPersona tp "
			+ "left join fetch s.estado se "
			+ "where "
			+ "se.codigo ='"+Constantes.LISTADO.ESTADO_SUPERVISORA.VIGENTE+"' "
			
			+ "and (:idTipoDocumento is null or td.idListadoDetalle=:idTipoDocumento) "
			+ "and (:idTipoPersona is null or tp.idListadoDetalle=:idTipoPersona) "
			+ "and (:ruc is null or s.numeroDocumento=:ruc) "
			+ "and (s.numeroExpediente like :nroExpediente) "
			+ "and (concat(s.nombreRazonSocial,' ',s.apellidoPaterno,' ',s.apellidoMaterno,' ',s.nombres,' ') like :nombres) "
			+ "and (:fechaInicio is null or s.fechaIngreso>=:fechaInicio) "
			+ "and (:fechaFin is null or s.fechaIngreso<=:fechaFin) ",
	countQuery = "select count(sd) from SupervisoraDictamen sd "
			+ "left join sd.supervisora s "
			+ "left join  s.tipoDocumento td "
			+ "left join  s.pais p "
			+ "left join  s.tipoPersona tp "
			+ "left join  s.estado se "
			+ "where "
			+ "se.codigo ='"+Constantes.LISTADO.ESTADO_SUPERVISORA.VIGENTE+"' "
			
			+ "and (:idTipoDocumento is null or td.idListadoDetalle=:idTipoDocumento) "
			+ "and (:idTipoPersona is null or tp.idListadoDetalle=:idTipoPersona) "
			+ "and (:ruc is null or s.numeroDocumento=:ruc) "
			+ "and (s.numeroExpediente like :nroExpediente) "
			+ "and (concat(s.nombreRazonSocial,' ',s.apellidoPaterno,' ',s.apellidoMaterno,' ',s.nombres,' ') like :nombres) "
			+ "and (:fechaInicio is null or s.fechaIngreso>=:fechaInicio) "
			+ "and (:fechaFin is null or s.fechaIngreso<=:fechaFin) ")
	public List<SupervisoraDictamen> buscarXMonto(
			String nroExpediente,
			Long idTipoPersona,
			Long idTipoDocumento,
			String ruc,
			String nombres,
			Date fechaInicio,
			Date fechaFin
	);
	
	//AFC
	@Query(value="select s from Supervisora s "
			+ "left join fetch s.tipoDocumento td "
			+ "left join fetch s.pais p "
			+ "left join fetch s.tipoPersona tp "
			+ "left join fetch s.estado se "
			+ "where "
			+ "se.codigo ='"+Constantes.LISTADO.ESTADO_SUPERVISORA.VIGENTE+"' "
			
			+ "and (:idTipoDocumento is null or td.idListadoDetalle=:idTipoDocumento) "
			+ "and (:idTipoPersona is null or tp.idListadoDetalle=:idTipoPersona) "
			+ "and (:ruc is null or s.numeroDocumento=:ruc) "
			+ "and (s.numeroExpediente like :nroExpediente) "
			+ "and (concat(s.nombreRazonSocial,' ',s.apellidoPaterno,' ',s.apellidoMaterno,' ',s.nombres,' ') like :nombres) "
			+ "and (:fechaInicio is null or s.fechaIngreso>=:fechaInicio) "
			+ "and (:fechaFin is null or s.fechaIngreso<=:fechaFin) ",
	countQuery = "select count(s) from Supervisora s "
			+ "left join  s.tipoDocumento td "
			+ "left join  s.pais p "
			+ "left join  s.tipoPersona tp "
			+ "left join  s.estado se "
			+ "where "
			+ "se.codigo ='"+Constantes.LISTADO.ESTADO_SUPERVISORA.VIGENTE+"' "
			
			+ "and (:idTipoDocumento is null or td.idListadoDetalle=:idTipoDocumento) "
			+ "and (:idTipoPersona is null or tp.idListadoDetalle=:idTipoPersona) "
			+ "and (:ruc is null or s.numeroDocumento=:ruc) "
			+ "and (s.numeroExpediente like :nroExpediente) "
			+ "and (concat(s.nombreRazonSocial,' ',s.apellidoPaterno,' ',s.apellidoMaterno,' ',s.nombres,' ') like :nombres) "
			+ "and (:fechaInicio is null or s.fechaIngreso>=:fechaInicio) "
			+ "and (:fechaFin is null or s.fechaIngreso<=:fechaFin) ")			
	List<Supervisora> buscarPN(String nroExpediente,
			Long idTipoPersona,
			Long idTipoDocumento,
			String ruc,
			String nombres,
			Date fechaInicio,
			Date fechaFin);
	
	
	@Query("select d "
		       + "from SupervisoraDictamen d "
		       + "join d.supervisora s "
		       + "join PropuestaConsorcio p on p.supervisora.idSupervisora = s.idSupervisora "
		       + "where p.propuestaTecnica.idPropuestaTecnica = :idPropuestaTecnica "
		       + "and d.sector.idListadoDetalle = :idSectorListadoDetalle")
		List<SupervisoraDictamen> obtenerConsorciosConFacturado(Long idPropuestaTecnica, Long idSectorListadoDetalle);

	
}
