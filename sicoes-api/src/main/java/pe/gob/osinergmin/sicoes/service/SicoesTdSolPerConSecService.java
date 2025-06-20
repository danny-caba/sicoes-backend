package pe.gob.osinergmin.sicoes.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface SicoesTdSolPerConSecService extends BaseService<SicoesTdSolPerConSec, Long> {
	List <SicoesTdSolPerConSec> guardarSicoes(SicoesSolicitud sicoesSolicitud, Contexto contexto);
	List<SicoesTdSolPerConSec> obtenerSeccionPorPersonal();
	List<SicoesTdSolPerConSec> obtenerSeccionesXSolicitud(Long idSolicitud);
	List<SicoesTdSolPerConSec> guardarSeccionesSubsanacion(SicoesSolicitud sicoesSolicitud, Contexto contexto);
}