package pe.gob.osinergmin.sicoes.model.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import pe.gob.osinergmin.sicoes.model.BaseModel;
import pe.gob.osinergmin.sicoes.model.Notificacion;

/**
 * Entidad para la tabla SICOES_TC_REQ_APROBACION
 * Representa los requerimientos de aprobación de renovación de contratos
 */
@Getter
@Setter
@Entity
@Table(name = "SICOES_TC_REQ_APROBACION")
public class RequerimientoAprobacion extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_REQ_APROBACION")
    @SequenceGenerator(name = "GEN_SICOES_SEQ_REQ_APROBACION", sequenceName = "SICOES_SEQ_REQ_APROBACION", allocationSize = 1)
    @Column(name = "ID_REQ_APROBACION")
    private Long idReqAprobacion;

    @Column(name = "ID_REQUERIMIENTO", precision = 38, nullable = false)
    private Long idRequerimiento;

    @Column(name = "ID_REQ_INFORME", precision = 38)
    private Long idReqInforme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_INFORME_RENOVACION", nullable = false,insertable = false, updatable = false)
    private InformeRenovacionContrato informeRenovacionContrato;

    @Column(name = "ID_REQ_DOCUMENTO", precision = 38)
    private Long idReqDocumento;

    @Column(name = "ID_TIPO_LD", precision = 38)
    private Long idTipoLd;

    @Column(name = "ID_GRUPO_LD", precision = 38)
    private Long idGrupoLd;

    @Column(name = "ID_USUARIO", precision = 38)
    private Long idUsuario;

    @Column(name = "ID_ESTADO_LD", precision = 38)
    private Long idEstadoLd;

    @Column(name = "ID_FIRMADO_LD", precision = 38)
    private Long idFirmadoLd;

    @Column(name = "DE_OBSERVACION", length = 500)
    private String deObservacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_APROBACION")
    private Date feAprobacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_RECHAZO")
    private Date feRechazo;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_FIRMA")
    private Date feFirma;

    @Column(name = "ID_INFORME_RENOVACION", precision = 38)
    private Long idInformeRenovacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_INFORME_RENOVACION", insertable = false, updatable = false)
    private InformeRenovacion informeRenovacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_NOTIFICACION")
    private Notificacion notificacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_ASIGNACION")
    private Date feAsignacion;

    @Column(name = "ID_TIPO_APROBADOR_LD", precision = 38)
    private Long idTipoAprobadorLd;

    @Column(name = "ID_GRUPO_APROBADOR_LD", precision = 38)
    private Long idGrupoAprobadorLd;
}
