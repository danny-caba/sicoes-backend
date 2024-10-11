package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfesionDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long idProfesion;
	private String deProfesion;
	
	public Long getIdProfesion() {
		return idProfesion;
	}
	public void setIdProfesion(Long idProfesion) {
		this.idProfesion = idProfesion;
	}
	public String getDeProfesion() {
		return deProfesion;
	}
	public void setDeProfesion(String deProfesion) {
		this.deProfesion = deProfesion;
	}

}
