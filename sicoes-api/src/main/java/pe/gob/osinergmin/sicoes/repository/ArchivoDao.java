package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.util.Constantes;

@Repository
public interface ArchivoDao extends JpaRepository<Archivo, Long> {
	
	@Query("select a from Archivo a "	
		+"left join fetch a.estado e "
		+ "where a.idArchivo=:idArchivo")	
	public Archivo obtener(Long idArchivo);
	
	@Query("select a from Archivo a "	
			+"left join fetch a.estado e "
			+ "where a.codigo=:codigo")	
		public Archivo obtener(String codigo);
	
	@Query(value="select a from Archivo a "
			 +"left join fetch a.estado e ",
			countQuery = "select count(a) from Archivo a ")			
			Page<Archivo> buscar(Pageable pageable);

	@Query("select a from Archivo a "	
			+"left join fetch a.estado e "
			+ "where a.idEstudio=:idEstudio")	
	public List<Archivo> buscarXEstudio(Long idEstudio);
	
	@Query("select a from Archivo a "
			+"left join fetch a.estado e "
			+ "where a.idSolicitud=:idSolicitud and a.estado.codigo=:codigoEstadoArchivo")	
	public List<Archivo> buscarXSolicitud(Long idSolicitud,String codigoEstadoArchivo);


	@Query("select a from Archivo a "
			+"left join fetch a.estado e "
			+"left join fetch a.tipoArchivo ta "			
			+ "where a.idSolicitud=:idSolicitud and a.estado.codigo=:codigoEstadoArchivo and ta.codigo not in ('"+Constantes.LISTADO.TIPO_ARCHIVO.EVIDENCIA+"') and a.version>=:version")	
	public List<Archivo> buscarPresentacionXSolicitud(Long idSolicitud,String codigoEstadoArchivo,Long version);

	
	
	@Query("select a from Archivo a "	
			+"left join fetch a.estado e "
			+ "where a.idDocumento=:idDocumento")	
	public List<Archivo> buscarXDocumento(Long idDocumento);
	
	@Query("select a from Archivo a "	
			+"left join fetch a.estado e "
			+ "where a.idPropuestaTecnica=:idPropuestaTecnica")	
	public List<Archivo> buscarXPropuestaTecnica(Long idPropuestaTecnica);
	
	@Query("select to_char(p.fechaPresentacion,'dd/MM/yyyy HH:mm') , (pro.numeroProceso|| ' - '|| pro.nombreProceso) ,(pi.numeroItem|| ' - '|| pi.descripcionItem),s.tipoPersona.nombre,s.pais.nombre,(s.nombreRazonSocial|| ': '|| s.codigoRuc),'',pi.divisa.nombre,pp.importe,ta.nombre,a.nombreReal,a.descripcion from Archivo a,PropuestaEconomica pe,Propuesta p "	
			+"left join a.estado e "
			+"left join a.tipoArchivo ta "
			+"left join p.propuestaEconomica pp "
			+"left join p.procesoItem pi "
			+"left join pi.proceso pro "
			+"left join p.supervisora s "
			+"left join p.estado ep "
			+"where ep.codigo = '"+Constantes.LISTADO.ESTADO_PRESENTACION.PRESENTADO+"' "
			+"and a.idPropuestaEconomica=pe.idPropuestaEconomica "
			+"and a.idPropuestaEconomica=pp.idPropuestaEconomica "
			+"and pi.procesoItemUuid = :procesoItemUuid ")
	public List<Object[]> buscarXPropuestaEconomica(String procesoItemUuid);
	
	@Query("select to_char(p.fechaPresentacion,'dd/MM/yyyy HH:mm'),(pro.numeroProceso|| ' - '|| pro.nombreProceso), (pi.numeroItem|| ' - '|| pi.descripcionItem),s.tipoPersona.nombre,s.pais.nombre, (s.nombreRazonSocial|| ': '|| s.codigoRuc),pt.consorcio.nombre,pi.divisa.nombre, '',ta.nombre,a.nombreReal,a.descripcion from Archivo a,PropuestaTecnica pt,Propuesta p "	
			+"left join a.estado e "
			+"left join a.tipoArchivo ta "
			+"left join p.propuestaTecnica pp "
			+"left join p.procesoItem pi "
			+"left join pi.proceso pro "
			+"left join p.supervisora s "
			+"left join p.estado ep "
			+"where ep.codigo = '"+Constantes.LISTADO.ESTADO_PRESENTACION.PRESENTADO+"' "
			+"and a.idPropuestaTecnica=pt.idPropuestaTecnica "
			+"and a.idPropuestaTecnica=pp.idPropuestaTecnica "
			+"and pi.procesoItemUuid = :procesoItemUuid ")
	public List<Object[]> buscarXPropuestaTecnica(String procesoItemUuid);
	
	@Query("select a from Archivo a "
			+"left join fetch a.estado e "
			+ "where a.idOtroRequisito=:idOtroRequisito")	
	public List<Archivo> buscarXOtroRequisito(Long idOtroRequisito);
	
	@Query("select a from Archivo a "
			+"left join fetch a.estado e "
			+ "where a.idNotificacionSolicitud=:idNotificacionSolicitud")	
	public List<Archivo> buscarXSolicitudNotificacion(Long idNotificacionSolicitud);
	
	@Query("select a from Archivo a "	
			+"left join fetch a.estado e "
			+ "where a.idSolicitud=:idSolicitud")	
	public List<Archivo> obtenerArchivoContenido(Long idSolicitud);
	//2
	@Query("select a from Archivo a "	
			+"left join fetch a.estado e "
			+ "where a.idSolicitud=:idSolicitud and a.idEstudio in (select idEstudio from Estudio e where e.solicitud.idSolicitud=:idSolicitud and (e.flagSiged is null  or e.flagSiged=0) )")	
	public List<Archivo> obtenerArchivoEstudioPendiente(Long idSolicitud);
	//1
	@Query("select a from Archivo a "	
			+"left join fetch a.estado e "
			+ "where a.idSolicitud=:idSolicitud and a.idDocumento in (select idDocumento from Documento e where e.solicitud.idSolicitud=:idSolicitud and (e.flagSiged is null  or e.flagSiged=0) )")	
	public List<Archivo> obtenerArchivoDocumentoPendiente(Long idSolicitud);
	//3
	@Query("select a from Archivo a "	
			+"left join fetch a.estado e "
			+ "where a.idSolicitud=:idSolicitud and a.idOtroRequisito in (select idOtroRequisito from OtroRequisito e where e.solicitud.idSolicitud=:idSolicitud and (e.flagSiged is null  or e.flagSiged=0) )")	
	public List<Archivo> obtenerArchivoOtroRequisitoPendiente(Long idSolicitud);
	//4
	@Query("select a from Archivo a join a.tipoArchivo t "	
			+"left join fetch a.estado e "
			+ "where a.idSolicitud=:idSolicitud and t.codigo=:tipoArchivo")	
	public List<Archivo> obtenerArchivoOtrosPendiente(Long idSolicitud,String tipoArchivo);
	
	@Query(value="select a from Archivo a "
			+ "left join fetch a.tipoArchivo ta "
			+ "left join fetch a.estado e "
			+ "where ta.codigo=:codigo " 
			+ "and (:idSolicitud is null or a.idSolicitud=:idSolicitud) ",
			countQuery = "select count(a) from Archivo a where a.tipoArchivo.codigo=:codigo "
					+ "and (:idSolicitud is null or a.idSolicitud=:idSolicitud) ")		
	public Page<Archivo> buscarArchivo(String codigo, Long idSolicitud, Pageable pageable);
	
	
	@Query(value="select a from Archivo a "
			+ "left join fetch a.tipoArchivo ta "
			+ "left join fetch a.estado e "
			+ "where ta.codigo=:codigo " 
			+ "and (:idPropuestaEconomica is null or a.idPropuestaEconomica=:idPropuestaEconomica) "
			+ "and (:idPropuestaTecnica is null or a.idPropuestaTecnica=:idPropuestaTecnica)",
			countQuery = "select count(a) from Archivo a where a.tipoArchivo.codigo=:codigo "
					+ "and (:idPropuestaEconomica is null or a.idPropuestaEconomica=:idPropuestaEconomica) "
					+ "and (:idPropuestaTecnica is null or a.idPropuestaTecnica=:idPropuestaTecnica) ")		
	public Page<Archivo> buscarArchivoPropuesta(String codigo,Long idPropuestaEconomica,Long idPropuestaTecnica, Pageable pageable);
	
	@Query(value="select a from Archivo a "
			+ "left join fetch a.tipoArchivo ta "
			+ "left join fetch a.estado e "
			+ " where ta.codigo=:codigo and a.idSolicitud=:idSolicitud ")
	public Archivo obtenerTipoArchivo(Long idSolicitud, String codigo);

	@Modifying
	@Query(value="delete from Archivo a where a.idEstudio=:idEstudio")			
	public void eliminarIdEstudio(Long idEstudio);

	@Modifying
	@Query(value="delete from Archivo a where a.idOtroRequisito=:idOtroRequisito")
	public void eliminarIdOtroRequisito(Long idOtroRequisito);
	
	@Modifying
	@Query(value="delete from Archivo a where a.idDocumento=:idDocumento")
	public void eliminarIdDocumento(Long idDocumento);
	
	@Query(value="select a from Archivo a "
			+ "left join fetch a.tipoArchivo ta "
			+ "left join fetch a.estado e "
			+ "where ta.codigo=:codigo and a.idSolicitud=:idSolicitud")
	public List<Archivo> buscarArchivo(String codigo, Long idSolicitud);

	@Modifying
	@Query(value="delete from Archivo a where a.idSolicitud=:idSolicitud and idDocumento is null and idOtroRequisito is null and idEstudio is null and idNotificacionSolicitud is null")
	public void  eliminarXIDSolicitud(Long idSolicitud);

	@Query(value="select a from Archivo a "
			+ "left join fetch a.tipoArchivo ta "
			+ "left join fetch a.estado e "
			+ "where a.idNotificacion=:idNotificacion")
	public List<Archivo> obtenerArchivoIdNotificacion(Long idNotificacion);
	
	@Query("select a from Archivo a "	
			+"left join fetch a.estado e "
			+ "where a.idPropuestaTecnica=:idPropuestaTecnica")	
	public List<Archivo> listarXTecnica(Long idPropuestaTecnica);
	
	@Query("select a from Archivo a "	
			+"left join fetch a.estado e "
			+ "where a.idPropuestaEconomica=:idPropuestaEconomica")	
	public List<Archivo> listarXEconomica(Long idPropuestaEconomica);

	@Query("select a from Archivo a "	
			+"left join fetch a.estado e "
			+ "where a.idPropuestaTecnica=:idPropuestaTecnica ")	
	public List<Archivo> obtenerArchivoPropuestaTecnica(Long idPropuestaTecnica);
	
	@Query("select a from Archivo a "	
			+"left join fetch a.estado e "
			+ "where a.idPropuestaEconomica =:idPropuestaEconomica ")	
	public List<Archivo> obtenerArchivoPropuestaEconomica(Long idPropuestaEconomica);

	@Query("select a from Archivo a "	
			+"left join fetch a.estado e "
			+ "where a.idPropuesta in(select p.idPropuesta from Propuesta p where p.procesoItem.idProcesoItem =:idProcesoItem) ")	
	//public List<Archivo> obtenerArchivoPropuesta(Long idPropuestaEconomica,Long idPropuestaTecnica);
	public List<Archivo> obtenerArchivoItem(Long idProcesoItem);

	@Query(value="select a from Archivo a "
			+ "left join fetch a.tipoArchivo ta "
			+ "left join fetch a.estado e "
			+ " where ta.codigo=:codigoFormato and a.idSolicitud=:idSolicitud and a.idAsignacion=:idAsignacion")
	public Archivo obtenerTipoArchivo(Long idSolicitud, String codigoFormato, Long idAsignacion);
	
	@Query(value="select a from Archivo a "
			+ "left join fetch a.tipoArchivo ta "
			+ "left join fetch a.estado e "
			+ " where a.idAsignacion is not null and a.flagSiged<=5 ")
	public List<Archivo> obtenerDocumentoTecnicosPendientes();
	
	@Modifying
	@Query(value="update Archivo set flagSiged=flagSiged+:estado where idArchivo=:idArchivo  ")
	public void actualizarEstado(Long idArchivo,Long estado);

}
