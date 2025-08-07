package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.osinergmin.sicoes.model.RequerimientoDocumento;
import pe.gob.osinergmin.sicoes.model.RequerimientoDocumentoDetalle;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDocumentoCoordinadorDTO;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDocumentoDTO;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDocumentoDetalleDao;
import pe.gob.osinergmin.sicoes.service.RequerimientoDocumentoService;
import pe.gob.osinergmin.sicoes.util.Raml;

import java.util.List;

@RestController
@RequestMapping("/api/requerimientos/documentos")
public class RequerimientoDocumentoRestController extends BaseRestController {

    private final Logger logger = LogManager.getLogger(RequerimientoDocumentoRestController.class);

    @Autowired
    private RequerimientoDocumentoService requerimientoDocumentoService;

    @Autowired
    private RequerimientoDocumentoDetalleDao requerimientoDocumentoDetalleDao;

    @GetMapping
    @Raml("requerimientoDocumento.listar.properties")
    public Page<RequerimientoDocumento> listarRequerimientosDocumentos(@ModelAttribute FiltroRequerimientoDocumentoDTO filtros, Pageable pageable) {
        logger.info("Listando requerimientos documentos con filtros: {}", filtros);
        return requerimientoDocumentoService.listarRequerimientosDocumentos(filtros, pageable, getContexto());
    }

    @GetMapping("/{documentoUuid}/detalle")
    @Raml("requerimientoDocumentoDetalle.listar.properties")
    public List<RequerimientoDocumentoDetalle> listarRequerimientosDocumentosDetalle(@PathVariable("documentoUuid") String documentoUuid) {
        logger.info("Listando detalles de requerimientos documentos para el UUID: {}", documentoUuid);
        return requerimientoDocumentoService.listarRequerimientosDocumentosDetalle(documentoUuid, getContexto());
    }

    @PostMapping("/detalle")
    @Raml("requerimientoDocumentoDetalle.actualizar.properties")
    public List<RequerimientoDocumentoDetalle> actualizarRequerimientosDocumentosDetalle(@RequestBody List<RequerimientoDocumentoDetalle> listRequerimientoDocumentoDetalle) {
        logger.info("Actualizando detalles de requerimientos documentos: {}", listRequerimientoDocumentoDetalle);
        return requerimientoDocumentoService.actualizarRequerimientosDocumentosDetalle(listRequerimientoDocumentoDetalle, getContexto());
    }

    @GetMapping("/coordinador")
    @Raml("requerimientoDocumento.listarPorCoordinador.properties")
    public Page<RequerimientoDocumento> listarRequerimientosDocumentosCoordinador(@ModelAttribute FiltroRequerimientoDocumentoCoordinadorDTO filtros, Pageable pageable) {
        logger.info("Listando requerimientos documentos para coordinador con filtros: {}", filtros);
        return requerimientoDocumentoService.listarRequerimientosDocumentosCoordinador(filtros, pageable, getContexto());
    }

    @PatchMapping("/detalle")
    @Raml("requerimientoDocumentoDetalle.patch.properties")
    public RequerimientoDocumentoDetalle patchRequerimientoDocumentoDetalle(@RequestBody RequerimientoDocumentoDetalle requerimientoDocumentoDetalle) {
        logger.info("Actualizando detalle de requerimiento documento: {}", requerimientoDocumentoDetalle);
        return requerimientoDocumentoService.patchRequerimientoDocumentoDetalle(requerimientoDocumentoDetalle, getContexto());
    }

    @PostMapping("/{uuid}/evaluar")
    @Raml("requerimientoDocumento.evaluar.properties")
    public RequerimientoDocumento evaluarRequerimientosDocumento(@PathVariable("uuid") String uuid) {
        logger.info("Evaluando requerimientos documento con UUID: {}", uuid);
        return requerimientoDocumentoService.evaluarRequerimientosDocumento(uuid, getContexto());
    }

    @PostMapping("/{documentoUuid}/revisar")
    @Raml("requerimientoDocumento.registrar.properties")
    public RequerimientoDocumento revisarRequerimientosDocumento(@PathVariable("documentoUuid") String documentoUuid,
                                                                 @RequestBody List<RequerimientoDocumentoDetalle> listRequerimientoDocumentoDetalle) {
        logger.info("Revisando requerimientos documento con UUID: {} y detalles: {}", documentoUuid, listRequerimientoDocumentoDetalle);
        return requerimientoDocumentoService.revisar(documentoUuid, listRequerimientoDocumentoDetalle, getContexto());
    }

    @GetMapping("/detalle/{uuid}")
    @Raml("requerimientoDocumentoDetalle.obtener.properties")
    public RequerimientoDocumentoDetalle obtenerDetalle(@PathVariable("uuid") String uuid) {
        logger.info("Obteniendo detalle de requerimiento documento con UUID: {}", uuid);
        return requerimientoDocumentoDetalleDao.buscarPorUuid(uuid);
    }
}