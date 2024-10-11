package pe.gob.osinergmin.sicoes.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.PropuestaProfesional;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.SupervisoraDictamen;
import pe.gob.osinergmin.sicoes.model.SupervisoraMovimiento;
import pe.gob.osinergmin.sicoes.model.SupervisoraPerfil;
import pe.gob.osinergmin.sicoes.repository.SupervisoraPerfilDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.SupervisoraMovimientoService;
import pe.gob.osinergmin.sicoes.service.SupervisoraPerfilService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;
import pe.gob.osinergmin.sicoes.util.DatosExportacion;
import pe.gob.osinergmin.sicoes.util.ExcelUtils;
import pe.gob.osinergmin.sicoes.util.PageUtilImpl;


@Service
public class SupervisoraPerfilServiceImpl implements SupervisoraPerfilService{
	

	Logger logger = LogManager.getLogger(SupervisoraPerfilServiceImpl.class);
	
	@Autowired
	private SupervisoraPerfilDao supervisoraDictamenDao;
	
	@Autowired
	private SupervisoraPerfilDao supervisoraPerfilDao;
	
	@Autowired
	private SupervisoraMovimientoService supervisoraMovimientoService;
	
	@Autowired
	private ListadoDetalleService listadoDetalleService;
	
	@Override
	public SupervisoraPerfil obtener(Long id, Contexto contexto) {
		SupervisoraPerfil supervisoraPerfil= supervisoraPerfilDao.obtener(id);
		return supervisoraPerfil;
	}

	@Override
	public SupervisoraPerfil guardar(SupervisoraPerfil supervisoraPerfil, Contexto contexto) {
		
		SupervisoraPerfil supervisoraPerfilBD = null;
		
		if(supervisoraPerfil.getIdSupervisoraPerfil() == null) {
			AuditoriaUtil.setAuditoriaRegistro(supervisoraPerfil,contexto);
			 supervisoraPerfilBD = supervisoraPerfilDao.save(supervisoraPerfil);
		}else {
			supervisoraPerfilBD = supervisoraPerfilDao.getOne(supervisoraPerfil.getIdSupervisoraPerfil());
			supervisoraPerfilBD.setEstado(supervisoraPerfil.getEstado());
			AuditoriaUtil.setAuditoriaRegistro(supervisoraPerfilBD,contexto);
			supervisoraPerfilDao.save(supervisoraPerfilBD);
		}
		
		return supervisoraPerfilBD;
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		supervisoraPerfilDao.deleteById(id);
	}

	@Override
	public Page<SupervisoraPerfil> buscar(String[] codigoTipoPersona,String codigoTipoDocumento,String ruc,
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
			perfil="%"+perfil+"%".toUpperCase();
		}

		Date fechaInicio = DateUtil.getInitDay(sfechaInicio);
		Date fechaFin=DateUtil.getEndDay(sfechaInicio);
		
		Page<SupervisoraPerfil> supervisoras =supervisoraPerfilDao.buscar(codigoTipoPersona,ruc,nombres,perfil,fechaInicio,fechaFin,pageable);
		
		return supervisoras;
	}

	@Override
	public List<SupervisoraPerfil> buscar(Long idSupervisora, Contexto contexto) {
		return supervisoraPerfilDao.buscar(idSupervisora);
	}
	
	@Override
	public InputStream generarExport(String numeroExpediente,Long idTipoEmpresa,String nombreRazonSocial,String numeroDocumento, Contexto contexto) {
		if(numeroExpediente==null) {
			numeroExpediente="%%";
		}else {
			numeroExpediente+="%";
		}
		
		if(nombreRazonSocial==null) {
			nombreRazonSocial="%%";
		}else {
			nombreRazonSocial+="%";
		}
		
		
		
		List<Object[]> result=  supervisoraPerfilDao.listarSupervisoras(numeroExpediente,idTipoEmpresa,nombreRazonSocial,numeroDocumento);
		DatosExportacion datosExportacion =new DatosExportacion();
		datosExportacion.setTitulo("Reporte de Empresas Supervisoras");
		datosExportacion.setFecha(new Date());
		datosExportacion.setUsuario(contexto.getUsuario().getNombreUsuario());
		datosExportacion.setDescuentoRegistros(0L);
//		String descripcionTipoDocumento="";
//		if(tipoDocumento!=null) {
//			ListadoDetalle tipoDocumentoLD=listadoDetalleDao.obtenerPorId(tipoDocumento);
//			descripcionTipoDocumento=tipoDocumentoLD.getNombre();
//		}
		datosExportacion.setFiltros(
				new String[][]{
//					{{"Nombres:",nombres==null?"":nombres},
//					{"Tipo de documento:",descripcionTipoDocumento},
//					{"Numero de Expediente :",numeroExpediente},
//					{"Fecha de registro desde:",fechaRegistroInicio},
//					{"Hasta:",fechaRegistroFin},					
					});
		datosExportacion.setNombreTitulos(new String[] {"Tipo de Persona","Pais","Tipo Documento","Numero de documento","Razon social / Apellidos y nombres","Estado","Sector","Subsector","Actividad","Unidad","Subcategoria","Perfil","Fecha Ingreso","Expediente"});
		datosExportacion.setListado(result);
		return ExcelUtils.generarArchivo(datosExportacion);
	}

	@Override
	public List<SupervisoraPerfil> buscar(Long idSupervisora, Long idSector, Long idSubSector) {
		return supervisoraPerfilDao.buscar(idSupervisora,idSector);
	}

	@Override
	public List<SupervisoraPerfil> buscarBloqueados(Long idSupervisora,Long idSubsector, Contexto contexto) {
		return supervisoraPerfilDao.buscarBloqueados(idSupervisora,idSubsector);
	}

	@Override
	public Page<Supervisora> liberacionPersonal(String codigoRuc, Long idEstado, Long idSector, Long idSubsector,
			String proceso, String item, Pageable pageable, Contexto contexto) {
		
		Page<Supervisora> supervisoras = null;
		
		if(proceso != null || item !=null) {
			 supervisoras = supervisoraPerfilDao.liberacionPersonal(codigoRuc, idSector, idSubsector,idEstado,proceso,item, pageable);
		}else {
			supervisoras= supervisoraPerfilDao.liberacionPersonal(codigoRuc, idSector, idSubsector,idEstado, pageable);
		}
		
		for(Supervisora supervisora:supervisoras.getContent() ) {
			SupervisoraMovimiento movimiento = supervisoraMovimientoService.ultimoMovimientoXProfesional(supervisora.getIdSupervisora(), idSubsector,idEstado,contexto);
			if(movimiento != null) {
				supervisora.setPerfil(supervisoraPerfilDao.obteneUltimoPerfil(supervisora.getIdSupervisora(), idSector, idSubsector));
				supervisora.setMovimiento(movimiento);
			}	
		}
		
		return supervisoras;
	}
}
