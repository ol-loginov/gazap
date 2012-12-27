package waypalm.site.services;

import org.springframework.transaction.annotation.Transactional;
import waypalm.common.web.model.SocialProfileProvider;
import waypalm.domain.entity.Profile;
import waypalm.domain.entity.SocialLink;

import java.util.Collection;

public interface UserAccess {
    boolean isAuthorized();

    Collection<SocialProfileProvider> getAvailableSocialProviders();

    SocialProfileProvider createSocialProvider(SocialLink link);

    Profile loadCurrentProfile();
}
