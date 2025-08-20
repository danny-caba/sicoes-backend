package pe.gob.osinergmin.sicoes.service;

import java.util.List;

import pe.gob.osinergmin.sicoes.model.PropuestaConsorcio;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface PropuestaConsorcioService extends BaseService<PropuestaConsorcio, Long>{
	
	public List<Supervisora> obtenerEmpresasSupervisoraSector(Long idSector);
	
	public List<PropuestaConsorcio> obtenerEmpresasConsorcio(Long idPropuestaTecnica, Long idSector);

	public void eliminar(Long idProConsorcio, Contexto contexto);
}
