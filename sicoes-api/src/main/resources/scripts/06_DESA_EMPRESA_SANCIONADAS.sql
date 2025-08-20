--------------------------------------------------------
-- Archivo creado  - domingo-febrero-26-2023   
--------------------------------------------------------

alter session set "_ORACLE_SCRIPT"=true;
create user EMPSAN identified by EMPSAN;
grant connect,dba,resource to EMPSAN;

create user US_ACC_EMPSAN identified by US_ACC_EMPSAN;
grant connect,dba,resource to US_ACC_EMPSAN;

	  <Resource name ="jdbc/APP_SICOES" auth ="Container" type ="javax.sql.DataSource" 
         username ="US_SICOES" password ="US_SICOES" driverClassName ="oracle.jdbc.driver.OracleDriver"
         url = "jdbc:oracle:thin:@localhost:1521:xe" maxActive = "100" maxIdle = "30"
          maxWait = "10000" />
          
           <Resource name ="jdbc/APP_EMPSAN" auth ="Container" type ="javax.sql.DataSource" 
         username ="US_ACC_EMPSAN" password ="US_ACC_EMPSAN" driverClassName ="oracle.jdbc.driver.OracleDriver"
         url = "jdbc:oracle:thin:@localhost:1521:xe" maxActive = "100" maxIdle = "30"
          maxWait = "10000" />
          
--------------------------------------------------------
--  DDL for Table ESAN_SANCION
--------------------------------------------------------

CREATE TABLE ESAN_EMPRESA 
(
  RUC VARCHAR2(20 BYTE) 
, ID_EMPRESA NUMBER NOT NULL 
);

CREATE UNIQUE INDEX ESAN_EMPRESA_PK ON ESAN_EMPRESA (ID_EMPRESA ASC) ;

CREATE TABLE EMPSAN.ESAN_SANCION 
   (	ID_ESTADO_SANCION NUMBER, 
	ID_EMPRESA NUMBER
   ) ;
REM INSERTING into EMPSAN.ESAN_EMPRESA
SET DEFINE OFF;
Insert into EMPSAN.ESAN_EMPRESA (RUC,ID_EMPRESA) values ('20605040421','1');
Insert into EMPSAN.ESAN_EMPRESA (RUC,ID_EMPRESA) values ('20510496168','2');
Insert into EMPSAN.ESAN_EMPRESA (RUC,ID_EMPRESA) values ('10447866744','3');
Insert into EMPSAN.ESAN_EMPRESA (RUC,ID_EMPRESA) values ('10476184563','4');
REM INSERTING into EMPSAN.ESAN_SANCION
SET DEFINE OFF;
Insert into EMPSAN.ESAN_SANCION (ID_ESTADO_SANCION,ID_EMPRESA) values ('2','1');
Insert into EMPSAN.ESAN_SANCION (ID_ESTADO_SANCION,ID_EMPRESA) values ('2','2');



/

create or replace FUNCTION        ESAN_DEV_EMP_SANCIONADA_FUN(Nro_RUC IN VARCHAR2)
RETURN  VARCHAR2 IS eRetorno  VARCHAR2(2);
eCantidad NUMBER(2);
eEmpresa VARCHAR2(2):=null;

/* Función que me identifica a las empresas que tienen sanción activa de acuerdo a las fechas*/ 

BEGIN
  BEGIN
     SELECT ID_EMPRESA INTO eEmpresa FROM EMPSAN.ESAN_EMPRESA WHERE RUC = Nro_RUC;
     EXCEPTION WHEN NO_DATA_FOUND THEN
        eRetorno := 'NO';    
        /*raise_application_error (-20002,'Debe ingresar un RUC válido.');*/ 
  END; 
  
  SELECT COUNT(*) INTO eCantidad FROM EMPSAN.ESAN_SANCION WHERE ID_ESTADO_SANCION IN (2) AND  ID_EMPRESA = eEmpresa;
 
 IF NVL(eCantidad,0) > 0 THEN
    eRetorno := 'SI';
 ELSIF NVL(eCantidad,0) = 0 THEN
    eRetorno := 'NO';
 END IF;
 
 RETURN eRetorno;

END ESAN_DEV_EMP_SANCIONADA_FUN;
/

grant EXECUTE, DEBUG on EMPSAN.ESAN_DEV_EMP_SANCIONADA_FUN to US_SICOES ;

CREATE SYNONYM ESAN_DEV_EMP_SANCIONADA_FUN FOR EMPSAN.ESAN_DEV_EMP_SANCIONADA_FUN;
