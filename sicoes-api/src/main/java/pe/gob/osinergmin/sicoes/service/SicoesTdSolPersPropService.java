package pe.gob.osinergmin.sicoes.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface SicoesTdSolPersPropService extends BaseService<SicoesTdSoliPersProp, Long> {
	public SicoesTdSoliPersProp update(SicoesTdSoliPersProp model, Contexto contexto);
	List<SicoesTdSoliPersProp> registrarProfesionales(SicoesSolicitud resSolicitud, List<SicoesTdSolPerConSec> secciones, Contexto contexto);
	List<SicoesTdSoliPersProp> obtenerProfesionalesPorSeccion(Long idSeccion);
	Page<SicoesTdSoliPersProp> personasPorSeccion(Long idSeccionPerConSec, Pageable pageable, Contexto contexto);
	List<SicoesTdSoliPersProp> registrarProfesionalesSubsanacion(List<SicoesTdSolPerConSec> seccionesSubsanacion, Contexto contexto);

}