package pe.gob.osinergmin.sicoes.util.bean.sne;

import pe.gob.osinergmin.sicoes.util.bean.BaseOutRO;

public class AfiliacionOutRO extends BaseOutRO {

	private static final long serialVersionUID = 1L;
	private String correoAfiliado;
	
	private String tipoAfiliacion;
	private String fechaActivacion;
	
	public String getCorreoAfiliado() {
		return correoAfiliado;
	}
	public void setCorreoAfiliado(String correoAfiliado) {
		this.correoAfiliado = correoAfiliado;
	}
	public String getTipoAfiliacion() {
		return tipoAfiliacion;
	}
	public void setTipoAfiliacion(String tipoAfiliacion) {
		this.tipoAfiliacion = tipoAfiliacion;
	}
	public String getFechaActivacion() {
		return fechaActivacion;
	}
	public void setFechaActivacion(String fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}
	
}