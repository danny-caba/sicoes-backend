package pe.gob.osinergmin.sicoes.repository.renovacioncontrato.impl;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.BandejaAprobacionFullDTO;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoAprobacionDaoCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class RequerimientoAprobacionDaoImpl implements RequerimientoAprobacionDaoCustom {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Page<BandejaAprobacionFullDTO> buscarByIdUsuarioConFiltrosDinamicos(
            String numeroExpediente,
            Long estadoAprobacionInforme,
            Long idContratista,
            String nombreContratista,
            Integer idUsuario,
            Pageable pageable) {
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT r.ID_REQ_APROBACION as idReqAprobacion, r.ID_REQUERIMIENTO as idRequerimiento, ");
        sql.append("r.ID_REQ_INFORME as idReqInforme, r.ID_REQ_DOCUMENTO as idReqDocumento, ");
        sql.append("r.ID_TIPO_LD as idTipoLd, r.ID_GRUPO_LD as idGrupoLd, r.ID_USUARIO as idUsuario, ");
        sql.append("r.ID_ESTADO_LD as idEstadoLd, r.ID_FIRMADO_LD as idFirmadoLd, r.DE_OBSERVACION as deObservacion, ");
        sql.append("r.FE_APROBACION as feAprobacion, r.FE_RECHAZO as feRechazo, r.FE_FIRMA as feFirma, ");
        sql.append("r.ID_INFORME_RENOVACION as idInformeRenovacion, r.ID_NOTIFICACION as idNotificacion, ");
        sql.append("r.FE_ASIGNACION as feAsignacion, r.ID_TIPO_APROBADOR_LD as idTipoAprobadorLd, ");
        sql.append("r.ID_GRUPO_APROBADOR_LD as idGrupoAprobadorLd, ");
        sql.append("r.US_CREACION as usCreacion, r.IP_CREACION as ipCreacion, r.FE_CREACION as feCreacion, ");
        sql.append("r.US_ACTUALIZACION as usActualizacion, r.IP_ACTUALIZACION as ipActualizacion, r.FE_ACTUALIZACION as feActualizacion, ");
        sql.append("i.ES_VIGENTE as esVigente, i.ES_APROBACION_INFORME as esAprobacionInforme, ");
        sql.append("i.DE_UUID_INFO_RENOVACION as deUuidInfoRenovacion, i.DE_NOMBRE_ARCHIVO as deNombreArchivo, ");
        sql.append("rr.NU_EXPEDIENTE as nuExpediente, rr.TI_SECTOR as tiSector, rr.TI_SUB_SECTOR as tiSubSector, rr.NO_ITEM as noItem, ");
        sql.append("s.NO_RAZON_SOCIAL as noRazonSocial, s.ID_SUPERVISORA as idSupervisora ");
        sql.append("FROM ES_SICOES.SICOES_TC_REQ_APROBACION r ");
        sql.append("JOIN ES_SICOES.SICOES_TD_INFORME_RENOVACION i ON r.ID_INFORME_RENOVACION = i.ID_INFORME_RENOVACION ");
        sql.append("JOIN ES_SICOES.SICOES_TC_REQ_RENOVACION rr ON i.ID_REQUERIMIENTO = rr.ID_REQ_RENOVACION ");
        sql.append("LEFT JOIN ES_SICOES.SICOES_TC_SOLI_PERF_CONT spc ON rr.ID_SOLI_PERF_CONT = spc.ID_SOLI_PERF_CONT ");
        sql.append("LEFT JOIN ES_SICOES.SICOES_TM_SUPERVISORA s ON spc.ID_SUPERVISORA = s.ID_SUPERVISORA ");
        sql.append("WHERE r.ID_GRUPO_LD = 542 ");
        sql.append("AND r.ID_GRUPO_APROBADOR_LD = 954 ");
        sql.append("AND r.ID_USUARIO = :idUsuario ");
        sql.append("AND r.ID_ESTADO_LD IN (958, 960, 1160) ");
        
        // Agregar condiciones dinámicamente solo si tienen valor
        if (numeroExpediente != null && !numeroExpediente.trim().isEmpty()) {
            sql.append("AND rr.NU_EXPEDIENTE = :numeroExpediente ");
        }
        
        if (estadoAprobacionInforme != null) {
            sql.append("AND i.ES_APROBACION_INFORME = :estadoAprobacionInforme ");
        }
        
        if (idContratista != null) {
            sql.append("AND spc.ID_SUPERVISORA = :idContratista ");
        }
        
        if (nombreContratista != null && !nombreContratista.trim().isEmpty()) {
            sql.append("AND UPPER(s.NO_RAZON_SOCIAL) LIKE UPPER(:nombreContratista) ");
        }
        
        sql.append("AND NOT EXISTS (SELECT 1 FROM ES_SICOES.SICOES_TC_REQ_APROBACION r2 ");
        sql.append("WHERE r2.ID_INFORME_RENOVACION = r.ID_INFORME_RENOVACION ");
        sql.append("AND r2.ID_GRUPO_APROBADOR_LD = 954 AND r2.ID_ESTADO_LD = 959) ");
        sql.append("ORDER BY r.FE_CREACION DESC");
        
        // Query para contar total de resultados
        String countSql = "SELECT COUNT(*) " + sql.substring(sql.indexOf("FROM"));
        countSql = countSql.substring(0, countSql.lastIndexOf("ORDER BY"));
        
        Query countQuery = entityManager.createNativeQuery(countSql);
        Query query = entityManager.createNativeQuery(sql.toString());
        
        // Setear parámetros solo si existen
        query.setParameter("idUsuario", idUsuario);
        countQuery.setParameter("idUsuario", idUsuario);
        
        if (numeroExpediente != null && !numeroExpediente.trim().isEmpty()) {
            query.setParameter("numeroExpediente", numeroExpediente);
            countQuery.setParameter("numeroExpediente", numeroExpediente);
        }
        
        if (estadoAprobacionInforme != null) {
            query.setParameter("estadoAprobacionInforme", estadoAprobacionInforme);
            countQuery.setParameter("estadoAprobacionInforme", estadoAprobacionInforme);
        }
        
        if (idContratista != null) {
            query.setParameter("idContratista", idContratista);
            countQuery.setParameter("idContratista", idContratista);
        }
        
        if (nombreContratista != null && !nombreContratista.trim().isEmpty()) {
            query.setParameter("nombreContratista", "%" + nombreContratista + "%");
            countQuery.setParameter("nombreContratista", "%" + nombreContratista + "%");
        }
        
        // Paginación
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        
        // Ejecutar queries
        Long total = ((BigDecimal) countQuery.getSingleResult()).longValue();
        List<Object[]> results = query.getResultList();
        
        // Mapear resultados a DTOs
        List<BandejaAprobacionFullDTO> dtos = mapearResultados(results);
        
        return new PageImpl<>(dtos, pageable, total);
    }
    
    @Override
    public Page<BandejaAprobacionFullDTO> buscarByIdUsuarioG2ConFiltrosDinamicos(
            String numeroExpediente,
            Long estadoAprobacionInforme,
            Long idContratista,
            String nombreContratista,
            Pageable pageable) {
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT r.ID_REQ_APROBACION as idReqAprobacion, r.ID_REQUERIMIENTO as idRequerimiento, ");
        sql.append("r.ID_REQ_INFORME as idReqInforme, r.ID_REQ_DOCUMENTO as idReqDocumento, ");
        sql.append("r.ID_TIPO_LD as idTipoLd, r.ID_GRUPO_LD as idGrupoLd, r.ID_USUARIO as idUsuario, ");
        sql.append("r.ID_ESTADO_LD as idEstadoLd, r.ID_FIRMADO_LD as idFirmadoLd, r.DE_OBSERVACION as deObservacion, ");
        sql.append("r.FE_APROBACION as feAprobacion, r.FE_RECHAZO as feRechazo, r.FE_FIRMA as feFirma, ");
        sql.append("r.ID_INFORME_RENOVACION as idInformeRenovacion, r.ID_NOTIFICACION as idNotificacion, ");
        sql.append("r.FE_ASIGNACION as feAsignacion, r.ID_TIPO_APROBADOR_LD as idTipoAprobadorLd, ");
        sql.append("r.ID_GRUPO_APROBADOR_LD as idGrupoAprobadorLd, ");
        sql.append("r.US_CREACION as usCreacion, r.IP_CREACION as ipCreacion, r.FE_CREACION as feCreacion, ");
        sql.append("r.US_ACTUALIZACION as usActualizacion, r.IP_ACTUALIZACION as ipActualizacion, r.FE_ACTUALIZACION as feActualizacion, ");
        sql.append("i.ES_VIGENTE as esVigente, i.ES_APROBACION_INFORME as esAprobacionInforme, ");
        sql.append("i.DE_UUID_INFO_RENOVACION as deUuidInfoRenovacion, i.DE_NOMBRE_ARCHIVO as deNombreArchivo, ");
        sql.append("rr.NU_EXPEDIENTE as nuExpediente, rr.TI_SECTOR as tiSector, rr.TI_SUB_SECTOR as tiSubSector, rr.NO_ITEM as noItem, ");
        sql.append("s.NO_RAZON_SOCIAL as noRazonSocial, s.ID_SUPERVISORA as idSupervisora ");
        sql.append("FROM ES_SICOES.SICOES_TC_REQ_APROBACION r ");
        sql.append("JOIN ES_SICOES.SICOES_TD_INFORME_RENOVACION i ON r.ID_INFORME_RENOVACION = i.ID_INFORME_RENOVACION ");
        sql.append("JOIN ES_SICOES.SICOES_TC_REQ_RENOVACION rr ON i.ID_REQUERIMIENTO = rr.ID_REQ_RENOVACION ");
        sql.append("LEFT JOIN ES_SICOES.SICOES_TC_SOLI_PERF_CONT spc ON rr.ID_SOLI_PERF_CONT = spc.ID_SOLI_PERF_CONT ");
        sql.append("LEFT JOIN ES_SICOES.SICOES_TM_SUPERVISORA s ON spc.ID_SUPERVISORA = s.ID_SUPERVISORA ");
        // Para usuarios G2: mostrar todos los registros de aprobación (G1 y G2)
        sql.append("WHERE r.ID_ESTADO_LD IN (958, 959, 960, 1160) ");
        
        // Agregar condiciones dinámicamente solo si tienen valor
        if (numeroExpediente != null && !numeroExpediente.trim().isEmpty()) {
            sql.append("AND rr.NU_EXPEDIENTE = :numeroExpediente ");
        }
        
        if (estadoAprobacionInforme != null) {
            sql.append("AND i.ES_APROBACION_INFORME = :estadoAprobacionInforme ");
        }
        
        if (idContratista != null) {
            sql.append("AND spc.ID_SUPERVISORA = :idContratista ");
        }
        
        if (nombreContratista != null && !nombreContratista.trim().isEmpty()) {
            sql.append("AND UPPER(s.NO_RAZON_SOCIAL) LIKE UPPER(:nombreContratista) ");
        }
        
        sql.append("ORDER BY r.FE_CREACION DESC");
        
        // Query para contar total de resultados
        String countSql = "SELECT COUNT(*) " + sql.substring(sql.indexOf("FROM"));
        countSql = countSql.substring(0, countSql.lastIndexOf("ORDER BY"));
        
        Query countQuery = entityManager.createNativeQuery(countSql);
        Query query = entityManager.createNativeQuery(sql.toString());
        
        // Setear parámetros solo si existen
        if (numeroExpediente != null && !numeroExpediente.trim().isEmpty()) {
            query.setParameter("numeroExpediente", numeroExpediente);
            countQuery.setParameter("numeroExpediente", numeroExpediente);
        }
        
        if (estadoAprobacionInforme != null) {
            query.setParameter("estadoAprobacionInforme", estadoAprobacionInforme);
            countQuery.setParameter("estadoAprobacionInforme", estadoAprobacionInforme);
        }
        
        if (idContratista != null) {
            query.setParameter("idContratista", idContratista);
            countQuery.setParameter("idContratista", idContratista);
        }
        
        if (nombreContratista != null && !nombreContratista.trim().isEmpty()) {
            query.setParameter("nombreContratista", "%" + nombreContratista + "%");
            countQuery.setParameter("nombreContratista", "%" + nombreContratista + "%");
        }
        
        // Paginación
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        
        // Ejecutar queries
        Long total = ((BigDecimal) countQuery.getSingleResult()).longValue();
        List<Object[]> results = query.getResultList();
        
        // Mapear resultados a DTOs
        List<BandejaAprobacionFullDTO> dtos = mapearResultados(results);
        
        return new PageImpl<>(dtos, pageable, total);
    }
    
    private List<BandejaAprobacionFullDTO> mapearResultados(List<Object[]> results) {
        List<BandejaAprobacionFullDTO> dtos = new ArrayList<>();
        
        for (Object[] row : results) {
            BandejaAprobacionFullDTO dto = new BandejaAprobacionFullDTO() {
                @Override
                public Long getIdReqAprobacion() {
                    return row[0] != null ? ((BigDecimal) row[0]).longValue() : null;
                }
                
                @Override
                public Long getIdRequerimiento() {
                    return row[1] != null ? ((BigDecimal) row[1]).longValue() : null;
                }
                
                @Override
                public Long getIdReqInforme() {
                    return row[2] != null ? ((BigDecimal) row[2]).longValue() : null;
                }
                
                @Override
                public Long getIdReqDocumento() {
                    return row[3] != null ? ((BigDecimal) row[3]).longValue() : null;
                }
                
                @Override
                public Long getIdTipoLd() {
                    return row[4] != null ? ((BigDecimal) row[4]).longValue() : null;
                }
                
                @Override
                public Long getIdGrupoLd() {
                    return row[5] != null ? ((BigDecimal) row[5]).longValue() : null;
                }
                
                @Override
                public Long getIdUsuario() {
                    return row[6] != null ? ((BigDecimal) row[6]).longValue() : null;
                }
                
                @Override
                public Long getIdEstadoLd() {
                    return row[7] != null ? ((BigDecimal) row[7]).longValue() : null;
                }
                
                @Override
                public Long getIdFirmadoLd() {
                    return row[8] != null ? ((BigDecimal) row[8]).longValue() : null;
                }
                
                @Override
                public String getDeObservacion() {
                    return (String) row[9];
                }
                
                @Override
                public Date getFeAprobacion() {
                    return (Date) row[10];
                }
                
                @Override
                public Date getFeRechazo() {
                    return (Date) row[11];
                }
                
                @Override
                public Date getFeFirma() {
                    return (Date) row[12];
                }
                
                @Override
                public Long getIdInformeRenovacion() {
                    return row[13] != null ? ((BigDecimal) row[13]).longValue() : null;
                }
                
                @Override
                public Long getIdNotificacion() {
                    return row[14] != null ? ((BigDecimal) row[14]).longValue() : null;
                }
                
                @Override
                public Date getFeAsignacion() {
                    return (Date) row[15];
                }
                
                @Override
                public Long getIdTipoAprobadorLd() {
                    return row[16] != null ? ((BigDecimal) row[16]).longValue() : null;
                }
                
                @Override
                public Long getIdGrupoAprobadorLd() {
                    return row[17] != null ? ((BigDecimal) row[17]).longValue() : null;
                }
                
                @Override
                public String getUsCreacion() {
                    return (String) row[18];
                }
                
                @Override
                public String getIpCreacion() {
                    return (String) row[19];
                }
                
                @Override
                public Date getFeCreacion() {
                    return (Date) row[20];
                }
                
                @Override
                public String getUsActualizacion() {
                    return (String) row[21];
                }
                
                @Override
                public String getIpActualizacion() {
                    return (String) row[22];
                }
                
                @Override
                public Date getFeActualizacion() {
                    return (Date) row[23];
                }
                
                @Override
                public Long getEsVigente() {
                    return row[24] != null ? ((BigDecimal) row[24]).longValue() : null;
                }
                
                @Override
                public Long getEsAprobacionInforme() {
                    return row[25] != null ? ((BigDecimal) row[25]).longValue() : null;
                }
                
                @Override
                public String getDeUuidInfoRenovacion() {
                    return (String) row[26];
                }
                
                @Override
                public String getDeNombreArchivo() {
                    return (String) row[27];
                }
                
                @Override
                public String getNuExpediente() {
                    return (String) row[28];
                }
                
                @Override
                public String getTiSector() {
                    return (String) row[29];
                }
                
                @Override
                public String getTiSubSector() {
                    return (String) row[30];
                }
                
                @Override
                public String getNoItem() {
                    return (String) row[31];
                }
                
                @Override
                public String getNoRazonSocial() {
                    return (String) row[32];
                }
                
                @Override
                public Long getIdSupervisora() {
                    return row[33] != null ? ((BigDecimal) row[33]).longValue() : null;
                }
            };
            
            dtos.add(dto);
        }
        
        return dtos;
    }
}