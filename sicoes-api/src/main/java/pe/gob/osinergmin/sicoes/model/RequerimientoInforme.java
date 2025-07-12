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
@Table(name="SICOES_TC_REQ_INFORME")
public class RequerimientoInforme extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_REQ_INFORME")
    @SequenceGenerator(name="GEN_SICOES_SEQ_REQ_INFORME", sequenceName = "SICOES_SEQ_REQ_INFORME", allocationSize = 1)
    @Column(name = "ID_REQ_INFORME")
    private Long idRequerimientoInforme;

    @Column(name = "CO_UUID")
    private String requerimientoInformeUuid;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ID_REQUERIMIENTO")
    private Requerimiento requerimiento;

    public Long getIdRequerimientoInforme() {
        return idRequerimientoInforme;
    }

    public void setIdRequerimientoInforme(Long idRequerimientoInforme) {
        this.idRequerimientoInforme = idRequerimientoInforme;
    }

    public String getRequerimientoInformeUuid() {
        return requerimientoInformeUuid;
    }

    public void setRequerimientoInformeUuid(String requerimientoInformeUuid) {
        this.requerimientoInformeUuid = requerimientoInformeUuid;
    }

    public Requerimiento getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
    }
}
