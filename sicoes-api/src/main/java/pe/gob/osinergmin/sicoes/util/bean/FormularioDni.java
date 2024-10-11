package pe.gob.osinergmin.sicoes.util.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormularioDni implements Serializable {
	private String dni;
	private String tipoConsulta = "1";
	private String reservado = "";
	private String dniUsuarioReniec;
	private String sedeOsinergmin = "GSTI";
	private String hostOsinergmin = "GSTI";
	private String caracterVerificacion;
	private String fechaEmision;
	private String fechaNacimiento;
	private boolean valido;
	
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getCaracterVerificacion() {
		return caracterVerificacion;
	}
	public void setCaracterVerificacion(String caracterVerificacion) {
		this.caracterVerificacion = caracterVerificacion;
	}
	
	public String getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public boolean isValido() {
		return valido;
	}
	public void setValido(boolean valido) {
		this.valido = valido;
	}
	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getTipoConsulta() {
		return tipoConsulta;
	}
	public void setTipoConsulta(String tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}
	public String getReservado() {
		return reservado;
	}
	public void setReservado(String reservado) {
		this.reservado = reservado;
	}
	public String getDniUsuarioReniec() {
		return dniUsuarioReniec;
	}
	public void setDniUsuarioReniec(String dniUsuarioReniec) {
		this.dniUsuarioReniec = dniUsuarioReniec;
	}
	public String getSedeOsinergmin() {
		return sedeOsinergmin;
	}
	public void setSedeOsinergmin(String sedeOsinergmin) {
		this.sedeOsinergmin = sedeOsinergmin;
	}
	public String getHostOsinergmin() {
		return hostOsinergmin;
	}
	public void setHostOsinergmin(String hostOsinergmin) {
		this.hostOsinergmin = hostOsinergmin;
	}
	public String fechaFormat(String fecha) {
		LocalDate aLD = LocalDate.parse(fecha);
		DateTimeFormatter dTF = DateTimeFormatter.ofPattern("yyyyMMdd");
		return dTF.format(aLD);
	}
	
	public boolean esUsuarioValido(Datos datos) {
		boolean esValido = true;
		if(!caracterVerificacion.equalsIgnoreCase(datos.getCaracterVerificacion())) {
			esValido = false;
		}
		
		if(!fechaFormat(fechaEmision).equalsIgnoreCase(datos.getFechaEmision())) {
			esValido = false;
		}
		
		if(!fechaFormat(fechaNacimiento).equalsIgnoreCase(datos.getFechaNacimiento())) {
			esValido = false;
		}
		
		return esValido;
	}
	@Override
	public String toString() {
		return "FormularioDni [dni=" + dni + ", tipoConsulta=" + tipoConsulta + ", reservado=" + reservado
				+ ", dniUsuarioReniec=" + dniUsuarioReniec + ", sedeOsinergmin=" + sedeOsinergmin + ", hostOsinergmin="
				+ hostOsinergmin + ", caracterVerificacion=" + caracterVerificacion + ", fechaEmision="
				+ fechaEmision + ", fechaNacimiento=" + fechaNacimiento + ", valido=" + valido + "]";
	}
		
}