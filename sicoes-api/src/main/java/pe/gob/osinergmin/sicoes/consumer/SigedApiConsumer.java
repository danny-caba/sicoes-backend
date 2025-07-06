package pe.gob.osinergmin.sicoes.consumer;

import java.io.File;
import java.util.Date;
import java.util.List;

import gob.osinergmin.siged.remote.rest.ro.base.BaseOutRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.ExpedienteOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.query.ClienteConsultaOutRO;
import pe.gob.osinergmin.sicoes.model.dto.UsuarioDetalleSigedDTO;
import pe.gob.osinergmin.sicoes.util.bean.siged.ResponseUserListDto;

public interface SigedApiConsumer {
	
	public ExpedienteOutRO crearExpediente(ExpedienteInRO expediente, List<File> archivos);
	public DocumentoOutRO agregarDocumento(ExpedienteInRO expediente, List<File> archivos) throws Exception;
	public Date calcularFechaFin(Date fechaInicio, Long diasPlazo,String tipoPlazo);	
	public BaseOutRO archivarExpediente(String numeroExpediente, String observacion);
	public List<ResponseUserListDto.Usuario> listarUsuariosSiged() throws Exception;
	public UsuarioDetalleSigedDTO obtenerUsuarioSiged(Long idUsuario) throws Exception;
	public ClienteConsultaOutRO buscarCliente(Integer tipoIdentificacion, String nroIdentificacion);
	
}
