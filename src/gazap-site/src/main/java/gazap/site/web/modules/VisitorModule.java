package gazap.site.web.modules;

import com.iserv2.commons.mvc.views.ContentModule;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "visitor")
public class VisitorModule extends ContentModule {
    private int id;
    private boolean debug;
    private boolean logged;
    private String name;
    private String gravatar;
    private String welcomePrompt;

    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    @XmlElement
    public String getGravatar() {
        return gravatar;
    }

    public void setGravatar(String gravatar) {
        this.gravatar = gravatar;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(required = false, defaultValue = "false")
    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @XmlElement
    public String getWelcomePrompt() {
        return welcomePrompt;
    }

    public void setWelcomePrompt(String welcomePrompt) {
        this.welcomePrompt = welcomePrompt;
    }
}
