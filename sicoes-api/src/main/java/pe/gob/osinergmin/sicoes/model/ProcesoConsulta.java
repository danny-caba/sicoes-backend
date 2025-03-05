package pe.gob.osinergmin.sicoes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="SICOES_TD_PROCESO_CONSULTA")
public class ProcesoConsulta extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "GEN_SICOES_SEQ_PROCESO_CONSULTA")
    @SequenceGenerator(name="GEN_SICOES_SEQ_PROCESO_CONSULTA",
            sequenceName = "SICOES_SEQ_PROCESO_CONSULTA",
            allocationSize = 1)
    @Column(name = "ID_PROCESO_CONSULTA")
    private Long idProcesoConsulta;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_PROCESO")
    private Proceso proceso;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_ESTADO_LD")
    private ListadoDetalle estado;

    @Column(name="CO_UUID")
    private String procesoConsultaUuid;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_SECCION")
    private ListadoDetalle seccion;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_SUPERVISORA")
    private Supervisora supervisora;

    @Column(name="DE_NUMERAL")
    private String deNumeral;

    @Column(name="DE_LITERAL")
    private String deLiteral;

    @Column(name="DE_PAGINA")
    private String dePagina;

    @Column(name="DE_CONSULTA")
    private String deConsulta;

    @Column(name="DE_ARTICULO_NORMA")
    private String deArticuloNorma;

    public Long getIdProcesoConsulta() {
        return idProcesoConsulta;
    }

    public void setIdProcesoConsulta(Long idProcesoConsulta) {
        this.idProcesoConsulta = idProcesoConsulta;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public ListadoDetalle getEstado() {
        return estado;
    }

    public void setEstado(ListadoDetalle estado) {
        this.estado = estado;
    }

    public String getProcesoConsultaUuid() {
        return procesoConsultaUuid;
    }

    public void setProcesoConsultaUuid(String procesoConsultaUuid) {
        this.procesoConsultaUuid = procesoConsultaUuid;
    }

    public Supervisora getSupervisora() {
        return supervisora;
    }

    public void setSupervisora(Supervisora supervisora) {
        this.supervisora = supervisora;
    }

    public ListadoDetalle getSeccion() {
        return seccion;
    }

    public void setSeccion(ListadoDetalle seccion) {
        this.seccion = seccion;
    }

    public String getDeNumeral() {
        return deNumeral;
    }

    public void setDeNumeral(String deNumeral) {
        this.deNumeral = deNumeral;
    }

    public String getDeLiteral() {
        return deLiteral;
    }

    public void setDeLiteral(String deLiteral) {
        this.deLiteral = deLiteral;
    }

    public String getDePagina() {
        return dePagina;
    }

    public void setDePagina(String dePagina) {
        this.dePagina = dePagina;
    }

    public String getDeConsulta() {
        return deConsulta;
    }

    public void setDeConsulta(String deConsulta) {
        this.deConsulta = deConsulta;
    }

    public String getDeArticuloNorma() {
        return deArticuloNorma;
    }

    public void setDeArticuloNorma(String deArticuloNorma) {
        this.deArticuloNorma = deArticuloNorma;
    }

    @Override
    public String toString() {
        return "ProcesoConsulta [idProcesoConsulta=" + idProcesoConsulta
                + ", proceso=" + proceso
                + ", estado=" + estado
                + ", procesoConsultaUuid=" + procesoConsultaUuid
                + ", seccion=" + seccion
                + ", deNumeral=" + deNumeral
                + ", deLiteral=" + deLiteral
                + ", dePagina=" + dePagina
                + ", deConsulta=" + deConsulta
                + ", deArticuloNorma=" + deArticuloNorma
                + "]";
    }
}
