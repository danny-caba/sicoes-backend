package pe.gob.osinergmin.sicoes.util;

public enum Estudios {

	ESTUDIO("academicos", Constantes.LISTADO.TIPO_ESTUDIO.ACADEMICOS),
	CAPACITACION("capacitaciones", Constantes.LISTADO.TIPO_ESTUDIO.CAPACITACION),;

	private String path;
	private String codigo;
	
	private Estudios(String path, String codigo) {
		this.path = path;
		this.codigo = codigo;
	}
	
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public static String getCodigoByPath(String path) {
		for (Estudios e : Estudios.values()) {
			if(path.equals(e.getPath())) {
				return e.getCodigo();
			}
		}
		return null;
	}
	
	
}
