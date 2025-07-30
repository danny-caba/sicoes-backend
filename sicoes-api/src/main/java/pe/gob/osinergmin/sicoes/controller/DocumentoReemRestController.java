package pe.gob.osinergmin.sicoes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.DocumentoReemplazo;
import pe.gob.osinergmin.sicoes.service.DocumentoReemService;
import pe.gob.osinergmin.sicoes.util.Raml;

import java.util.List;

@RestController
@RequestMapping("/api/documentosreemplazo")
public class DocumentoReemRestController extends BaseRestController{

    @Autowired
    DocumentoReemService documentoReemService;

    private Logger logger = LogManager.getLogger(DocumentoReemRestController.class);

    @PostMapping
    @Raml("documentoReemplazo.obtener.properties")
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

    @GetMapping
    @Raml("documentoReemplazo.listar.properties")
    public Page<DocumentoReemplazo> listarPorIdReemplazo(@RequestParam Long idReemplazo,
                                                         Pageable pageable){
        logger.info("listar documentos x id reemplazo  {}",idReemplazo);
        return documentoReemService.buscar(idReemplazo,pageable,getContexto());
    }

    @GetMapping("/{idDocumento}")
    @Raml("documentoReemplazo.obtener.properties")
    public DocumentoReemplazo listar(@PathVariable Long idDocumento){
        logger.info("listar documentos x id {}",idDocumento);
        return documentoReemService.obtener(idDocumento,getContexto());
    }



}
