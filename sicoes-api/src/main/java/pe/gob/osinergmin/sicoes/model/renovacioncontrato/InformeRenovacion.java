package pe.gob.osinergmin.sicoes.model.renovacioncontrato;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pe.gob.osinergmin.sicoes.model.BaseModel;

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

    @Column(name = "ID_USUARIO", precision = 10, nullable = false)
    private Long idUsuario;

    @Column(name = "ID_NOTIFICACION", precision = 10)
    private Long idNotificacion;

    @Column(name = "ID_REQUERIMIENTO", nullable = false)
    private Long idRequerimiento;

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

    @Column(name = "ES_APROBACION_INFORME", precision = 10, nullable = false)
    private Integer esAprobacionInforme;

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

    public InformeRenovacion(Long idUsuario, Long idRequerimiento, String deUuidInfoRenovacion, 
                           Integer esVigente, Integer esAprobacionInforme, String esCompletado, String esRegistro) {
        this.idUsuario = idUsuario;
        this.idRequerimiento = idRequerimiento;
        this.deUuidInfoRenovacion = deUuidInfoRenovacion;
        this.esVigente = esVigente;
        this.esAprobacionInforme = esAprobacionInforme;
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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public Long getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(Long idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
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

    public Integer getEsAprobacionInforme() {
        return esAprobacionInforme;
    }

    public void setEsAprobacionInforme(Integer esAprobacionInforme) {
        this.esAprobacionInforme = esAprobacionInforme;
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
                + ", idUsuario=" + idUsuario 
                + ", idNotificacion=" + idNotificacion 
                + ", idRequerimiento=" + idRequerimiento 
                + ", deUuidInfoRenovacion=" + deUuidInfoRenovacion 
                + ", esVigente=" + esVigente 
                + ", esAprobacionInforme=" + esAprobacionInforme 
                + ", deNombreArchivo=" + deNombreArchivo 
                + ", deRutaArchivo=" + deRutaArchivo 
                + ", esCompletado=" + esCompletado 
                + ", esRegistro=" + esRegistro + "]";
    }
}
