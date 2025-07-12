package pe.gob.osinergmin.sicoes.model.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "usuario")
public class UsuarioDetalleSigedDTO {
    private Long idUsuario;
    private String nombresUsuario;
    private String usuario;
    private String correoUsuario;

    @XmlElement(name = "idUsuario")
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlElement(name = "nombresUsuario")
    public void setNombresUsuario(String nombresUsuario) {
        this.nombresUsuario = nombresUsuario;
    }

    @XmlElement(name = "usuario")
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @XmlElement(name = "correoUsuario")
    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public Long getIdUsuario() { return idUsuario; }
    public String getNombresUsuario() { return nombresUsuario; }
    public String getUsuario() { return usuario; }
    public String getCorreoUsuario() { return correoUsuario; }
}
