package gazap.site.services;

import gazap.common.web.model.SocialProfile;
import gazap.domain.entity.UserProfile;
import gazap.domain.entity.UserSocialLink;
import gazap.site.model.ServiceErrorException;
import org.springframework.social.connect.ConnectionKey;

public interface UserService {
    UserSocialLink createSocialConnection(UserProfile profile, ConnectionKey key, SocialProfile socialProfile) throws ServiceErrorException;
}
