package pe.gob.osinergmin.sicoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {WebSocketServletAutoConfiguration.class})
@EnableScheduling
public class SicoesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SicoesApiApplication.class, args);
	
	}

}
