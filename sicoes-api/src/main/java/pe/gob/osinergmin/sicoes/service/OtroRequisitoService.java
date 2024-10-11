package pe.gob.osinergmin.sicoes.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.model.OtroRequisito;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface OtroRequisitoService extends BaseService<OtroRequisito, Long> {

	public OtroRequisito obtener(String tipo,Long idOtroRequisito,Contexto contexto);
	
	public Page<OtroRequisito> buscar(String tipo,Pageable pageable,Contexto contexto);

	public List<OtroRequisito> listarOtroRequisito(String tipo,Long idSolicitud);
	
	public List<OtroRequisito> listarOtroRequisitoObservado(String tipo,Long idSolicitud);
	
	public List<OtroRequisito> listarOtroRequisito(Long idSolicitud);
	
	public List<OtroRequisito> listarOtroRequisitoArchivo(String tipo,Long idSolicitud,Contexto contexto);
	
	public List<OtroRequisito> listarOtroRequisitoArchivoPresentacion(String tipo,Long idSolicitud,Contexto contexto);
	
	public Page<OtroRequisito> listarOtroRequisito(String tipo, String solicitudUuid, Pageable pageable,Contexto contexto);
	

	public OtroRequisito evalular(OtroRequisito otroRequisito, Contexto contexto);

	public OtroRequisito finalizar(OtroRequisito otroRequisito, Contexto contexto);

	public List<OtroRequisito> listarOtroRequisitoXSolicitud( Long idSolicitud, Long idUsuario, Contexto contexto);

	public List<OtroRequisito> listarOtroRequisitoXSolicitudObservados(Long idSolicitud,
			Long idUsuario, Contexto contexto);

	public List<OtroRequisito> listarOtroRequisitoXSolicitudPJ(Long idSolicitud, Long idUsuario,
			Contexto contexto);

	public List<OtroRequisito> listarOtroRequisitoXSolicitudObservadosPJ( Long idSolicitud, Long idUsuario,
			Contexto contexto);

	public List<OtroRequisito> listarOtroRequisitosPerfil(Long idSolicitud);
	
	public List<OtroRequisito> listarOtroRequisitoPerfilesPendientesDeEvaluacion();
	
	public OtroRequisito asignarEvaluadorPerfil(Long idOtroRequisito, List<Asignacion> asignaciones, Contexto contexto);
	
	public void copiarOtrosRequisitosUltimaSolicitud(String solicitudUuidUltima, String solicitudUuid, Contexto contexto);
	
	public OtroRequisito actualizarEstadoSolicitudRevertirEvaluacion(OtroRequisito otroRequisito, Contexto contexto);

	/* --- 04-01-2024 --- INI ---*/
	public List<OtroRequisito> listarOtroRequisitosPerfilObservado(Long idSolicitud);
	public List<OtroRequisito> listarOtroRequisitosPerfilCalifica(Long idSolicitud);
	public OtroRequisito modificarEvaluadorPerfil(Long idOtroRequisito, List<Asignacion> asignaciones,
			Contexto contexto);
	
	/* --- 04-01-2024 --- FIN ---*/

	public List<OtroRequisito> listarPerfilesPendientesDeEvaluacion();

	public List<OtroRequisito> listarOtroRequisitoXSolicitudAdmin(Long idSolicitud);

	public List<OtroRequisito> listarOtroRequisitoXSolicitudAdminUser(Long idSolicitud);

	
}
