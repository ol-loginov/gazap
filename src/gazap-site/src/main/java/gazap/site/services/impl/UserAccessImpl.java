package gazap.site.services.impl;

import gazap.domain.dao.UserProfileDao;
import gazap.domain.entity.UserSocialLink;
import gazap.site.model.SocialProfileProvider;
import gazap.site.services.UserAccess;
import gazap.site.web.mvc.PrincipalImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

@Service
public class UserAccessImpl implements UserAccess {
    private static final Map<String, SocialProfileProvider> SOCIAL_PROFILE_PROVIDERS;
    private static final Map<String, String> OPENID_PROVIDER_SITES;

    @Autowired
    protected UserProfileDao userProfileDao;

    static {
        SOCIAL_PROFILE_PROVIDERS = new TreeMap<String, SocialProfileProvider>() {{
            put("google", PROVIDER_GOOGLE);
            put("yandex", PROVIDER_YANDEX);
            put("twitter", PROVIDER_TWITTER);
            put("facebook", PROVIDER_FACEBOOK);
        }};

        OPENID_PROVIDER_SITES = new TreeMap<String, String>() {{
            put("www.google.com", "google");
            put("openid.yandex.ru", "yandex");
        }};
    }

    @Override
    public boolean isAuthorized() {
        PrincipalImpl principal = getCurrentPrincipal();
        return principal != null && principal.isValid();
    }

    private SecurityContext getContext() {
        return SecurityContextHolder.getContext();
    }

    private PrincipalImpl getCurrentPrincipal() {
        Authentication authentication = getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object p = authentication.getPrincipal();
        if (!(p instanceof PrincipalImpl)) {
            return null;
        }
        return (PrincipalImpl) p;
    }

    @Override
    public Collection<SocialProfileProvider> getAvailableSocialProviders() {
        return Collections.unmodifiableCollection(SOCIAL_PROFILE_PROVIDERS.values());
    }

    @Override
    public SocialProfileProvider createSocialProvider(UserSocialLink link) {
        String providerName = OPENID_PROVIDER_SITES.get(link.getProvider());
        if (providerName == null) {
            providerName = link.getProvider();
        }
        return SOCIAL_PROFILE_PROVIDERS.get(providerName);
    }
}
