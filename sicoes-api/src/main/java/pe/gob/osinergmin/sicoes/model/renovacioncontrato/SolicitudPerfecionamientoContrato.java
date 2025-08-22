package pe.gob.osinergmin.sicoes.model.renovacioncontrato;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="SICOES_TC_SOLI_PERF_CONT")
@Getter
@Setter
public class SolicitudPerfecionamientoContrato {
    
    @Id
    @Column(name = "ID_SOLI_PERF_CONT")
    private Long idSolicitudPerfecionamientoContrato;

    @Column(name = "ID_APROBADOR_G1")
    private Long idAprobadorG1;

    @Column(name = "ID_APROBADOR_G2")
    private Long idAprobadorG2;

    @Column(name = "ID_APROBADOR_G3")
    private Long idAprobadorG3;

    @Column(name = "ID_PERFIL")
    private Long idPerfil;
}
