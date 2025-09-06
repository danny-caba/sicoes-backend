package pe.gob.osinergmin.sicoes.config;

import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import pe.gob.osinergmin.sicoes.service.impl.SolicitudServiceImpl;

@Configuration
//@PropertySource("file:D:/SICOES/data/sicoes/sicoes-api.properties")
//@PropertySource("file:/data/sicoes/sicoes4-api.properties")
//@PropertySource("classpath:application.properties")
@PropertySource("file:C:/dlw-sicoes/sicoes-api.properties")
//@EnableTransactionManagement
public class DataSourceConfig {
	
	Logger logger = LogManager.getLogger(DataSourceConfig.class);
	
    @Bean(name="empsanJdbcTemplate")
	@ConfigurationProperties(prefix = "spring.datasource.secondary") // In the pro
    public JdbcTemplate empsanJdbcTemplate() throws NamingException {
		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        return new JdbcTemplate(dataSourceLookup.getDataSource(jndiSecondary().getJndiName()));
    }
    
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setCacheSeconds(10); //reload messages every 10 seconds
        messageSource.setDefaultLocale(Locale.getDefault());
        messageSource.setUseCodeAsDefaultMessage(true);
        Iterator<String> mensajes=messageSource.getBasenameSet().iterator();
 
        logger.info("Inicio messageSource");
        while(mensajes.hasNext()) {
        	String nombre=mensajes.next();
        	logger.info(nombre);
        }
        logger.info("Fin messageSource");
        return messageSource;
    }

    @Bean
	@ConfigurationProperties(prefix = "spring.datasource.secondary") // In properties file, the prefix of the datasource
	public JndiPropertyHolder jndiSecondary() {
		return new JndiPropertyHolder();
	}

    private static class JndiPropertyHolder {
		private String jndiName;

		public String getJndiName() {
			return jndiName;
		}

		public void setJndiName(String jndiName) {
			this.jndiName = jndiName;
		}
	}
}
