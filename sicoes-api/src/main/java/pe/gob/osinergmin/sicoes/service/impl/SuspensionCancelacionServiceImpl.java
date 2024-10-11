package pe.gob.osinergmin.sicoes.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.SuspensionCancelacion;
import pe.gob.osinergmin.sicoes.repository.SuspensionCancelacionDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.SupervisoraService;
import pe.gob.osinergmin.sicoes.service.SuspensionCancelacionService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;
import pe.gob.osinergmin.sicoes.util.DatosExportacion;
import pe.gob.osinergmin.sicoes.util.ExcelUtils;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@Service
public class SuspensionCancelacionServiceImpl implements SuspensionCancelacionService {

	Logger logger = LogManager.getLogger(SuspensionCancelacionServiceImpl.class);

	@Autowired
	private ListadoDetalleService listadoDetalleService;
	
	@Autowired
	private SuspensionCancelacionDao suspensionCancelacionDao;
	
	@Autowired
	private SupervisoraService  supervisoraService;

	@Override
	public SuspensionCancelacion obtener(Long id, Contexto contexto) {
		return suspensionCancelacionDao.obtener(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public SuspensionCancelacion guardar(SuspensionCancelacion suspensionCancelacion, Contexto contexto) {
		SuspensionCancelacion suspensionCancelacionBD=null;
		if(DateUtil.esMayorIgual(new Date(),suspensionCancelacion.getFechaInicio())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.SUSPENSION_CANCELACION_FECHA_INICIO_MAYOR);
		}
		Supervisora supervisora=supervisoraService.obtener(suspensionCancelacion.getSupervisora().getIdSupervisora(), contexto);
		if(supervisora.getIdSuspensionCancelacion()!=null&&suspensionCancelacion.getIdSuspensionCancelacion()==null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.SUSPENSION_CANCELACION_TIENE_SUSPENSION_CANCELACION_PENDIENTE);
		}
		if(suspensionCancelacion.getIdSuspensionCancelacion()==null) {
			String codigoTipo=suspensionCancelacion.getTipo().getCodigo();
			suspensionCancelacion.setTipo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_SUSPENSION_CANCELACION.CODIGO,codigoTipo));
			suspensionCancelacion.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUSPENSION_CANCELACION.CODIGO,Constantes.LISTADO.ESTADO_SUSPENSION_CANCELACION.PROGRAMADO));
			suspensionCancelacionBD=suspensionCancelacion;
			
		}else { 
			suspensionCancelacionBD = suspensionCancelacionDao.obtener(suspensionCancelacion.getIdSuspensionCancelacion());	
			suspensionCancelacionBD.setCausal(suspensionCancelacion.getCausal());
			suspensionCancelacion.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUSPENSION_CANCELACION.CODIGO,Constantes.LISTADO.ESTADO_SUSPENSION_CANCELACION.PROGRAMADO));
			suspensionCancelacionBD.setSustento(suspensionCancelacion.getSustento());
			suspensionCancelacionBD.setFechaInicio(suspensionCancelacion.getFechaInicio());
			suspensionCancelacionBD.setFechaFin(suspensionCancelacion.getFechaFin());
		}
		AuditoriaUtil.setAuditoriaRegistro(suspensionCancelacionBD,contexto);
		suspensionCancelacionBD=suspensionCancelacionDao.save(suspensionCancelacionBD);
		supervisoraService.asociarSuspensionCancelacion(suspensionCancelacionBD);
//		cancelarSupender(suspensionCancelacion);
		return suspensionCancelacionBD;
	}

	@Transactional(rollbackFor = Exception.class)
	public void eliminar(Long id, Contexto contexto) {
		SuspensionCancelacion suspensionCancelacion= obtener(id, contexto);
		supervisoraService.limpiarSuspencionCancelacion(suspensionCancelacion.getSupervisora().getIdSupervisora(),contexto);		
		suspensionCancelacionDao.deleteById(id);
	}

	@Override
	public Page<SuspensionCancelacion> buscar(String nroExpediente, Long idTipoPersona, Long idTipoDocumento,
			String ruc, String nombres, Long idEstado, Pageable pageable, Contexto contexto) {
		if("".equals(ruc)) {ruc=null;}
		if("".equals(nombres)||nombres==null) {
			nombres="%";
		}else {
			nombres="%"+nombres+"%";
		}
		
		if("".equals(nroExpediente)||nroExpediente==null) {
			nroExpediente="%";
		}else {
			nroExpediente="%"+nroExpediente+"%";
		}
	
	
			
		
		Page<SuspensionCancelacion> supervisoras =suspensionCancelacionDao.buscar(nroExpediente, idTipoPersona,idTipoDocumento,ruc,nombres,idEstado,pageable);
		return supervisoras;
	}
	
	@Override
	public InputStream generarExport(String nroExpediente, Long idTipoPersona, Long idTipoDocumento, String ruc,
			String nombres, Long idEstado,  Contexto contexto) {
		if("".equals(ruc)) {ruc=null;}
		if("".equals(nombres)||nombres==null) {
			nombres="%";
		}else {
			nombres="%"+nombres+"%";
		}
		
		if("".equals(nroExpediente)||nroExpediente==null) {
			nroExpediente="%";
		}else {
			nroExpediente="%"+nroExpediente+"%";
		}
	
	
			
		
		List<Object[]> result=  suspensionCancelacionDao.listarSupervisoras( nroExpediente,  idTipoPersona,  idTipoDocumento,  ruc,
				 nombres,  idEstado);
		DatosExportacion datosExportacion =new DatosExportacion();
		datosExportacion.setTitulo("REPORTE DE SUSPENSIÓN Y CANCELACIÓN DE EMPRESAS SUPERVISORAS");
		datosExportacion.setFecha(new Date());
		datosExportacion.setUsuario(contexto.getUsuario().getNombreUsuario());
		datosExportacion.setDescuentoRegistros(0L);
		datosExportacion.setFiltros(
				new String[][]{
//					{{"Nombres:",nombres==null?"":nombres},
//					{"Tipo de documento:",descripcionTipoDocumento},
//					{"Numero de Expediente :",numeroExpediente},
//					{"Fecha de registro desde:",fechaRegistroInicio},
//					{"Hasta:",fechaRegistroFin},					
					});
		datosExportacion.setNombreTitulos(new String[] {"Tipo de Persona","Pais","Tipo Documento","Numero de documento","Razon social / Apellidos y nombres","Estado","Sector","Subsector","Actividad","Unidad","Subcategoria","Perfil","Fecha Ingreso","Expediente","Estado","Fecha Inicio Suspensión/Cancelacion","Fecha Fin Suspensión"});
		datosExportacion.setListado(result);
		return ExcelUtils.generarArchivo(datosExportacion);
	}

	@Transactional(rollbackFor = Exception.class)
	public void procesarSupencionCancelacion() {
		logger.info("procesarSupencionCancelacion inicio");
		List<SuspensionCancelacion> listSuspensionCancelacion = suspensionCancelacionDao.obtenerSuspensionCancelacionPendienteProcesar();
		List<SuspensionCancelacion> listSuspensionCancelacionProcesadoParcialmente = suspensionCancelacionDao.obtenerSuspensionCancelacionProcesadoParcialmente();
		if(!listSuspensionCancelacionProcesadoParcialmente.isEmpty()) {
			listSuspensionCancelacion.addAll(listSuspensionCancelacionProcesadoParcialmente);
		}
		for(SuspensionCancelacion suspensionCancelacion:listSuspensionCancelacion) {
			cancelarSupender(suspensionCancelacion);
		}
		logger.info("procesarSupencionCancelacion fin");
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void cancelarSupender(SuspensionCancelacion suspensionCancelacion) {
		if(Constantes.LISTADO.TIPO_SUSPENSION_CANCELACION.CANCELACION.equals(suspensionCancelacion.getTipo().getCodigo())&&
				Constantes.LISTADO.ESTADO_SUSPENSION_CANCELACION.PROGRAMADO.equals(suspensionCancelacion.getEstado().getCodigo())) {
				suspensionCancelacion.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUSPENSION_CANCELACION.CODIGO,Constantes.LISTADO.ESTADO_SUSPENSION_CANCELACION.PROCESADO));
				suspensionCancelacion.setFechaSuspensionCancelacion(new Date());
				supervisoraService.cancelar(suspensionCancelacion.getSupervisora());
			}else if(Constantes.LISTADO.TIPO_SUSPENSION_CANCELACION.SUSPENSION.equals(suspensionCancelacion.getTipo().getCodigo())&&
					Constantes.LISTADO.ESTADO_SUSPENSION_CANCELACION.PROGRAMADO.equals(suspensionCancelacion.getEstado().getCodigo())) {
				suspensionCancelacion.setFechaSuspensionCancelacion(new Date());
				suspensionCancelacion.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUSPENSION_CANCELACION.CODIGO,Constantes.LISTADO.ESTADO_SUSPENSION_CANCELACION.PROCESADO_PARCIALMENTE));
				supervisoraService.supender(suspensionCancelacion.getSupervisora());
			}else if(Constantes.LISTADO.TIPO_SUSPENSION_CANCELACION.SUSPENSION.equals(suspensionCancelacion.getTipo().getCodigo())&&
					Constantes.LISTADO.ESTADO_SUSPENSION_CANCELACION.PROCESADO_PARCIALMENTE.equals(suspensionCancelacion.getEstado().getCodigo())) {				
				suspensionCancelacion.setFechaActivacion(new Date());
				suspensionCancelacion.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUSPENSION_CANCELACION.CODIGO,Constantes.LISTADO.ESTADO_SUSPENSION_CANCELACION.PROCESADO));
				supervisoraService.activar(suspensionCancelacion.getSupervisora());
			}
			suspensionCancelacionDao.save(suspensionCancelacion);	
	}

	

}
