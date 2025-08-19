package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcesoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idProceso;
	private ListadoDetalleDTO sector;
	private ListadoDetalleDTO subsector;
	private ListadoDetalleDTO estado;
	private ListadoDetalleDTO tipoFacturacion;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date fechaPublicacion;
	private String numeroProceso;
	private String nombreProceso;
	private String numeroExpediente;
	private String codigoArea;
	private String nombreArea;
	private String procesoUuid;

	public Long getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}

	public ListadoDetalleDTO getSector() {
		return sector;
	}

	public void setSector(ListadoDetalleDTO sector) {
		this.sector = sector;
	}

	public ListadoDetalleDTO getSubsector() {
		return subsector;
	}

	public void setSubsector(ListadoDetalleDTO subsector) {
		this.subsector = subsector;
	}

	public ListadoDetalleDTO getEstado() {
		return estado;
	}

	public void setEstado(ListadoDetalleDTO estado) {
		this.estado = estado;
	}

	public ListadoDetalleDTO getTipoFacturacion() {
		return tipoFacturacion;
	}

	public void setTipoFacturacion(ListadoDetalleDTO tipoFacturacion) {
		this.tipoFacturacion = tipoFacturacion;
	}

	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public String getNumeroProceso() {
		return numeroProceso;
	}

	public void setNumeroProceso(String numeroProceso) {
		this.numeroProceso = numeroProceso;
	}

	public String getNombreProceso() {
		return nombreProceso;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public String getCodigoArea() {
		return codigoArea;
	}

	public void setCodigoArea(String codigoArea) {
		this.codigoArea = codigoArea;
	}

	public String getNombreArea() {
		return nombreArea;
	}

	public void setNombreArea(String nombreArea) {
		this.nombreArea = nombreArea;
	}

	public String getProcesoUuid() {
		return procesoUuid;
	}

	public void setProcesoUuid(String procesoUuid) {
		this.procesoUuid = procesoUuid;
	}
}