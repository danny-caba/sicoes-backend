package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SICOES_TM_PROFESION")
public class Profesion extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SICOES_SEQ_PROFESION")
	@SequenceGenerator(name="SICOES_SEQ_PROFESION", sequenceName = "SICOES_SEQ_PROFESION", allocationSize = 1)
	@Column(name = "ID_PROFESION")	
	private Long idProfesion;
	
	@Column(name="DE_PROFESION")	
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
