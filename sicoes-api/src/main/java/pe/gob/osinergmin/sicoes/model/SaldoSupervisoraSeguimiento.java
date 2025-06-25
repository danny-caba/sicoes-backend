package pe.gob.osinergmin.sicoes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="SICOES_TZ_SALDO_SUPERVISORA")
public class SaldoSupervisoraSeguimiento extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_TRAZA_SALDO")
    @SequenceGenerator(name="GEN_SICOES_SEQ_TRAZA_SALDO", sequenceName = "SICOES_SEQ_TRAZA_SALDO", allocationSize = 1)
    @Column(name = "ID_TRAZA_SALDO")
    private Long idSaldoSupervisoraSeguimiento;

    @Column(name = "CA_SALDO_INICIAL")
    private Long cantidadInicial;

    @Column(name = "CA_SALDO_RESUL")
    private Long cantidadResultante;

    public Long getIdSaldoSupervisoraSeguimiento() {
        return idSaldoSupervisoraSeguimiento;
    }

    public void setIdSaldoSupervisoraSeguimiento(Long idSaldoSupervisoraSeguimiento) {
        this.idSaldoSupervisoraSeguimiento = idSaldoSupervisoraSeguimiento;
    }

    public Long getCantidadInicial() {
        return cantidadInicial;
    }

    public void setCantidadInicial(Long cantidadInicial) {
        this.cantidadInicial = cantidadInicial;
    }

    public Long getCantidadResultante() {
        return cantidadResultante;
    }

    public void setCantidadResultante(Long cantidadResultante) {
        this.cantidadResultante = cantidadResultante;
    }
}
