package pe.gob.osinergmin.sicoes.service;
import java.io.InputStream;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.PropuestaProfesional;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface PropuestaProfesionalService extends BaseService<PropuestaProfesional, Long> {

	public Page<PropuestaProfesional> listarPropuestasProfesionales(String fechaDesde, String fechaHasta, Long idSector, Long idSubsector, String nroProceso, String nombreProceso, Long idEstadoItem, Long idEstadoInvitacion,String empresa,String item, Pageable pageable, Contexto contexto);

	public PropuestaProfesional obtener(Long id, String codigoPropuesta, Contexto contexto);
	
	public PropuestaProfesional rechazarInvitacion(Long id,PropuestaProfesional propuestaProfesional,String uuidPropuesta,Contexto contexto);
	
	public PropuestaProfesional aceptarInvitacion(Long id,PropuestaProfesional propuestaProfesional,String uuidPropuesta,Contexto contexto);

	public void eliminar(Long id, String uuidPropuesta, Contexto contexto);

	public List<PropuestaProfesional> listar(String propuestaUuid, Contexto contexto);

	public List<PropuestaProfesional> listarPorId(Long id, Contexto contexto);

	public PropuestaProfesional cancelarInvitacion(Long id, PropuestaProfesional propuestaProfesional,String propuestaUuid, Contexto contexto);

	public List<Object[]> listarProfesionalesXPerfil(String procesoItemUuid,Contexto contexto);

	public Page<PropuestaProfesional> listarPropuestasProfesionalesXPropuesta(String propuestaUuid, Pageable pageable, Contexto contexto);

	public InputStream generarExport(String procesoItemUuid, Contexto contexto);

	public List<PropuestaProfesional> listar();

	public List<PropuestaProfesional> listarAceptados(String propuestaUuid, Contexto contexto);

	public Page<PropuestaProfesional> listarProfesionalesAceptados(String propuestaUuid, Pageable pageable, Contexto contexto);

	public List<PropuestaProfesional> listarAceptadosXItem(String procesoItemUuid, Contexto contexto);

	public List<PropuestaProfesional> listarNoGanadoresXItem(String procesoItemUuid, Contexto contexto);

	public List<PropuestaProfesional> listarNoAceptados(String propuestaUuid, Contexto contexto);

	public List<Object[]> obtenerPersonalPropuesto(Long idSoliPerfCont);

}