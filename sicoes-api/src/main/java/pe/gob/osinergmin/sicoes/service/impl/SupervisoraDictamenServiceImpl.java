package pe.gob.osinergmin.sicoes.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.DictamenEvaluacion;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.SupervisoraDictamen;
import pe.gob.osinergmin.sicoes.model.dto.DetalleDTO;
import pe.gob.osinergmin.sicoes.model.dto.SupervisoraDTO;
import pe.gob.osinergmin.sicoes.repository.SupervisoraDictamenDao;
import pe.gob.osinergmin.sicoes.service.SupervisoraDictamenService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;


@Service
public class SupervisoraDictamenServiceImpl implements SupervisoraDictamenService {

	Logger logger = LogManager.getLogger(SupervisoraDictamenServiceImpl.class);
	
	@Autowired
	private SupervisoraDictamenDao supervisoraDictamenDao;

	@Override
	public SupervisoraDictamen guardar(SupervisoraDictamen supervisoraDictamen, Contexto contexto) {
		AuditoriaUtil.setAuditoriaRegistro(supervisoraDictamen,contexto);
		supervisoraDictamenDao.save(supervisoraDictamen);
		return supervisoraDictamen;
	}

	@Override
	public SupervisoraDictamen obtener(Long idDictamenEvaluacion, Contexto contexto) {
		return supervisoraDictamenDao.obtener(idDictamenEvaluacion);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void eliminar(Long id, Contexto contexto) {
		supervisoraDictamenDao.deleteById(id);
	}

	@Override
	public SupervisoraDictamen obtenerXSupervisora(Long idSupervisora, Long idSector, Contexto contexto) {
		return supervisoraDictamenDao.obtenerXSupervisora(idSupervisora,idSector);
	}

	@Override
	public List<SupervisoraDictamen> listarXSupervisora(Long idSupervisora, Contexto contexto) {
		return supervisoraDictamenDao.listarXSupervisora(idSupervisora);
	}

	@Override
	public Page<SupervisoraDTO> buscarXMonto(
		String nroExpediente,Long idTipoPersona,Long idTipoDocumento,String ruc,
		String nombres,
		String perfil,
		String sfechaInicio,
		String sfechaFin,Pageable pageable, Contexto contexto
	) {
		List<SupervisoraDTO> groupedResult;
		int start = 0;
		int end = 0;
		
		
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
		
		//AFC
	    
	    if (idTipoPersona != null && idTipoPersona == 679) {
		   List<Supervisora> resultList1 = supervisoraDictamenDao.buscarPN(nroExpediente, idTipoPersona, idTipoDocumento, ruc, nombres, fechaInicio, fechaFin);
		   groupedResult = resultList1.stream()
		            .collect(Collectors.groupingBy(sd -> sd.getIdSupervisora()))
		            .values().stream()
		            .map(this::mapToSupervisoraPNDTO)
		            .collect(Collectors.toList());

		   start = (int) pageable.getOffset();
		   end = Math.min((start + pageable.getPageSize()), groupedResult.size());

	    }else {
	    List<SupervisoraDictamen> resultList = supervisoraDictamenDao.buscarXMonto(nroExpediente, idTipoPersona, idTipoDocumento, ruc, nombres, fechaInicio, fechaFin);
		   groupedResult = resultList.stream()
	            .collect(Collectors.groupingBy(sd -> sd.getSupervisora().getIdSupervisora()))
	            .values().stream()
	            .map(this::mapToSupervisoraDTO)
	            .collect(Collectors.toList());

		    start = (int) pageable.getOffset();
		    end = Math.min((start + pageable.getPageSize()), groupedResult.size());

	    }

	    return new PageImpl<>(groupedResult.subList(start, end), pageable, groupedResult.size());
	}
	
	private SupervisoraDTO mapToSupervisoraDTO(List<SupervisoraDictamen> supervisoraDictamens) {
	    SupervisoraDictamen firstItem = supervisoraDictamens.get(0);

	    SupervisoraDTO supervisoraDTO = new SupervisoraDTO();
	    supervisoraDTO.setIdSupervisora(firstItem.getSupervisora().getIdSupervisora());
	    supervisoraDTO.setPais(firstItem.getSupervisora().getPais().getDescripcion());
	    supervisoraDTO.setNumeroExpediente(firstItem.getSupervisora().getNumeroExpediente());
	    supervisoraDTO.setTipoPersona(firstItem.getSupervisora().getTipoPersona().getDescripcion());
	    supervisoraDTO.setTipoDocumento(firstItem.getSupervisora().getTipoDocumento().getDescripcion());
	    supervisoraDTO.setNumeroDocumento(firstItem.getSupervisora().getNumeroDocumento());
	    supervisoraDTO.setNombreRazonSocial(firstItem.getSupervisora().getNombreRazonSocial());
	    supervisoraDTO.setEstado(firstItem.getSupervisora().getEstado().getDescripcion());
	    supervisoraDTO.setFechaIngreso(firstItem.getSupervisora().getFechaIngreso());
	    
	    List<DetalleDTO> detalles = supervisoraDictamens.stream()
	            .map(this::mapToDetalleDTO)
	            .collect(Collectors.toList());

	    supervisoraDTO.setDetalles(detalles);

	    return supervisoraDTO;
	}
	
	private DetalleDTO mapToDetalleDTO(SupervisoraDictamen supervisoraDictamen) {
	    DetalleDTO detalleDTO = new DetalleDTO();
	    detalleDTO.setSector(supervisoraDictamen.getSector());
	    detalleDTO.setMontoFacturado(supervisoraDictamen.getMontoFacturado());
	    return detalleDTO;
	}

	//AFC
	private SupervisoraDTO mapToSupervisoraPNDTO(List<Supervisora> Supervisoras) {
		Supervisora firstItem = Supervisoras.get(0);

	    SupervisoraDTO supervisoraDTO = new SupervisoraDTO();
	    supervisoraDTO.setIdSupervisora(firstItem.getIdSupervisora());
	    supervisoraDTO.setPais(firstItem.getPais().getDescripcion());
	    supervisoraDTO.setNumeroExpediente(firstItem.getNumeroExpediente());
	    supervisoraDTO.setTipoPersona(firstItem.getTipoPersona().getDescripcion());
	    supervisoraDTO.setTipoDocumento(firstItem.getTipoDocumento().getDescripcion());
	    supervisoraDTO.setNumeroDocumento(firstItem.getNumeroDocumento());
	    supervisoraDTO.setNombreRazonSocial((firstItem.getNombres() + " " + firstItem.getApellidoPaterno()+ " " +firstItem.getApellidoMaterno()));
	    supervisoraDTO.setEstado(firstItem.getEstado().getDescripcion());
	    supervisoraDTO.setFechaIngreso(firstItem.getFechaIngreso());
	    
	    return supervisoraDTO;

	}
	
}
