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
@Table(name="SICOES_TM_SALDO_SUPERVISORA")
public class SaldoSupervisora extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_SALDO_SUPERVISORA")
    @SequenceGenerator(name="GEN_SICOES_SEQ_SALDO_SUPERVISORA", sequenceName = "SICOES_SEQ_SALDO_SUPERVISORA", allocationSize = 1)
    @Column(name = "ID_SALDO_SUPERVISORA")
    private Long idSaldoSupervisora;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ID_SUPERVISORA")
    private Supervisora supervisora;

    @Column(name = "CA_CANTIDAD")
    private Long cantidad;

    public Long getIdSaldoSupervisora() {
        return idSaldoSupervisora;
    }

    public void setIdSaldoSupervisora(Long idSaldoSupervisora) {
        this.idSaldoSupervisora = idSaldoSupervisora;
    }

    public Supervisora getSupervisora() {
        return supervisora;
    }

    public void setSupervisora(Supervisora supervisora) {
        this.supervisora = supervisora;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
