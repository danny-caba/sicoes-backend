--------------------------------------------------------
-- Archivo creado  - sábado-enero-28-2023   
--------------------------------------------------------
DROP SEQUENCE "SICOES"."SICOES_SEQ_ARCHIVO";
DROP SEQUENCE "SICOES"."SICOES_SEQ_ASIGNACION";
DROP SEQUENCE "SICOES"."SICOES_SEQ_DOCUMENTO";
DROP SEQUENCE "SICOES"."SICOES_SEQ_ESTUDIO";
DROP SEQUENCE "SICOES"."SICOES_SEQ_LISTADO";
DROP SEQUENCE "SICOES"."SICOES_SEQ_LISTADO_DETALLE";
DROP SEQUENCE "SICOES"."SICOES_SEQ_NOTIFICACION";
DROP SEQUENCE "SICOES"."SICOES_SEQ_OTRO_REQUISITO";
DROP SEQUENCE "SICOES"."SICOES_SEQ_PERSONA";
DROP SEQUENCE "SICOES"."SICOES_SEQ_REPRESENTANTE";
DROP SEQUENCE "SICOES"."SICOES_SEQ_REQUISITO";
DROP SEQUENCE "SICOES"."SICOES_SEQ_SOLICITUD";
DROP SEQUENCE "SICOES"."SICOES_SEQ_TOKEN";
DROP SEQUENCE "SICOES"."SICOES_SEQ_USUARIO";
DROP SEQUENCE "SICOES"."SICOES_SEQ_USUARIO_ROL";
DROP TABLE "SICOES"."OAUTH_ACCESS_TOKEN" cascade constraints;
DROP TABLE "SICOES"."OAUTH_CLIENT_DETAILS" cascade constraints;
DROP TABLE "SICOES"."OAUTH_CLIENT_TOKEN" cascade constraints;
DROP TABLE "SICOES"."OAUTH_CODE" cascade constraints;
DROP TABLE "SICOES"."OAUTH_REFRESH_TOKEN" cascade constraints;
DROP TABLE "SICOES"."SICOES_TM_LISTADO" cascade constraints;
DROP TABLE "SICOES"."SICOES_TM_LISTADO_DETALLE" cascade constraints;
DROP TABLE "SICOES"."SICOES_TM_OPCION" cascade constraints;
DROP TABLE "SICOES"."SICOES_TM_ROL" cascade constraints;
DROP TABLE "SICOES"."SICOES_TM_ROL_OPCION" cascade constraints;
DROP TABLE "SICOES"."SICOES_TM_UBIGEO" cascade constraints;
DROP TABLE "SICOES"."SICOES_TM_USUARIO" cascade constraints;
DROP TABLE "SICOES"."SICOES_TM_USUARIO_ROL" cascade constraints;
DROP TABLE "SICOES"."SICOES_TR_ARCHIVO" cascade constraints;
DROP TABLE "SICOES"."SICOES_TR_ASIGNACION" cascade constraints;
DROP TABLE "SICOES"."SICOES_TR_DOCUMENTO" cascade constraints;
DROP TABLE "SICOES"."SICOES_TR_ESTUDIO" cascade constraints;
DROP TABLE "SICOES"."SICOES_TR_NOTIFICACION" cascade constraints;
DROP TABLE "SICOES"."SICOES_TR_OTRO_REQUISITO" cascade constraints;
DROP TABLE "SICOES"."SICOES_TR_PERSONA" cascade constraints;
DROP TABLE "SICOES"."SICOES_TR_REPRESENTANTE" cascade constraints;
DROP TABLE "SICOES"."SICOES_TR_SOLICITUD" cascade constraints;
DROP TABLE "SICOES"."SICOES_TR_TOKEN" cascade constraints;
--------------------------------------------------------
--  DDL for Sequence SICOES_SEQ_ARCHIVO
--------------------------------------------------------

   CREATE SEQUENCE  "SICOES"."SICOES_SEQ_ARCHIVO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 41 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence SICOES_SEQ_ASIGNACION
--------------------------------------------------------

   CREATE SEQUENCE  "SICOES"."SICOES_SEQ_ASIGNACION"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence SICOES_SEQ_DOCUMENTO
--------------------------------------------------------

   CREATE SEQUENCE  "SICOES"."SICOES_SEQ_DOCUMENTO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence SICOES_SEQ_ESTUDIO
--------------------------------------------------------

   CREATE SEQUENCE  "SICOES"."SICOES_SEQ_ESTUDIO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence SICOES_SEQ_LISTADO
--------------------------------------------------------

   CREATE SEQUENCE  "SICOES"."SICOES_SEQ_LISTADO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence SICOES_SEQ_LISTADO_DETALLE
--------------------------------------------------------

   CREATE SEQUENCE  "SICOES"."SICOES_SEQ_LISTADO_DETALLE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence SICOES_SEQ_NOTIFICACION
--------------------------------------------------------

   CREATE SEQUENCE  "SICOES"."SICOES_SEQ_NOTIFICACION"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 41 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence SICOES_SEQ_OTRO_REQUISITO
--------------------------------------------------------

   CREATE SEQUENCE  "SICOES"."SICOES_SEQ_OTRO_REQUISITO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence SICOES_SEQ_PERSONA
--------------------------------------------------------

   CREATE SEQUENCE  "SICOES"."SICOES_SEQ_PERSONA"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence SICOES_SEQ_REPRESENTANTE
--------------------------------------------------------

   CREATE SEQUENCE  "SICOES"."SICOES_SEQ_REPRESENTANTE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence SICOES_SEQ_REQUISITO
--------------------------------------------------------

   CREATE SEQUENCE  "SICOES"."SICOES_SEQ_REQUISITO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence SICOES_SEQ_SOLICITUD
--------------------------------------------------------

   CREATE SEQUENCE  "SICOES"."SICOES_SEQ_SOLICITUD"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence SICOES_SEQ_TOKEN
--------------------------------------------------------

   CREATE SEQUENCE  "SICOES"."SICOES_SEQ_TOKEN"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence SICOES_SEQ_USUARIO
--------------------------------------------------------

   CREATE SEQUENCE  "SICOES"."SICOES_SEQ_USUARIO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence SICOES_SEQ_USUARIO_ROL
--------------------------------------------------------

   CREATE SEQUENCE  "SICOES"."SICOES_SEQ_USUARIO_ROL"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Table OAUTH_ACCESS_TOKEN
--------------------------------------------------------

  CREATE TABLE "SICOES"."OAUTH_ACCESS_TOKEN" 
   (	"AUTHENTICATION_ID" VARCHAR2(255 BYTE), 
	"TOKEN_ID" VARCHAR2(255 BYTE), 
	"TOKEN" BLOB, 
	"USER_NAME" VARCHAR2(255 BYTE), 
	"AUTHENTICATION" BLOB, 
	"REFRESH_TOKEN" VARCHAR2(255 BYTE), 
	"CLIENT_ID" VARCHAR2(255 BYTE)
   ) ;

   COMMENT ON COLUMN "SICOES"."OAUTH_ACCESS_TOKEN"."AUTHENTICATION_ID" IS 'Corresponde al identificador de la tabla';
   COMMENT ON TABLE "SICOES"."OAUTH_ACCESS_TOKEN"  IS 'Almac¿n de datos para los listados del sistema';
--------------------------------------------------------
--  DDL for Table OAUTH_CLIENT_DETAILS
--------------------------------------------------------

  CREATE TABLE "SICOES"."OAUTH_CLIENT_DETAILS" 
   (	"CLIENT_ID" VARCHAR2(256 BYTE), 
	"RESOURCE_IDS" VARCHAR2(256 BYTE), 
	"CLIENT_SECRET" VARCHAR2(256 BYTE), 
	"SCOPE" VARCHAR2(256 BYTE), 
	"AUTHORIZED_GRANT_TYPES" VARCHAR2(256 BYTE), 
	"WEB_SERVER_REDIRECT_URI" VARCHAR2(256 BYTE), 
	"AUTHORITIES" VARCHAR2(256 BYTE), 
	"ACCESS_TOKEN_VALIDITY" NUMBER(*,0), 
	"REFRESH_TOKEN_VALIDITY" NUMBER(*,0), 
	"ADDITIONAL_INFORMATION" VARCHAR2(4000 BYTE), 
	"AUTOAPPROVE" VARCHAR2(256 BYTE)
   ) ;

   COMMENT ON COLUMN "SICOES"."OAUTH_CLIENT_DETAILS"."CLIENT_ID" IS 'Corresponde al identificador de la tabla';
   COMMENT ON TABLE "SICOES"."OAUTH_CLIENT_DETAILS"  IS 'Almac¿n de datos para los listados del sistema';
--------------------------------------------------------
--  DDL for Table OAUTH_CLIENT_TOKEN
--------------------------------------------------------

  CREATE TABLE "SICOES"."OAUTH_CLIENT_TOKEN" 
   (	"USER_NAME" VARCHAR2(255 BYTE), 
	"TOKEN" BLOB
   ) ;

   COMMENT ON TABLE "SICOES"."OAUTH_CLIENT_TOKEN"  IS 'Almac¿n de datos para los listados del sistema';
--------------------------------------------------------
--  DDL for Table OAUTH_CODE
--------------------------------------------------------

  CREATE TABLE "SICOES"."OAUTH_CODE" 
   (	"CODE" VARCHAR2(255 BYTE), 
	"AUTHENTICATION" BLOB
   ) ;

   COMMENT ON COLUMN "SICOES"."OAUTH_CODE"."CODE" IS 'Corresponde al identificador de la tabla';
   COMMENT ON TABLE "SICOES"."OAUTH_CODE"  IS 'Almac¿n de datos para los listados del sistema';
--------------------------------------------------------
--  DDL for Table OAUTH_REFRESH_TOKEN
--------------------------------------------------------

  CREATE TABLE "SICOES"."OAUTH_REFRESH_TOKEN" 
   (	"TOKEN_ID" VARCHAR2(255 BYTE), 
	"TOKEN" BLOB, 
	"AUTHENTICATION" BLOB
   ) ;

   COMMENT ON COLUMN "SICOES"."OAUTH_REFRESH_TOKEN"."TOKEN_ID" IS 'Corresponde al identificador de la tabla';
   COMMENT ON TABLE "SICOES"."OAUTH_REFRESH_TOKEN"  IS 'Almac¿n de datos para los listados del sistema';
--------------------------------------------------------
--  DDL for Table SICOES_TM_LISTADO
--------------------------------------------------------

  CREATE TABLE "SICOES"."SICOES_TM_LISTADO" 
   (	"ID_LISTADO" NUMBER(*,0), 
	"CD_LISTADO" VARCHAR2(50 BYTE), 
	"NO_LISTADO" VARCHAR2(100 BYTE), 
	"DE_LISTADO" VARCHAR2(200 BYTE), 
	"CO_PADRE" VARCHAR2(50 BYTE), 
	"US_CREACION" VARCHAR2(50 BYTE), 
	"FE_CREACION" TIMESTAMP (6), 
	"IP_CREACION" VARCHAR2(50 BYTE), 
	"US_ACTUALIZACION" VARCHAR2(50 BYTE), 
	"FE_ACTUALIZACION" TIMESTAMP (6), 
	"IP_ACTUALIZACION" VARCHAR2(50 BYTE)
   ) ;

   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO"."ID_LISTADO" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO"."CD_LISTADO" IS 'Corresponde al c¿digo del listado';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO"."NO_LISTADO" IS 'Corresponde al nombre del listado';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO"."DE_LISTADO" IS 'Corresponde a la descripci¿n del listado';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO"."US_CREACION" IS 'Corresponde al usuario de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO"."FE_CREACION" IS 'Corresponde a la fecha de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO"."IP_CREACION" IS 'Corresponde a la ip de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO"."US_ACTUALIZACION" IS 'Corresponde al usuario de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO"."FE_ACTUALIZACION" IS 'Corresponde a la fecha de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO"."IP_ACTUALIZACION" IS 'Corresponde a la ip de actualizaci¿n';
   COMMENT ON TABLE "SICOES"."SICOES_TM_LISTADO"  IS 'Almac¿n de datos para los listados del sistema';
--------------------------------------------------------
--  DDL for Table SICOES_TM_LISTADO_DETALLE
--------------------------------------------------------

  CREATE TABLE "SICOES"."SICOES_TM_LISTADO_DETALLE" 
   (	"ID_LISTADO_DETALLE" NUMBER(*,0), 
	"ID_LISTADO" NUMBER(*,0), 
	"ID_LISTADO_PADRE" NUMBER(*,0), 
	"ID_SUPERIOR_LD" NUMBER(*,0), 
	"CO_LISTADO_DETALLE" VARCHAR2(50 BYTE), 
	"NU_ORDEN" NUMBER(*,0), 
	"NO_LISTADO_DETALLE" VARCHAR2(500 BYTE), 
	"DE_LISTADO_DETALLE" VARCHAR2(1000 BYTE), 
	"DE_VALOR" VARCHAR2(4000 BYTE), 
	"US_CREACION" VARCHAR2(50 BYTE), 
	"FE_CREACION" TIMESTAMP (6), 
	"IP_CREACION" VARCHAR2(50 BYTE), 
	"US_ACTUALIZACION" VARCHAR2(50 BYTE), 
	"FE_ACTUALIZACION" TIMESTAMP (6), 
	"IP_ACTUALIZACION" VARCHAR2(50 BYTE)
   ) ;

   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO_DETALLE"."ID_LISTADO_DETALLE" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO_DETALLE"."ID_LISTADO" IS 'Corresponde al identificador de la tabla LISTADO';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO_DETALLE"."ID_LISTADO_PADRE" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO_DETALLE"."ID_SUPERIOR_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO_DETALLE"."CO_LISTADO_DETALLE" IS 'Corresponde al c¿digo del detalle';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO_DETALLE"."NU_ORDEN" IS 'Corresponde al nro de orden';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO_DETALLE"."NO_LISTADO_DETALLE" IS 'Corresponde al nombre del detalle';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO_DETALLE"."DE_LISTADO_DETALLE" IS 'Corresponde a la descripci¿n del detalle';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO_DETALLE"."DE_VALOR" IS 'Corresponde al valor del registro';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO_DETALLE"."US_CREACION" IS 'Corresponde al usuario de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO_DETALLE"."FE_CREACION" IS 'Corresponde a la fecha de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO_DETALLE"."IP_CREACION" IS 'Corresponde a la ip de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO_DETALLE"."US_ACTUALIZACION" IS 'Corresponde al usuario de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO_DETALLE"."FE_ACTUALIZACION" IS 'Corresponde a la fecha de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_LISTADO_DETALLE"."IP_ACTUALIZACION" IS 'Corresponde a la ip de actualizaci¿n';
   COMMENT ON TABLE "SICOES"."SICOES_TM_LISTADO_DETALLE"  IS 'Almac¿n de datos para los detalles de los listados generales del sistema';
--------------------------------------------------------
--  DDL for Table SICOES_TM_OPCION
--------------------------------------------------------

  CREATE TABLE "SICOES"."SICOES_TM_OPCION" 
   (	"ID_OPCION" NUMBER(*,0), 
	"ID_PADRE" NUMBER(*,0), 
	"ID_ESTADO_LD" NUMBER(*,0), 
	"NO_OPCION" VARCHAR2(50 BYTE), 
	"DE_OPCION" VARCHAR2(200 BYTE), 
	"CO_OPCION" VARCHAR2(200 BYTE), 
	"NU_ORDEN" VARCHAR2(200 BYTE), 
	"FL_VISIBLE" VARCHAR2(1 BYTE), 
	"US_CREACION" VARCHAR2(50 BYTE), 
	"FE_CREACION" TIMESTAMP (6), 
	"IP_CREACION" VARCHAR2(50 BYTE), 
	"US_ACTUALIZACION" VARCHAR2(50 BYTE), 
	"FE_ACTUALIZACION" TIMESTAMP (6), 
	"IP_ACTUALIZACION" VARCHAR2(50 BYTE)
   ) ;

   COMMENT ON COLUMN "SICOES"."SICOES_TM_OPCION"."ID_OPCION" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_OPCION"."ID_PADRE" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_OPCION"."ID_ESTADO_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_OPCION"."US_CREACION" IS 'Corresponde al usuario de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_OPCION"."FE_CREACION" IS 'Corresponde a la fecha de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_OPCION"."IP_CREACION" IS 'Corresponde a la ip de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_OPCION"."US_ACTUALIZACION" IS 'Corresponde al usuario de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_OPCION"."FE_ACTUALIZACION" IS 'Corresponde a la fecha de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_OPCION"."IP_ACTUALIZACION" IS 'Corresponde a la ip de actualizaci¿n';
   COMMENT ON TABLE "SICOES"."SICOES_TM_OPCION"  IS 'Almac¿n de datos para las empresas';
--------------------------------------------------------
--  DDL for Table SICOES_TM_ROL
--------------------------------------------------------

  CREATE TABLE "SICOES"."SICOES_TM_ROL" 
   (	"ID_ROL" NUMBER(*,0), 
	"ID_ESTADO_LD" NUMBER(*,0), 
	"NO_ROL" VARCHAR2(50 BYTE), 
	"DE_ROL" VARCHAR2(200 BYTE), 
	"CO_ROL" VARCHAR2(200 BYTE), 
	"US_CREACION" VARCHAR2(50 BYTE), 
	"FE_CREACION" TIMESTAMP (6), 
	"IP_CREACION" VARCHAR2(50 BYTE), 
	"US_ACTUALIZACION" VARCHAR2(50 BYTE), 
	"FE_ACTUALIZACION" TIMESTAMP (6), 
	"IP_ACTUALIZACION" VARCHAR2(50 BYTE)
   ) ;

   COMMENT ON COLUMN "SICOES"."SICOES_TM_ROL"."ID_ROL" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_ROL"."ID_ESTADO_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_ROL"."US_CREACION" IS 'Corresponde al usuario de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_ROL"."FE_CREACION" IS 'Corresponde a la fecha de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_ROL"."IP_CREACION" IS 'Corresponde a la ip de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_ROL"."US_ACTUALIZACION" IS 'Corresponde al usuario de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_ROL"."FE_ACTUALIZACION" IS 'Corresponde a la fecha de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_ROL"."IP_ACTUALIZACION" IS 'Corresponde a la ip de actualizaci¿n';
   COMMENT ON TABLE "SICOES"."SICOES_TM_ROL"  IS 'Almac¿n de datos para las empresas';
--------------------------------------------------------
--  DDL for Table SICOES_TM_ROL_OPCION
--------------------------------------------------------

  CREATE TABLE "SICOES"."SICOES_TM_ROL_OPCION" 
   (	"ID_ROL_OPCION" NUMBER(*,0), 
	"ID_OPCION" NUMBER(*,0), 
	"ID_ROL" NUMBER(*,0), 
	"US_CREACION" VARCHAR2(50 BYTE), 
	"FE_CREACION" TIMESTAMP (6), 
	"IP_CREACION" VARCHAR2(50 BYTE), 
	"US_ACTUALIZACION" VARCHAR2(50 BYTE), 
	"FE_ACTUALIZACION" TIMESTAMP (6), 
	"IP_ACTUALIZACION" VARCHAR2(50 BYTE)
   ) ;

   COMMENT ON COLUMN "SICOES"."SICOES_TM_ROL_OPCION"."ID_ROL_OPCION" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_ROL_OPCION"."ID_OPCION" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_ROL_OPCION"."ID_ROL" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_ROL_OPCION"."US_CREACION" IS 'Corresponde al usuario de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_ROL_OPCION"."FE_CREACION" IS 'Corresponde a la fecha de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_ROL_OPCION"."IP_CREACION" IS 'Corresponde a la ip de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_ROL_OPCION"."US_ACTUALIZACION" IS 'Corresponde al usuario de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_ROL_OPCION"."FE_ACTUALIZACION" IS 'Corresponde a la fecha de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_ROL_OPCION"."IP_ACTUALIZACION" IS 'Corresponde a la ip de actualizaci¿n';
   COMMENT ON TABLE "SICOES"."SICOES_TM_ROL_OPCION"  IS 'Almac¿n de datos para las empresas';
--------------------------------------------------------
--  DDL for Table SICOES_TM_UBIGEO
--------------------------------------------------------

  CREATE TABLE "SICOES"."SICOES_TM_UBIGEO" 
   (	"ID_UBIGEO" NUMBER(*,0), 
	"CO_UBIGEO" VARCHAR2(10 BYTE), 
	"CO_DEPARTAMENTO" VARCHAR2(10 BYTE), 
	"NO_DEPARTAMENTO" VARCHAR2(100 BYTE), 
	"CO_PROVINCIA" VARCHAR2(10 BYTE), 
	"NO_PROVINCIA" VARCHAR2(100 BYTE), 
	"CO_DISTRITO" VARCHAR2(10 BYTE), 
	"NO_DISTRITO" VARCHAR2(100 BYTE), 
	"US_CREACION" VARCHAR2(50 BYTE), 
	"FE_CREACION" TIMESTAMP (6), 
	"IP_CREACION" VARCHAR2(50 BYTE), 
	"US_ACTUALIZACION" VARCHAR2(50 BYTE), 
	"FE_ACTUALIZACION" TIMESTAMP (6), 
	"IP_ACTUALIZACION" VARCHAR2(50 BYTE)
   ) ;

   COMMENT ON COLUMN "SICOES"."SICOES_TM_UBIGEO"."ID_UBIGEO" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_UBIGEO"."US_CREACION" IS 'Corresponde al usuario de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_UBIGEO"."FE_CREACION" IS 'Corresponde a la fecha de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_UBIGEO"."IP_CREACION" IS 'Corresponde a la ip de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_UBIGEO"."US_ACTUALIZACION" IS 'Corresponde al usuario de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_UBIGEO"."FE_ACTUALIZACION" IS 'Corresponde a la fecha de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_UBIGEO"."IP_ACTUALIZACION" IS 'Corresponde a la ip de actualizaci¿n';
   COMMENT ON TABLE "SICOES"."SICOES_TM_UBIGEO"  IS 'Almac¿n de datos para las empresas';
--------------------------------------------------------
--  DDL for Table SICOES_TM_USUARIO
--------------------------------------------------------

  CREATE TABLE "SICOES"."SICOES_TM_USUARIO" 
   (	"ID_USUARIO" NUMBER(*,0), 
	"DE_USUARIO" VARCHAR2(200 BYTE), 
	"DE_RUC" VARCHAR2(50 BYTE), 
	"NO_USUARIO" VARCHAR2(200 BYTE), 
	"DE_CONTRASENIA" VARCHAR2(200 BYTE), 
	"DE_RAZON_SOCIAL" VARCHAR2(200 BYTE), 
	"DE_CORREO" VARCHAR2(200 BYTE), 
	"US_CREACION" VARCHAR2(50 BYTE), 
	"FE_CREACION" TIMESTAMP (6), 
	"IP_CREACION" VARCHAR2(50 BYTE), 
	"US_ACTUALIZACION" VARCHAR2(50 BYTE), 
	"FE_ACTUALIZACION" TIMESTAMP (6), 
	"IP_ACTUALIZACION" VARCHAR2(50 BYTE)
   ) ;

   COMMENT ON COLUMN "SICOES"."SICOES_TM_USUARIO"."ID_USUARIO" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_USUARIO"."US_CREACION" IS 'Corresponde al usuario de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_USUARIO"."FE_CREACION" IS 'Corresponde a la fecha de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_USUARIO"."IP_CREACION" IS 'Corresponde a la ip de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_USUARIO"."US_ACTUALIZACION" IS 'Corresponde al usuario de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_USUARIO"."FE_ACTUALIZACION" IS 'Corresponde a la fecha de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_USUARIO"."IP_ACTUALIZACION" IS 'Corresponde a la ip de actualizaci¿n';
   COMMENT ON TABLE "SICOES"."SICOES_TM_USUARIO"  IS 'Almac¿n de datos para las empresas';
--------------------------------------------------------
--  DDL for Table SICOES_TM_USUARIO_ROL
--------------------------------------------------------

  CREATE TABLE "SICOES"."SICOES_TM_USUARIO_ROL" 
   (	"ID_USUARIO_ROL" NUMBER(*,0), 
	"ID_ROL" NUMBER(*,0), 
	"ID_USUARIO" NUMBER(*,0), 
	"US_CREACION" VARCHAR2(50 BYTE), 
	"FE_CREACION" TIMESTAMP (6), 
	"IP_CREACION" VARCHAR2(50 BYTE), 
	"US_ACTUALIZACION" VARCHAR2(50 BYTE), 
	"FE_ACTUALIZACION" TIMESTAMP (6), 
	"IP_ACTUALIZACION" VARCHAR2(50 BYTE)
   ) ;

   COMMENT ON COLUMN "SICOES"."SICOES_TM_USUARIO_ROL"."ID_USUARIO_ROL" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_USUARIO_ROL"."ID_ROL" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_USUARIO_ROL"."ID_USUARIO" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_USUARIO_ROL"."US_CREACION" IS 'Corresponde al usuario de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_USUARIO_ROL"."FE_CREACION" IS 'Corresponde a la fecha de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_USUARIO_ROL"."IP_CREACION" IS 'Corresponde a la ip de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_USUARIO_ROL"."US_ACTUALIZACION" IS 'Corresponde al usuario de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_USUARIO_ROL"."FE_ACTUALIZACION" IS 'Corresponde a la fecha de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TM_USUARIO_ROL"."IP_ACTUALIZACION" IS 'Corresponde a la ip de actualizaci¿n';
   COMMENT ON TABLE "SICOES"."SICOES_TM_USUARIO_ROL"  IS 'Almac¿n de datos para las empresas';
--------------------------------------------------------
--  DDL for Table SICOES_TR_ARCHIVO
--------------------------------------------------------

  CREATE TABLE "SICOES"."SICOES_TR_ARCHIVO" 
   (	"ID_ARCHIVO" NUMBER(*,0), 
	"ID_DOCUMENTO" NUMBER(*,0), 
	"ID_OTRO_REQUISITO" NUMBER(*,0), 
	"ID_ESTUDIO" NUMBER(*,0), 
	"ID_ESTADO_LD" NUMBER(*,0), 
	"ID_TIPO_ARCHIVO_LD" NUMBER(*,0), 
	"ID_SOLICITUD" NUMBER(*,0), 
	"NO_ARCHIVO" VARCHAR2(200 BYTE), 
	"NO_REAL" VARCHAR2(200 BYTE), 
	"NO_ALFRESCO" VARCHAR2(200 BYTE), 
	"CO_ARCHIVO" VARCHAR2(200 BYTE), 
	"DE_ARCHIVO" VARCHAR2(4000 BYTE), 
	"CO_TIPO_ARCHIVO" VARCHAR2(200 BYTE), 
	"NU_CORRELATIVO" NUMBER(*,0), 
	"NU_VERSION" NUMBER(*,0), 
	"NU_FOLIO" NUMBER(*,0), 
	"NU_PESO" NUMBER(*,0), 
	"US_CREACION" VARCHAR2(50 BYTE), 
	"FE_CREACION" TIMESTAMP (6), 
	"IP_CREACION" VARCHAR2(50 BYTE), 
	"US_ACTUALIZACION" VARCHAR2(50 BYTE), 
	"FE_ACTUALIZACION" TIMESTAMP (6), 
	"IP_ACTUALIZACION" VARCHAR2(50 BYTE)
   ) ;

   COMMENT ON COLUMN "SICOES"."SICOES_TR_ARCHIVO"."ID_ARCHIVO" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ARCHIVO"."ID_DOCUMENTO" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ARCHIVO"."ID_OTRO_REQUISITO" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ARCHIVO"."ID_ESTUDIO" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ARCHIVO"."ID_ESTADO_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ARCHIVO"."ID_TIPO_ARCHIVO_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ARCHIVO"."ID_SOLICITUD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ARCHIVO"."US_CREACION" IS 'Corresponde al usuario de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ARCHIVO"."FE_CREACION" IS 'Corresponde a la fecha de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ARCHIVO"."IP_CREACION" IS 'Corresponde a la ip de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ARCHIVO"."US_ACTUALIZACION" IS 'Corresponde al usuario de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ARCHIVO"."FE_ACTUALIZACION" IS 'Corresponde a la fecha de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ARCHIVO"."IP_ACTUALIZACION" IS 'Corresponde a la ip de actualizaci¿n';
   COMMENT ON TABLE "SICOES"."SICOES_TR_ARCHIVO"  IS 'Almac¿n de datos para las empresas';
--------------------------------------------------------
--  DDL for Table SICOES_TR_ASIGNACION
--------------------------------------------------------

  CREATE TABLE "SICOES"."SICOES_TR_ASIGNACION" 
   (	"ID_ASIGNACION" NUMBER(*,0), 
	"ID_SOLICITUD" NUMBER(*,0), 
	"ID_TIPO_LD" NUMBER(*,0), 
	"ID_USUARIO" NUMBER(*,0), 
	"ID_GRUPO_LD" NUMBER(*,0), 
	"ID_EVALUACION_LD" NUMBER(*,0), 
	"DE_OBSERVACION" VARCHAR2(4000 BYTE), 
	"FE_APROBACION" DATE, 
	"NU_DIA_PLAZO_RESP" NUMBER(*,0), 
	"FE_PLAZO_RESP" DATE, 
	"FE_REGISTRO" DATE, 
	"FL_ACTIVO" NUMBER(*,0), 
	"US_CREACION" VARCHAR2(50 BYTE), 
	"FE_CREACION" TIMESTAMP (6), 
	"IP_CREACION" VARCHAR2(50 BYTE), 
	"US_ACTUALIZACION" VARCHAR2(50 BYTE), 
	"FE_ACTUALIZACION" TIMESTAMP (6), 
	"IP_ACTUALIZACION" VARCHAR2(50 BYTE)
   ) ;

   COMMENT ON COLUMN "SICOES"."SICOES_TR_ASIGNACION"."ID_ASIGNACION" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ASIGNACION"."ID_SOLICITUD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ASIGNACION"."ID_TIPO_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ASIGNACION"."ID_USUARIO" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ASIGNACION"."ID_GRUPO_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ASIGNACION"."ID_EVALUACION_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ASIGNACION"."US_CREACION" IS 'Corresponde al usuario de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ASIGNACION"."FE_CREACION" IS 'Corresponde a la fecha de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ASIGNACION"."IP_CREACION" IS 'Corresponde a la ip de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ASIGNACION"."US_ACTUALIZACION" IS 'Corresponde al usuario de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ASIGNACION"."FE_ACTUALIZACION" IS 'Corresponde a la fecha de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ASIGNACION"."IP_ACTUALIZACION" IS 'Corresponde a la ip de actualizaci¿n';
   COMMENT ON TABLE "SICOES"."SICOES_TR_ASIGNACION"  IS 'Almac¿n de datos para las empresas';
--------------------------------------------------------
--  DDL for Table SICOES_TR_DOCUMENTO
--------------------------------------------------------

  CREATE TABLE "SICOES"."SICOES_TR_DOCUMENTO" 
   (	"ID_DOCUMENTO" NUMBER(*,0), 
	"ID_TIPO_LD" NUMBER(*,0), 
	"ID_TIPO_DOCUMENTO_LD" NUMBER(*,0), 
	"ID_TIPO_CAMBIO_LD" NUMBER(*,0), 
	"ID_SOLICITUD" NUMBER(*,0), 
	"ID_DOCUMENTO_PADRE" NUMBER(*,0), 
	"NU_DOCUMENTO" VARCHAR2(20 BYTE), 
	"CO_CONTRATO" VARCHAR2(200 BYTE), 
	"DE_CONTRATO" VARCHAR2(200 BYTE), 
	"FE_INICIO_CONTRATO" DATE, 
	"FE_FIN_CONTRATO" DATE, 
	"DE_DURACION" VARCHAR2(200 BYTE), 
	"FL_VIGENTE" VARCHAR2(1 BYTE), 
	"FE_CONFORMIDAD" DATE, 
	"MO_CONTRATO" NUMBER(18,2), 
	"MO_TIPO_CAMBIO" NUMBER(18,2), 
	"ID_EVALUACION_LD" NUMBER(*,0), 
	"ID_PAIS_LD" NUMBER(*,0), 
	"ID_TIPO_ID_TRIBURARIO_LD" NUMBER(*,0), 
	"ID_CONFORMIDAD_LD" NUMBER(*,0), 
	"MO_CONTRATO_SOL" NUMBER(18,2), 
	"DE_OBSERVACION" VARCHAR2(4000 BYTE), 
	"NO_ENTIDAD" VARCHAR2(4000 BYTE), 
	"FL_SIGED" NUMBER(*,0), 
	"US_CREACION" VARCHAR2(50 BYTE), 
	"FE_CREACION" TIMESTAMP (6), 
	"IP_CREACION" VARCHAR2(50 BYTE), 
	"US_ACTUALIZACION" VARCHAR2(50 BYTE), 
	"FE_ACTUALIZACION" TIMESTAMP (6), 
	"IP_ACTUALIZACION" VARCHAR2(50 BYTE)
   ) ;

   COMMENT ON COLUMN "SICOES"."SICOES_TR_DOCUMENTO"."ID_DOCUMENTO" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_DOCUMENTO"."ID_TIPO_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_DOCUMENTO"."ID_TIPO_DOCUMENTO_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_DOCUMENTO"."ID_TIPO_CAMBIO_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_DOCUMENTO"."ID_SOLICITUD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_DOCUMENTO"."ID_DOCUMENTO_PADRE" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_DOCUMENTO"."ID_EVALUACION_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_DOCUMENTO"."ID_PAIS_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_DOCUMENTO"."ID_TIPO_ID_TRIBURARIO_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_DOCUMENTO"."ID_CONFORMIDAD_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_DOCUMENTO"."US_CREACION" IS 'Corresponde al usuario de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_DOCUMENTO"."FE_CREACION" IS 'Corresponde a la fecha de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_DOCUMENTO"."IP_CREACION" IS 'Corresponde a la ip de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_DOCUMENTO"."US_ACTUALIZACION" IS 'Corresponde al usuario de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_DOCUMENTO"."FE_ACTUALIZACION" IS 'Corresponde a la fecha de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_DOCUMENTO"."IP_ACTUALIZACION" IS 'Corresponde a la ip de actualizaci¿n';
   COMMENT ON TABLE "SICOES"."SICOES_TR_DOCUMENTO"  IS 'Almac¿n de datos para las empresas';
--------------------------------------------------------
--  DDL for Table SICOES_TR_ESTUDIO
--------------------------------------------------------

  CREATE TABLE "SICOES"."SICOES_TR_ESTUDIO" 
   (	"ID_ESTUDIO" NUMBER(*,0), 
	"ID_TIPO_LD" NUMBER(*,0), 
	"FL_EGRESADO" VARCHAR2(50 BYTE), 
	"DE_COLEGIATURA" VARCHAR2(50 BYTE), 
	"DE_ESPECIALIDAD" VARCHAR2(200 BYTE), 
	"FE_VIGENCIA_GRADO" DATE, 
	"NO_INSTITUCION" VARCHAR2(200 BYTE), 
	"FL_COLEGIATURA" VARCHAR2(50 BYTE), 
	"FE_VIGENCIA_COLEGIATURA" DATE, 
	"NO_INSTITUCION_COLEGIATURA" VARCHAR2(200 BYTE), 
	"NU_HORA" NUMBER(*,0), 
	"FE_VIGENCIA" DATE, 
	"FE_INICIO" DATE, 
	"FE_FIN" DATE, 
	"ID_EVALUACION_LD" NUMBER(*,0), 
	"ID_SOLICITUD" NUMBER(*,0), 
	"ID_ESTUDIO_PADRE" NUMBER(*,0), 
	"ID_TIPO_ESTUDIO_LD" NUMBER(*,0), 
	"ID_FUENTE_LD" NUMBER(*,0), 
	"DE_OBSERVACION" VARCHAR2(4000 BYTE), 
	"FL_ACTIVO" NUMBER(*,0), 
	"FL_SIGED" NUMBER(*,0), 
	"US_CREACION" VARCHAR2(50 BYTE), 
	"FE_CREACION" TIMESTAMP (6), 
	"IP_CREACION" VARCHAR2(50 BYTE), 
	"US_ACTUALIZACION" VARCHAR2(50 BYTE), 
	"FE_ACTUALIZACION" TIMESTAMP (6), 
	"IP_ACTUALIZACION" VARCHAR2(50 BYTE)
   ) ;

   COMMENT ON COLUMN "SICOES"."SICOES_TR_ESTUDIO"."ID_ESTUDIO" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ESTUDIO"."ID_TIPO_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ESTUDIO"."ID_EVALUACION_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ESTUDIO"."ID_SOLICITUD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ESTUDIO"."ID_ESTUDIO_PADRE" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ESTUDIO"."ID_TIPO_ESTUDIO_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ESTUDIO"."ID_FUENTE_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ESTUDIO"."US_CREACION" IS 'Corresponde al usuario de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ESTUDIO"."FE_CREACION" IS 'Corresponde a la fecha de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ESTUDIO"."IP_CREACION" IS 'Corresponde a la ip de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ESTUDIO"."US_ACTUALIZACION" IS 'Corresponde al usuario de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ESTUDIO"."FE_ACTUALIZACION" IS 'Corresponde a la fecha de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_ESTUDIO"."IP_ACTUALIZACION" IS 'Corresponde a la ip de actualizaci¿n';
   COMMENT ON TABLE "SICOES"."SICOES_TR_ESTUDIO"  IS 'Almac¿n de datos para las empresas';
--------------------------------------------------------
--  DDL for Table SICOES_TR_NOTIFICACION
--------------------------------------------------------

  CREATE TABLE "SICOES"."SICOES_TR_NOTIFICACION" 
   (	"ID_NOTIFICACION" NUMBER(*,0), 
	"ID_ESTADO_LD" NUMBER(*,0), 
	"CORREO" VARCHAR2(4000 BYTE), 
	"ASUNTO" VARCHAR2(4000 BYTE), 
	"MENSAJE" VARCHAR2(4000 BYTE), 
	"US_CREACION" VARCHAR2(50 BYTE), 
	"FE_CREACION" TIMESTAMP (6), 
	"IP_CREACION" VARCHAR2(50 BYTE), 
	"US_ACTUALIZACION" VARCHAR2(50 BYTE), 
	"FE_ACTUALIZACION" TIMESTAMP (6), 
	"IP_ACTUALIZACION" VARCHAR2(50 BYTE)
   ) ;

   COMMENT ON COLUMN "SICOES"."SICOES_TR_NOTIFICACION"."ID_NOTIFICACION" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_NOTIFICACION"."ID_ESTADO_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_NOTIFICACION"."US_CREACION" IS 'Corresponde al usuario de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_NOTIFICACION"."FE_CREACION" IS 'Corresponde a la fecha de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_NOTIFICACION"."IP_CREACION" IS 'Corresponde a la ip de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_NOTIFICACION"."US_ACTUALIZACION" IS 'Corresponde al usuario de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_NOTIFICACION"."FE_ACTUALIZACION" IS 'Corresponde a la fecha de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_NOTIFICACION"."IP_ACTUALIZACION" IS 'Corresponde a la ip de actualizaci¿n';
   COMMENT ON TABLE "SICOES"."SICOES_TR_NOTIFICACION"  IS 'Almac¿n de datos para las empresas';
--------------------------------------------------------
--  DDL for Table SICOES_TR_OTRO_REQUISITO
--------------------------------------------------------

  CREATE TABLE "SICOES"."SICOES_TR_OTRO_REQUISITO" 
   (	"ID_OTRO_REQUISITO" NUMBER(*,0), 
	"ID_TIPO_LD" NUMBER(*,0), 
	"ID_TIPO_REQUISITO_LD" NUMBER(*,0), 
	"ID_SECTOR_LD" NUMBER(*,0), 
	"ID_SUBSECTOR_LD" NUMBER(*,0), 
	"ID_ACTIVIDAD_LD" NUMBER(*,0), 
	"ID_UNIDAD_LD" NUMBER(*,0), 
	"ID_SUBCATEGORIA_LD" NUMBER(*,0), 
	"ID_PERFIL_LD" NUMBER(*,0), 
	"FL_ELECTRONICO" VARCHAR2(1 BYTE), 
	"FL_FIRMA_DIGITAL" VARCHAR2(1 BYTE), 
	"FE_EXPEDICION" DATE, 
	"FL_ACTIVO" VARCHAR2(1 BYTE), 
	"ID_EVALUACION_LD" NUMBER(*,0), 
	"ID_SOLICITUD" NUMBER(*,0), 
	"ID_OTRO_REQUISITO_PADRE" NUMBER(*,0), 
	"DE_OBSERVACION" VARCHAR2(4000 BYTE), 
	"FL_SIGED" NUMBER(*,0), 
	"US_CREACION" VARCHAR2(50 BYTE), 
	"FE_CREACION" TIMESTAMP (6), 
	"IP_CREACION" VARCHAR2(50 BYTE), 
	"US_ACTUALIZACION" VARCHAR2(50 BYTE), 
	"FE_ACTUALIZACION" TIMESTAMP (6), 
	"IP_ACTUALIZACION" VARCHAR2(50 BYTE)
   ) ;

   COMMENT ON COLUMN "SICOES"."SICOES_TR_OTRO_REQUISITO"."ID_OTRO_REQUISITO" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_OTRO_REQUISITO"."ID_TIPO_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_OTRO_REQUISITO"."ID_TIPO_REQUISITO_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_OTRO_REQUISITO"."ID_SECTOR_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_OTRO_REQUISITO"."ID_SUBSECTOR_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_OTRO_REQUISITO"."ID_ACTIVIDAD_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_OTRO_REQUISITO"."ID_UNIDAD_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_OTRO_REQUISITO"."ID_SUBCATEGORIA_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_OTRO_REQUISITO"."ID_PERFIL_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_OTRO_REQUISITO"."ID_EVALUACION_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_OTRO_REQUISITO"."ID_SOLICITUD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_OTRO_REQUISITO"."ID_OTRO_REQUISITO_PADRE" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_OTRO_REQUISITO"."US_CREACION" IS 'Corresponde al usuario de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_OTRO_REQUISITO"."FE_CREACION" IS 'Corresponde a la fecha de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_OTRO_REQUISITO"."IP_CREACION" IS 'Corresponde a la ip de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_OTRO_REQUISITO"."US_ACTUALIZACION" IS 'Corresponde al usuario de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_OTRO_REQUISITO"."FE_ACTUALIZACION" IS 'Corresponde a la fecha de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_OTRO_REQUISITO"."IP_ACTUALIZACION" IS 'Corresponde a la ip de actualizaci¿n';
   COMMENT ON TABLE "SICOES"."SICOES_TR_OTRO_REQUISITO"  IS 'Almac¿n de datos para las empresas';
--------------------------------------------------------
--  DDL for Table SICOES_TR_PERSONA
--------------------------------------------------------

  CREATE TABLE "SICOES"."SICOES_TR_PERSONA" 
   (	"ID_PERSONA" NUMBER(*,0), 
	"ID_TIPO_DOCUMENTO_LD" NUMBER(*,0), 
	"ID_PAIS_LD" NUMBER(*,0), 
	"NU_DOCUMENTO" VARCHAR2(20 BYTE), 
	"NO_RAZON_SOCIAL" VARCHAR2(200 BYTE), 
	"NO_PERSONA" VARCHAR2(200 BYTE), 
	"AP_PATERNO" VARCHAR2(200 BYTE), 
	"AP_MATERNO" VARCHAR2(200 BYTE), 
	"CO_RUC" VARCHAR2(20 BYTE), 
	"DE_DIRECCION" VARCHAR2(4000 BYTE), 
	"CO_CLIENTE" VARCHAR2(100 BYTE), 
	"CO_DEPARTAMENTO" VARCHAR2(10 BYTE), 
	"CO_PROVINCIA" VARCHAR2(10 BYTE), 
	"CO_DISTRITO" VARCHAR2(10 BYTE), 
	"NO_DEPARTAMENTO" VARCHAR2(100 BYTE), 
	"NO_PROVINCIA" VARCHAR2(100 BYTE), 
	"NO_DISTRITO" VARCHAR2(100 BYTE), 
	"CO_PARTIDA_REGISTRAL" VARCHAR2(50 BYTE), 
	"DE_TELEFONO1" VARCHAR2(10 BYTE), 
	"DE_TELEFONO2" VARCHAR2(10 BYTE), 
	"DE_TELEFONO3" VARCHAR2(10 BYTE), 
	"DE_CORREO" VARCHAR2(200 BYTE), 
	"US_CREACION" VARCHAR2(50 BYTE), 
	"FE_CREACION" TIMESTAMP (6), 
	"IP_CREACION" VARCHAR2(50 BYTE), 
	"US_ACTUALIZACION" VARCHAR2(50 BYTE), 
	"FE_ACTUALIZACION" TIMESTAMP (6), 
	"IP_ACTUALIZACION" VARCHAR2(50 BYTE)
   ) ;

   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."ID_PERSONA" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."ID_TIPO_DOCUMENTO_LD" IS 'Corresponde al identificador de la tabla tipo documento de la persona';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."ID_PAIS_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."NU_DOCUMENTO" IS 'Corresponde al n¿mero de documento de la persona';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."NO_RAZON_SOCIAL" IS 'Corresponde a la raz¿n social de la PJ';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."NO_PERSONA" IS 'Corresponde al nombre de PN';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."AP_PATERNO" IS 'Corresponde al apellido paterno de PN';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."AP_MATERNO" IS 'Corresponde al apellido materno de PN';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."CO_RUC" IS 'Corresponde al ruc de PN';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."DE_DIRECCION" IS 'Corresponde a la direcci¿n de la persona';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."CO_DEPARTAMENTO" IS 'Corresponde al c¿digo ubigeo del departamento';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."CO_PROVINCIA" IS 'Corresponde al c¿digo ubigeo de la provincia';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."CO_DISTRITO" IS 'Corresponde al c¿digo ubigeo del distrito';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."CO_PARTIDA_REGISTRAL" IS 'Corresponde al c¿digo de partida registral';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."DE_TELEFONO1" IS 'Corresponde al n¿mero de telefono 1';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."DE_TELEFONO2" IS 'Corresponde al n¿mero de telefono 2';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."DE_TELEFONO3" IS 'Corresponde al n¿mero de telefono 3';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."DE_CORREO" IS 'Corresponde al correo electronico de la persona';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."US_CREACION" IS 'Corresponde al usuario de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."FE_CREACION" IS 'Corresponde a la fecha de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."IP_CREACION" IS 'Corresponde a la ip de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."US_ACTUALIZACION" IS 'Corresponde al usuario de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."FE_ACTUALIZACION" IS 'Corresponde a la fecha de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_PERSONA"."IP_ACTUALIZACION" IS 'Corresponde a la ip de actualizaci¿n';
   COMMENT ON TABLE "SICOES"."SICOES_TR_PERSONA"  IS 'Almac¿n de datos para las empresas';
--------------------------------------------------------
--  DDL for Table SICOES_TR_REPRESENTANTE
--------------------------------------------------------

  CREATE TABLE "SICOES"."SICOES_TR_REPRESENTANTE" 
   (	"ID_REPRESENTANTE" NUMBER(*,0), 
	"ID_TIPO_DOCUMENTO_LD" NUMBER(*,0), 
	"NU_DOCUMENTO" VARCHAR2(20 BYTE), 
	"NO_REPRESENTANTE" VARCHAR2(200 BYTE), 
	"AP_PATERNO" VARCHAR2(4000 BYTE), 
	"AP_MATERNO" VARCHAR2(10 BYTE), 
	"US_CREACION" VARCHAR2(50 BYTE), 
	"FE_CREACION" TIMESTAMP (6), 
	"IP_CREACION" VARCHAR2(50 BYTE), 
	"US_ACTUALIZACION" VARCHAR2(50 BYTE), 
	"FE_ACTUALIZACION" TIMESTAMP (6), 
	"IP_ACTUALIZACION" VARCHAR2(50 BYTE)
   ) ;

   COMMENT ON COLUMN "SICOES"."SICOES_TR_REPRESENTANTE"."ID_REPRESENTANTE" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_REPRESENTANTE"."ID_TIPO_DOCUMENTO_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_REPRESENTANTE"."US_CREACION" IS 'Corresponde al usuario de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_REPRESENTANTE"."FE_CREACION" IS 'Corresponde a la fecha de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_REPRESENTANTE"."IP_CREACION" IS 'Corresponde a la ip de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_REPRESENTANTE"."US_ACTUALIZACION" IS 'Corresponde al usuario de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_REPRESENTANTE"."FE_ACTUALIZACION" IS 'Corresponde a la fecha de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_REPRESENTANTE"."IP_ACTUALIZACION" IS 'Corresponde a la ip de actualizaci¿n';
   COMMENT ON TABLE "SICOES"."SICOES_TR_REPRESENTANTE"  IS 'Almac¿n de datos para las empresas';
--------------------------------------------------------
--  DDL for Table SICOES_TR_SOLICITUD
--------------------------------------------------------

  CREATE TABLE "SICOES"."SICOES_TR_SOLICITUD" 
   (	"ID_SOLICITUD" NUMBER(*,0), 
	"NU_EXPEDIENTE" VARCHAR2(50 BYTE), 
	"ID_REPRESENTANTE" NUMBER(*,0), 
	"ID_PERSONA" NUMBER(*,0), 
	"ID_USUARIO" NUMBER(*,0), 
	"ID_RESUL_ADMIN" NUMBER(*,0), 
	"ID_RESUL_TECNICO" NUMBER(*,0), 
	"ID_ESTADO_LD" NUMBER(*,0), 
	"ID_TIPO_SOLICITUD_LD" NUMBER(*,0), 
	"ID_ESTADO_REVISION_LD" NUMBER(*,0), 
	"ID_SOLICITUD_PADRE" NUMBER(*,0), 
	"ID_ESTADO_EVAL_TECNICA_LD" NUMBER(*,0), 
	"ID_ESTADO_EVAL_ADMIN_LD" NUMBER(*,0), 
	"CO_CONSENTIMIENTO" VARCHAR2(50 BYTE), 
	"NU_DIA_PLAZO_RESP" NUMBER(*,0), 
	"FE_PLAZO_RESP" DATE, 
	"NU_DIA_PLAZO_ASIG" NUMBER(*,0), 
	"FE_PLAZO_ASIG" DATE, 
	"NU_DIA_PLAZO_SUB" NUMBER(*,0), 
	"FE_PLAZO_ASIG_SUB" DATE, 
	"FE_REGISTRO" DATE, 
	"FE_PRESENTACION" DATE, 
	"DE_OBS_TECNICA" VARCHAR2(4000 BYTE), 
	"DE_OBS_ADMIN" VARCHAR2(4000 BYTE), 
	"DE_NO_CALIFICA" VARCHAR2(4000 BYTE), 
	"FL_ACTIVO" NUMBER(*,0), 
	"US_CREACION" VARCHAR2(50 BYTE), 
	"FE_CREACION" TIMESTAMP (6), 
	"IP_CREACION" VARCHAR2(50 BYTE), 
	"US_ACTUALIZACION" VARCHAR2(50 BYTE), 
	"FE_ACTUALIZACION" TIMESTAMP (6), 
	"IP_ACTUALIZACION" VARCHAR2(50 BYTE)
   ) ;

   COMMENT ON COLUMN "SICOES"."SICOES_TR_SOLICITUD"."ID_SOLICITUD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_SOLICITUD"."ID_REPRESENTANTE" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_SOLICITUD"."ID_PERSONA" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_SOLICITUD"."ID_USUARIO" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_SOLICITUD"."ID_RESUL_ADMIN" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_SOLICITUD"."ID_RESUL_TECNICO" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_SOLICITUD"."ID_ESTADO_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_SOLICITUD"."ID_TIPO_SOLICITUD_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_SOLICITUD"."ID_ESTADO_REVISION_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_SOLICITUD"."ID_SOLICITUD_PADRE" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_SOLICITUD"."ID_ESTADO_EVAL_TECNICA_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_SOLICITUD"."ID_ESTADO_EVAL_ADMIN_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_SOLICITUD"."US_CREACION" IS 'Corresponde al usuario de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_SOLICITUD"."FE_CREACION" IS 'Corresponde a la fecha de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_SOLICITUD"."IP_CREACION" IS 'Corresponde a la ip de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_SOLICITUD"."US_ACTUALIZACION" IS 'Corresponde al usuario de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_SOLICITUD"."FE_ACTUALIZACION" IS 'Corresponde a la fecha de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_SOLICITUD"."IP_ACTUALIZACION" IS 'Corresponde a la ip de actualizaci¿n';
   COMMENT ON TABLE "SICOES"."SICOES_TR_SOLICITUD"  IS 'Almac¿n de datos para las empresas';
--------------------------------------------------------
--  DDL for Table SICOES_TR_TOKEN
--------------------------------------------------------

  CREATE TABLE "SICOES"."SICOES_TR_TOKEN" 
   (	"ID_TOKEN" NUMBER(*,0), 
	"ID_TIPO_LD" NUMBER(*,0), 
	"ID_ESTADO_LD" NUMBER(*,0), 
	"ID_USUARIO" NUMBER(*,0), 
	"CO_TOKEN" VARCHAR2(50 BYTE), 
	"US_CREACION" VARCHAR2(50 BYTE), 
	"FE_CREACION" TIMESTAMP (6), 
	"IP_CREACION" VARCHAR2(50 BYTE), 
	"US_ACTUALIZACION" VARCHAR2(50 BYTE), 
	"FE_ACTUALIZACION" TIMESTAMP (6), 
	"IP_ACTUALIZACION" VARCHAR2(50 BYTE)
   ) ;

   COMMENT ON COLUMN "SICOES"."SICOES_TR_TOKEN"."ID_TOKEN" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_TOKEN"."ID_TIPO_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_TOKEN"."ID_ESTADO_LD" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_TOKEN"."ID_USUARIO" IS 'Corresponde al identificador de la tabla';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_TOKEN"."US_CREACION" IS 'Corresponde al usuario de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_TOKEN"."FE_CREACION" IS 'Corresponde a la fecha de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_TOKEN"."IP_CREACION" IS 'Corresponde a la ip de creaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_TOKEN"."US_ACTUALIZACION" IS 'Corresponde al usuario de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_TOKEN"."FE_ACTUALIZACION" IS 'Corresponde a la fecha de actualizaci¿n';
   COMMENT ON COLUMN "SICOES"."SICOES_TR_TOKEN"."IP_ACTUALIZACION" IS 'Corresponde a la ip de actualizaci¿n';
   COMMENT ON TABLE "SICOES"."SICOES_TR_TOKEN"  IS 'Almac¿n de datos para las empresas';
REM INSERTING into SICOES.OAUTH_ACCESS_TOKEN
SET DEFINE OFF;
Insert into SICOES.OAUTH_ACCESS_TOKEN (AUTHENTICATION_ID,TOKEN_ID,USER_NAME,REFRESH_TOKEN,CLIENT_ID) values ('f0670573fa97faf74cdb80b206609f07','e73134e33ca782296472c3d4eccddf52','rzegarra','79668112162191a0a897d9f1b5da5472','app');
Insert into SICOES.OAUTH_ACCESS_TOKEN (AUTHENTICATION_ID,TOKEN_ID,USER_NAME,REFRESH_TOKEN,CLIENT_ID) values ('06c20b1c25eacafad13c60802285f592','12f28ca97e8cda9244c0122e0fa13c11','PHAVELUR','b7b41368be2057220bab47d2d1302fc2','app');
REM INSERTING into SICOES.OAUTH_CLIENT_DETAILS
SET DEFINE OFF;
Insert into SICOES.OAUTH_CLIENT_DETAILS (CLIENT_ID,RESOURCE_IDS,CLIENT_SECRET,SCOPE,AUTHORIZED_GRANT_TYPES,WEB_SERVER_REDIRECT_URI,AUTHORITIES,ACCESS_TOKEN_VALIDITY,REFRESH_TOKEN_VALIDITY,ADDITIONAL_INFORMATION,AUTOAPPROVE) values ('app',null,'$2a$10$QU/kOtNKBdN6Hmkr.osPp.V3rKcGp9QtruuXuv2MpV8FNAQgqru3W','read,write','password,refresh_token',null,null,'1800','1800','{}',null);
REM INSERTING into SICOES.OAUTH_CLIENT_TOKEN
SET DEFINE OFF;
REM INSERTING into SICOES.OAUTH_CODE
SET DEFINE OFF;
REM INSERTING into SICOES.OAUTH_REFRESH_TOKEN
SET DEFINE OFF;
Insert into SICOES.OAUTH_REFRESH_TOKEN (TOKEN_ID) values ('79668112162191a0a897d9f1b5da5472');
REM INSERTING into SICOES.SICOES_TM_LISTADO
SET DEFINE OFF;
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('30','PAISES','Paises','Paises',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('31','MONEDA','Moneda','Moneda',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('21','ESTADO_EVALUACION','Estado de Evaluación','Estado de Evaluación',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('22','GRADO_ACADEMICO','Grado Academico','Grado Academico',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('3','TIPO_DOCUMENTO_PJ','Tipo Documento_PJ','Tipo Documento_PJ',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('5','TIPO_DOCUMENTO_PN','Tipo Documento_PN','Tipo Documento_PN',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('4','TIPO_CAPACITACION','Tipo Capacitación','Tipo Capacitación',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('8','CAUSALES_CANCELACION','Lista de Causales de Cancelación','Lista de Causales de Cancelación',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('9','SECTOR','Sector','Sector',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('6','TIPO_CAUSALES','Tipo de Causales','Tipo de Causales',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('7','CAUSALES_SUSPENSION','Lista de Causales de Suspensión','Lista de Causales de Suspensión',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('10','SUBSECTOR','SubSector','SubSector',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('11','ACTIVIDAD','Actividad','Actividad',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('12','UNIDAD','Unidad','Unidad',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('13','OTROS_REQUISITOS','Otros Requisitos','Otros Requisitos',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('14','TIPO_DOCUMENTO_ACREDITA','Tipo Documento Acredita','Tipo Documento Acredita',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('15','PAIS','Pais','Pais',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('1','ESTADO_SOLICITUD','Estado de Solicitud','Estados de Solicitud',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('2','TIPO_DOCUMENTO','Tipo Documento','Tipo Documento',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('16','ESTADO_REVISION','Estado Revision','Estado Revision',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('17','TIPO_SOLICITUD','Tipo de Solicitud','Tipo de Solicitud',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('18','RESULTADO_EVALUACION','Resultado de Evaluación','Resultado de Evaluación',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('19','ESTADO_NOTIFICACION','Estado de Notificación','Estado de Notificación',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('20','ESTADO_TOKEN','Estado de Token','Estado de Token',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('23','TIPO_EVALUADOR','Tipo de Evaluador','Tipo de Evaluador',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('24','ESTADO_ROL','Estado Rol','Estado Rol',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('25','TIPO_ARCHIVO','Tipo de archivo','Tipo de archivo',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('28','TIPO_ESTUDIO','Tipo de Estudio','Tipo de Estudio',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('29','FUENTE_ESTUDIO','Fuente de Estudio','Fuente de Estudio',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('26','OTROS_DOCUMENTOS_PN','Otros Documentos PN','Otros Documentos PN',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('27','OTROS_DOCUMENTOS_PJ','Otros Documentos PJ','Otros Documentos PJ',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('32','CUENTA_CONFORMIDAD','Cuenta Conformidad','Cuenta Conformidad',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('37','RESULTADO_EVALUACION_TEC_ADM','Resultado Evaluacion Tecnica Admnistrativa','Resultado Evaluacion Tecnica Admnistrativa',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('38','GRUPOS','Grupos','Grupos',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('36','PERFILES','Perfiles','Perfiles',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('34','RESULTADO_EVALUACION_ADM','Resultado Evaluacion Administrativa','Resultado Evaluacion Administrativa',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('35','SUBCATEGORIA','SubCategoria','SubCategoria',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('40','RESULTADO_APROBACION','Resultado Aprobacion','Resultado Aprobacion',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('39','PLAZOS','Plazos','Plazos',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO (ID_LISTADO,CD_LISTADO,NO_LISTADO,DE_LISTADO,CO_PADRE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('41','APROBADORES_ADMINISTRATIVOS','Aprobadores Administrativos','Aprobadores Administrativos',null,'SICOES',to_timestamp('13/10/22 06:46:02,883000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
REM INSERTING into SICOES.SICOES_TM_LISTADO_DETALLE
SET DEFINE OFF;
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('66','13',null,null,'OR1','1','Copia simple del documento registral vigente que consigne y acredite al representante legal de la empresa supervisora,expedido con una antigüedad no mayor de treinta(30) días calendario. ','Copia simple del documento registral vigente que consigne y acredite al representante legal de la empresa supervisora,expedido con una antigüedad no mayor de treinta(30) días calendario. ','Copia simple del documento registral vigente que consigne y acredite al representante legal de la empresa supervisora,expedido con una antigüedad no mayor de treinta(30) días calendario. ','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('67','13',null,null,'OR2','2','Instrumento público que acredite la constitución de la empresa extranjera, el mismo que deberá contar con la acreditación en el Consulado Peruano , y  de ser el caso, apostillado o legalizado por el Ministerio de Relaciones Exteriores del Perú.','Instrumento público que acredite la constitución de la empresa extranjera, el mismo que deberá contar con la acreditación en el Consulado Peruano , y  de ser el caso, apostillado o legalizado por el Ministerio de Relaciones Exteriores del Perú.','Instrumento público que acredite la constitución de la empresa extranjera, el mismo que deberá contar con la acreditación en el Consulado Peruano , y  de ser el caso, apostillado o legalizado por el Ministerio de Relaciones Exteriores del Perú.','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('68','13',null,null,'OR3','3','Registro Único de Contribuyentes (RUC).','Registro Único de Contribuyentes (RUC).','Registro Único de Contribuyentes (RUC).','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('69','13',null,null,'OR4','4','Declaración Jurada de Impedimentos.','Declaración Jurada de Impedimentos.','Declaración Jurada de Impedimentos.','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('64','14',null,null,'OTRO_REQUISITO','2','Otros Requisitos','Otros Requisitos','Otros Requisitos','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('91','14',null,null,'PERFIL','3','Perfil','Perfil','Perfil','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('65','15',null,null,'PERU','1','Perú','Perú','Perú','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('527','15',null,null,'BOLIVIA','2','Bolivia','Bolivia','Bolivia','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('528','15',null,null,'ARGENTINA','3','Argentina','Argentina','Argentina','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('529','15',null,null,'BRASIL','4','Brasil','Brasil','Brasil','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('530','15',null,null,'CHILE','5','Chile','Chile','Chile','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('531','15',null,null,'COLOMBIA','6','Colombia','Colombia','Colombia','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('532','15',null,null,'COSTA_RICA','7','Costa Rica','Costa Rica','Costa Rica','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('533','15',null,null,'ECUADOR','8','Ecuador','Ecuador','Ecuador','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('534','15',null,null,'URUGUAY','9','Uruguay','Uruguay','Uruguay','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('535','15',null,null,'VENEZUELA','10','Venezuela','Venezuela','Venezuela','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('536','15',null,null,'USA','11','EEUU','EEUU','EEUU','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('537','15',null,null,'ESPANIA','12','España','España','España','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('73','16',null,null,'POR_ASIGNAR','1','Por Asignar','Por Asignar','Por Asignar','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('74','16',null,null,'ASIGNADO','1','Asignado','Asignado','Asignado','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('75','16',null,null,'EN_PROCESO','2','En Proceso','En Proceso','En Proceso','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('76','16',null,null,'OBSERVADO','3','Observado','Observado','Observado','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('77','16',null,null,'CONCLUIDO','4','Concluido','Concluido','Concluido','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('563','16',null,null,'EN_APROBACION','4','En Aprobación','En Aprobación','En Aprobación','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('83','17',null,null,'INSCRIPCION','1','Inscripción','Inscripción','Inscripción','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('84','17',null,null,'ACTUALIZACION','1','Actualización','Actualización','Actualización','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('85','17',null,null,'MODIFICACION','1','Modificación','Modificación','Modificación','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('86','17',null,null,'SUSPENCION','1','Suspención','Suspención','Suspención','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('87','17',null,null,'CANCELACION','1','Cancelación','Cancelación','Cancelación','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('78','18',null,null,'RE_01','1','Por Evaluar','Por Evaluar','Por Evaluar','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('79','18',null,null,'RE_02','2','Cumple','Cumple','Cumple','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('80','18',null,null,'RE_03','3','No Cumple','No Cumple','No Cumple','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('81','18',null,null,'RE_04','4','Observado','Observado','Observado','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('82','18',null,null,'RE_06','5','No Aplica','No Aplica','No Aplica','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('88','19',null,null,'PENDIENTE','1','Pendiente','Pendiente','Pendiente','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('89','19',null,null,'ENVIADO','2','Enviado','Enviado','Enviado','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('90','19',null,null,'FALLADO','3','Fallado','Fallado','Fallado','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('97','21',null,null,'POR_ASIGNAR_EVA','1','Por Asignar','Por Asignar','Por Asignar','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('98','21',null,null,'ASIGNADO_EVA','1','Asignado','Asignado','Asignado','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('99','21',null,null,'EN_PROCESO_EVA','2','En Proceso','En Proceso','En Proceso','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('100','21',null,null,'CONCLUIDO_EVA','4','Concluido','Concluido','Concluido','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('92','22',null,null,'DOCTORADO','1','Doctorado','Doctorado','D','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('93','22',null,null,'MAESTRIA','2','Maestria','Maestria','M','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('94','22',null,null,'TITULADO','3','Titulado','Titulado','T','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('95','22',null,null,'BACHILLER','4','Bachiller','Bachiller','B','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('96','22',null,null,'TECNICO','5','Técnico','Técnico','TC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('101','23',null,null,'ADMINISTRATIVO','1','Administrativo','Administrativo','Administrativo','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('102','23',null,null,'TECNICO','2','Tecnico','Tecnico','Tecnico','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('544','23',null,null,'APROBADOR_TECNICO','3','Aprobador Tecnico','Aprobador Tecnico','Aprobador Tecnico','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('562','23',null,null,'APROBADOR_ADMINISTRATIVO','4','Aprobador Administrativo','Aprobador Administrativo','Aprobador Administrativo','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('103','24',null,null,'ACTIVO','1','Activo','Activo','Activo','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('104','24',null,null,'INACTIVO','1','Inactivo','Inactivo','Inactivo','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('105','25',null,null,'TA01','1','Archivo de Grado.','Archivo de Grado.','Grado_Academico','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('106','25',null,null,'TA02','2','Archivo de la Colegiatura.','Colegiatura','Grado_Academico','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('107','25',null,null,'TA03','3','Copia de DNI.','Copia de DNI.','DNI','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('108','25',null,null,'TA04','4','Registro único de contribuyente.','Registro único de contribuyente.','RUC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('109','25',null,null,'TA05','5','Declaración jurada de impedimentos.','Declaración jurada de impedimentos.','DJ','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('110','25',null,null,'TA06','6','Copia Documento Registral.','Copia Documento Registral.','Copia Documento Registral.','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('111','25',null,null,'TA07','7','Acreditación de constitución extranjera.','Acreditación de constitución extranjera.','Acreditacion de constitucion extranjera.','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('112','25',null,null,'TA08','8','Archivo de experiencia.','Archivo de experiencia.','Experiencia','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('113','25',null,null,'TA09','9','Archivo de capacitación.','Archivo de capacitación.','Capacitacion','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('144','25',null,null,'TA10','10','Archivo de evidencia','Archivo de evidencia','Archivo de evidencia','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('545','25',null,null,'TA11','11','Archivo Formato','Archivo Formato','Archivo Formato','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('567','25',null,null,'TA12','12','Archivo Subsanacion','Archivo Subsanacion','Archivo Subsanacion','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('568','25',null,null,'TA13','13','Archivo Resultado','Archivo Resultado','Archivo Resultado','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('114','26',null,null,'OPN01','1','Copia de Dni o CE','Copia de Dni o CE','Copia de Dni o CE','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('115','26',null,null,'OPN02','2','Registro Único de Contribuyentes(RUC)','Registro Único de Contribuyentes','Registro Único de Contribuyentes','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('116','26',null,null,'OPN03','3','Declaración Jurada de Impedimentos','Declaración Jurada de Impedimentos','Declaración Jurada de Impedimentos','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('117','27',null,null,'OPJ01','1','Copia simple del documento registral vigente que consigne y acredite al representante legal de la empresa supervisora,espedido con una antigüedad no mayor de treinta{30} dias calendario.','Copia simple del documento registral vigente que consigne y acredite al representante legal de la empresa supervisora,espedido con una antigûedad no mayor de treinta{30} dias calendario.','Copia simple del documento registral vigente que consigne y acredite al representante legal de la empresa supervisora,espedido con una antigûedad no mayor de treinta{30} dias calendario.','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('118','27',null,null,'OPJ02','2','Instrumento Público que acredite la constitución de la empresa extranjera, el mismo que deberá contar con la acreditación en el Consulado Peruano y, de ser el caso, apostillado o legalizado por el Ministerio de Relaciones Exteriores del Perú.','Instrumento Público que acredite la constitución de la empresa extranjera, el mismo que deberá contar con la acreditación en el Consulado Peruano y, de ser el caso, apostillado o legalizado por el Ministerio de Relaciones Exteriores del Perú.','Instrumento Público que acredite la constitución de la empresa extranjera, el mismo que deberá contar con la acreditación en el Consulado Peruano y, de ser el caso, apostillado o legalizado por el Ministerio de Relaciones Exteriores del Perú.','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('119','27',null,null,'OPJ03','3','Registro Único de Contribuyentes(RUC)','Registro Único de Contribuyentes','Registro Único de Contribuyentes','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('120','27',null,null,'OPJ04','4','Declaración Jurada de Impedimentos','Declaración Jurada de Impedimentos','Declaración Jurada de Impedimentos','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('121','28',null,null,'ACADEMICOS','1','Academicos','Academicos','Academicos','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('122','28',null,null,'CAPACITACION','2','Capacitación','Capacitación','Capacitación','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('123','29',null,null,'SUNEDU','1','Sunedu','Sunedu','Sunedu','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('124','29',null,null,'MANUAL','2','Manual','Manual','Manual','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('125','30',null,null,'PERU','1','Perú','Perú','Perú','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('126','31',null,null,'SOLES','1','Soles','Soles','Soles','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('127','31',null,null,'DOLARES','2','Dolares','Dolares','Dolares','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('128','32',null,null,'SI','1','Si','Si','Si','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('129','32',null,null,'NO','2','No','No','No','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('141','34',null,null,'REA_01','1','Califica','Califica','Califica','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('142','34',null,null,'REA_02','2','No Califica','No Califica','No Califica','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('143','34',null,null,'REA_03','3','Observado','Observado','Observado','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('190','35','11','39','SC_35','35','Procesamiento','Procesamiento','Procesamiento','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('191','35','11','39','SC_36','36','Producción','Producción','Producción','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('192','35','11','40','SC_37','37','Contratos y Asuntos Regulatorios','Contratos y Asuntos Regulatorios','Contratos y Asuntos Regulatorios','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('193','35','11','40','SC_38','38','FISE y Contabilidad Regulatoria','FISE y Contabilidad Regulatoria','FISE y Contabilidad Regulatoria','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('194','35','11','40','SC_39','39','Legal','Legal','Legal','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('195','35','11','41','SC_40','40','Transporte','Transporte','Transporte','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('187','35','11','55','SC_32','32','Inversiones en Electricidad','Inversiones en Electricidad','Inversiones en Electricidad','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('188','35','11','56','SC_33','33','Fiscalización de Generación y Transmisión Eléctrica','Fiscalización de Generación y Transmisión Eléctrica','Fiscalización de Generación y Transmisión Eléctrica','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('189','35','11','56','SC_34','34','Generación Eléctrica y COES','Generación Eléctrica y COES','Generación Eléctrica y COES','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('196','35','11','58','SC_41','41','Geomecánica','Geomecánica','Geomecánica','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('197','35','11','58','SC_42','42','Transporte, maquinaria y servicios auxiliares','Transporte, maquinaria y servicios auxiliares','Transporte, maquinaria y servicios auxiliares','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('198','35','11','58','SC_43','43','Ventilación','Ventilación','Ventilación','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('199','35','11','59','SC_44','44','Geotecnia','Geotecnia','Geotecnia','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('156','35','12','145','SC_01','1','Calidad - Fuerza Mayor','Calidad - Fuerza Mayor','Calidad - Fuerza Mayor','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('157','35','12','145','SC_02','2','Calidad - Interrupciones','Calidad - Interrupciones','Calidad - Interrupciones','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('158','35','12','145','SC_03','3','Calidad - NTCSE','Calidad - NTCSE','Calidad - NTCSE','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('159','35','12','145','SC_04','4','Comercialización','Comercialización','Comercialización','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('160','35','12','145','SC_05','5','Distribución','Distribución','Distribución','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('161','35','12','145','SC_06','6','Economía','Economía','Economía','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('162','35','12','145','SC_07','7','Fiscalización del Fondo de Inclusión Social Energético (FISE)','Fiscalización del Fondo de Inclusión Social Energético (FISE)','Fiscalización del Fondo de Inclusión Social Energético (FISE)','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('163','35','12','145','SC_08','8','Legal','Legal','Legal','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('164','35','12','146','SC_09','9','Supervisión de comercialización de distribución de gas natural por red de ductos','Supervisión de comercialización de distribución de gas natural por red de ductos','Supervisión de comercialización de distribución de gas natural por red de ductos','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('165','35','12','146','SC_10','10','Supervisión de la calidad del servicio de distribución de gas natural por red de ductos','Supervisión de la calidad del servicio de distribución de gas natural por red de ductos','Supervisión de la calidad del servicio de distribución de gas natural por red de ductos','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('166','35','12','146','SC_11','11','Supervisión de la contabilidad regulatoria de la empresa concesionaria de distribución de gas natural por red de ductos','Supervisión de la contabilidad regulatoria de la empresa concesionaria de distribución de gas natural por red de ductos','Supervisión de la contabilidad regulatoria de la empresa concesionaria de distribución de gas natural por red de ductos','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('167','35','12','146','SC_12','12','Supervisión de la distribución de gas natural por red de ductos','Supervisión de la distribución de gas natural por red de ductos','Supervisión de la distribución de gas natural por red de ductos','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('168','35','12','146','SC_13','13','Supervisión de los planes de inversión de las empresas concesionarias de distribución','Supervisión de los planes de inversión de las empresas concesionarias de distribución','Supervisión de los planes de inversión de las empresas concesionarias de distribución','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('169','35','12','146','SC_14','14','Supervisión de los proyectos de masificación de instalaciones de redes de distribución de gas natural','Supervisión de los proyectos de masificación de instalaciones de redes de distribución de gas natural','Supervisión de los proyectos de masificación de instalaciones de redes de distribución de gas natural','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('170','35','12','146','SC_15','15','Supervisión para el otorgamiento y actualización del registro de instaladores de gas natural','Supervisión para el otorgamiento y actualización del registro de instaladores de gas natural','Supervisión para el otorgamiento y actualización del registro de instaladores de gas natural','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('171','35','12','147','SC_16','16','Plantas Envasadoras de GLP','Plantas Envasadoras de GLP','Plantas Envasadoras de GLP','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('172','35','12','148','SC_17','17','Supervisión Comercial','Supervisión Comercial','Supervisión Comercial','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('173','35','12','149','SC_18','18','Administración y Otorgamiento del Registro de Hidrocarburos(CL, GLP, GNV, GNC,GNL y OPDH)','Administración y Otorgamiento del Registro de Hidrocarburos(CL, GLP, GNV, GNC,GNL y OPDH)','Administración y Otorgamiento del Registro de Hidrocarburos(CL, GLP, GNV, GNC,GNL y OPDH)','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('174','35','12','149','SC_19','19','Legal','Legal','Legal','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('175','35','12','149','SC_20','20','Supervisión de comercialización de hidrocarburos (CL, GLP, GNV, GNC y GNL)','Supervisión de comercialización de hidrocarburos (CL, GLP, GNV, GNC y GNL)','Supervisión de comercialización de hidrocarburos (CL, GLP, GNV, GNC y GNL)','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('176','35','12','149','SC_21','21','Supervisión de instalaciones y medios de transporte (CL, GLP y OPDH)','Supervisión de instalaciones y medios de transporte (CL, GLP y OPDH)','Supervisión de instalaciones y medios de transporte (CL, GLP y OPDH)','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('177','35','12','149','SC_22','22','Supervisión de instalaciones y medios de transporte (GNV, GNC y GNL)','Supervisión de instalaciones y medios de transporte (GNV, GNC y GNL)','Supervisión de instalaciones y medios de transporte (GNV, GNC y GNL)','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('178','35','12','149','SC_23','23','Supervisión de la calidad y cantidad de combustibles','Supervisión de la calidad y cantidad de combustibles','Supervisión de la calidad y cantidad de combustibles','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('179','35','12','150','SC_24','24','Exploración y explotación','Exploración y explotación','Exploración y explotación','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('180','35','12','151','SC_25','25','Legal','Legal','Legal','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('181','35','12','152','SC_26','26','Fiscalización del Fondo de Inclusión Social Energético (FISE) / Fiscalización del Fondo de Estabilización de los Precios de los Combustibles derivados del Petróleo(FEPC)','Fiscalización del Fondo de Inclusión Social Energético (FISE) / Fiscalización del Fondo de Estabilización de los Precios de los Combustibles derivados del Petróleo(FEPC)','Fiscalización del Fondo de Inclusión Social Energético (FISE) / Fiscalización del Fondo de Estabilización de los Precios de los Combustibles derivados del Petróleo(FEPC)','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('182','35','12','153','SC_27','27','Fiscalización de Proyectos Mayores','Fiscalización de Proyectos Mayores','Fiscalización de Proyectos Mayores','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('183','35','12','154','SC_28','28','Plantas y Refinerías','Plantas y Refinerías','Plantas y Refinerías','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('184','35','12','155','SC_29','29','Consumidor directo','Consumidor directo','Consumidor directo','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('185','35','12','155','SC_30','30','Diseño, construcción, operación y mantenimiento / Instrumentos de Gestión de Seguridad','Diseño, construcción, operación y mantenimiento / Instrumentos de Gestión de Seguridad','Diseño, construcción, operación y mantenimiento / Instrumentos de Gestión de Seguridad','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('186','35','12','155','SC_31','31','Transporte Marítimo','Transporte Marítimo','Transporte Marítimo','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('200','36','35','156','PER_01','1','Perfil S1A_DEM','Perfil S1A_DEM','Perfil S1A_DEM','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('201','36','35','156','PER_02','2','Perfil S1B_DEM','Perfil S1B_DEM','Perfil S1B_DEM','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('202','36','35','156','PER_03','3','Perfil S2_DEM','Perfil S2_DEM','Perfil S2_DEM','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('203','36','35','156','PER_04','4','Perfil S3A_DEM','Perfil S3A_DEM','Perfil S3A_DEM','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('204','36','35','156','PER_05','5','Perfil S3B_DEM','Perfil S3B_DEM','Perfil S3B_DEM','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('205','36','35','156','PER_06','6','Perfil S4A_DEM','Perfil S4A_DEM','Perfil S4A_DEM','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('206','36','35','156','PER_07','7','Perfil S4B_DEM','Perfil S4B_DEM','Perfil S4B_DEM','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('207','36','35','157','PER_08','8','Perfil S1A_DEI','Perfil S1A_DEI','Perfil S1A_DEI','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('208','36','35','157','PER_09','9','Perfil S1B_DEI','Perfil S1B_DEI','Perfil S1B_DEI','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('209','36','35','157','PER_10','10','Perfil S2_DEI','Perfil S2_DEI','Perfil S2_DEI','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('210','36','35','157','PER_11','11','Perfil S3A_DEI','Perfil S3A_DEI','Perfil S3A_DEI','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('211','36','35','157','PER_12','12','Perfil S3B_DEI','Perfil S3B_DEI','Perfil S3B_DEI','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('212','36','35','157','PER_13','13','Perfil S4A_DEI','Perfil S4A_DEI','Perfil S4A_DEI','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('213','36','35','157','PER_14','14','Perfil S4B_DEI','Perfil S4B_DEI','Perfil S4B_DEI','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('214','36','35','158','PER_15','15','Perfil S1A_DEN','Perfil S1A_DEN','Perfil S1A_DEN','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('215','36','35','158','PER_16','16','Perfil S1B_DEN','Perfil S1B_DEN','Perfil S1B_DEN','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('216','36','35','158','PER_17','17','Perfil S2_DEN','Perfil S2_DEN','Perfil S2_DEN','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('217','36','35','158','PER_18','18','Perfil S3A_DEN','Perfil S3A_DEN','Perfil S3A_DEN','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('218','36','35','158','PER_19','19','Perfil S3B_DEN','Perfil S3B_DEN','Perfil S3B_DEN','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('219','36','35','158','PER_20','20','Perfil S4A_DEN','Perfil S4A_DEN','Perfil S4A_DEN','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('220','36','35','158','PER_21','21','Perfil S4B_DEN','Perfil S4B_DEN','Perfil S4B_DEN','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('221','36','35','159','PER_22','22','Perfil S1A_DEC','Perfil S1A_DEC','Perfil S1A_DEC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('222','36','35','159','PER_23','23','Perfil S1B_DEC','Perfil S1B_DEC','Perfil S1B_DEC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('223','36','35','159','PER_24','24','Perfil S2_DEC','Perfil S2_DEC','Perfil S2_DEC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('224','36','35','159','PER_25','25','Perfil S3A_DEC','Perfil S3A_DEC','Perfil S3A_DEC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('225','36','35','159','PER_26','26','Perfil S3B_DEC','Perfil S3B_DEC','Perfil S3B_DEC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('226','36','35','159','PER_27','27','Perfil S4A_DEC','Perfil S4A_DEC','Perfil S4A_DEC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('227','36','35','159','PER_28','28','Perfil S4B_DEC','Perfil S4B_DEC','Perfil S4B_DEC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('1','1',null,null,'BORRADOR','1','Borrador','Borrador','Borrador','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('70','1',null,null,'EN_PROCESO','1','En Proceso','En Proceso','Borrador','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('71','1',null,null,'OBSERVADO','1','Observado','Observado','Borrador','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('72','1',null,null,'CONCLUIDO','1','Concluido','Concluido','Borrador','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('2','2',null,null,'RUC','1','RUC','RUC','1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('3','2',null,null,'DNI','2','DNI','DNI','2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('546','2',null,null,'CARNET_EXTRA','3','Carnet de Extranjería','Carnet de Extranjería','3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('4','3',null,null,'CONTRATO','1','Contrato','Contrato','Contrato','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('5','3',null,null,'ORDEN_SERVICIO','2','Orden de Servicio','Orden de Servicio','Orden de Servicio','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('6','3',null,null,'COMPROBANTE_PAGO','3','Comprobante de Pago','Comprobante de Pago','Comprobante de Pago','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('7','4',null,null,'DIPLOMADO','1','Diplomado','Diplomado','Diplomado','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('8','4',null,null,'CURSO','2','Curso','Curso','Curso','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('9','4',null,null,'SEMINARIO','3','Seminario','Seminario','Seminario','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('10','4',null,null,'TALLER','4','Taller','Taller','Taller','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('11','4',null,null,'CERTIFICACION','5','Certificación','Certificación','Certificación','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('12','4',null,null,'ACREDITACION','6','Acreditación','Acreditación','Acreditación','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('13','4',null,null,'OTRO','7','Otro','Otro','Otro','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('14','5',null,null,'CONTRATO','1','Contrato','Contrato','Contrato','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('15','5',null,null,'CONSTANCIA','2','Constancia','Constancia','Constancia','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('16','5',null,null,'CERTIFICADO','3','Certificado','Certificado','Certificado','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('17','6',null,null,'DE_PARTE','1','De Parte','De Parte','De Parte','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('18','6',null,null,'DE_OFICIO','2','De Oficio','De Oficio','De Oficio','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('19','7','6','17','CS01','1','a)Decisión unilateral de la empresa supervisora inscrita.','a)Decisión unilateral de la empresa supervisora inscrita.','a)Decisión unilateral de la empresa supervisora inscrita.','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('20','7','6','18','CS02','2','b)Participar como profesional o técnico propuesto para más de una Empresa Supervisora – persona jurídica en procesos de selección que sean convocados de forma simultánea.','b)Participar como profesional o técnico propuesto para más de una Empresa Supervisora – persona jurídica en procesos de selección que sean convocados de forma simultánea.','b)Participar como profesional o técnico propuesto para más de una Empresa Supervisora – persona jurídica en procesos de selección que sean convocados de forma simultánea.','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('21','7','6','18','CS03','3','c)Laborar bajo cualquier modalidad contractual para agentes fiscalizados por Osinergmin.','c)Laborar bajo cualquier modalidad contractual para agentes fiscalizados por Osinergmin.','c)Laborar bajo cualquier modalidad contractual para agentes fiscalizados por Osinergmin.','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('22','7','6','18','CS04','4','d)Vulneración de las disposiciones en materia de confidencialidad previstas en el presente Reglamento.','d)Vulneración de las disposiciones en materia de confidencialidad previstas en el presente Reglamento.','d)Vulneración de las disposiciones en materia de confidencialidad previstas en el presente Reglamento.','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('23','7','6','18','CS04','5','e)Resolución de contrato por incumplimiento de obligaciones de la empresa supervisora contratada. Incluye aquellas resoluciones de contrato por acumulación de penalidades','e)Resolución de contrato por incumplimiento de obligaciones de la empresa supervisora contratada. Incluye aquellas resoluciones de contrato por acumulación de penalidades','e)Resolución de contrato por incumplimiento de obligaciones de la empresa supervisora contratada. Incluye aquellas resoluciones de contrato por acumulación de penalidades','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('24','7','6','18','CS06','6','f)La pérdida de la designación a consecuencia de la no suscripción oportuna del contrato de supervisión, por causa atribuible a la empresa supervisora designada.','f)La pérdida de la designación a consecuencia de la no suscripción oportuna del contrato de supervisión, por causa atribuible a la empresa supervisora designada.','f)La pérdida de la designación a consecuencia de la no suscripción oportuna del contrato de supervisión, por causa atribuible a la empresa supervisora designada.','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('25','7','6','18','CS07','7','g)Presentar información falsa, adulterada o inexacta para su inscripción en el Registro, Proceso de Selección, Perfeccionamiento de contrato y/o ejecución contractual; independientemente de la responsabilidad administrativa y penal que corresponda. Para ello se evaluará si la falta corresponde a información presentada por la persona jurídica, persona natural o profesional o técnico propuesto, según sea el caso.','g)Presentar información falsa, adulterada o inexacta para su inscripción en el Registro, Proceso de Selección, Perfeccionamiento de contrato y/o ejecución contractual; independientemente de la responsabilidad administrativa y penal que corresponda. Para ello se evaluará si la falta corresponde a información presentada por la persona jurídica, persona natural o profesional o técnico propuesto, según sea el caso.','g)Presentar información falsa, adulterada o inexacta para su inscripción en el Registro, Proceso de Selección, Perfeccionamiento de contrato y/o ejecución contractual; independientemente de la responsabilidad administrativa y penal que corresponda. Para ello se evaluará si la falta corresponde a información presentada por la persona jurídica, persona natural o profesional o técnico propuesto, según sea el caso.','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('26','7','6','18','CS08','8','h)Por actos de corrupción comprobados.','h)Por actos de corrupción comprobados.','h)Por actos de corrupción comprobados.','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('27','7','6','18','CS09','9','i)Declaración de nulidad del contrato de supervisión, por los supuestos indicados en el artículo 37 de la presente Directiva.','i)Declaración de nulidad del contrato de supervisión, por los supuestos indicados en el artículo 37 de la presente Directiva.','i)Declaración de nulidad del contrato de supervisión, por los supuestos indicados en el artículo 37 de la presente Directiva.','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('31','8','6','17','CC04','4','d)Decisión unilateral de la empresa.','d)Decisión unilateral de la empresa.','d)Decisión unilateral de la empresa.','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('28','8','6','18','CC01','1','a)Fallecimiento.','a)Fallecimiento.','a)Fallecimiento.','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('29','8','6','18','CC02','2','b)En caso de las personas jurídicas, haber sido declarada en quiebra o insolvencia o tener procedimiento iniciado o sobreseído de tal naturaleza.','b)En caso de las personas jurídicas, haber sido declarada en quiebra o insolvencia o tener procedimiento iniciado o sobreseído de tal naturaleza.','b)En caso de las personas jurídicas, haber sido declarada en quiebra o insolvencia o tener procedimiento iniciado o sobreseído de tal naturaleza.','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('228','36','35','160','PER_29','29','Perfil S1A_DED','Perfil S1A_DED','Perfil S1A_DED','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('229','36','35','160','PER_30','30','Perfil S1B_DED','Perfil S1B_DED','Perfil S1B_DED','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('30','8','6','18','CC03','3','c)Extinción de la persona jurídica.','c)Extinción de la persona jurídica.','c)Extinción de la persona jurídica.','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('32','9',null,null,'SECTOR_ENERGETICO','1','Sector Energético','Sector Energético','Sector Energetico','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('33','9',null,null,'SECTOR_MINERO','2','Sector Minero','Sector Minero','Sector Minero','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('34','10','9','32','GAS_NATURAL','1','Sub Sector de Hirocarburos - Gas Natural','Sub Sector de Hirocarburos - Gas Natural','Sub Sector de Hirocarburos - Gas Natural','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('35','10','9','32','HIDROCARBURO_LIQUIDO','2','Sub Sector de Hirocarburos - Hidrocarburos Líquidos','Sub Sector de Hirocarburos - Hidrocarburos Líquidos','Sub Sector de Hirocarburos - Hidrocarburos Líquidos','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('36','10','9','32','ELECTRICIDAD','3','Sub Sector de Electricidad','Sub Sector de Electricidad','Sub Sector de Electricidad','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('37','10','9','33','SUB_MINERO','1','Sector Minero','Sector Minero','Sector Minero','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('38','11','10','34','GS01','1','Distribución por red de ductos','Distribución por red de ductos','Distribución por red de ductos','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('39','11','10','34','GS02','2','Explotación y Producción / Procesamiento / Almacenamiento','Explotación y Producción / Procesamiento / Almacenamiento','Explotación y Producción / Procesamiento / Almacenamiento','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('40','11','10','34','GS03','3','Otras actividades legalmente atribuidas a Osinergmin','Otras actividades legalmente atribuidas a Osinergmin','Otras actividades legalmente atribuidas a Osinergmin','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('41','11','10','34','GS04','4','Transporte por red de ductos','Transporte por red de ductos','Transporte por red de ductos','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('46','11','10','35','HL01','1','Almacenamiento','Almacenamiento','Almacenamiento','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('47','11','10','35','HL02','2','Distribución y Comercialización','Distribución y Comercialización','Distribución y Comercialización','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('48','11','10','35','HL03','3','Exploración / Explotación y Producción','Exploración / Explotación y Producción','Exploración / Explotación y Producción','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('49','11','10','35','HL04','4','Expoloración / Explotación /Procesamiento y refinación /Almacenamiento /Transporte /Distribución y Comercialización /Otras legalmente atribuidas a Osinergmin','Expoloración / Explotación /Procesamiento y refinación /Almacenamiento /Transporte /Distribución y Comercialización /Otras legalmente atribuidas a Osinergmin','Expoloración / Explotación /Procesamiento y refinación /Almacenamiento /Transporte /Distribución y Comercialización /Otras legalmente atribuidas a Osinergmin','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('50','11','10','35','HL05','5','Otras legalmente atribuidas a Osinergmin','Otras legalmente atribuidas a Osinergmin','Otras legalmente atribuidas a Osinergmin','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('51','11','10','35','HL06','6','Procesamiento y Refinación','Procesamiento y Refinación','Procesamiento y Refinación','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('52','11','10','35','HL07','7','Procesamiento y Refinación / Almacenamiento','Procesamiento y Refinación / Almacenamiento','Procesamiento y Refinación / Almacenamiento','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('53','11','10','35','HL08','8','Transporte','Transporte','Transporte','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('54','11','10','36','E02','1','Distribución y comercialización de electricidad','Distribución y comercialización de electricidad','Distribución y comercialización de electricidad','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('55','11','10','36','E03','2','Generación de Electricidad / Transmisión de Electricidad','Generación de Electricidad / Transmisión de Electricidad','Generación de Electricidad / Transmisión de Electricidad','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('56','11','10','36','E04','3','Generación de Electricidad / Transmisión de Electricidad / Planificación, programación y despacho económico del SEIN','Generación de Electricidad / Transmisión de Electricidad / Planificación, programación y despacho económico del SEIN','Generación de Electricidad / Transmisión de Electricidad / Planificación, programación y despacho económico del SEIN','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('57','11','10','36','E05','4','Transmisión de Electricidad','Transmisión de Electricidad','Transmisión de Electricidad','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('58','11','9','37','M01','1','Exploración / Explotación','Exploración / Explotación','Exploración / Explotación','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('59','11','9','37','M02','2','Explotación / Beneficio','Explotación / Beneficio','Explotación / Beneficio','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('146','12','11','38','UN_02','2','Unidad Técnica de Gas Natural (UTGN)','Unidad Técnica de Gas Natural (UTGN)','Unidad Técnica de Gas Natural (UTGN)','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('147','12','11','46','UN_03','3','Unidad de Supervisión de Plantas Envasadoras e Importadores(USEI)','Unidad de Supervisión de Plantas Envasadoras e Importadores(USEI)','Unidad de Supervisión de Plantas Envasadoras e Importadores(USEI)','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('148','12','11','47','UN_04','4','Unidad de Supervisión de Plantas y Refinerías(USPR)','Unidad de Supervisión de Plantas y Refinerías(USPR)','Unidad de Supervisión de Plantas y Refinerías(USPR)','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('149','12','11','47','UN_05','5','Unidad Técnica de Hidrocarburos Líquidos(UTHL)','Unidad Técnica de Hidrocarburos Líquidos(UTHL)','Unidad Técnica de Hidrocarburos Líquidos(UTHL)','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('150','12','11','48','UN_06','6','Unidad de Supervisión de Exploración y Explotación(USEE)','Unidad de Supervisión de Exploración y Explotación(USEE)','Unidad de Supervisión de Exploración y Explotación(USEE)','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('151','12','11','49','UN_07','7','Área Legal de la DSHL','Área Legal de la DSHL','Área Legal de la DSHL','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('152','12','11','50','UN_08','8','Unidad de Supervisión de Plantas Envasadoras e Importadores(USEI)','Unidad de Supervisión de Plantas Envasadoras e Importadores(USEI)','Unidad de Supervisión de Plantas Envasadoras e Importadores(USEI)','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('153','12','11','51','UN_09','9','Unidad de Supervisión de Plantas y Refinerías(USPR)','Unidad de Supervisión de Plantas y Refinerías(USPR)','Unidad de Supervisión de Plantas y Refinerías(USPR)','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('154','12','11','52','UN_10','10','Unidad de Supervisión de Plantas y Refinerías(USPR)','Unidad de Supervisión de Plantas y Refinerías(USPR)','Unidad de Supervisión de Plantas y Refinerías(USPR)','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('155','12','11','53','UN_11','11','Unidad de Transporte Marítimo y Ductos de Hidrocarburos Líquidos(USTD)','Unidad de Transporte Marítimo y Ductos de Hidrocarburos Líquidos(USTD)','Unidad de Transporte Marítimo y Ductos de Hidrocarburos Líquidos(USTD)','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('145','12','11','54','UN_01','1','Unidad Técnica de Electricidad (UTE)','Unidad Técnica de Electricidad (UTE)','Unidad Técnica de Electricidad (UTE)','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('230','36','35','160','PER_31','31','Perfil S2_DED','Perfil S2_DED','Perfil S2_DED','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('231','36','35','160','PER_32','32','Perfil S3A_DED','Perfil S3A_DED','Perfil S3A_DED','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('232','36','35','160','PER_33','33','Perfil S3B_DED','Perfil S3B_DED','Perfil S3B_DED','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('233','36','35','160','PER_34','34','Perfil S4A_DED','Perfil S4A_DED','Perfil S4A_DED','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('234','36','35','160','PER_35','35','Perfil S4B_DED','Perfil S4B_DED','Perfil S4B_DED','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('235','36','35','161','PER_36','36','Perfil S1A_DEE','Perfil S1A_DEE','Perfil S1A_DEE','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('236','36','35','161','PER_37','37','Perfil S1B_DEE','Perfil S1B_DEE','Perfil S1B_DEE','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('237','36','35','161','PER_38','38','Perfil S2_DEE','Perfil S2_DEE','Perfil S2_DEE','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('238','36','35','161','PER_39','39','Perfil S3A_DEE','Perfil S3A_DEE','Perfil S3A_DEE','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('239','36','35','161','PER_40','40','Perfil S3B_DEE','Perfil S3B_DEE','Perfil S3B_DEE','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('240','36','35','161','PER_41','41','Perfil S4A_DEE','Perfil S4A_DEE','Perfil S4A_DEE','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('241','36','35','161','PER_42','42','Perfil S4B_DEE','Perfil S4B_DEE','Perfil S4B_DEE','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('242','36','35','162','PER_43','43','Perfil S2_DEF-1','Perfil S2_DEF-1','Perfil S2_DEF-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('243','36','35','162','PER_44','44','Perfil S2_DEF-2','Perfil S2_DEF-2','Perfil S2_DEF-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('244','36','35','162','PER_45','45','Perfil S3A_DEF','Perfil S3A_DEF','Perfil S3A_DEF','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('245','36','35','162','PER_46','46','Perfil S3B_DEF','Perfil S3B_DEF','Perfil S3B_DEF','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('246','36','35','162','PER_47','47','Perfil S4A_DEF','Perfil S4A_DEF','Perfil S4A_DEF','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('247','36','35','162','PER_48','48','Perfil S4B_DEF','Perfil S4B_DEF','Perfil S4B_DEF','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('248','36','35','163','PER_49','49','Perfil S1A_DEL','Perfil S1A_DEL','Perfil S1A_DEL','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('249','36','35','163','PER_50','50','Perfil S1B_DEL','Perfil S1B_DEL','Perfil S1B_DEL','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('250','36','35','163','PER_51','51','Perfil S2_DEL','Perfil S2_DEL','Perfil S2_DEL','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('251','36','35','163','PER_52','52','Perfil S3A_DEL','Perfil S3A_DEL','Perfil S3A_DEL','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('252','36','35','163','PER_53','53','Perfil S3B_DEL','Perfil S3B_DEL','Perfil S3B_DEL','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('253','36','35','163','PER_54','54','Perfil S4A_DEL','Perfil S4A_DEL','Perfil S4A_DEL','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('254','36','35','163','PER_55','55','Perfil S4B_DEL','Perfil S4B_DEL','Perfil S4B_DEL','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('313','36','35','164','PER_114','114','Perfil S1B_DGC-1','Perfil S1B_DGC-1','Perfil S1B_DGC-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('314','36','35','164','PER_115','115','Perfil S1B_DGC-2','Perfil S1B_DGC-2','Perfil S1B_DGC-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('315','36','35','164','PER_116','116','Perfil S2_DGC-1','Perfil S2_DGC-1','Perfil S2_DGC-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('316','36','35','164','PER_117','117','Perfil S2_DGC-2','Perfil S2_DGC-2','Perfil S2_DGC-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('317','36','35','164','PER_118','118','Perfil S2_DGC-3','Perfil S2_DGC-3','Perfil S2_DGC-3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('318','36','35','164','PER_119','119','Perfil S3A_DGC-1','Perfil S3A_DGC-1','Perfil S3A_DGC-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('319','36','35','164','PER_120','120','Perfil S3A_DGC-2','Perfil S3A_DGC-2','Perfil S3A_DGC-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('320','36','35','164','PER_121','121','Perfil S3B_DGC-1','Perfil S3B_DGC-1','Perfil S3B_DGC-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('321','36','35','164','PER_122','122','Perfil S3B_DGC-2','Perfil S3B_DGC-2','Perfil S3B_DGC-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('322','36','35','164','PER_123','123','Perfil S3B_DGC-3','Perfil S3B_DGC-3','Perfil S3B_DGC-3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('323','36','35','164','PER_124','124','Perfil S4A_DGC','Perfil S4A_DGC','Perfil S4A_DGC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('324','36','35','164','PER_125','125','Perfil S4B_DGC','Perfil S4B_DGC','Perfil S4B_DGC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('325','36','35','165','PER_126','126','Perfil S1B_DGS-1','Perfil S1B_DGS-1','Perfil S1B_DGS-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('326','36','35','165','PER_127','127','Perfil S1B_DGS-2','Perfil S1B_DGS-2','Perfil S1B_DGS-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('327','36','35','165','PER_128','128','Perfil S2_DGS-1','Perfil S2_DGS-1','Perfil S2_DGS-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('328','36','35','165','PER_129','129','Perfil S2_DGS-2','Perfil S2_DGS-2','Perfil S2_DGS-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('329','36','35','165','PER_130','130','Perfil S3A_DGS','Perfil S3A_DGS','Perfil S3A_DGS','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('330','36','35','165','PER_131','131','Perfil S3B_DGS','Perfil S3B_DGS','Perfil S3B_DGS','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('331','36','35','165','PER_132','132','Perfil S4A_DGS','Perfil S4A_DGS','Perfil S4A_DGS','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('332','36','35','165','PER_133','133','Perfil S4B_DGS','Perfil S4B_DGS','Perfil S4B_DGS','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('333','36','35','166','PER_134','134','Perfil S1B_DGCR-1','Perfil S1B_DGCR-1','Perfil S1B_DGCR-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('334','36','35','166','PER_135','135','Perfil S1B_DGCR-2','Perfil S1B_DGCR-2','Perfil S1B_DGCR-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('335','36','35','166','PER_136','136','Perfil S3A_DGCR','Perfil S3A_DGCR','Perfil S3A_DGCR','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('336','36','35','167','PER_137','137','Perfil S1B_DGD-1','Perfil S1B_DGD-1','Perfil S1B_DGD-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('337','36','35','167','PER_138','138','Perfil S1B_DGD-2','Perfil S1B_DGD-2','Perfil S1B_DGD-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('338','36','35','167','PER_139','139','Perfil S2_DGD-1','Perfil S2_DGD-1','Perfil S2_DGD-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('339','36','35','167','PER_140','140','Perfil S2_DGD-2','Perfil S2_DGD-2','Perfil S2_DGD-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('340','36','35','167','PER_141','141','Perfil S3A_DGD-1','Perfil S3A_DGD-1','Perfil S3A_DGD-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('341','36','35','167','PER_142','142','Perfil S3A_DGD-2','Perfil S3A_DGD-2','Perfil S3A_DGD-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('342','36','35','167','PER_143','143','Perfil S3B_DGD','Perfil S3B_DGD','Perfil S3B_DGD','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('343','36','35','167','PER_144','144','Perfil S4A_DGD','Perfil S4A_DGD','Perfil S4A_DGD','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('344','36','35','168','PER_145','145','Perfil S1B_DGI-1','Perfil S1B_DGI-1','Perfil S1B_DGI-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('345','36','35','168','PER_146','146','Perfil S1B_DGI-2','Perfil S1B_DGI-2','Perfil S1B_DGI-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('346','36','35','168','PER_147','147','Perfil S2_DGI','Perfil S2_DGI','Perfil S2_DGI','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('347','36','35','168','PER_148','148','Perfil S3A_DGI','Perfil S3A_DGI','Perfil S3A_DGI','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('348','36','35','168','PER_149','149','Perfil S3B_DGI','Perfil S3B_DGI','Perfil S3B_DGI','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('349','36','35','169','PER_150','150','Perfil S1B_DGP','Perfil S1B_DGP','Perfil S1B_DGP','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('350','36','35','169','PER_151','151','Perfil S2_DGP-1','Perfil S2_DGP-1','Perfil S2_DGP-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('351','36','35','169','PER_152','152','Perfil S2_DGP-2','Perfil S2_DGP-2','Perfil S2_DGP-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('352','36','35','169','PER_153','153','Perfil S3A_DGP','Perfil S3A_DGP','Perfil S3A_DGP','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('353','36','35','169','PER_154','154','Perfil S4A_DGP','Perfil S4A_DGP','Perfil S4A_DGP','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('354','36','35','170','PER_155','155','Perfil S1B_DGR-1','Perfil S1B_DGR-1','Perfil S1B_DGR-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('465','36','35','182','PER_266','266','Perfil S1A_HM-9','Perfil S1A_HM-9','Perfil S1A_HM-9','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('466','36','35','183','PER_267','267','Perfil S1A_HR-1','Perfil S1A_HR-1','Perfil S1A_HR-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('286','36','35','189','PER_87','87','Perfil S2_EG-2','Perfil S2_EG-2','Perfil S2_EG-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('287','36','35','189','PER_88','88','Perfil S2_EG-4','Perfil S2_EG-4','Perfil S2_EG-4','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('288','36','35','189','PER_89','89','Perfil S2_EG-6','Perfil S2_EG-6','Perfil S2_EG-6','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('289','36','35','189','PER_90','90','Perfil S2_ET-5','Perfil S2_ET-5','Perfil S2_ET-5','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('290','36','35','189','PER_91','91','Perfil S3A_EG-1','Perfil S3A_EG-1','Perfil S3A_EG-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('291','36','35','189','PER_92','92','Perfil S3B_EG','Perfil S3B_EG','Perfil S3B_EG','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('292','36','35','189','PER_93','93','Perfil S4A_EG','Perfil S4A_EG','Perfil S4A_EG','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('293','36','35','189','PER_94','94','Perfil S4B_EG','Perfil S4B_EG','Perfil S4B_EG','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('294','36','35','189','PER_95','95','Perfil S1B_EG-5','Perfil S1B_EG-5','Perfil S1B_EG-5','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('295','36','35','189','PER_96','96','Perfil S1B_EG-8','Perfil S1B_EG-8','Perfil S1B_EG-8','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('296','36','35','189','PER_97','97','Perfil S2_EG-3','Perfil S2_EG-3','Perfil S2_EG-3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('297','36','35','189','PER_98','98','Perfil S2_EG-5','Perfil S2_EG-5','Perfil S2_EG-5','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('298','36','35','189','PER_99','99','Perfil S2_ET-4','Perfil S2_ET-4','Perfil S2_ET-4','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('299','36','35','189','PER_100','100','Perfil S3A_EG-2','Perfil S3A_EG-2','Perfil S3A_EG-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('300','36','35','189','PER_101','101','Perfil S3A_EG-3','Perfil S3A_EG-3','Perfil S3A_EG-3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('301','36','35','189','PER_102','102','Perfil S4A_ET','Perfil S4A_ET','Perfil S4A_ET','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('302','36','35','189','PER_103','103','Perfil S4B_ET','Perfil S4B_ET','Perfil S4B_ET','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('303','36','35','189','PER_104','104','Perfil S1A_ET','Perfil S1A_ET','Perfil S1A_ET','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('304','36','35','189','PER_105','105','Perfil S1B_ET-1','Perfil S1B_ET-1','Perfil S1B_ET-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('305','36','35','189','PER_106','106','Perfil S1B_ET-2','Perfil S1B_ET-2','Perfil S1B_ET-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('306','36','35','189','PER_107','107','Perfil S1B_ET-3','Perfil S1B_ET-3','Perfil S1B_ET-3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('307','36','35','189','PER_108','108','Perfil S2_ET-1','Perfil S2_ET-1','Perfil S2_ET-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('308','36','35','189','PER_109','109','Perfil S2_ET-2','Perfil S2_ET-2','Perfil S2_ET-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('309','36','35','189','PER_110','110','Perfil S2_ET-3','Perfil S2_ET-3','Perfil S2_ET-3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('310','36','35','189','PER_111','111','Perfil S3A_ET-1','Perfil S3A_ET-1','Perfil S3A_ET-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('311','36','35','189','PER_112','112','Perfil S3A_ET-2','Perfil S3A_ET-2','Perfil S3A_ET-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('312','36','35','189','PER_113','113','Perfil S3B_ET','Perfil S3B_ET','Perfil S3B_ET','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('359','36','35','190','PER_160','160','Perfil S1A_GPC-1','Perfil S1A_GPC-1','Perfil S1A_GPC-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('360','36','35','190','PER_161','161','Perfil S1A_GPC-2','Perfil S1A_GPC-2','Perfil S1A_GPC-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('361','36','35','190','PER_162','162','Perfil S1A_GPC-3','Perfil S1A_GPC-3','Perfil S1A_GPC-3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('362','36','35','190','PER_163','163','Perfil S1A_GPC-4','Perfil S1A_GPC-4','Perfil S1A_GPC-4','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('363','36','35','190','PER_164','164','Perfil S1B_GPC-1','Perfil S1B_GPC-1','Perfil S1B_GPC-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('364','36','35','190','PER_165','165','Perfil S1B_GPC-2','Perfil S1B_GPC-2','Perfil S1B_GPC-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('365','36','35','190','PER_166','166','Perfil S4A_GPC','Perfil S4A_GPC','Perfil S4A_GPC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('366','36','35','190','PER_167','167','Perfil S4B_GPC','Perfil S4B_GPC','Perfil S4B_GPC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('367','36','35','191','PER_168','168','Perfil S1A_GPD-1','Perfil S1A_GPD-1','Perfil S1A_GPD-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('368','36','35','191','PER_169','169','Perfil S1A_GPD-2','Perfil S1A_GPD-2','Perfil S1A_GPD-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('369','36','35','191','PER_170','170','Perfil S1A_GPD-3','Perfil S1A_GPD-3','Perfil S1A_GPD-3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('370','36','35','191','PER_171','171','Perfil S2_GPD','Perfil S2_GPD','Perfil S2_GPD','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('371','36','35','191','PER_172','172','Perfil S4A_GPD','Perfil S4A_GPD','Perfil S4A_GPD','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('372','36','35','191','PER_173','173','Perfil S4B_GPD','Perfil S4B_GPD','Perfil S4B_GPD','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('373','36','35','192','PER_174','174','Perfil S1A_GC-1','Perfil S1A_GC-1','Perfil S1A_GC-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('374','36','35','192','PER_175','175','Perfil S1A_GC-2','Perfil S1A_GC-2','Perfil S1A_GC-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('375','36','35','192','PER_176','176','Perfil S2_GC','Perfil S2_GC','Perfil S2_GC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('376','36','35','192','PER_177','177','Perfil S3A_GC','Perfil S3A_GC','Perfil S3A_GC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('377','36','35','192','PER_178','178','Perfil S3B_GC','Perfil S3B_GC','Perfil S3B_GC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('378','36','35','192','PER_179','179','Perfil S4A_GC','Perfil S4A_GC','Perfil S4A_GC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('379','36','35','192','PER_180','180','Perfil S4B_GC','Perfil S4B_GC','Perfil S4B_GC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('380','36','35','193','PER_181','181','Perfil S1A_GF','Perfil S1A_GF','Perfil S1A_GF','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('381','36','35','193','PER_182','182','Perfil S3A_GF','Perfil S3A_GF','Perfil S3A_GF','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('382','36','35','194','PER_183','183','Perfil S3A_GL','Perfil S3A_GL','Perfil S3A_GL','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('383','36','35','194','PER_184','184','Perfil S4A_GL','Perfil S4A_GL','Perfil S4A_GL','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('384','36','35','194','PER_185','185','Perfil S4B_GL','Perfil S4B_GL','Perfil S4B_GL','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('385','36','35','195','PER_186','186','Perfil S1A_GT-1','Perfil S1A_GT-1','Perfil S1A_GT-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('386','36','35','195','PER_187','187','Perfil S1A_GT-2','Perfil S1A_GT-2','Perfil S1A_GT-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('387','36','35','195','PER_188','188','Perfil S1A_GT-3','Perfil S1A_GT-3','Perfil S1A_GT-3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('388','36','35','195','PER_189','189','Perfil S1A_GT-4','Perfil S1A_GT-4','Perfil S1A_GT-4','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('389','36','35','195','PER_190','190','Perfil S1A_GT-5','Perfil S1A_GT-5','Perfil S1A_GT-5','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('390','36','35','195','PER_191','191','Perfil S1B_GT-1','Perfil S1B_GT-1','Perfil S1B_GT-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('391','36','35','195','PER_192','192','Perfil S1B_GT-2','Perfil S1B_GT-2','Perfil S1B_GT-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('392','36','35','195','PER_193','193','Perfil S1B_GT-3','Perfil S1B_GT-3','Perfil S1B_GT-3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('393','36','35','195','PER_194','194','Perfil S2_GT-1','Perfil S2_GT-1','Perfil S2_GT-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('394','36','35','195','PER_195','195','Perfil S2_GT-2','Perfil S2_GT-2','Perfil S2_GT-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('395','36','35','195','PER_196','196','Perfil S2_GT-3','Perfil S2_GT-3','Perfil S2_GT-3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('396','36','35','195','PER_197','197','Perfil S3B_GT-1','Perfil S3B_GT-1','Perfil S3B_GT-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('397','36','35','195','PER_198','198','Perfil S3B_GT-2','Perfil S3B_GT-2','Perfil S3B_GT-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('398','36','35','195','PER_199','199','Perfil S4A_GT-1','Perfil S4A_GT-1','Perfil S4A_GT-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('399','36','35','195','PER_200','200','Perfil S4A_GT-2','Perfil S4A_GT-2','Perfil S4A_GT-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('400','36','35','195','PER_201','201','Perfil S4B_GT','Perfil S4B_GT','Perfil S4B_GT','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('505','36','35','196','PER_306','306','Perfil S1B_MM-1','Perfil S1B_MM-1','Perfil S1B_MM-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('506','36','35','196','PER_307','307','Perfil S1B_MM-2','Perfil S1B_MM-2','Perfil S1B_MM-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('507','36','35','196','PER_308','308','Perfil S1B_MP-1','Perfil S1B_MP-1','Perfil S1B_MP-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('508','36','35','196','PER_309','309','Perfil S1B_MP-2','Perfil S1B_MP-2','Perfil S1B_MP-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('509','36','35','196','PER_310','310','Perfil S1B_MP-3','Perfil S1B_MP-3','Perfil S1B_MP-3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('510','36','35','196','PER_311','311','Perfil S2_MM','Perfil S2_MM','Perfil S2_MM','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('511','36','35','196','PER_312','312','Perfil S2_MP','Perfil S2_MP','Perfil S2_MP','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('512','36','35','196','PER_313','313','Perfil S3B_MM','Perfil S3B_MM','Perfil S3B_MM','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('513','36','35','196','PER_314','314','Perfil S3B_MP','Perfil S3B_MP','Perfil S3B_MP','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('514','36','35','197','PER_315','315','Perfil S1B_MT-1','Perfil S1B_MT-1','Perfil S1B_MT-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('515','36','35','197','PER_316','316','Perfil S1B_MT-2','Perfil S1B_MT-2','Perfil S1B_MT-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('516','36','35','197','PER_317','317','Perfil S1B_MT-3','Perfil S1B_MT-3','Perfil S1B_MT-3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('517','36','35','197','PER_318','318','Perfil S2_MT','Perfil S2_MT','Perfil S2_MT','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('518','36','35','197','PER_319','319','Perfil S3B_MT','Perfil S3B_MT','Perfil S3B_MT','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('519','36','35','198','PER_320','320','Perfil S1B_MV-1','Perfil S1B_MV-1','Perfil S1B_MV-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('520','36','35','198','PER_321','321','Perfil S1B_MV-2','Perfil S1B_MV-2','Perfil S1B_MV-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('521','36','35','198','PER_322','322','Perfil S2_MV','Perfil S2_MV','Perfil S2_MV','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('522','36','35','198','PER_323','323','Perfil S3B_MV','Perfil S3B_MV','Perfil S3B_MV','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('523','36','35','199','PER_324','324','Perfil S1B_MG-1','Perfil S1B_MG-1','Perfil S1B_MG-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('524','36','35','199','PER_325','325','Perfil S1B_MG-2','Perfil S1B_MG-2','Perfil S1B_MG-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('525','36','35','199','PER_326','326','Perfil S2_MG','Perfil S2_MG','Perfil S2_MG','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('526','36','35','199','PER_327','327','Perfil S3B_MG','Perfil S3B_MG','Perfil S3B_MG','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('538','37',null,null,'ASIGNADO','1','Asignado','Asignado','Asignado','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('539','37',null,null,'EN_PROCESO','2','En Proceso','En Proceso','En Proceso','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('540','37',null,null,'EN_APROBACION','3','En Aprobación','En Aprobación','En Aprobación','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('541','37',null,null,'CONCLUIDO','4','Concluido','Concluido','Concluido','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('542','38',null,null,'G1','1','Grupo 1','Grupo 1','Grupo 1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('543','38',null,null,'G2','2','Grupo 2','Grupo 2','Grupo 2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('547','39',null,null,'PLAZO-01','1','Plazo para dar respuesta a la solicitud presentada por el/la solicitante','Plazo para dar respuesta a la solicitud presentada por el/la solicitante','6','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('548','39',null,null,'PLAZO-02','2','Plazo para asignar evaluadores','Plazo para asignar evaluadores','1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('549','39',null,null,'PLAZO-03','3','Plazo para concluir la evaluación técnica','Plazo para concluir la evaluación técnica','3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('550','39',null,null,'PLAZO-04','4','Plazo para subsanar observaciones','Plazo para subsanar observaciones','3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('551','39',null,null,'PLAZO-05','5','Plazo para que el área técnica revise la subsanación de observaciones','Plazo para que el área técnica revise la subsanación de observaciones','3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('552','39',null,null,'PLAZO-06','6','Respuesta de la subsanación de las observaciones al solicitante','Respuesta de la subsanación de las observaciones al solicitante','5','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('553','39',null,null,'PLAZO-07','7','Plazo para dar respuesta a la solicitud de modificación presentada por el/la solicitante','Plazo para dar respuesta a la solicitud de modificación presentada por el/la solicitante','6','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('554','39',null,null,'PLAZO-08','8','Plazo para asignar evaluadores','Plazo para asignar evaluadores','1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('555','39',null,null,'PLAZO-09','9','Plazo para concluir la evaluación administrativa','Plazo para concluir la evaluación administrativa','3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('556','39',null,null,'PLAZO-10','10','Plazo para subsanar observaciones','Plazo para subsanar observaciones','3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('557','39',null,null,'PLAZO-11','11','Plazo para que el área técnica revise la subsanación de observaciones','Plazo para que el área técnica revise la subsanación de observaciones','3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('558','39',null,null,'PLAZO-12','12','Respuesta de la subsanación de las observaciones al solicitante','Respuesta de la subsanación de las observaciones al solicitante','5','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('559','39',null,null,'PLAZO-13','13','No hay plazos','No hay plazos','-','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('560','40',null,null,'APROBADO','1','Aprobado','Aprobado','-','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('561','40',null,null,'RECHAZADO','2','Rechazado','Rechazado','-','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('565','40',null,null,'CANCELADO','1','Cancelado','Cancelado','-','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('566','40',null,null,'ASIGNADO','1','Asignado','Asignado','-','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('564','41',null,null,'USUARIO 1','1','USUARIO 1','USUARIO 1','JVASQUEZG','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('569','18',null,null,'RE_07','6','Respondido','Respondido','Respondido','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('355','36','35','170','PER_156','156','Perfil S1B_DGR-2','Perfil S1B_DGR-2','Perfil S1B_DGR-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('356','36','35','170','PER_157','157','Perfil S2_DGR','Perfil S2_DGR','Perfil S2_DGR','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('357','36','35','170','PER_158','158','Perfil S3A_DGR','Perfil S3A_DGR','Perfil S3A_DGR','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('358','36','35','170','PER_159','159','Perfil S3B_DGR','Perfil S3B_DGR','Perfil S3B_DGR','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('401','36','35','171','PER_202','202','Perfil S1A_HP','Perfil S1A_HP','Perfil S1A_HP','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('402','36','35','171','PER_203','203','Perfil S1B_HP','Perfil S1B_HP','Perfil S1B_HP','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('403','36','35','171','PER_204','204','Perfil S2_HP-1','Perfil S2_HP-1','Perfil S2_HP-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('404','36','35','171','PER_205','205','Perfil S2_HP-2','Perfil S2_HP-2','Perfil S2_HP-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('405','36','35','171','PER_206','206','Perfil S4A_HP','Perfil S4A_HP','Perfil S4A_HP','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('406','36','35','172','PER_207','207','Perfil S1A_HC','Perfil S1A_HC','Perfil S1A_HC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('407','36','35','172','PER_208','208','Perfil S2_HC-1','Perfil S2_HC-1','Perfil S2_HC-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('408','36','35','172','PER_209','209','Perfil S2_HC-2','Perfil S2_HC-2','Perfil S2_HC-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('409','36','35','172','PER_210','210','Perfil S3A_HC-1','Perfil S3A_HC-1','Perfil S3A_HC-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('410','36','35','172','PER_211','211','Perfil S3A_HC-2','Perfil S3A_HC-2','Perfil S3A_HC-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('411','36','35','172','PER_212','212','Perfil S3A_HC-3','Perfil S3A_HC-3','Perfil S3A_HC-3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('412','36','35','173','PER_213','213','Perfil S2_DHR','Perfil S2_DHR','Perfil S2_DHR','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('413','36','35','173','PER_214','214','Perfil S3A_DHR','Perfil S3A_DHR','Perfil S3A_DHR','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('414','36','35','173','PER_215','215','Perfil S3B_DHR','Perfil S3B_DHR','Perfil S3B_DHR','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('415','36','35','173','PER_216','216','Perfil S4_DHR','Perfil S4_DHR','Perfil S4_DHR','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('416','36','35','174','PER_217','217','Perfil S2_DHL','Perfil S2_DHL','Perfil S2_DHL','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('417','36','35','174','PER_218','218','Perfil S3A_DHL','Perfil S3A_DHL','Perfil S3A_DHL','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('418','36','35','174','PER_219','219','Perfil S3B_DHL','Perfil S3B_DHL','Perfil S3B_DHL','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('419','36','35','174','PER_220','220','Perfil S4A_DHL','Perfil S4A_DHL','Perfil S4A_DHL','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('420','36','35','175','PER_221','221','Perfil S3A_DHS','Perfil S3A_DHS','Perfil S3A_DHS','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('421','36','35','175','PER_222','222','Perfil S3B_DHS','Perfil S3B_DHS','Perfil S3B_DHS','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('422','36','35','176','PER_223','223','Perfil S1B_DHI','Perfil S1B_DHI','Perfil S1B_DHI','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('423','36','35','176','PER_224','224','Perfil S2_DHI','Perfil S2_DHI','Perfil S2_DHI','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('424','36','35','176','PER_225','225','Perfil S3A_DHI','Perfil S3A_DHI','Perfil S3A_DHI','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('425','36','35','176','PER_226','226','Perfil S3B_DHI','Perfil S3B_DHI','Perfil S3B_DHI','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('426','36','35','176','PER_227','227','Perfil S4_DHI','Perfil S4_DHI','Perfil S4_DHI','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('427','36','35','177','PER_228','228','Perfil S1B_DHIG','Perfil S1B_DHIG','Perfil S1B_DHIG','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('428','36','35','177','PER_229','229','Perfil S2_DHIG','Perfil S2_DHIG','Perfil S2_DHIG','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('429','36','35','177','PER_230','230','Perfil S3A_DHIG','Perfil S3A_DHIG','Perfil S3A_DHIG','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('430','36','35','177','PER_231','231','Perfil S3B_DHIG','Perfil S3B_DHIG','Perfil S3B_DHIG','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('431','36','35','177','PER_232','232','Perfil S4_DHIG','Perfil S4_DHIG','Perfil S4_DHIG','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('432','36','35','178','PER_233','233','Perfil S2_DHC','Perfil S2_DHC','Perfil S2_DHC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('433','36','35','178','PER_234','234','Perfil S3A_DHC','Perfil S3A_DHC','Perfil S3A_DHC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('434','36','35','178','PER_235','235','Perfil S4_DHC','Perfil S4_DHC','Perfil S4_DHC','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('435','36','35','179','PER_236','236','Perfil S1A_HE-1**','Perfil S1A_HE-1**','Perfil S1A_HE-1**','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('436','36','35','179','PER_237','237','Perfil S1A_HE-2**','Perfil S1A_HE-2**','Perfil S1A_HE-2**','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('437','36','35','179','PER_238','238','Perfil S1A_HE-3**','Perfil S1A_HE-3**','Perfil S1A_HE-3**','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('438','36','35','179','PER_239','239','Perfil S1A_HE-4**','Perfil S1A_HE-4**','Perfil S1A_HE-4**','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('439','36','35','179','PER_240','240','Perfil S1A_HE-5**','Perfil S1A_HE-5**','Perfil S1A_HE-5**','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('440','36','35','179','PER_241','241','Perfil S1A_HE-6**','Perfil S1A_HE-6**','Perfil S1A_HE-6**','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('441','36','35','179','PER_242','242','Perfil S1A_HE-7**','Perfil S1A_HE-7**','Perfil S1A_HE-7**','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('442','36','35','179','PER_243','243','Perfil S1A_HE-8**','Perfil S1A_HE-8**','Perfil S1A_HE-8**','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('443','36','35','179','PER_244','244','Perfil S2_HE-1**','Perfil S2_HE-1**','Perfil S2_HE-1**','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('444','36','35','179','PER_245','245','Perfil S2_HE-2','Perfil S2_HE-2','Perfil S2_HE-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('445','36','35','179','PER_246','246','Perfil S3A_HE-1**','Perfil S3A_HE-1**','Perfil S3A_HE-1**','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('446','36','35','179','PER_247','247','Perfil S3A_HE-2','Perfil S3A_HE-2','Perfil S3A_HE-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('447','36','35','179','PER_248','248','Perfil S4A_HE','Perfil S4A_HE','Perfil S4A_HE','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('448','36','35','180','PER_249','249','Perfil S4A_HL','Perfil S4A_HL','Perfil S4A_HL','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('449','36','35','180','PER_250','250','Perfil S4B_HL','Perfil S4B_HL','Perfil S4B_HL','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('450','36','35','181','PER_251','251','Perfil S1B_HF-1','Perfil S1B_HF-1','Perfil S1B_HF-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('451','36','35','181','PER_252','252','Perfil S1B_HF-2','Perfil S1B_HF-2','Perfil S1B_HF-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('452','36','35','181','PER_253','253','Perfil S2_HF-1','Perfil S2_HF-1','Perfil S2_HF-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('453','36','35','181','PER_254','254','Perfil S2_HF-2','Perfil S2_HF-2','Perfil S2_HF-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('454','36','35','181','PER_255','255','Perfil S3A_HF-1','Perfil S3A_HF-1','Perfil S3A_HF-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('455','36','35','181','PER_256','256','Perfil S3A_HF-2','Perfil S3A_HF-2','Perfil S3A_HF-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('456','36','35','181','PER_257','257','Perfil S4A_HF','Perfil S4A_HF','Perfil S4A_HF','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('457','36','35','182','PER_258','258','Perfil S1A_HM-1','Perfil S1A_HM-1','Perfil S1A_HM-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('458','36','35','182','PER_259','259','Perfil S1A_HM-2','Perfil S1A_HM-2','Perfil S1A_HM-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('459','36','35','182','PER_260','260','Perfil S1A_HM-3','Perfil S1A_HM-3','Perfil S1A_HM-3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('460','36','35','182','PER_261','261','Perfil S1A_HM-4','Perfil S1A_HM-4','Perfil S1A_HM-4','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('461','36','35','182','PER_262','262','Perfil S1A_HM-5','Perfil S1A_HM-5','Perfil S1A_HM-5','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('462','36','35','182','PER_263','263','Perfil S1A_HM-6','Perfil S1A_HM-6','Perfil S1A_HM-6','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('463','36','35','182','PER_264','264','Perfil S1A_HM-7','Perfil S1A_HM-7','Perfil S1A_HM-7','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('464','36','35','182','PER_265','265','Perfil S1A_HM-8','Perfil S1A_HM-8','Perfil S1A_HM-8','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('467','36','35','183','PER_268','268','Perfil S1A_HR-2','Perfil S1A_HR-2','Perfil S1A_HR-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('468','36','35','183','PER_269','269','Perfil S1A_HR-3','Perfil S1A_HR-3','Perfil S1A_HR-3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('469','36','35','183','PER_270','270','Perfil S2_HR-1','Perfil S2_HR-1','Perfil S2_HR-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('470','36','35','183','PER_271','271','Perfil S2_HR-2','Perfil S2_HR-2','Perfil S2_HR-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('471','36','35','183','PER_272','272','Perfil S2_HR-3','Perfil S2_HR-3','Perfil S2_HR-3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('472','36','35','183','PER_273','273','Perfil S2_HR-4','Perfil S2_HR-4','Perfil S2_HR-4','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('473','36','35','183','PER_274','274','Perfil S2_HR-5','Perfil S2_HR-5','Perfil S2_HR-5','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('474','36','35','183','PER_275','275','Perfil S2_HR-6','Perfil S2_HR-6','Perfil S2_HR-6','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('475','36','35','183','PER_276','276','Perfil S2_HR-7','Perfil S2_HR-7','Perfil S2_HR-7','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('476','36','35','183','PER_277','277','Perfil S2_HR-8','Perfil S2_HR-8','Perfil S2_HR-8','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('477','36','35','183','PER_278','278','Perfil S3A_HR-1','Perfil S3A_HR-1','Perfil S3A_HR-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('478','36','35','183','PER_279','279','Perfil S3A_HR-2','Perfil S3A_HR-2','Perfil S3A_HR-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('479','36','35','183','PER_280','280','Perfil S3B_HR','Perfil S3B_HR','Perfil S3B_HR','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('480','36','35','184','PER_281','281','Perfil S2_HCD','Perfil S2_HCD','Perfil S2_HCD','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('481','36','35','185','PER_282','282','Perfil S1A_HD-1','Perfil S1A_HD-1','Perfil S1A_HD-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('482','36','35','185','PER_283','283','Perfil S1A_HD-2','Perfil S1A_HD-2','Perfil S1A_HD-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('483','36','35','185','PER_284','284','Perfil S1A_HD-3','Perfil S1A_HD-3','Perfil S1A_HD-3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('484','36','35','185','PER_285','285','Perfil S1A_HD-4','Perfil S1A_HD-4','Perfil S1A_HD-4','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('485','36','35','185','PER_286','286','Perfil S1A_HD-5','Perfil S1A_HD-5','Perfil S1A_HD-5','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('486','36','35','185','PER_287','287','Perfil S1A_HD-6','Perfil S1A_HD-6','Perfil S1A_HD-6','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('487','36','35','185','PER_288','288','Perfil S1A_HD-7','Perfil S1A_HD-7','Perfil S1A_HD-7','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('488','36','35','185','PER_289','289','Perfil S1A_HD-8','Perfil S1A_HD-8','Perfil S1A_HD-8','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('489','36','35','185','PER_290','290','Perfil S1A_HD-9','Perfil S1A_HD-9','Perfil S1A_HD-9','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('490','36','35','185','PER_291','291','Perfil S1B_HD-1','Perfil S1B_HD-1','Perfil S1B_HD-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('491','36','35','185','PER_292','292','Perfil S1B_HD-2','Perfil S1B_HD-2','Perfil S1B_HD-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('492','36','35','185','PER_293','293','Perfil S2_HD-1','Perfil S2_HD-1','Perfil S2_HD-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('493','36','35','185','PER_294','294','Perfil S2_HD-2','Perfil S2_HD-2','Perfil S2_HD-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('494','36','35','185','PER_295','295','Perfil S3A_HD-1','Perfil S3A_HD-1','Perfil S3A_HD-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('495','36','35','185','PER_296','296','Perfil S3A_HD-2','Perfil S3A_HD-2','Perfil S3A_HD-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('496','36','35','185','PER_297','297','Perfil S3B_HD-1','Perfil S3B_HD-1','Perfil S3B_HD-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('497','36','35','185','PER_298','298','Perfil S3B_HD-2','Perfil S3B_HD-2','Perfil S3B_HD-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('498','36','35','185','PER_299','299','Perfil S4_HD','Perfil S4_HD','Perfil S4_HD','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('499','36','35','186','PER_300','300','Perfil S1A_HT','Perfil S1A_HT','Perfil S1A_HT','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('500','36','35','186','PER_301','301','Perfil S1B_HT','Perfil S1B_HT','Perfil S1B_HT','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('501','36','35','186','PER_302','302','Perfil S2_HT','Perfil S2_HT','Perfil S2_HT','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('502','36','35','186','PER_303','303','Perfil S3A_HT','Perfil S3A_HT','Perfil S3A_HT','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('503','36','35','186','PER_304','304','Perfil S3B_HT','Perfil S3B_HT','Perfil S3B_HT','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('504','36','35','186','PER_305','305','Perfil S4A_HT','Perfil S4A_HT','Perfil S4A_HT','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('255','36','35','187','PER_56','56','Perfil S1A_EI-1','Perfil S1A_EI-1','Perfil S1A_EI-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('256','36','35','187','PER_57','57','Perfil S1A_EI-2','Perfil S1A_EI-2','Perfil S1A_EI-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('257','36','35','187','PER_58','58','Perfil S1B_EI-1','Perfil S1B_EI-1','Perfil S1B_EI-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('258','36','35','187','PER_59','59','Perfil S1B_EI-2','Perfil S1B_EI-2','Perfil S1B_EI-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('259','36','35','187','PER_60','60','Perfil S2_EI-1','Perfil S2_EI-1','Perfil S2_EI-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('260','36','35','187','PER_61','61','Perfil S2_EI-2','Perfil S2_EI-2','Perfil S2_EI-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('261','36','35','187','PER_62','62','Perfil S2_EI-3','Perfil S2_EI-3','Perfil S2_EI-3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('262','36','35','187','PER_63','63','Perfil S2_EI-4','Perfil S2_EI-4','Perfil S2_EI-4','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('263','36','35','187','PER_64','64','Perfil S3A_EI-1','Perfil S3A_EI-1','Perfil S3A_EI-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('264','36','35','187','PER_65','65','Perfil S3A_EI-2','Perfil S3A_EI-2','Perfil S3A_EI-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('265','36','35','187','PER_66','66','Perfil S3A_EI-3','Perfil S3A_EI-3','Perfil S3A_EI-3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('266','36','35','187','PER_67','67','Perfil S3B_EI-1','Perfil S3B_EI-1','Perfil S3B_EI-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('267','36','35','187','PER_68','68','Perfil S3B_EI-2','Perfil S3B_EI-2','Perfil S3B_EI-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('268','36','35','187','PER_69','69','Perfil S4A_EI','Perfil S4A_EI','Perfil S4A_EI','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('269','36','35','187','PER_70','70','Perfil S4B_EI','Perfil S4B_EI','Perfil S4B_EI','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('270','36','35','188','PER_71','71','Perfil S1B_EF-1','Perfil S1B_EF-1','Perfil S1B_EF-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('271','36','35','188','PER_72','72','Perfil S1B_EF-2','Perfil S1B_EF-2','Perfil S1B_EF-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('272','36','35','188','PER_73','73','Perfil S2_EF','Perfil S2_EF','Perfil S2_EF','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('273','36','35','188','PER_74','74','Perfil S3A_EF-1','Perfil S3A_EF-1','Perfil S3A_EF-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('274','36','35','188','PER_75','75','Perfil S3A_EF-2','Perfil S3A_EF-2','Perfil S3A_EF-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('275','36','35','188','PER_76','76','Perfil S3B_EF','Perfil S3B_EF','Perfil S3B_EF','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('276','36','35','188','PER_77','77','Perfil S4A_EF','Perfil S4A_EF','Perfil S4A_EF','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('277','36','35','188','PER_78','78','Perfil S4B_EF','Perfil S4B_EF','Perfil S4B_EF','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('278','36','35','189','PER_79','79','Perfil S1A_EG','Perfil S1A_EG','Perfil S1A_EG','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('279','36','35','189','PER_80','80','Perfil S1B_EG-1','Perfil S1B_EG-1','Perfil S1B_EG-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('280','36','35','189','PER_81','81','Perfil S1B_EG-2','Perfil S1B_EG-2','Perfil S1B_EG-2','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('281','36','35','189','PER_82','82','Perfil S1B_EG-3','Perfil S1B_EG-3','Perfil S1B_EG-3','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('282','36','35','189','PER_83','83','Perfil S1B_EG-4','Perfil S1B_EG-4','Perfil S1B_EG-4','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('283','36','35','189','PER_84','84','Perfil S1B_EG-6','Perfil S1B_EG-6','Perfil S1B_EG-6','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('284','36','35','189','PER_85','85','Perfil S1B_EG-7','Perfil S1B_EG-7','Perfil S1B_EG-7','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_LISTADO_DETALLE (ID_LISTADO_DETALLE,ID_LISTADO,ID_LISTADO_PADRE,ID_SUPERIOR_LD,CO_LISTADO_DETALLE,NU_ORDEN,NO_LISTADO_DETALLE,DE_LISTADO_DETALLE,DE_VALOR,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('285','36','35','189','PER_86','86','Perfil S2_EG-1','Perfil S2_EG-1','Perfil S2_EG-1','SICOES',to_timestamp('13/10/22 06:48:13,611000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
REM INSERTING into SICOES.SICOES_TM_OPCION
SET DEFINE OFF;
Insert into SICOES.SICOES_TM_OPCION (ID_OPCION,ID_PADRE,ID_ESTADO_LD,NO_OPCION,DE_OPCION,CO_OPCION,NU_ORDEN,FL_VISIBLE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('1',null,null,'Bandeja Inscripcion','Bandeja de solicitudes (Usuario Externo)','OP_01','1',null,'SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_OPCION (ID_OPCION,ID_PADRE,ID_ESTADO_LD,NO_OPCION,DE_OPCION,CO_OPCION,NU_ORDEN,FL_VISIBLE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('2',null,null,'Solicitar Modificación','Solicitar Modificación','OP_02','2',null,'SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_OPCION (ID_OPCION,ID_PADRE,ID_ESTADO_LD,NO_OPCION,DE_OPCION,CO_OPCION,NU_ORDEN,FL_VISIBLE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('3',null,null,'Solicitar Actualizacion','Solicitar Actualizacion','OP_03','3',null,'SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_OPCION (ID_OPCION,ID_PADRE,ID_ESTADO_LD,NO_OPCION,DE_OPCION,CO_OPCION,NU_ORDEN,FL_VISIBLE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('4',null,null,'Suspender de parte','Suspender de parte','OP_04','4',null,'SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_OPCION (ID_OPCION,ID_PADRE,ID_ESTADO_LD,NO_OPCION,DE_OPCION,CO_OPCION,NU_ORDEN,FL_VISIBLE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('5',null,null,'Cancelar de parte ','Cancelar de parte ','OP_05','5',null,'SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_OPCION (ID_OPCION,ID_PADRE,ID_ESTADO_LD,NO_OPCION,DE_OPCION,CO_OPCION,NU_ORDEN,FL_VISIBLE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('6',null,null,'Ver Bandeja','Bandeja de Solicitudes (usuario Interno)','OP_06','6',null,'SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_OPCION (ID_OPCION,ID_PADRE,ID_ESTADO_LD,NO_OPCION,DE_OPCION,CO_OPCION,NU_ORDEN,FL_VISIBLE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('7',null,null,'Asignar Evaluador Administrativo','Asignar Evaluador Administrativo','OP_07','7',null,'SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_OPCION (ID_OPCION,ID_PADRE,ID_ESTADO_LD,NO_OPCION,DE_OPCION,CO_OPCION,NU_ORDEN,FL_VISIBLE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('8',null,null,'Asignar Evaluador Tecnico','Asignar Evaluador Tecnico','OP_08','8',null,'SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_OPCION (ID_OPCION,ID_PADRE,ID_ESTADO_LD,NO_OPCION,DE_OPCION,CO_OPCION,NU_ORDEN,FL_VISIBLE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('9',null,null,'Ver Bandeja','Bandeja de Solicitudes de Sus/Canc','OP_09','8',null,'SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_OPCION (ID_OPCION,ID_PADRE,ID_ESTADO_LD,NO_OPCION,DE_OPCION,CO_OPCION,NU_ORDEN,FL_VISIBLE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('10',null,null,'Asignar Evaluador Administrativo','Asignar Evaluador Administrativo','OP_10','10',null,'SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_OPCION (ID_OPCION,ID_PADRE,ID_ESTADO_LD,NO_OPCION,DE_OPCION,CO_OPCION,NU_ORDEN,FL_VISIBLE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('11',null,null,'Asignar Evaluador Tecnico','Asignar Evaluador Tecnico','OP_11','11',null,'SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_OPCION (ID_OPCION,ID_PADRE,ID_ESTADO_LD,NO_OPCION,DE_OPCION,CO_OPCION,NU_ORDEN,FL_VISIBLE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('12',null,null,'Ver Bandeja','Mis Solicitudes ','OP_12','12',null,'SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_OPCION (ID_OPCION,ID_PADRE,ID_ESTADO_LD,NO_OPCION,DE_OPCION,CO_OPCION,NU_ORDEN,FL_VISIBLE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('13',null,null,'Ver Bandeja','Bandeja de Aprobaciones ','OP_13','13',null,'SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_OPCION (ID_OPCION,ID_PADRE,ID_ESTADO_LD,NO_OPCION,DE_OPCION,CO_OPCION,NU_ORDEN,FL_VISIBLE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('14',null,null,'Ver detalle','Ver detalle','OP_14','14',null,'SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_OPCION (ID_OPCION,ID_PADRE,ID_ESTADO_LD,NO_OPCION,DE_OPCION,CO_OPCION,NU_ORDEN,FL_VISIBLE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('15',null,null,'Ver Bandeja','Registro Unico de Empresas Supervisoras','OP_15','15',null,'SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_OPCION (ID_OPCION,ID_PADRE,ID_ESTADO_LD,NO_OPCION,DE_OPCION,CO_OPCION,NU_ORDEN,FL_VISIBLE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('16',null,null,'Suspender','Suspender','OP_16','16',null,'SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_OPCION (ID_OPCION,ID_PADRE,ID_ESTADO_LD,NO_OPCION,DE_OPCION,CO_OPCION,NU_ORDEN,FL_VISIBLE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('17',null,null,'Cancelar','Cancelar','OP_17','17',null,'SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_OPCION (ID_OPCION,ID_PADRE,ID_ESTADO_LD,NO_OPCION,DE_OPCION,CO_OPCION,NU_ORDEN,FL_VISIBLE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('18',null,null,'Registrar ES por Excepción','Registrar ES por Excepción','OP_18','18',null,'SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_OPCION (ID_OPCION,ID_PADRE,ID_ESTADO_LD,NO_OPCION,DE_OPCION,CO_OPCION,NU_ORDEN,FL_VISIBLE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('19',null,null,'Ver Bandeja','Registro de E.S. Suspendidas/Canceladas','OP_19','19',null,'SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_OPCION (ID_OPCION,ID_PADRE,ID_ESTADO_LD,NO_OPCION,DE_OPCION,CO_OPCION,NU_ORDEN,FL_VISIBLE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('20',null,null,'Generar Reporte','Generar Reporte','OP_20','20',null,'SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_OPCION (ID_OPCION,ID_PADRE,ID_ESTADO_LD,NO_OPCION,DE_OPCION,CO_OPCION,NU_ORDEN,FL_VISIBLE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('21',null,null,'Ver Detalle','Ver Detalle','OP_21','21',null,'SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
REM INSERTING into SICOES.SICOES_TM_ROL
SET DEFINE OFF;
Insert into SICOES.SICOES_TM_ROL (ID_ROL,ID_ESTADO_LD,NO_ROL,DE_ROL,CO_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('1','103','Responsable Administrativo ','Responsable Administrativo (Asignar ET)','01','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL (ID_ROL,ID_ESTADO_LD,NO_ROL,DE_ROL,CO_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('2','103','Responsable Técnico','Responsable técnico (Asignar EA)','02','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL (ID_ROL,ID_ESTADO_LD,NO_ROL,DE_ROL,CO_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('3','103','Evaluador Administrativo','Evaluadores administrativos (EA)','03','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL (ID_ROL,ID_ESTADO_LD,NO_ROL,DE_ROL,CO_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('4','103','Evaluador Técnico','Evaluadores técnicos (ET)','04','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL (ID_ROL,ID_ESTADO_LD,NO_ROL,DE_ROL,CO_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('5','103','Aprobador Técnico','Evaluador que finaliza la evaluación de la solicitud (Finaliza Revisión)','05','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL (ID_ROL,ID_ESTADO_LD,NO_ROL,DE_ROL,CO_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('6','103','Usuario Externo','Usuario Externo','06','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL (ID_ROL,ID_ESTADO_LD,NO_ROL,DE_ROL,CO_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('7','103','Aprobador Administrativo','Evaluador que finaliza la evaluación de la solicitud (Finaliza Revisión)','07','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
REM INSERTING into SICOES.SICOES_TM_ROL_OPCION
SET DEFINE OFF;
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('1','1','6','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('2','2','6','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('3','3','6','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('4','4','6','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('5','5','6','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('6','6','1','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('7','7','1','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('8','9','1','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('9','10','1','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('10','14','1','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('11','15','1','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('12','6','2','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('13','8','2','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('14','9','2','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('15','11','2','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('16','14','2','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('17','6','3','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('18','7','3','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('19','12','3','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('20','6','4','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('21','8','4','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('22','12','4','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_ROL_OPCION (ID_ROL_OPCION,ID_OPCION,ID_ROL,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('23','13','5','SICOES',to_timestamp('12/11/22 11:23:43,364000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
REM INSERTING into SICOES.SICOES_TM_UBIGEO
SET DEFINE OFF;
REM INSERTING into SICOES.SICOES_TM_USUARIO
SET DEFINE OFF;
Insert into SICOES.SICOES_TM_USUARIO (ID_USUARIO,DE_USUARIO,DE_RUC,NO_USUARIO,DE_CONTRASENIA,DE_RAZON_SOCIAL,DE_CORREO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('12','PHAVELUR','10456426561','ROSALES HUERTAS LUIS ALBERTO',null,null,null,'SIAS_JOB',to_timestamp('28/01/23 03:18:00,758000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO (ID_USUARIO,DE_USUARIO,DE_RUC,NO_USUARIO,DE_CONTRASENIA,DE_RAZON_SOCIAL,DE_CORREO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('1','20566213932','20566213932',null,'$2a$10$QU/kOtNKBdN6Hmkr.osPp.V3rKcGp9QtruuXuv2MpV8FNAQgqru3W','CELSAT PERU SAC','CELSAT@GMAIL.COM','SICOES',to_timestamp('13/10/22 06:18:56,939000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO (ID_USUARIO,DE_USUARIO,DE_RUC,NO_USUARIO,DE_CONTRASENIA,DE_RAZON_SOCIAL,DE_CORREO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('2','rzegarra',null,'RITHA ZEGARRA RUESTA','$2a$10$QU/kOtNKBdN6Hmkr.osPp.V3rKcGp9QtruuXuv2MpV8FNAQgqru3W',null,'CELSAT@GMAIL.COM','SICOES',to_timestamp('13/10/22 06:18:56,939000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO (ID_USUARIO,DE_USUARIO,DE_RUC,NO_USUARIO,DE_CONTRASENIA,DE_RAZON_SOCIAL,DE_CORREO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('3','jurcia',null,'JAVIER URCIA GUTIERREZ','$2a$10$QU/kOtNKBdN6Hmkr.osPp.V3rKcGp9QtruuXuv2MpV8FNAQgqru3W',null,'CELSAT@GMAIL.COM','SICOES',to_timestamp('13/10/22 06:18:56,939000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO (ID_USUARIO,DE_USUARIO,DE_RUC,NO_USUARIO,DE_CONTRASENIA,DE_RAZON_SOCIAL,DE_CORREO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('4','ksilvap',null,'KRISTELL SILVA PRETEL','$2a$10$QU/kOtNKBdN6Hmkr.osPp.V3rKcGp9QtruuXuv2MpV8FNAQgqru3W',null,'CELSAT@GMAIL.COM','SICOES',to_timestamp('13/10/22 06:18:56,939000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO (ID_USUARIO,DE_USUARIO,DE_RUC,NO_USUARIO,DE_CONTRASENIA,DE_RAZON_SOCIAL,DE_CORREO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('5','restecnico',null,'Luis Rosales','$2a$10$QU/kOtNKBdN6Hmkr.osPp.V3rKcGp9QtruuXuv2MpV8FNAQgqru3W',null,'CELSAT@GMAIL.COM','SICOES',to_timestamp('13/10/22 06:18:56,939000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO (ID_USUARIO,DE_USUARIO,DE_RUC,NO_USUARIO,DE_CONTRASENIA,DE_RAZON_SOCIAL,DE_CORREO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('6','evafina',null,'Walter Duran','$2a$10$QU/kOtNKBdN6Hmkr.osPp.V3rKcGp9QtruuXuv2MpV8FNAQgqru3W',null,'CELSAT@GMAIL.COM','SICOES',to_timestamp('13/10/22 06:18:56,939000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO (ID_USUARIO,DE_USUARIO,DE_RUC,NO_USUARIO,DE_CONTRASENIA,DE_RAZON_SOCIAL,DE_CORREO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('7','EFIELD',null,'EDUARDO FIELD RODRIGUEZ','$2a$10$QU/kOtNKBdN6Hmkr.osPp.V3rKcGp9QtruuXuv2MpV8FNAQgqru3W',null,'CELSAT@GMAIL.COM','SICOES',to_timestamp('13/10/22 06:18:56,939000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO (ID_USUARIO,DE_USUARIO,DE_RUC,NO_USUARIO,DE_CONTRASENIA,DE_RAZON_SOCIAL,DE_CORREO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('8','RVERAC',null,'RITA VERA CESPEDES','$2a$10$QU/kOtNKBdN6Hmkr.osPp.V3rKcGp9QtruuXuv2MpV8FNAQgqru3W',null,'CELSAT@GMAIL.COM','SICOES',to_timestamp('13/10/22 06:18:56,939000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO (ID_USUARIO,DE_USUARIO,DE_RUC,NO_USUARIO,DE_CONTRASENIA,DE_RAZON_SOCIAL,DE_CORREO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('9','JVASQUEZG',null,'JAVIER URCIA JAVIER VASQUEZ GUERRA','$2a$10$QU/kOtNKBdN6Hmkr.osPp.V3rKcGp9QtruuXuv2MpV8FNAQgqru3W',null,'CELSAT@GMAIL.COM','SICOES',to_timestamp('13/10/22 06:18:56,939000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO (ID_USUARIO,DE_USUARIO,DE_RUC,NO_USUARIO,DE_CONTRASENIA,DE_RAZON_SOCIAL,DE_CORREO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('10','AOLIVERA',null,'AOLIVERA','$2a$10$QU/kOtNKBdN6Hmkr.osPp.V3rKcGp9QtruuXuv2MpV8FNAQgqru3W',null,'CELSAT@GMAIL.COM','SICOES',to_timestamp('13/10/22 06:18:56,939000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO (ID_USUARIO,DE_USUARIO,DE_RUC,NO_USUARIO,DE_CONTRASENIA,DE_RAZON_SOCIAL,DE_CORREO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('11','AOYOLA',null,'AOYOLA','$2a$10$QU/kOtNKBdN6Hmkr.osPp.V3rKcGp9QtruuXuv2MpV8FNAQgqru3W',null,'CELSAT@GMAIL.COM','SICOES',to_timestamp('13/10/22 06:18:56,939000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
REM INSERTING into SICOES.SICOES_TM_USUARIO_ROL
SET DEFINE OFF;
Insert into SICOES.SICOES_TM_USUARIO_ROL (ID_USUARIO_ROL,ID_ROL,ID_USUARIO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('14','6','12','12',to_timestamp('28/01/23 03:18:00,826000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO_ROL (ID_USUARIO_ROL,ID_ROL,ID_USUARIO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('1','6','1','SICOES',to_timestamp('12/11/22 11:37:25,337000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO_ROL (ID_USUARIO_ROL,ID_ROL,ID_USUARIO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('2','2','2','SICOES',to_timestamp('12/11/22 11:37:25,337000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO_ROL (ID_USUARIO_ROL,ID_ROL,ID_USUARIO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('3','4','2','SICOES',to_timestamp('12/11/22 11:37:25,337000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO_ROL (ID_USUARIO_ROL,ID_ROL,ID_USUARIO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('4','3','3','SICOES',to_timestamp('12/11/22 11:37:25,337000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO_ROL (ID_USUARIO_ROL,ID_ROL,ID_USUARIO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('5','4','3','SICOES',to_timestamp('12/11/22 11:37:25,337000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO_ROL (ID_USUARIO_ROL,ID_ROL,ID_USUARIO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('6','1','4','SICOES',to_timestamp('12/11/22 11:37:25,337000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO_ROL (ID_USUARIO_ROL,ID_ROL,ID_USUARIO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('7','3','4','SICOES',to_timestamp('12/11/22 11:37:25,337000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO_ROL (ID_USUARIO_ROL,ID_ROL,ID_USUARIO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('8','3','7','SICOES',to_timestamp('12/11/22 11:37:25,337000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO_ROL (ID_USUARIO_ROL,ID_ROL,ID_USUARIO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('9','4','7','SICOES',to_timestamp('12/11/22 11:37:25,337000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO_ROL (ID_USUARIO_ROL,ID_ROL,ID_USUARIO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('10','5','8','SICOES',to_timestamp('12/11/22 11:37:25,337000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO_ROL (ID_USUARIO_ROL,ID_ROL,ID_USUARIO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('11','7','9','SICOES',to_timestamp('12/11/22 11:37:25,337000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO_ROL (ID_USUARIO_ROL,ID_ROL,ID_USUARIO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('12','5','10','SICOES',to_timestamp('12/11/22 11:37:25,337000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TM_USUARIO_ROL (ID_USUARIO_ROL,ID_ROL,ID_USUARIO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('13','5','11','SICOES',to_timestamp('12/11/22 11:37:25,337000000 AM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
REM INSERTING into SICOES.SICOES_TR_ARCHIVO
SET DEFINE OFF;
Insert into SICOES.SICOES_TR_ARCHIVO (ID_ARCHIVO,ID_DOCUMENTO,ID_OTRO_REQUISITO,ID_ESTUDIO,ID_ESTADO_LD,ID_TIPO_ARCHIVO_LD,ID_SOLICITUD,NO_ARCHIVO,NO_REAL,NO_ALFRESCO,CO_ARCHIVO,DE_ARCHIVO,CO_TIPO_ARCHIVO,NU_CORRELATIVO,NU_VERSION,NU_FOLIO,NU_PESO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('21',null,null,null,null,'144','1',null,'Formato02 (4).pdf','SICOES/aqui/Formato02 (4).pdf','9a72e19a-76d4-4b8e-9d47-7e0b79485f38','SDFSDF','application/pdf',null,null,'1','14327','4',to_timestamp('28/01/23 03:57:07,471000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TR_ARCHIVO (ID_ARCHIVO,ID_DOCUMENTO,ID_OTRO_REQUISITO,ID_ESTUDIO,ID_ESTADO_LD,ID_TIPO_ARCHIVO_LD,ID_SOLICITUD,NO_ARCHIVO,NO_REAL,NO_ALFRESCO,CO_ARCHIVO,DE_ARCHIVO,CO_TIPO_ARCHIVO,NU_CORRELATIVO,NU_VERSION,NU_FOLIO,NU_PESO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('22',null,null,null,null,'144','1',null,'Formato04 (15).pdf','SICOES/aqui/Formato04 (15).pdf','77e1b24d-4746-4bad-b261-9ddbfad641df','SDFSDF','application/pdf',null,null,'1','15155','4',to_timestamp('28/01/23 03:57:12,675000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TR_ARCHIVO (ID_ARCHIVO,ID_DOCUMENTO,ID_OTRO_REQUISITO,ID_ESTUDIO,ID_ESTADO_LD,ID_TIPO_ARCHIVO_LD,ID_SOLICITUD,NO_ARCHIVO,NO_REAL,NO_ALFRESCO,CO_ARCHIVO,DE_ARCHIVO,CO_TIPO_ARCHIVO,NU_CORRELATIVO,NU_VERSION,NU_FOLIO,NU_PESO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('1',null,null,'5',null,'105','1','Grado_Academico_D_05_v1.pdf','Formato02 (4).pdf','SICOES/aqui/Formato02 (4).pdf','f0badea2-0434-43b5-b92a-9b0031b751f1',null,'application/pdf','5','1','1','14327','12',to_timestamp('28/01/23 03:19:52,576000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','12',to_timestamp('28/01/23 03:22:55,035000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ARCHIVO (ID_ARCHIVO,ID_DOCUMENTO,ID_OTRO_REQUISITO,ID_ESTUDIO,ID_ESTADO_LD,ID_TIPO_ARCHIVO_LD,ID_SOLICITUD,NO_ARCHIVO,NO_REAL,NO_ALFRESCO,CO_ARCHIVO,DE_ARCHIVO,CO_TIPO_ARCHIVO,NU_CORRELATIVO,NU_VERSION,NU_FOLIO,NU_PESO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('2',null,null,'6',null,'105','1','Grado_Academico_D_01_v1.pdf','Formato02 (3).pdf','SICOES/aqui/Formato02 (3).pdf','c6328740-e65b-45aa-802e-b95d5f52cf69',null,'application/pdf','1','1','1','14769','12',to_timestamp('28/01/23 03:20:36,999000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','12',to_timestamp('28/01/23 03:22:55,026000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ARCHIVO (ID_ARCHIVO,ID_DOCUMENTO,ID_OTRO_REQUISITO,ID_ESTUDIO,ID_ESTADO_LD,ID_TIPO_ARCHIVO_LD,ID_SOLICITUD,NO_ARCHIVO,NO_REAL,NO_ALFRESCO,CO_ARCHIVO,DE_ARCHIVO,CO_TIPO_ARCHIVO,NU_CORRELATIVO,NU_VERSION,NU_FOLIO,NU_PESO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('3',null,null,'7',null,'105','1','Grado_Academico_D_02_v1.pdf','Formato02 (3).pdf','SICOES/aqui/Formato02 (3).pdf','b54a5d8a-1c7b-4689-9462-60114121b2a8',null,'application/pdf','2','1','1','14769','12',to_timestamp('28/01/23 03:20:52,689000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','12',to_timestamp('28/01/23 03:22:55,028000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ARCHIVO (ID_ARCHIVO,ID_DOCUMENTO,ID_OTRO_REQUISITO,ID_ESTUDIO,ID_ESTADO_LD,ID_TIPO_ARCHIVO_LD,ID_SOLICITUD,NO_ARCHIVO,NO_REAL,NO_ALFRESCO,CO_ARCHIVO,DE_ARCHIVO,CO_TIPO_ARCHIVO,NU_CORRELATIVO,NU_VERSION,NU_FOLIO,NU_PESO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('4',null,null,'8',null,'105','1','Grado_Academico_D_03_v1.pdf','Formato02 (2).pdf','SICOES/aqui/Formato02 (2).pdf','bd9665aa-a797-4734-9b18-e54819bf3714',null,'application/pdf','3','1','1','14328','12',to_timestamp('28/01/23 03:21:07,108000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','12',to_timestamp('28/01/23 03:22:55,030000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ARCHIVO (ID_ARCHIVO,ID_DOCUMENTO,ID_OTRO_REQUISITO,ID_ESTUDIO,ID_ESTADO_LD,ID_TIPO_ARCHIVO_LD,ID_SOLICITUD,NO_ARCHIVO,NO_REAL,NO_ALFRESCO,CO_ARCHIVO,DE_ARCHIVO,CO_TIPO_ARCHIVO,NU_CORRELATIVO,NU_VERSION,NU_FOLIO,NU_PESO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('5',null,null,'9',null,'105','1','Grado_Academico_D_04_v1.pdf','Formato02 (2).pdf','SICOES/aqui/Formato02 (2).pdf','2c2ff040-ddb1-4328-b971-fac9ee449dc1',null,'application/pdf','4','1','1','14328','12',to_timestamp('28/01/23 03:21:22,940000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','12',to_timestamp('28/01/23 03:22:55,033000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ARCHIVO (ID_ARCHIVO,ID_DOCUMENTO,ID_OTRO_REQUISITO,ID_ESTUDIO,ID_ESTADO_LD,ID_TIPO_ARCHIVO_LD,ID_SOLICITUD,NO_ARCHIVO,NO_REAL,NO_ALFRESCO,CO_ARCHIVO,DE_ARCHIVO,CO_TIPO_ARCHIVO,NU_CORRELATIVO,NU_VERSION,NU_FOLIO,NU_PESO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('6',null,null,'10',null,'113','1','Capacitacion_Diplomado_01_v1.pdf','Formato02 (4).pdf','SICOES/aqui/Formato02 (4).pdf','85f19de2-6c5a-42e0-a2d4-c3833b0e8015',null,'application/pdf','1','1','1','14327','12',to_timestamp('28/01/23 03:21:46,887000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','12',to_timestamp('28/01/23 03:22:55,042000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ARCHIVO (ID_ARCHIVO,ID_DOCUMENTO,ID_OTRO_REQUISITO,ID_ESTUDIO,ID_ESTADO_LD,ID_TIPO_ARCHIVO_LD,ID_SOLICITUD,NO_ARCHIVO,NO_REAL,NO_ALFRESCO,CO_ARCHIVO,DE_ARCHIVO,CO_TIPO_ARCHIVO,NU_CORRELATIVO,NU_VERSION,NU_FOLIO,NU_PESO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('7',null,null,'11',null,'113','1','Capacitacion_Diplomado_02_v1.pdf','Formato02 (4).pdf','SICOES/aqui/Formato02 (4).pdf','b33a9d78-8c9b-4d36-a0dc-ed3c9da969b1',null,'application/pdf','2','1','1','14327','12',to_timestamp('28/01/23 03:22:06,842000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','12',to_timestamp('28/01/23 03:22:55,044000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ARCHIVO (ID_ARCHIVO,ID_DOCUMENTO,ID_OTRO_REQUISITO,ID_ESTUDIO,ID_ESTADO_LD,ID_TIPO_ARCHIVO_LD,ID_SOLICITUD,NO_ARCHIVO,NO_REAL,NO_ALFRESCO,CO_ARCHIVO,DE_ARCHIVO,CO_TIPO_ARCHIVO,NU_CORRELATIVO,NU_VERSION,NU_FOLIO,NU_PESO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('8',null,null,'12',null,'113','1','Capacitacion_Seminario_03_v1.pdf','Formato04 (15).pdf','SICOES/aqui/Formato04 (15).pdf','45f040ac-7b80-4c7d-9037-cff408611d3b',null,'application/pdf','3','1','1','15155','12',to_timestamp('28/01/23 03:22:30,288000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','12',to_timestamp('28/01/23 03:22:55,046000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ARCHIVO (ID_ARCHIVO,ID_DOCUMENTO,ID_OTRO_REQUISITO,ID_ESTUDIO,ID_ESTADO_LD,ID_TIPO_ARCHIVO_LD,ID_SOLICITUD,NO_ARCHIVO,NO_REAL,NO_ALFRESCO,CO_ARCHIVO,DE_ARCHIVO,CO_TIPO_ARCHIVO,NU_CORRELATIVO,NU_VERSION,NU_FOLIO,NU_PESO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('9',null,null,'13',null,'113','1','Capacitacion_Seminario_04_v1.pdf','Formato02 (1).pdf','SICOES/aqui/Formato02 (1).pdf','5880910a-b162-4c24-9a5c-64f035c39ac0',null,'application/pdf','4','1','1','14379','12',to_timestamp('28/01/23 03:22:52,428000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','12',to_timestamp('28/01/23 03:22:55,049000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ARCHIVO (ID_ARCHIVO,ID_DOCUMENTO,ID_OTRO_REQUISITO,ID_ESTUDIO,ID_ESTADO_LD,ID_TIPO_ARCHIVO_LD,ID_SOLICITUD,NO_ARCHIVO,NO_REAL,NO_ALFRESCO,CO_ARCHIVO,DE_ARCHIVO,CO_TIPO_ARCHIVO,NU_CORRELATIVO,NU_VERSION,NU_FOLIO,NU_PESO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('10','1',null,null,null,'112','1','Experiencia_Orden de Servicio_001_v1.pdf','Formato02 (3).pdf','SICOES/aqui/Formato02 (3).pdf','843d489b-546b-4971-bcc4-12e57ec4e891',null,'application/pdf','1','1','1','14769','12',to_timestamp('28/01/23 03:23:21,801000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','12',to_timestamp('28/01/23 03:24:14,179000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ARCHIVO (ID_ARCHIVO,ID_DOCUMENTO,ID_OTRO_REQUISITO,ID_ESTUDIO,ID_ESTADO_LD,ID_TIPO_ARCHIVO_LD,ID_SOLICITUD,NO_ARCHIVO,NO_REAL,NO_ALFRESCO,CO_ARCHIVO,DE_ARCHIVO,CO_TIPO_ARCHIVO,NU_CORRELATIVO,NU_VERSION,NU_FOLIO,NU_PESO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('11','2',null,null,null,'112','1','Experiencia_Orden de Servicio_002_v1.pdf','Formato02 (2).pdf','SICOES/aqui/Formato02 (2).pdf','752818f1-c322-463c-8629-bfb1cc543e0c',null,'application/pdf','2','1','1','14328','12',to_timestamp('28/01/23 03:23:46,730000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','12',to_timestamp('28/01/23 03:24:14,180000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ARCHIVO (ID_ARCHIVO,ID_DOCUMENTO,ID_OTRO_REQUISITO,ID_ESTUDIO,ID_ESTADO_LD,ID_TIPO_ARCHIVO_LD,ID_SOLICITUD,NO_ARCHIVO,NO_REAL,NO_ALFRESCO,CO_ARCHIVO,DE_ARCHIVO,CO_TIPO_ARCHIVO,NU_CORRELATIVO,NU_VERSION,NU_FOLIO,NU_PESO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('12','3',null,null,null,'112','1','Experiencia_Orden de Servicio_003_v1.pdf','Formato02 (3).pdf','SICOES/aqui/Formato02 (3).pdf','0f634e70-27db-4f90-8d67-00d9b2f6077a',null,'application/pdf','3','1','1','14769','12',to_timestamp('28/01/23 03:24:11,752000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','12',to_timestamp('28/01/23 03:24:14,183000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ARCHIVO (ID_ARCHIVO,ID_DOCUMENTO,ID_OTRO_REQUISITO,ID_ESTUDIO,ID_ESTADO_LD,ID_TIPO_ARCHIVO_LD,ID_SOLICITUD,NO_ARCHIVO,NO_REAL,NO_ALFRESCO,CO_ARCHIVO,DE_ARCHIVO,CO_TIPO_ARCHIVO,NU_CORRELATIVO,NU_VERSION,NU_FOLIO,NU_PESO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('13',null,'1',null,null,'107','1','DNI_45642656_v1.pdf','Formato02 (3).pdf','SICOES/aqui/Formato02 (3).pdf','9cf66f11-a889-4e7f-993f-043e478b5735',null,'application/pdf','1','1','1','14769','12',to_timestamp('28/01/23 03:24:23,890000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','12',to_timestamp('28/01/23 03:24:40,224000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ARCHIVO (ID_ARCHIVO,ID_DOCUMENTO,ID_OTRO_REQUISITO,ID_ESTUDIO,ID_ESTADO_LD,ID_TIPO_ARCHIVO_LD,ID_SOLICITUD,NO_ARCHIVO,NO_REAL,NO_ALFRESCO,CO_ARCHIVO,DE_ARCHIVO,CO_TIPO_ARCHIVO,NU_CORRELATIVO,NU_VERSION,NU_FOLIO,NU_PESO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('14',null,'2',null,null,'108','1','RUC_10456426561_v1.pdf','Formato02 (3).pdf','SICOES/aqui/Formato02 (3).pdf','e35b5af4-0981-4794-8291-82762b7025b5',null,'application/pdf','2','1','1','14769','12',to_timestamp('28/01/23 03:24:26,866000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','12',to_timestamp('28/01/23 03:24:40,226000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ARCHIVO (ID_ARCHIVO,ID_DOCUMENTO,ID_OTRO_REQUISITO,ID_ESTUDIO,ID_ESTADO_LD,ID_TIPO_ARCHIVO_LD,ID_SOLICITUD,NO_ARCHIVO,NO_REAL,NO_ALFRESCO,CO_ARCHIVO,DE_ARCHIVO,CO_TIPO_ARCHIVO,NU_CORRELATIVO,NU_VERSION,NU_FOLIO,NU_PESO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('15',null,'3',null,null,'109','1','DJ_PN_45642656_v1.pdf','Formato02 (1).pdf','SICOES/aqui/Formato02 (1).pdf','9383b2f6-ca20-4164-b675-4f7505c2ee10',null,'application/pdf','3','1','1','14379','12',to_timestamp('28/01/23 03:24:30,280000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','12',to_timestamp('28/01/23 03:24:40,228000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ARCHIVO (ID_ARCHIVO,ID_DOCUMENTO,ID_OTRO_REQUISITO,ID_ESTUDIO,ID_ESTADO_LD,ID_TIPO_ARCHIVO_LD,ID_SOLICITUD,NO_ARCHIVO,NO_REAL,NO_ALFRESCO,CO_ARCHIVO,DE_ARCHIVO,CO_TIPO_ARCHIVO,NU_CORRELATIVO,NU_VERSION,NU_FOLIO,NU_PESO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('16',null,null,null,null,'545','1','Formato04.pdf','Formato04.pdf','SICOES/aqui/Formato04.pdf','8220192a-e393-491a-a083-2ac560bc3d1b',null,'application/pdf',null,null,null,null,'12',to_timestamp('28/01/23 03:24:42,458000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
REM INSERTING into SICOES.SICOES_TR_ASIGNACION
SET DEFINE OFF;
Insert into SICOES.SICOES_TR_ASIGNACION (ID_ASIGNACION,ID_SOLICITUD,ID_TIPO_LD,ID_USUARIO,ID_GRUPO_LD,ID_EVALUACION_LD,DE_OBSERVACION,FE_APROBACION,NU_DIA_PLAZO_RESP,FE_PLAZO_RESP,FE_REGISTRO,FL_ACTIVO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('1','1','101','4',null,null,null,null,null,null,to_date('28/01/23','DD/MM/RR'),'1','SIAS_JOB',to_timestamp('28/01/23 03:54:43,291000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TR_ASIGNACION (ID_ASIGNACION,ID_SOLICITUD,ID_TIPO_LD,ID_USUARIO,ID_GRUPO_LD,ID_EVALUACION_LD,DE_OBSERVACION,FE_APROBACION,NU_DIA_PLAZO_RESP,FE_PLAZO_RESP,FE_REGISTRO,FL_ACTIVO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('2','1','102','2',null,null,null,null,'3',to_date('31/01/23','DD/MM/RR'),to_date('28/01/23','DD/MM/RR'),'1','SIAS_JOB',to_timestamp('28/01/23 03:57:32,187000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TR_ASIGNACION (ID_ASIGNACION,ID_SOLICITUD,ID_TIPO_LD,ID_USUARIO,ID_GRUPO_LD,ID_EVALUACION_LD,DE_OBSERVACION,FE_APROBACION,NU_DIA_PLAZO_RESP,FE_PLAZO_RESP,FE_REGISTRO,FL_ACTIVO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('3','1','544','10','542',null,null,null,null,null,to_date('28/01/23','DD/MM/RR'),'1','SIAS_JOB',to_timestamp('28/01/23 03:59:13,939000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TR_ASIGNACION (ID_ASIGNACION,ID_SOLICITUD,ID_TIPO_LD,ID_USUARIO,ID_GRUPO_LD,ID_EVALUACION_LD,DE_OBSERVACION,FE_APROBACION,NU_DIA_PLAZO_RESP,FE_PLAZO_RESP,FE_REGISTRO,FL_ACTIVO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('4','1','544','11','543',null,null,null,null,null,to_date('28/01/23','DD/MM/RR'),'1','SIAS_JOB',to_timestamp('28/01/23 03:59:13,951000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
REM INSERTING into SICOES.SICOES_TR_DOCUMENTO
SET DEFINE OFF;
Insert into SICOES.SICOES_TR_DOCUMENTO (ID_DOCUMENTO,ID_TIPO_LD,ID_TIPO_DOCUMENTO_LD,ID_TIPO_CAMBIO_LD,ID_SOLICITUD,ID_DOCUMENTO_PADRE,NU_DOCUMENTO,CO_CONTRATO,DE_CONTRATO,FE_INICIO_CONTRATO,FE_FIN_CONTRATO,DE_DURACION,FL_VIGENTE,FE_CONFORMIDAD,MO_CONTRATO,MO_TIPO_CAMBIO,ID_EVALUACION_LD,ID_PAIS_LD,ID_TIPO_ID_TRIBURARIO_LD,ID_CONFORMIDAD_LD,MO_CONTRATO_SOL,DE_OBSERVACION,NO_ENTIDAD,FL_SIGED,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('1',null,'5',null,'1',null,'10456426561',null,'sjhsjdklfsd',to_date('27/01/23','DD/MM/RR'),null,'1 día','1',null,null,null,'81',null,null,null,null,'SDFSDF','ROSALES HUERTAS LUIS ALBERTO','1','12',to_timestamp('28/01/23 03:23:23,366000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','2',to_timestamp('28/01/23 03:58:13,205000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_DOCUMENTO (ID_DOCUMENTO,ID_TIPO_LD,ID_TIPO_DOCUMENTO_LD,ID_TIPO_CAMBIO_LD,ID_SOLICITUD,ID_DOCUMENTO_PADRE,NU_DOCUMENTO,CO_CONTRATO,DE_CONTRATO,FE_INICIO_CONTRATO,FE_FIN_CONTRATO,DE_DURACION,FL_VIGENTE,FE_CONFORMIDAD,MO_CONTRATO,MO_TIPO_CAMBIO,ID_EVALUACION_LD,ID_PAIS_LD,ID_TIPO_ID_TRIBURARIO_LD,ID_CONFORMIDAD_LD,MO_CONTRATO_SOL,DE_OBSERVACION,NO_ENTIDAD,FL_SIGED,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('2',null,'5',null,'1',null,'10456426561',null,'323232',to_date('13/01/23','DD/MM/RR'),to_date('27/01/23','DD/MM/RR'),'14 días','0',null,null,null,'81',null,null,null,null,'SDFSD','ROSALES HUERTAS LUIS ALBERTO','1','12',to_timestamp('28/01/23 03:23:49,716000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','2',to_timestamp('28/01/23 03:58:19,094000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_DOCUMENTO (ID_DOCUMENTO,ID_TIPO_LD,ID_TIPO_DOCUMENTO_LD,ID_TIPO_CAMBIO_LD,ID_SOLICITUD,ID_DOCUMENTO_PADRE,NU_DOCUMENTO,CO_CONTRATO,DE_CONTRATO,FE_INICIO_CONTRATO,FE_FIN_CONTRATO,DE_DURACION,FL_VIGENTE,FE_CONFORMIDAD,MO_CONTRATO,MO_TIPO_CAMBIO,ID_EVALUACION_LD,ID_PAIS_LD,ID_TIPO_ID_TRIBURARIO_LD,ID_CONFORMIDAD_LD,MO_CONTRATO_SOL,DE_OBSERVACION,NO_ENTIDAD,FL_SIGED,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('3',null,'5',null,'1',null,'10456426561',null,'dsfsdfsdf',to_date('01/01/23','DD/MM/RR'),null,'27 días','1',null,null,null,'79',null,null,null,null,'SDFSDF','ROSALES HUERTAS LUIS ALBERTO','1','12',to_timestamp('28/01/23 03:24:14,169000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','2',to_timestamp('28/01/23 03:58:24,342000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
REM INSERTING into SICOES.SICOES_TR_ESTUDIO
SET DEFINE OFF;
Insert into SICOES.SICOES_TR_ESTUDIO (ID_ESTUDIO,ID_TIPO_LD,FL_EGRESADO,DE_COLEGIATURA,DE_ESPECIALIDAD,FE_VIGENCIA_GRADO,NO_INSTITUCION,FL_COLEGIATURA,FE_VIGENCIA_COLEGIATURA,NO_INSTITUCION_COLEGIATURA,NU_HORA,FE_VIGENCIA,FE_INICIO,FE_FIN,ID_EVALUACION_LD,ID_SOLICITUD,ID_ESTUDIO_PADRE,ID_TIPO_ESTUDIO_LD,ID_FUENTE_LD,DE_OBSERVACION,FL_ACTIVO,FL_SIGED,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('6','92','1',null,'rdgrt',to_date('03/01/23','DD/MM/RR'),'3232','0',null,null,null,null,null,null,'81','1',null,'121','124','SDFSDF',null,'1','SIAS_JOB',to_timestamp('28/01/23 03:20:40,184000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','4',to_timestamp('28/01/23 03:56:16,669000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ESTUDIO (ID_ESTUDIO,ID_TIPO_LD,FL_EGRESADO,DE_COLEGIATURA,DE_ESPECIALIDAD,FE_VIGENCIA_GRADO,NO_INSTITUCION,FL_COLEGIATURA,FE_VIGENCIA_COLEGIATURA,NO_INSTITUCION_COLEGIATURA,NU_HORA,FE_VIGENCIA,FE_INICIO,FE_FIN,ID_EVALUACION_LD,ID_SOLICITUD,ID_ESTUDIO_PADRE,ID_TIPO_ESTUDIO_LD,ID_FUENTE_LD,DE_OBSERVACION,FL_ACTIVO,FL_SIGED,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('7','92','0',null,'323',to_date('03/01/23','DD/MM/RR'),'sdddfs','0',null,null,null,null,null,null,'81','1',null,'121','124','SDFSDF',null,'1','SIAS_JOB',to_timestamp('28/01/23 03:20:54,732000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','4',to_timestamp('28/01/23 03:56:21,549000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ESTUDIO (ID_ESTUDIO,ID_TIPO_LD,FL_EGRESADO,DE_COLEGIATURA,DE_ESPECIALIDAD,FE_VIGENCIA_GRADO,NO_INSTITUCION,FL_COLEGIATURA,FE_VIGENCIA_COLEGIATURA,NO_INSTITUCION_COLEGIATURA,NU_HORA,FE_VIGENCIA,FE_INICIO,FE_FIN,ID_EVALUACION_LD,ID_SOLICITUD,ID_ESTUDIO_PADRE,ID_TIPO_ESTUDIO_LD,ID_FUENTE_LD,DE_OBSERVACION,FL_ACTIVO,FL_SIGED,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('8','92','1',null,'sada',to_date('12/01/23','DD/MM/RR'),'sdsdds','0',null,null,null,null,null,null,'79','1',null,'121','124','SDFSFD',null,'1','SIAS_JOB',to_timestamp('28/01/23 03:21:09,387000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','4',to_timestamp('28/01/23 03:56:27,389000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ESTUDIO (ID_ESTUDIO,ID_TIPO_LD,FL_EGRESADO,DE_COLEGIATURA,DE_ESPECIALIDAD,FE_VIGENCIA_GRADO,NO_INSTITUCION,FL_COLEGIATURA,FE_VIGENCIA_COLEGIATURA,NO_INSTITUCION_COLEGIATURA,NU_HORA,FE_VIGENCIA,FE_INICIO,FE_FIN,ID_EVALUACION_LD,ID_SOLICITUD,ID_ESTUDIO_PADRE,ID_TIPO_ESTUDIO_LD,ID_FUENTE_LD,DE_OBSERVACION,FL_ACTIVO,FL_SIGED,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('9','92','0',null,'dsds',to_date('05/01/23','DD/MM/RR'),'sdsd','0',null,null,null,null,null,null,'80','1',null,'121','124','SDFSDF',null,'1','SIAS_JOB',to_timestamp('28/01/23 03:21:24,190000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','4',to_timestamp('28/01/23 03:56:32,451000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ESTUDIO (ID_ESTUDIO,ID_TIPO_LD,FL_EGRESADO,DE_COLEGIATURA,DE_ESPECIALIDAD,FE_VIGENCIA_GRADO,NO_INSTITUCION,FL_COLEGIATURA,FE_VIGENCIA_COLEGIATURA,NO_INSTITUCION_COLEGIATURA,NU_HORA,FE_VIGENCIA,FE_INICIO,FE_FIN,ID_EVALUACION_LD,ID_SOLICITUD,ID_ESTUDIO_PADRE,ID_TIPO_ESTUDIO_LD,ID_FUENTE_LD,DE_OBSERVACION,FL_ACTIVO,FL_SIGED,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('5','92','0',null,'sdfds',to_date('01/01/23','DD/MM/RR'),'135','0',null,null,null,null,null,null,'82','1',null,'121','124','SDFSDF',null,'1','SIAS_JOB',to_timestamp('28/01/23 03:19:58,591000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','4',to_timestamp('28/01/23 03:56:37,297000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ESTUDIO (ID_ESTUDIO,ID_TIPO_LD,FL_EGRESADO,DE_COLEGIATURA,DE_ESPECIALIDAD,FE_VIGENCIA_GRADO,NO_INSTITUCION,FL_COLEGIATURA,FE_VIGENCIA_COLEGIATURA,NO_INSTITUCION_COLEGIATURA,NU_HORA,FE_VIGENCIA,FE_INICIO,FE_FIN,ID_EVALUACION_LD,ID_SOLICITUD,ID_ESTUDIO_PADRE,ID_TIPO_ESTUDIO_LD,ID_FUENTE_LD,DE_OBSERVACION,FL_ACTIVO,FL_SIGED,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('10','7',null,null,'qedwsew',null,'weewe',null,null,null,'232332',to_date('04/01/23','DD/MM/RR'),to_date('20/01/23','DD/MM/RR'),to_date('26/01/23','DD/MM/RR'),'81','1',null,'122','124','SDFSDF',null,'1','SIAS_JOB',to_timestamp('28/01/23 03:21:48,509000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','2',to_timestamp('28/01/23 03:57:47,332000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ESTUDIO (ID_ESTUDIO,ID_TIPO_LD,FL_EGRESADO,DE_COLEGIATURA,DE_ESPECIALIDAD,FE_VIGENCIA_GRADO,NO_INSTITUCION,FL_COLEGIATURA,FE_VIGENCIA_COLEGIATURA,NO_INSTITUCION_COLEGIATURA,NU_HORA,FE_VIGENCIA,FE_INICIO,FE_FIN,ID_EVALUACION_LD,ID_SOLICITUD,ID_ESTUDIO_PADRE,ID_TIPO_ESTUDIO_LD,ID_FUENTE_LD,DE_OBSERVACION,FL_ACTIVO,FL_SIGED,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('11','7',null,null,'kjhlhjl',null,'hjkghjk',null,null,null,'3454',to_date('02/01/23','DD/MM/RR'),to_date('06/01/23','DD/MM/RR'),to_date('12/01/23','DD/MM/RR'),'79','1',null,'122','124','SDFSF',null,'1','SIAS_JOB',to_timestamp('28/01/23 03:22:08,278000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','2',to_timestamp('28/01/23 03:57:53,810000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ESTUDIO (ID_ESTUDIO,ID_TIPO_LD,FL_EGRESADO,DE_COLEGIATURA,DE_ESPECIALIDAD,FE_VIGENCIA_GRADO,NO_INSTITUCION,FL_COLEGIATURA,FE_VIGENCIA_COLEGIATURA,NO_INSTITUCION_COLEGIATURA,NU_HORA,FE_VIGENCIA,FE_INICIO,FE_FIN,ID_EVALUACION_LD,ID_SOLICITUD,ID_ESTUDIO_PADRE,ID_TIPO_ESTUDIO_LD,ID_FUENTE_LD,DE_OBSERVACION,FL_ACTIVO,FL_SIGED,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('12','9',null,null,'dggdd',null,'fgdgdfgf',null,null,null,'343434',to_date('01/01/23','DD/MM/RR'),to_date('05/01/23','DD/MM/RR'),to_date('13/01/23','DD/MM/RR'),'81','1',null,'122','124','SDF',null,'1','SIAS_JOB',to_timestamp('28/01/23 03:22:32,550000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','2',to_timestamp('28/01/23 03:58:00,797000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_ESTUDIO (ID_ESTUDIO,ID_TIPO_LD,FL_EGRESADO,DE_COLEGIATURA,DE_ESPECIALIDAD,FE_VIGENCIA_GRADO,NO_INSTITUCION,FL_COLEGIATURA,FE_VIGENCIA_COLEGIATURA,NO_INSTITUCION_COLEGIATURA,NU_HORA,FE_VIGENCIA,FE_INICIO,FE_FIN,ID_EVALUACION_LD,ID_SOLICITUD,ID_ESTUDIO_PADRE,ID_TIPO_ESTUDIO_LD,ID_FUENTE_LD,DE_OBSERVACION,FL_ACTIVO,FL_SIGED,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('13','9',null,null,'rgtrrt',null,'rtrt',null,null,null,'344',to_date('01/01/23','DD/MM/RR'),to_date('04/01/23','DD/MM/RR'),to_date('12/01/23','DD/MM/RR'),'80','1',null,'122','124','SDFSDF',null,'1','SIAS_JOB',to_timestamp('28/01/23 03:22:55,017000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','2',to_timestamp('28/01/23 03:58:07,243000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
REM INSERTING into SICOES.SICOES_TR_NOTIFICACION
SET DEFINE OFF;
Insert into SICOES.SICOES_TR_NOTIFICACION (ID_NOTIFICACION,ID_ESTADO_LD,CORREO,ASUNTO,MENSAJE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('1','88','larh0510@gmail.com','Validación de Correo Electrónico','<!DOCTYPE html>
<html lang="es" xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8"></meta>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"></meta>
</head>
<body style="background: #efeeed;font-family: Arial, Verdana, Helvetica, sans-serif;margin: 0;padding: 0; ">
	<div style="max-width: 600px;width: 100%;margin: 0 auto;min-width:320px;">
		<div style="font-family: Arial, Verdana, Helvetica, sans-serif;font-weight: normal;font-size: 11px;line-height: 12px;color: #6e6e6e;text-align: center;margin: 0;padding: 5px 0 0px;">
		</div>
		<div style="background-color: #fff;padding: 25px 18px 5px 18px;">
			<div style="color: #121212;font-family: Helvetica,Arial,sans-serif;font-size: 14px;font-weight: 400;line-height: 1.3;margin: 0;padding: 10px 0px 0px 0px;text-align: left;">
				<p style="margin: 0;margin-bottom: 0;margin-left: 32px;margin-right: 32px;color: inherit;font-family: Helvetica,Arial,sans-serif;font-size: 34px;font-weight: 700;line-height: 25px!important;margin: 0;margin-bottom: 0;margin-left: 32px;margin-right: 32px;padding: 0;text-align: left;word-wrap: normal;">			
					<span style="color: #104971;font-size: 14px;font-family: Arial;font-weight: bold">Estimado(a):</span>

				</p>
			</div>
			<div style="font-size: 13px;color: #0984C2;font-family: Arial;font-weight: 400;line-height: 19px;margin: 0;margin-bottom: 0px;margin-left: 32px;margin-right: 32px;padding: 0;padding-top: 17px;text-align: justify;">
				<p>El código de verificación es: 359793</p>
				<p>De tener alguna consulta puede efectuarla a través de: SICOES@osinergmin.gob.pe</p>
			</div>
		</div>	
		<div style="background-color: #fff;padding: 0px 18px 5px 18px;">
			<div style="font-size: 11px;color: #0984C2;font-family: Arial;font-weight: 400;line-height: 19px;margin: 0;margin-bottom: 20px;margin-left: 32px;margin-right: 32px;padding: 0;padding-top: 17px;text-align: justify;">
				<p>
					<br>
					<span style="font-weight: bold"></span><br>	
					Atentamente,<br>
					Sistema de Registro y Contratación de Empresas Supervisoras<br>
					Gerencia de Administración y Finanzas<br>
					Osinergmin <br>
					<a href="https://www.mimp.gob.pe" style="color: #104971">www.sias.gob.pe</a>
				</p>			
			</div>	
		</div>
		<div style="background-color: #fff;padding: 25px 15px 30px 15px;text-align: center;">
		</div>
		<div style="font-family: Arial, Verdana, Helvetica, sans-serif;font-weight: normal;font-size: 11px;line-height: 12px;color: #6e6e6e;text-align: center;margin: 0;padding: 5px 0 0px;">
		</div>
	</div>
</body>
</html>','SIAS_JOB',to_timestamp('28/01/23 03:19:00,650000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TR_NOTIFICACION (ID_NOTIFICACION,ID_ESTADO_LD,CORREO,ASUNTO,MENSAJE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('2','88','desarrollador.celsat001@gmail.com;desarrollador.celsat001@gmail.com','Nueva solicitud de Inscripción en el Registro de Empresa Supervisora - RUC 45642656-null','<!DOCTYPE html>
<html lang="es" xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8"></meta>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"></meta>
</head>
<body style="background: #efeeed;font-family: Arial, Verdana, Helvetica, sans-serif;margin: 0;padding: 0; ">
	<div style="max-width: 600px;width: 100%;margin: 0 auto;min-width:320px;">
		<div style="font-family: Arial, Verdana, Helvetica, sans-serif;font-weight: normal;font-size: 11px;line-height: 12px;color: #6e6e6e;text-align: center;margin: 0;padding: 5px 0 0px;">
		</div>
		<div style="background-color: #fff;padding: 25px 18px 5px 18px;">
			<div style="color: #121212;font-family: Helvetica,Arial,sans-serif;font-size: 14px;font-weight: 400;line-height: 1.3;margin: 0;padding: 10px 0px 0px 0px;text-align: left;">
				<p style="margin: 0;margin-bottom: 0;margin-left: 32px;margin-right: 32px;color: inherit;font-family: Helvetica,Arial,sans-serif;font-size: 34px;font-weight: 700;line-height: 25px!important;margin: 0;margin-bottom: 0;margin-left: 32px;margin-right: 32px;padding: 0;text-align: left;word-wrap: normal;">			
					<span style="color: #104971;font-size: 14px;font-family: Arial;font-weight: bold">Estimado(a):</span>

				</p>
			</div>
			<div style="font-size: 13px;color: #0984C2;font-family: Arial;font-weight: 400;line-height: 19px;margin: 0;margin-bottom: 0px;margin-left: 32px;margin-right: 32px;padding: 0;padding-top: 17px;text-align: justify;">
				<p>Se informa que se ha recibido una nueva solicitud de Inscripción en el Registro de Empresa Supervisora:</p>
				<table style="font-size: 13px;color: #0984C2">

					<tr><td>Número de RUC</td><td>:</td><td>&nbsp;45642656</td></tr>

					<tr><td>Fecha de Ingreso:</td><td>:</td><td>28/01/2023</td></tr>
					<tr ><td>Plazo máximo para dar respuesta a ES:</td><td>:</td><td>03/02/2023</td></tr>
				</table>

				<p style="color: #0984C2;">Sistema de Contratación de Empresas Supervisoras</p>
			</div>
		</div>	
		<div style="background-color: #fff;padding: 0px 18px 5px 18px;">
			<div style="font-size: 11px;color: #0984C2;font-family: Arial;font-weight: 400;line-height: 19px;margin: 0;margin-bottom: 20px;margin-left: 32px;margin-right: 32px;padding: 0;padding-top: 17px;text-align: justify;">
				<p>
					<br>
					<span style="font-weight: bold"></span><br>	
					Atentamente,<br>
					Sistema de Registro y Contratación de Empresas Supervisoras<br>
					Gerencia de Administración y Finanzas<br>
					Osinergmin 
				</p>				
			</div>	
		</div>
		<div style="font-family: Arial, Verdana, Helvetica, sans-serif;font-weight: normal;font-size: 11px;line-height: 12px;color: #6e6e6e;text-align: center;margin: 0;padding: 5px 0 0px;">
		</div>
	</div>
</body>
</html>','SIAS_JOB',to_timestamp('28/01/23 03:19:13,799000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TR_NOTIFICACION (ID_NOTIFICACION,ID_ESTADO_LD,CORREO,ASUNTO,MENSAJE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('23','88','desarrollador.celsat001@gmail.com;desarrollador.celsat001@gmail.com','Asignación de aprobación de solicitud de Inscripción en el Registro de Empresa Supervisora - RUC45642656-202000014915','<!DOCTYPE html>
<html lang="es" xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8"></meta>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"></meta>
</head>
<body style="background: #efeeed;font-family: Arial, Verdana, Helvetica, sans-serif;margin: 0;padding: 0; ">
	<div style="max-width: 600px;width: 100%;margin: 0 auto;min-width:320px;">
		<div style="font-family: Arial, Verdana, Helvetica, sans-serif;font-weight: normal;font-size: 11px;line-height: 12px;color: #6e6e6e;text-align: center;margin: 0;padding: 5px 0 0px;">
		</div>
		<div style="background-color: #fff;padding: 25px 18px 5px 18px;">
			<div style="color: #121212;font-family: Helvetica,Arial,sans-serif;font-size: 14px;font-weight: 400;line-height: 1.3;margin: 0;padding: 10px 0px 0px 0px;text-align: left;">
				<p style="margin: 0;margin-bottom: 0;margin-left: 32px;margin-right: 32px;color: inherit;font-family: Helvetica,Arial,sans-serif;font-size: 34px;font-weight: 700;line-height: 25px!important;margin: 0;margin-bottom: 0;margin-left: 32px;margin-right: 32px;padding: 0;text-align: left;word-wrap: normal;">			
					<span style="color: #104971;font-size: 14px;font-family: Arial;font-weight: bold">Estimado(a):</span>

				</p>
			</div>
			<div style="font-size: 13px;color: #0984C2;font-family: Arial;font-weight: 400;line-height: 19px;margin: 0;margin-bottom: 0px;margin-left: 32px;margin-right: 32px;padding: 0;padding-top: 17px;text-align: justify;">
				<p>Se informa que se le ha asignado la aprobación técnica de la solicitud de Inscripción en el Registro de Empresa Supervisora:</p>
				<table style="font-size: 13px;color: #0984C2">
				<tr><td>N° expediente SIGED:</td><td>:</td><td>&nbsp;202000014915</td></tr>
					<tr><td>Número de RUC</td><td>:</td><td>&nbsp;45642656</td></tr>
					
					<tr><td>Fecha de Ingreso:</td><td>:</td><td>28/01/2023</td></tr>
					<tr><td>Plazo máximo para dar respuesta a la empresa supervisora:</td><td>:</td><td>03/02/2023</td></tr>
				</table>
				
				<p style="color: #0984C2;">Sistema de Contratación de Empresas Supervisoras</p>
			</div>
		</div>	
		<div style="background-color: #fff;padding: 0px 18px 5px 18px;">
			<div style="font-size: 11px;color: #0984C2;font-family: Arial;font-weight: 400;line-height: 19px;margin: 0;margin-bottom: 20px;margin-left: 32px;margin-right: 32px;padding: 0;padding-top: 17px;text-align: justify;">
				<p>
					<br>
					<span style="font-weight: bold"></span><br>	
					Atentamente,<br>
					Sistema de Registro y Contratación de Empresas Supervisoras<br>
					Gerencia de Administración y Finanzas<br>
					Osinergmin 
				</p>			
			</div>	
		</div>
		<div style="background-color: #fff;padding: 25px 15px 30px 15px;text-align: center;">
			<img alt="logo" class="footer-brand-logo-img" src="" style="clear: both; display: inline-block; margin-bottom: 5px; max-width: 80%; outline: 0px; text-decoration: none;"></img>
		</div>
		<div style="font-family: Arial, Verdana, Helvetica, sans-serif;font-weight: normal;font-size: 11px;line-height: 12px;color: #6e6e6e;text-align: center;margin: 0;padding: 5px 0 0px;">
		</div>
	</div>
</body>
</html>','SIAS_JOB',to_timestamp('28/01/23 03:59:13,949000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TR_NOTIFICACION (ID_NOTIFICACION,ID_ESTADO_LD,CORREO,ASUNTO,MENSAJE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('24','88','desarrollador.celsat001@gmail.com;desarrollador.celsat001@gmail.com','Asignación de aprobación de solicitud de Inscripción en el Registro de Empresa Supervisora - RUC45642656-202000014915','<!DOCTYPE html>
<html lang="es" xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8"></meta>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"></meta>
</head>
<body style="background: #efeeed;font-family: Arial, Verdana, Helvetica, sans-serif;margin: 0;padding: 0; ">
	<div style="max-width: 600px;width: 100%;margin: 0 auto;min-width:320px;">
		<div style="font-family: Arial, Verdana, Helvetica, sans-serif;font-weight: normal;font-size: 11px;line-height: 12px;color: #6e6e6e;text-align: center;margin: 0;padding: 5px 0 0px;">
		</div>
		<div style="background-color: #fff;padding: 25px 18px 5px 18px;">
			<div style="color: #121212;font-family: Helvetica,Arial,sans-serif;font-size: 14px;font-weight: 400;line-height: 1.3;margin: 0;padding: 10px 0px 0px 0px;text-align: left;">
				<p style="margin: 0;margin-bottom: 0;margin-left: 32px;margin-right: 32px;color: inherit;font-family: Helvetica,Arial,sans-serif;font-size: 34px;font-weight: 700;line-height: 25px!important;margin: 0;margin-bottom: 0;margin-left: 32px;margin-right: 32px;padding: 0;text-align: left;word-wrap: normal;">			
					<span style="color: #104971;font-size: 14px;font-family: Arial;font-weight: bold">Estimado(a):</span>

				</p>
			</div>
			<div style="font-size: 13px;color: #0984C2;font-family: Arial;font-weight: 400;line-height: 19px;margin: 0;margin-bottom: 0px;margin-left: 32px;margin-right: 32px;padding: 0;padding-top: 17px;text-align: justify;">
				<p>Se informa que se le ha asignado la aprobación técnica de la solicitud de Inscripción en el Registro de Empresa Supervisora:</p>
				<table style="font-size: 13px;color: #0984C2">
				<tr><td>N° expediente SIGED:</td><td>:</td><td>&nbsp;202000014915</td></tr>
					<tr><td>Número de RUC</td><td>:</td><td>&nbsp;45642656</td></tr>
					
					<tr><td>Fecha de Ingreso:</td><td>:</td><td>28/01/2023</td></tr>
					<tr><td>Plazo máximo para dar respuesta a la empresa supervisora:</td><td>:</td><td>03/02/2023</td></tr>
				</table>
				
				<p style="color: #0984C2;">Sistema de Contratación de Empresas Supervisoras</p>
			</div>
		</div>	
		<div style="background-color: #fff;padding: 0px 18px 5px 18px;">
			<div style="font-size: 11px;color: #0984C2;font-family: Arial;font-weight: 400;line-height: 19px;margin: 0;margin-bottom: 20px;margin-left: 32px;margin-right: 32px;padding: 0;padding-top: 17px;text-align: justify;">
				<p>
					<br>
					<span style="font-weight: bold"></span><br>	
					Atentamente,<br>
					Sistema de Registro y Contratación de Empresas Supervisoras<br>
					Gerencia de Administración y Finanzas<br>
					Osinergmin 
				</p>			
			</div>	
		</div>
		<div style="background-color: #fff;padding: 25px 15px 30px 15px;text-align: center;">
			<img alt="logo" class="footer-brand-logo-img" src="" style="clear: both; display: inline-block; margin-bottom: 5px; max-width: 80%; outline: 0px; text-decoration: none;"></img>
		</div>
		<div style="font-family: Arial, Verdana, Helvetica, sans-serif;font-weight: normal;font-size: 11px;line-height: 12px;color: #6e6e6e;text-align: center;margin: 0;padding: 5px 0 0px;">
		</div>
	</div>
</body>
</html>','SIAS_JOB',to_timestamp('28/01/23 03:59:13,961000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TR_NOTIFICACION (ID_NOTIFICACION,ID_ESTADO_LD,CORREO,ASUNTO,MENSAJE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('21','88','CELSAT@GMAIL.COM','Asignación de evaluación de solicitud de Inscripción en el Registro de Empresa Supervisora - RUC45642656-202000014915','<!DOCTYPE html>
<html lang="es" xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8"></meta>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"></meta>
</head>
<body style="background: #efeeed;font-family: Arial, Verdana, Helvetica, sans-serif;margin: 0;padding: 0; ">
	<div style="max-width: 600px;width: 100%;margin: 0 auto;min-width:320px;">
		<div style="font-family: Arial, Verdana, Helvetica, sans-serif;font-weight: normal;font-size: 11px;line-height: 12px;color: #6e6e6e;text-align: center;margin: 0;padding: 5px 0 0px;">
		</div>
		<div style="background-color: #fff;padding: 25px 18px 5px 18px;">
			<div style="color: #121212;font-family: Helvetica,Arial,sans-serif;font-size: 14px;font-weight: 400;line-height: 1.3;margin: 0;padding: 10px 0px 0px 0px;text-align: left;">
				<p style="margin: 0;margin-bottom: 0;margin-left: 32px;margin-right: 32px;color: inherit;font-family: Helvetica,Arial,sans-serif;font-size: 34px;font-weight: 700;line-height: 25px!important;margin: 0;margin-bottom: 0;margin-left: 32px;margin-right: 32px;padding: 0;text-align: left;word-wrap: normal;">			
					<span style="color: #104971;font-size: 14px;font-family: Arial;font-weight: bold">Estimado(a):</span>

				</p>
			</div>
			<div style="font-size: 13px;color: #0984C2;font-family: Arial;font-weight: 400;line-height: 19px;margin: 0;margin-bottom: 0px;margin-left: 32px;margin-right: 32px;padding: 0;padding-top: 17px;text-align: justify;">
				<p>Se informa que se le ha asignado la evaluación administrativa de la [solicitud de Inscripción en el Registro de Empresa Supervisora o solicitud de modificación en el Registro de Empresa Supervisora]:</p>
				<table style="font-size: 13px;color: #0984C2">
				<tr><td>N° expediente SIGED:</td><td>:</td><td>&nbsp;202000014915</td></tr>
					<tr><td>Número de RUC</td><td>:</td><td>&nbsp;45642656</td></tr>
					
					<tr><td>Fecha de Ingreso:</td><td>:</td><td>28/01/2023</td></tr>
					<tr><td>Etapa: </td><td>:</td><td>Por Asignar</td></tr>
					<tr><td>Plazo máximo para concluir evaluación:</td><td>:</td><td>29/01/2023</td></tr>
				</table>
				
				<p style="color: #0984C2;">Sistema de Contratación de Empresas Supervisoras</p>
			</div>
		</div>	
		<div style="background-color: #fff;padding: 0px 18px 5px 18px;">
			<div style="font-size: 11px;color: #0984C2;font-family: Arial;font-weight: 400;line-height: 19px;margin: 0;margin-bottom: 20px;margin-left: 32px;margin-right: 32px;padding: 0;padding-top: 17px;text-align: justify;">
				<p>
					<br>
					<span style="font-weight: bold"></span><br>	
					Atentamente,<br>
					Sistema de Registro y Contratación de Empresas Supervisoras<br>
					Gerencia de Administración y Finanzas<br>
					Osinergmin 
				</p>		
			</div>	
		</div>
		<div style="background-color: #fff;padding: 25px 15px 30px 15px;text-align: center;">
			<img alt="logo" class="footer-brand-logo-img" src="" style="clear: both; display: inline-block; margin-bottom: 5px; max-width: 80%; outline: 0px; text-decoration: none;"></img>
		</div>
		<div style="font-family: Arial, Verdana, Helvetica, sans-serif;font-weight: normal;font-size: 11px;line-height: 12px;color: #6e6e6e;text-align: center;margin: 0;padding: 5px 0 0px;">
		</div>
	</div>
</body>
</html>','SIAS_JOB',to_timestamp('28/01/23 03:54:43,323000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TR_NOTIFICACION (ID_NOTIFICACION,ID_ESTADO_LD,CORREO,ASUNTO,MENSAJE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('22','88','CELSAT@GMAIL.COM','Asignación de evaluación de solicitud de Inscripción en el Registro de Empresa Supervisora - RUC45642656-202000014915','<!DOCTYPE html>
<html lang="es" xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8"></meta>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"></meta>
</head>
<body style="background: #efeeed;font-family: Arial, Verdana, Helvetica, sans-serif;margin: 0;padding: 0; ">
	<div style="max-width: 600px;width: 100%;margin: 0 auto;min-width:320px;">
		<div style="font-family: Arial, Verdana, Helvetica, sans-serif;font-weight: normal;font-size: 11px;line-height: 12px;color: #6e6e6e;text-align: center;margin: 0;padding: 5px 0 0px;">
		</div>
		<div style="background-color: #fff;padding: 25px 18px 5px 18px;">
			<div style="color: #121212;font-family: Helvetica,Arial,sans-serif;font-size: 14px;font-weight: 400;line-height: 1.3;margin: 0;padding: 10px 0px 0px 0px;text-align: left;">
				<p style="margin: 0;margin-bottom: 0;margin-left: 32px;margin-right: 32px;color: inherit;font-family: Helvetica,Arial,sans-serif;font-size: 34px;font-weight: 700;line-height: 25px!important;margin: 0;margin-bottom: 0;margin-left: 32px;margin-right: 32px;padding: 0;text-align: left;word-wrap: normal;">			
					<span style="color: #104971;font-size: 14px;font-family: Arial;font-weight: bold">Estimado(a):</span>

				</p>
			</div>
			<div style="font-size: 13px;color: #0984C2;font-family: Arial;font-weight: 400;line-height: 19px;margin: 0;margin-bottom: 0px;margin-left: 32px;margin-right: 32px;padding: 0;padding-top: 17px;text-align: justify;">
				<p>Se informa que se le ha asignado la evaluación técnica de la [solicitud de Inscripción en el Registro de Empresa Supervisora o solicitud de modificación en el Registro de Empresa Supervisora]:</p>
				<table style="font-size: 13px;color: #0984C2">
				<tr><td>N° expediente SIGED:</td><td>:</td><td>&nbsp;202000014915</td></tr>
					<tr><td>Número de RUC</td><td>:</td><td>&nbsp;45642656</td></tr>
					
					<tr><td>Fecha de Ingreso:</td><td>:</td><td>28/01/2023</td></tr>
					<tr><td>Etapa: </td><td>:</td><td>Por Asignar</td></tr>
					<tr><td>Plazo máximo para concluir evaluación:</td><td>:</td><td>29/01/2023</td></tr>
				</table>
				
				<p style="color: #0984C2;">Sistema de Contratación de Empresas Supervisoras</p>
			</div>
		</div>	
		<div style="background-color: #fff;padding: 0px 18px 5px 18px;">
			<div style="font-size: 11px;color: #0984C2;font-family: Arial;font-weight: 400;line-height: 19px;margin: 0;margin-bottom: 20px;margin-left: 32px;margin-right: 32px;padding: 0;padding-top: 17px;text-align: justify;">
				<p>
					<br>
					<span style="font-weight: bold"></span><br>	
					Atentamente,<br>
					Sistema de Registro y Contratación de Empresas Supervisoras<br>
					Gerencia de Administración y Finanzas<br>
					Osinergmin 
				</p>		
			</div>	
		</div>
		<div style="background-color: #fff;padding: 25px 15px 30px 15px;text-align: center;">
			<img alt="logo" class="footer-brand-logo-img" src="" style="clear: both; display: inline-block; margin-bottom: 5px; max-width: 80%; outline: 0px; text-decoration: none;"></img>
		</div>
		<div style="font-family: Arial, Verdana, Helvetica, sans-serif;font-weight: normal;font-size: 11px;line-height: 12px;color: #6e6e6e;text-align: center;margin: 0;padding: 5px 0 0px;">
		</div>
	</div>
</body>
</html>','SIAS_JOB',to_timestamp('28/01/23 03:57:32,201000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TR_NOTIFICACION (ID_NOTIFICACION,ID_ESTADO_LD,CORREO,ASUNTO,MENSAJE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('3','88','desarrollador.celsat001@gmail.com;desarrollador.celsat001@gmail.com','Nueva solicitud de modificación en el Registro de Empresa Supervisora - RUC 45642656-null','<!DOCTYPE html>
<html lang="es" xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8"></meta>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"></meta>
</head>
<body style="background: #efeeed;font-family: Arial, Verdana, Helvetica, sans-serif;margin: 0;padding: 0; ">
	<div style="max-width: 600px;width: 100%;margin: 0 auto;min-width:320px;">
		<div style="font-family: Arial, Verdana, Helvetica, sans-serif;font-weight: normal;font-size: 11px;line-height: 12px;color: #6e6e6e;text-align: center;margin: 0;padding: 5px 0 0px;">
		</div>
		<div style="background-color: #fff;padding: 25px 18px 5px 18px;">
			<div style="color: #121212;font-family: Helvetica,Arial,sans-serif;font-size: 14px;font-weight: 400;line-height: 1.3;margin: 0;padding: 10px 0px 0px 0px;text-align: left;">
				<p style="margin: 0;margin-bottom: 0;margin-left: 32px;margin-right: 32px;color: inherit;font-family: Helvetica,Arial,sans-serif;font-size: 34px;font-weight: 700;line-height: 25px!important;margin: 0;margin-bottom: 0;margin-left: 32px;margin-right: 32px;padding: 0;text-align: left;word-wrap: normal;">			
					<span style="color: #104971;font-size: 14px;font-family: Arial;font-weight: bold">Estimado(a):</span>

				</p>
			</div>
			<div style="font-size: 13px;color: #0984C2;font-family: Arial;font-weight: 400;line-height: 19px;margin: 0;margin-bottom: 0px;margin-left: 32px;margin-right: 32px;padding: 0;padding-top: 17px;text-align: justify;">
				<p>Se informa que se ha recibido una nueva solicitud de modificación en el Registro de Empresa Supervisora:</p>
				<table style="font-size: 13px;color: #0984C2">

					<tr><td>Número de RUC</td><td>:</td><td>&nbsp;45642656</td></tr>

					<tr><td>Fecha de Ingreso:</td><td>:</td><td>28/01/2023</td></tr>
					<tr ><td>Plazo máximo para dar respuesta a ES:</td><td>:</td><td>03/02/2023</td></tr>
				</table>

				<p style="color: #0984C2;">Sistema de Contratación de Empresas Supervisoras</p>
			</div>
		</div>	
		<div style="background-color: #fff;padding: 0px 18px 5px 18px;">
			<div style="font-size: 11px;color: #0984C2;font-family: Arial;font-weight: 400;line-height: 19px;margin: 0;margin-bottom: 20px;margin-left: 32px;margin-right: 32px;padding: 0;padding-top: 17px;text-align: justify;">
				<p>
					<br>
					<span style="font-weight: bold"></span><br>	
					Atentamente,<br>
					Sistema de Registro y Contratación de Empresas Supervisoras<br>
					Gerencia de Administración y Finanzas<br>
					Osinergmin 
				</p>				
			</div>	
		</div>
		<div style="font-family: Arial, Verdana, Helvetica, sans-serif;font-weight: normal;font-size: 11px;line-height: 12px;color: #6e6e6e;text-align: center;margin: 0;padding: 5px 0 0px;">
		</div>
	</div>
</body>
</html>','SIAS_JOB',to_timestamp('28/01/23 03:24:35,388000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TR_NOTIFICACION (ID_NOTIFICACION,ID_ESTADO_LD,CORREO,ASUNTO,MENSAJE,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('4','88','desarrollador.celsat001@gmail.com;desarrollador.celsat001@gmail.com','Nueva solicitud de modificación en el Registro de Empresa Supervisora - RUC 45642656-null','<!DOCTYPE html>
<html lang="es" xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8"></meta>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"></meta>
</head>
<body style="background: #efeeed;font-family: Arial, Verdana, Helvetica, sans-serif;margin: 0;padding: 0; ">
	<div style="max-width: 600px;width: 100%;margin: 0 auto;min-width:320px;">
		<div style="font-family: Arial, Verdana, Helvetica, sans-serif;font-weight: normal;font-size: 11px;line-height: 12px;color: #6e6e6e;text-align: center;margin: 0;padding: 5px 0 0px;">
		</div>
		<div style="background-color: #fff;padding: 25px 18px 5px 18px;">
			<div style="color: #121212;font-family: Helvetica,Arial,sans-serif;font-size: 14px;font-weight: 400;line-height: 1.3;margin: 0;padding: 10px 0px 0px 0px;text-align: left;">
				<p style="margin: 0;margin-bottom: 0;margin-left: 32px;margin-right: 32px;color: inherit;font-family: Helvetica,Arial,sans-serif;font-size: 34px;font-weight: 700;line-height: 25px!important;margin: 0;margin-bottom: 0;margin-left: 32px;margin-right: 32px;padding: 0;text-align: left;word-wrap: normal;">			
					<span style="color: #104971;font-size: 14px;font-family: Arial;font-weight: bold">Estimado(a):</span>

				</p>
			</div>
			<div style="font-size: 13px;color: #0984C2;font-family: Arial;font-weight: 400;line-height: 19px;margin: 0;margin-bottom: 0px;margin-left: 32px;margin-right: 32px;padding: 0;padding-top: 17px;text-align: justify;">
				<p>Se informa que se ha recibido una nueva solicitud de modificación en el Registro de Empresa Supervisora:</p>
				<table style="font-size: 13px;color: #0984C2">

					<tr><td>Número de RUC</td><td>:</td><td>&nbsp;45642656</td></tr>

					<tr><td>Fecha de Ingreso:</td><td>:</td><td>28/01/2023</td></tr>
					<tr ><td>Plazo máximo para dar respuesta a ES:</td><td>:</td><td>03/02/2023</td></tr>
				</table>

				<p style="color: #0984C2;">Sistema de Contratación de Empresas Supervisoras</p>
			</div>
		</div>	
		<div style="background-color: #fff;padding: 0px 18px 5px 18px;">
			<div style="font-size: 11px;color: #0984C2;font-family: Arial;font-weight: 400;line-height: 19px;margin: 0;margin-bottom: 20px;margin-left: 32px;margin-right: 32px;padding: 0;padding-top: 17px;text-align: justify;">
				<p>
					<br>
					<span style="font-weight: bold"></span><br>	
					Atentamente,<br>
					Sistema de Registro y Contratación de Empresas Supervisoras<br>
					Gerencia de Administración y Finanzas<br>
					Osinergmin 
				</p>				
			</div>	
		</div>
		<div style="font-family: Arial, Verdana, Helvetica, sans-serif;font-weight: normal;font-size: 11px;line-height: 12px;color: #6e6e6e;text-align: center;margin: 0;padding: 5px 0 0px;">
		</div>
	</div>
</body>
</html>','SIAS_JOB',to_timestamp('28/01/23 03:24:40,231000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
REM INSERTING into SICOES.SICOES_TR_OTRO_REQUISITO
SET DEFINE OFF;
Insert into SICOES.SICOES_TR_OTRO_REQUISITO (ID_OTRO_REQUISITO,ID_TIPO_LD,ID_TIPO_REQUISITO_LD,ID_SECTOR_LD,ID_SUBSECTOR_LD,ID_ACTIVIDAD_LD,ID_UNIDAD_LD,ID_SUBCATEGORIA_LD,ID_PERFIL_LD,FL_ELECTRONICO,FL_FIRMA_DIGITAL,FE_EXPEDICION,FL_ACTIVO,ID_EVALUACION_LD,ID_SOLICITUD,ID_OTRO_REQUISITO_PADRE,DE_OBSERVACION,FL_SIGED,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('1','64','114',null,null,null,null,null,null,'1','0',null,'1','81','1',null,null,'1','12',to_timestamp('28/01/23 03:19:13,617000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','4',to_timestamp('28/01/23 03:56:49,471000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_OTRO_REQUISITO (ID_OTRO_REQUISITO,ID_TIPO_LD,ID_TIPO_REQUISITO_LD,ID_SECTOR_LD,ID_SUBSECTOR_LD,ID_ACTIVIDAD_LD,ID_UNIDAD_LD,ID_SUBCATEGORIA_LD,ID_PERFIL_LD,FL_ELECTRONICO,FL_FIRMA_DIGITAL,FE_EXPEDICION,FL_ACTIVO,ID_EVALUACION_LD,ID_SOLICITUD,ID_OTRO_REQUISITO_PADRE,DE_OBSERVACION,FL_SIGED,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('2','64','115',null,null,null,null,null,null,null,null,null,'1','80','1',null,null,'1','12',to_timestamp('28/01/23 03:19:13,729000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','4',to_timestamp('28/01/23 03:56:51,646000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_OTRO_REQUISITO (ID_OTRO_REQUISITO,ID_TIPO_LD,ID_TIPO_REQUISITO_LD,ID_SECTOR_LD,ID_SUBSECTOR_LD,ID_ACTIVIDAD_LD,ID_UNIDAD_LD,ID_SUBCATEGORIA_LD,ID_PERFIL_LD,FL_ELECTRONICO,FL_FIRMA_DIGITAL,FE_EXPEDICION,FL_ACTIVO,ID_EVALUACION_LD,ID_SOLICITUD,ID_OTRO_REQUISITO_PADRE,DE_OBSERVACION,FL_SIGED,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('3','64','116',null,null,null,null,null,null,null,null,null,'1','79','1',null,null,'1','12',to_timestamp('28/01/23 03:19:13,754000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','4',to_timestamp('28/01/23 03:56:53,138000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_OTRO_REQUISITO (ID_OTRO_REQUISITO,ID_TIPO_LD,ID_TIPO_REQUISITO_LD,ID_SECTOR_LD,ID_SUBSECTOR_LD,ID_ACTIVIDAD_LD,ID_UNIDAD_LD,ID_SUBCATEGORIA_LD,ID_PERFIL_LD,FL_ELECTRONICO,FL_FIRMA_DIGITAL,FE_EXPEDICION,FL_ACTIVO,ID_EVALUACION_LD,ID_SOLICITUD,ID_OTRO_REQUISITO_PADRE,DE_OBSERVACION,FL_SIGED,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('4','91',null,'32','34','38','146','164','313',null,null,null,null,'143','1',null,null,'1','SIAS_JOB',to_timestamp('28/01/23 03:19:26,547000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','2',to_timestamp('28/01/23 03:58:38,620000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
REM INSERTING into SICOES.SICOES_TR_PERSONA
SET DEFINE OFF;
Insert into SICOES.SICOES_TR_PERSONA (ID_PERSONA,ID_TIPO_DOCUMENTO_LD,ID_PAIS_LD,NU_DOCUMENTO,NO_RAZON_SOCIAL,NO_PERSONA,AP_PATERNO,AP_MATERNO,CO_RUC,DE_DIRECCION,CO_CLIENTE,CO_DEPARTAMENTO,CO_PROVINCIA,CO_DISTRITO,NO_DEPARTAMENTO,NO_PROVINCIA,NO_DISTRITO,CO_PARTIDA_REGISTRAL,DE_TELEFONO1,DE_TELEFONO2,DE_TELEFONO3,DE_CORREO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('2','3',null,'45642656',null,'LUIS ALBERTO','ROSALES','HUERTAS','10456426561','JR.AREQUIPA 528           LIMA/BARRANCA/BARRANCA',null,'150000','150200','150201','LIMA','BARRANCA','BARRANCA',null,'232323','3232',null,'larh0510@gmail.com','SIAS_JOB',to_timestamp('28/01/23 03:24:35,313000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
Insert into SICOES.SICOES_TR_PERSONA (ID_PERSONA,ID_TIPO_DOCUMENTO_LD,ID_PAIS_LD,NU_DOCUMENTO,NO_RAZON_SOCIAL,NO_PERSONA,AP_PATERNO,AP_MATERNO,CO_RUC,DE_DIRECCION,CO_CLIENTE,CO_DEPARTAMENTO,CO_PROVINCIA,CO_DISTRITO,NO_DEPARTAMENTO,NO_PROVINCIA,NO_DISTRITO,CO_PARTIDA_REGISTRAL,DE_TELEFONO1,DE_TELEFONO2,DE_TELEFONO3,DE_CORREO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('3','3',null,'45642656',null,'LUIS ALBERTO','ROSALES','HUERTAS','10456426561','JR.AREQUIPA 528           LIMA/BARRANCA/BARRANCA','249748','150000','150200','150201','LIMA','BARRANCA','BARRANCA',null,'232323','3232',null,'larh0510@gmail.com','SIAS_JOB',to_timestamp('28/01/23 03:24:40,155000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','SIAS_JOB',to_timestamp('28/01/23 03:25:03,139000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
Insert into SICOES.SICOES_TR_PERSONA (ID_PERSONA,ID_TIPO_DOCUMENTO_LD,ID_PAIS_LD,NU_DOCUMENTO,NO_RAZON_SOCIAL,NO_PERSONA,AP_PATERNO,AP_MATERNO,CO_RUC,DE_DIRECCION,CO_CLIENTE,CO_DEPARTAMENTO,CO_PROVINCIA,CO_DISTRITO,NO_DEPARTAMENTO,NO_PROVINCIA,NO_DISTRITO,CO_PARTIDA_REGISTRAL,DE_TELEFONO1,DE_TELEFONO2,DE_TELEFONO3,DE_CORREO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('1','3',null,'45642656',null,'LUIS ALBERTO','ROSALES','HUERTAS','10456426561','JR.AREQUIPA 528           LIMA/BARRANCA/BARRANCA',null,'150000','150200','150201','LIMA','BARRANCA','BARRANCA',null,'232323','3232',null,'larh0510@gmail.com','SIAS_JOB',to_timestamp('28/01/23 03:19:13,549000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
REM INSERTING into SICOES.SICOES_TR_REPRESENTANTE
SET DEFINE OFF;
REM INSERTING into SICOES.SICOES_TR_SOLICITUD
SET DEFINE OFF;
Insert into SICOES.SICOES_TR_SOLICITUD (ID_SOLICITUD,NU_EXPEDIENTE,ID_REPRESENTANTE,ID_PERSONA,ID_USUARIO,ID_RESUL_ADMIN,ID_RESUL_TECNICO,ID_ESTADO_LD,ID_TIPO_SOLICITUD_LD,ID_ESTADO_REVISION_LD,ID_SOLICITUD_PADRE,ID_ESTADO_EVAL_TECNICA_LD,ID_ESTADO_EVAL_ADMIN_LD,CO_CONSENTIMIENTO,NU_DIA_PLAZO_RESP,FE_PLAZO_RESP,NU_DIA_PLAZO_ASIG,FE_PLAZO_ASIG,NU_DIA_PLAZO_SUB,FE_PLAZO_ASIG_SUB,FE_REGISTRO,FE_PRESENTACION,DE_OBS_TECNICA,DE_OBS_ADMIN,DE_NO_CALIFICA,FL_ACTIVO,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('1','202000014915',null,'3','12','143',null,'70','83','75',null,'75','75',null,'6',to_date('03/02/23','DD/MM/RR'),'1',to_date('29/01/23','DD/MM/RR'),null,null,to_date('28/01/23','DD/MM/RR'),to_date('28/01/23','DD/MM/RR'),'SDFSDSF','SDFDF',null,'1','SIAS_JOB',to_timestamp('28/01/23 03:19:13,545000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1','2',to_timestamp('28/01/23 03:59:13,967000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1');
REM INSERTING into SICOES.SICOES_TR_TOKEN
SET DEFINE OFF;
Insert into SICOES.SICOES_TR_TOKEN (ID_TOKEN,ID_TIPO_LD,ID_ESTADO_LD,ID_USUARIO,CO_TOKEN,US_CREACION,FE_CREACION,IP_CREACION,US_ACTUALIZACION,FE_ACTUALIZACION,IP_ACTUALIZACION) values ('1',null,null,null,'359793','SICOES',to_timestamp('28/01/23 03:19:00,260000000 PM','DD/MM/RR HH12:MI:SSXFF AM'),'127.0.0.1',null,null,null);
--------------------------------------------------------
--  DDL for Index PK_SICOES_TM_LISTADO
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TM_LISTADO" ON "SICOES"."SICOES_TM_LISTADO" ("ID_LISTADO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TM_LISTADO_DETALLE
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TM_LISTADO_DETALLE" ON "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TM_OPCION
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TM_OPCION" ON "SICOES"."SICOES_TM_OPCION" ("ID_OPCION") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TM_ROL
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TM_ROL" ON "SICOES"."SICOES_TM_ROL" ("ID_ROL") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TM_ROL_OPCION
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TM_ROL_OPCION" ON "SICOES"."SICOES_TM_ROL_OPCION" ("ID_ROL_OPCION") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TM_UBIGEO
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TM_UBIGEO" ON "SICOES"."SICOES_TM_UBIGEO" ("ID_UBIGEO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TM_USUARIO
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TM_USUARIO" ON "SICOES"."SICOES_TM_USUARIO" ("ID_USUARIO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TM_USUARIO_ROL
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TM_USUARIO_ROL" ON "SICOES"."SICOES_TM_USUARIO_ROL" ("ID_USUARIO_ROL") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TR_ARCHIVO
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TR_ARCHIVO" ON "SICOES"."SICOES_TR_ARCHIVO" ("ID_ARCHIVO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TR_ASIGNACION
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TR_ASIGNACION" ON "SICOES"."SICOES_TR_ASIGNACION" ("ID_ASIGNACION") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TR_DOCUMENTO
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TR_DOCUMENTO" ON "SICOES"."SICOES_TR_DOCUMENTO" ("ID_DOCUMENTO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TR_ESTUDIO
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TR_ESTUDIO" ON "SICOES"."SICOES_TR_ESTUDIO" ("ID_ESTUDIO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TR_NOTIFICACION
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TR_NOTIFICACION" ON "SICOES"."SICOES_TR_NOTIFICACION" ("ID_NOTIFICACION") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TR_OTRO_REQUISITO
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TR_OTRO_REQUISITO" ON "SICOES"."SICOES_TR_OTRO_REQUISITO" ("ID_OTRO_REQUISITO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TR_PERSONA
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TR_PERSONA" ON "SICOES"."SICOES_TR_PERSONA" ("ID_PERSONA") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TR_REPRESENTANTE
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TR_REPRESENTANTE" ON "SICOES"."SICOES_TR_REPRESENTANTE" ("ID_REPRESENTANTE") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TR_SOLICITUD
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TR_SOLICITUD" ON "SICOES"."SICOES_TR_SOLICITUD" ("ID_SOLICITUD") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TR_TOKEN
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TR_TOKEN" ON "SICOES"."SICOES_TR_TOKEN" ("ID_TOKEN") 
  ;
--------------------------------------------------------
--  DDL for Index SYS_IL0000137971C00003$$
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."SYS_IL0000137971C00003$$" ON "SICOES"."OAUTH_ACCESS_TOKEN" (
  ;
--------------------------------------------------------
--  DDL for Index SYS_IL0000137971C00005$$
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."SYS_IL0000137971C00005$$" ON "SICOES"."OAUTH_ACCESS_TOKEN" (
  ;
--------------------------------------------------------
--  DDL for Index SYS_IL0000137977C00002$$
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."SYS_IL0000137977C00002$$" ON "SICOES"."OAUTH_CLIENT_TOKEN" (
  ;
--------------------------------------------------------
--  DDL for Index SYS_IL0000137980C00002$$
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."SYS_IL0000137980C00002$$" ON "SICOES"."OAUTH_CODE" (
  ;
--------------------------------------------------------
--  DDL for Index SYS_IL0000137983C00002$$
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."SYS_IL0000137983C00002$$" ON "SICOES"."OAUTH_REFRESH_TOKEN" (
  ;
--------------------------------------------------------
--  DDL for Index SYS_IL0000137983C00003$$
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."SYS_IL0000137983C00003$$" ON "SICOES"."OAUTH_REFRESH_TOKEN" (
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TM_LISTADO
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TM_LISTADO" ON "SICOES"."SICOES_TM_LISTADO" ("ID_LISTADO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TM_LISTADO_DETALLE
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TM_LISTADO_DETALLE" ON "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TM_OPCION
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TM_OPCION" ON "SICOES"."SICOES_TM_OPCION" ("ID_OPCION") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TM_ROL
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TM_ROL" ON "SICOES"."SICOES_TM_ROL" ("ID_ROL") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TM_ROL_OPCION
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TM_ROL_OPCION" ON "SICOES"."SICOES_TM_ROL_OPCION" ("ID_ROL_OPCION") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TM_UBIGEO
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TM_UBIGEO" ON "SICOES"."SICOES_TM_UBIGEO" ("ID_UBIGEO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TM_USUARIO
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TM_USUARIO" ON "SICOES"."SICOES_TM_USUARIO" ("ID_USUARIO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TM_USUARIO_ROL
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TM_USUARIO_ROL" ON "SICOES"."SICOES_TM_USUARIO_ROL" ("ID_USUARIO_ROL") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TR_ARCHIVO
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TR_ARCHIVO" ON "SICOES"."SICOES_TR_ARCHIVO" ("ID_ARCHIVO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TR_ASIGNACION
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TR_ASIGNACION" ON "SICOES"."SICOES_TR_ASIGNACION" ("ID_ASIGNACION") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TR_DOCUMENTO
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TR_DOCUMENTO" ON "SICOES"."SICOES_TR_DOCUMENTO" ("ID_DOCUMENTO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TR_ESTUDIO
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TR_ESTUDIO" ON "SICOES"."SICOES_TR_ESTUDIO" ("ID_ESTUDIO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TR_NOTIFICACION
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TR_NOTIFICACION" ON "SICOES"."SICOES_TR_NOTIFICACION" ("ID_NOTIFICACION") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TR_OTRO_REQUISITO
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TR_OTRO_REQUISITO" ON "SICOES"."SICOES_TR_OTRO_REQUISITO" ("ID_OTRO_REQUISITO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TR_PERSONA
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TR_PERSONA" ON "SICOES"."SICOES_TR_PERSONA" ("ID_PERSONA") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TR_REPRESENTANTE
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TR_REPRESENTANTE" ON "SICOES"."SICOES_TR_REPRESENTANTE" ("ID_REPRESENTANTE") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TR_SOLICITUD
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TR_SOLICITUD" ON "SICOES"."SICOES_TR_SOLICITUD" ("ID_SOLICITUD") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SICOES_TR_TOKEN
--------------------------------------------------------

  CREATE UNIQUE INDEX "SICOES"."PK_SICOES_TR_TOKEN" ON "SICOES"."SICOES_TR_TOKEN" ("ID_TOKEN") 
  ;
--------------------------------------------------------
--  Constraints for Table OAUTH_ACCESS_TOKEN
--------------------------------------------------------

  ALTER TABLE "SICOES"."OAUTH_ACCESS_TOKEN" MODIFY ("AUTHENTICATION_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table OAUTH_CLIENT_DETAILS
--------------------------------------------------------

  ALTER TABLE "SICOES"."OAUTH_CLIENT_DETAILS" MODIFY ("CLIENT_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table OAUTH_CODE
--------------------------------------------------------

  ALTER TABLE "SICOES"."OAUTH_CODE" MODIFY ("CODE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table OAUTH_REFRESH_TOKEN
--------------------------------------------------------

  ALTER TABLE "SICOES"."OAUTH_REFRESH_TOKEN" MODIFY ("TOKEN_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SICOES_TM_LISTADO
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TM_LISTADO" MODIFY ("ID_LISTADO" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_LISTADO" MODIFY ("CD_LISTADO" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_LISTADO" MODIFY ("NO_LISTADO" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_LISTADO" MODIFY ("US_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_LISTADO" MODIFY ("FE_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_LISTADO" MODIFY ("IP_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_LISTADO" ADD CONSTRAINT "PK_SICOES_TM_LISTADO" PRIMARY KEY ("ID_LISTADO")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table SICOES_TM_LISTADO_DETALLE
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TM_LISTADO_DETALLE" MODIFY ("ID_LISTADO_DETALLE" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_LISTADO_DETALLE" MODIFY ("CO_LISTADO_DETALLE" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_LISTADO_DETALLE" MODIFY ("US_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_LISTADO_DETALLE" MODIFY ("FE_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_LISTADO_DETALLE" MODIFY ("IP_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_LISTADO_DETALLE" ADD CONSTRAINT "PK_SICOES_TM_LISTADO_DETALLE" PRIMARY KEY ("ID_LISTADO_DETALLE")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table SICOES_TM_OPCION
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TM_OPCION" MODIFY ("ID_OPCION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_OPCION" MODIFY ("US_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_OPCION" MODIFY ("FE_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_OPCION" MODIFY ("IP_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_OPCION" ADD CONSTRAINT "PK_SICOES_TM_OPCION" PRIMARY KEY ("ID_OPCION")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table SICOES_TM_ROL
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TM_ROL" MODIFY ("ID_ROL" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_ROL" MODIFY ("US_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_ROL" MODIFY ("FE_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_ROL" MODIFY ("IP_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_ROL" ADD CONSTRAINT "PK_SICOES_TM_ROL" PRIMARY KEY ("ID_ROL")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table SICOES_TM_ROL_OPCION
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TM_ROL_OPCION" MODIFY ("ID_ROL_OPCION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_ROL_OPCION" MODIFY ("US_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_ROL_OPCION" MODIFY ("FE_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_ROL_OPCION" MODIFY ("IP_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_ROL_OPCION" ADD CONSTRAINT "PK_SICOES_TM_ROL_OPCION" PRIMARY KEY ("ID_ROL_OPCION")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table SICOES_TM_UBIGEO
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TM_UBIGEO" MODIFY ("ID_UBIGEO" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_UBIGEO" MODIFY ("US_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_UBIGEO" MODIFY ("FE_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_UBIGEO" MODIFY ("IP_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_UBIGEO" ADD CONSTRAINT "PK_SICOES_TM_UBIGEO" PRIMARY KEY ("ID_UBIGEO")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table SICOES_TM_USUARIO
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TM_USUARIO" MODIFY ("ID_USUARIO" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_USUARIO" MODIFY ("US_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_USUARIO" MODIFY ("FE_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_USUARIO" MODIFY ("IP_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_USUARIO" ADD CONSTRAINT "PK_SICOES_TM_USUARIO" PRIMARY KEY ("ID_USUARIO")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table SICOES_TM_USUARIO_ROL
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TM_USUARIO_ROL" MODIFY ("ID_USUARIO_ROL" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_USUARIO_ROL" MODIFY ("US_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_USUARIO_ROL" MODIFY ("FE_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_USUARIO_ROL" MODIFY ("IP_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TM_USUARIO_ROL" ADD CONSTRAINT "PK_SICOES_TM_USUARIO_ROL" PRIMARY KEY ("ID_USUARIO_ROL")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table SICOES_TR_ARCHIVO
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TR_ARCHIVO" MODIFY ("ID_ARCHIVO" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_ARCHIVO" MODIFY ("US_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_ARCHIVO" MODIFY ("FE_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_ARCHIVO" MODIFY ("IP_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_ARCHIVO" ADD CONSTRAINT "PK_SICOES_TR_ARCHIVO" PRIMARY KEY ("ID_ARCHIVO")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table SICOES_TR_ASIGNACION
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TR_ASIGNACION" MODIFY ("ID_ASIGNACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_ASIGNACION" MODIFY ("US_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_ASIGNACION" MODIFY ("FE_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_ASIGNACION" MODIFY ("IP_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_ASIGNACION" ADD CONSTRAINT "PK_SICOES_TR_ASIGNACION" PRIMARY KEY ("ID_ASIGNACION")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table SICOES_TR_DOCUMENTO
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TR_DOCUMENTO" MODIFY ("ID_DOCUMENTO" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_DOCUMENTO" MODIFY ("US_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_DOCUMENTO" MODIFY ("FE_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_DOCUMENTO" MODIFY ("IP_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_DOCUMENTO" ADD CONSTRAINT "PK_SICOES_TR_DOCUMENTO" PRIMARY KEY ("ID_DOCUMENTO")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table SICOES_TR_ESTUDIO
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TR_ESTUDIO" MODIFY ("ID_ESTUDIO" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_ESTUDIO" MODIFY ("US_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_ESTUDIO" MODIFY ("FE_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_ESTUDIO" MODIFY ("IP_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_ESTUDIO" ADD CONSTRAINT "PK_SICOES_TR_ESTUDIO" PRIMARY KEY ("ID_ESTUDIO")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table SICOES_TR_NOTIFICACION
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TR_NOTIFICACION" MODIFY ("FE_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_NOTIFICACION" MODIFY ("IP_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_NOTIFICACION" ADD CONSTRAINT "PK_SICOES_TR_NOTIFICACION" PRIMARY KEY ("ID_NOTIFICACION")
  USING INDEX  ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_NOTIFICACION" MODIFY ("ID_NOTIFICACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_NOTIFICACION" MODIFY ("US_CREACION" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SICOES_TR_OTRO_REQUISITO
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TR_OTRO_REQUISITO" MODIFY ("ID_OTRO_REQUISITO" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_OTRO_REQUISITO" MODIFY ("US_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_OTRO_REQUISITO" MODIFY ("FE_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_OTRO_REQUISITO" MODIFY ("IP_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_OTRO_REQUISITO" ADD CONSTRAINT "PK_SICOES_TR_OTRO_REQUISITO" PRIMARY KEY ("ID_OTRO_REQUISITO")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table SICOES_TR_PERSONA
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TR_PERSONA" MODIFY ("ID_PERSONA" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_PERSONA" MODIFY ("US_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_PERSONA" MODIFY ("FE_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_PERSONA" MODIFY ("IP_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_PERSONA" ADD CONSTRAINT "PK_SICOES_TR_PERSONA" PRIMARY KEY ("ID_PERSONA")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table SICOES_TR_REPRESENTANTE
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TR_REPRESENTANTE" MODIFY ("ID_REPRESENTANTE" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_REPRESENTANTE" MODIFY ("US_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_REPRESENTANTE" MODIFY ("FE_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_REPRESENTANTE" MODIFY ("IP_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_REPRESENTANTE" ADD CONSTRAINT "PK_SICOES_TR_REPRESENTANTE" PRIMARY KEY ("ID_REPRESENTANTE")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table SICOES_TR_SOLICITUD
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TR_SOLICITUD" MODIFY ("ID_SOLICITUD" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_SOLICITUD" MODIFY ("US_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_SOLICITUD" MODIFY ("FE_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_SOLICITUD" MODIFY ("IP_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_SOLICITUD" ADD CONSTRAINT "PK_SICOES_TR_SOLICITUD" PRIMARY KEY ("ID_SOLICITUD")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table SICOES_TR_TOKEN
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TR_TOKEN" MODIFY ("ID_TOKEN" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_TOKEN" MODIFY ("US_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_TOKEN" MODIFY ("FE_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_TOKEN" MODIFY ("IP_CREACION" NOT NULL ENABLE);
  ALTER TABLE "SICOES"."SICOES_TR_TOKEN" ADD CONSTRAINT "PK_SICOES_TR_TOKEN" PRIMARY KEY ("ID_TOKEN")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SICOES_TM_LISTADO_DETALLE
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TM_LISTADO_DETALLE" ADD CONSTRAINT "FK_TM_LIS_DETALLE_ID_LISTADO" FOREIGN KEY ("ID_LISTADO")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO" ("ID_LISTADO") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TM_LISTADO_DETALLE" ADD CONSTRAINT "FK_TM_LIS_DETALLE_ID_LIS_PADRE" FOREIGN KEY ("ID_LISTADO_PADRE")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO" ("ID_LISTADO") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TM_LISTADO_DETALLE" ADD CONSTRAINT "FK_TM_LIS_DETALLE_ID_SUPE_LD" FOREIGN KEY ("ID_SUPERIOR_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SICOES_TM_OPCION
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TM_OPCION" ADD CONSTRAINT "Reference_56" FOREIGN KEY ("ID_PADRE")
	  REFERENCES "SICOES"."SICOES_TM_OPCION" ("ID_OPCION") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TM_OPCION" ADD CONSTRAINT "Reference_62" FOREIGN KEY ("ID_ESTADO_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SICOES_TM_ROL
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TM_ROL" ADD CONSTRAINT "Reference_57" FOREIGN KEY ("ID_ESTADO_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SICOES_TM_ROL_OPCION
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TM_ROL_OPCION" ADD CONSTRAINT "Reference_58" FOREIGN KEY ("ID_OPCION")
	  REFERENCES "SICOES"."SICOES_TM_OPCION" ("ID_OPCION") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TM_ROL_OPCION" ADD CONSTRAINT "Reference_59" FOREIGN KEY ("ID_ROL")
	  REFERENCES "SICOES"."SICOES_TM_ROL" ("ID_ROL") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SICOES_TM_USUARIO_ROL
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TM_USUARIO_ROL" ADD CONSTRAINT "Reference_60" FOREIGN KEY ("ID_ROL")
	  REFERENCES "SICOES"."SICOES_TM_ROL" ("ID_ROL") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TM_USUARIO_ROL" ADD CONSTRAINT "Reference_61" FOREIGN KEY ("ID_USUARIO")
	  REFERENCES "SICOES"."SICOES_TM_USUARIO" ("ID_USUARIO") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SICOES_TR_ARCHIVO
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TR_ARCHIVO" ADD CONSTRAINT "Reference_37" FOREIGN KEY ("ID_DOCUMENTO")
	  REFERENCES "SICOES"."SICOES_TR_DOCUMENTO" ("ID_DOCUMENTO") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_ARCHIVO" ADD CONSTRAINT "Reference_38" FOREIGN KEY ("ID_OTRO_REQUISITO")
	  REFERENCES "SICOES"."SICOES_TR_OTRO_REQUISITO" ("ID_OTRO_REQUISITO") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_ARCHIVO" ADD CONSTRAINT "Reference_42" FOREIGN KEY ("ID_ESTUDIO")
	  REFERENCES "SICOES"."SICOES_TR_ESTUDIO" ("ID_ESTUDIO") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_ARCHIVO" ADD CONSTRAINT "Reference_46" FOREIGN KEY ("ID_ESTADO_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_ARCHIVO" ADD CONSTRAINT "Reference_63" FOREIGN KEY ("ID_TIPO_ARCHIVO_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_ARCHIVO" ADD CONSTRAINT "Reference_64" FOREIGN KEY ("ID_SOLICITUD")
	  REFERENCES "SICOES"."SICOES_TR_SOLICITUD" ("ID_SOLICITUD") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SICOES_TR_ASIGNACION
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TR_ASIGNACION" ADD CONSTRAINT "Reference_24" FOREIGN KEY ("ID_TIPO_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_ASIGNACION" ADD CONSTRAINT "Reference_25" FOREIGN KEY ("ID_SOLICITUD")
	  REFERENCES "SICOES"."SICOES_TR_SOLICITUD" ("ID_SOLICITUD") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_ASIGNACION" ADD CONSTRAINT "Reference_27" FOREIGN KEY ("ID_USUARIO")
	  REFERENCES "SICOES"."SICOES_TM_USUARIO" ("ID_USUARIO") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_ASIGNACION" ADD CONSTRAINT "Reference_74" FOREIGN KEY ("ID_GRUPO_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_ASIGNACION" ADD CONSTRAINT "Reference_75" FOREIGN KEY ("ID_EVALUACION_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SICOES_TR_DOCUMENTO
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TR_DOCUMENTO" ADD CONSTRAINT "Reference_36" FOREIGN KEY ("ID_SOLICITUD")
	  REFERENCES "SICOES"."SICOES_TR_SOLICITUD" ("ID_SOLICITUD") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_DOCUMENTO" ADD CONSTRAINT "Reference_39" FOREIGN KEY ("ID_EVALUACION_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_DOCUMENTO" ADD CONSTRAINT "Reference_43" FOREIGN KEY ("ID_DOCUMENTO_PADRE")
	  REFERENCES "SICOES"."SICOES_TR_DOCUMENTO" ("ID_DOCUMENTO") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_DOCUMENTO" ADD CONSTRAINT "Reference_6" FOREIGN KEY ("ID_TIPO_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_DOCUMENTO" ADD CONSTRAINT "Reference_68" FOREIGN KEY ("ID_PAIS_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_DOCUMENTO" ADD CONSTRAINT "Reference_69" FOREIGN KEY ("ID_TIPO_ID_TRIBURARIO_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_DOCUMENTO" ADD CONSTRAINT "Reference_7" FOREIGN KEY ("ID_TIPO_DOCUMENTO_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_DOCUMENTO" ADD CONSTRAINT "Reference_70" FOREIGN KEY ("ID_CONFORMIDAD_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_DOCUMENTO" ADD CONSTRAINT "Reference_8" FOREIGN KEY ("ID_TIPO_CAMBIO_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SICOES_TR_ESTUDIO
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TR_ESTUDIO" ADD CONSTRAINT "Reference_23" FOREIGN KEY ("ID_TIPO_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_ESTUDIO" ADD CONSTRAINT "Reference_31" FOREIGN KEY ("ID_SOLICITUD")
	  REFERENCES "SICOES"."SICOES_TR_SOLICITUD" ("ID_SOLICITUD") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_ESTUDIO" ADD CONSTRAINT "Reference_41" FOREIGN KEY ("ID_EVALUACION_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_ESTUDIO" ADD CONSTRAINT "Reference_45" FOREIGN KEY ("ID_ESTUDIO_PADRE")
	  REFERENCES "SICOES"."SICOES_TR_ESTUDIO" ("ID_ESTUDIO") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_ESTUDIO" ADD CONSTRAINT "Reference_65" FOREIGN KEY ("ID_TIPO_ESTUDIO_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_ESTUDIO" ADD CONSTRAINT "Reference_66" FOREIGN KEY ("ID_FUENTE_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SICOES_TR_NOTIFICACION
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TR_NOTIFICACION" ADD CONSTRAINT "Reference_53" FOREIGN KEY ("ID_ESTADO_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SICOES_TR_OTRO_REQUISITO
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TR_OTRO_REQUISITO" ADD CONSTRAINT "Reference_13" FOREIGN KEY ("ID_TIPO_REQUISITO_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_OTRO_REQUISITO" ADD CONSTRAINT "Reference_28" FOREIGN KEY ("ID_SECTOR_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_OTRO_REQUISITO" ADD CONSTRAINT "Reference_29" FOREIGN KEY ("ID_SUBSECTOR_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_OTRO_REQUISITO" ADD CONSTRAINT "Reference_30" FOREIGN KEY ("ID_PERFIL_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_OTRO_REQUISITO" ADD CONSTRAINT "Reference_35" FOREIGN KEY ("ID_SOLICITUD")
	  REFERENCES "SICOES"."SICOES_TR_SOLICITUD" ("ID_SOLICITUD") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_OTRO_REQUISITO" ADD CONSTRAINT "Reference_40" FOREIGN KEY ("ID_EVALUACION_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_OTRO_REQUISITO" ADD CONSTRAINT "Reference_44" FOREIGN KEY ("ID_OTRO_REQUISITO_PADRE")
	  REFERENCES "SICOES"."SICOES_TR_OTRO_REQUISITO" ("ID_OTRO_REQUISITO") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_OTRO_REQUISITO" ADD CONSTRAINT "Reference_49" FOREIGN KEY ("ID_TIPO_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_OTRO_REQUISITO" ADD CONSTRAINT "Reference_54" FOREIGN KEY ("ID_ACTIVIDAD_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_OTRO_REQUISITO" ADD CONSTRAINT "Reference_55" FOREIGN KEY ("ID_SUBCATEGORIA_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_OTRO_REQUISITO" ADD CONSTRAINT "Reference_73" FOREIGN KEY ("ID_UNIDAD_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SICOES_TR_PERSONA
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TR_PERSONA" ADD CONSTRAINT "Reference_4" FOREIGN KEY ("ID_TIPO_DOCUMENTO_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_PERSONA" ADD CONSTRAINT "Reference_76" FOREIGN KEY ("ID_PAIS_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SICOES_TR_REPRESENTANTE
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TR_REPRESENTANTE" ADD CONSTRAINT "Reference_5" FOREIGN KEY ("ID_TIPO_DOCUMENTO_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SICOES_TR_SOLICITUD
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TR_SOLICITUD" ADD CONSTRAINT "Reference_10" FOREIGN KEY ("ID_REPRESENTANTE")
	  REFERENCES "SICOES"."SICOES_TR_REPRESENTANTE" ("ID_REPRESENTANTE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_SOLICITUD" ADD CONSTRAINT "Reference_16" FOREIGN KEY ("ID_PERSONA")
	  REFERENCES "SICOES"."SICOES_TR_PERSONA" ("ID_PERSONA") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_SOLICITUD" ADD CONSTRAINT "Reference_26" FOREIGN KEY ("ID_USUARIO")
	  REFERENCES "SICOES"."SICOES_TM_USUARIO" ("ID_USUARIO") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_SOLICITUD" ADD CONSTRAINT "Reference_32" FOREIGN KEY ("ID_RESUL_ADMIN")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_SOLICITUD" ADD CONSTRAINT "Reference_33" FOREIGN KEY ("ID_RESUL_TECNICO")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_SOLICITUD" ADD CONSTRAINT "Reference_34" FOREIGN KEY ("ID_ESTADO_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_SOLICITUD" ADD CONSTRAINT "Reference_47" FOREIGN KEY ("ID_TIPO_SOLICITUD_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_SOLICITUD" ADD CONSTRAINT "Reference_48" FOREIGN KEY ("ID_ESTADO_REVISION_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_SOLICITUD" ADD CONSTRAINT "Reference_67" FOREIGN KEY ("ID_SOLICITUD_PADRE")
	  REFERENCES "SICOES"."SICOES_TR_SOLICITUD" ("ID_SOLICITUD") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_SOLICITUD" ADD CONSTRAINT "Reference_71" FOREIGN KEY ("ID_ESTADO_EVAL_TECNICA_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_SOLICITUD" ADD CONSTRAINT "Reference_72" FOREIGN KEY ("ID_ESTADO_EVAL_ADMIN_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SICOES_TR_TOKEN
--------------------------------------------------------

  ALTER TABLE "SICOES"."SICOES_TR_TOKEN" ADD CONSTRAINT "Reference_50" FOREIGN KEY ("ID_TIPO_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_TOKEN" ADD CONSTRAINT "Reference_51" FOREIGN KEY ("ID_ESTADO_LD")
	  REFERENCES "SICOES"."SICOES_TM_LISTADO_DETALLE" ("ID_LISTADO_DETALLE") ENABLE;
  ALTER TABLE "SICOES"."SICOES_TR_TOKEN" ADD CONSTRAINT "Reference_52" FOREIGN KEY ("ID_USUARIO")
	  REFERENCES "SICOES"."SICOES_TM_USUARIO" ("ID_USUARIO") ENABLE;

DELETE FROM OAUTH_ACCESS_TOKEN;
DELETE FROM OAUTH_REFRESH_TOKEN;
COMMIT;