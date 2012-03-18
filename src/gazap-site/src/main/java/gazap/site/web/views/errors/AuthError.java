package gazap.site.web.views.errors;

import java.util.ArrayList;
import java.util.List;

public class AuthError {
    private String returnUrl;
    private String errorCode;
    private String errorDetail;
    private List<String> suggestions = new ArrayList<String>();

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }
}
