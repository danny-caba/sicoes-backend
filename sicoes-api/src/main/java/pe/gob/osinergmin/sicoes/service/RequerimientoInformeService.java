package pe.gob.osinergmin.sicoes.service;

import pe.gob.osinergmin.sicoes.model.dto.RequerimientoInformeDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface RequerimientoInformeService {

    RequerimientoInformeDTO guardar(RequerimientoInformeDTO requerimientoInformeDTO, Contexto contexto);

}
