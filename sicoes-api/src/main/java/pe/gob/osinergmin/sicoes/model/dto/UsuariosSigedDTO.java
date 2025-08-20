package pe.gob.osinergmin.sicoes.model.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "usuarios")
public class UsuariosSigedDTO {
    private List<UsuarioDetalleSigedDTO> usuarioList;

    @XmlElement(name = "usuario")
    public List<UsuarioDetalleSigedDTO> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<UsuarioDetalleSigedDTO> usuarioList) {
        this.usuarioList = usuarioList;
    }
}
