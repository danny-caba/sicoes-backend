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


/**
 * The persistent class for the listado database table.
 * 
 */
@Entity
@Table(name="SICOES_TR_TOKEN")
public class Token extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_TOKEN")
	@SequenceGenerator(name="GEN_SICOES_SEQ_TOKEN", sequenceName = "SICOES_SEQ_TOKEN", allocationSize = 1)
	@Column(name = "ID_TOKEN")	
	private Long idToken;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_LD")	
	private ListadoDetalle tipo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_LD")	
	private ListadoDetalle estado;

	@Column(name="ID_USUARIO")
	private Long idUsuario;
	
	@Column(name="CO_TOKEN")
	private String codigo;

	public Long getIdToken() {
		return idToken;
	}

	public void setIdToken(Long idToken) {
		this.idToken = idToken;
	}

	public ListadoDetalle getTipo() {
		return tipo;
	}

	public void setTipo(ListadoDetalle tipo) {
		this.tipo = tipo;
	}

	public ListadoDetalle getEstado() {
		return estado;
	}

	public void setEstado(ListadoDetalle estado) {
		this.estado = estado;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
}