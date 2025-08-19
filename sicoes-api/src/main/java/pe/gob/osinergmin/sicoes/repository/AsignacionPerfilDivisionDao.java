package pe.gob.osinergmin.sicoes.repository;

import java.util.List;


public interface AsignacionPerfilDivisionDao {

	public List<Long> listarAsignacionesPendientesAprobacionXRetornar(Long idDivision);
}
