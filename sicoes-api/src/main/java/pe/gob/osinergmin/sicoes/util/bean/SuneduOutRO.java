package pe.gob.osinergmin.sicoes.util.bean;

import java.util.List;

public class SuneduOutRO  extends BaseOutRO{

	private static final long serialVersionUID = 1L;
	
	private List<GradoTituloRO> gradosyTitulos;

	public List<GradoTituloRO> getGradosyTitulos() {
		return gradosyTitulos;
	}

	public void setGradosyTitulos(List<GradoTituloRO> gradosyTitulos) {
		this.gradosyTitulos = gradosyTitulos;
	}
}
