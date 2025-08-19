package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import pe.gob.osinergmin.sicoes.model.Paces;
import pe.gob.osinergmin.sicoes.model.Proceso;


public interface PacesDao extends JpaRepository<Paces, Long> {
	

	
	@Query(value="select distinct p from Paces p "					
			+ "where (:idArea is null or p.idDivision = :idArea) "
			+ "and eliminado=false "			
			+ "order by p.idPaces desc ",
			countQuery ="select count(distinct p) from Paces p "					
					+ "where (:idArea is null or p.idDivision = :idArea) "
					+ "and eliminado=false "					
					+ "order by p.idPaces desc ")		
	public Page<Paces> obtenerPor(Long idArea,Pageable pageable);

	@Query(value="select distinct p from Paces p "					
			+ "where (:idArea is null or p.idDivision = :idArea) "
			+ "and eliminado=false "
			+ "and (p.idAprobadorg2 = :idAprobadorg2) "			
			+ "order by p.idPaces desc ",
			countQuery ="select count(distinct p) from Paces p "					
					+ "where (:idArea is null or p.idDivision = :idArea) "
					+ "and eliminado=false "		
					+ "and (p.idAprobadorg2 = :idAprobadorg2) "				
					+ "order by p.idPaces desc ")		
	public Page<Paces> obtenerAsignadosG2Por(Long idArea,Long idAprobadorg2,Pageable pageable);

	@Query(value="select distinct p from Paces p "					
			+ "where (:idArea is null or p.idDivision = :idArea) "
			+ "and eliminado=false "
			+ "and (p.idAprobadorg3 = :idAprobadorg3) "			
			+ "order by p.idPaces desc ",
			countQuery ="select count(distinct p) from Paces p "					
					+ "where (:idArea is null or p.idDivision = :idArea) "
					+ "and eliminado=false "		
					+ "and (p.idAprobadorg3 = :idAprobadorg3) "				
					+ "order by p.idPaces desc ")		
	public Page<Paces> obtenerAsignadosG3Por(Long idArea,Long idAprobadorg3,Pageable pageable);

	
	@Query(value="select distinct p from Paces p "					
			+ "where (:idArea is null or p.idDivision = :idArea) "
			+ "and eliminado=false "	
			+ "and idProceso is null "
			+ "order by p.idPaces desc ",
			countQuery ="select count(distinct p) from Paces p "					
					+ "where (:idArea is null or p.idDivision = :idArea) "
					+ "and eliminado=false "
					+ "and idProceso is null "
					+ "order by p.idPaces desc ")		
	public Page<Paces> obtenerAceptadoEnviadoPor(Long idArea,Pageable pageable);
	

	@Modifying
	@Query(value="update Paces set deMes=:deMes, "+ 								 
								 " noConvocatoria=:noConvocatoria, "+
								 " dePresupuesto=:dePresupuesto, "+
								 " deItemPaces=:deItemPaces, "+
								 " reProgramaAnualSupervision=:reProgramaAnualSupervision "+
								"where idPaces=:idPaces  ")
	public void actualizar(Long idPaces,String deMes,String noConvocatoria,String dePresupuesto,String deItemPaces,String reProgramaAnualSupervision);
	
	
	
}
