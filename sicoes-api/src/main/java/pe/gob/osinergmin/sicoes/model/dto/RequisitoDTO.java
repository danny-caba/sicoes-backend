package pe.gob.osinergmin.sicoes.model.dto;

public class RequisitoDTO {
    private Long idSeccionRequisito;
    private String coRequisito;
    private String descripcion;

    private String tipoDato;

    private String tipoDatoEntrada;

    public Long getIdSeccionRequisito() {
        return idSeccionRequisito;
    }

    public void setIdSeccionRequisito(Long idSeccionRequisito) {
        this.idSeccionRequisito = idSeccionRequisito;
    }

    public String getCoRequisito() {
        return coRequisito;
    }

    public void setCoRequisito(String coRequisito) {
        this.coRequisito = coRequisito;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getTipoDatoEntrada() {
        return tipoDatoEntrada;
    }

    public void setTipoDatoEntrada(String tipoDatoEntrada) {
        this.tipoDatoEntrada = tipoDatoEntrada;
    }
}
