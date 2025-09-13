package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;
import java.util.Date;

@Getter
@Setter
public class AprobacionCreateResponseDTO { 
    private Long idRequerimientoAprobacion;
    private String observacion;
    private Date fechaAprobacion;
    private Date fechaRechazo;
    private Date fechaFirma;
    private ListadoDetalleDTO estadoLd;
    private UsuarioCreateResponseDTO usuario;
    private ListadoDetalleDTO tipoAprobacionLd;
    private ListadoDetalleDTO grupoAprobadorLd;
    private NotificacionCreateResponseDTO notificacion;
    private InformeCreateResponseDTO informe;
}
