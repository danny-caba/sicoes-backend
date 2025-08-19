package pe.gob.osinergmin.sicoes.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.Documento;
import pe.gob.osinergmin.sicoes.model.OtroRequisito;
import pe.gob.osinergmin.sicoes.model.ProcesoItem;
import pe.gob.osinergmin.sicoes.model.Propuesta;
import pe.gob.osinergmin.sicoes.model.Requisito;
import pe.gob.osinergmin.sicoes.model.Seccion;
import pe.gob.osinergmin.sicoes.model.SolicitudNotificacion;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface RequisitoService extends BaseService<Requisito, Long> {

 
	 public Requisito guardar(Requisito requisito, Contexto contexto);
	 public Requisito actualizar(Requisito requisito, Contexto contexto);
	 public void eliminarRequisito(Requisito requisito, Contexto contexto);
	 public Map<String, Object> obtenerConPaginacion (int pagina,int tamaño);
	 public Map<String, Object> obtenerSinPaginacion();

	public Page<Requisito> listarRequisitos(Pageable pageable, Contexto contexto);


}