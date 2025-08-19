package pe.gob.osinergmin.sicoes;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import pe.gob.osinergmin.sicoes.service.SolicitudService;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@TestPropertySource("/application-test.properties")
//
//@ContextConfiguration(loader = AnnotationConfigContextLoader.class)


@RunWith(SpringRunner.class)
@ComponentScan({"pe.gob.osinergmin.sicoes.service","pe.gob.osinergmin.sicoes.service.impl","pe.gob.osinergmin.sicoes.repository"})
//@SpringBootTest(classes = {Application.class})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class TestJasper {
	
	@Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:orcl");
        dataSource.setUsername("SICOES");
        dataSource.setPassword("SICOES");

        return dataSource;
    }


	@Autowired
	private SolicitudService solicitudService;

	@Test
	public void unLockUser() {
		System.out.println("GENERAR ARCHIVO!");
	}

}