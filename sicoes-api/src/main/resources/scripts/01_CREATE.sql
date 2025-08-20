/*==============================================================*/
/* Database name:  SICOES                                       */
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     1/08/2023 11:34:49                           */
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
   drop constraint SICOES_ID_TIPO_CONF_BAJE_FK;

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

alter table SICOES_TM_SUPER_DICTAMEN
   drop constraint SICOES_ID_SECTOR_DIC_FK;

alter table SICOES_TM_SUPER_DICTAMEN
   drop constraint SICOES_ID_SUPER_DICTMEN_FK;

alter table SICOES_TM_SUPER_PERFIL
   drop constraint SICOES_ID_ESTADO_PEFIL_FK;

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
   drop constraint SICOES_ID_ASIG_ARCHIVO_FK;

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

alter table SICOES_TR_DICTAMEN_EVAL
   drop constraint SICOES_ID_SECTOR_DICTAM_FK;

alter table SICOES_TR_DICTAMEN_EVAL
   drop constraint SICOES_ID_SOLICITUD_DICTAM_FK;

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

alter table SICOES_TR_INTEGRANTE
   drop constraint SICOES_ID_PRO_TECN_INTE_FK;

alter table SICOES_TR_INTEGRANTE
   drop constraint SICOES_ID_SUPER_INTE_FK;

alter table SICOES_TR_INTEGRANTE
   drop constraint SICOES_ID_TIPO_INTE_FK;

alter table SICOES_TR_ITEM_ESTADO
   drop constraint SICOES_ID_ESTADO_LD_ESTADO_FK;

alter table SICOES_TR_ITEM_ESTADO
   drop constraint SICOES_ID_EST_ANTE_LDT_FK;

alter table SICOES_TR_ITEM_ESTADO
   drop constraint SICOES_ID_PRO_ITEM_ESTADO_FK;

alter table SICOES_TR_ITEM_ESTADO
   drop constraint SICOES_ID_USU_EST_FK;

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
   drop constraint SICOES_ID_ESTADO_LD_PROC_FK;

alter table SICOES_TR_PROCESO
   drop constraint SICOES_ID_SECTOR_PROCESO_FK;

alter table SICOES_TR_PROCESO
   drop constraint SICOES_ID_SUB_SEC_LD_PROC_FK;

alter table SICOES_TR_PROCESO
   drop constraint SICOES_ID_USUARIO_PROCESO_FK;

alter table SICOES_TR_PROCESO
   drop constraint SICOES_ID_USU_CRE_PROCESO_FK;

alter table SICOES_TR_PROCESO
   drop constraint SICOES_TIPO_FACTU_LD_LST_LD_FK;

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
   drop constraint SICOES_ID_SEC_LD_PRO_PRO_FK;

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

alter table SICOES_TR_SUPER_MOVIMIENTO
   drop constraint SICOES_ID_ACCION_MOVI_FK;

alter table SICOES_TR_SUPER_MOVIMIENTO
   drop constraint SICOES_ID_EST_MOVI_FK;

alter table SICOES_TR_SUPER_MOVIMIENTO
   drop constraint SICOES_ID_MOTIVO_MOVI_FK;

alter table SICOES_TR_SUPER_MOVIMIENTO
   drop constraint SICOES_ID_PROFE_MOVI_FK;

alter table SICOES_TR_SUPER_MOVIMIENTO
   drop constraint SICOES_ID_SECTOR_MOVI_FK;

alter table SICOES_TR_SUPER_MOVIMIENTO
   drop constraint SICOES_ID_SUBSECTOR_MOVI_FK;

alter table SICOES_TR_SUPER_MOVIMIENTO
   drop constraint SICOES_ID_SUPERV_MOVI_FK;

alter table SICOES_TR_SUPER_MOVIMIENTO
   drop constraint SICOES_ID_TIPO_MOTIVO_MOVI_FK;

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

alter table SICOES_TM_SUPER_DICTAMEN
   drop primary key cascade;

drop table SICOES_TM_SUPER_DICTAMEN cascade constraints;

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

alter table SICOES_TR_DICTAMEN_EVAL
   drop primary key cascade;

drop table SICOES_TR_DICTAMEN_EVAL cascade constraints;

alter table SICOES_TR_DOCUMENTO
   drop primary key cascade;

drop table SICOES_TR_DOCUMENTO cascade constraints;

alter table SICOES_TR_ESTUDIO
   drop primary key cascade;

drop table SICOES_TR_ESTUDIO cascade constraints;

alter table SICOES_TR_INTEGRANTE
   drop primary key cascade;

drop table SICOES_TR_INTEGRANTE cascade constraints;

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

alter table SICOES_TR_SUPER_MOVIMIENTO
   drop primary key cascade;

drop table SICOES_TR_SUPER_MOVIMIENTO cascade constraints;

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

drop sequence SICOES_SEQ_DICTAMEN_EVAL;

drop sequence SICOES_SEQ_DOCUMENTO;

drop sequence SICOES_SEQ_ESTUDIO;

drop sequence SICOES_SEQ_ITEM_ESTADO;

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

drop sequence SICOES_SEQ_SUPER_DICTAMEN;

drop sequence SICOES_SEQ_SUPER_REPRESENTANTE;

drop sequence SICOES_SEQ_SUSPEN_CANCELACION;

drop sequence SICOES_SEQ_TOKEN;

drop sequence SICOES_SEQ_USUARIO;

drop sequence SICOES_SEQ_USUARIO_ROL;

drop sequence SICOES_SEQ_SUPER_MOVIMIENTO;

create sequence SICOES_SEQ_ARCHIVO;

create sequence SICOES_SEQ_ASIGNACION;

create sequence SICOES_SEQ_BITACORA;

create sequence SICOES_SEQ_CONF_BANDEJA;

create sequence SICOES_SEQ_DICTAMEN_EVAL;

create sequence SICOES_SEQ_DOCUMENTO;

create sequence SICOES_SEQ_ESTUDIO;

create sequence SICOES_SEQ_ITEM_ESTADO;

create sequence SICOES_SEQ_LISTADO;

create sequence SICOES_SEQ_LISTADO_DETALLE;

create sequence SICOES_SEQ_NOTIFICACION;

create sequence SICOES_SEQ_OTRO_REQUISITO;

create sequence SICOES_SEQ_PERSONA;

create sequence SICOES_SEQ_PROCESO;

create sequence SICOES_SEQ_PROCESO_ETAPA;

create sequence SICOES_SEQ_PROCESO_ITEM;

create sequence SICOES_SEQ_PROCESO_ITEM_PERFIL;

create sequence SICOES_SEQ_PROCESO_MIEMBRO;

create sequence SICOES_SEQ_PROPUESTA;

create sequence SICOES_SEQ_PRO_ECONOMICA;

create sequence SICOES_SEQ_PRO_PROFESIONAL;

create sequence SICOES_SEQ_PRO_TECNICA;

create sequence SICOES_SEQ_REPRESENTANTE;

create sequence SICOES_SEQ_SOLICITUD;

create sequence SICOES_SEQ_SOL_NOTIFICACION;

create sequence SICOES_SEQ_SUPERVISORA;

create sequence SICOES_SEQ_SUPERVISORA_PERFIL;

create sequence SICOES_SEQ_SUPER_DICTAMEN;

create sequence SICOES_SEQ_SUPER_REPRESENTANTE;

create sequence SICOES_SEQ_SUSPEN_CANCELACION;

create sequence SICOES_SEQ_TOKEN;

create sequence SICOES_SEQ_USUARIO;

create sequence SICOES_SEQ_USUARIO_ROL;

create sequence SICOES_SEQ_SUPER_MOVIMIENTO;

/*==============================================================*/
/* Table: OAUTH_ACCESS_TOKEN                                    */
/*==============================================================*/
create table OAUTH_ACCESS_TOKEN 
(
   AUTHENTICATION_ID    varchar(255)         not null,
   TOKEN_ID             varchar(255),
   TOKEN                blob,
   USER_NAME            varchar(255),
   AUTHENTICATION       blob,
   REFRESH_TOKEN        varchar(255),
   CLIENT_ID            varchar(255)
);

comment on table OAUTH_ACCESS_TOKEN is
'Almac�n de datos de autenticaci�n de los accesos por token';

comment on column OAUTH_ACCESS_TOKEN.AUTHENTICATION_ID is
'Corresponde al identificador de la tabla';

comment on column OAUTH_ACCESS_TOKEN.TOKEN_ID is
'Corresponde al id del token';

comment on column OAUTH_ACCESS_TOKEN.TOKEN is
'Corresponde al token';

comment on column OAUTH_ACCESS_TOKEN.USER_NAME is
'Corresponde al nombre del usuario';

comment on column OAUTH_ACCESS_TOKEN.AUTHENTICATION is
'Corresponde a la autenticaci�n';

comment on column OAUTH_ACCESS_TOKEN.REFRESH_TOKEN is
'Corresponde al token de actualizaci�n';

comment on column OAUTH_ACCESS_TOKEN.CLIENT_ID is
'Corresponde al id del cliente';

/*==============================================================*/
/* Table: OAUTH_CLIENT_DETAILS                                  */
/*==============================================================*/
create table OAUTH_CLIENT_DETAILS 
(
   CLIENT_ID            varchar(256)         not null,
   RESOURCE_IDS         varchar(256),
   CLIENT_SECRET        varchar(256),
   SCOPE                varchar(256),
   AUTHORIZED_GRANT_TYPES varchar(256),
   WEB_SERVER_REDIRECT_URI varchar(256),
   AUTHORITIES          varchar(256),
   ACCESS_TOKEN_VALIDITY integer,
   REFRESH_TOKEN_VALIDITY integer,
   ADDITIONAL_INFORMATION varchar(4000),
   AUTOAPPROVE          varchar(256)
);

comment on table OAUTH_CLIENT_DETAILS is
'Almac�n de datos de autenticaci�n del detalle del cliente ';

comment on column OAUTH_CLIENT_DETAILS.CLIENT_ID is
'Corresponde al identificador de la tabla';

comment on column OAUTH_CLIENT_DETAILS.RESOURCE_IDS is
'Corresponde a los resource ids';

comment on column OAUTH_CLIENT_DETAILS.CLIENT_SECRET is
'Corresponde al client secret';

comment on column OAUTH_CLIENT_DETAILS.SCOPE is
'Corresponde a los scopes permitidos';

comment on column OAUTH_CLIENT_DETAILS.AUTHORIZED_GRANT_TYPES is
'Corresponde a los tipos de concesi�n permitidos';

comment on column OAUTH_CLIENT_DETAILS.WEB_SERVER_REDIRECT_URI is
'Corresponde al uri de redireccionamiento';

comment on column OAUTH_CLIENT_DETAILS.AUTHORITIES is
'Corresponde a los authorities (roles)';

comment on column OAUTH_CLIENT_DETAILS.ACCESS_TOKEN_VALIDITY is
'Corresponde a la expiraci�n de tiempo del token de acceso';

comment on column OAUTH_CLIENT_DETAILS.REFRESH_TOKEN_VALIDITY is
'Corresponde al tiempo de expiraci�n del token de actualizaci�n';

comment on column OAUTH_CLIENT_DETAILS.ADDITIONAL_INFORMATION is
'Corresponde a la informaci�n adicional';

comment on column OAUTH_CLIENT_DETAILS.AUTOAPPROVE is
'Corresponde al autoapprove';

/*==============================================================*/
/* Table: OAUTH_CLIENT_TOKEN                                    */
/*==============================================================*/
create table OAUTH_CLIENT_TOKEN 
(
   USER_NAME            varchar(255),
   TOKEN                blob
);

comment on table OAUTH_CLIENT_TOKEN is
'Almac�n de datos de autenticaci�n de los token del cliente';

comment on column OAUTH_CLIENT_TOKEN.USER_NAME is
'Corresponde al nombre del usuario';

comment on column OAUTH_CLIENT_TOKEN.TOKEN is
'Corresponde al token';

/*==============================================================*/
/* Table: OAUTH_CODE                                            */
/*==============================================================*/
create table OAUTH_CODE 
(
   CODE                 varchar(255)         not null,
   AUTHENTICATION       blob
);

comment on table OAUTH_CODE is
'Almac�n de datos de autenticaci�n';

comment on column OAUTH_CODE.CODE is
'Corresponde al identificador de la tabla';

comment on column OAUTH_CODE.AUTHENTICATION is
'Corresponde a la autenticaci�n';

/*==============================================================*/
/* Table: OAUTH_REFRESH_TOKEN                                   */
/*==============================================================*/
create table OAUTH_REFRESH_TOKEN 
(
   TOKEN_ID             varchar(255)         not null,
   TOKEN                blob,
   AUTHENTICATION       blob
);

comment on table OAUTH_REFRESH_TOKEN is
'Almac�n de datos de autenticaci�n de los token de actualizaci�n';

comment on column OAUTH_REFRESH_TOKEN.TOKEN_ID is
'Corresponde al identificador de la tabla';

comment on column OAUTH_REFRESH_TOKEN.TOKEN is
'Corresponde al token';

comment on column OAUTH_REFRESH_TOKEN.AUTHENTICATION is
'Corresponde a la autenticaci�n';

/*==============================================================*/
/* Table: SICOES_TM_CONF_BANDEJA                                */
/*==============================================================*/
create table SICOES_TM_CONF_BANDEJA 
(
   ID_CONF_BANDEJA      integer              not null,
   ID_TIPO_CONF         integer,
   ID_SECTOR_LD         integer,
   ID_SUBSECTOR_LD      integer,
   ID_ACTIVIDAD_LD      integer,
   ID_UNIDAD_LD         integer,
   ID_SUBCATEGORIA_LD   integer,
   ID_PERFIL_LD         integer,
   ID_USUARIO           integer,
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TM_CONF_BANDEJA is
'Almac�n de datos para los listados generales del sistema';

comment on column SICOES_TM_CONF_BANDEJA.ID_CONF_BANDEJA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_CONF_BANDEJA.ID_TIPO_CONF is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_CONF_BANDEJA.ID_SECTOR_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_CONF_BANDEJA.ID_SUBSECTOR_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_CONF_BANDEJA.ID_ACTIVIDAD_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_CONF_BANDEJA.ID_UNIDAD_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_CONF_BANDEJA.ID_SUBCATEGORIA_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_CONF_BANDEJA.ID_PERFIL_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_CONF_BANDEJA.ID_USUARIO is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_CONF_BANDEJA.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TM_CONF_BANDEJA.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TM_CONF_BANDEJA.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TM_CONF_BANDEJA.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TM_CONF_BANDEJA.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TM_CONF_BANDEJA.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TM_CONF_BANDEJA
   add constraint PK_SICOES_TM_CONF_BANDEJA primary key (ID_CONF_BANDEJA);

/*==============================================================*/
/* Table: SICOES_TM_LISTADO                                     */
/*==============================================================*/
create table SICOES_TM_LISTADO 
(
   ID_LISTADO           integer              not null,
   CD_LISTADO           varchar(50)          not null,
   NO_LISTADO           varchar(100)         not null,
   DE_LISTADO           varchar(200),
   CO_PADRE             varchar(50),
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TM_LISTADO is
'Almac�n de datos para los listados generales del sistema';

comment on column SICOES_TM_LISTADO.ID_LISTADO is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_LISTADO.CD_LISTADO is
'Corresponde al c�digo del listado';

comment on column SICOES_TM_LISTADO.NO_LISTADO is
'Corresponde al nombre del listado';

comment on column SICOES_TM_LISTADO.DE_LISTADO is
'Corresponde a la descripci�n del listado';

comment on column SICOES_TM_LISTADO.CO_PADRE is
'Corresponde al c�digo del padre';

comment on column SICOES_TM_LISTADO.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TM_LISTADO.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TM_LISTADO.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TM_LISTADO.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TM_LISTADO.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TM_LISTADO.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TM_LISTADO
   add constraint PK_SICOES_TM_LISTADO primary key (ID_LISTADO);

/*==============================================================*/
/* Table: SICOES_TM_LISTADO_DETALLE                             */
/*==============================================================*/
create table SICOES_TM_LISTADO_DETALLE 
(
   ID_LISTADO_DETALLE   integer              not null,
   ID_LISTADO           integer,
   ID_LISTADO_PADRE     integer,
   ID_SUPERIOR_LD       integer,
   CO_LISTADO_DETALLE   varchar(50)          not null,
   NU_ORDEN             integer,
   NO_LISTADO_DETALLE   varchar(500),
   DE_LISTADO_DETALLE   varchar(1000),
   DE_VALOR             varchar(4000),
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TM_LISTADO_DETALLE is
'Almac�n de datos para los detalles de los listados generales del sistema';

comment on column SICOES_TM_LISTADO_DETALLE.ID_LISTADO_DETALLE is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_LISTADO_DETALLE.ID_LISTADO is
'Corresponde al identificador de la tabla LISTADO';

comment on column SICOES_TM_LISTADO_DETALLE.ID_LISTADO_PADRE is
'Corresponde al identificador PADRE de la tabla LISTADO';

comment on column SICOES_TM_LISTADO_DETALLE.ID_SUPERIOR_LD is
'Corresponde al identificador SUPERIOR de la tabla LISTADO_DETALLE ';

comment on column SICOES_TM_LISTADO_DETALLE.CO_LISTADO_DETALLE is
'Corresponde al c�digo del detalle';

comment on column SICOES_TM_LISTADO_DETALLE.NU_ORDEN is
'Corresponde al nro de orden';

comment on column SICOES_TM_LISTADO_DETALLE.NO_LISTADO_DETALLE is
'Corresponde al nombre del detalle';

comment on column SICOES_TM_LISTADO_DETALLE.DE_LISTADO_DETALLE is
'Corresponde a la descripci�n del detalle';

comment on column SICOES_TM_LISTADO_DETALLE.DE_VALOR is
'Corresponde al valor del registro';

comment on column SICOES_TM_LISTADO_DETALLE.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TM_LISTADO_DETALLE.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TM_LISTADO_DETALLE.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TM_LISTADO_DETALLE.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TM_LISTADO_DETALLE.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TM_LISTADO_DETALLE.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TM_LISTADO_DETALLE
   add constraint PK_SICOES_TM_LISTADO_DETALLE primary key (ID_LISTADO_DETALLE);

/*==============================================================*/
/* Table: SICOES_TM_OPCION                                      */
/*==============================================================*/
create table SICOES_TM_OPCION 
(
   ID_OPCION            integer              not null,
   ID_PADRE             integer,
   ID_ESTADO_LD         integer,
   NO_OPCION            varchar(50),
   DE_OPCION            varchar(200),
   CO_OPCION            varchar(200),
   NU_ORDEN             varchar(200),
   FL_VISIBLE           varchar(1),
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TM_OPCION is
'Almac�n de datos para las opciones del sistema';

comment on column SICOES_TM_OPCION.ID_OPCION is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_OPCION.ID_PADRE is
'Corresponde al identificador de la tabla PADRE';

comment on column SICOES_TM_OPCION.ID_ESTADO_LD is
'Corresponde al identificador del ESTADO de la tabla LISTADO_DETALLE
';

comment on column SICOES_TM_OPCION.NO_OPCION is
'Corresponde al nombre de la opci�n';

comment on column SICOES_TM_OPCION.DE_OPCION is
'Corresponde a la descripci�n de la opci�n';

comment on column SICOES_TM_OPCION.CO_OPCION is
'Corresponde al c�digo de la opci�n';

comment on column SICOES_TM_OPCION.NU_ORDEN is
'Corresponde al nro de orden';

comment on column SICOES_TM_OPCION.FL_VISIBLE is
'Corresponde al flag de visibilidad';

comment on column SICOES_TM_OPCION.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TM_OPCION.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TM_OPCION.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TM_OPCION.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TM_OPCION.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TM_OPCION.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TM_OPCION
   add constraint PK_SICOES_TM_OPCION primary key (ID_OPCION);

/*==============================================================*/
/* Table: SICOES_TM_ROL                                         */
/*==============================================================*/
create table SICOES_TM_ROL 
(
   ID_ROL               integer              not null,
   ID_ESTADO_LD         integer,
   NO_ROL               varchar(50),
   DE_ROL               varchar(200),
   CO_ROL               varchar(200),
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TM_ROL is
'Almac�n de datos para los roles del sistema';

comment on column SICOES_TM_ROL.ID_ROL is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_ROL.ID_ESTADO_LD is
'Corresponde al identificador del ESTADO de la tabla LISTADO_DETALLE
';

comment on column SICOES_TM_ROL.NO_ROL is
'Corresponde al nombre del rol';

comment on column SICOES_TM_ROL.DE_ROL is
'Corresponde a la descripci�n del rol';

comment on column SICOES_TM_ROL.CO_ROL is
'Corresponde al c�digo del rol';

comment on column SICOES_TM_ROL.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TM_ROL.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TM_ROL.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TM_ROL.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TM_ROL.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TM_ROL.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TM_ROL
   add constraint PK_SICOES_TM_ROL primary key (ID_ROL);

/*==============================================================*/
/* Table: SICOES_TM_ROL_OPCION                                  */
/*==============================================================*/
create table SICOES_TM_ROL_OPCION 
(
   ID_ROL_OPCION        integer              not null,
   ID_OPCION            integer,
   ID_ROL               integer,
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TM_ROL_OPCION is
'Almac�n de datos para las opciones que tienen los roles del sistema';

comment on column SICOES_TM_ROL_OPCION.ID_ROL_OPCION is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_ROL_OPCION.ID_OPCION is
'Corresponde al identificador de la tabla OPCION';

comment on column SICOES_TM_ROL_OPCION.ID_ROL is
'Corresponde al identificador de la tabla ROL';

comment on column SICOES_TM_ROL_OPCION.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TM_ROL_OPCION.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TM_ROL_OPCION.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TM_ROL_OPCION.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TM_ROL_OPCION.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TM_ROL_OPCION.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TM_ROL_OPCION
   add constraint PK_SICOES_TM_ROL_OPCION primary key (ID_ROL_OPCION);

/*==============================================================*/
/* Table: SICOES_TM_SUPERVISORA                                 */
/*==============================================================*/
create table SICOES_TM_SUPERVISORA 
(
   ID_SUPERVISORA       integer              not null,
   ID_TIPO_DOCUMENTO_LD integer,
   ID_PAIS_LD           integer,
   ID_ESTADO_LD         integer,
   ID_TIPO_PERSONA_LD   integer,
   NU_EXPEDIENTE        varchar(20),
   NU_DOCUMENTO         varchar(20),
   NO_RAZON_SOCIAL      varchar(200),
   NO_PERSONA           varchar(200),
   AP_PATERNO           varchar(200),
   AP_MATERNO           varchar(200),
   CO_RUC               varchar(20),
   DE_DIRECCION         varchar(4000),
   CO_CLIENTE           varchar(100),
   CO_DEPARTAMENTO      varchar(10),
   CO_PROVINCIA         varchar(10),
   CO_DISTRITO          varchar(10),
   NO_DEPARTAMENTO      varchar(100),
   NO_PROVINCIA         varchar(100),
   NO_DISTRITO          varchar(100),
   CO_PARTIDA_REGISTRAL varchar(50),
   DE_TELEFONO1         varchar(20),
   DE_TELEFONO2         varchar(20),
   DE_TELEFONO3         varchar(20),
   CO_TIPO_PN           varchar(4000),
   DE_TIPO_PN           varchar(4000),
   DE_CORREO            varchar(200),
   FE_INGRESO           DATE,
   FE_SUSPEN_CANCELACION DATE,
   ID_SUSPEN_CANCELACION integer,
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TM_SUPERVISORA is
'Almac�n de datos de los supervisores del sistema';

comment on column SICOES_TM_SUPERVISORA.ID_SUPERVISORA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_SUPERVISORA.ID_TIPO_DOCUMENTO_LD is
'Corresponde al identificador del TIPO_DOCUMENTO de la tabla LISTADO_DETALLE';

comment on column SICOES_TM_SUPERVISORA.ID_PAIS_LD is
'Corresponde al identificador del PAIS de la tabla LISTADO_DETALLE';

comment on column SICOES_TM_SUPERVISORA.ID_ESTADO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_SUPERVISORA.ID_TIPO_PERSONA_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_SUPERVISORA.NU_EXPEDIENTE is
'Corresponde al n�mero de documento de la persona';

comment on column SICOES_TM_SUPERVISORA.NU_DOCUMENTO is
'Corresponde al n�mero de documento de la persona';

comment on column SICOES_TM_SUPERVISORA.NO_RAZON_SOCIAL is
'Corresponde a la raz�n social de la PJ';

comment on column SICOES_TM_SUPERVISORA.NO_PERSONA is
'Corresponde al nombre de PN';

comment on column SICOES_TM_SUPERVISORA.AP_PATERNO is
'Corresponde al apellido paterno de PN';

comment on column SICOES_TM_SUPERVISORA.AP_MATERNO is
'Corresponde al apellido materno de PN';

comment on column SICOES_TM_SUPERVISORA.CO_RUC is
'Corresponde al ruc de PN';

comment on column SICOES_TM_SUPERVISORA.DE_DIRECCION is
'Corresponde a la direcci�n de la persona';

comment on column SICOES_TM_SUPERVISORA.CO_CLIENTE is
'Corresponde al c�digo del cliente';

comment on column SICOES_TM_SUPERVISORA.CO_DEPARTAMENTO is
'Corresponde al c�digo ubigeo del departamento';

comment on column SICOES_TM_SUPERVISORA.CO_PROVINCIA is
'Corresponde al c�digo ubigeo de la provincia';

comment on column SICOES_TM_SUPERVISORA.CO_DISTRITO is
'Corresponde al c�digo ubigeo del distrito';

comment on column SICOES_TM_SUPERVISORA.NO_DEPARTAMENTO is
'Corresponde al nombre del departamento';

comment on column SICOES_TM_SUPERVISORA.NO_PROVINCIA is
'Corresponde al nombre de la provincia';

comment on column SICOES_TM_SUPERVISORA.NO_DISTRITO is
'Corresponde al nombre del distrito';

comment on column SICOES_TM_SUPERVISORA.CO_PARTIDA_REGISTRAL is
'Corresponde al c�digo de partida registral';

comment on column SICOES_TM_SUPERVISORA.DE_TELEFONO1 is
'Corresponde al n�mero de tel�fono 1';

comment on column SICOES_TM_SUPERVISORA.DE_TELEFONO2 is
'Corresponde al n�mero de tel�fono 2';

comment on column SICOES_TM_SUPERVISORA.DE_TELEFONO3 is
'Corresponde al n�mero de tel�fono 3';

comment on column SICOES_TM_SUPERVISORA.DE_TIPO_PN is
'Corresponde a la descripci�n de la observaci�n t�cnica';

comment on column SICOES_TM_SUPERVISORA.DE_CORREO is
'Corresponde al correo electr�nico de la persona';

comment on column SICOES_TM_SUPERVISORA.FE_INGRESO is
'Corresponde a la fecha de ingreso';

comment on column SICOES_TM_SUPERVISORA.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TM_SUPERVISORA.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TM_SUPERVISORA.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TM_SUPERVISORA.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TM_SUPERVISORA.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TM_SUPERVISORA.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TM_SUPERVISORA
   add constraint PK_SICOES_TM_SUPERVISORA primary key (ID_SUPERVISORA);

/*==============================================================*/
/* Table: SICOES_TM_SUPER_DICTAMEN                              */
/*==============================================================*/
create table SICOES_TM_SUPER_DICTAMEN 
(
   ID_SUPER_DICTAMEN    integer              not null,
   ID_SUPERVISORA       integer,
   ID_SECTOR_LD         integer,
   MO_FACTURADO         DECIMAL(16, 2),
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TM_SUPER_DICTAMEN is
'Almac�n de datos de los representantes legales de los usuarios del sistema';

comment on column SICOES_TM_SUPER_DICTAMEN.ID_SUPER_DICTAMEN is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_SUPER_DICTAMEN.ID_SUPERVISORA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_SUPER_DICTAMEN.ID_SECTOR_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_SUPER_DICTAMEN.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TM_SUPER_DICTAMEN.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TM_SUPER_DICTAMEN.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TM_SUPER_DICTAMEN.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TM_SUPER_DICTAMEN.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TM_SUPER_DICTAMEN.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TM_SUPER_DICTAMEN
   add constraint PK_SICOES_TM_SUPER_DICTAMEN primary key (ID_SUPER_DICTAMEN);

/*==============================================================*/
/* Table: SICOES_TM_SUPER_PERFIL                                */
/*==============================================================*/
create table SICOES_TM_SUPER_PERFIL 
(
   ID_SUPER_PERFIL      integer              not null,
   NU_EXPEDIENTE        varchar(20),
   ID_SUPERVISORA       integer,
   ID_ESTADO_LD         integer,
   ID_SECTOR_LD         integer,
   ID_SUBSECTOR_LD      integer,
   ID_ACTIVIDAD_LD      integer,
   ID_UNIDAD_LD         integer,
   ID_SUBCATEGORIA_LD   integer,
   ID_PERFIL_LD         integer,
   FE_INGRESO           DATE,
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TM_SUPER_PERFIL is
'Almac�n de datos de los perfiles que tienen los supervisores del sistema';

comment on column SICOES_TM_SUPER_PERFIL.ID_SUPER_PERFIL is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_SUPER_PERFIL.ID_SUPERVISORA is
'Corresponde al identificador de la tabla SUPERVISORA';

comment on column SICOES_TM_SUPER_PERFIL.ID_ESTADO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_SUPER_PERFIL.ID_SECTOR_LD is
'Corresponde al identificador del SECTOR de la tabla LISTADO_DETALLE';

comment on column SICOES_TM_SUPER_PERFIL.ID_SUBSECTOR_LD is
'Corresponde al identificador del SUBSECTOR de la tabla LISTADO_DETALLE';

comment on column SICOES_TM_SUPER_PERFIL.ID_ACTIVIDAD_LD is
'Corresponde al identificador de la ACTIVIDAD de la tabla LISTADO_DETALLE';

comment on column SICOES_TM_SUPER_PERFIL.ID_UNIDAD_LD is
'Corresponde al identificador de la UNIDAD de la tabla LISTADO_DETALLE';

comment on column SICOES_TM_SUPER_PERFIL.ID_SUBCATEGORIA_LD is
'Corresponde al identificador de la SUBCATEGORIA de la tabla LISTADO_DETALLE';

comment on column SICOES_TM_SUPER_PERFIL.ID_PERFIL_LD is
'Corresponde al identificador del PERFIL de la tabla LISTADO_DETALLE';

comment on column SICOES_TM_SUPER_PERFIL.FE_INGRESO is
'Corresponde a la fecha de ingreso';

comment on column SICOES_TM_SUPER_PERFIL.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TM_SUPER_PERFIL.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TM_SUPER_PERFIL.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TM_SUPER_PERFIL.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TM_SUPER_PERFIL.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TM_SUPER_PERFIL.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TM_SUPER_PERFIL
   add constraint PK_SICOES_TM_SUPER_PERFIL primary key (ID_SUPER_PERFIL);

/*==============================================================*/
/* Table: SICOES_TM_SUPER_REPRESENTANTE                         */
/*==============================================================*/
create table SICOES_TM_SUPER_REPRESENTANTE 
(
   ID_SUPER_REPRESENTANTE integer              not null,
   ID_TIPO_DOCUMENTO_LD integer,
   ID_SUPERVISORA       integer,
   NU_DOCUMENTO         varchar(20),
   NO_REPRESENTANTE     varchar(200),
   AP_PATERNO           varchar(4000),
   AP_MATERNO           varchar(10),
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TM_SUPER_REPRESENTANTE is
'Almac�n de datos de los representantes legales de los usuarios del sistema';

comment on column SICOES_TM_SUPER_REPRESENTANTE.ID_SUPER_REPRESENTANTE is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_SUPER_REPRESENTANTE.ID_TIPO_DOCUMENTO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_SUPER_REPRESENTANTE.ID_SUPERVISORA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_SUPER_REPRESENTANTE.NU_DOCUMENTO is
'Corresponde al nro del documento';

comment on column SICOES_TM_SUPER_REPRESENTANTE.AP_PATERNO is
'Corresponde al apellido paterno del representante';

comment on column SICOES_TM_SUPER_REPRESENTANTE.AP_MATERNO is
'Corresponde al apellido materno del representante';

comment on column SICOES_TM_SUPER_REPRESENTANTE.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TM_SUPER_REPRESENTANTE.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TM_SUPER_REPRESENTANTE.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TM_SUPER_REPRESENTANTE.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TM_SUPER_REPRESENTANTE.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TM_SUPER_REPRESENTANTE.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TM_SUPER_REPRESENTANTE
   add constraint PK_SICOES_TM_SUPER_REPRESENTAN primary key (ID_SUPER_REPRESENTANTE);

/*==============================================================*/
/* Table: SICOES_TM_UBIGEO                                      */
/*==============================================================*/
create table SICOES_TM_UBIGEO 
(
   ID_UBIGEO            integer              not null,
   CO_UBIGEO            varchar(10),
   CO_DEPARTAMENTO      varchar(10),
   NO_DEPARTAMENTO      varchar(100),
   CO_PROVINCIA         varchar(10),
   NO_PROVINCIA         varchar(100),
   CO_DISTRITO          varchar(10),
   NO_DISTRITO          varchar(100),
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TM_UBIGEO is
'Almac�n de datos del ubigeo';

comment on column SICOES_TM_UBIGEO.ID_UBIGEO is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_UBIGEO.CO_UBIGEO is
'Corresponde al c�digo del ubigeo';

comment on column SICOES_TM_UBIGEO.CO_DEPARTAMENTO is
'Corresponde al c�digo del departamento';

comment on column SICOES_TM_UBIGEO.NO_DEPARTAMENTO is
'Corresponde al nombre del departamento';

comment on column SICOES_TM_UBIGEO.CO_PROVINCIA is
'Corresponde al c�digo de la provincia';

comment on column SICOES_TM_UBIGEO.NO_PROVINCIA is
'Corresponde al nombre de la provincia';

comment on column SICOES_TM_UBIGEO.CO_DISTRITO is
'Corresponde al c�digo del distrito';

comment on column SICOES_TM_UBIGEO.NO_DISTRITO is
'Corresponde al nombre del distrito';

comment on column SICOES_TM_UBIGEO.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TM_UBIGEO.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TM_UBIGEO.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TM_UBIGEO.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TM_UBIGEO.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TM_UBIGEO.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TM_UBIGEO
   add constraint PK_SICOES_TM_UBIGEO primary key (ID_UBIGEO);

/*==============================================================*/
/* Table: SICOES_TM_USUARIO                                     */
/*==============================================================*/
create table SICOES_TM_USUARIO 
(
   ID_USUARIO           integer              not null,
   ID_TIPO_PERSONA_LD   integer,
   ID_TIPO_DOCUMENTO_LD integer,
   DE_RUC               varchar(50),
   ID_PAIS_LD           integer,
   ID_TIPO_USUARIO_LD   integer,
   DE_USUARIO           varchar(200),
   NO_USUARIO           varchar(200),
   CO_USUARIO           varchar(200),
   DE_CONTRASENIA       varchar(200),
   DE_RAZON_SOCIAL      varchar(200),
   DE_CORREO            varchar(200),
   NU_TELEFONO          varchar(30),
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TM_USUARIO is
'Almac�n de datos de los usuarios del sistema';

comment on column SICOES_TM_USUARIO.ID_USUARIO is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_USUARIO.ID_TIPO_PERSONA_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_USUARIO.ID_TIPO_DOCUMENTO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_USUARIO.DE_RUC is
'Corresponde a la descripci�n del ruc';

comment on column SICOES_TM_USUARIO.ID_PAIS_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_USUARIO.ID_TIPO_USUARIO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_USUARIO.DE_USUARIO is
'Corresponde a la descripci�n del usuario';

comment on column SICOES_TM_USUARIO.NO_USUARIO is
'Corresponde al nombre del usuario';

comment on column SICOES_TM_USUARIO.DE_CONTRASENIA is
'Corresponde a la descripci�n de la contrase�a';

comment on column SICOES_TM_USUARIO.DE_RAZON_SOCIAL is
'Corresponde a la descripci�n de la raz�n social';

comment on column SICOES_TM_USUARIO.DE_CORREO is
'Corresponde a la descripci�n del correo';

comment on column SICOES_TM_USUARIO.NU_TELEFONO is
'Corresponde a la n�mero de telefono';

comment on column SICOES_TM_USUARIO.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TM_USUARIO.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TM_USUARIO.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TM_USUARIO.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TM_USUARIO.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TM_USUARIO.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TM_USUARIO
   add constraint PK_SICOES_TM_USUARIO primary key (ID_USUARIO);

/*==============================================================*/
/* Table: SICOES_TM_USUARIO_ROL                                 */
/*==============================================================*/
create table SICOES_TM_USUARIO_ROL 
(
   ID_USUARIO_ROL       integer              not null,
   ID_ROL               integer,
   ID_USUARIO           integer,
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TM_USUARIO_ROL is
'Almac�n de datos de los roles que tienen los usuarios del sistema';

comment on column SICOES_TM_USUARIO_ROL.ID_USUARIO_ROL is
'Corresponde al identificador de la tabla';

comment on column SICOES_TM_USUARIO_ROL.ID_ROL is
'Corresponde al identificador de la tabla ROL';

comment on column SICOES_TM_USUARIO_ROL.ID_USUARIO is
'Corresponde al identificador de la tabla USUARIO';

comment on column SICOES_TM_USUARIO_ROL.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TM_USUARIO_ROL.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TM_USUARIO_ROL.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TM_USUARIO_ROL.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TM_USUARIO_ROL.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TM_USUARIO_ROL.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TM_USUARIO_ROL
   add constraint PK_SICOES_TM_USUARIO_ROL primary key (ID_USUARIO_ROL);

/*==============================================================*/
/* Table: SICOES_TR_ARCHIVO                                     */
/*==============================================================*/
create table SICOES_TR_ARCHIVO 
(
   ID_ARCHIVO           integer              not null,
   ID_DOCUMENTO         integer,
   ID_OTRO_REQUISITO    integer,
   ID_ESTUDIO           integer,
   ID_ESTADO_LD         integer,
   ID_TIPO_ARCHIVO_LD   integer,
   ID_SOLICITUD         integer,
   ID_NOTIFICACION_SOLICITUD integer,
   ID_NOTIFICACION      integer,
   ID_PRO_TECNICA       integer,
   ID_PRO_ECONOMICA     integer,
   ID_PROPUESTA         integer,
   ID_ASIGNACION        integer,
   NO_ARCHIVO           varchar(200),
   NO_REAL              varchar(200),
   NO_ALFRESCO          varchar(200),
   CO_ARCHIVO           varchar(200),
   DE_ARCHIVO           varchar(4000),
   CO_TIPO_ARCHIVO      varchar(200),
   NU_CORRELATIVO       integer,
   NU_VERSION           integer,
   NU_FOLIO             integer,
   NU_PESO              integer,
   FL_SIGED             integer,
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_ARCHIVO is
'Almac�n de datos de los archivos del sistema';

comment on column SICOES_TR_ARCHIVO.ID_ARCHIVO is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_ARCHIVO.ID_DOCUMENTO is
'Corresponde al identificador de la tabla DOCUMENTO';

comment on column SICOES_TR_ARCHIVO.ID_OTRO_REQUISITO is
'Corresponde al identificador de la tabla OTRO_REQUISITO';

comment on column SICOES_TR_ARCHIVO.ID_ESTUDIO is
'Corresponde al identificador de la tabla ESTUDIO';

comment on column SICOES_TR_ARCHIVO.ID_ESTADO_LD is
'Corresponde al identificador del ESTADO de la tabla LISTADO_DETALLE

';

comment on column SICOES_TR_ARCHIVO.ID_TIPO_ARCHIVO_LD is
'Corresponde al identificador del TIPO_ARCHIVO de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_ARCHIVO.ID_SOLICITUD is
'Corresponde al identificador de la tabla SOLICITUD';

comment on column SICOES_TR_ARCHIVO.ID_NOTIFICACION_SOLICITUD is
'Corresponde al identificador de la tabla SOL_NOTIFICACION';

comment on column SICOES_TR_ARCHIVO.ID_NOTIFICACION is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_ARCHIVO.ID_PRO_TECNICA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_ARCHIVO.ID_PRO_ECONOMICA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_ARCHIVO.ID_PROPUESTA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_ARCHIVO.ID_ASIGNACION is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_ARCHIVO.NO_ARCHIVO is
'Corresponde al nombre del archivo';

comment on column SICOES_TR_ARCHIVO.NO_REAL is
'Corresponde al nombre real del archivo';

comment on column SICOES_TR_ARCHIVO.NO_ALFRESCO is
'Corresponde al nombre del archivo en alfresco';

comment on column SICOES_TR_ARCHIVO.CO_ARCHIVO is
'Corresponde al c�digo del archivo';

comment on column SICOES_TR_ARCHIVO.DE_ARCHIVO is
'Corresponde a la descripci�n del archivo';

comment on column SICOES_TR_ARCHIVO.CO_TIPO_ARCHIVO is
'Corresponde al c�digo del tipo del archivo';

comment on column SICOES_TR_ARCHIVO.NU_CORRELATIVO is
'Corresponde al nro correlativo';

comment on column SICOES_TR_ARCHIVO.NU_VERSION is
'Corresponde al n�mero de la versi�n';

comment on column SICOES_TR_ARCHIVO.NU_FOLIO is
'Corresponde al nro de folios';

comment on column SICOES_TR_ARCHIVO.NU_PESO is
'Corresponde al peso del archivo';

comment on column SICOES_TR_ARCHIVO.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_ARCHIVO.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_ARCHIVO.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_ARCHIVO.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_ARCHIVO.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_ARCHIVO.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_ARCHIVO
   add constraint PK_SICOES_TR_ARCHIVO primary key (ID_ARCHIVO);

/*==============================================================*/
/* Table: SICOES_TR_ASIGNACION                                  */
/*==============================================================*/
create table SICOES_TR_ASIGNACION 
(
   ID_ASIGNACION        integer              not null,
   ID_SOLICITUD         integer,
   ID_TIPO_LD           integer,
   ID_USUARIO           integer,
   ID_GRUPO_LD          integer,
   ID_EVALUACION_LD     integer,
   DE_OBSERVACION       varchar(4000),
   FE_APROBACION        date,
   NU_DIA_PLAZO_RESP    integer,
   FE_PLAZO_RESP        DATE,
   FE_REGISTRO          date,
   FL_ACTIVO            integer,
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_ASIGNACION is
'Almac�n de datos de las asignaciones del sistema';

comment on column SICOES_TR_ASIGNACION.ID_ASIGNACION is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_ASIGNACION.ID_SOLICITUD is
'Corresponde al identificador de la tabla SOLICITUD';

comment on column SICOES_TR_ASIGNACION.ID_TIPO_LD is
'Corresponde al identificador del TIPO de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_ASIGNACION.ID_USUARIO is
'Corresponde al identificador de la tabla USUARIO';

comment on column SICOES_TR_ASIGNACION.ID_GRUPO_LD is
'Corresponde al identificador del GRUPO de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_ASIGNACION.ID_EVALUACION_LD is
'Corresponde al identificador de la EVALUACION de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_ASIGNACION.DE_OBSERVACION is
'Corresponde a la descripci�n de la observaci�n';

comment on column SICOES_TR_ASIGNACION.FE_APROBACION is
'Corresponde a la fecha de la aprobaci�n';

comment on column SICOES_TR_ASIGNACION.NU_DIA_PLAZO_RESP is
'Corresponde al n�mero de d�as m�ximo de plazo para responder';

comment on column SICOES_TR_ASIGNACION.FE_PLAZO_RESP is
'Corresponde a la fecha m�xima de plazo para responder';

comment on column SICOES_TR_ASIGNACION.FE_REGISTRO is
'Corresponde a la fecha de registro';

comment on column SICOES_TR_ASIGNACION.FL_ACTIVO is
'Corresponde al flag activo';

comment on column SICOES_TR_ASIGNACION.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_ASIGNACION.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_ASIGNACION.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_ASIGNACION.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_ASIGNACION.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_ASIGNACION.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_ASIGNACION
   add constraint PK_SICOES_TR_ASIGNACION primary key (ID_ASIGNACION);

/*==============================================================*/
/* Table: SICOES_TR_BITACORA                                    */
/*==============================================================*/
create table SICOES_TR_BITACORA 
(
   ID_BITACORA          integer              not null,
   ID_USUARIO           integer,
   FECHA_HORA           DATE,
   DESCRIPCION          varchar(4000),
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_BITACORA is
'Almac�n de datos de los token del sistema';

comment on column SICOES_TR_BITACORA.ID_BITACORA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_BITACORA.ID_USUARIO is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_BITACORA.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_BITACORA.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_BITACORA.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_BITACORA.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_BITACORA.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_BITACORA.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_BITACORA
   add constraint PK_SICOES_TR_BITACORA primary key (ID_BITACORA);

/*==============================================================*/
/* Table: SICOES_TR_DICTAMEN_EVAL                               */
/*==============================================================*/
create table SICOES_TR_DICTAMEN_EVAL 
(
   ID_DICTAMEN_EVAL     integer              not null,
   ID_SOLICITUD         integer,
   ID_SECTOR_LD         integer,
   MO_FACTURADO         DECIMAL(16, 2),
   US_CREACION          varchar(50)          not null,
   FE_CREACION          DATE                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     DATE,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_DICTAMEN_EVAL is
'Almac�n de datos de los ';

comment on column SICOES_TR_DICTAMEN_EVAL.ID_DICTAMEN_EVAL is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_DICTAMEN_EVAL.ID_SOLICITUD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_DICTAMEN_EVAL.ID_SECTOR_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_DICTAMEN_EVAL.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_DICTAMEN_EVAL.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_DICTAMEN_EVAL.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_DICTAMEN_EVAL.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_DICTAMEN_EVAL.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_DICTAMEN_EVAL.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_DICTAMEN_EVAL
   add constraint PK_SICOES_TR_DICTAMEN_EVAL primary key (ID_DICTAMEN_EVAL);

/*==============================================================*/
/* Table: SICOES_TR_DOCUMENTO                                   */
/*==============================================================*/
create table SICOES_TR_DOCUMENTO 
(
   ID_DOCUMENTO         integer              not null,
   ID_TIPO_LD           integer,
   ID_TIPO_DOCUMENTO_LD integer,
   ID_TIPO_CAMBIO_LD    integer,
   ID_SOLICITUD         integer,
   ID_DOCUMENTO_PADRE   integer,
   NU_DOCUMENTO         varchar(20),
   CO_CONTRATO          varchar(200),
   DE_CONTRATO          varchar(200),
   FE_INICIO_CONTRATO   date,
   FE_FIN_CONTRATO      date,
   DE_DURACION          varchar(200),
   FL_VIGENTE           varchar(1),
   FE_CONFORMIDAD       date,
   MO_CONTRATO          number(18,2),
   MO_TIPO_CAMBIO       number(18,2),
   ID_EVALUACION_LD     integer,
   ID_PAIS_LD           integer,
   ID_TIPO_ID_TRIBURARIO_LD integer,
   ID_CONFORMIDAD_LD    integer,
   MO_CONTRATO_SOL      number(18,2),
   DE_OBSERVACION       varchar(4000),
   NO_ENTIDAD           varchar(4000),
   FL_SIGED             INTEGER,
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_DOCUMENTO is
'Almac�n de datos de los documentos del sistema';

comment on column SICOES_TR_DOCUMENTO.ID_DOCUMENTO is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_DOCUMENTO.ID_TIPO_LD is
'Corresponde al identificador del TIPO de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_DOCUMENTO.ID_TIPO_DOCUMENTO_LD is
'Corresponde al identificador del TIPO_DOCUMENTO de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_DOCUMENTO.ID_TIPO_CAMBIO_LD is
'Corresponde al identificador del TIPO_CAMBIO de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_DOCUMENTO.ID_SOLICITUD is
'Corresponde al identificador de la tabla SOLICITUD';

comment on column SICOES_TR_DOCUMENTO.ID_DOCUMENTO_PADRE is
'Corresponde al identificador PADRE de la tabla DOCUMENTO';

comment on column SICOES_TR_DOCUMENTO.NU_DOCUMENTO is
'Corresponde al nro del documento';

comment on column SICOES_TR_DOCUMENTO.CO_CONTRATO is
'Corresponde al c�digo del contrato';

comment on column SICOES_TR_DOCUMENTO.DE_CONTRATO is
'Corresponde a la descripci�n del contrato';

comment on column SICOES_TR_DOCUMENTO.FE_INICIO_CONTRATO is
'Corresponde a la fecha inicio del contrato';

comment on column SICOES_TR_DOCUMENTO.FE_FIN_CONTRATO is
'Corresponde a la fecha fin del contrato';

comment on column SICOES_TR_DOCUMENTO.DE_DURACION is
'Corresponde a la descripci�n de la duraci�n';

comment on column SICOES_TR_DOCUMENTO.FL_VIGENTE is
'Corresponde a la vigencia del contrato';

comment on column SICOES_TR_DOCUMENTO.FE_CONFORMIDAD is
'Corresponde a la fecha de conformidad';

comment on column SICOES_TR_DOCUMENTO.MO_CONTRATO is
'Corresponde al monto del contrato';

comment on column SICOES_TR_DOCUMENTO.MO_TIPO_CAMBIO is
'Corresponde al tipo de cambio';

comment on column SICOES_TR_DOCUMENTO.ID_EVALUACION_LD is
'Corresponde al identificador de la EVALUACION de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_DOCUMENTO.ID_PAIS_LD is
'Corresponde al identificador del PAIS de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_DOCUMENTO.ID_TIPO_ID_TRIBURARIO_LD is
'Corresponde al identificador del TIPO_ID_TRIBUTARIO de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_DOCUMENTO.ID_CONFORMIDAD_LD is
'Corresponde al identificador de la CONFORMIDAD de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_DOCUMENTO.MO_CONTRATO_SOL is
'Corresponde al monto en soles del contrato cobrado del monto total';

comment on column SICOES_TR_DOCUMENTO.DE_OBSERVACION is
'Corresponde a la descripci�n de la observaci�n';

comment on column SICOES_TR_DOCUMENTO.NO_ENTIDAD is
'Corresponde a la nombre de la entidad';

comment on column SICOES_TR_DOCUMENTO.FL_SIGED is
'Corresponde al flag siged';

comment on column SICOES_TR_DOCUMENTO.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_DOCUMENTO.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_DOCUMENTO.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_DOCUMENTO.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_DOCUMENTO.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_DOCUMENTO.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_DOCUMENTO
   add constraint PK_SICOES_TR_DOCUMENTO primary key (ID_DOCUMENTO);

/*==============================================================*/
/* Table: SICOES_TR_ESTUDIO                                     */
/*==============================================================*/
create table SICOES_TR_ESTUDIO 
(
   ID_ESTUDIO           integer              not null,
   ID_TIPO_LD           integer,
   FL_EGRESADO          varchar(50),
   DE_COLEGIATURA       varchar(50),
   DE_ESPECIALIDAD      varchar(200),
   DE_DETALLE_TIPO      varchar(200),
   FE_VIGENCIA_GRADO    DATE,
   NO_INSTITUCION       varchar(200),
   FL_COLEGIATURA       varchar(50),
   FE_VIGENCIA_COLEGIATURA DATE,
   NO_INSTITUCION_COLEGIATURA varchar(200),
   NU_HORA              integer,
   FE_VIGENCIA          DATE,
   FE_INICIO            DATE,
   FE_FIN               DATE,
   ID_EVALUACION_LD     integer,
   ID_SOLICITUD         integer,
   ID_ESTUDIO_PADRE     integer,
   ID_TIPO_ESTUDIO_LD   integer,
   ID_FUENTE_LD         integer,
   DE_OBSERVACION       varchar(4000),
   FL_ACTIVO            integer,
   FL_SIGED             INTEGER,
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_ESTUDIO is
'Almac�n de datos de los estudios de experiencia de los usuarios del sistema';

comment on column SICOES_TR_ESTUDIO.ID_ESTUDIO is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_ESTUDIO.ID_TIPO_LD is
'Corresponde al identificador del TIPO de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_ESTUDIO.FL_EGRESADO is
'Corresponde al flag egresado';

comment on column SICOES_TR_ESTUDIO.DE_COLEGIATURA is
'Corresponde a la descripci�n de la colegiatura';

comment on column SICOES_TR_ESTUDIO.DE_ESPECIALIDAD is
'Corresponde a la descripci�n de la especialidad';

comment on column SICOES_TR_ESTUDIO.DE_DETALLE_TIPO is
'Corresponde a la descripci�n de la especialidad';

comment on column SICOES_TR_ESTUDIO.FE_VIGENCIA_GRADO is
'Corresponde a fecha de la vigencia del grado';

comment on column SICOES_TR_ESTUDIO.NO_INSTITUCION is
'Corresponde al nombre de la instituci�n';

comment on column SICOES_TR_ESTUDIO.FL_COLEGIATURA is
'Corresponde al flag colegiatura';

comment on column SICOES_TR_ESTUDIO.FE_VIGENCIA_COLEGIATURA is
'Corresponde a la fecha de vigencia de la colegiatura';

comment on column SICOES_TR_ESTUDIO.NO_INSTITUCION_COLEGIATURA is
'Corresponde al nombre de la instituci�n de la colegiatura';

comment on column SICOES_TR_ESTUDIO.NU_HORA is
'Corresponde al nro de horas';

comment on column SICOES_TR_ESTUDIO.FE_VIGENCIA is
'Corresponde a la fecha de vigencia';

comment on column SICOES_TR_ESTUDIO.FE_INICIO is
'Corresponde a la fecha de inicio';

comment on column SICOES_TR_ESTUDIO.FE_FIN is
'Corresponde a la fecha fin';

comment on column SICOES_TR_ESTUDIO.ID_EVALUACION_LD is
'Corresponde al identificador de la EVALUACION de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_ESTUDIO.ID_SOLICITUD is
'Corresponde al identificador de la tabla SOLICITUD';

comment on column SICOES_TR_ESTUDIO.ID_ESTUDIO_PADRE is
'Corresponde al identificador PADRE de la tabla ESTUDIO';

comment on column SICOES_TR_ESTUDIO.ID_TIPO_ESTUDIO_LD is
'Corresponde al identificador del TIPO_ESTUDIO de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_ESTUDIO.ID_FUENTE_LD is
'Corresponde al identificador de la FUENTE de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_ESTUDIO.DE_OBSERVACION is
'Corresponde a la descripci�n de la observaci�n';

comment on column SICOES_TR_ESTUDIO.FL_ACTIVO is
'Corresponde al flag activo';

comment on column SICOES_TR_ESTUDIO.FL_SIGED is
'Corresponde al flag siged';

comment on column SICOES_TR_ESTUDIO.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_ESTUDIO.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_ESTUDIO.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_ESTUDIO.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_ESTUDIO.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_ESTUDIO.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_ESTUDIO
   add constraint PK_SICOES_TR_ESTUDIO primary key (ID_ESTUDIO);

/*==============================================================*/
/* Table: SICOES_TR_INTEGRANTE                                  */
/*==============================================================*/
create table SICOES_TR_INTEGRANTE 
(
   ID_INTEGRANTE        integer              not null,
   ID_PRO_TECNICA       integer,
   ID_TIPO_LD           integer,
   ID_SUPERVISORA       integer,
   NU_PARTICIPACION     number(16,2),
   MO_EVALUADO          number(16,2),
   MO_REQUERIDO         number(16,2),
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_INTEGRANTE is
'Almac�n de datos de los ';

comment on column SICOES_TR_INTEGRANTE.ID_INTEGRANTE is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_INTEGRANTE.ID_PRO_TECNICA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_INTEGRANTE.ID_TIPO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_INTEGRANTE.ID_SUPERVISORA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_INTEGRANTE.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_INTEGRANTE.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_INTEGRANTE.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_INTEGRANTE.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_INTEGRANTE.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_INTEGRANTE.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_INTEGRANTE
   add constraint PK_SICOES_TR_INTEGRANTE primary key (ID_INTEGRANTE);

/*==============================================================*/
/* Table: SICOES_TR_ITEM_ESTADO                                 */
/*==============================================================*/
create table SICOES_TR_ITEM_ESTADO 
(
   ID_ITEM_ESTADO       integer              not null,
   ID_PROCESO_ITEM      integer,
   DE_ITEM              varchar(4000),
   ID_ESTADO_LD         integer,
   ID_ESTADO_ANTERIOR_LD integer,
   ID_USUARIO           integer,
   FE_REGISTRO          date                 not null,
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_ITEM_ESTADO is
'Almac�n de datos de los ';

comment on column SICOES_TR_ITEM_ESTADO.ID_ITEM_ESTADO is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_ITEM_ESTADO.ID_PROCESO_ITEM is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_ITEM_ESTADO.ID_ESTADO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_ITEM_ESTADO.ID_ESTADO_ANTERIOR_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_ITEM_ESTADO.ID_USUARIO is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_ITEM_ESTADO.FE_REGISTRO is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_ITEM_ESTADO.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_ITEM_ESTADO.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_ITEM_ESTADO.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_ITEM_ESTADO.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_ITEM_ESTADO.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_ITEM_ESTADO.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_ITEM_ESTADO
   add constraint PK_SICOES_TR_ITEM_ESTADO primary key (ID_ITEM_ESTADO);

/*==============================================================*/
/* Table: SICOES_TR_NOTIFICACION                                */
/*==============================================================*/
create table SICOES_TR_NOTIFICACION 
(
   ID_NOTIFICACION      integer              not null,
   ID_ESTADO_LD         integer,
   CORREO               varchar(4000),
   ASUNTO               varchar(4000),
   MENSAJE              varchar(4000),
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_NOTIFICACION is
'Almac�n de datos para las notificaciones del sistema
';

comment on column SICOES_TR_NOTIFICACION.ID_NOTIFICACION is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_NOTIFICACION.ID_ESTADO_LD is
'Corresponde al identificador del ESTADO de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_NOTIFICACION.CORREO is
'Corresponde al correo';

comment on column SICOES_TR_NOTIFICACION.ASUNTO is
'Corresponde al asunto';

comment on column SICOES_TR_NOTIFICACION.MENSAJE is
'Corresponde al mensaje';

comment on column SICOES_TR_NOTIFICACION.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_NOTIFICACION.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_NOTIFICACION.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_NOTIFICACION.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_NOTIFICACION.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_NOTIFICACION.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_NOTIFICACION
   add constraint PK_SICOES_TR_NOTIFICACION primary key (ID_NOTIFICACION);

/*==============================================================*/
/* Table: SICOES_TR_OTRO_REQUISITO                              */
/*==============================================================*/
create table SICOES_TR_OTRO_REQUISITO 
(
   ID_OTRO_REQUISITO    integer              not null,
   ID_TIPO_LD           integer,
   ID_TIPO_REQUISITO_LD integer,
   ID_SECTOR_LD         integer,
   ID_SUBSECTOR_LD      integer,
   ID_ACTIVIDAD_LD      integer,
   ID_UNIDAD_LD         integer,
   ID_SUBCATEGORIA_LD   integer,
   ID_PERFIL_LD         integer,
   FL_ELECTRONICO       varchar(1),
   FL_FIRMA_DIGITAL     varchar(1),
   FE_EXPEDICION        DATE,
   FL_ACTIVO            varchar(1),
   ID_EVALUACION_LD     integer,
   ID_RESULTADO_LD      integer,
   ID_SOLICITUD         integer,
   ID_OTRO_REQUISITO_PADRE integer,
   ID_FINALIZADO_LD     integer,
   ID_USUARIO_FIN       integer,
   FE_FINALIZACION      DATE,
   DE_OBSERVACION       varchar(4000),
   FL_SIGED             INTEGER,
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_OTRO_REQUISITO is
'Almac�n de datos para los otros requisitos de los usuarios del sistema';

comment on column SICOES_TR_OTRO_REQUISITO.ID_OTRO_REQUISITO is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_OTRO_REQUISITO.ID_TIPO_LD is
'Corresponde al identificador del TIPO de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_OTRO_REQUISITO.ID_TIPO_REQUISITO_LD is
'Corresponde al identificador del TIPO_REQUISITO de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_OTRO_REQUISITO.ID_SECTOR_LD is
'Corresponde al identificador del SECTOR de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_OTRO_REQUISITO.ID_SUBSECTOR_LD is
'Corresponde al identificador del SUBSECTOR de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_OTRO_REQUISITO.ID_ACTIVIDAD_LD is
'Corresponde al identificador de la ACTIVIDAD de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_OTRO_REQUISITO.ID_UNIDAD_LD is
'Corresponde al identificador de la UNIDAD de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_OTRO_REQUISITO.ID_SUBCATEGORIA_LD is
'Corresponde al identificador de la SUBCATEGORIA de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_OTRO_REQUISITO.ID_PERFIL_LD is
'Corresponde al identificador del PERFIL de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_OTRO_REQUISITO.FL_FIRMA_DIGITAL is
'Corresponde al flag firma digital';

comment on column SICOES_TR_OTRO_REQUISITO.FE_EXPEDICION is
'Corresponde a la fecha de expedici�n';

comment on column SICOES_TR_OTRO_REQUISITO.FL_ACTIVO is
'Corresponde al flag activo';

comment on column SICOES_TR_OTRO_REQUISITO.ID_EVALUACION_LD is
'Corresponde al identificador de la EVALUACION de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_OTRO_REQUISITO.ID_RESULTADO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_OTRO_REQUISITO.ID_SOLICITUD is
'Corresponde al identificador de la tabla SOLICITUD';

comment on column SICOES_TR_OTRO_REQUISITO.ID_OTRO_REQUISITO_PADRE is
'Corresponde al identificador PADRE de la tabla OTRO_REQUISITO';

comment on column SICOES_TR_OTRO_REQUISITO.ID_FINALIZADO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_OTRO_REQUISITO.ID_USUARIO_FIN is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_OTRO_REQUISITO.DE_OBSERVACION is
'Corresponde a la descripci�n de la observaci�n';

comment on column SICOES_TR_OTRO_REQUISITO.FL_SIGED is
'Corresponde al flag siged';

comment on column SICOES_TR_OTRO_REQUISITO.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_OTRO_REQUISITO.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_OTRO_REQUISITO.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_OTRO_REQUISITO.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_OTRO_REQUISITO.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_OTRO_REQUISITO.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_OTRO_REQUISITO
   add constraint PK_SICOES_TR_OTRO_REQUISITO primary key (ID_OTRO_REQUISITO);

/*==============================================================*/
/* Table: SICOES_TR_PERSONA                                     */
/*==============================================================*/
create table SICOES_TR_PERSONA 
(
   ID_PERSONA           integer              not null,
   ID_TIPO_DOCUMENTO_LD integer,
   ID_PAIS_LD           integer,
   ID_TIPO_PERSONA_LD   integer,
   NU_DOCUMENTO         varchar(20),
   NO_RAZON_SOCIAL      varchar(200),
   NO_PERSONA           varchar(200),
   AP_PATERNO           varchar(200),
   AP_MATERNO           varchar(200),
   CO_RUC               varchar(20),
   DE_DIRECCION         varchar(4000),
   CO_CLIENTE           varchar(100),
   CO_DEPARTAMENTO      varchar(10),
   CO_PROVINCIA         varchar(10),
   CO_DISTRITO          varchar(10),
   CO_TIPO_PN           varchar(4000),
   DE_TIPO_PN           varchar(4000),
   NO_DEPARTAMENTO      varchar(100),
   NO_PROVINCIA         varchar(100),
   NO_DISTRITO          varchar(100),
   CO_PARTIDA_REGISTRAL varchar(50),
   DE_TELEFONO1         varchar(20),
   DE_TELEFONO2         varchar(20),
   DE_TELEFONO3         varchar(20),
   DE_CORREO            varchar(200),
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_PERSONA is
'Almac�n de datos de las personas del sistema';

comment on column SICOES_TR_PERSONA.ID_PERSONA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PERSONA.ID_TIPO_DOCUMENTO_LD is
'Corresponde al identificador del TIPO_DOCUMENTO de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_PERSONA.ID_PAIS_LD is
'Corresponde al identificador del PAIS de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_PERSONA.ID_TIPO_PERSONA_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PERSONA.NU_DOCUMENTO is
'Corresponde al n�mero de documento de la persona';

comment on column SICOES_TR_PERSONA.NO_RAZON_SOCIAL is
'Corresponde a la raz�n social de la PJ';

comment on column SICOES_TR_PERSONA.NO_PERSONA is
'Corresponde al nombre de PN';

comment on column SICOES_TR_PERSONA.AP_PATERNO is
'Corresponde al apellido paterno de PN';

comment on column SICOES_TR_PERSONA.AP_MATERNO is
'Corresponde al apellido materno de PN';

comment on column SICOES_TR_PERSONA.CO_RUC is
'Corresponde al ruc de PN';

comment on column SICOES_TR_PERSONA.DE_DIRECCION is
'Corresponde a la direcci�n de la persona';

comment on column SICOES_TR_PERSONA.CO_CLIENTE is
'Corresponde al c�digo del cliente';

comment on column SICOES_TR_PERSONA.CO_DEPARTAMENTO is
'Corresponde al c�digo ubigeo del departamento';

comment on column SICOES_TR_PERSONA.CO_PROVINCIA is
'Corresponde al c�digo ubigeo de la provincia';

comment on column SICOES_TR_PERSONA.CO_DISTRITO is
'Corresponde al c�digo ubigeo del distrito';

comment on column SICOES_TR_PERSONA.DE_TIPO_PN is
'Corresponde a la descripci�n de la observaci�n t�cnica';

comment on column SICOES_TR_PERSONA.NO_DEPARTAMENTO is
'Corresponde al nombre del departamento';

comment on column SICOES_TR_PERSONA.NO_PROVINCIA is
'Corresponde al nombre de la provincia';

comment on column SICOES_TR_PERSONA.NO_DISTRITO is
'Corresponde al nombre del distrito';

comment on column SICOES_TR_PERSONA.CO_PARTIDA_REGISTRAL is
'Corresponde al c�digo de partida registral';

comment on column SICOES_TR_PERSONA.DE_TELEFONO1 is
'Corresponde al n�mero de tel�fono 1';

comment on column SICOES_TR_PERSONA.DE_TELEFONO2 is
'Corresponde al n�mero de tel�fono 2';

comment on column SICOES_TR_PERSONA.DE_TELEFONO3 is
'Corresponde al n�mero de tel�fono 3';

comment on column SICOES_TR_PERSONA.DE_CORREO is
'Corresponde al correo electr�nico de la persona';

comment on column SICOES_TR_PERSONA.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_PERSONA.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_PERSONA.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_PERSONA.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_PERSONA.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_PERSONA.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_PERSONA
   add constraint PK_SICOES_TR_PERSONA primary key (ID_PERSONA);

/*==============================================================*/
/* Table: SICOES_TR_PROCESO                                     */
/*==============================================================*/
create table SICOES_TR_PROCESO 
(
   ID_PROCESO           integer              not null,
   ID_SECTOR_LD         integer,
   ID_SUBSECTOR_LD      integer,
   ID_ESTADO_LD         integer,
   ID_USUARIO           integer,
   ID_USUARIO_CREADOR   integer,
   ID_TIPO_FACTURACION_LD integer,
   CO_UUID              varchar(50),
   FE_PUBLICACION       DATE,
   NU_PROCESO           varchar(50),
   NO_PROCESO           varchar(200),
   NU_EXPEDIENTE        varchar(50),
   CO_AREA              varchar(50),
   NO_AREA              varchar(200),
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_PROCESO is
'Almac�n de datos de los ';

comment on column SICOES_TR_PROCESO.ID_SECTOR_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROCESO.ID_SUBSECTOR_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROCESO.ID_ESTADO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROCESO.ID_USUARIO is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROCESO.ID_USUARIO_CREADOR is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROCESO.ID_TIPO_FACTURACION_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROCESO.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_PROCESO.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_PROCESO.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_PROCESO.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_PROCESO.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_PROCESO.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_PROCESO
   add constraint PK_SICOES_TR_PROCESO primary key (ID_PROCESO);

/*==============================================================*/
/* Table: SICOES_TR_PROCESO_ETAPA                               */
/*==============================================================*/
create table SICOES_TR_PROCESO_ETAPA 
(
   ID_PROCESO_ETAPA     integer              not null,
   ID_ETAPA_LD          integer,
   ID_PROCESO_LD        integer,
   ID_PROCESO           integer,
   FE_INICIO            DATE,
   FE_FIN               DATE,
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_PROCESO_ETAPA is
'Almac�n de datos de los ';

comment on column SICOES_TR_PROCESO_ETAPA.ID_PROCESO_ETAPA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROCESO_ETAPA.ID_ETAPA_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROCESO_ETAPA.ID_PROCESO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROCESO_ETAPA.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_PROCESO_ETAPA.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_PROCESO_ETAPA.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_PROCESO_ETAPA.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_PROCESO_ETAPA.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_PROCESO_ETAPA.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_PROCESO_ETAPA
   add constraint PK_SICOES_TR_PROCESO_ETAPA primary key (ID_PROCESO_ETAPA);

/*==============================================================*/
/* Table: SICOES_TR_PROCESO_ITEM                                */
/*==============================================================*/
create table SICOES_TR_PROCESO_ITEM 
(
   ID_PROCESO_ITEM      integer              not null,
   ID_PROCESO           integer,
   ID_ESTADO_LD         integer,
   ID_DIVISA_LD         integer,
   CO_UUID              varchar(50),
   NU_ITEM              integer,
   DE_ITEM              varchar(200),
   NU_IMPORTE_REF       NUMBER(18,2),
   NU_IMPORTE           NUMBER(18,2),
   NU_TIPO_CAMBIO       NUMBER(18,2),
   NU_FACTURACION_MIN   NUMBER(18,2),
   DE_DESCARGA          varchar(500),
   DE_RUTA_DESCARGA     varchar(500),
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_PROCESO_ITEM is
'Almac�n de datos de los ';

comment on column SICOES_TR_PROCESO_ITEM.ID_PROCESO_ITEM is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROCESO_ITEM.ID_ESTADO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROCESO_ITEM.ID_DIVISA_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROCESO_ITEM.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_PROCESO_ITEM.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_PROCESO_ITEM.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_PROCESO_ITEM.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_PROCESO_ITEM.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_PROCESO_ITEM.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_PROCESO_ITEM
   add constraint PK_SICOES_TR_PROCESO_ITEM primary key (ID_PROCESO_ITEM);

/*==============================================================*/
/* Table: SICOES_TR_PROCESO_ITEM_PERFIL                         */
/*==============================================================*/
create table SICOES_TR_PROCESO_ITEM_PERFIL 
(
   ID_PROCESO_ITEM_PERFIL integer              not null,
   ID_PROCESO_ITEM      integer,
   ID_SECTOR_LD         integer,
   ID_SUBSECTOR_LD      integer,
   ID_PERFIL_LD         integer,
   ID_ESTADO_LD         integer,
   NRO_PROFESIONALES    integer,
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_PROCESO_ITEM_PERFIL is
'Almac�n de datos de los ';

comment on column SICOES_TR_PROCESO_ITEM_PERFIL.ID_PROCESO_ITEM_PERFIL is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROCESO_ITEM_PERFIL.ID_PROCESO_ITEM is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROCESO_ITEM_PERFIL.ID_SECTOR_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROCESO_ITEM_PERFIL.ID_SUBSECTOR_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROCESO_ITEM_PERFIL.ID_PERFIL_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROCESO_ITEM_PERFIL.ID_ESTADO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROCESO_ITEM_PERFIL.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_PROCESO_ITEM_PERFIL.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_PROCESO_ITEM_PERFIL.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_PROCESO_ITEM_PERFIL.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_PROCESO_ITEM_PERFIL.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_PROCESO_ITEM_PERFIL.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_PROCESO_ITEM_PERFIL
   add constraint PK_SICOES_TR_PROCESO_ITEM_PERF primary key (ID_PROCESO_ITEM_PERFIL);

/*==============================================================*/
/* Table: SICOES_TR_PROCESO_MIEMBRO                             */
/*==============================================================*/
create table SICOES_TR_PROCESO_MIEMBRO 
(
   ID_PROCESO_MIEMBRO   integer              not null,
   ID_CARGO_LD          integer,
   ID_PROCESO           integer,
   ID_ESTADO_LD         integer,
   CO_USUARIO           integer,
   NO_USUARIO           varchar(200),
   FE_REGISTRO          DATE,
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_PROCESO_MIEMBRO is
'Almac�n de datos de los ';

comment on column SICOES_TR_PROCESO_MIEMBRO.ID_PROCESO_MIEMBRO is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROCESO_MIEMBRO.ID_CARGO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROCESO_MIEMBRO.ID_ESTADO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROCESO_MIEMBRO.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_PROCESO_MIEMBRO.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_PROCESO_MIEMBRO.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_PROCESO_MIEMBRO.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_PROCESO_MIEMBRO.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_PROCESO_MIEMBRO.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_PROCESO_MIEMBRO
   add constraint PK_SICOES_TR_PROCESO_MIEMBRO primary key (ID_PROCESO_MIEMBRO);

/*==============================================================*/
/* Table: SICOES_TR_PROPUESTA                                   */
/*==============================================================*/
create table SICOES_TR_PROPUESTA 
(
   ID_PROPUESTA         integer              not null,
   ID_SUPERVISORA       integer,
   CO_UUID              varchar(50),
   FE_PRESENTACION      DATE,
   ID_ESTADO_LD         integer,
   ID_PROCESO_ITEM      integer,
   ID_PRO_TECNICA       integer,
   ID_PRO_ECONOMICA     integer,
   ID_GANADOR_LD        integer,
   DE_DESCARGA          varchar(500),
   DE_RUTA_DESCARGA     varchar(500),
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_PROPUESTA is
'Almac�n de datos de los ';

comment on column SICOES_TR_PROPUESTA.ID_PROPUESTA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROPUESTA.ID_SUPERVISORA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROPUESTA.ID_ESTADO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROPUESTA.ID_PROCESO_ITEM is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROPUESTA.ID_PRO_TECNICA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROPUESTA.ID_PRO_ECONOMICA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROPUESTA.ID_GANADOR_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PROPUESTA.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_PROPUESTA.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_PROPUESTA.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_PROPUESTA.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_PROPUESTA.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_PROPUESTA.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_PROPUESTA
   add constraint PK_SICOES_TR_PROPUESTA primary key (ID_PROPUESTA);

/*==============================================================*/
/* Table: SICOES_TR_PRO_ECONOMICA                               */
/*==============================================================*/
create table SICOES_TR_PRO_ECONOMICA 
(
   ID_PRO_ECONOMICA     integer              not null,
   DE_IMPORTE           varchar(4000),
   NU_FOLIO_INICIO      integer,
   NU_FOLIO_FIN         integer,
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_PRO_ECONOMICA is
'Almac�n de datos de los ';

comment on column SICOES_TR_PRO_ECONOMICA.ID_PRO_ECONOMICA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PRO_ECONOMICA.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_PRO_ECONOMICA.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_PRO_ECONOMICA.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_PRO_ECONOMICA.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_PRO_ECONOMICA.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_PRO_ECONOMICA.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_PRO_ECONOMICA
   add constraint PK_SICOES_TR_PRO_ECONOMICA primary key (ID_PRO_ECONOMICA);

/*==============================================================*/
/* Table: SICOES_TR_PRO_PROFESIONAL                             */
/*==============================================================*/
create table SICOES_TR_PRO_PROFESIONAL 
(
   ID_PRO_PROFESIONAL   integer              not null,
   ID_PROPUESTA         integer,
   ID_SUPERVISORA       integer,
   ID_ESTADO_LD         integer,
   ID_SECTOR_LD         integer,
   ID_SUBSECTOR_LD      integer,
   ID_PERFIL_LD         integer,
   ID_ESTADO_BLOQUEO_LD integer,
   DE_COMENTARIO        varchar(4000),
   FE_DESBLOQUEO        DATE,
   FE_INVITACION        DATE,
   FE_RESPUESTA         DATE,
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_PRO_PROFESIONAL is
'Almac�n de datos de los ';

comment on column SICOES_TR_PRO_PROFESIONAL.ID_PRO_PROFESIONAL is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PRO_PROFESIONAL.ID_PROPUESTA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PRO_PROFESIONAL.ID_SUPERVISORA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PRO_PROFESIONAL.ID_ESTADO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PRO_PROFESIONAL.ID_SECTOR_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PRO_PROFESIONAL.ID_SUBSECTOR_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PRO_PROFESIONAL.ID_PERFIL_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PRO_PROFESIONAL.ID_ESTADO_BLOQUEO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PRO_PROFESIONAL.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_PRO_PROFESIONAL.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_PRO_PROFESIONAL.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_PRO_PROFESIONAL.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_PRO_PROFESIONAL.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_PRO_PROFESIONAL.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_PRO_PROFESIONAL
   add constraint PK_SICOES_TR_PRO_PROFESIONAL primary key (ID_PRO_PROFESIONAL);

/*==============================================================*/
/* Table: SICOES_TR_PRO_TECNICA                                 */
/*==============================================================*/
create table SICOES_TR_PRO_TECNICA 
(
   ID_PRO_TECNICA       integer              not null,
   ID_CONSORCIO_LD      integer,
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_PRO_TECNICA is
'Almac�n de datos de los ';

comment on column SICOES_TR_PRO_TECNICA.ID_PRO_TECNICA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PRO_TECNICA.ID_CONSORCIO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_PRO_TECNICA.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_PRO_TECNICA.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_PRO_TECNICA.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_PRO_TECNICA.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_PRO_TECNICA.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_PRO_TECNICA.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_PRO_TECNICA
   add constraint PK_SICOES_TR_PRO_TECNICA primary key (ID_PRO_TECNICA);

/*==============================================================*/
/* Table: SICOES_TR_REPRESENTANTE                               */
/*==============================================================*/
create table SICOES_TR_REPRESENTANTE 
(
   ID_REPRESENTANTE     integer              not null,
   ID_TIPO_DOCUMENTO_LD integer,
   NU_DOCUMENTO         varchar(20),
   NO_REPRESENTANTE     varchar(200),
   AP_PATERNO           varchar(4000),
   AP_MATERNO           varchar(10),
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_REPRESENTANTE is
'Almac�n de datos de los representantes legales de los usuarios del sistema';

comment on column SICOES_TR_REPRESENTANTE.ID_REPRESENTANTE is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_REPRESENTANTE.ID_TIPO_DOCUMENTO_LD is
'Corresponde al identificador del TIPO_DOCUMENTO de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_REPRESENTANTE.NU_DOCUMENTO is
'Corresponde al nro del documento';

comment on column SICOES_TR_REPRESENTANTE.AP_PATERNO is
'Corresponde al apellido paterno del representante';

comment on column SICOES_TR_REPRESENTANTE.AP_MATERNO is
'Corresponde al apellido materno del representante';

comment on column SICOES_TR_REPRESENTANTE.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_REPRESENTANTE.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_REPRESENTANTE.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_REPRESENTANTE.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_REPRESENTANTE.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_REPRESENTANTE.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_REPRESENTANTE
   add constraint PK_SICOES_TR_REPRESENTANTE primary key (ID_REPRESENTANTE);

/*==============================================================*/
/* Table: SICOES_TR_SOLICITUD                                   */
/*==============================================================*/
create table SICOES_TR_SOLICITUD 
(
   ID_SOLICITUD         integer              not null,
   NU_EXPEDIENTE        varchar(50),
   ID_REPRESENTANTE     integer,
   ID_PERSONA           integer,
   ID_USUARIO           integer,
   ID_RESUL_ADMIN       integer,
   ID_RESUL_TECNICO     integer,
   ID_ESTADO_LD         integer,
   ID_TIPO_SOLICITUD_LD integer,
   ID_ESTADO_REVISION_LD integer,
   ID_SOLICITUD_PADRE   integer,
   ID_ESTADO_EVAL_TECNICA_LD integer,
   ID_ESTADO_EVAL_ADMIN_LD integer,
   CO_UUID              varchar(50),
   CO_UUID_PADRE        varchar(50),
   CO_CONSENTIMIENTO    varchar(50),
   NU_DIA_PLAZO_RESP    integer,
   FE_PLAZO_RESP        DATE,
   NU_DIA_PLAZO_ASIG    integer,
   FE_PLAZO_ASIG        DATE,
   NU_DIA_PLAZO_SUB     integer,
   FE_PLAZO_SUB         DATE,
   NU_DIA_PLAZO_TECN    integer,
   FE_PLAZO_TECN        DATE,
   FE_REGISTRO          DATE,
   FE_PRESENTACION      DATE,
   CO_TIPO_PN           varchar(4000),
   DE_TIPO_PN           varchar(4000),
   DE_OBS_TECNICA       varchar(4000),
   DE_OBS_ADMIN         varchar(4000),
   DE_NO_CALIFICA       varchar(4000),
   FE_ARCHIVAMIENTO     DATE,
   FL_ACTIVO            integer,
   FL_ARCHIVAMIENTO     integer,
   FL_RESPUESTA         integer,
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_SOLICITUD is
'Almac�n de datos de las solicitudes del sistema';

comment on column SICOES_TR_SOLICITUD.ID_SOLICITUD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_SOLICITUD.NU_EXPEDIENTE is
'Corresponde al nro de expediente';

comment on column SICOES_TR_SOLICITUD.ID_REPRESENTANTE is
'Corresponde al identificador de la tabla REPRESENTANTE';

comment on column SICOES_TR_SOLICITUD.ID_PERSONA is
'Corresponde al identificador de la tabla PERSONA';

comment on column SICOES_TR_SOLICITUD.ID_USUARIO is
'Corresponde al identificador de la tabla USUARIO';

comment on column SICOES_TR_SOLICITUD.ID_RESUL_ADMIN is
'Corresponde al identificador del RESUL_ADMIN de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_SOLICITUD.ID_RESUL_TECNICO is
'Corresponde al identificador del RESUL_TECNICO de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_SOLICITUD.ID_ESTADO_LD is
'Corresponde al identificador del ESTADO de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_SOLICITUD.ID_TIPO_SOLICITUD_LD is
'Corresponde al identificador del TIPO_SOLICITUD de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_SOLICITUD.ID_ESTADO_REVISION_LD is
'Corresponde al identificador del ESTADO_REVISION de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_SOLICITUD.ID_SOLICITUD_PADRE is
'Corresponde al identificador PADRE de la tabla SOLICITUD';

comment on column SICOES_TR_SOLICITUD.ID_ESTADO_EVAL_TECNICA_LD is
'Corresponde al identificador del ESTADO_EVAL_TECNICA de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_SOLICITUD.ID_ESTADO_EVAL_ADMIN_LD is
'Corresponde al identificador del ESTADO_EVAL_ADMIN de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_SOLICITUD.CO_CONSENTIMIENTO is
'Corresponde al consentimiento';

comment on column SICOES_TR_SOLICITUD.NU_DIA_PLAZO_RESP is
'Corresponde al n�mero de d�as m�ximo de plazo para responder';

comment on column SICOES_TR_SOLICITUD.FE_PLAZO_RESP is
'Corresponde a la fecha m�xima de plazo para responder';

comment on column SICOES_TR_SOLICITUD.NU_DIA_PLAZO_ASIG is
'Corresponde al n�mero de d�as m�ximo de plazo para asignar';

comment on column SICOES_TR_SOLICITUD.FE_PLAZO_ASIG is
'Corresponde a la fecha m�xima de plazo para asignar';

comment on column SICOES_TR_SOLICITUD.NU_DIA_PLAZO_SUB is
'Corresponde al n�mero de d�as m�ximo de plazo para subsanar';

comment on column SICOES_TR_SOLICITUD.FE_PLAZO_SUB is
'Corresponde a la fecha m�xima de plazo para subsanar';

comment on column SICOES_TR_SOLICITUD.NU_DIA_PLAZO_TECN is
'Corresponde al n�mero de d�as m�ximo de plazo para subsanar';

comment on column SICOES_TR_SOLICITUD.FE_PLAZO_TECN is
'Corresponde a la fecha m�xima de plazo para subsanar';

comment on column SICOES_TR_SOLICITUD.FE_REGISTRO is
'Corresponde a la fecha de registro';

comment on column SICOES_TR_SOLICITUD.FE_PRESENTACION is
'Corresponde a la fecha de presentaci�n';

comment on column SICOES_TR_SOLICITUD.DE_TIPO_PN is
'Corresponde a la descripci�n de la observaci�n t�cnica';

comment on column SICOES_TR_SOLICITUD.DE_OBS_TECNICA is
'Corresponde a la descripci�n de la observaci�n t�cnica';

comment on column SICOES_TR_SOLICITUD.DE_OBS_ADMIN is
'Corresponde a la descripci�n de la observaci�n administrativa';

comment on column SICOES_TR_SOLICITUD.DE_NO_CALIFICA is
'Corresponde a la descripci�n no califica';

comment on column SICOES_TR_SOLICITUD.FE_ARCHIVAMIENTO is
'Corresponde a la fecha de registro';

comment on column SICOES_TR_SOLICITUD.FL_ACTIVO is
'Corresponde al flag activo';

comment on column SICOES_TR_SOLICITUD.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_SOLICITUD.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_SOLICITUD.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_SOLICITUD.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_SOLICITUD.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_SOLICITUD.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_SOLICITUD
   add constraint PK_SICOES_TR_SOLICITUD primary key (ID_SOLICITUD);

/*==============================================================*/
/* Table: SICOES_TR_SOL_NOTIFICACION                            */
/*==============================================================*/
create table SICOES_TR_SOL_NOTIFICACION 
(
   ID_SOL_NOTIFICACION  integer              not null,
   ID_SOLICITUD         integer,
   ID_TIPO_LD           integer,
   FE_NOTIFICACION      DATE,
   FE_REGISTRO          DATE,
   DE_OBSERVACION       varchar(4000),
   ID_ESTADO_LD         integer,
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_SOL_NOTIFICACION is
'Almac�n de datos de las notificaciones de solicitudes del sistema';

comment on column SICOES_TR_SOL_NOTIFICACION.ID_SOL_NOTIFICACION is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_SOL_NOTIFICACION.ID_SOLICITUD is
'Corresponde al identificador de la tabla SOLICITUD';

comment on column SICOES_TR_SOL_NOTIFICACION.ID_TIPO_LD is
'Corresponde al identificador del TIPO de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_SOL_NOTIFICACION.FE_NOTIFICACION is
'Corresponde a la fecha de la notificaci�n';

comment on column SICOES_TR_SOL_NOTIFICACION.FE_REGISTRO is
'Corresponde a la fecha del registro';

comment on column SICOES_TR_SOL_NOTIFICACION.DE_OBSERVACION is
'Corresponde a la descripci�n de la observaci�n';

comment on column SICOES_TR_SOL_NOTIFICACION.ID_ESTADO_LD is
'Corresponde al identificador del ESTADO de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_SOL_NOTIFICACION.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_SOL_NOTIFICACION.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_SOL_NOTIFICACION.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_SOL_NOTIFICACION.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_SOL_NOTIFICACION.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_SOL_NOTIFICACION.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_SOL_NOTIFICACION
   add constraint PK_SICOES_TR_SOL_NOTIFICACION primary key (ID_SOL_NOTIFICACION);

/*==============================================================*/
/* Table: SICOES_TR_SUPER_MOVIMIENTO                            */
/*==============================================================*/
create table SICOES_TR_SUPER_MOVIMIENTO 
(
   ID_SUPER_MOVIMIENTO  integer              not null,
   ID_SECTOR_LD         integer,
   ID_SUBSECTOR_LD      integer,
   ID_SUPERVISORA       integer,
   ID_ESTADO_LD         integer,
   ID_MOTIVO_LD         integer,
   ID_TIPO_MOTIVO_LD    integer,
   ID_ACCION_LD         integer,
   ID_PRO_PROFESIONAL   integer,
   DE_MOTIVO            varchar(4000),
   FE_REGISTRO          date,
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_SUPER_MOVIMIENTO is
'Almac�n de datos de los representantes legales de los usuarios del sistema';

comment on column SICOES_TR_SUPER_MOVIMIENTO.ID_SUPER_MOVIMIENTO is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_SUPER_MOVIMIENTO.ID_SECTOR_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_SUPER_MOVIMIENTO.ID_SUBSECTOR_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_SUPER_MOVIMIENTO.ID_SUPERVISORA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_SUPER_MOVIMIENTO.ID_ESTADO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_SUPER_MOVIMIENTO.ID_MOTIVO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_SUPER_MOVIMIENTO.ID_TIPO_MOTIVO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_SUPER_MOVIMIENTO.ID_ACCION_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_SUPER_MOVIMIENTO.ID_PRO_PROFESIONAL is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_SUPER_MOVIMIENTO.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_SUPER_MOVIMIENTO.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_SUPER_MOVIMIENTO.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_SUPER_MOVIMIENTO.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_SUPER_MOVIMIENTO.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_SUPER_MOVIMIENTO.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_SUPER_MOVIMIENTO
   add constraint PK_SICOES_TR_SUPER_MOVIMIENTO primary key (ID_SUPER_MOVIMIENTO);

/*==============================================================*/
/* Table: SICOES_TR_SUSPEN_CANCELACION                          */
/*==============================================================*/
create table SICOES_TR_SUSPEN_CANCELACION 
(
   ID_SUSPEN_CANCELACION integer              not null,
   ID_SUPERVISORA       integer,
   ID_TIPO_LD           integer,
   FE_INICIO            DATE                 not null,
   FE_FIN               DATE,
   ID_ESTADO_LD         integer,
   ID_CAUSAL_LD         integer,
   DE_SUSTENTO          varchar(4000),
   FE_SUSPEN_CANCELACION DATE,
   FE_ACTIVACION        DATE,
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_SUSPEN_CANCELACION is
'Almac�n de datos de las notificaciones de solicitudes del sistema';

comment on column SICOES_TR_SUSPEN_CANCELACION.ID_SUSPEN_CANCELACION is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_SUSPEN_CANCELACION.ID_SUPERVISORA is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_SUSPEN_CANCELACION.ID_TIPO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_SUSPEN_CANCELACION.FE_INICIO is
'Corresponde a la fecha de Inicio';

comment on column SICOES_TR_SUSPEN_CANCELACION.FE_FIN is
'Corresponde a la fecha de fin';

comment on column SICOES_TR_SUSPEN_CANCELACION.ID_ESTADO_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_SUSPEN_CANCELACION.ID_CAUSAL_LD is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_SUSPEN_CANCELACION.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_SUSPEN_CANCELACION.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_SUSPEN_CANCELACION.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_SUSPEN_CANCELACION.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_SUSPEN_CANCELACION.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_SUSPEN_CANCELACION.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_SUSPEN_CANCELACION
   add constraint PK_SICOES_TR_SUSPEN_CANCELACIO primary key (ID_SUSPEN_CANCELACION);

/*==============================================================*/
/* Table: SICOES_TR_TOKEN                                       */
/*==============================================================*/
create table SICOES_TR_TOKEN 
(
   ID_TOKEN             integer              not null,
   ID_TIPO_LD           integer,
   ID_ESTADO_LD         integer,
   ID_USUARIO           integer,
   CO_TOKEN             varchar(50),
   US_CREACION          varchar(50)          not null,
   FE_CREACION          date                 not null,
   IP_CREACION          varchar(50)          not null,
   US_ACTUALIZACION     varchar(50),
   FE_ACTUALIZACION     date,
   IP_ACTUALIZACION     varchar(50)
);

comment on table SICOES_TR_TOKEN is
'Almac�n de datos de los token del sistema';

comment on column SICOES_TR_TOKEN.ID_TOKEN is
'Corresponde al identificador de la tabla';

comment on column SICOES_TR_TOKEN.ID_TIPO_LD is
'Corresponde al identificador del TIPO de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_TOKEN.ID_ESTADO_LD is
'Corresponde al identificador del ESTADO de la tabla LISTADO_DETALLE';

comment on column SICOES_TR_TOKEN.ID_USUARIO is
'Corresponde al identificador de la tabla USUARIO';

comment on column SICOES_TR_TOKEN.CO_TOKEN is
'Corresponde al c�digo del token';

comment on column SICOES_TR_TOKEN.US_CREACION is
'Corresponde al usuario de creaci�n';

comment on column SICOES_TR_TOKEN.FE_CREACION is
'Corresponde a la fecha de creaci�n';

comment on column SICOES_TR_TOKEN.IP_CREACION is
'Corresponde a la ip de creaci�n';

comment on column SICOES_TR_TOKEN.US_ACTUALIZACION is
'Corresponde al usuario de actualizaci�n';

comment on column SICOES_TR_TOKEN.FE_ACTUALIZACION is
'Corresponde a la fecha de actualizaci�n';

comment on column SICOES_TR_TOKEN.IP_ACTUALIZACION is
'Corresponde a la ip de actualizaci�n';

alter table SICOES_TR_TOKEN
   add constraint PK_SICOES_TR_TOKEN primary key (ID_TOKEN);

alter table SICOES_TM_CONF_BANDEJA
   add constraint SICOES_ID_ACTIVI_LD_LS_DT_FK foreign key (ID_ACTIVIDAD_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TM_CONF_BANDEJA
   add constraint SICOES_ID_PERFIL_LD_LS_DT_FK foreign key (ID_PERFIL_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TM_CONF_BANDEJA
   add constraint SICOES_ID_SECTOR_LIS_DET_FK foreign key (ID_SECTOR_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TM_CONF_BANDEJA
   add constraint SICOES_ID_SUBCATE_LD_DT_FK foreign key (ID_SUBCATEGORIA_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TM_CONF_BANDEJA
   add constraint SICOES_ID_SUBSECTOR_LS_DT_FK foreign key (ID_SUBSECTOR_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TM_CONF_BANDEJA
   add constraint SICOES_ID_TIPO_CONF_BAJE_FK foreign key (ID_TIPO_CONF)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TM_CONF_BANDEJA
   add constraint SICOES_ID_UNIDAD_LD_LS_DT_FK foreign key (ID_UNIDAD_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TM_CONF_BANDEJA
   add constraint SICOES_ID_USU_LS_DE_FK foreign key (ID_USUARIO)
      references SICOES_TM_USUARIO (ID_USUARIO);

alter table SICOES_TM_LISTADO_DETALLE
   add constraint SICOES_ID_LIS_LIS_DET_FK foreign key (ID_LISTADO)
      references SICOES_TM_LISTADO (ID_LISTADO);

alter table SICOES_TM_LISTADO_DETALLE
   add constraint SICOES_ID_LIS_PDRE_LIS_DET_FK foreign key (ID_LISTADO_PADRE)
      references SICOES_TM_LISTADO (ID_LISTADO);

alter table SICOES_TM_LISTADO_DETALLE
   add constraint SICOES_ID_SUP_LD_LIS_DET_FK foreign key (ID_SUPERIOR_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TM_OPCION
   add constraint SICOES_ID_EST_LD_OPC_FK foreign key (ID_ESTADO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TM_OPCION
   add constraint SICOES_ID_PDRE_OPC_FK foreign key (ID_PADRE)
      references SICOES_TM_OPCION (ID_OPCION);

alter table SICOES_TM_ROL
   add constraint SICOES_ID_EST_LD_ROL_FK foreign key (ID_ESTADO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TM_ROL_OPCION
   add constraint SICOES_ID_OPC_ROL_OPC_FK foreign key (ID_OPCION)
      references SICOES_TM_OPCION (ID_OPCION);

alter table SICOES_TM_ROL_OPCION
   add constraint SICOES_ID_ROL_ROL_OPC_FK foreign key (ID_ROL)
      references SICOES_TM_ROL (ID_ROL);

alter table SICOES_TM_SUPERVISORA
   add constraint SICOES_ID_ESTADO_LD_LIS_DET_FK foreign key (ID_ESTADO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TM_SUPERVISORA
   add constraint SICOES_ID_PAIS_LD_SUP_FK foreign key (ID_PAIS_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TM_SUPERVISORA
   add constraint SICOES_ID_TIPO_DOC_LD_SUP_FK foreign key (ID_TIPO_DOCUMENTO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TM_SUPERVISORA
   add constraint SICOES_ID_TIPPER_LD_LIS_DET_FK foreign key (ID_TIPO_PERSONA_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TM_SUPER_DICTAMEN
   add constraint SICOES_ID_SECTOR_DIC_FK foreign key (ID_SECTOR_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TM_SUPER_DICTAMEN
   add constraint SICOES_ID_SUPER_DICTMEN_FK foreign key (ID_SUPERVISORA)
      references SICOES_TM_SUPERVISORA (ID_SUPERVISORA);

alter table SICOES_TM_SUPER_PERFIL
   add constraint SICOES_ID_ESTADO_PEFIL_FK foreign key (ID_ESTADO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TM_SUPER_PERFIL
   add constraint SICOES_ID_SUP_SUP_PER_FK foreign key (ID_SUPERVISORA)
      references SICOES_TM_SUPERVISORA (ID_SUPERVISORA);

alter table SICOES_TM_SUPER_REPRESENTANTE
   add constraint SICOES_ID_SUPER_SUPER_FK foreign key (ID_SUPERVISORA)
      references SICOES_TM_SUPERVISORA (ID_SUPERVISORA);

alter table SICOES_TM_SUPER_REPRESENTANTE
   add constraint SICOES_ID_TIP_DOC_LIST_DETA_FK foreign key (ID_TIPO_DOCUMENTO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TM_USUARIO
   add constraint SICOES_ID_PAIS_LD_USUARIO_FK foreign key (ID_PAIS_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TM_USUARIO
   add constraint SICOES_ID_TIP_DOC_USUARIO_LD foreign key (ID_TIPO_DOCUMENTO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TM_USUARIO
   add constraint SICOES_ID_TIP_PER_LIST_DET_FK foreign key (ID_TIPO_PERSONA_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TM_USUARIO
   add constraint SICOES_ID_TIP_USU_LD_USU_FK foreign key (ID_TIPO_USUARIO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TM_USUARIO_ROL
   add constraint SICOES_ID_ROL_USU_ROL_FK foreign key (ID_ROL)
      references SICOES_TM_ROL (ID_ROL);

alter table SICOES_TM_USUARIO_ROL
   add constraint SICOES_ID_USU_USU_ROL_FK foreign key (ID_USUARIO)
      references SICOES_TM_USUARIO (ID_USUARIO);

alter table SICOES_TR_ARCHIVO
   add constraint SICOES_ID_ASIG_ARCHIVO_FK foreign key (ID_ASIGNACION)
      references SICOES_TR_ASIGNACION (ID_ASIGNACION);

alter table SICOES_TR_ARCHIVO
   add constraint SICOES_ID_DOC_ARCH_FK foreign key (ID_DOCUMENTO)
      references SICOES_TR_DOCUMENTO (ID_DOCUMENTO);

alter table SICOES_TR_ARCHIVO
   add constraint SICOES_ID_EST_ARCH_FK foreign key (ID_ESTUDIO)
      references SICOES_TR_ESTUDIO (ID_ESTUDIO);

alter table SICOES_TR_ARCHIVO
   add constraint SICOES_ID_EST_LD_ARCH_FK foreign key (ID_ESTADO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_ARCHIVO
   add constraint SICOES_ID_NOTI_ARCHIVO_FK foreign key (ID_NOTIFICACION)
      references SICOES_TR_NOTIFICACION (ID_NOTIFICACION);

alter table SICOES_TR_ARCHIVO
   add constraint SICOES_ID_NOT_SOL_ARCH_FK foreign key (ID_NOTIFICACION_SOLICITUD)
      references SICOES_TR_SOL_NOTIFICACION (ID_SOL_NOTIFICACION);

alter table SICOES_TR_ARCHIVO
   add constraint SICOES_ID_OTRO_REQ_ARCH_FK foreign key (ID_OTRO_REQUISITO)
      references SICOES_TR_OTRO_REQUISITO (ID_OTRO_REQUISITO);

alter table SICOES_TR_ARCHIVO
   add constraint SICOES_ID_PROPU_ARCHIVO_FK foreign key (ID_PROPUESTA)
      references SICOES_TR_PROPUESTA (ID_PROPUESTA);

alter table SICOES_TR_ARCHIVO
   add constraint SICOES_ID_PRO_ECO_ARCHIVO_FK foreign key (ID_PRO_ECONOMICA)
      references SICOES_TR_PRO_ECONOMICA (ID_PRO_ECONOMICA);

alter table SICOES_TR_ARCHIVO
   add constraint SICOES_ID_PRO_TEC_PRO_PRO_FK foreign key (ID_PRO_TECNICA)
      references SICOES_TR_PRO_TECNICA (ID_PRO_TECNICA);

alter table SICOES_TR_ARCHIVO
   add constraint SICOES_ID_SOL_ARCH_FK foreign key (ID_SOLICITUD)
      references SICOES_TR_SOLICITUD (ID_SOLICITUD);

alter table SICOES_TR_ARCHIVO
   add constraint SICOES_ID_TIPO_ARCH_LD_ARCH_FK foreign key (ID_TIPO_ARCHIVO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_ASIGNACION
   add constraint SICOES_ID_EVA_LD_ASIG_FK foreign key (ID_EVALUACION_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_ASIGNACION
   add constraint SICOES_ID_GRP_LD_ASIG_FK foreign key (ID_GRUPO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_ASIGNACION
   add constraint SICOES_ID_SOL_ASIG_FK foreign key (ID_SOLICITUD)
      references SICOES_TR_SOLICITUD (ID_SOLICITUD);

alter table SICOES_TR_ASIGNACION
   add constraint SICOES_ID_TIPO_LD_ASIG_FK foreign key (ID_TIPO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_ASIGNACION
   add constraint SICOES_ID_USU_ASIG_FK foreign key (ID_USUARIO)
      references SICOES_TM_USUARIO (ID_USUARIO);

alter table SICOES_TR_BITACORA
   add constraint SICOES_ID_USUARIO_BITACORA_FK foreign key (ID_USUARIO)
      references SICOES_TM_USUARIO (ID_USUARIO);

alter table SICOES_TR_DICTAMEN_EVAL
   add constraint SICOES_ID_SECTOR_DICTAM_FK foreign key (ID_SECTOR_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_DICTAMEN_EVAL
   add constraint SICOES_ID_SOLICITUD_DICTAM_FK foreign key (ID_SOLICITUD)
      references SICOES_TR_SOLICITUD (ID_SOLICITUD);

alter table SICOES_TR_DOCUMENTO
   add constraint SICOES_ID_CONF_LD_DOC_FK foreign key (ID_CONFORMIDAD_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_DOCUMENTO
   add constraint SICOES_ID_DOC_PDRE_DOC_FK foreign key (ID_DOCUMENTO_PADRE)
      references SICOES_TR_DOCUMENTO (ID_DOCUMENTO);

alter table SICOES_TR_DOCUMENTO
   add constraint SICOES_ID_EVA_LD_DOC_FK foreign key (ID_EVALUACION_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_DOCUMENTO
   add constraint SICOES_ID_PAIS_LD_DOC_FK foreign key (ID_PAIS_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_DOCUMENTO
   add constraint SICOES_ID_SOL_DOC_FK foreign key (ID_SOLICITUD)
      references SICOES_TR_SOLICITUD (ID_SOLICITUD);

alter table SICOES_TR_DOCUMENTO
   add constraint SICOES_ID_TIPO_CAM_LD_DOC_FK foreign key (ID_TIPO_CAMBIO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_DOCUMENTO
   add constraint SICOES_ID_TIPO_DOC_LD_DOC_FK foreign key (ID_TIPO_DOCUMENTO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_DOCUMENTO
   add constraint SICOES_ID_TIPO_LD_DOC_FK foreign key (ID_TIPO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_DOCUMENTO
   add constraint SICOES_ID_TIP_ID_TRI_LD_DOC_FK foreign key (ID_TIPO_ID_TRIBURARIO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_ESTUDIO
   add constraint SICOES_ID_EST_PDRE_EST_FK foreign key (ID_ESTUDIO_PADRE)
      references SICOES_TR_ESTUDIO (ID_ESTUDIO);

alter table SICOES_TR_ESTUDIO
   add constraint SICOES_ID_EVA_LD_EST_FK foreign key (ID_EVALUACION_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_ESTUDIO
   add constraint SICOES_ID_FUEN_LD_EST_FK foreign key (ID_FUENTE_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_ESTUDIO
   add constraint SICOES_ID_SOL_EST_FK foreign key (ID_SOLICITUD)
      references SICOES_TR_SOLICITUD (ID_SOLICITUD);

alter table SICOES_TR_ESTUDIO
   add constraint SICOES_ID_TIPO_EST_LD_EST_FK foreign key (ID_TIPO_ESTUDIO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_ESTUDIO
   add constraint SICOES_ID_TIPO_LD_EST_FK foreign key (ID_TIPO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_INTEGRANTE
   add constraint SICOES_ID_PRO_TECN_INTE_FK foreign key (ID_PRO_TECNICA)
      references SICOES_TR_PRO_TECNICA (ID_PRO_TECNICA);

alter table SICOES_TR_INTEGRANTE
   add constraint SICOES_ID_SUPER_INTE_FK foreign key (ID_SUPERVISORA)
      references SICOES_TM_SUPERVISORA (ID_SUPERVISORA);

alter table SICOES_TR_INTEGRANTE
   add constraint SICOES_ID_TIPO_INTE_FK foreign key (ID_TIPO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_ITEM_ESTADO
   add constraint SICOES_ID_ESTADO_LD_ESTADO_FK foreign key (ID_ESTADO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_ITEM_ESTADO
   add constraint SICOES_ID_EST_ANTE_LDT_FK foreign key (ID_ESTADO_ANTERIOR_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_ITEM_ESTADO
   add constraint SICOES_ID_PRO_ITEM_ESTADO_FK foreign key (ID_PROCESO_ITEM)
      references SICOES_TR_PROCESO_ITEM (ID_PROCESO_ITEM);

alter table SICOES_TR_ITEM_ESTADO
   add constraint SICOES_ID_USU_EST_FK foreign key (ID_USUARIO)
      references SICOES_TM_USUARIO (ID_USUARIO);

alter table SICOES_TR_NOTIFICACION
   add constraint SICOES_ID_EST_LD_NOT_FK foreign key (ID_ESTADO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_OTRO_REQUISITO
   add constraint SICOES_FINALIZADO_OTRO_RQ_FK foreign key (ID_FINALIZADO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_OTRO_REQUISITO
   add constraint SICOES_ID_ACT_LD_OTRO_REQ_FK foreign key (ID_ACTIVIDAD_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_OTRO_REQUISITO
   add constraint SICOES_ID_EVA_LD_OTRO_REQ_FK foreign key (ID_EVALUACION_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_OTRO_REQUISITO
   add constraint SICOES_ID_OT_RE_PDRE_OT_REQ_FK foreign key (ID_OTRO_REQUISITO_PADRE)
      references SICOES_TR_OTRO_REQUISITO (ID_OTRO_REQUISITO);

alter table SICOES_TR_OTRO_REQUISITO
   add constraint SICOES_ID_PERF_LD_OTRO_REQ_FK foreign key (ID_PERFIL_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_OTRO_REQUISITO
   add constraint SICOES_ID_RESUL_LIST_DET_FK foreign key (ID_RESULTADO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_OTRO_REQUISITO
   add constraint SICOES_ID_SEC_LD_OTRO_REQ_FK foreign key (ID_SECTOR_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_OTRO_REQUISITO
   add constraint SICOES_ID_SOL_OTRO_REQ_FK foreign key (ID_SOLICITUD)
      references SICOES_TR_SOLICITUD (ID_SOLICITUD);

alter table SICOES_TR_OTRO_REQUISITO
   add constraint SICOES_ID_SUBC_LD_OTRO_REQ_FK foreign key (ID_SUBCATEGORIA_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_OTRO_REQUISITO
   add constraint SICOES_ID_SUB_LD_OTRO_REQ_FK foreign key (ID_SUBSECTOR_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_OTRO_REQUISITO
   add constraint SICOES_ID_TIPO_LD_OTRO_REQ_FK foreign key (ID_TIPO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_OTRO_REQUISITO
   add constraint SICOES_ID_TIP_REQ_LD_OT_REQ_FK foreign key (ID_TIPO_REQUISITO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_OTRO_REQUISITO
   add constraint SICOES_ID_UNI_LD_REQ_FK foreign key (ID_UNIDAD_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_OTRO_REQUISITO
   add constraint SICOES_ID_USUARIO_OTRO_RQ_FK foreign key (ID_USUARIO_FIN)
      references SICOES_TM_USUARIO (ID_USUARIO);

alter table SICOES_TR_PERSONA
   add constraint SICOES_ID_PAIS_LD_PERS_FK foreign key (ID_PAIS_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PERSONA
   add constraint SICOES_ID_TIPO_DOC_LD_PERS_FK foreign key (ID_TIPO_DOCUMENTO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PERSONA
   add constraint SICOES_ID_TI_PER_LD_LS_DET_FK foreign key (ID_TIPO_PERSONA_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PROCESO
   add constraint SICOES_ID_ESTADO_LD_PROC_FK foreign key (ID_ESTADO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PROCESO
   add constraint SICOES_ID_SECTOR_PROCESO_FK foreign key (ID_SECTOR_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PROCESO
   add constraint SICOES_ID_SUB_SEC_LD_PROC_FK foreign key (ID_SUBSECTOR_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PROCESO
   add constraint SICOES_ID_USUARIO_PROCESO_FK foreign key (ID_USUARIO)
      references SICOES_TM_USUARIO (ID_USUARIO);

alter table SICOES_TR_PROCESO
   add constraint SICOES_ID_USU_CRE_PROCESO_FK foreign key (ID_USUARIO_CREADOR)
      references SICOES_TM_USUARIO (ID_USUARIO);

alter table SICOES_TR_PROCESO
   add constraint SICOES_TIPO_FACTU_LD_LST_LD_FK foreign key (ID_TIPO_FACTURACION_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PROCESO_ETAPA
   add constraint SICOES_ID_ETA_LD_PROC_ETA_LD foreign key (ID_ETAPA_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PROCESO_ETAPA
   add constraint SICOES_ID_PROC_LD_PROC_ETA_FK foreign key (ID_PROCESO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PROCESO_ETAPA
   add constraint SICOES_ID_PROC_PROC_ESTAPA_FK foreign key (ID_PROCESO)
      references SICOES_TR_PROCESO (ID_PROCESO);

alter table SICOES_TR_PROCESO_ITEM
   add constraint SICOES_ID_DIV_LD_PRO_ITEM_FK foreign key (ID_DIVISA_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PROCESO_ITEM
   add constraint SICOES_ID_EST_LD_PRO_ITEM_FK foreign key (ID_ESTADO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PROCESO_ITEM
   add constraint SICOES_ID_PROC_PROC_ITEM_FK foreign key (ID_PROCESO)
      references SICOES_TR_PROCESO (ID_PROCESO);

alter table SICOES_TR_PROCESO_ITEM_PERFIL
   add constraint SICOES_ID_ESTADO_PRO_ITEM_FK foreign key (ID_ESTADO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PROCESO_ITEM_PERFIL
   add constraint SICOES_ID_PERFIL_ITEM_PERF_FK foreign key (ID_PERFIL_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PROCESO_ITEM_PERFIL
   add constraint SICOES_ID_PROCESO_ITEM_PERF_FK foreign key (ID_PROCESO_ITEM)
      references SICOES_TR_PROCESO_ITEM (ID_PROCESO_ITEM);

alter table SICOES_TR_PROCESO_ITEM_PERFIL
   add constraint FK_SICOES_T_SICOES_ID_SICOES_T foreign key (ID_SECTOR_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PROCESO_ITEM_PERFIL
   add constraint SICOES_ID_SUBSECT_ITEM_PERF_FK foreign key (ID_SUBSECTOR_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PROCESO_MIEMBRO
   add constraint SICOES_ID_CAR_LD_PROC_MIEM_FK foreign key (ID_CARGO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PROCESO_MIEMBRO
   add constraint SICOES_ID_ESTADO_PRO_MIEM_FK foreign key (ID_ESTADO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PROCESO_MIEMBRO
   add constraint SICOES_ID_PROC_PROC_MIEM_FK foreign key (ID_PROCESO)
      references SICOES_TR_PROCESO (ID_PROCESO);

alter table SICOES_TR_PROPUESTA
   add constraint SICOES_ID_EST_LD_PROP_FK foreign key (ID_ESTADO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PROPUESTA
   add constraint SICOES_ID_GANADOR_PROPU_FK foreign key (ID_GANADOR_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PROPUESTA
   add constraint SICOES_ID_PROC_ITEM_PROP_FK foreign key (ID_PROCESO_ITEM)
      references SICOES_TR_PROCESO_ITEM (ID_PROCESO_ITEM);

alter table SICOES_TR_PROPUESTA
   add constraint SICOES_ID_PRO_ECO_PRO_FK foreign key (ID_PRO_ECONOMICA)
      references SICOES_TR_PRO_ECONOMICA (ID_PRO_ECONOMICA);

alter table SICOES_TR_PROPUESTA
   add constraint SICOES_ID_PRO_TECNICA_PRO_FK foreign key (ID_PRO_TECNICA)
      references SICOES_TR_PRO_TECNICA (ID_PRO_TECNICA);

alter table SICOES_TR_PROPUESTA
   add constraint SICOES_ID_SUPE_PROP_FK foreign key (ID_SUPERVISORA)
      references SICOES_TM_SUPERVISORA (ID_SUPERVISORA);

alter table SICOES_TR_PRO_PROFESIONAL
   add constraint SICOES_ID_ESTADO_BLOQUEO_FK foreign key (ID_ESTADO_BLOQUEO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PRO_PROFESIONAL
   add constraint SICOES_ID_EST_LD_PRO_PRO_FK foreign key (ID_ESTADO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PRO_PROFESIONAL
   add constraint SICOES_ID_PER_LD_PRO_PRO_FK foreign key (ID_PERFIL_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PRO_PROFESIONAL
   add constraint SICOES_ID_PROP_PRO_PRO_FK foreign key (ID_PROPUESTA)
      references SICOES_TR_PROPUESTA (ID_PROPUESTA);

alter table SICOES_TR_PRO_PROFESIONAL
   add constraint SICOES_ID_SEC_LD_PRO_PRO_FK foreign key (ID_SECTOR_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PRO_PROFESIONAL
   add constraint SICOES_ID_SSECT_LD_PRO_PRO_FK foreign key (ID_SUBSECTOR_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_PRO_PROFESIONAL
   add constraint SICOES_ID_SUPE_PRO_PRO_FK foreign key (ID_SUPERVISORA)
      references SICOES_TM_SUPERVISORA (ID_SUPERVISORA);

alter table SICOES_TR_PRO_TECNICA
   add constraint SICOES_ID_CONSOR_LD_LIST_DT_FK foreign key (ID_CONSORCIO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_REPRESENTANTE
   add constraint SICOES_ID_TIPO_DOC_LD_REPR_FK foreign key (ID_TIPO_DOCUMENTO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_SOLICITUD
   add constraint SICOES_ID_EST_EVA_AD_LD_SOL_FK foreign key (ID_ESTADO_EVAL_ADMIN_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_SOLICITUD
   add constraint SICOES_ID_EST_EVA_TE_LD_SOL_FK foreign key (ID_ESTADO_EVAL_TECNICA_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_SOLICITUD
   add constraint SICOES_ID_EST_LD_SOL_FK foreign key (ID_ESTADO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_SOLICITUD
   add constraint SICOES_ID_EST_REV_LD_SOL_FK foreign key (ID_ESTADO_REVISION_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_SOLICITUD
   add constraint SICOES_ID_PER_SOL_FK foreign key (ID_PERSONA)
      references SICOES_TR_PERSONA (ID_PERSONA);

alter table SICOES_TR_SOLICITUD
   add constraint SICOES_ID_REPR_SOL_FK foreign key (ID_REPRESENTANTE)
      references SICOES_TR_REPRESENTANTE (ID_REPRESENTANTE);

alter table SICOES_TR_SOLICITUD
   add constraint SICOES_ID_RES_ADM_SOL_FK foreign key (ID_RESUL_ADMIN)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_SOLICITUD
   add constraint SICOES_ID_RES_TEC_SOL_FK foreign key (ID_RESUL_TECNICO)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_SOLICITUD
   add constraint SICOES_ID_SOL_PDRE_SOL_FK foreign key (ID_SOLICITUD_PADRE)
      references SICOES_TR_SOLICITUD (ID_SOLICITUD);

alter table SICOES_TR_SOLICITUD
   add constraint SICOES_ID_TIPO_SOL_LD_SOL_FK foreign key (ID_TIPO_SOLICITUD_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_SOLICITUD
   add constraint SICOES_ID_USU_SOL_FK foreign key (ID_USUARIO)
      references SICOES_TM_USUARIO (ID_USUARIO);

alter table SICOES_TR_SOL_NOTIFICACION
   add constraint SICOES_ID_SOL_SOL_NOT_FK foreign key (ID_SOLICITUD)
      references SICOES_TR_SOLICITUD (ID_SOLICITUD);

alter table SICOES_TR_SOL_NOTIFICACION
   add constraint SICOES_ID_TIPO_LD_SOL_NOT_FK foreign key (ID_TIPO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_SUPER_MOVIMIENTO
   add constraint SICOES_ID_ACCION_MOVI_FK foreign key (ID_ACCION_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_SUPER_MOVIMIENTO
   add constraint SICOES_ID_EST_MOVI_FK foreign key (ID_ESTADO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_SUPER_MOVIMIENTO
   add constraint SICOES_ID_MOTIVO_MOVI_FK foreign key (ID_MOTIVO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_SUPER_MOVIMIENTO
   add constraint SICOES_ID_PROFE_MOVI_FK foreign key (ID_PRO_PROFESIONAL)
      references SICOES_TR_PRO_PROFESIONAL (ID_PRO_PROFESIONAL);

alter table SICOES_TR_SUPER_MOVIMIENTO
   add constraint SICOES_ID_SECTOR_MOVI_FK foreign key (ID_SECTOR_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_SUPER_MOVIMIENTO
   add constraint SICOES_ID_SUBSECTOR_MOVI_FK foreign key (ID_SUBSECTOR_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_SUPER_MOVIMIENTO
   add constraint SICOES_ID_SUPERV_MOVI_FK foreign key (ID_SUPERVISORA)
      references SICOES_TM_SUPERVISORA (ID_SUPERVISORA);

alter table SICOES_TR_SUPER_MOVIMIENTO
   add constraint SICOES_ID_TIPO_MOTIVO_MOVI_FK foreign key (ID_TIPO_MOTIVO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_SUSPEN_CANCELACION
   add constraint SICOES_ID_CAU_LD_SUSP_CAN_FK foreign key (ID_CAUSAL_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_SUSPEN_CANCELACION
   add constraint SICOES_ID_ESTADO_SUSP_CAN_FK foreign key (ID_ESTADO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_SUSPEN_CANCELACION
   add constraint SICOES_ID_SUPERV_SUPERV_FK foreign key (ID_SUPERVISORA)
      references SICOES_TM_SUPERVISORA (ID_SUPERVISORA);

alter table SICOES_TR_SUSPEN_CANCELACION
   add constraint SICOES_ID_TIPO_LD_LS_DT_FK foreign key (ID_TIPO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_TOKEN
   add constraint SICOES_ID_EST_LD_TOKEN_FK foreign key (ID_ESTADO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_TOKEN
   add constraint SICOES_ID_TIPO_LD_TOKEN_FK foreign key (ID_TIPO_LD)
      references SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE);

alter table SICOES_TR_TOKEN
   add constraint SICOES_ID_USU_TOKEN_FK foreign key (ID_USUARIO)
      references SICOES_TM_USUARIO (ID_USUARIO);

