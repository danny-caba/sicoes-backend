package pe.gob.osinergmin.sicoes.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class FiltroRequerimientoDTO {
    private Long division;
    private Long perfil;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaInicio;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaFin;
    private Long supervisora;
    private Long estadoAprobacion;

    public Long getDivision() {
        return division;
    }

    public void setDivision(Long division) {
        this.division = division;
    }

    public Long getPerfil() {
        return perfil;
    }

    public void setPerfil(Long perfil) {
        this.perfil = perfil;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Long getSupervisora() {
        return supervisora;
    }

    public void setSupervisora(Long supervisora) {
        this.supervisora = supervisora;
    }

    public Long getEstadoAprobacion() {
        return estadoAprobacion;
    }

    public void setEstadoAprobacion(Long estadoAprobacion) {
        this.estadoAprobacion = estadoAprobacion;
    }
}
