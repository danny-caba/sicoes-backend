package pe.gob.osinergmin.sicoes.model.renovacioncontrato;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pe.gob.osinergmin.sicoes.model.BaseModel;

/**
 * Entidad para la tabla SICOES_TZ_HIST_EST_APROB_CAMPO
 * Representa el historial de estados de aprobación de campos
 */
@Entity
@Table(name = "SICOES_TZ_HIST_EST_APROB_CAMPO")
public class HistorialEstadoAprobacionCampo extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_HIST_EST_APROB")
    @SequenceGenerator(name = "GEN_SICOES_SEQ_HIST_EST_APROB", sequenceName = "SEQ_SICOES_HIST_EST_APROB", allocationSize = 1)
    @Column(name = "ID_HISTORIAL_ESTADO_CAMPO")
    private Long idHistorialEstadoCampo;

    @Column(name = "ID_REQ_APROBACION", precision = 38, nullable = false)
    private Long idReqAprobacion;

    @Column(name = "ID_USUARIO", precision = 10, nullable = false)
    private Long idUsuario;

    @Column(name = "DE_ESTADO_ANTERIOR", length = 20)
    private String deEstadoAnterior;

    @Column(name = "DE_ESTADO_NUEVO", length = 20, nullable = false)
    private String deEstadoNuevo;

    @Column(name = "FE_FECHA_CAMBIO", nullable = false)
    private Timestamp feFechaCambio;

    @Column(name = "ES_REGISTRO", length = 1, nullable = false)
    private String esRegistro;

    // Constructores
    public HistorialEstadoAprobacionCampo() {
    }

    public HistorialEstadoAprobacionCampo(Long idReqAprobacion, Long idUsuario, String deEstadoAnterior, 
                           String deEstadoNuevo, String esRegistro) {
        this.idReqAprobacion = idReqAprobacion;
        this.idUsuario = idUsuario;
        this.deEstadoAnterior = deEstadoAnterior;
        this.deEstadoNuevo = deEstadoNuevo;
        this.esRegistro = esRegistro;
        this.feFechaCambio = new Timestamp(System.currentTimeMillis());
    }

    // Getters y Setters
    public Long getIdHistorialEstadoCampo() {
        return idHistorialEstadoCampo;
    }

    public void setIdHistorialEstadoCampo(Long idHistorialEstadoCampo) {
        this.idHistorialEstadoCampo = idHistorialEstadoCampo;
    }

    public Long getIdReqAprobacion() {
        return idReqAprobacion;
    }

    public void setIdReqAprobacion(Long idReqAprobacion) {
        this.idReqAprobacion = idReqAprobacion;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDeEstadoAnterior() {
        return deEstadoAnterior;
    }

    public void setDeEstadoAnterior(String deEstadoAnterior) {
        this.deEstadoAnterior = deEstadoAnterior;
    }

    public String getDeEstadoNuevo() {
        return deEstadoNuevo;
    }

    public void setDeEstadoNuevo(String deEstadoNuevo) {
        this.deEstadoNuevo = deEstadoNuevo;
    }

    public Timestamp getFeFechaCambio() {
        return feFechaCambio;
    }

    public void setFeFechaCambio(Timestamp feFechaCambio) {
        this.feFechaCambio = feFechaCambio;
    }

    public String getEsRegistro() {
        return esRegistro;
    }

    public void setEsRegistro(String esRegistro) {
        this.esRegistro = esRegistro;
    }

    @Override
    public String toString() {
        return "HistorialEstadoAprobacionCampo [idHistorialEstadoCampo=" + idHistorialEstadoCampo 
                + ", idReqAprobacion=" + idReqAprobacion 
                + ", idUsuario=" + idUsuario 
                + ", deEstadoAnterior=" + deEstadoAnterior 
                + ", deEstadoNuevo=" + deEstadoNuevo 
                + ", feFechaCambio=" + feFechaCambio 
                + ", esRegistro=" + esRegistro + "]";
    }
}
