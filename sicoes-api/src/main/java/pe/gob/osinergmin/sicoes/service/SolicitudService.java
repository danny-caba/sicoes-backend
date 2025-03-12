package pe.gob.osinergmin.sicoes.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface SolicitudService extends BaseService<Solicitud, Long>{

	public Page<Solicitud> buscar(String fechaDesde,String fechaHasta,String nroExpediente, Long idTipoSolicitud,
			Long idEstadoSolicitud,String solicitante,Long idEstadoRevision,Pageable pageable,Contexto contexto);
	public Solicitud enviar(Solicitud solicitud, Contexto contexto);
	public void actualizarAsignado(Asignacion asignacion, Contexto contexto);
	public Archivo generarReporte(Long id, Contexto contexto)throws Exception ;
	public Archivo generarReporteSubsanacion(Long id, Contexto contexto)throws Exception ;
	public Archivo generarReporteResultado(Solicitud solicitud,String tipo, Contexto contexto) throws Exception;
	public Solicitud guardarObservacionAdm(Solicitud solicitud, Contexto contexto);
	public Solicitud guardarObservacionTec(Solicitud solicitud, Contexto contexto);
	public Solicitud guardarResultado(Solicitud solicitud, Contexto contexto);
	public List<pe.gob.osinergmin.sicoes.util.bean.siged.DocumentoOutRO> obtenerExpediente(String solicitudUuid, Contexto contexto);
	public Solicitud finalizarAdministrativo(Solicitud solicitud, Contexto contexto);
	public Solicitud finalizarTecnico(Solicitud solicitud, Contexto contexto);
	public void actualizarProceso(Solicitud solicitud,String codigoTipo, Contexto contexto);
	public Page<Solicitud> buscarResponsable(String fechaDesde, String fechaHasta, String nroExpediente,
			Long idTipoSolicitud, Long idEstadoSolicitud, String solicitante, Long idEstadoRevision, Pageable pageable,
			Contexto contexto);
	
	public Page<Solicitud> buscarEvaluador(String sFechaDesde,String sSechaHasta,String nroExpediente, Long idTipoSolicitud,
			Long idEstadoSolicitud, String solicitante, Long idEstadoRevision,Long idEstadoEvalTecnica, Long idEstadoEvalAdministrativa, Pageable pageable,Contexto contexto);
	public Solicitud guardar(Solicitud solicitud);
	Solicitud actualizar(Solicitud solicitud, Contexto contexto);
	Solicitud modificar(String solicitudUuid, Contexto contexto);
	Solicitud editar(String solicitudUuid, Contexto contexto);
	public void finalizarRevision(Long idSolicitud,String codigoTipo, Contexto contexto);
	public void regresarProceso(Solicitud solicitud, String codigo, Contexto contexto);
	public Solicitud clonarSolicitud(Long idSolicitud, Contexto contexto);
	public void copiar(Long idSolicitud, Contexto contexto);
	public void marcarSolicitudRespuesta(Long idSolicitud,Date fecha, Contexto contexto);
	public void marcarSolicitudArchivado(Long idSolicitud, Contexto contexto);
	public void archivarSolicitud();
	public void registrarNotificacionSolicitud(Contexto contexto) throws Exception;
	public Page<Solicitud> buscarAprobador(String nroExpediente, String solicitante, Long idTipoSolicitud,
			Long idEstadoRevision, Long idEstadoEvaluacionTecnica, Long idEstadoEvaluacionAdministrativa,
			Pageable pageable, Contexto contexto);
	
	ListadoDetalle obtenerTipoPersona(String solicitudUuid);
	
	boolean validarJuridicoPostor(String idSolicitud);
	Solicitud obtener(String solicitudUuid, Contexto contexto);
	Long obtenerId(String solicitudUuid);
	public Solicitud obtenerSolicitudAprobada(String codigoRuc);
	public Archivo generarInformeTecnico(Long idSolicitud, Long idAsignacion, Contexto contexto)throws Exception ;
	public ExpedienteInRO crearExpedienteAgregarDocumentos(Solicitud solicitud, Contexto contexto);
	public Solicitud obtenerSinAsignados(Long idSolicitud, Contexto contexto);
	public void subirDocumentoTecnicos(Contexto contextoAnonimo);
	public void subirDocumentoTecnicos(Archivo archivoAux,Contexto contexto);
	public Solicitud obtenerUltimaSolicitudPresentadaPorUsuario(Contexto contexto);
        public void anularSolicitud(Long idSolicitud, Contexto contexto);
	public void cancelarSolicitud(String solicitudUuid, Contexto contexto);
	public List<Long> obtenerSubsectoresXUsuarioSolicitud(String uuid,Long idUsuario);
	public Archivo generarInformeAdministrativo(Long idSolicitud, Long idAsignacion, Contexto contexto) throws Exception; //AFC
	public void subirDocumentoAdministrativos(Archivo informeVT, Contexto contexto); //AFC
}
