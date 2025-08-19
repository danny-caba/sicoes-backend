package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.SuspensionCancelacion;
import pe.gob.osinergmin.sicoes.util.Constantes;

@Repository
public interface SuspensionCancelacionDao extends JpaRepository<SuspensionCancelacion, Long> {
	
	@Query("select s from SuspensionCancelacion s "
			+ "left join fetch s.supervisora su "
			+ "left join fetch s.tipo t "
			+ "left join fetch s.estado e "
			+ "left join fetch s.causal c "
		+ "where s.idSuspensionCancelacion=:id")	
	public SuspensionCancelacion obtener(Long id);


	
	@Query(value="select sc from SuspensionCancelacion sc "
			+ "left join fetch sc.tipo st "
			+ "left join fetch sc.causal sca "
			+ "left join fetch sc.estado ses "
			+ "left join fetch sc.supervisora s "
			+ "left join fetch s.tipoDocumento td "
			+ "left join fetch s.pais p "
			+ "left join fetch s.tipoPersona tp "
			+ "left join fetch s.estado se "
//			+ "where "
			+ "where ((st.codigo='"+Constantes.LISTADO.TIPO_SUSPENSION_CANCELACION.CANCELACION+"' and ses.codigo='"+Constantes.LISTADO.ESTADO_SUSPENSION_CANCELACION.PROCESADO+"') or "
			+ "(st.codigo='"+Constantes.LISTADO.TIPO_SUSPENSION_CANCELACION.SUSPENSION+"' and ses.codigo='"+Constantes.LISTADO.ESTADO_SUSPENSION_CANCELACION.PROCESADO_PARCIALMENTE+"')) "					
			+ "and (:idTipoDocumento is null or td.idListadoDetalle=:idTipoDocumento) "
			+ "and (:idTipoPersona is null or tp.idListadoDetalle=:idTipoPersona) "
			+ "and (:ruc is null or s.numeroDocumento=:ruc) "
			+ "and (s.numeroExpediente like :nroExpediente) "
			+ "and (concat(s.nombreRazonSocial,' ',s.apellidoPaterno,' ',s.apellidoMaterno,' ',s.nombres,' ') like :nombres) "
			+ "and (:idEstado is null or se.idListadoDetalle=:idEstado) ",
	countQuery = "select count(sc) from SuspensionCancelacion sc "
			+ "left join sc.tipo st "
			+ "left join sc.causal sca "
			+ "left join sc.estado ses "
			+ "left join  sc.supervisora s "
			+ "left join  s.tipoDocumento td "
			+ "left join  s.pais p "
			+ "left join  s.tipoPersona tp "
			+ "left join  s.estado se "
//			+ "where "
			+ "where ((st.codigo='"+Constantes.LISTADO.TIPO_SUSPENSION_CANCELACION.CANCELACION+"' and ses.codigo='"+Constantes.LISTADO.ESTADO_SUSPENSION_CANCELACION.PROCESADO+"') or "
					+ "(st.codigo='"+Constantes.LISTADO.TIPO_SUSPENSION_CANCELACION.SUSPENSION+"' and ses.codigo='"+Constantes.LISTADO.ESTADO_SUSPENSION_CANCELACION.PROCESADO_PARCIALMENTE+"')) "					
			+ "and (:idTipoDocumento is null or td.idListadoDetalle=:idTipoDocumento) "
			+ "and (:idTipoPersona is null or tp.idListadoDetalle=:idTipoPersona) "
			+ "and (:ruc is null or s.numeroDocumento=:ruc) "
			+ "and (s.numeroExpediente like :nroExpediente) "
			+ "and (concat(s.nombreRazonSocial,' ',s.apellidoPaterno,' ',s.apellidoMaterno,' ',s.nombres,' ') like :nombres) "
			+ "and (:idEstado is null or se.idListadoDetalle=:idEstado) ")		
	public Page<SuspensionCancelacion> buscar(String nroExpediente, Long idTipoPersona, Long idTipoDocumento,
			String ruc, String nombres, Long idEstado, Pageable pageable);


	@Query("select s from SuspensionCancelacion s "
			+ "left join fetch s.supervisora su "
			+ "left join fetch s.tipo t "
			+ "left join fetch s.estado e "
			+ "left join fetch s.causal c "
		+ "where e.codigo ='"+Constantes.LISTADO.ESTADO_SUSPENSION_CANCELACION.PROGRAMADO+"' "
		+ "and s.fechaInicio<=trunc(sysdate) ")	
	public List<SuspensionCancelacion> obtenerSuspensionCancelacionPendienteProcesar();
	
	@Query("select s from SuspensionCancelacion s "
			+ "left join fetch s.supervisora su "
			+ "left join fetch s.tipo t "
			+ "left join fetch s.estado e "
			+ "left join fetch s.causal c "
		+ "where e.codigo ='"+Constantes.LISTADO.ESTADO_SUSPENSION_CANCELACION.PROCESADO_PARCIALMENTE+"' "
		+ "and s.fechaFin<trunc(sysdate) ")	
	public List<SuspensionCancelacion> obtenerSuspensionCancelacionProcesadoParcialmente();


	@Query("select tp.nombre,p.nombre,td.nombre,s.numeroDocumento,COALESCE(s.nombreRazonSocial,concat(s.apellidoPaterno,' ',s.apellidoMaterno,' ',s.nombres,' ')),es.nombre,se.nombre,ss.nombre,a.nombre,u.nombre,sct.nombre,pe.nombre,sp.fechaIngreso,sp.numeroExpediente,es.nombre,sc.fechaInicio,sc.fechaFin  from SupervisoraPerfil sp "
			+ "left join sp.sector se "
			+ "left join sp.actividad a "
			+ "left join sp.subsector ss "
			+ "left join sp.subCategoria sct "
			+ "left join sp.unidad u "
			+ "left join sp.perfil pe "
			+ "left join sp.supervisora s "
			+ "left join s.tipoDocumento td "
			+ "left join s.pais p "
			+ "left join s.tipoPersona tp "
			+ "left join s.estado es, SuspensionCancelacion sc "
			+ "left join sc.tipo st "
			+ "left join sc.estado ses "
			+ "where "
			+ "s.idSuspensionCancelacion=sc.idSuspensionCancelacion "	
			+ "and ((st.codigo='"+Constantes.LISTADO.TIPO_SUSPENSION_CANCELACION.CANCELACION+"' and ses.codigo='"+Constantes.LISTADO.ESTADO_SUSPENSION_CANCELACION.PROCESADO+"') or "
			+ "(st.codigo='"+Constantes.LISTADO.TIPO_SUSPENSION_CANCELACION.SUSPENSION+"' and ses.codigo='"+Constantes.LISTADO.ESTADO_SUSPENSION_CANCELACION.PROCESADO_PARCIALMENTE+"')) "					
			+ "and (:idTipoDocumento is null or td.idListadoDetalle=:idTipoDocumento) "
			+ "and (:idTipoPersona is null or tp.idListadoDetalle=:idTipoPersona) "
			+ "and (:ruc is null or s.numeroDocumento=:ruc) "
			+ "and (s.numeroExpediente like :nroExpediente) "
			+ "and (concat(s.nombreRazonSocial,' ',s.apellidoPaterno,' ',s.apellidoMaterno,' ',s.nombres,' ') like :nombres) "
			+ "and (:idEstado is null or se.idListadoDetalle=:idEstado) ")		
	public List<Object[]> listarSupervisoras(String nroExpediente, Long idTipoPersona, Long idTipoDocumento, String ruc,
			String nombres, Long idEstado);

}
