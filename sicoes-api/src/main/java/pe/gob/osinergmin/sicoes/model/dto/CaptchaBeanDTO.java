package pe.gob.osinergmin.sicoes.model.dto;

import pe.gob.osinergmin.sicoes.model.CaptchaOutRO;

import java.io.Serializable;

public class CaptchaBeanDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success;
    private String errors;

    public CaptchaBeanDTO() {}

    public CaptchaBeanDTO(CaptchaOutRO captchaOutRO) {
        this.success = captchaOutRO.isSuccess();
        this.errors = captchaOutRO.getErrors();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

}
