package gazap.site.web.modules;

import com.iserv2.commons.mvc.views.ContentModule;
import gazap.site.model.SocialProfileProvider;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "visitor")
public class VisitorModule extends ContentModule {
    @XmlElement(required = false, defaultValue = "false")
    private boolean debug;
    @XmlElement
    private boolean logged;
    @XmlElement
    private String name;
    @XmlElement
    private String gravatar;
    @XmlElement
    private String welcomePrompt;
    @XmlElementWrapper(name = "authProviders", required = false)
    @XmlElement(name = "provider")
    private List<SocialProfileProvider> authProviders;

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public String getGravatar() {
        return gravatar;
    }

    public void setGravatar(String gravatar) {
        this.gravatar = gravatar;
    }

    public List<SocialProfileProvider> getAuthProviders() {
        if (authProviders == null) {
            authProviders = new ArrayList<SocialProfileProvider>();
        }
        return authProviders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getWelcomePrompt() {
        return welcomePrompt;
    }

    public void setWelcomePrompt(String welcomePrompt) {
        this.welcomePrompt = welcomePrompt;
    }
}
