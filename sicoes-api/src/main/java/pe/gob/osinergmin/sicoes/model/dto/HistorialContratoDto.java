package pe.gob.osinergmin.sicoes.model.dto;

import java.time.LocalDateTime;

public class HistorialContratoDto {
    private String tipo;
    private LocalDateTime fechaRegistro;
    private String usuario;
    private LocalDateTime fechaAprobacion;
    private String resultado;
    private String observacion;

    public HistorialContratoDto(String tipo,
                                LocalDateTime fechaRegistro,
                                String usuario,
                                LocalDateTime fechaAprobacion,
                                String resultado,
                                String observacion) {
        this.tipo = tipo;
        this.fechaRegistro = fechaRegistro;
        this.usuario = usuario;
        this.fechaAprobacion = fechaAprobacion;
        this.resultado = resultado;
        this.observacion = observacion;
    }

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public LocalDateTime getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(LocalDateTime fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
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

