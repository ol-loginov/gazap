package waypalm.site.services;

import org.springframework.social.connect.ConnectionKey;
import waypalm.common.web.model.SocialProfile;
import waypalm.domain.entity.UserProfile;
import waypalm.domain.entity.UserSocialLink;
import waypalm.site.model.ServiceErrorException;

public interface UserService {
    UserSocialLink createSocialConnection(UserProfile profile, ConnectionKey key, SocialProfile socialProfile) throws ServiceErrorException;

    UserProfile createUser(String email, String password);

    UserProfile findUserByAliasOrId(String aliasOrId);
}
