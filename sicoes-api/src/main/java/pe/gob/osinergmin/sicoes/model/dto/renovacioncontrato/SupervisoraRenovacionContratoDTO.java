package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupervisoraRenovacionContratoDTO {

    private Long idSupervisora;

    private String numeroExpediente;

    private ListadoDetalleDTO tipoPersona;

    private ListadoDetalleDTO tipoDocumento;

    private String numeroDocumento;

    private String nombreRazonSocial;

    private String nombres;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private String codigoRuc;

    private String direccion;

}
