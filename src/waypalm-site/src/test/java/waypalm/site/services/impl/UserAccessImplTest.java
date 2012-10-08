package waypalm.site.services.impl;

import com.iserv2.test.ServiceTest;
import com.iserv2.test.Values;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import waypalm.common.web.model.SocialProfileProviders;
import waypalm.common.web.security.PrincipalImpl;
import waypalm.domain.dao.UserRepository;
import waypalm.domain.entity.Profile;
import waypalm.domain.entity.SocialLink;
import waypalm.domain.entity.base.EntityUtil;

public class UserAccessImplTest extends ServiceTest<UserAccessImpl> {
    @Mock
    private UserRepository userRepository;

    @Override
    public void setUp() {
        super.setUp();
        service.userRepository = userRepository;

        SecurityContextHolder.clearContext();
        SecurityContextHolder.createEmptyContext();
    }

    @Test
    public void createSocialProvider() {
        SocialLink link;

        link = new SocialLink();
        link.setProvider("google");
        Assert.assertEquals(SocialProfileProviders.PROVIDER_GOOGLE, service.createSocialProvider(link));
        link = new SocialLink();
        link.setProvider("www.google.com");
        Assert.assertEquals(SocialProfileProviders.PROVIDER_GOOGLE, service.createSocialProvider(link));

        link = new SocialLink();
        link.setProvider("yandex");
        Assert.assertEquals(SocialProfileProviders.PROVIDER_YANDEX, service.createSocialProvider(link));
        link = new SocialLink();
        link.setProvider("openid.yandex.ru");
        Assert.assertEquals(SocialProfileProviders.PROVIDER_YANDEX, service.createSocialProvider(link));

        link = new SocialLink();
        link.setProvider("twitter");
        Assert.assertEquals(SocialProfileProviders.PROVIDER_TWITTER, service.createSocialProvider(link));

        link = new SocialLink();
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

        Profile visitor = EntityUtil.withId(new Profile(), 0);
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(new PrincipalImpl(visitor), ""));
        Assert.assertFalse(service.isAuthorized());

        visitor = EntityUtil.withId(new Profile());
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(new PrincipalImpl(visitor), ""));
        Assert.assertTrue(service.isAuthorized());
    }
}
