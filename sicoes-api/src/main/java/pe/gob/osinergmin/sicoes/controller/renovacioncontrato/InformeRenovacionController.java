package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import java.util.Collections;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.RechazoInformeDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.ActualizacionBandejaDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeAprobacionResponseDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.HistorialAprobacionDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.HistorialAprobacionResponseDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.HistorialAprobacionesResponse;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InformeRenovacionService;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/informe/renovacion")
public class InformeRenovacionController extends BaseRestController {

    private Logger logger = LogManager.getLogger(InformeRenovacionController.class);

    @Autowired
    private InformeRenovacionService informeRenovacionService;
    @Autowired
    private NotificacionService notificacionService;
    @Autowired
    private SigedOldConsumer sigedOldConsumer;

    @GetMapping("/adjunto/descargar")
    @Raml("renovacioncontrato.informe.adjunto.descargar.properties")
    public ResponseEntity<Resource> descargarAdjuntoInforme(
            @RequestParam String uuid,
            @RequestParam(required = false) String nombreArchivo) {

        logger.info("descargarAdjuntoInforme - Usuario: {}, UUID: {}, Archivo: {}",
                getContexto().getUsuario().getIdUsuario(), uuid, nombreArchivo);

        try {
            // Validar parámetros de entrada
            if (uuid == null || uuid.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            // Validar permisos del usuario
            if (!validarPermisosDescarga(uuid)) {
                logger.warn("Usuario {} no tiene permisos para descargar archivo UUID: {}",
                        getContexto().getUsuario().getIdUsuario(), uuid);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            // Descargar archivo desde Alfresco
            byte[] contenidoArchivo = informeRenovacionService.descargarAdjuntoDesdeAlfresco(uuid, getContexto());

            if (contenidoArchivo == null || contenidoArchivo.length == 0) {
                logger.warn("Archivo no encontrado o vacío para UUID: {}", uuid);
                return ResponseEntity.notFound().build();
            }

            // Preparar respuesta de descarga
            ByteArrayResource resource = new ByteArrayResource(contenidoArchivo);

            // Determinar nombre del archivo
            String nombreDescarga = (nombreArchivo != null && !nombreArchivo.trim().isEmpty())
                    ? nombreArchivo.trim()
                    : "adjunto_" + uuid + ".pdf";

            // Configurar headers de respuesta
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombreDescarga + "\"");
            headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
            headers.add(HttpHeaders.PRAGMA, "no-cache");
            headers.add(HttpHeaders.EXPIRES, "0");

            // Detectar tipo de contenido
            MediaType mediaType = determinarTipoContenido(nombreDescarga);

            logger.info("Descarga exitosa - Usuario: {}, UUID: {}, Tamaño: {} bytes",
                    getContexto().getUsuario().getIdUsuario(), uuid, contenidoArchivo.length);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(contenidoArchivo.length)
                    .contentType(mediaType)
                    .body(resource);

        } catch (SecurityException e) {
            logger.warn("Error de seguridad al descargar adjunto: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        } catch (IllegalArgumentException e) {
            logger.warn("Error de validación al descargar adjunto: {}", e.getMessage());
            return ResponseEntity.badRequest().build();

        } catch (Exception e) {
            logger.error("Error al descargar adjunto de informe - UUID: " + uuid, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ENDPOINT TEMPORAL PARA TESTING - BYPASA VALIDACIONES
    @GetMapping("/adjunto/descargar-directo")
    public ResponseEntity<Resource> descargarAdjuntoDirecto(
            @RequestParam String uuid,
            @RequestParam(required = false) String nombreArchivo) {

        logger.info("descargarAdjuntoDirecto - UUID: {}, Archivo: {}", uuid, nombreArchivo);

        try {
            // Validar parámetros básicos
            if (uuid == null || uuid.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            // Descargar directamente desde Alfresco SIN validaciones de permisos
            byte[] contenidoArchivo = sigedOldConsumer.descargarArchivoPorUuidAlfresco(uuid);

            if (contenidoArchivo == null || contenidoArchivo.length == 0) {
                logger.warn("Archivo no encontrado o vacío para UUID: {}", uuid);
                return ResponseEntity.notFound().build();
            }

            // Preparar respuesta de descarga
            ByteArrayResource resource = new ByteArrayResource(contenidoArchivo);

            // Determinar nombre del archivo
            String nombreDescarga = (nombreArchivo != null && !nombreArchivo.trim().isEmpty())
                    ? nombreArchivo.trim()
                    : "adjunto_" + uuid + ".pdf";

            // Configurar headers de respuesta
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombreDescarga + "\"");
            headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
            headers.add(HttpHeaders.PRAGMA, "no-cache");
            headers.add(HttpHeaders.EXPIRES, "0");

            // Detectar tipo de contenido
            MediaType mediaType = determinarTipoContenido(nombreDescarga);

            logger.info("Descarga directa exitosa - UUID: {}, Tamaño: {} bytes", uuid, contenidoArchivo.length);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(contenidoArchivo.length)
                    .contentType(mediaType)
                    .body(resource);

        } catch (Exception e) {
            logger.error("Error al descargar adjunto directo - UUID: " + uuid, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private boolean validarPermisosDescarga(String uuid) {
        try {
            // Validar que el usuario esté autenticado
            if (getContexto().getUsuario() == null) {
                return false;
            }

            String usuario = getContexto().getUsuario().getUsuario();
            if (usuario == null) {
                return false;
            }

            // Verificar roles que pueden descargar adjuntos
            return usuario.contains("APROBADOR") ||
                    usuario.contains("TECNICO") ||
                    usuario.contains("ADMIN") ||
                    usuario.contains("EVALUADOR");

        } catch (Exception e) {
            logger.warn("Error al validar permisos de descarga", e);
            return false;
        }
    }

    private MediaType determinarTipoContenido(String nombreArchivo) {
        if (nombreArchivo == null) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }

        String extension = nombreArchivo.toLowerCase();
        if (extension.endsWith(".pdf")) {
            return MediaType.APPLICATION_PDF;
        } else if (extension.endsWith(".doc") || extension.endsWith(".docx")) {
            return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        } else if (extension.endsWith(".xls") || extension.endsWith(".xlsx")) {
            return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        } else if (extension.endsWith(".jpg") || extension.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (extension.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    @GetMapping("/aprobar/buscar")
    @Raml("renovacioncontrato.informe.aprobar.buscar.properties")
    public ResponseEntity<Page<InformeAprobacionResponseDTO>> buscarInformesParaAprobar(
            @RequestParam(required = false) String numeroExpediente,
            @RequestParam(required = false) String tipoSector,
            @RequestParam(required = false) String tipoSubSector,
            @RequestParam(required = false) String nombreItem,
            @RequestParam(required = false) String razSocialSupervisora,
            @RequestParam(required = false) Integer estadoInforme,
            @RequestParam(required = false) Integer grupoAprobador,
            @RequestParam(required = false) Integer prioridad,
            @RequestParam(required = false) String fechaDesde,
            @RequestParam(required = false) String fechaHasta,
            @RequestParam(required = false) Boolean soloVencidos,
            @RequestParam(required = false) Boolean soloAsignados,
            Pageable pageable) {

        logger.info("buscarInformesParaAprobar - GSE Bandeja Aprobaciones - Usuario: {}",
                getContexto().getUsuario().getIdUsuario());

        try {
            // Validar permisos del usuario para la bandeja de aprobaciones
            if (!validarPermisosAprobacion()) {
                logger.warn("Usuario {} no tiene permisos para acceder a la bandeja de aprobaciones",
                        getContexto().getUsuario().getIdUsuario());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            // Buscar informes pendientes de aprobación
            Page<InformeAprobacionResponseDTO> informes = informeRenovacionService.buscarInformesParaAprobar(
                    numeroExpediente, tipoSector, tipoSubSector, nombreItem, razSocialSupervisora,
                    estadoInforme, grupoAprobador, prioridad, fechaDesde, fechaHasta,
                    soloVencidos, soloAsignados, pageable, getContexto());

            logger.info("Búsqueda de informes para aprobar completada - Encontrados: {}, Usuario: {}",
                    informes.getTotalElements(), getContexto().getUsuario().getIdUsuario());

            return ResponseEntity.ok(informes);

        } catch (SecurityException e) {
            logger.warn("Error de seguridad al buscar informes para aprobar: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        } catch (IllegalArgumentException e) {
            logger.warn("Error de validación al buscar informes para aprobar: {}", e.getMessage());
            return ResponseEntity.badRequest().build();

        } catch (Exception e) {
            logger.error("Error al buscar informes para aprobar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private boolean validarPermisosAprobacion() {
        try {
            if (getContexto().getUsuario() == null) {
                return false;
            }

            String usuario = getContexto().getUsuario().getUsuario();
            if (usuario == null) {
                return false;
            }

            // Verificar roles que pueden acceder a la bandeja de aprobaciones
            return usuario.contains("APROBADOR") ||
                    usuario.contains("GSE") ||
                    usuario.contains("TECNICO") ||
                    usuario.contains("ADMIN") ||
                    usuario.contains("SUPERVISOR");

        } catch (Exception e) {
            logger.warn("Error al validar permisos de aprobación", e);
            return false;
        }
    }

    @GetMapping("/historial-aprobaciones")
    @Raml("renovacioncontrato.informe.historial.aprobaciones.properties")
    public ResponseEntity<HistorialAprobacionesResponse> listarHistorialAprobaciones(
            @RequestParam(required = false) String documento_id,
            @RequestParam(required = false) String fecha_desde,
            @RequestParam(required = false) String fecha_hasta,
            @RequestParam(required = false) String resultado,
            @RequestParam(required = false) String grupo,
            Pageable pageable) {

        logger.info("listarHistorialAprobaciones - Historial de Aprobaciones - Usuario: {}, DocumentoId: {}",
                getContexto().getUsuario().getIdUsuario(), documento_id);

        try {
            // Validar permisos del usuario para acceder al historial
            if (!validarPermisosHistorial()) {
                logger.warn("Usuario {} no tiene permisos para acceder al historial de aprobaciones",
                        getContexto().getUsuario().getIdUsuario());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            // Obtener historial de aprobaciones con el nuevo formato
            HistorialAprobacionesResponse response = informeRenovacionService.listarHistorialAprobacionesFormateado(
                    documento_id, fecha_desde, fecha_hasta, resultado, grupo, pageable, getContexto());

            logger.info("Consulta de historial de aprobaciones completada - Total: {}, Usuario: {}",
                    response.getData() != null ? response.getData().getTotalRegistros() : 0,
                    getContexto().getUsuario().getIdUsuario());

            return ResponseEntity.ok(response);

        } catch (SecurityException e) {
            logger.warn("Error de seguridad al consultar historial de aprobaciones: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        } catch (IllegalArgumentException e) {
            logger.warn("Error de validación al consultar historial de aprobaciones: {}", e.getMessage());
            return ResponseEntity.badRequest().build();

        } catch (Exception e) {
            logger.error("Error al consultar historial de aprobaciones", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private boolean validarPermisosHistorial() {
        try {
            if (getContexto().getUsuario() == null) {
                return false;
            }

            String usuario = getContexto().getUsuario().getUsuario();
            if (usuario == null) {
                return false;
            }

            // Verificar roles que pueden acceder al historial de aprobaciones
            return usuario.contains("APROBADOR") ||
                    usuario.contains("GSE") ||
                    usuario.contains("TECNICO") ||
                    usuario.contains("ADMIN") ||
                    usuario.contains("SUPERVISOR") ||
                    usuario.contains("AUDITOR") ||
                    usuario.contains("CONSULTOR");

        } catch (Exception e) {
            logger.warn("Error al validar permisos de historial", e);
            return false;
        }
    }

    @PostMapping("/rechazar")
    @Raml("renovacioncontrato.informe.rechazar.properties")
    public ResponseEntity<Map<String, String>> rechazarInformeRenovacion(@RequestBody RechazoInformeDTO rechazoDTO) {
        logger.info("rechazarInformeRenovacion - Aprobador tecnico G1 y Aprobador tecnico G2");

        try {
            // Validar datos de entrada
            if (rechazoDTO.getIdInformeRenovacion() == null) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("message", "El ID del informe de renovación es requerido"));
            }


            // Procesar el rechazo
            informeRenovacionService.rechazarInforme(rechazoDTO, getContexto());

            return ResponseEntity.ok(Collections.singletonMap("message", "Informe de renovación rechazado exitosamente"));

        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al rechazar informe: " + e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error al rechazar informe de renovación", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error al procesar el rechazo del informe"));
        }
    }

    @PutMapping("/actualizar-bandeja-aprobaciones")
    @Raml("renovacioncontrato.bandeja.aprobaciones.actualizar.properties")
    public ResponseEntity<Map<String, String>> actualizarBandejaAprobaciones(@RequestBody ActualizacionBandejaDTO actualizacionDTO) {
        logger.info("actualizarBandejaAprobaciones - Para actualizar la Bandeja de Aprobaciones");

        try {
            // Validar datos de entrada
            if (actualizacionDTO.getIdRequerimientoAprobacion() == null) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("message", "El ID del requerimiento de aprobación es requerido"));
            }

            // Actualizar bandeja de aprobaciones
            informeRenovacionService.actualizarBandejaAprobaciones(actualizacionDTO, getContexto());

            return ResponseEntity.ok(Collections.singletonMap("message", "Bandeja de aprobaciones actualizada exitosamente"));

        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al actualizar bandeja de aprobaciones: " + e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error al actualizar bandeja de aprobaciones", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error al actualizar la bandeja de aprobaciones"));
        }
    }

    @PutMapping("/actualizar-grilla-renovacion")
    @Raml("renovacioncontrato.grilla.renovacion.actualizar.properties")
    public ResponseEntity<Map<String, String>> actualizarGrillaRenovacion(@RequestBody ActualizacionBandejaDTO actualizacionDTO) {
        logger.info("actualizarGrillaRenovacion - Para actualizar la grilla de Renovación de Contrato");

        try {
            // Validar datos de entrada
            if (actualizacionDTO.getIdRequerimientoRenovacion() == null) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("message", "El ID del requerimiento de renovación es requerido"));
            }

            // Actualizar grilla de renovación de contrato
            informeRenovacionService.actualizarGrillaRenovacionContrato(actualizacionDTO, getContexto());

            return ResponseEntity.ok(Collections.singletonMap("message", "Grilla de renovación de contrato actualizada exitosamente"));

        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al actualizar grilla de renovación: " + e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error al actualizar grilla de renovación de contrato", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error al actualizar la grilla de renovación de contrato"));
        }
    }

    @PostMapping("/aprobar")
    @Raml("renovacioncontrato.informe.aprobar.properties")
    public ResponseEntity<Map<String, String>> aprobarInformeRenovacion(@RequestBody Map<String, Object> requestData) {
        logger.info("aprobarInformeRenovacion - Aprobador de informes de renovación");

        try {
            // Validar datos de entrada
            Object idInformeRenovacionObj = requestData.get("idInformeRenovacion");
            if (idInformeRenovacionObj == null) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("message", "El ID del informe de renovación es requerido"));
            }

            Long idInformeRenovacion;
            try {
                idInformeRenovacion = Long.valueOf(idInformeRenovacionObj.toString());
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("message", "El ID del informe de renovación debe ser un número válido"));
            }

            String observacion = requestData.get("observacion") != null ? 
                requestData.get("observacion").toString() : "";

            Object idUsuarioObj = requestData.get("idUsuario");
            Long idUsuario = null;
            if (idUsuarioObj != null) {
                try {
                    idUsuario = Long.valueOf(idUsuarioObj.toString());
                } catch (NumberFormatException e) {
                    logger.warn("ID de usuario inválido: {}", idUsuarioObj);
                }
            }

            // Procesar la aprobación
            informeRenovacionService.aprobarInforme(idInformeRenovacion, idUsuario, observacion, getContexto());

            return ResponseEntity.ok(Collections.singletonMap("message", "Informe de renovación aprobado exitosamente"));

        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al aprobar informe: " + e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error al procesar la aprobación del informe: " + e.getMessage()));
        }
    }

    @PostMapping("/notifica/{idTipoNotifica}")
    @Raml("renovacioncontrato.informe.notifica.properties")
    public ResponseEntity<Map<String, String>> notificarRenovacion(
            @PathVariable Integer idTipoNotifica,
            @RequestBody(required = false) Map<String, Object> requestData) {
        logger.info("notificarRenovacion - Envío de notificación tipo: {}", idTipoNotifica);

        try {
            // Validar tipo de notificación
            if (idTipoNotifica == null || idTipoNotifica < 1 || idTipoNotifica > 3) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("message", "Tipo de notificación inválido (debe ser 1, 2 o 3)"));
            }

            // Enviar notificación según el tipo
            String mensaje = "";
            switch (idTipoNotifica) {
                case 1:
                    mensaje = "Notificación de aprobación de informe de renovación";
                    break;
                case 2:
                    mensaje = "Notificación de rechazo de informe de renovación";
                    break;
                case 3:
                    mensaje = "Notificación de solicitud de perfeccionamiento de contrato";
                    break;
            }

            // Procesar la notificación usando el servicio existente
            notificacionService.enviarNotificacionRenovacion(idTipoNotifica, mensaje, getContexto());

            return ResponseEntity.ok(Collections.singletonMap("message", "Notificación enviada exitosamente"));

        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al enviar notificación: " + e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error al enviar notificación de renovación", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error al enviar la notificación"));
        }
    }

    @PostMapping("/solicitud-perfeccionamiento-contrato")
    @Raml("renovacioncontrato.informe.solicitud.perfeccionamiento.properties")
    public ResponseEntity<Map<String, String>> solicitudPerfeccionamientoContrato(@RequestBody Map<String, Object> requestData) {
        logger.info("solicitudPerfeccionamientoContrato - Generar solicitud de perfeccionamiento");

        try {
            // Validar datos de entrada
            Object informesIdsObj = requestData.get("informesIds");
            if (informesIdsObj == null) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("message", "Los IDs de los informes son requeridos"));
            }

            @SuppressWarnings("unchecked")
            java.util.List<Long> informesIds = (java.util.List<Long>) informesIdsObj;
            if (informesIds.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("message", "Debe proporcionar al menos un ID de informe"));
            }

            String observacion = requestData.get("observacion") != null ? 
                requestData.get("observacion").toString() : "";

            Object idUsuarioObj = requestData.get("idUsuario");
            Long idUsuario = null;
            if (idUsuarioObj != null) {
                try {
                    idUsuario = Long.valueOf(idUsuarioObj.toString());
                } catch (NumberFormatException e) {
                    logger.warn("ID de usuario inválido: {}", idUsuarioObj);
                }
            }

            String fechaSolicitud = requestData.get("fechaSolicitud") != null ? 
                requestData.get("fechaSolicitud").toString() : 
                java.time.LocalDateTime.now().toString();

            // Procesar la solicitud de perfeccionamiento
            informeRenovacionService.crearSolicitudPerfeccionamientoContrato(
                informesIds, idUsuario, observacion, fechaSolicitud, getContexto());

            return ResponseEntity.ok(Collections.singletonMap("message", "Solicitud de perfeccionamiento de contrato generada exitosamente"));

        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al generar solicitud de perfeccionamiento: " + e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error al generar solicitud de perfeccionamiento de contrato", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error al procesar la solicitud de perfeccionamiento"));
        }
    }

}