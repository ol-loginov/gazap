package gazap.site.services;

import gazap.common.web.model.SocialProfileProvider;
import gazap.domain.entity.UserProfile;
import gazap.domain.entity.UserSocialLink;

import java.util.Collection;

public interface UserAccess {
    boolean isAuthorized();

    Collection<SocialProfileProvider> getAvailableSocialProviders();

    SocialProfileProvider createSocialProvider(UserSocialLink link);

    UserActionGuard can();

    UserProfile getCurrentProfile();
}
