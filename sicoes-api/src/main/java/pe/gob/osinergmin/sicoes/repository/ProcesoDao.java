package pe.gob.osinergmin.sicoes.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.Proceso;
import pe.gob.osinergmin.sicoes.model.ProcesoEtapa;
import pe.gob.osinergmin.sicoes.util.Constantes;

@Repository
public interface ProcesoDao extends JpaRepository<Proceso, Long> {
	
	@Query("select p from Proceso p "	
			+ "left join fetch p.sector s "
			+ "left join fetch p.subsector ss "
			+ "left join fetch p.estado e "
			+ "left join fetch p.tipoFacturacion tf "
			+ "where p.procesoUuid=:procesoUuid")	
	public Proceso obtener(String procesoUuid);
	
	@Query("select p from Proceso p "	
			+ "left join fetch p.sector s "
			+ "left join fetch p.subsector ss "
			+ "left join fetch p.estado e "
			+ "left join fetch p.tipoFacturacion tf "
			+ "where p.idProceso=:idProceso")	
	public Proceso obtener(Long idProceso);
	
	@Query(value="select distinct p from Proceso p "
			+ "left join fetch p.usuario u "
			+ "left join fetch p.sector s "
			+ "left join fetch p.subsector ss "
			+ "left join fetch p.estado e "
			+ "left join fetch p.tipoFacturacion tf "
			+ "where (:fechaDesde is null or trunc(p.fechaPublicacion)>=:fechaDesde) "
			+ "and (:fechaHasta is null or trunc(p.fechaPublicacion)<=:fechaHasta) "
			+ "and (:idEstado is null or p.estado.idListadoDetalle = :idEstado) "
			+ "and (:idSector is null or p.sector.idListadoDetalle = :idSector) "
			+ "and (:idSubSector is null or p.subsector.idListadoDetalle = :idSubSector) "
			+ "and (:nroProceso is null or p.numeroProceso like :nroProceso) "
			+ "and (:nombreProceso is null or p.nombreProceso like :nombreProceso) "
			+ "and (:nroExpediente is null or p.numeroExpediente like :nroExpediente) "
			+ "and ((u.codigoUsuarioInterno=:codigoUsuario) "
			+ "or exists (select pmp.idProceso from ProcesoMiembro pm left join pm.proceso pmp left join pm.estado e where pmp.idProceso=p.idProceso and pm.codigoUsuario=:codigoUsuario and e.codigo='"+Constantes.LISTADO.ESTADO_MIEMBRO.ACTIVO+"' )) "
			+ "order by p.fechaPublicacion desc ",
			countQuery ="select count(distinct p) from Proceso p "
					+ "left join p.usuario u "
					+ "left join p.sector s "
					+ "left join p.subsector ss "
					+ "left join p.estado e "
					+ "left join p.tipoFacturacion tf "
					+ "where (:fechaDesde is null or trunc(p.fechaPublicacion)>=:fechaDesde) "
					+ "and (:fechaHasta is null or trunc(p.fechaPublicacion)<=:fechaHasta) "
					+ "and (:idEstado is null or p.estado.idListadoDetalle = :idEstado) "
					+ "and (:idSector is null or p.sector.idListadoDetalle = :idSector) "
					+ "and (:idSubSector is null or p.subsector.idListadoDetalle = :idSubSector) "
					+ "and (:nroProceso is null or p.numeroProceso like :nroProceso) "
					+ "and (:nombreProceso is null or p.nombreProceso like :nombreProceso) "
					+ "and (:nroExpediente is null or p.numeroExpediente like :nroExpediente) "
					+ "and ((u.codigoUsuarioInterno=:codigoUsuario) "
					+ "or exists (select pmp.idProceso from ProcesoMiembro pm left join pm.proceso pmp left join pm.estado e where pmp.idProceso=p.idProceso and pm.codigoUsuario=:codigoUsuario  and e.codigo='"+Constantes.LISTADO.ESTADO_MIEMBRO.ACTIVO+"' )) "
					+ "order by p.fechaPublicacion desc ")		
	public Page<Proceso> buscar(Date fechaDesde,Date fechaHasta,Long idEstado,Long idSector,Long idSubSector,String nroProceso,String nombreProceso,String nroExpediente,Long codigoUsuario,Pageable pageable);

	@Query(value="select distinct p from Proceso p "
			+ "left join fetch p.usuario u "
			+ "left join fetch p.sector s "
			+ "left join fetch p.subsector ss "
			+ "left join fetch p.estado e "
			+ "left join fetch p.tipoFacturacion tf "
			+ "where (:nombreArea is null or :nombreArea = '' or p.nombreArea like :nombreArea) "
			+ "and (:idEstado is null or p.estado.idListadoDetalle = :idEstado) "
			+ "and (:nombreProceso is null or :nombreProceso = '' or UPPER(p.nombreProceso) like CONCAT('%', UPPER(:nombreProceso), '%')) "
			+ "and (p.estado.idListadoDetalle <> 722) "
			+ "order by p.idProceso desc ",
			countQuery ="select count(distinct p) from Proceso p "
					+ "left join p.usuario u "
					+ "left join p.sector s "
					+ "left join p.subsector ss "
					+ "left join p.estado e "
					+ "left join p.tipoFacturacion tf "
					+ "where (:nombreArea is null or :nombreArea = '' or p.nombreArea like :nombreArea) "
					+ "and (:idEstado is null or p.estado.idListadoDetalle = :idEstado) "
					+ "and (:nombreProceso is null or :nombreProceso = '' or UPPER(p.nombreProceso) like CONCAT('%', UPPER(:nombreProceso), '%')) "
					+ "order by p.idProceso desc ")		
	public Page<Proceso> buscar(Long idEstado,String nombreArea,String nombreProceso,Pageable pageable);

	
	@Query("select p from ProcesoEtapa pe "
			+ "left join pe.proceso p "
			+ "left join pe.etapa et "
			+ "left join p.estado e "
			+ "where et.codigo='"+Constantes.LISTADO.ETAPA_PROCESO.ETAPA_PRESENTADO+"' "
			+ "and e.codigo='"+Constantes.LISTADO.ESTADO_PROCESO.CONVOCATORIA+"' "
			+ "and et.orden = 1 "
			+ "and trunc(pe.fechaInicio)<=trunc(sysdate) and trunc(pe.fechaFin)>=trunc(sysdate) ")
	public List<Proceso> obtenerProcesoPresentacion();
	
	@Query("select p from Proceso p ")
	public List<Proceso> obtenerProcesos();
	
	@Query("select p from ProcesoEtapa pe "
			+ "left join pe.proceso p "
			+ "left join pe.etapa et "
			+ "left join p.estado e "
			+ "where e.codigo='"+Constantes.LISTADO.ESTADO_PROCESO.PRESENTACION+"' "
			+ "and et.orden = 1 "
			+ "and trunc(pe.fechaFin)<trunc(sysdate) ")
	public List<Proceso> obtenerProcesoAdmision();

	@Query("select p from Proceso p where UPPER(p.nombreProceso)=:nombreProceso")
	public List<Proceso> obtenerProcesosNombre(String nombreProceso);

	@Query("select p from Proceso p where UPPER(p.numeroProceso)=:numeroProceso")
	public List<Proceso> obtenerProcesosNumero(String numeroProceso);

	@Query("select distinct p from ProcesoEtapa pe "
			+ "left join pe.proceso p "
			+ "left join pe.etapa et "
			+ "left join p.estado e "
			+ "where e.codigo='"+Constantes.LISTADO.ESTADO_PROCESO.ADMISION_CALIFICACION+"' "
			+ "and p.fechaPublicacion is not null "
			+ "and trunc(pe.fechaFin) < trunc(sysdate) "
			+ "and trunc(p.fechaPublicacion) >= trunc(:fechaInicioJob) ")
	List<Proceso> obtenerProcesoAdmisionCalificacion(Date fechaInicioJob);

	@Query("select distinct p from ProcesoEtapa pe "
			+ "left join pe.proceso p "
			+ "left join pe.etapa et "
			+ "left join p.estado e "
			+ "where e.codigo='"+Constantes.LISTADO.ESTADO_PROCESO.DESIGNACION+"' "
			+ "and p.fechaPublicacion is not null "
			+ "and trunc(sysdate) > trunc(pe.fechaInicio) ")
	List<Proceso> obtenerProcesoDesignado();
}
