package pe.gob.osinergmin.sicoes.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class FiltroRequerimientoDTO {
    private Long division;
    private Long perfil;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaInicio;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaFin;
    private Long supervisora;
    private Long estadoAprobacion;
}
