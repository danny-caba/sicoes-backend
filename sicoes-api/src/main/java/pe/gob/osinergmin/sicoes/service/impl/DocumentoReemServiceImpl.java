package pe.gob.osinergmin.sicoes.service.impl;

import com.lowagie.text.pdf.PdfReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.model.dto.EvaluarConformidadRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.EvaluarConformidadResponseDTO;
import pe.gob.osinergmin.sicoes.repository.ArchivoDao;
import pe.gob.osinergmin.sicoes.repository.DocumentoReemDao;
import pe.gob.osinergmin.sicoes.repository.EvaluarDocuReemDao;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.DocumentoReemService;
import pe.gob.osinergmin.sicoes.service.DocumentoService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.util.*;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private EvaluarDocuReemDao evaluarDocuReemDao;

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
    public DocumentoReemplazo obtener(Long idDocumento, Contexto contexto) {
        DocumentoReemplazo documento = documentoReemDao.obtenerPorIdDocumento(idDocumento);
        List<Archivo> archivos = archivoDao.buscarPorDocumentoReemplazo(idDocumento);
        documento.setArchivo(archivos.get(0));
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
    public EvaluarConformidadResponseDTO evaluarConformidad(EvaluarConformidadRequestDTO request, Contexto contexto) {
        Optional<EvaluarDocuReemplazo> registroExistente = evaluarDocuReemDao
                .findByDocumentoIdDocumento(request.getIdDocumento())
                .stream()
                .findFirst();

        if (registroExistente.isPresent()) {
            registroExistente.get().setFechaEvaluacion(Date.from(Instant.now()));
            registroExistente.get().setConforme(request.getConformidad());
            registroExistente.get().setEvaluadoPor(contexto.getUsuario());

            EvaluarDocuReemplazo registroActualizado = evaluarDocuReemDao.save(registroExistente.get());
            AuditoriaUtil.setAuditoriaActualizacion(registroActualizado, contexto);

            return EvaluarConformidadResponseDTO.builder()
                    .idEvaluarDocuReemp(registroActualizado.getIdEvalDocumento())
                    .idDocuReemp(registroActualizado.getDocumento().getIdDocumento())
                    .fecEvaluacion(DateUtil.getDate(registroActualizado.getFechaEvaluacion(),"dd/MM/yyyy HH:mm:ss"))
                    .conformidad(registroActualizado.getConforme())
                    .evaluador(registroActualizado.getEvaluadoPor().getUsuario())
                    .build();
        }

        DocumentoReemplazo documentoReemplazo = new DocumentoReemplazo();
        documentoReemplazo.setIdDocumento(request.getIdDocumento());

        Rol rol = new Rol();
        rol.setIdRol(request.getIdRol());

        EvaluarDocuReemplazo registroNuevo = new EvaluarDocuReemplazo();
        registroNuevo.setDocumento(documentoReemplazo);
        registroNuevo.setEvaluadoPor(contexto.getUsuario());
        registroNuevo.setFechaEvaluacion(Date.from(Instant.now()));
        registroNuevo.setRol(rol);
        AuditoriaUtil.setAuditoriaRegistro(registroNuevo, contexto);

        EvaluarDocuReemplazo registroInsertado = evaluarDocuReemDao.save(registroNuevo);

        return EvaluarConformidadResponseDTO.builder()
                .idEvaluarDocuReemp(registroInsertado.getIdEvalDocumento())
                .idDocuReemp(registroInsertado.getDocumento().getIdDocumento())
                .fecEvaluacion(DateUtil.getDate(registroInsertado.getFechaEvaluacion(),"dd/MM/yyyy HH:mm:ss"))
                .conformidad(registroInsertado.getConforme())
                .evaluador(registroInsertado.getEvaluadoPor().getUsuario())
                .build();
    }
}
