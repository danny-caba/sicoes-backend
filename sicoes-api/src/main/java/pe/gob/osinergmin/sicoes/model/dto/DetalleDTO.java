package pe.gob.osinergmin.sicoes.model.dto;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;

public class DetalleDTO {
    private ListadoDetalle sector;
    private Double montoFacturado;
    
	public ListadoDetalle getSector() {
		return sector;
	}
	public void setSector(ListadoDetalle sector) {
		this.sector = sector;
	}
	public Double getMontoFacturado() {
		return montoFacturado;
	}
	public void setMontoFacturado(Double montoFacturado) {
		this.montoFacturado = montoFacturado;
	}
    
}
