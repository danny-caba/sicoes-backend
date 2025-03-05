package pe.gob.osinergmin.sicoes.service;


import pe.gob.osinergmin.sicoes.model.SicoesArchivo;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface SicoesArchivoService {

	public SicoesArchivo guardarEnAlfresco(SicoesArchivo archivo, Contexto contexto);
	
	public void eliminarIdSicoesArchivo(Long id, Contexto contexto);
}
