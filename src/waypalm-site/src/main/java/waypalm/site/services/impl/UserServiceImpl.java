package waypalm.site.services.impl;

import com.iserv2.commons.lang.IservHashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import waypalm.common.web.model.SocialProfile;
import waypalm.common.web.security.PasswordSalter;
import waypalm.domain.dao.UserRepository;
import waypalm.domain.entity.UserProfile;
import waypalm.domain.entity.UserSocialLink;
import waypalm.domain.entity.UserSummary;
import waypalm.site.model.ServiceErrorException;
import waypalm.site.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    @Qualifier("passwordEncoder")
    protected PasswordEncoder passwordEncoder;
    @Autowired
    @Qualifier("passwordSalter")
    protected PasswordSalter passwordSalter;

    private UserProfile createUserWithRandomPassword(String email) throws ServiceErrorException {
        return createUser(IservHashUtil.isNull(email, ""), IservHashUtil.newUuid());
    }

    @Override
    @Transactional
    public UserProfile createUser(String email, String password) {
        UserProfile user = new UserProfile();
        user.setPasswordSalt(IservHashUtil.md5("" + user.getCreatedAt().getTime() + System.nanoTime(), true));
        user.setPassword(passwordEncoder.encodePassword(password, passwordSalter.getSalt(user.getPasswordSalt())));
        user.setEmail(IservHashUtil.isNull(email, ""));
        user.setDisplayName("");
        userRepository.create(user);

        UserSummary userSummary = new UserSummary(user);
        userRepository.create(userSummary);

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

        userRepository.create(socialLink);
        return socialLink;
    }

    @Override
    public UserProfile findUserByAliasOrId(String aliasOrId) {
        if (!StringUtils.hasText(aliasOrId)) {
            return null;
        }
        return Character.isDigit(aliasOrId.charAt(0))
                ? userRepository.getUser(Integer.parseInt(aliasOrId))
                : userRepository.findUserByAlias(aliasOrId);
    }
}

