package pe.gob.osinergmin.sicoes.model.renovacioncontrato;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import pe.gob.osinergmin.sicoes.model.BaseModel;

import javax.persistence.*;
import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="SICOES_TM_LISTADO_DETALLE")
@Getter
@Setter
public class ListadoDetalleRenovacionContrato implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_LISTADO_DETALLE")
    private Long idListadoDetalle;

    @Column(name="ID_LISTADO")
    private Long idListado;

    @Column(name="ID_LISTADO_PADRE")
    private Long idListadoPadre;

    @Column(name="ID_SUPERIOR_LD")
    private Long idListadoSuperior;

    @Column(name="CO_LISTADO_DETALLE")
    private String codigo;

    @Column(name="NU_ORDEN")
    private Long orden;

    @Column(name="NO_LISTADO_DETALLE")
    private String nombre;

    @Column(name="DE_LISTADO_DETALLE")
    private String descripcion;

    @Column(name="DE_VALOR")
    private String valor;
}
