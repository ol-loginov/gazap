package gazap.site.web.views.access;

import gazap.common.web.model.SocialProfileProvider;
import gazap.site.web.views.GazapPage;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "LoginDialog")
public class LoginDialog extends GazapPage {
    @XmlElement(required = false)
    private String redirectUrl;
    @XmlElementWrapper(name = "providers")
    @XmlElement(name = "entry")
    private List<SocialProfileProvider> authProviders = new ArrayList<SocialProfileProvider>();

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public List<SocialProfileProvider> getAuthProviders() {
        return authProviders;
    }
}