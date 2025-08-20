package pe.gob.osinergmin.sicoes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="SICOES_TZ_REEMPLAZO_PERSONAL")
public class PersonalReemplazo extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_TZ_REEMPLAZO_PERSONAL")
    @SequenceGenerator(name="GEN_SICOES_SEQ_TZ_REEMPLAZO_PERSONAL", sequenceName = "SICOES_SEQ_ID_REEMP_PERS", allocationSize = 1)
    @Column(name = "ID_REEMPLAZO_PERSONAL")
    private Long idReemplazo;

    @Column(name = "ID_SOLI_PERF_CONT",nullable = false)
    private Long idSolicitud;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PERSONA_PROPUESTA")
    private Supervisora personaPropuesta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CO_PERFIL", referencedColumnName = "CO_LISTADO_DETALLE")
    private ListadoDetalle perfil;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FE_FECHA_REGISTRO")
    private Date feFechaRegistro;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FE_FECHA_INICIO_CONTRACTUAL")
    private Date feFechaInicioContractual;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ES_ESTADO_REEMPLAZO")
    private ListadoDetalle estadoReemplazo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PERSONA_BAJA")
    private Supervisora personaBaja;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CO_PERFIL_PER_BAJA", referencedColumnName = "CO_LISTADO_DETALLE")
    private ListadoDetalle perfilBaja;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FE_FECHA_BAJA")
    private Date feFechaBaja;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FE_FECHA_DESVINCULACION")
    private Date feFechaDesvinculacion;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FE_FECHA_FINALIZACION_CONTRATO")
    private Date feFechaFinalizacionContrato;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ES_ESTADO_REVISAR_DOC")
    private ListadoDetalle estadoRevisarDoc;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ES_ESTADO_EVAL_DOC")
    private ListadoDetalle estadoEvalDoc;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ES_ESTADO_REVISAR_EVAL_DOC")
    private ListadoDetalle estadoRevisarEval;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ES_ESTADO_APROBACION_INFORME")
    private ListadoDetalle estadoAprobacionInforme;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ES_ESTADO_APROBACION_ADENDA")
    private ListadoDetalle estadoAprobacionAdenda;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ES_ESTADO_DOC_INI_SERV")
    private ListadoDetalle estadoDocIniServ;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ES_ESTADO_EVAL_DOC_INI_SERV")
    private ListadoDetalle estadoEvalDocIniServ;

    @Transient
    private String numeroExpediente;

    @Transient
    public Supervisora supervisora;

    @Transient
    private java.util.List<Archivo> archivos = java.util.Collections.emptyList();

    @Transient
    private java.util.List<Archivo> adicionales = java.util.Collections.emptyList();

    @Transient
    private Archivo archivo;

}
