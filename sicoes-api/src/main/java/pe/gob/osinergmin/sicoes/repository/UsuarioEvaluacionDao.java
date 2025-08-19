package pe.gob.osinergmin.sicoes.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.model.ConfiguracionBandeja;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.UsuarioReasignacion;
import pe.gob.osinergmin.sicoes.model.UsuarioRol;
import pe.gob.osinergmin.sicoes.model.UsuarioRolConfiguracion;

public interface UsuarioEvaluacionDao {
	public List<Usuario> obtenerUsuariosEvaluadores(Long idDivision);
	public List<Asignacion> obtenerAsignacionesPorIdUsuario(Long idUsuario, Long idConfiguracionBandeja);
	public BigDecimal obtenerTotalRegistrosPerfiles(Long idUsuario);
	public List<ConfiguracionBandeja> listarPerfiles(Long idUsuario, int offset, int pageSize);
		
	public List<ConfiguracionBandeja> listarConfiguraciones(Long idUsuario,Long idPerfil, Long idRol, int offset, int pageSize);
	public BigDecimal obtenerTotalRegistrosConfiguraciones(Long idUsuario,Long idPerfil, Long idRol);
	public List<ConfiguracionBandeja> listarConfiguracionesReasignadas(Long idUsuario);
	public List<UsuarioReasignacion> listarBandejaHistorialReasignaciones(String nombreUsuario, String fechaInicio, String fechaFin, Long idDivision, int offset, int pageSize);
	public List<Asignacion> obtenerReasignacionesFinalizadas();
	public List<Usuario> obtenerUsuariosXCodigoRol(String codigoRol, Long idUsuario);
	public List<UsuarioRolConfiguracion> obtenerUsuarioRolConfiguraciones(UsuarioRol usuarioRol);
}
