package pe.gob.osinergmin.sicoes.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class BaseService {


	
	 @Autowired
	 private Environment env;
	
	@PostConstruct
	public void inicio() {
	
	}
}
