package pe.gob.osinergmin.sicoes.service.impl;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Opcion;
import pe.gob.osinergmin.sicoes.model.Paces;
import pe.gob.osinergmin.sicoes.model.PacesEstado;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.repository.PacesDao;
import pe.gob.osinergmin.sicoes.repository.PacesEstadoDao;
import pe.gob.osinergmin.sicoes.repository.UsuarioDao;
import pe.gob.osinergmin.sicoes.service.PacesService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import weblogic.ant.taskdefs.utils.ForEach;


@Service
public class PacesServiceImpl implements PacesService {

	@Autowired
	private PacesDao pacesDao;
	@Autowired
	private PacesEstadoDao pacesEstadoDao;
	
	@Autowired
	private ListadoDetalleDao listadoDetalleDao;
	@Autowired
	private UsuarioDao usuarioDao;
	
	
	@Override
	public Page<Paces> obtenerPor(Long idArea,Long idEstado,  Pageable pageable,Contexto contexto) {
		
		
		Page<Paces> paces =pacesDao.obtenerPor(idArea,  pageable);

		for (Paces pace : paces) {
						
			pace.setIdTipoEstado(pacesEstadoDao.obtenerPorIdPace(pace.getIdPaces()).stream()
			        .sorted((p1, p2) -> p2.getIdPacesEstado().compareTo(p1.getIdPacesEstado())) 
			        .findFirst().get().getIdTipoEstado());											
		}
	
	if(idEstado!=null)
	{
		 List<Paces> filtrados = paces.getContent().stream()
			        .filter(Paces -> Paces.getIdTipoEstado().equals(idEstado))  // Filtrar por tipoEstado			        
			        .collect(Collectors.toList());
		 
		 return new PageImpl<>(filtrados, pageable,  filtrados.size());		
	}
		
		return paces;
	}
	
	
	@Override
	public Page<Paces> obtenerAsignadosG2Por(Long idArea,Long idEstado,  Pageable pageable,Contexto contexto) {
		
		
		Page<Paces> paces =pacesDao.obtenerAsignadosG2Por(idArea,contexto.getUsuario().getIdUsuario(),pageable);
						
		PacesEstado estado=null;
		Optional<ListadoDetalle> listadoDetalleOpt=null;
		for (Paces pace : paces) {
			
		    estado=pacesEstadoDao.obtenerPorIdPace(pace.getIdPaces()).stream()
			        .sorted((p1, p2) -> p2.getIdPacesEstado().compareTo(p1.getIdPacesEstado())) 
			        .findFirst().get();
			pace.setIdTipoEstado(estado.getIdTipoEstado());
			pace.setObservacion(estado.getDeObservacion());			
			
			listadoDetalleOpt = listadoDetalleDao.findById(pace.getIdTipoEstado());
			if(Constantes.LISTADO.ESTADO_PACE.ESTADO_PACE_APROBADO_DIVISION.equals(listadoDetalleOpt.get().getCodigo()))
			{
				pace.setDetalleEstado("Aprobado por: "+usuarioDao.findById(Long.parseLong(estado.getUsuCreacion())).get().getNombreUsuario() +
			               " Fecha: " +estado.getFeFechaEstado().toString() );	
			}					
		}
					
		List<Paces> pacesList = new ArrayList<>();
		
		for (Paces pace : paces) {
			listadoDetalleOpt = listadoDetalleDao.findById(pace.getIdTipoEstado());			
			if (Constantes.LISTADO.ESTADO_PACE.ESTADO_PACE_REGISTRADO.equals(listadoDetalleOpt.get().getCodigo()) ||
			           Constantes.LISTADO.ESTADO_PACE.ESTADO_PACE_ACTUALIZADO.equals(listadoDetalleOpt.get().getCodigo()) ||
			           Constantes.LISTADO.ESTADO_PACE.ESTADO_PACE_OBSERVADO.equals(listadoDetalleOpt.get().getCodigo()) ||
			           Constantes.LISTADO.ESTADO_PACE.ESTADO_PACE_APROBADO_DIVISION.equals(listadoDetalleOpt.get().getCodigo()))	
			{
				pacesList.add(pace);
			}			
		}		
		paces=new PageImpl<>(pacesList, pageable, pacesList.size());
			
		
		if(idEstado!=null)
		{				
			 List<Paces> filtrados = paces.getContent().stream()			        				 				 				 				    			        			    
				        .filter(pace -> pace.getIdTipoEstado().equals(idEstado))
				        .collect(Collectors.toList());
			 return new PageImpl<>(filtrados, pageable, filtrados.size());		
		}
			
		return paces;
	}
	
	
	@Override
	public Page<Paces> obtenerAsignadosG3Por(Long idArea,Long idEstado,  Pageable pageable,Contexto contexto) {
		
		
		Page<Paces> paces =pacesDao.obtenerAsignadosG3Por(idArea,contexto.getUsuario().getIdUsuario(),pageable);
						
		PacesEstado estado=null;
		Optional<ListadoDetalle> listadoDetalleOpt=null;
		for (Paces pace : paces) {
			
		    estado=pacesEstadoDao.obtenerPorIdPace(pace.getIdPaces()).stream()
			        .sorted((p1, p2) -> p2.getIdPacesEstado().compareTo(p1.getIdPacesEstado())) 
			        .findFirst().get();
			pace.setIdTipoEstado(estado.getIdTipoEstado());
			pace.setObservacion(estado.getDeObservacion());			
			
			listadoDetalleOpt = listadoDetalleDao.findById(pace.getIdTipoEstado());
			if(Constantes.LISTADO.ESTADO_PACE.ESTADO_PACE_APROBADO_ENVIADO.equals(listadoDetalleOpt.get().getCodigo()))
			{
				pace.setDetalleEstado("Aprobado por: "+usuarioDao.findById(Long.parseLong(estado.getUsuCreacion())).get().getNombreUsuario() +
			               " Fecha: " +estado.getFeFechaEstado().toString() );	
			}					
		}
					
		List<Paces> pacesList = new ArrayList<>();
		
		for (Paces pace : paces) {
			listadoDetalleOpt = listadoDetalleDao.findById(pace.getIdTipoEstado());			
		if (
			           Constantes.LISTADO.ESTADO_PACE.ESTADO_PACE_APROBADO_ENVIADO.equals(listadoDetalleOpt.get().getCodigo()) ||
			           Constantes.LISTADO.ESTADO_PACE.ESTADO_PACE_CANCELADO.equals(listadoDetalleOpt.get().getCodigo()) ||
			           Constantes.LISTADO.ESTADO_PACE.ESTADO_PACE_APROBADO_DIVISION.equals(listadoDetalleOpt.get().getCodigo()))	
			{
				pacesList.add(pace);
			}			
		}		
		paces=new PageImpl<>(pacesList, pageable, pacesList.size());
			
		
		if(idEstado!=null)
		{				
			 List<Paces> filtrados = paces.getContent().stream()			        				 				 				 				    			        			    
				        .filter(pace -> pace.getIdTipoEstado().equals(idEstado))
				        .collect(Collectors.toList());
			 return new PageImpl<>(filtrados, pageable, filtrados.size());		
		}
			
		return paces;
	}
	
	
	@Override
	public Page<Paces> obtenerAceptadosEnviadosPor(Long idArea,Long idEstado,  Pageable pageable,Contexto contexto) {
		
		
		Page<Paces> paces =pacesDao.obtenerAceptadoEnviadoPor(idArea,pageable);
						
		PacesEstado estado=null;
		Optional<ListadoDetalle> listadoDetalleOpt=null;
		for (Paces pace : paces) {
			
		    estado=pacesEstadoDao.obtenerPorIdPace(pace.getIdPaces()).stream()
			        .sorted((p1, p2) -> p2.getIdPacesEstado().compareTo(p1.getIdPacesEstado())) 
			        .findFirst().get();
			pace.setIdTipoEstado(estado.getIdTipoEstado());
			pace.setObservacion(estado.getDeObservacion());											
		}
					
		List<Paces> pacesList = new ArrayList<>();
		
		for (Paces pace : paces) {
			listadoDetalleOpt = listadoDetalleDao.findById(pace.getIdTipoEstado());			
		if (Constantes.LISTADO.ESTADO_PACE.ESTADO_PACE_APROBADO_ENVIADO.equals(listadoDetalleOpt.get().getCodigo()))	
			{
				pacesList.add(pace);
			}			
		}		
		paces=new PageImpl<>(pacesList, pageable, pacesList.size());
					
		if(idEstado!=null)
		{				
			 List<Paces> filtrados = paces.getContent().stream()			        				 				 				 				    			        			    
				        .filter(pace -> pace.getIdTipoEstado().equals(idEstado))
				        .collect(Collectors.toList());
			 return new PageImpl<>(filtrados, pageable, filtrados.size());		
		}
			
		return paces;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Boolean actualizar(Long idPaces,int deMes,String noConvocatoria,Double dePresupuesto,String deItemPaces,String reProgramaAnualSupervision,Contexto context)
	{							
				if(pacesDao.findById(idPaces).isPresent())				
				{
					Paces opcionBD=pacesDao.findById(idPaces).get();
					opcionBD.setDeMes(deMes);
					opcionBD.setDePresupuesto(dePresupuesto);
					opcionBD.setDeItemPaces(deItemPaces);
					opcionBD.setNoConvocatoria(noConvocatoria);
					opcionBD.setReProgramaAnualSupervision(reProgramaAnualSupervision);									
					AuditoriaUtil.setAuditoriaActualizacion(opcionBD, context);
					pacesDao.save(opcionBD);					
					return true;
				
				}
				return false;										
	}
	
	
	
	@Transactional(rollbackFor = Exception.class)
	public Boolean observarPaceDivision(Long idPaces,String observacion,Contexto context)
	{							
				if(pacesDao.findById(idPaces).isPresent())				
				{									
					return agregarEstadoPace(idPaces,
							listadoDetalleDao.listarListadoDetallePorCoodigo(Constantes.LISTADO.ESTADO_PACE.ESTADO_PACE_OBSERVADO).get(0).getIdListadoDetalle(),observacion,context);				
				}
				return false;										
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Boolean aprobarPaceDivision(Long idPaces,String observacion,Contexto context)
	{							
				if(pacesDao.findById(idPaces).isPresent())				
				{
					return agregarEstadoPace(idPaces,
							listadoDetalleDao.listarListadoDetallePorCoodigo(Constantes.LISTADO.ESTADO_PACE.ESTADO_PACE_APROBADO_DIVISION).get(0).getIdListadoDetalle(),observacion,context);				
				}
				return false;										
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Boolean cancelarPaceGerencia(Long idPaces,String observacion,Contexto context)
	{							
				if(pacesDao.findById(idPaces).isPresent())				
				{
					return agregarEstadoPace(idPaces,
							listadoDetalleDao.listarListadoDetallePorCoodigo(Constantes.LISTADO.ESTADO_PACE.ESTADO_PACE_CANCELADO).get(0).getIdListadoDetalle(),observacion,context);				
				}
				return false;										
	}
	@Transactional(rollbackFor = Exception.class)
	public Boolean aprobarEnviarPaceGerencia(Long idPaces,String observacion,Contexto context)
	{							
				if(pacesDao.findById(idPaces).isPresent())				
				{
					return agregarEstadoPace(idPaces,
							listadoDetalleDao.listarListadoDetallePorCoodigo(Constantes.LISTADO.ESTADO_PACE.ESTADO_PACE_APROBADO_ENVIADO).get(0).getIdListadoDetalle(),observacion,context);				
				}
				return false;										
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	public Boolean agregarEstadoPace(Long idPaces,Long estado,String observacion,Contexto context)
	{							
				if(pacesDao.findById(idPaces).isPresent())				
				{
					PacesEstado nuevo=new PacesEstado();
					nuevo.setPaces(pacesDao.findById(idPaces).get());
					nuevo.setIdTipoEstado(estado);// estado observado
					nuevo.setFeFechaEstado(new Date());
					nuevo.setDeObservacion(observacion);
					AuditoriaUtil.setAuditoriaRegistro(nuevo, context);									
					pacesEstadoDao.save(nuevo);					
					return true;				
				}
				return false;										
	}
	
	@Override
	public Paces guardar(Paces model, Contexto contexto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Paces obtener(Long id, Contexto contexto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		
		Paces pace= pacesDao.findById(id).get();
		pace.setEliminado(true);
		AuditoriaUtil.setAuditoriaActualizacion(pace, contexto);									
		pacesDao.save(pace);	
		//pacesDao.deleteById(id);
		
	}

}
