package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.PersonalReemplazo;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface PersonalReemplazoService extends BaseService<PersonalReemplazo,Long> {

    Page<PersonalReemplazo> listarPersonalReemplazo(Long idSolicitud, Pageable pageable, Contexto contexto);
    PersonalReemplazo guardar(PersonalReemplazo personalReemplazo);
    PersonalReemplazo eliminarBaja(PersonalReemplazo personalReemplazo);
    PersonalReemplazo actualizar(PersonalReemplazo personalReemplazo);
    PersonalReemplazo eliminarPropuesta(PersonalReemplazo personalReemplazo);
    PersonalReemplazo registrar(PersonalReemplazo personalReemplazo, Contexto contexto);
}
