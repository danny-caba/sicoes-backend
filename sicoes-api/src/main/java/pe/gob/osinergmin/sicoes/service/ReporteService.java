package pe.gob.osinergmin.sicoes.service;

import java.io.InputStream;
import java.util.List;

import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.vo.CeapamDTO;

public interface ReporteService {
	
	public InputStream  rpt01CeapamSupervisados(String fechaInicio,String fechaFin, Long idEncargado,Long idModalidad,Long idSede,Contexto contexto);

	public InputStream  rpt02CeapamAcreditados(String fechaInicio,String fechaFin,Long tipoCeapam,Contexto contexto);
	
	public InputStream rpt03Seguimientos(String sFechaInicio,String sFechaFin,Long tipoCeapam,Long idResponsable,Long idSede, Contexto contexto);
	
	public InputStream rpt04Seguimiento_EstadoSalud_BienestarPam(String fechaInicio,String fechaFin,Long tipoCeapam,Long idEncargado,Long idSede,Contexto contexto);

	public InputStream rpt07Directorio(Long idTipoCeapam, Long idCondicionCeapam, Long idTipoInstitucion,Long idAcreditado, Long idEstado, Contexto contexto);

	public InputStream rpt08PersonasAdultasMayores(Long idTipoCeapam, Long idCondicionCeapam, Long idTipoInstitucion,Long idSede, Contexto contexto);

	public InputStream rpt09_1_TiemposProcesoAcreditacion(String fechaInicio,String fechaFin,Long idTipoCeapam, Long idCondicionCeapam,Long idTipoInstitucion, Contexto contexto);
	
	public InputStream rpt09_2_TiemposProcesoSupervision(String fechaInicio,String fechaFin,Long idTipoCeapam, Long idCondicionCeapam,Long idTipoInstitucion, Contexto contexto);

	public InputStream rpt10_1_ServiciosAmbienteInfraestructura(String fechaInicio, String fechaFin, Long idTipoCeapam, Long idCondicionCeapam, Long idTipoInstitucion, Long idSede, Contexto contexto);
	
	public InputStream rpt10_2_SalonesMobiliariosEnseres(String fechaInicio, String fechaFin, Long idTipoCeapam, Long idCondicionCeapam, Long idTipoInstitucion, Long idSede, Contexto contexto);

	public InputStream rpt11PersonalCeapam(String fechaInicio, String fechaFin, Long idTipoCeapam,Long idCondicionCeapam, Long idTipoInstitucion, Contexto contexto);

	public InputStream rpt12CeapamIdentificados(String fechaInicio, String fechaFin, Long idTipoCeapam,Long idCondicionCeapam, Long idTipoInstitucion, Contexto contexto);

	public InputStream rpt13CeapamObservacionesAcreditacion(Long idEtapa, Long idTipoCeapam, Long idCondicionCeapam,Long idTipoInstitucion, Contexto contexto);

	public InputStream rpt14CeapamObservacionesSupervision(Long idEtapa, Long idTipoCeapam, Long idCondicionCeapam,Long idTipoInstitucion, Contexto contexto);

	public List<CeapamDTO> busquedaCeapams(String codigo, String nombre, Contexto contexto);

	public List<CeapamDTO> listadoCeapams(Contexto contexto);
	
}
