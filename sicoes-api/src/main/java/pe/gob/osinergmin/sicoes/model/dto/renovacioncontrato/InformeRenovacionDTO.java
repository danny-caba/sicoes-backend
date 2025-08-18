package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;

import java.io.Serializable;

public class InformeRenovacionDTO extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idInformeRenovacion;

    @JsonIgnore
    private Usuario usuario;
    private Notificacion notificacion;
    private RequerimientoRenovacion requerimientoRenovacion;
    private String deObjeto;
    private String deBaseLegal;
    private String deAntecedentes;
    private String deJustificacion;
    private String deNecesidad;
    private String deConclusiones;
    private Integer esVigente;
    private ListadoDetalle estadoAprobacionInforme;
    private String deUuidInfoRenovacion;
    private String deNombreArchivo;
    private String deRutaArchivo;
    private String esCompletado;
    private String esRegistro;
    private String supervisora;

    // Constructores
    public InformeRenovacionDTO() {
    }

    public InformeRenovacionDTO(String deUuidInfoRenovacion,
                                Integer esVigente, String esCompletado, String esRegistro) {
        this.deUuidInfoRenovacion = deUuidInfoRenovacion;
        this.esVigente = esVigente;
        this.esCompletado = esCompletado;
        this.esRegistro = esRegistro;
    }

    // Getters y Setters
    public Long getIdInformeRenovacion() {
        return idInformeRenovacion;
    }

    public void setIdInformeRenovacion(Long idInformeRenovacion) {
        this.idInformeRenovacion = idInformeRenovacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Notificacion getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }

    public RequerimientoRenovacion getRequerimientoRenovacion() {
        return requerimientoRenovacion;
    }

    public void setRequerimientoRenovacion(RequerimientoRenovacion requerimientoRenovacion) {
        this.requerimientoRenovacion = requerimientoRenovacion;
    }

    public String getDeObjeto() {
        return deObjeto;
    }

    public void setDeObjeto(String deObjeto) {
        this.deObjeto = deObjeto;
    }

    public String getDeBaseLegal() {
        return deBaseLegal;
    }

    public void setDeBaseLegal(String deBaseLegal) {
        this.deBaseLegal = deBaseLegal;
    }

    public String getDeAntecedentes() {
        return deAntecedentes;
    }

    public void setDeAntecedentes(String deAntecedentes) {
        this.deAntecedentes = deAntecedentes;
    }

    public String getDeJustificacion() {
        return deJustificacion;
    }

    public void setDeJustificacion(String deJustificacion) {
        this.deJustificacion = deJustificacion;
    }

    public String getDeNecesidad() {
        return deNecesidad;
    }

    public void setDeNecesidad(String deNecesidad) {
        this.deNecesidad = deNecesidad;
    }

    public String getDeConclusiones() {
        return deConclusiones;
    }

    public void setDeConclusiones(String deConclusiones) {
        this.deConclusiones = deConclusiones;
    }

    public Integer getEsVigente() {
        return esVigente;
    }

    public void setEsVigente(Integer esVigente) {
        this.esVigente = esVigente;
    }

    public ListadoDetalle getEstadoAprobacionInforme() {
        return estadoAprobacionInforme;
    }

    public void setEstadoAprobacionInforme(ListadoDetalle estadoAprobacionInforme) {
        this.estadoAprobacionInforme = estadoAprobacionInforme;
    }

    public String getDeUuidInfoRenovacion() {
        return deUuidInfoRenovacion;
    }

    public void setDeUuidInfoRenovacion(String deUuidInfoRenovacion) {
        this.deUuidInfoRenovacion = deUuidInfoRenovacion;
    }

    public String getDeNombreArchivo() {
        return deNombreArchivo;
    }

    public void setDeNombreArchivo(String deNombreArchivo) {
        this.deNombreArchivo = deNombreArchivo;
    }

    public String getDeRutaArchivo() {
        return deRutaArchivo;
    }

    public void setDeRutaArchivo(String deRutaArchivo) {
        this.deRutaArchivo = deRutaArchivo;
    }

    public String getEsCompletado() {
        return esCompletado;
    }

    public void setEsCompletado(String esCompletado) {
        this.esCompletado = esCompletado;
    }

    public String getEsRegistro() {
        return esRegistro;
    }

    public void setEsRegistro(String esRegistro) {
        this.esRegistro = esRegistro;
    }

    @Override
    public String toString() {
        return "InformeRenovacion [idInformeRenovacion=" + idInformeRenovacion 
                + ", deUuidInfoRenovacion=" + deUuidInfoRenovacion 
                + ", esVigente=" + esVigente 
                + ", deNombreArchivo=" + deNombreArchivo 
                + ", deRutaArchivo=" + deRutaArchivo 
                + ", esCompletado=" + esCompletado 
                + ", esRegistro=" + esRegistro + "]";
    }

    public String getSupervisora() {
        return supervisora;
    }

    public void setSupervisora(String supervisora) {
        this.supervisora = supervisora;
    }
}
