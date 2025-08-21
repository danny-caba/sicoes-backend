package pe.gob.osinergmin.sicoes.util.renovacioncontrato;

public enum EstadoInvitacion {
    INVITACION("947", "Invitacion");
    
    private String codigo;
    private String descripcion;
    
    EstadoInvitacion(String codigo, String descripcion) {
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
