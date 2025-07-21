package pe.gob.osinergmin.sicoes.service.impl;

import com.lowagie.text.pdf.PdfReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.repository.ArchivoDao;
import pe.gob.osinergmin.sicoes.repository.DocumentoReemDao;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.DocumentoReemService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentoReemServiceImpl implements DocumentoReemService {

    Logger logger = LogManager.getLogger(DocumentoReemServiceImpl.class);

    @Autowired
    private DocumentoReemDao documentoReemDao;

    @Autowired
    private ListadoDetalleDao listadoDetalleDao;

    @Autowired
    private ArchivoService archivoService;

    @Autowired
    private ListadoDetalleService listadoDetalleService;

    @Autowired
    private ArchivoDao archivoDao;

    @Autowired
    private Environment env;

    @Autowired
    private SigedOldConsumer sigedOldConsumer;

    private final EntityManager entityManager;

    public DocumentoReemServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DocumentoReemplazo guardar(DocumentoReemplazo documento, Contexto contexto) {
        if (documento.getIdReemplazoPersonal() == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONAL_REEMPLAZO_NO_ENVIADO);
        }

        Archivo archivo = documento.getArchivo();
        MultipartFile file = archivo.getFile();
        if (file == null || file.isEmpty()){
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_NO_ENVIADO);
        }

        String originalFileName = reemplazarCaracteres(file.getOriginalFilename());
        String uniqueCode = UUID.randomUUID().toString();

        AuditoriaUtil.setAuditoriaRegistro(documento, contexto);
        DocumentoReemplazo documentoBD = documentoReemDao.save(documento);

        archivo.setIdDocumentoReem(documentoBD.getIdDocumento());
        archivo.setFile(file);
        ListadoDetalle estadoLd = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_ARCHIVO.CODIGO, Constantes.LISTADO.ESTADO_ARCHIVO.ASOCIADO);
        archivo.setEstado(estadoLd);

        archivo.setNombreReal(originalFileName);
        archivo.setCodigo(uniqueCode);
        archivo.setTipo(file.getContentType());
        archivo.setPeso(file.getSize());

        try {
            if ("application/pdf".equals(file.getContentType())) {
                PdfReader reader = new PdfReader(file.getBytes());
                int count = reader.getNumberOfPages();
                archivo.setNroFolio(count * 1L);
            } else {
                archivo.setNroFolio(1L);
            }
        } catch (Exception e) {
            logger.error("Error al leer el archivo para obtener el número de folios: " + e.getMessage(), e);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_NO_SE_PUEDE_LEER);
        }

        AuditoriaUtil.setAuditoriaRegistro(archivo,contexto);
        Archivo archivoGuardadoBD = archivoDao.save(archivo);

        String alfrescoPath = sigedOldConsumer.subirArchivosAlfresco(null, null, null,
                null, null,null,documentoBD.getIdDocumento(), archivo);
        archivoGuardadoBD.setNombreAlFresco(alfrescoPath);
        archivoGuardadoBD = archivoDao.save(archivoGuardadoBD);
        logger.info("Archivo registrado en DB con ID: " + archivoGuardadoBD.getIdArchivo() + " y ruta Alfresco: " + alfrescoPath);

        return documentoBD;
    }

    @Override
    public DocumentoReemplazo obtener(Long aLong, Contexto contexto) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void eliminar(Long id, Contexto contexto) {
        if (!documentoReemDao.existsById(id)) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.DOCUMENTO_REEMPLAZO_NO_EXISTE);
        }
        //Eliminar archivo asociado
        archivoService.eliminarIdDocumentoReem(id,contexto);
        //Eliminar documento reemplazo
        documentoReemDao.deleteById(id);
    }

    private String reemplazarCaracteres(String originalFilename) {
        if (originalFilename == null) {
            throw new NullPointerException("originalFilename es nulo");
        }
        return originalFilename.replaceAll("[^\\p{ASCII}]", "");
    }

    @Override
    public List<DocumentoReemplazo> buscar(Long idReemplazoPersonal, Contexto contexto) {
        return documentoReemDao.buscar(idReemplazoPersonal);
    }
}
