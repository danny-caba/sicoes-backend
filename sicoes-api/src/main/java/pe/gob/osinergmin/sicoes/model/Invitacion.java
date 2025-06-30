package pe.gob.osinergmin.sicoes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "SICOES_TC_REQ_INVITACION")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invitacion extends BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SICOES_SEQ_REQ_INVITACION_GEN")
    @SequenceGenerator(name = "SICOES_SEQ_REQ_INVITACION_GEN", sequenceName = "SICOES_SEQ_REQ_INVITACION", allocationSize = 1)
    @Column(name = "ID_REQ_INVITACION")
    private Long idReqInvitacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_REQUERIMIENTO")
    private Requerimiento requerimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO_LD")
    private ListadoDetalle estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUPERVISORA")
    private Supervisora supervisora;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FE_INVITACION")
    private Date feInvitacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FE_CADUCIDAD")
    private Date feCaducidad;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FE_ACEPTACION")
    private Date feAceptacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FE_RECHAZO")
    private Date feRechazo;

    @Column(name = "CA_SALDO_CONTRATO")
    private Long caSaldoContrato;

    @Column(name = "FL_ACTIVO")
    private String flActivo;

    @Column(name = "IP_CREACION")
    private String ipCreacion;

    @Column(name = "IP_ACTUALIZACION")
    private String ipActualizacion;

}
