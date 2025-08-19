package pe.gob.osinergmin.sicoes.service;

import java.io.InputStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.SuspensionCancelacion;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface SuspensionCancelacionService extends BaseService<SuspensionCancelacion, Long> {

	public SuspensionCancelacion obtener(Long id,Contexto contexto);

	public Page<SuspensionCancelacion> buscar(String nroExpediente, Long idTipoPersona, Long idTipoDocumento,
			String ruc, String nombres, Long idEstado, Pageable pageable, Contexto contexto);

	public void procesarSupencionCancelacion();

	public InputStream generarExport(String nroExpediente, Long idTipoPersona, Long idTipoDocumento,
			String ruc, String nombres, Long idEstado,  Contexto contexto);

}