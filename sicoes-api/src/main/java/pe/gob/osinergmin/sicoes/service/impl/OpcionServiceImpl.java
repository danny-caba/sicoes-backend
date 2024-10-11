package pe.gob.osinergmin.sicoes.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.Opcion;
import pe.gob.osinergmin.sicoes.model.Rol;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.repository.OpcionDao;
import pe.gob.osinergmin.sicoes.service.OpcionService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
public class OpcionServiceImpl implements OpcionService{
	
	Logger logger = LogManager.getLogger(OpcionServiceImpl.class);
	
	@Autowired
	OpcionDao opcionDao;
	
	@Transactional(rollbackFor = Exception.class)
	public Opcion guardar(Opcion opcion,Contexto contexto) {
//		if(opcion.getCodigo()==null) {
//			throw new ValidacionException(Constantes.CODIGO_MENSAJE.OPCION_CODIGO_VACIO);
//		}
//		if(opcion.getNombre()==null) {
//			throw new ValidacionException(Constantes.CODIGO_MENSAJE.OPCION_NOMBRE_VACIO);
//		}
//		if(opcion.getDescripcion()==null) {
//			throw new ValidacionException(Constantes.CODIGO_MENSAJE.OPCION_DESCRIPCION_VACIO);
//		}
//		if(opcion.getEstado()==null||opcion.getEstado().getIdListadoDetalle()==null) {
//			throw new ValidacionException(Constantes.CODIGO_MENSAJE.OPCION_ESTADO_VACIO);
//		}
		if(opcion.getIdOpcion()!=null) {
			Opcion opcionBD=opcionDao.obtener(opcion.getIdOpcion());
			opcionBD.setCodigo(opcion.getCodigo());
			opcionBD.setNombre(opcion.getNombre());
			opcionBD.setDescripcion(opcion.getDescripcion());
			opcionBD.setEstado(opcion.getEstado());
			opcionBD.setPadre(opcion.getPadre());
			opcion=opcionBD;
		}
		AuditoriaUtil.setAuditoriaRegistro(opcion, contexto);
		opcionDao.save(opcion);
		return opcion;
	}

	@Override
	public Page<Opcion> buscar(Opcion opcion, Usuario usuario, Pageable pageable) {
		return opcionDao.buscar(opcion.getIdOpcion(),pageable);
	}

	@Transactional(rollbackFor = Exception.class)
	public void eliminar(Long idOpcion,Contexto contexto) {
		 opcionDao.deleteById(idOpcion);
	}

	@Override
	public Opcion obtener(Long id,Contexto contexto) {
		return opcionDao.obtener(id);
	}

	@Override
	public List<Opcion> buscarPadres() {
		return opcionDao.buscarPadres();
	}

	@Override
	public List<Opcion> buscarOpcionRol(Long idRol) {
		return opcionDao.buscarOpcionRol(idRol);
	}

	@Override
	public List<Opcion> opcionesRoles(List<Rol> roles, Contexto contexto) {
		return opcionDao.opcionesRoles(roles);
	}


}
