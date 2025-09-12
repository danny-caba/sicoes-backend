package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;
import java.util.Date;

@Getter
@Setter
public class AprobacionCreateResponseDTO { 
    private Long idRequerimientoRenovacion;
    private UsuarioCreateResponseDTO usuario;
    private String observacion;
    private Date fechaAprobacion;
    private Date fechaRechazo;
    private Date fechaFirma;
    private NotificacionCreateResponseDTO notificacion;
    private ListadoDetalleDTO tipoAprobacionLd;
    private ListadoDetalleDTO estadoLd;
    private ListadoDetalleDTO grupoAprobadorLd;
    private InformeCreateResponseDTO informe;
}
