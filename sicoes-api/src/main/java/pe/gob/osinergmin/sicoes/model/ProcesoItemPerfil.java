package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;


/**
 * The persistent class for the listado database table.
 * 
 */
@Entity
@Table(name="SICOES_TR_PROCESO_ITEM_PERFIL")
public class ProcesoItemPerfil extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_PROCESO_ITEM_PERFIL")
	@SequenceGenerator(name="GEN_SICOES_SEQ_PROCESO_ITEM_PERFIL", sequenceName = "SICOES_SEQ_PROCESO_ITEM_PERFIL", allocationSize = 1)
	@Column(name = "ID_PROCESO_ITEM_PERFIL")	
	private Long idProcesoItemPerfil;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROCESO_ITEM")
	private ProcesoItem procesoItem;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SECTOR_LD")	
	private ListadoDetalle sector;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SUBSECTOR_LD")	
	private ListadoDetalle subsector;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERFIL_LD")	
	private ListadoDetalle perfil;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_LD")	
	private ListadoDetalle estado;
	
	@Column(name="NRO_PROFESIONALES")
	private Long nroProfesionales;

	public Long getIdProcesoItemPerfil() {
		return idProcesoItemPerfil;
	}

	public ProcesoItem getProcesoItem() {
		return procesoItem;
	}

	public ListadoDetalle getSector() {
		return sector;
	}

	public ListadoDetalle getSubsector() {
		return subsector;
	}

	public ListadoDetalle getPerfil() {
		return perfil;
	}

	public ListadoDetalle getEstado() {
		return estado;
	}

	public Long getNroProfesionales() {
		return nroProfesionales;
	}

	public void setIdProcesoItemPerfil(Long idProcesoItemPerfil) {
		this.idProcesoItemPerfil = idProcesoItemPerfil;
	}

	public void setProcesoItem(ProcesoItem procesoItem) {
		this.procesoItem = procesoItem;
	}

	public void setSector(ListadoDetalle sector) {
		this.sector = sector;
	}

	public void setSubsector(ListadoDetalle subsector) {
		this.subsector = subsector;
	}

	public void setPerfil(ListadoDetalle perfil) {
		this.perfil = perfil;
	}

	public void setEstado(ListadoDetalle estado) {
		this.estado = estado;
	}

	public void setNroProfesionales(Long nroProfesionales) {
		this.nroProfesionales = nroProfesionales;
	}

	@Override
	public String toString() {
		return "ProcesoItemPerfil [idProcesoItemPerfil=" + idProcesoItemPerfil + ", procesoItem=" + procesoItem
				+ ", sector=" + sector + ", subsector=" + subsector + ", perfil=" + perfil + ", estado=" + estado
				+ ", nroProfesionales=" + nroProfesionales + "]";
	}

}
	
	
	
