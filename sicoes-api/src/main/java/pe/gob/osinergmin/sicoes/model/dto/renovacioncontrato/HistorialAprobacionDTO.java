package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import java.time.LocalDateTime;

public class HistorialAprobacionDTO {
    
    private Integer idHistorialAprobacion;
    private Integer idReqAprobacion;
    private Integer idGrupoLd;
    private Integer idGrupoAprobadorLd;
    private Integer estadoAnterior;
    private String descripcionEstadoAnterior;
    private Integer estadoNuevo;
    private String descripcionEstadoNuevo;
    private String accionRealizada;
    private LocalDateTime fechaAccion;
    private Integer idUsuarioAccion;
    private String nombreUsuarioAccion;
    private String rolUsuarioAccion;
    private Integer grupoAprobador;
    private String descripcionGrupoAprobador;
    private String tipoAccion;
    private String estadoFinalProceso;
    
    public HistorialAprobacionDTO() {
    }
    
    public HistorialAprobacionDTO(Integer idHistorialAprobacion, Integer idReqAprobacion, 
            Integer idGrupoLd, Integer idGrupoAprobadorLd, Integer estadoAnterior, 
            String descripcionEstadoAnterior, Integer estadoNuevo, String descripcionEstadoNuevo, 
            String accionRealizada, LocalDateTime fechaAccion, Integer idUsuarioAccion, 
            String nombreUsuarioAccion, String rolUsuarioAccion, Integer grupoAprobador, 
            String descripcionGrupoAprobador, String tipoAccion, String estadoFinalProceso) {
        this.idHistorialAprobacion = idHistorialAprobacion;
        this.idReqAprobacion = idReqAprobacion;
        this.idGrupoLd = idGrupoLd;
        this.idGrupoAprobadorLd = idGrupoAprobadorLd;
        this.estadoAnterior = estadoAnterior;
        this.descripcionEstadoAnterior = descripcionEstadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.descripcionEstadoNuevo = descripcionEstadoNuevo;
        this.accionRealizada = accionRealizada;
        this.fechaAccion = fechaAccion;
        this.idUsuarioAccion = idUsuarioAccion;
        this.nombreUsuarioAccion = nombreUsuarioAccion;
        this.rolUsuarioAccion = rolUsuarioAccion;
        this.grupoAprobador = grupoAprobador;
        this.descripcionGrupoAprobador = descripcionGrupoAprobador;
        this.tipoAccion = tipoAccion;
        this.estadoFinalProceso = estadoFinalProceso;
    }

    public Integer getIdHistorialAprobacion() {
        return idHistorialAprobacion;
    }

    public void setIdHistorialAprobacion(Integer idHistorialAprobacion) {
        this.idHistorialAprobacion = idHistorialAprobacion;
    }

    public Integer getIdReqAprobacion() {
        return idReqAprobacion;
    }

    public void setIdReqAprobacion(Integer idReqAprobacion) {
        this.idReqAprobacion = idReqAprobacion;
    }

    public Integer getIdGrupoLd() {
        return idGrupoLd;
    }

    public void setIdGrupoLd(Integer idGrupoLd) {
        this.idGrupoLd = idGrupoLd;
    }

    public Integer getIdGrupoAprobadorLd() {
        return idGrupoAprobadorLd;
    }

    public void setIdGrupoAprobadorLd(Integer idGrupoAprobadorLd) {
        this.idGrupoAprobadorLd = idGrupoAprobadorLd;
    }

    public Integer getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(Integer estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public String getDescripcionEstadoAnterior() {
        return descripcionEstadoAnterior;
    }

    public void setDescripcionEstadoAnterior(String descripcionEstadoAnterior) {
        this.descripcionEstadoAnterior = descripcionEstadoAnterior;
    }

    public Integer getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(Integer estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public String getDescripcionEstadoNuevo() {
        return descripcionEstadoNuevo;
    }

    public void setDescripcionEstadoNuevo(String descripcionEstadoNuevo) {
        this.descripcionEstadoNuevo = descripcionEstadoNuevo;
    }

    public String getAccionRealizada() {
        return accionRealizada;
    }

    public void setAccionRealizada(String accionRealizada) {
        this.accionRealizada = accionRealizada;
    }

    public LocalDateTime getFechaAccion() {
        return fechaAccion;
    }

    public void setFechaAccion(LocalDateTime fechaAccion) {
        this.fechaAccion = fechaAccion;
    }

    public Integer getIdUsuarioAccion() {
        return idUsuarioAccion;
    }

    public void setIdUsuarioAccion(Integer idUsuarioAccion) {
        this.idUsuarioAccion = idUsuarioAccion;
    }

    public String getNombreUsuarioAccion() {
        return nombreUsuarioAccion;
    }

    public void setNombreUsuarioAccion(String nombreUsuarioAccion) {
        this.nombreUsuarioAccion = nombreUsuarioAccion;
    }

    public String getRolUsuarioAccion() {
        return rolUsuarioAccion;
    }

    public void setRolUsuarioAccion(String rolUsuarioAccion) {
        this.rolUsuarioAccion = rolUsuarioAccion;
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

    public String getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public String getEstadoFinalProceso() {
        return estadoFinalProceso;
    }

    public void setEstadoFinalProceso(String estadoFinalProceso) {
        this.estadoFinalProceso = estadoFinalProceso;
    }
}