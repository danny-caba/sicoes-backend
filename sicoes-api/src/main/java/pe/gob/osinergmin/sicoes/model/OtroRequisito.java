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
import javax.persistence.Transient;

import org.eclipse.persistence.annotations.Convert;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The persistent class for the listado database table.
 * 
 */
@Entity
@Table(name="SICOES_TR_OTRO_REQUISITO")
public class OtroRequisito extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_OTRO_REQUISITO")
	@SequenceGenerator(name="GEN_SICOES_SEQ_OTRO_REQUISITO", sequenceName = "SICOES_SEQ_OTRO_REQUISITO", allocationSize = 1)
	@Column(name = "ID_OTRO_REQUISITO")	
	private Long idOtroRequisito;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_LD")	
	private ListadoDetalle tipo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_REQUISITO_LD")	
	private ListadoDetalle tipoRequisito;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SECTOR_LD")	
	private ListadoDetalle sector;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SUBSECTOR_LD")	
	private ListadoDetalle subsector;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ACTIVIDAD_LD")	
	private ListadoDetalle actividad;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ACTIVIDAD_AREAS_LD")	
	private ListadoDetalle actividadArea;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SUBCATEGORIA_LD")	
	private ListadoDetalle subCategoria;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERFIL_LD")	
	private ListadoDetalle perfil;
	
	@Column(name="ID_OTRO_REQUISITO_PADRE")
	private Long idOtroRequisitoPadre;
	
	@Column(name="FL_ELECTRONICO")	
	private String flagElectronico;
	
	@Column(name="FL_FIRMA_DIGITAL")	
	private String flagFirmaDigital;
	
	@Column(name="FL_ACTIVO")	
	private String flagActivo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_EVALUACION_LD")	
	private ListadoDetalle evaluacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_UNIDAD_LD")	
	private ListadoDetalle unidad;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_RESULTADO_LD")	
	private ListadoDetalle resultado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SOLICITUD")
	private Solicitud solicitud;
	
	@Column(name="DE_OBSERVACION")
	private String observacion;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_EXPEDICION")
	private Date fechaExpedicion;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	@Column(name="FE_FINALIZACION")
	private Date fechaFinalizador;
	
	@Column(name="FL_SIGED")	
	private Long flagSiged;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_FINALIZADO_LD")
	private ListadoDetalle finalizado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO_FIN")
	private Usuario usuario;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	@Column(name="FE_ASIGNACION")
	private Date fechaAsignacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_REVERSION")
	private ListadoDetalle estadoReversion;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	@Column(name="FE_ACTUALIZACION")
	private Date fecActualizacion;
	
	@Column(name = "MO_FACTURADO_SECTOR")
	private Double montoFacturadoSector;
	
	@Column(name = "REQUISITO_PRINCIPAL")
    private Integer requisitoPrincipal;  // Valor por defecto en Java

	// Getter y setter manuales para convertir a booleano
    public boolean isNuevaColumna() {
        return requisitoPrincipal != null && requisitoPrincipal == 1;
    }

    public void setNuevaColumna(boolean nuevaColumna) {
        this.requisitoPrincipal = nuevaColumna ? 1 : 0;
    }
    
    public OtroRequisito() {
        this.requisitoPrincipal = 1; // true por defecto
    }
	
	@Transient
	private Archivo archivo;
	@Transient
	private Long nroFolio;
	@Transient
	private Long peso;
	@Transient
	private Double montoFacturado;
	@Transient
	private String evaluadorFinalizador;
	

	public ListadoDetalle getActividadArea() {
		return actividadArea;
	}

	public void setActividadArea(ListadoDetalle actividadArea) {
		this.actividadArea = actividadArea;
	}

	public Long getIdOtroRequisito() {
		return idOtroRequisito;
	}

	public ListadoDetalle getTipo() {
		return tipo;
	}

	public ListadoDetalle getTipoRequisito() {
		return tipoRequisito;
	}

	public ListadoDetalle getSector() {
		return sector;
	}

	public ListadoDetalle getSubsector() {
		return subsector;
	}

	public ListadoDetalle getActividad() {
		return actividad;
	}

	public ListadoDetalle getSubCategoria() {
		return subCategoria;
	}

	public ListadoDetalle getPerfil() {
		return perfil;
	}

	public Long getIdOtroRequisitoPadre() {
		return idOtroRequisitoPadre;
	}

	public String getFlagElectronico() {
		return flagElectronico;
	}

	public String getFlagFirmaDigital() {
		return flagFirmaDigital;
	}

	public String getFlagActivo() {
		return flagActivo;
	}

	public ListadoDetalle getEvaluacion() {
		return evaluacion;
	}

	public ListadoDetalle getUnidad() {
		return unidad;
	}

	public ListadoDetalle getResultado() {
		return resultado;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public String getObservacion() {
		return observacion;
	}

	public Date getFechaExpedicion() {
		return fechaExpedicion;
	}

	public Long getFlagSiged() {
		return flagSiged;
	}

	public ListadoDetalle getFinalizado() {
		return finalizado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Archivo getArchivo() {
		return archivo;
	}

	public Long getNroFolio() {
		return nroFolio;
	}

	public Long getPeso() {
		return peso;
	}

	public String getEvaluadorFinalizador() {
		return evaluadorFinalizador;
	}

	public void setIdOtroRequisito(Long idOtroRequisito) {
		this.idOtroRequisito = idOtroRequisito;
	}

	public void setTipo(ListadoDetalle tipo) {
		this.tipo = tipo;
	}

	public void setTipoRequisito(ListadoDetalle tipoRequisito) {
		this.tipoRequisito = tipoRequisito;
	}

	public void setSector(ListadoDetalle sector) {
		this.sector = sector;
	}

	public void setSubsector(ListadoDetalle subsector) {
		this.subsector = subsector;
	}

	public void setActividad(ListadoDetalle actividad) {
		this.actividad = actividad;
	}

	public void setSubCategoria(ListadoDetalle subCategoria) {
		this.subCategoria = subCategoria;
	}

	public void setPerfil(ListadoDetalle perfil) {
		this.perfil = perfil;
	}

	public void setIdOtroRequisitoPadre(Long idOtroRequisitoPadre) {
		this.idOtroRequisitoPadre = idOtroRequisitoPadre;
	}

	public void setFlagElectronico(String flagElectronico) {
		this.flagElectronico = flagElectronico;
	}

	public void setFlagFirmaDigital(String flagFirmaDigital) {
		this.flagFirmaDigital = flagFirmaDigital;
	}

	public void setFlagActivo(String flagActivo) {
		this.flagActivo = flagActivo;
	}

	public void setEvaluacion(ListadoDetalle evaluacion) {
		this.evaluacion = evaluacion;
	}

	public void setUnidad(ListadoDetalle unidad) {
		this.unidad = unidad;
	}

	public void setResultado(ListadoDetalle resultado) {
		this.resultado = resultado;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public void setFechaExpedicion(Date fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}

	public void setFlagSiged(Long flagSiged) {
		this.flagSiged = flagSiged;
	}

	public void setFinalizado(ListadoDetalle finalizado) {
		this.finalizado = finalizado;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}

	public void setNroFolio(Long nroFolio) {
		this.nroFolio = nroFolio;
	}

	public void setPeso(Long peso) {
		this.peso = peso;
	}

	public void setEvaluadorFinalizador(String evaluadorFinalizador) {
		this.evaluadorFinalizador = evaluadorFinalizador;
	}

	public Double getMontoFacturado() {
		return montoFacturado;
	}

	public void setMontoFacturado(Double montoFacturado) {
		this.montoFacturado = montoFacturado;
	}

	public Date getFechaFinalizador() {
		return fechaFinalizador;
	}

	public void setFechaFinalizador(Date fechaFinalizador) {
		this.fechaFinalizador = fechaFinalizador;
	}
	
	public Date getFecActualizacion() {
		return fecActualizacion;
	}

	public void setFecActualizacion(Date fecActualizacion) {
		this.fecActualizacion = fecActualizacion;
	}

	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public ListadoDetalle getEstadoReversion() {
		return estadoReversion;
	}

	public void setEstadoReversion(ListadoDetalle estadoReversion) {
		this.estadoReversion = estadoReversion;
	}

	public Double getMontoFacturadoSector() {
		return montoFacturadoSector;
	}

	public void setMontoFacturadoSector(Double montoFacturadoSector) {
		this.montoFacturadoSector = montoFacturadoSector;
	}	
	
		
}