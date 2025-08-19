1=1 ORDER BY ID_LISTADO ASC,ID_SUPERIOR_LD ASC,ID_LISTADO_DETALLE ASC

ng serve --host=0.0.0.0 --serve-path=/sicoes

-- 03_GRANTS.sql 
	Select ' GRANT INSERT,UPDATE,DELETE,SELECT ON  ES_SICOES.'|| o.object_name || ' TO US_SICOES '||';' SCRIPT from all_objects o
	where o.owner = 'ES_SICOES' AND object_type='TABLE' AND DATA_OBJECT_ID IS NOT NULL
	UNION ALL
	select ' GRANT SELECT ON  ES_SICOES.'|| o.object_name || ' TO US_SICOES '||';' SCRIPT from all_objects o
	where o.owner = 'ES_SICOES' AND object_type='TABLE' AND DATA_OBJECT_ID IS  NULL
	UNION ALL
	select '  GRANT SELECT ON  ES_SICOES.'|| o.object_name || ' TO US_SICOES '||';' SCRIPT from all_objects o
	where o.owner = 'ES_SICOES' AND object_type='SEQUENCE'
	UNION ALL
	select '  GRANT EXECUTE ON  ES_SICOES.'|| o.object_name || ' TO US_SICOES '||';' SCRIPT from all_objects o
	where o.owner = 'ES_SICOES' AND object_type='PACKAGE'
	UNION ALL
	Select ' GRANT SELECT ON  ES_SICOES.'|| o.object_name || ' TO US_SICOES '||';' SCRIPT from all_objects o
	where o.owner = 'ES_SICOES' AND object_type='VIEW' 


 -- ELIMINAR SINONIMOS
	Select ' DROP SYNONYM '|| o.object_name||';'  SCRIPT from all_objects o
	where o.owner = 'ES_SICOES' AND object_type='TABLE' AND DATA_OBJECT_ID IS NOT NULL
	UNION ALL
	select ' DROP SYNONYM '|| o.object_name||';'  SCRIPT from all_objects o
	where o.owner = 'ES_SICOES' AND object_type='TABLE' AND DATA_OBJECT_ID IS  NULL
	UNION ALL
	select '  DROP SYNONYM '|| o.object_name||';'  SCRIPT from all_objects o
	where o.owner = 'ES_SICOES' AND object_type='SEQUENCE'
	UNION ALL
	select '  DROP SYNONYM '|| o.object_name||';' SCRIPT from all_objects o
	where o.owner = 'ES_SICOES' AND object_type='PACKAGE'
	UNION ALL
	Select ' DROP SYNONYM '|| o.object_name||';' SCRIPT from all_objects o
	where o.owner = 'ES_SICOES' AND object_type='VIEW' 

-- 04_SYNONYM.sql
	Select ' CREATE SYNONYM '|| o.object_name || ' FOR ES_SICOES.'||o.object_name||';' SCRIPT from all_objects o
	where o.owner = 'ES_SICOES' AND object_type='TABLE' AND DATA_OBJECT_ID IS NOT NULL
	UNION ALL
	select ' CREATE SYNONYM '|| o.object_name || ' FOR ES_SICOES.'||o.object_name||';' SCRIPT from all_objects o
	where o.owner = 'ES_SICOES' AND object_type='TABLE' AND DATA_OBJECT_ID IS  NULL
	UNION ALL
	select '  CREATE SYNONYM '|| o.object_name || ' FOR ES_SICOES.'||o.object_name||';' SCRIPT from all_objects o
	where o.owner = 'ES_SICOES' AND object_type='SEQUENCE'
	UNION ALL
	select '  CREATE SYNONYM '|| o.object_name || ' FOR ES_SICOES.'||o.object_name||';' SCRIPT from all_objects o
	where o.owner = 'ES_SICOES' AND object_type='PACKAGE'
	UNION ALL
	Select ' CREATE SYNONYM '|| o.object_name || ' FOR ES_SICOES.'||o.object_name||';' SCRIPT from all_objects o
	where o.owner = 'ES_SICOES' AND object_type='VIEW' 




--ELIMINAR SECUENCIA

select '  DROP SEQUENCE ES_SICOES.'|| o.object_name ||';' SCRIPT from all_objects o
	where o.owner = 'ES_SICOES' AND object_type='SEQUENCE'

END;

