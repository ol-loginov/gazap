package gazap.site.services;

import gazap.domain.entity.UserSocialLink;
import gazap.site.model.SocialProfileProvider;

import java.util.Collection;

public interface UserAccess {
    boolean isAuthorized();

    Collection<SocialProfileProvider> getAvailableSocialProviders();

    SocialProfileProvider createSocialProvider(UserSocialLink link);

    SocialProfileProvider PROVIDER_GOOGLE = new SocialProfileProvider(SocialProfileProvider.OPENID, "google", "https://www.google.com/accounts/o8/id");
    SocialProfileProvider PROVIDER_YANDEX = new SocialProfileProvider(SocialProfileProvider.OPENID, "yandex", "http://openid.yandex.ru");
    SocialProfileProvider PROVIDER_TWITTER = new SocialProfileProvider(SocialProfileProvider.OAUTH, "twitter", null);
    SocialProfileProvider PROVIDER_FACEBOOK = new SocialProfileProvider(SocialProfileProvider.OAUTH, "facebook", null);
}
