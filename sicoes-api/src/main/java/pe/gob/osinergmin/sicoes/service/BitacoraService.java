package pe.gob.osinergmin.sicoes.service;

import java.util.List;

import pe.gob.osinergmin.sicoes.model.Propuesta;
import pe.gob.osinergmin.sicoes.model.PropuestaConsorcio;
import pe.gob.osinergmin.sicoes.model.PropuestaEconomica;
import pe.gob.osinergmin.sicoes.model.PropuestaTecnica;
import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface BitacoraService {

	public void registrarSolicitudInscripcion(Solicitud solicitud,Contexto contexto);
	public void registrarSolicitudInscripcionGenerarExpediente(Solicitud solicitud,Contexto contexto);
	public void registrarSolicitudInscripcionGenerarExpedienteError(Solicitud solicitud,Contexto contexto);
	public void subsanacionSolicitud(Solicitud solicitud,Contexto contexto);
	public void subsanacionSolicitudGenerarExpediente(Solicitud solicitud,Contexto contexto);
	public void subsanacionSolicitudGenerarExpedienteError(Solicitud solicitud,Contexto contexto) ;
	public void logeoUsuario(Usuario usuario,Contexto contexto);
	public void presentarPropuesta(Propuesta propuesta,Contexto contexto);
	public void registroPropuesta(Propuesta propuesta, Contexto contexto);
	public void registroPropuestaTecnicaInicio(PropuestaTecnica propuestaTecnica, Contexto contexto);
	public void registroPropuestaTecnicaFin(PropuestaTecnica propuestaTecnica, Contexto contexto);
	public void registroPropuestaEconomicaInicio(PropuestaEconomica propuestaEconomica, Contexto contexto);
	public void registroPropuestaEconomicaFin(PropuestaEconomica propuestaEconomica, Contexto contexto);
	public void registrarPropuestaTecnicaError(PropuestaTecnica propuestaTecnica,String error, Contexto contexto);
	public void registrarPropuestaEconomicaError(PropuestaEconomica propuestaEconomica,String error, Contexto contexto);
	public void registrarEmpresaConsorcio(PropuestaConsorcio propuestaConsorcio, Contexto contexto);
	public void registrarEmpresaConsorcioError(PropuestaConsorcio propuestaConsorcio, Contexto contexto);
	public void registrarParticipacionEmpresaConsorcio(List<PropuestaConsorcio> propuestaConsorcio ,Contexto contexto);
	public void registrarParticipacionEmpresaConsorcioError(List<PropuestaConsorcio> propuestaConsorcio ,Contexto contexto);
	void registrarModificacionSolicitud(Solicitud solicitud,Contexto contexto);
}
