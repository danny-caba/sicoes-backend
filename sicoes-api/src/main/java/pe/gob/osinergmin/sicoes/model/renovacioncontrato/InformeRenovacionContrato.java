package pe.gob.osinergmin.sicoes.model.renovacioncontrato;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pe.gob.osinergmin.sicoes.model.BaseModel;
import pe.gob.osinergmin.sicoes.model.Notificacion;
import pe.gob.osinergmin.sicoes.model.Usuario;

@Entity
@Table(name="SICOES_TD_INFORME_RENOVACION")
@Getter
@Setter
public class InformeRenovacionContrato extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;
       
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_informe_renovacion")
    @SequenceGenerator(name = "seq_informe_renovacion", sequenceName = "SEQ_SICOES_TD_INF_RENOVACION", allocationSize = 1)

    @Column(name = "ID_INFORME_RENOVACION", nullable = false)
    private Long idInformeRenovacion;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_NOTIFICACION", referencedColumnName = "ID_NOTIFICACION")
    private Notificacion notificacion;

    @ManyToOne
    @JoinColumn(name = "ID_REQUERIMIENTO", referencedColumnName = "ID_REQ_RENOVACION", nullable = false)
    private RequerimientoRenovacion requerimiento;

    @OneToMany(mappedBy = "informeRenovacionContrato", fetch = FetchType.LAZY)
    private List<RequerimientoAprobacion> aprobaciones;

    @Lob
    @Column(name = "DE_OBJETO")
    private String objeto;

    @Lob
    @Column(name = "DE_BASE_LEGAL")
    private String baseLegal;

    @Lob
    @Column(name = "DE_ANTECEDENTES")
    private String antecedentes;

    @Lob
    @Column(name = "DE_JUSTIFICACION")
    private String justificacion;

    @Lob
    @Column(name = "DE_NECESIDAD")
    private String necesidad;

    @Lob
    @Column(name = "DE_CONCLUSIONES")
    private String conclusiones;

    @Column(name = "ES_VIGENTE", nullable = false)
    private Boolean vigente;

    @Column(name = "DE_UUID_INFO_RENOVACION", nullable = false, length = 36)
    private String uuidInformeRenovacion;

    @Column(name = "DE_NOMBRE_ARCHIVO", length = 100)
    private String nombreArchivo;

    @Column(name = "DE_RUTA_ARCHIVO", length = 100)
    private String rutaArchivo;

    @Column(name = "ES_REGISTRO", nullable = false, length = 1)
    private String registro;

    @Column(name = "ES_COMPLETADO", nullable = false, length = 1)
    private String completado;
    
//    @Column(name = "ES_APROBACION_INFORME")
//    private Integer esAprobacionInforme;
}
