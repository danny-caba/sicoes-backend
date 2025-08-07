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
@Table(name="SICOES_TX_PERFIL_APROBADOR")
public class PerfilAprobador extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SICOES_SEQ_PERFIL_APROBADOR")
	@SequenceGenerator(name="SICOES_SEQ_PERFIL_APROBADOR", sequenceName = "SICOES_SEQ_PERFIL_APROBADOR", allocationSize = 1)
	@Column(name = "ID_PERFIL_APROBADOR")	
	private Long idPerfilAprobador;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERFIL")	
	private ListadoDetalle perfil;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_APROBADOR_G1")
	private Usuario aprobadorG1;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_APROBADOR_G2")
	private Usuario aprobadorG2;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_APROBADOR_G3")
	private Usuario aprobadorG3;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_EVALUADOR")
	private Usuario evaluador;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_EVALUADOR_2")
	private Usuario evaluador2;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_EVALUADOR_3")
	private Usuario evaluador3;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_EVALUADOR_4")
	private Usuario evaluador4;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_EVALUADOR_5")
	private Usuario evaluador5;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_EVALUADOR_6")
	private Usuario evaluador6;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_EVALUADOR_7")
	private Usuario evaluador7;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_EVALUADOR_8")
	private Usuario evaluador8;

	public Long getIdPerfilAprobador() {
		return idPerfilAprobador;
	}

	public void setIdPerfilAprobador(Long idPerfilAprobador) {
		this.idPerfilAprobador = idPerfilAprobador;
	}

	public ListadoDetalle getPerfil() {
		return perfil;
	}

	public void setPerfil(ListadoDetalle perfil) {
		this.perfil = perfil;
	}

	public Usuario getAprobadorG1() {
		return aprobadorG1;
	}

	public void setAprobadorG1(Usuario aprobadorG1) {
		this.aprobadorG1 = aprobadorG1;
	}

	public Usuario getAprobadorG2() {
		return aprobadorG2;
	}

	public void setAprobadorG2(Usuario aprobadorG2) {
		this.aprobadorG2 = aprobadorG2;
	}

	public Usuario getAprobadorG3() {
		return aprobadorG3;
	}

	public void setAprobadorG3(Usuario aprobadorG3) {
		this.aprobadorG3 = aprobadorG3;
	}

	public Usuario getEvaluador() {
		return evaluador;
	}

	public void setEvaluador(Usuario evaluador) {
		this.evaluador = evaluador;
	}

	public Usuario getEvaluador2() {
		return evaluador2;
	}

	public void setEvaluador2(Usuario evaluador2) {
		this.evaluador2 = evaluador2;
	}

	public Usuario getEvaluador3() {
		return evaluador3;
	}

	public void setEvaluador3(Usuario evaluador3) {
		this.evaluador3 = evaluador3;
	}

	public Usuario getEvaluador4() {
		return evaluador4;
	}

	public void setEvaluador4(Usuario evaluador4) {
		this.evaluador4 = evaluador4;
	}

	public Usuario getEvaluador5() {
		return evaluador5;
	}

	public void setEvaluador5(Usuario evaluador5) {
		this.evaluador5 = evaluador5;
	}

	public Usuario getEvaluador6() {
		return evaluador6;
	}

	public void setEvaluador6(Usuario evaluador6) {
		this.evaluador6 = evaluador6;
	}

	public Usuario getEvaluador7() {
		return evaluador7;
	}

	public void setEvaluador7(Usuario evaluador7) {
		this.evaluador7 = evaluador7;
	}

	public Usuario getEvaluador8() {
		return evaluador8;
	}

	public void setEvaluador8(Usuario evaluador8) {
		this.evaluador8 = evaluador8;
	}

	@Override
	public String toString() {
		return "PerfilAprobador [idPerfilAprobador=" + idPerfilAprobador + ", perfil=" + perfil + ", aprobadorG1="
				+ aprobadorG1 + ", aprobadorG2=" + aprobadorG2 + ", evaluador=" + evaluador + ", evaluador2="
				+ evaluador2 + ", evaluador3=" + evaluador3 + ", evaluador4=" + evaluador4 + ", evaluador5="
				+ evaluador5 + ", evaluador6=" + evaluador6 + ", evaluador7=" + evaluador7 + ", evaluador8="
				+ evaluador8 + "]";
	}

	
}
