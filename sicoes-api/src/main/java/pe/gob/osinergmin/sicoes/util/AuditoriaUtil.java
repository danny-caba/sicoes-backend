package pe.gob.osinergmin.sicoes.util;

import java.util.Date;

import pe.gob.osinergmin.sicoes.model.BaseModel;

public class AuditoriaUtil {
	

	public static Contexto getContextoJob() {
		Contexto contexto = new Contexto();
		contexto.setUsuarioApp("ASAP_JOB");
		contexto.setIp("127.0.0.1");	
		return contexto;
	}
	

	public static void setAuditoriaRegistro(BaseModel baseModel,Contexto contexto) {
		String usuarioCodigo=null;
		if(contexto.getUsuario()!=null){
			usuarioCodigo=contexto.getUsuario().getIdUsuario().toString();
		}else {
			usuarioCodigo=contexto.getUsuarioApp();
		}
		if(baseModel.getUsuCreacion()==null) {
			baseModel.setUsuCreacion(usuarioCodigo);

			baseModel.setFecCreacion(new Date());
			baseModel.setIpCreacion(contexto.getIp());	
		}else {
			baseModel.setUsuActualizacion(usuarioCodigo);
			baseModel.setFecActualizacion(new Date());
			baseModel.setIpActualizacion(contexto.getIp());
		}
		
	}
	
	
	public static void setAuditoriaActualizacion(BaseModel baseModel,Contexto contexto) {
		String usuarioCodigo=null;
		if(contexto.getUsuario()!=null){
			usuarioCodigo=contexto.getUsuario().getIdUsuario().toString();
		}else {
			usuarioCodigo=contexto.getUsuarioApp();
		}
		
		baseModel.setUsuActualizacion(usuarioCodigo);
		baseModel.setFecActualizacion(new Date());
		baseModel.setIpActualizacion(contexto.getIp());
		
		
	}

}
