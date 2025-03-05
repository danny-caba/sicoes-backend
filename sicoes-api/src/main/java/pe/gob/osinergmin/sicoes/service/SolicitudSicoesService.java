package pe.gob.osinergmin.sicoes.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.Persona;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface SolicitudSicoesService extends BaseService<SicoesSolicitud, Long>  {

	public SicoesSolicitud obtener(Long idSolicitud,Contexto contexto);
	public SicoesSolicitud  updateSolicitud (String estado,SicoesSolicitud sicoesSolicitud, Contexto contexto);
	public Optional<SicoesSolicitud>  getSolicitud (Long  id , Contexto contexto);
	
	public Optional<SicoesSolicitud>  putRequisitoFinaliza (SicoesSolicitud sicoesSolicitud , Contexto contexto);
}