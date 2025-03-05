package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.ProcesoConsulta;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.io.InputStream;

public interface ProcesoConsultaService extends BaseService<ProcesoConsulta, Long> {
    Page<ProcesoConsulta> consultasPorUsuario(Long idProceso, Pageable pageable, Contexto contexto);
    void eliminar(String uuid, Contexto contexto);
    InputStream generarExport(Long idProceso) throws Exception;
    boolean actualizarEstadoEnvio(Long idProceso, Contexto contexto);
}
