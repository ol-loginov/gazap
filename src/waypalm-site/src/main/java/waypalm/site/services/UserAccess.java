package waypalm.site.services;

import waypalm.common.web.model.SocialProfileProvider;
import waypalm.domain.entity.UserProfile;
import waypalm.domain.entity.UserSocialLink;

import java.util.Collection;

public interface UserAccess {
    boolean isAuthorized();

    Collection<SocialProfileProvider> getAvailableSocialProviders();

    SocialProfileProvider createSocialProvider(UserSocialLink link);

    UserActionGuard can(UserProfile profile);

    UserProfile getCurrentProfile();
}
