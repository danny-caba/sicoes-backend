package pe.gob.osinergmin.sicoes.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.ProcesoEtapa;
import pe.gob.osinergmin.sicoes.model.ProcesoMiembro;
import pe.gob.osinergmin.sicoes.util.Constantes;

@Repository
public interface ProcesoMiembroDao extends JpaRepository<ProcesoMiembro, Long> {

	
	@Query("select m from ProcesoMiembro m "	
			+ "left join fetch m.cargo c "
			+ "left join fetch m.proceso p "
		+ "where m.idProcesoMiembro=:idProcesoMiembro "
		+ "and p.procesoUuid=:procesoUuid")	
	public ProcesoMiembro obtener(Long idProcesoMiembro,String procesoUuid);
	
	@Query(value="select m from ProcesoMiembro m "
			+ "left join fetch m.cargo c "
			+ "left join fetch m.proceso p "
			+ "where p.procesoUuid=:procesoUuid "
			+ "order by m.fechaRegistro desc ",
			countQuery ="select count(m) from ProcesoMiembro m "
					+ "left join m.cargo c "
					+ "left join m.proceso p "
					+ "where p.procesoUuid=:procesoUuid "
					+ "order by m.fechaRegistro desc ")	
	public Page<ProcesoMiembro> buscar(String procesoUuid,Pageable pageable);
	
	@Query("select m from ProcesoMiembro m "	
			+ "left join fetch m.cargo c "
			+ "left join fetch m.proceso p "
		+ "where p.idProceso=:idProceso "
		+ "order by m.fechaRegistro desc ")	
	public List<ProcesoMiembro> buscar(Long idProceso);

	
	@Query("select m from ProcesoMiembro m "	
			+ "left join fetch m.cargo c "
			+ "left join fetch m.estado e "
			+ "left join fetch m.proceso p "
		+ "where m.cargo.codigo like :tipoCargo "
		+ "and p.procesoUuid=:procesoUuid "
		+"and e.codigo='"+Constantes.LISTADO.ESTADO_MIEMBRO.ACTIVO +"' ")	
	public ProcesoMiembro obtener(String procesoUuid, String tipoCargo);
	
	@Query("select m from ProcesoMiembro m "	
			+ "left join fetch m.cargo c "
			+ "left join fetch m.estado e "
			+ "left join fetch m.proceso p "
		+ "where m.codigoUsuario =:codigoUsuario "
		+ "and p.procesoUuid=:procesoUuid "
		+"and e.codigo='"+Constantes.LISTADO.ESTADO_MIEMBRO.ACTIVO +"' ")	
	public ProcesoMiembro obtener(String procesoUuid, Long codigoUsuario);


}
