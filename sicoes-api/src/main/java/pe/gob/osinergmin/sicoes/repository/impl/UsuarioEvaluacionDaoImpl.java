package pe.gob.osinergmin.sicoes.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.model.ConfiguracionBandeja;
import pe.gob.osinergmin.sicoes.model.Division;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Rol;
import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.UsuarioReasignacion;
import pe.gob.osinergmin.sicoes.model.UsuarioRol;
import pe.gob.osinergmin.sicoes.model.UsuarioRolConfiguracion;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetallePerfilDTO;
import pe.gob.osinergmin.sicoes.repository.ListadoDetallePerfilDao;
import pe.gob.osinergmin.sicoes.repository.UsuarioEvaluacionDao;

@Repository
public class UsuarioEvaluacionDaoImpl implements UsuarioEvaluacionDao{

	@PersistenceContext
	protected EntityManager entityManager;
	
	@Override
	public List<Usuario> obtenerUsuariosEvaluadores(Long idPerfil) {
		
		Query queryRes = null;
		Long idDivision = null;
		
		
		if (idPerfil!=null) {
			Query e = entityManager.createNativeQuery(
					"SELECT ID_DIVISION,ID_PERFIL,ID_PERFIL_DIVISION FROM SICOES_TX_PERFIL_DIVISION WHERE ID_PERFIL="+idPerfil); 
			queryRes=e;
		}
		
		List<Object[]> listaDivision = queryRes.getResultList();
		
		for (Object[] objects : listaDivision) {
			idDivision = obtenerLong(objects[0]);
		}
		
		
		if(idDivision!=null) {
			Query query = entityManager.createNativeQuery(
					"SELECT                                                             "+
		    				"USU.ID_USUARIO,                                             "+
		    				"USU.DE_RUC,                                             "+
		    				"USU.DE_USUARIO,                                             "+
		    				"USU.NO_USUARIO,                                             "+
		    				"USU.DE_CONTRASENIA,                                             "+
		    				"USU.DE_RAZON_SOCIAL,                                             "+
		    				"USU.DE_CORREO,                                             "+
		    				"USU.NU_TELEFONO,                                             "+
		    				"USU.ES_USUARIO                                             "+
		    				"FROM                                                               "+
		    				"SICOES_TM_USUARIO USU   "+
		    				"INNER JOIN SICOES_TM_CONF_BANDEJA CONF ON (USU.ID_USUARIO = CONF.ID_USUARIO)"+
		    				"INNER JOIN SICOES_TX_PERFIL_DIVISION DIV ON (CONF.ID_PERFIL_LD = DIV.ID_PERFIL)"+
		    				"WHERE                                                              "+
		    				"DIV.ID_DIVISION="+idDivision
	        		);
			System.out.println(query);
			queryRes=query;
		}
        
        List<Object[]> lista = queryRes.getResultList();
        
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario = new Usuario();
        for (Object[] dto: lista) {
        	usuario = new Usuario();
        	usuario.setIdUsuario(obtenerLong(dto[0]));
        	usuario.setCodigoRuc(obtenerString(dto[1]));
        	usuario.setUsuario(obtenerString(dto[2]));
        	usuario.setNombreUsuario(obtenerString(dto[3]));
        	usuario.setContrasenia(obtenerString(dto[4]));
        	usuario.setRazonSocial(obtenerString(dto[5]));
        	usuario.setCorreo(obtenerString(dto[6]));
        	usuario.setTelefono(obtenerLong(dto[7]));
        	usuario.setEstadoUsuario(obtenerString(dto[8]));
        	usuarios.add(usuario);
        }
        
		return usuarios;
	}
	
	@Override
	public List<Asignacion> obtenerAsignacionesPorIdUsuario(Long idUsuario, Long idConfiguracionBandeja) {
		
		Query queryRes = null;
		
		
		
		if(idUsuario!=null) {
			Query query = entityManager.createNativeQuery(
					
    				"SELECT ASIG.ID_ASIGNACION ,                                             "+
    				"ASIG.ID_SOLICITUD,                                             "+
    				"ASIG.ID_TIPO_LD                                             "+
    				"FROM                                                               "+
    				"SICOES_TM_CONF_BANDEJA CONF   "+
    				"INNER JOIN SICOES_TM_USUARIO USU ON (CONF.ID_USUARIO = USU.ID_USUARIO) "+
    				"INNER JOIN SICOES_TR_ASIGNACION ASIG ON (USU.ID_USUARIO = ASIG.ID_USUARIO OR USU.ID_USUARIO = ASIG.ID_USUARIO_ORIGEN) "+
    				"INNER JOIN SICOES_TR_SOLICITUD SOL ON (ASIG.ID_SOLICITUD = SOL.ID_SOLICITUD AND SOL.ID_ESTADO_LD IN (1,70,74,75,538,539)) "+
    				"INNER JOIN SICOES_TR_OTRO_REQUISITO REQ ON (SOL.ID_SOLICITUD = REQ.ID_SOLICITUD AND CONF.ID_PERFIL_LD = REQ.ID_PERFIL_LD AND REQ.ID_FINALIZADO_LD IS NULL) "+
    				"LEFT JOIN SICOES_TM_LISTADO_DETALLE TCONF ON (CONF.ID_TIPO_CONF = TCONF.ID_LISTADO_DETALLE) "+
    				"LEFT JOIN SICOES_TM_LISTADO_DETALLE SEC ON (CONF.ID_SECTOR_LD = SEC.ID_LISTADO_DETALLE) "+
    				"LEFT JOIN SICOES_TM_LISTADO_DETALLE SBSEC ON (CONF.ID_SUBSECTOR_LD = SBSEC.ID_LISTADO_DETALLE) "+
    				"LEFT JOIN SICOES_TM_LISTADO_DETALLE ACT ON (CONF.ID_ACTIVIDAD_LD = ACT.ID_LISTADO_DETALLE) "+
    				"LEFT JOIN SICOES_TM_LISTADO_DETALLE UNI ON (CONF.ID_UNIDAD_LD = UNI.ID_LISTADO_DETALLE) "+
    				"LEFT JOIN SICOES_TM_LISTADO_DETALLE SBCAT ON (CONF.ID_SUBCATEGORIA_LD = SBCAT.ID_LISTADO_DETALLE) "+
    				"INNER JOIN SICOES_TM_LISTADO_DETALLE PER ON (CONF.ID_PERFIL_LD = PER.ID_LISTADO_DETALLE) "+
    				"LEFT JOIN  SICOES_TX_USUARIO_REASIGNACION TX ON (CONF.ID_CONF_BANDEJA = TX.ID_CONF_BANDEJA AND TX.ES_USUARIO_REASIGNACION = '1') "+
    				"LEFT JOIN SICOES_TM_USUARIO USU2 ON (TX.ID_USUARIO = USU2.ID_USUARIO) "+
    				"WHERE 1=1                                                              "+
    				" AND CONF.ES_CONF_BANDEJA = '1' AND USU.ID_USUARIO='" + idUsuario + "' AND CONF.ID_CONF_BANDEJA = '"+idConfiguracionBandeja+"'"+
    				"ORDER BY CONF.ID_CONF_BANDEJA ASC "
	        		);
			System.out.println(query);
			queryRes=query;
		}
        
        List<Object[]> lista = queryRes.getResultList();
        
        List<Asignacion> asignaciones = new ArrayList<>();
        Asignacion asignacion = new Asignacion();
        for (Object[] dto: lista) {
        	asignacion = new Asignacion();
        	asignacion.setIdAsignacion(obtenerLong(dto[0]));
        	asignaciones.add(asignacion);
        }
        
		return asignaciones;
	}
	
	@Override
	public BigDecimal obtenerTotalRegistrosPerfiles(Long idUsuario) {
		
		Query query = null;
		
		String consulta = "SELECT COUNT(1) " + 
				"  FROM SICOES_TM_CONF_BANDEJA CONF\r\n" + 
				"       INNER JOIN SICOES_TM_USUARIO USU ON (CONF.ID_USUARIO = USU.ID_USUARIO)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE TCONF\r\n" + 
				"          ON (CONF.ID_TIPO_CONF = TCONF.ID_LISTADO_DETALLE)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE SEC\r\n" + 
				"          ON (CONF.ID_SECTOR_LD = SEC.ID_LISTADO_DETALLE)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE SBSEC\r\n" + 
				"          ON (CONF.ID_SUBSECTOR_LD = SBSEC.ID_LISTADO_DETALLE)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE ACT\r\n" + 
				"          ON (CONF.ID_ACTIVIDAD_LD = ACT.ID_LISTADO_DETALLE)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE UNI\r\n" + 
				"          ON (CONF.ID_UNIDAD_LD = UNI.ID_LISTADO_DETALLE)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE SBCAT\r\n" + 
				"          ON (CONF.ID_SUBCATEGORIA_LD = SBCAT.ID_LISTADO_DETALLE)\r\n" + 
				"       INNER JOIN SICOES_TM_LISTADO_DETALLE PER\r\n" + 
				"          ON (CONF.ID_PERFIL_LD = PER.ID_LISTADO_DETALLE)\r\n" + 
				" WHERE CONF.ES_CONF_BANDEJA = '1' \r\n" + 
				"       AND USU.ID_USUARIO='" + idUsuario + "'\r\n";

		query = entityManager.createNativeQuery(consulta);
		
		Object resultadoTotal = query.getSingleResult();

		return resultadoTotal!=null?(BigDecimal)resultadoTotal:new BigDecimal("0");
	}
	
	@Override
	public List<ConfiguracionBandeja> listarPerfiles(Long idUsuario, int offset, int pageSize) {
		
		Query query = null;
		
		String consulta = "SELECT CONF.ID_CONF_BANDEJA,\r\n" + 
				"       CONF.ES_CONF_BANDEJA,\r\n" + 
				"       USU.ID_USUARIO,\r\n" + 
				"       USU.NO_USUARIO,\r\n" + 
				"       USU.DE_USUARIO,\r\n" + 
				"       USU.ES_USUARIO,\r\n" + 
				"       USU.DE_CORREO,\r\n" + 
				"       USU.NU_TELEFONO,\r\n" + 
				"       TCONF.ID_LISTADO_DETALLE AS ID_LISTADO_DETALLE_1,\r\n" + 
				"       TCONF.ID_LISTADO AS ID_LISTADO_1,\r\n" + 
				"       TCONF.CO_LISTADO_DETALLE AS CO_LISTADO_DETALLE_1,\r\n" + 
				"       TCONF.NO_LISTADO_DETALLE AS NO_LISTADO_DETALLE_1,\r\n" + 
				"       TCONF.DE_LISTADO_DETALLE AS DE_LISTADO_DETALLE_1,\r\n" + 
				"       TCONF.DE_VALOR AS DE_VALOR_1,\r\n" + 
				"       SEC.ID_LISTADO_DETALLE AS ID_LISTADO_DETALLE_2,\r\n" + 
				"       SEC.ID_LISTADO AS ID_LISTADO_2,\r\n" + 
				"       SEC.CO_LISTADO_DETALLE AS CO_LISTADO_DETALLE_2,\r\n" + 
				"       SEC.NO_LISTADO_DETALLE AS NO_LISTADO_DETALLE_2,\r\n" + 
				"       SEC.DE_LISTADO_DETALLE AS DE_LISTADO_DETALLE_2,\r\n" + 
				"       SEC.DE_VALOR AS DE_VALOR_2,\r\n" + 
				"       SBSEC.ID_LISTADO_DETALLE AS ID_LISTADO_DETALLE_3,\r\n" + 
				"       SBSEC.ID_LISTADO AS ID_LISTADO_3,\r\n" + 
				"       SBSEC.CO_LISTADO_DETALLE AS CO_LISTADO_DETALLE_3,\r\n" + 
				"       SBSEC.NO_LISTADO_DETALLE AS NO_LISTADO_DETALLE_3,\r\n" + 
				"       SBSEC.DE_LISTADO_DETALLE AS DE_LISTADO_DETALLE_3,\r\n" + 
				"       SBSEC.DE_VALOR AS DE_VALOR_3,\r\n" + 
				"       ACT.ID_LISTADO_DETALLE AS ID_LISTADO_DETALLE_4,\r\n" + 
				"       ACT.ID_LISTADO AS ID_LISTADO_4,\r\n" + 
				"       ACT.CO_LISTADO_DETALLE AS CO_LISTADO_DETALLE_4,\r\n" + 
				"       ACT.NO_LISTADO_DETALLE AS NO_LISTADO_DETALLE_4,\r\n" + 
				"       ACT.DE_LISTADO_DETALLE AS DE_LISTADO_DETALLE_4,\r\n" + 
				"       ACT.DE_VALOR AS DE_VALOR_4,\r\n" + 
				"       UNI.ID_LISTADO_DETALLE AS ID_LISTADO_DETALLE_5,\r\n" + 
				"       UNI.ID_LISTADO AS ID_LISTADO_5,\r\n" + 
				"       UNI.CO_LISTADO_DETALLE AS CO_LISTADO_DETALLE_5,\r\n" + 
				"       UNI.NO_LISTADO_DETALLE AS NO_LISTADO_DETALLE_5,\r\n" + 
				"       UNI.DE_LISTADO_DETALLE AS DE_LISTADO_DETALLE_5,\r\n" + 
				"       UNI.DE_VALOR AS DE_VALOR_5,\r\n" + 
				"       SBCAT.ID_LISTADO_DETALLE AS ID_LISTADO_DETALLE_6,\r\n" + 
				"       SBCAT.ID_LISTADO AS ID_LISTADO_6,\r\n" + 
				"       SBCAT.CO_LISTADO_DETALLE AS CO_LISTADO_DETALLE_6,\r\n" + 
				"       SBCAT.NO_LISTADO_DETALLE AS NO_LISTADO_DETALLE_6,\r\n" + 
				"       SBCAT.DE_LISTADO_DETALLE AS DE_LISTADO_DETALLE_6,\r\n" + 
				"       SBCAT.DE_VALOR AS DE_VALOR_6,\r\n" + 
				"       PER.ID_LISTADO_DETALLE AS ID_LISTADO_DETALLE_7,\r\n" + 
				"       PER.ID_LISTADO AS ID_LISTADO_7,\r\n" + 
				"       PER.CO_LISTADO_DETALLE AS CO_LISTADO_DETALLE_7,\r\n" + 
				"       PER.NO_LISTADO_DETALLE AS NO_LISTADO_DETALLE_7,\r\n" + 
				"       PER.DE_LISTADO_DETALLE AS DE_LISTADO_DETALLE_7,\r\n" + 
				"       PER.DE_VALOR AS DE_VALOR_7,\r\n" +
				"(SELECT RL.NO_ROL FROM SICOES_TM_CONF_BANDEJA X\r\n" + 
				"INNER JOIN SICOES_TX_USUARIO_ROL_CONF URC ON X.ID_CONF_BANDEJA = URC.ID_CONF_BANDEJA  AND URC.ES_USUARIO_ROL_CONF = '1'\r\n" + 
				"INNER JOIN SICOES_TM_ROL RL ON URC.ID_USUARIO_ROL = RL.ID_ROL\r\n" + 
				"\r\n" + 
				"WHERE X.ID_USUARIO = USU.ID_USUARIO AND X.ES_CONF_BANDEJA = '1' AND CONF.ID_CONF_BANDEJA = X.ID_CONF_BANDEJA) AS NOMBRE_ROL " +
				"  FROM SICOES_TM_CONF_BANDEJA CONF\r\n" + 
				"       INNER JOIN SICOES_TM_USUARIO USU ON (CONF.ID_USUARIO = USU.ID_USUARIO)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE TCONF\r\n" + 
				"          ON (CONF.ID_TIPO_CONF = TCONF.ID_LISTADO_DETALLE)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE SEC\r\n" + 
				"          ON (CONF.ID_SECTOR_LD = SEC.ID_LISTADO_DETALLE)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE SBSEC\r\n" + 
				"          ON (CONF.ID_SUBSECTOR_LD = SBSEC.ID_LISTADO_DETALLE)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE ACT\r\n" + 
				"          ON (CONF.ID_ACTIVIDAD_LD = ACT.ID_LISTADO_DETALLE)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE UNI\r\n" + 
				"          ON (CONF.ID_UNIDAD_LD = UNI.ID_LISTADO_DETALLE)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE SBCAT\r\n" + 
				"          ON (CONF.ID_SUBCATEGORIA_LD = SBCAT.ID_LISTADO_DETALLE)\r\n" + 
				"       INNER JOIN SICOES_TM_LISTADO_DETALLE PER\r\n" + 
				"          ON (CONF.ID_PERFIL_LD = PER.ID_LISTADO_DETALLE)\r\n" + 
				" WHERE CONF.ES_CONF_BANDEJA = '1' \r\n" + 
				"       AND USU.ID_USUARIO='" + idUsuario + "'\r\n";

				
				consulta += " ORDER BY USU.ID_USUARIO DESC, CONF.ID_CONF_BANDEJA DESC ";
				
				int off = offset * pageSize;
				
				consulta += " OFFSET '"+off+"' ROWS FETCH NEXT '"+pageSize+"' ROWS ONLY ";
				
				
		
		query = entityManager.createNativeQuery(consulta);
		
		System.out.println(query);
		
        
		List<Object[]> lista = query.getResultList();
        
        List<ConfiguracionBandeja> listaConfBandeja = new ArrayList<>();
        ConfiguracionBandeja configuracionBandeja = new ConfiguracionBandeja();
        for (Object[] dto: lista) {
        	configuracionBandeja = new ConfiguracionBandeja();
        	configuracionBandeja.setIdConfiguracionBandeja(obtenerLong(dto[0]));
        	configuracionBandeja.setEstadoConfiguracion(obtenerString(dto[1]));
        	configuracionBandeja.setUsuario(new Usuario());
        	configuracionBandeja.getUsuario().setIdUsuario(obtenerLong(dto[2]));
        	configuracionBandeja.getUsuario().setNombreUsuario(obtenerString(dto[3]));
        	configuracionBandeja.getUsuario().setUsuario(obtenerString(dto[4]));
        	configuracionBandeja.getUsuario().setEstadoUsuario(obtenerString(dto[5]));
        	configuracionBandeja.getUsuario().setCorreo(obtenerString(dto[6]));
        	configuracionBandeja.getUsuario().setTelefono(obtenerLong(dto[7]));
        	
        	configuracionBandeja.setTipoConfiguracion(new ListadoDetalle());
        	configuracionBandeja.getTipoConfiguracion().setIdListadoDetalle(obtenerLong(dto[8]));
        	configuracionBandeja.getTipoConfiguracion().setIdListado(obtenerLong(dto[9]));
        	configuracionBandeja.getTipoConfiguracion().setCodigo(obtenerString(dto[10]));
        	configuracionBandeja.getTipoConfiguracion().setNombre(obtenerString(dto[11]));
        	configuracionBandeja.getTipoConfiguracion().setDescripcion(obtenerString(dto[12]));
        	configuracionBandeja.getTipoConfiguracion().setValor(obtenerString(dto[13]));
        	
        	configuracionBandeja.setSector(new ListadoDetalle());
        	configuracionBandeja.getSector().setIdListadoDetalle(obtenerLong(dto[14]));
        	configuracionBandeja.getSector().setIdListado(obtenerLong(dto[15]));
        	configuracionBandeja.getSector().setCodigo(obtenerString(dto[16]));
        	configuracionBandeja.getSector().setNombre(obtenerString(dto[17]));
        	configuracionBandeja.getSector().setDescripcion(obtenerString(dto[18]));
        	configuracionBandeja.getSector().setValor(obtenerString(dto[19]));
        	
        	configuracionBandeja.setSubsector(new ListadoDetalle());
        	configuracionBandeja.getSubsector().setIdListadoDetalle(obtenerLong(dto[20]));
        	configuracionBandeja.getSubsector().setIdListado(obtenerLong(dto[21]));
        	configuracionBandeja.getSubsector().setCodigo(obtenerString(dto[22]));
        	configuracionBandeja.getSubsector().setNombre(obtenerString(dto[23]));
        	configuracionBandeja.getSubsector().setDescripcion(obtenerString(dto[24]));
        	configuracionBandeja.getSubsector().setValor(obtenerString(dto[25]));
        	
        	configuracionBandeja.setActividad(new ListadoDetalle());
        	configuracionBandeja.getActividad().setIdListadoDetalle(obtenerLong(dto[26]));
        	configuracionBandeja.getActividad().setIdListado(obtenerLong(dto[27]));
        	configuracionBandeja.getActividad().setCodigo(obtenerString(dto[28]));
        	configuracionBandeja.getActividad().setNombre(obtenerString(dto[29]));
        	configuracionBandeja.getActividad().setDescripcion(obtenerString(dto[30]));
        	configuracionBandeja.getActividad().setValor(obtenerString(dto[31]));
        	
        	configuracionBandeja.setUnidad(new ListadoDetalle());
        	configuracionBandeja.getUnidad().setIdListadoDetalle(obtenerLong(dto[32]));
        	configuracionBandeja.getUnidad().setIdListado(obtenerLong(dto[33]));
        	configuracionBandeja.getUnidad().setCodigo(obtenerString(dto[34]));
        	configuracionBandeja.getUnidad().setNombre(obtenerString(dto[35]));
        	configuracionBandeja.getUnidad().setDescripcion(obtenerString(dto[36]));
        	configuracionBandeja.getUnidad().setValor(obtenerString(dto[37]));
        	
        	configuracionBandeja.setSubCategoria(new ListadoDetalle());
        	configuracionBandeja.getSubCategoria().setIdListadoDetalle(obtenerLong(dto[38]));
        	configuracionBandeja.getSubCategoria().setIdListado(obtenerLong(dto[39]));
        	configuracionBandeja.getSubCategoria().setCodigo(obtenerString(dto[40]));
        	configuracionBandeja.getSubCategoria().setNombre(obtenerString(dto[41]));
        	configuracionBandeja.getSubCategoria().setDescripcion(obtenerString(dto[42]));
        	configuracionBandeja.getSubCategoria().setValor(obtenerString(dto[43]));
        	
        	configuracionBandeja.setPerfil(new ListadoDetalle());
        	configuracionBandeja.getPerfil().setIdListadoDetalle(obtenerLong(dto[44]));
        	configuracionBandeja.getPerfil().setIdListado(obtenerLong(dto[45]));
        	configuracionBandeja.getPerfil().setCodigo(obtenerString(dto[46]));
        	configuracionBandeja.getPerfil().setNombre(obtenerString(dto[47]));
        	configuracionBandeja.getPerfil().setDescripcion(obtenerString(dto[48]));
        	configuracionBandeja.getPerfil().setValor(obtenerString(dto[49]));
        	
        	configuracionBandeja.setNombreRol(obtenerString(dto[50]));
        	
        	listaConfBandeja.add(configuracionBandeja);
        	
        }
        
		return listaConfBandeja;
	}
	
	@Override
	public List<ConfiguracionBandeja> listarConfiguraciones(Long idUsuario,Long idPerfil, Long idRol, int offset, int pageSize) {
		
		Query query = null;
		
		String consulta = "SELECT CONF.ID_CONF_BANDEJA,\r\n" + 
				"       CONF.ES_CONF_BANDEJA,\r\n" + 
				"       USU.ID_USUARIO,\r\n" + 
				"       USU.NO_USUARIO,\r\n" + 
				"       USU.DE_USUARIO,\r\n" + 
				"       USU.ES_USUARIO,\r\n" + 
				"       USU.DE_CORREO,\r\n" + 
				"       USU.NU_TELEFONO,\r\n" + 
				"       TCONF.ID_LISTADO_DETALLE AS ID_LISTADO_DETALLE_1,\r\n" + 
				"       TCONF.ID_LISTADO AS ID_LISTADO_1,\r\n" + 
				"       TCONF.CO_LISTADO_DETALLE AS CO_LISTADO_DETALLE_1,\r\n" + 
				"       TCONF.NO_LISTADO_DETALLE AS NO_LISTADO_DETALLE_1,\r\n" + 
				"       TCONF.DE_LISTADO_DETALLE AS DE_LISTADO_DETALLE_1,\r\n" + 
				"       TCONF.DE_VALOR AS DE_VALOR_1,\r\n" + 
				"       SEC.ID_LISTADO_DETALLE AS ID_LISTADO_DETALLE_2,\r\n" + 
				"       SEC.ID_LISTADO AS ID_LISTADO_2,\r\n" + 
				"       SEC.CO_LISTADO_DETALLE AS CO_LISTADO_DETALLE_2,\r\n" + 
				"       SEC.NO_LISTADO_DETALLE AS NO_LISTADO_DETALLE_2,\r\n" + 
				"       SEC.DE_LISTADO_DETALLE AS DE_LISTADO_DETALLE_2,\r\n" + 
				"       SEC.DE_VALOR AS DE_VALOR_2,\r\n" + 
				"       SBSEC.ID_LISTADO_DETALLE AS ID_LISTADO_DETALLE_3,\r\n" + 
				"       SBSEC.ID_LISTADO AS ID_LISTADO_3,\r\n" + 
				"       SBSEC.CO_LISTADO_DETALLE AS CO_LISTADO_DETALLE_3,\r\n" + 
				"       SBSEC.NO_LISTADO_DETALLE AS NO_LISTADO_DETALLE_3,\r\n" + 
				"       SBSEC.DE_LISTADO_DETALLE AS DE_LISTADO_DETALLE_3,\r\n" + 
				"       SBSEC.DE_VALOR AS DE_VALOR_3,\r\n" + 
				"       ACT.ID_LISTADO_DETALLE AS ID_LISTADO_DETALLE_4,\r\n" + 
				"       ACT.ID_LISTADO AS ID_LISTADO_4,\r\n" + 
				"       ACT.CO_LISTADO_DETALLE AS CO_LISTADO_DETALLE_4,\r\n" + 
				"       ACT.NO_LISTADO_DETALLE AS NO_LISTADO_DETALLE_4,\r\n" + 
				"       ACT.DE_LISTADO_DETALLE AS DE_LISTADO_DETALLE_4,\r\n" + 
				"       ACT.DE_VALOR AS DE_VALOR_4,\r\n" + 
				"       UNI.ID_LISTADO_DETALLE AS ID_LISTADO_DETALLE_5,\r\n" + 
				"       UNI.ID_LISTADO AS ID_LISTADO_5,\r\n" + 
				"       UNI.CO_LISTADO_DETALLE AS CO_LISTADO_DETALLE_5,\r\n" + 
				"       UNI.NO_LISTADO_DETALLE AS NO_LISTADO_DETALLE_5,\r\n" + 
				"       UNI.DE_LISTADO_DETALLE AS DE_LISTADO_DETALLE_5,\r\n" + 
				"       UNI.DE_VALOR AS DE_VALOR_5,\r\n" + 
				"       SBCAT.ID_LISTADO_DETALLE AS ID_LISTADO_DETALLE_6,\r\n" + 
				"       SBCAT.ID_LISTADO AS ID_LISTADO_6,\r\n" + 
				"       SBCAT.CO_LISTADO_DETALLE AS CO_LISTADO_DETALLE_6,\r\n" + 
				"       SBCAT.NO_LISTADO_DETALLE AS NO_LISTADO_DETALLE_6,\r\n" + 
				"       SBCAT.DE_LISTADO_DETALLE AS DE_LISTADO_DETALLE_6,\r\n" + 
				"       SBCAT.DE_VALOR AS DE_VALOR_6,\r\n" + 
				"       PER.ID_LISTADO_DETALLE AS ID_LISTADO_DETALLE_7,\r\n" + 
				"       PER.ID_LISTADO AS ID_LISTADO_7,\r\n" + 
				"       PER.CO_LISTADO_DETALLE AS CO_LISTADO_DETALLE_7,\r\n" + 
				"       PER.NO_LISTADO_DETALLE AS NO_LISTADO_DETALLE_7,\r\n" + 
				"       PER.DE_LISTADO_DETALLE AS DE_LISTADO_DETALLE_7,\r\n" + 
				"       PER.DE_VALOR AS DE_VALOR_7,\r\n" + 
				"       RLCON.NO_ROL\r\n" +
				"  FROM SICOES_TM_CONF_BANDEJA CONF\r\n" + 
				"       INNER JOIN SICOES_TM_USUARIO USU ON (CONF.ID_USUARIO = USU.ID_USUARIO)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE TCONF\r\n" + 
				"          ON (CONF.ID_TIPO_CONF = TCONF.ID_LISTADO_DETALLE)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE SEC\r\n" + 
				"          ON (CONF.ID_SECTOR_LD = SEC.ID_LISTADO_DETALLE)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE SBSEC\r\n" + 
				"          ON (CONF.ID_SUBSECTOR_LD = SBSEC.ID_LISTADO_DETALLE)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE ACT\r\n" + 
				"          ON (CONF.ID_ACTIVIDAD_LD = ACT.ID_LISTADO_DETALLE)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE UNI\r\n" + 
				"          ON (CONF.ID_UNIDAD_LD = UNI.ID_LISTADO_DETALLE)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE SBCAT\r\n" + 
				"          ON (CONF.ID_SUBCATEGORIA_LD = SBCAT.ID_LISTADO_DETALLE)\r\n" + 
				"       INNER JOIN SICOES_TM_LISTADO_DETALLE PER\r\n" + 
				"          ON (CONF.ID_PERFIL_LD = PER.ID_LISTADO_DETALLE)\r\n" + 
				"INNER JOIN\r\n" + 
				"         (SELECT RL.ID_ROL,RL.NO_ROL,X.ID_USUARIO,X.ID_CONF_BANDEJA\r\n" + 
				"            FROM SICOES_TM_CONF_BANDEJA X\r\n" + 
				"                 INNER JOIN SICOES_TX_USUARIO_ROL_CONF URC\r\n" + 
				"                    ON     X.ID_CONF_BANDEJA = URC.ID_CONF_BANDEJA\r\n" + 
				"                       AND URC.ES_USUARIO_ROL_CONF = '1'\r\n" + 
				"                 INNER JOIN SICOES_TM_ROL RL ON URC.ID_USUARIO_ROL = RL.ID_ROL\r\n" + 
				"           WHERE X.ES_CONF_BANDEJA = '1') RLCON\r\n" + 
				"            ON (    CONF.ID_CONF_BANDEJA = RLCON.ID_CONF_BANDEJA\r\n" + 
				"                AND USU.ID_USUARIO = RLCON.ID_USUARIO) "+
				" WHERE CONF.ES_CONF_BANDEJA = '1' \r\n" + 
				"       AND USU.ID_USUARIO='" + idUsuario + "'\r\n" + 
				"       AND RLCON.ID_ROL='" + idRol + "' ";

				if (idPerfil!=null) {
					consulta += " AND PER.ID_LISTADO_DETALLE='" + idPerfil + "' ";
				}
				
				consulta += " ORDER BY USU.ID_USUARIO DESC, CONF.ID_CONF_BANDEJA DESC ";
				
				int off = offset * pageSize;
				
				consulta += " OFFSET '"+off+"' ROWS FETCH NEXT '"+pageSize+"' ROWS ONLY ";
				
				
		
		query = entityManager.createNativeQuery(consulta);
		
		System.out.println(query);
		
        
		List<Object[]> lista = query.getResultList();
        
        List<ConfiguracionBandeja> listaConfBandeja = new ArrayList<>();
        ConfiguracionBandeja configuracionBandeja = new ConfiguracionBandeja();
        for (Object[] dto: lista) {
        	configuracionBandeja = new ConfiguracionBandeja();
        	configuracionBandeja.setIdConfiguracionBandeja(obtenerLong(dto[0]));
        	configuracionBandeja.setEstadoConfiguracion(obtenerString(dto[1]));
        	configuracionBandeja.setUsuario(new Usuario());
        	configuracionBandeja.getUsuario().setIdUsuario(obtenerLong(dto[2]));
        	configuracionBandeja.getUsuario().setNombreUsuario(obtenerString(dto[3]));
        	configuracionBandeja.getUsuario().setUsuario(obtenerString(dto[4]));
        	configuracionBandeja.getUsuario().setEstadoUsuario(obtenerString(dto[5]));
        	configuracionBandeja.getUsuario().setCorreo(obtenerString(dto[6]));
        	configuracionBandeja.getUsuario().setTelefono(obtenerLong(dto[7]));
        	
        	configuracionBandeja.setTipoConfiguracion(new ListadoDetalle());
        	configuracionBandeja.getTipoConfiguracion().setIdListadoDetalle(obtenerLong(dto[8]));
        	configuracionBandeja.getTipoConfiguracion().setIdListado(obtenerLong(dto[9]));
        	configuracionBandeja.getTipoConfiguracion().setCodigo(obtenerString(dto[10]));
        	configuracionBandeja.getTipoConfiguracion().setNombre(obtenerString(dto[11]));
        	configuracionBandeja.getTipoConfiguracion().setDescripcion(obtenerString(dto[12]));
        	configuracionBandeja.getTipoConfiguracion().setValor(obtenerString(dto[13]));
        	
        	configuracionBandeja.setSector(new ListadoDetalle());
        	configuracionBandeja.getSector().setIdListadoDetalle(obtenerLong(dto[14]));
        	configuracionBandeja.getSector().setIdListado(obtenerLong(dto[15]));
        	configuracionBandeja.getSector().setCodigo(obtenerString(dto[16]));
        	configuracionBandeja.getSector().setNombre(obtenerString(dto[17]));
        	configuracionBandeja.getSector().setDescripcion(obtenerString(dto[18]));
        	configuracionBandeja.getSector().setValor(obtenerString(dto[19]));
        	
        	configuracionBandeja.setSubsector(new ListadoDetalle());
        	configuracionBandeja.getSubsector().setIdListadoDetalle(obtenerLong(dto[20]));
        	configuracionBandeja.getSubsector().setIdListado(obtenerLong(dto[21]));
        	configuracionBandeja.getSubsector().setCodigo(obtenerString(dto[22]));
        	configuracionBandeja.getSubsector().setNombre(obtenerString(dto[23]));
        	configuracionBandeja.getSubsector().setDescripcion(obtenerString(dto[24]));
        	configuracionBandeja.getSubsector().setValor(obtenerString(dto[25]));
        	
        	configuracionBandeja.setActividad(new ListadoDetalle());
        	configuracionBandeja.getActividad().setIdListadoDetalle(obtenerLong(dto[26]));
        	configuracionBandeja.getActividad().setIdListado(obtenerLong(dto[27]));
        	configuracionBandeja.getActividad().setCodigo(obtenerString(dto[28]));
        	configuracionBandeja.getActividad().setNombre(obtenerString(dto[29]));
        	configuracionBandeja.getActividad().setDescripcion(obtenerString(dto[30]));
        	configuracionBandeja.getActividad().setValor(obtenerString(dto[31]));
        	
        	configuracionBandeja.setUnidad(new ListadoDetalle());
        	configuracionBandeja.getUnidad().setIdListadoDetalle(obtenerLong(dto[32]));
        	configuracionBandeja.getUnidad().setIdListado(obtenerLong(dto[33]));
        	configuracionBandeja.getUnidad().setCodigo(obtenerString(dto[34]));
        	configuracionBandeja.getUnidad().setNombre(obtenerString(dto[35]));
        	configuracionBandeja.getUnidad().setDescripcion(obtenerString(dto[36]));
        	configuracionBandeja.getUnidad().setValor(obtenerString(dto[37]));
        	
        	configuracionBandeja.setSubCategoria(new ListadoDetalle());
        	configuracionBandeja.getSubCategoria().setIdListadoDetalle(obtenerLong(dto[38]));
        	configuracionBandeja.getSubCategoria().setIdListado(obtenerLong(dto[39]));
        	configuracionBandeja.getSubCategoria().setCodigo(obtenerString(dto[40]));
        	configuracionBandeja.getSubCategoria().setNombre(obtenerString(dto[41]));
        	configuracionBandeja.getSubCategoria().setDescripcion(obtenerString(dto[42]));
        	configuracionBandeja.getSubCategoria().setValor(obtenerString(dto[43]));
        	
        	configuracionBandeja.setPerfil(new ListadoDetalle());
        	configuracionBandeja.getPerfil().setIdListadoDetalle(obtenerLong(dto[44]));
        	configuracionBandeja.getPerfil().setIdListado(obtenerLong(dto[45]));
        	configuracionBandeja.getPerfil().setCodigo(obtenerString(dto[46]));
        	configuracionBandeja.getPerfil().setNombre(obtenerString(dto[47]));
        	configuracionBandeja.getPerfil().setDescripcion(obtenerString(dto[48]));
        	configuracionBandeja.getPerfil().setValor(obtenerString(dto[49]));
        	
        	configuracionBandeja.setNombreRol(obtenerString(dto[50]));
        	
        	listaConfBandeja.add(configuracionBandeja);
        	
        }
        
		return listaConfBandeja;
	}
	
	@Override
	public BigDecimal obtenerTotalRegistrosConfiguraciones(Long idUsuario,Long idPerfil, Long idRol) {
		
		Query query = null;
		
		String consulta = "SELECT COUNT(1) " + 
				"  FROM SICOES_TM_CONF_BANDEJA CONF\r\n" + 
				"       INNER JOIN SICOES_TM_USUARIO USU ON (CONF.ID_USUARIO = USU.ID_USUARIO)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE TCONF\r\n" + 
				"          ON (CONF.ID_TIPO_CONF = TCONF.ID_LISTADO_DETALLE)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE SEC\r\n" + 
				"          ON (CONF.ID_SECTOR_LD = SEC.ID_LISTADO_DETALLE)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE SBSEC\r\n" + 
				"          ON (CONF.ID_SUBSECTOR_LD = SBSEC.ID_LISTADO_DETALLE)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE ACT\r\n" + 
				"          ON (CONF.ID_ACTIVIDAD_LD = ACT.ID_LISTADO_DETALLE)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE UNI\r\n" + 
				"          ON (CONF.ID_UNIDAD_LD = UNI.ID_LISTADO_DETALLE)\r\n" + 
				"       LEFT JOIN SICOES_TM_LISTADO_DETALLE SBCAT\r\n" + 
				"          ON (CONF.ID_SUBCATEGORIA_LD = SBCAT.ID_LISTADO_DETALLE)\r\n" + 
				"       INNER JOIN SICOES_TM_LISTADO_DETALLE PER\r\n" + 
				"          ON (CONF.ID_PERFIL_LD = PER.ID_LISTADO_DETALLE)\r\n" + 
				"INNER JOIN\r\n" + 
				"         (SELECT RL.ID_ROL,RL.NO_ROL,X.ID_USUARIO,X.ID_CONF_BANDEJA\r\n" + 
				"            FROM SICOES_TM_CONF_BANDEJA X\r\n" + 
				"                 INNER JOIN SICOES_TX_USUARIO_ROL_CONF URC\r\n" + 
				"                    ON     X.ID_CONF_BANDEJA = URC.ID_CONF_BANDEJA\r\n" + 
				"                       AND URC.ES_USUARIO_ROL_CONF = '1'\r\n" + 
				"                 INNER JOIN SICOES_TM_ROL RL ON URC.ID_USUARIO_ROL = RL.ID_ROL\r\n" + 
				"           WHERE X.ES_CONF_BANDEJA = '1') RLCON\r\n" + 
				"            ON (    CONF.ID_CONF_BANDEJA = RLCON.ID_CONF_BANDEJA\r\n" + 
				"                AND USU.ID_USUARIO = RLCON.ID_USUARIO) "+
				" WHERE CONF.ES_CONF_BANDEJA = '1' \r\n" + 
				"       AND USU.ID_USUARIO='" + idUsuario + "'\r\n" + 
				"       AND RLCON.ID_ROL='" + idRol + "' ";

				if (idPerfil!=null) {
					consulta += " AND PER.ID_LISTADO_DETALLE='" + idPerfil + "' ";
				}
				
		query = entityManager.createNativeQuery(consulta);
		
		Object resultadoTotal = query.getSingleResult();

		return resultadoTotal!=null?(BigDecimal)resultadoTotal:new BigDecimal("0");
	}
	
	@Override
	public List<ConfiguracionBandeja> listarConfiguracionesReasignadas(Long idUsuario) {
		
		Query queryRes = null;
		
		
		
		if(idUsuario!=null) {
			Query query = entityManager.createNativeQuery(
					"SELECT                                                             "+
		    				"DISTINCT(CONF.ID_CONF_BANDEJA),                                             "+
		    				"CONF.ES_CONF_BANDEJA,                                             "+
		    				"USU.ID_USUARIO,                                             "+
		    				"USU.NO_USUARIO,                                             "+
		    				"USU.DE_USUARIO,                                             "+
		    				"USU.ES_USUARIO,                                             "+
		    				"USU.DE_CORREO,                                             "+
		    				"USU.NU_TELEFONO,                                             "+
		    				"TCONF.ID_LISTADO_DETALLE AS ID_LISTADO_DETALLE_1,										 "+
		    				"TCONF.ID_LISTADO AS ID_LISTADO_1,										 "+
		    				"TCONF.CO_LISTADO_DETALLE AS CO_LISTADO_DETALLE_1,										 "+
		    				"TCONF.NO_LISTADO_DETALLE AS NO_LISTADO_DETALLE_1,										 "+
		    				"TCONF.DE_LISTADO_DETALLE AS DE_LISTADO_DETALLE_1,										 "+
		    				"TCONF.DE_VALOR AS DE_VALOR_1,										 "+
		    				"SEC.ID_LISTADO_DETALLE AS ID_LISTADO_DETALLE_2,										 "+
		    				"SEC.ID_LISTADO AS ID_LISTADO_2,										 "+
		    				"SEC.CO_LISTADO_DETALLE AS CO_LISTADO_DETALLE_2,										 "+
		    				"SEC.NO_LISTADO_DETALLE AS NO_LISTADO_DETALLE_2,										 "+
		    				"SEC.DE_LISTADO_DETALLE AS DE_LISTADO_DETALLE_2,										 "+
		    				"SEC.DE_VALOR AS DE_VALOR_2,										 "+
							"SBSEC.ID_LISTADO_DETALLE AS ID_LISTADO_DETALLE_3,										 "+
							"SBSEC.ID_LISTADO AS ID_LISTADO_3,										 "+
							"SBSEC.CO_LISTADO_DETALLE AS CO_LISTADO_DETALLE_3,										 "+
							"SBSEC.NO_LISTADO_DETALLE AS NO_LISTADO_DETALLE_3,										 "+
							"SBSEC.DE_LISTADO_DETALLE AS DE_LISTADO_DETALLE_3,										 "+
							"SBSEC.DE_VALOR AS DE_VALOR_3,										 "+
							"ACT.ID_LISTADO_DETALLE AS ID_LISTADO_DETALLE_4,										 "+
							"ACT.ID_LISTADO AS ID_LISTADO_4,										 "+
							"ACT.CO_LISTADO_DETALLE AS CO_LISTADO_DETALLE_4,										 "+
							"ACT.NO_LISTADO_DETALLE AS NO_LISTADO_DETALLE_4,										 "+
							"ACT.DE_LISTADO_DETALLE AS DE_LISTADO_DETALLE_4,										 "+
							"ACT.DE_VALOR AS DE_VALOR_4,										 "+
							"UNI.ID_LISTADO_DETALLE AS ID_LISTADO_DETALLE_5,										 "+
							"UNI.ID_LISTADO AS ID_LISTADO_5,										 "+
							"UNI.CO_LISTADO_DETALLE AS CO_LISTADO_DETALLE_5,										 "+
							"UNI.NO_LISTADO_DETALLE AS NO_LISTADO_DETALLE_5,										 "+
							"UNI.DE_LISTADO_DETALLE AS DE_LISTADO_DETALLE_5,										 "+
							"UNI.DE_VALOR AS DE_VALOR_5,										 "+
							"SBCAT.ID_LISTADO_DETALLE AS ID_LISTADO_DETALLE_6,										 "+
							"SBCAT.ID_LISTADO AS ID_LISTADO_6,										 "+
							"SBCAT.CO_LISTADO_DETALLE AS CO_LISTADO_DETALLE_6,										 "+
							"SBCAT.NO_LISTADO_DETALLE AS NO_LISTADO_DETALLE_6,										 "+
							"SBCAT.DE_LISTADO_DETALLE AS DE_LISTADO_DETALLE_6,										 "+
							"SBCAT.DE_VALOR AS DE_VALOR_6,										 "+
							"PER.ID_LISTADO_DETALLE AS ID_LISTADO_DETALLE_7,										 "+
							"PER.ID_LISTADO AS ID_LISTADO_7,										 "+
							"PER.CO_LISTADO_DETALLE AS CO_LISTADO_DETALLE_7,										 "+
							"PER.NO_LISTADO_DETALLE AS NO_LISTADO_DETALLE_7,										 "+
							"PER.DE_LISTADO_DETALLE AS DE_LISTADO_DETALLE_7,										 "+
							"PER.DE_VALOR AS DE_VALOR_7,										 "+
							"TX.ID_USUARIO_REASIGNACION,								  "+
							"CASE WHEN TX.ID_USUARIO_REASIGNACION IS NOT NULL THEN NVL(TO_CHAR(TX.FE_INICIO,'DD/MM/YYYY'),'') || ' - ' || NVL(TO_CHAR(TX.FE_FIN,'DD/MM/YYYY'),'') ELSE '' END AS PERIODO,								 				  "+
							"USU2.ID_USUARIO AS ID_USUARIO_2,                                             "+
							"USU2.NO_USUARIO AS NO_USUARIO_2,                                             "+
							"USU2.DE_USUARIO AS DE_USUARIO_2,                                             "+
							"USU2.ES_USUARIO AS ES_USUARIO_2                                             "+
		    				"FROM                                                               "+
		    				"SICOES_TM_CONF_BANDEJA CONF   "+
		    				"INNER JOIN SICOES_TM_USUARIO USU ON (CONF.ID_USUARIO = USU.ID_USUARIO) "+
		    				"INNER JOIN SICOES_TR_ASIGNACION ASIG ON (USU.ID_USUARIO = ASIG.ID_USUARIO OR USU.ID_USUARIO = ASIG.ID_USUARIO_ORIGEN) "+
		    				"INNER JOIN SICOES_TR_SOLICITUD SOL ON (ASIG.ID_SOLICITUD = SOL.ID_SOLICITUD AND SOL.ID_ESTADO_LD IN (1,70,74,75,538,539)) "+
		    				"INNER JOIN SICOES_TR_OTRO_REQUISITO REQ ON (SOL.ID_SOLICITUD = REQ.ID_SOLICITUD AND CONF.ID_PERFIL_LD = REQ.ID_PERFIL_LD AND REQ.ID_FINALIZADO_LD IS NULL) "+
		    				"LEFT JOIN SICOES_TM_LISTADO_DETALLE TCONF ON (CONF.ID_TIPO_CONF = TCONF.ID_LISTADO_DETALLE) "+
		    				"LEFT JOIN SICOES_TM_LISTADO_DETALLE SEC ON (CONF.ID_SECTOR_LD = SEC.ID_LISTADO_DETALLE) "+
		    				"LEFT JOIN SICOES_TM_LISTADO_DETALLE SBSEC ON (CONF.ID_SUBSECTOR_LD = SBSEC.ID_LISTADO_DETALLE) "+
		    				"LEFT JOIN SICOES_TM_LISTADO_DETALLE ACT ON (CONF.ID_ACTIVIDAD_LD = ACT.ID_LISTADO_DETALLE) "+
		    				"LEFT JOIN SICOES_TM_LISTADO_DETALLE UNI ON (CONF.ID_UNIDAD_LD = UNI.ID_LISTADO_DETALLE) "+
		    				"LEFT JOIN SICOES_TM_LISTADO_DETALLE SBCAT ON (CONF.ID_SUBCATEGORIA_LD = SBCAT.ID_LISTADO_DETALLE) "+
		    				"INNER JOIN SICOES_TM_LISTADO_DETALLE PER ON (CONF.ID_PERFIL_LD = PER.ID_LISTADO_DETALLE) "+
		    				"LEFT JOIN  SICOES_TX_USUARIO_REASIGNACION TX ON (CONF.ID_CONF_BANDEJA = TX.ID_CONF_BANDEJA AND TX.ES_USUARIO_REASIGNACION = '1') "+
		    				"LEFT JOIN SICOES_TM_USUARIO USU2 ON (TX.ID_USUARIO = USU2.ID_USUARIO) "+
		    				"WHERE 1=1                                                              "+
		    				" AND CONF.ES_CONF_BANDEJA = '1' AND USU.ID_USUARIO='" + idUsuario + "' "+
		    				" ORDER BY CONF.ID_CONF_BANDEJA ASC "
	        		);
			System.out.println(query);
			queryRes=query;
		}
        
        List<Object[]> lista = queryRes.getResultList();
        
        List<ConfiguracionBandeja> listaConfBandeja = new ArrayList<>();
        ConfiguracionBandeja configuracionBandeja = new ConfiguracionBandeja();
        for (Object[] dto: lista) {
        	configuracionBandeja = new ConfiguracionBandeja();
        	configuracionBandeja.setIdConfiguracionBandeja(obtenerLong(dto[0]));
        	configuracionBandeja.setEstadoConfiguracion(obtenerString(dto[1]));
        	configuracionBandeja.setUsuario(new Usuario());
        	configuracionBandeja.getUsuario().setIdUsuario(obtenerLong(dto[2]));
        	configuracionBandeja.getUsuario().setNombreUsuario(obtenerString(dto[3]));
        	configuracionBandeja.getUsuario().setUsuario(obtenerString(dto[4]));
        	configuracionBandeja.getUsuario().setEstadoUsuario(obtenerString(dto[5]));
        	configuracionBandeja.getUsuario().setCorreo(obtenerString(dto[6]));
        	configuracionBandeja.getUsuario().setTelefono(obtenerLong(dto[7]));
        	
        	configuracionBandeja.setTipoConfiguracion(new ListadoDetalle());
        	configuracionBandeja.getTipoConfiguracion().setIdListadoDetalle(obtenerLong(dto[8]));
        	configuracionBandeja.getTipoConfiguracion().setIdListado(obtenerLong(dto[9]));
        	configuracionBandeja.getTipoConfiguracion().setCodigo(obtenerString(dto[10]));
        	configuracionBandeja.getTipoConfiguracion().setNombre(obtenerString(dto[11]));
        	configuracionBandeja.getTipoConfiguracion().setDescripcion(obtenerString(dto[12]));
        	configuracionBandeja.getTipoConfiguracion().setValor(obtenerString(dto[13]));
        	
        	configuracionBandeja.setSector(new ListadoDetalle());
        	configuracionBandeja.getSector().setIdListadoDetalle(obtenerLong(dto[14]));
        	configuracionBandeja.getSector().setIdListado(obtenerLong(dto[15]));
        	configuracionBandeja.getSector().setCodigo(obtenerString(dto[16]));
        	configuracionBandeja.getSector().setNombre(obtenerString(dto[17]));
        	configuracionBandeja.getSector().setDescripcion(obtenerString(dto[18]));
        	configuracionBandeja.getSector().setValor(obtenerString(dto[19]));
        	
        	configuracionBandeja.setSubsector(new ListadoDetalle());
        	configuracionBandeja.getSubsector().setIdListadoDetalle(obtenerLong(dto[20]));
        	configuracionBandeja.getSubsector().setIdListado(obtenerLong(dto[21]));
        	configuracionBandeja.getSubsector().setCodigo(obtenerString(dto[22]));
        	configuracionBandeja.getSubsector().setNombre(obtenerString(dto[23]));
        	configuracionBandeja.getSubsector().setDescripcion(obtenerString(dto[24]));
        	configuracionBandeja.getSubsector().setValor(obtenerString(dto[25]));
        	
        	configuracionBandeja.setActividad(new ListadoDetalle());
        	configuracionBandeja.getActividad().setIdListadoDetalle(obtenerLong(dto[26]));
        	configuracionBandeja.getActividad().setIdListado(obtenerLong(dto[27]));
        	configuracionBandeja.getActividad().setCodigo(obtenerString(dto[28]));
        	configuracionBandeja.getActividad().setNombre(obtenerString(dto[29]));
        	configuracionBandeja.getActividad().setDescripcion(obtenerString(dto[30]));
        	configuracionBandeja.getActividad().setValor(obtenerString(dto[31]));
        	
        	configuracionBandeja.setUnidad(new ListadoDetalle());
        	configuracionBandeja.getUnidad().setIdListadoDetalle(obtenerLong(dto[32]));
        	configuracionBandeja.getUnidad().setIdListado(obtenerLong(dto[33]));
        	configuracionBandeja.getUnidad().setCodigo(obtenerString(dto[34]));
        	configuracionBandeja.getUnidad().setNombre(obtenerString(dto[35]));
        	configuracionBandeja.getUnidad().setDescripcion(obtenerString(dto[36]));
        	configuracionBandeja.getUnidad().setValor(obtenerString(dto[37]));
        	
        	configuracionBandeja.setSubCategoria(new ListadoDetalle());
        	configuracionBandeja.getSubCategoria().setIdListadoDetalle(obtenerLong(dto[38]));
        	configuracionBandeja.getSubCategoria().setIdListado(obtenerLong(dto[39]));
        	configuracionBandeja.getSubCategoria().setCodigo(obtenerString(dto[40]));
        	configuracionBandeja.getSubCategoria().setNombre(obtenerString(dto[41]));
        	configuracionBandeja.getSubCategoria().setDescripcion(obtenerString(dto[42]));
        	configuracionBandeja.getSubCategoria().setValor(obtenerString(dto[43]));
        	
        	configuracionBandeja.setPerfil(new ListadoDetalle());
        	configuracionBandeja.getPerfil().setIdListadoDetalle(obtenerLong(dto[44]));
        	configuracionBandeja.getPerfil().setIdListado(obtenerLong(dto[45]));
        	configuracionBandeja.getPerfil().setCodigo(obtenerString(dto[46]));
        	configuracionBandeja.getPerfil().setNombre(obtenerString(dto[47]));
        	configuracionBandeja.getPerfil().setDescripcion(obtenerString(dto[48]));
        	configuracionBandeja.getPerfil().setValor(obtenerString(dto[49]));
        	
        	configuracionBandeja.setUsuarioReasignacion(new UsuarioReasignacion());
        	configuracionBandeja.getUsuarioReasignacion().setIdUsuarioReasignacion(obtenerLong(dto[50]));
        	configuracionBandeja.getUsuarioReasignacion().setPeriodo(obtenerString(dto[51]));
        	
        	configuracionBandeja.setUsuarioR(new Usuario());
        	configuracionBandeja.getUsuarioR().setIdUsuario(obtenerLong(dto[52]));
        	configuracionBandeja.getUsuarioR().setNombreUsuario(obtenerString(dto[53]));
        	configuracionBandeja.getUsuarioR().setUsuario(obtenerString(dto[54]));
        	configuracionBandeja.getUsuarioR().setEstadoUsuario(obtenerString(dto[55]));
        	
        	listaConfBandeja.add(configuracionBandeja);
        	
        }
        
		return listaConfBandeja;
	}
	
	@Override
	public List<UsuarioReasignacion> listarBandejaHistorialReasignaciones(String nombreUsuario, String fechaInicio, String fechaFin, Long idDivision, int offset, int pageSize) {
		
		Query query = null;
		
		String consulta = "SELECT U.ID_USUARIO,\r\n" + 
				"           U.NO_USUARIO,\r\n" + 
				"           U.DE_USUARIO,\r\n" + 
				"           UR.ID_CONF_BANDEJA,\r\n" + 
				"           U2.NO_USUARIO AS NO_USUARIO_REASIGNADO,\r\n" + 
				"           U2.DE_USUARIO AS DE_USUARIO_REASIGNADO,\r\n" + 
				"           TO_CHAR (UR.FE_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO,\r\n" + 
				"           TO_CHAR (UR.FE_FIN, 'DD/MM/YYYY') AS FECHA_FIN,\r\n" + 
				"           CB.ID_PERFIL_LD,\r\n" + 
				"           PER.DE_VALOR,\r\n" + 
				"           DIV.ID_DIVISION,\r\n" + 
				"           DIV.DE_DIVISION\r\n" + 
				"      FROM SICOES_TM_USUARIO U\r\n" + 
				"           INNER JOIN SICOES_TM_CONF_BANDEJA CB ON U.ID_USUARIO = CB.ID_USUARIO\r\n" + 
				"           INNER JOIN SICOES_TX_PERFIL_DIVISION PDIV\r\n" + 
				"              ON CB.ID_PERFIL_LD = PDIV.ID_PERFIL\r\n" + 
				"           INNER JOIN SICOES_TM_DIVISION DIV\r\n" + 
				"              ON PDIV.ID_DIVISION = DIV.ID_DIVISION\r\n" + 
				"           INNER JOIN SICOES_TM_LISTADO_DETALLE PER\r\n" + 
				"              ON CB.ID_PERFIL_LD = ID_LISTADO_DETALLE\r\n" + 
				"           INNER JOIN SICOES_TX_USUARIO_REASIGNACION UR\r\n" + 
				"              ON     CB.ID_CONF_BANDEJA = UR.ID_CONF_BANDEJA\r\n" + 
				"           INNER JOIN SICOES_TM_USUARIO U2 ON UR.ID_USUARIO = U2.ID_USUARIO WHERE 1=1 ";
		
			if (nombreUsuario!=null && !nombreUsuario.equals("")) {
				consulta +=" AND (U.NO_USUARIO LIKE '%"+nombreUsuario+"%' OR U.DE_USUARIO LIKE '%"+nombreUsuario+"%') ";
			}
		
			if (fechaInicio!=null && !fechaInicio.equals("")) {
				consulta +=" AND UR.FE_INICIO >= TO_DATE('"+fechaInicio+"','DD/MM/YYYY') ";
			}
			if (fechaFin!=null && !fechaFin.equals("")) {
				consulta +=" AND UR.FE_FIN <= TO_DATE('"+fechaFin+"','DD/MM/YYYY') ";
			}
			
			if (idDivision!=null) {
				consulta +=" AND PDIV.ID_DIVISION = '"+idDivision+"' ";
			}
			
			consulta += " ORDER BY U.ID_USUARIO DESC, UR.ID_CONF_BANDEJA DESC ";
			
	        int off = offset * pageSize;
			
			consulta += " OFFSET '"+off+"' ROWS FETCH NEXT '"+pageSize+"' ROWS ONLY ";
			
			
			
			query = entityManager.createNativeQuery(consulta);
			
			System.out.println(consulta);
		
    
        List<Object[]> lista = query.getResultList();
        
        List<UsuarioReasignacion> listaHistorialReasignaciones = new ArrayList<>();
        UsuarioReasignacion usuarioReasignacion = new UsuarioReasignacion();
        for (Object[] dto: lista) {
        	usuarioReasignacion = new UsuarioReasignacion();
        	usuarioReasignacion.setConfiguracionBandeja(new ConfiguracionBandeja());
        	usuarioReasignacion.setUsuario(new Usuario());
        	usuarioReasignacion.getUsuario().setIdUsuario(obtenerLong(dto[0]));
        	usuarioReasignacion.getUsuario().setNombreUsuario(obtenerString(dto[1]));
        	usuarioReasignacion.getUsuario().setUsuario(obtenerString(dto[2]));
        	usuarioReasignacion.getConfiguracionBandeja().setIdConfiguracionBandeja(obtenerLong(dto[3]));
        	//usuarioReasignacion.setIdConfiguracionBandeja(obtenerLong(dto[3]));
        	usuarioReasignacion.setUsuarioReasignacion(new Usuario());
        	usuarioReasignacion.getUsuarioReasignacion().setNombreUsuario(obtenerString(dto[4]));
        	usuarioReasignacion.getUsuarioReasignacion().setUsuario(obtenerString(dto[5]));
        	usuarioReasignacion.setFechaInicioS(obtenerString(dto[6]));
        	usuarioReasignacion.setFechaFinS(obtenerString(dto[7]));
        	usuarioReasignacion.getConfiguracionBandeja().setPerfil(new ListadoDetalle());
        	usuarioReasignacion.getConfiguracionBandeja().getPerfil().setIdListadoDetalle(obtenerLong(dto[8]));
        	usuarioReasignacion.getConfiguracionBandeja().getPerfil().setValor(obtenerString(dto[9]));
        	usuarioReasignacion.setDivision(new Division());
        	usuarioReasignacion.getDivision().setIdDivision(obtenerLong(dto[10]));
        	usuarioReasignacion.getDivision().setDeDivision(obtenerString(dto[11]));
        	listaHistorialReasignaciones.add(usuarioReasignacion);
        }
        
		return listaHistorialReasignaciones;
	} 
	
	
	@Override
	public List<Asignacion> obtenerReasignacionesFinalizadas() {
		
		Query queryRes = null;
		
			Query query = entityManager.createNativeQuery(
					"SELECT                                                             "+
		    				"ASIG.ID_ASIGNACION,                                             "+
		    				"ASIG.ID_SOLICITUD,                                             "+
		    				"ASIG.ID_USUARIO,                                             "+
		    				"ASIG.ID_USUARIO_ORIGEN,                                             "+
		    				"CONF.ID_CONF_BANDEJA											"+
		    				"FROM                                                               "+
		    				"SICOES_TM_USUARIO USU		   "+
		    				"INNER JOIN SICOES_TM_USUARIO_ROL USRO ON (USU.ID_USUARIO = USRO.ID_USUARIO) "+
		    				"INNER JOIN SICOES_TM_ROL ROL ON (USRO.ID_ROL = ROL.ID_ROL AND ROL.CO_ROL IN('04','05')) "+
		    				"INNER JOIN SICOES_TM_CONF_BANDEJA CONF ON (USU.ID_USUARIO = CONF.ID_USUARIO) "+
		    				"INNER JOIN SICOES_TM_LISTADO_DETALLE PER ON (CONF.ID_PERFIL_LD = PER.ID_LISTADO_DETALLE) "+
		    				"INNER JOIN SICOES_TR_ASIGNACION ASIG ON ((USU.ID_USUARIO = ASIG.ID_USUARIO OR USU.ID_USUARIO = ASIG.ID_USUARIO_ORIGEN) AND ID_EVALUACION_LD IS NULL) "+
		    				"INNER JOIN SICOES_TR_SOLICITUD SOL ON (ASIG.ID_SOLICITUD = SOL.ID_SOLICITUD AND SOL.ID_ESTADO_LD IN (1,70,74,75,538,539)) "+
		    				"INNER JOIN SICOES_TR_OTRO_REQUISITO REQ ON (SOL.ID_SOLICITUD = REQ.ID_SOLICITUD AND CONF.ID_PERFIL_LD = REQ.ID_PERFIL_LD) "+
		    				"INNER JOIN  SICOES_TX_USUARIO_REASIGNACION TX ON (CONF.ID_CONF_BANDEJA = TX.ID_CONF_BANDEJA AND TX.ES_USUARIO_REASIGNACION = '1' AND TRUNC(TX.FE_FIN) < TRUNC(SYSDATE)) "+
		    				"INNER JOIN SICOES_TM_USUARIO USU2 ON (TX.ID_USUARIO = USU2.ID_USUARIO) "+
		    				"GROUP BY ASIG.ID_ASIGNACION, "+
		    			    "ASIG.ID_SOLICITUD, "+
		    			    "ASIG.ID_USUARIO, "+
		    			    "ASIG.ID_USUARIO_ORIGEN, "+
		    			    "CONF.ID_CONF_BANDEJA "
	        		);
			System.out.println(query);
			queryRes=query;
		
        
        List<Object[]> lista = queryRes.getResultList();
        
        List<Asignacion> asignaciones = new ArrayList<>();
        Asignacion asignacion = new Asignacion();
        for (Object[] dto: lista) {
        	asignacion = new Asignacion();
        	asignacion.setIdAsignacion(obtenerLong(dto[0]));
        	asignacion.setSolicitud(new Solicitud());
        	asignacion.getSolicitud().setIdSolicitud(obtenerLong(dto[1]));
        	asignacion.setUsuario(new Usuario());
        	asignacion.getUsuario().setIdUsuario(obtenerLong(dto[2]));
        	asignacion.setIdUsuarioOrigen(obtenerLong(dto[3]));
        	asignacion.setConfiguracionBandeja(new ConfiguracionBandeja());
        	asignacion.getConfiguracionBandeja().setIdConfiguracionBandeja(obtenerLong(dto[4]));
        	asignaciones.add(asignacion);
        }
        
		return asignaciones;
	}
	
	@Override
	public List<Usuario> obtenerUsuariosXCodigoRol(String codigoRol, Long idUsuario) {
		
		Query queryRes = null;

		String consulta = "SELECT          USU.ID_USUARIO,\r\n" + 
				"                USU.DE_RUC,\r\n" + 
				"                USU.DE_USUARIO,\r\n" + 
				"                USU.NO_USUARIO,\r\n" + 
				"                USU.DE_CONTRASENIA,\r\n" + 
				"                USU.DE_RAZON_SOCIAL,\r\n" + 
				"                USU.DE_CORREO,\r\n" + 
				"                USU.NU_TELEFONO,\r\n" + 
				"                USU.ES_USUARIO\r\n" + 
				"  FROM SICOES_TM_USUARIO USU\r\n" + 
				"       INNER JOIN SICOES_TM_USUARIO_ROL USR\r\n" + 
				"          ON (USU.ID_USUARIO = USR.ID_USUARIO AND USR.ES_USUARIO_ROL = '1')\r\n" + 
				"       INNER JOIN SICOES_TM_ROL ROL ON (USR.ID_ROL = ROL.ID_ROL) \r\n" + 
				" WHERE USU.ES_USUARIO = '1' ";
		
		if (idUsuario!=null) {
			consulta +=" AND USU.ID_USUARIO != '"+idUsuario+"' ";
		}
		
		if (codigoRol!=null) {
			consulta +=" AND ROL.CO_ROL = '"+codigoRol+"' ";
		}
		
		consulta += " ORDER BY USU.ID_USUARIO ASC ";
		
		queryRes = entityManager.createNativeQuery(consulta);
		
        
        List<Object[]> lista = queryRes.getResultList();
        
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario = new Usuario();
        for (Object[] dto: lista) {
        	usuario = new Usuario();
        	usuario.setIdUsuario(obtenerLong(dto[0]));
        	usuario.setCodigoRuc(obtenerString(dto[1]));
        	usuario.setUsuario(obtenerString(dto[2]));
        	usuario.setNombreUsuario(obtenerString(dto[3]));
        	usuario.setContrasenia(obtenerString(dto[4]));
        	usuario.setRazonSocial(obtenerString(dto[5]));
        	usuario.setCorreo(obtenerString(dto[6]));
        	usuario.setTelefono(obtenerLong(dto[7]));
        	usuario.setEstadoUsuario(obtenerString(dto[8]));
        	usuarios.add(usuario);
        }
        
		return usuarios;
	}
	
	public List<UsuarioRolConfiguracion> obtenerUsuarioRolConfiguraciones(UsuarioRol usuarioRol){
		
		
		Query queryResult = null;
		
		String consulta = "SELECT RCONF.ID_USUARIO_ROL_CONF,\r\n" + 
				"       RCONF.ID_USUARIO_ROL,\r\n" + 
				"       RCONF.ID_CONF_BANDEJA\r\n" + 
				"  FROM SICOES_TX_USUARIO_ROL_CONF RCONF\r\n" + 
				"       INNER JOIN SICOES_TM_CONF_BANDEJA CONF\r\n" + 
				"          ON (    RCONF.ID_CONF_BANDEJA = CONF.ID_CONF_BANDEJA\r\n" + 
				"              AND CONF.ES_CONF_BANDEJA = '1')\r\n" + 
				" WHERE RCONF.ES_USUARIO_ROL_CONF = '1' ";
		
		if (usuarioRol.getUsuario()!=null && usuarioRol.getUsuario().getIdUsuario()!=null) {
			consulta +=" AND CONF.ID_USUARIO = '"+usuarioRol.getUsuario().getIdUsuario()+"' ";
		}
		
		if (usuarioRol.getRol()!=null && usuarioRol.getRol().getIdRol()!=null) {
			consulta +=" AND RCONF.ID_USUARIO_ROL = '"+usuarioRol.getRol().getIdRol()+"' ";
		}
		
		queryResult = entityManager.createNativeQuery(consulta);
		
		List<Object[]> lista = queryResult.getResultList();
      
		List<UsuarioRolConfiguracion> listaURC = new ArrayList<>();
		UsuarioRolConfiguracion usuarioRC = new UsuarioRolConfiguracion();
		 for (Object[] dto: lista) {
			  usuarioRC = new UsuarioRolConfiguracion();
			  usuarioRC.setIdUsuarioRolConfig(obtenerLong(dto[0]));
			  usuarioRC.setIdUsuarioRol(obtenerLong(dto[1]));
			  usuarioRC.setIdConfiguracionBandeja(obtenerLong(dto[2]));
			  listaURC.add(usuarioRC);
		 }
		
		return listaURC;
	}
	
	private String obtenerString(Object o) {
		if(o == null) {
			return null;
		}
		return o + "";
	}
	
	private Long obtenerLong(Object o) {
		if(o == null) {
			return null;
		}
		return Long.valueOf(o.toString());
	}

}
