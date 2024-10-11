package pe.gob.osinergmin.sicoes.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.SupervisoraPerfil;
import pe.gob.osinergmin.sicoes.util.Constantes;

@Repository
public interface SupervisoraPerfilDao extends JpaRepository<SupervisoraPerfil, Long> {
		
		@Query("select sp from SupervisoraPerfil sp "
				+ "left join fetch sp.sector se "
				+ "left join fetch sp.actividad a "
				+ "left join fetch sp.subsector ss "
				+ "left join fetch sp.subCategoria sc "
				+ "left join fetch sp.unidad u "
				+ "left join fetch sp.perfil pe "
				+ "left join fetch sp.supervisora s "
				+ "left join fetch s.tipoDocumento td "
				+ "left join fetch s.pais p "
				+ "left join fetch s.estado spe "
				+ "where s.idSupervisora=:idSupervisora")
		public SupervisoraPerfil obtener(Long idSupervisora);
		
		@Query(value="select sp from SupervisoraPerfil sp "
				+ "left join fetch sp.sector se "
				+ "left join fetch sp.actividad a "
				+ "left join fetch sp.subsector ss "
				+ "left join fetch sp.subCategoria sc "
				+ "left join fetch sp.unidad u "
				+ "left join fetch sp.perfil pe "
				+ "left join fetch sp.supervisora s "
				+ "left join fetch s.tipoDocumento td "
				+ "left join fetch s.tipoPersona tp "
				+ "left join fetch s.pais p "
				+ "left join fetch s.estado spe "
				+ "where "
//				+ "(td.codigo=:codigoTipoDocumento) "
				+ "(tp.codigo in (:codigoTipoPersona)) "
				+ "and (:ruc is null or s.codigoRuc=:ruc) "
				+ "and (concat(s.nombreRazonSocial,' ',s.apellidoPaterno,' ',s.apellidoMaterno,' ',s.nombres,' ') like :nombres) "
				+ "and (pe.nombre is null or pe.nombre like :perfil) "
				+ "and (:fechaInicio is null or sp.fechaIngreso>=:fechaInicio) "
				+ "and (:fechaFin is null or sp.fechaIngreso<=:fechaFin) "
				+ "order by s.fechaIngreso desc ",
		countQuery = "select count(sp) from SupervisoraPerfil sp "
				+ "left join  sp.supervisora s "
				+ "left join  sp.sector se "
				+ "left join  sp.actividad a "
				+ "left join  sp.subsector ss "
				+ "left join  sp.subCategoria sc "
				+ "left join  sp.unidad u "
				+ "left join  sp.perfil pe "
				+ "left join  s.tipoDocumento td "
				+ "left join  s.tipoPersona tp "
				+ "left join  s.pais p "
				+ "where "
//				+ "(td.codigo=:codigoTipoDocumento) "
				+ " (tp.codigo in (:codigoTipoPersona)) "
				+ "and (:ruc is null or s.codigoRuc=:ruc) "
				+ "and (concat(s.nombreRazonSocial,' ',s.apellidoPaterno,' ',s.apellidoMaterno,' ',s.nombres,' ') like :nombres) "
				+ "and (pe.nombre is null or pe.nombre like :perfil) "
				+ "and (:fechaInicio is null or sp.fechaIngreso>=:fechaInicio) "
				+ "and (:fechaFin is null or sp.fechaIngreso<=:fechaFin) "
				+ "order by s.fechaIngreso desc ")			
		Page<SupervisoraPerfil> buscar(String[] codigoTipoPersona,String ruc,
				String nombres,String perfil,
				Date fechaInicio,
				Date fechaFin,Pageable pageable);

		@Query("select sp from SupervisoraPerfil sp "
				+ "left join fetch sp.sector se "
				+ "left join fetch sp.actividad a "
				+ "left join fetch sp.subsector ss "
				+ "left join fetch sp.subCategoria sc "
				+ "left join fetch sp.unidad u "
				+ "left join fetch sp.perfil pe "
				+ "left join fetch sp.supervisora s "
				+ "left join fetch s.tipoDocumento td "
				+ "left join fetch s.pais p "
				+ "left join fetch s.estado spe "
				+ "where s.idSupervisora=:idSupervisora")
		public List<SupervisoraPerfil> buscar(Long idSupervisora);

		
		@Query("select tp.nombre,p.nombre,td.nombre,s.numeroDocumento,COALESCE(s.nombreRazonSocial,concat(s.apellidoPaterno,' ',s.apellidoMaterno,' ',s.nombres,' ')),es.nombre,se.nombre,ss.nombre,a.nombre,u.nombre,sc.nombre,pe.nombre,sp.fechaIngreso,sp.numeroExpediente  from SupervisoraPerfil sp "
				+ "left join sp.sector se "
				+ "left join sp.actividad a "
				+ "left join sp.subsector ss "
				+ "left join sp.subCategoria sc "
				+ "left join sp.unidad u "
				+ "left join sp.perfil pe "
				+ "left join sp.supervisora s "
				+ "left join s.tipoDocumento td "
				+ "left join s.pais p "
				+ "left join s.tipoPersona tp "
				+ "left join s.estado es "	
				+ "where "
				+ "(:idTipoEmpresa is null or tp.idListadoDetalle=:idTipoEmpresa)"
				+ "and (sp.numeroExpediente like :numeroExpediente) "
				+ "and (concat(s.nombreRazonSocial,' ',s.apellidoPaterno,' ',s.apellidoMaterno,' ',s.nombres,' ') like :nombreRazonSocial) "
				+ "and (:numeroDocumento is null or s.numeroDocumento=:numeroDocumento)")
		public List<Object[]> listarSupervisoras(String numeroExpediente, Long idTipoEmpresa, String nombreRazonSocial,
				String numeroDocumento);

		@Query("select sp from SupervisoraPerfil sp "
				+ "left join fetch sp.sector se "
				+ "left join fetch sp.actividad a "
				+ "left join fetch sp.subsector ss "
				+ "left join fetch sp.subCategoria sc "
				+ "left join fetch sp.unidad u "
				+ "left join fetch sp.perfil pe "
				+ "left join fetch sp.supervisora s "
				+ "left join fetch s.tipoDocumento td "
				+ "left join fetch s.pais p "
				+ "left join fetch s.estado e "
				+ "where s.idSupervisora=:idSupervisora "
				+ "and se.idListadoDetalle=:idSector "
//				+ "and ss.idListadoDetalle=:idSubsector "
				+ "and e.codigo='"+Constantes.LISTADO.ESTADO_SUPERVISORA.VIGENTE+"'")
		public List<SupervisoraPerfil> buscar(Long idSupervisora, Long idSector) ;
		
		@Query("select sp from SupervisoraPerfil sp "
				+ "left join fetch sp.sector se "
				+ "left join fetch sp.actividad a "
				+ "left join fetch sp.subsector ss "
				+ "left join fetch sp.subCategoria sc "
				+ "left join fetch sp.unidad u "
				+ "left join fetch sp.perfil pe "
				+ "left join fetch sp.supervisora s "
				+ "left join fetch s.tipoDocumento td "
				+ "left join fetch s.pais p "
				+ "left join fetch s.estado e "
				+ "where s.idSupervisora=:idSupervisora "
				+ "and se.idListadoDetalle=:idSector "
				+ "and ss.idListadoDetalle=:idSubsector "
				+ "and rownum <=1 "
				+ "order by sp.idSupervisoraPerfil desc ")
		public SupervisoraPerfil obteneUltimoPerfil(Long idSupervisora, Long idSector,Long idSubsector) ;

		@Query("select sp from SupervisoraPerfil sp "
				+ "left join fetch sp.sector se "
				+ "left join fetch sp.actividad a "
				+ "left join fetch sp.subsector ss "
				+ "left join fetch sp.subCategoria sc "
				+ "left join fetch sp.unidad u "
				+ "left join fetch sp.perfil pe "
				+ "left join fetch sp.supervisora s "
				+ "left join fetch sp.estado spe "
				+ "left join fetch s.tipoDocumento td "
				+ "left join fetch s.pais p "
				+ "where s.idSupervisora=:idSupervisora "
				+ "and ss.idListadoDetalle=:idSubsector "
				+ "and spe.codigo='"+Constantes.LISTADO.ESTADO_SUP_PERFIL.BLOQUEADO +"'")
		public List<SupervisoraPerfil> buscarBloqueados(Long idSupervisora,Long idSubsector);

		
		@Query("select distinct su from Supervisora su "
				+ "where (:codigoRuc is null or su.codigoRuc=:codigoRuc) "
				+ "and su in "
				+ "(select s from SupervisoraPerfil sp, PropuestaProfesional pp "
				+ "left join sp.sector se "
				+ "left join sp.actividad a "
				+ "left join sp.subsector ss "
				+ "left join sp.subCategoria sc "
				+ "left join sp.unidad u "
				+ "left join sp.perfil pe "
				+ "left join sp.supervisora s "
				+ "left join sp.estado spe "
				+ "left join s.tipoDocumento td "
				+ "left join s.pais p "
				+ "left join pp.supervisora pps "
				+ "left join pp.propuesta ppp "
				+ "left join ppp.procesoItem ppi "
				+ "left join ppi.proceso pro "
				+ "where s.tipoPersona.codigo ='"+Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO+"' "
				+ "and (:codigoRuc is null or s.codigoRuc=:codigoRuc) "
				+ "and s.idSupervisora = pps.idSupervisora "
				+ "and (:item is null or ppi.descripcionItem like:item) "
				+ "and (:proceso is null or pro.nombreProceso like:proceso) "
				+ "and (:idSector is null or se.idListadoDetalle=:idSector) "
				+ "and (:idSubsector is null or ss.idListadoDetalle=:idSubsector)) ")
		public List<Supervisora> listarProfesionales(String codigoRuc, Long idSector, Long idSubsector,String item,String proceso);
		
		@Query("select distinct su from Supervisora su "
				+ "where (:codigoRuc is null or su.codigoRuc=:codigoRuc) "
				+ "and su in "
				+ "(select s from SupervisoraPerfil sp "
				+ "left join sp.sector se "
				+ "left join sp.actividad a "
				+ "left join sp.subsector ss "
				+ "left join sp.subCategoria sc "
				+ "left join sp.unidad u "
				+ "left join sp.perfil pe "
				+ "left join sp.supervisora s "
				+ "left join sp.estado spe "
				+ "left join s.tipoDocumento td "
				+ "left join s.pais p "
				+ "where s.tipoPersona.codigo ='"+Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO+"' "
				+ "and (:idSector is null or se.idListadoDetalle=:idSector) "
				+ "and (:idSubsector is null or ss.idListadoDetalle=:idSubsector)) ")
		public List<Supervisora> listarProfesionales(String codigoRuc, Long idSector, Long idSubsector);
		
		
//		@Query(value="select distinct sp.supervisora from SupervisoraPerfil sp"
//				+ "left join fetch sp.sector se "
//				+ "left join fetch sp.actividad a "
//				+ "left join fetch sp.subsector ss "
//				+ "left join fetch sp.subCategoria sc "
//				+ "left join fetch sp.unidad u "
//				+ "left join fetch sp.perfil pe "
//				+ "left join fetch sp.supervisora s "
//				+ "left join fetch sp.estado spe "
//				+ "left join fetch s.tipoDocumento td "
//				+ "left join fetch s.tipoPersona tp "
//				+ "left join fetch s.pais p "
//				+ "where s.tipoPersona.codigo ='"+Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO+"' "
//				+ "and (:codigoRuc is null or s.codigoRuc=:codigoRuc) "
//				+ "and (:idSector is null or se.idListadoDetalle=:idSector) "
//				+ "and (:idSubsector is null or ss.idListadoDetalle=:idSubsector) "
//				+ "and (:idEstado is null or spe.idListadoDetalle=:idEstado) ",
//		countQuery = "select count(distinct sp.supervisora) from SupervisoraPerfil sp"
//				+ "left join  sp.supervisora s "
//				+ "left join  sp.sector se "
//				+ "left join  sp.actividad a "
//				+ "left join  sp.subsector ss "
//				+ "left join  sp.subCategoria sc "
//				+ "left join  sp.unidad u "
//				+ "left join  sp.perfil pe "
//				+ "left join sp.estado spe "
//				+ "left join  s.tipoDocumento td "
//				+ "left join  s.tipoPersona tp "
//				+ "left join  s.pais p "
//				+ "where s.tipoPersona.codigo ='"+Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO+"' "
//				+ "and (:codigoRuc is null or s.codigoRuc=:codigoRuc) "
//				+ "and (:idSector is null or se.idListadoDetalle=:idSector) "
//				+ "and (:idSubsector is null or ss.idListadoDetalle=:idSubsector) "
//				+ "and (:idEstado is null or spe.idListadoDetalle=:idEstado) ")			
//		public Page<Supervisora> liberacionPersonal(String codigoRuc, Long idSector, Long idSubsector, Long idEstado, Pageable pageable);
//	
		
		@Query(value="select distinct s from SupervisoraPerfil sp "
				+ "left join sp.sector se "
				+ "left join sp.actividad a "
				+ "left join sp.subsector ss "
				+ "left join sp.subCategoria sc "
				+ "left join sp.unidad u "
				+ "left join sp.perfil pe "
				+ "left join sp.supervisora s "
				+ "left join s.tipoDocumento td "
				+ "left join s.tipoPersona tp "
				+ "left join s.pais p "
				+ "left join sp.estado spe "
				+ "where s.tipoPersona.codigo ='"+Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO+"' "
				+ "and (:codigoRuc is null or s.codigoRuc=:codigoRuc) "
				+ "and (:idSector is null or se.idListadoDetalle=:idSector) "
				+ "and (:idSubsector is null or ss.idListadoDetalle=:idSubsector) "
				+ "and (:idEstado is null or spe.idListadoDetalle=:idEstado) "
				,
		countQuery = "select count(distinct s) from SupervisoraPerfil sp "
				+ "left join  sp.supervisora s "
				+ "left join  sp.sector se "
				+ "left join  sp.actividad a "
				+ "left join  sp.subsector ss "
				+ "left join  sp.subCategoria sc "
				+ "left join  sp.unidad u "
				+ "left join  sp.perfil pe "
				+ "left join  s.tipoDocumento td "
				+ "left join  s.tipoPersona tp "
				+ "left join  s.pais p "
				+ "left join  sp.estado spe "
				+ "where s.tipoPersona.codigo ='"+Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO+"' "
				+ "and (:codigoRuc is null or s.codigoRuc=:codigoRuc) "
				+ "and (:idSector is null or se.idListadoDetalle=:idSector) "
				+ "and (:idSubsector is null or ss.idListadoDetalle=:idSubsector) "
				+ "and (:idEstado is null or spe.idListadoDetalle=:idEstado) "
				)			
		public Page<Supervisora> liberacionPersonal(String codigoRuc, Long idSector, Long idSubsector, Long idEstado,Pageable pageable);

		
		@Query(value="select distinct s from SupervisoraPerfil sp, PropuestaProfesional pp "
				+ "left join sp.sector se "
				+ "left join sp.actividad a "
				+ "left join sp.subsector ss "
				+ "left join sp.subCategoria sc "
				+ "left join sp.unidad u "
				+ "left join sp.perfil pe "
				+ "left join sp.supervisora s "
				+ "left join s.tipoDocumento td "
				+ "left join s.tipoPersona tp "
				+ "left join s.pais p "
				+ "left join sp.estado spe "
				+ "left join pp.supervisora pps "
				+ "left join pp.propuesta ppp "
				+ "left join ppp.procesoItem ppi "
				+ "left join ppi.proceso pro "
				+ "where s.tipoPersona.codigo ='"+Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO+"' "
				+ "and s.idSupervisora = pps.idSupervisora "
				+ "and (:codigoRuc is null or s.codigoRuc=:codigoRuc) "
				+ "and (:idSector is null or se.idListadoDetalle=:idSector) "
				+ "and (:idSubsector is null or ss.idListadoDetalle=:idSubsector) "
				+ "and (:idEstado is null or spe.idListadoDetalle=:idEstado) "
				+ "and (:item is null or ppi.descripcionItem like:item) "
				+ "and (:proceso is null or pro.nombreProceso like:proceso) ",
		countQuery = "select count(distinct s) from SupervisoraPerfil sp, PropuestaProfesional pp "
				+ "left join  sp.supervisora s "
				+ "left join  sp.sector se "
				+ "left join  sp.actividad a "
				+ "left join  sp.subsector ss "
				+ "left join  sp.subCategoria sc "
				+ "left join  sp.unidad u "
				+ "left join  sp.perfil pe "
				+ "left join  s.tipoDocumento td "
				+ "left join  s.tipoPersona tp "
				+ "left join  s.pais p "
				+ "left join  sp.estado spe "
				+ "left join pp.supervisora pps "
				+ "left join pp.propuesta ppp "
				+ "left join ppp.procesoItem ppi "
				+ "left join ppi.proceso pro "
				+ "where s.tipoPersona.codigo ='"+Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO+"' "
				+ "and s.idSupervisora = pps.idSupervisora "
				+ "and (:codigoRuc is null or s.codigoRuc=:codigoRuc) "
				+ "and (:idSector is null or se.idListadoDetalle=:idSector) "
				+ "and (:idSubsector is null or ss.idListadoDetalle=:idSubsector) "
				+ "and (:idEstado is null or spe.idListadoDetalle=:idEstado) "
				+ "and (:item is null or ppi.descripcionItem like:item) "
				+ "and (:proceso is null or pro.nombreProceso like:proceso) ")			
		public Page<Supervisora> liberacionPersonal(String codigoRuc, Long idSector, Long idSubsector, Long idEstado,String proceso, String item, Pageable pageable);
}
