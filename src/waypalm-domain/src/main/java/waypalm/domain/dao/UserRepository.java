package waypalm.domain.dao;

import waypalm.domain.entity.UserProfile;
import waypalm.domain.entity.UserSocialLink;
import waypalm.domain.entity.UserSummary;

public interface UserRepository extends Dao {
    UserSocialLink findSocialConnection(String provider, String providerUser, String email);

    UserProfile getUser(int id);

    UserProfile findUserByEmail(String contactEmail);

    UserProfile findUserByAlias(String alias);

    UserSummary loadSummary(UserProfile profile);
}
