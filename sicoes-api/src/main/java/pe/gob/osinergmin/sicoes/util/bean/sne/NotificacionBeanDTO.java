package pe.gob.osinergmin.sicoes.util.bean.sne;

import java.io.Serializable;

public class NotificacionBeanDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String appInvoker;
	private Long idClienteSiged;
	private Long idUnidadOperativa;
	private String expedienteSigedNotificacion;
	private Long idDocumentoSigedDocumentoNotificacion;
	
	public String getAppInvoker() {
		return appInvoker;
	}
	public void setAppInvoker(String appInvoker) {
		this.appInvoker = appInvoker;
	}
	public Long getIdClienteSiged() {
		return idClienteSiged;
	}
	public void setIdClienteSiged(Long idClienteSiged) {
		this.idClienteSiged = idClienteSiged;
	}
	public Long getIdUnidadOperativa() {
		return idUnidadOperativa;
	}
	public void setIdUnidadOperativa(Long idUnidadOperativa) {
		this.idUnidadOperativa = idUnidadOperativa;
	}
	public String getExpedienteSigedNotificacion() {
		return expedienteSigedNotificacion;
	}
	public void setExpedienteSigedNotificacion(String expedienteSigedNotificacion) {
		this.expedienteSigedNotificacion = expedienteSigedNotificacion;
	}
	public Long getIdDocumentoSigedDocumentoNotificacion() {
		return idDocumentoSigedDocumentoNotificacion;
	}
	public void setIdDocumentoSigedDocumentoNotificacion(Long idDocumentoSigedDocumentoNotificacion) {
		this.idDocumentoSigedDocumentoNotificacion = idDocumentoSigedDocumentoNotificacion;
	}
	@Override
	public String toString() {
		return "NotificacionBeanDTO [appInvoker=" + appInvoker + ", idClienteSiged=" + idClienteSiged
				+ ", idUnidadOperativa=" + idUnidadOperativa + ", expedienteSigedNotificacion="
				+ expedienteSigedNotificacion + ", idDocumentoSigedDocumentoNotificacion="
				+ idDocumentoSigedDocumentoNotificacion + "]";
	}
	
	
}