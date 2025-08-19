package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import pe.gob.osinergmin.sicoes.model.PropuestaConsorcio;
import pe.gob.osinergmin.sicoes.model.Supervisora;

public interface EmpresasConsorcioDao {
	
	public List<Supervisora> obtenerEmpresasSupervisoraSector(Long idSector);
	public List<PropuestaConsorcio> obtenerEmpresasConsorcio(Long idPropuestaTecnica, Long idSector);
}
