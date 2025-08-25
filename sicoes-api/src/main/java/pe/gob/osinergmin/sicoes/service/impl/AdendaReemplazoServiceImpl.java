package pe.gob.osinergmin.sicoes.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.model.dto.FirmaRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.IdsDocumentoArchivoDTO;
import pe.gob.osinergmin.sicoes.repository.*;
import pe.gob.osinergmin.sicoes.service.*;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.bean.siged.AccessRequestInFirmaDigital;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdendaReemplazoServiceImpl implements AdendaReemplazoService {

    Logger logger = LogManager.getLogger(AdendaReemplazoServiceImpl.class);

    @Autowired
    private AdendaReemplazoDao adendaReemplazoDao;

    @Autowired
    private PersonalReemplazoDao reemplazoDao;

    @Autowired
    private PersonalReemplazoService personalReemplazoService;

    @Autowired
    private SupervisoraMovimientoService supervisoraMovimientoService;

    @Autowired
    private ListadoDetalleDao listadoDetalleDao;

    @Autowired
    private DocumentoReemDao documentoReemDao;

    @Autowired
    private EvaluarDocuReemDao evaluarDocuReemDao;

    @Autowired
    private SigedOldConsumer sigedOldConsumer;

    @Autowired
    private SigedApiConsumer sigedApiConsumer;

    @Autowired
    private ArchivoService archivoService;

    @Autowired
    private DocumentoReemService documentoReemService;

    @Autowired
    private PropuestaProfesionalDao propuestaProfesionalDao;

    @Autowired
    private ListadoDetalleService listadoDetalleService;

    @Autowired
    private UsuarioRolDao usuarioRolDao;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private SolicitudDao solicitudDao;

    @Autowired
    private PerfilAprobadorDao perfilAprobadorDao;

    @Autowired
    private AprobacionDao aprobacionDao;

    @Autowired
    private NotificacionContratoService notificacionContratoService;

    @Autowired
    private SicoesSolicitudDao sicoesSolicitudDao;

    @Autowired
    private RolDao rolDao;

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
        Aprobacion aprobacion = aprobacionDao.findByRemplazoPersonal(personalReemplazo.getIdReemplazo())
                .orElseThrow(()-> new ValidacionException(Constantes.CODIGO_MENSAJE.APROB_REEMPLAZO_NO_EXISTE));
        Rol rolUsuarioInterno = rolDao.obtenerCodigo(Constantes.ROLES.EVALUADOR_CONTRATOS);
        aprobacion.setIdRol(rolUsuarioInterno.getIdRol());
        AuditoriaUtil.setAuditoriaActualizacion(aprobacion,contexto);
        aprobacion.setEstadoAprob(estadoAproGeneral);
        aprobacionDao.save(aprobacion);

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
        List<File> archivosAlfresco=null;
        for (DocumentoReemplazo documento : documentos) {
            ExpedienteInRO expedienteInRO = personalReemplazoService.crearExpedienteAgregarDocumentos(solicitud, contexto);
            archivosAlfresco = archivoService.obtenerArchivosPorIdDocumentoReem(documento.getIdDocumento(), contexto);
            try {
                DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO,archivosAlfresco);
                if (documentoOutRO.getResultCode() != 1){
                    throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_CREAR_EXPEDIENTE,
                            documentoOutRO.getMessage());
                }
                //Buscamos los id de los archivos de SIGED
                IdsDocumentoArchivoDTO idsDocumentoArchivoDTO;
                String nombreDocumento = archivosAlfresco.get(0).getName();
                idsDocumentoArchivoDTO = sigedOldConsumer.obtenerIdArchivo(solicitud.getNumeroExpediente(),
                        contexto.getUsuario().getUsuario(),nombreDocumento);
                documento.setIdArchivoSiged(String.valueOf(idsDocumentoArchivoDTO.getIdArchivo()));
                documento.setIdDocumentoSiged (String.valueOf(idsDocumentoArchivoDTO.getIdDocumento()));
                documentoReemService.actualizar(documento,contexto);
            } catch (ValidacionException e) {
                throw e;
            } catch (Exception e) {
                logger.error("ERROR {} ", e.getMessage(), e);
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_CREAR_EXPEDIENTE);
            }
        }
        return adendaReemplazoDao.save(adenda);
    }

    @Override
    public AdendaReemplazo obtener(Long aLong, Contexto contexto) {
        return null;
    }

    @Override
    public void eliminar(Long aLong, Contexto contexto) {

    }

    @Override
    public Map<String, Object> iniciarFirma(Long idAdenda, Boolean visto,
                                             Boolean firmaJefe, Boolean firmaGerente) {
        logger.info("Inicio proceso de firma para adenda con ID: {}", idAdenda);

        Optional<AdendaReemplazo> adendaReemplazo = adendaReemplazoDao.findById(idAdenda);
        if (!adendaReemplazo.isPresent()){
            throw new ValidacionException(
                    Constantes.CODIGO_MENSAJE.ADENDA_NO_EXISTE);
        }

        Long idDocumento = adendaReemplazo.get().getIdDocumento();
        Optional<DocumentoReemplazo> documento = documentoReemDao.findById(idDocumento);
        if (!documento.isPresent()){
            throw new ValidacionException(
                    Constantes.CODIGO_MENSAJE.DOCUMENTO_REEMPLAZO_NO_EXISTE);
        }
        String idArchivoSiged = documento.get().getIdArchivoSiged();
        // Verificación de la existencia del archivo
        if (idArchivoSiged == null) {
            throw new ValidacionException(
                    Constantes.CODIGO_MENSAJE.ID_ARCHIVO_SIGED_NO_ENCONTRADO);
        }

        String listadoAprobacion = Constantes.LISTADO.ESTADO_APROBACION.CODIGO;
        String descAprobacion = Constantes.LISTADO.ESTADO_APROBACION.APROBADO;
        ListadoDetalle estadoApro = listadoDetalleDao.obtenerListadoDetalle(listadoAprobacion, descAprobacion);
        if (visto){
            if (estadoApro.equals(adendaReemplazo.get().getEstadoVbGaf())){
                throw new ValidacionException(
                        Constantes.CODIGO_MENSAJE.VISTO_BUENO_APROBADO);
            }
        } else {
            if (firmaJefe){
                if (estadoApro.equals(adendaReemplazo.get().getEstadoFirmaJefe())){
                    throw new ValidacionException(
                            Constantes.CODIGO_MENSAJE.FIRMA_APROBADA);
                }
            }
            if (firmaGerente){
                if (estadoApro.equals(adendaReemplazo.get().getEstadoFirmaGerencia())){
                    throw new ValidacionException(
                            Constantes.CODIGO_MENSAJE.FIRMA_APROBADA);
                }
            }
        }

        AccessRequestInFirmaDigital accessRequestInFirmaDigital = sigedOldConsumer.obtenerParametrosfirmaDigital();

        // Propiedades de la firma digital
        String usuarioSiged = accessRequestInFirmaDigital.getLoginUsuario();
        String passwordSiged = accessRequestInFirmaDigital.getPasswordUsuario();
        String urlFirma = accessRequestInFirmaDigital.getAction();
        String motivo = null;
        if (visto){
            motivo = "VB_REEMPLAZO";
        }

        // Log de los parámetros de la firma
        logger.info("--- Enviando valores para firma ---");
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
        Optional<AdendaReemplazo> adendaReemplazo = adendaReemplazoDao.findById(firmaRequestDTO.getIdAdenda());
        if (!adendaReemplazo.isPresent()){
            throw new ValidacionException(
                    Constantes.CODIGO_MENSAJE.ADENDA_NO_EXISTE);
        }
        AdendaReemplazo adenda = adendaReemplazo.get();

        try {
            AccessRequestInFirmaDigital accessRequestInFirmaDigital = sigedOldConsumer.obtenerParametrosfirmaDigital();
            boolean motivo = firmaRequestDTO.getVisto();

            String url = construirUrl(accessRequestInFirmaDigital.getFinalizarAction(),motivo);
            HttpHeaders headers = construirHeaders(firmaRequestDTO.getCookie());

            logger.info("url {}",url);
            logger.info("headers {}",headers);

            ResponseEntity<String> response = enviarSolicitudExterna(url,headers);
            logger.info("response {}",response);

            Map<String,Object> responseBodyMap = procesarRespuesta(response);
            logger.info("responseBodyMap {}",responseBodyMap);

            boolean finalizar = verificarFinalizacion(responseBodyMap);
            if (finalizar){
                //Vamos actualizar adenda el flag visto bueno
                String listadoAprobacion = Constantes.LISTADO.ESTADO_APROBACION.CODIGO;
                String listadoEstadoSolicitud = Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO;
                String descAprobacion = Constantes.LISTADO.ESTADO_APROBACION.APROBADO;
                String descAsignado = Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO;
                String descConcluido = Constantes.LISTADO.ESTADO_SOLICITUD.CONCLUIDO;
                ListadoDetalle estadoApro = listadoDetalleDao.obtenerListadoDetalle(listadoAprobacion, descAprobacion);
                ListadoDetalle estadoAsig = listadoDetalleDao.obtenerListadoDetalle(listadoAprobacion, descAsignado);
                ListadoDetalle estadoConcluido = listadoDetalleDao.obtenerListadoDetalle(listadoEstadoSolicitud,descConcluido);

                if (firmaRequestDTO.getVisto()){ //Visto bueno
                    adenda.setEstadoVbGaf(estadoApro);
                    adenda.setEstadoFirmaJefe(estadoAsig);
                    adenda.setObservacionVb(firmaRequestDTO.getObservacion());

                    //Notificacion
                    PersonalReemplazo personalReemplazo = reemplazoDao.findById(adenda.getIdReemplazoPersonal())
                            .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REEMPLAZO_PERSONAL_NO_EXISTE));
                    Optional<Usuario> usuario = usuarioRolDao.obtenerUsuariosRol(Constantes.ROLES.G3_APROBADOR_ADMINISTRATIVO)
                            .stream()
                            .findFirst()
                            .map(rol -> usuarioDao.obtener(rol.getUsuario().getIdUsuario()));
                    if (usuario.isPresent()) {
                        String numeroExpediente = this.obtenerNumeroExpediente(personalReemplazo);
                        notificacionContratoService.notificarAprobacionPendiente(usuario.get(), numeroExpediente, contexto);
                    } else {
                        throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_G3_NO_EXISTE);
                    }
                } else { //Firma
                    if (firmaRequestDTO.getFirmaJefe()){
                        adenda.setEstadoFirmaJefe(estadoApro);
                        adenda.setEstadoFirmaGerencia(estadoAsig);
                        adenda.setObservacionFirmaJefe(firmaRequestDTO.getObservacion());
                        //Notificacion
                        PersonalReemplazo personalReemplazo = reemplazoDao.findById(adenda.getIdReemplazoPersonal())
                                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REEMPLAZO_PERSONAL_NO_EXISTE));
                        Optional<Usuario> usuario = usuarioRolDao.obtenerUsuariosRol(Constantes.ROLES.G4_APROBADOR_ADMINISTRATIVO)
                                .stream()
                                .findFirst()
                                .map(rol -> usuarioDao.obtener(rol.getUsuario().getIdUsuario()));
                        if (usuario.isPresent()) {
                            String numeroExpediente = this.obtenerNumeroExpediente(personalReemplazo);
                            notificacionContratoService.notificarAprobacionPendiente(usuario.get(), numeroExpediente, contexto);
                        } else {
                            throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_G4_NO_EXISTE);
                        }
                    }
                    if (firmaRequestDTO.getFirmaGerente()){
                        Optional<PersonalReemplazo> personalReemplazo = reemplazoDao.findById(adenda.getIdReemplazoPersonal());
                        if (!personalReemplazo.isPresent()){
                            throw new ValidacionException(
                                    Constantes.CODIGO_MENSAJE.REEMPLAZO_PERSONAL_NO_EXISTE);
                        }
                        PersonalReemplazo personalExiste = personalReemplazo.get();
                        personalExiste.setEstadoRevisarEval(estadoConcluido);
                        personalExiste.setFeFechaBaja(new Date()); //Verificar
                        //Guardando cambio historico de estado
                        Supervisora personalBaja = personalExiste.getPersonaBaja();
                        SupervisoraMovimiento movi = new SupervisoraMovimiento();

                        PropuestaProfesional profesional = propuestaProfesionalDao.listarXSolicitud(
                                personalExiste.getIdSolicitud(),personalBaja.getIdSupervisora());
                        profesional.setSupervisora(personalBaja);

                        movi.setSector(profesional.getSector());
                        movi.setSubsector(profesional.getSubsector());
                        movi.setSupervisora(personalBaja); //Asignando codigo de personal baja
                        movi.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUP_PERFIL.CODIGO, Constantes.LISTADO.ESTADO_SUP_PERFIL.ACTIVO));
                        movi.setTipoMotivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_MOTIVO_BLOQUEO.CODIGO, Constantes.LISTADO.TIPO_MOTIVO_BLOQUEO.AUTOMATICO));
                        movi.setMotivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.MOTIVO_BLOQUEO_DESBLOQUEO.CODIGO, Constantes.LISTADO.MOTIVO_BLOQUEO_DESBLOQUEO.REEMPLAZO_PERSONAL));
                        movi.setAccion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ACCION_BLOQUEO_DESBLOQUEO.CODIGO, Constantes.LISTADO.ACCION_BLOQUEO_DESBLOQUEO.DESBLOQUEO));
                        movi.setPropuestaProfesional(profesional);
                        movi.setFechaRegistro(new Date());

                        supervisoraMovimientoService.guardar(movi,contexto);
                        personalReemplazoService.actualizar(personalExiste,contexto);

                        adenda.setEstadoFirmaGerencia(estadoApro);
                        adenda.setEstadoAprobacion(estadoConcluido);
                        adenda.setObservacionFirmaGerencia(firmaRequestDTO.getObservacion());
                    }

                }
                actualizar(adenda,contexto);
            }

            Map<String, Object> responseBody = new HashMap<>();
            if (responseBodyMap != null && responseBodyMap.containsKey("archivos")) {
                responseBody.put("archivos", responseBodyMap.get("archivos"));
            } else {
                responseBody.put("archivos", Collections.emptyList());
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
    public AdendaReemplazo rechazarVisto(AdendaReemplazo adendaReemplazo,Contexto contexto) {
        String listadoAprobacion = Constantes.LISTADO.ESTADO_APROBACION.CODIGO;
        String descAprobacion = Constantes.LISTADO.ESTADO_APROBACION.DESAPROBADO;
        ListadoDetalle estadoApro = listadoDetalleDao.obtenerListadoDetalle(listadoAprobacion, descAprobacion);
        adendaReemplazo.setEstadoAprobacion(estadoApro);
        adendaReemplazo.setEstadoVbGaf(estadoApro);
        return actualizar(adendaReemplazo,contexto);
    }

    @Override
    public AdendaReemplazo rechazarFirma(AdendaReemplazo adendaReemplazo, Boolean firmaJefe, Boolean firmaGerente,
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
        return actualizar(adendaReemplazo,contexto);
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

    private Map<String, Object> procesarRespuesta(ResponseEntity<String> response) throws Exception {
        logger.info("Procesar respuesta recibida: {}", response);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
    }

    private boolean verificarFinalizacion(Map<String, Object> responseBodyMap) {
        boolean finalizar = true;
        if (responseBodyMap != null && responseBodyMap.containsKey("archivos")) {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> archivos = (List<Map<String, Object>>) responseBodyMap.get("archivos");
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
}
