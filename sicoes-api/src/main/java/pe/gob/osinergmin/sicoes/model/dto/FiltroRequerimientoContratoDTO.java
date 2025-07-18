package pe.gob.osinergmin.sicoes.model.dto;

public class FiltroRequerimientoContratoDTO {

    private Long division;
    private Long perfil;
    private Long supervisora;
    private String numeroContrato;

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

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }
}
