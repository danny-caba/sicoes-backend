package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitudSeccion;
import pe.gob.osinergmin.sicoes.model.SicoesTdSolPerConSec;
import pe.gob.osinergmin.sicoes.model.SicoesTdSoliPersProp;
import pe.gob.osinergmin.sicoes.model.dto.SicoesSolicitudSeccionDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.List;
//import pe.gob.osinergmin.sicoes.util.Contexto;

public interface SicoesSolicitudSeccionService extends BaseService<SicoesSolicitudSeccion, Long>{

    SicoesSolicitudSeccion putRequisito (SicoesSolicitudSeccion sicoesSolicitud , Contexto contexto);
    SicoesSolicitudSeccion actualizarRequisitoPersonal(SicoesTdSoliPersProp personalPropuesto , Contexto contexto);
    void registrarSolicitudSeccion(Iterable<SicoesTdSolPerConSec> secciones, List<SicoesTdSoliPersProp> profesionales, Contexto contexto);
    Page<SicoesSolicitudSeccion> obtenerRequisitosPorSeccion(Long idSeccion, Long tipoContrato, boolean evaluador, Long propuesta, Pageable pageable, Contexto contexto);
    Page<SicoesSolicitudSeccion> obtenerRequisitosPorPersonal(Long idSoliPersProp, Long tipoContrato, Pageable pageable, Contexto contexto);
    Page<SicoesTdSoliPersProp> obtenerRequisitosConFlagActivo(Long idSeccion, Long tipoContrato, Pageable pageable, Contexto contexto);
    List<SicoesSolicitudSeccion> actualizarSolicitudDetalle(List<SicoesSolicitudSeccion> listaSolicitudSeccion, Contexto contexto);
    List<SicoesSolicitudSeccion> registrarSolicitudSeccionSubsanacion(List<SicoesTdSolPerConSec> seccionesSubsanacion, Contexto contexto);
    List<SicoesSolicitudSeccion> obtenerRequisitosPorSeccionSub(Long idSeccion);
    List<SicoesSolicitudSeccion> obtenerRequisitosPorPersonalSub(Long idSoliPersProp);
    boolean modificarFlagRequisito(List<SicoesSolicitudSeccion> lstRequisitos, Contexto contexto);
    boolean descartarFlagRequisitoPersonal(SicoesTdSoliPersProp persProp, Contexto contexto);
    List<SicoesSolicitudSeccion> obtenerRequisitosPorSeccionFinalizacion(List<Long> seccionIds);
    List<SicoesSolicitudSeccion> obtenerRequisitosPorSecciones(List<Long> seccionesIds);
    List<SicoesSolicitudSeccion> obtenerRequisitosPorSeccionConPersonal(Long idSeccion);
    List<SicoesSolicitudSeccion> obtenerRequisitosEvaluados(Long idSeccion, String tipoRequisitoEvaluado);
    List<SicoesSolicitudSeccion> obtenerRequisitosPorPersonalActivo(Long idSoliPersProp);
    void actualizarProcesoRevisionPersonal(SicoesSolicitud solicitud, Contexto contexto);
}
