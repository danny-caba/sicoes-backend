package pe.gob.osinergmin.sicoes.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Requerimiento;
import pe.gob.osinergmin.sicoes.model.Supervisora;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitacionDTO {

    private Long idReqInvitacion;

    private Requerimiento requerimiento;
    private ListadoDetalle estado;
    private Supervisora supervisora;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date feInvitacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date feCaducidad;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date feAceptacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date feRechazo;

    private Long caSaldoContrato;

    private String flActivo;

    private String usCreacion;
    private String ipCreacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date feCreacion;

    private String usActualizacion;
    private String ipActualizacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date feActualizacion;
}
