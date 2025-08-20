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
 * Entidad para la tabla SICOES_TZ_HIST_EST_REQ_RENOV
 * Representa el historial de estados del requerimiento de renovación
 */
@Entity
@Table(name = "SICOES_TZ_HIST_EST_REQ_RENOV")
public class HistorialEstadoRequerimientoRenovacion extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_HIST_EST_CAMPO")
    @SequenceGenerator(name = "GEN_SICOES_SEQ_HIST_EST_CAMPO", sequenceName = "SEQ_SICOES_HIST_EST_CAMPO", allocationSize = 1)
    @Column(name = "ID_HISTORIAL_ESTADO_CAMPO")
    private Long idHistorialEstadoCampo;

    @Column(name = "ID_REQUERIMIENTO", nullable = false)
    private Long idRequerimiento;

    @Column(name = "ID_USUARIO", precision = 10)
    private Long idUsuario;

    @Column(name = "DE_ESTADO_ANTERIOR_LD", precision = 10)
    private Long deEstadoAnteriorLd;

    @Column(name = "DE_ESTADO_NUEVO_LD", precision = 10, nullable = false)
    private Long deEstadoNuevoLd;

    @Column(name = "FE_FECHA_CAMBIO", nullable = false)
    private Timestamp feFechaCambio;

    @Column(name = "ES_REGISTRO", length = 1, nullable = false)
    private String esRegistro;

    // Constructores
    public HistorialEstadoRequerimientoRenovacion() {
    }

    public HistorialEstadoRequerimientoRenovacion(Long idRequerimiento, Long idUsuario, Long deEstadoAnteriorLd, 
                          Long deEstadoNuevoLd, String esRegistro) {
        this.idRequerimiento = idRequerimiento;
        this.idUsuario = idUsuario;
        this.deEstadoAnteriorLd = deEstadoAnteriorLd;
        this.deEstadoNuevoLd = deEstadoNuevoLd;
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

    public Long getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(Long idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getDeEstadoAnteriorLd() {
        return deEstadoAnteriorLd;
    }

    public void setDeEstadoAnteriorLd(Long deEstadoAnteriorLd) {
        this.deEstadoAnteriorLd = deEstadoAnteriorLd;
    }

    public Long getDeEstadoNuevoLd() {
        return deEstadoNuevoLd;
    }

    public void setDeEstadoNuevoLd(Long deEstadoNuevoLd) {
        this.deEstadoNuevoLd = deEstadoNuevoLd;
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
        return "HistorialEstadoRequerimientoRenovacion [idHistorialEstadoCampo=" + idHistorialEstadoCampo 
                + ", idRequerimiento=" + idRequerimiento 
                + ", idUsuario=" + idUsuario 
                + ", deEstadoAnteriorLd=" + deEstadoAnteriorLd 
                + ", deEstadoNuevoLd=" + deEstadoNuevoLd 
                + ", feFechaCambio=" + feFechaCambio 
                + ", esRegistro=" + esRegistro + "]";
    }
}
