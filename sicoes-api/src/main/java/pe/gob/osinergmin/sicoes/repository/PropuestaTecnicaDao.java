package pe.gob.osinergmin.sicoes.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Persona;
import pe.gob.osinergmin.sicoes.model.PropuestaTecnica;

@Repository
public interface PropuestaTecnicaDao extends JpaRepository<PropuestaTecnica, Long> {
	
	@Query("select pt from Propuesta p "
			+ "left join fetch PropuestaTecnica pt on p.propuestaTecnica.idPropuestaTecnica = pt.idPropuestaTecnica "
		+ "where p.propuestaUuid = :uuidPropuesta and pt.idPropuestaTecnica=:idPropuestaTecnica ")	
	public PropuestaTecnica obtener(Long idPropuestaTecnica,String uuidPropuesta);
	
	@Query("select p from PropuestaTecnica p "
			+ "left join fetch p.consorcio c "
			+ "where p.idPropuestaTecnica=:idPropuestaTecnica")
	public PropuestaTecnica obtener(Long idPropuestaTecnica);
	
	
	@Query(value="select pt from Propuesta p "
			+ "left join fetch PropuestaTecnica pt ",
			countQuery ="select count(pt) from Propuesta p "
					+ "left join fetch PropuestaTecnica pt ")		
	public Page<PropuestaTecnica> buscar(Pageable pageable);

	
	@Modifying
	@Query("delete from PropuestaTecnica p where p.idPropuestaTecnica =:idPropuestaTecnica and idPropuestaTecnica=(select p.propuestaTecnica.idPropuestaTecnica from  Propuesta p where propuestaUuid = :uuidPropuesta ) ")
	public void eliminarPropuestaTecnica(Long idPropuestaTecnica, String uuidPropuesta);
	

}
