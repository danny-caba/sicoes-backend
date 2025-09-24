package pe.gob.osinergmin.sicoes.service.impl;

import com.lowagie.text.pdf.PdfReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.DocumentoReemplazo;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DocumentoReemServiceImpl implements DocumentoReemService {

    private static final Logger logger = LogManager.getLogger(DocumentoReemServiceImpl.class);

    private final DocumentoReemDao documentoReemDao;
    private final ListadoDetalleDao listadoDetalleDao;
    private final ArchivoService archivoService;
    private final  ListadoDetalleService listadoDetalleService;
    private final ArchivoDao archivoDao;
    private final  SigedOldConsumer sigedOldConsumer;
    private final EntityManager entityManager;

    @Autowired
    public DocumentoReemServiceImpl(DocumentoReemDao documentoReemDao,
                                    ListadoDetalleDao listadoDetalleDao,
                                    ArchivoService archivoService,
                                    ListadoDetalleService listadoDetalleService,
                                    ArchivoDao archivoDao,
                                    SigedOldConsumer sigedOldConsumer,
                                    EntityManager entityManager) {
        this.documentoReemDao = documentoReemDao;
        this.listadoDetalleDao = listadoDetalleDao;
        this.archivoService = archivoService;
        this.listadoDetalleService = listadoDetalleService;
        this.archivoDao = archivoDao;
        this.sigedOldConsumer = sigedOldConsumer;
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
        archivo.setNombre(originalFileName);
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
            logger.error("Error al leer el archivo para obtener el número de folios: {}", e.getMessage(), e);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_NO_SE_PUEDE_LEER);
        }

        AuditoriaUtil.setAuditoriaRegistro(archivo,contexto);
        Archivo archivoGuardadoBD = archivoDao.save(archivo);

        String alfrescoPath = sigedOldConsumer.subirArchivosAlfresco(null, null, null,
                null, null,null,documentoBD.getIdDocumento(), null, archivo);
        archivoGuardadoBD.setNombreAlFresco(alfrescoPath);
        archivoGuardadoBD = archivoDao.save(archivoGuardadoBD);
        logger.info("Archivo registrado en DB con ID: {} y ruta Alfresco: {}", archivoGuardadoBD.getIdArchivo(), alfrescoPath);

        return documentoBD;
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentoReemplazo obtener(Long idDocumento, Contexto contexto) {
        DocumentoReemplazo documento = documentoReemDao.obtenerPorIdDocumento(idDocumento);
        logger.info("documento: {}",documento);
        List<Archivo> archivos = archivoDao.buscarPorDocumentoReemplazo(idDocumento);
        logger.info("archivos: {}",archivos);
        entityManager.detach(documento);
        documento.setEvaluacion(null);
        documento.setArchivo(archivos.get(0));
        logger.info("documentoFinal: {}",documento);
        return documento;
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
    public Page<DocumentoReemplazo> buscar(Long idReemplazoPersonal, Pageable pageable, Contexto contexto) {
        Page<Long> documentIds = documentoReemDao.findDocumentIds(idReemplazoPersonal, pageable);
        List<DocumentoReemplazo> documentos = new ArrayList<>();
        if (!documentIds.isEmpty()) {
            documentos = documentoReemDao.findDocumentosFull(documentIds.getContent());
            logger.info("Documentos completos cargados: {}", documentos);
        }

        List<Long> ids = documentos.stream()
                .map(DocumentoReemplazo::getIdDocumento)
                .collect(Collectors.toList());

        List<Archivo> archivos = archivoDao.findByIdDocumentoReemIn(ids);
        Map<Long, Archivo> porDoc = archivos.stream()
                        .collect(Collectors.toMap(Archivo::getIdDocumentoReem, Function.identity()));
        documentos.forEach(d -> d.setArchivo(porDoc.get(d.getIdDocumento())));
        return new PageImpl<>(documentos, pageable, documentIds.getTotalElements());
    }

    @Override
    public Page<DocumentoReemplazo> buscarIdReemplazoSeccion(Long idReemplazoPersonal, String seccion, Pageable pageable) {
        String listado = Constantes.LISTADO.SECCIONES_REEMPLAZO_PERSONAL;
        // Obtener el detalle de listado usando Optional
        Optional<ListadoDetalle> optionalListadoDetalle = Optional.ofNullable(listadoDetalleDao.obtenerListadoDetalle(listado, seccion));
        // Verificar si el detalle existe
        if (!optionalListadoDetalle.isPresent()) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.DOCUMENTO_REEMPLAZO_ID_SECCION);
        }

        Long idSeccion = optionalListadoDetalle.get().getIdListadoDetalle();
        Page<Long> documentIds = documentoReemDao.findDocumentSeccionIds(idReemplazoPersonal,idSeccion,pageable);

        if (documentIds.isEmpty()){
            return new PageImpl<>(new ArrayList<>(),pageable,0);
        }

        // Obtener el documento usando Optional
        List<DocumentoReemplazo> documentos = documentoReemDao.findDocumentosFull(documentIds.getContent());
        if (documentos.isEmpty()) {
            logger.info("No se encontró el documento para idReemplazo: {} y idSeccion: {}", idReemplazoPersonal, idSeccion);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.DOCUMENTO_REEMPLAZO_NO_EXISTE);
        }

        List<Long> ids = documentos.stream()
                .map(DocumentoReemplazo::getIdDocumento)
                .collect(Collectors.toList());

        List<Archivo> archivos = archivoDao.findByIdDocumentoReemIn(ids);
        Map<Long, Archivo> porDoc = archivos.stream()
                .collect(Collectors.toMap(Archivo::getIdDocumentoReem, Function.identity()));
        documentos.forEach(d -> d.setArchivo(porDoc.get(d.getIdDocumento())));
        return new PageImpl<>(documentos, pageable, documentIds.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DocumentoReemplazo actualizar(DocumentoReemplazo documento, Contexto contexto) {
        if (documento.getIdDocumento()==null){
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_DOCUMENTO_REEMPLAZO);
        }
        if (documento.getIdReemplazoPersonal() == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONAL_REEMPLAZO_NO_ENVIADO);
        }
        DocumentoReemplazo existe = documentoReemDao.findById(documento.getIdDocumento())
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.DOCUMENTO_REEMPLAZO_NO_EXISTE));

        // Verifica y actualiza el campo de seccion
        if (documento.getSeccion() != null) {
            existe.setSeccion(documento.getSeccion());
        }

        // Verifica y actualiza el campo de tipoDocumento
        if (documento.getTipoDocumento() != null) {
            existe.setTipoDocumento(documento.getTipoDocumento());
        }

        // Verifica y actualiza el correlativo
        if (documento.getNuCorrelativo() != null) {
            existe.setNuCorrelativo(documento.getNuCorrelativo());
        }

        // Verifica y actualiza el nombre del documento
        if (documento.getDeNombreDocumento() != null) {
            existe.setDeNombreDocumento(documento.getDeNombreDocumento());
        }

        // Verifica y actualiza el ID del documento en SIGED
        if (documento.getIdDocumentoSiged() != null) {
            existe.setIdDocumentoSiged(documento.getIdDocumentoSiged());
        }

        // Verifica y actualiza el ID del archivo en SIGED
        if (documento.getIdArchivoSiged() != null) {
            existe.setIdArchivoSiged(documento.getIdArchivoSiged());
        }

        // Verifica y actualiza la fecha de inicio de validez
        if (documento.getFeFechaInicioValidez() != null) {
            existe.setFeFechaInicioValidez(documento.getFeFechaInicioValidez());
        }

        // Verifica y actualiza la fecha de fin de validez
        if (documento.getFeFechaFinValidez() != null) {
            existe.setFeFechaFinValidez(documento.getFeFechaFinValidez());
        }

        // Verifica y actualiza la fecha de registro
        if (documento.getFeFechaRegistro() != null) {
            existe.setFeFechaRegistro(documento.getFeFechaRegistro());
        }

        // Verifica y actualiza la fecha de inicio de contrato
        if (documento.getFeFechaIniContrato() != null) {
            existe.setFeFechaIniContrato(documento.getFeFechaIniContrato());
        }

        AuditoriaUtil.setAuditoriaActualizacion(existe, contexto);
        return documentoReemDao.save(existe);
    }
}
