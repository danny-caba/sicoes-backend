package pe.gob.osinergmin.sicoes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class AprobacionReemp {
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "TIPO_APROBACION")
    private Long tipoAprobacion;
    @Column(name = "NOM_TIPO_APROBACION")
    private String nomTipoAprobacion;
    @Column(name = "NUMERO_EXPEDIENTE")
    private Long numeroExpediente;
    @Column(name = "ID_INFORME")
    private Long idInforme;
    @Column(name = "INFORME")
    private String informe;
    @Column(name = "TP")
    private String tp;
    @Column(name = "ID_CONTRATISTA")
    private Long idContratista;
    @Column(name = "CONTRATISTA")
    private String contratista;
    @Column(name = "ID_TIPO_SOLICITUD")
    private Long idTipoSolicitud;
    @Column(name = "TIPO_SOLICITUD")
    private String tipoSolictiud;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FECHA_INGRESO")
    private Date fechaIngreso;
    @Column(name = "ID_ES_APROB")
    private Long idEsAprob;
    @Column(name = "ES_APROB")
    private String esAprob;
    @Column(name = "ID_ES_APROB_DIV")
    private Long idEsAprobDiv;
    @Column(name = "ES_APROB_DIV")
    private String esAprobDiv;
    @Column(name = "ID_ES_APROB_LI")
    private Long idEsAprobLi;
    @Column(name = "ES_APROB_LI")
    private String esAprobLi;
    @Column(name = "ID_ESTADO_APROB_LOGISTICA")
    private Long idEstadoAprobLogistica;
    @Column(name = "ESTADO_APROB_LOGISTICA")
    private String estadoAprobLogistica;
    @Column(name = "ID_ESTADO_VISTO_BUENO_GAF")
    private Long idEstadoVistoBuenoGaf;
    @Column(name = "ESTADO_VISTO_BUENO_GAF")
    private String estadoVistoBuenoGaf;
    @Column(name = "ID_ESTADO_FIRMA_JEFE")
    private Long idEstadoFirmaJefe;
    @Column(name = "ESTADO_FIRMA_JEFE")
    private String estadoFirmaJefe;
    @Column(name = "ID_ESTADO_FIRMA_GERENCIA")
    private Long idEstadoFirmaGerencia;
    @Column(name = "ESTADO_FIRMA_GERENCIA")
    private String estadoFirmaGerencia;
}
