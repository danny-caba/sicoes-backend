package pe.gob.osinergmin.sicoes.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.model.dto.SupervisoraRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.SupervisoraResponseDTO;
import pe.gob.osinergmin.sicoes.repository.SupervisoraDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.SupervisoraPerfilService;
import pe.gob.osinergmin.sicoes.service.SupervisoraRepresentanteService;
import pe.gob.osinergmin.sicoes.service.SupervisoraService;
import pe.gob.osinergmin.sicoes.service.SuspensionCancelacionService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;


@Service
public class SupervisoraServiceImpl implements SupervisoraService{
	
	Logger logger = LogManager.getLogger(SupervisoraServiceImpl.class);

	@Autowired
	private SupervisoraDao supervisoraDao;
	
	@Autowired
	private SupervisoraRepresentanteService supervisoraRepresentanteService;
	
	@Autowired
	private SupervisoraPerfilService supervisoraPerfilService;
	
	@Autowired
	private ListadoDetalleService listadoDetalleService;
	
	
	@Autowired
	private SuspensionCancelacionService suspensionCancelacionService; 
	
	@Value("${path.jasper}")
	private String pathJasper;
	
	@Override
	public Supervisora obtener(Long id, Contexto contexto) {
		Supervisora supervisora= supervisoraDao.obtener(id);
		SupervisoraRepresentante supervisoraRepresentante=supervisoraRepresentanteService.obtenerXIdSupervisora(supervisora.getIdSupervisora(), contexto);
		supervisora.setSupervisoraRepresentante(supervisoraRepresentante);
		List<SupervisoraPerfil> supervisoraPerfiles=supervisoraPerfilService.buscar(supervisora.getIdSupervisora(), contexto);
		supervisora.setSupervisoraPerfiles(supervisoraPerfiles);
		return supervisora;
	}

	@Transactional(rollbackFor = Exception.class)
	public Supervisora guardar(Supervisora supervisora, Contexto contexto) {
		AuditoriaUtil.setAuditoriaRegistro(supervisora,contexto);
		Supervisora supervisoraBD=supervisoraDao.save(supervisora);
		return supervisoraBD;
	}

	@Transactional(rollbackFor = Exception.class)
	public void eliminar(Long id, Contexto contexto) {
		supervisoraDao.deleteById(id);
	}

	@Override
	public Page<Supervisora> buscar(String nroExpediente,Long idTipoPersona,Long idTipoDocumento,String ruc,
			String nombres,
			String perfil,
			String sfechaInicio,
			String sfechaFin,Pageable pageable, Contexto contexto) {
		if("".equals(ruc)) {ruc=null;}
		if("".equals(nombres)||nombres==null) {
			nombres="%";
		}else {
			nombres="%"+nombres+"%";
		}
		if("".equals(perfil)||perfil==null) {
			perfil="%";
		}else {
			perfil="%"+perfil+"%";
		}
		
		if("".equals(nroExpediente)||nroExpediente==null) {
			nroExpediente="%";
		}else {
			nroExpediente="%"+nroExpediente+"%";
		}
	
	
		Date fechaInicio=DateUtil.getFormat(sfechaInicio, "dd/MM/yyyy");
		Date fechaFin=null;
		if(sfechaFin!=null&&!"".equals(sfechaFin)) {
			fechaFin=DateUtil.getFormat(sfechaFin, "dd/MM/yyyy");	
		}else {
			fechaFin=DateUtil.getFormat(sfechaInicio, "dd/MM/yyyy");			
		}
		
		
		Page<Supervisora> supervisoras =supervisoraDao.buscar(nroExpediente, idTipoPersona,idTipoDocumento,ruc,nombres,fechaInicio,fechaFin,pageable);
		for(Supervisora  supervisora:supervisoras.getContent()) {
			if(supervisora.getIdSuspensionCancelacion()!=null) {
				supervisora.setSuspensionCancelacion(suspensionCancelacionService.obtener(supervisora.getIdSuspensionCancelacion(), contexto));
			}
		}
		return supervisoras;
	}
	@Override
	public Page<Supervisora> buscarSuspendidasCanceladas(String nroExpediente,Long idTipoPersona,Long idTipoDocumento,String ruc,
			String nombres,
			String perfil,
			String sfechaInicio,
			String sfechaFin,Pageable pageable, Contexto contexto) {
		if("".equals(ruc)) {ruc=null;}
		if("".equals(nombres)||nombres==null) {
			nombres="%";
		}else {
			nombres="%"+nombres+"%";
		}
		if("".equals(perfil)||perfil==null) {
			perfil="%";
		}else {
			perfil="%"+perfil+"%";
		}
		
		if("".equals(nroExpediente)||nroExpediente==null) {
			nroExpediente="%";
		}else {
			nroExpediente="%"+nroExpediente+"%";
		}
	
	
		Date fechaInicio=DateUtil.getFormat(sfechaInicio, "dd/MM/yyyy");
		Date fechaFin=null;
		if(sfechaFin!=null&&!"".equals(sfechaFin)) {
			fechaFin=DateUtil.getFormat(sfechaFin, "dd/MM/yyyy");	
		}else {
			fechaFin=DateUtil.getFormat(sfechaInicio, "dd/MM/yyyy");			
		}
		
		
		Page<Supervisora> supervisoras =supervisoraDao.buscarSuspendidasCanceladas(nroExpediente, idTipoPersona,idTipoDocumento,ruc,nombres,fechaInicio,fechaFin,pageable);
		
		return supervisoras;
	}
	
	
	
	
	
	
	public JasperReport getJasperCompilado(File path) throws JRException, FileNotFoundException {
		FileInputStream employeeReportStream =new  FileInputStream(path);
		JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
		return jasperReport;
	} 
	
	private void close(InputStream file) {
		try {
			if (file != null)
				file.close();
		} catch (Exception e) {
			logger.error("Error al cerrar el stream del logo", e);
		}
	}
	
	public Archivo generarReporte(String numeroExpediente,Long idTipoEmpresa,String nombreRazonSocial,String numeroDocumento, Contexto contexto)throws Exception {
		List<Supervisora> supervisoras=supervisoraDao.listarSupervisoras(numeroExpediente,idTipoEmpresa,nombreRazonSocial,numeroDocumento);
		File jrxml = new File(pathJasper+"Formato_X.jrxml");
		Map<String, Object> parameters = new HashMap<String, Object>();
		logger.info("SUBREPORT_DIR: {}",pathJasper);
		parameters.put("SUBREPORT_DIR", pathJasper);		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		JasperPrint print = null;
		InputStream appLogo = null;
		InputStream osinermingLogo = null;
		try {
			appLogo = new FileInputStream(pathJasper+"logo-sicoes.png");
			osinermingLogo = new FileInputStream(pathJasper+"logo-osinerming.png");
			parameters.put("P_LOGO_APP", appLogo);
			parameters.put("P_LOGO_OSINERGMIN", osinermingLogo);		
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(supervisoras);
			JasperReport jasperReport=getJasperCompilado(jrxml);
			print = JasperFillManager.fillReport(jasperReport, parameters, ds);
			output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(print, output);

		} catch (Exception e) {
			throw e;
		} finally {
			close(appLogo);
			close(osinermingLogo);
		}

		byte[] bytesSalida = output.toByteArray();
		Archivo archivo=new Archivo();
		archivo.setNombre("Reporte_Supervisoras.pdf");
		archivo.setNombreReal("Reporte_Supervisoras.pdf");
		archivo.setTipo("application/pdf");
		archivo.setContenido(bytesSalida);
		archivo.setTipoArchivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ARCHIVO.CODIGO, Constantes.LISTADO.TIPO_ARCHIVO.SUPERVISORA));
		return archivo;
	}

	@Override
	public Supervisora obtenerXCodigo(Long codigoCliente, Contexto contexto) {
		return supervisoraDao.obtenerXCodigo(codigoCliente);
	}

	@Override
	public Supervisora obtenerPorCodigoUsuario(Long codigoUsuario, Contexto contexto) {
		return supervisoraDao.obtenerPorCodigoUsuario(codigoUsuario);
	}

	@Transactional(rollbackFor = Exception.class)
	public void cancelar(Supervisora supervisora) {
		supervisora.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUPERVISORA.CODIGO, Constantes.LISTADO.ESTADO_SUPERVISORA.CANCELADO));
		supervisora.setFechaSuspensionCancelacion(new Date());
		supervisoraDao.save(supervisora);
		
	}

	@Transactional(rollbackFor = Exception.class)
	public void supender(Supervisora supervisora) {
		supervisora.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUPERVISORA.CODIGO, Constantes.LISTADO.ESTADO_SUPERVISORA.SUSPENDIDO));
		supervisora.setFechaSuspensionCancelacion(new Date());
		supervisoraDao.save(supervisora);
	}

	@Transactional(rollbackFor = Exception.class)
	public void activar(Supervisora supervisora) {
		supervisora.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUPERVISORA.CODIGO, Constantes.LISTADO.ESTADO_SUPERVISORA.VIGENTE));
		supervisora.setIdSuspensionCancelacion(null);
		supervisora.setFechaSuspensionCancelacion(null);
		supervisoraDao.save(supervisora);
		
	}

	@Transactional(rollbackFor = Exception.class)
	public void asociarSuspensionCancelacion(SuspensionCancelacion suspensionCancelacionBD) {
		Supervisora supervisora=supervisoraDao.obtener(suspensionCancelacionBD.getSupervisora().getIdSupervisora());
		supervisora.setIdSuspensionCancelacion(suspensionCancelacionBD.getIdSuspensionCancelacion());
		
		supervisoraDao.save(supervisora);
	}

	@Override
	public Supervisora obtenerSupervisoraXNroDocumento(String numeroDocumento) {
		return supervisoraDao.obtenerSupervisoraXNroDocumento(numeroDocumento);
	}
	
	@Override
	public Supervisora obtenerSupervisoraXRUC(String codigoRuc) {
		return supervisoraDao.obtenerSupervisoraXRUC(codigoRuc);
	}

	@Override
	public Supervisora obtenerSupervisoraPorRucPostorOrJuridica(String codigoRuc) {
		return supervisoraDao.obtenerSupervisoraPorRucPostorOrJuridica(codigoRuc);
	}
	
	@Override
	public Supervisora obtenerSupervisoraXRUCVigente(String codigoRuc) {
		return supervisoraDao.obtenerSupervisoraXRUCVigente(codigoRuc);
	}
	
	

	@Transactional(rollbackFor = Exception.class)
	public void limpiarSuspencionCancelacion(Long idSupervisora, Contexto contexto) {
		Supervisora supervisora=supervisoraDao.obtener(idSupervisora);
		supervisora.setIdSuspensionCancelacion(null);		
		supervisoraDao.save(supervisora);
	}

	@Override
	public List<Supervisora> listarSupervisoras(Long idPerfil,Contexto contexto) {
		String profesional = Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO;
		 List<Supervisora> supervisorasPerfil = new ArrayList<>();
		List<Supervisora> supervisoras = supervisoraDao.listarSupervisoras(profesional);
		
		for(Supervisora supervisora:supervisoras) {
			List<SupervisoraPerfil> perfiles = supervisoraPerfilService.buscar(supervisora.getIdSupervisora(), contexto);
			for(SupervisoraPerfil perfil:perfiles) {
				logger.info("ID_PERFIL :"+idPerfil+" = ID_LISTADO_DETALLE: "+perfil.getPerfil().getIdListadoDetalle());
				if(perfil.getPerfil().getIdListadoDetalle().equals(idPerfil) ) {
					supervisorasPerfil.add(supervisora);
				}
			}
		}
		
		return supervisorasPerfil;
	}

	@Override
	public List<Object[]> listarProfesionales(Long idPerfil) {
		return supervisoraDao.listarProfesionales(idPerfil);
	}

	@Override
	public List<Supervisora> obtenerSupervisoraPNProfesional(String codigoRuc) {
		return supervisoraDao.obtenerSupervisoraPNProfesional(codigoRuc);
	}

	@Override
	public Supervisora obtenerSupervisoraXRUCNoProfesional(String codigoRuc) {
		return supervisoraDao.obtenerSupervisoraXRUCNoProfesional(codigoRuc);
	}

	/**
	 * Busca supervisoras para funcionalidad de autocomplete
	 * Requerimiento 350 - Renovación de Contratos
	 * Transforma las entidades Supervisora en DTOs con la lógica de concatenación de nombres
	 * @param request DTO con el criterio de búsqueda (nombreSupervisora)
	 * @param contexto Contexto de la aplicación
	 * @return Lista de SupervisoraResponseDTO con los datos formateados para autocomplete
	 */
	@Override
	public List<SupervisoraResponseDTO> buscarSupervisorasParaAutocomplete(SupervisoraRequestDTO request, Contexto contexto) {
		List<Supervisora> supervisoras = supervisoraDao.buscarSupervisorasParaAutocomplete(request.getNombreSupervisora());
		
		return supervisoras.stream()
			.limit(10) // Limitar a 10 resultados para autocomplete
			.map(s -> {
				// Lógica de concatenación de nombres según tipo de persona
				String etiqueta;
				if (s.getNombreRazonSocial() != null && !s.getNombreRazonSocial().trim().isEmpty()) {
					// Persona Jurídica
					etiqueta = s.getNombreRazonSocial();
				} else {
					// Persona Natural - concatenar nombres y apellidos
					StringBuilder sb = new StringBuilder();
					if (s.getNombres() != null && !s.getNombres().trim().isEmpty()) {
						sb.append(s.getNombres());
					}
					if (s.getApellidoPaterno() != null && !s.getApellidoPaterno().trim().isEmpty()) {
						if (sb.length() > 0) sb.append(" ");
						sb.append(s.getApellidoPaterno());
					}
					if (s.getApellidoMaterno() != null && !s.getApellidoMaterno().trim().isEmpty()) {
						if (sb.length() > 0) sb.append(" ");
						sb.append(s.getApellidoMaterno());
					}
					etiqueta = sb.toString().trim();
				}
				
				return new SupervisoraResponseDTO(
					s.getIdSupervisora(),
					s.getTipoPersona() != null ? s.getTipoPersona().getIdListadoDetalle() : null,
					s.getTipoPersona() != null ? s.getTipoPersona().getNombre() : null,
					s.getNombreRazonSocial(),
					s.getNombres(),
					s.getApellidoPaterno(),
					s.getApellidoMaterno(),
					etiqueta
				);
			})
			.collect(java.util.stream.Collectors.toList());
	}



}
