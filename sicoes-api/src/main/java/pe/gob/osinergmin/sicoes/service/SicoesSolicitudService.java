package pe.gob.osinergmin.sicoes.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface SicoesSolicitudService extends BaseService<SicoesSolicitud, Long> {

	SicoesSolicitud guardarContratoConsentido(Propuesta propuesta, Contexto contexto);
	Page<SicoesSolicitud> listarSolicitudesPresentacion(String estado, String nroConcurso, Long item, String convocatoria, String tipoSolicitud, Pageable pageable, Contexto contexto);
	boolean validarFechaPresentacionSubsanacion(Long idSolicitud, Contexto contexto);
	String actualizarSolicitud(List<SicoesSolicitudSeccion> listaSolicitudSeccion, SicoesSolicitud solicitud, Contexto contexto);
	Page<SicoesSolicitud> listarSolicitudesProceso(String estado, String nroConcurso, Long item, String convocatoria, String tipoSolicitud, Pageable pageable, Contexto contexto);
	SicoesSolicitud finalizarSolicitud(SicoesSolicitud solicitud, List<SicoesSolicitudSeccion> listaSolicitudSeccion, Contexto contexto);
	boolean enviarCorreoSancion(SicoesSolicitud solicitud, String periodoInhabilitacion, String inicio, String fin, Contexto contexto);
	boolean enviarCorreoSancionPN(Map<String, Object> datos, Supervisora supervisora, Contexto contexto);
	boolean validarRemype(String numeroDocumento, Contexto contexto);
	void registrarNotificacionSolicitudPerfCont(Contexto contexto) throws Exception;
	void archivarSolicitudesPerfContNoPresentadas(Contexto contexto);
}
