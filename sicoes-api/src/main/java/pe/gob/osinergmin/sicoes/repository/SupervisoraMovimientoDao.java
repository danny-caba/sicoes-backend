package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.ItemEstado;
import pe.gob.osinergmin.sicoes.model.OtroRequisito;
import pe.gob.osinergmin.sicoes.model.SupervisoraMovimiento;

@Repository
public interface SupervisoraMovimientoDao  extends JpaRepository<SupervisoraMovimiento, Long> {

	@Query("select m from SupervisoraMovimiento m "
			+ "left join fetch m.sector s "
			+ "left join fetch m.subsector ss "
			+ "left join fetch m.supervisora su "
			+ "left join fetch m.estado e "
			+ "left join fetch m.motivo mo "
			+ "left join fetch m.tipoMotivo tm "
			+ "left join fetch m.accion a "
			+ "left join fetch m.propuestaProfesional pp "
			+ "where m.idSupervisoraMovimiento=:idSupervisoraMovimiento ")
	public SupervisoraMovimiento obtener(Long idSupervisoraMovimiento);

	
	@Query(value="select m from SupervisoraMovimiento m "
			+ "left join fetch m.sector s "
			+ "left join fetch m.subsector ss "
			+ "left join fetch m.supervisora su "
			+ "left join fetch m.estado e "
			+ "left join fetch m.motivo mo "
			+ "left join fetch m.tipoMotivo tm "
			+ "left join fetch m.accion a "
			+ "left join fetch m.propuestaProfesional pp "
			+ "left join fetch pp.propuesta pr "
			+ "left join fetch pr.procesoItem pi "
			+ "left join fetch pi.proceso pc "
			+ "where (:idSector is null or s.idListadoDetalle = :idSector) "
			+ "and (:idSubsector is null or ss.idListadoDetalle = :idSubsector) "
			+ "and (:idEstadoItem is null or pi.estado.idListadoDetalle = :idEstadoItem) "
			+ "and (:item is null or pi.descripcionItem like :item) "
			+ "and (:nombreProceso is null or pc.nombreProceso like :nombreProceso) "
			+ "and (:ruc is null or pp.supervisora.codigoRuc like :ruc) "
			+ "order by m.fechaRegistro desc ",
			countQuery ="select count(m) from SupervisoraMovimiento m "
					+ "left join m.sector s "
					+ "left join m.subsector ss "
					+ "left join m.supervisora su "
					+ "left join m.estado e "
					+ "left join m.motivo mo "
					+ "left join m.tipoMotivo tm "
					+ "left join m.accion a "
					+ "left join m.propuestaProfesional pp "
					+ "left join pp.propuesta pr "
					+ "left join pr.procesoItem pi "
					+ "left join pi.proceso pc "
					+ "where (:idSector is null or s.idListadoDetalle = :idSector) "
					+ "and (:idSubsector is null or ss.idListadoDetalle = :idSubsector) "
					+ "and (:idEstadoItem is null or pi.estado.idListadoDetalle = :idEstadoItem) "
					+ "and (:item is null or pi.descripcionItem like :item) "
					+ "and (:nombreProceso is null or pc.nombreProceso like :nombreProceso) "
					+ "and (:ruc is null or pp.supervisora.codigoRuc like :ruc) "
					+ "order by m.fechaRegistro desc ")		
	public Page<SupervisoraMovimiento> listarMovimientos(Long idSector,Long idSubsector,Long idEstadoItem,String item,String nombreProceso,String ruc,Pageable pageable);
	
	@Query(value="select m from SupervisoraMovimiento m "
			+ "left join fetch m.sector s "
			+ "left join fetch m.subsector ss "
			+ "left join fetch m.supervisora su "
			+ "left join fetch m.estado e "
			+ "left join fetch m.motivo mo "
			+ "left join fetch m.tipoMotivo tm "
			+ "left join fetch m.accion a "
			+ "where su.idSupervisora =:idSupervisora "
			+ "and s.idListadoDetalle =:idSector "
			+ "and ss.idListadoDetalle =:idSubsector "
			+ "order by m.fechaRegistro desc ",
			countQuery ="select count(m) from SupervisoraMovimiento m "
					+ "left join m.sector s "
					+ "left join m.subsector ss "
					+ "left join m.supervisora su "
					+ "left join m.estado e "
					+ "left join m.motivo mo "
					+ "left join m.tipoMotivo tm "
					+ "left join m.accion a "
					+ "where su.idSupervisora =:idSupervisora "
					+ "and s.idListadoDetalle =:idSector "
					+ "and ss.idListadoDetalle =:idSubsector "
					+ "order by m.fechaRegistro desc ")		
	public Page<SupervisoraMovimiento> listarHistorial(Long idSupervisora, Long idSector, Long idSubsector, Pageable pageable);
	
	
	@Query("select m from SupervisoraMovimiento m "
			+ "left join fetch m.sector s "
			+ "left join fetch m.subsector ss "
			+ "left join fetch m.supervisora su "
			+ "left join fetch m.estado e "
			+ "left join fetch m.motivo mo "
			+ "left join fetch m.tipoMotivo tm "
			+ "left join fetch m.accion a "
			+ "left join fetch m.propuestaProfesional pp "
			+ "where su.idSupervisora=:idSupervisora "
			+ "and ss.idListadoDetalle=:idSubsector "
			+ "order by m.fechaRegistro desc ")			
	public List<SupervisoraMovimiento> listarXProfesional(Long idSupervisora,Long idSubsector);
	
	@Query("SELECT sm FROM SupervisoraMovimiento sm " +
		       "JOIN sm.propuestaProfesional pp " +
		       "JOIN pp.propuesta p " +
		       "JOIN p.procesoItem pi " +
		       "WHERE sm.supervisora.id = :idSupervisora " +
		       "AND sm.subsector.id = :idSubsector " +
		       "ORDER BY sm.fechaRegistro desc ")
	public List<SupervisoraMovimiento> listarXProfesionalXItem(Long idSupervisora, Long idSubsector);
	
	@Query("select m from SupervisoraMovimiento m "
			+ "left join fetch m.sector s "
			+ "left join fetch m.subsector ss "
			+ "left join fetch m.supervisora su "
			+ "left join fetch m.estado e "
			+ "left join fetch m.motivo mo "
			+ "left join fetch m.tipoMotivo tm "
			+ "left join fetch m.accion a "
			+ "left join fetch m.propuestaProfesional pp "
			+ "where su.idSupervisora=:idSupervisora "
			+ "and ss.idListadoDetalle=:idSubsector "
			+ "order by m.fechaRegistro desc ")			
	public List<SupervisoraMovimiento> listarUltimoMovimiento(Long idSupervisora,Long idSubsector);

	@Query("select m from SupervisoraMovimiento m "
			+ "left join fetch m.sector s "
			+ "left join fetch m.subsector ss "
			+ "left join fetch m.supervisora su "
			+ "left join fetch m.estado e "
			+ "left join fetch m.motivo mo "
			+ "left join fetch m.tipoMotivo tm "
			+ "left join fetch m.accion a "
			+ "left join fetch m.propuestaProfesional pp "
			+ "where su.idSupervisora=:idSupervisora "
			+ "order by m.fechaRegistro desc ")
	public List<SupervisoraMovimiento> listarUltimoMovimientoSinSubsector(Long idSupervisora);
	

}
