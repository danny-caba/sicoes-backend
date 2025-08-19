package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pe.gob.osinergmin.sicoes.model.ProcesoDocumento;
import pe.gob.osinergmin.sicoes.model.ProcesoEtapa;

public interface ProcesoDocumentoDao extends JpaRepository<ProcesoDocumento, Long> { 
	@Query(value="select e from ProcesoDocumento e "
			+ "left join fetch e.etapa et "
			+ "left join fetch e.proceso p "
			+ "left join fetch e.archivo ar "
			+ "where p.idProceso=:id "
			+ "and et.idListadoDetalle <> :idListadoDetalle ",
			countQuery ="select count(s) from ProcesoDocumento e "
					+ "left join e.etapa et "
					+ "left join e.proceso p "
					+ "left join e.archivo ar "
					+ "where p.idProceso=:id "
					+ "and et.idListadoDetalle <> :idListadoDetalle")
	public Page<ProcesoDocumento> buscar(Long id, Long idListadoDetalle, Pageable pageable);
	
	
	@Query("select pd from ProcesoDocumento pd inner join pd.archivo ar where pd.idProcesoDocumento = :id")
	public ProcesoDocumento obtener(Long id);
}
