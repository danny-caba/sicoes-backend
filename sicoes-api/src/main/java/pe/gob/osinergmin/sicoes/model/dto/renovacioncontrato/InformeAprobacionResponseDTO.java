package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import java.time.LocalDateTime;

public class InformeAprobacionResponseDTO {
    
    private Integer idInformeRenovacion;
    private String numeroExpediente;
    private String nombreItem;
    private String tipoSector;
    private String tipoSubSector;
    private String razSocialSupervisora;
    private String numeroRucSupervisora;
    private String nombreRepresentante;
    private Integer estadoInforme;
    private String descripcionEstado;
    private Integer grupoAprobador;
    private String descripcionGrupoAprobador;
    private LocalDateTime fechaCreacionInforme;
    private String observaciones;
    private String conclusiones;
    private Integer prioridad;
    private Integer diasVencimiento;
    private Integer idUsuarioCreacion;
    private String nombreUsuarioCreacion;
    private Integer idRequerimientoRenovacion;
    private String rutaAdjuntoInforme;
    private String nombreArchivoInforme;
    private String uuidArchivoInforme;
    private Integer esVigente;
    private String esCompletado;
    
    public InformeAprobacionResponseDTO() {
    }
    
    public InformeAprobacionResponseDTO(Integer idInformeRenovacion, String numeroExpediente, 
            String nombreItem, String tipoSector, String tipoSubSector, String razSocialSupervisora,
            String numeroRucSupervisora, String nombreRepresentante, Integer estadoInforme,
            String descripcionEstado, Integer grupoAprobador, String descripcionGrupoAprobador,
            LocalDateTime fechaCreacionInforme, String observaciones, String conclusiones, 
            Integer prioridad, Integer diasVencimiento, Integer idUsuarioCreacion,
            String nombreUsuarioCreacion, Integer idRequerimientoRenovacion, 
            String rutaAdjuntoInforme, String nombreArchivoInforme, String uuidArchivoInforme,
            Integer esVigente, String esCompletado) {
        this.idInformeRenovacion = idInformeRenovacion;
        this.numeroExpediente = numeroExpediente;
        this.nombreItem = nombreItem;
        this.tipoSector = tipoSector;
        this.tipoSubSector = tipoSubSector;
        this.razSocialSupervisora = razSocialSupervisora;
        this.numeroRucSupervisora = numeroRucSupervisora;
        this.nombreRepresentante = nombreRepresentante;
        this.estadoInforme = estadoInforme;
        this.descripcionEstado = descripcionEstado;
        this.grupoAprobador = grupoAprobador;
        this.descripcionGrupoAprobador = descripcionGrupoAprobador;
        this.fechaCreacionInforme = fechaCreacionInforme;
        this.observaciones = observaciones;
        this.conclusiones = conclusiones;
        this.prioridad = prioridad;
        this.diasVencimiento = diasVencimiento;
        this.idUsuarioCreacion = idUsuarioCreacion;
        this.nombreUsuarioCreacion = nombreUsuarioCreacion;
        this.idRequerimientoRenovacion = idRequerimientoRenovacion;
        this.rutaAdjuntoInforme = rutaAdjuntoInforme;
        this.nombreArchivoInforme = nombreArchivoInforme;
        this.uuidArchivoInforme = uuidArchivoInforme;
        this.esVigente = esVigente;
        this.esCompletado = esCompletado;
    }

    public Integer getIdInformeRenovacion() {
        return idInformeRenovacion;
    }

    public void setIdInformeRenovacion(Integer idInformeRenovacion) {
        this.idInformeRenovacion = idInformeRenovacion;
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

    public String getTipoSector() {
        return tipoSector;
    }

    public void setTipoSector(String tipoSector) {
        this.tipoSector = tipoSector;
    }

    public String getTipoSubSector() {
        return tipoSubSector;
    }

    public void setTipoSubSector(String tipoSubSector) {
        this.tipoSubSector = tipoSubSector;
    }

    public String getRazSocialSupervisora() {
        return razSocialSupervisora;
    }

    public void setRazSocialSupervisora(String razSocialSupervisora) {
        this.razSocialSupervisora = razSocialSupervisora;
    }

    public String getNumeroRucSupervisora() {
        return numeroRucSupervisora;
    }

    public void setNumeroRucSupervisora(String numeroRucSupervisora) {
        this.numeroRucSupervisora = numeroRucSupervisora;
    }

    public String getNombreRepresentante() {
        return nombreRepresentante;
    }

    public void setNombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
    }

    public Integer getEstadoInforme() {
        return estadoInforme;
    }

    public void setEstadoInforme(Integer estadoInforme) {
        this.estadoInforme = estadoInforme;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public Integer getGrupoAprobador() {
        return grupoAprobador;
    }

    public void setGrupoAprobador(Integer grupoAprobador) {
        this.grupoAprobador = grupoAprobador;
    }

    public String getDescripcionGrupoAprobador() {
        return descripcionGrupoAprobador;
    }

    public void setDescripcionGrupoAprobador(String descripcionGrupoAprobador) {
        this.descripcionGrupoAprobador = descripcionGrupoAprobador;
    }

    public LocalDateTime getFechaCreacionInforme() {
        return fechaCreacionInforme;
    }

    public void setFechaCreacionInforme(LocalDateTime fechaCreacionInforme) {
        this.fechaCreacionInforme = fechaCreacionInforme;
    }


    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getConclusiones() {
        return conclusiones;
    }

    public void setConclusiones(String conclusiones) {
        this.conclusiones = conclusiones;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public Integer getDiasVencimiento() {
        return diasVencimiento;
    }

    public void setDiasVencimiento(Integer diasVencimiento) {
        this.diasVencimiento = diasVencimiento;
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


    public Integer getIdRequerimientoRenovacion() {
        return idRequerimientoRenovacion;
    }

    public void setIdRequerimientoRenovacion(Integer idRequerimientoRenovacion) {
        this.idRequerimientoRenovacion = idRequerimientoRenovacion;
    }

    public String getRutaAdjuntoInforme() {
        return rutaAdjuntoInforme;
    }

    public void setRutaAdjuntoInforme(String rutaAdjuntoInforme) {
        this.rutaAdjuntoInforme = rutaAdjuntoInforme;
    }

    public String getNombreArchivoInforme() {
        return nombreArchivoInforme;
    }

    public void setNombreArchivoInforme(String nombreArchivoInforme) {
        this.nombreArchivoInforme = nombreArchivoInforme;
    }

    public String getUuidArchivoInforme() {
        return uuidArchivoInforme;
    }

    public void setUuidArchivoInforme(String uuidArchivoInforme) {
        this.uuidArchivoInforme = uuidArchivoInforme;
    }

    public Integer getEsVigente() {
        return esVigente;
    }

    public void setEsVigente(Integer esVigente) {
        this.esVigente = esVigente;
    }

    public String getEsCompletado() {
        return esCompletado;
    }

    public void setEsCompletado(String esCompletado) {
        this.esCompletado = esCompletado;
    }
}