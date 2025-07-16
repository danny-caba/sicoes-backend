package pe.gob.osinergmin.sicoes.service;

import java.io.File;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface ArchivoService extends BaseService<Archivo, Long> {

	public Archivo obtener(Long idArhivo,Contexto contexto);
	public Archivo obtener(String codigo, Contexto contexto);
	public Page<Archivo> buscar(Pageable pageable,Contexto contexto);
	public List<Archivo> buscar(Long idEstudio,Long idDocumento,Long idOtroRequisito, Contexto contexto) ;
	public List<Archivo> buscarPropuesta(Long idPropuestaTecnica, Contexto contexto) ;
	public List<Object[]> buscarPropuesta (String procesoItemUuid,Contexto contexto) ;
	public List<Archivo> buscar(Long idSolicitud, Contexto contexto) ;
	public List<Archivo> buscarPresentacion(Long idSolicitud,String tipoArchivo, Contexto contexto);
	public List<Archivo> buscarArchivo(SolicitudNotificacion solicitudNotificacion, Contexto contexto);
	public void asociarArchivos(Long idSolicitud,Long idEstudio, List<Archivo> archivos,Contexto contexto);
	public void asociarArchivo(Documento documentoBD, Archivo archivo, Contexto contexto);
	public void asociarArchivos(Long idPropuestaTecnica, List<Archivo> archivos, Contexto contexto);
	public void asociarArchivo(SolicitudNotificacion solicitudNotificacion, Archivo archivo, Contexto contexto);
	public void asociarArchivo(OtroRequisito otroRequisito, Archivo archivo, Contexto contexto);
	public List<File> obtenerArchivoContenido(Long idSolicitud,String tipoArchivo, Contexto contexto);
	public List<File> obtenerArchivoContenidoPerfCont(List<Archivo> archivosRegistrados, SicoesSolicitud solicitud, Contexto contexto);
	public Page<Archivo> buscarArchivo(String codigo, String solicitudUuid, Pageable pageable, Contexto contexto);
	public Page<Archivo> buscarArchivoPropuestaEconomica(String codigo, String propuestaUuid, Pageable pageable, Contexto contexto);
	public Page<Archivo> buscarArchivoPropuestaTecnica(String codigo, String propuestaUuid, Pageable pageable, Contexto contexto);
	public Page<Archivo> buscarArchivoProceso(String codigo, Long idProceso, Pageable pageable, Contexto contexto);
	public Archivo obtenerTipoArchivo(Long idSolicitud, String formato);
	public void eliminarIdEstudio(Long id, Contexto contexto);
	public void eliminarIdOtroRequisito(Long id, Contexto contexto);
	public void eliminarIdDocumento(Long id, Contexto contexto);
	public List<Archivo> listarEvidencias(Long idSolicitud);
	public void eliminarXIDSolicitud(Long idSolicitud);
	public List<File> obtenerArchivosContenido(Long idNotificacion);
	public List<Archivo> listarXTecnica(String propuestaUuid, Contexto contexto);
	public List<Archivo> listarXEconomica(String propuestaUuid, Contexto contexto);
	public String generarArchivoContenido(Propuesta propuesta, Contexto contexto);
	public String generarArchivoContenido(ProcesoItem procesoItem, Contexto contexto);
	public Archivo obtenerTipoArchivo(Long idSolicitud, String codigoFormato, Long idAsignacion);
	public List<Archivo> obtenerDocumentoTecnicosPendientes(Contexto contexto);
	public void actualizarEstado(Archivo archivo,Long estado, Contexto contexto);
	public Archivo obtenerContenido(Long idArhivo,Contexto contexto);
	public Archivo guardarEnSiged(Long idProceso, Archivo archivo, Contexto contexto);
	public Archivo obtenerArchivoXlsPorProceso(Long idProceso);
	List<Archivo> buscarPorPerfContrato(Long idPerfContrato, Contexto contexto);
	Archivo guardarArchivoSubsanacionContrato(Archivo archivo, Contexto contexto);
	List<Archivo> obtenerArchivosPorRequisitos(List<Long> requisitosIds, Contexto contexto);
	void eliminarArchivoCodigo(String codigo, Contexto contexto);
	List<File> obtenerArchivoDj(Long idOtroRequisito, String procedimiento, Contexto contexto);
	List<File> obtenerArchivoModificacion(Long idOtroRequisito, Contexto contexto);
	List<Archivo> buscarArchivosPendientes(Long idSolicitud, Contexto contexto);
	public Archivo guardarArchivoContrato(Long idProceso, String tipoRequisito, MultipartFile file, Contexto contexto);
	public List<Archivo> obtenerArchivosPorContrato(Long idProceso);
	public void eliminarArchivo(Long idArchivo);
	public Archivo guardarArchivoPerfContrato(Long idSoliPerfCont, String tipoRequisito, MultipartFile file, Contexto contexto);
	public List<Archivo> obtenerArchivosPorPerfContrato(Long idSoliPerfCont);
	public Archivo guardarExcelEnSiged(Long idProceso, Archivo archivo, Contexto contexto);
    List<Archivo> buscarXRequerimiento(Long idSolicitud, Contexto contexto);
	Archivo guardarXRequerimiento(Archivo archivo, Contexto contexto);
    Archivo guardarXRequerimientoAprobacion(Archivo archivo, Contexto contexto);
    Archivo guardarXRequerimientoInforme(Archivo archivo, Contexto contexto);
	Archivo guardarXRequerimientoDocumento(Archivo archivo, Contexto contexto);

}