package pe.gob.osinergmin.sicoes.util;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PageUtilImpl<T> extends PageImpl<T>{

	private static final long serialVersionUID = 1L;
	
	private Double totalMonto;
	private Integer dia;
	private Integer mes;
	private Integer anio;

	public PageUtilImpl(List<T> content) {
		super(content);
	}
	
	public PageUtilImpl(List<T> content, Pageable pageable, long total) {
		super(content, pageable,total);
	
	}

	public Double getTotalMonto() {
		return totalMonto;
	}

	public void setTotalMonto(Double totalMonto) {
		this.totalMonto = totalMonto;
	}

	public Integer getDia() {
		return dia;
	}

	public void setDia(Integer dia) {
		this.dia = dia;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	
}
