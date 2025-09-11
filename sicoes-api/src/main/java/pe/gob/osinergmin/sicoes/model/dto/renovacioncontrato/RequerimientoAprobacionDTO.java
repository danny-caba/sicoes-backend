package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
public class RequerimientoAprobacionDTO {
    private Long idReqAprobacion;

    private Long idRequerimiento;

    private Long idReqInforme;

    private InformeRenovacionContratoDTO informeRenovacionContrato;

    private Long idReqDocumento;

    private Long idTipoLd;

    private Long idGrupoLd;

    private Long idUsuario;

    private Long idEstadoLd;

    @Column(name = "ID_FIRMADO_LD", precision = 38)
    private Long idFirmadoLd;

    @Column(name = "DE_OBSERVACION", length = 500)
    private String deObservacion;

    private Date feAprobacion;

    private Date feRechazo;

    private Date feFirma;

    private InformeRenovacionContratoDTO informeRenovacion;

    private NotificacionRCDTO notificacion;

    private Date feAsignacion;

    private Long idTipoAprobadorLd;

    private Long idGrupoAprobadorLd;

    private ListadoDetalleDTO grupoLd;
    private ListadoDetalleDTO tipoAprobadorLd;
    private ListadoDetalleDTO grupoAprobadorLd;

}
