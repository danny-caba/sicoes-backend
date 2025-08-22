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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DireccionxClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;

import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
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
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeRenovacionContratoDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.SolicitudPerfecionamientoContrato;
import pe.gob.osinergmin.sicoes.repository.ArchivoDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.SolicitudPerfecionamientoContratoDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.NotificacionRenovacionContratoService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper.InformeRenovacionContratoMapper;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;


@Service
public class CrearInformeRenovacionContratoImpl  {

    private final Logger logger = LogManager.getLogger(CrearInformeRenovacionContratoImpl.class);
 
    @Value("${path.jasper}")
	private String pathJasper;

    @Value("${crear.expediente.parametros.tipo.documento.adjuntar}")
    private String crearExpedienteParametrosTipoDocumentoAdjuntar;

    @Value("${crear.expediente.parametros.proceso}")
    private String crearExpedienteParametrosProceso;

	@Value("${siged.old.proyecto}")
	private String SIGLA_PROYECTO;

	@Value("${siged.titulo.informe.renovacion.contrato}")
	private String TITULO_INFORME_RENOVACION_CONTRATO;

    @Value("${siged.bus.server.id.usuario}")
    private String BUS_SERVER_ID_USUARIO;

    @Value("${crear.expediente.parametros.enumerado}")
    private String crearExpedienteParametrosEnumerado;

    @Value("${crear.expediente.parametros.esta.en.flujo}")
    private String crearExpedienteParametrosEstaEnFlujo;

    @Value("${crear.expediente.parametros.firmado}")
    private String crearExpedienteParametrosFirmado;

    @Value("${crear.expediente.parametros.crea.expediente.no}")
    private String crearExpedienteParametrosCreaExpedienteNo;

    @Value("${crear.expediente.parametros.crea.folio}")
    private String crearExpedienteParametrosCreaFolio;

    @Value("${crear.expediente.parametros.crea.publico}")
    private String crearExpedienteParametrosCreaPublico;

    private final InformeRenovacionContratoDao informeRenovacionContratoDao;
    private final SigedOldConsumer sigedOldConsumer;
    private final SigedApiConsumer sigedApiConsumer;
    private final NotificacionRenovacionContratoService notificacionRenovacionContratoService;
    private final ArchivoDao archivoDao;
    private final ListadoDetalleService listadoDetalleService;
    private final SolicitudPerfecionamientoContratoDao solicitudPerfecionamientoContratoDao;


    public CrearInformeRenovacionContratoImpl(
        InformeRenovacionContratoDao informeRenovacionContratoDao,
        SigedOldConsumer sigedOldConsumer,
        SigedApiConsumer sigedApiConsumer,
        NotificacionRenovacionContratoService notificacionRenovacionContratoService,
        ArchivoDao archivoDao,
        ListadoDetalleService listadoDetalleService,
        SolicitudPerfecionamientoContratoDao solicitudPerfecionamientoContratoDao) {

        this.informeRenovacionContratoDao = informeRenovacionContratoDao;
        this.sigedOldConsumer = sigedOldConsumer;
        this.sigedApiConsumer = sigedApiConsumer;
        this.notificacionRenovacionContratoService = notificacionRenovacionContratoService;
        this.archivoDao = archivoDao;
        this.listadoDetalleService = listadoDetalleService;
        this.solicitudPerfecionamientoContratoDao = solicitudPerfecionamientoContratoDao;
    }


    public InformeRenovacionContratoDTO ejecutar(InformeRenovacionContratoDTO informeRenovacionContratoDTO, Contexto contexto) {
        

    Long idSolicitud = informeRenovacionContratoDTO.getRequerimiento().getSolicitudPerfil().getIdSolicitud(); 
    List<SolicitudPerfecionamientoContrato> listaPerfilesAprobadoresBySolicitud  = 
        solicitudPerfecionamientoContratoDao.getPerfilAprobadorByIdPerfilListadoDetalle(idSolicitud);
    
    if (listaPerfilesAprobadoresBySolicitud.isEmpty()) {
        return informeRenovacionContratoDTO;
    }

    InformeRenovacionContrato informe = InformeRenovacionContratoMapper.MAPPER.toEntity(informeRenovacionContratoDTO);
    informe.setVigente(Boolean.TRUE);
    informe.setRegistro(Constantes.ESTADO.ACTIVO);
    informe.setCompletado(Constantes.ESTADO.INACTIVO);

    AuditoriaUtil.setAuditoriaRegistro(informe,contexto);

    File jrxml = new File(pathJasper + "Formato_Informe_RenovacionContrato.jrxml");

    ByteArrayOutputStream output = generarPdfOutputStream(informe, jrxml);
    byte[] bytesSalida = output.toByteArray();

    Archivo archivoPdf = buidlArchivo(bytesSalida, informeRenovacionContratoDTO.getIdInformeRenovacion());

    archivoPdf.setIdContrato(informeRenovacionContratoDTO.getRequerimiento().getIdReqRenovacion());

    String alfrescoPath = sigedOldConsumer.subirArchivosAlfresco(
        null, 
        null, 
        null, 
        null, 
        archivoPdf.getIdContrato(), 
        null, 
        archivoPdf);
    archivoPdf.setNombreAlFresco(alfrescoPath);

    archivoPdf = archivoDao.save(archivoPdf);

    logger.info("Archivo registrado en DB con ID: {} y ruta Alfresco: {} " , archivoPdf.getIdArchivo() , alfrescoPath);

    adjuntarDocumentoSiged(informe,archivoPdf.getNombre(),bytesSalida);

    InformeRenovacionContrato nuevoInformeRenovacionContrato =  informeRenovacionContratoDao.save(informe);

    Usuario usuario = contexto.getUsuario();
    String numExpediente = informeRenovacionContratoDTO.getRequerimiento().getNuExpediente();
    notificacionRenovacionContratoService.notificacionInformePorAprobar( usuario,  numExpediente, contexto);

    return InformeRenovacionContratoMapper.MAPPER.toDTO(nuevoInformeRenovacionContrato);
    }


    private void adjuntarDocumentoSiged(InformeRenovacionContrato nuevoInformeRenovacionContrato, String nombre,byte[] bytesSalida) {
        List<File> archivosAlfresco = new ArrayList<>();
        ExpedienteInRO expedienteInRO = crearExpediente(
            nuevoInformeRenovacionContrato,
            Integer.parseInt(crearExpedienteParametrosTipoDocumentoAdjuntar)
        );
        File file = null;
        try {
            file = crearFileDesdeBytes(bytesSalida, nombre,"pdf");
            archivosAlfresco.add(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    
        try {
            DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);

            logger.info("SIGED RESULT: {}", documentoOutRO.getMessage());
            if (documentoOutRO.getResultCode() != 1) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO, documentoOutRO.getMessage());
            }
        } catch (Exception e) {
            logger.error("Error al agregar documento informe renovacion en SIGED", e);
        }
    }
        
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

    private Archivo buidlArchivo(byte[] bytesSalida, Long idInformeRenovacion) {
        Archivo archivo = new Archivo();
        archivo.setNombre("INFORME_RENOVACION_"+".pdf");
        archivo.setNombreReal("INFORME_RENOVACION_REAL_"+".pdf");
        archivo.setTipo("application/pdf");

        
    ListadoDetalle archivoRenovacion = listadoDetalleService.obtenerListadoDetalle(
        Constantes.LISTADO.TIPO_ARCHIVO.CODIGO    ,
        Constantes.LISTADO.TIPO_ARCHIVO.INFORME_RENOVACION_CONTRATO
        );
    if (archivoRenovacion == null) {
        throw  new RuntimeException("Estado 'renovacion contrato' no encontrado en listado detalle");
    }

        archivo.setPeso(bytesSalida.length * 1L);
        archivo.setNroFolio(1L);
        archivo.setContenido(bytesSalida);
        archivo.setTipo(crearExpedienteParametrosTipoDocumentoAdjuntar);
        archivo.setIdInformeRenovacion(idInformeRenovacion);
        archivo.setTipoArchivo(archivoRenovacion);

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

    public ExpedienteInRO crearExpediente(InformeRenovacionContrato nuevoInformeRenovacionContrato, Integer codigoTipoDocumento) {
	ExpedienteInRO expediente = new ExpedienteInRO();
		DocumentoInRO documento = new DocumentoInRO();
		ClienteListInRO clientes = new ClienteListInRO();
		ClienteInRO cs = new ClienteInRO();
		List<ClienteInRO> cliente = new ArrayList<>();
		DireccionxClienteListInRO direcciones = new DireccionxClienteListInRO();
		DireccionxClienteInRO d = new DireccionxClienteInRO();
		List<DireccionxClienteInRO> direccion = new ArrayList<>();

        Integer codigoUsuario = 1;

		expediente.setProceso(Integer.parseInt(crearExpedienteParametrosProceso));
		expediente.setDocumento(documento);
        String numExpediente = nuevoInformeRenovacionContrato.getRequerimiento().getNuExpediente();
        expediente.setNroExpediente(numExpediente); 

        documento.setAsunto(TITULO_INFORME_RENOVACION_CONTRATO); 
		documento.setAppNameInvokes(SIGLA_PROYECTO);

		cs.setCodigoTipoIdentificacion(Integer.parseInt("1"));
        cs.setNombre("Osinergmin");
		cs.setApellidoPaterno("-");
		cs.setApellidoMaterno("-");
		cs.setRepresentanteLegal("-");		
		cs.setRazonSocial("solicitud.getPersona().getNombreRazonSocial()");
		cs.setNroIdentificacion("solicitud.getPersona().getNumeroDocumento()");
		cs.setTipoCliente(Integer.parseInt("crear.expediente.parametros.tipo.cliente"));
		cliente.add(cs);
		
        d.setDireccion("-");
		d.setDireccionPrincipal(true);
		d.setEstado('1');//"crear.expediente.parametros.direccion.estado"
		d.setTelefono("-");
		d.setUbigeo(Integer.parseInt("-"));
		direccion.add(d);

		direcciones.setDireccion(direccion);
		cs.setDirecciones(direcciones);
		clientes.setCliente(cliente);
		documento.setClientes(clientes);
		documento.setCodTipoDocumento(codigoTipoDocumento);

		documento.setUsuarioCreador(Integer.parseInt(BUS_SERVER_ID_USUARIO));
		documento.setEnumerado(crearExpedienteParametrosEnumerado.charAt(0));
		documento.setEstaEnFlujo(crearExpedienteParametrosEstaEnFlujo.charAt(0));
		documento.setFirmado(crearExpedienteParametrosFirmado.charAt(0));
		documento.setCreaExpediente(crearExpedienteParametrosCreaExpedienteNo.charAt(0));
		documento.setNroFolios(Integer.parseInt(crearExpedienteParametrosCreaFolio));
		documento.setPublico(crearExpedienteParametrosCreaPublico.charAt(0));
		documento.setUsuarioCreador(codigoUsuario);

        logger.info("DOC_EXPEDIENTE {}",documento);
        logger.info("EXPEDIENTE_INFORME_RENOVACION {}",expediente);
		return expediente;
    }
}
