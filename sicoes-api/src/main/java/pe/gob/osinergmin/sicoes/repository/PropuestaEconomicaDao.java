package pe.gob.osinergmin.sicoes.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.PropuestaEconomica;
import pe.gob.osinergmin.sicoes.model.PropuestaTecnica;

@Repository
public interface PropuestaEconomicaDao extends JpaRepository<PropuestaEconomica, Long> {
	
	@Query("select pe from Propuesta p "
			+ "left join fetch PropuestaEconomica pe on p.propuestaEconomica.idPropuestaEconomica = pe.idPropuestaEconomica "
		+ "where p.propuestaUuid = :uuidPropuesta and pe.idPropuestaEconomica=:idPropuestaEconomica ")	
	public PropuestaEconomica obtener(Long idPropuestaEconomica,String uuidPropuesta);
	
	@Query("select p from PropuestaEconomica p "
			+ "where p.idPropuestaEconomica=:idPropuestaEconomica")
	public PropuestaEconomica obtener(Long idPropuestaEconomica);
	
	@Query(value="select pe from Propuesta p "
			+ "left join fetch PropuestaEconomica pe ",
			countQuery ="select count(pe) from Propuesta p "
					+ "left join fetch PropuestaEconomica pe ")	
	public Page<PropuestaEconomica> buscar(Pageable pageable);

	@Modifying	
	@Query("delete from PropuestaEconomica p where p.idPropuestaEconomica =:idPropuestaEconomica and p.idPropuestaEconomica=(select p.propuestaEconomica.idPropuestaEconomica from  Propuesta p where propuestaUuid = :uuidPropuesta ) ")
	public void eliminarPropuestaEconomica(Long idPropuestaEconomica, String uuidPropuesta);

}
