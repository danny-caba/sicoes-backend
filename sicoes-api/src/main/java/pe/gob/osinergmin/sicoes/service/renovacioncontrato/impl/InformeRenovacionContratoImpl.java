package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeRenovacionContratoDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InformeRenovacionContratoService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper.InformeRenovacionContratoMapper;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;


@Service
public class InformeRenovacionContratoImpl implements InformeRenovacionContratoService {

    private final Logger logger = LogManager.getLogger(InformeRenovacionContratoImpl.class);

    @Value("${path.jasper}")
	private String pathJasper;

    private final InformeRenovacionContratoDao informeRenovacionContratoDao;
    private final SigedOldConsumer sigedOldConsumer;

    public InformeRenovacionContratoImpl(InformeRenovacionContratoDao informeRenovacionContratoDao,SigedOldConsumer sigedOldConsumer) {
        this.informeRenovacionContratoDao = informeRenovacionContratoDao;
        this.sigedOldConsumer = sigedOldConsumer;
    }

    @Override
    public Page<InformeRenovacionContratoDTO> listaInformes(
            String numeroExpediente, 
            Long estado,
            Long idContratista ,
            Contexto contexto,
            Pageable pageable) {

        Boolean esVigente = true;
        contexto.getUsuario().getRoles();
        // TODO: Falta implementar flujo segun roles
        Page<InformeRenovacionContrato> listInforme =  informeRenovacionContratoDao.findByFiltrosWithJoins(
                                                        numeroExpediente,
                                                        esVigente,
                                                        estado,
                                                        idContratista,
                                                        pageable);
        return listInforme.map(InformeRenovacionContratoMapper.MAPPER::toDTO);
    }

    @Override
    public InformeRenovacionContratoDTO crearInforme(InformeRenovacionContratoDTO informeRenovacionContratoDTO, Contexto contexto) {
        

    InformeRenovacionContrato informe = InformeRenovacionContratoMapper.MAPPER.toEntity(informeRenovacionContratoDTO);
    UUID uuid = UUID.randomUUID();
    String uuidString = uuid.toString();

    Archivo archivo = new Archivo();
    SimpleDateFormat sdf2=new SimpleDateFormat("ddMMyyyyhhmm");
    String fechaHoy = sdf2.format(new Date());

    archivo.setNombre("INFORME_RENOVACION_"+".pdf");
    archivo.setNombreReal("INFORME_RENOVACION_REAL_"+".pdf");
    archivo.setTipo("application/pdf");


    informe.setUuidInformeRenovacion(uuidString);
    informe.setVigente(Boolean.TRUE);
    informe.setRegistro(Constantes.ESTADO.ACTIVO);
    informe.setCompletado(Constantes.ESTADO.INACTIVO);

    AuditoriaUtil.setAuditoriaRegistro(informe,contexto);
    //TODO: Falta implementar crear PDF
    

    File jrxml = new File(pathJasper + "Formato_Informe_RenovacionContrato.jrxml");
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    JasperPrint print = null;
    
    try {
    JasperReport jasperReport = getJasperCompilado(jrxml);
        
    String nombreAreaSolicitud = "area";
    String nombreEvaluador = "Evaluador";
    String nombreEmpresaSupervisora = "Supervisora";
    String numExpediente = "num expe";

    Map<String, Object> parameters = new HashMap<>();
    
    logger.info("SUBREPORT_DIR: {}", pathJasper);
	parameters.put("SUBREPORT_DIR", pathJasper);

    parameters.put("nombreAreaSolcitud", nombreAreaSolicitud);
    parameters.put("nombreEvaluador", nombreEvaluador);
    parameters.put("nombreEmpresaSupervisora", nombreEmpresaSupervisora);
    parameters.put("numExpediente", numExpediente);

    parameters.put("objecto", informe.getObjeto());
    parameters.put("baseLegal", informe.getBaseLegal());
    parameters.put("antecedentes", informe.getAntecedentes()); 
    parameters.put("justificacion", informe.getJustificacion());

    print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
	output = new ByteArrayOutputStream();
	JasperExportManager.exportReportToPdfStream(print, output);
    

        
    InformeRenovacionContrato nuevoInforme =  informeRenovacionContratoDao.save(informe);

    return InformeRenovacionContratoMapper.MAPPER.toDTO(nuevoInforme);
    } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (JRException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    byte[] bytesSalida = output.toByteArray();

    archivo.setPeso(bytesSalida.length * 1L);
    archivo.setNroFolio(1L);
    archivo.setContenido(bytesSalida);
 
    //TODO: Falta implementar flujo ALFRESCO

    sigedOldConsumer.subirArchivosAlfresco(null, null, null, null, archivo.getIdContrato(), null, archivo);
    //TODO: Falta implementar flujo SIGED

    return null;
    }

    public JasperReport getJasperCompilado(File path) throws JRException, FileNotFoundException {
		FileInputStream employeeReportStream = new FileInputStream(path);
		return JasperCompileManager.compileReport(employeeReportStream);
	}
}
