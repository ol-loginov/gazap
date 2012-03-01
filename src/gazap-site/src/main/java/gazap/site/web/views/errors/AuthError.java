package gazap.site.web.views.errors;

import gazap.site.web.views.GazapPage;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "ErrorPage")
public class AuthError extends GazapPage {
    private String returnUrl;
    private String errorCode;
    private String errorDetail;
    private List<String> suggestions = new ArrayList<String>();

    @XmlElement(required = false)
    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    @XmlElement(required = false)
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @XmlElement(required = false)
    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    @XmlElementWrapper(name = "suggestions")
    @XmlElement(name = "key")
    public List<String> getSuggestions() {
        return suggestions;
    }
}
