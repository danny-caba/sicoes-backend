package pe.gob.osinergmin.sicoes.repository;

import pe.gob.osinergmin.sicoes.model.dto.EvaluacionPendienteDTO;

public interface EvaluacionPendienteDao {
	
	public EvaluacionPendienteDTO listarEvaluacionesPendientes(Long IdOtroRequisito);
}