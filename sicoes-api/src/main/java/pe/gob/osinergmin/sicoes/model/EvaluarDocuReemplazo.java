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
@Table(name="SICOES_TZ_EVALUAR_DOCU_REEMP")
public class EvaluarDocuReemplazo extends BaseModel implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_TZ_EVALUAR_DOCU_REEMP")
    @SequenceGenerator(name="GEN_SICOES_SEQ_TZ_EVALUAR_DOCU_REEMP", sequenceName = "SICOES_SEQ_ID_EVAL_DOCU_REEMP", allocationSize = 1)
    @Column(name = "ID_EVAL_DOCUMENTO")
    private Long idEvalDocumento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DOCUMENTO")
    private DocumentoReemplazo documento;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EVALUADO_POR")
    private Usuario evaluadoPor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ROL")
    private Rol rol;

    @Column(name = "CO_CONFORME", length = 2)
    private String conforme;

    @Column(name = "DE_OBSERVACION", length = 4000)
    private String observacion;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")

    @Column(name = "FE_FECHA_EVALUACION")
    private Date fechaEvaluacion;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FE_FECHA_INICIO_VALIDEZ")
    private Date fechaInicioValidez;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FE_FECHA_FIN_VALIDEZ")
    private Date fechaFinValidez;
}
