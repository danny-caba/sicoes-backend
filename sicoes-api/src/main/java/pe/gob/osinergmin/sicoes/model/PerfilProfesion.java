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
@Table(name="SICOES_TX_PERFIL_PROFESION")
public class PerfilProfesion extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SICOES_SEQ_PERFIL_PROFESION")
	@SequenceGenerator(name="SICOES_SEQ_PERFIL_PROFESION", sequenceName = "SICOES_SEQ_PERFIL_PROFESION", allocationSize = 1)
	@Column(name = "ID_PERFIL_PROFESION")
	private Long idPerfilProfesion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERFIL	")
	private ListadoDetalle perfil;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROFESION")
	private Profesion profesion;

	public Long getIdPerfilProfesion() {
		return idPerfilProfesion;
	}

	public void setIdPerfilProfesion(Long idPerfilProfesion) {
		this.idPerfilProfesion = idPerfilProfesion;
	}

	public ListadoDetalle getPerfil() {
		return perfil;
	}

	public void setPerfil(ListadoDetalle perfil) {
		this.perfil = perfil;
	}

	public Profesion getProfesion() {
		return profesion;
	}

	public void setProfesion(Profesion profesion) {
		this.profesion = profesion;
	}

}
