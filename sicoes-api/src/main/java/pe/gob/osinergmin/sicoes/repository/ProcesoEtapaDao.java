package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.ProcesoEtapa;

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
		+ "where p.idProceso=:idProceso")	
	public ProcesoEtapa obtener(Long idProceso);
	
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

}
