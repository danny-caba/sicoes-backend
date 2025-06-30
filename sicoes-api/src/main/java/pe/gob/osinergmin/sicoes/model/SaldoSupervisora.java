package pe.gob.osinergmin.sicoes.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "SICOES_TM_SALDO_SUPERVISORA")
public class SaldoSupervisora {

    @Id
    @Column(name = "ID_SALDO_SUPERVISORA")
    private Long idSaldoSupervisora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUPERVISORA")
    private Supervisora supervisora;

    @Column(name = "CA_CANTIDAD")
    private Long cantidad;

    @Column(name = "US_CREACION")
    private String usCreacion;

    @Column(name = "IP_CREACION")
    private String ipCreacion;

    @Column(name = "FE_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date feCreacion;

    @Column(name = "US_ACTUALIZACION")
    private String usActualizacion;

    @Column(name = "IP_ACTUALIZACION")
    private String ipActualizacion;

    @Column(name = "FE_ACTUALIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date feActualizacion;
}
