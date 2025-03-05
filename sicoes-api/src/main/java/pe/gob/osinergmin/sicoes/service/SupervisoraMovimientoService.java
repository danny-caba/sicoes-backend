package pe.gob.osinergmin.sicoes.service;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.SupervisoraMovimiento;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface SupervisoraMovimientoService extends BaseService<SupervisoraMovimiento, Long> {

	public Page<SupervisoraMovimiento> listarMovimientos(Long idSector, Long idSubsector, Long idEstadoItem, String item, String nombreProceso, String ruc, Pageable pageable, Contexto contexto);

	public Page<SupervisoraMovimiento> listarHistorial(Long idSupervisora, Long idSector, Long idSubsector, Pageable pageable,
			Contexto contexto);

	public SupervisoraMovimiento desbloquear(SupervisoraMovimiento supervisoraMovimiento, Contexto contexto);

	public List<SupervisoraMovimiento> listarXProfesional(Long idSupervisora, Long idSubsector);
	
	public List<SupervisoraMovimiento> listarXProfesionalXItem(Long idSupervisora, Long idSubsector);

	public SupervisoraMovimiento ultimoMovimientoXProfesional(Long idSupervisora, Long idSubsector, Long idEstado,
			Contexto contexto);

}