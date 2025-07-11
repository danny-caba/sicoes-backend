package pe.gob.osinergmin.sicoes.service;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.model.ConfiguracionBandeja;
import pe.gob.osinergmin.sicoes.model.Division;
import pe.gob.osinergmin.sicoes.model.UsuarioReasignacion;
import pe.gob.osinergmin.sicoes.model.dto.DivisionDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface ConfBandejaService extends BaseService<ConfiguracionBandeja, Long> {

	public ConfiguracionBandeja obtener(Long idConfBandeja,Contexto contexto);
	public void actualizarEstadoConfigBandeja(ConfiguracionBandeja configuracionBandeja,Contexto contexto);
	List<Map<String, List<ConfiguracionBandeja>>> registrarConfiguracionBandeja(ConfiguracionBandeja configuracionBandeja,Contexto contexto);
	public ConfiguracionBandeja actualizarConfiguracionBandeja(ConfiguracionBandeja configuracionBandeja,Contexto contexto);
	public Map<String, Object> listarConfigBandejaPorIdUsuario(Long idUsuario, Long idPerfil, Long idRol, int offset, int pageSize);
	public List<ConfiguracionBandeja> listarConfiguracionesReasignadas(Long idUsuario);
	public List<UsuarioReasignacion> listarBandejaHistorialReasignaciones(String nombreUsuario, String fechaInicio, String fechaFin, Long idDivision, int offset, int pageSize);
	public List<DivisionDTO> obtenerDivisiones();
	public Map<String, Object> listarPerfiles(Long idUsuario, int offset, int pageSize);
	public void obtenerReasignacionesFinalizadas(Contexto contexto);
}