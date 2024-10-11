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
@Table(name="SICOES_TR_PROCESO_ITEM")
public class ProcesoItem extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_PROCESO_ITEM")
	@SequenceGenerator(name="GEN_SICOES_SEQ_PROCESO_ITEM", sequenceName = "SICOES_SEQ_PROCESO_ITEM", allocationSize = 1)
	@Column(name = "ID_PROCESO_ITEM")	
	private Long idProcesoItem;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROCESO")
	private Proceso proceso;
	
	@Column(name="NU_ITEM")
	private Long numeroItem;
	
	@Column(name="DE_ITEM")
	private String descripcionItem;
	
	@Column(name="NU_IMPORTE")
	private Double montoReferencial;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_LD")	
	private ListadoDetalle estado;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DIVISA_LD")	
	private ListadoDetalle divisa;
	
	@Column(name="CO_UUID")
	private String procesoItemUuid;
	
	@Column(name="NU_TIPO_CAMBIO")	
	private Double montoTipoCambio;	

	@Column(name="NU_IMPORTE_REF")
	private Double montoReferencialSoles;
	
	@Column(name="NU_FACTURACION_MIN")	
	private Double facturacionMinima;
	
	@Column(name="DE_DESCARGA")	
	private String decripcionDescarga;
	
	@Column(name="DE_RUTA_DESCARGA")	
	private String rutaDescarga;

	@Transient
	private ProcesoEtapa etapa;
	
	@Transient
	private Propuesta propuesta;
	
	@Transient
	private Long nroProfesionales;
	
	@Transient
	private List<ProcesoItemPerfil> listProcesoItemPerfil;

	public Long getIdProcesoItem() {
		return idProcesoItem;
	}

	public Proceso getProceso() {
		return proceso;
	}

	public Long getNumeroItem() {
		return numeroItem;
	}

	public String getDescripcionItem() {
		return descripcionItem;
	}

	public Double getMontoReferencial() {
		return montoReferencial;
	}

	public ListadoDetalle getEstado() {
		return estado;
	}

	public ListadoDetalle getDivisa() {
		return divisa;
	}

	public String getProcesoItemUuid() {
		return procesoItemUuid;
	}

	public Double getMontoTipoCambio() {
		return montoTipoCambio;
	}

	public Double getMontoReferencialSoles() {
		return montoReferencialSoles;
	}

	public Double getFacturacionMinima() {
		return facturacionMinima;
	}

	public String getDecripcionDescarga() {
		return decripcionDescarga;
	}

	public String getRutaDescarga() {
		return rutaDescarga;
	}

	public ProcesoEtapa getEtapa() {
		return etapa;
	}

	public Propuesta getPropuesta() {
		return propuesta;
	}

	public Long getNroProfesionales() {
		return nroProfesionales;
	}

	public List<ProcesoItemPerfil> getListProcesoItemPerfil() {
		return listProcesoItemPerfil;
	}

	public void setIdProcesoItem(Long idProcesoItem) {
		this.idProcesoItem = idProcesoItem;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public void setNumeroItem(Long numeroItem) {
		this.numeroItem = numeroItem;
	}

	public void setDescripcionItem(String descripcionItem) {
		this.descripcionItem = descripcionItem;
	}

	public void setMontoReferencial(Double montoReferencial) {
		this.montoReferencial = montoReferencial;
	}

	public void setEstado(ListadoDetalle estado) {
		this.estado = estado;
	}

	public void setDivisa(ListadoDetalle divisa) {
		this.divisa = divisa;
	}

	public void setProcesoItemUuid(String procesoItemUuid) {
		this.procesoItemUuid = procesoItemUuid;
	}

	public void setMontoTipoCambio(Double montoTipoCambio) {
		this.montoTipoCambio = montoTipoCambio;
	}

	public void setMontoReferencialSoles(Double montoReferencialSoles) {
		this.montoReferencialSoles = montoReferencialSoles;
	}

	public void setFacturacionMinima(Double facturacionMinima) {
		this.facturacionMinima = facturacionMinima;
	}

	public void setDecripcionDescarga(String decripcionDescarga) {
		this.decripcionDescarga = decripcionDescarga;
	}

	public void setRutaDescarga(String rutaDescarga) {
		this.rutaDescarga = rutaDescarga;
	}

	public void setEtapa(ProcesoEtapa etapa) {
		this.etapa = etapa;
	}

	public void setPropuesta(Propuesta propuesta) {
		this.propuesta = propuesta;
	}

	public void setNroProfesionales(Long nroProfesionales) {
		this.nroProfesionales = nroProfesionales;
	}

	public void setListProcesoItemPerfil(List<ProcesoItemPerfil> listProcesoItemPerfil) {
		this.listProcesoItemPerfil = listProcesoItemPerfil;
	}

	@Override
	public String toString() {
		return "ProcesoItem [idProcesoItem=" + idProcesoItem + ", proceso=" + proceso + ", numeroItem=" + numeroItem
				+ ", descripcionItem=" + descripcionItem + ", montoReferencial=" + montoReferencial + ", estado="
				+ estado + ", divisa=" + divisa + ", procesoItemUuid=" + procesoItemUuid + ", montoTipoCambio="
				+ montoTipoCambio + ", montoReferencialSoles=" + montoReferencialSoles + ", facturacionMinima="
				+ facturacionMinima + ", decripcionDescarga=" + decripcionDescarga + ", rutaDescarga=" + rutaDescarga
				+ ", etapa=" + etapa + ", propuesta=" + propuesta + ", nroProfesionales=" + nroProfesionales
				+ ", listProcesoItemPerfil=" + listProcesoItemPerfil + "]";
	}

	

}