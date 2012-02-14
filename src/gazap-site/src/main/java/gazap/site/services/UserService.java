package gazap.site.services;

import gazap.domain.entity.UserProfile;
import gazap.domain.entity.UserSocialLink;
import gazap.site.model.ServiceErrorException;
import gazap.site.model.SocialProfile;
import org.springframework.social.connect.ConnectionKey;

public interface UserService {
    UserSocialLink createSocialConnection(UserProfile profile, ConnectionKey key, SocialProfile socialProfile) throws ServiceErrorException;
}
