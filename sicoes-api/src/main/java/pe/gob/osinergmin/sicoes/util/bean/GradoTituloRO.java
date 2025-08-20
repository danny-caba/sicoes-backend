package pe.gob.osinergmin.sicoes.util.bean;

public class GradoTituloRO extends BaseOutRO{


	private static final long serialVersionUID = 1L;
	
	private String abreviaturaTitulo;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String especialidad;
	private String tipoDocumento;
	private String numeroDocumento;
	private String pais;
	private String tituloProfesional;
	private String universidad;
	private String tipoInstitucion;
	private String tipoGestion;
	private String fechaEmision;
	private String resolucion;
	private String fechaResolucion;
	public String getAbreviaturaTitulo() {
		return abreviaturaTitulo;
	}
	public void setAbreviaturaTitulo(String abreviaturaTitulo) {
		this.abreviaturaTitulo = abreviaturaTitulo;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getTituloProfesional() {
		return tituloProfesional;
	}
	public void setTituloProfesional(String tituloProfesional) {
		this.tituloProfesional = tituloProfesional;
	}
	public String getUniversidad() {
		return universidad;
	}
	public void setUniversidad(String universidad) {
		this.universidad = universidad;
	}
	public String getTipoInstitucion() {
		return tipoInstitucion;
	}
	public void setTipoInstitucion(String tipoInstitucion) {
		this.tipoInstitucion = tipoInstitucion;
	}
	public String getTipoGestion() {
		return tipoGestion;
	}
	public void setTipoGestion(String tipoGestion) {
		this.tipoGestion = tipoGestion;
	}
	public String getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public String getResolucion() {
		return resolucion;
	}
	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}
	public String getFechaResolucion() {
		return fechaResolucion;
	}
	public void setFechaResolucion(String fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}
	@Override
	public String toString() {
		return "GradoTitulo [abreviaturaTitulo=" + abreviaturaTitulo + ", nombres=" + nombres + ", apellidoPaterno="
				+ apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", especialidad=" + especialidad
				+ ", tipoDocumento=" + tipoDocumento + ", numeroDocumento=" + numeroDocumento + ", pais=" + pais
				+ ", tituloProfesional=" + tituloProfesional + ", universidad=" + universidad + ", tipoInstitucion="
				+ tipoInstitucion + ", tipoGestion=" + tipoGestion + ", fechaEmision=" + fechaEmision + ", resolucion="
				+ resolucion + ", fechaResolucion=" + fechaResolucion + "]";
	}
	
	
}
