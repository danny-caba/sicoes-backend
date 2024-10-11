package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.ProcesoMiembro;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface ProcesoMiembroService extends BaseService<ProcesoMiembro, Long> {

	public Page<ProcesoMiembro> listarMiembros(String uuidProceso,Pageable pageable, Contexto contexto);

	public ProcesoMiembro obtener(Long id, String uuidProceso, Contexto contexto);

	public void eliminarMiembro(Long id, String uuidProceso, Contexto contexto);

	public ProcesoMiembro obtenerXtipo(String procesoUuid, String tipoCargo, Contexto contexto);
	
	public ProcesoMiembro obtenerXUsuario(String procesoUuid,Long codigoUsuario, Contexto contexto);

	public ProcesoMiembro inactivar(ProcesoMiembro miembro, Contexto contexto);
}