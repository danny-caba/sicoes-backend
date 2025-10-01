package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO para consulta completa de bandeja de aprobaciones con todos los datos necesarios
 */
public interface BandejaAprobacionFullDTO {
    
    // Campos de SICOES_TC_REQ_APROBACION
    Long getIdReqAprobacion();
    Long getIdRequerimiento();
    Long getIdReqInforme();
    Long getIdReqDocumento();
    Long getIdTipoLd();
    Long getIdGrupoLd();
    Long getIdUsuario();
    Long getIdEstadoLd();
    Long getIdFirmadoLd();
    String getDeObservacion();
    Date getFeAprobacion();
    Date getFeRechazo();
    Date getFeFirma();
    Long getIdInformeRenovacion();
    Long getIdNotificacion();
    Date getFeAsignacion();
    Long getIdTipoAprobadorLd();
    Long getIdGrupoAprobadorLd();
    String getUsCreacion();
    String getIpCreacion();
    Date getFeCreacion();
    String getUsActualizacion();
    String getIpActualizacion();
    Date getFeActualizacion();
    
    // Campos de SICOES_TD_INFORME_RENOVACION
    Long getEsVigente();
    Long getEsAprobacionInforme();
    String getDeUuidInfoRenovacion();
    String getDeNombreArchivo();
    
    // Campos de SICOES_TC_REQ_RENOVACION
    String getNuExpediente();
    String getTiSector();
    String getTiSubSector();
    String getNoItem();
    
    // Campos de SICOES_TM_SUPERVISORA
    String getNoRazonSocial();
    Long getIdSupervisora();
}