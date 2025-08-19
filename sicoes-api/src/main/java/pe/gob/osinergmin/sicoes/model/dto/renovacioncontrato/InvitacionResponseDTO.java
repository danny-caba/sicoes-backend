package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import java.time.LocalDateTime;

public class InvitacionResponseDTO {
    
    private Integer idRequerimientoInvitacion;
    private String numeroExpediente;
    private String nombreItem;
    private String razSocial;
    private String numeroRuc;
    private String nombreRepresentante;
    private String numeroDocumentoRepresentante;
    private Integer estadoInvitacion;
    private String descripcionEstado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaVencimiento;
    private String observaciones;
    private Integer idUsuarioCreacion;
    private String nombreUsuarioCreacion;
    private Long idSupervisora;
    
    public InvitacionResponseDTO() {
    }
    
    public InvitacionResponseDTO(Integer idRequerimientoInvitacion, String numeroExpediente, 
            String nombreItem, String razSocial, String numeroRuc, String nombreRepresentante, 
            String numeroDocumentoRepresentante, Integer estadoInvitacion, String descripcionEstado, 
            LocalDateTime fechaCreacion, LocalDateTime fechaVencimiento, String observaciones,
            Integer idUsuarioCreacion, String nombreUsuarioCreacion) {
        this.idRequerimientoInvitacion = idRequerimientoInvitacion;
        this.numeroExpediente = numeroExpediente;
        this.nombreItem = nombreItem;
        this.razSocial = razSocial;
        this.numeroRuc = numeroRuc;
        this.nombreRepresentante = nombreRepresentante;
        this.numeroDocumentoRepresentante = numeroDocumentoRepresentante;
        this.estadoInvitacion = estadoInvitacion;
        this.descripcionEstado = descripcionEstado;
        this.fechaCreacion = fechaCreacion;
        this.fechaVencimiento = fechaVencimiento;
        this.observaciones = observaciones;
        this.idUsuarioCreacion = idUsuarioCreacion;
        this.nombreUsuarioCreacion = nombreUsuarioCreacion;
    }

    public Integer getIdRequerimientoInvitacion() {
        return idRequerimientoInvitacion;
    }

    public void setIdRequerimientoInvitacion(Integer idRequerimientoInvitacion) {
        this.idRequerimientoInvitacion = idRequerimientoInvitacion;
    }

    public String getNumeroExpediente() {
        return numeroExpediente;
    }

    public void setNumeroExpediente(String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }

    public String getNombreItem() {
        return nombreItem;
    }

    public void setNombreItem(String nombreItem) {
        this.nombreItem = nombreItem;
    }

    public String getRazSocial() {
        return razSocial;
    }

    public void setRazSocial(String razSocial) {
        this.razSocial = razSocial;
    }

    public String getNumeroRuc() {
        return numeroRuc;
    }

    public void setNumeroRuc(String numeroRuc) {
        this.numeroRuc = numeroRuc;
    }

    public String getNombreRepresentante() {
        return nombreRepresentante;
    }

    public void setNombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
    }

    public String getNumeroDocumentoRepresentante() {
        return numeroDocumentoRepresentante;
    }

    public void setNumeroDocumentoRepresentante(String numeroDocumentoRepresentante) {
        this.numeroDocumentoRepresentante = numeroDocumentoRepresentante;
    }

    public Integer getEstadoInvitacion() {
        return estadoInvitacion;
    }

    public void setEstadoInvitacion(Integer estadoInvitacion) {
        this.estadoInvitacion = estadoInvitacion;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getIdUsuarioCreacion() {
        return idUsuarioCreacion;
    }

    public void setIdUsuarioCreacion(Integer idUsuarioCreacion) {
        this.idUsuarioCreacion = idUsuarioCreacion;
    }

    public String getNombreUsuarioCreacion() {
        return nombreUsuarioCreacion;
    }

    public void setNombreUsuarioCreacion(String nombreUsuarioCreacion) {
        this.nombreUsuarioCreacion = nombreUsuarioCreacion;
    }

    public Long getIdSupervisora() {
        return idSupervisora;
    }

    public void setIdSupervisora(Long idSupervisora) {
        this.idSupervisora = idSupervisora;
    }
}