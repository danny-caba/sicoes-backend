package pe.gob.osinergmin.sicoes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "SICOES_TZ_DOCUMENTO_REEMP")
public class DocumentoReemplazo extends BaseModel implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ID_DOCUMENTO_REEMP")
    @SequenceGenerator(name = "SEQ_ID_DOCUMENTO_REEMP", sequenceName = "SICOES_SEQ_ID_DOCUMENTO_REEMP", allocationSize = 1)
    @Column(name = "ID_DOCUMENTO")
    private Long idDocumento;

    @Column(name = "ID_SECCION")
    private Long idSeccion;

    @Column(name = "ID_TIPO_DOCUMENTO")
    private String idTipoDocumento;

    @Column(name = "NU_CORRELATIVO")
    private Long nuCorrelativo;

    @Column(name = "ID_REEMPLAZO_PERSONAL")
    private Long idReemplazoPersonal;

    @Column(name = "DE_NOMBRE_DOCUMENTO")
    private String deNombreDocumento;

    @Column(name = "ID_DOCUMENTO_SIGED")
    private String idDocumentoSiged;

    @Column(name = "ID_ARCHIVO_SIGED")
    private String idArchivoSiged;

    @Column(name = "FE_FECHA_INICIO_VALIDEZ")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date feFechaInicioValidez;

    @Column(name = "FE_FECHA_FIN_VALIDEZ")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date feFechaFinValidez;

    @Column(name = "FE_FECHA_REGISTRO")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date feFechaRegistro;

    @Column(name = "FE_FECHA_INI_CONTRATO")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date feFechaIniContrato;

    @Transient
    private Archivo archivo;
}
