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

    @Column(name = "ID_PERSONA_PROPUESTA")
    private Long idPersonaPropuesta;

    @Column(name = "CO_PERFIL", length = 15)
    private String coPerfil;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FE_FECHA_REGISTRO")
    private Date feFechaRegistro;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FE_FECHA_INICIO_CONTRACTUAL")
    private Date feFechaInicioContractual;

    @Column(name = "ES_ESTADO_REEMPLAZO", length = 1)
    private Long esEstadoReemplazo;

    @Column(name = "ID_PERSONA_BAJA")
    private Long idPersonaBaja;

    @Column(name = "CO_PERFIL_PER_BAJA", length = 15)
    private String coPerfilPerBaja;

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

    @Column(name = "ES_ESTADO_EVAL_DOC", length = 1)
    private Long esEstadoEvalDoc;

    @Column(name = "ES_ESTADO_REVISAR_EVAL_DOC", length = 1)
    private Long esEstadoRevisarEval;

    @Column(name = "ES_ESTADO_APROBACION_INFORME", length = 1)
    private Long esEstadoAprobacionInforme;

    @Column(name = "ES_ESTADO_APROBACION_ADENDA", length = 1)
    private Long esEstadoAprobacionAdenda;

    @Column(name = "ES_ESTADO_EVAL_DOC_INI_SERV", length = 1)
    private Long esEstadoEvalDocIniServ;

}
