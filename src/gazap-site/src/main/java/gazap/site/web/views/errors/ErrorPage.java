package gazap.site.web.views.errors;

import gazap.site.web.views.GazapPage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class ErrorPage extends GazapPage {
    @XmlElement(required = false)
    private String bugEmail;
    @XmlElement(required = false)
    private String bugSubject;
    @XmlElement(required = false)
    private String bugBody;

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
}
