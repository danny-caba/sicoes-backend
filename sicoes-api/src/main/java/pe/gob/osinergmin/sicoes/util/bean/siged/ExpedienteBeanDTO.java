package pe.gob.osinergmin.sicoes.util.bean.siged;

import java.io.Serializable;

import gob.osinergmin.siged.remote.rest.ro.base.BaseOutRO;

public class ExpedienteBeanDTO extends BaseOutRO implements Serializable{
	
	private String nroExpediente;
	private String asunto;
	private String destinatario;
	private String idProceso;
	
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
	
	public String getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}
	public ExpedienteBeanDTO(ExpedienteBeanOutRO outRO) {
		nroExpediente = outRO.getNroExpediente();
		asunto = outRO.getAsunto();
		destinatario = outRO.getDestinatario();
		idProceso = outRO.getIdproceso();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExpedienteBeanDTO other = (ExpedienteBeanDTO) obj;
		if (asunto == null) {
			if (other.asunto != null)
				return false;
		} else if (!asunto.equals(other.asunto))
			return false;
		if (destinatario == null) {
			if (other.destinatario != null)
				return false;
		} else if (!destinatario.equals(other.destinatario))
			return false;
		if (nroExpediente == null) {
			if (other.nroExpediente != null)
				return false;
		} else if (!nroExpediente.equals(other.nroExpediente))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ExpedienteBeanDTO [nroExpediente=" + nroExpediente + ", asunto=" + asunto + ", destinatario="
				+ destinatario + "]";
	}
	
}
