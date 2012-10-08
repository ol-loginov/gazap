package waypalm.site.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import waypalm.common.web.model.SocialProfileProvider;
import waypalm.common.web.model.SocialProfileProviders;
import waypalm.common.web.security.PrincipalImpl;
import waypalm.domain.dao.UserRepository;
import waypalm.domain.dao.WorldRepository;
import waypalm.domain.entity.UserProfile;
import waypalm.domain.entity.UserSocialLink;
import waypalm.site.services.UserAccess;
import waypalm.site.services.UserActionGuard;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserAccessImpl implements UserAccess {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected WorldRepository worldRepository;

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
    public UserProfile getCurrentProfile() {
        PrincipalImpl principal = getCurrentPrincipal();
        if (principal != null) {
            return userRepository.getUser(principal.getUserId());
        }
        return null;
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

    @Override
    public UserActionGuard can(UserProfile profile) {
        return new UserActionGuardImpl(profile, worldRepository);
    }
}
