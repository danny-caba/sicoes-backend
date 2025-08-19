package pe.gob.osinergmin.sicoes.util.bean;

public class PartidaRegistralRO extends BaseOutRO{


	private static final long serialVersionUID = 1L;
	
	private String zona;
	private String oficina;
	private String partida;
	private String tipo;
	private String denominacion;
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public String getOficina() {
		return oficina;
	}
	public void setOficina(String oficina) {
		this.oficina = oficina;
	}
	public String getPartida() {
		return partida;
	}
	public void setPartida(String partida) {
		this.partida = partida;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDenominacion() {
		return denominacion;
	}
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	@Override
	public String toString() {
		return "PartidaRegistralRO [zona=" + zona + ", oficina=" + oficina + ", partida=" + partida + ", tipo=" + tipo
				+ ", denominacion=" + denominacion + "]";
	}

	
	

}
