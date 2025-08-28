package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import java.util.List;

/**
 * DTO para requerimiento de renovación aprobado.
 */
public class RequerimientoRenovacionAprobacionResponseDTO {
    /** Identificador del requerimiento de renovación */
    private Long idRequerimientoRenovacion;
    /** Lista de informes aprobados */
    private List<InformeAprobacionResponseDTO> informes;

    // Getters y Setters
    public Long getIdRequerimientoRenovacion() { return idRequerimientoRenovacion; }
    public void setIdRequerimientoRenovacion(Long idRequerimientoRenovacion) { this.idRequerimientoRenovacion = idRequerimientoRenovacion; }
    public List<InformeAprobacionResponseDTO> getInformes() { return informes; }
    public void setInformes(List<InformeAprobacionResponseDTO> informes) { this.informes = informes; }
}
