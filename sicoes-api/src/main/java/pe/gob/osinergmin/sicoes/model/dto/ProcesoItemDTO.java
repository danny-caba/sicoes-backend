package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcesoItemDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idProcesoItem;
	private ProcesoDTO proceso;
	private Long numeroItem;
	private String descripcionItem;
	private Double montoReferencial;
	private ListadoDetalleDTO estado;
	private ListadoDetalleDTO divisa;
	private String procesoItemUuid;
	private Double montoTipoCambio;
	private Double montoReferencialSoles;
	private Double facturacionMinima;
	private String decripcionDescarga;
	private String rutaDescarga;

	public Long getIdProcesoItem() {
		return idProcesoItem;
	}

	public void setIdProcesoItem(Long idProcesoItem) {
		this.idProcesoItem = idProcesoItem;
	}

	public ProcesoDTO getProceso() {
		return proceso;
	}

	public void setProceso(ProcesoDTO proceso) {
		this.proceso = proceso;
	}

	public Long getNumeroItem() {
		return numeroItem;
	}

	public void setNumeroItem(Long numeroItem) {
		this.numeroItem = numeroItem;
	}

	public String getDescripcionItem() {
		return descripcionItem;
	}

	public void setDescripcionItem(String descripcionItem) {
		this.descripcionItem = descripcionItem;
	}

	public Double getMontoReferencial() {
		return montoReferencial;
	}

	public void setMontoReferencial(Double montoReferencial) {
		this.montoReferencial = montoReferencial;
	}

	public ListadoDetalleDTO getEstado() {
		return estado;
	}

	public void setEstado(ListadoDetalleDTO estado) {
		this.estado = estado;
	}

	public ListadoDetalleDTO getDivisa() {
		return divisa;
	}

	public void setDivisa(ListadoDetalleDTO divisa) {
		this.divisa = divisa;
	}

	public String getProcesoItemUuid() {
		return procesoItemUuid;
	}

	public void setProcesoItemUuid(String procesoItemUuid) {
		this.procesoItemUuid = procesoItemUuid;
	}

	public Double getMontoTipoCambio() {
		return montoTipoCambio;
	}

	public void setMontoTipoCambio(Double montoTipoCambio) {
		this.montoTipoCambio = montoTipoCambio;
	}

	public Double getMontoReferencialSoles() {
		return montoReferencialSoles;
	}

	public void setMontoReferencialSoles(Double montoReferencialSoles) {
		this.montoReferencialSoles = montoReferencialSoles;
	}

	public Double getFacturacionMinima() {
		return facturacionMinima;
	}

	public void setFacturacionMinima(Double facturacionMinima) {
		this.facturacionMinima = facturacionMinima;
	}

	public String getDecripcionDescarga() {
		return decripcionDescarga;
	}

	public void setDecripcionDescarga(String decripcionDescarga) {
		this.decripcionDescarga = decripcionDescarga;
	}

	public String getRutaDescarga() {
		return rutaDescarga;
	}

	public void setRutaDescarga(String rutaDescarga) {
		this.rutaDescarga = rutaDescarga;
	}
}