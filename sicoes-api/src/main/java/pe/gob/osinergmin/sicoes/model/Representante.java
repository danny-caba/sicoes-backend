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
@Table(name="SICOES_TR_REPRESENTANTE")
public class Representante extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_REPRESENTANTE")
	@SequenceGenerator(name="GEN_SICOES_SEQ_REPRESENTANTE", sequenceName = "SICOES_SEQ_REPRESENTANTE", allocationSize = 1)
	@Column(name = "ID_REPRESENTANTE")	
	private Long idRepresentante;

	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_DOCUMENTO_LD")
	private ListadoDetalle tipoDocumento;
	
	@Column(name="NU_DOCUMENTO")	
	private String numeroDocumento;
	
	@Column(name="NO_REPRESENTANTE")	
	private String nombres;
	
	@Column(name="AP_PATERNO")	
	private String apellidoPaterno;
	
	@Column(name="AP_MATERNO")	
	private String apellidoMaterno;

	public Long getIdRepresentante() {
		return idRepresentante;
	}

	public void setIdRepresentante(Long idRepresentante) {
		this.idRepresentante = idRepresentante;
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

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public boolean isPesonaJuridica() {
		return (numeroDocumento!=null&&numeroDocumento.length()==11&&numeroDocumento.startsWith("20"));
	}

	public String getNombreRepresentante() {
		return nombres+" "+apellidoPaterno+" "+apellidoMaterno;
	}
	
}