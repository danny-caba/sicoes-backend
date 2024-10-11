package pe.gob.osinergmin.sicoes.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.env.Environment;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.Documento;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.repository.ArchivoDao;
import pe.gob.osinergmin.sicoes.repository.DocumentoDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.DocumentoService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.SolicitudService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;
import pe.gob.osinergmin.sicoes.util.PageUtilImpl;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.ValidacionUtil;

@Service
public class DocumentoServiceImpl implements DocumentoService {

	Logger logger = LogManager.getLogger(DocumentoServiceImpl.class);

	@Autowired
	private DocumentoDao documentoDao;

	@Autowired
	private ArchivoService archivoService;

	@Autowired
	private ArchivoDao archivoDao;
	
	@Autowired
	private ListadoDetalleService listadoDetalleService;
	
	@Autowired
	private SolicitudService solicitudService; 
	
	@Autowired
	private Environment env;

	@Override
	public Documento obtener(Long idDocumento, Contexto contexto) {
		Documento doc = documentoDao.obtener(idDocumento);
		List<Archivo> list = archivoService.buscar(null, idDocumento, null, contexto);
		if (list != null && !list.isEmpty()) {
			doc.setArchivo(list.get(0));
		}
		return doc;
	}

	@Override
	public Page<Documento> buscar(Long idSolicitud, Pageable pageable, Contexto contexto) {
		Pageable pageableMaximo = PageRequest.of(0, Integer.parseInt(env.getProperty("maximo.paginas")));
		Page<Documento> documentoTotal = documentoDao.buscar(idSolicitud, pageableMaximo);
		Page<Documento> documentos = documentoDao.buscar(idSolicitud, pageable);
		PageUtilImpl<Documento> page =new PageUtilImpl<Documento>(documentos.getContent(),documentos.getPageable(),documentos.getTotalElements());
		page.setTotalMonto(documentoDao.sumarMontoTotal(idSolicitud));
		if(page.getTotalMonto()==null) {
			calcularExperienciaTotal(page,documentoTotal.getContent());
		}
		for (Documento documento : documentos) {
			List<Archivo> list = archivoService.buscar(null, documento.getIdDocumento(), null, contexto);
			if (list != null && !list.isEmpty()) {
				documento.setArchivo(list.get(0));
			}
		}
		return page;
	}
	
	 private static void calcularExperienciaTotal(PageUtilImpl page,List<Documento> documentos) {
	    	Date fechaMinima=null;
	    	Date fechaMaxima=null;
	    	Date fechaEvaluar=null;
	        int dias=0;
	        if(documentos==null||documentos.isEmpty())return;
	        for(Documento documento:documentos) {
	        	Date fechaInicioAux=documento.getFechaInicio();
	        	Date fechaFinAux=null;
	        	if(documento.getFechaFin()!=null) {
	        		fechaFinAux=documento.getFechaFin();
	        	}else {
	        		fechaFinAux=new Date();
	        	}	        	 
	        	if(fechaMinima==null||DateUtil.esMenorIgual(fechaInicioAux,fechaMinima)) {
	        		fechaMinima= fechaInicioAux;
	        	}
	        	if(fechaMaxima==null||DateUtil.esMayorIgual(fechaFinAux,fechaMaxima)) {
	        		fechaMaxima=fechaFinAux;
	        	}	        	
	        }
	        fechaEvaluar=fechaMinima;
	        while(DateUtil.esMenorIgual(fechaEvaluar,fechaMaxima)) {
	        	for(Documento documento:documentos) {
	        		Date fechaInicioAux=documento.getFechaInicio();
		        	Date fechaFinAux=null;
		        	if(documento.getFechaFin()!=null) {
		        		fechaFinAux=documento.getFechaFin();
		        	}else {
		        		fechaFinAux=new Date();
		        	}
	        		if(DateUtil.esMayorIgual(fechaEvaluar,fechaInicioAux)&&DateUtil.esMenorIgual(fechaEvaluar,fechaFinAux)) {
	        			dias++;
	        			break;
	        		}
	        	}
	        	
	        	fechaEvaluar=DateUtil.sumarDia(fechaEvaluar,1L);
	        }	        
	        int anio=dias/365;
	        int mes=(dias-(anio*365))/30;
	        int dia=(dias-(anio*365)-(mes*30));
	        page.setAnio(anio);
	        page.setMes(mes);
	        page.setDia(dia);
	    }	    
	   
	 
	 public boolean validarJuridicoPostor(ListadoDetalle tipoPersona) {

			boolean isJuridica = Constantes.LISTADO.TIPO_PERSONA.JURIDICA.equals(tipoPersona.getCodigo());
			boolean isPerNatPostor = Constantes.LISTADO.TIPO_PERSONA.PN_POSTOR.equals(tipoPersona.getCodigo());
			boolean isExtrajero = Constantes.LISTADO.TIPO_PERSONA.EXTRANJERO.equals(tipoPersona.getCodigo());

			return isJuridica || isPerNatPostor || isExtrajero;
		}
	 
	 

	@Transactional(rollbackFor = Exception.class)
	public Documento guardar(Documento documento, Contexto contexto) {
		Solicitud sol = solicitudService.obtener(documento.getSolicitud().getSolicitudUuid(),contexto);
		if (!validarJuridicoPostor(sol.getPersona().getTipoPersona())) {
			if (StringUtils.isBlank(documento.getNombreEntidad())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ENTIDAD_NO_ENVIADO);
			}
		}else {
			if (documento.getPais()==null||documento.getPais().getIdListadoDetalle()==null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.PAIS_NO_ENVIADO);
			}
			if (StringUtils.isBlank(documento.getCodigoContrato())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.CODIGO_NO_ENVIADO);
			}
			if (documento.getCuentaConformidad()==null||documento.getCuentaConformidad().getIdListadoDetalle()==null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.CONFORMIDAD_NO_ENVIADO);
			}
			if (documento.getMontoContrato()==null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.MONTO_NO_ENVIADO);
			}
			if (documento.getMontoContratoSol()==null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.MONTO_SOL_NO_ENVIADO);
			}
			/*if(documento.getMontoContrato()<documento.getMontoContratoSol()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.MONTO_FACTURADO_MAYOR);
			}*/
			if (documento.getTipoCambio()==null||documento.getTipoCambio().getIdListadoDetalle()==null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_CAMBIO_NO_ENVIADO);
			}
//			
//			if (tipoCambio.getCodigo().equals(Constantes.LISTADO.TIPO_CAMBIO.DOLARES) && documento.getMontoTipoCambio()==null) {
//				throw new ValidacionException(Constantes.CODIGO_MENSAJE.MONTO_CAMBIO_NO_ENVIADO);
//			}
			Double montoContratado = null;
			ListadoDetalle tipoCambio = listadoDetalleService.obtener(documento.getTipoCambio().getIdListadoDetalle(), contexto);
			logger.info("DOLAR12"+tipoCambio.getCodigo());
			if (Constantes.LISTADO.TIPO_CAMBIO.DOLARES.equals(tipoCambio.getCodigo())) {
				if (documento.getMontoTipoCambio()==null||documento.getMontoTipoCambio() <= 0) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_CAMBIO_NO_ENVIADO);
				}
				montoContratado=documento.getMontoContrato()*documento.getMontoTipoCambio();
				
			}else {
				montoContratado=documento.getMontoContrato();
			}
	
			if(montoContratado<documento.getMontoContratoSol()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.MONTO_FACTURADO_MAYOR);
			}		
		}
		if (documento.getSolicitud() == null || documento.getSolicitud().getSolicitudUuid() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_SOLICITUD_NO_ENVIADO);
		}
		if (documento.getTipoDocumento()==null||documento.getTipoDocumento().getIdListadoDetalle()==null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPODOC_NO_ENVIADO);
		}
		if (StringUtils.isBlank(documento.getDescripcionContrato())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.DESCRIPCION_NO_ENVIADO);
		}
		
		if (StringUtils.isBlank(documento.getNumeroDocumento())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.D_DOC_NO_ENVIADO);
		}
		if (documento.getFechaInicio()==null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.FECHA_INICIO_NO_ENVIADO);
		}
		if ("0".equals(documento.getFlagVigente())) {
			if (documento.getFechaFin()==null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.FECHA_FIN_NO_ENVIADO);
			}
			if(DateUtil.esMayorIgual(documento.getFechaInicio(), documento.getFechaFin())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.FECHA_INICIO_MAYOR);
			}
			if (StringUtils.isBlank(documento.getDuracion())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.DURACION_NO_ENVIADO);
			}
		}
		ValidacionUtil.validarPresentacion(contexto,sol);
		Documento documentoBD=documentoDao.obtener(documento.getIdDocumento());
		if(documentoBD!=null) {	
			documentoBD.setTipoDocumento(listadoDetalleService.obtener(documento.getTipoDocumento().getIdListadoDetalle(), contexto));
			documentoBD.setTipoCambio(documento.getTipoCambio());
			documentoBD.setCuentaConformidad(documento.getCuentaConformidad());
			documentoBD.setTipoIdentificacion(documento.getTipoIdentificacion());
			documentoBD.setPais(documento.getPais());
			documentoBD.setNumeroDocumento(documento.getNumeroDocumento());
			documentoBD.setNombreEntidad(documento.getNombreEntidad());
			documentoBD.setCodigoContrato(documento.getCodigoContrato());
			documentoBD.setDescripcionContrato(documento.getDescripcionContrato());
			documentoBD.setFechaInicio(documento.getFechaInicio());
			documentoBD.setFechaFin(documento.getFechaFin());
			documentoBD.setDuracion(documento.getDuracion());
			documentoBD.setFlagVigente(documento.getFlagVigente());
			documentoBD.setFechaConformidad(documento.getFechaConformidad());
			documentoBD.setMontoContrato(documento.getMontoContrato());
			documentoBD.setMontoTipoCambio(documento.getMontoTipoCambio());
			documentoBD.setMontoContratoSol(documento.getMontoContratoSol());
			if(Constantes.LISTADO.ESTADO_SOLICITUD.OBSERVADO.equals(sol.getEstado().getCodigo())&&
				Constantes.LISTADO.RESULTADO_EVALUACION.OBSERVADO.equals(documentoBD.getEvaluacion().getCodigo())	) {
				documentoBD.setEvaluacion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_EVALUACION.CODIGO,Constantes.LISTADO.RESULTADO_EVALUACION.RESPONDIDO));
			}
		}else {
			documento.getSolicitud().setIdSolicitud(sol.getIdSolicitud());
			documento.setEvaluacion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_EVALUACION.CODIGO,Constantes.LISTADO.RESULTADO_EVALUACION.POR_EVALUAR));
			documentoBD=documento;
		}
		documentoBD.setFlagSiged(Constantes.FLAG.INACTIVO);
		AuditoriaUtil.setAuditoriaRegistro(documento, contexto);		
		documentoBD = documentoDao.save(documentoBD);		
		if (documento.getArchivo() == null||documento.getArchivo().getIdArchivo()==null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_NO_ENVIADO);
		}
		Archivo archivoBD = archivoDao.obtener(documento.getArchivo().getIdArchivo());
		archivoService.asociarArchivo(documentoBD, archivoBD, contexto);
		actualizarNombreArchivo(documentoBD.getSolicitud().getIdSolicitud(),contexto);
		return documentoBD;
	}
	
	private void actualizarNombreArchivo(Long idSolicitud,Contexto contexto) {
		List<Documento> 	documentos=buscar(idSolicitud, contexto);
		int indice=1;
		logger.info("Nombres de Documento");
		for(Documento documento:documentos) {
			List<Archivo> archivosDocumento=archivoService.buscar(null,documento.getIdDocumento(),null, contexto);
			for(Archivo archivoAux:archivosDocumento) {
				archivoAux.setCorrelativo(Long.parseLong(indice+""));
				if(Constantes.LISTADO.RESULTADO_EVALUACION.RESPONDIDO.equals(documento.getEvaluacion().getCodigo())) {
					archivoAux.setVersion(2L);
				}else {
					archivoAux.setVersion(1L);
				}				
				String nombre=archivoAux.getTipoArchivo().getValor()+"_"+documento.getTipoDocumento().getValor()+"_"+StringUtils.leftPad(archivoAux.getCorrelativo()+"",3,'0')+"_v"+archivoAux.getVersion()+".pdf";
				archivoAux.setNombre(nombre);
				logger.info(archivoAux.getCorrelativo()+":"+archivoAux.getNombre());
				archivoService.guardar(archivoAux, contexto);
			}
			indice++;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void eliminar(Long id, Contexto contexto) {
		archivoService.eliminarIdDocumento(id, contexto);
		documentoDao.deleteById(id);

	}

	@Override
	public List<Documento> buscar( Long idSolicitud, Contexto contexto) {
		return documentoDao.buscar(idSolicitud);
	}
	
	@Override
	public List<Documento> buscarPresentacion( Long idSolicitud, Contexto contexto) {		
		return documentoDao.buscarPresentacion(idSolicitud);
	}

	@Override
	public Documento evalular(Documento documento, Contexto contexto) {
		//FIMEX VALIDAR DATOS DE ENTRADA 
		Documento documentoBD = documentoDao.obtener(documento.getIdDocumento());
		documentoBD.setEvaluacion(documento.getEvaluacion());
		documentoBD.setObservacion(documento.getObservacion());
		AuditoriaUtil.setAuditoriaRegistro(documentoBD, contexto);
		documentoDao.save(documentoBD);
		solicitudService.actualizarProceso(documentoBD.getSolicitud(),Constantes.LISTADO.TIPO_EVALUADOR.TECNICO,contexto);
		return documentoBD;
	}
	
	public static long diasEntreDosFechas(Date fechaDesde, Date fechaHasta){
	     long startTime = fechaDesde.getTime() ;
	     long endTime = fechaHasta.getTime();
	     long diasDesde = (long) Math.floor(startTime / (1000*60*60*24)); // convertimos a dias, para que no afecten cambios de hora 
	     long diasHasta = (long) Math.floor(endTime / (1000*60*60*24)); // convertimos a dias, para que no afecten cambios de hora
	     long dias = diasHasta - diasDesde;

	     return dias;
	} 

//	public static void main(String[] args) {
//		int dias=diasEntreDosFechas(S)
//	}
	
	public void copiarDocumentosUltimaSolicitud(String solicitudUuidUltima, String solicitudUuid, Contexto contexto) {
		Long idSolicitudUltima = solicitudService.obtenerId(solicitudUuidUltima);
		Long idSolicitud = solicitudService.obtenerId(solicitudUuid);
		List<Documento> listaDocumentosUltimaSolicitud = documentoDao.buscar(idSolicitudUltima);
		List<Documento> listaDocumentosSolicitud = documentoDao.buscar(idSolicitud);
		
		if (listaDocumentosSolicitud == null || listaDocumentosSolicitud.isEmpty()) {
			if (listaDocumentosUltimaSolicitud != null) {
				Documento documentoNuevo = null;
				for (Documento documento : listaDocumentosUltimaSolicitud) {
					documentoNuevo = new Documento();
					Solicitud solicitud = new Solicitud();
					solicitud.setIdSolicitud(idSolicitud);
					documentoNuevo.setSolicitud(solicitud);
					documentoNuevo.setTipoDocumento(documento.getTipoDocumento());
					documentoNuevo.setNumeroDocumento(documento.getNumeroDocumento());
					documentoNuevo.setDescripcionContrato(documento.getDescripcionContrato());
					documentoNuevo.setFechaInicio(documento.getFechaInicio());
					documentoNuevo.setFechaFin(documento.getFechaFin());
					documentoNuevo.setDuracion(documento.getDuracion());
					documentoNuevo.setFlagVigente(documento.getFlagVigente());
					documentoNuevo.setEvaluacion(documento.getEvaluacion());
					documentoNuevo.setPais(documento.getPais());
					documentoNuevo.setTipoCambio(documento.getTipoCambio());
					documentoNuevo.setNombreEntidad(documento.getNombreEntidad());
					documentoNuevo.setFlagSiged(documento.getFlagSiged());
					documentoNuevo.setUsuCreacion(contexto.getUsuario().getUsuario());
					documentoNuevo.setIpCreacion(contexto.getIp());
					documentoNuevo.setFecCreacion(new Date());
					documentoNuevo = documentoDao.save(documentoNuevo);
					
					Pageable pageable = null;
					Page<Archivo> listaArchivos = archivoDao.buscarArchivo(Constantes.LISTADO.TIPO_ARCHIVO.EXPERIENCIA, idSolicitudUltima, pageable);
					if (listaArchivos != null) {
						Archivo archivoNuevo = null;
						for (Archivo archivo : listaArchivos) {
							archivoNuevo = new Archivo();
							archivoNuevo.setIdDocumento(documentoNuevo.getIdDocumento());
							archivoNuevo.setEstado(archivo.getEstado());
							archivoNuevo.setTipoArchivo(archivo.getTipoArchivo());
							archivoNuevo.setIdSolicitud(idSolicitud);
							archivoNuevo.setNombre(archivo.getNombre());
							archivoNuevo.setNombreReal(archivo.getNombreReal());
							archivoNuevo.setNombreAlFresco(archivo.getNombreAlFresco());
							archivoNuevo.setCodigo(UUID.randomUUID().toString());
							archivoNuevo.setTipo(archivo.getTipo());
							archivoNuevo.setCorrelativo(archivo.getCorrelativo());
							archivoNuevo.setVersion(archivo.getVersion());
							archivoNuevo.setNroFolio(archivo.getNroFolio());
							archivoNuevo.setPeso(archivo.getPeso());
							archivoNuevo.setUsuCreacion(contexto.getUsuario().getUsuario());
							archivoNuevo.setIpCreacion(contexto.getIp());
							archivoNuevo.setFecCreacion(new Date());
							archivoNuevo = archivoDao.save(archivoNuevo);
						}
					}
				}
			}	
		}
	}
}
