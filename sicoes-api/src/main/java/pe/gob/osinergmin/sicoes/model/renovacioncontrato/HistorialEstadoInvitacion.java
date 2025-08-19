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
 * Entidad para la tabla SICOES_TZ_HIST_EST_INVITACION
 * Representa el historial de estados de invitación
 */
@Entity
@Table(name = "SICOES_TZ_HIST_EST_INVITACION")
public class HistorialEstadoInvitacion extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_HIST_EST_INVIT")
    @SequenceGenerator(name = "GEN_SICOES_SEQ_HIST_EST_INVIT", sequenceName = "SEQ_SICOES_HIST_EST_INVIT", allocationSize = 1)
    @Column(name = "ID_HISTORIAL_INVITACION")
    private Long idHistorialInvitacion;

    @Column(name = "ID_REQ_INVITACION", precision = 38, nullable = false)
    private Long idReqInvitacion;

    @Column(name = "DE_ESTADO_ANTERIOR", length = 20)
    private String deEstadoAnterior;

    @Column(name = "DE_ESTADO_NUEVO", length = 20, nullable = false)
    private String deEstadoNuevo;

    @Column(name = "FE_FECHA_CAMBIO", nullable = false)
    private Timestamp feFechaCambio;

    @Column(name = "ID_USUARIO", precision = 10, nullable = false)
    private Long idUsuario;

    @Column(name = "ES_REGISTRO", length = 1, nullable = false)
    private String esRegistro;

    // Constructores
    public HistorialEstadoInvitacion() {
    }

    public HistorialEstadoInvitacion(Long idReqInvitacion, String deEstadoAnterior, String deEstadoNuevo, 
                           Long idUsuario, String esRegistro) {
        this.idReqInvitacion = idReqInvitacion;
        this.deEstadoAnterior = deEstadoAnterior;
        this.deEstadoNuevo = deEstadoNuevo;
        this.idUsuario = idUsuario;
        this.esRegistro = esRegistro;
        this.feFechaCambio = new Timestamp(System.currentTimeMillis());
    }

    // Getters y Setters
    public Long getIdHistorialInvitacion() {
        return idHistorialInvitacion;
    }

    public void setIdHistorialInvitacion(Long idHistorialInvitacion) {
        this.idHistorialInvitacion = idHistorialInvitacion;
    }

    public Long getIdReqInvitacion() {
        return idReqInvitacion;
    }

    public void setIdReqInvitacion(Long idReqInvitacion) {
        this.idReqInvitacion = idReqInvitacion;
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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEsRegistro() {
        return esRegistro;
    }

    public void setEsRegistro(String esRegistro) {
        this.esRegistro = esRegistro;
    }

    @Override
    public String toString() {
        return "HistorialEstadoInvitacion [idHistorialInvitacion=" + idHistorialInvitacion 
                + ", idReqInvitacion=" + idReqInvitacion 
                + ", deEstadoAnterior=" + deEstadoAnterior 
                + ", deEstadoNuevo=" + deEstadoNuevo 
                + ", feFechaCambio=" + feFechaCambio 
                + ", idUsuario=" + idUsuario 
                + ", esRegistro=" + esRegistro + "]";
    }
}
