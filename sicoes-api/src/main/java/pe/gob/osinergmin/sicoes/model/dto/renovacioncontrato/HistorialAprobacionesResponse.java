package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class HistorialAprobacionesResponse {
    
    private String status;
    private HistorialAprobacionesData data;
    
    public HistorialAprobacionesResponse() {
        this.status = "success";
    }
    
    public HistorialAprobacionesResponse(List<HistorialAprobacionResponseDTO> historialAprobaciones, 
                                        Integer totalRegistros, String documentoContratoId) {
        this.status = "success";
        this.data = new HistorialAprobacionesData(historialAprobaciones, totalRegistros, documentoContratoId);
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public HistorialAprobacionesData getData() {
        return data;
    }
    
    public void setData(HistorialAprobacionesData data) {
        this.data = data;
    }
    
    public static class HistorialAprobacionesData {
        
        @JsonProperty("historial_aprobaciones")
        private List<HistorialAprobacionResponseDTO> historialAprobaciones;
        
        @JsonProperty("total_registros")
        private Integer totalRegistros;
        
        @JsonProperty("documento_contrato_id")
        private String documentoContratoId;
        
        public HistorialAprobacionesData() {
        }
        
        public HistorialAprobacionesData(List<HistorialAprobacionResponseDTO> historialAprobaciones, 
                                        Integer totalRegistros, String documentoContratoId) {
            this.historialAprobaciones = historialAprobaciones;
            this.totalRegistros = totalRegistros;
            this.documentoContratoId = documentoContratoId;
        }
        
        public List<HistorialAprobacionResponseDTO> getHistorialAprobaciones() {
            return historialAprobaciones;
        }
        
        public void setHistorialAprobaciones(List<HistorialAprobacionResponseDTO> historialAprobaciones) {
            this.historialAprobaciones = historialAprobaciones;
        }
        
        public Integer getTotalRegistros() {
            return totalRegistros;
        }
        
        public void setTotalRegistros(Integer totalRegistros) {
            this.totalRegistros = totalRegistros;
        }
        
        public String getDocumentoContratoId() {
            return documentoContratoId;
        }
        
        public void setDocumentoContratoId(String documentoContratoId) {
            this.documentoContratoId = documentoContratoId;
        }
    }
}