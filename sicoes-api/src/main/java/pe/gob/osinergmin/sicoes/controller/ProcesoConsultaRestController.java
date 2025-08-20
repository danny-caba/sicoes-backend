package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.model.ProcesoConsulta;
import pe.gob.osinergmin.sicoes.service.ProcesoConsultaService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api")
public class ProcesoConsultaRestController extends BaseRestController {

    private Logger logger = LogManager.getLogger(ProcesoItemRestController.class);

    @Autowired
    private ProcesoConsultaService procesoConsultaService;

    @PostMapping("/consultas")
    @Raml("procesoConsulta.obtener.properties")
    public ProcesoConsulta registrar(@RequestBody ProcesoConsulta consulta) {
        logger.info("registrarConsulta {} ", consulta);
        return procesoConsultaService.guardar(consulta, getContexto());
    }

    @PutMapping("/consultas/{procesoConsultaUuid}")
    @Raml("procesoConsulta.obtener.properties")
    public ProcesoConsulta modificar(@PathVariable String procesoConsultaUuid, @RequestBody ProcesoConsulta consulta) {
        logger.info("modificarConsulta {} ", consulta);
        consulta.setProcesoConsultaUuid(procesoConsultaUuid);
        return procesoConsultaService.guardar(consulta, getContexto());
    }

    @PutMapping("/consultas")
//    @Raml("procesoConsulta.obtener.properties")
    public boolean actualizarEstadoEnvio(@RequestBody Long idProceso) {
        logger.info("actualizarEstadoEnvio {} ", idProceso);
        return procesoConsultaService.actualizarEstadoEnvio(idProceso, getContexto());
    }

    @DeleteMapping("/consultas/{procesoConsultaUuid}")
    public void eliminar(@PathVariable String procesoConsultaUuid) {
        logger.info("eliminarConsulta {} ", procesoConsultaUuid);
        procesoConsultaService.eliminar(procesoConsultaUuid, getContexto());
    }

    @GetMapping("/consultas")
    @Raml("procesoConsulta.listar.properties")
    public Page<ProcesoConsulta> BuscarConsultasPorUsuario(@RequestParam(required = false) Long idProceso, Pageable pageable) {
        logger.info("consultasPorUsuario");
        return procesoConsultaService.consultasPorUsuario(idProceso, pageable, getContexto());
    }

}
