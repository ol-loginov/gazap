package gazap.site.web.modules;

import com.iserv2.commons.mvc.views.ContentModule;
import gazap.site.model.viewer.UserTitle;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "visitor")
public class VisitorModule extends ContentModule {
    private boolean debug;
    private UserTitle user;

    @XmlElement
    public UserTitle getUser() {
        return user;
    }

    public void setUser(UserTitle user) {
        this.user = user;
    }

    @XmlElement
    public boolean isLogged() {
        return user != null;
    }

    @XmlElement(required = false, defaultValue = "false")
    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
