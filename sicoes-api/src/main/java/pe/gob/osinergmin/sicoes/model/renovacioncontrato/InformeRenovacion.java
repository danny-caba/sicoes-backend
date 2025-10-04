package pe.gob.osinergmin.sicoes.model.renovacioncontrato;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import pe.gob.osinergmin.sicoes.model.BaseModel;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Notificacion;
import pe.gob.osinergmin.sicoes.model.Usuario;

/**
 * Entidad para la tabla SICOES_TD_INFORME_RENOVACION
 * Representa los informes de renovación de contrato
 */
@Getter
@Setter
@Entity
@Table(name = "SICOES_TD_INFORME_RENOVACION")
public class InformeRenovacion extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_INF_RENOVACION")
    @SequenceGenerator(name = "GEN_SICOES_SEQ_INF_RENOVACION", sequenceName = "SEQ_SICOES_TD_INF_RENOVACION", allocationSize = 1)
    @Column(name = "ID_INFORME_RENOVACION")
    private Long idInformeRenovacion;

    @Column(name = "ID_USUARIO", precision = 10, nullable = false)
    private Long idUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", insertable = false, updatable = false)
    private Usuario usuario;

    @Column(name = "ID_NOTIFICACION", precision = 10)
    private Long idNotificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_NOTIFICACION", insertable = false, updatable = false)
    private Notificacion notificacion;

    @Column(name = "ID_REQUERIMIENTO", nullable = false)
    private Long idRequerimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_REQUERIMIENTO", insertable = false, updatable = false)
    private RequerimientoRenovacion requerimientoRenovacion;

    @Lob
    @Column(name = "DE_OBJETO")
    private String deObjeto;

    @Lob
    @Column(name = "DE_BASE_LEGAL")
    private String deBaseLegal;

    @Lob
    @Column(name = "DE_ANTECEDENTES")
    private String deAntecedentes;

    @Lob
    @Column(name = "DE_JUSTIFICACION")
    private String deJustificacion;

    @Lob
    @Column(name = "DE_NECESIDAD")
    private String deNecesidad;

    @Lob
    @Column(name = "DE_CONCLUSIONES")
    private String deConclusiones;

    @Column(name = "ES_VIGENTE", precision = 1, nullable = false)
    private Integer esVigente;

    @Column(name = "ES_APROBACION_INFORME", insertable = true, updatable = true)
    private Long esAprobacionInforme;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ES_APROBACION_INFORME", insertable = false, updatable = false)
    private ListadoDetalle estadoAprobacionInforme;

    @Column(name = "DE_UUID_INFO_RENOVACION", length = 36, nullable = false)
    private String deUuidInfoRenovacion;

    @Column(name = "DE_NOMBRE_ARCHIVO", length = 100)
    private String deNombreArchivo;

    @Column(name = "DE_RUTA_ARCHIVO", length = 100)
    private String deRutaArchivo;

    @Column(name = "ES_COMPLETADO", length = 1, nullable = false)
    private String esCompletado;

    @Column(name = "ES_REGISTRO", length = 1, nullable = false)
    private String esRegistro;

    // Constructores
    public InformeRenovacion() {
    }
}
