package waypalm.site.services;

import org.springframework.social.connect.ConnectionKey;
import waypalm.common.web.model.SocialProfile;
import waypalm.domain.entity.Profile;
import waypalm.domain.entity.SocialLink;
import waypalm.site.model.ServiceErrorException;

public interface UserService {
    SocialLink createSocialConnection(Profile profile, ConnectionKey key, SocialProfile socialProfile) throws ServiceErrorException;

    Profile createUser(String email, String password);

    Profile findUserByAliasOrId(String aliasOrId);
}
