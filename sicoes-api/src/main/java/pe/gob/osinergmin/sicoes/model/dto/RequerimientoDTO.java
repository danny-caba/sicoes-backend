package pe.gob.osinergmin.sicoes.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import pe.gob.osinergmin.sicoes.model.BaseModel;
import pe.gob.osinergmin.sicoes.model.Division;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequerimientoDTO extends BaseModel {

    private Long idRequerimiento;

    private String nuExpediente;

    private ListadoDetalle estado;

    private Division division;

    private ListadoDetalle perfil;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date feRegistro;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date fePlazoCargaDoc;

    private String deObservacion;

    private String nuSiaf;

    private String usCreacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date feCreacion;

    private String usActualizacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date feActualizacion;

}
