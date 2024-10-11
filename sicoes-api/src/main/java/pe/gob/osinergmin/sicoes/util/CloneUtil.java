package pe.gob.osinergmin.sicoes.util;

import java.util.Date;
import java.util.UUID;

import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.DictamenEvaluacion;
import pe.gob.osinergmin.sicoes.model.Documento;
import pe.gob.osinergmin.sicoes.model.Estudio;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.OtroRequisito;
import pe.gob.osinergmin.sicoes.model.Persona;
import pe.gob.osinergmin.sicoes.model.Representante;
import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.SupervisoraPerfil;
import pe.gob.osinergmin.sicoes.model.SupervisoraRepresentante;
import pe.gob.osinergmin.sicoes.model.Usuario;

public class CloneUtil {

	
	public static Representante clonarRepresentante(Representante representante,Contexto contexto ) {
		Representante representanteNuevo=new Representante();
		representanteNuevo.setTipoDocumento(representante.getTipoDocumento());
		representanteNuevo.setNumeroDocumento(representante.getNumeroDocumento());
		representanteNuevo.setNombres(representante.getNombres());
		representanteNuevo.setApellidoPaterno(representante.getApellidoPaterno());
		representanteNuevo.setApellidoMaterno(representante.getApellidoMaterno());
		
		return representanteNuevo;
	}
	
	public static Usuario clonarUsuario(Usuario usuario,Contexto contexto ) {
		Usuario usuarioNuevo=new Usuario();
		usuarioNuevo.setIdUsuario(usuario.getIdUsuario());
		usuarioNuevo.setCodigoRuc(usuario.getCodigoRuc());
		usuarioNuevo.setCorreo(usuario.getCorreo());
		
		return usuarioNuevo;
	}
	
	public static Archivo clonarArchivo(Archivo archivo,Solicitud sol,Contexto contexto ) {
		Archivo archivoNuevo=new Archivo();
		archivoNuevo.setEstado(archivo.getEstado());
		archivoNuevo.setTipoArchivo(archivo.getTipoArchivo());
		archivoNuevo.setIdSolicitud(sol.getIdSolicitud());
		archivoNuevo.setNombre(archivo.getNombre());
		archivoNuevo.setNombreReal(archivo.getNombreReal());
		archivoNuevo.setNombreAlFresco(archivo.getNombreAlFresco());
		archivoNuevo.setCodigo(UUID.randomUUID().toString());
		archivoNuevo.setTipo(archivo.getTipo());
		archivoNuevo.setDescripcion(archivo.getDescripcion());
		archivoNuevo.setCorrelativo(archivo.getCorrelativo());
		archivoNuevo.setVersion(archivo.getVersion());
		archivoNuevo.setNroFolio(archivo.getNroFolio());
		archivoNuevo.setPeso(archivo.getPeso());
		
		return archivoNuevo;
	}
	
	public static DictamenEvaluacion clonarDictamen(DictamenEvaluacion dictamen,Solicitud sol) {
		DictamenEvaluacion dictamenNuevo =new DictamenEvaluacion();
		dictamenNuevo.setSector(dictamen.getSector());
		dictamenNuevo.setSolicitud(sol);
		dictamenNuevo.setMontoFacturado(dictamen.getMontoFacturado());
		
		return dictamenNuevo;
	}
	
	public static OtroRequisito clonarOtroRequisito(OtroRequisito otroRequisito,Solicitud sol, ListadoDetalle estadoPorEvaluar, Contexto contexto ) {
		 Date fechaHoy = new Date();
		OtroRequisito otroRequisitoNuevo=new OtroRequisito();
		otroRequisitoNuevo.setTipo(otroRequisito.getTipo());
		otroRequisitoNuevo.setTipoRequisito(otroRequisito.getTipoRequisito());
		otroRequisitoNuevo.setFlagElectronico(otroRequisito.getFlagElectronico());
		otroRequisitoNuevo.setFlagFirmaDigital(otroRequisito.getFlagFirmaDigital());
		otroRequisitoNuevo.setFlagActivo(otroRequisito.getFlagActivo());
		otroRequisitoNuevo.setFecActualizacion(fechaHoy);//afc1
		if(Constantes.LISTADO.RESULTADO_EVALUACION.OBSERVADO.equals(otroRequisito.getEvaluacion().getCodigo())) {
			otroRequisitoNuevo.setIdOtroRequisitoPadre(otroRequisito.getIdOtroRequisito());
		}
		
		if(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL.equals(otroRequisito.getTipo().getCodigo())) { 
			if(Constantes.LISTADO.RESULTADO_EVALUACION_ADM.CALIFICA.equals(otroRequisito.getEvaluacion().getCodigo())
					|| Constantes.LISTADO.RESULTADO_EVALUACION_ADM.NO_CALIFICA.equals(otroRequisito.getEvaluacion().getCodigo())) {
				otroRequisitoNuevo.setEvaluacion(otroRequisito.getEvaluacion());
				otroRequisitoNuevo.setFinalizado(otroRequisito.getFinalizado());
				otroRequisitoNuevo.setUsuario(otroRequisito.getUsuario());
				otroRequisitoNuevo.setFechaFinalizador(otroRequisito.getFechaFinalizador());
			}else {
				otroRequisitoNuevo.setEvaluacion(estadoPorEvaluar);
				otroRequisitoNuevo.setIdOtroRequisitoPadre(otroRequisito.getIdOtroRequisito());
			}
		}else {
			otroRequisitoNuevo.setEvaluacion(otroRequisito.getEvaluacion());
		}
		
//		otroRequisitoNuevo.setObservacion(otroRequisito.getObservacion());
		otroRequisitoNuevo.setFechaExpedicion(otroRequisito.getFechaExpedicion());
		
		otroRequisitoNuevo.setSector(otroRequisito.getSector());
		otroRequisitoNuevo.setSubsector(otroRequisito.getSubsector());
		otroRequisitoNuevo.setActividad(otroRequisito.getActividad());
		otroRequisitoNuevo.setUnidad(otroRequisito.getUnidad());
		otroRequisitoNuevo.setSubCategoria(otroRequisito.getSubCategoria());
		otroRequisitoNuevo.setPerfil(otroRequisito.getPerfil());
		otroRequisitoNuevo.setSolicitud(sol);
		otroRequisitoNuevo.setFlagSiged(otroRequisito.getFlagSiged());
//		otroRequisitoNuevo.setFinalizado(otroRequisito.getFinalizado());
		otroRequisitoNuevo.setFechaAsignacion(otroRequisito.getFechaAsignacion());
		return otroRequisitoNuevo;
	}
	
	public static SupervisoraPerfil clonarSupervisoraPerfil(OtroRequisito otroRequisito,Supervisora supervisora,ListadoDetalle estadoActivo,Contexto contexto ) {
		SupervisoraPerfil supervisoraPerfil=new SupervisoraPerfil();	
		supervisoraPerfil.setSector(otroRequisito.getSector());
		supervisoraPerfil.setSubsector(otroRequisito.getSubsector());
		supervisoraPerfil.setActividad(otroRequisito.getActividad());
		supervisoraPerfil.setUnidad(otroRequisito.getUnidad());
		supervisoraPerfil.setSubCategoria(otroRequisito.getSubCategoria());
		supervisoraPerfil.setPerfil(otroRequisito.getPerfil());
		supervisoraPerfil.setSupervisora(supervisora);
		supervisoraPerfil.setEstado(estadoActivo);
		return supervisoraPerfil;
	}
	
	public static Documento clonarDocumento(Documento documento,Solicitud sol,Contexto contexto ) {
		Documento documentoNuevo=new Documento();

		documentoNuevo.setTipo(documento.getTipo());
		documentoNuevo.setTipoDocumento(documento.getTipoDocumento());
		documentoNuevo.setTipoCambio(documento.getTipoCambio());
		documentoNuevo.setCuentaConformidad(documento.getCuentaConformidad());
		documentoNuevo.setTipoIdentificacion(documento.getTipoIdentificacion());
		documentoNuevo.setPais(documento.getPais());
		documentoNuevo.setSolicitud(sol);
		documentoNuevo.setNumeroDocumento(documento.getNumeroDocumento());
		documentoNuevo.setNombreEntidad(documento.getNombreEntidad());
		documentoNuevo.setCodigoContrato(documento.getCodigoContrato());
		documentoNuevo.setDescripcionContrato(documento.getDescripcionContrato());
		documentoNuevo.setFechaInicio(documento.getFechaInicio());
		documentoNuevo.setFechaFin(documento.getFechaFin());
		documentoNuevo.setDuracion(documento.getDuracion());
		documentoNuevo.setFlagVigente(documento.getFlagVigente());
		documentoNuevo.setFechaConformidad(documento.getFechaConformidad());
		documentoNuevo.setMontoContrato(documento.getMontoContrato());
		documentoNuevo.setMontoTipoCambio(documento.getMontoTipoCambio());
		documentoNuevo.setMontoContratoSol(documento.getMontoContratoSol());
		documentoNuevo.setFlagSiged(documento.getFlagSiged());
		if(Constantes.LISTADO.RESULTADO_EVALUACION.OBSERVADO.equals(documento.getEvaluacion().getCodigo())) {
			documentoNuevo.setIdDocumentoPadre(documento.getIdDocumento());
		}
//		documentoNuevo.setObservacion(documento.getObservacion());
		documentoNuevo.setEvaluacion(documento.getEvaluacion());
		
		return documentoNuevo;
	}
	
	public static Estudio clonarEstudio(Estudio estudio,Solicitud sol,Contexto contexto ) {
		Estudio estudioNuevo=new Estudio();
		estudioNuevo.setTipoEstudio(estudio.getTipoEstudio());
		estudioNuevo.setTipo(estudio.getTipo());
		estudioNuevo.setFlagEgresado(estudio.getFlagEgresado());
		estudioNuevo.setColegiatura(estudio.getColegiatura());
		estudioNuevo.setEspecialidad(estudio.getEspecialidad());
		estudioNuevo.setFechaVigenciaGrado(estudio.getFechaVigenciaGrado());
		estudioNuevo.setDetalleTipo(estudio.getDetalleTipo());
		estudioNuevo.setInstitucion(estudio.getInstitucion());
		estudioNuevo.setFlagColegiatura(estudio.getFlagColegiatura());
		estudioNuevo.setFechaVigenciaColegiatura(estudio.getFechaVigenciaColegiatura());
		estudioNuevo.setInstitucionColegiatura(estudio.getInstitucionColegiatura());
		estudioNuevo.setHora(estudio.getHora());
		estudioNuevo.setFechaVigencia(estudio.getFechaVigencia());
		estudioNuevo.setFechaInicio(estudio.getFechaInicio());
		estudioNuevo.setFechaFin(estudio.getFechaFin());
		estudioNuevo.setFlagSiged(estudio.getFlagSiged());
		estudioNuevo.setEvaluacion(estudio.getEvaluacion());
//		estudioNuevo.setObservacion(estudio.getObservacion());
		estudioNuevo.setFuente(estudio.getFuente());
		estudioNuevo.setSolicitud(sol);
		if(Constantes.LISTADO.RESULTADO_EVALUACION.OBSERVADO.equals(estudio.getEvaluacion().getCodigo())) {
			estudioNuevo.setIdEstudioPadre(estudio.getIdEstudio());
		}
		return estudioNuevo;
	}
	
	public static Persona clonarPersona(Persona persona,Contexto contexto ) {
		Persona personaNueva=new Persona();
		personaNueva.setTipoDocumento(persona.getTipoDocumento());
		personaNueva.setTipoPersona(persona.getTipoPersona());
		personaNueva.setNumeroDocumento(persona.getNumeroDocumento());
		personaNueva.setNombreRazonSocial(persona.getNombreRazonSocial());
		personaNueva.setNombres(persona.getNombres());
		personaNueva.setApellidoPaterno(persona.getApellidoPaterno());
		personaNueva.setApellidoMaterno(persona.getApellidoMaterno());
		personaNueva.setCodigoRuc(persona.getCodigoRuc());
		personaNueva.setDireccion(persona.getDireccion());
		personaNueva.setCodigoDepartamento(persona.getCodigoDepartamento());
		personaNueva.setCodigoProvincia(persona.getCodigoProvincia());
		personaNueva.setCodigoDistrito(persona.getCodigoDistrito());
		personaNueva.setNombreDepartamento(persona.getNombreDepartamento());
		personaNueva.setNombreDistrito(persona.getNombreDistrito());
		personaNueva.setNombreProvincia(persona.getNombreProvincia());
		personaNueva.setCodigoPartidaRegistral(persona.getCodigoPartidaRegistral());
		personaNueva.setTelefono1(persona.getTelefono1());
		personaNueva.setTelefono2(persona.getTelefono2());
		personaNueva.setTelefono3(persona.getTelefono3());
		personaNueva.setCorreo(persona.getCorreo());
		personaNueva.setCodigoCliente(persona.getCodigoCliente());
		personaNueva.setPais(persona.getPais());
		personaNueva.setTipoPN(persona.getTipoPN());
		
		return personaNueva;
	}
	
	public static Supervisora clonarSupervisora(Persona persona,Contexto contexto ) {
		Supervisora supervisora=new Supervisora();
		
		
		return actualizarSupervisora(persona, supervisora, contexto);
	}

	public static Supervisora actualizarSupervisora(Persona persona, Supervisora supervisora, Contexto contexto) {
		supervisora.setTipoDocumento(persona.getTipoDocumento());
		supervisora.setNumeroDocumento(persona.getNumeroDocumento());
		supervisora.setNombreRazonSocial(persona.getNombreRazonSocial());
		supervisora.setNombres(persona.getNombres());
		supervisora.setApellidoPaterno(persona.getApellidoPaterno());
		supervisora.setApellidoMaterno(persona.getApellidoMaterno());
		supervisora.setCodigoRuc(persona.getCodigoRuc());
		supervisora.setDireccion(persona.getDireccion());
		supervisora.setCodigoDepartamento(persona.getCodigoDepartamento());
		supervisora.setCodigoProvincia(persona.getCodigoProvincia());
		supervisora.setCodigoDistrito(persona.getCodigoDistrito());
		supervisora.setNombreDepartamento(persona.getNombreDepartamento());
		supervisora.setNombreDistrito(persona.getNombreDistrito());
		supervisora.setNombreProvincia(persona.getNombreProvincia());
		supervisora.setCodigoPartidaRegistral(persona.getCodigoPartidaRegistral());
		supervisora.setTelefono1(persona.getTelefono1());
		supervisora.setTelefono2(persona.getTelefono2());
		supervisora.setTelefono3(persona.getTelefono3());
		supervisora.setCorreo(persona.getCorreo());
		supervisora.setCodigoCliente(persona.getCodigoCliente());
		supervisora.setPais(persona.getPais());
		return supervisora;
	}

	public static SupervisoraRepresentante clonarSupervisoraRepresentante(Representante representante,
			Contexto contexto) {
		SupervisoraRepresentante supervisoraRepresentante=new SupervisoraRepresentante();		
		return clonarSupervisoraRepresentante(representante,
				supervisoraRepresentante, contexto);
	}

	public static SupervisoraRepresentante clonarSupervisoraRepresentante(Representante representante,
			SupervisoraRepresentante supervisoraRepresentante, Contexto contexto) {
		
		supervisoraRepresentante.setTipoDocumento(representante.getTipoDocumento());
		supervisoraRepresentante.setNumeroDocumento(representante.getNumeroDocumento());
		supervisoraRepresentante.setNombres(representante.getNombres());
		supervisoraRepresentante.setApellidoPaterno(representante.getApellidoPaterno());
		supervisoraRepresentante.setApellidoMaterno(representante.getApellidoMaterno());
		
		return supervisoraRepresentante;
	}
}
