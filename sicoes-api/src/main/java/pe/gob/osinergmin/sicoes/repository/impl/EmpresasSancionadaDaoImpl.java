package pe.gob.osinergmin.sicoes.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.repository.EmpresasSancionadaDao;

@Repository
public class EmpresasSancionadaDaoImpl implements EmpresasSancionadaDao{

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Environment env;		
	
	private Logger logger = LogManager.getLogger(EmpresasSancionadaDaoImpl.class);
	
	public String validarEmpresaSancionada(String ruc) {
		try {
			return jdbcTemplate.queryForObject("SELECT ESAN_DEV_EMP_SANCIONADA_FUN(?) FROM DUAL", new Object[] { ruc }, 
				new RowMapper<String>() {
						public String mapRow(ResultSet resultSet, int i) throws SQLException {
								return resultSet.getString(1);
							
						}
					}
				);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return e.getCause().getMessage().split("\\n")[0].split(":")[1];
		}
	}
}
