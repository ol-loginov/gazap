package waypalm.domain.dao;

import waypalm.domain.entity.Profile;
import waypalm.domain.entity.ProfileSummary;
import waypalm.domain.entity.SocialLink;

public interface UserRepository extends Dao {
    SocialLink findSocialConnection(String provider, String providerUser, String email);

    Profile getProfile(int id);

    Profile findProfileByEmail(String contactEmail);

    Profile findProfileByAlias(String alias);

    ProfileSummary getProfileSummary(Profile profile);
}
