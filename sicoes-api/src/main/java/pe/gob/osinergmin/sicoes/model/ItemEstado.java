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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name="SICOES_TR_ITEM_ESTADO")
public class ItemEstado extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_ITEM_ESTADO")
	@SequenceGenerator(name="GEN_SICOES_SEQ_ITEM_ESTADO", sequenceName = "SICOES_SEQ_ITEM_ESTADO", allocationSize = 1)
	@Column(name = "ID_ITEM_ESTADO")	
	private Long idItemEstado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROCESO_ITEM")
	private ProcesoItem procesoItem;
	
	@Column(name = "DE_ITEM")	
	private String descripcion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_LD")
	private ListadoDetalle estado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_ANTERIOR_LD")
	private ListadoDetalle estadoAnterior;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO")
	private Usuario usuario;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	@Column(name="FE_REGISTRO")
	private Date fechaRegistro;

	public Long getIdItemEstado() {
		return idItemEstado;
	}

	public ProcesoItem getProcesoItem() {
		return procesoItem;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public ListadoDetalle getEstado() {
		return estado;
	}

	public ListadoDetalle getEstadoAnterior() {
		return estadoAnterior;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setIdItemEstado(Long idItemEstado) {
		this.idItemEstado = idItemEstado;
	}

	public void setProcesoItem(ProcesoItem procesoItem) {
		this.procesoItem = procesoItem;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setEstado(ListadoDetalle estado) {
		this.estado = estado;
	}

	public void setEstadoAnterior(ListadoDetalle estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@Override
	public String toString() {
		return "ItemEstado [idItemEstado=" + idItemEstado + ", procesoItem=" + procesoItem + ", descripcion="
				+ descripcion + ", estado=" + estado + ", estadoAnterior=" + estadoAnterior + ", usuario=" + usuario
				+ ", fechaRegistro=" + fechaRegistro + "]";
	}

	
}