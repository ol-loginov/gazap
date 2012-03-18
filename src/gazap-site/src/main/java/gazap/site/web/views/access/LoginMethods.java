package gazap.site.web.views.access;

import gazap.common.web.model.SocialProfileProvider;
import gazap.site.web.annotations.PageTitleKey;

import java.util.ArrayList;
import java.util.List;

@PageTitleKey("Login.pageTitle")
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