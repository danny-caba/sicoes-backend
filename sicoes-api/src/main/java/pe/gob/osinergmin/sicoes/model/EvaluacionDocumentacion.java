package pe.gob.osinergmin.sicoes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class EvaluacionDocumentacion {
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "ID_SOLICITUD")
    private Long idSolicitud;
    @Column(name = "TIPO_DOCUMENTO")
    private String tipoDocumento;
    @Column(name = "NUMERO_DOCUMENTO")
    private String numeroDocumento;
    @Column(name = "ID_PERSONA")
    private Long idPersona;
    @Column(name = "PERSONA")
    private String persona;
    @Column(name = "PERFIL")
    private String perfil;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FECHA_REGISTRO")
    private Date fechaRegistro;
    @Column(name = "ID_ESTADO_EVAL_DOC_PERS_REEMP")
    private Long idEsEvalDocPersReemp;
    @Column(name = "ESTADO_EVAL_DOC_PERS_REEMP")
    private String esEvalDocPersReemp;
    @Column(name = "ID_ESTADO_APROBACION_INFORME")
    private Long idEsAprobInforme;
    @Column(name = "ESTADO_APROBACION_INFORME")
    private String esAprobInforme;
    @Column(name = "ID_ESTADO_APROBACION_ADENDA")
    private Long idEsAprobAdenda;
    @Column(name = "ESTADO_APROBACION_ADENDA")
    private String esAprobAdenda;
    @Column(name = "ID_ESTADO_EVAL_DOC_INI_SERV")
    private Long idEsEvalDocIniServ;
    @Column(name = "ESTADO_EVAL_DOC_INI_SERV")
    private String esEvalDocIniServ;
}