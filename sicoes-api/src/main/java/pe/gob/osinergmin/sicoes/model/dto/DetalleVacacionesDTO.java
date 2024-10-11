package pe.gob.osinergmin.sicoes.model.dto;


public class DetalleVacacionesDTO {
	
	private Long id;
	
	private String dniEmpleado;
	
	private String usuarioSaliente;
	
	private String apellidos;
	
	private String nombres;
	
	private String fechaInicioAusencia;
	
	private String fechaFinAusencia;
	
	private String motivoAusencia;
	
	private Long codigoUsuarioReemplazo;
	
	private String fechaInicioReemplazo;
	
	private String fechaFinReemplazo;
	
	private String documentoReemplazo;
	
	private String usuarioReemplazo;
	
	private String apellidosReemplazo;
	
	private String nombresReemplazo;
	
	private String nombreCompletoReemplazo;
	
	private String fechaConsulta;
	
	private Integer codigoConsulta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDniEmpleado() {
		return dniEmpleado;
	}

	public void setDniEmpleado(String dniEmpleado) {
		this.dniEmpleado = dniEmpleado;
	}

	public String getUsuarioSaliente() {
		return usuarioSaliente;
	}

	public void setUsuarioSaliente(String usuarioSaliente) {
		this.usuarioSaliente = usuarioSaliente;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getFechaInicioAusencia() {
		return fechaInicioAusencia;
	}

	public void setFechaInicioAusencia(String fechaInicioAusencia) {
		this.fechaInicioAusencia = fechaInicioAusencia;
	}

	public String getFechaFinAusencia() {
		return fechaFinAusencia;
	}

	public void setFechaFinAusencia(String fechaFinAusencia) {
		this.fechaFinAusencia = fechaFinAusencia;
	}

	public String getMotivoAusencia() {
		return motivoAusencia;
	}

	public void setMotivoAusencia(String motivoAusencia) {
		this.motivoAusencia = motivoAusencia;
	}

	public Long getCodigoUsuarioReemplazo() {
		return codigoUsuarioReemplazo;
	}

	public void setCodigoUsuarioReemplazo(Long codigoUsuarioReemplazo) {
		this.codigoUsuarioReemplazo = codigoUsuarioReemplazo;
	}

	public String getDocumentoReemplazo() {
		return documentoReemplazo;
	}

	public void setDocumentoReemplazo(String documentoReemplazo) {
		this.documentoReemplazo = documentoReemplazo;
	}

	public String getUsuarioReemplazo() {
		return usuarioReemplazo;
	}

	public void setUsuarioReemplazo(String usuarioReemplazo) {
		this.usuarioReemplazo = usuarioReemplazo;
	}

	public String getApellidosReemplazo() {
		return apellidosReemplazo;
	}

	public void setApellidosReemplazo(String apellidosReemplazo) {
		this.apellidosReemplazo = apellidosReemplazo;
	}

	public String getNombresReemplazo() {
		return nombresReemplazo;
	}

	public void setNombresReemplazo(String nombresReemplazo) {
		this.nombresReemplazo = nombresReemplazo;
	}

	public String getFechaInicioReemplazo() {
		return fechaInicioReemplazo;
	}

	public void setFechaInicioReemplazo(String fechaInicioReemplazo) {
		this.fechaInicioReemplazo = fechaInicioReemplazo;
	}

	public String getFechaFinReemplazo() {
		return fechaFinReemplazo;
	}

	public void setFechaFinReemplazo(String fechaFinReemplazo) {
		this.fechaFinReemplazo = fechaFinReemplazo;
	}

	public String getNombreCompletoReemplazo() {
		return nombreCompletoReemplazo;
	}

	public void setNombreCompletoReemplazo(String nombreCompletoReemplazo) {
		this.nombreCompletoReemplazo = nombreCompletoReemplazo;
	}

	public String getFechaConsulta() {
		return fechaConsulta;
	}

	public void setFechaConsulta(String fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}

	public Integer getCodigoConsulta() {
		return codigoConsulta;
	}

	public void setCodigoConsulta(Integer codigoConsulta) {
		this.codigoConsulta = codigoConsulta;
	}

	@Override
	public String toString() {
		return "DetalleVacacionesDTO [id=" + id + ", dniEmpleado=" + dniEmpleado + ", usuarioSaliente="
				+ usuarioSaliente + ", apellidos=" + apellidos + ", nombres=" + nombres + ", fechaInicioAusencia="
				+ fechaInicioAusencia + ", fechaFinAusencia=" + fechaFinAusencia + ", motivoAusencia=" + motivoAusencia
				+ ", codigoUsuarioReemplazo=" + codigoUsuarioReemplazo + ", fechaInicioReemplazo="
				+ fechaInicioReemplazo + ", fechaFinReemplazo=" + fechaFinReemplazo + ", documentoReemplazo="
				+ documentoReemplazo + ", usuarioReemplazo=" + usuarioReemplazo + ", apellidosReemplazo="
				+ apellidosReemplazo + ", nombresReemplazo=" + nombresReemplazo + ", nombreCompletoReemplazo="
				+ nombreCompletoReemplazo + ", fechaConsulta=" + fechaConsulta + ", codigoConsulta=" + codigoConsulta
				+ "]";
	}
}