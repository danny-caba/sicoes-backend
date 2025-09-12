package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;

public class DivisionDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long idDivision;
	private String deDivision;

	public Long getIdDivision() {
		return idDivision;
	}

	public void setIdDivision(Long idDivision) {
		this.idDivision = idDivision;
	}

	public String getDeDivision() {
		return deDivision;
	}

	public void setDeDivision(String deDivision) {
		this.deDivision = deDivision;
	}

}
