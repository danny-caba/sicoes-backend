package pe.gob.osinergmin.sicoes.util.bean.siged;

import pe.gob.osinergmin.sicoes.util.bean.BaseOutRO;

public class ExpedienteBeanOutRO extends BaseOutRO{
	
	private String nroExpediente;
	private String asunto;
	private String destinatario;
	private String idproceso;
	
	public ExpedienteBeanOutRO() {
	}
	
	public String getNroExpediente() {
		return nroExpediente;
	}
	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public String getIdproceso() {
		return idproceso;
	}
	public void setIdproceso(String idproceso) {
		this.idproceso = idproceso;
	}

	public ExpedienteBeanOutRO(String nroExpediente, String asunto, String destinatario, String proceso) {
		super();
		this.nroExpediente = nroExpediente;
		this.asunto = asunto;
		this.destinatario = destinatario;
		this.idproceso = proceso;
	}
	
	

}
