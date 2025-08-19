package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRCDTO {

    private Long idUsuario;

    private Long codigoUsuarioInterno;

    private String codigoRuc;

    private String contrasenia;

    private String razonSocial;

    private String correo;

    private Long telefono;
}
