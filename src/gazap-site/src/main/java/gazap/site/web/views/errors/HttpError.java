package gazap.site.web.views.errors;

import gazap.site.web.views.GazapPage;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "ErrorPage")
@XmlAccessorType(XmlAccessType.NONE)
public class HttpError extends GazapPage {
    private String bugEmail;
    private String bugSubject;
    private String bugBody;
    private String http;
    private List<String> suggestions = new ArrayList<String>();

    @XmlElement(required = false)
    public String getBugEmail() {
        return bugEmail;
    }

    public void setBugEmail(String bugEmail) {
        this.bugEmail = bugEmail;
    }

    @XmlElement(required = false)
    public String getBugSubject() {
        return bugSubject;
    }

    public void setBugSubject(String bugSubject) {
        this.bugSubject = bugSubject;
    }

    @XmlElement(required = false)
    public String getBugBody() {
        return bugBody;
    }

    public void setBugBody(String bugBody) {
        this.bugBody = bugBody;
    }

    @XmlElement
    public String getHttp() {
        return http;
    }

    public void setHttp(String http) {
        this.http = http;
    }

    @XmlElementWrapper(name = "suggestions")
    @XmlElement(name = "key")
    public List<String> getSuggestions() {
        return suggestions;
    }
}
