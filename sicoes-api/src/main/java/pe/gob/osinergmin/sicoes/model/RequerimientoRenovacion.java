package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "SICOES_TC_REQ_RENOVACION", schema = "ES_SICOES")
@Getter
@Setter
public class RequerimientoRenovacion extends BaseModel implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_req_renovacion")
    @SequenceGenerator(name = "seq_req_renovacion", sequenceName = "SEQ_SICOES_TC_REQ_RENOVACION", allocationSize = 1)
    @Column(name = "ID_REQ_RENOVACION", nullable = false)
    private Long idReqRenovacion;

    // @ManyToOne
    // @JoinColumn(name = "ID_SOLI_PERF_CONT", referencedColumnName = "ID_SOLI_PERF_CONT")
    // private SolicitudPerfeccionamiento solicitudPerfeccionamiento;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    private Usuario usuario;

    @Column(name = "NU_EXPEDIENTE", nullable = false, length = 50)
    private String numeroExpediente;

    @Column(name = "TI_SECTOR", nullable = false, length = 100)
    private String sector;

    @Column(name = "TI_SUB_SECTOR", nullable = false, length = 100)
    private String subSector;

    @Column(name = "NO_ITEM", nullable = false, length = 100)
    private String item;

    @Column(name = "FE_REGISTRO", nullable = false)
    private Date fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "ES_REQ_RENOVACION", referencedColumnName = "ID_LISTADO_DETALLE", nullable = false)
    private ListadoDetalle estadoRequerimiento;

    @ManyToOne
    @JoinColumn(name = "ES_APROBACION_INFORME", referencedColumnName = "ID_LISTADO_DETALLE", nullable = false)
    private ListadoDetalle estadoAprobacionInforme;

    @ManyToOne
    @JoinColumn(name = "ES_APROBACION_GPPM", referencedColumnName = "ID_LISTADO_DETALLE", nullable = false)
    private ListadoDetalle estadoAprobacionGPPM;

    @ManyToOne
    @JoinColumn(name = "ES_APROBACION_GSE", referencedColumnName = "ID_LISTADO_DETALLE", nullable = false)
    private ListadoDetalle estadoAprobacionGSE;

    @Column(name = "DE_OBSERVACION", length = 500)
    private String observacion;

    @Column(name = "ES_REGISTRO", nullable = false, length = 1)
    private String registro;

}
