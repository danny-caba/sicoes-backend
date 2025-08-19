package pe.gob.osinergmin.sicoes.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.Seccion;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface SeccionService extends BaseService<Seccion, Long> {
	 public Seccion guardar(Seccion seccion, Contexto contexto);
	 public Seccion actualizar(Seccion seccion, Contexto contexto);
	 public void eliminarSeccion(Seccion seccion, Contexto contexto);
	 public Map<String, Object> obtenerConPaginacion (int pagina,int tamaño);
	 public Map<String, Object> obtenerSinPaginacion();
	 public Page<Seccion> listarSecciones(Pageable pageable, Contexto contexto);
	 public Seccion obtenerSeccionPorId(Long idSeccion);
}