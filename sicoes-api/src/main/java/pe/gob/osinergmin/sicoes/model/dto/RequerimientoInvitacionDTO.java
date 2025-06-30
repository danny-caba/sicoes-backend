package pe.gob.osinergmin.sicoes.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Requerimiento;
import pe.gob.osinergmin.sicoes.model.Supervisora;

import java.util.Date;

public class RequerimientoInvitacionDTO {

    private Long idReqInvitacion;

    private Requerimiento requerimiento;
    private ListadoDetalle estado;
    private Supervisora supervisora;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date feInvitacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date feCaducidad;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date feAceptacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date feRechazo;

    private Long caSaldoContrato;

    private String flActivo;

    private String usCreacion;
    private String ipCreacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date feCreacion;

    private String usActualizacion;
    private String ipActualizacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date feActualizacion;

    public Long getIdReqInvitacion() {
        return idReqInvitacion;
    }

    public Requerimiento getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
    }

    public ListadoDetalle getEstado() {
        return estado;
    }

    public void setEstado(ListadoDetalle estado) {
        this.estado = estado;
    }

    public Supervisora getSupervisora() {
        return supervisora;
    }

    public void setSupervisora(Supervisora supervisora) {
        this.supervisora = supervisora;
    }

    public Date getFeInvitacion() {
        return feInvitacion;
    }

    public void setFeInvitacion(Date feInvitacion) {
        this.feInvitacion = feInvitacion;
    }

    public Date getFeCaducidad() {
        return feCaducidad;
    }

    public void setFeCaducidad(Date feCaducidad) {
        this.feCaducidad = feCaducidad;
    }

    public Date getFeRechazo() {
        return feRechazo;
    }

    public void setFeRechazo(Date feRechazo) {
        this.feRechazo = feRechazo;
    }

    public Date getFeAceptacion() {
        return feAceptacion;
    }

    public void setFeAceptacion(Date feAceptacion) {
        this.feAceptacion = feAceptacion;
    }

    public String getFlActivo() {
        return flActivo;
    }

    public void setFlActivo(String flActivo) {
        this.flActivo = flActivo;
    }

    public Long getCaSaldoContrato() {
        return caSaldoContrato;
    }

    public void setCaSaldoContrato(Long caSaldoContrato) {
        this.caSaldoContrato = caSaldoContrato;
    }

    public String getIpCreacion() {
        return ipCreacion;
    }

    public void setIpCreacion(String ipCreacion) {
        this.ipCreacion = ipCreacion;
    }

    public String getUsCreacion() {
        return usCreacion;
    }

    public void setUsCreacion(String usCreacion) {
        this.usCreacion = usCreacion;
    }

    public Date getFeCreacion() {
        return feCreacion;
    }

    public void setFeCreacion(Date feCreacion) {
        this.feCreacion = feCreacion;
    }

    public String getIpActualizacion() {
        return ipActualizacion;
    }

    public void setIpActualizacion(String ipActualizacion) {
        this.ipActualizacion = ipActualizacion;
    }

    public String getUsActualizacion() {
        return usActualizacion;
    }

    public void setUsActualizacion(String usActualizacion) {
        this.usActualizacion = usActualizacion;
    }

    public Date getFeActualizacion() {
        return feActualizacion;
    }

    public void setFeActualizacion(Date feActualizacion) {
        this.feActualizacion = feActualizacion;
    }

    public void setIdReqInvitacion(Long idReqInvitacion) {
        this.idReqInvitacion = idReqInvitacion;
    }
}
