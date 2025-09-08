package pe.gob.osinergmin.sicoes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class HistorialAprobReemp {
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "TIPO_APROBADOR")
    private String tipoAprobador;
    @Column(name = "GRUPO")
    private String grupo;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "FECHA_DESIGNACION")
    private Date fechaDesignacion;
    @Column(name = "APROBADOR")
    private String aprobador;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "FECHA_APROBACION")
    private Date fechaAprobacion;
    @Column(name = "RESULTADO")
    private String resultado;
    @Column(name = "OBSERVACION")
    private String observacion;
}