package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import java.time.LocalDateTime;

public class HistorialAprobacionResponseDTO {
    
    private String tipo;
    private String grupo;
    private LocalDateTime fechaHoraDesignacion;
    private String aprobador;
    private LocalDateTime fechaHoraAprobacion;
    private String resultado;
    private String observacion;
    
    public HistorialAprobacionResponseDTO() {
    }
    
    public HistorialAprobacionResponseDTO(String tipo, String grupo, LocalDateTime fechaHoraDesignacion,
            String aprobador, LocalDateTime fechaHoraAprobacion, String resultado, String observacion) {
        this.tipo = tipo;
        this.grupo = grupo;
        this.fechaHoraDesignacion = fechaHoraDesignacion;
        this.aprobador = aprobador;
        this.fechaHoraAprobacion = fechaHoraAprobacion;
        this.resultado = resultado;
        this.observacion = observacion;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getGrupo() {
        return grupo;
    }
    
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
    
    public LocalDateTime getFechaHoraDesignacion() {
        return fechaHoraDesignacion;
    }
    
    public void setFechaHoraDesignacion(LocalDateTime fechaHoraDesignacion) {
        this.fechaHoraDesignacion = fechaHoraDesignacion;
    }
    
    public String getAprobador() {
        return aprobador;
    }
    
    public void setAprobador(String aprobador) {
        this.aprobador = aprobador;
    }
    
    public LocalDateTime getFechaHoraAprobacion() {
        return fechaHoraAprobacion;
    }
    
    public void setFechaHoraAprobacion(LocalDateTime fechaHoraAprobacion) {
        this.fechaHoraAprobacion = fechaHoraAprobacion;
    }
    
    public String getResultado() {
        return resultado;
    }
    
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    
    public String getObservacion() {
        return observacion;
    }
    
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}