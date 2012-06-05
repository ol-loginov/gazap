package gazap.site.web.model;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonAutoDetect(JsonMethod.NONE)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ApiAnswer {
    @JsonProperty
    private boolean success = false;
    @JsonProperty
    private String code;
    @JsonProperty
    private String message;
    @JsonProperty
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
