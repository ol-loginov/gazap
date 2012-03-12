package gazap.site.services.impl;

import com.iserv2.commons.lang.HashUtil;
import gazap.common.web.model.SocialProfile;
import gazap.domain.dao.UserProfileDao;
import gazap.domain.entity.UserProfile;
import gazap.domain.entity.UserSocialLink;
import gazap.domain.entity.UserSummary;
import gazap.site.model.ServiceErrorException;
import gazap.site.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    protected UserProfileDao profileDao;
    @Autowired
    @Qualifier("passwordEncoder")
    protected PasswordEncoder passwordEncoder;

    private UserProfile createUserWithRandomPassword(String email) throws ServiceErrorException {
        return createUser(HashUtil.isNull(email, ""), HashUtil.newUuid());
    }

    @Override
    @Transactional
    public UserProfile createUser(String email, String password) {
        UserProfile user = new UserProfile();
        user.setPassword(passwordEncoder.encodePassword(password, null));
        user.setEmail(HashUtil.isNull(email, ""));
        user.setDisplayName("");
        profileDao.create(user);

        UserSummary userSummary = new UserSummary(user);
        profileDao.create(userSummary);

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
        socialLink.setUser(user != null ? user : createUserWithRandomPassword(social.getEmail()));

        profileDao.create(socialLink);
        return socialLink;
    }
}

