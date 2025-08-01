package pe.gob.osinergmin.sicoes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name="SICOES_TZ_ADENDA_REEMP")
public class Adenda extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_ID_ADENDA_REEMP")
    @SequenceGenerator(name = "GEN_SICOES_SEQ_ID_ADENDA_REEMP", sequenceName = "SICOES_SEQ_ID_ADENDA_REEMP", allocationSize = 1)
    @Column(name = "ID_ADENDA")
    private Long idAdenda;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_REEMPLAZO_PERSONAL")
    private PersonalReemplazo remplazoPersonal;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CO_ESTADO_APROBACION")
    private ListadoDetalle coEstadoAprobacion;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CO_ESTADO_APR_LOGISTICA")
    private ListadoDetalle estadoAprobacionLogistica;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CO_ESTADO_VB_GAF")
    private ListadoDetalle estadoVbGAF;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CO_ESTADO_FIRMA_JEFE")
    private ListadoDetalle estadoFirmaJefe;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CO_ESTADO_FIRMA_GERENCIA")
    private ListadoDetalle estadoFirmaGerencia;

    @Column(name = "ID_DOCUMENTO")
    private Long idDocumento;

}