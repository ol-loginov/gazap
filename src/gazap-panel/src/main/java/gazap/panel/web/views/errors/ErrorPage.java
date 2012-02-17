package gazap.panel.web.views.errors;

import gazap.panel.web.views.GazapPage;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "ErrorPage")
@XmlAccessorType(XmlAccessType.NONE)
public class ErrorPage extends GazapPage {
    @XmlElement(required = false)
    private String bugEmail;
    @XmlElement(required = false)
    private String bugSubject;
    @XmlElement(required = false)
    private String bugBody;
    @XmlElement
    private String http;
    @XmlElementWrapper(name = "suggestions")
    @XmlElement(name = "key")
    private List<String> suggestions = new ArrayList<String>();

    public String getBugEmail() {
        return bugEmail;
    }

    public void setBugEmail(String bugEmail) {
        this.bugEmail = bugEmail;
    }

    public String getBugSubject() {
        return bugSubject;
    }

    public void setBugSubject(String bugSubject) {
        this.bugSubject = bugSubject;
    }

    public String getBugBody() {
        return bugBody;
    }

    public void setBugBody(String bugBody) {
        this.bugBody = bugBody;
    }

    public String getHttp() {
        return http;
    }

    public void setHttp(String http) {
        this.http = http;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }
}
