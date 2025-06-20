package pe.gob.osinergmin.sicoes.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.Paces;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface PacesService extends BaseService<Paces, Long> {

	public Page<Paces> obtenerPor(Long idArea,Long idEstado, Pageable pageable, Contexto contexto);
	public Page<Paces> obtenerAsignadosG2Por(Long idArea,Long idEstado,  Pageable pageable,Contexto contexto);
	public Page<Paces> obtenerAsignadosG3Por(Long idArea,Long idEstado,  Pageable pageable,Contexto contexto);
	public Boolean actualizar(Long idPaces,int deMes,String noConvocatoria,Double dePresupuesto,String deItemPaces,String reProgramaAnualSupervision,Contexto context);
	public Page<Paces> obtenerAceptadosEnviadosPor(Long idArea,Long idEstado,  Pageable pageable,Contexto contexto);
	public Boolean observarPaceDivision(Long idPaces,String observacion,Contexto context);
	public Boolean aprobarPaceDivision(Long idPaces,String observacion,Contexto context);	
	public Boolean cancelarPaceGerencia(Long idPaces,String observacion,Contexto context);
	public Boolean aprobarEnviarPaceGerencia(Long idPaces,String observacion,Contexto context);
	public Boolean actualizarAprobador(Long idPace,Long idAprobadorG2,Long idAprobadorG3,Contexto context);
}
