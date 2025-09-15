package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import gob.osinergmin.siged.remote.rest.ro.in.DocumentoAnularInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoAnularOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformePresupuestalCreateRequest;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformePresupuestalCreateResponse;
import pe.gob.osinergmin.sicoes.util.Contexto;

/**
 * Servicio para la gestión de informes presupuestales.
 * 
 * Esta interfaz define los métodos para gestionar documentos
 * asociados a informes presupuestales en el sistema.
 */
public interface InformePresupuestalService {
    /**
     * Sube un documento al sistema.
     *
     * @param requestDTO DTO con los datos del documento a subir
     * @param contexto Contexto de la operación
     * @return Respuesta con los datos del documento subido
     * @throws Exception Si ocurre algún error durante la operación
     */
    InformePresupuestalCreateResponse uploadDocument(
            Long idRequerimientoAprobacion,
            MultipartFile file,
            Contexto contexto) throws Exception;

    /**
     * Anula un documento en el sistema SIGED.
     *
     * @param requestDTO DTO con los datos del documento a anular
     * @param contexto Contexto de la operación
     * @return Respuesta con los datos del documento anulado
     * @throws Exception Si ocurre algún error durante la operación
     */
    InformePresupuestalCreateResponse anularDocumentoSiged(Long idRequerimientoAprobacion, Contexto contexto) throws Exception;
}
