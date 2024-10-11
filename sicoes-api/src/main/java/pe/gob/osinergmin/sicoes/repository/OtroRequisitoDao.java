package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.OtroRequisito;
import pe.gob.osinergmin.sicoes.util.Constantes;

@Repository
public interface OtroRequisitoDao extends JpaRepository<OtroRequisito, Long> {
	
	@Query("select o from OtroRequisito o "	
			+ "left join fetch o.tipo t "
			+ "left join fetch o.tipoRequisito tr "
			+ "left join fetch o.sector s "
			+ "left join fetch o.actividad a "
			+ "left join fetch o.subsector ss "
			+ "left join fetch o.subCategoria sc "
			+ "left join fetch o.unidad u "
			+ "left join fetch o.perfil p "
			+ "left join fetch o.evaluacion e "
			+ "left join fetch o.resultado r "
			+ "left join fetch o.solicitud so "
			+ "left join fetch o.finalizado fn "
			+ "left join fetch o.usuario us "
			+ "where o.idOtroRequisito=:idOtroRequisito")
	public OtroRequisito obtener(Long idOtroRequisito);
	
	@Query(value="select o from OtroRequisito o "	
			,
			countQuery = "select count(o) from OtroRequisito o "
					)			
			Page<OtroRequisito> buscar(Pageable pageable);
	
	@Query(value="select o from OtroRequisito o "	
			+ "left join fetch o.tipo t "
			+ "left join fetch o.subsector ss "
			+ "left join fetch o.tipoRequisito tr "
			+ "left join fetch o.sector s "
			+ "left join fetch o.subCategoria sc "
			+ "left join fetch o.unidad u "
			+ "left join fetch o.perfil p "
			+ "left join fetch o.evaluacion e "
			+ "left join fetch o.resultado r "
			+ "left join fetch o.solicitud so "
			+ "left join fetch o.finalizado fn "
			+ "left join fetch o.usuario us "
			+ "where so.idSolicitud=:idSolicitud and t.codigo=:tipoRequisito order by tr.orden asc, o.fecCreacion desc ",
	countQuery = "select count(o) from OtroRequisito o "	
			+ "left join o.tipo t "
			+ "left join o.subsector ss "
			+ "left join o.tipoRequisito tr "
			+ "left join o.sector s "
			+ "left join o.subCategoria sc "
			+ "left join o.unidad u "
			+ "left join o.perfil p "
			+ "left join o.evaluacion e "
			+ "left join o.solicitud so "
			+ "where so.idSolicitud=:idSolicitud and t.codigo=:tipoRequisito ")			
	public Page<OtroRequisito> listarOtroRequisito(Long idSolicitud,String tipoRequisito,Pageable pageable);
	
	@Query("select o from OtroRequisito o "	
			+ "left join fetch o.tipo t "
			+ "left join fetch o.tipoRequisito tr "
			+ "left join fetch o.sector s "
			+ "left join fetch o.subsector ss "
			+ "left join fetch o.actividad ac "
			+ "left join fetch o.subCategoria sc "
			+ "left join fetch o.unidad u "
			+ "left join fetch o.perfil p "
			+ "left join fetch o.evaluacion e "
			+ "left join fetch o.resultado r "
			+ "left join fetch o.solicitud so "
			+ "left join fetch o.finalizado fn "
			+ "left join fetch o.usuario us "
			+ "where so.idSolicitud=:idSolicitud and t.codigo=:tipoRequisito order by tr.orden asc, o.fecCreacion asc ")			
	public List<OtroRequisito> listarOtroRequisito(String tipoRequisito,Long idSolicitud);
	
	@Query("select o from OtroRequisito o "	
			+ "left join fetch o.tipo t "
			+ "left join fetch o.tipoRequisito tr "
			+ "left join fetch o.sector s "
			+ "left join fetch o.subsector ss "
			+ "left join fetch o.actividad ac "
			+ "left join fetch o.subCategoria sc "
			+ "left join fetch o.unidad u "
			+ "left join fetch o.perfil p "
			+ "left join fetch o.evaluacion e "
			+ "left join fetch o.resultado r "
			+ "left join fetch o.solicitud so "
			+ "left join fetch o.finalizado fn "
			+ "left join fetch o.usuario us "
			+ "where so.idSolicitud=:idSolicitud and s.idListadoDetalle=:idListadoDetalle order by tr.orden asc, o.fecCreacion asc ")			
	public List<OtroRequisito> listarOtroRequisitoXSector(Long idListadoDetalle, Long idSolicitud);
	
	@Query("select o from OtroRequisito o "	
			+ "left join fetch o.tipo t "
			+ "left join fetch o.tipoRequisito tr "
			+ "left join fetch o.sector s "
			+ "left join fetch o.subsector ss "
			+ "left join fetch o.actividad ac "
			+ "left join fetch o.subCategoria sc "
			+ "left join fetch o.unidad u "
			+ "left join fetch o.perfil p "
			+ "left join fetch o.evaluacion e "
			+ "left join fetch o.resultado r "
			+ "left join fetch o.solicitud so "
			+ "left join fetch o.finalizado fn "
			+ "left join fetch o.usuario us "
			+ "where so.idSolicitud=:idSolicitud and t.codigo=:tipoRequisito and o.idOtroRequisitoPadre is not null")		
	public List<OtroRequisito> listarOtroRequisitoObservado(String tipoRequisito,Long idSolicitud);
	
	
	@Query("select o from OtroRequisito o "	
			+ "left join fetch o.tipo t "
			+ "left join fetch o.tipoRequisito tr "
			+ "left join fetch o.sector s "
			+ "left join fetch o.subsector ss "
			+ "left join o.subCategoria sc "
			+ "left join o.unidad u "
			+ "left join fetch o.perfil p "
			+ "left join fetch o.evaluacion e "
			+ "left join fetch o.resultado r "
			+ "left join fetch o.solicitud so "
			+ "left join fetch o.finalizado fn "
			+ "left join fetch o.usuario us "
			+ "where so.idSolicitud=:idSolicitud and t.codigo=:tipoRequisito and (o.flagSiged is null  or o.flagSiged=0)  order by tr.orden asc ")			
	public List<OtroRequisito> listarOtroRequisitoEstado(String tipoRequisito,Long idSolicitud);
	
	
	
	@Query("select o from OtroRequisito o "	
			+ "left join fetch o.tipo t "
			+ "left join fetch o.tipoRequisito tr "
			+ "left join fetch o.sector s "
			+ "left join fetch o.subsector ss "
			+ "left join o.subCategoria sc "
			+ "left join o.unidad u "
			+ "left join fetch o.perfil p "
			+ "left join fetch o.evaluacion e "
			+ "left join fetch o.resultado r "
			+ "left join fetch o.solicitud so "
			+ "left join fetch o.finalizado fn "
			+ "left join fetch o.usuario us "
			+ "where so.idSolicitud=:idSolicitud  order by tr.orden asc ")			
	public List<OtroRequisito> listarOtroRequisito(Long idSolicitud);

	@Query("select count(o) from OtroRequisito o "	
			+ "left join  o.tipo t "
			+ "left join  o.tipoRequisito tr "
			+ "left join  o.sector s "
			+ "left join  o.subsector ss "
			+ "left join o.subCategoria sc "
			+ "left join o.unidad u "
			+ "left join  o.perfil p "
			+ "left join  o.evaluacion e "
			+ "left join  o.solicitud so "
			+ "left join  o.finalizado fn "
			+ "left join  o.usuario us "
			+ "where so.idSolicitud=:idSolicitud "
			+ "and s.idListadoDetalle=:idSector "
			+ "and ss.idListadoDetalle=:idSubSector "
			+ "and (:idOtroRequisito is null or o.idOtroRequisito<>:idOtroRequisito)")
	public Long exiteRequisito(Long idSolicitud, Long idSector, Long idSubSector,Long idOtroRequisito);

	@Query("select count(o) from OtroRequisito o "	
			+ "left join  o.tipo t "
			+ "left join  o.tipoRequisito tr "
			+ "left join  o.sector s "
			+ "left join  o.subsector ss "
			+ "left join o.subCategoria sc "
			+ "left join o.unidad u "
			+ "left join  o.perfil p "
			+ "left join  o.evaluacion e "
			+ "left join  o.solicitud so "
			+ "left join  o.finalizado fn "
			+ "left join  o.usuario us "
			+ "where so.idSolicitud=:idSolicitud "
			+ "and p.idListadoDetalle=:idPerfil "
			+ "and (:idOtroRequisito is null or o.idOtroRequisito<>:idOtroRequisito)")
	public Long exiteRequisito(Long idSolicitud, Long idPerfil, Long idOtroRequisito);

	
	@Query("select o from OtroRequisito o "	
			+ "left join fetch o.tipo t "
			+ "left join fetch o.tipoRequisito tr "
			+ "left join fetch o.sector s "
			+ "left join fetch o.subsector ss "
			+ "left join o.subCategoria sc "
			+ "left join o.unidad u "
			+ "left join fetch o.perfil p "
			+ "left join fetch o.evaluacion e "
			+ "left join fetch o.resultado r "
			+ "left join fetch o.solicitud so "
			+ "left join fetch o.finalizado fn "
			+ "left join fetch o.usuario us "
			+ "where so.idSolicitud=:idSolicitud "
			+ "and t.codigo='"+Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL+"' "
			+ "and o.perfil in "
			+ "(select c.perfil from ConfiguracionBandeja c "
			+ "left join c.sector s "
			+ "left join c.subsector ss "
			+ "left join c.actividad a "
			+ "left join c.unidad u "
			+ "left join c.subCategoria sc "
			+ "left join c.perfil p "
			+ "left join c.usuario u "
			+ "where u.idUsuario=:idUsuario )")			
	public List<OtroRequisito> listarOtroRequisitoXSolicitud(Long idSolicitud,Long idUsuario);
	
	@Query("select o from OtroRequisito o "	
			+ "left join fetch o.tipo t "
			+ "left join fetch o.tipoRequisito tr "
			+ "left join fetch o.sector s "
			+ "left join fetch o.subsector ss "
			+ "left join o.subCategoria sc "
			+ "left join o.unidad u "
			+ "left join fetch o.perfil p "
			+ "left join fetch o.evaluacion e "
			+ "left join fetch o.resultado r "
			+ "left join fetch o.solicitud so "
			+ "left join fetch o.finalizado fn "
			+ "left join fetch o.usuario us "
			+ "where so.idSolicitud=:idSolicitud "
			+ "and t.codigo='"+Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL+"' "
			+ "and o.subsector in "
			+ "(select c.subsector from ConfiguracionBandeja c "
			+ "left join c.sector s "
			+ "left join c.subsector ss "
			+ "left join c.actividad a "
			+ "left join c.unidad u "
			+ "left join c.subCategoria sc "
			+ "left join c.perfil p "
			+ "left join c.usuario u "
			+ "where c.perfil is null "
			+ "and u.idUsuario=:idUsuario )")			
	public List<OtroRequisito> listarOtroRequisitoXSolicitudPJ(Long idSolicitud,Long idUsuario);
	
	
	//afc
	@Query("select o from OtroRequisito o "	
			+ "left join fetch o.tipo t "
			+ "left join fetch o.tipoRequisito tr "
			+ "left join fetch o.sector s "
			+ "left join fetch o.subsector ss "
			+ "left join o.subCategoria sc "
			+ "left join o.unidad u "
			+ "left join fetch o.perfil p "
			+ "left join fetch o.evaluacion e "
			+ "left join fetch o.resultado r "
			+ "left join fetch o.solicitud so "
			+ "left join fetch so.division d "
			+ "left join fetch o.finalizado fn "
			+ "left join fetch o.usuario us "
			+ "where so.idSolicitud=:idSolicitud "
			+ "and t.codigo='"+Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL+"' "
//			+ "and o.subsector in "
//			+ "(select c.subsector from ConfiguracionBandeja c "
//			+ "left join c.sector s "
//			+ "left join c.subsector ss "
//			+ "left join c.actividad a "
//			+ "left join c.unidad u "
//			+ "left join c.subCategoria sc "
//			+ "left join c.perfil p "
//			+ "left join c.usuario u "
//			+ "where c.perfil is null "
//			+ "and u.idUsuario=:idUsuario )")	
			)
	public List<OtroRequisito> listarOtroRequisitoXSolicitudAdmin(Long idSolicitud);
	
	//afc
	@Query("select o from OtroRequisito o "	
			+ "left join fetch o.tipo t "
			+ "left join fetch o.tipoRequisito tr "
			+ "left join fetch o.sector s "
			+ "left join fetch o.subsector ss "
			+ "left join o.subCategoria sc "
			+ "left join o.unidad u "
			+ "left join fetch o.perfil p "
			+ "left join fetch o.evaluacion e "
			+ "left join fetch o.resultado r "
			+ "left join fetch o.solicitud so "
			+ "left join fetch so.division d "
			+ "left join fetch o.finalizado fn "
			+ "left join fetch o.usuario us "
			+ "where so.idSolicitud=:idSolicitud "
			+ "and t.codigo='"+Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.OTRO_REQUISITO+"' "
//			+ "and o.subsector in "
//			+ "(select c.subsector from ConfiguracionBandeja c "
//			+ "left join c.sector s "
//			+ "left join c.subsector ss "
//			+ "left join c.actividad a "
//			+ "left join c.unidad u "
//			+ "left join c.subCategoria sc "
//			+ "left join c.perfil p "
//			+ "left join c.usuario u "
//			+ "where c.perfil is null "
//			+ "and u.idUsuario=:idUsuario )")	
			)
	public List<OtroRequisito> listarOtroRequisitoXSolicitudAdminUser(Long idSolicitud);
	//afc
	@Query("select o from OtroRequisito o "	
			+ "left join fetch o.tipo t "
			+ "left join fetch o.tipoRequisito tr "
			+ "left join fetch o.sector s "
			+ "left join fetch o.subsector ss "
			+ "left join o.subCategoria sc "
			+ "left join o.unidad u "
			+ "left join fetch o.perfil p "
			+ "left join fetch o.evaluacion e "
			+ "left join fetch o.resultado r "
			+ "left join fetch o.solicitud so "
			+ "left join fetch o.finalizado fn "
			+ "left join fetch o.usuario us "
			+ "where so.idSolicitud=:idSolicitud "
			+ "and t.codigo= '"+Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL +"' "
			+ "and e.codigo = '"+Constantes.LISTADO.RESULTADO_EVALUACION_ADM.OBSERVADO+"' "
			+ "and o.perfil in "
			+ "(select c.perfil from ConfiguracionBandeja c "
			+ "left join c.sector s "
			+ "left join c.subsector ss "
			+ "left join c.actividad a "
			+ "left join c.unidad u "
			+ "left join c.subCategoria sc "
			+ "left join c.perfil p "
			+ "left join c.usuario u "
			+ "where u.idUsuario=:idUsuario )")		
	public List<OtroRequisito> listarOtroRequisitoXSolicitudObservados(Long idSolicitud,Long idUsuario);
	
	@Query("select o from OtroRequisito o "	
			+ "left join fetch o.tipo t "
			+ "left join fetch o.tipoRequisito tr "
			+ "left join fetch o.sector s "
			+ "left join fetch o.subsector ss "
			+ "left join o.subCategoria sc "
			+ "left join o.unidad u "
			+ "left join fetch o.perfil p "
			+ "left join fetch o.evaluacion e "
			+ "left join fetch o.resultado r "
			+ "left join fetch o.solicitud so "
			+ "left join fetch o.finalizado fn "
			+ "left join fetch o.usuario us "
			+ "where so.idSolicitud=:idSolicitud "
			+ "and t.codigo='"+Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL +"' "
			+ "and e.codigo = '"+Constantes.LISTADO.RESULTADO_EVALUACION_ADM.OBSERVADO+"' "
			+ "and o.subsector in "
			+ "(select c.subsector from ConfiguracionBandeja c "
			+ "left join c.sector s "
			+ "left join c.subsector ss "
			+ "left join c.actividad a "
			+ "left join c.unidad u "
			+ "left join c.subCategoria sc "
			+ "left join c.perfil p "
			+ "left join c.usuario u "
			+ "where u.idUsuario=:idUsuario )")		
	public List<OtroRequisito> listarOtroRequisitoXSolicitudObservadosPJ(Long idSolicitud,Long idUsuario);

	
	@Query("select o from OtroRequisito o "	
			+ "left join fetch o.tipo t "
			+ "left join fetch o.tipoRequisito tr "
			+ "left join fetch o.sector s "
			+ "left join fetch o.subsector ss "
			+ "left join o.subCategoria sc "
			+ "left join o.unidad u "
			+ "left join fetch o.perfil p "
			+ "left join fetch o.evaluacion e "
			+ "left join fetch o.resultado r "
			+ "left join fetch o.solicitud so "
			+ "left join fetch o.finalizado fn "
			+ "left join fetch o.usuario us "
			+ "where t.codigo = '"+Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL+"' "
			+ "and so.idSolicitud=:idSolicitud  order by tr.orden asc ")			
	public List<OtroRequisito> listarOtroRequisitosPerfil(Long idSolicitud);

	@Query("select o from OtroRequisito o "	
			+ "left join fetch o.tipo t "
			+ "left join fetch o.tipoRequisito tr "
			+ "left join fetch o.sector s "
			+ "left join fetch o.subsector ss "
			+ "left join fetch o.actividad ac "
			+ "left join fetch o.subCategoria sc "
			+ "left join fetch o.unidad u "
			+ "left join fetch o.perfil p "
			+ "left join fetch o.evaluacion e "
			+ "left join fetch o.resultado r "
			+ "left join fetch o.solicitud so "
			+ "left join fetch o.finalizado fn "
			+ "left join fetch o.usuario us "
			+ "where so.idSolicitud=:idSolicitud and s.codigo=:sectorEnergetico order by tr.orden asc, o.fecCreacion asc ")
	public List<OtroRequisito> listarOtroRequisitoXSector(String sectorEnergetico, Long idSolicitud);
	
	@Query("select o from OtroRequisito o "	
			+ "left join fetch o.tipo t "
			+ "left join fetch o.tipoRequisito tr "
			+ "left join fetch o.sector s "
			+ "left join fetch o.subsector ss "
			+ "left join o.subCategoria sc "
			+ "left join o.unidad u "
			+ "left join fetch o.perfil p "
			+ "left join fetch o.evaluacion e "
			+ "left join fetch o.resultado r "
			+ "left join fetch o.solicitud so "
			+ "left join fetch o.finalizado fn "
			+ "left join fetch o.usuario us "
			+ "where t.codigo = '" + Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL + "' "
			+ "and (fn.codigo = '" + Constantes.LISTADO.SI_NO.NO + "' or fn.codigo is null)")		
	public List<OtroRequisito> listarOtroRequisitoPerfilesPendientesDeEvaluacion();
	
	/* --- 04-01-2024 --- INI ---*/
	@Query("select o from OtroRequisito o "	
		+ "left join fetch o.tipo t "
		+ "left join fetch o.tipoRequisito tr "
		+ "left join fetch o.sector s "
		+ "left join fetch o.subsector ss "
		+ "left join o.subCategoria sc "
		+ "left join o.unidad u "
		+ "left join fetch o.perfil p "
		+ "left join fetch o.evaluacion e "
		+ "left join fetch o.resultado r "
		+ "left join fetch o.solicitud so "
		+ "left join fetch o.finalizado fn "
		+ "left join fetch o.usuario us "
		+ "where t.codigo = '"+Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL+"' "
		+ "and e.codigo = '"+Constantes.LISTADO.RESULTADO_EVALUACION_ADM.OBSERVADO+"' "
		+ "and so.idSolicitud=:idSolicitud  order by tr.orden asc ")			
	public List<OtroRequisito> listarOtroRequisitosPerfilObservado(Long idSolicitud);
	
	
	@Query("select o from OtroRequisito o "	
			+ "left join fetch o.tipo t "
			+ "left join fetch o.tipoRequisito tr "
			+ "left join fetch o.sector s "
			+ "left join fetch o.subsector ss "
			+ "left join o.subCategoria sc "
			+ "left join o.unidad u "
			+ "left join fetch o.perfil p "
			+ "left join fetch o.evaluacion e "
			+ "left join fetch o.resultado r "
			+ "left join fetch o.solicitud so "
			+ "left join fetch o.finalizado fn "
			+ "left join fetch o.usuario us "
			+ "where t.codigo = '"+Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL+"' "
			+ "and e.codigo <> '"+Constantes.LISTADO.RESULTADO_EVALUACION_ADM.NO_CALIFICA+"' "
			+ "and so.idSolicitud=:idSolicitud  order by tr.orden asc ")			
	public List<OtroRequisito> listarOtroRequisitosPerfilCalifica(Long idSolicitud);
	/* --- 04-01-2024 --- FIN ---*/
	
	@Query("select o from OtroRequisito o "	
			+ "left join fetch o.tipo t "
			+ "left join fetch o.tipoRequisito tr "
			+ "left join fetch o.sector s "
			+ "left join fetch o.subsector ss "
			+ "left join o.subCategoria sc "
			+ "left join o.unidad u "
			+ "left join fetch o.perfil p "
			+ "left join fetch o.evaluacion e "
			+ "left join fetch o.resultado r "
			+ "left join fetch o.solicitud so "
			+ "left join fetch o.finalizado fn "
			+ "left join fetch o.usuario us "
			+ "where t.codigo = '" + Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL + "' "
			+ "and (fn.codigo = '" + Constantes.LISTADO.SI_NO.NO + "' or fn.codigo is null) "
			+ "and o.fechaAsignacion is not null")	
	public List<OtroRequisito> listarPerfilesPendientesDeEvaluacion();
	
	@Query("select o from OtroRequisito o "	
			+ "left join fetch o.tipo t "
			+ "left join fetch o.finalizado fn "
			+ "left join fetch o.usuario us "
			+ "where t.codigo = '" + Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL + "' "
			+ "and (fn.codigo = '" + Constantes.LISTADO.SI_NO.NO + "' or fn.codigo is null) "
			+ "and o.fechaAsignacion is not null "
			+ "and us.idUsuario=:idUsuario")
	public List<OtroRequisito> listarPerfilesPendientesDeEvaluacionPorIdUsuario(Long idUsuario);

}
