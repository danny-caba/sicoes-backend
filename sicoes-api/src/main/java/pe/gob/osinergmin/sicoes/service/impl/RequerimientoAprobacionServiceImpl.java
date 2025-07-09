package pe.gob.osinergmin.sicoes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.repository.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.RequerimientoAprobacionService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
public class RequerimientoAprobacionServiceImpl implements RequerimientoAprobacionService {

    @Autowired
    private ListadoDetalleService listadoDetalleService;
    @Autowired
    private RequerimientoAprobacionDao requerimientoAprobacionDao;

    @Override
    public Page<RequerimientoAprobacion> obtenerHistorial(String uuid, Contexto contexto, Pageable pageable) {
        return requerimientoAprobacionDao.obtenerAprobaciones(uuid, pageable)
                .map(this::mapHistorialAprobaciones);
    }

    @Override
    public RequerimientoAprobacion guardar(RequerimientoAprobacion model, Contexto contexto) {
        return null;
    }

    @Override
    public RequerimientoAprobacion obtener(Long aLong, Contexto contexto) {
        return null;
    }

    @Override
    public void eliminar(Long aLong, Contexto contexto) {

    }

    private RequerimientoAprobacion mapHistorialAprobaciones(RequerimientoAprobacion reqAprobacion) {
        ListadoDetalle tipoRolAprobador = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_ROL_APROBACION.CODIGO, Constantes.LISTADO.TIPO_ROL_APROBACION.APROBADOR_TECNICO);
        ListadoDetalle grupoRolAprobador = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.GRUPO_ROL_APROBACION.CODIGO, Constantes.LISTADO.GRUPO_ROL_APROBACION.GRUPO_1);
        reqAprobacion.setTipoRolAprobador(tipoRolAprobador);
        reqAprobacion.setGrupoRolAprobador(grupoRolAprobador);
        reqAprobacion.setNombreRolAprobador("Nombre Rol Aprobador");//TODO: buscar el nombre el aprobador
        return reqAprobacion;
    }
}
