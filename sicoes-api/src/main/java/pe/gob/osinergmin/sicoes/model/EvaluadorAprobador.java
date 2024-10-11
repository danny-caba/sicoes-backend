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
@Table(name="SICOES_TX_EVALUADOR_APROBADOR")
public class EvaluadorAprobador extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SICOES_SEQ_EVALUADOR_APROBADOR")
	@SequenceGenerator(name="SICOES_SEQ_EVALUADOR_APROBADOR", sequenceName = "SICOES_SEQ_EVALUADOR_APROBADOR", allocationSize = 1)
	@Column(name = "ID_EVALUADOR_APROBADOR")	
	private Long idEvaluadorAprobador;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO")
	private Usuario usuario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_RESPONSABLE")
	private Usuario responsable;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_GRUPO")	
	private ListadoDetalle grupo;
	
	public Long getIdEvaluadorAprobador() {
		return idEvaluadorAprobador;
	}

	public void setIdEvaluadorAprobador(Long idEvaluadorAprobador) {
		this.idEvaluadorAprobador = idEvaluadorAprobador;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getResponsable() {
		return responsable;
	}

	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}

	public ListadoDetalle getGrupo() {
		return grupo;
	}

	public void setGrupo(ListadoDetalle grupo) {
		this.grupo = grupo;
	}
	
}
