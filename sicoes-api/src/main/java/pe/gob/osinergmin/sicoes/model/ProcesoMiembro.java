package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * The persistent class for the listado database table.
 * 
 */
@Entity
@Table(name="SICOES_TR_PROCESO_MIEMBRO")
public class ProcesoMiembro extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_PROCESO_MIEMBRO")
	@SequenceGenerator(name="GEN_SICOES_SEQ_PROCESO_MIEMBRO", sequenceName = "SICOES_SEQ_PROCESO_MIEMBRO", allocationSize = 1)
	@Column(name = "ID_PROCESO_MIEMBRO")	
	private Long idProcesoMiembro;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CARGO_LD")	
	private ListadoDetalle cargo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_LD")	
	private ListadoDetalle estado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROCESO")
	private Proceso proceso;
	
	@Column(name="CO_USUARIO")
	private Long codigoUsuario;

	@Column(name="NO_USUARIO")
	private String nombreUsuario;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	@Column(name="FE_REGISTRO")
	private Date fechaRegistro;

	public Long getIdProcesoMiembro() {
		return idProcesoMiembro;
	}

	public ListadoDetalle getCargo() {
		return cargo;
	}

	public ListadoDetalle getEstado() {
		return estado;
	}

	public Proceso getProceso() {
		return proceso;
	}

	public Long getCodigoUsuario() {
		return codigoUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setIdProcesoMiembro(Long idProcesoMiembro) {
		this.idProcesoMiembro = idProcesoMiembro;
	}

	public void setCargo(ListadoDetalle cargo) {
		this.cargo = cargo;
	}

	public void setEstado(ListadoDetalle estado) {
		this.estado = estado;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public void setCodigoUsuario(Long codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@Override
	public String toString() {
		return "ProcesoMiembro [idProcesoMiembro=" + idProcesoMiembro + ", cargo=" + cargo + ", estado=" + estado
				+ ", proceso=" + proceso + ", codigoUsuario=" + codigoUsuario + ", nombreUsuario=" + nombreUsuario
				+ ", fechaRegistro=" + fechaRegistro + "]";
	}
}