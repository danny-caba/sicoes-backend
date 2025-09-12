package pe.gob.osinergmin.sicoes.model.dto;
import java.io.Serializable;

public class ArchivoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idArchivo;
    private ListadoDetalleDTO tipoArchivo;
    private String nombre;
    private String nombreReal;
    private String codigo;
    private String tipo;

    public Long getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(Long idArchivo) {
        this.idArchivo = idArchivo;
    }

    public ListadoDetalleDTO getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(ListadoDetalleDTO tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreReal() {
        return nombreReal;
    }

    public void setNombreReal(String nombreReal) {
        this.nombreReal = nombreReal;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
