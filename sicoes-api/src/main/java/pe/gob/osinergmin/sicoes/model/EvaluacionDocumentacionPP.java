package pe.gob.osinergmin.sicoes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class EvaluacionDocumentacionPP {
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
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FECHA_BAJA")
    private Date fechaBaja;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FECHA_FINALIZACION_CONTRATO")
    private Date fechaFinalizacionContrato;
};