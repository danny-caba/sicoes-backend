package pe.gob.osinergmin.sicoes.model.dto;

public class PerfilDetalleDTO {
    private Long idDivision;
    private Long idListadoDetalle;
    private String dePerfil;
    private String detalle;

    public PerfilDetalleDTO(Long idDivision, Long idListadoDetalle, String dePerfil, String detalle) {
        this.idDivision = idDivision;
        this.idListadoDetalle = idListadoDetalle;
        this.dePerfil = dePerfil;
        this.detalle = detalle;
    }

    public Long getIdDivision() {
        return idDivision;
    }

    public void setIdDivision(Long idDivision) {
        this.idDivision = idDivision;
    }

    public Long getIdListadoDetalle() {
        return idListadoDetalle;
    }

    public void setIdListadoDetalle(Long idListadoDetalle) {
        this.idListadoDetalle = idListadoDetalle;
    }

    public String getDePerfil() {
        return dePerfil;
    }

    public void setDePerfil(String dePerfil) {
        this.dePerfil = dePerfil;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
