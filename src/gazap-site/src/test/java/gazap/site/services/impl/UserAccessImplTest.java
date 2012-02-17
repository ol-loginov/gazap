package gazap.site.services.impl;

import com.iserv2.test.ServiceTest;
import com.iserv2.test.Values;
import gazap.common.web.model.SocialProfileProviders;
import gazap.common.web.security.PrincipalImpl;
import gazap.domain.dao.UserProfileDao;
import gazap.domain.entity.UserProfile;
import gazap.domain.entity.UserSocialLink;
import gazap.domain.entity.base.EntityUtil;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserAccessImplTest extends ServiceTest<UserAccessImpl> {
    @Mock
    private UserProfileDao userProfileDao;

    @Override
    public void setUp() {
        super.setUp();
        service.userProfileDao = userProfileDao;

        SecurityContextHolder.clearContext();
        SecurityContextHolder.createEmptyContext();
    }

    @Test
    public void createSocialProvider() {
        UserSocialLink link;

        link = new UserSocialLink();
        link.setProvider("google");
        Assert.assertEquals(SocialProfileProviders.PROVIDER_GOOGLE, service.createSocialProvider(link));
        link = new UserSocialLink();
        link.setProvider("www.google.com");
        Assert.assertEquals(SocialProfileProviders.PROVIDER_GOOGLE, service.createSocialProvider(link));

        link = new UserSocialLink();
        link.setProvider("yandex");
        Assert.assertEquals(SocialProfileProviders.PROVIDER_YANDEX, service.createSocialProvider(link));
        link = new UserSocialLink();
        link.setProvider("openid.yandex.ru");
        Assert.assertEquals(SocialProfileProviders.PROVIDER_YANDEX, service.createSocialProvider(link));

        link = new UserSocialLink();
        link.setProvider("twitter");
        Assert.assertEquals(SocialProfileProviders.PROVIDER_TWITTER, service.createSocialProvider(link));

        link = new UserSocialLink();
        link.setProvider("facebook");
        Assert.assertEquals(SocialProfileProviders.PROVIDER_FACEBOOK, service.createSocialProvider(link));
    }

    @Test
    public void getAvailableSocialProviders() {
        Assert.assertEquals(4, service.getAvailableSocialProviders().size());
        Values.assertContains(service.getAvailableSocialProviders(), SocialProfileProviders.PROVIDER_FACEBOOK);
        Values.assertContains(service.getAvailableSocialProviders(), SocialProfileProviders.PROVIDER_GOOGLE);
        Values.assertContains(service.getAvailableSocialProviders(), SocialProfileProviders.PROVIDER_TWITTER);
        Values.assertContains(service.getAvailableSocialProviders(), SocialProfileProviders.PROVIDER_YANDEX);
    }

    @Test
    public void isAuthorized() {
        Assert.assertFalse(service.isAuthorized());

        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("mega-name", ""));
        Assert.assertFalse(service.isAuthorized());

        UserProfile visitor = EntityUtil.withId(new UserProfile(), 0);
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(new PrincipalImpl(visitor), ""));
        Assert.assertFalse(service.isAuthorized());

        visitor = EntityUtil.withId(new UserProfile());
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(new PrincipalImpl(visitor), ""));
        Assert.assertTrue(service.isAuthorized());
    }
}
