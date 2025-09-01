package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.consumer.SissegApiConsumer;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.FirmaDigitalRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.FirmaDigitalResponseDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoRenovacionDao;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.FirmaDigitalService;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;
import pe.gob.osinergmin.sicoes.util.bean.siged.AccessRequestInFirmaDigital;

import java.util.Optional;

@Service
public class FirmaDigitalImplService implements FirmaDigitalService {

    private static final Logger logger = LogManager.getLogger(FirmaDigitalImplService.class);

    @Autowired
    private SigedOldConsumer sigedOldConsumer;

    @Autowired
    private SissegApiConsumer sissegApiConsumer;

    @Autowired
    private RequerimientoRenovacionDao requerimientoRenovacionDao;

    @Override
    public FirmaDigitalResponseDTO obtenerParametrosfirmaDigital(FirmaDigitalRequestDTO firmaDigitalRequestDTO, Contexto contexto) throws Exception {
        logger.info("Iniciando obtenerParametrosfirmaDigital para idReqRenovacion: {}", firmaDigitalRequestDTO.getIdReqRenovacion());

        // 1. Validar que existe el requerimiento de renovación
        Optional<RequerimientoRenovacion> requerimientoOpt = requerimientoRenovacionDao.findById(firmaDigitalRequestDTO.getIdReqRenovacion());
        if (!requerimientoOpt.isPresent()) {
            throw new DataNotFoundException("No se encontró el requerimiento de renovación con ID: " + firmaDigitalRequestDTO.getIdReqRenovacion());
        }
        RequerimientoRenovacion requerimiento = requerimientoOpt.get();

        // 2. Obtener parámetros base de SIGED
        AccessRequestInFirmaDigital acceso = sigedOldConsumer.obtenerParametrosfirmaDigital();

        // 3. Obtener token de acceso desde SISSEG
        String accessToken = sissegApiConsumer.obtenerAccessToken(firmaDigitalRequestDTO.getToken().replaceAll(" ", "+"));

        // 4. Obtener ID del usuario desde SISSEG
        Long idUsuario = sissegApiConsumer.obtenerIdUsuario(contexto.getUsuario().getUsuario());

        // 5. Obtener datos completos del usuario desde SISSEG
        Usuario user = sissegApiConsumer.obtenerUsuario(idUsuario, accessToken);

        // 6. Configurar credenciales del usuario en el objeto de acceso
        acceso.setLoginUsuario(user.getUsuario());
        acceso.setPasswordUsuario(user.getContrasenia());

        // 7. Obtener ID del archivo para firma usando número de expediente
        Long idArchivo = obtenerIdArchivos(requerimiento.getNuExpediente(), contexto.getUsuario().getUsuario());

        // 8. Crear y devolver la respuesta
        FirmaDigitalResponseDTO response = new FirmaDigitalResponseDTO();
        response.setAcceso(acceso);
        response.setIdArchivo(idArchivo);

        logger.info("Parámetros de firma digital obtenidos exitosamente para idReqRenovacion: {}, idArchivo: {}", 
                    firmaDigitalRequestDTO.getIdReqRenovacion(), idArchivo);

        return response;
    }

    @Override
    public Long obtenerIdArchivos(String numeroExpediente, String nombreUsuario) throws Exception {
        logger.info("Obteniendo ID de archivo para expediente: {} y usuario: {}", numeroExpediente, nombreUsuario);

        // Por ahora devolver un ID fijo para testing - puede implementarse después con lógica específica
        // TODO: Implementar búsqueda real del archivo según la lógica de negocio específica
        Long idArchivo = 1L; // ID temporal para pruebas

        if (numeroExpediente == null || numeroExpediente.isEmpty()) {
            throw new DataNotFoundException("No se encontró archivo asociado al expediente: " + numeroExpediente + " para el usuario: " + nombreUsuario);
        }

        logger.info("ID de archivo obtenido: {} para expediente: {}", idArchivo, numeroExpediente);
        return idArchivo;
    }
}
