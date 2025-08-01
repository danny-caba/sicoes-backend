package pe.gob.osinergmin.sicoes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="SICOES_TZ_APROBACION_REEMP")
public class Aprobacion extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_ID_APROB_REEMP")
    @SequenceGenerator(name = "GEN_SICOES_SEQ_ID_APROB_REEMP", sequenceName = "SICOES_SEQ_ID_APROB_REEMP", allocationSize = 1)
    @Column(name = "ID_APROBACION")
    private Long idAprobacion;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_REEMPLAZO_PERSONAL")
    private PersonalReemplazo remplazoPersonal;

    @Column(name = "NU_NUMERO_EXPEDIENTE")
    private Long numeroExpediente;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DOCUMENTO")
    private DocumentoReemplazo  documento;

    @Column(name = "DE_TP")
    private String deTp;

    @Column(name = "ID_CONTRATISTA")
    private Long idContratista;

    @Column(name = "CO_TIPO_SOLICITUD")
    private String coTipoSolicitud;

    @Column(name = "CO_TIPO_APROBACION")
    private String coTipoAprobacion;

    @Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FE_FECHA_INGRESO")
    private Date fechaIngreso;

    @Column(name = "DE_OBSERVACION")
    private String deObservacion;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ES_ESTADO_APROB")
    private ListadoDetalle estadoAprob;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "ES_ESTADO_APROB_GERENTE_DIV")
    private ListadoDetalle estadoAprobGerenteDiv;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "ES_ESTADO_APROB_GERENTE_LINEA")
    private ListadoDetalle estadoAprobGerenteLinea;

    @Column(name = "ID_APROBADOR")
    private Long idAprobador;

    @Column(name = "ID_ROL")
    private Long idRol;

}