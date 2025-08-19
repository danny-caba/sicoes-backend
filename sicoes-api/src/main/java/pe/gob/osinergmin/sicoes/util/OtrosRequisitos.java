package pe.gob.osinergmin.sicoes.util;

public enum OtrosRequisitos {

	OTROS_REQUISITOS("otrosRequisitos", Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.OTRO_REQUISITO),
	//DOCUMENTO_ACREDITAN("documentoAcreditan", Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.ACREDITAN),
	PERFIL("perfil", Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL),;

	private String path;
	private String codigo;
	
	private OtrosRequisitos(String path, String codigo) {
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
		for (OtrosRequisitos e : OtrosRequisitos.values()) {
			if(path.equals(e.getPath())) {
				return e.getCodigo();
			}
		}
		return null;
	}
	
	
}
