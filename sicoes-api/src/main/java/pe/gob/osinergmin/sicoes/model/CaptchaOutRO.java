package pe.gob.osinergmin.sicoes.model;

public class CaptchaOutRO {

    private boolean success;
    private String errors;

    public CaptchaOutRO() {}

    public CaptchaOutRO(boolean success, String errors) {
        this.success = success;
        this.errors = errors;
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
