package pe.gob.osinergmin.sicoes.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.util.Constantes;

@Repository
public interface SolicitudDao extends JpaRepository<Solicitud, Long> {
	
	@Query("select s from Solicitud s "			
			+ "left join fetch s.persona p "
			+ "left join fetch p.tipoPersona tipPersona "
			+ "where s.solicitudUuid=:solicitudUuid")
	public Solicitud obtenerTipoPersona(String solicitudUuid);
	
	@Query("select s from Solicitud s "			
			+ "left join fetch s.representante r "
			+ "left join fetch r.tipoDocumento rtd "
			+ "left join fetch s.persona p "
			+ "left join fetch p.tipoPersona tipPersona "
			+ "left join fetch p.tipoDocumento ptd "
			+ "left join fetch p.pais pp "
			+ "left join fetch s.usuario u "
			+ "left join fetch u.pais up "
			+ "left join fetch u.tipoPersona tip "
			+ "left join fetch s.resultadoAdministrativo ra "
			+ "left join fetch s.resultadoTecnico rt "
			+ "left join fetch s.estadoEvaluacionAdministrativa ra "
			+ "left join fetch s.estadoEvaluacionTecnica rt "
			+ "left join fetch s.estado e "
			+ "left join fetch s.estadoRevision er "
			+ "left join fetch s.profesion pr "
			+ "left join fetch s.division d "
			+ "where s.idSolicitud=:idSolicitud")
	public Solicitud obtener(Long idSolicitud);
	
	@Query("select s from Solicitud s "			
			+ "left join fetch s.representante r "
			+ "left join fetch r.tipoDocumento rtd "
			+ "left join fetch s.persona p "
			+ "left join fetch p.tipoPersona tipPersona "
			+ "left join fetch p.tipoDocumento ptd "
			+ "left join fetch p.pais pp "
			+ "left join fetch s.usuario u "
			+ "left join fetch u.pais up "
			+ "left join fetch u.tipoPersona tip "
			+ "left join fetch s.resultadoAdministrativo ra "
			+ "left join fetch s.resultadoTecnico rt "
			+ "left join fetch s.estadoEvaluacionAdministrativa ra "
			+ "left join fetch s.estadoEvaluacionTecnica rt "
			+ "left join fetch s.estado e "
			+ "left join fetch s.estadoRevision er "	
			+ "where s.idSolicitud=:idSolicitud")
	public Solicitud obtenerPadre(Long idSolicitud);
	
	@Query("select s from Solicitud s "		
			+ "left join fetch s.representante r "
			+ "left join fetch r.tipoDocumento rtd "
			+ "left join fetch s.persona p "
			+ "left join fetch p.tipoPersona tipPersona "
			+ "left join fetch p.tipoDocumento ptd "
			+ "left join fetch p.pais pp "
			+ "left join fetch s.usuario u "
			+ "left join fetch u.pais up "
			+ "left join fetch u.tipoPersona tip "
			+ "left join fetch s.resultadoAdministrativo ra "
			+ "left join fetch s.resultadoTecnico rt "
			+ "left join fetch s.estadoEvaluacionAdministrativa ra "
			+ "left join fetch s.estadoEvaluacionTecnica rt "
			+ "left join fetch s.estado e "
			+ "left join fetch s.estadoRevision er "	
			+ "where s.idSolicitudPadre=:idSolicitud")
	public Solicitud obtenerIdSolicitudPadre(Long idSolicitud);
	
	@Query("select s.idSolicitud from Solicitud s "			
			+ "where s.solicitudUuid=:solicitudUuid")
	public Long obtenerId(String solicitudUuid);
	

	@Query(value="select distinct s from Solicitud s "
			+ "left join fetch s.representante r "
			+ "left join fetch s.persona p "
			+ "left join fetch s.usuario u "
			+ "left join fetch u.pais up "
			+ "left join fetch p.tipoPersona tip "
			+ "left join fetch s.resultadoAdministrativo ra "
			+ "left join fetch s.resultadoTecnico rt "
			+ "left join fetch s.tipoSolicitud ts "
			+ "left join fetch s.estado e "
			+ "left join fetch s.estadoRevision er, OtroRequisito ot left join ot.solicitud ots left join ot.subsector otsb left join ot.perfil otp "

			+ "where (:fechaDesde is null or trunc(s.fechaRegistro)>=:fechaDesde) "
			+ "and (:fechaHasta is null or trunc(s.fechaRegistro)<=:fechaHasta) "
			+ "and (:idEstadoSolicitud is null or e.idListadoDetalle = :idEstadoSolicitud) "
			+ "and (:codigoEstadoBorrador is null or e.codigo <> :codigoEstadoBorrador) "
			+ "and (:idTipoSolicitud is null or ts.idListadoDetalle = :idTipoSolicitud) "
			+ "and (:nroExpediente is null or s.numeroExpediente like :nroExpediente) "
			+ "and (:solicitante is null or concat(p.numeroDocumento,' - ',u.nombreUsuario) like :solicitante) "
			+ "and (:idEstadoRevision is null or er.idListadoDetalle = :idEstadoRevision) "
			+ "and (:idUsuario is null or u.idUsuario=:idUsuario)  and s.flagActivo=1 "
			+ "and s.idSolicitud=ots.idSolicitud "			
			+ "and ( otp.idListadoDetalle in ( "
				+ "select cbp.idListadoDetalle  from ConfiguracionBandeja cb left join cb.perfil cbp left join cb.usuario cbu "
				+ "where cbu.idUsuario=:idUsuarioResponsable and  cbp.idListadoDetalle is not null "
			+ ") "
			+ "or ( otsb.idListadoDetalle in ( "
				+ "select cbs.idListadoDetalle  from ConfiguracionBandeja cb left join cb.subsector cbs left join cb.usuario cbu "
				+ "where cbu.idUsuario=:idUsuarioResponsable and  cb.perfil is null "
			+ ") and otp.idListadoDetalle is null ) )"
//			+ "and (tip.codigo in ('"+Constantes.LISTADO.TIPO_PERSONA.JURIDICA+"','"+Constantes.LISTADO.TIPO_PERSONA.PN_POSTOR+"') or s.idSolicitud in ( "
//				+ "select ots.idSolicitud  from OtroRequisito ot left join ot.solicitud ots left join ot.perfil otp  "
//				+ "where otp.idListadoDetalle in ( "
//					+ "select cbp.idListadoDetalle  from ConfiguracionBandeja cb left join cb.perfil cbp left join cb.usuario cbu "
//					+ "where cbu.idUsuario=:idUsuarioResponsable "
//				+ ") "
//			+ " )) "
			+ "order by s.fechaPresentacion desc",
			countQuery ="select count(distinct s.idSolicitud) from Solicitud s "
					+ "left join s.representante r "
					+ "left join s.persona p "
					+ "left join s.usuario u "
					+ "left join p.tipoPersona tip "
					+ "left join s.resultadoAdministrativo ra "
					+ "left join s.resultadoTecnico rt "
					+ "left join s.tipoSolicitud ts "
					+ "left join s.estado e "
					+ "left join s.estadoRevision er, OtroRequisito ot left join ot.solicitud ots left join ot.subsector otsb left join ot.perfil otp "
					+ "where (:fechaDesde is null or trunc(s.fechaRegistro)>=:fechaDesde) "
					+ "and (:fechaHasta is null or trunc(s.fechaRegistro)<=:fechaHasta) "
					+ "and (:idEstadoSolicitud is null or e.idListadoDetalle = :idEstadoSolicitud) "
					+ "and (:codigoEstadoBorrador is null or e.codigo <> :codigoEstadoBorrador) "
					+ "and (:idTipoSolicitud is null or ts.idListadoDetalle = :idTipoSolicitud) "
					+ "and (:nroExpediente is null or s.numeroExpediente like :nroExpediente) "
					+ "and (:solicitante is null or concat(p.numeroDocumento,' - ',u.nombreUsuario) like :solicitante) "
					+ "and (:idEstadoRevision is null or er.idListadoDetalle = :idEstadoRevision) "
					+ "and (:idUsuario is null or u.idUsuario=:idUsuario)  and s.flagActivo=1 "
					+ "and s.idSolicitud=ots.idSolicitud "			
					+ "and ( otp.idListadoDetalle in ( "
						+ "select cbp.idListadoDetalle  from ConfiguracionBandeja cb left join cb.perfil cbp left join cb.usuario cbu "
						+ "where cbu.idUsuario=:idUsuarioResponsable and  cbp.idListadoDetalle is not null "
					+ ") "
					+ "or ( otsb.idListadoDetalle in ( "
						+ "select cbs.idListadoDetalle  from ConfiguracionBandeja cb left join cb.subsector cbs left join cb.usuario cbu "
						+ "where cbu.idUsuario=:idUsuarioResponsable and  cb.perfil is null "
					+ ") and otp.idListadoDetalle is null ) )"
//					+ "and (tip.codigo in ('"+Constantes.LISTADO.TIPO_PERSONA.JURIDICA+"','"+Constantes.LISTADO.TIPO_PERSONA.PN_POSTOR+"') or s.idSolicitud in ( "
//						+ "select ots.idSolicitud  from OtroRequisito ot left join ot.solicitud ots left join ot.perfil otp  "
//						+ "where otp.idListadoDetalle in ( "
//							+ "select cbp.idListadoDetalle  from ConfiguracionBandeja cb left join cb.perfil cbp left join cb.usuario cbu "
//							+ "where cbu.idUsuario=:idUsuarioResponsable "
//						+ ") "
//					+ " )) "
					)
	public Page<Solicitud> buscarResponsable(Date fechaDesde,Date fechaHasta,Long idTipoSolicitud,	Long idEstadoSolicitud,String nroExpediente,String solicitante,Long idEstadoRevision,Long idUsuario,Long idUsuarioResponsable,String codigoEstadoBorrador,Pageable pageable);


	@Query(value="select s from Solicitud s "
			+ "left join fetch s.representante r "
			+ "left join fetch s.persona p "
			+ "left join fetch p.tipoPersona tipPersona "
			+ "left join fetch s.usuario u "
			+ "left join fetch u.pais up "
			+ "left join fetch u.tipoPersona tip "
			+ "left join fetch s.resultadoAdministrativo ra "
			+ "left join fetch s.resultadoTecnico rt "
			+ "left join fetch s.tipoSolicitud ts "
			+ "left join fetch s.estado e "
			+ "left join fetch s.estadoRevision er "

			+ "where (:fechaDesde is null or trunc(s.fechaRegistro)>=:fechaDesde) "
			+ "and (:fechaHasta is null or trunc(s.fechaRegistro)<=:fechaHasta) "
			+ "and (:idEstadoSolicitud is null or e.idListadoDetalle = :idEstadoSolicitud) "
			+ "and (:codigoEstadoBorrador is null or e.codigo <> :codigoEstadoBorrador) "
			+ "and (:idTipoSolicitud is null or ts.idListadoDetalle = :idTipoSolicitud) "
			+ "and (:nroExpediente is null or s.numeroExpediente like :nroExpediente) "
			+ "and (:solicitante is null or concat(p.numeroDocumento,' - ',u.nombreUsuario) like :solicitante) "
			+ "and (:idEstadoRevision is null or er.idListadoDetalle = :idEstadoRevision) "
			+ "and (:idUsuario is null or u.idUsuario=:idUsuario)  and s.flagActivo=1 "			
			+ "order by s.fechaPresentacion desc,s.fechaRegistro desc",
			countQuery ="select count(s) from Solicitud s "
					+ "left join s.representante r "
					+ "left join s.persona p "
					+ "left join p.tipoPersona tipPersona "
					+ "left join s.usuario u "
					+ "left join s.resultadoAdministrativo ra "
					+ "left join s.resultadoTecnico rt "
					+ "left join s.tipoSolicitud ts "
					+ "left join s.estado e "
					+ "left join s.estadoRevision er "		
					+ "where (:fechaDesde is null or trunc(s.fechaRegistro)>=:fechaDesde) "
					+ "and (:fechaHasta is null or trunc(s.fechaRegistro)<=:fechaHasta) "
					+ "and (:idEstadoSolicitud is null or e.idListadoDetalle = :idEstadoSolicitud) "
					+ "and (:codigoEstadoBorrador is null or e.codigo <> :codigoEstadoBorrador) "
					+ "and (:idTipoSolicitud is null or ts.idListadoDetalle = :idTipoSolicitud) "
					+ "and (:nroExpediente is null or s.numeroExpediente like :nroExpediente) "
					+ "and (:solicitante is null or concat(p.numeroDocumento,' - ',u.nombreUsuario) like :solicitante) "
					+ "and (:idEstadoRevision is null or er.idListadoDetalle = :idEstadoRevision) "
					+ "and (:idUsuario is null or u.idUsuario=:idUsuario)  and s.flagActivo=1 ")
	public Page<Solicitud> buscar(Date fechaDesde,Date fechaHasta,Long idTipoSolicitud,	Long idEstadoSolicitud,String nroExpediente,String solicitante,Long idEstadoRevision,Long idUsuario,String codigoEstadoBorrador,Pageable pageable);

	@Query(value="select s from Solicitud s "
			+ "left join fetch s.representante r "
			+ "left join fetch s.persona p "
			+ "left join fetch s.usuario u "
			+ "left join fetch u.pais up "
			+ "left join fetch u.tipoPersona tip "
			+ "left join fetch s.resultadoAdministrativo ra "
			+ "left join fetch s.resultadoTecnico rt "
			+ "left join fetch s.tipoSolicitud ts "
			+ "left join fetch s.estado e "
			+ "left join fetch s.estadoRevision er "
			+ "left join fetch s.estadoEvaluacionTecnica evt "
			+ "left join fetch s.estadoEvaluacionAdministrativa eva "
			+ "where (:fechaDesde is null or trunc(s.fechaRegistro)>=:fechaDesde) "
			+ "and (:fechaHasta is null or trunc(s.fechaRegistro)<=:fechaHasta) "
			+ "and (:idEstadoSolicitud is null or e.idListadoDetalle = :idEstadoSolicitud) "
			+ "and (:codigoEstadoBorrador is null or e.codigo <> :codigoEstadoBorrador) "
			+ "and (:idTipoSolicitud is null or ts.idListadoDetalle = :idTipoSolicitud) "
			+ "and (:nroExpediente is null or s.numeroExpediente like :nroExpediente) "
			+ "and (:solicitante is null or concat(p.numeroDocumento,' - ',u.nombreUsuario) like :solicitante) "
			+ "and (:idEstadoRevision is null or er.idListadoDetalle = :idEstadoRevision) "
			+ "and (:idEstadoEvalTecnica is null or evt.idListadoDetalle = :idEstadoEvalTecnica) "
			+ "and (:idEstadoEvalAdministrativa is null or eva.idListadoDetalle = :idEstadoEvalAdministrativa) "
			+ "and (s.idSolicitud in (select a.solicitud.idSolicitud from Asignacion  a left join a.tipo t left join a.evaluacion ae where a.usuario.idUsuario=:idUsuario and a.flagActivo=1  and (t.codigo='"+Constantes.LISTADO.TIPO_EVALUADOR.ADMINISTRATIVO+"' or t.codigo='"+Constantes.LISTADO.TIPO_EVALUADOR.TECNICO+"'))) "
			+ "order by s.fechaPresentacion desc",
			countQuery ="select count(s) from Solicitud s "
					+ "left join s.representante r "
					+ "left join s.persona p "
					+ "left join s.usuario u "
					+ "left join s.resultadoAdministrativo ra "
					+ "left join s.resultadoTecnico rt "
					+ "left join s.tipoSolicitud ts "
					+ "left join s.estado e "
					+ "left join s.estadoRevision er "	
					+ "left join s.estadoEvaluacionTecnica evt "
					+ "left join s.estadoEvaluacionAdministrativa eva "
					+ "where (:fechaDesde is null or trunc(s.fechaRegistro)>=:fechaDesde) "
					+ "and (:fechaHasta is null or trunc(s.fechaRegistro)<=:fechaHasta) "
					+ "and (:idEstadoSolicitud is null or e.idListadoDetalle = :idEstadoSolicitud) "
					+ "and (:codigoEstadoBorrador is null or e.codigo <> :codigoEstadoBorrador) "
					+ "and (:idTipoSolicitud is null or ts.idListadoDetalle = :idTipoSolicitud) "
					+ "and (:nroExpediente is null or s.numeroExpediente like :nroExpediente) "
					+ "and (:solicitante is null or concat(p.numeroDocumento,' - ',u.nombreUsuario) like :solicitante) "
					+ "and (:idEstadoRevision is null or er.idListadoDetalle = :idEstadoRevision) "
					+ "and (:idEstadoEvalTecnica is null or evt.idListadoDetalle = :idEstadoEvalTecnica) "
					+ "and (:idEstadoEvalAdministrativa is null or eva.idListadoDetalle = :idEstadoEvalAdministrativa) "
					+ "and (s.idSolicitud in (select a.solicitud.idSolicitud from Asignacion  a left join a.tipo t left join a.evaluacion ae where a.usuario.idUsuario=:idUsuario and a.flagActivo=1  and (t.codigo='"+Constantes.LISTADO.TIPO_EVALUADOR.ADMINISTRATIVO+"' or t.codigo='"+Constantes.LISTADO.TIPO_EVALUADOR.TECNICO+"'))) "
					)
	public Page<Solicitud> buscarEvaluadores(Date fechaDesde, Date fechaHasta, Long idTipoSolicitud,
			Long idEstadoSolicitud, String nroExpediente, String solicitante, Long idEstadoRevision,Long idEstadoEvalTecnica,Long idEstadoEvalAdministrativa, Long idUsuario,
			String codigoEstadoBorrador, Pageable pageable);
	
	
	@Query(value="select s from Solicitud s "
			+ "left join fetch s.representante r "
			+ "left join fetch s.persona p "
			+ "left join fetch s.usuario u "
			+ "left join fetch u.pais up "
			+ "left join fetch u.tipoPersona tip "
			+ "left join fetch s.estadoEvaluacionAdministrativa ra "
			+ "left join fetch s.estadoEvaluacionTecnica rt "
			+ "left join fetch s.tipoSolicitud ts "
			+ "left join fetch s.estado e "
			+ "left join fetch s.estadoRevision er "	
			+ "where "
			+ " (:codigoEstadoBorrador is null or e.codigo <> :codigoEstadoBorrador) "
			+ "and (:nroExpediente is null or s.numeroExpediente like :nroExpediente) "
			+ "and (:solicitante is null or concat(p.numeroDocumento,' - ',u.nombreUsuario) like :solicitante) "
			+ "and (:idTipoSolicitud is null or ts.idListadoDetalle = :idTipoSolicitud) "
			+ "and (:idEstadoRevision is null or er.idListadoDetalle = :idEstadoRevision) "
			+ "and (:idEstadoEvaluacionTecnica is null or rt.idListadoDetalle = :idEstadoEvaluacionTecnica) "
			+ "and (:idEstadoEvaluacionAdministrativa is null or ra.idListadoDetalle = :idEstadoEvaluacionAdministrativa) "
//			+ "and s.flagActivo=1  "
			+ "and (s.idSolicitud in (select a.solicitud.idSolicitud from Asignacion a left join a.tipo t left join a.evaluacion ae   where a.usuario.idUsuario=:idUsuario and a.flagActivo=1  and (t.codigo='"+Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO+"' or t.codigo='"+Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_ADMINISTRATIVO+"') and ae.codigo in ('"+Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO/*+"','"+Constantes.LISTADO.RESULTADO_APROBACION.APROBADO+"','"+Constantes.LISTADO.RESULTADO_APROBACION.RECHAZADO*/+"') ))"
			+ "order by s.fechaPresentacion desc",
			countQuery ="select count(s) from Solicitud s "
					+ "left join s.representante r "
					+ "left join s.persona p "
					+ "left join s.usuario u "
					+ "left join s.estadoEvaluacionAdministrativa ra "
					+ "left join s.estadoEvaluacionTecnica rt "
					+ "left join s.tipoSolicitud ts "
					+ "left join s.estado e "
					+ "left join s.estadoRevision er "
					+ "where "
					+ " (:codigoEstadoBorrador is null or e.codigo <> :codigoEstadoBorrador) "
					+ "and (:nroExpediente is null or s.numeroExpediente like :nroExpediente) "
					+ "and (:solicitante is null or concat(p.numeroDocumento,' - ',u.nombreUsuario) like :solicitante) "
					+ "and (:idTipoSolicitud is null or ts.idListadoDetalle = :idTipoSolicitud) "
					+ "and (:idEstadoRevision is null or er.idListadoDetalle = :idEstadoRevision) "
					+ "and (:idEstadoEvaluacionTecnica is null or rt.idListadoDetalle = :idEstadoEvaluacionTecnica) "
					+ "and (:idEstadoEvaluacionAdministrativa is null or ra.idListadoDetalle = :idEstadoEvaluacionAdministrativa) "
//					+ "and s.flagActivo=1  "
					+ "and (s.idSolicitud in (select a.solicitud.idSolicitud from Asignacion a left join a.tipo t left join a.evaluacion ae   where a.usuario.idUsuario=:idUsuario and a.flagActivo=1  and (t.codigo='"+Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO+"' or t.codigo='"+Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_ADMINISTRATIVO+"') and ae.codigo in ('"+Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO/*+"','"+Constantes.LISTADO.RESULTADO_APROBACION.APROBADO+"','"+Constantes.LISTADO.RESULTADO_APROBACION.RECHAZADO*/+"') ))")
	public Page<Solicitud> buscarAprobador(String nroExpediente, String solicitante,Long idTipoSolicitud, Long idEstadoRevision, Long idEstadoEvaluacionTecnica, Long idEstadoEvaluacionAdministrativa, Long idUsuario,
			String codigoEstadoBorrador, Pageable pageable);

	
	
	@Query(value="select s from Solicitud s "
			+ "left join fetch s.representante r "
			+ "left join fetch r.tipoDocumento rtd "
			+ "left join fetch s.persona p "
			+ "left join fetch p.tipoPersona tipPersona "
			+ "left join fetch p.tipoDocumento ptd "
			+ "left join fetch p.pais pp "
			+ "left join fetch s.usuario u "
			+ "left join fetch u.pais up "
			+ "left join fetch u.tipoPersona tip "
			+ "left join fetch s.resultadoAdministrativo ra "
			+ "left join fetch s.resultadoTecnico rt "
			+ "left join fetch s.estadoEvaluacionAdministrativa ra "
			+ "left join fetch s.estadoEvaluacionTecnica rt "
			+ "left join fetch s.estado e "
			+ "left join fetch s.estadoRevision er "
			+ "where e.codigo ='"+Constantes.LISTADO.ESTADO_SOLICITUD.OBSERVADO+"' AND s.fechaPlazoSub<SYSDATE and s.flagActivo=1 and s.idSolicitudPadre is not null")
	public List<Solicitud> listarSolicitudesXArchivar();
	
	@Query(value="select s from Solicitud s "
			+ "left join fetch s.representante r "
			+ "left join fetch r.tipoDocumento rtd "
			+ "left join fetch s.persona p "
			+ "left join fetch p.tipoPersona tipPersona "
			+ "left join fetch p.tipoDocumento ptd "
			+ "left join fetch p.pais pp "
			+ "left join fetch s.usuario u "
			+ "left join fetch u.pais up "
			+ "left join fetch u.tipoPersona tip "
			+ "left join fetch s.resultadoAdministrativo ra "
			+ "left join fetch s.resultadoTecnico rt "
			+ "left join fetch s.estadoEvaluacionAdministrativa ra "
			+ "left join fetch s.estadoEvaluacionTecnica rt "
			+ "left join fetch s.estado e "
			+ "left join fetch s.estadoRevision er "
			+ "where e.codigo ='"+Constantes.LISTADO.ESTADO_SOLICITUD.OBSERVADO+"' AND s.flagActivo=1 AND s.idSolicitudPadre is not null AND s.fechaPlazoSub is null")
	public List<Solicitud> listarSolicitudesXNotificar();
	
	@Query(value="select count(1) from Solicitud s "
			+ "left join s.usuario u "
			+ "left join s.estado e "
			+ "left join s.persona p "
			+ "left join p.tipoPersona pt "
			+ "where u.idUsuario=:idUsuario and e.codigo in ('"+Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR+"','"+Constantes.LISTADO.ESTADO_SOLICITUD.EN_PROCESO+"') "
			+ "and pt.codigo =:codigoTipoPersona")
	public Integer contarPendientes(Long idUsuario,String codigoTipoPersona);

	
	@Query("select s from Solicitud s "			
			+ "left join fetch s.representante r "
			+ "left join fetch r.tipoDocumento rtd "
			+ "left join fetch s.persona p "
			+ "left join fetch p.tipoPersona tipPersona "
			+ "left join fetch p.tipoDocumento ptd "
			+ "left join fetch p.pais pp "
			+ "left join fetch s.usuario u "
			+ "left join fetch u.pais up "
			+ "left join fetch u.tipoPersona tip "
			+ "left join fetch s.resultadoAdministrativo ra "
			+ "left join fetch s.resultadoTecnico rt "
			+ "left join fetch s.estado e "
			+ "left join fetch s.estadoRevision er "
			+ "where p.codigoRuc=:codigoRuc "
			+ "and e.codigo ='"+Constantes.LISTADO.ESTADO_SOLICITUD.CONCLUIDO+"'")
	public Solicitud obtenerAprobadaPorRuc(String codigoRuc);
	
	@Query(value="select s from Solicitud s "
			+ "left join fetch s.usuario u "
			+ "where s.numeroExpediente is not null "
			+ "and u.idUsuario = :idUsuario and s.flagActivo = 1 "			
			+ "order by s.fechaPresentacion desc, s.fechaRegistro desc")
	public List<Solicitud> listarSolicitudesPresentadasPorIdUsuario(Long idUsuario);

}
