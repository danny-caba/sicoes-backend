package pe.gob.osinergmin.sicoes.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.Estudio;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface EstudioService extends BaseService<Estudio, Long> {

	public Estudio obtener(Long idEstudio,Contexto contexto);
	
	public Page<Estudio> buscar(String tipo,Long idSolicitud,Pageable pageable,Contexto contexto);

	public void calcularEstudioSunedu(Long idSolicitud, Pageable pageable, Contexto contexto);

	public List<Estudio> buscar(String tipo, Long idSolicitud, Contexto contexto);
	
	public List<Estudio> buscarPresentacion(String tipo, Long idSolicitud, Contexto contexto);
	
	public List<Estudio> buscar(Long idSolicitud, Contexto contexto);

	public Estudio evalular(Estudio estudio, Contexto contexto);
	
	public void copiarCapacitacionesUltimaSolicitud(String solicitudUuidUltima, String solicitudUuid, Contexto contexto);

	Estudio actualizarFile(Estudio estudio, Contexto contexto);

}