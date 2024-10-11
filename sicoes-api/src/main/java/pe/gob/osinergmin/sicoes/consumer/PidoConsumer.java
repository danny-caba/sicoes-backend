package pe.gob.osinergmin.sicoes.consumer;

import java.util.List;

import gob.osinergmin.sne.domain.dto.rest.out.PidoOutRO;
import pe.gob.osinergmin.sicoes.util.bean.PidoBeanOutRO;
import pe.gob.osinergmin.sicoes.util.bean.SuneduOutRO;
import pe.gob.osinergmin.sicoes.util.bean.siged.UnidadOutRO;
import pe.gob.osinergmin.sicoes.util.bean.siged.UsuarioOutRO;

public interface PidoConsumer {
	public PidoBeanOutRO obtenerContribuyente(String ruc);
	public PidoBeanOutRO obtenerPidoCiudadanoOrquestado(String nroDocumento);
	//public PidoBeanOutRO obtenerCiudadnoExtranjero(String nroDocumento);
	public SuneduOutRO obtenerGrados(String nroDocumento);
	public List<pe.gob.osinergmin.sicoes.util.bean.siged.DocumentoOutRO> listarExpediente(String numeroExpediente);
	public List<UnidadOutRO> listarUnidad();
	public List<UsuarioOutRO> listarUsuarios();
	public PidoOutRO consultarPidoMigraccionesCiudadanoExtranjero(String nroDocumento) throws Exception;
}
