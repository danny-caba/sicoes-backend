package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Documento;
import pe.gob.osinergmin.sicoes.model.dto.InfoSectorActividadDTO;
import pe.gob.osinergmin.sicoes.util.Constantes;

@Repository
public interface DocumentoDao extends JpaRepository<Documento, Long> {
	
	@Query("select d from Documento d "	
			+ "left join fetch d.tipo t "
			+ "left join fetch d.tipoDocumento td "
			+ "left join fetch d.tipoCambio tc "
			+ "left join fetch d.cuentaConformidad cc "
			+ "left join fetch d.tipoIdentificacion ti "
			+ "left join fetch d.evaluacion e "
			+ "left join fetch d.pais p "
			+ "left join fetch d.subSectorDoc ssd "
			+ "left join fetch d.actividadArea aa "
			+ "left join fetch d.solicitud s "
		+ "where d.idDocumento=:idDocumento")	
	public Documento obtener(Long idDocumento);
	
	@Query(value="select d from Documento d "	
			+ "left join fetch d.tipo t "
			+ "left join fetch d.tipoDocumento td "
			+ "left join fetch d.tipoCambio tc "
			+ "left join fetch d.cuentaConformidad cc "
			+ "left join fetch d.tipoIdentificacion ti "
			+ "left join fetch d.evaluacion e "
			+ "left join fetch d.pais p "
			+ "left join fetch d.solicitud s "		
			+ "left join fetch d.subSectorDoc ssd "
			+ "left join fetch d.actividadArea aa "
			+ "where s.idSolicitud=:idSolicitud "
			+ "order by d.idDocumento ASC",
			countQuery = "select count(d) from Documento d "
					+ "left join d.tipo t "
					+ "left join d.tipoDocumento td "
					+ "left join d.tipoCambio tc "
					+ "left join d.cuentaConformidad cc "
					+ "left join d.tipoIdentificacion ti "
					+ "left join d.pais p "
					+ "left join d.solicitud s "			
					+ "left join d.subSectorDoc ssd "
					+ "left join d.actividadArea aa "
					+ "where s.idSolicitud=:idSolicitud")			
	public Page<Documento> buscar(Long idSolicitud, Pageable pageable);
	
	@Query(value="select d from Documento d "	
			+ "left join fetch d.tipo t "
			+ "left join fetch d.tipoDocumento td "
			+ "left join fetch d.tipoCambio tc "
			+ "left join fetch d.cuentaConformidad cc "
			+ "left join fetch d.tipoIdentificacion ti "
			+ "left join fetch d.evaluacion e "
			+ "left join fetch d.pais p "
			+ "left join fetch d.solicitud s "			
			+ "left join fetch d.subSectorDoc ssd "
			+ "left join fetch d.actividadArea aa "
			+ "where s.idSolicitud=:idSolicitud and ssd.idListadoDetalle IN (:subsectores) "
			+ "order by d.idDocumento ASC",
			countQuery = "select count(d) from Documento d "
					+ "left join d.tipo t "
					+ "left join d.tipoDocumento td "
					+ "left join d.tipoCambio tc "
					+ "left join d.cuentaConformidad cc "
					+ "left join d.tipoIdentificacion ti "
					+ "left join d.pais p "
					+ "left join d.solicitud s "	
					+ "left join d.subSectorDoc ssd "
					+ "left join d.actividadArea aa "
					+ "where s.idSolicitud=:idSolicitud and ssd.idListadoDetalle IN (:subsectores)")			
	public Page<Documento> buscarSubSectorAsignado(Long idSolicitud,List<Long> subsectores, Pageable pageable);
	
	@Query("select d from Documento d "	
			+ "left join fetch d.tipo t "
			+ "left join fetch d.tipoDocumento td "
			+ "left join fetch d.tipoCambio tc "
			+ "left join fetch d.cuentaConformidad cc "
			+ "left join fetch d.tipoIdentificacion ti "
			+ "left join fetch d.pais p "
			+ "left join fetch d.subSectorDoc ssd "
			+ "left join fetch d.actividadArea aa "
		+ "where d.solicitud.idSolicitud=:idSolicitud")
	public List<Documento> buscar(Long idSolicitud);
	
	@Query("select d from Documento d "	
			+ "left join fetch d.tipo t "
			+ "left join fetch d.tipoDocumento td "
			+ "left join fetch d.tipoCambio tc "
			+ "left join fetch d.cuentaConformidad cc "
			+ "left join fetch d.tipoIdentificacion ti "
			+ "left join fetch d.pais p "
			+ "left join fetch d.evaluacion e "			
		+ "where d.solicitud.idSolicitud=:idSolicitud and (d.flagSiged is null  or d.flagSiged=0)  ")
	public List<Documento> buscarPresentacion(Long idSolicitud);

	@Query(value="select sum(d.montoContratoSol) from Documento d "
			+ "left join  d.solicitud s "	
			+ "where s.idSolicitud=:idSolicitud and d.montoContratoSol is not null ")
	public Double sumarMontoTotal(Long idSolicitud);

	@Query(value="select sum(d.montoContratoSol) from Documento d "
			+ "left join  d.evaluacion e "
			+ "left join  d.solicitud s "
			+ "where s.idSolicitud=:idSolicitud and d.montoContratoSol is not null and e.codigo <> '"+ Constantes.LISTADO.RESULTADO_EVALUACION.POR_EVALUAR +"' ")
	Double sumarMontoTotalEvaluado(Long idSolicitud);
	
	@Query("select d from Documento d "	
			+ "left join fetch d.tipo t "
			+ "left join fetch d.tipoDocumento td "
			+ "left join fetch d.tipoCambio tc "
			+ "left join fetch d.cuentaConformidad cc "
			+ "left join fetch d.tipoIdentificacion ti "
			+ "left join fetch d.evaluacion e "
			+ "left join fetch d.pais p "
			+ "left join fetch d.solicitud s "			
			+ "left join fetch d.subSectorDoc ssd "
			+ "left join fetch d.actividadArea aa "
			+ "where s.idSolicitud=:idSolicitud and ssd.idListadoDetalle = :idSubSector ")
	public List<Documento> buscarXSolicitudXSubSector(Long idSolicitud, Long idSubSector);
	
	@Query("select count(d) from Documento d " +
	           "left join d.tipoDocumento td " +
	           "where d.solicitud.idSolicitud = :idSolicitud " +
	           "and td.idListadoDetalle = :tipoDocumento " +
	           "and d.codigoContrato = :codigoContrato")
	public Long existeDocumento(Long idSolicitud, Long tipoDocumento, String codigoContrato);
	
	 @Query("select count(d) from Documento d " +
	           "left join d.tipoDocumento td " +
	           "where d.solicitud.idSolicitud = :idSolicitud " +
	           "and td.idListadoDetalle = :tipoDocumento " +
	           "and d.codigoContrato = :codigoContrato " +
	           "and d.idDocumento <> :idDocumento")
	public Long existeDocumentos(Long idSolicitud, Long tipoDocumento, String codigoContrato, Long idDocumento);
	
}
