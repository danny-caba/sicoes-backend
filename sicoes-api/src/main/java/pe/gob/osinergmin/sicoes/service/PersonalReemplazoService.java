package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.model.dto.AprobacionDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.List;

public interface PersonalReemplazoService extends BaseService<PersonalReemplazo,Long> {

    Page<PersonalReemplazo> listarPersonalReemplazo(Long idSolicitud, Pageable pageable, Contexto contexto);
    PersonalReemplazo guardar(PersonalReemplazo personalReemplazo);
    PersonalReemplazo eliminarBaja(PersonalReemplazo personalReemplazo);
    PersonalReemplazo actualizar(PersonalReemplazo personalReemplazo);
    PersonalReemplazo eliminarPropuesta(PersonalReemplazo personalReemplazo);
    PersonalReemplazo registrar(PersonalReemplazo personalReemplazo, Contexto contexto);
    List<Combo> listarContratistas(String codigo);
    List<AprobacionReemp> buscarAprobacion(String requerimeinto, Long tipoaprob , Long estadoaprob, Long tiposolicitud, Long idcontratista, Long numexpediente);
    Aprobacion updateAprobacion(AprobacionDTO aprobacion,Contexto contexto) ;
    EvaluacionDocumentacion obtenerEvaluacionDocumentacion(Long id , Long idsol);
    EvaluacionDocumentacionPP obtenerEvaluacionDocumentacionBPP(Long id , Long idsol);
}
