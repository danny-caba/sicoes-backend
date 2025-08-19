package pe.gob.osinergmin.sicoes.service;

import java.util.List;
import java.util.Map;

import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitudAdjunto;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitudSeccion;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface SicoesSolicitudAdjuntoService extends BaseService<SicoesSolicitudAdjunto, Long>{

	public SicoesSolicitudAdjunto registrarCargaArchivo(Long idDetalleSolicitud, SicoesSolicitudAdjunto solicitudAdjunto, Contexto contexto);
	public void eliminarArchivoCargado(Long id, Contexto contexto);
	public List<Map<String,Object>> getSicoesSolicitudAdjunto(int idSolcitudAdjunto , Contexto contexto);

}
