package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SICOES_TX_PERFIL_DIVISION")
public class PerfilDivision extends BaseModel implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SICOES_SEQ_PERFIL_DIVISION")
	@SequenceGenerator(name="SICOES_SEQ_PERFIL_DIVISION", sequenceName = "SICOES_SEQ_PERFIL_DIVISION", allocationSize = 1)
	@Column(name = "ID_PERFIL_DIVISION")	
	private Long idPerfilDivision;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERFIL")	
	private ListadoDetalle perfil;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DIVISION")	
	private Division division;

	public Long getIdPerfilDivision() {
		return idPerfilDivision;
	}

	public void setIdPerfilDivision(Long idPerfilDivision) {
		this.idPerfilDivision = idPerfilDivision;
	}

	public ListadoDetalle getPerfil() {
		return perfil;
	}

	public void setPerfil(ListadoDetalle perfil) {
		this.perfil = perfil;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

}
