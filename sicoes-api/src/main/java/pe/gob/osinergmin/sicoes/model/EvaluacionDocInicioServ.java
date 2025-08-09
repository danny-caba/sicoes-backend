package pe.gob.osinergmin.sicoes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
public class EvaluacionDocInicioServ {
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "NUMERO_DOCUMENTO")
    private String numeroDocumento;
    @Column(name = "ID_PERSONA")
    private Long idPersona;
    @Column(name = "PERSONA")
    private String persona;
    @Column(name = "PERFIL")
    private String perfil;
    @Column(name = "NU_DOCS")
    private Integer nuDocs;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FECHA_INICIO_CONTRATO")
    private Date fechaInicioContrato;
};