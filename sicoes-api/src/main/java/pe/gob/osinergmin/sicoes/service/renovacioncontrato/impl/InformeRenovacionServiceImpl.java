package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeRenovacionDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacion;
import pe.gob.osinergmin.sicoes.repository.SicoesSolicitudDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionDao;
import pe.gob.osinergmin.sicoes.service.*;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InformeRenovacionService;
import pe.gob.osinergmin.sicoes.util.Contexto;


@Service
public class InformeRenovacionServiceImpl implements InformeRenovacionService {

	Logger logger = LogManager.getLogger(InformeRenovacionServiceImpl.class);

	@Autowired
	private InformeRenovacionDao informeRenovacionDao;
	@Autowired
	private SicoesSolicitudService sicoesSolicitudService;
	@Autowired
	private SupervisoraRepresentanteService supervisoraRepresentanteService;
	@Autowired
	private SicoesSolicitudSeccionService sicoesSolicitudSeccionService;
	@Autowired
	private ListadoDetalleService listadoDetalleService;
	@Autowired
	private ArchivoService archivoService;
	@Autowired
	private SicoesTdSolPerConSecService seccionesService;
	@Autowired
	private SicoesSolicitudDao sicoesSolicitudDao;


	@Override
	public Page<InformeRenovacionDTO> buscar(String nuExpediente, String contratista, String estadoAprobacion, Pageable pageable, Contexto contexto) {
		logger.info(nuExpediente);

		Page<InformeRenovacion> page = informeRenovacionDao.findByNuExpedienteContratistaEstado(nuExpediente, contratista, pageable);

        return page.map(this::mapToDto);
	}

	private InformeRenovacionDTO mapToDto(InformeRenovacion entity) {
		InformeRenovacionDTO dto = new InformeRenovacionDTO();

		dto.setIdInformeRenovacion(entity.getIdInformeRenovacion());
		dto.setDeObjeto(entity.getDeObjeto());
		dto.setDeBaseLegal(entity.getDeBaseLegal());
		dto.setDeAntecedentes(entity.getDeAntecedentes());
		dto.setDeJustificacion(entity.getDeJustificacion());
		dto.setDeNecesidad(entity.getDeNecesidad());
		dto.setDeConclusiones(entity.getDeConclusiones());
		dto.setEsVigente(entity.getEsVigente());
		dto.setDeUuidInfoRenovacion(entity.getDeUuidInfoRenovacion());
		dto.setDeNombreArchivo(entity.getDeNombreArchivo());
		dto.setDeRutaArchivo(entity.getDeRutaArchivo());
		dto.setEsCompletado(entity.getEsCompletado());
		dto.setEsRegistro(entity.getEsRegistro());

		dto.setUsuario(entity.getUsuario());
		dto.setNotificacion(entity.getNotificacion());
		dto.setRequerimientoRenovacion(entity.getRequerimientoRenovacion());
		dto.setEstadoAprobacionInforme(entity.getEstadoAprobacionInforme());

		if (entity.getRequerimientoRenovacion() != null &&
				entity.getRequerimientoRenovacion().getSolicitudPerfil() != null &&
				entity.getRequerimientoRenovacion().getSolicitudPerfil().getPropuesta() != null &&
				entity.getRequerimientoRenovacion().getSolicitudPerfil().getPropuesta().getSupervisora() != null) {

			dto.setSupervisora(
					entity.getRequerimientoRenovacion()
							.getSolicitudPerfil()
							.getPropuesta()
							.getSupervisora()
							.getNombreRazonSocial()
			);
		}

		return dto;
	}


}