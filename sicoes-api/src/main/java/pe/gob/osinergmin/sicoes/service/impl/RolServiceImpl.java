package pe.gob.osinergmin.sicoes.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.Opcion;
import pe.gob.osinergmin.sicoes.model.Rol;
import pe.gob.osinergmin.sicoes.model.RolOpcion;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.repository.RolDao;
import pe.gob.osinergmin.sicoes.service.OpcionService;
import pe.gob.osinergmin.sicoes.service.RolOpcionService;
import pe.gob.osinergmin.sicoes.service.RolService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
public class RolServiceImpl implements RolService{

	@Autowired
	RolDao rolDao;
	
	@Autowired
	OpcionService opcionService; 
	
	@Autowired
	RolOpcionService rolOpcionService; 
	
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public Rol guardar(Rol rol, Contexto contexto) {
//		if(StringUtils.isEmpty(rol.getNombre())) {
//			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ROL_NOMBRE_REQUERIDO);
//		}
//		if(StringUtils.isEmpty(rol.getCodigo())) {
//			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ROL_CODIGO_REQUERIDO);
//		}
//		if(rol.getEstado()==null||rol.getEstado().getIdListadoDetalle()==null) {
//			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ROL_ESTADO_REQUERIDO);
//		}
//		if(rol.getOpciones()==null||rol.getOpciones().isEmpty()) {
//			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ROL_OPCIONES_REQUERIDO);
//		}
		Rol rolBD=null;
		if(rol.getIdRol()!=null) {
			rolBD=obtener(rol.getIdRol(),contexto);
			rolBD.setCodigo(rol.getCodigo());
			rolBD.setNombre(rol.getNombre());
			rolBD.setDescripcion(rol.getDescripcion());
			rolBD.setEstado(rol.getEstado());
			boolean existe=false;
			Iterator<Opcion> itOpcion=rolBD.getOpciones().iterator();
			while(itOpcion.hasNext()) {
				Opcion opcionBD=itOpcion.next();
				existe=false;
				for(Opcion opcion:rol.getOpciones()) {
					if(opcionBD.getIdOpcion().equals(opcion.getIdOpcion())) {
						rol.getOpciones().remove(opcion);
						existe=true;
						break;
					}
				}
				if(existe) {					
					itOpcion.remove();
				}else {
					itOpcion.remove();
					rolOpcionService.eliminar(rolBD.getIdRol(),opcionBD.getIdOpcion());
				}
				
			}
			rolBD=rolDao.save(rolBD);
		}else {
			rolBD=rolDao.save(rol);
		}
		for(Opcion opcion:rol.getOpciones()) {
			RolOpcion rolOpcion=new RolOpcion();
			rolOpcion.setRol(rol);
			rolOpcion.setOpcion(opcion);
			AuditoriaUtil.setAuditoriaRegistro(rolOpcion, contexto);
			rolOpcionService.guardar(rolOpcion,contexto);
		}
		return rolBD;
	}

	@Transactional(rollbackFor = Exception.class)
	public void eliminar(Long id, Contexto contexto) {
		Rol rol=obtener(id,contexto);
		for(Opcion o:rol.getOpciones()) {
			rolOpcionService.eliminar(rol.getIdRol(), o.getIdOpcion());
		}
		rolDao.deleteById(id);
		
	}

	@Override
	public Rol obtener(Long id, Contexto contexto) {
		Rol rol= rolDao.obtener(id);
		rol.setOpciones(opcionService.buscarOpcionRol(rol.getIdRol()));
		return rol;
	}

	@Override
	public Page<Rol> buscar(String nombre, Usuario usuario, Pageable pageable) {
		return rolDao.buscar(nombre,pageable);
	}

	@Override
	public List<Rol> buscarRolUsuario(Long idUsuario) {
		return rolDao.buscarRolUsuario(idUsuario);
	}

	@Override
	public Rol obtenerCodigo(String codigo) {
		Rol rol= rolDao.obtenerCodigo(codigo);
		rol.setOpciones(opcionService.buscarOpcionRol(rol.getIdRol()));
		return rol;
	}

}
