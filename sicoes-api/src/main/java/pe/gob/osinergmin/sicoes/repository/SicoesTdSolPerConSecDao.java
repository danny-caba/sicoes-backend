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
import pe.gob.osinergmin.sicoes.model.SicoesTdSolPerConSec;
import pe.gob.osinergmin.sicoes.util.Constantes;

@Repository
public interface SicoesTdSolPerConSecDao extends JpaRepository<SicoesTdSolPerConSec, Long> {

    @Query("select spc from SicoesTdSolPerConSec spc "
            + "where spc.flConPersonal = '1' "
            + "and spc.esSoliPerfCont = '1' ")
    List<SicoesTdSolPerConSec> obtenerSeccionPorPersonal();

    @Query("select spc from SicoesTdSolPerConSec spc "
            + "where spc.idSolPerConSec = :id ")
    SicoesTdSolPerConSec buscarPorId(Long id);

    @Query("select spc from SicoesTdSolPerConSec spc "
            + "where spc.idSoliPerfCont = :idSolicitud "
            + "and spc.esSoliPerfCont = '1' "
            + "order by spc.deSeccion ")
    List<SicoesTdSolPerConSec> obtenerSeccionesXSolicitud(Long idSolicitud);

@Query("select spc from SicoesTdSolPerConSec spc "
            + "where spc.idSoliPerfCont = :idSolicitudPadre "
            + "order by spc.deSeccion ")
    List<SicoesTdSolPerConSec> buscarPorIdSolicitudPadre(Long idSolicitudPadre);


 
}
