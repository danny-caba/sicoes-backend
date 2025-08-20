package pe.gob.osinergmin.sicoes.service;
import java.io.InputStream;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.Propuesta;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface PropuestaService extends BaseService<Propuesta, Long> {

	public Page<Propuesta> listarPropuestas(Pageable pageable, Contexto contexto);

	Propuesta obtener(String codigo, Contexto contexto);
	
	Propuesta presentar(String codigo,Propuesta propuesta, Contexto contexto);

	public Propuesta obtenerPropuestaXProcesoItem(Long idProcesoItem,Contexto contexto);

	public InputStream generarExport(String procesoItemUuid,Contexto contexto);

	public Long obtenerIdPropuestaEconomica(String propuestaUuid);

	public Long obtenerIdPropuestaTecnica(String propuestaUuid);

	public List<String> validaciones(String propuestaUuid, Contexto contexto);
	
	public void generarArchivoDescarga(Propuesta propuesta, Contexto contexto);

	public List<Propuesta> buscarPropuestasXItem(String procesoItemUuid, Contexto contexto);

	public Propuesta seleccionar(String propuestaUuid, Propuesta propuesta, Contexto contexto);

	public Page<Propuesta> listarPropuestasXItem(String procesoItemUuid, Pageable pageable,
			Contexto contexto);
	
	public void limpiarDescarga();
	
	public List<Propuesta> obtenerTodasPropuestaXProcesoItem(Long idProcesoItem,Contexto contexto) ;

	public Propuesta obtenerPropuestaGanadora(String procesoItemUuid, Contexto contexto);

}