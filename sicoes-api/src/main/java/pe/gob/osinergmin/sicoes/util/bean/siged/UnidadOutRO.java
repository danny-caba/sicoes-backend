package pe.gob.osinergmin.sicoes.util.bean.siged;

import java.io.Serializable;
import java.math.BigInteger;

public class UnidadOutRO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private BigInteger cantidadProcesos;
	private BigInteger idUnidad;
	private String nombreUnidad;

	public BigInteger getCantidadProcesos() {
		return cantidadProcesos;
	}

	public void setCantidadProcesos(BigInteger cantidadProcesos) {
		this.cantidadProcesos = cantidadProcesos;
	}

	public BigInteger getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(BigInteger idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getNombreUnidad() {
		return nombreUnidad;
	}

	public void setNombreUnidad(String nombreUnidad) {
		this.nombreUnidad = nombreUnidad;
	}

}
