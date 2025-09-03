package pe.gob.osinergmin.sicoes.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.model.HistorialVacaciones;
import pe.gob.osinergmin.sicoes.model.OtroRequisito;
import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.model.dto.DetalleVacacionesDTO;
import pe.gob.osinergmin.sicoes.model.dto.EvaluacionPendienteDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.bean.siged.AccessRequestInFirmaDigital;

public interface AsignacionService extends BaseService<Asignacion, Long> {

	public Asignacion obtener(Long idAsignacion,Contexto contexto);
	
	public Page<Asignacion> buscar(Long idSolicitud,String codigoTipoAprobador, Pageable pageable,Contexto contexto);
	
	public List<Asignacion> guardar(List<Asignacion> asignaciones, Contexto contexto);	
	
	public Asignacion guardarAprobador(Asignacion asignacion, Contexto contexto);

	public boolean modificarAprobador(Asignacion asignacion, Contexto contexto);

	public Asignacion guardar(Asignacion asignacion);
		
	public boolean actualizarAsignados(Long idSolicitud,String codigoTipoAprobador, Long numeroOrden,Contexto contexto) ;

	public Page<Asignacion> buscarAprobaciones(String solicitudUuid, Pageable pageable, Contexto contexto);

	public void eliminarXIdSolicitud(Long idSolicitud, Contexto contexto);

	public void eliminarAprobador(Long id, Contexto contexto);

	public List<Asignacion> obtener(Long idSolicitud, Long idUsuario);
	
	public List<Asignacion> buscarAprobadores(Long idSolicitud, Long idUsuario);

	public List<Asignacion> buscarAprobadoresPJ(Long idSolicitud, Long idUsuario);
	
	public void listarOtroRequisitoPerfilesPendientesDeEvaluacion(Contexto contexto);
	
	public void listarOtroRequisitoPerfilesPendientesDeAprobacion(Contexto contexto);

	/* --- 04-01-2024 --- INI ---*/
	public Asignacion clonarAsignacion(Solicitud solicitudBD, Solicitud soliNueva, Contexto contexto);
	/* --- 04-01-2024 --- FIN ---*/
	
	public List<EvaluacionPendienteDTO> listarPerfilesPendientesDeEvaluacion();
	
	public void notificarEvaluacionesPendientes(List<EvaluacionPendienteDTO> listaEvaluacionesPendientes, Contexto contexto);
	
	public void enviarMensajeEvaluacionPendiente(List<EvaluacionPendienteDTO> evaluaciones, String correoUsuario, String tipo, Contexto contexto);
	
	public void actualizarEvaluacionPendientePorVacaciones(Contexto contexto) throws Exception;
	
	public List<DetalleVacacionesDTO> obtenerListadoVacaciones() throws Exception;
	
	public Boolean registrarVacaciones(DetalleVacacionesDTO vacaciones, Contexto contexto) throws ParseException;
	
	public void actualizarPerfilAprobadorG2XVacaciones(Long idUsuarioSaliente, Long idUsuarioReemplazo, Contexto contexto);
	
	public void actualizarAsignacionXVacaciones(Long idUsuarioSaliente, Long idUsuarioReemplazo, Contexto contexto);
	
	public void retornarAsignacionXFinalVacaciones(List<DetalleVacacionesDTO> listaVacaciones, Contexto contexto);

	public List<Asignacion> buscarAprobadoresAdministrativos(Long idSolicitud, Long idUsuario); //AFC

	public List<Asignacion> buscarAprobadoresAdministrativosPJ(Long idSolicitud, Long idUsuario); //AFC

	public Long obtenerIdArchivo(String numeroExpediente, String nombreUsuario) throws Exception;
	
	public AccessRequestInFirmaDigital obtenerParametrosfirmaDigital(String token, String usuario) throws Exception;

	Asignacion validarAprobador(Asignacion asignacion, Contexto contexto);

	public void rechazarPerfil(Long idAsignacion, Long idOtroRequisito, String observacion, Contexto contexto);

	public void crearHistorialAsignacion(Long idAsignacion, String string, String observacion, Contexto contexto);

	public List<Integer> obtenerIdsPerfilesAsignadosAprobador(Long idAprobador);

	List<Asignacion> obtenerEvaluadoresAdministrativos(Long idSolicitud);

}
