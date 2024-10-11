package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.RolOpcion;
@Repository
public interface RolOpcionDao extends JpaRepository<RolOpcion, Long> {

	@Modifying
	@Query("delete from RolOpcion ro where ro.opcion.idOpcion=:idOpcion and  ro.rol.idRol=:idRol")
	void eliminar(Long idRol, Long idOpcion);
	

}
