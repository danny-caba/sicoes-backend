package pe.gob.osinergmin.sicoes.model.dto;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;

public class InfoSectorActividadDTO {
	private ListadoDetalle subSector;
	private ListadoDetalle actividadArea;
	
	public InfoSectorActividadDTO() {
		
	}
	
	public InfoSectorActividadDTO(ListadoDetalle subSector, ListadoDetalle actividadArea) {
		this.subSector = subSector;
		this.actividadArea = actividadArea;
	}
	
	public ListadoDetalle getSubSector() {
		return subSector;
	}
	public void setSubSector(ListadoDetalle subSector) {
		this.subSector = subSector;
	}
	public ListadoDetalle getActividadArea() {
		return actividadArea;
	}
	public void setActividadArea(ListadoDetalle actividadArea) {
		this.actividadArea = actividadArea;
	}
	
	
	
}
