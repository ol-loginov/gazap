package gazap.panel.services;

import gazap.common.web.model.SocialProfileProvider;
import gazap.domain.entity.UserSocialLink;

import java.util.Collection;

public interface UserAccess {
    boolean isAuthorized();

    SocialProfileProvider createSocialProvider(UserSocialLink link);

    Collection<SocialProfileProvider> getAvailableSocialProviders();
}
