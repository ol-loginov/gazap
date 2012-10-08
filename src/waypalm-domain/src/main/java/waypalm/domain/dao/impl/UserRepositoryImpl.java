package waypalm.domain.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import waypalm.domain.dao.UserRepository;
import waypalm.domain.entity.Profile;
import waypalm.domain.entity.ProfileSummary;
import waypalm.domain.entity.SocialLink;

@Repository
public class UserRepositoryImpl extends DaoImpl implements UserRepository {
    @Override
    public SocialLink findSocialConnection(String provider, String providerUser, String email) {
        SocialLink socialLink = null;
        if ("www.google.com".equalsIgnoreCase(provider) && StringUtils.hasText(email)) {
            socialLink = findSocialConnectionByEmail(provider, email);
        }
        return socialLink != null ? socialLink : findSocialConnectionByUser(provider, providerUser);
    }

    private SocialLink findSocialConnectionByUser(String provider, String providerUser) {
        Assert.notNull(provider);
        Assert.notNull(providerUser);
        return (SocialLink) getSession()
                .createQuery("from SocialLink c where c.provider=:provider and c.providerUser=:providerUser")
                .setString("provider", provider)
                .setString("providerUser", providerUser)
                .uniqueResult();
    }

    private SocialLink findSocialConnectionByEmail(String provider, String email) {
        Assert.notNull(provider, "provider required");
        Assert.notNull(email, "email required");
        return (SocialLink) getSession()
                .createQuery("from SocialLink c where c.provider=:provider and c.userEmail=:email")
                .setString("provider", provider)
                .setString("email", email)
                .uniqueResult();
    }

    @Override
    public Profile getProfile(int id) {
        return (Profile) getSession().get(Profile.class, id);
    }

    @Override
    public Profile findProfileByEmail(String email) {
        if (!StringUtils.hasText(email)) {
            return null;
        }
        return (Profile) getSession().createQuery("from Profile  where email=:email")
                .setParameter("email", email)
                .uniqueResult();
    }

    @Override
    public Profile findProfileByAlias(String alias) {
        if (!StringUtils.hasText(alias)) {
            return null;
        }
        return (Profile) getSession().createQuery("from Profile  where alias=:alias")
                .setParameter("alias", alias)
                .uniqueResult();
    }

    @Override
    public ProfileSummary getProfileSummary(Profile user) {
        return (ProfileSummary) getSession().get(ProfileSummary.class, user);
    }
}
