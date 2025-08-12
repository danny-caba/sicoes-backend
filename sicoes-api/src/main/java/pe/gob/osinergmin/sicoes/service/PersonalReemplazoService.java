package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.model.dto.*;
import pe.gob.osinergmin.sicoes.model.PersonalReemplazo;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.Date;
import java.util.List;

public interface PersonalReemplazoService extends BaseService<PersonalReemplazo,Long> {

    Page<PersonalReemplazo> listarPersonalReemplazo(Long idSolicitud, String descAprobacion, String descEvalDocIniServ,
                                                    Pageable pageable, Contexto contexto);
    PersonalReemplazo guardar(PersonalReemplazo personalReemplazo, Contexto contexto);
    PersonalReemplazo eliminarBaja(PersonalReemplazo personalReemplazo, Contexto contexto);
    PersonalReemplazo actualizar(PersonalReemplazo personalReemplazo, Contexto contexto);
    PersonalReemplazo eliminarPropuesta(PersonalReemplazo personalReemplazo, Contexto contexto);
    PersonalReemplazo registrar(PersonalReemplazo personalReemplazo, Contexto contexto);
    List<Combo> listarContratistas(String codigo);
    List<AprobacionReemp> buscarAprobacion(String requerimeinto, Long tipoaprob , Long estadoaprob, Long tiposolicitud, Long idcontratista, Long numexpediente);
    Aprobacion updateAprobacion(AprobacionDTO aprobacion,Contexto contexto) ;
    EvaluacionDocumentacion obtenerEvaluacionDocumentacion(Long id , Long idsol);
    EvaluacionDocumentacionPP obtenerEvaluacionDocumentacionBPP(Long id , Long idsol);
    PersonalReemplazo obtenerPersonalReemplazo(Long idReemplazo);
    EvaluarDocuResponseDTO evaluarConformidad(EvaluarDocuRequestDTO request, Contexto contexto);
    GenericResponseDTO<List<EvaluarDocuResponseDTO>> registrarObservaciones(List<EvaluarDocuRequestDTO> request, Contexto contexto);
    PersonalReemplazo registrarDocIniServ(PersonalReemplazo personalReemplazo, Contexto contexto);
    GenericResponseDTO<String> registrarRevDocumentos(RegistrarRevDocumentosRequestDTO request, Contexto contexto);
    Page<HistorialAprobReemp> listarHistorialReemp(Long idReemplazo, Pageable pageable );
    EvaluacionDocInicioServ obtenerEvaluacionDocInicioServicio(Long id);
    List<DocumentoInicioServ> obtenerDocumentosInicioServicio( Long id, String seccion);
    Boolean evaluarFechaDesvinculacion (Long id, Date fecha);
    PersonalReemplazo evaluarDocumentoInforme(Long id, Date fecha);
    List<DocumentoPP> obtenerDocumentoPPxSeccion(Long id, String idseccion);
    Boolean evaluarDocumReemplazo(EvaluarDocuDTO evaluacion);

}
