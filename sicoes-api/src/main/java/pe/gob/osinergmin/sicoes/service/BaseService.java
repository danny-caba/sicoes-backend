package pe.gob.osinergmin.sicoes.service;

import pe.gob.osinergmin.sicoes.util.Contexto;

public interface BaseService<T,ID> {
	
	T guardar(T model, Contexto contexto);
	T obtener(ID id, Contexto contexto);
	void eliminar(ID id, Contexto contexto);
	
}
