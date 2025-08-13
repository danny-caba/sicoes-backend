package pe.gob.osinergmin.sicoes.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.ProcesoConsulta;
import pe.gob.osinergmin.sicoes.model.Seccion;
import pe.gob.osinergmin.sicoes.model.SicoesTdSoliPersProp;
import pe.gob.osinergmin.sicoes.util.Constantes;

@Repository
public interface SicoesTdSoliPersPropDao extends JpaRepository<SicoesTdSoliPersProp, Long> {

    @Query("select stp from SicoesTdSoliPersProp stp "
            + "inner join fetch stp.sicoesTdSolPerConSec stsp "
            + "where stsp.idSolPerConSec=:idSeccion "
            + "and stp.esAdjuntoSolicitud = '1' ")
    List<SicoesTdSoliPersProp> obtenerProfesionalesPorSeccion(Long idSeccion);

    @Query(value = "select stp from SicoesTdSoliPersProp stp "
            + "inner join fetch stp.supervisora sup "
            + "inner join fetch stp.sicoesTdSolPerConSec stsp "
            + "where stsp.idSolPerConSec=:idSeccionPerConSec "
            + "order by stp.idSoliPersProp ",
            countQuery = "select count(stp) from SicoesTdSoliPersProp stp "
                    + "inner join stp.supervisora sup "
                    + "inner join stp.sicoesTdSolPerConSec stsp "
                    + "where stsp.idSolPerConSec=:idSeccionPerConSec ")
    Page<SicoesTdSoliPersProp> personasPorSeccion(Long idSeccionPerConSec, Pageable pageable);

    @Query(value = "select stp from SicoesTdSoliPersProp stp "
            + "inner join fetch stp.supervisora sup "
            + "inner join fetch stp.sicoesTdSolPerConSec stsp "
            + "where stsp.idSoliPerfCont=:idSolicitud "
            + "order by stp.idSoliPersProp ",
            countQuery = "select count(stp) from SicoesTdSoliPersProp stp "
                    + "inner join stp.supervisora sup "
                    + "inner join stp.sicoesTdSolPerConSec stsp "
                    + "where stsp.idSoliPerfCont=:idSolicitud ")
    Page<SicoesTdSoliPersProp> personasPorSolicitud(Long idSolicitud, Pageable pageable);
 
}
