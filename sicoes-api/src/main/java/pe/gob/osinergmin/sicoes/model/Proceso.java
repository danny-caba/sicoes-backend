package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
@Table(name="SICOES_TR_PROCESO")
public class Proceso extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_PROCESO")
	@SequenceGenerator(name="GEN_SICOES_SEQ_PROCESO", sequenceName = "SICOES_SEQ_PROCESO", allocationSize = 1)
	@Column(name = "ID_PROCESO")	
	private Long idProceso;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SECTOR_LD")	
	private ListadoDetalle sector;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SUBSECTOR_LD")	
	private ListadoDetalle subsector;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_LD")	
	private ListadoDetalle estado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_FACTURACION_LD")	
	private ListadoDetalle tipoFacturacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO")	
	private Usuario usuario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO_CREADOR")	
	private Usuario usuarioCreador;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_PUBLICACION")
	private Date fechaPublicacion;
	
	@Column(name="NU_PROCESO")
	private String numeroProceso;
	
	@Column(name="NO_PROCESO")
	private String nombreProceso;
	
	@Column(name="NU_EXPEDIENTE")
	private String numeroExpediente;
	
	@Column(name="CO_AREA")
	private String codigoArea;
	
	@Column(name="NO_AREA")
	private String nombreArea;

	@Column(name="CO_UUID")
	private String procesoUuid;

	@Transient
	private boolean editar;
	
	@Transient
	private boolean verPostulante;
	
	@Transient
	private boolean datosGenerales;
	
	@Transient
	private boolean etapa;
	
	@Transient
	private boolean miembros;
	
	@Transient
	private boolean items;
	
	@Transient
	private boolean informacion;
	
	@Transient
	private List<ProcesoEtapa> etapas;

	@Transient
	private Long idPace;	

	public Long getIdProceso() {
		return idProceso;
	}

	public ListadoDetalle getSector() {
		return sector;
	}

	public ListadoDetalle getSubsector() {
		return subsector;
	}

	public ListadoDetalle getEstado() {
		return estado;
	}

	public ListadoDetalle getTipoFacturacion() {
		return tipoFacturacion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Usuario getUsuarioCreador() {
		return usuarioCreador;
	}

	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	public String getNumeroProceso() {
		return numeroProceso;
	}

	public String getNombreProceso() {
		return nombreProceso;
	}

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public String getCodigoArea() {
		return codigoArea;
	}

	public String getNombreArea() {
		return nombreArea;
	}

	public String getProcesoUuid() {
		return procesoUuid;
	}

	public boolean isEditar() {
		return editar;
	}

	public boolean isVerPostulante() {
		return verPostulante;
	}

	public boolean isDatosGenerales() {
		return datosGenerales;
	}

	public boolean isEtapa() {
		return etapa;
	}

	public boolean isMiembros() {
		return miembros;
	}

	public boolean isItems() {
		return items;
	}
	
	public boolean isInformacion() {
		return informacion;
	}

	public List<ProcesoEtapa> getEtapas() {
		return etapas;
	}

	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}

	public void setSector(ListadoDetalle sector) {
		this.sector = sector;
	}

	public void setSubsector(ListadoDetalle subsector) {
		this.subsector = subsector;
	}

	public void setEstado(ListadoDetalle estado) {
		this.estado = estado;
	}

	public void setTipoFacturacion(ListadoDetalle tipoFacturacion) {
		this.tipoFacturacion = tipoFacturacion;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setUsuarioCreador(Usuario usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public void setNumeroProceso(String numeroProceso) {
		this.numeroProceso = numeroProceso;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public void setCodigoArea(String codigoArea) {
		this.codigoArea = codigoArea;
	}

	public void setNombreArea(String nombreArea) {
		this.nombreArea = nombreArea;
	}

	public void setProcesoUuid(String procesoUuid) {
		this.procesoUuid = procesoUuid;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}

	public void setVerPostulante(boolean verPostulante) {
		this.verPostulante = verPostulante;
	}

	public void setDatosGenerales(boolean datosGenerales) {
		this.datosGenerales = datosGenerales;
	}

	public void setEtapa(boolean etapa) {
		this.etapa = etapa;
	}

	public void setMiembros(boolean miembros) {
		this.miembros = miembros;
	}

	public void setInformacion(boolean informacion) {
		this.informacion = informacion;
	}

	public void setItems(boolean items) {
		this.items = items;
	}

	public void setEtapas(List<ProcesoEtapa> etapas) {
		this.etapas = etapas;
	}
	
	public Long getIdPace() {
		return idPace;
	}

	public void setIdPace(Long idPace) {
		this.idPace = idPace;
	}
	
}