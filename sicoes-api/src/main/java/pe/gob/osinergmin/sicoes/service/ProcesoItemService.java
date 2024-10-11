package pe.gob.osinergmin.sicoes.service;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.Proceso;
import pe.gob.osinergmin.sicoes.model.ProcesoItem;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface ProcesoItemService extends BaseService<ProcesoItem, Long> {

	Page<ProcesoItem> listarItems(String uuidProceso,Pageable pageable, Contexto contexto);
	Page<ProcesoItem> listarProcesos(String fechaDesde,String fechaHasta,Long idEstadoItem,Long idSector,Long idSubSector,String nroProceso,String nombreProceso,String descripcionItem,String nroExpediente,Long idEstadoProceso,Pageable pageable, Contexto contexto);
	ProcesoItem obtener(String codigo, Contexto contexto);
	void eliminar(String uuid, Contexto contexto);
	List<ProcesoItem> listar(String procesoUuid, Contexto contexto);
	void actualizarProcesoPresentacion(Proceso procesoBD, Contexto contexto);
	void actualizarProcesoAdmision(Proceso procesoBD, Contexto contexto);
	void generarArchivoDescarga(ProcesoItem procesoItem, Contexto contexto);
}