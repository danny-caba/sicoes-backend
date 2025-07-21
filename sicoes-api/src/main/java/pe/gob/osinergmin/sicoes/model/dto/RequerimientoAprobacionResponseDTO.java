package pe.gob.osinergmin.sicoes.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;

import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class RequerimientoAprobacionResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("requerimiento")
    private RequerimientoDTO requerimiento;

    @JsonProperty("tipo")
    private ListadoDetalle tipo;

    @JsonProperty("fechaAsignacion")
    private Date fechaAsignacion;

    @JsonProperty("archivoInforme")
    private Archivo archivoInforme;

    @JsonProperty("estadoFirmaJefeUnidad")
    private String estadoFirmaJefeUnidad;

    @JsonProperty("estadoFirmaGerente")
    private String estadoFirmaGerente;

    @JsonProperty("estadoAprobacionGPPM")
    private String estadoAprobacionGPPM;

    @JsonProperty("estadoAprobacionGSE")
    private String estadoAprobacionGSE;

    @JsonProperty("accionAprobar")
    private boolean accionAprobar;

    public RequerimientoAprobacionResponseDTO() {
        this.estadoFirmaJefeUnidad = null;
        this.estadoFirmaGerente = null;
        this.estadoAprobacionGPPM = null;
        this.estadoAprobacionGSE = null;
    }

    public RequerimientoDTO getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(RequerimientoDTO requerimiento) {
        this.requerimiento = requerimiento;
    }

    public ListadoDetalle getTipo() {
        return tipo;
    }

    public void setTipo(ListadoDetalle tipo) {
        this.tipo = tipo;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Archivo getArchivoInforme() {
        return archivoInforme;
    }

    public void setArchivoInforme(Archivo archivoInforme) {
        this.archivoInforme = archivoInforme;
    }

    public String getEstadoFirmaJefeUnidad() {
        return estadoFirmaJefeUnidad;
    }

    public void setEstadoFirmaJefeUnidad(String estadoFirmaJefeUnidad) {
        this.estadoFirmaJefeUnidad = estadoFirmaJefeUnidad;
    }

    public String getEstadoFirmaGerente() {
        return estadoFirmaGerente;
    }

    public void setEstadoFirmaGerente(String estadoFirmaGerente) {
        this.estadoFirmaGerente = estadoFirmaGerente;
    }

    public String getEstadoAprobacionGPPM() {
        return estadoAprobacionGPPM;
    }

    public void setEstadoAprobacionGPPM(String estadoAprobacionGPPM) {
        this.estadoAprobacionGPPM = estadoAprobacionGPPM;
    }

    public String getEstadoAprobacionGSE() {
        return estadoAprobacionGSE;
    }

    public void setEstadoAprobacionGSE(String estadoAprobacionGSE) {
        this.estadoAprobacionGSE = estadoAprobacionGSE;
    }

    public boolean getAccionAprobar() {
        return accionAprobar;
    }

    public void setAccionAprobar(boolean accionAprobar) {
        this.accionAprobar = accionAprobar;
    }

    @JsonInclude(JsonInclude.Include.ALWAYS)
    public static class RequerimientoDTO implements Serializable {
        private static final long serialVersionUID = 1L;

        @JsonProperty("nuExpediente")
        private String nuExpediente;

        @JsonProperty("requerimientoUuid")
        private String requerimientoUuid;

        @JsonProperty("estado")
        private ListadoDetalle estado;

        @JsonProperty("division")
        private DivisionDTO division;

        @JsonProperty("supervisora")
        private SupervisoraDTO supervisora;

        public RequerimientoDTO() {
            this.supervisora = null;
        }

        public String getNuExpediente() {
            return nuExpediente;
        }

        public void setNuExpediente(String nuExpediente) {
            this.nuExpediente = nuExpediente;
        }

        public String getRequerimientoUuid() {
            return requerimientoUuid;
        }

        public void setRequerimientoUuid(String requerimientoUuid) {
            this.requerimientoUuid = requerimientoUuid;
        }

        public ListadoDetalle getEstado() {
            return estado;
        }

        public void setEstado(ListadoDetalle estado) {
            this.estado = estado;
        }

        public DivisionDTO getDivision() {
            return division;
        }

        public void setDivision(DivisionDTO division) {
            this.division = division;
        }

        public SupervisoraDTO getSupervisora() {
            return supervisora;
        }

        public void setSupervisora(SupervisoraDTO supervisora) {
            this.supervisora = supervisora;
        }
    }

    @JsonInclude(JsonInclude.Include.ALWAYS)
    public static class SupervisoraDTO implements Serializable {
        private static final long serialVersionUID = 1L;

        @JsonProperty("tipoDocumento")
        private String tipoDocumento;

        @JsonProperty("nombreRazonSocial")
        private String nombreRazonSocial;

        @JsonProperty("nombres")
        private String nombres;

        @JsonProperty("apellidoMaterno")
        private String apellidoMaterno;

        @JsonProperty("apellidoPaterno")
        private String apellidoPaterno;

        public String getTipoDocumento() {
            return tipoDocumento;
        }

        public void setTipoDocumento(String tipoDocumento) {
            this.tipoDocumento = tipoDocumento;
        }

        public String getNombreRazonSocial() {
            return nombreRazonSocial;
        }

        public void setNombreRazonSocial(String nombreRazonSocial) {
            this.nombreRazonSocial = nombreRazonSocial;
        }

        public String getNombres() {
            return nombres;
        }

        public void setNombres(String nombres) {
            this.nombres = nombres;
        }

        public String getApellidoMaterno() {
            return apellidoMaterno;
        }

        public void setApellidoMaterno(String apellidoMaterno) {
            this.apellidoMaterno = apellidoMaterno;
        }

        public String getApellidoPaterno() {
            return apellidoPaterno;
        }

        public void setApellidoPaterno(String apellidoPaterno) {
            this.apellidoPaterno = apellidoPaterno;
        }
    }
}