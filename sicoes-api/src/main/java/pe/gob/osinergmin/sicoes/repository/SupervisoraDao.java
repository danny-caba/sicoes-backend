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
import pe.gob.osinergmin.sicoes.util.Constantes;

@Repository
public interface SupervisoraDao extends JpaRepository<Supervisora, Long> {
	
	@Query("select s from Supervisora s "
			+ "left join fetch s.tipoDocumento td "
			+ "left join fetch s.pais p "
			+ "left join fetch s.tipoPersona t "
			+ "left join fetch s.estado e "
			+ "where s.idSupervisora=:idSupervisora")
	public Supervisora obtener(Long idSupervisora);
	
	
	
	@Query("select s from Supervisora s "
			+ "left join fetch s.tipoDocumento td "
			+ "left join fetch s.pais p "
			+ "left join fetch s.tipoPersona t "
			+ "left join fetch s.estado e "
			+ "where "
			+ "(t.idListadoDetalle=:idTipoEmpresa) "
			+ "and (:numeroDocumento is null or s.numeroDocumento=:numeroDocumento) "
			+ "and (s.nombreRazonSocial like :nombreRazonSocial ) "
			+ "and (s.numeroExpediente like :numeroExpediente ) ")
	List<Supervisora> listarSupervisoras(String numeroExpediente,Long idTipoEmpresa,String nombreRazonSocial,String numeroDocumento);
	
	@Query(value="select s from Supervisora s "
			+ "left join fetch s.tipoDocumento td "
			+ "left join fetch s.pais p "
			+ "left join fetch s.tipoPersona tp "
			+ "left join fetch s.estado se "
			+ "where "
			+ "se.codigo ='"+Constantes.LISTADO.ESTADO_SUPERVISORA.VIGENTE+"' "
			
			+ "and (:idTipoDocumento is null or td.idListadoDetalle=:idTipoDocumento) "
			+ "and (:idTipoPersona is null or tp.idListadoDetalle=:idTipoPersona) "
			+ "and (:ruc is null or s.numeroDocumento=:ruc) "
			+ "and (s.numeroExpediente like :nroExpediente) "
			+ "and (concat(s.nombreRazonSocial,' ',s.apellidoPaterno,' ',s.apellidoMaterno,' ',s.nombres,' ') like :nombres) "
			+ "and (:fechaInicio is null or s.fechaIngreso>=:fechaInicio) "
			+ "and (:fechaFin is null or s.fechaIngreso<=:fechaFin) ",
	countQuery = "select count(s) from Supervisora s "
			+ "left join  s.tipoDocumento td "
			+ "left join  s.pais p "
			+ "left join  s.tipoPersona tp "
			+ "left join  s.estado se "
			+ "where "
			+ "se.codigo ='"+Constantes.LISTADO.ESTADO_SUPERVISORA.VIGENTE+"' "
			
			+ "and (:idTipoDocumento is null or td.idListadoDetalle=:idTipoDocumento) "
			+ "and (:idTipoPersona is null or tp.idListadoDetalle=:idTipoPersona) "
			+ "and (:ruc is null or s.numeroDocumento=:ruc) "
			+ "and (s.numeroExpediente like :nroExpediente) "
			+ "and (concat(s.nombreRazonSocial,' ',s.apellidoPaterno,' ',s.apellidoMaterno,' ',s.nombres,' ') like :nombres) "
			+ "and (:fechaInicio is null or s.fechaIngreso>=:fechaInicio) "
			+ "and (:fechaFin is null or s.fechaIngreso<=:fechaFin) ")			
	Page<Supervisora> buscar(String nroExpediente,Long idTipoPersona,Long idTipoDocumento,String ruc,
			String nombres,
			Date fechaInicio,
			Date fechaFin,Pageable pageable);
	
	@Query("select s from Supervisora s "
			+ "left join fetch s.tipoDocumento td "
			+ "left join fetch s.pais p "
			+ "where s.codigoCliente=:codigoCliente")
	public Supervisora obtenerXCodigo(Long codigoCliente);

	@Query("select s from Supervisora s "
			+ "left join fetch s.tipoDocumento td "
			+ "left join fetch s.pais p "
			+ "where s.idSupervisora=:codigoUsuario")
	public Supervisora obtenerPorCodigoUsuario(Long codigoUsuario);
	
	@Query(value="select s from Supervisora s "
			+ "left join fetch s.tipoDocumento td "
			+ "left join fetch s.pais p "
			+ "left join fetch s.tipoPersona tp "
			+ "left join fetch s.estado se "
			+ "where "
			+ "se.codigo <>'"+Constantes.LISTADO.ESTADO_SUPERVISORA.VIGENTE+"' "
			+ "and (:idTipoDocumento is null or td.idListadoDetalle=:idTipoDocumento) "
			+ "and (:idTipoPersona is null or tp.idListadoDetalle=:idTipoPersona) "
			+ "and (:ruc is null or s.numeroDocumento=:ruc) "
			+ "and (s.numeroExpediente like :nroExpediente) "
			+ "and (concat(s.nombreRazonSocial,' ',s.apellidoPaterno,' ',s.apellidoMaterno,' ',s.nombres,' ') like :nombres) "
			+ "and (:fechaInicio is null or s.fechaIngreso>=:fechaInicio) "
			+ "and (:fechaFin is null or s.fechaIngreso<=:fechaFin) ",
	countQuery = "select count(s) from Supervisora s "
			+ "left join  s.tipoDocumento td "
			+ "left join  s.pais p "
			+ "left join  s.tipoPersona tp "
			+ "left join  s.estado se "
			+ "where "
			+ "se.codigo <>'"+Constantes.LISTADO.ESTADO_SUPERVISORA.VIGENTE+"' "
			+ "and (:idTipoDocumento is null or td.idListadoDetalle=:idTipoDocumento) "
			+ "and (:idTipoPersona is null or tp.idListadoDetalle=:idTipoPersona) "
			+ "and (:ruc is null or s.numeroDocumento=:ruc) "
			+ "and (s.numeroExpediente like :nroExpediente) "
			+ "and (concat(s.nombreRazonSocial,' ',s.apellidoPaterno,' ',s.apellidoMaterno,' ',s.nombres,' ') like :nombres) "
			+ "and (:fechaInicio is null or s.fechaIngreso>=:fechaInicio) "
			+ "and (:fechaFin is null or s.fechaIngreso<=:fechaFin) ")			
	public Page<Supervisora> buscarSuspendidasCanceladas(String nroExpediente, Long idTipoPersona, Long idTipoDocumento,
			String ruc, String nombres, Date fechaInicio, Date fechaFin, Pageable pageable);
	
	
	@Query("select s from Supervisora s "
			+ "left join fetch s.tipoDocumento td "
			+ "left join fetch s.pais p "
			+ "left join fetch s.tipoPersona t "
			+ "left join fetch s.estado e "
			+ "where s.numeroDocumento=:numeroDocumento")
	public Supervisora obtenerSupervisoraXNroDocumento(String numeroDocumento);
	
	@Query("select s from Supervisora s "
			+ "left join fetch s.tipoDocumento td "
			+ "left join fetch s.pais p "
			+ "left join fetch s.tipoPersona t "
			+ "left join fetch s.estado e "
			+ "where s.codigoRuc=:codigoRuc")
	public Supervisora obtenerSupervisoraXRUC(String codigoRuc);

	@Query("select s from Supervisora s "
			+ "left join fetch s.tipoDocumento td "
			+ "left join fetch s.pais p "
			+ "left join fetch s.tipoPersona t "
			+ "left join fetch s.estado e "
			+ "where s.codigoRuc=:codigoRuc "
			+ "and s.tipoPersona.codigo in ('"+Constantes.LISTADO.TIPO_PERSONA.PN_POSTOR+"','"+Constantes.LISTADO.TIPO_PERSONA.JURIDICA+"') ")
	public Supervisora obtenerSupervisoraPorRucPostorOrJuridica(String codigoRuc);

	@Query("select s from Supervisora s "
			+ "left join fetch s.tipoDocumento td "
			+ "left join fetch s.pais p "
			+ "left join fetch s.tipoPersona t "
			+ "left join fetch s.estado e "
			+ "where s.codigoRuc=:codigoRuc "
			+ "and t.codigo='"+Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO+"' ")
	public List<Supervisora> obtenerSupervisoraPNProfesional(String codigoRuc);
	
	
	@Query("select s from Supervisora s "
			+ "left join fetch s.tipoDocumento td "
			+ "left join fetch s.pais p "
			+ "left join fetch s.tipoPersona t "
			+ "left join fetch s.estado e "
			+ "where s.codigoRuc=:codigoRuc "
			+ "and e.codigo ='"+Constantes.LISTADO.ESTADO_SUPERVISORA.VIGENTE+"' ")
	public Supervisora obtenerSupervisoraXRUCVigente(String codigoRuc);


	@Query("select s from Supervisora s "
			+ "left join fetch s.tipoDocumento td "
			+ "left join fetch s.pais p "
			+ "left join fetch s.tipoPersona t "
			+ "left join fetch s.estado e "
			+ "where "
			+ "(t.codigo=:profesional) " 
			+ "and e.codigo ='"+Constantes.LISTADO.ESTADO_SUPERVISORA.VIGENTE+"' ")
	public List<Supervisora> listarSupervisoras(String profesional);



	   @Query("select distinct sp.sector.nombre, sp.subsector.nombre, sp.actividad.nombre, sp.unidad.nombre, sp.subCategoria.nombre, sp.perfil.nombre, s.codigoRuc, (s.nombres|| ' '|| s.apellidoPaterno|| ' '||s.apellidoMaterno), s.telefono1, s.telefono2, s.telefono3, s.correo from Supervisora s, SupervisoraPerfil sp "    
			    + "left join s.tipoDocumento td "
				+ "left join s.pais p "
				+ "left join s.tipoPersona t "
				+ "left join s.estado e "
				+ "left join sp.supervisora sps "
				+ "left join sp.perfil per "
            + "where s.idSupervisora=sps.idSupervisora and  per.idListadoDetalle = :idPerfil ")
    public List<Object[]> listarProfesionales(Long idPerfil);



	@Query("select s from Supervisora s "
			+ "left join fetch s.tipoDocumento td "
			+ "left join fetch s.pais p "
			+ "left join fetch s.tipoPersona t "
			+ "left join fetch s.estado e "
			+ "where s.codigoRuc=:codigoRuc "
			+ "and t.codigo !='"+Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO+"' "
			+ "and e.codigo ='"+Constantes.LISTADO.ESTADO_SUPERVISORA.VIGENTE+"' ")
	public Supervisora obtenerSupervisoraXRUCNoProfesional(String codigoRuc);

	@Query("select distinct s.idSupervisora, (s.nombres|| ' '|| s.apellidoPaterno|| ' '||s.apellidoMaterno), s.numeroDocumento from Supervisora s, SupervisoraPerfil sp "
			+ "left join sp.supervisora sps "
			+ "left join sp.perfil per "
			+ "where s.idSupervisora = sps.idSupervisora and  per.idListadoDetalle = :idPerfil ")
	public List<Object[]> listarProfesionalesPorPerfil(Long idPerfil);
	
}
