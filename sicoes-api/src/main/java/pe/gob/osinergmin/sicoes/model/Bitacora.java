package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;
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

@Entity
@Table(name="SICOES_TR_BITACORA")
public class Bitacora extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_BITACORA")
	@SequenceGenerator(name="GEN_SICOES_SEQ_BITACORA", sequenceName = "SICOES_SEQ_BITACORA", allocationSize = 1)
	@Column(name = "ID_BITACORA")	
	private Long idBitacora;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO")	
	private Usuario usuario;
	
	@Column(name = "FECHA_HORA")	
	private Date fechaHora;
	
	@Column(name = "DESCRIPCION")	
	private String descripcion;

	public Long getIdBitacora() {
		return idBitacora;
	}

	public void setIdBitacora(Long idBitacora) {
		this.idBitacora = idBitacora;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Bitacora [idBitacora=" + idBitacora + ", usuario=" + usuario + ", fechaHora=" + fechaHora
				+ ", descripcion=" + descripcion + "]";
	}
	
	

}
