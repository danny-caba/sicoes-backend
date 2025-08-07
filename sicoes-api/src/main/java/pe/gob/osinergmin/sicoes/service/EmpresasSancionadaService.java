package pe.gob.osinergmin.sicoes.service;

import java.util.List;
import java.util.Map;

public interface EmpresasSancionadaService {

	public String validarEmpresaSancionada(String ruc);

	public String validadSancion(String codigoRuc);

	public List<String[]> validadSancionV2(String codigoRuc);

	public String validadSancionPersonNatural(String documento);

	public String validadSancionPersonNaturalFec(String documento);

	public Map<String, String> validadSancionPersonNaturalV2(String documento);

	String validarVinculoLaboral (String numeroDocumento);
}
