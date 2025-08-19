package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * The persistent class for the SICOES_TM_PACES database table.
 */
@Entity
@Table(name = "SICOES_TM_PACES", schema = "ES_SICOES")
public class Paces extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PACES", nullable = false)
    private Long idPaces;

    @Column(name = "ID_DIVISION")  
	private Long idDivision;

    @Column(name = "DE_MES")
    private Integer deMes;

    @Column(name = "DE_ITEM_PACES", length = 1000)
    private String deItemPaces;
   
    @Column(name = "DE_PRESUPUESTO", precision = 12, scale = 2)
    private Double dePresupuesto;

    @Column(name = "NO_CONVOCATORIA", length = 1000)
    private String noConvocatoria;

    @Column(name = "RE_PROGRAMA_ANUAL_SUPERVISION", length = 1000)
    private String reProgramaAnualSupervision;      

	@Column(name = "ID_APROBADOR_G2")  
   	private Long idAprobadorg2;
    
    @Column(name = "ID_APROBADOR_G3")  
   	private Long idAprobadorg3;
    
    @Column(name = "ELIMINADO_PACES")    
    private boolean eliminado;
      
    @Column(name = "ID_PROCESO")    
    private Long idProceso;	

	/*columnas que no pertenecen al modelo*/
    @Transient    
    private Long idTipoEstado;
    
    @Transient    
    private String observacion;
    @Transient    
    private String DetalleEstado;
  
        
    public Long getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}
	
    public String getDetalleEstado() {
		return DetalleEstado;
	}

	public void setDetalleEstado(String detalleEstado) {
		DetalleEstado = detalleEstado;
	}

	public boolean isEliminado() {
  		return eliminado;
  	}

  	public void setEliminado(boolean eliminado) {
  		this.eliminado = eliminado;
  	}
  
    public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	// Getters and Setters
    public Long getIdPaces() {
        return idPaces;
    }

    public void setIdPaces(Long idPaces) {
        this.idPaces = idPaces;
    }

    public Long getDivision() {
		return idDivision;
	}

	public void setDivision(Long idDivision) {
		this.idDivision = idDivision;
	}
          
    public Integer getDeMes() {
        return deMes;
    }

    public void setDeMes(Integer deMes) {
        this.deMes = deMes;
    }

    public String getDeItemPaces() {
        return deItemPaces;
    }

    public void setDeItemPaces(String deItemPaces) {
        this.deItemPaces = deItemPaces;
    }

    public Long getIdTipoEstado() {
        return idTipoEstado;
    }

    public void setIdTipoEstado(Long idTipoEstado) {
        this.idTipoEstado = idTipoEstado;
    }

    public Double getDePresupuesto() {
        return dePresupuesto;
    }

    public void setDePresupuesto(Double dePresupuesto) {
        this.dePresupuesto = dePresupuesto;
    }

    public String getNoConvocatoria() {
        return noConvocatoria;
    }

    public void setNoConvocatoria(String noConvocatoria) {
        this.noConvocatoria = noConvocatoria;
    }

    public String getReProgramaAnualSupervision() {
        return reProgramaAnualSupervision;
    }

    public void setReProgramaAnualSupervision(String reProgramaAnualSupervision) {
        this.reProgramaAnualSupervision = reProgramaAnualSupervision;
    }

    public Long getIdAprobadorg2() {
		return idAprobadorg2;
	}

	public void setIdAprobadorg2(Long idAprobadorg2) {
		this.idAprobadorg2 = idAprobadorg2;
	}

	public Long getIdAprobadorg3() {
		return idAprobadorg3;
	}

	public void setIdAprobadorg3(Long idAprobadorg3) {
		this.idAprobadorg3 = idAprobadorg3;
	}
	
   /* public String getUsCreacion() {
        return usCreacion;
    }

    public void setUsCreacion(String usCreacion) {
        this.usCreacion = usCreacion;
    }

    public Date getFeCreacion() {
        return feCreacion;
    }

    public void setFeCreacion(Date feCreacion) {
        this.feCreacion = feCreacion;
    }

    public String getIpCreacion() {
        return ipCreacion;
    }

    public void setIpCreacion(String ipCreacion) {
        this.ipCreacion = ipCreacion;
    }

    public String getUsActualizacion() {
        return usActualizacion;
    }

    public void setUsActualizacion(String usActualizacion) {
        this.usActualizacion = usActualizacion;
    }

    public Date getFeActualizacion() {
        return feActualizacion;
    }

    public void setFeActualizacion(Date feActualizacion) {
        this.feActualizacion = feActualizacion;
    }

    public String getIpActualizacion() {
        return ipActualizacion;
    }

    public void setIpActualizacion(String ipActualizacion) {
        this.ipActualizacion = ipActualizacion;
    }*/
   
}
