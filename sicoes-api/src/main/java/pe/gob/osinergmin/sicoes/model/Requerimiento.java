package pe.gob.osinergmin.sicoes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "SICOES_TC_REQUERIMIENTO")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Requerimiento extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SICOES_SEQ_REQUERIMIENTO_GEN")
    @SequenceGenerator(name = "SICOES_SEQ_REQUERIMIENTO_GEN", sequenceName = "SICOES_SEQ_REQUERIMIENTO", allocationSize = 1)
    @Column(name = "ID_REQUERIMIENTO")
    private Long idRequerimiento;

    @Column(name = "NU_EXPEDIENTE")
    private String nuExpediente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO_LD")
    private ListadoDetalle estado;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FE_REGISTRO")
    private Date feRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DIVISION")
    private Division division;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PERFIL_LD")
    private ListadoDetalle perfil;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FE_PLAZO_CARGA_DOC")
    private Date fePlazoCargaDoc;

    @Lob
    @Column(name = "DE_OBSERVACION")
    private String deObservacion;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FE_REGISTRO")
    private Date fechaPlazoCargaDoc;

    @Column(name = "NU_SIAF")
    private String nuSiaf;

    @Column(name = "IP_CREACION")
    private String ipCreacion;

    @Column(name = "IP_ACTUALIZACION")
    private String ipActualizacion;

    @OneToMany(mappedBy = "requerimiento", fetch = FetchType.LAZY)
    private List<RequerimientoInvitacion> reqInvitaciones;
}
