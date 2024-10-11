package pe.gob.osinergmin.sicoes.service;

import pe.gob.osinergmin.sicoes.model.RolOpcion;

public interface RolOpcionService extends BaseService<RolOpcion,Long>{

	void eliminar(Long idRol, Long idOpcion);

}
