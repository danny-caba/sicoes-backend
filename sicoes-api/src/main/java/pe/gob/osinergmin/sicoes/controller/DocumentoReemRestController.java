package pe.gob.osinergmin.sicoes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.DocumentoReemplazo;
import pe.gob.osinergmin.sicoes.service.DocumentoReemService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api/documentosreemplazo")
public class DocumentoReemRestController extends BaseRestController{

    @Autowired
    DocumentoReemService documentoReemService;

    private Logger logger = LogManager.getLogger(DocumentoReemRestController.class);

    @PostMapping
    @Raml("documentoreemplazo.obtener.properties")
    public DocumentoReemplazo registrar(@ModelAttribute DocumentoReemplazo documento,
                                        @RequestParam("file") MultipartFile file){
        logger.info("registrar documento reemplazo {} ",documento);
        Archivo archivo = documento.getArchivo();
        archivo.setFile(file);
        documento.setArchivo(archivo);

        DocumentoReemplazo documentoReemplazo = documentoReemService.guardar(documento,getContexto());
        logger.info("documento reemplazo {}",documentoReemplazo);
        return documentoReemplazo;
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        logger.info("eliminar documento reemplazo {}",id);
        documentoReemService.eliminar(id,getContexto());
    }
}
