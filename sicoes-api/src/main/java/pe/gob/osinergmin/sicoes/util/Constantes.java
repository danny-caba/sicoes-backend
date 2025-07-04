package pe.gob.osinergmin.sicoes.util;

public class Constantes {

	public static final String PATH_SAVE_ALBUM = "C:\\var\\www\\durangroup.pe\\public_html\\images";
	public static final String PATH_SERVER = "http://5df3-201-240-201-231.ngrok.io/images/";
	public static final String SIGLA = "SOL";
//	public static final Long CODIGO_APLICACION = 150L;
	public static final String PARAMETRO_CLAVE = "LLAVE";

	public static final class SED_DET_AUDIT {
		public static final String DATO_GENERAL = "000|DATO GENERALES| | ";
		public static final String PERSONAL = "020|PERSONAL|Se agregó o  actualizó  al personal| ";
		public static final String POBLACION_CAP = "030|POBLACION|Capacidad de CEAPAM| ";
		public static final String POBLACION_ADD = "040|POBLACION|Se agrego o actualizo a los PAM| ";
	}
	
	public static final class FLAG {
		public static final Long ACTIVO = 1L;
		public static final Long INACTIVO = 0L;
	}
	public static final class UBIGEO{
		public static final String PROVINCIA1="01";
		public static final String DISTRITO1="01";
		
	}
	
	public static final class ESTADO {
		public static final String ACTIVO = "1";
		public static final String INACTIVO = "0";
	}

	/*public static final class PERFIL {
		public static final Long USUARIO_EXTERNO 		= 500L;
		public static final Long USUARIO_COORDINADOR 	= 501L;
		public static final Long USUARIO_EVALUADOR 		= 502L;
	}*/
	public static final String USUARIO_ADMIN="SICOES";
	public static final String CORREO_ADMIN="SICOES";
	public static final String DIAS_HABILES = "H";
	
	public static final class TIPO_COMPONENTE {
		public static final String COMPO01 = "COMPO01";
		public static final String COMPO02 = "COMPO02";
		public static final String COMPO03 = "COMPO03";
		public static final String COMPO04 = "COMPO04";
		public static final String COMPO05 = "COMPO05";
		public static final String COMPO06 = "COMPO06";
		public static final String COMPO07 = "COMPO07";
		public static final String COMPO08 = "COMPO08";
		public static final String COMPO09 = "COMPO09";
		public static final String COMPO10 = "COMPO10";
		public static final String COMPO11 = "COMPO11";
		public static final String COMPO12 = "COMPO12";
		public static final String COMPO13 = "COMPO13";
		public static final String COMPO14 = "COMPO14";
		public static final String COMPO15 = "COMPO15";
		public static final String COMPO16 = "COMPO16";
		public static final String COMPO17 = "COMPO17";
		public static final String COMPO18 = "COMPO18";
		public static final String COMPO19 = "COMPO19";

	}

	public static final class LISTADO {
		
		
		public static final class ESTADO_SOLICITUD {
			public static final String CODIGO 		= "ESTADO_SOLICITUD";
			public static final String BORRADOR 	= "BORRADOR";		
			public static final String EN_PROCESO	= "EN_PROCESO";
			public static final String OBSERVADO	= "OBSERVADO";
			public static final String CONCLUIDO	= "CONCLUIDO";	
			public static final String ARCHIVADO	= "ARCHIVADO";
			
		}
		
		public static final class TIPO_SUSPENSION_CANCELACION {
			public static final String CODIGO 		= "TIPO_SUSP_CAN";	
			public static final String SUSPENSION 	= "SUSPENDIDA";	
			public static final String CANCELACION 	= "CANCELADA";	
		}
		
		public static final class TIPO_CAMBIO {
			public static final String CODIGO 		= "MONEDA";
			public static final String SOLES 	= "SOLES";	
			public static final String DOLARES 	= "DOLARES";	
						
		}
		
		public static final class ESTADO_NOTIFICACIONES {
			public static final String CODIGO 			= "ESTADO_NOTIFICACION";	
			public static final String PENDIENTE		= "PENDIENTE";
			public static final String ENVIADO			= "ENVIADO";
			public static final String FALLADO			= "FALLADO";
		}
		
		public static final class ESTADO_TOKEN {
			public static final String CODIGO 			= "ESTADO_TOKEN";	
			public static final String PENDIENTE		= "PENDIENTE";
			public static final String USADO			= "USADO";
		}
		
		public static final class TIPO_TOKEN {
			public static final String CODIGO 			= "TIPO_TOKEN";	
			public static final String VERIFICACION		= "VERIFICACION";
			public static final String CONTRASENIA		= "CONTRASENIA";
			public static final String RECUPERAR_CONTRASENIA		= "RECUPERAR_CONTRASENIA";

		}
		
		public static final class ESTADO_REVISION {
			public static final String CODIGO 		= "ESTADO_REVISION";
			public static final String POR_ASIGNAR	= "POR_ASIGNAR";
			public static final String ASIGNADO		= "ASIGNADO";
			public static final String EN_PROCESO	= "EN_PROCESO";
			public static final String OBSERVADO	= "OBSERVADO";
			public static final String CONCLUIDO	= "CONCLUIDO";
			public static final String EN_APROBACION	= "EN_APROBACION";
			public static final String ARCHIVADO		= "ARCHIVADO";
		}
		
		public static final class TIPO_SOLICITUD {
			public static final String CODIGO 			= "TIPO_SOLICITUD";	
			public static final String INSCRIPCION		= "INSCRIPCION";
			public static final String ACTUALIZACION	= "ACTUALIZACION";
			public static final String MODIFICACION		= "MODIFICACION";
			public static final String SUSPENCION		= "SUSPENCION";
			public static final String CANCELACION		= "CANCELACION";
			public static final String SUBSANACION		= "SUBSANACION";
		}
		
		public static final class TIPO_ARCHIVO {
			public static final String CODIGO 		= "TIPO_ARCHIVO";
			public static final String GRADO 		= "TA01";		
			public static final String COLEGIATURA	= "TA02";
			public static final String DNI			= "TA03";
			public static final String RUC			= "TA04";
			public static final String JURADA		= "TA05";
			public static final String REGISTRAL	= "TA06";
			public static final String ACREDITACION	= "TA07";
			public static final String EXPERIENCIA	= "TA08";
			public static final String CAPACITACION	= "TA09";
			public static final String EVIDENCIA	= "TA10";
			public static final String FORMATO		= "TA11";
			public static final String SUBSANACION	= "TA12";
			public static final String RESULTADO	= "TA13";
			public static final String RESULTADO_SUBSANACION	= "TA14";
			public static final String SUPERVISORA	= "TA15";
			public static final String DOCUMENTO_ARCHIVAMIENTO	= "TA16";
			public static final String CONSTANCIA_APROBACION	= "TA17";
			public static final String CONSTITUCION_EMPRESA	= "TA18";
			
			public static final String INFORME_TECNICO	= "TA21";
			public static final String INFORME_ADMINISTRATIVO	= "TA22";

			public static final String INFORMACION_PROCESO = "TA24";

			public static final String INFORMACION_FORMULACION_CONSULTAS = "TA25";

			public static final String PERFECCIONAMIENTO_CONTRATO = "ARCHIVO_PERFECCIONAMIENTO";

			public static final String SOLICITUD_PERFECCIONAMIENTO = "SOLICITUD_PERFECCIONAMIENTO";

		}
		
		public static final class ESTADO_REQUISITO {
			public static final String CODIGO 		= "ESTADO_REQUISITO";	
			public static final String POR_EVALUAR	= "POR_EVALUAR";
			public static final String CUMPLE		= "CUMPLE";
			public static final String NO_CUMPLE	= "NO_CUMPLE";
			public static final String OBSERVADO	= "OBSERVADO";
			public static final String NO_APLICA	= "NO_APLICA";
		}
		public static final class TIPO_DOCUMENTO_ACREDITA {
			public static final String CODIGO 		= "TIPO_DOCUMENTO_ACREDITA";	
			//public static final String ACREDITAN		= "ACREDITAN";
			public static final String OTRO_REQUISITO	= "OTRO_REQUISITO";
			public static final String PERFIL			= "PERFIL";
		}
		
		public static final class RESULTADO_EVALUACION_TEC_ADM {
			public static final String CODIGO 		= "RESULTADO_EVALUACION_TEC_ADM";
			public static final String ASIGNADO		= "ASIGNADO";
			public static final String EN_PROCESO	= "EN_PROCESO";
			public static final String EN_APROBACION= "EN_APROBACION";
			public static final String CONCLUIDO	= "CONCLUIDO";
		}
		
		public static final class APROBADORES_ADMINISTRATIVOS {
			public static final String CODIGO 		= "APROBADORES_ADMINISTRATIVOS";
		}
		
		public static final class GRUPOS {
			public static final String CODIGO 		= "GRUPOS";
			public static final String G1 			= "G1";
			public static final String G2 			= "G2";
		}
		
		public static final class ESTADO_ARCHIVO {
			public static final String CODIGO 		= "ESTADO_ARCHIVO";
			public static final String CARGADO 		= "CARGADO";
			public static final String ASOCIADO 	= "ASOCIADO";
		}
		
		
		
//		public static final class OTROS_REQUISITOS {
//			public static final String CODIGO 		= "OTROS_REQUISITOS";
//		}
		
		public static final class OTROS_DOCUMENTOS_PN {
			public static final String CODIGO 					= "OTROS_DOCUMENTOS_PN";
			public static final String DJ						= "OPN03";
			public static final String RUC						= "OPN02";
			public static final String DNI						= "OPN01";
			public static final String CONSTANCIA_APROBACION	= "OPN05";			
			public static final String TERMINOS_CONDICIONES		= "OPN04";

		}
		
		public static final class OTROS_DOCUMENTOS_PN_POSTOR {
			public static final String CODIGO 			= "OTROS_DOCUMENTOS_PN_POSTOR";
			public static final String INSTRUMENTO		="OPJ02";
			public static final String RUC				="OPJ03";
			public static final String DJ				="OPJ04";
			
			public static final String TERMINOS_CONDICIONES	="OPJ05";
			
		}
		
		public static final class OTROS_DOCUMENTOS_PJ {
			public static final String CODIGO 			= "OTROS_DOCUMENTOS_PJ";
			public static final String DOC_ACREDITE		="OPJ01";
			public static final String INSTRUMENTO		="OPJ02";
			public static final String RUC				="OPJ03";
			public static final String DJ				="OPJ04";
			public static final String TERMINOS_CONDICIONES	="OPJ05";

		}
		
		public static final class OTROS_DOCUMENTOS_EXTRANJERO {
			public static final String CODIGO 			= "OTROS_DOCUMENTOS_EXTRANJERO";
			public static final String DOC_ACREDITE		="OEXT01";
			public static final String INSTRUMENTO		="OEXT02";
			public static final String TERMINOS_CONDICIONES	="OEXT03";
		}
		
		
		
		public static final class TIPO_ESTUDIO {
			public static final String CODIGO 		= "TIPO_ESTUDIO";
			public static final String ACADEMICOS 		= "ACADEMICOS";
			public static final String CAPACITACION = "CAPACITACION";
			
			
		}
		
		
		public static final class GRADO_ACADEMICO {
			public static final String CODIGO 		= "GRADO_ACADEMICO";
			public static final String E_UNIVERSITARIO 	= "E_UNIVERSITARIO";
			public static final String E_TECNICO		= "E_TECNICO";
			public static final String TECNICO			= "TECNICO";
			public static final String BACHILLER		= "BACHILLER";
			public static final String TITULADO			= "TITULADO";
			public static final String MAESTRIA			= "MAESTRIA";
			public static final String DOCTORADO		= "DOCTORADO";
		}
		public static final class FUENTE_ESTUDIO {
			public static final String CODIGO 		= "FUENTE_ESTUDIO";
			public static final String SUNEDU 		= "SUNEDU";
			public static final String MANUAL 		= "MANUAL";
		}
		
		
		public static final class TIPO_EVALUADOR {
			public static final String CODIGO 						= "TIPO_EVALUADOR";
			public static final String ADMINISTRATIVO 				= "ADMINISTRATIVO";
			public static final String TECNICO 						= "TECNICO";
			public static final String APROBADOR_TECNICO 			= "APROBADOR_TECNICO";
			public static final String APROBADOR_ADMINISTRATIVO 	= "APROBADOR_ADMINISTRATIVO";
			
			
			
		}
		
		public static final class RESULTADO_APROBACION {
			public static final String CODIGO 			= "RESULTADO_APROBACION";
			public static final String APROBADO 		= "APROBADO";
			public static final String RECHAZADO 		= "RECHAZADO";
			public static final String ASIGNADO 		= "ASIGNADO";
			public static final String CANCELADO 		= "CANCELADO";
			public static final String REGISTRADO 		= "REGISTRADO";
			
			
		}
		
		
		public static final class PLAZOS {
			public static final String CODIGO 								= "PLAZOS";
			public static final String RESPUESTA_SOLICITUD 					= "PLAZO-01";
			public static final String ASIGNAR_EVALUADORES					= "PLAZO-02";
			public static final String CONCLUIR_EVALUACION_TECNICA			= "PLAZO-03";
			public static final String SUBSANAR_OBSERVACIONES				= "PLAZO-04";
			public static final String REVISION_SUBSANACION_OBSERVACIONES	= "PLAZO-05";
			public static final String RESPUESTA_SUBSANACION_OBSERVACIONES	= "PLAZO-06";
			public static final String PLAZO_ARCHIVAMIENTO					= "PLAZO-14";
			public static final String EVALUACION_5_DIAS					= "PLAZO-15";
			public static final String EVALUACION_1_DIA						= "PLAZO-16";
			public static final String EVALUACION_9_DIAS					= "PLAZO-17";
			public static final String PRESENTAR_PERFECCIONAMIENTO			= "PLAZO-18";
			public static final String SUBSANAR_PERFECCIONAMIENTO			= "PLAZO-19";

		}
		
		public static final class TIPO_NOTIFICACION_SOLICITUD {
			public static final String CODIGO 							= "TIPO_NOTIFICACION_SOLICITUD";
			public static final String RESPUESTA 						= "RESPUESTA";
			public static final String ARCHIVAMIENTO					= "ARCHIVAMIENTO";
			
		}
		
		public static final class TIPO_PERSONA {
			public static final String CODIGO 							= "TIPO_PERSONA";
			public static final String JURIDICA 						= "JURIDICO";
			public static final String EXTRANJERO 						= "PJ_EXTRANJERO";
			public static final String NATURAL							= "NATURAL";
			public static final String PN_POSTOR						= "PN_POSTOR";
			public static final String PN_PERS_PROPUESTO				= "PN_PERS_PROPUESTO";
			
		}
		public static final class ESTADO_SUPERVISORA {
			public static final String CODIGO 						= "ESTADO_SUPERVISORA";
			public static final String VIGENTE 						= "VIGENTE";
			public static final String SUSPENDIDO 					= "SUSPENDIDO";
			public static final String CANCELADO 					= "CANCELADO";
			public static final String BLOQUEADO 					= "BLOQUEADO";
			
		}
		public static final class ESTADO_SUSPENSION_CANCELACION {
			public static final String CODIGO 					= "ESTADO_SUSPENSION_CANCELACION";
			public static final String PROGRAMADO				= "01";
			public static final String PROCESADO_PARCIALMENTE	= "02";
			public static final String PROCESADO				= "03";
			
		}
		
		public static final class SI_NO {
			public static final String CODIGO = "CUENTA_CONFORMIDAD";
			public static final String SI = "SI";
			public static final String NO = "NO";
		}
		
		public static final class ESTADO {
			public static final String CODIGO = "ESTADO";
			public static final String ACTIVO = "ACTIVO";
			public static final String INACTIVO = "INACTIVO";
		}
		
		public static final class ESTADO_SERVICIO {
			public static final String CODIGO = "ESTADO_SERVICIO";
			public static final String EDITAR = "EDITAR";
			public static final String REGISTRAR = "REGISTRAR";
		}
		
		public static final class PARAMETROS {
			public static final String CODIGO = "PARAMETROS";
			public static final String DIRECTOR 	= "P01";
			public static final String LOGO 		= "P02";
			public static final String PRESOL 		= "P03";
			public static final String PRECODIGO	= "P04";
			public static final String CODRESOLUCION= "P05";
			public static final String CODINFORME	= "P06";
			public static final String PREINFORME	= "P07";
		}
		
		public static final class VINCULO_PARENTESCO {
			public static final String CODIGO = "VINCULO_PARENTESCO";
		}
		public static final class SERVICIOS_RESULTADO {
			public static final String CODIGO = "SERVICIOS";
		}
		public static final class PERSONAL_RESULTADO {
			public static final String CODIGO = "PERSONAL";
		}
		public static final class RESULTADO_EVALUACION {
			public static final String CODIGO	 	= "RESULTADO_EVALUACION";
			public static final String POR_EVALUAR 	= "RE_01";
			public static final String CUMPLE 		= "RE_02";
			public static final String NO_CUMPLE 	= "RE_03";
			public static final String OBSERVADO 	= "RE_04";
			public static final String NO_APLICA 	= "RE_06";
			public static final String RESPONDIDO 	= "RE_07";
		}
		
		public static final class RESULTADO_EVALUACION_ADM{
			public static final String CODIGO	 	= "RESULTADO_EVALUACION_ADM";
			public static final String CALIFICA	 	= "REA_01";
			public static final String NO_CALIFICA	= "REA_02";
			public static final String OBSERVADO	= "REA_03";
		}
		public static final class VALORACION_FUNCIONAL {
			public static final String CODIGO = "VALORACION_FUNCIONAL";
		}
		public static final class VALORACION_AFECIVA {
			public static final String CODIGO = "VALORACION_AFECIVA";
		}
		public static final class VALORACION_SOCIO_FAMILIAR {
			public static final String CODIGO = "VALORACION_SOCIO_FAMILIAR";
		}
		public static final class VALORACION_COGNITIVO {
			public static final String CODIGO = "VALORACION_COGNITIVO";
		}
		public static final class SEXO {
			public static final String CODIGO = "SEXO";
		}
		public static final class DOCUMENTO_AVAL {
			public static final String CODIGO = "DOCUMENTO_AVAL";
		}
		public static final class VALORACION_NUTRICIONAL {
			public static final String CODIGO = "VALORACION_NUTRICIONAL";
		}
		public static final class AMBIENTES_INFRAESTRUCTURA {
			public static final String CODIGO = "AMBIENTES_INFRAESTRUCTURA";
		}

		public static final class PROCESO {
			public static final String CODIGO = "PROCESO";
			public static final String ACREDITACION = "ACREDITACION";
			public static final String SUPERVISION = "SUPERVISION";

		}
		public static final class TIPO_DOCUMENTO {
			public static final String CODIGO = "TIPO_DOCUMENTO";
			public static final String RUC 	= "RUC";
			public static final String DNI 	= "DNI";
			public static final String CARNET_EXTRA 	= "CARNET_EXTRA";
			
		}
		public static final class PAISES {
			public static final String CODIGO = "PAISES";
			public static final String PERU = "PERU";

		}
		
		public static final class ESTADO_PROCESO {
			public static final String CODIGO 					= "ESTADO_PROCESO";
			public static final String EN_ELABORACION 			= "EN_ELABORACION";		
			public static final String CONVOCATORIA				= "CONVOCATORIA";
			public static final String PRESENTACION				= "PRESENTACION";
			public static final String ADMISION_CALIFICACION	= "ADMISION_CALIFICACION";
			public static final String DESIGNACION				= "DESIGNACION";	
			public static final String CERRADO					= "CERRADO";
			
		}
		
		public static final class CARGO_MIEMBRO {
			public static final String CODIGO 			= "CARGO_MIEMBRO";
			public static final String C_PRESIDENTE 	= "C_PRESIDENTE";		
			public static final String C_PRIMER			= "C_PRIMER";
			public static final String C_MIEMBRO		= "C_MIEMBRO";
			
		}
		
		public static final class ESTADO_ITEM {
			public static final String CODIGO 			= "ESTADO_ITEM";
			public static final String EN_ELABORACION 	= "EN_ELABORACION";		
			public static final String CONVOCATORIA		= "CONVOCATORIA";
			public static final String PRESENTACION		= "PRESENTACION";
			public static final String DESIGNACION		= "DESIGNACION";	
			public static final String CONSENTIDO		= "CONSENTIDO";	
			public static final String DESIERTO			= "DESIERTO";
			public static final String NULO				= "NULO";
			public static final String SUSPENDIDO		= "SUSPENDIDO";
			public static final String CANCELADO		= "CANCELADO";
			public static final String ADMISION_CALIFICACION = "ADMISION_CALIFICACION";
			public static final String ADJUDICADO		= "ADJUDICADO";
			
			
			
		}
		
		public static final class ESTADO_INVITACION {
			public static final String CODIGO 			= "ESTADO_INVITACION";
			public static final String INVITADO 		= "INVITADO";		
			public static final String CANCELADO 		= "CANCELADO";
			public static final String ACEPTADO			= "ACEPTADO";
			public static final String RECHAZADO		= "RECHAZADO";
			public static final String CADUCADO			= "CADUCADO";	
			
		}

		public static final class ETAPA_PROCESO {
			public static final String CODIGO 			= "ETAPA_PROCESO";
			public static final String ETAPA_PRESENTADO 	= "ETAPA_PRESENTADO";
			public static final Long ETAPA_ABSOLUCION_ORDEN = 5L;
			public static final Long ETAPA_FORMULACION_ORDEN = 4L;
		}
		
		public static final class ESTADO_PRESENTACION{
			public static final String CODIGO 			= "ESTADO_PRESENTACION";
			public static final String NO_PRESENTO 		= "NO_PRESENTADO";
			public static final String PRESENTADO 		= "PRESENTADO";		
		}
		
		public static final class ESTADO_MIEMBRO{
			public static final String CODIGO 			= "ESTADO_MIEMBRO";
			public static final String ACTIVO 			= "ACTIVO";
			public static final String INACTIVO 		= "INACTIVO";		
		}
		
		public static final class ESTADO_SUP_PERFIL {
			public static final String CODIGO 						= "ESTADO_SUP_PERFIL";
			public static final String ACTIVO 						= "ACTIVO";
			public static final String BLOQUEADO 					= "BLOQUEADO";
			
		}
		
		public static final class TIPO_CONFIGURACION {
			public static final String CODIGO 						= "TIPO_CONFIGURACION";
			public static final String EVALUADORES 					= "EVALUADORES";
			public static final String APROBADORES 					= "APROBADORES";
			
		}
		
		public static final class TIPO_FACTURACION {
			public static final String CODIGO 						= "TIPO_FACTURACION";
			public static final String MIXTA 						= "MIXTA";
			public static final String POR_SECTOR 					= "POR_SECTOR";
			
		}
		
		
		public static final class SECTOR {
			public static final String CODIGO 						= "SECTOR";
			public static final String SECTOR_ENERGETICO 			= "SECTOR_ENERGETICO";
			public static final String SECTOR_MINERO 				= "SECTOR_MINERO";
			
		}
		
		public static final class TIPO_MOTIVO_BLOQUEO {
			public static final String CODIGO 					= "TIPO_MOTIVO_BLOQUEO";
			public static final String AUTOMATICO 				= "AUTOMATICO";
			public static final String MANUAL 					= "MANUAL";
			
		}
		
		public static final class MOTIVO_BLOQUEO_DESBLOQUEO {
			public static final String CODIGO 					= "MOTIVO_BLOQUEO_DESBLOQUEO";
			public static final String INVITACION_ACEPTADA 		= "INVITACION_ACEPTADA";
			public static final String INVITACION_CANCELADA 	= "INVITACION_CANCELADA";
			public static final String PROCESO_CANCELADO 		= "PROCESO_CANCELADO";
			public static final String POSTOR_GANADOR 			= "POSTOR_GANADOR";
			public static final String POSTOR_NO_GANADOR 		= "POSTOR_NO_GANADOR";
			
		}
		
		public static final class ACCION_BLOQUEO_DESBLOQUEO {
			public static final String CODIGO 					= "ACCION_BLOQUEO_DESBLOQUEO";
			public static final String BLOQUEO 					= "BLOQUEO";
			public static final String DESBLOQUEO 				= "DESBLOQUEO";
			
		}
		
		public static final class ESTADO_REVERSION {
			public static final String ESTADO_REVERSION_EVALUACION = "ESTADO_REVERSION_EVALUACION";
			public static final String REV_SOLICITADO = "REV_SOLICITADO";
			public static final String REV_APROBADO = "REV_APROBADO";		
			public static final String REV_RECHAZADO = "REV_RECHAZADO";
		}
		
		public static final class ESTADO_PACE {
			public static final String ESTADO_PACE_REGISTRADO = "PACE_REG";
			public static final String ESTADO_PACE_ACTUALIZADO = "PACE_ACT";
			public static final String ESTADO_PACE_APROBADO_DIVISION = "PACE_APR_DV";
			public static final String ESTADO_PACE_OBSERVADO = "PACE_OBS";
			public static final String ESTADO_PACE_APROBADO_ENVIADO = "PACE_APR_EN";
			public static final String ESTADO_PACE_CANCELADO= "PACE_CAN";

		}
		public static final class VALORES {
			public static final String CODIGO = "ADJUDICACION_SIMPLIFICADA";
			public static final String MONTO_SOLES = "MONTO_SOLES";
		}

		public static final String PERFILES = "PERFILES";

		public static final class RECAPTCHA {
			public static final String CODIGO = "ESTADO_RECAPTCHA";
			public static final String ACTIVO = "ACTIVO";
			public static final String INACTIVO = "INACTIVO";
			public static final String ESTADO_ACTUAL = "ESTADO_ACTUAL";
		}

		public static final class ESTADO_REPRESENTANTE {
			public static final String CODIGO = "ESTADO_REPRESENTANTE";
			public static final String ACTIVO = "ACTIVO";
			public static final String INACTIVO = "INACTIVO";
		}

		public static final class ESTADO_OTRO_REQUISITO {
			public static final String CODIGO 		= "ESTADO_OTRO_REQUISITO";
			public static final String ORIGINAL 	= "ORIGINAL";
		}

		public static final class ESTADO_DOCUMENTO {
			public static final String CODIGO 		= "ESTADO_DOCUMENTO";
			public static final String ORIGINAL 	= "ORIGINAL";
			public static final String ACTUAL 		= "ACTUAL";
		}

		public static final class ESTADO_ESTUDIO {
			public static final String CODIGO 		= "ESTADO_ESTUDIO";
			public static final String ORIGINAL 	= "ORIGINAL";
			public static final String ACTUAL 		= "ACTUAL";
		}

		public static final class ORIGEN_REGISTRO {
			public static final String CODIGO			= "ORIGEN_REGISTRO";
			public static final String NORMAL			= "NORMAL";
			public static final String MODIFICACION		= "MODIFICACION";
		}

		public static final class ESTADO_REQUERIMIENTO {
			public static final String CODIGO 						= "ESTADO_REQUERIMIENTO";
			public static final String PRELIMINAR 					= "PRELIMINAR";
			public static final String EN_APROBACION 				= "EN_APROBACION";
			public static final String EN_PROCESO 					= "EN_PROCESO";
			public static final String CONCLUIDO 					= "EN_PROCESO";
			public static final String ARCHIVADO 					= "ARCHIVADO";
			public static final String DESAPROBADO 					= "DESAPROBADO";
		}

		public static final class ESTADO_REQ_INVITACION {
			public static final String CODIGO 						= "ESTADO_REQ_INVITACION";
			public static final String ACEPTADO 					= "ACEPTADO";
			public static final String RECHAZADO 					= "RECHAZADO";
			public static final String CANCELADO 					= "CANCELADO";
			public static final String ELIMINADO 					= "ELIMINADO";
		}

		public static final class ESTADO_TIPO_APROBACION {
			public static final String CODIGO 						= "ESTADO_TIPO_APROBACION";
			public static final String APROBAR 						= "APROBAR";
			public static final String FIRMAR 						= "FIRMAR";
		}

		public static final class ESTADO_GRUPO_APROBACION {
			public static final String CODIGO 						= "ESTADO_GRUPO_APROBACION";
			public static final String JEFE_UNIDAD 					= "JEFE_UNIDAD";
			public static final String GERENTE 						= "GERENTE";
			public static final String GPPM 						= "GPPM";
			public static final String GSE		 					= "GSE";
		}

		public static final class ESTADO_APROBACION {
			public static final String CODIGO 						= "ESTADO_APROBACION";
			public static final String ASIGNADO 					= "ASIGNADO";
			public static final String APROBADO 					= "APROBADO";
			public static final String DESAPROBADO 					= "DESAPROBADO";
		}
	}
	

	public static final class CODIGO_MENSAJE {

		public static final String MENSAJE_ERROR_GENERICO = "E001001";
		public static final String MENSAJE_ERROR_GRANT = "E001002";
		public static final String USUARIO_NO_EXISTE = "E001003";
		public static final String TOKEN_NO_EXISTE = "E001004";
		public static final String TOKEN_NO_VALIDO = "E001005";
		public static final String MENSAJE_VALIDACION_CORREO = "E001006";
		
		public static final String ERROR_EN_SERVICIO = "E001007";

		public static final String ERROR_FECHA_FIN_ANTES_INICIO = "E002001";
		public static final String ERROR_FECHA_INICIO_ANTES_HOY = "E002002";
		
		public static final String AUTENTICACION_METODO_NO_SOPORTADO = "V00501";
		public static final String AUTENTICACION_SIN_CREDENCIALES = "V00502";
		public static final String AUTENTICACION_CREDENCIALES_INCORRECTAS = "V00503";
		public static final String AUTENTICACION_PROBLEMAS_AUTENTICACION = "V00504";
		public static final String AUTENTICACION_USUARIO_NO_EXISTE = "V00505";
		public static final String AUTENTICACION_CUENTA_NO_CONFIRMADA = "V00506";
		public static final String AUTENTICACION_REFRESH_TOKEN_EXP = "V00507";
		public static final String AUTENTICACION_FUNCIONARIO_NO_EXISTE = "V00508";
		public static final String AUTENTICACION_FUNCIONARIO_CREDENCIAL_INCORRECTA = "V00509";
		public static final String AUTENTICACION_SESION_EXPIRADA = "V00510";
		
		public static final String ASIGNACION_EXISTENTE = "V00200";
		public static final String ASIGNACION_YA_ASIGNADA_TECNICO = "V00201";
		public static final String ASIGNACION_YA_ASIGNADA_ADMINISTRATIVO = "V00202";
		public static final String ASIGNACION_NO_CORRESPONDE = "V00203";
		
		
		public static final String ARCHIVO_NO_ENCONTRADO 	= "V00300";
		public static final String ARCHIVO_NO_ENVIADO 		= "V00301";
		public static final String ARCHIVO_CODIGO_REQUERIDO = "V00302";
		public static final String ARCHIVO_NO_SE_PUEDE_LEER = "V00303";
		public static final String ARCHIVO_PROBLEMA_SUBIR_ALFRESCO		= "V00304";		
		public static final String ARCHIVO_PROBLEMA_DESCARGAR_ALFRESCO	= "V00305";
		public static final String ARCHIVO_SUBIR_ARCHIVO = "V00306";
		
		
		public static final String SOLICITUD_GUARDAR_FORMATO_04 = "V00400";
		public static final String SOLICITUD_CREAR_EXPEDIENTE	= "V00401";
		
		
		public static final String DISTRITO_NO_ENVIADO			= "V00402";
		public static final String DEPARTAMENTO_NO_ENVIADO		= "V00403";
		public static final String PROVINCIA_NO_ENVIADO			= "V00404";
		public static final String CORREO_NO_ENVIADO			= "V00405";
		public static final String RAZONSOCIAL_NO_ENVIADO		= "V00406";
		public static final String TIPODOC_NO_ENVIADO			= "V00407";
		public static final String PERSONA_NO_ENVIADO			= "V00408";
		public static final String DOC_NO_ENVIADO				= "V00409";
		public static final String NOMBRE_PERSONA_NO_ENVIADO	= "V00410";
		public static final String APELLIDO_PATERNO_NO_ENVIADO	= "V00411";
		public static final String APELLIDO_MATERNO_NO_ENVIADO	= "V00412";
		public static final String PARTIDA_REGISTRAL_NO_ENVIADO	= "V00413";
		public static final String SOLICITUD_NO_ENVIADO			= "V00414";
		public static final String REPRESENTANTE_NO_ENVIADO		= "V00415";
		public static final String R_TIPO_DOC_NO_ENVIADO		= "V00416";
		public static final String R_NOMBRE_NO_ENVIADO			= "V00417";
		public static final String R_APATERNO_NO_ENVIADO		= "V00418";
		public static final String R_AMATERNO_NO_ENVIADO		= "V00419";
		public static final String R_NUM_DOC_NO_ENVIADO			= "V00420";
		public static final String CODIGO_DEPARTAMENTO_NOENVIADO= "V00421";
		public static final String CODIGO_DISTRITO_NOENVIADO	= "V00422";
		public static final String CODIGO_PROVINCIA_NOENVIADO	= "V00423";
		public static final String SOLICITUD_PRESENTADA			= "V00424";
		public static final String ERROR_OBTENER_ESTUDIO		= "V00425";
		public static final String SOLICITUD_NO_ASIGNADA_EVALUACION_TECNICA = "V00426";
		public static final String SOLICITUD_NO_ASIGNADA_EVALUACION_ADMINISTRATIVA= "V00427";
		public static final String SOLICITUD_GUARDAR_FORMATO_SUBSANAR = "V00428";
		public static final String SOLICITUD_GUARDAR_FORMATO_RESULTADO = "V00429";
		
		public static final String SOLICITUD_ENVIAR_DOCUMENTO_RESULTADO	= "V00430";
		public static final String SOLICITUD_ENVIAR_DOCUMENTO_SUBNACION	= "V00431";
		public static final String SOLICITUD_ID_OTRO_REQUISITO_REQUERIDO	= "V00432";
		public static final String SOLICITUD_ARCHIVAR_EXPEDIENTE 			= "V00433";
		
		public static final String INGRESE_TELEFONO1 			= "V00434";
		public static final String INGRESE_TELEFONO2 			= "V00435";
		public static final String INGRESE_TELEFONO3 			= "V00436";
		public static final String SOLICITUD_EN_TRAMITE 		= "V00437";
		public static final String SOLICITUD_AFILIAR_SNE		= "V00438";
		public static final String SOLICITUD_AGREGAR_DOCUMENTOS	= "V00439";
		
		public static final String ID_SOLICITUD_NO_ENVIADO		= "V00600";
		public static final String P_SECTOR_NO_ENVIADO			= "V00601";
		public static final String P_SUBSECTOR_NO_ENVIADO		= "V00602";
		public static final String P_ACTIVIDAD_NO_ENVIADO		= "V00603";
		public static final String P_SUBCATEGORIA_NO_ENVIADO	= "V00604";
		public static final String P_PERFIL_NO_ENVIADO			= "V00605";
		
		

		public static final String ESPECIALIDAD_NO_ENVIADO		= "V00606";
		public static final String INSTITUCION_NO_ENVIADO		= "V00607";
		public static final String E_TIPO_NO_ENVIADO			= "V00608";
		public static final String E_ARCHIVOS_NO_ENVIADO		= "V00609";
		public static final String COLEGIATURA_NO_ENVIADO		= "V00610";
		public static final String INST_COLEGIATURA_NO_ENVIADO	= "V00611";
		public static final String FECHA_COLEGIATURA_NO_ENVIADO	= "V00612";
		public static final String FECHA_GRADO_NO_ENVIADO		= "V00613";
		public static final String FECHA_VIGENCIA_NO_ENVIADO	= "V00614";
		public static final String FECHA_INICIO_NO_ENVIADO		= "V00615";
		public static final String FECHA_FIN_NO_ENVIADO			= "V00616";
		public static final String HORAS_NO_ENVIADO				= "V00617";
		public static final String DESCRIPCION_NO_ENVIADO		= "V00618";
		public static final String DURACION_NO_ENVIADO			= "V00619";
		public static final String ENTIDAD_NO_ENVIADO			= "V00620";
		public static final String D_DOC_NO_ENVIADO				= "V00621";
		public static final String PAIS_NO_ENVIADO				= "V00622";
		public static final String CODIGO_NO_ENVIADO			= "V00623";
		public static final String CONFORMIDAD_NO_ENVIADO		= "V00624";
		public static final String MONTO_NO_ENVIADO				= "V00625";
		public static final String MONTO_SOL_NO_ENVIADO			= "V00626";
		public static final String TIPO_CAMBIO_NO_ENVIADO		= "V00627";
		public static final String MONTO_CAMBIO_NO_ENVIADO		= "V00628";
		public static final String P_UNIDAD_NO_ENVIADO			= "V00629";
		
		public static final String ID_REQUISITO_NO_ENVIADO		= "V00630";
		public static final String TIPO_NO_ENVIADO				= "V00631";
		public static final String ID_EVALUACION_NO_ENVIADO		= "V00632";
		public static final String TIPO_REQUISITO_NO_ENVIADO	= "V00633";
		public static final String ID_USUARIO_NO_ENVIADO		= "V00634";
		public static final String ID_TIPO_NO_ENVIADO			= "V00635";
		public static final String ID_GRUPO_NO_ENVIADO			= "V00636";
		public static final String FECHA_INICIO_MAYOR			= "V00637";
		
		public static final String SECTOR_YA_EXISTE				= "V00638";
		public static final String OTRO_REQUISITO_REQUERIDOS 	= "V00639";
		public static final String INGRESE_PERFILES 			= "V00640";
		public static final String INGRESE_GRADOS_ACADEMICOS	= "V00641";
		public static final String INGRESE_CAPACITACIONES		= "V00642";
		public static final String INGRESE_DOCUMENTOS_EXPERIENCIA	= "V00643";
		public static final String ASIGNE_EVALUADORES			= "V00644";
		public static final String EVALUADORES_REPETIDOS		= "V00645";
		public static final String ESTUDIO_SIN_ARCHIVO			= "V00646";
		public static final String GRUPOS_NO_EXISTE				= "V00647";
		public static final String EVALUE_TODO_REQUISITOS		= "V00648";
		public static final String FINALICE_TODO_REQUISITOS		= "V00656";
		public static final String INGRESE_APROBADORES_TECNICOS = "V00649";
		public static final String RESPONDA_EVALUACIONES_OBSERVADA	= "V00650";
		public static final String P_NO_PUEDE_EDITAR_SOLICITUD  = "V00651";	
		public static final String INGRESE_SECTORES 			= "V00652";
		public static final String ERROR_EMPRESA_SANCIONADA 	= "V00653";
		public static final String MONTO_FACTURADO_MAYOR 		= "V00654";
		public static final String ACCESO_NO_AUTORIZADO 		= "V00655";
		
		
		public static final String  SUSPENSION_CANCELACION_FECHA_INICIO_MAYOR= "V00700";
		public static final String SUSPENSION_CANCELACION_TIENE_SUSPENSION_CANCELACION_PENDIENTE = "V00701";
		public static final String USUARIO_EXISTENTE = "V00800";
		public static final String USUARIO_PASSWORD_DIFERENTE = "V00801";
		public static final String CONTRASENIA_CORTA =	 "V00802";			
		public static final String NUMERO_PROCESO_NO_ENVIADO 	=	 "V00900";
		public static final String NOMBRE_PROCESO_NO_ENVIADO 	=	 "V00901";
		public static final String NUMERO_EXPEDIENTE_NO_ENVIADO =	 "V00902";
		public static final String AREA_NO_ENVIADA 				=	 "V00903";
		public static final String ETAPA_NO_ENVIADA 			=	 "V00904";
		public static final String FECHA_MINIMA_MENOR 			=	 "V00905";
		public static final String PROCESO_NO_ENVIADO 			=	 "V00906";
		public static final String REGISTRO_EXISTENTE_ETAPA 	=	 "V00907";
		public static final String USUARIO_NO_ENVIADO 			=	 "V00908";
		public static final String CARGO_NO_ENVIADO 			=	 "V00909";
		public static final String EXISTE_CARGO_PRESIDENTE 		=	 "V00910";
		public static final String NUMERO_ITEM_NO_ENVIADO 		=	 "V00911";
		public static final String NUMERO_PRO_NO_ENVIADO 		=	 "V00912";
		public static final String DIVISA_NO_ENVIADO 			=	 "V00913";
		public static final String MONTO_SOLES_NO_ENVIADO 		=	 "V00914";
		public static final String MONTO_TIPO_CAMBIO_NO_ENVIADO =	 "V00915";
		public static final String MINIMO_ITEM_PROCESO 			=	 "V00916";
		public static final String REGISTRAR_MIEMBROS 			=	 "V00917";
		public static final String EXISTE_CARGO_TITULAR			=	 "V00918";
		public static final String EXISTE_CARGO_TITULAR_3 		=	 "V00919";
		public static final String EDITAR_ESTADO_ELABORACION 	=	 "V00920";
		public static final String PROCESO_UUID_NO_ENVIADO 		=	 "V00921";
		public static final String E_PRESENTACION_NO_AGREGADA 	=	 "V00922";
		public static final String NO_ERES_M_PRESIDENTE 		=	 "V00923";
		public static final String NO_CAMBIAR_ESTADO 			=	 "V00924";
		public static final String EDITAR_ELABORACION 			=	 "V00925";
		public static final String PROPUESTA_UUID_NO_ENVIADO 	=	 "V00926";
		public static final String CONSORCIO_NO_ENVIADO 		=	 "V00927";
		public static final String USUARIO_NO_ES_SUPERVISORA 	=	 "V00928";
		public static final String REGISTRO_EXISTENTE_USUARIO 	=	 "V00929";
		public static final String CANTIDAD_PROFESIONALES 		=	 "V00930";
		public static final String EXISTE_INVITACION 			=	 "V00931";
		public static final String SUP_CANCELADA_SUSPENDIDA 	=	 "V00932";
		public static final String SUP_BLOQUEADO 				=	 "V00933";
		public static final String PROPUESTA_YA_EXISTE 			=	 "V00934";
		public static final String AGREGAR_ARCHIVO_TECNICA 		=	 "V00935";
		public static final String AGREGAR_ARCHIVO_ECONOMICA 	=	 "V00936";	
		public static final String NO_CUMPLE_FACTURACION 		=	 "V00937";
		public static final String CUMPLE_PROFESIONALES 		=	 "V00938";
		public static final String PROF_BLOQUEADO 				=	 "V00939";
		public static final String MONTO_PROPUESTA_ECONOMICA 	=	 "V00940";
		public static final String PROFESIONAL_NO_RESTRICTIVO 	=	 "V00941";
		public static final String PROCESO_ITEM_PRESENTACION 	=	 "V00942";
		public static final String ESTADO_INVITACION_EDITAR 	=	 "V00943";
		public static final String F_INICIO_PRESENTACION 		=	 "V00944";
		public static final String F_FIN_PRESENTACION 			=	 "V00945";
		public static final String F_INICIO_CONVOCATORIA 		=	 "V00946";
		public static final String F_FIN_CONVOCATORIA 			=	 "V00947";
		public static final String F_INICIO_ADM_CERRADO			=	 "V00948";
		public static final String F_FIN_ADM_CERRADO			=	 "V00949";
		public static final String SOLO_10_ARCHIVOS				=	 "V00950";
		public static final String ARCHIVO_TAMANIO 				= 	 "V00951";
		public static final String AGREGAR_ELABORACION 			= 	 "V00952";
		public static final String AGREGAR_PERFIL 				= 	 "V00953";
		public static final String MINIMO_PRO_PERFIL 			= 	 "V00954";
		public static final String NOMBRE_PROCESO_EXISTE		= 	 "V00955";
		public static final String NUMERO_PROCESO_EXISTE 		= 	 "V00956";
		public static final String NRO_PROCESO_ITEM_EXISTE 		= 	 "V00957";
		public static final String PROFESIONAL_NO_ENVIADO 		= 	 "V00958";
		public static final String ITEM_DIFERENTE_SUBSECTOR 	= 	 "V00959";
		public static final String INV_ESTADO_PRESENTACION   	=    "V00960";
		public static final String PERFIL_YA_EXISTE 			=	 "V00980";
		public static final String MONTO_FACTURADO 				=	 "V00981";
		public static final String SOLICITUD_GUARDAR_FORMATO_TECNICO	=	"V00982";
		public static final String SOLICITUD_GUARDAR_FORMATO_ADMINISTRATIVO	=	"V00997"; //AFC
		public static final String ADJUNTAR_DOCUMENTO_TECNICO	=	"V00983";
		public static final String SELECCIONAR_GANADOR	=	"V00984";
		public static final String TIPO_FACTURACION_NO_ENVIADO	=	"V00985";
		public static final String FACTURACION_MINIMA_NO_CUMPLE	=	"V00986";
		
		public static final String AGREGAR_GRUPO_MENOR				= "V00987";
		public static final String AUTENTICACION_CONFIGURE_ID_USUARIO_SIGED = "V00988";
		public static final String SELECCIONAR_GANADOR_DESIGNACION 	= "V00989";
		public static final String CAMBIAR_GANADOR_DESIGNACION 	= "V00990";
		public static final String FECHA_ETAPA_INICIO	= "V00991";
		public static final String FECHA_ETAPA_FIN	= "V00992";
		public static final String GANADOR_NO_SELECCIONADO	= "V00993";
		
		public static final String FECHA_INICIO_MAYOR_IGUAL	= "V00994";
		
		public static final String ESTADO_NO_EN_PROCESO	= "V00995";
		public static final String EVALUACION_NO_FINALIZADO = "V00996";
		public static final String NRO_DE_CONTRATO_YA_EXISTE = "V00998";
		public static final String DOCUMENTO_EXISTE_SOLICITUD_SECTOR_SUBSECTOR = "V00999";
		public static final String EXISTE_SECTOR_SUBSECTOR = "V01000";
		public static final String SECCION_NO_ENVIADA = "V01001";
		public static final String NUMERAL_NO_ENVIADA = "V01002";
		public static final String LITERAL_NO_ENVIADA = "V01003";
		public static final String PAGINA_NO_ENVIADA = "V01004";
		public static final String CONSULTA_NO_ENVIADA = "V01005";
		public static final String NO_EXISTE_ETAPA = "V01006";
		public static final String FUERA_FECHA_CONSULTA = "V01007";
		public static final String PRESENTACION_FUERA_FECHA = "V01008";
		public static final String ASIGNACION_FUERA_PROCESO = "V01009";
		public static final String ARCHIVO_ELIMINAR_USUARIO = "V01010";
		public static final String EVALUACION_FINALIZADO = "V01011";
		public static final String ARCHIVO_NOMBRE_DUPLICADO = "V01012";
		public static final String SOLICITUD_PERFECCIONAMIENTO_SIN_FECHA = "V01013";
		public static final String ESTADO_TIPO_INCORRECTO = "V01014";
		public static final String ARCHIVO_DUPLICADO = "V01015";
		public static final String SOLICITUD_SIN_CAMBIOS = "V01016";
		public static final String DJ_AUSENTE = "V01017";
		public static final String REQUERIMIENTO_EN_PROCESO = "V01018";
		public static final String SIAF_NO_ENVIADO = "V01019";
		public static final String REQUERIMIENTO_NO_ENCONTRADO = "V01020";
	}
	public static final class ROLES {
		public static final String RESPONSABLE_ADMINISTRATIVO 	= "01";
		public static final String RESPONSABLE_TECNICO 			= "02";
		public static final String EVALUADOR_ADMINISTRATIVO 	= "03";
		public static final String EVALUADOR_TECNICO 			= "04";
		public static final String APROBADOR_TECNICO 			= "05";
		public static final String USUARIO_EXTERNO				= "06";
		public static final String APROBADOR_ADMINISTRATIVO		= "07";
		public static final String ADMINISTRADOR_PROCESOS		= "08";
	}
	
	public static final class OPCIONES_EXTERNO {
		public static final String BANDEJA_INVITACIONES 		= "OP_24";
	}
	
	public static final class BITACORA {
		
		public static final class COD_MENSAJE {
			public static final String REGISTRO_PROPUESTA			= "V00511";
			public static final String PRESENTACION 				= "V00512";		
			public static final String REGISTRO_PRO_TECNICA_INI		= "V00513";
			public static final String REGISTRO_PRO_TECNICA_FIN		= "V00514";
			public static final String REGISTRO_PRO_TECNICA_ERROR	= "V00515";
			public static final String REGISTRO_PRO_ECONOMICA_INI	= "V00516";
			public static final String REGISTRO_PRO_ECONOMICA_FIN	= "V00517";
			public static final String REGISTRO_PRO_ECONOMICA_ERROR	= "V00518";
			public static final String MENSAJE_DEFECTO				= "Error sin mensaje";
			
		}
		public static final String APROBADOR_ADMINSTRATIVO		= "07";
	}
	
	public static final class DIVISION {
		
		public static final class IDENTIFICADOR {
			public static final Long ID_GSM = 5L;
		}
	}

	public static final class TIPO_SOLICITUD_PERF_CONTRATO {
		public static final String INSCRIPCION 			= "1";
		public static final String SUBSANACION 		= "2";
	}

	public static final class FLAG_PROCESO_SUBSANACION {
		public static final String INSCRIPCION 			= "0";
		public static final String SUBSANACION 		= "1";
	}

	public static final class FLAG_PERSONAL_PERF_CONTRATO {
		public static final String SI 		= "1";
		public static final String NO 		= "0";
	}

	public static final class ESTADO_PROCESO_PERF_CONTRATO {
		public static final String PRELIMINAR 		= "1";
		public static final String EN_PROCESO 		= "2";
		public static final String OBSERVADO 		= "3";
		public static final String CONCLUIDO 		= "4";
		public static final String ARCHIVADO 		= "5";
	}

	public static final class DESC_PROCESO_PERF_CONTRATO {
		public static final String PRELIMINAR 		= "PRELIMINAR";
		public static final String EN_PROCESO 		= "EN_PROCESO";
		public static final String OBSERVADO 		= "OBSERVADO";
		public static final String CONCLUIDO 		= "CONCLUIDO";
		public static final String ARCHIVADO 		= "ARCHIVADO";
	}

}
