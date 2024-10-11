/*==============================================================*/
/* Database name:  SICOES                                       */
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     7/06/2023 11:05:48                           */
/*==============================================================*/

alter table SICOES_TM_CONF_BANDEJA
   drop constraint SICOES_ID_ACTIVI_LD_LS_DT_FK;

alter table SICOES_TM_CONF_BANDEJA
   drop constraint SICOES_ID_PERFIL_LD_LS_DT_FK;

alter table SICOES_TM_CONF_BANDEJA
   drop constraint SICOES_ID_SECTOR_LIS_DET_FK;

alter table SICOES_TM_CONF_BANDEJA
   drop constraint SICOES_ID_SUBCATE_LD_DT_FK;

alter table SICOES_TM_CONF_BANDEJA
   drop constraint SICOES_ID_SUBSECTOR_LS_DT_FK;

alter table SICOES_TM_CONF_BANDEJA
   drop constraint SICOES_ID_UNIDAD_LD_LS_DT_FK;

alter table SICOES_TM_CONF_BANDEJA
   drop constraint SICOES_ID_USU_LS_DE_FK;

alter table SICOES_TM_LISTADO_DETALLE
   drop constraint SICOES_ID_LIS_LIS_DET_FK;

alter table SICOES_TM_LISTADO_DETALLE
   drop constraint SICOES_ID_LIS_PDRE_LIS_DET_FK;

alter table SICOES_TM_LISTADO_DETALLE
   drop constraint SICOES_ID_SUP_LD_LIS_DET_FK;

alter table SICOES_TM_OPCION
   drop constraint SICOES_ID_EST_LD_OPC_FK;

alter table SICOES_TM_OPCION
   drop constraint SICOES_ID_PDRE_OPC_FK;

alter table SICOES_TM_ROL
   drop constraint SICOES_ID_EST_LD_ROL_FK;

alter table SICOES_TM_ROL_OPCION
   drop constraint SICOES_ID_OPC_ROL_OPC_FK;

alter table SICOES_TM_ROL_OPCION
   drop constraint SICOES_ID_ROL_ROL_OPC_FK;

alter table SICOES_TM_SUPERVISORA
   drop constraint SICOES_ID_ESTADO_LD_LIS_DET_FK;

alter table SICOES_TM_SUPERVISORA
   drop constraint SICOES_ID_PAIS_LD_SUP_FK;

alter table SICOES_TM_SUPERVISORA
   drop constraint SICOES_ID_TIPO_DOC_LD_SUP_FK;

alter table SICOES_TM_SUPERVISORA
   drop constraint SICOES_ID_TIPPER_LD_LIS_DET_FK;

alter table SICOES_TM_SUPER_PERFIL
   drop constraint SICOES_ID_SUP_SUP_PER_FK;

alter table SICOES_TM_SUPER_REPRESENTANTE
   drop constraint SICOES_ID_SUPER_SUPER_FK;

alter table SICOES_TM_SUPER_REPRESENTANTE
   drop constraint SICOES_ID_TIP_DOC_LIST_DETA_FK;

alter table SICOES_TM_USUARIO
   drop constraint SICOES_ID_PAIS_LD_USUARIO_FK;

alter table SICOES_TM_USUARIO
   drop constraint SICOES_ID_TIP_DOC_USUARIO_LD;

alter table SICOES_TM_USUARIO
   drop constraint SICOES_ID_TIP_PER_LIST_DET_FK;

alter table SICOES_TM_USUARIO
   drop constraint SICOES_ID_TIP_USU_LD_USU_FK;

alter table SICOES_TM_USUARIO_ROL
   drop constraint SICOES_ID_ROL_USU_ROL_FK;

alter table SICOES_TM_USUARIO_ROL
   drop constraint SICOES_ID_USU_USU_ROL_FK;

alter table SICOES_TR_ARCHIVO
   drop constraint SICOES_ID_DOC_ARCH_FK;

alter table SICOES_TR_ARCHIVO
   drop constraint SICOES_ID_EST_ARCH_FK;

alter table SICOES_TR_ARCHIVO
   drop constraint SICOES_ID_EST_LD_ARCH_FK;

alter table SICOES_TR_ARCHIVO
   drop constraint SICOES_ID_NOTI_ARCHIVO_FK;

alter table SICOES_TR_ARCHIVO
   drop constraint SICOES_ID_NOT_SOL_ARCH_FK;

alter table SICOES_TR_ARCHIVO
   drop constraint SICOES_ID_OTRO_REQ_ARCH_FK;

alter table SICOES_TR_ARCHIVO
   drop constraint SICOES_ID_PROPU_ARCHIVO_FK;

alter table SICOES_TR_ARCHIVO
   drop constraint SICOES_ID_PRO_ECO_ARCHIVO_FK;

alter table SICOES_TR_ARCHIVO
   drop constraint SICOES_ID_PRO_TEC_PRO_PRO_FK;

alter table SICOES_TR_ARCHIVO
   drop constraint SICOES_ID_SOL_ARCH_FK;

alter table SICOES_TR_ARCHIVO
   drop constraint SICOES_ID_TIPO_ARCH_LD_ARCH_FK;

alter table SICOES_TR_ASIGNACION
   drop constraint SICOES_ID_EVA_LD_ASIG_FK;

alter table SICOES_TR_ASIGNACION
   drop constraint SICOES_ID_GRP_LD_ASIG_FK;

alter table SICOES_TR_ASIGNACION
   drop constraint SICOES_ID_SOL_ASIG_FK;

alter table SICOES_TR_ASIGNACION
   drop constraint SICOES_ID_TIPO_LD_ASIG_FK;

alter table SICOES_TR_ASIGNACION
   drop constraint SICOES_ID_USU_ASIG_FK;

alter table SICOES_TR_BITACORA
   drop constraint SICOES_ID_USUARIO_BITACORA_FK;

alter table SICOES_TR_DOCUMENTO
   drop constraint SICOES_ID_CONF_LD_DOC_FK;

alter table SICOES_TR_DOCUMENTO
   drop constraint SICOES_ID_DOC_PDRE_DOC_FK;

alter table SICOES_TR_DOCUMENTO
   drop constraint SICOES_ID_EVA_LD_DOC_FK;

alter table SICOES_TR_DOCUMENTO
   drop constraint SICOES_ID_PAIS_LD_DOC_FK;

alter table SICOES_TR_DOCUMENTO
   drop constraint SICOES_ID_SOL_DOC_FK;

alter table SICOES_TR_DOCUMENTO
   drop constraint SICOES_ID_TIPO_CAM_LD_DOC_FK;

alter table SICOES_TR_DOCUMENTO
   drop constraint SICOES_ID_TIPO_DOC_LD_DOC_FK;

alter table SICOES_TR_DOCUMENTO
   drop constraint SICOES_ID_TIPO_LD_DOC_FK;

alter table SICOES_TR_DOCUMENTO
   drop constraint SICOES_ID_TIP_ID_TRI_LD_DOC_FK;

alter table SICOES_TR_ESTUDIO
   drop constraint SICOES_ID_EST_PDRE_EST_FK;

alter table SICOES_TR_ESTUDIO
   drop constraint SICOES_ID_EVA_LD_EST_FK;

alter table SICOES_TR_ESTUDIO
   drop constraint SICOES_ID_FUEN_LD_EST_FK;

alter table SICOES_TR_ESTUDIO
   drop constraint SICOES_ID_SOL_EST_FK;

alter table SICOES_TR_ESTUDIO
   drop constraint SICOES_ID_TIPO_EST_LD_EST_FK;

alter table SICOES_TR_ESTUDIO
   drop constraint SICOES_ID_TIPO_LD_EST_FK;

alter table SICOES_TR_ITEM_ESTADO
   drop constraint SICOES_ID_ESTADO_LD_ESTADO_FK;

alter table SICOES_TR_ITEM_ESTADO
   drop constraint SICOES_ID_PRO_ITEM_ESTADO_FK;

alter table SICOES_TR_NOTIFICACION
   drop constraint SICOES_ID_EST_LD_NOT_FK;

alter table SICOES_TR_OTRO_REQUISITO
   drop constraint SICOES_FINALIZADO_OTRO_RQ_FK;

alter table SICOES_TR_OTRO_REQUISITO
   drop constraint SICOES_ID_ACT_LD_OTRO_REQ_FK;

alter table SICOES_TR_OTRO_REQUISITO
   drop constraint SICOES_ID_EVA_LD_OTRO_REQ_FK;

alter table SICOES_TR_OTRO_REQUISITO
   drop constraint SICOES_ID_OT_RE_PDRE_OT_REQ_FK;

alter table SICOES_TR_OTRO_REQUISITO
   drop constraint SICOES_ID_PERF_LD_OTRO_REQ_FK;

alter table SICOES_TR_OTRO_REQUISITO
   drop constraint SICOES_ID_RESUL_LIST_DET_FK;

alter table SICOES_TR_OTRO_REQUISITO
   drop constraint SICOES_ID_SEC_LD_OTRO_REQ_FK;

alter table SICOES_TR_OTRO_REQUISITO
   drop constraint SICOES_ID_SOL_OTRO_REQ_FK;

alter table SICOES_TR_OTRO_REQUISITO
   drop constraint SICOES_ID_SUBC_LD_OTRO_REQ_FK;

alter table SICOES_TR_OTRO_REQUISITO
   drop constraint SICOES_ID_SUB_LD_OTRO_REQ_FK;

alter table SICOES_TR_OTRO_REQUISITO
   drop constraint SICOES_ID_TIPO_LD_OTRO_REQ_FK;

alter table SICOES_TR_OTRO_REQUISITO
   drop constraint SICOES_ID_TIP_REQ_LD_OT_REQ_FK;

alter table SICOES_TR_OTRO_REQUISITO
   drop constraint SICOES_ID_UNI_LD_REQ_FK;

alter table SICOES_TR_OTRO_REQUISITO
   drop constraint SICOES_ID_USUARIO_OTRO_RQ_FK;

alter table SICOES_TR_PERSONA
   drop constraint SICOES_ID_PAIS_LD_PERS_FK;

alter table SICOES_TR_PERSONA
   drop constraint SICOES_ID_TIPO_DOC_LD_PERS_FK;

alter table SICOES_TR_PERSONA
   drop constraint SICOES_ID_TI_PER_LD_LS_DET_FK;

alter table SICOES_TR_PROCESO
   drop constraint FK_SICOES_T_REFERENCE_SICOES_T;

alter table SICOES_TR_PROCESO
   drop constraint SICOES_ID_ESTADO_LD_PROC_FK;

alter table SICOES_TR_PROCESO
   drop constraint SICOES_ID_SECTOR_PROCESO_FK;

alter table SICOES_TR_PROCESO
   drop constraint SICOES_ID_SUB_SEC_LD_PROC_FK;

alter table SICOES_TR_PROCESO
   drop constraint SICOES_ID_USUARIO_PROCESO_FK;

alter table SICOES_TR_PROCESO_ETAPA
   drop constraint SICOES_ID_ETA_LD_PROC_ETA_LD;

alter table SICOES_TR_PROCESO_ETAPA
   drop constraint SICOES_ID_PROC_LD_PROC_ETA_FK;

alter table SICOES_TR_PROCESO_ETAPA
   drop constraint SICOES_ID_PROC_PROC_ESTAPA_FK;

alter table SICOES_TR_PROCESO_ITEM
   drop constraint SICOES_ID_DIV_LD_PRO_ITEM_FK;

alter table SICOES_TR_PROCESO_ITEM
   drop constraint SICOES_ID_EST_LD_PRO_ITEM_FK;

alter table SICOES_TR_PROCESO_ITEM
   drop constraint SICOES_ID_PROC_PROC_ITEM_FK;

alter table SICOES_TR_PROCESO_ITEM_PERFIL
   drop constraint SICOES_ID_ESTADO_PRO_ITEM_FK;

alter table SICOES_TR_PROCESO_ITEM_PERFIL
   drop constraint SICOES_ID_PERFIL_ITEM_PERF_FK;

alter table SICOES_TR_PROCESO_ITEM_PERFIL
   drop constraint SICOES_ID_PROCESO_ITEM_PERF_FK;

alter table SICOES_TR_PROCESO_ITEM_PERFIL
   drop constraint FK_SICOES_T_SICOES_ID_SICOES_T;

alter table SICOES_TR_PROCESO_ITEM_PERFIL
   drop constraint SICOES_ID_SUBSECT_ITEM_PERF_FK;

alter table SICOES_TR_PROCESO_MIEMBRO
   drop constraint SICOES_ID_CAR_LD_PROC_MIEM_FK;

alter table SICOES_TR_PROCESO_MIEMBRO
   drop constraint SICOES_ID_ESTADO_PRO_MIEM_FK;

alter table SICOES_TR_PROCESO_MIEMBRO
   drop constraint SICOES_ID_PROC_PROC_MIEM_FK;

alter table SICOES_TR_PROPUESTA
   drop constraint SICOES_ID_EST_LD_PROP_FK;

alter table SICOES_TR_PROPUESTA
   drop constraint SICOES_ID_GANADOR_PROPU_FK;

alter table SICOES_TR_PROPUESTA
   drop constraint SICOES_ID_PROC_ITEM_PROP_FK;

alter table SICOES_TR_PROPUESTA
   drop constraint SICOES_ID_PRO_ECO_PRO_FK;

alter table SICOES_TR_PROPUESTA
   drop constraint SICOES_ID_PRO_TECNICA_PRO_FK;

alter table SICOES_TR_PROPUESTA
   drop constraint SICOES_ID_SUPE_PROP_FK;

alter table SICOES_TR_PRO_PROFESIONAL
   drop constraint SICOES_ID_ESTADO_BLOQUEO_FK;

alter table SICOES_TR_PRO_PROFESIONAL
   drop constraint SICOES_ID_EST_LD_PRO_PRO_FK;

alter table SICOES_TR_PRO_PROFESIONAL
   drop constraint SICOES_ID_PER_LD_PRO_PRO_FK;

alter table SICOES_TR_PRO_PROFESIONAL
   drop constraint SICOES_ID_PROP_PRO_PRO_FK;

alter table SICOES_TR_PRO_PROFESIONAL
   drop constraint "Reference_118";

alter table SICOES_TR_PRO_PROFESIONAL
   drop constraint SICOES_ID_SSECT_LD_PRO_PRO_FK;

alter table SICOES_TR_PRO_PROFESIONAL
   drop constraint SICOES_ID_SUPE_PRO_PRO_FK;

alter table SICOES_TR_PRO_TECNICA
   drop constraint SICOES_ID_CONSOR_LD_LIST_DT_FK;

alter table SICOES_TR_REPRESENTANTE
   drop constraint SICOES_ID_TIPO_DOC_LD_REPR_FK;

alter table SICOES_TR_SOLICITUD
   drop constraint SICOES_ID_EST_EVA_AD_LD_SOL_FK;

alter table SICOES_TR_SOLICITUD
   drop constraint SICOES_ID_EST_EVA_TE_LD_SOL_FK;

alter table SICOES_TR_SOLICITUD
   drop constraint SICOES_ID_EST_LD_SOL_FK;

alter table SICOES_TR_SOLICITUD
   drop constraint SICOES_ID_EST_REV_LD_SOL_FK;

alter table SICOES_TR_SOLICITUD
   drop constraint SICOES_ID_PER_SOL_FK;

alter table SICOES_TR_SOLICITUD
   drop constraint SICOES_ID_REPR_SOL_FK;

alter table SICOES_TR_SOLICITUD
   drop constraint SICOES_ID_RES_ADM_SOL_FK;

alter table SICOES_TR_SOLICITUD
   drop constraint SICOES_ID_RES_TEC_SOL_FK;

alter table SICOES_TR_SOLICITUD
   drop constraint SICOES_ID_SOL_PDRE_SOL_FK;

alter table SICOES_TR_SOLICITUD
   drop constraint SICOES_ID_TIPO_SOL_LD_SOL_FK;

alter table SICOES_TR_SOLICITUD
   drop constraint SICOES_ID_USU_SOL_FK;

alter table SICOES_TR_SOL_NOTIFICACION
   drop constraint SICOES_ID_SOL_SOL_NOT_FK;

alter table SICOES_TR_SOL_NOTIFICACION
   drop constraint SICOES_ID_TIPO_LD_SOL_NOT_FK;

alter table SICOES_TR_SUSPEN_CANCELACION
   drop constraint SICOES_ID_CAU_LD_SUSP_CAN_FK;

alter table SICOES_TR_SUSPEN_CANCELACION
   drop constraint SICOES_ID_ESTADO_SUSP_CAN_FK;

alter table SICOES_TR_SUSPEN_CANCELACION
   drop constraint SICOES_ID_SUPERV_SUPERV_FK;

alter table SICOES_TR_SUSPEN_CANCELACION
   drop constraint SICOES_ID_TIPO_LD_LS_DT_FK;

alter table SICOES_TR_TOKEN
   drop constraint SICOES_ID_EST_LD_TOKEN_FK;

alter table SICOES_TR_TOKEN
   drop constraint SICOES_ID_TIPO_LD_TOKEN_FK;

alter table SICOES_TR_TOKEN
   drop constraint SICOES_ID_USU_TOKEN_FK;

drop table OAUTH_ACCESS_TOKEN cascade constraints;

drop table OAUTH_CLIENT_DETAILS cascade constraints;

drop table OAUTH_CLIENT_TOKEN cascade constraints;

drop table OAUTH_CODE cascade constraints;

drop table OAUTH_REFRESH_TOKEN cascade constraints;

alter table SICOES_TM_CONF_BANDEJA
   drop primary key cascade;

drop table SICOES_TM_CONF_BANDEJA cascade constraints;

alter table SICOES_TM_LISTADO
   drop primary key cascade;

drop table SICOES_TM_LISTADO cascade constraints;

alter table SICOES_TM_LISTADO_DETALLE
   drop primary key cascade;

drop table SICOES_TM_LISTADO_DETALLE cascade constraints;

alter table SICOES_TM_OPCION
   drop primary key cascade;

drop table SICOES_TM_OPCION cascade constraints;

alter table SICOES_TM_ROL
   drop primary key cascade;

drop table SICOES_TM_ROL cascade constraints;

alter table SICOES_TM_ROL_OPCION
   drop primary key cascade;

drop table SICOES_TM_ROL_OPCION cascade constraints;

alter table SICOES_TM_SUPERVISORA
   drop primary key cascade;

drop table SICOES_TM_SUPERVISORA cascade constraints;

alter table SICOES_TM_SUPER_PERFIL
   drop primary key cascade;

drop table SICOES_TM_SUPER_PERFIL cascade constraints;

alter table SICOES_TM_SUPER_REPRESENTANTE
   drop primary key cascade;

drop table SICOES_TM_SUPER_REPRESENTANTE cascade constraints;

alter table SICOES_TM_UBIGEO
   drop primary key cascade;

drop table SICOES_TM_UBIGEO cascade constraints;

alter table SICOES_TM_USUARIO
   drop primary key cascade;

drop table SICOES_TM_USUARIO cascade constraints;

alter table SICOES_TM_USUARIO_ROL
   drop primary key cascade;

drop table SICOES_TM_USUARIO_ROL cascade constraints;

alter table SICOES_TR_ARCHIVO
   drop primary key cascade;

drop table SICOES_TR_ARCHIVO cascade constraints;

alter table SICOES_TR_ASIGNACION
   drop primary key cascade;

drop table SICOES_TR_ASIGNACION cascade constraints;

alter table SICOES_TR_BITACORA
   drop primary key cascade;

drop table SICOES_TR_BITACORA cascade constraints;

alter table SICOES_TR_DOCUMENTO
   drop primary key cascade;

drop table SICOES_TR_DOCUMENTO cascade constraints;

alter table SICOES_TR_ESTUDIO
   drop primary key cascade;

drop table SICOES_TR_ESTUDIO cascade constraints;

alter table SICOES_TR_ITEM_ESTADO
   drop primary key cascade;

drop table SICOES_TR_ITEM_ESTADO cascade constraints;

alter table SICOES_TR_NOTIFICACION
   drop primary key cascade;

drop table SICOES_TR_NOTIFICACION cascade constraints;

alter table SICOES_TR_OTRO_REQUISITO
   drop primary key cascade;

drop table SICOES_TR_OTRO_REQUISITO cascade constraints;

alter table SICOES_TR_PERSONA
   drop primary key cascade;

drop table SICOES_TR_PERSONA cascade constraints;

alter table SICOES_TR_PROCESO
   drop primary key cascade;

drop table SICOES_TR_PROCESO cascade constraints;

alter table SICOES_TR_PROCESO_ETAPA
   drop primary key cascade;

drop table SICOES_TR_PROCESO_ETAPA cascade constraints;

alter table SICOES_TR_PROCESO_ITEM
   drop primary key cascade;

drop table SICOES_TR_PROCESO_ITEM cascade constraints;

alter table SICOES_TR_PROCESO_ITEM_PERFIL
   drop primary key cascade;

drop table SICOES_TR_PROCESO_ITEM_PERFIL cascade constraints;

alter table SICOES_TR_PROCESO_MIEMBRO
   drop primary key cascade;

drop table SICOES_TR_PROCESO_MIEMBRO cascade constraints;

alter table SICOES_TR_PROPUESTA
   drop primary key cascade;

drop table SICOES_TR_PROPUESTA cascade constraints;

alter table SICOES_TR_PRO_ECONOMICA
   drop primary key cascade;

drop table SICOES_TR_PRO_ECONOMICA cascade constraints;

alter table SICOES_TR_PRO_PROFESIONAL
   drop primary key cascade;

drop table SICOES_TR_PRO_PROFESIONAL cascade constraints;

alter table SICOES_TR_PRO_TECNICA
   drop primary key cascade;

drop table SICOES_TR_PRO_TECNICA cascade constraints;

alter table SICOES_TR_REPRESENTANTE
   drop primary key cascade;

drop table SICOES_TR_REPRESENTANTE cascade constraints;

alter table SICOES_TR_SOLICITUD
   drop primary key cascade;

drop table SICOES_TR_SOLICITUD cascade constraints;

alter table SICOES_TR_SOL_NOTIFICACION
   drop primary key cascade;

drop table SICOES_TR_SOL_NOTIFICACION cascade constraints;

alter table SICOES_TR_SUSPEN_CANCELACION
   drop primary key cascade;

drop table SICOES_TR_SUSPEN_CANCELACION cascade constraints;

alter table SICOES_TR_TOKEN
   drop primary key cascade;

drop table SICOES_TR_TOKEN cascade constraints;

drop sequence SICOES_SEQ_ARCHIVO;

drop sequence SICOES_SEQ_ASIGNACION;

drop sequence SICOES_SEQ_BITACORA;

drop sequence SICOES_SEQ_CONF_BANDEJA;

drop sequence SICOES_SEQ_DOCUMENTO;

drop sequence SICOES_SEQ_ESTUDIO;

drop sequence SICOES_SEQ_LISTADO;

drop sequence SICOES_SEQ_LISTADO_DETALLE;

drop sequence SICOES_SEQ_NOTIFICACION;

drop sequence SICOES_SEQ_OTRO_REQUISITO;

drop sequence SICOES_SEQ_PERSONA;

drop sequence SICOES_SEQ_PROCESO;

drop sequence SICOES_SEQ_PROCESO_ETAPA;

drop sequence SICOES_SEQ_PROCESO_ITEM;

drop sequence SICOES_SEQ_PROCESO_ITEM_PERFIL;

drop sequence SICOES_SEQ_PROCESO_MIEMBRO;

drop sequence SICOES_SEQ_PROPUESTA;

drop sequence SICOES_SEQ_PRO_ECONOMICA;

drop sequence SICOES_SEQ_PRO_PROFESIONAL;

drop sequence SICOES_SEQ_PRO_TECNICA;

drop sequence SICOES_SEQ_REPRESENTANTE;

drop sequence SICOES_SEQ_SOLICITUD;

drop sequence SICOES_SEQ_SOL_NOTIFICACION;

drop sequence SICOES_SEQ_SUPERVISORA;

drop sequence SICOES_SEQ_SUPERVISORA_PERFIL;

drop sequence SICOES_SEQ_SUPER_REPRESENTANTE;

drop sequence SICOES_SEQ_SUSPEN_CANCELACION;

drop sequence SICOES_SEQ_TOKEN;

drop sequence SICOES_SEQ_USUARIO;

drop sequence SICOES_SEQ_USUARIO_ROL;
