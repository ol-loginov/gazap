package gazap.site.model;

public class ApiAnswer {
    private boolean success;
    private String code;
    private String message;
    private ApiFieldMessage[] errorList;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ApiFieldMessage[] getErrorList() {
        return errorList;
    }

    public void setErrorList(ApiFieldMessage[] errorList) {
        this.errorList = errorList;
    }
}
