package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.model.dto.*;
import pe.gob.osinergmin.sicoes.model.PersonalReemplazo;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.List;

public interface PersonalReemplazoService extends BaseService<PersonalReemplazo,Long> {

    Page<PersonalReemplazo> listarPersonalReemplazo(Long idSolicitud, String descAprobacion, String descEvalDocIniServ,
                                                    Pageable pageable, Contexto contexto);
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
    PersonalReemplazo obtenerPersonalReemplazo(Long idReemplazo);
    EvaluarDocuResponseDTO evaluarConformidad(EvaluarDocuRequestDTO request, Contexto contexto);
    GenericResponseDTO<List<EvaluarDocuResponseDTO>> registrarObservaciones(List<EvaluarDocuRequestDTO> request, Contexto contexto);
    GenericResponseDTO<String> registrarRevDocumentos(RegistrarRevDocumentosRequestDTO request, Contexto contexto);
    Page<HistorialAprobReemp> listarHistorialReemp(Long idReemplazo, Pageable pageable );
}
