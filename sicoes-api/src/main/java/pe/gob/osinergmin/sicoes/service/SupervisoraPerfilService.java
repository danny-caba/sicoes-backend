package pe.gob.osinergmin.sicoes.service;

import java.io.InputStream;
import java.util.List;

import net.bytebuddy.implementation.bind.annotation.Super;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.SupervisoraPerfil;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface SupervisoraPerfilService extends BaseService<SupervisoraPerfil, Long>  {

	public SupervisoraPerfil obtener(Long idSupervisora,Contexto contexto);
	
	public Page<SupervisoraPerfil> buscar(String[] codigoTipoPersona,String idTipoDocumento,String ruc,
			String nombres,
			String perfil,
			String fechaInicio,
			String fechaFin,Pageable pageable,Contexto contexto);
	
	public List<SupervisoraPerfil> buscar(Long idSupervisora,Contexto contexto);
	
	public InputStream generarExport(String numeroExpediente,Long idTipoEmpresa,String nombreRazonSocial,String numeroDocumento, Contexto contexto);

	public List<SupervisoraPerfil> buscar(Long idSupervisora, Long idSector, Long idSubSector);

	public List<SupervisoraPerfil> buscarBloqueados(Long idSupervisora, Long idSubsector, Contexto contexto);

	public Page<Supervisora> liberacionPersonal(String codigoRuc, Long idEstado, Long idSector, Long idSubsector,
			String proceso, String item, Pageable pageable, Contexto contexto);

	public Page<SupervisoraPerfil> buscarPorIdPropuesta(Long idPropuesta, Pageable pageable, Contexto contexto);
	public Page<SupervisoraPerfil> buscarPorIdPerfil(Long idPerfil,Pageable pageable, Contexto contexto);
}