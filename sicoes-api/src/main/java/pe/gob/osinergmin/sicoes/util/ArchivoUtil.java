package pe.gob.osinergmin.sicoes.util;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Component
public class ArchivoUtil {

    private static final Logger logger = LogManager.getLogger(ArchivoUtil.class);

    public void close(InputStream file) {
        try {
            if (file != null) file.close();
        } catch (Exception e) {
            logger.error("Error al cerrar el stream del logo", e);
        }
    }

    public JasperReport getJasperCompilado(File path) throws JRException, FileNotFoundException {
        FileInputStream employeeReportStream = new FileInputStream(path);
        return JasperCompileManager.compileReport(employeeReportStream);
    }

    public static String obtenerNombreArchivo(ListadoDetalle tipo) {
        switch (tipo.getCodigo()) {
            case Constantes.LISTADO.TIPO_ARCHIVO.CONSOLIDADO_DOCUMENTOS:
                return "Consolidado_Documentos.pdf";
            case Constantes.LISTADO.TIPO_ARCHIVO.FINALIZACION_EVALUACION:
                return "Finalizacion_Evaluacion.pdf";
            case Constantes.LISTADO.TIPO_ARCHIVO.CARGA_DOCUMENTOS:
                return "Carga_Documentos.pdf";
            default:
                return "";
        }
    }

    public static String obtenerNombreJasper(ListadoDetalle tipo) {
        switch (tipo.getCodigo()) {
            case Constantes.LISTADO.TIPO_ARCHIVO.CONSOLIDADO_DOCUMENTOS:
                return "Formato_04_Consolidado_Documentos.jrxml";
            case Constantes.LISTADO.TIPO_ARCHIVO.FINALIZACION_EVALUACION:
                return "Formato_04_Finalizacion_Evaluacion.jrxml";
            case Constantes.LISTADO.TIPO_ARCHIVO.CARGA_DOCUMENTOS:
                return "Formato_04_Carga_Documentos.jrxml";
            default:
                return "";
        }
    }

}
