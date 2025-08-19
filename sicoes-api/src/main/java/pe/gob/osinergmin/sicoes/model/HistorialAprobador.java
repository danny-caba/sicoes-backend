package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SICOES_TX_HISTORIAL_APROBADOR")
public class HistorialAprobador extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_HIST_APROBADOR")
	@SequenceGenerator(name = "GEN_SICOES_SEQ_HIST_APROBADOR", sequenceName = "SICOES_SEQ_HIST_APROBADOR", allocationSize = 1)
	@Column(name = "ID_HISTORIAL_APROBADOR")
	private Long idHistorialAprobador;
	
	@Column(name="ID_PERFIL")	
	private Long idPerfil;
	
	@Column(name="ID_APROBADOR_G1")	
	private Long idAprobadorG1;
	
	@Column(name="ID_APROBADOR_G2")	
	private Long idAprobadorG2;
	
	@Column(name="FL_ACTIVO")	
	private Long flagActivo;

	public Long getIdHistorialAprobador() {
		return idHistorialAprobador;
	}

	public void setIdHistorialAprobador(Long idHistorialAprobador) {
		this.idHistorialAprobador = idHistorialAprobador;
	}

	public Long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	public Long getIdAprobadorG1() {
		return idAprobadorG1;
	}

	public void setIdAprobadorG1(Long idAprobadorG1) {
		this.idAprobadorG1 = idAprobadorG1;
	}

	public Long getIdAprobadorG2() {
		return idAprobadorG2;
	}

	public void setIdAprobadorG2(Long idAprobadorG2) {
		this.idAprobadorG2 = idAprobadorG2;
	}

	public Long getFlagActivo() {
		return flagActivo;
	}

	public void setFlagActivo(Long flagActivo) {
		this.flagActivo = flagActivo;
	}

	@Override
	public String toString() {
		return "HistorialAprobador [idHistorialAprobador=" + idHistorialAprobador + ", idPerfil=" + idPerfil
				+ ", idAprobadorG1=" + idAprobadorG1 + ", idAprobadorG2=" + idAprobadorG2 + ", flagActivo=" + flagActivo
				+ "]";
	}
}
