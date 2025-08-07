package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;

public class RequerimientoAprobacionDTO  implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idReqAprobacion;
    private ListadoDetalleDTO estado;
    private String nuSiaf;
    private String deObservacion;
    private String rol;

    public Long getIdReqAprobacion() {
        return idReqAprobacion;
    }

    public void setIdReqAprobacion(Long idReqAprobacion) {
        this.idReqAprobacion = idReqAprobacion;
    }

    public ListadoDetalleDTO getEstado() {
        return estado;
    }

    public void setEstado(ListadoDetalleDTO estado) {
        this.estado = estado;
    }

    public String getNuSiaf() {
        return nuSiaf;
    }

    public void setNuSiaf(String nuSiaf) {
        this.nuSiaf = nuSiaf;
    }

    public String getDeObservacion() {
        return deObservacion;
    }

    public void setDeObservacion(String deObservacion) {
        this.deObservacion = deObservacion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
