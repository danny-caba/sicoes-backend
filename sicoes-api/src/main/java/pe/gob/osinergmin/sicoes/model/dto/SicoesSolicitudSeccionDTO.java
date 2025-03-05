package pe.gob.osinergmin.sicoes.model.dto;

import pe.gob.osinergmin.sicoes.model.Archivo;

public class SicoesSolicitudSeccionDTO {
    private Long idSolicitudSeccion;
    private Long idSoliPersProp;
    private Long idPerConSec;
    private String procSubsanacion;
    private Double valor;
    private String texto;
    private String procRevision;
    private String evaluacion;
    private String estado;
    private String deRequisito;
    private RequisitoDTO requisito;

    public Long getIdSolicitudSeccion() {
        return idSolicitudSeccion;
    }

    public void setIdSolicitudSeccion(Long idSolicitudSeccion) {
        this.idSolicitudSeccion = idSolicitudSeccion;
    }

    public Long getIdSoliPersProp() {
        return idSoliPersProp;
    }

    public void setIdSoliPersProp(Long idSoliPersProp) {
        this.idSoliPersProp = idSoliPersProp;
    }

    public Long getIdPerConSec() {
        return idPerConSec;
    }

    public void setIdPerConSec(Long idPerConSec) {
        this.idPerConSec = idPerConSec;
    }

    public String getProcSubsanacion() {
        return procSubsanacion;
    }

    public void setProcSubsanacion(String procSubsanacion) {
        this.procSubsanacion = procSubsanacion;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getProcRevision() {
        return procRevision;
    }

    public void setProcRevision(String procRevision) {
        this.procRevision = procRevision;
    }

    public String getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(String evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDeRequisito() {
        return deRequisito;
    }

    public void setDeRequisito(String deRequisito) {
        this.deRequisito = deRequisito;
    }

    public RequisitoDTO getRequisito() {
        return requisito;
    }

    public void setRequisito(RequisitoDTO requisito) {
        this.requisito = requisito;
    }
}
