package pe.gob.osinergmin.sicoes.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "SICOES_TM_UBIGEO")
public class Ubigeo implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_UBIGEO")
	private Long codigoUbigeo;
	
	@Column(name = "CO_UBIGEO")
	private String codigo;
	
	@Column(name = "CO_DEPARTAMENTO")
	private String codigoDepartamento;
	
	@Column(name = "NO_DEPARTAMENTO")
	private String nombreDepartamento;
	
	@Column(name = "CO_PROVINCIA")
	private String codigoProvincia;
	
	@Column(name = "NO_PROVINCIA")
	private String nombreProvincia;
	
	@Column(name = "CO_DISTRITO")
	private String codigoDistrito;
	
	@Column(name = "NO_DISTRITO")
	private String nombreDistrito;
	
	@Transient
	private String nombre;

	public Long getCodigoUbigeo() {
		return codigoUbigeo;
	}

	public void setCodigoUbigeo(Long codigoUbigeo) {
		this.codigoUbigeo = codigoUbigeo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigoDepartamento() {
		return codigoDepartamento;
	}

	public void setCodigoDepartamento(String codigoDepartamento) {
		this.codigoDepartamento = codigoDepartamento;
	}

	public String getNombreDepartamento() {
		return nombreDepartamento;
	}

	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}

	public String getCodigoProvincia() {
		return codigoProvincia;
	}

	public void setCodigoProvincia(String codigoProvincia) {
		this.codigoProvincia = codigoProvincia;
	}

	public String getNombreProvincia() {
		return nombreProvincia;
	}

	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}

	public String getCodigoDistrito() {
		return codigoDistrito;
	}

	public void setCodigoDistrito(String codigoDistrito) {
		this.codigoDistrito = codigoDistrito;
	}

	public String getNombreDistrito() {
		return nombreDistrito;
	}

	public void setNombreDistrito(String nombreDistrito) {
		this.nombreDistrito = nombreDistrito;
	}

	@Override
	public String toString() {
		return "Ubigeo [codigoUbigeo=" + codigoUbigeo + ", codigo=" + codigo + ", codigoDepartamento="
				+ codigoDepartamento + ", nombreDepartamento=" + nombreDepartamento + ", codigoProvincia="
				+ codigoProvincia + ", nombreProvincia=" + nombreProvincia + ", codigoDistrito=" + codigoDistrito
				+ ", nombreDistrito=" + nombreDistrito + "]";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	
}