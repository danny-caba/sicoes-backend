package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import gob.osinergmin.siged.remote.rest.ro.in.ArchivoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DireccionxClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ArchivoListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.query.ClienteConsultaOutRO;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Proceso;
import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeRenovacionContratoDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.repository.ArchivoDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InformeRenovacionContratoService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.NotificacionRenovacionContratoService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper.InformeRenovacionContratoMapper;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;


@Service
public class InformeRenovacionContratoImpl implements InformeRenovacionContratoService {

    private final Logger logger = LogManager.getLogger(InformeRenovacionContratoImpl.class);

    @Value("${path.jasper}")
	private String pathJasper;

    @Value("${crear.expediente.parametros.tipo.documento.adjuntar}")
    private String crearExpedienteParametrosTipoDocumentoAdjuntar;

    private final InformeRenovacionContratoDao informeRenovacionContratoDao;
    private final SigedOldConsumer sigedOldConsumer;
    private final SigedApiConsumer sigedApiConsumer;
    private final NotificacionRenovacionContratoService notificacionRenovacionContratoService;
    private final ArchivoDao archivoDao;

    public InformeRenovacionContratoImpl(
        InformeRenovacionContratoDao informeRenovacionContratoDao,
        SigedOldConsumer sigedOldConsumer,
        SigedApiConsumer sigedApiConsumer,
        NotificacionRenovacionContratoService notificacionRenovacionContratoService,
        ArchivoDao archivoDao) {

        this.informeRenovacionContratoDao = informeRenovacionContratoDao;
        this.sigedOldConsumer = sigedOldConsumer;
        this.sigedApiConsumer = sigedApiConsumer;
        this.notificacionRenovacionContratoService = notificacionRenovacionContratoService;
        this.archivoDao = archivoDao;
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

    informe.setUuidInformeRenovacion(uuidString);
    informe.setVigente(Boolean.TRUE);
    informe.setRegistro(Constantes.ESTADO.ACTIVO);
    informe.setCompletado(Constantes.ESTADO.INACTIVO);

    AuditoriaUtil.setAuditoriaRegistro(informe,contexto);

    File jrxml = new File(pathJasper + "Formato_Informe_RenovacionContrato.jrxml");

    ByteArrayOutputStream output = generarPdfOutputStream(informe, jrxml);
    byte[] bytesSalida = output.toByteArray();

    Archivo archivoPdf = buidlArchivo(bytesSalida);

    String alfrescoPath = sigedOldConsumer.subirArchivosAlfresco(null, null, null, null, archivoPdf.getIdContrato(), null, archivoPdf);
    archivoPdf.setNombreAlFresco(alfrescoPath);

    archivoPdf = archivoDao.save(archivoPdf);
    logger.info("Archivo registrado en DB con ID: " + archivoPdf.getIdArchivo() + " y ruta Alfresco: " + alfrescoPath);

    adjuntarDocumentoSiged(archivoPdf,informe,"Informe Renovacion Contrato",bytesSalida);

    InformeRenovacionContrato nuevoInformeRenovacionContrato =  informeRenovacionContratoDao.save(informe);


    Usuario usuario = contexto.getUsuario();
    String numExpediente = "";
    notificacionRenovacionContratoService.notificacionInformePorAprobar( usuario,  numExpediente, contexto);

    return InformeRenovacionContratoMapper.MAPPER.toDTO(nuevoInformeRenovacionContrato);
    }


    private void adjuntarDocumentoSiged(Archivo archivo, InformeRenovacionContrato nuevoInformeRenovacionContrato, String asunto,byte[] bytesSalida) {
        List<File> archivosAlfresco = new ArrayList<>();
        ExpedienteInRO expedienteInRO = crearExpediente(
            nuevoInformeRenovacionContrato,
            Integer.parseInt(crearExpedienteParametrosTipoDocumentoAdjuntar)
        );
        File file = null;
        try {
            file = crearFileDesdeBytes(bytesSalida, "","pdf");
            archivosAlfresco.add(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    
        try {
            DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);
            logger.info("SIGED RESULT: {}", documentoOutRO.getMessage());
            if (documentoOutRO.getResultCode() != 1) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO, documentoOutRO.getMessage());
            }
        } catch (Exception e) {
            logger.error("Error al agregar documento en SIGED", e);
        }
    }
        /**
     * Crea un archivo temporal con prefijo y sufijo personalizados
     * @param bytes Array de bytes del documento
     * @param prefix Prefijo del nombre del archivo
     * @param suffix Sufijo (extensión) del archivo
     * @return File objeto creado
     * @throws IOException Si ocurre un error de E/S
     */
    public static File crearFileDesdeBytes(byte[] bytes, String prefix, String suffix) throws IOException {
        // Crear archivo temporal en el directorio temporal del sistema
        File tempFile = File.createTempFile(prefix, suffix);
        
        // Escribir los bytes al archivo
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(bytes);
            fos.flush();
        }
        
        return tempFile;
    }

    private Archivo buidlArchivo(byte[] bytesSalida) {
        Archivo archivo = new Archivo();
        archivo.setNombre("INFORME_RENOVACION_"+".pdf");
        archivo.setNombreReal("INFORME_RENOVACION_REAL_"+".pdf");
        archivo.setTipo("application/pdf");

        archivo.setPeso(bytesSalida.length * 1L);
        archivo.setNroFolio(1L);
        archivo.setContenido(bytesSalida);
        archivo.setIdContrato(1L);//TODO: validar

        return archivo;
    }

    private ByteArrayOutputStream generarPdfOutputStream(InformeRenovacionContrato informe, File jrxml) {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    JasperPrint print = null;

    try {
    JasperReport jasperReport = getJasperCompilado(jrxml);

    Map<String, Object> parameters = buildParameters(informe);

    print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
    output = new ByteArrayOutputStream();
    JasperExportManager.exportReportToPdfStream(print, output);

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (JRException e) {
        e.printStackTrace();
    }
    return output;
    }
    
    private Map<String, Object> buildParameters(InformeRenovacionContrato informe) {

        InputStream isLogoSicoes = null;
        InputStream isLogoOsinergmin = null;

        String nombreAreaSolicitud = "area";
        String nombreEvaluador = "Evaluador";
        String nombreEmpresaSupervisora = "Supervisora";
        String numExpediente = "num expe";

        Map<String, Object> parameters = new HashMap<>();
        logger.info("SUBREPORT_DIR: {}", pathJasper);
        parameters.put("SUBREPORT_DIR", pathJasper);
    
        try {
            isLogoSicoes = Files.newInputStream(Paths.get(pathJasper + "logo-sicoes.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        parameters.put("P_LOGO_SICOES", isLogoSicoes);
        try {
            isLogoOsinergmin = Files.newInputStream(Paths.get(pathJasper + "logo-osinerming.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        parameters.put("P_LOGO_OSINERGMIN", isLogoOsinergmin);
    
        parameters.put("nombreAreaSolcitud", nombreAreaSolicitud);
        parameters.put("nombreEvaluador", nombreEvaluador);
        parameters.put("nombreEmpresaSupervisora", nombreEmpresaSupervisora);
        parameters.put("numExpediente", numExpediente);
    
        parameters.put("objecto", informe.getObjeto());
        parameters.put("baseLegal", informe.getBaseLegal());
        parameters.put("antecedentes", informe.getAntecedentes()); 
        parameters.put("justificacion", informe.getJustificacion());

        return parameters;
    }

    public JasperReport getJasperCompilado(File path) throws JRException, FileNotFoundException {
    FileInputStream employeeReportStream = new FileInputStream(path);
    return JasperCompileManager.compileReport(employeeReportStream);
	}

    public ExpedienteInRO crearExpediente(InformeRenovacionContrato nuevoInformeRenovacionContratoo, Integer tipoDocumento) {
	ExpedienteInRO expediente = new ExpedienteInRO();
		DocumentoInRO documento = new DocumentoInRO();
		ClienteListInRO clientes = new ClienteListInRO();
		ClienteInRO cs = new ClienteInRO();
		List<ClienteInRO> cliente = new ArrayList<>();
		DireccionxClienteListInRO direcciones = new DireccionxClienteListInRO();
		DireccionxClienteInRO d = new DireccionxClienteInRO();
		List<DireccionxClienteInRO> direccion = new ArrayList<>();
        Integer codigoTipoDocumento = Integer.parseInt("crear.expediente.parametros.tipo.documento.crear");//TODO: falta ENV

		expediente.setProceso(Integer.parseInt("crear.expediente.parametros.proceso"));  //TODO: falta ENV
		expediente.setDocumento(documento);

        expediente.setNroExpediente("numExpediente"); //TODO: falta
        documento.setAsunto("ASUNTO"); //TODO: falta
		documento.setAppNameInvokes("SIGLA_PROYECTO");//TODO: falta

		// ListadoDetalle tipoDocumento = listadoDetalleService
		// 		.obtener(solicitud.getPersona().getTipoDocumento().getIdListadoDetalle(), contexto);

		cs.setCodigoTipoIdentificacion(Integer.parseInt("tipoDocumento.getValor()")); //TODO: falta
        
		if (cs.getCodigoTipoIdentificacion() == 1) {//TODO: falta
			cs.setNombre("solicitud.getPersona().getNombreRazonSocial()");
			cs.setApellidoPaterno("-");
			cs.setApellidoMaterno("-");
		} else if(cs.getCodigoTipoIdentificacion() == 3){
			cs.setNombre("solicitud.getPersona().getNombres()");
			cs.setApellidoPaterno("solicitud.getPersona().getApellidoPaterno()");
			cs.setApellidoMaterno("solicitud.getPersona().getApellidoMaterno()");
		}

			cs.setRepresentanteLegal(
					// solicitud.getRepresentante().getNombres() + " " + solicitud.getRepresentante().getApellidoPaterno()
					// 		+ " " + solicitud.getRepresentante().getApellidoMaterno()
                    "Representante legal");
		
		cs.setRazonSocial("solicitud.getPersona().getNombreRazonSocial()");
		cs.setNroIdentificacion("solicitud.getPersona().getNumeroDocumento()");
		cs.setTipoCliente(Integer.parseInt("crear.expediente.parametros.tipo.cliente"));
		cliente.add(cs);
		d.setDireccion("solicitud.getPersona().getDireccion()");
		d.setDireccionPrincipal(true);
		d.setEstado('1');//"crear.expediente.parametros.direccion.estado"
		d.setTelefono("solicitud.getPersona().getTelefono1()");
		d.setUbigeo(Integer.parseInt("solicitud.getPersona().getCodigoDistrito()"));

		direccion.add(d);
		direcciones.setDireccion(direccion);
		cs.setDirecciones(direcciones);
		clientes.setCliente(cliente);
		documento.setClientes(clientes);
		documento.setCodTipoDocumento(codigoTipoDocumento);//TODO: falta
		documento.setEnumerado('1'); //TODO: falta ENV
		documento.setEstaEnFlujo('1'); //TODO: falta ENV
		documento.setFirmado('1'); //TODO: falta ENV
		documento.setCreaExpediente('1'); //TODO: falta ENV
		documento.setPublico('1'); //TODO: falta ENV
		documento.setNroFolios(1); //TODO: falta ENV  env.getProperty("crear.expediente.parametros.crea.folio")
		
		String stecnico="crear.expediente.parametros.tipo.documento.informe.tecnico"; //TODO: falta ENV
		int vtenico= Integer.parseInt(stecnico);
		documento.setUsuarioCreador(1);//TODO: falta ENV

        logger.info("DOC_EXPEDIENTE {}",documento);
        logger.info("EXPEDIENTE_INFORME_RENOVACION {}",expediente);
		return expediente;
    }
}
