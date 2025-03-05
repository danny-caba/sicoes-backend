package pe.gob.osinergmin.sicoes.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pe.gob.osinergmin.sicoes.model.CaptchaOutRO;
import pe.gob.osinergmin.sicoes.model.dto.CaptchaBeanDTO;
import pe.gob.osinergmin.sicoes.service.CaptchaServiceConsumer;

@Repository("captchaConsumer")
public class CaptchaServiceConsumerImpl implements CaptchaServiceConsumer {

    @Value("${recaptcha.key.secret}")
    private String RECAPTCHA_PRIVATE_KEY;

    @Value("${recaptcha.ws.url}")
    private String RECAPTCHA_URL;

    @Override
    public CaptchaBeanDTO processResponse(String response, String score) throws Exception {
        String url =  String.format( "%s?secret=%s&response=%s",
                RECAPTCHA_URL,
                RECAPTCHA_PRIVATE_KEY,
                response );
        CaptchaOutRO captchaOutRO;

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>("", headers);
            String result = restTemplate.postForObject(url, request, String.class);
            JSONObject jsonObject = new JSONObject(result);

            Boolean success = jsonObject.getBoolean("success");

            if(success){
                String puntuacionMinima = score;
                Double puntuacionCaptcha = jsonObject.getDouble("score");
                if(puntuacionCaptcha < Double.parseDouble(puntuacionMinima)) {
                    captchaOutRO = new CaptchaOutRO(false, "Captcha: La validación del recaptcha no supera el minimo permitido");
                }else {
                    captchaOutRO = new CaptchaOutRO(jsonObject.getBoolean("success"), "");
                }
            }else{
                captchaOutRO = new CaptchaOutRO(false, "Captcha: " + jsonObject.getJSONArray("error-codes").getString(0));
            }
        } catch (Exception ex) {
            captchaOutRO = new CaptchaOutRO(false, "Captcha: No se realizo correctamente la validación del Token del Captcha");
        }
        CaptchaBeanDTO captchaBeanDTO = new CaptchaBeanDTO(captchaOutRO);
        return captchaBeanDTO;
    }
}