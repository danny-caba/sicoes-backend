package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;


@RestController
@RequestMapping("/api/informe/renovacion/")
public class InformePresupuestalRestController extends BaseRestController {

    @Autowired
    private SigedOldConsumer sigedOldConsumer;
    
    @Autowired
    private ArchivoService archivoService;

    @PostMapping("/subir")
    public String subirArchivo(
            @RequestParam("file") MultipartFile file,
            @RequestParam Long idSolicitud) {
        
        try {
            // 1. Create Archivo object from MultipartFile
            Archivo archivo = new Archivo();
            archivo.setNombreReal(file.getOriginalFilename());
            archivo.setContenido(file.getBytes());
            archivo.setIdSolicitud(idSolicitud);
            
            // 2. Upload to Alfresco
            String alfrescoPath = sigedOldConsumer.subirArchivosAlfresco(
                idSolicitud,  // idSolicitud
                null,         // idPropuesta
                null,         // idProceso
                null,         // idSeccionRequisito
                null,         // idContrato
                null,         // idSoliPerfCont
                archivo       // archivo
            );
            
            // 3. Save file metadata to database
            archivo.setNombreAlFresco(alfrescoPath);
            archivoService.guardar(archivo, getContexto());
            
            return "Archivo subido exitosamente. Ruta: " + alfrescoPath;
            
        } catch (Exception e) {
            throw new RuntimeException("Error al subir el archivo: " + e.getMessage(), e);
        }
    }

    @DeleteMapping("/eliminar/{idArchivo}")
    public String eliminarArchivo(@PathVariable Long idArchivo) {
        try {
            // 1. Get file metadata from database
            Archivo archivo = archivoService.obtener(idArchivo, getContexto());
            
            if (archivo == null) {
                return "Archivo no encontrado";
            }


            // 2. Delete from Alfresco (if using UUID)
            /*if (archivo.getUuid() != null) {
                sigedOldConsumer.eliminarDocumento(archivo.getUuid());
            }*/
            
            // 3. Delete from database
            archivoService.eliminar(idArchivo, getContexto());
            
            return "Archivo eliminado exitosamente";
            
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el archivo: " + e.getMessage(), e);
        }
    }

}
