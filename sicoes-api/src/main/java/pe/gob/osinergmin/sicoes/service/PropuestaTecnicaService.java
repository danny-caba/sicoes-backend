package pe.gob.osinergmin.sicoes.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.PropuestaTecnica;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface PropuestaTecnicaService extends BaseService<PropuestaTecnica, Long> {

	public Page<PropuestaTecnica> listarPropuestasTecnicas(Pageable pageable, Contexto contexto);

	public PropuestaTecnica obtener(Long id, String codigoPropuesta, Contexto contexto);

	public void eliminar(Long id, String uuidPropuesta, Contexto contexto);
}