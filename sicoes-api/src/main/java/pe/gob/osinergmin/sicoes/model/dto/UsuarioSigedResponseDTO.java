package pe.gob.osinergmin.sicoes.model.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "obtenerUsuarioSIGED")
public class UsuarioSigedResponseDTO {
    private int resultCode;
    private UsuariosSigedDTO usuarios;

    @XmlElement(name = "resultCode")
    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    @XmlElement(name = "usuarios")
    public UsuariosSigedDTO getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(UsuariosSigedDTO usuarios) {
        this.usuarios = usuarios;
    }
}
