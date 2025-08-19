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

import pe.gob.osinergmin.sicoes.model.BaseModel;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Notificacion;
import pe.gob.osinergmin.sicoes.model.Usuario;

/**
 * Entidad para la tabla SICOES_TD_INFORME_RENOVACION
 * Representa los informes de renovación de contrato
 */
@Entity
@Table(name = "SICOES_TD_INFORME_RENOVACION")
public class InformeRenovacion extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_INF_RENOVACION")
    @SequenceGenerator(name = "GEN_SICOES_SEQ_INF_RENOVACION", sequenceName = "SEQ_SICOES_TD_INF_RENOVACION", allocationSize = 1)
    @Column(name = "ID_INFORME_RENOVACION")
    private Long idInformeRenovacion;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", insertable = false, updatable = false)
    private Usuario usuario;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_NOTIFICACION", insertable = false, updatable = false)
    private Notificacion notificacion;



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



    @ManyToOne(fetch = FetchType.LAZY)
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

    public InformeRenovacion(String deUuidInfoRenovacion, 
                           Integer esVigente, String esCompletado, String esRegistro) {
        this.deUuidInfoRenovacion = deUuidInfoRenovacion;
        this.esVigente = esVigente;
        this.esCompletado = esCompletado;
        this.esRegistro = esRegistro;
    }

    // Getters y Setters
    public Long getIdInformeRenovacion() {
        return idInformeRenovacion;
    }

    public void setIdInformeRenovacion(Long idInformeRenovacion) {
        this.idInformeRenovacion = idInformeRenovacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Notificacion getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }

    public RequerimientoRenovacion getRequerimientoRenovacion() {
        return requerimientoRenovacion;
    }

    public void setRequerimientoRenovacion(RequerimientoRenovacion requerimientoRenovacion) {
        this.requerimientoRenovacion = requerimientoRenovacion;
    }

    public String getDeObjeto() {
        return deObjeto;
    }

    public void setDeObjeto(String deObjeto) {
        this.deObjeto = deObjeto;
    }

    public String getDeBaseLegal() {
        return deBaseLegal;
    }

    public void setDeBaseLegal(String deBaseLegal) {
        this.deBaseLegal = deBaseLegal;
    }

    public String getDeAntecedentes() {
        return deAntecedentes;
    }

    public void setDeAntecedentes(String deAntecedentes) {
        this.deAntecedentes = deAntecedentes;
    }

    public String getDeJustificacion() {
        return deJustificacion;
    }

    public void setDeJustificacion(String deJustificacion) {
        this.deJustificacion = deJustificacion;
    }

    public String getDeNecesidad() {
        return deNecesidad;
    }

    public void setDeNecesidad(String deNecesidad) {
        this.deNecesidad = deNecesidad;
    }

    public String getDeConclusiones() {
        return deConclusiones;
    }

    public void setDeConclusiones(String deConclusiones) {
        this.deConclusiones = deConclusiones;
    }

    public Integer getEsVigente() {
        return esVigente;
    }

    public void setEsVigente(Integer esVigente) {
        this.esVigente = esVigente;
    }

    public ListadoDetalle getEstadoAprobacionInforme() {
        return estadoAprobacionInforme;
    }

    public void setEstadoAprobacionInforme(ListadoDetalle estadoAprobacionInforme) {
        this.estadoAprobacionInforme = estadoAprobacionInforme;
    }

    public String getDeUuidInfoRenovacion() {
        return deUuidInfoRenovacion;
    }

    public void setDeUuidInfoRenovacion(String deUuidInfoRenovacion) {
        this.deUuidInfoRenovacion = deUuidInfoRenovacion;
    }

    public String getDeNombreArchivo() {
        return deNombreArchivo;
    }

    public void setDeNombreArchivo(String deNombreArchivo) {
        this.deNombreArchivo = deNombreArchivo;
    }

    public String getDeRutaArchivo() {
        return deRutaArchivo;
    }

    public void setDeRutaArchivo(String deRutaArchivo) {
        this.deRutaArchivo = deRutaArchivo;
    }

    public String getEsCompletado() {
        return esCompletado;
    }

    public void setEsCompletado(String esCompletado) {
        this.esCompletado = esCompletado;
    }

    public String getEsRegistro() {
        return esRegistro;
    }

    public void setEsRegistro(String esRegistro) {
        this.esRegistro = esRegistro;
    }

    @Override
    public String toString() {
        return "InformeRenovacion [idInformeRenovacion=" + idInformeRenovacion 
                + ", deUuidInfoRenovacion=" + deUuidInfoRenovacion 
                + ", esVigente=" + esVigente 
                + ", deNombreArchivo=" + deNombreArchivo 
                + ", deRutaArchivo=" + deRutaArchivo 
                + ", esCompletado=" + esCompletado 
                + ", esRegistro=" + esRegistro + "]";
    }
}
