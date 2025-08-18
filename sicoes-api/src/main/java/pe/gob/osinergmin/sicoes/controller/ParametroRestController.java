package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.SolicitudService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Raml;

import java.util.List;

@RestController
@RequestMapping("/api/renovaciones/parametro")
public class ParametroRestController extends BaseRestController{

	private Logger logger = LogManager.getLogger(ParametroRestController.class);
	
	@Autowired
	ListadoDetalleService listadoDetalleService;
	@Autowired
	SolicitudService solicitudService; 
	@Autowired
	NotificacionService notificacionService;

	@GetMapping("/estados")
	public List<ListadoDetalle> listarEstados(Pageable pageable) throws InterruptedException {
		logger.info("listarEstados ");
		List<ListadoDetalle> result= listadoDetalleService.buscar(Constantes.LISTADO.ESTADO_REQ_RENOVACION.CODIGO,getContexto());
		return result;
	}

	@GetMapping("/sectores")
	@Raml("asignacion.listar.properties")
	public List<ListadoDetalle> listarSectores(Pageable pageable) throws InterruptedException {
		logger.info("listarSectores ");
		List<ListadoDetalle> result = listadoDetalleService.buscar(Constantes.LISTADO.SECTOR.CODIGO,getContexto());
		return result;
	}

}
