package pe.gob.osinergmin.sicoes.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.ProcesoItemPerfil;
import pe.gob.osinergmin.sicoes.repository.ProcesoItemPerfilDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.ProcesoItemPerfilService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
public class ProcesoItemPerfilServiceImpl implements ProcesoItemPerfilService {

	Logger logger = LogManager.getLogger(ProcesoItemPerfilServiceImpl.class);

	@Autowired
	private ProcesoItemPerfilDao procesoItemPerfilDao;
	
	@Autowired
	private ListadoDetalleService listadoDetalleService;
	
	@Override
	public Page<ProcesoItemPerfil> listarPerfiles(String procesoItemUuid, Pageable pageable, Contexto contexto) {
		return procesoItemPerfilDao.buscar(procesoItemUuid,pageable);
	}
	
	@Override
	public List<ProcesoItemPerfil> listar(String procesoItemUuid,Contexto contexto) {
		return procesoItemPerfilDao.listar(procesoItemUuid);
	}
	
	@Override
	public List<ProcesoItemPerfil> buscar(Long idProceso) {
		return procesoItemPerfilDao.buscarPerfiles(idProceso);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void eliminarPerfil(Long idProcesoItemPerfil,String procesoItemUuid, Contexto contexto) {	
		procesoItemPerfilDao.delete(procesoItemPerfilDao.obtener(idProcesoItemPerfil, procesoItemUuid));	
	}
	
	@Override
	public ProcesoItemPerfil obtener(Long idProcesoItemPerfil, String procesoItemUuid, Contexto contexto) {
		return procesoItemPerfilDao.obtener(idProcesoItemPerfil,procesoItemUuid);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ProcesoItemPerfil guardar(ProcesoItemPerfil perfil, Contexto contexto) {
		ProcesoItemPerfil perfilBD=null;
		if(perfil.getIdProcesoItemPerfil()==null) {
			perfilBD=perfil;
			ListadoDetalle presentacion =listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_ITEM.CODIGO, Constantes.LISTADO.ESTADO_ITEM.PRESENTACION);
			perfilBD.setEstado(presentacion);
		}else {
			perfilBD = procesoItemPerfilDao.obtener(perfil.getIdProcesoItemPerfil(),perfil.getProcesoItem().getProcesoItemUuid());
			perfilBD.setNroProfesionales(perfil.getNroProfesionales());
			perfilBD.setSector(perfil.getSector());
			perfilBD.setSubsector(perfil.getSubsector());
			perfilBD.setPerfil(perfil.getPerfil());
		}
		
		
		
		AuditoriaUtil.setAuditoriaRegistro(perfilBD,contexto);
		procesoItemPerfilDao.save(perfilBD);
		return perfilBD;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void eliminar(Long id, Contexto contexto) {
		procesoItemPerfilDao.deleteById(id);
	}

	@Override
	public ProcesoItemPerfil obtener(Long id, Contexto contexto) {
		return procesoItemPerfilDao.obtener(id);
	}
	
	@Override
	public List<Object[]> listarProfesionalesXPerfil(String procesoItemUuid,Contexto contexto) {
		List<Object[]> list = procesoItemPerfilDao.listarProfesionalesXPerfil(procesoItemUuid); 
		return list;
	}
}
