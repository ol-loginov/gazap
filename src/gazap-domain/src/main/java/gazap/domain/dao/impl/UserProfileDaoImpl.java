package gazap.domain.dao.impl;

import gazap.domain.dao.UserProfileDao;
import gazap.domain.entity.UserProfile;
import gazap.domain.entity.UserSocialLink;
import gazap.domain.entity.UserSummary;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Repository
public class UserProfileDaoImpl extends DaoImpl implements UserProfileDao {
    @Override
    public UserSocialLink findSocialConnection(String provider, String providerUser, String email) {
        UserSocialLink socialLink = null;
        if ("www.google.com".equalsIgnoreCase(provider) && StringUtils.hasText(email)) {
            socialLink = findSocialConnectionByEmail(provider, email);
        }
        return socialLink != null ? socialLink : findSocialConnectionByUser(provider, providerUser);
    }

    private UserSocialLink findSocialConnectionByUser(String provider, String providerUser) {
        Assert.notNull(provider);
        Assert.notNull(providerUser);
        return (UserSocialLink) getSession()
                .createQuery("from UserSocialLink c where c.provider=:provider and c.providerUser=:providerUser")
                .setString("provider", provider)
                .setString("providerUser", providerUser)
                .uniqueResult();
    }

    private UserSocialLink findSocialConnectionByEmail(String provider, String email) {
        Assert.notNull(provider, "provider required");
        Assert.notNull(email, "email required");
        return (UserSocialLink) getSession()
                .createQuery("from UserSocialLink c where c.provider=:provider and c.userEmail=:email")
                .setString("provider", provider)
                .setString("email", email)
                .uniqueResult();
    }

    @Override
    public UserProfile getUser(int id) {
        return (UserProfile) getSession().get(UserProfile.class, id);
    }

    @Override
    public UserProfile findUserByEmail(String email) {
        if (!StringUtils.hasText(email)) {
            return null;
        }
        return (UserProfile) getSession().createQuery("from UserProfile  where email=:email")
                .setParameter("email", email)
                .uniqueResult();
    }

    @Override
    public UserProfile findUserByAlias(String alias) {
        if (!StringUtils.hasText(alias)) {
            return null;
        }
        return (UserProfile) getSession().createQuery("from UserProfile  where alias=:alias")
                .setParameter("alias", alias)
                .uniqueResult();
    }

    @Override
    public UserSummary loadSummary(UserProfile user) {
        return (UserSummary) getSession().createQuery("from UserSummary where user=:user")
                .setEntity("user", user)
                .uniqueResult();
    }
}
