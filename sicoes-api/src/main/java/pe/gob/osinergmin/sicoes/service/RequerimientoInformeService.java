package pe.gob.osinergmin.sicoes.service;

import pe.gob.osinergmin.sicoes.model.RequerimientoInforme;
import pe.gob.osinergmin.sicoes.model.RequerimientoInformeDetalle;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface RequerimientoInformeService {

    RequerimientoInforme guardar(RequerimientoInformeDetalle requerimientoInformeDetalle, Contexto contexto);

}
