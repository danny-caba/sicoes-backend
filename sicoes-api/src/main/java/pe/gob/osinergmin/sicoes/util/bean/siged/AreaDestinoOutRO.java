package pe.gob.osinergmin.sicoes.util.bean.siged;

public class AreaDestinoOutRO {
	
	private Integer idUnidad;
	private String nombreUnidad;
	private Integer idProceso;
	private String nombreProceso;
	
	public AreaDestinoOutRO(){
	}
	
	public AreaDestinoOutRO(Integer idUnidad, String nombreUnidad, Integer idProceso, String nombreProceso) {
		super();
		this.idUnidad = idUnidad;
		this.nombreUnidad = nombreUnidad;
		this.idProceso = idProceso;
		this.nombreProceso = nombreProceso;
	}
	
	public Integer getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}
	public String getNombreUnidad() {
		return nombreUnidad;
	}
	public void setNombreUnidad(String nombreUnidad) {
		this.nombreUnidad = nombreUnidad;
	}
	public Integer getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(Integer idProceso) {
		this.idProceso = idProceso;
	}
	public String getNombreProceso() {
		return nombreProceso;
	}
	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}

}
