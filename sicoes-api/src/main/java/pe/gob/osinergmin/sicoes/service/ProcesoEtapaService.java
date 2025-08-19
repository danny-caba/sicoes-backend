package pe.gob.osinergmin.sicoes.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.ProcesoEtapa;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface ProcesoEtapaService extends BaseService<ProcesoEtapa, Long> {

	Page<ProcesoEtapa> listarEtapas(String uuidProceso,Pageable pageable, Contexto contexto);

	ProcesoEtapa obtener(Long id, String uuidProceso, Contexto contexto);
	
	ProcesoEtapa obtener(Long idProceso, Contexto contexto);

	void eliminar(Long id, String uuidProceso, Contexto contexto);
	
	List<ProcesoEtapa> listar(String procesoUuid, Contexto contexto);
	
	List<ProcesoEtapa> obtenerProcesosEtapa(Long idProceso, Contexto contexto);

	List<Object[]> listarEtapasFormulacionConsultas(Long idListadoDetalle);

}