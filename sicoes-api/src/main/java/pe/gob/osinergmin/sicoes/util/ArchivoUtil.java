package pe.gob.osinergmin.sicoes.util;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

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

}
