package pe.gob.osinergmin.sicoes.util;

import java.util.Date;
import java.util.List;

public class DatosExportacion {

	
	private Long descuentoRegistros;
	
	private Date fecha;
	
	private String titulo;

	private String subtitulo;
	
	private String[][] filtros;
	
	private String[] nombreTitulos;
	
	private FormatoElemento [] formatoTitulos;
	
	private String usuario;
	
	private List<Object[]> listado;
	
	private String nombreHoja;

	public Long getDescuentoRegistros() {
		return descuentoRegistros;
	}

	public void setDescuentoRegistros(Long descuentoRegistros) {
		this.descuentoRegistros = descuentoRegistros;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getSubtitulo() {
		return subtitulo;
	}

	public void setSubtitulo(String subtitulo) {
		this.subtitulo = subtitulo;
	}

	public String[][] getFiltros() {
		return filtros;
	}

	public void setFiltros(String[][] filtros) {
		this.filtros = filtros;
	}

	public String[] getNombreTitulos() {
		return nombreTitulos;
	}

	public void setNombreTitulos(String[] nombreTitulos) {
		this.nombreTitulos = nombreTitulos;
	}

	public FormatoElemento[] getFormatoTitulos() {
		return formatoTitulos;
	}

	public void setFormatoTitulos(FormatoElemento[] formatoTitulos) {
		this.formatoTitulos = formatoTitulos;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<Object[]> getListado() {
		return listado;
	}

	public void setListado(List<Object[]> listado) {
		this.listado = listado;
	}

	public String getNombreHoja() {
		return nombreHoja;
	}

	public void setNombreHoja(String nombreHoja) {
		this.nombreHoja = nombreHoja;
	}
	
	
	
}
