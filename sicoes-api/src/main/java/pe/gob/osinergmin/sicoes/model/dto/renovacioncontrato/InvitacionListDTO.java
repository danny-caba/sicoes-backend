package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Notificacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.PlazoConfirmacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class InvitacionListDTO {

    private Long idReqInvitacion;
    private Notificacion notificacion;
    private RequerimientoRenovacion requerimientoRenovacion;
    private ListadoDetalle estadoInvitacion;
    private Long idSupervisora;
    private Date feInvitacion;
    private Date feCaducidad;
    private Date feAceptacion;
    private Date feRechazo;
    private String flActivo;
    private String coUuid;
    private Date feCancelado;
    private String sector;
    private String subSector;


}