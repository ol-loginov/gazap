package gazap.site.web.modules;

import com.iserv2.commons.mvc.views.ContentModule;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cdn")
@XmlAccessorType(XmlAccessType.NONE)
public class CdnModule extends ContentModule {
    @XmlElement
    private String server;
    @XmlElement
    private String contextPath;
    @XmlElement
    private int year;
    @XmlElement
    private String locale;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
