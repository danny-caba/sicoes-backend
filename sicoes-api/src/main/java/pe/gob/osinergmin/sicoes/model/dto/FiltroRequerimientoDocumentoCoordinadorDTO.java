package pe.gob.osinergmin.sicoes.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class FiltroRequerimientoDocumentoCoordinadorDTO {

    private Long division;

    private Long perfil;

    private Long supervisora;

    private Long estado;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFin;

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

    public Long getSupervisora() {
        return supervisora;
    }

    public void setSupervisora(Long supervisora) {
        this.supervisora = supervisora;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
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

}
