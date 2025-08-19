package pe.gob.osinergmin.sicoes.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.DictamenEvaluacion;
import pe.gob.osinergmin.sicoes.model.OtroRequisito;
import pe.gob.osinergmin.sicoes.util.Contexto;


public interface DictamenEvaluacionService extends BaseService<DictamenEvaluacion, Long> {

	public DictamenEvaluacion obtenerXSolicitud(Long idSolicitud, Long idSector);

	public Page<DictamenEvaluacion> listarXSolicitud(String solicitudUuid, Pageable pageable, Contexto contexto);

	public List<DictamenEvaluacion> listar(Long idSolicitud, Contexto contexto);

	public DictamenEvaluacion obtenerXSol(Long idSolicitud, Long idSector, Contexto contexto);

	public void eliminarXSector(OtroRequisito requisito, Contexto contexto);

	public void guardarDictamen(DictamenEvaluacion dictamenNuevo, Contexto contexto);

	public void eliminarXCodigoSector(String sectorEnergetico, Long idSolicitud, Contexto contexto);

	public DictamenEvaluacion obtenerXCodigoSector(Long idSolicitud, String sectorEnergetico, Contexto contexto);

	Double sumarMontoEvaluado(Long idSolicitud, Contexto contexto);

}