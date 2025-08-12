package pe.gob.osinergmin.sicoes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class DocumentoPP {
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "ID_EVAL_DOCUMENTO")
    private Long idReemplazo;
    @Column(name = "ID_TIPO_DOCUMENTO")
    private Long idTipoDocumento;
    @Column(name = "TIPO_DOCUMENTO")
    private String tipoDocumento;
    @Column(name = "NOMBRE_DOCUMENTO")
    private String nombreDocumento;
    @Column(name = "CONFORMIDAD")
    private String conformidad;
    @Column(name = "ID_USUARIO_EVALUACION")
    private Long idUsuarioEvaluacion;
    @Column(name = "USUARIO_EVALUACION")
    private String usuarioEvaluacion;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FECHA_EVALUACION")
    private Date fechaEvaluacion;

}