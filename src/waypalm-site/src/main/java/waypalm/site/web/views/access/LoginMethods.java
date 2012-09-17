package waypalm.site.web.views.access;

import waypalm.common.web.model.SocialProfileProvider;

import java.util.ArrayList;
import java.util.List;

public class LoginMethods {
    private String redirectUrl;
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