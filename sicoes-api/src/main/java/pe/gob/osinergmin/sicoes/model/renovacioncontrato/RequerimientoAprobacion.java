package pe.gob.osinergmin.sicoes.model.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import pe.gob.osinergmin.sicoes.model.BaseModel;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Notificacion;
import pe.gob.osinergmin.sicoes.model.Usuario;

@SqlResultSetMapping(
    name = "RequerimientoAprobacionMapping",
    entities = @EntityResult(
        entityClass = RequerimientoAprobacion.class,
        fields = {
            @FieldResult(name = "idReqAprobacion", column = "ID_REQ_APROBACION"),
            @FieldResult(name = "idRequerimiento", column = "ID_REQUERIMIENTO"),
            @FieldResult(name = "idReqInforme", column = "ID_REQ_INFORME"),
            @FieldResult(name = "idReqDocumento", column = "ID_REQ_DOCUMENTO"),
            @FieldResult(name = "idTipoLd", column = "ID_TIPO_LD"),
            @FieldResult(name = "idGrupoLd", column = "ID_GRUPO_LD"),
            @FieldResult(name = "idUsuario", column = "ID_USUARIO"),
            @FieldResult(name = "idEstadoLd", column = "ID_ESTADO_LD"),
            @FieldResult(name = "idFirmadoLd", column = "ID_FIRMADO_LD"),
            @FieldResult(name = "deObservacion", column = "DE_OBSERVACION"),
            @FieldResult(name = "feAprobacion", column = "FE_APROBACION"),
            @FieldResult(name = "feRechazo", column = "FE_RECHAZO"),
            @FieldResult(name = "feFirma", column = "FE_FIRMA"),
            @FieldResult(name = "idInformeRenovacion", column = "ID_INFORME_RENOVACION"),
            @FieldResult(name = "feAsignacion", column = "FE_ASIGNACION"),
            @FieldResult(name = "idTipoAprobadorLd", column = "ID_TIPO_APROBADOR_LD"),
            @FieldResult(name = "idGrupoAprobadorLd", column = "ID_GRUPO_APROBADOR_LD"),
            @FieldResult(name = "usuCreacion", column = "US_CREACION"),
            @FieldResult(name = "ipCreacion", column = "IP_CREACION"),
            @FieldResult(name = "fecCreacion", column = "FE_CREACION"),
            @FieldResult(name = "usuActualizacion", column = "US_ACTUALIZACION"),
            @FieldResult(name = "ipActualizacion", column = "IP_ACTUALIZACION"),
            @FieldResult(name = "fecActualizacion", column = "FE_ACTUALIZACION")
        }
    )
)

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

    @Column(name = "ID_REQUERIMIENTO", precision = 38, nullable = true)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRUPO_LD")
    private ListadoDetalle grupo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO_LD")
    private ListadoDetalle estado;

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

    @Column(name = "ID_NOTIFICACION", precision = 38)
    private Long idNotificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_NOTIFICACION", insertable = false, updatable = false)
    private Notificacion notificacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_ASIGNACION")
    private Date feAsignacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_APROBADOR_LD")
    private ListadoDetalle tipoAprobador;

    @Column(name = "ID_GRUPO_APROBADOR_LD", precision = 38)
    private Long idGrupoAprobadorLd;
}
