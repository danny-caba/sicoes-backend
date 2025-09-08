package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class InvitacionResponseDTO {
    
    private Integer idRequerimientoInvitacion;
    private String numeroExpediente;
    private String nombreItem;
    private String razSocial;
    private String numeroRuc;
    private String nombreRepresentante;
    private String numeroDocumentoRepresentante;
    private Integer estadoInvitacion;
    private ListadoDetalle estado;
    private String descripcionEstado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaVencimiento;
    private Date feInvitacion;
    private Date feAceptacion;
    private String observaciones;
    private Integer idUsuarioCreacion;
    private String nombreUsuarioCreacion;
    private Long idSupervisora;
    private String sector;
    private String subSector;

}