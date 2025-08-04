package pe.gob.osinergmin.sicoes.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FirmaRequestDTO {
    private Long idAdenda;
    private String observacion;
    private String cookie;
    private Boolean visto;
    private Boolean firmaJefe;
    private Boolean firmaGerente;
}
