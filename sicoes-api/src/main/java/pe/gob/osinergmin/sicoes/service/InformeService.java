package pe.gob.osinergmin.sicoes.service;

import pe.gob.osinergmin.sicoes.model.dto.InformeDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface InformeService {

    InformeDTO guardar(InformeDTO informeDTO, Contexto contexto);

}
