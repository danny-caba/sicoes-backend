package pe.gob.osinergmin.sicoes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class DocumentoInicioServ {
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "ID_TIPO_DOCUMENTO")
    private Long idTipoDocumento;
    @Column(name = "TIPO_DOCUMENTO")
    private String tipoDocumento;
    @Column(name = "NUMERO_CORRELATIVO")
    private String numeroCorrelativo;
    @Column(name = "NOMBRE_DOCUMENTO")
    private String nombreDocumento;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FECHA_INICIO_VALIDEZ")
    private Date fechaInicioValidez;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FECHA_FIN_VALIDEZ")
    private Date fechaFinValidez;
    @Column(name = "USUARIO")
    private String usuario;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FECHA")
    private Date fecha;
}