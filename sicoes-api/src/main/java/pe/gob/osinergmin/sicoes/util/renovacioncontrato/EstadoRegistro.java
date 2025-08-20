package pe.gob.osinergmin.sicoes.util.renovacioncontrato;

public enum EstadoRegistro {
    ACTIVO("1", "Activo"),
    INACTIVO("0", "Inactivo");
    
    private String codigo;
    private String descripcion;
    
    EstadoRegistro(String codigo, String descripcion) {
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
