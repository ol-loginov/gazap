package waypalm.common.web.model;

import java.util.Map;
import java.util.TreeMap;

public class SocialProfileProviders {
    public static final SocialProfileProvider PROVIDER_GOOGLE = new SocialProfileProvider(SocialProfileProvider.OPENID, "google", "https://www.google.com/accounts/o8/id");
    public static final SocialProfileProvider PROVIDER_YANDEX = new SocialProfileProvider(SocialProfileProvider.OPENID, "yandex", "http://openid.yandex.ru");
    public static final SocialProfileProvider PROVIDER_TWITTER = new SocialProfileProvider(SocialProfileProvider.OAUTH, "twitter", null);
    public static final SocialProfileProvider PROVIDER_FACEBOOK = new SocialProfileProvider(SocialProfileProvider.OAUTH, "facebook", null);

    public static final Map<String, SocialProfileProvider> SOCIAL_PROFILE_PROVIDERS;
    public static final Map<String, String> OPENID_PROVIDER_SITES;

    static {
        SOCIAL_PROFILE_PROVIDERS = new TreeMap<String, SocialProfileProvider>() {{
            put("google", SocialProfileProviders.PROVIDER_GOOGLE);
            put("yandex", SocialProfileProviders.PROVIDER_YANDEX);
            put("twitter", SocialProfileProviders.PROVIDER_TWITTER);
            put("facebook", SocialProfileProviders.PROVIDER_FACEBOOK);
        }};

        OPENID_PROVIDER_SITES = new TreeMap<String, String>() {{
            put("www.google.com", "google");
            put("openid.yandex.ru", "yandex");
        }};
    }
}
