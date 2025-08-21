package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.PerfilAprobador;
import pe.gob.osinergmin.sicoes.repository.PerfilAprobadorDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.util.Constantes;

import java.util.Optional;

@Service
public class PerfilAprobadorService {
    private final PerfilAprobadorDao perfilAprobadorDao;
    private final ListadoDetalleService listadoDetalleService;

    public PerfilAprobadorService(
            PerfilAprobadorDao perfilAprobadorDao,
            ListadoDetalleService listadoDetalleService){
        this.perfilAprobadorDao=perfilAprobadorDao;
        this.listadoDetalleService = listadoDetalleService;
    }

    public Boolean esPerfilAprobadorG1(Long idUsuario){

        ListadoDetalle detallePerfilAprbadorG1 = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_ARCHIVO.CODIGO, //TODO: setear los codigos
                Constantes.LISTADO.TIPO_ARCHIVO.INFORME_RENOVACION_CONTRATO //TODO: setear los codigos
        );
        if (detallePerfilAprbadorG1 == null) {
            throw  new RuntimeException("Estado 'detallePerfilAprbadorG1' no encontrado en listado detalle");
        }

       Optional<PerfilAprobador> perfilAprobador = perfilAprobadorDao.obtenerPerfilAprobadorPorIdPerfilAndIdUsuario(detallePerfilAprbadorG1.getIdListadoDetalle(),idUsuario);
        return perfilAprobador.isPresent()?true:false;
    }

    public boolean esPerfilAprobadorG2(Long idUsuario) {
        //TODO: falta implementar
        return true;
    }
}
