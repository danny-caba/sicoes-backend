package pe.gob.osinergmin.sicoes.consumer;

import java.util.List;

import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.Ubigeo;
import pe.gob.osinergmin.sicoes.util.bean.siged.AccessRequestInFirmaDigital;

public interface SigedOldConsumer {
	
	public String subirArchivosAlfresco(Long idSolicitud,Long idPropuesta,Long idProceso,Long idSeccionRequisito,Long idContrato,Long idSoliPerfCont,Archivo archivo);
	
	public byte[] descargarArchivosAlfresco(Archivo archivo);
	
	public byte[] descargarArchivoPorUuidAlfresco(String uuid);
	
	public List<Ubigeo> departamentos() ;

	public List<Ubigeo> provincias(String codigoDepartamento);
	public List<Ubigeo> distritos(String codigoProvincia);
	
	public Long obtenerIdArchivos(String numeroExpediente, String nombreUsuario) throws Exception;
	
	public AccessRequestInFirmaDigital obtenerParametrosfirmaDigital();
}
