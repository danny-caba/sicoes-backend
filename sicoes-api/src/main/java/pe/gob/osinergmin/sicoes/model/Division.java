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
@Table(name="SICOES_TM_DIVISION")
public class Division extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SICOES_SEQ_DIVISION")
	@SequenceGenerator(name="SICOES_SEQ_DIVISION", sequenceName = "SICOES_SEQ_DIVISION", allocationSize = 1)
	@Column(name = "ID_DIVISION")	
	private Long idDivision;
	
	@Column(name="DE_DIVISION")	
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
