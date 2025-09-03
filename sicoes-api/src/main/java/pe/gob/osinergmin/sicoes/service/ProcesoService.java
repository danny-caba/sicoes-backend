package pe.gob.osinergmin.sicoes.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import pe.gob.osinergmin.sicoes.model.Proceso;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface ProcesoService extends BaseService<Proceso, Long> {

	public Page<Proceso> listarProcesos(String fechaDesde,String fechaHasta,Long idEstado,Long idSector,Long idSubSector,String nroProceso,String nombreProceso,String nroExpediente,Pageable pageable, Contexto contexto);
	
	public Proceso publicar(String uuid,Proceso proceso, Contexto contexto);

	public Proceso obtener(String uuid, Contexto contexto);
	
	public void actualizarProcesoPresentacion(Contexto contexto);
	
	public void actualizarProcesoAdmision(Contexto contexto);
	
	public void validacionVerProfesionales(String procesoUuid, Contexto contexto);

	public List<String> validacionXUsuario(String procesoUuid, String procesoItemUuid, Contexto contexto);
	
	public Page<Proceso> listarProcesosSeleccion(Long idEstado, String nombreArea,String nombreProceso,Pageable pageable, Contexto contexto);
	
	public Proceso obtenerPublico(String uuid, Contexto contexto);

	void actualizarProcesoDesignacion(Contexto contexto);

	void actualizarProcesoConsentimiento(Contexto contexto);

}