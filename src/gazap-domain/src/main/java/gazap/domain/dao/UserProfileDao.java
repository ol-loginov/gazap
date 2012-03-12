package gazap.domain.dao;

import gazap.domain.entity.UserProfile;
import gazap.domain.entity.UserSocialLink;

public interface UserProfileDao extends Dao {
    UserSocialLink findSocialConnection(String provider, String providerUser, String email);

    UserProfile getUser(int id);

    UserProfile findUserByEmail(String contactEmail);

    UserProfile findUserByAlias(String alias);
}
