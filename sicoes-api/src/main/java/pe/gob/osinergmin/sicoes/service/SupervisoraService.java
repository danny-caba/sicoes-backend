package pe.gob.osinergmin.sicoes.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.SuspensionCancelacion;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface SupervisoraService extends BaseService<Supervisora, Long>  {

	public Supervisora obtener(Long idSupervisora,Contexto contexto);
	
	public Page<Supervisora> buscar(String nroExpediente,Long idTipoPersona,Long idTipoDocumento,String ruc,
			String nombres,
			String perfil,
			String fechaInicio,
			String fechaFin,Pageable pageable,Contexto contexto);
	
	public Archivo generarReporte(String numeroExpediente,Long idTipoEmpresa,String nombreRazonSocial,String numeroDocumento,Contexto contexto)throws Exception ;
	public Page<Supervisora> buscarSuspendidasCanceladas(String nroExpediente,Long idTipoPersona,Long idTipoDocumento,String ruc,
			String nombres,
			String perfil,
			String sfechaInicio,
			String sfechaFin,Pageable pageable, Contexto contexto);

	public Supervisora obtenerXCodigo(Long codigoCliente, Contexto contexto);

	public Supervisora obtenerPorCodigoUsuario(Long codigoUsuario, Contexto contexto);

	public void cancelar(Supervisora supervisora);

	public void supender(Supervisora supervisora);

	public void activar(Supervisora supervisora);

	public void asociarSuspensionCancelacion(SuspensionCancelacion suspensionCancelacionBD);
	
	public Supervisora obtenerSupervisoraXNroDocumento(String numeroDocumento);

	public void limpiarSuspencionCancelacion(Long idSupervisora, Contexto contexto);

	public List<Supervisora> listarSupervisoras(Long idPerfil,Contexto contexto);

	public Supervisora obtenerSupervisoraXRUC(String codigoRuc);

	public List<Object[]> listarProfesionales(Long idListadoDetalle);

	public Supervisora obtenerSupervisoraXRUCVigente(String codigoRuc);

	public Supervisora obtenerSupervisoraPorRucPostorOrJuridica(String codigoRuc);

	public List<Supervisora> obtenerSupervisoraPNProfesional(String codigoRuc);

	public Supervisora obtenerSupervisoraXRUCNoProfesional(String codigoRuc);


	


}
