package gazap.panel.services.impl;

import gazap.common.web.model.SocialProfileProvider;
import gazap.common.web.model.SocialProfileProviders;
import gazap.common.web.security.PrincipalImpl;
import gazap.domain.dao.UserProfileDao;
import gazap.domain.entity.UserSocialLink;
import gazap.panel.services.UserAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserAccessImpl implements UserAccess {
    @Autowired
    protected UserProfileDao userProfileDao;

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
        return Collections.unmodifiableCollection(SocialProfileProviders.SOCIAL_PROFILE_PROVIDERS.values());
    }

    @Override
    public SocialProfileProvider createSocialProvider(UserSocialLink link) {
        String providerName = SocialProfileProviders.OPENID_PROVIDER_SITES.get(link.getProvider());
        if (providerName == null) {
            providerName = link.getProvider();
        }
        return SocialProfileProviders.SOCIAL_PROFILE_PROVIDERS.get(providerName);
    }
}
