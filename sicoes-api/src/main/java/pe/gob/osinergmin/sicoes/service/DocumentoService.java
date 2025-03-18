package pe.gob.osinergmin.sicoes.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.model.Documento;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface DocumentoService extends BaseService<Documento, Long> {

	public Documento obtener(Long idDocumento,Contexto contexto);
	
	public Page<Documento> buscar(Long idSolicitud, Pageable pageable,Contexto contexto);
	
	public List<Documento> buscar(Long idSolicitud, Contexto contexto);
	
	public List<Documento> buscarPresentacion( Long idSolicitud, Contexto contexto);

	public Documento evalular(Documento documento, Contexto contexto);

	public boolean validarJuridicoPostor(ListadoDetalle tipoPersona);
	
	public void copiarDocumentosUltimaSolicitud(String solicitudUuidUltima, String solicitudUuid, Contexto contexto);

	public Documento asignarEvaluadorPerfil(Documento doc, List<Asignacion> asignaciones, Contexto contexto);

	Documento actualizarFile(Documento documento, Contexto contexto);

}