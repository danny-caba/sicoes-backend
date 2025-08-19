package pe.gob.osinergmin.sicoes.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class EstadoUtil {

    private static final Logger logger = LogManager.getLogger(EstadoUtil.class);
    private static Map<String, ListadoDetalle> estadosCache = new HashMap<>();
    private static ListadoDetalleService listadoDetalleServiceStatic;

    @Autowired
    private ListadoDetalleService listadoDetalleService;

    @PostConstruct
    public void init() {
        EstadoUtil.listadoDetalleServiceStatic = this.listadoDetalleService;
        // Inicializamos el caché con los estados comunes
        cargarEstadosNotificacion();
    }

    /**
     * Carga en caché los estados comunes de notificaciones
     */
    private static void cargarEstadosNotificacion() {
        try {
            // Creamos claves únicas combinando el tipo de listado y el código
            String keyPendiente = crearClave(Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
                                           Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
            String keyEnviado = crearClave(Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
                                         Constantes.LISTADO.ESTADO_NOTIFICACIONES.ENVIADO);
            String keyFallado = crearClave(Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
                                         Constantes.LISTADO.ESTADO_NOTIFICACIONES.FALLADO);

            // Obtenemos los objetos de la BD y los guardamos en caché
            estadosCache.put(keyPendiente, listadoDetalleServiceStatic.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
                Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE));

            estadosCache.put(keyEnviado, listadoDetalleServiceStatic.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
                Constantes.LISTADO.ESTADO_NOTIFICACIONES.ENVIADO));

            estadosCache.put(keyFallado, listadoDetalleServiceStatic.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
                Constantes.LISTADO.ESTADO_NOTIFICACIONES.FALLADO));

            logger.info("Estados de notificación cargados en caché");
        } catch (Exception e) {
            logger.error("Error al cargar estados de notificación", e);
        }
    }

    /**
     * Crea una clave única para el mapa de caché
     */
    private static String crearClave(String codigoListado, String codigoDetalle) {
        return codigoListado + ":" + codigoDetalle;
    }

    /**
     * Obtiene un estado desde la caché o de la BD si no está en caché
     */
    public static ListadoDetalle obtenerEstado(String codigoListado, String codigoDetalle) {
        String clave = crearClave(codigoListado, codigoDetalle);

        // Si no está en caché, lo obtenemos de la BD y lo cacheamos
        if (!estadosCache.containsKey(clave)) {
            try {
                ListadoDetalle estado = listadoDetalleServiceStatic.obtenerListadoDetalle(
                    codigoListado, codigoDetalle);
                estadosCache.put(clave, estado);
                return estado;
            } catch (Exception e) {
                logger.error("Error al obtener estado " + clave, e);
                return null;
            }
        }

        return estadosCache.get(clave);
    }

    /**
     * Métodos específicos para estados de notificación
     */
    public static ListadoDetalle getEstadoNotificacionPendiente() {
        return obtenerEstado(Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
                           Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
    }

    public static ListadoDetalle getEstadoNotificacionEnviado() {
        return obtenerEstado(Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
                           Constantes.LISTADO.ESTADO_NOTIFICACIONES.ENVIADO);
    }

    public static ListadoDetalle getEstadoNotificacionFallado() {
        return obtenerEstado(Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
                           Constantes.LISTADO.ESTADO_NOTIFICACIONES.FALLADO);
    }

    /**
     * Limpia la caché (En caso requieren recargar los estados considerar un job)
     */
    public static void limpiarCache() {
        estadosCache.clear();
    }
}