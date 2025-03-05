package pe.gob.osinergmin.sicoes.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import pe.gob.osinergmin.sicoes.model.ProcesoDocumento;
import pe.gob.osinergmin.sicoes.model.ProcesoEtapa;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface ProcesoDocumentoService extends BaseService<ProcesoDocumento, Long> {

	Page<ProcesoDocumento> listarDocumentos(Long idProceso,Pageable pageable, Contexto contexto);
	
	ProcesoDocumento registrar(MultipartFile file, Long idEtapa, Long idProceso, String documentName, Contexto contexto);

    ProcesoDocumento registrarXls(MultipartFile file, Long idEtapa, Long idProceso, String documentName,
                                  Contexto contexto);
}
