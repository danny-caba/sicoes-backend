package pe.gob.osinergmin.sicoes.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.model.AdendaReemplazo;
import pe.gob.osinergmin.sicoes.model.Aprobacion;
import pe.gob.osinergmin.sicoes.model.DocumentoReemplazo;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.PersonalReemplazo;
import pe.gob.osinergmin.sicoes.model.PropuestaProfesional;
import pe.gob.osinergmin.sicoes.model.Rol;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.SupervisoraMovimiento;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.dto.FirmaRequestDTO;
import pe.gob.osinergmin.sicoes.repository.*;
import pe.gob.osinergmin.sicoes.service.*;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.bean.siged.AccessRequestInFirmaDigital;

import java.util.*;

@Service
public class AdendaReemplazoServiceImpl implements AdendaReemplazoService {

    private static final Logger logger = LogManager.getLogger(AdendaReemplazoServiceImpl.class);

    private final AdendaReemplazoDao adendaReemplazoDao;
    private final PersonalReemplazoDao reemplazoDao;
    private final PersonalReemplazoService personalReemplazoService;
    private final SupervisoraMovimientoService supervisoraMovimientoService;
    private final ListadoDetalleDao listadoDetalleDao;
    private final DocumentoReemDao documentoReemDao;
    private final SigedOldConsumer sigedOldConsumer;
    private final PropuestaProfesionalDao propuestaProfesionalDao;
    private final ListadoDetalleService listadoDetalleService;
    private final UsuarioRolDao usuarioRolDao;
    private final UsuarioDao usuarioDao;
    private final AprobacionDao aprobacionDao;
    private final NotificacionContratoService notificacionContratoService;
    private final SicoesSolicitudDao sicoesSolicitudDao;
    private final RolDao rolDao;
    private final AdendaReemplazoService adendaReemplazoService;
    private static final String ARCHIVOS_KEY = "archivos";

    @Autowired
    public AdendaReemplazoServiceImpl(AdendaReemplazoDao adendaReemplazoDao,
                                      PersonalReemplazoDao reemplazoDao,
                                      PersonalReemplazoService personalReemplazoService,
                                      SupervisoraMovimientoService supervisoraMovimientoService,
                                      ListadoDetalleDao listadoDetalleDao,
                                      DocumentoReemDao documentoReemDao,
                                      SigedOldConsumer sigedOldConsumer,
                                      PropuestaProfesionalDao propuestaProfesionalDao,
                                      ListadoDetalleService listadoDetalleService,
                                      UsuarioRolDao usuarioRolDao,
                                      UsuarioDao usuarioDao,
                                      AprobacionDao aprobacionDao,
                                      NotificacionContratoService notificacionContratoService,
                                      SicoesSolicitudDao sicoesSolicitudDao,
                                      RolDao rolDao,
                                      @Lazy AdendaReemplazoService adendaReemplazoService

                                       ) {
        this.adendaReemplazoDao = adendaReemplazoDao;
        this.reemplazoDao = reemplazoDao;
        this.personalReemplazoService = personalReemplazoService;
        this.supervisoraMovimientoService = supervisoraMovimientoService;
        this.listadoDetalleDao = listadoDetalleDao;
        this.documentoReemDao = documentoReemDao;
        this.sigedOldConsumer = sigedOldConsumer;
        this.propuestaProfesionalDao = propuestaProfesionalDao;
        this.listadoDetalleService = listadoDetalleService;
        this.usuarioRolDao = usuarioRolDao;
        this.usuarioDao = usuarioDao;
        this.aprobacionDao = aprobacionDao;
        this.notificacionContratoService = notificacionContratoService;
        this.sicoesSolicitudDao = sicoesSolicitudDao;
        this.rolDao = rolDao;
        this.adendaReemplazoService = adendaReemplazoService;
    }

    @Override
    @Transactional
    public AdendaReemplazo guardar(AdendaReemplazo adenda, Contexto contexto) {
        logger.info("guardar adenda");
        Long idReemplazoPersonal = adenda.getIdReemplazoPersonal();
        if (idReemplazoPersonal==null){
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONAL_REEMPLAZO_NO_ENVIADO);
        }
        PersonalReemplazo personalReemplazo = reemplazoDao.findById(idReemplazoPersonal)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REEMPLAZO_PERSONAL_NO_EXISTE));
        //Validar que documentacion 6.Cargar Adenda exista
        ListadoDetalle seccion = listadoDetalleDao.obtenerListadoDetalle(
                Constantes.LISTADO.SECCIONES_REEMPLAZO_PERSONAL,
                Constantes.LISTADO.SECCION_DOC_REEMPLAZO.CARGAR_ADENDA);
        logger.info("idReemplazo {}",personalReemplazo.getIdReemplazo());
        logger.info("seccion {}",seccion.getIdListadoDetalle());

        if (!documentoReemDao.existsByIdReemplazoPersonalAndSeccion_IdListadoDetalle(
                personalReemplazo.getIdReemplazo(),seccion.getIdListadoDetalle())) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.DOCUMENTO_REEMPLAZO_NO_EXISTE);
        }

        String listadoAprobacion = Constantes.LISTADO.ESTADO_APROBACION.CODIGO;
        String descAprobacion = Constantes.LISTADO.ESTADO_APROBACION.EN_APROBACION;
        ListadoDetalle estadoAproGeneral = listadoDetalleDao.obtenerListadoDetalle(listadoAprobacion, descAprobacion);

        String listadoAprobacionAdenda = Constantes.LISTADO.ESTADO_ADENDA.CODIGO;
        String descAsignadoAdenda = Constantes.LISTADO.ESTADO_ADENDA.ASIGNADO;
        ListadoDetalle estadoAproAdenda = listadoDetalleDao.obtenerListadoDetalle(listadoAprobacionAdenda, descAprobacion);
        ListadoDetalle estadoAsignadoAdenda = listadoDetalleDao.obtenerListadoDetalle(listadoAprobacionAdenda, descAsignadoAdenda);

        //Buscar Aprobacion
        procesarAprobacion(personalReemplazo.getIdReemplazo(),
                Constantes.ROLES.EVALUADOR_CONTRATOS,contexto,estadoAproGeneral,null);

        personalReemplazo.setEstadoAprobacionAdenda(estadoAproGeneral);
        personalReemplazoService.actualizar(personalReemplazo,contexto);
        adenda.setEstadoAprobacion(estadoAproAdenda);
        adenda.setEstadoAprLogistica(estadoAsignadoAdenda);
        AuditoriaUtil.setAuditoriaRegistro(adenda,contexto);

        //Notificacion
        Optional<Usuario> evaluadorContratos = usuarioRolDao.obtenerUsuariosRol(Constantes.ROLES.EVALUADOR_CONTRATOS)
                .stream()
                .findFirst()
                .map(rol -> usuarioDao.obtener(rol.getUsuario().getIdUsuario()));
        if (evaluadorContratos.isPresent()) {
            String numeroExpediente = this.obtenerNumeroExpediente(personalReemplazo);
            notificacionContratoService.notificarAprobacionPendiente(evaluadorContratos.get(), numeroExpediente, contexto);
        } else {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.EVALUADOR_CONTRATOS_NO_EXISTE);
        }

        //Integrar documentacion en la plataforma SIGED
        Long idPerfContrato = personalReemplazo.getIdSolicitud();
        SicoesSolicitud solicitud = sicoesSolicitudDao.obtenerSolicitudDetallado(idPerfContrato);
        String listadoSeccion = Constantes.LISTADO.SECCIONES_REEMPLAZO_PERSONAL;
        String descSeccion = Constantes.LISTADO.SECCION_DOC_REEMPLAZO.CARGAR_ADENDA;
        ListadoDetalle detalleSeccion = listadoDetalleDao.obtenerListadoDetalle(listadoSeccion, descSeccion);
        logger.info("detalleSeccion:{}", detalleSeccion.getIdListadoDetalle());
        List<DocumentoReemplazo> documentos = documentoReemDao.obtenerPorIdReemplazoSecciones(
                personalReemplazo.getIdReemplazo(),
                Collections.singletonList(detalleSeccion.getIdListadoDetalle()));
        personalReemplazoService.procesarDocumentosReemplazo(documentos, solicitud, contexto);
        return adendaReemplazoDao.save(adenda);
    }

    @Override
    public AdendaReemplazo obtener(Long aLong, Contexto contexto) {
         throw new UnsupportedOperationException("Método obtener no está implementado");
    }

    @Override
    public void eliminar(Long aLong, Contexto contexto) {
         throw new UnsupportedOperationException("Método eliminar no está implementado");
    }

    @Override
    public Map<String, Object> iniciarFirma(Long idAdenda, boolean visto,
                                             boolean firmaJefe, boolean firmaGerente) {
        logger.info("Inicio proceso de firma para adenda con ID: {}", idAdenda);

        AdendaReemplazo adendaReemplazo = getAdendaReemplazo(idAdenda);
        DocumentoReemplazo documento = getDocumentoReemplazo(adendaReemplazo);

        String idArchivoSiged = documento.getIdArchivoSiged();
        verifyArchivoSiged(idArchivoSiged);

        ListadoDetalle estadoApro = getEstadoAprobacion();
        verifyFirmaOrVisto(visto, firmaJefe, firmaGerente, adendaReemplazo, estadoApro);

        // Propiedades de la firma digital
        AccessRequestInFirmaDigital accessRequestInFirmaDigital = sigedOldConsumer.obtenerParametrosfirmaDigital();
        String usuarioSiged = accessRequestInFirmaDigital.getLoginUsuario();
        String passwordSiged = accessRequestInFirmaDigital.getPasswordUsuario();
        String urlFirma = accessRequestInFirmaDigital.getAction();
        String motivo = null;
        if (visto) {
            motivo = "VB_REEMPLAZO";
        }

        // Log de los parámetros de la firma
        logger.info("idArchivoSiged {}", idArchivoSiged);
        logger.info("usuarioSiged {}", usuarioSiged);
        logger.info("passwordSiged {}", passwordSiged);
        logger.info("urlFirma {}", urlFirma);
        logger.info("motivo {}", motivo);

        try {
            // Llamada al método para procesar la firma
            Map<String, Object>  resultado = procesarFirma(Integer.valueOf(idArchivoSiged), usuarioSiged, passwordSiged, urlFirma, motivo);
            logger.info("Resultado proceso firma: {}", resultado);
            return resultado;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            logger.info("Error HTTP al invocar proceso de firma: {} - {}", ex.getStatusCode(), ex.getResponseBodyAsString(), ex);
            throw new ValidacionException(
                    Constantes.CODIGO_MENSAJE.ERROR_COMUNICACION_FIRMA);
        } catch (Exception ex) {
            logger.info("Error inesperado en proceso de firma", ex);
            throw new ValidacionException(
                    Constantes.CODIGO_MENSAJE.ERROR_INESPERADO_FIRMA);
        }
    }

    @Override
    public Map<String, Object> procesarFirma(Integer idArchivoSiged, String usuarioSiged, String passwordSiged, String urlFirma, String motivo) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Crear el cuerpo de la solicitud
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("loginUsuario", usuarioSiged);
        body.add("passwordUsuario", passwordSiged);
        body.add("archivosFirmar", idArchivoSiged.toString());

        if (motivo != null) {
            body.add("motivo", motivo);
        }

        // Crear la entidad de la solicitud
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        // Llamada al servicio de firma digital
        ResponseEntity<String> response = restTemplate.exchange(urlFirma, HttpMethod.POST, requestEntity, String.class);

        // Log de la respuesta recibida
        logger.info("Respuesta recibida del servicio de firma: {}", response);
        logger.info("Respuesta body: {}", response.getBody());

        // Manejo de cookies
        List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
        String cookieId = Optional.ofNullable(cookies)
                .orElse(Collections.emptyList())
                .stream()
                .filter(cookie -> cookie.startsWith("JSESSIONID"))
                .findFirst()
                .map(cookie -> cookie.split(";")[0].split("=")[1])
                .orElse("");

        logger.info("Cookie obtenida: {}", cookieId);

        // Obtener el contenido HTML de la respuesta
        String contenidoHtml = response.getBody();
        logger.debug("Contenido HTML recibido:\n{}", contenidoHtml);

        // Preparar el mapa de respuesta
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("cookie", cookieId);
        responseBody.put("html", contenidoHtml);
        return responseBody;
    }

    @Override
    @Transactional
    public Map<String, Object> finalizarFirma(FirmaRequestDTO firmaRequestDTO, Contexto contexto) {
        logger.info("Inicio proceso finalizar firma para adenda con ID: {}",firmaRequestDTO.getIdAdenda());
        AdendaReemplazo adenda = getAdendaReemplazo(firmaRequestDTO.getIdAdenda());
        try {
            AccessRequestInFirmaDigital accessRequestInFirmaDigital = sigedOldConsumer.obtenerParametrosfirmaDigital();
            boolean motivo = firmaRequestDTO.isVisto();

            String url = construirUrl(accessRequestInFirmaDigital.getFinalizarAction(),motivo);
            HttpHeaders headers = construirHeaders(firmaRequestDTO.getCookie());

            ResponseEntity<String> response = enviarSolicitudExterna(url,headers);
            Map<String,Object> responseBodyMap = procesarRespuesta(response);

            if (verificarFinalizacion(responseBodyMap)){
                procesarFirmaAdenda(firmaRequestDTO, adenda, contexto);
            }

            Map<String, Object> responseBody = new HashMap<>();
            if (responseBodyMap != null && responseBodyMap.containsKey(ARCHIVOS_KEY)) {
                responseBody.put(ARCHIVOS_KEY, responseBodyMap.get(ARCHIVOS_KEY));
            } else {
                responseBody.put(ARCHIVOS_KEY, Collections.emptyList());
            }
            return responseBody;
        } catch (Exception ex){
            logger.error("Error inesperado en proceso de finalizar firma", ex);
            throw new ValidacionException(
                    Constantes.CODIGO_MENSAJE.ERROR_INESPERADO_FIRMA);
        }
    }

    @Override
    @Transactional
    public AdendaReemplazo finalizarFirmaAdenda(FirmaRequestDTO firmaRequestDTO, Contexto contexto) {
        logger.info("Inicio proceso finalizar para adenda con ID: {}",firmaRequestDTO.getIdAdenda());
        AdendaReemplazo adenda = getAdendaReemplazo(firmaRequestDTO.getIdAdenda());
        try {
            //Vamos actualizar adenda el flag visto bueno
            String listadoAprobacionAdenda = Constantes.LISTADO.ESTADO_ADENDA.CODIGO;
            String listadoEstadoSolicitud = Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO;
            String listadoTipoAprob = Constantes.LISTADO.TIPO_APROBACION.CODIGO;

            String descAprobacionAdenda = Constantes.LISTADO.ESTADO_ADENDA.APROBADO;
            String descAsignado = Constantes.LISTADO.ESTADO_ADENDA.ASIGNADO;
            String descConcluido = Constantes.LISTADO.ESTADO_SOLICITUD.CONCLUIDO;


            ListadoDetalle estadoAproAdenda = listadoDetalleDao.obtenerListadoDetalle(listadoAprobacionAdenda, descAprobacionAdenda);
            ListadoDetalle estadoAsig = listadoDetalleDao.obtenerListadoDetalle(listadoAprobacionAdenda, descAsignado);
            ListadoDetalle estadoConcluido = listadoDetalleDao.obtenerListadoDetalle(listadoEstadoSolicitud,descConcluido);

            logger.info("firmaRequest:{}",firmaRequestDTO);

            if (Boolean.TRUE.equals(firmaRequestDTO.isVisto())){ //Visto bueno
                adenda.setEstadoVbGaf(estadoAproAdenda);
                adenda.setEstadoFirmaJefe(estadoAsig);
                adenda.setObservacionVb(firmaRequestDTO.getObservacion());
                ListadoDetalle tipoFirma = listadoDetalleDao.obtenerListadoDetalle(
                        listadoTipoAprob,Constantes.LISTADO.TIPO_APROBACION.FIRMAR);
                //Buscar Aprobacion
                procesarAprobacion(adenda.getIdReemplazoPersonal(),
                        Constantes.ROLES.G3_APROBADOR_ADMINISTRATIVO,contexto,null,tipoFirma);
                //Notificacion
                notificarAprobacionPendiente(adenda.getIdReemplazoPersonal(),
                        Constantes.ROLES.G3_APROBADOR_ADMINISTRATIVO,
                        Constantes.CODIGO_MENSAJE.USUARIO_G3_NO_EXISTE,
                        contexto);
            } else { //Firma
                logger.info("firma_jefe {}", firmaRequestDTO.isFirmaJefe());

                if (Boolean.TRUE.equals(firmaRequestDTO.isFirmaJefe())){
                    adenda.setEstadoFirmaJefe(estadoAproAdenda);
                    adenda.setEstadoFirmaGerencia(estadoAsig);
                    adenda.setObservacionFirmaJefe(firmaRequestDTO.getObservacion());
                    //Buscar Aprobacion
                    procesarAprobacion(adenda.getIdReemplazoPersonal(),
                            Constantes.ROLES.G4_APROBADOR_ADMINISTRATIVO,contexto,null,null);
                    //Notificacion
                    notificarAprobacionPendiente(adenda.getIdReemplazoPersonal(),
                            Constantes.ROLES.G4_APROBADOR_ADMINISTRATIVO,
                            Constantes.CODIGO_MENSAJE.USUARIO_G4_NO_EXISTE,
                            contexto);
                }
                if (Boolean.TRUE.equals(firmaRequestDTO.isFirmaGerente())){
                    actualizarPersonalReemplazoYNotificar(adenda,estadoConcluido,contexto);
                    adenda.setEstadoFirmaGerencia(estadoAproAdenda);
                    adenda.setEstadoAprobacion(estadoConcluido);
                    adenda.setObservacionFirmaGerencia(firmaRequestDTO.getObservacion());
                }
            }
            return adendaReemplazoService.actualizar(adenda,contexto);
        } catch (Exception ex){
            logger.error("Error inesperado en proceso de finalizar firma", ex);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ERROR_INESPERADO_FIRMA);
        }
    }

    @Override
    @Transactional
    public AdendaReemplazo actualizar(AdendaReemplazo adendaReemplazo, Contexto contexto) {
        logger.info("Iniciando actualizacion de adenda con ID: {}",adendaReemplazo.getIdAdenda());
        Optional<AdendaReemplazo> adendaExiste = adendaReemplazoDao.findById(adendaReemplazo.getIdAdenda());
        if (!adendaExiste.isPresent()){
            throw new ValidacionException(
                    Constantes.CODIGO_MENSAJE.ADENDA_NO_EXISTE);
        }
        AdendaReemplazo adenda = adendaExiste.get();
        if (adendaReemplazo.getIdReemplazoPersonal() != null) {
            adenda.setIdReemplazoPersonal(adendaReemplazo.getIdReemplazoPersonal());
        }
        if (adendaReemplazo.getEstadoAprobacion() != null) {
            adenda.setEstadoAprobacion(adendaReemplazo.getEstadoAprobacion());
        }
        if (adendaReemplazo.getEstadoAprLogistica() != null) {
            adenda.setEstadoAprLogistica(adendaReemplazo.getEstadoAprLogistica());
        }
        if (adendaReemplazo.getEstadoVbGaf() != null) {
            adenda.setEstadoVbGaf(adendaReemplazo.getEstadoVbGaf());
        }
        if (adendaReemplazo.getEstadoFirmaJefe() != null) {
            adenda.setEstadoFirmaJefe(adendaReemplazo.getEstadoFirmaJefe());
        }
        if (adendaReemplazo.getEstadoFirmaGerencia() != null) {
            adenda.setEstadoFirmaGerencia(adendaReemplazo.getEstadoFirmaGerencia());
        }
        if (adendaReemplazo.getIdDocumento() != null) {
            adenda.setIdDocumento(adendaReemplazo.getIdDocumento());
        }
        if (adendaReemplazo.getObservacionVb() != null) {
            adenda.setObservacionVb(adendaReemplazo.getObservacionVb());
        }
        if (adendaReemplazo.getObservacionFirmaJefe()!= null) {
            adenda.setObservacionFirmaJefe(adendaReemplazo.getObservacionFirmaJefe());
        }
        if (adendaReemplazo.getObservacionFirmaGerencia()!= null) {
            adenda.setObservacionFirmaGerencia(adendaReemplazo.getObservacionFirmaGerencia());
        }

        AuditoriaUtil.setAuditoriaActualizacion(adenda,contexto);
        return adendaReemplazoDao.save(adenda);
    }

    @Override
    @Transactional
    public AdendaReemplazo rechazarVisto(AdendaReemplazo adendaReemplazo, Contexto contexto) {
        String listadoAprobacion = Constantes.LISTADO.ESTADO_APROBACION.CODIGO;
        String descAprobacion = Constantes.LISTADO.ESTADO_APROBACION.DESAPROBADO;
        ListadoDetalle estadoApro = listadoDetalleDao.obtenerListadoDetalle(listadoAprobacion, descAprobacion);
        adendaReemplazo.setEstadoAprobacion(estadoApro);
        adendaReemplazo.setEstadoVbGaf(estadoApro);
        return adendaReemplazoService.actualizar(adendaReemplazo,contexto);
    }

    @Override
    @Transactional
    public AdendaReemplazo rechazarFirma(AdendaReemplazo adendaReemplazo, boolean firmaJefe, boolean firmaGerente,
                                         Contexto contexto) {
        String listadoAprobacion = Constantes.LISTADO.ESTADO_APROBACION.CODIGO;
        String descAprobacion = Constantes.LISTADO.ESTADO_APROBACION.DESAPROBADO;
        ListadoDetalle estadoApro = listadoDetalleDao.obtenerListadoDetalle(listadoAprobacion, descAprobacion);
        if (firmaJefe){
            adendaReemplazo.setEstadoAprobacion(estadoApro);
            adendaReemplazo.setEstadoFirmaJefe(estadoApro);
        }
        if (firmaGerente){
            adendaReemplazo.setEstadoAprobacion(estadoApro);
            adendaReemplazo.setEstadoFirmaGerencia(estadoApro);
        }
        return adendaReemplazoService.actualizar(adendaReemplazo,contexto);
    }

    private ResponseEntity<String> enviarSolicitudExterna(String url, HttpHeaders headers) {
        logger.info("Enviando solicitud al servicio externo...");
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        logger.info("url {}",url);
        logger.info("http_metodo {}",HttpMethod.POST);
        logger.info("entity {}",requestEntity);
        logger.info("clase {}",String.class);

        ResponseEntity<String> response =  restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, String.class);
        logger.info("Respuesta de finalizar fimar {}",response);
        logger.info("Estado {}",response.getStatusCode());
        logger.info("Body {}",response.getBody());
        return response;
    }

    private Map<String, Object> procesarRespuesta(ResponseEntity<String> response) throws JsonProcessingException {
        logger.info("Procesar respuesta recibida: {}", response);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
    }

    private boolean verificarFinalizacion(Map<String, Object> responseBodyMap) {
        boolean finalizar = true;
        if (responseBodyMap != null && responseBodyMap.containsKey(ARCHIVOS_KEY)) {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> archivos = (List<Map<String, Object>>) responseBodyMap.get(ARCHIVOS_KEY);
            boolean algunError = archivos.stream()
                    .anyMatch(archivo -> "N".equals(archivo.get("firmaExitosa")));
            if (algunError) {
                finalizar = false;
            }
        }
        return finalizar;
    }

    private String construirUrl(String urlFinalizar, boolean motivo) {
        String url = urlFinalizar + motivo;
        logger.info("URL construida: {}", url);
        return url;
    }

    private HttpHeaders construirHeaders(String cookie) {
        String cookieFinal = "JSESSIONID=" + cookie;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.COOKIE, cookieFinal);
        logger.info("Encabezados configurados: {}", headers);
        return headers;
    }

    private String obtenerNumeroExpediente(PersonalReemplazo personalReemplazo) {
        if (personalReemplazo == null) {
            return "";
        }
        SicoesSolicitud solicitud = sicoesSolicitudDao.findById(personalReemplazo.getIdSolicitud())
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_EXISTE));
        return Optional.ofNullable(solicitud.getNumeroExpediente())
                .orElse("");
    }

    private void procesarAprobacion(Long idReemplazo, String codigoRol, Contexto contexto,
                                    ListadoDetalle estadoAprob, ListadoDetalle tipoAprob) {
        Aprobacion aprobacion = aprobacionDao.findByRemplazoPersonal(idReemplazo)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.APROB_REEMPLAZO_NO_EXISTE));

        Rol rolUsuarioInterno = rolDao.obtenerCodigo(codigoRol);
        aprobacion.setIdRol(rolUsuarioInterno.getIdRol());
        if (estadoAprob != null) {
            aprobacion.setEstadoAprob(estadoAprob);
        }
        if (tipoAprob != null){
            aprobacion.setCoTipoAprobacion(tipoAprob);
        }
        AuditoriaUtil.setAuditoriaActualizacion(aprobacion, contexto);
        aprobacionDao.save(aprobacion);
    }

    private AdendaReemplazo getAdendaReemplazo(Long idAdenda) {
        Optional<AdendaReemplazo> adendaReemplazo = adendaReemplazoDao.findById(idAdenda);
        if (!adendaReemplazo.isPresent()) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ADENDA_NO_EXISTE);
        }
        return adendaReemplazo.get();
    }

    private DocumentoReemplazo getDocumentoReemplazo(AdendaReemplazo adendaReemplazo) {
        Long idDocumento = adendaReemplazo.getIdDocumento();
        Optional<DocumentoReemplazo> documento = documentoReemDao.findById(idDocumento);
        if (!documento.isPresent()) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.DOCUMENTO_REEMPLAZO_NO_EXISTE);
        }
        return documento.get();
    }

    private void verifyArchivoSiged(String idArchivoSiged) {
        if (idArchivoSiged == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_ARCHIVO_SIGED_NO_ENCONTRADO);
        }
    }

    private ListadoDetalle getEstadoAprobacion() {
        String listadoAprobacion = Constantes.LISTADO.ESTADO_APROBACION.CODIGO;
        String descAprobacion = Constantes.LISTADO.ESTADO_APROBACION.APROBADO;
        return listadoDetalleDao.obtenerListadoDetalle(listadoAprobacion, descAprobacion);
    }

    private void verifyFirmaOrVisto(boolean visto, boolean firmaJefe, boolean firmaGerente,
                                    AdendaReemplazo adendaReemplazo, ListadoDetalle estadoApro) {
        if (visto) {
            if (estadoApro.equals(adendaReemplazo.getEstadoVbGaf())) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.VISTO_BUENO_APROBADO);
            }
        } else {
            if (firmaJefe && estadoApro.equals(adendaReemplazo.getEstadoFirmaJefe())) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.FIRMA_APROBADA);
            }
            if (firmaGerente && estadoApro.equals(adendaReemplazo.getEstadoFirmaGerencia())) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.FIRMA_APROBADA);
            }
        }
    }

    private void procesarFirmaAdenda(FirmaRequestDTO firmaRequestDTO, AdendaReemplazo adenda, Contexto contexto) {
        String listadoAprobacion = Constantes.LISTADO.ESTADO_APROBACION.CODIGO;
        String descAprobacion = Constantes.LISTADO.ESTADO_APROBACION.APROBADO;
        ListadoDetalle estadoApro = listadoDetalleDao.obtenerListadoDetalle(listadoAprobacion, descAprobacion);
        String listadoEstadoSolicitud = Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO;
        String descConcluido = Constantes.LISTADO.ESTADO_SOLICITUD.CONCLUIDO;
        ListadoDetalle estadoConcluido = listadoDetalleDao.obtenerListadoDetalle(listadoEstadoSolicitud, descConcluido);

        if (firmaRequestDTO.isVisto()) {
            procesarVistoBueno(firmaRequestDTO, adenda, estadoApro, estadoConcluido, contexto);
        } else {
            procesarFirma(firmaRequestDTO, adenda, estadoApro, estadoConcluido, contexto);
        }

        adendaReemplazoService.actualizar(adenda, contexto);
    }

    private void procesarVistoBueno(FirmaRequestDTO firmaRequestDTO, AdendaReemplazo adenda,
                                    ListadoDetalle estadoApro, ListadoDetalle estadoConcluido, Contexto contexto) {
        adenda.setEstadoVbGaf(estadoApro);
        adenda.setEstadoFirmaJefe(estadoConcluido);
        adenda.setObservacionVb(firmaRequestDTO.getObservacion());

        notificarAprobacionPendiente(adenda.getIdReemplazoPersonal(),
                Constantes.ROLES.G3_APROBADOR_ADMINISTRATIVO,
                Constantes.CODIGO_MENSAJE.USUARIO_G3_NO_EXISTE,
                contexto);
    }

    private void procesarFirma(FirmaRequestDTO firmaRequestDTO, AdendaReemplazo adenda,
                               ListadoDetalle estadoApro, ListadoDetalle estadoConcluido, Contexto contexto) {
        if (firmaRequestDTO.isFirmaJefe()) {
            adenda.setEstadoFirmaJefe(estadoApro);
            adenda.setEstadoFirmaGerencia(estadoConcluido);
            adenda.setObservacionFirmaJefe(firmaRequestDTO.getObservacion());

            notificarAprobacionPendiente(adenda.getIdReemplazoPersonal(),
                    Constantes.ROLES.G4_APROBADOR_ADMINISTRATIVO,
                    Constantes.CODIGO_MENSAJE.USUARIO_G4_NO_EXISTE,
                    contexto);
        }

        if (firmaRequestDTO.isFirmaGerente()) {
            actualizarPersonalReemplazoYNotificar(adenda, estadoConcluido, contexto);
            adenda.setEstadoFirmaGerencia(estadoApro);
            adenda.setEstadoAprobacion(estadoConcluido);
            adenda.setObservacionFirmaGerencia(firmaRequestDTO.getObservacion());
        }
    }

    private void notificarAprobacionPendiente(Long idReemplazoPersonal, String codigoRol, String mensajeRol,Contexto contexto) {
        PersonalReemplazo personalReemplazo = reemplazoDao.findById(idReemplazoPersonal)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REEMPLAZO_PERSONAL_NO_EXISTE));

        Optional<Usuario> usuario = usuarioRolDao.obtenerUsuariosRol(codigoRol)
                .stream()
                .findFirst()
                .map(rol -> usuarioDao.obtener(rol.getUsuario().getIdUsuario()));

        if (usuario.isPresent()) {
            String numeroExpediente = this.obtenerNumeroExpediente(personalReemplazo);
            notificacionContratoService.notificarAprobacionPendiente(usuario.get(), numeroExpediente, contexto);
        } else {
            throw new ValidacionException(mensajeRol);
        }
    }

    private void actualizarPersonalReemplazoYNotificar(AdendaReemplazo adenda, ListadoDetalle estadoConcluido, Contexto contexto) {
        Optional<PersonalReemplazo> personalReemplazo = reemplazoDao.findById(adenda.getIdReemplazoPersonal());
        if (!personalReemplazo.isPresent()) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.REEMPLAZO_PERSONAL_NO_EXISTE);
        }

        PersonalReemplazo personalExiste = personalReemplazo.get();
        personalExiste.setEstadoRevisarEval(estadoConcluido);
        personalExiste.setFeFechaBaja(new Date()); // Verificar

        // Guardando cambio histórico de estado
        Supervisora personalBaja = personalExiste.getPersonaBaja();
        SupervisoraMovimiento movi = new SupervisoraMovimiento();
        PropuestaProfesional profesional = propuestaProfesionalDao.listarXSolicitud(
                personalExiste.getIdSolicitud(), personalBaja.getIdSupervisora());

        if (profesional != null) {
            profesional.setSupervisora(personalBaja);
            movi.setSector(profesional.getSector());
            movi.setSubsector(profesional.getSubsector());
            movi.setPropuestaProfesional(profesional);
        }

        movi.setSupervisora(personalBaja); // Asignando código de personal baja
        movi.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUP_PERFIL.CODIGO, Constantes.LISTADO.ESTADO_SUP_PERFIL.ACTIVO));
        movi.setTipoMotivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_MOTIVO_BLOQUEO.CODIGO, Constantes.LISTADO.TIPO_MOTIVO_BLOQUEO.AUTOMATICO));
        movi.setMotivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.MOTIVO_BLOQUEO_DESBLOQUEO.CODIGO, Constantes.LISTADO.MOTIVO_BLOQUEO_DESBLOQUEO.REEMPLAZO_PERSONAL));
        movi.setAccion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ACCION_BLOQUEO_DESBLOQUEO.CODIGO, Constantes.LISTADO.ACCION_BLOQUEO_DESBLOQUEO.DESBLOQUEO));
        movi.setFechaRegistro(new Date());

        supervisoraMovimientoService.guardar(movi, contexto);
        personalReemplazoService.actualizar(personalExiste, contexto);
    }

}
