package pe.gob.osinergmin.sicoes.util.bean;

import java.util.List;

public class SunarpOutRO extends BaseOutRO{

	private static final long serialVersionUID = 1L;
	
	private List<PartidaRegistralRO> partidaRegistrales;

	public List<PartidaRegistralRO> getPartidaRegistrales() {
		return partidaRegistrales;
	}

	public void setPartidaRegistrales(List<PartidaRegistralRO> partidaRegistrales) {
		this.partidaRegistrales = partidaRegistrales;
	}


}

