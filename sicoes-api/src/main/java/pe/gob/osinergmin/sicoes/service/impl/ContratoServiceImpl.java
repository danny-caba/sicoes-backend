package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.repository.ContratoDao;
import pe.gob.osinergmin.sicoes.repository.SeccionDao;
import pe.gob.osinergmin.sicoes.repository.SicoesTdSolPerConSecDao;
import pe.gob.osinergmin.sicoes.service.*;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
public class ContratoServiceImpl implements ContratoService {
	Logger logger = LogManager.getLogger(ContratoServiceImpl.class);

	@Autowired
	private ContratoDao contratoDao;

	@Override
	public Contrato guardar(Contrato contrato, Contexto contexto) {
		return contratoDao.save(contrato);
	}

	@Override
	public Contrato obtener(Long idContrato, Contexto contexto) {
		return contratoDao.findById(idContrato).orElse(null);
	}

	@Override
	public void eliminar(Long idContrato, Contexto contexto) {
		contratoDao.deleteById(idContrato);
	}

	@Override
	public Contrato registrarNuevoContrato(SicoesSolicitud solicitud, Contexto contexto) throws Exception {
		Contrato contrato = new Contrato();
		contrato.setSolicitudPerfCont(solicitud);
		contrato.setEstadoContrato("1");
		AuditoriaUtil.setAuditoriaRegistro(contrato, contexto);
		return contratoDao.save(contrato);
	}

	@Override
	public Page<Contrato> obtenerContratos(String expediente, String contratista, String tipoContrato, String areaSolicitante, Pageable pageable, Contexto contexto) {
		return contratoDao.obtenerContratos(expediente, contratista, tipoContrato, areaSolicitante, pageable);
	}

}
