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
@Table(name = "SICOES_TM_SUPER_REPRESENTANTE")
public class SupervisoraRepresentante extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_SUPER_REPRESENTANTE")
	@SequenceGenerator(name = "GEN_SICOES_SEQ_SUPER_REPRESENTANTE", sequenceName = "SICOES_SEQ_SUPER_REPRESENTANTE", allocationSize = 1)
	@Column(name = "ID_SUPER_REPRESENTANTE")
	private Long idSupervisoraRepresentante;
	
	
	@Column(name = "ID_SUPERVISORA")
	private Long idSupervisora;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_DOCUMENTO_LD")
	private ListadoDetalle tipoDocumento;

	@Column(name = "NU_DOCUMENTO")
	private String numeroDocumento;

	@Column(name = "NO_REPRESENTANTE")
	private String nombres;

	@Column(name = "AP_PATERNO")
	private String apellidoPaterno;

	@Column(name = "AP_MATERNO")
	private String apellidoMaterno;

	public Long getIdSupervisoraRepresentante() {
		return idSupervisoraRepresentante;
	}

	public void setIdSupervisoraRepresentante(Long idSupervisoraRepresentante) {
		this.idSupervisoraRepresentante = idSupervisoraRepresentante;
	}

	public ListadoDetalle getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(ListadoDetalle tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public Long getIdSupervisora() {
		return idSupervisora;
	}

	public void setIdSupervisora(Long idSupervisora) {
		this.idSupervisora = idSupervisora;
	}
	
	
}
