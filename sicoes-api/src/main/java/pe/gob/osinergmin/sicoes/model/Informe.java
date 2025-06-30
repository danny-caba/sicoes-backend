package pe.gob.osinergmin.sicoes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "SICOES_TC_REQ_INFORME")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Informe extends BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SICOES_SEQ_REQ_INFORME_GEN")
    @SequenceGenerator(name = "SICOES_SEQ_REQ_INFORME_GEN", sequenceName = "SICOES_SEQ_REQ_INFORME", allocationSize = 1)
    @Column(name = "ID_REQ_INFORME")
    private Long idReqInforme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_REQUERIMIENTO")
    private Requerimiento requerimiento;

    @Column(name = "IP_CREACION")
    private String ipCreacion;

    @Column(name = "IP_ACTUALIZACION")
    private String ipActualizacion;

}
