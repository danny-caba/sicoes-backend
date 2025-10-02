package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BandejaAprobacionResponseDTO {
    private Long idRequermientoAprobacion;
    private Long idInformeRenovacion;
    private String tipoAprobacion;
    private String numeroExpediente;
    private String informe;
    private String tp;
    private String contratista;
    private String tipoContrato;
    private String fechaIngresoInforme;
    private String estadoAprobacionInforme;
    private String estadoAprobacionJefeDivision;
    private String estadoAprobacionGerenteDivision;
    private String estadoAprobacionGPPM;
    private String estadoAprobacionGSE;
    private ListadoDetalleDTO tipoAprobacionLd;
    private ListadoDetalleDTO estadoLd;
    private ListadoDetalleDTO grupoAprobadorLd;
    private String uuidInformeRenovacion;
    private String nombreArchivo;


}
