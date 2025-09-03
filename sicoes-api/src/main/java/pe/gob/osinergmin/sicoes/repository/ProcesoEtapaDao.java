package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.ProcesoEtapa;
import pe.gob.osinergmin.sicoes.util.Constantes;

@Repository
public interface ProcesoEtapaDao extends JpaRepository<ProcesoEtapa, Long> {
	
	@Query("select e from ProcesoEtapa e "	
			+ "left join fetch e.etapa et "
			+ "left join fetch e.proceso p "
		+ "where e.idProcesoEtapa=:idProcesoEtapa "
		+ "and p.procesoUuid=:procesoUuid")	
	public ProcesoEtapa obtener(Long idProcesoEtapa,String procesoUuid);
	
	@Query("select e from ProcesoEtapa e "	
			+ "left join fetch e.etapa et "
			+ "left join fetch e.proceso p "
		+ "where p.idProceso=:idProceso AND e.etapa = 744")	
	public ProcesoEtapa obtener(Long idProceso);
	
	@Query("select e from ProcesoEtapa e " + 
		       "left join fetch e.etapa et " +
		       "left join fetch e.proceso p " +
		"where p.idProceso = :idProceso")
	public List<ProcesoEtapa> obtenerProcesosEtapa(Long idProceso);
	
	@Query("SELECT pe FROM ProcesoEtapa pe "
			+ "WHERE pe.proceso.procesoUuid = :procesoUuid")
	public List<ProcesoEtapa> buscarPorProceso(String procesoUuid);
	
	@Query(value="select e from ProcesoEtapa e "
			+ "left join fetch e.etapa et "
			+ "left join fetch e.proceso p "
			+ "where p.procesoUuid=:procesoUuid "
			+ "order by e.fechaInicio desc ",
			countQuery ="select count(s) from ProcesoEtapa e "
					+ "left join e.etapa et "
					+ "left join e.proceso p "
					+ "where p.procesoUuid=:procesoUuid "
					+ "order by e.fechaInicio desc ")		
	public Page<ProcesoEtapa> buscar(String procesoUuid,Pageable pageable);
	
	@Query("select e from ProcesoEtapa e "	
			+ "left join fetch e.etapa et "
			+ "left join fetch e.proceso p "
			+ "where p.idProceso=:idProceso ")	
	public List<ProcesoEtapa> buscar(Long idProceso);
	
	@Query("select e from ProcesoEtapa e "	
			+ "left join fetch e.etapa et "
			+ "left join fetch e.proceso p "
		+ "where p.procesoUuid=:procesoUuid")	
	public List<ProcesoEtapa> buscar(String procesoUuid);
	
	@Query("select e from ProcesoEtapa e " +
	           "left join fetch e.etapa et " +
	           "left join fetch e.proceso p " +
		"where p.procesoUuid = :procesoUuid " +
		"and et.idListadoDetalle = :idEtapa")
	List<ProcesoEtapa> buscarExiste(String procesoUuid, Long idEtapa);

	@Query(value="select e.proceso.idProceso, e.etapa.idListadoDetalle from ProcesoEtapa e " +
			"join e.proceso p " +
			"where e.etapa.idListadoDetalle = :idListadoDetalle and p.estado.idListadoDetalle <> 722" +
			"and trunc(e.fechaFin) = trunc(sysdate - 1)")
	List<Object[]> obtenerProcesosEtapaFormulacionConsulta(Long idListadoDetalle);

	@Query("select e from ProcesoEtapa e "
			+ "left join fetch e.etapa et "
			+ "left join fetch e.proceso p "
			+ "where p.procesoUuid=:procesoUuid "
			+ "and et.codigo ='"+ Constantes.LISTADO.ETAPA_PROCESO.ETAPA_PRESENTADO+"' "
			+ "and et.orden = :orden ")
	List<ProcesoEtapa> buscarEtapaConsentimiento(String procesoUuid, Long orden);

}
 
