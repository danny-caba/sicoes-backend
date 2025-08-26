package pe.gob.osinergmin.sicoes.repository.renovacioncontrato;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.SolicitudPerfecionamientoContrato;

@Repository
public interface SolicitudPerfecionamientoContratoDao extends JpaRepository<SolicitudPerfecionamientoContrato, Long> {
    

    @Query(value = "SELECT " +
    "spc.ID_SOLI_PERF_CONT as ID_SOLI_PERF_CONT, " +
    "procesoItemPerfil.ID_PERFIL_LD AS ID_PERFIL, " +
    "perfilAprobador.ID_APROBADOR_G1 AS ID_APROBADOR_G1, " +
    "perfilAprobador.ID_APROBADOR_G2 AS ID_APROBADOR_G2, " +
    "perfilAprobador.ID_APROBADOR_G3 AS ID_APROBADOR_G3 " +
    "FROM SICOES_TC_SOLI_PERF_CONT spc " +
    "LEFT JOIN SICOES_TR_PROPUESTA propuesta ON spc.ID_PROPUESTA = propuesta.ID_PROPUESTA " +
    "LEFT JOIN SICOES_TR_PROCESO_ITEM procesoItem ON propuesta.ID_PROCESO_ITEM = procesoItem.ID_PROCESO_ITEM " +
    "LEFT JOIN SICOES_TR_PROCESO_ITEM_PERFIL procesoItemPerfil ON procesoItem.ID_PROCESO_ITEM = procesoItemPerfil.ID_PROCESO_ITEM " +
    "LEFT JOIN SICOES_TX_PERFIL_APROBADOR perfilAprobador ON procesoItemPerfil.ID_PERFIL_LD = perfilAprobador.ID_PERFIL " +
    "WHERE spc.ID_SOLI_PERF_CONT = :idSolicitudPerfecionamientoContrato AND spc.DE_SOLICITUD = 'CONCLUIDO' " ,
    nativeQuery = true)
    List<SolicitudPerfecionamientoContrato> getPerfilAprobadorByIdPerfilListadoDetalle(
        @Param("idSolicitudPerfecionamientoContrato") Long idSolicitudPerfecionamientoContrato);
   


    @Query(value = "SELECT " +
    "spc.ID_SOLI_PERF_CONT as ID_SOLI_PERF_CONT, " +
    "procesoItemPerfil.ID_PERFIL_LD AS ID_PERFIL, " +
    "perfilAprobador.ID_APROBADOR_G1 AS ID_APROBADOR_G1, " +
    "perfilAprobador.ID_APROBADOR_G2 AS ID_APROBADOR_G2, " +
    "perfilAprobador.ID_APROBADOR_G3 AS ID_APROBADOR_G3 " +
    "FROM SICOES_TC_SOLI_PERF_CONT spc " +
    "LEFT JOIN SICOES_TR_PROPUESTA propuesta ON spc.ID_PROPUESTA = propuesta.ID_PROPUESTA " +
    "LEFT JOIN SICOES_TR_PROCESO_ITEM procesoItem ON propuesta.ID_PROCESO_ITEM = procesoItem.ID_PROCESO_ITEM " +
    "LEFT JOIN SICOES_TR_PROCESO_ITEM_PERFIL procesoItemPerfil ON procesoItem.ID_PROCESO_ITEM = procesoItemPerfil.ID_PROCESO_ITEM " +
    "LEFT JOIN SICOES_TX_PERFIL_APROBADOR perfilAprobador ON procesoItemPerfil.ID_PERFIL_LD = perfilAprobador.ID_PERFIL " +
    "WHERE perfilAprobador.ID_APROBADOR_G1 = :idUsuario AND spc.DE_SOLICITUD = 'CONCLUIDO' " ,
    nativeQuery = true)
    List<SolicitudPerfecionamientoContrato> getPerfilAprobadorByIdUsuarioG1(
        @Param("idUsuario") Long idUsuario);



    @Query(value = "SELECT " +
    "spc.ID_SOLI_PERF_CONT as ID_SOLI_PERF_CONT, " +
    "procesoItemPerfil.ID_PERFIL_LD AS ID_PERFIL, " +
    "perfilAprobador.ID_APROBADOR_G1 AS ID_APROBADOR_G1, " +
    "perfilAprobador.ID_APROBADOR_G2 AS ID_APROBADOR_G2, " +
    "perfilAprobador.ID_APROBADOR_G3 AS ID_APROBADOR_G3 " +
    "FROM SICOES_TC_SOLI_PERF_CONT spc " +
    "LEFT JOIN SICOES_TR_PROPUESTA propuesta ON spc.ID_PROPUESTA = propuesta.ID_PROPUESTA " +
    "LEFT JOIN SICOES_TR_PROCESO_ITEM procesoItem ON propuesta.ID_PROCESO_ITEM = procesoItem.ID_PROCESO_ITEM " +
    "LEFT JOIN SICOES_TR_PROCESO_ITEM_PERFIL procesoItemPerfil ON procesoItem.ID_PROCESO_ITEM = procesoItemPerfil.ID_PROCESO_ITEM " +
    "LEFT JOIN SICOES_TX_PERFIL_APROBADOR perfilAprobador ON procesoItemPerfil.ID_PERFIL_LD = perfilAprobador.ID_PERFIL " +
    "WHERE perfilAprobador.ID_APROBADOR_G2 = :idUsuario AND spc.DE_SOLICITUD = 'CONCLUIDO' " ,
    nativeQuery = true)
    List<SolicitudPerfecionamientoContrato> getPerfilAprobadorByIdUsuarioG2(
        @Param("idUsuario") Long idUsuario);
    
}
