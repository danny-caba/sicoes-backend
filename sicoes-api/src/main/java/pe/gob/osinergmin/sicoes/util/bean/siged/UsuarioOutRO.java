package pe.gob.osinergmin.sicoes.util.bean.siged;

import java.io.Serializable;
import java.math.BigInteger;

public class UsuarioOutRO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private BigInteger idUsuario;
	private String nombres;
	
	public BigInteger getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(BigInteger idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	
}
