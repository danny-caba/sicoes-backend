package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.SolicitudNotificacion;
import pe.gob.osinergmin.sicoes.util.Contexto;


public interface SolicitudNotificacionService extends BaseService<SolicitudNotificacion, Long>{

	public Page<SolicitudNotificacion> buscar(String codigoTipoDocumento,Pageable pageable, Contexto contexto);

	public SolicitudNotificacion obtenerXSolicitud(String tipo, String solicitudUuid, Contexto contexto);
}
