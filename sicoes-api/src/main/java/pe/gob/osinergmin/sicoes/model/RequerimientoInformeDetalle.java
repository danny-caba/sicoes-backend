package pe.gob.osinergmin.sicoes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="SICOES_TD_REQ_INFORME")
public class RequerimientoInformeDetalle extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_REQ_DET_INFORME")
    @SequenceGenerator(name="GEN_SICOES_SEQ_REQ_DET_INFORME", sequenceName = "SICOES_SEQ_REQ_DET_INFORME", allocationSize = 1)
    @Column(name = "ID_REQ_DET_INFORME")
    private Long idRequerimientoInformeDetalle;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ID_REQ_INFORME")
    private RequerimientoInforme requerimientoInforme;

    @Column(name = "DE_OBJETIVO", length = 5000)
    private String objetivo;

    @Column(name = "DE_PERFIL_REQUERIDO", length = 5000)
    private String perfilRequerido;

    @Column(name = "DE_PLAZO_EJECUCION", length = 5000)
    private String plazoEjecucion;

    @Column(name = "DE_COSTO_SERVICIO", length = 5000)
    private String costoServicio;

    @Column(name = "DE_TERMINO_SERVICIO", length = 5000)
    private String terminoServicio;

    @Column(name = "DE_ENTREGABLES", length = 5000)
    private String entregables;

    @Column(name = "DE_PENALIDADES", length = 5000)
    private String penalidades;

    @Column(name = "DE_TIPO_SEGURO", length = 5000)
    private String tipoSeguro;

    @Column(name = "DE_DISPONIBILIDAD_PRES", length = 5000)
    private String disponibilidadPresupuestal;

    @Column(name = "DE_DECLARACION_JURADA", length = 5000)
    private String DeclaracionJurada;

    public Long getIdRequerimientoInformeDetalle() {
        return idRequerimientoInformeDetalle;
    }

    public void setIdRequerimientoInformeDetalle(Long idRequerimientoInformeDetalle) {
        this.idRequerimientoInformeDetalle = idRequerimientoInformeDetalle;
    }

    public RequerimientoInforme getRequerimientoInforme() {
        return requerimientoInforme;
    }

    public void setRequerimientoInforme(RequerimientoInforme requerimientoInforme) {
        this.requerimientoInforme = requerimientoInforme;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getPerfilRequerido() {
        return perfilRequerido;
    }

    public void setPerfilRequerido(String perfilRequerido) {
        this.perfilRequerido = perfilRequerido;
    }

    public String getPlazoEjecucion() {
        return plazoEjecucion;
    }

    public void setPlazoEjecucion(String plazoEjecucion) {
        this.plazoEjecucion = plazoEjecucion;
    }

    public String getCostoServicio() {
        return costoServicio;
    }

    public void setCostoServicio(String costoServicio) {
        this.costoServicio = costoServicio;
    }

    public String getTerminoServicio() {
        return terminoServicio;
    }

    public void setTerminoServicio(String terminoServicio) {
        this.terminoServicio = terminoServicio;
    }

    public String getEntregables() {
        return entregables;
    }

    public void setEntregables(String entregables) {
        this.entregables = entregables;
    }

    public String getPenalidades() {
        return penalidades;
    }

    public void setPenalidades(String penalidades) {
        this.penalidades = penalidades;
    }

    public String getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(String tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }

    public String getDisponibilidadPresupuestal() {
        return disponibilidadPresupuestal;
    }

    public void setDisponibilidadPresupuestal(String disponibilidadPresupuestal) {
        this.disponibilidadPresupuestal = disponibilidadPresupuestal;
    }

    public String getDeclaracionJurada() {
        return DeclaracionJurada;
    }

    public void setDeclaracionJurada(String declaracionJurada) {
        DeclaracionJurada = declaracionJurada;
    }
}
