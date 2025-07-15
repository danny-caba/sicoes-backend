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
import pe.gob.osinergmin.sicoes.service.RequerimientoDocumentoService;
import pe.gob.osinergmin.sicoes.util.Raml;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/requerimientos/documentos")
public class RequerimientoDocumentoRestController extends BaseRestController {

    private final Logger logger = LogManager.getLogger(RequerimientoDocumentoRestController.class);

    @Autowired
    private RequerimientoDocumentoService requerimientoDocumentoService;

    @GetMapping
    @Raml("requerimientoDocumento.listar.properties")
    public Page<RequerimientoDocumento> listarRequerimientosDocumentos(@ModelAttribute FiltroRequerimientoDocumentoDTO filtros, Pageable pageable) {
        return requerimientoDocumentoService.listarRequerimientosDocumentos(filtros, pageable, getContexto());
    }

    @GetMapping("/{documentoUuid}/detalle")
    @Raml("requerimientoDocumentoDetalle.listar.properties")
    public List<RequerimientoDocumentoDetalle> listarRequerimientosDocumentosDetalle(@PathVariable("documentoUuid") String documentoUuid) {
        return requerimientoDocumentoService.listarRequerimientosDocumentosDetalle(documentoUuid);
    }

    @PostMapping("/detalle")
    @Raml("requerimientoDocumento.registrar.properties")
    @Transactional
    public RequerimientoDocumento registrarRequerimientosDocumento(@RequestBody List<RequerimientoDocumentoDetalle> listRequerimientoDocumentoDetalle) {
        return requerimientoDocumentoService.registrarRequerimientosDocumento(listRequerimientoDocumentoDetalle, getContexto());
    }

    @GetMapping("/coordinador")
    @Raml("requerimientoDocumento.listarPorCoordinador.properties")
    public Page<RequerimientoDocumento> listarRequerimientosDocumentosCoordinador(@ModelAttribute FiltroRequerimientoDocumentoCoordinadorDTO filtros, Pageable pageable) {
        return requerimientoDocumentoService.listarRequerimientosDocumentosCoordinador(filtros, pageable, getContexto());
    }

    @PatchMapping("/detalle")
    @Raml("requerimientoDocumento.actualizarDetalle.properties")
    @Transactional
    public RequerimientoDocumentoDetalle acualizarRequerimientosDocumentoDetalle(@RequestBody RequerimientoDocumentoDetalle requerimientoDocumentoDetalle) {
        return requerimientoDocumentoService.acualizarRequerimientosDocumentoDetalle(requerimientoDocumentoDetalle, getContexto());
    }

    @PostMapping("/{uuid}/evaluar")
    @Raml("requerimientoDocumento.evaluar.properties")
    @Transactional
    public RequerimientoDocumento evaluarRequerimientosDocumento(@RequestBody RequerimientoDocumento requerimientoDocumento) {
        return requerimientoDocumentoService.evaluarRequerimientosDocumento(requerimientoDocumento, getContexto());
    }

}