package pe.gob.osinergmin.sicoes.util.renovacioncontrato;

public enum TipoDia {
    CALENDARIO("1", "Calendario"),
    DIA_HABIL("2", "Dia Habil");
    
    private String codigo;
    private String descripcion;
    
    TipoDia(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}
