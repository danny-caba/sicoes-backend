package pe.gob.osinergmin.sicoes.model.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "usuarios")
public class UsuariosSigedDTO {
    private List<UsuarioSigedDTO> usuarioList;

    @XmlElement(name = "usuario")
    public List<UsuarioSigedDTO> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<UsuarioSigedDTO> usuarioList) {
        this.usuarioList = usuarioList;
    }
}
