package pe.gob.osinergmin.sicoes.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class BaseModel {
	
	protected static final SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");

	
	@Transient
	@JsonIgnore
	protected DecimalFormat integerFormat =  new DecimalFormat("###,###",DecimalFormatSymbols.getInstance(Locale.forLanguageTag("es_PE")));
	@Transient
	@JsonIgnore
	protected DecimalFormat decimalFormat =  new DecimalFormat("###,###.00",DecimalFormatSymbols.getInstance(Locale.forLanguageTag("es_PE"))); 

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_CREACION", nullable=false,insertable=true,updatable=false)
	@JsonIgnore
	private Date fecCreacion;
	
	@Column(name="IP_CREACION", nullable=false, insertable=true,updatable=false)
	@JsonIgnore
	protected String ipCreacion;
	
	@Column(name="US_CREACION", nullable=false, insertable=true,updatable=false)
	@JsonIgnore
	protected String usuCreacion;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_ACTUALIZACION",insertable=false,updatable=true)
	@JsonIgnore
	protected Date fecActualizacion;

	@Column(name="IP_ACTUALIZACION",insertable=false,updatable=true)
	@JsonIgnore
	protected String ipActualizacion;

	
	@Column(name="US_ACTUALIZACION",insertable=false,updatable=true)
	@JsonIgnore
	protected String usuActualizacion;
	
	@Transient
	@JsonIgnore
	protected String origen;
	
	public Date getFecActualizacion() {
		return fecActualizacion;
	}

	public void setFecActualizacion(Date fecActualizacion) {
		this.fecActualizacion = fecActualizacion;
	}

	public Date getFecCreacion() {
		return fecCreacion;
	}

	public void setFecCreacion(Date fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public String getIpActualizacion() {
		return ipActualizacion;
	}

	public void setIpActualizacion(String ipActualizacion) {
		this.ipActualizacion = ipActualizacion;
	}

	public String getIpCreacion() {
		return ipCreacion;
	}

	public void setIpCreacion(String ipCreacion) {
		this.ipCreacion = ipCreacion;
	}

	public String getUsuActualizacion() {
		return usuActualizacion;
	}

	public void setUsuActualizacion(String usuActualizacion) {
		this.usuActualizacion = usuActualizacion;
	}

	public String getUsuCreacion() {
		return usuCreacion;
	}

	public void setUsuCreacion(String usuCreacion) {
		this.usuCreacion = usuCreacion;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	
}
