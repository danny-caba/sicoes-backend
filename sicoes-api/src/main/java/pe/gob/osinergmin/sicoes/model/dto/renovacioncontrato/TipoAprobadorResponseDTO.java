package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para identificar el tipo de aprobador del usuario
 */
@Getter
@Setter
public class TipoAprobadorResponseDTO {
    
    private Long idUsuario;
    private String tipoAprobador; // G1, G2, G3_GPPM, G3_GSE, EVALUADOR
    private String codigoGrupoAprobacion; // JEFE_UNIDAD, GERENTE, GPPM, GSE
    private String descripcion;
    private boolean esAprobadorG1;
    private boolean esAprobadorG2;
    private boolean esAprobadorG3;
    private boolean esEvaluador;
    
    public TipoAprobadorResponseDTO() {
    }
    
    public TipoAprobadorResponseDTO(Long idUsuario, String tipoAprobador, String codigoGrupoAprobacion) {
        this.idUsuario = idUsuario;
        this.tipoAprobador = tipoAprobador;
        this.codigoGrupoAprobacion = codigoGrupoAprobacion;
        
        // Configurar flags booleanos
        this.esAprobadorG1 = "G1".equals(tipoAprobador);
        this.esAprobadorG2 = "G2".equals(tipoAprobador);
        this.esAprobadorG3 = tipoAprobador != null && tipoAprobador.startsWith("G3");
        this.esEvaluador = "EVALUADOR".equals(tipoAprobador);
        
        // Configurar descripción
        switch (tipoAprobador != null ? tipoAprobador : "") {
            case "G1":
                this.descripcion = "Aprobador Técnico G1 - Jefe de Unidad";
                break;
            case "G2":
                this.descripcion = "Aprobador Técnico G2 - Gerente";
                break;
            case "G3_GPPM":
                this.descripcion = "Aprobador Técnico G3 - GPPM";
                break;
            case "G3_GSE":
                this.descripcion = "Aprobador Técnico G3 - GSE";
                break;
            case "EVALUADOR":
                this.descripcion = "Evaluador de Contratos";
                break;
            default:
                this.descripcion = "Tipo de aprobador no identificado";
        }
    }
}