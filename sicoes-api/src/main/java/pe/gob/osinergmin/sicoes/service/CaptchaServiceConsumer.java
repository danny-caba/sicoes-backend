package pe.gob.osinergmin.sicoes.service;
import pe.gob.osinergmin.sicoes.model.dto.CaptchaBeanDTO;

public interface CaptchaServiceConsumer {
    public CaptchaBeanDTO processResponse(String response, String score) throws Exception;
}