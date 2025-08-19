package pe.gob.osinergmin.sicoes.util.bean.sne;

import java.io.Serializable;

public class VacacionesBeanDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String codigoAplicativo;
	private Long codigoConsulta;
	
	public String getCodigoAplicativo() {
		return codigoAplicativo;
	}
	public void setCodigoAplicativo(String codigoAplicativo) {
		this.codigoAplicativo = codigoAplicativo;
	}
	public Long getCodigoConsulta() {
		return codigoConsulta;
	}
	public void setCodigoConsulta(Long codigoConsulta) {
		this.codigoConsulta = codigoConsulta;
	}
	
	@Override
	public String toString() {
		return "VacacionesBeanDTO [codigoAplicativo=" + codigoAplicativo + ", codigoConsulta=" + codigoConsulta + "]";
	}
}
