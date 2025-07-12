package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;

public class ContratoDetalleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idContrato;
    private String numeroContratoSap;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "America/Lima")
    private Date fechaSuscripcionContrato;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "America/Lima")
    private Date fechaInicioContrato;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "America/Lima")
    private Date fechaFinalContrato;

    private SolicitudDTO solicitudPerfCont;
    
    public ContratoDetalleDTO() {
    }

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getNumeroContratoSap() {
		return numeroContratoSap;
	}

	public void setNumeroContratoSap(String numeroContratoSap) {
		this.numeroContratoSap = numeroContratoSap;
	}

	public Date getFechaSuscripcionContrato() {
		return fechaSuscripcionContrato;
	}

	public void setFechaSuscripcionContrato(Date fechaSuscripcionContrato) {
		this.fechaSuscripcionContrato = fechaSuscripcionContrato;
	}

	public Date getFechaInicioContrato() {
		return fechaInicioContrato;
	}

	public void setFechaInicioContrato(Date fechaInicioContrato) {
		this.fechaInicioContrato = fechaInicioContrato;
	}

	public Date getFechaFinalContrato() {
		return fechaFinalContrato;
	}

	public void setFechaFinalContrato(Date fechaFinalContrato) {
		this.fechaFinalContrato = fechaFinalContrato;
	}

	public SolicitudDTO getSolicitudPerfCont() {
		return solicitudPerfCont;
	}

	public void setSolicitudPerfCont(SolicitudDTO solicitudPerfCont) {
		this.solicitudPerfCont = solicitudPerfCont;
	}
    
}