package gazap.site.web.views.errors;

import gazap.site.model.ServiceError;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "ErrorPage")
public class PageOauthError extends ErrorPage {
    @XmlElement(required = false)
    private String returnUrl;
    @XmlElement(required = false)
    private ServiceError serviceError;
    @XmlElement(required = false)
    private String serviceErrorDetail;

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public ServiceError getServiceError() {
        return serviceError;
    }

    public void setServiceError(ServiceError serviceError) {
        this.serviceError = serviceError;
    }

    public String getServiceErrorDetail() {
        return serviceErrorDetail;
    }

    public void setServiceErrorDetail(String serviceErrorDetail) {
        this.serviceErrorDetail = serviceErrorDetail;
    }
}
