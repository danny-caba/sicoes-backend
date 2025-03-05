package pe.gob.osinergmin.sicoes.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "SICOES_TM_PACES_ESTADO", schema = "ES_SICOES")
public class PacesEstado extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PACES_ESTADO", nullable = false)
    private Long idPacesEstado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PACES", nullable = false)
    private Paces paces;

    @Column(name = "ID_TIPO_ESTADO", nullable = false)
    private Long idTipoEstado;

    @Column(name = "DE_OBSERVACION", length = 1000)
    private String deObservacion;

    @Column(name = "FE_FECHA_ESTADO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date feFechaEstado;

           

	// Getters and Setters
    

	
    public Long getIdPacesEstado() {
        return idPacesEstado;
    }

    public void setIdPacesEstado(Long idPacesEstado) {
        this.idPacesEstado = idPacesEstado;
    }

    public Paces getPaces() {
        return paces;
    }

    public void setPaces(Paces paces) {
        this.paces = paces;
    }

    public Long getIdTipoEstado() {
        return idTipoEstado;
    }

    public void setIdTipoEstado(Long idTipoEstado) {
        this.idTipoEstado = idTipoEstado;
    }

    public String getDeObservacion() {
        return deObservacion;
    }

    public void setDeObservacion(String deObservacion) {
        this.deObservacion = deObservacion;
    }

    public Date getFeFechaEstado() {
        return feFechaEstado;
    }

    public void setFeFechaEstado(Date feFechaEstado) {
        this.feFechaEstado = feFechaEstado;
    }

}
