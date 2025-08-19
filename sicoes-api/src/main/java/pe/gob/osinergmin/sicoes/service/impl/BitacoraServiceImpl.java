package pe.gob.osinergmin.sicoes.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.Bitacora;
import pe.gob.osinergmin.sicoes.model.Proceso;
import pe.gob.osinergmin.sicoes.model.ProcesoItem;
import pe.gob.osinergmin.sicoes.model.Propuesta;
import pe.gob.osinergmin.sicoes.model.PropuestaConsorcio;
import pe.gob.osinergmin.sicoes.model.PropuestaEconomica;
import pe.gob.osinergmin.sicoes.model.PropuestaTecnica;
import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.repository.BitacoraDao;
import pe.gob.osinergmin.sicoes.service.BitacoraService;
import pe.gob.osinergmin.sicoes.service.ProcesoItemService;
import pe.gob.osinergmin.sicoes.service.ProcesoService;
import pe.gob.osinergmin.sicoes.service.PropuestaService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
public class BitacoraServiceImpl implements BitacoraService {

	Logger logger = LogManager.getLogger(BitacoraServiceImpl.class);

	@Autowired
	BitacoraDao bitacoraDao;
	
	@Autowired 
	ProcesoService procesoService;
	
	@Autowired 
	ProcesoItemService procesoItemService;
	
	@Autowired 
	PropuestaService propuestaService;
	
	@Autowired
	MessageSource messageSource;

	public void registrarSolicitudInscripcion(Solicitud solicitud,Contexto contexto) {
		Bitacora bitacora=getBitacora(solicitud.getUsuario(),contexto);
		bitacora.setDescripcion("Registro de Solicitud de inscripción- Envío");
		bitacoraDao.save(bitacora);
	}
		
	public void registrarSolicitudInscripcionGenerarExpediente(Solicitud solicitud,Contexto contexto) {
		Bitacora bitacora=getBitacora(solicitud.getUsuario(),contexto);
		bitacora.setDescripcion("Registro de Solicitud de inscripción- Generación Expediente - "+solicitud.getNumeroExpediente());
		bitacoraDao.save(bitacora);
	}
	public void registrarSolicitudInscripcionGenerarExpedienteError(Solicitud solicitud,Contexto contexto) {
		Bitacora bitacora=getBitacora(solicitud.getUsuario(),contexto);
		bitacora.setDescripcion("Registro de Solicitud de inscripción- Generación Expediente - Error al generar el Expediente");
		bitacoraDao.save(bitacora);
	}
	
	public void subsanacionSolicitud(Solicitud solicitud,Contexto contexto) {
		Bitacora bitacora=getBitacora(solicitud.getUsuario(),contexto);
		bitacora.setDescripcion("Subsanación de Solicitud de inscripción- Envío");
		bitacoraDao.save(bitacora);
	}
	public void subsanacionSolicitudGenerarExpediente(Solicitud solicitud,Contexto contexto) {
		Bitacora bitacora=getBitacora(solicitud.getUsuario(),contexto);
		bitacora.setDescripcion("Subsanación de Solicitud de inscripción- Generación Expediente -  "+solicitud.getNumeroExpediente());
		bitacoraDao.save(bitacora);
	}
	public void subsanacionSolicitudGenerarExpedienteError(Solicitud solicitud,Contexto contexto) {
		Bitacora bitacora=getBitacora(solicitud.getUsuario(),contexto);
		bitacora.setDescripcion("Subsanación de Solicitud de inscripción- Generación Expediente -  Error al generar el Expediente");
		bitacoraDao.save(bitacora);
	}
	public void logeoUsuario(Usuario usuario,Contexto contexto) {
		Bitacora bitacora=getBitacora(usuario,contexto);
		bitacora.setDescripcion("Logueo Usuario Externo");
		bitacoraDao.save(bitacora);
	}
	
	private Bitacora getBitacora(Usuario usuario,Contexto contexto) {
		Bitacora bitacora =new Bitacora();
		bitacora.setUsuario(usuario);
		bitacora.setFechaHora(new Date());
		AuditoriaUtil.setAuditoriaRegistro(bitacora,contexto);
		return bitacora;
	}
	
	public void presentarPropuesta(Propuesta propuesta,Contexto contexto) {
		Bitacora bitacora = getBitacora(contexto.getUsuario(),contexto);
		ProcesoItem item = procesoItemService.obtener(propuesta.getProcesoItem().getIdProcesoItem(), contexto);
		Proceso proceso = procesoService.obtener(item.getProceso().getIdProceso(), contexto);
		Object[] parametros = new Object[] { proceso.getNumeroProceso() };
		String mensaje = messageSource.getMessage(Constantes.BITACORA.COD_MENSAJE.PRESENTACION, parametros,Constantes.BITACORA.COD_MENSAJE.MENSAJE_DEFECTO, Locale.getDefault());
		bitacora.setDescripcion(mensaje);
		bitacoraDao.save(bitacora);
	}
	
	public void registroPropuesta(Propuesta propuesta,Contexto contexto) {
		Bitacora bitacora = getBitacora(contexto.getUsuario(),contexto);
		ProcesoItem item = procesoItemService.obtener(propuesta.getProcesoItem().getProcesoItemUuid(), contexto);
		Proceso proceso = procesoService.obtener(item.getProceso().getIdProceso(), contexto);
		Object[] parametros = new Object[] { proceso.getNumeroProceso() };
		String mensaje = messageSource.getMessage(Constantes.BITACORA.COD_MENSAJE.REGISTRO_PROPUESTA, parametros,Constantes.BITACORA.COD_MENSAJE.MENSAJE_DEFECTO, Locale.getDefault());
		bitacora.setDescripcion(mensaje);
		bitacoraDao.save(bitacora);
	}

	@Override
	public void registroPropuestaTecnicaInicio(PropuestaTecnica propuestaTecnica, Contexto contexto) {
		Bitacora bitacora = getBitacora(contexto.getUsuario(),contexto);
		Propuesta propuesta =propuestaService.obtener(propuestaTecnica.getPropuestaUuid(), contexto);
		ProcesoItem item = procesoItemService.obtener(propuesta.getProcesoItem().getIdProcesoItem(), contexto);
		Proceso proceso = procesoService.obtener(item.getProceso().getIdProceso(), contexto);
		Object[] parametros = new Object[] { proceso.getNumeroProceso() };
		String mensaje = messageSource.getMessage(Constantes.BITACORA.COD_MENSAJE.REGISTRO_PRO_TECNICA_INI, parametros,Constantes.BITACORA.COD_MENSAJE.MENSAJE_DEFECTO, Locale.getDefault());
		bitacora.setDescripcion(mensaje);
		bitacoraDao.save(bitacora);
	}

	@Override
	public void registroPropuestaTecnicaFin(PropuestaTecnica propuestaTecnica, Contexto contexto) {
		Bitacora bitacora = getBitacora(contexto.getUsuario(),contexto);
		Propuesta propuesta =propuestaService.obtener(propuestaTecnica.getPropuestaUuid(), contexto);
		ProcesoItem item = procesoItemService.obtener(propuesta.getProcesoItem().getIdProcesoItem(), contexto);
		Proceso proceso = procesoService.obtener(item.getProceso().getIdProceso(), contexto);
		Object[] parametros = new Object[] { proceso.getNumeroProceso() };
		String mensaje = messageSource.getMessage(Constantes.BITACORA.COD_MENSAJE.REGISTRO_PRO_TECNICA_FIN, parametros,Constantes.BITACORA.COD_MENSAJE.MENSAJE_DEFECTO, Locale.getDefault());
		bitacora.setDescripcion(mensaje);
		bitacoraDao.save(bitacora);
	}

	@Override
	public void registroPropuestaEconomicaInicio(PropuestaEconomica propuestaEconomica, Contexto contexto) {
		Bitacora bitacora = getBitacora(contexto.getUsuario(),contexto);
		Propuesta propuesta =propuestaService.obtener(propuestaEconomica.getPropuestaUuid(), contexto);
		ProcesoItem item = procesoItemService.obtener(propuesta.getProcesoItem().getIdProcesoItem(), contexto);
		Proceso proceso = procesoService.obtener(item.getProceso().getIdProceso(), contexto);
		Object[] parametros = new Object[] { proceso.getNumeroProceso() };
		String mensaje = messageSource.getMessage(Constantes.BITACORA.COD_MENSAJE.REGISTRO_PRO_ECONOMICA_INI, parametros,Constantes.BITACORA.COD_MENSAJE.MENSAJE_DEFECTO, Locale.getDefault());
		bitacora.setDescripcion(mensaje);
		bitacoraDao.save(bitacora);
	}

	@Override
	public void registroPropuestaEconomicaFin(PropuestaEconomica propuestaEconomica, Contexto contexto) {
		Bitacora bitacora = getBitacora(contexto.getUsuario(),contexto);
		Propuesta propuesta =propuestaService.obtener(propuestaEconomica.getPropuestaUuid(), contexto);
		ProcesoItem item = procesoItemService.obtener(propuesta.getProcesoItem().getIdProcesoItem(), contexto);
		Proceso proceso = procesoService.obtener(item.getProceso().getIdProceso(), contexto);
		Object[] parametros = new Object[] { proceso.getNumeroProceso() };
		String mensaje = messageSource.getMessage(Constantes.BITACORA.COD_MENSAJE.REGISTRO_PRO_ECONOMICA_FIN, parametros,Constantes.BITACORA.COD_MENSAJE.MENSAJE_DEFECTO, Locale.getDefault());
		bitacora.setDescripcion(mensaje);
		bitacoraDao.save(bitacora);
	}

	@Override
	public void registrarPropuestaTecnicaError(PropuestaTecnica propuestaTecnica,String error, Contexto contexto) {
		Bitacora bitacora = getBitacora(contexto.getUsuario(),contexto);
		Propuesta propuesta =propuestaService.obtener(propuestaTecnica.getPropuestaUuid(), contexto);
		ProcesoItem item = procesoItemService.obtener(propuesta.getProcesoItem().getIdProcesoItem(), contexto);
		Proceso proceso = procesoService.obtener(item.getProceso().getIdProceso(), contexto);
		Object[] parametros = new Object[] { proceso.getNumeroProceso(),error };
		String mensaje = messageSource.getMessage(Constantes.BITACORA.COD_MENSAJE.REGISTRO_PRO_TECNICA_ERROR, parametros,Constantes.BITACORA.COD_MENSAJE.MENSAJE_DEFECTO, Locale.getDefault());
		bitacora.setDescripcion(mensaje);
		bitacoraDao.save(bitacora);
	}

	@Override
	public void registrarPropuestaEconomicaError(PropuestaEconomica propuestaEconomica,String error, Contexto contexto) {
		Bitacora bitacora = getBitacora(contexto.getUsuario(),contexto);
		Propuesta propuesta =propuestaService.obtener(propuestaEconomica.getPropuestaUuid(), contexto);
		ProcesoItem item = procesoItemService.obtener(propuesta.getProcesoItem().getIdProcesoItem(), contexto);
		Proceso proceso = procesoService.obtener(item.getProceso().getIdProceso(), contexto);
		Object[] parametros = new Object[] { proceso.getNumeroProceso(),error };
		String mensaje = messageSource.getMessage(Constantes.BITACORA.COD_MENSAJE.REGISTRO_PRO_ECONOMICA_ERROR, parametros,Constantes.BITACORA.COD_MENSAJE.MENSAJE_DEFECTO, Locale.getDefault());
		bitacora.setDescripcion(mensaje);
		bitacoraDao.save(bitacora);
	}

	@Override
	public void registrarEmpresaConsorcio(PropuestaConsorcio propuestaConsorcio, Contexto contexto) {
		Bitacora bitacora=getBitacora(contexto.getUsuario(), contexto);
		bitacora.setDescripcion("Registro de empresa consorcio");
		bitacoraDao.save(bitacora);		
	}

	@Override
	public void registrarEmpresaConsorcioError(PropuestaConsorcio propuestaConsorcio, Contexto contexto) {
		Bitacora bitacora=getBitacora(contexto.getUsuario(), contexto);
		bitacora.setDescripcion("Registro de empresa consorcio - Error al registrar");
		bitacoraDao.save(bitacora);				
	}

	@Override
	public void registrarParticipacionEmpresaConsorcio(List<PropuestaConsorcio> propuestaConsorcio, Contexto contexto) {
		Bitacora bitacora=getBitacora(contexto.getUsuario(), contexto);
		bitacora.setDescripcion("Registro de participación de cada empresa consorcio");
		bitacoraDao.save(bitacora);		
	}

	@Override
	public void registrarParticipacionEmpresaConsorcioError(List<PropuestaConsorcio> propuestaConsorcio, Contexto contexto) {
		Bitacora bitacora=getBitacora(contexto.getUsuario(), contexto);
		bitacora.setDescripcion("Registro de participación de cada empresa consorcio - Error al registrar");
		bitacoraDao.save(bitacora);			
	}

	@Override
	public void registrarModificacionSolicitud(Solicitud solicitud, Contexto contexto) {
		Bitacora bitacora=getBitacora(solicitud.getUsuario(),contexto);
		bitacora.setDescripcion("Registro de Modificacion de Solicitud - Envío");
		bitacoraDao.save(bitacora);
	}
}
