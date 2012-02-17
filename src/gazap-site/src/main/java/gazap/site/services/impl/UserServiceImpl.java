package gazap.site.services.impl;

import com.iserv2.commons.lang.HashUtil;
import gazap.common.web.model.SocialProfile;
import gazap.domain.dao.UserProfileDao;
import gazap.domain.entity.UserProfile;
import gazap.domain.entity.UserSocialLink;
import gazap.site.model.ServiceErrorException;
import gazap.site.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    protected UserProfileDao profileDao;

    private UserProfile createUser(String email) throws ServiceErrorException {
        UserProfile user = new UserProfile();
        user.setPassword(HashUtil.newUuid());
        user.setContactEmail(HashUtil.isNull(email, ""));
        user.setDisplayName("");
        profileDao.create(user);
        return user;
    }

    @Override
    @Transactional
    public UserSocialLink createSocialConnection(UserProfile user, ConnectionKey key, SocialProfile social) throws ServiceErrorException {
        Assert.notNull(social, "social required");

        UserSocialLink socialLink = new UserSocialLink();
        socialLink.setProvider(key.getProviderId());
        socialLink.setProviderUser(key.getProviderUserId());
        socialLink.setUserUrl(social.getUrl());
        socialLink.setUserEmail(social.getEmail());
        socialLink.setUser(user != null ? user : createUser(social.getEmail()));

        profileDao.create(socialLink);
        return socialLink;
    }
}

