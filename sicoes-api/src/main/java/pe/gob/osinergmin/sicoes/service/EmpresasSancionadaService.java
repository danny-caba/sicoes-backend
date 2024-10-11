package pe.gob.osinergmin.sicoes.service;

public interface EmpresasSancionadaService {

	public String validarEmpresaSancionada(String ruc);

	public String validadSancion(String codigoRuc);

	public String validadSancionPersonNatural(String documento);

	public String validadSancionPersonNaturalFec(String documento);
}
