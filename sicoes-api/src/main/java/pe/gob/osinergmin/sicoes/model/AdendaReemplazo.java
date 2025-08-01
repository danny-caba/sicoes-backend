package pe.gob.osinergmin.sicoes.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name="SICOES_TZ_ADENDA_REEMP")
public class AdendaReemplazo extends BaseModel implements Serializable {
    public static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_TZ_ADENDA_REEMP")
    @SequenceGenerator(name="GEN_SICOES_TZ_ADENDA_REEMP", sequenceName = "SICOES_SEQ_ID_ADENDA_REEMP", allocationSize = 1)
    @Column(name = "ID_ADENDA")
    private Long idAdenda;

    @Column(name = "ID_REEMPLAZO_PERSONAL")
    private Long idReemplazoPersonal;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CO_ESTADO_APROBACION")
    private ListadoDetalle estadoAprobacion;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CO_ESTADO_APR_LOGISTICA")
    private ListadoDetalle estadoAprLogistica;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CO_ESTADO_VB_GAF")
    private ListadoDetalle estadoVbGaf;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CO_ESTADO_FIRMA_JEFE")
    private ListadoDetalle estadoFirmaJefe;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CO_ESTADO_FIRMA_GRENCIA")
    private ListadoDetalle estadoFirmaGerencia;

    @Column(name = "ID_DOCUMENTO")
    private Long idDocumento;
}
